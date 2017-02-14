import React, { Component, PropTypes } from 'react';
import {push, replace} from 'react-router-redux';
import {connect} from 'react-redux';

import DatetimeRange from '../widget/DatetimeRange';
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
    createViewRequest,
    browseViewRequest,
    addNotification
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
            refresh: null
        }
        this.fetchLayoutAndData();
    }

    componentWillReceiveProps(props) {
        const {windowType, defaultViewId, defaultSort, defaultPage} = props;
        const {page, sort, viewId} = this.state;
        //if we browse list of docs, changing type of Document
        //does not re-construct component, so we need to
        //make it manually while the windowType changes.
        if(windowType !== this.props.windowType) {
            this.setState({
                data:null,
                layout:null
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
        this.sockClient.connect({}, frame => {
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
        const {dispatch, windowType} = this.props;
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
            id && updateUri("viewId", id);
            page && updateUri("page", page);
            sortingQuery && updateUri("sort", sortingQuery);
        }

        return dispatch(
            browseViewRequest(id, page, this.pageLength, sortingQuery, windowType)
        ).then(response => {
            this.setState({
                data: response.data,
                viewId: response.data.viewId,
                filters: response.data.filters,
                refresh: Date.now()
            })
        });
    }

    // END OF FETCHING LAYOUT && DATA ------------------------------------------

    // MANAGING SORT, PAGINATION, FILTERS --------------------------------------

    handleChangePage = (index) => {
        const {data, sort, page, viewId} = this.state;
        const {dispatch} = this.props;

        let currentPage = page;

        switch(index){
            case "up":
                currentPage * data.pageLength < data.size ? currentPage++ : null;
                break;
            case "down":
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

    getSortingQuery = (asc, field) => (asc ? "+" : "-") + field;

    sortData = (asc, field, startPage, currPage) => {
        const {data, viewId, page} = this.state;

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
            layout, data, viewId, clickOutsideLock, refresh, page, sort, filters
        } = this.state;

        const {
            dispatch, windowType, type, open, closeOverlays, selected, inBackground
        } = this.props;

        if(layout && data) {
            return (
                <div className="document-list-wrapper">
                    <div className="panel panel-primary panel-spaced panel-inline document-list-header">
                        <div>
                            {type === "grid" &&
                                <button
                                    className="btn btn-meta-outline-secondary btn-distance btn-sm hidden-sm-down"
                                    onClick={() => this.redirectToNewDocument()}
                                >
                                    <i className="meta-icon-add" /> New {layout.caption}
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
                                dispatch(push("/window/" + windowType + "/" + id))
                            }}
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
    dispatch: PropTypes.func.isRequired,
}

DocumentList = connect()(DocumentList);

export default DocumentList;
