import React, { Component, PropTypes } from 'react';
import {push} from 'react-router-redux';
import {connect} from 'react-redux';

import QuickActions from './QuickActions';
import Table from '../table/Table';
import Filters from '../filters/Filters';
import SelectionAttributes from './SelectionAttributes';
import DataLayoutWrapper from '../DataLayoutWrapper';

import SockJs from 'sockjs-client';
import Stomp from 'stompjs/lib/stomp.min.js';

import {
    initLayout
} from '../../actions/GenericActions';

import {
    selectTableItems
} from '../../actions/WindowActions';

import {
    createViewRequest,
    browseViewRequest
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

    componentWillReceiveProps(props) {
        const {
            windowType, defaultViewId, defaultSort, defaultPage, selected,
            inBackground
        } = props;
        const {page, sort, viewId, cachedSelection} = this.state;
        
        /* 
         * If we browse list of docs, changing type of Document
         * does not re-construct component, so we need to
         * make it manually while the windowType changes.
         */
        if(windowType !== this.props.windowType) {
            this.setState({
                data:null,
                layout:null,
                filters: null,
                viewId: null
            }, () => {

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
            this.connectWS(defaultViewId);
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
                this.props.dispatch(
                    selectTableItems(cachedSelection)
                )
            }else{
                this.setState({
                    cachedSelection: selected
                })
            }
        }
    }

    componentDidMount() {
        const {viewId} = this.state;
        this.connectWS(viewId);
    }

    componentWillUnmount() {
        this.disconnectWS();
    }

    shouldComponentUpdate(nextProps, nextState) {
        return !!nextState.layout && !!nextState.data;
    }

    connectWS = (viewId) => {
        this.sockClient && this.sockClient.disconnect();

        this.sock = new SockJs(config.WS_URL);
        this.sockClient = Stomp.Stomp.over(this.sock);
        this.sockClient.debug = null;
        this.sockClient.connect({}, () => {
            this.sockClient.subscribe('/view/'+ viewId, msg => {
                const {fullyChanged} = JSON.parse(msg.body);
                if(fullyChanged == true){
                    this.browseView();
                }
            });
        });
    }

    disconnectWS = () => {
        this.sockClient && this.sockClient.disconnect();
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

    // FETCHING LAYOUT && DATA -------------------------------------------------

    fetchLayoutAndData = (isNewFilter) => {
        const {
            dispatch, windowType, type, setModalTitle
        } = this.props;

        const {
            viewId
        } = this.state;

        dispatch(
            initLayout('documentView', windowType, null, null, null, null, type, true)
        ).then(response => {
            this.setState({
                layout: response.data,
                page:1,
                sort:null
            }, () => {
                if(viewId && !isNewFilter){
                    this.browseView();
                }else{
                    this.createView();
                }

                setModalTitle && setModalTitle(response.data.caption)
            })
        });
    }

    /*
     *  If viewId exist, than browse that view.
     */
    browseView = () => {
        const {viewId, page, sort} = this.state;

        this.getData(
            viewId, page, sort
        ).catch((err) => {
            if(err.response && err.response.status === 404) {
                this.createView();
            }
        });
    }

    createView = () => {
        const {
            dispatch, windowType, type, refType, refId
        } = this.props;

        const {page, sort, filters} = this.state;

        dispatch(
            createViewRequest(windowType, type, this.pageLength, filters, refType, refId)
        ).then(response => {
            this.setState({
                data: response.data,
                viewId: response.data.viewId
            }, () => {
                this.getData(response.data.viewId, page, sort);
            })
        })
    }

    getData = (id, page, sortingQuery) => {
        const {dispatch, windowType, updateUri} = this.props;

        if(updateUri){
            id && updateUri('viewId', id);
            page && updateUri('page', page);
            sortingQuery && updateUri('sort', sortingQuery);
        }

        return dispatch(
            browseViewRequest(id, page, this.pageLength, sortingQuery, windowType)
        ).then(response => {
            this.setState({
                data: response.data,
                viewId: response.data.viewId,
                filters: response.data.filters,
                refresh: Date.now()
            });
        });
    }

    // END OF FETCHING LAYOUT && DATA ------------------------------------------

    // MANAGING SORT, PAGINATION, FILTERS --------------------------------------

    handleChangePage = (index) => {
        const {data, sort, page, viewId} = this.state;

        let currentPage = page;

        switch(index){
            case 'up':
                currentPage * data.pageLength < data.size ? currentPage++ : null;
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
            this.getData(viewId, startPage ? 1 : page, this.getSortingQuery(asc, field));
        });
    }

    handleFilterChange = (filters) => {
        this.setState({
            filters: filters
        }, () => {
            this.fetchLayoutAndData(true);
        })
    }

    // END OF MANAGING SORT, PAGINATION, FILTERS -------------------------------

    render() {
        const {
            layout, data, viewId, clickOutsideLock, refresh, page, filters
        } = this.state;

        const {
            dispatch, windowType, open, closeOverlays, selected, inBackground,
            fetchQuickActionsOnInit, isModal
        } = this.props;
        

        if(layout && data) {
            return (
                <div className="document-list-wrapper">
                    <div className="panel panel-primary panel-spaced panel-inline document-list-header">
                        <div>
                            {layout.supportNewRecord && !isModal &&
                                <button
                                    className="btn btn-meta-outline-secondary btn-distance btn-sm hidden-sm-down btn-new-document"
                                    onClick={() => this.redirectToNewDocument()}
                                    title={'New '+ layout.newRecordCaption}
                                >
                                    <i className="meta-icon-add" /> {layout.newRecordCaption}
                                </button>
                            }
                            {layout.filters && <Filters
                                filterData={layout.filters}
                                filtersActive={filters}
                                windowType={windowType}
                                viewId={viewId}
                                updateDocList={this.handleFilterChange}
                            />}
                        </div>
                        <QuickActions
                            windowType={windowType}
                            viewId={viewId}
                            selected={data.size && selected}
                            refresh={refresh}
                            shouldNotUpdate={inBackground}
                            fetchOnInit={fetchQuickActionsOnInit}
                        />
                    </div>
                    <div className="document-list-body">
                        <Table
                            entity="documentView"
                            ref={c => this.table =
                                c && c.getWrappedInstance()
                                && c.getWrappedInstance().refs.instance
                            }
                            rowData={{1: data.result}}
                            cols={layout.elements}
                            tabid={1}
                            type={windowType}
                            emptyText={layout.emptyResultText}
                            emptyHint={layout.emptyResultHint}
                            readonly={true}
                            keyProperty="id"
                            onDoubleClick={(id) => {
                                !isModal &&
                                dispatch(push('/window/' + windowType + '/' + id))
                            }}
                            isModal={isModal}
                            size={data.size}
                            pageLength={this.pageLength}
                            handleChangePage={this.handleChangePage}
                            page={page}
                            mainTable={true}
                            updateDocList={this.fetchLayoutAndData}
                            sort={this.sortData}
                            orderBy={data.orderBy}
                            tabIndex={0}
                            open={open}
                            closeOverlays={closeOverlays}
                            indentSupported={layout.supportTree}
                            disableOnClickOutside={clickOutsideLock}
                            defaultSelected={selected}
                        >
                            {layout.supportAttributes &&
                                <DataLayoutWrapper
                                    className="table-flex-wrapper attributes-selector"
                                    entity="documentView"
                                    windowType={windowType}
                                    viewId={viewId}
                                >
                                    <SelectionAttributes
                                        refresh={refresh}
                                        setClickOutsideLock={this.setClickOutsideLock}
                                        selected={data.size && selected}
                                        shouldNotUpdate={inBackground}
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
    windowType: PropTypes.number.isRequired,
    dispatch: PropTypes.func.isRequired
}

DocumentList = connect()(DocumentList);

export default DocumentList;
