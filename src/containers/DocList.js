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

        this.state = {};

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
            dispatch(browseViewRequest(this.state.data.viewId, 1, 20)).then((response) => {
                this.setState(Object.assign({}, this.state, {
                    data: response.data
                }))
            });
        });
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
