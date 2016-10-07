import React, { Component, PropTypes } from 'react';
import {push} from 'react-router-redux';
import {connect} from 'react-redux';

import Header from '../components/app/Header';
import DocumentList from '../components/app/DocumentList';

import {
    getWindowBreadcrumb
} from '../actions/MenuActions';

class DocList extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount = () => {
        const {dispatch, windowType} = this.props;
        dispatch(getWindowBreadcrumb(windowType))
    }

    renderDocumentList = (windowType) => {
        return (<DocumentList
            type="grid"
            windowType={windowType}
        />)
    }

    render() {
        const {dispatch, windowType, breadcrumb} = this.props;

        return (
            <div>
                <Header
                    breadcrumb={breadcrumb}
                    windowType={windowType}
                />
                <div className="container header-sticky-distance">
                    {this.renderDocumentList(windowType)}
                </div>
            </div>
        );

    }
}

DocList.propTypes = {
    dispatch: PropTypes.func.isRequired,
    breadcrumb: PropTypes.array.isRequired
}

function mapStateToProps(state) {
    const { menuHandler } = state;

    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: []
    }

    return {
        breadcrumb
    }
}

DocList = connect(mapStateToProps)(DocList)

export default DocList;
