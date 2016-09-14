import React, { Component, PropTypes } from 'react';
import {push} from 'react-router-redux';
import {connect} from 'react-redux';

import Header from '../components/app/Header';
import DatetimeRange from '../components/app/DatetimeRange';
import Table from '../components/table/Table';

class DocList extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {dispatch, windowType} = this.props;
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
                        {/*<Table
                            rowData={[]}
                            cols={[]}
                            tabid={1}
                            type={windowType}
                            emptyText={"asd"}
                            emptyHint={"asd"}
                        />*/}
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
