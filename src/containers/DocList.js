import React, { Component, PropTypes } from 'react';
import {push,replace} from 'react-router-redux';
import {connect} from 'react-redux';

import DocumentList from '../components/App/DocumentList';
import Container from '../components/Container';
import Modal from '../components/App/Modal';

import {
    getWindowBreadcrumb
} from '../actions/MenuActions';

import {
    updateUri
} from '../actions/AppActions';

class DocList extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount = () => {
        const {dispatch, windowType} = this.props;
        dispatch(getWindowBreadcrumb(windowType))
    }

    updateUriCallback = (prop, value) => {
        const {dispatch, query, pathname} = this.props;
        dispatch(updateUri(pathname, query, prop, value));
    }

    renderDocumentList = (windowType, query, viewId) => {
        return (<DocumentList
            type="grid"
            updateUri={this.updateUriCallback}
            windowType={windowType}
            query={query}
            viewId={viewId}
        />)
    }

    render() {
        const {
            dispatch, windowType, breadcrumb, query, actions, modal, viewId, selected
        } = this.props;

        return (
            <Container
                breadcrumb={breadcrumb}
                windowType={windowType}
                actions={actions}
            >
                {modal.visible &&
                    <Modal
                        windowType={modal.type}
                        data={modal.data}
                        layout={modal.layout}
                        rowData={modal.rowData}
                        tabId={modal.tabId}
                        rowId={modal.rowId}
                        modalTitle={modal.title}
                        modalType={modal.modalType}
                        viewId={viewId}
                        selected={selected}
                     />
                 }
                {this.renderDocumentList(windowType, query, viewId)}
            </Container>
        );
    }
}

DocList.propTypes = {
    dispatch: PropTypes.func.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    query: PropTypes.object.isRequired,
    search: PropTypes.string.isRequired,
    pathname: PropTypes.string.isRequired,
    modal: PropTypes.object.isRequired,
    viewId: PropTypes.string.isRequired,
    selected: PropTypes.array,
    actions: PropTypes.array.isRequired
}

function mapStateToProps(state) {
    const { windowHandler, menuHandler, listHandler, routing } = state;

    const {
        modal,
        selected
    } = windowHandler || {
        modal: false,
        selected: []
    }

    const {
        actions,
        breadcrumb
    } = menuHandler || {
        actions: [],
        breadcrumb: []
    }

    const {
        viewId
    } = listHandler || {
        viewId: ""
    }

    const {
        query,
        search,
        pathname
    } = routing.locationBeforeTransitions || {
        query: {},
        search: "",
        pathname: ""
    }


    return {
        modal,
        breadcrumb,
        query,
        search,
        pathname,
        actions,
        viewId,
        selected
    }
}

DocList = connect(mapStateToProps)(DocList)

export default DocList;
