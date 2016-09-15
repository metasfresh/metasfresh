import React, { Component, PropTypes } from 'react';
import {push} from 'react-router-redux';
import {connect} from 'react-redux';

import Header from '../components/app/Header';
import DatetimeRange from '../components/app/DatetimeRange';
import Table from '../components/table/Table';

import {
    viewLayoutRequest,
    createViewRequest,
    browseViewRequest
} from '../actions/AppActions';

class DocList extends Component {
    constructor(props){
        super(props);

        const {dispatch} = this.props;

        this.state = {
            page: 1
        };

        dispatch(viewLayoutRequest(143, "list")).then((response) => {
            return this.setState(Object.assign({}, this.state, {
                layout: response.data,
                filters: response.data.filters
            }))
        }).then(()=>
            dispatch(createViewRequest(143, "list", 50, [])).then((response) => {
                return this.setState(Object.assign({}, this.state, {
                    data: response.data
                }))
            })
        ).then(() => {
            dispatch(browseViewRequest(this.state.data.viewId, this.state.page, 50)).then((response) => {
                this.setState(Object.assign({}, this.state, {
                    data: response.data
                }))
            });
        });
    }

    handleChangePage = (index) => {
        
    }

    render() {
        const {dispatch, windowType} = this.props;
        const {layout,data} = this.state;
        return (
            <div>
                <Header />
                <div className="container header-sticky-distance">
                    <div className="panel panel-primary panel-spaced panel-inline document-list-header">
                        <button
                            className="btn btn-meta-outline-secondary btn-distance btn-sm"
                            onClick={() => dispatch(push('/window/' + windowType + '/new'))}
                        >
                            <i className="meta-icon-add" /> New sales order
                        </button>
                        <span>Filters: </span>
                        <DatetimeRange />
                        <button className="btn btn-meta-outline-secondary btn-distance btn-sm">
                            <i className="meta-icon-preview" /> No search filters
                        </button>

                    </div>
                    <div>
                        {layout && data && <Table
                            rowData={{1: data.result}}
                            cols={layout.elements}
                            tabid={1}
                            type={windowType}
                            emptyText={layout.emptyResultText}
                            emptyHint={layout.emptyResultHint}
                            readonly={true}
                        />}
                    </div>
                    <div className="items-row">
                        <div>
                            <p>No items selected</p>
                            <p>Select all on this page</p>
                        </div>
                        <div>
                        <nav aria-label="Page navigation">
                            <ul className="pagination">
                                <li className="page-item">
                                    <a className="page-link" href="#" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                    <span className="sr-only">Previous</span>
                                    </a>
                                </li>
                                <li className="page-item"><a className="page-link" href="#">1</a></li>
                                <li className="page-item"><a className="page-link" href="#">2</a></li>
                                <li className="page-item"><a className="page-link" href="#">3</a></li>
                                <li className="page-item"><a className="page-link" href="#">4</a></li>
                                <li className="page-item"><a className="page-link" href="#">5</a></li>
                                <li className="page-item">
                                <a className="page-link" href="#" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                    <span className="sr-only">Next</span>
                                </a>
                                </li>
                            </ul>
                        </nav>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

DocList.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { windowHandler, menuHandler, routing } = state;
    const {

    } = windowHandler || {

    }


    const {

    } = menuHandler || {

    }

    return {

    }
}

DocList = connect(mapStateToProps)(DocList)

export default DocList;
