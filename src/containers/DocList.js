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

        dispatch(getWindowBreadcrumb(windowType));
    }

    componentDidUpdate = (prevProps) => {
        const {dispatch, windowType} = this.props;

        if(prevProps.windowType !== windowType){
            dispatch(getWindowBreadcrumb(windowType));
        }
    }

    updateUriCallback = (prop, value) => {
        const {dispatch, query, pathname} = this.props;
        dispatch(updateUri(pathname, query, prop, value));
    }

    renderDocumentList = () => {
        const {windowType, query, viewId, selected} = this.props;
        return (<DocumentList
            type="grid"
            updateUri={this.updateUriCallback}
            windowType={windowType}
            query={query}
            viewId={viewId}
            selected={selected}
        />)
    }

    render() {
        const {
            dispatch, windowType, breadcrumb, query, actions, modal, viewId,
            selected, references
        } = this.props;

        return (
            <Container
                entity="documentView"
                breadcrumb={breadcrumb}
                windowType={windowType}
                actions={actions}
                references={references}
                query={query}
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
                        modalViewId={modal.viewId}
                        query={query}
                        selected={selected}
                        viewId={viewId}
                        selected={selected}
                     />
                 }
                {this.renderDocumentList()}
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
    actions: PropTypes.array.isRequired,
    references: PropTypes.array.isRequired
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
        references,
        breadcrumb
    } = menuHandler || {
        actions: [],
        refereces: [],
        breadcrumb: []
    }

    const {
        viewId
    } = listHandler || {
        viewId: ""
    }

    const {
        search,
        pathname
    } = routing.locationBeforeTransitions || {
        search: "",
        pathname: ""
    }


    return {
        modal,
        breadcrumb,
        search,
        pathname,
        actions,
        viewId,
        selected,
        references
    }
}

DocList = connect(mapStateToProps)(DocList)

export default DocList;
