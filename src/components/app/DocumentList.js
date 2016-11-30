import React, { Component, PropTypes } from 'react';
import {push, replace} from 'react-router-redux';
import {connect} from 'react-redux';

import DatetimeRange from '../Widget/DatetimeRange';
import Table from '../Table/Table';
import Filters from '../Filters/Filters';

import {
    viewLayoutRequest,
    createViewRequest,
    browseViewRequest
} from '../../actions/AppActions';

import {
    setPagination,
    setSorting,
    clearListProps,
    initDocumentView,
    setFilter
} from '../../actions/ListActions';

class DocumentList extends Component {
    constructor(props){
        super(props);
        const {type, windowType} = props;

        this.state = {
            data: null,
            layout: null
        }

        this.updateData(type, windowType);
    }

    componentWillReceiveProps(props) {
        const {sorting, type, windowType} = props;

        //if we browse list of docs, changing type of Document
        //does not re-construct component, so we need to
        //make it manually while the windowType changes.
        if(windowType !== this.props.windowType) {
            this.updateData(type, windowType);
        }
    }

    shouldComponentUpdate(nextProps, nextState) {
        return !!nextState.layout && !!nextState.data;
    }

    componentDidUpdate(prevProps) {
        const {windowType, filters} = this.props;

        let oldFilter = prevProps.filters[0] ? JSON.stringify(prevProps.filters[0]) : '';
        let newFilter = filters[0] ? JSON.stringify(filters[0]) : '';

        if(newFilter !== oldFilter){
            this.updateData('grid', windowType);
        }
    }

    updateData = (type, windowType) => {
        const {dispatch,filters, filtersWindowType} = this.props;

        if(!!filtersWindowType && (filtersWindowType != windowType)) {
            dispatch(setFilter(null,null));
        }else{
            windowType && dispatch(viewLayoutRequest(windowType, type)).then(response => {
                this.setState(Object.assign({}, this.state, {
                    layout: response.data
                }), () => {
                    dispatch(createViewRequest(windowType, type, 20, filters)).then((response) => {
                        this.setState(Object.assign({}, this.state, {
                            data: response.data
                        }), () => {
                            this.getView(response.data.viewId);
                            dispatch(initDocumentView(response.data.viewId));
                        })
                    })
                })
            });
        }
    }

    getView = (viewId) => {
        const {data} = this.state;
        const {dispatch, page, sorting, windowType, query, updateUri} = this.props;
        let urlQuery = "";
        let urlPage = page;

        !!updateUri && updateUri("viewId", viewId);

        if(query){
            if(query.sort){
                urlQuery = query.sort;
                dispatch(setSorting(urlQuery.substring(1), urlQuery[0], windowType));
            }
            if(query.page){
                urlPage = query.page;
                dispatch(setPagination(parseInt(query.page)));
            }

        }

        //
        //  Condition, that ensure wheter windowType
        //  is the same as for saved query params
        //
        else if(windowType === sorting.windowType) {
            urlQuery = this.getSortingQuery(sorting.dir, sorting.prop);
        }else{
            dispatch(clearListProps());
        }


        this.getData(data.viewId, urlPage, 20, urlQuery);
    }

    getData = (id, page, pages, sortingQuery) => {
        const {data} = this.state;
        const {dispatch} = this.props;

        dispatch(browseViewRequest(id, page, pages, sortingQuery)).then((response) => {
            this.setState(Object.assign({}, this.state, {
                data: response.data
            }))
        });
    }

    getSortingQuery = (asc, field) => {
        let sortingQuery = '';

        if(field && asc) {
            sortingQuery = '+' + field;
        } else if(field && !asc) {
            sortingQuery = '-' + field;
        }
        return sortingQuery;
    }

    sortData = (asc, field, startPage, currPage) => {
        const {sorting, page, dispatch, windowType, updateUri} = this.props;
        const {data} = this.state;

        asc && field && !!updateUri && updateUri("sort", (asc?"+":"-")+field);

        dispatch(setSorting(field, asc, windowType));

        if(startPage){
            dispatch(setPagination(1, windowType));
        }

        this.getData(data.viewId, currPage, 20, this.getSortingQuery(asc, field));
    }

    handleChangePage = (index) => {
        const {data} = this.state;
        const {sorting, page, dispatch, updateUri} = this.props;

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

        if(currentPage !== page){
            dispatch(setPagination(currentPage));
            !!updateUri && updateUri("page", currentPage);

            this.sortData(sorting.dir, sorting.prop, false, currentPage);
        }
    }

    render() {
        const {layout, data} = this.state;
        const {dispatch, windowType, type, filters, page} = this.props;

        if(layout && data) {
            return (
                <div>
                    <div className="panel panel-primary panel-spaced panel-inline document-list-header">
                        {type === "grid" &&
                            <button
                                className="btn btn-meta-outline-secondary btn-distance btn-sm hidden-sm-down"
                                onClick={() => dispatch(push('/window/' + windowType + '/new'))}
                            >
                                <i className="meta-icon-add" /> New {layout.caption}
                            </button>
                        }
                        <Filters
                            filterData={layout.filters}
                            windowType={windowType}
                            updateDocList={this.updateData}
                        />
                    </div>

                    <div>
                        <Table
                            ref={c => this.table = c && c.getWrappedInstance().refs.instance}
                            rowData={{1: data.result}}
                            cols={layout.elements}
                            tabid={1}
                            type={windowType}
                            emptyText={layout.emptyResultText}
                            emptyHint={layout.emptyResultHint}
                            readonly={true}
                            keyProperty="id"
                            onDoubleClick={(id) => {dispatch(push("/window/" + windowType + "/" + id))}}
                            size={data.size}
                            pageLength={20}
                            handleChangePage={this.handleChangePage}
                            page={page}
                            mainTable={true}
                            updateDocList={this.updateData}
                            sort={this.sortData}
                            orderBy={data.orderBy}
                        />
                    </div>
                </div>
            );
        }else{
            return false;
        }

    }
}

DocumentList.propTypes = {
    dispatch: PropTypes.func.isRequired,
    page: PropTypes.number.isRequired,
    sorting: PropTypes.object.isRequired,
    filters: PropTypes.array.isRequired,
    filtersWindowType: PropTypes.string
}

function mapStateToProps(state) {
    const { listHandler } = state;

    const {
        filters,
        filtersWindowType,
        page,
        sorting
    } = listHandler || {
        filters: {},
        page: 1,
        sorting: {},
        filtersWindowType: ""
    }

    return {
        filters,
        page,
        sorting,
        filtersWindowType
    }
}

DocumentList = connect(mapStateToProps)(DocumentList)

export default DocumentList;
