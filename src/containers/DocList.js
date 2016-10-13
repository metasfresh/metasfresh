import React, { Component, PropTypes } from 'react';
import {push,replace} from 'react-router-redux';
import {connect} from 'react-redux';

import DocumentList from '../components/app/DocumentList';
import Container from '../components/Container';

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

    sortingCallback = (asc, field) => {
        const {dispatch, windowType} = this.props;
        dispatch(replace('/window/' + windowType + '?sortby=' + field + '&sortdir=' + asc));
    }

    renderDocumentList = (windowType, query) => {
        return (<DocumentList
            type="grid"
            sortingCallback={this.sortingCallback}
            windowType={windowType}
            query={query}
        />)
    }

    render() {
        const {dispatch, windowType, breadcrumb, query} = this.props;

        return (
            <Container
                breadcrumb={breadcrumb}
                windowType={windowType}
            >
                {this.renderDocumentList(windowType, query)}
            </Container>
        );
    }
}

DocList.propTypes = {
    dispatch: PropTypes.func.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    query: PropTypes.object.isRequired,
}

function mapStateToProps(state) {
    const { menuHandler, routing } = state;

    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: []
    }

    const {
        query
    } = routing.locationBeforeTransitions || {
        query: {}
    }

    return {
        breadcrumb,
        query
    }
}

DocList = connect(mapStateToProps)(DocList)

export default DocList;
