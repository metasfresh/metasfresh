import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {push} from 'react-router-redux';
import {connect} from 'react-redux';
import counterpart from 'counterpart';

import QuickActions from './QuickActions';
import BlankPage from '../BlankPage';
import Table from '../table/Table';
import Filters from '../filters/Filters';
import SelectionAttributes from './SelectionAttributes';
import DataLayoutWrapper from '../DataLayoutWrapper';

import {
    initLayout,
    getDataByIds
} from '../../actions/GenericActions';

import {
    selectTableItems,
    getItemsByProperty,
    mapIncluded,
    indicatorState,
    connectWS,
    disconnectWS
} from '../../actions/WindowActions';

import {
    setSorting,
    setPagination,
    setListId,
    setListIncludedView
} from '../../actions/ListActions';

import {
    createViewRequest,
    browseViewRequest,
    filterViewRequest
} from '../../actions/AppActions';

class DocumentList extends Component {
    constructor(props){
        super(props);

        const {defaultViewId, defaultPage, defaultSort} = this.props;

        this.pageLength = 20;

        this.state = {
            data: null,
            layout: null,

            viewId: defaultViewId,
            page: defaultPage || 1,
            sort: defaultSort,
            filters: null,

            clickOutsideLock: false,
            refresh: null,

            cachedSelection: null
        }
        this.fetchLayoutAndData();
    }

    componentDidMount = () => {
        this.mounted = true
    }

    componentDidUpdate(prevProps, prevState) {
        const { setModalDescription } = this.props;
        const { data } = this.state;
        if(prevState.data !== data){
            setModalDescription && setModalDescription(data.description);
        }
    }

    componentWillUnmount() {
        this.mounted = false;
        disconnectWS.call(this);
    }

    componentWillReceiveProps(props) {
        const {
            windowType, defaultViewId, defaultSort, defaultPage, selected,
            inBackground, dispatch, includedView, selectedWindowType,
            disconnectFromState, refId
        } = props;
        const {page, sort, viewId, cachedSelection, layout} = this.state;

        /*
         * If we browse list of docs, changing type of Document
         * does not re-construct component, so we need to
         * make it manually while the windowType changes.
         * OR
         * We want to refresh the window (generate new viewId)
         * OR
         * The reference ID is changed
         */
        if(
            windowType !== this.props.windowType ||
            (defaultViewId === undefined &&
                defaultViewId !== this.props.defaultViewId) ||
            refId !== this.props.refId
        ) {
            this.setState({
                data:null,
                layout:null,
                filters: null,
                viewId: null
            }, () => {
                !disconnectFromState && dispatch(selectTableItems([], null))
                this.fetchLayoutAndData();
            });
        }

        if(
            defaultSort != this.props.defaultSort &&
            defaultSort != sort
        ){
            this.setState({
                sort: defaultSort
            });
        }

        if(
            defaultPage != this.props.defaultPage &&
            defaultPage != page
        ){
            this.setState({
                page: defaultPage || 1
            });
        }

        if(
            defaultViewId != this.props.defaultViewId &&
            defaultViewId != viewId
        ) {
            this.setState({
                viewId: defaultViewId
            });
        }

        /*
         * It is case when we need refersh global selection state,
         * because scope is changed
         *
         * After opening modal cache current selection
         * After closing modal with gridview, refresh selected.
         */
        if(
            inBackground != this.props.inBackground
        ) {
            if(!inBackground){
                // In case of preventing cached selection restore
                cachedSelection && !disconnectFromState &&
                    dispatch(selectTableItems(cachedSelection, windowType))
                this.setState({
                    refreshSelection: true
                }, () => this.setState({
                    refreshSelection: false
                }))
            }else{
                this.setState({
                    cachedSelection: selected
                })
            }
        }

        /*
         * When the selection of unfocused table changes
         */
        if(
            selectedWindowType === windowType &&
            cachedSelection !== null &&
            cachedSelection !== undefined &&
            layout && layout.supportIncludedView &&
            includedView && includedView.windowType && includedView.viewId
        ){
            // There is no need to restore cached selection in that case
            this.setState({
                cachedSelection: null
            }, () => {
                dispatch(setListIncludedView());
            })
        }
    }

    shouldComponentUpdate(nextProps, nextState) {
        return !!nextState.layout && !!nextState.data;
    }

    connectWS = (viewId) => {
        const {windowType} = this.props;
        connectWS.call(this, '/view/' + viewId, (msg) => {
            const {fullyChanged, changedIds} = msg;
            if(changedIds){
                getDataByIds(
                    'documentView', windowType, viewId, changedIds.join()
                ).then(response => {
                    response.data.map(row => {
                        this.setState({
                            data: Object.assign(this.state.data, {}, {
                                result: this.state.data.result.map(
                                    resultRow =>
                                        resultRow.id === row.id ?
                                            row : resultRow
                                )
                            })
                        })
                    })
                });
            }
            if(fullyChanged == true){
                this.browseView(true);
            }
        });
    }

    doesSelectionExist(selected, hasIncluded) {
        const {data} = this.state;
        // When the rows are changing we should ensure
        // that selection still exist

        if(hasIncluded){
            return true;
        }

        let rows = [];

        data && data.result && data.result.map(item => {
            rows = rows.concat(mapIncluded(item));
        });

        return (data && data.size && data.result && selected && selected[0] &&
            getItemsByProperty(
                rows, 'id', selected[0]
            ).length
        );
    }

    getTableData = (data) => {
        return data;
    }

    redirectToNewDocument = () => {
        const {dispatch, windowType} = this.props;

        dispatch(push('/window/' + windowType + '/new'));
    }

    setClickOutsideLock = (value) => {
        this.setState({
            clickOutsideLock: !!value
        })
    }
    
    clearStaticFilters = () => {
        const {dispatch, windowType, viewId} = this.props;

        dispatch(push('/window/' + windowType));
    }

    // FETCHING LAYOUT && DATA -------------------------------------------------

    fetchLayoutAndData = (isNewFilter) => {
        const {
            windowType, type, setModalTitle, setNotFound
        } = this.props;

        const {
            viewId
        } = this.state;

        initLayout(
            'documentView', windowType, null, null, null, null, type, true
        ).then(response => {
            this.mounted && this.setState({
                layout: response.data
            }, () => {
                if(viewId && !isNewFilter){
                    this.browseView();
                }else{
                    if(viewId){
                        this.filterView();
                    } else {
                        this.createView();
                    }
                }
                setModalTitle && setModalTitle(response.data.caption)
            })
        }).catch(() => {
            // We have to always update that fields to refresh that view!
            // Check the shouldComponentUpdate method
            this.setState({
                data: 'notfound',
                layout: 'notfound'
            }, () => {
                setNotFound && setNotFound(true);
            })
        })
    }

    /*
     *  If viewId exist, than browse that view.
     */
    browseView = (refresh) => {
        const {viewId, page, sort} = this.state;

        this.getData(
            viewId, page, sort, refresh
        ).catch((err) => {
            if(err.response && err.response.status === 404) {
                this.createView();
            }
        });
    }

    createView = () => {
        const {
            windowType, type, refType, refId
        } = this.props;

        const {page, sort, filters} = this.state;

        createViewRequest(
            windowType, type, this.pageLength, filters, refType, refId
        ).then(response => {
            this.mounted && this.setState({
                data: response.data,
                viewId: response.data.viewId
            }, () => {
                this.getData(response.data.viewId, page, sort);
            })
        })
    }

    filterView = () => {
        const {
            windowType
        } = this.props;

        const {page, sort, filters, viewId} = this.state;

        filterViewRequest(
            windowType, viewId, filters
        ).then(response => {
            this.mounted && this.setState({
                data: response.data,
                viewId: response.data.viewId
            }, () => {
                this.getData(response.data.viewId, page, sort);
            })
        })
    }

    getData = (id, page, sortingQuery, refresh) => {
        const {
            dispatch, windowType, updateUri, setNotFound
        } = this.props;

        setNotFound && setNotFound(false);
        dispatch(indicatorState('pending'));

        if(updateUri){
            id && updateUri('viewId', id);
            page && updateUri('page', page);
            sortingQuery && updateUri('sort', sortingQuery);
        }

        return browseViewRequest(
            id, page, this.pageLength, sortingQuery, windowType
        ).then(response => {
            dispatch(indicatorState('saved'));

            this.mounted && this.setState(Object.assign({}, {
                data: response.data,
                filters: response.data.filters
            }, refresh && {
                refresh: Date.now()
            }), () => {
                this.connectWS(response.data.viewId);
            })
        });
    }

    // END OF FETCHING LAYOUT && DATA ------------------------------------------

    // MANAGING SORT, PAGINATION, FILTERS --------------------------------------

    handleChangePage = (index) => {
        const {data, sort, page, viewId} = this.state;

        let currentPage = page;

        switch(index){
            case 'up':
                currentPage * data.pageLength < data.size ?
                    currentPage++ : null;
                break;
            case 'down':
                currentPage != 1 ? currentPage-- : null;
                break;
            default:
                currentPage = index;
        }

        this.setState({
            page: currentPage
        }, () => {
            this.getData(viewId, currentPage, sort);
        });
    }

    getSortingQuery = (asc, field) => (asc ? '+' : '-') + field;

    sortData = (asc, field, startPage) => {
        const {viewId, page} = this.state;

        this.setState({
            sort: this.getSortingQuery(asc, field)
        }, () => {
            this.getData(
                viewId, startPage ? 1 : page, this.getSortingQuery(asc, field)
            );
        });
    }

    handleFilterChange = (filters) => {
        this.setState({
            filters: filters,
            page: 1
        }, () => {
            this.fetchLayoutAndData(true);
        })
    }

    // END OF MANAGING SORT, PAGINATION, FILTERS -------------------------------

    redirectToDocument = (id) => {
        const {
            dispatch, isModal, windowType, isSideListShow
        } = this.props;
        const {page, viewId, sort} = this.state;

        if(isModal){
            return;
        }

        dispatch(push('/window/' + windowType + '/' + id));

        if(!isSideListShow){
            // Caching last settings
            dispatch(setPagination(page, windowType));
            dispatch(setSorting(sort, windowType));
            dispatch(setListId(viewId, windowType));
        }
    }

    render() {
        const {
            layout, data, viewId, clickOutsideLock, refresh, page, filters,
            cachedSelection
        } = this.state;

        const {
            windowType, open, closeOverlays, selected, inBackground,
            fetchQuickActionsOnInit, isModal, processStatus, readonly,
            includedView, isIncluded, disablePaginationShortcuts,
            notfound, disconnectFromState, autofocus, selectedWindowType
        } = this.props;

        const hasIncluded = layout && layout.supportIncludedView &&
            includedView && includedView.windowType && includedView.viewId;
        const selectionValid = this.doesSelectionExist(selected, hasIncluded);

        if(notfound || layout === 'notfound' || data === 'notfound'){
            return <BlankPage 
                what={counterpart.translate('view.error.windowName')}
            />
        }

        if(layout && data) {
            return (
                <div
                    className={
                        'document-list-wrapper ' +
                        (isIncluded ? 'document-list-included ' : '') +
                        (hasIncluded ? 'document-list-has-included ' : '')
                    }
                >
                        {!readonly && <div
                            className="panel panel-primary panel-spaced panel-inline document-list-header"
                        >
                            <div className={hasIncluded ? 'disabled' : ''}>
                                {layout.supportNewRecord && !isModal &&
                                    <button
                                        className="btn btn-meta-outline-secondary btn-distance btn-sm hidden-sm-down btn-new-document"
                                        onClick={() =>
                                            this.redirectToNewDocument()}
                                        title={layout.newRecordCaption}
                                    >
                                        <i className="meta-icon-add" />
                                        {layout.newRecordCaption}
                                    </button>
                                }
                                {layout.filters && <Filters
                                    {...{windowType, viewId}}
                                    filterData={layout.filters}
                                    filtersActive={filters}
                                    updateDocList={this.handleFilterChange}
                                    clearStaticFilters={this.clearStaticFilters}
                                />}
                            </div>
                            <QuickActions
                                {...{windowType, selectedWindowType, viewId,
                                    refresh, processStatus}}
                                selected={selectionValid ? selected : undefined}
                                fetchOnInit={fetchQuickActionsOnInit}
                                disabled={hasIncluded}
                                shouldNotUpdate={inBackground && !hasIncluded}
                            />
                        </div>}
                        <div className="document-list-body">
                            <Table
                                entity="documentView"
                                ref={c => this.table =
                                    c && c.getWrappedInstance()
                                    && c.getWrappedInstance().refs.instance
                                }
                                rowData={{1: data.result}}
                                cols={layout.elements}
                                collapsible={layout.collapsible}
                                expandedDepth={layout.expandedDepth}
                                tabid={1}
                                type={windowType}
                                emptyText={layout.emptyResultText}
                                emptyHint={layout.emptyResultHint}
                                readonly={true}
                                keyProperty="id"
                                onDoubleClick={(id) =>
                                        !isIncluded &&
                                            this.redirectToDocument(id)}
                                size={data.size}
                                pageLength={this.pageLength}
                                handleChangePage={this.handleChangePage}
                                mainTable={true}
                                updateDocList={this.fetchLayoutAndData}
                                sort={this.sortData}
                                orderBy={data.orderBy}
                                tabIndex={0}
                                indentSupported={layout.supportTree}
                                disableOnClickOutside={clickOutsideLock}
                                defaultSelected={cachedSelection ?
                                    cachedSelection : selected}
                                refreshSelection={this.state.refreshSelection}
                                queryLimitHit={data.queryLimitHit}
                                doesSelectionExist={this.doesSelectionExist}
                                {...{isIncluded, disconnectFromState, autofocus,
                                    open, page, closeOverlays, inBackground,
                                    disablePaginationShortcuts, isModal,
                                    hasIncluded, viewId
                                }}
                            >
                                {layout.supportAttributes && !isIncluded &&
                                    !hasIncluded &&
                                    <DataLayoutWrapper
                                        className="table-flex-wrapper attributes-selector js-not-unselect"
                                        entity="documentView"
                                        {...{windowType, viewId}}
                                    >
                                        <SelectionAttributes
                                            {...{refresh}}
                                            setClickOutsideLock={
                                                this.setClickOutsideLock
                                            }
                                            selected={selectionValid ?
                                                selected : undefined
                                            }
                                            shouldNotUpdate={
                                                inBackground
                                            }
                                        />
                                    </DataLayoutWrapper>
                                }
                            </Table>
                        </div>
                </div>
            );
        }else{
            return false;
        }

    }
}

DocumentList.propTypes = {
    windowType: PropTypes.string.isRequired,
    dispatch: PropTypes.func.isRequired
}

DocumentList = connect()(DocumentList);

export default DocumentList;
