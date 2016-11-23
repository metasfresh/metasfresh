import React, { Component, PropTypes } from 'react';
import {push,replace} from 'react-router-redux';
import {connect} from 'react-redux';

import DocumentList from '../components/app/DocumentList';
import Container from '../components/Container';
import Modal from '../components/app/Modal';

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

    renderDocumentList = (windowType, query) => {
        return (<DocumentList
            type="grid"
            updateUri={this.updateUriCallback}
            windowType={windowType}
            query={query}
        />)
    }

    render() {
        const {
            dispatch, windowType, breadcrumb, query, actions, modal
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
                     />
                 }
                {this.renderDocumentList(windowType, query)}
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
    actions: PropTypes.array.isRequired
}

function mapStateToProps(state) {
    const { windowHandler, menuHandler, routing } = state;

    const {
        modal
    } = windowHandler || {
        modal: false
    }

    const {
        actions,
        breadcrumb
    } = menuHandler || {
        actions: [],
        breadcrumb: []
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
        actions
    }
}

DocList = connect(mapStateToProps)(DocList)

export default DocList;
