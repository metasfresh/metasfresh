import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import DocumentList from '../components/app/DocumentList';
import Container from '../components/Container';
import Modal from '../components/app/Modal';
import RawModal from '../components/app/RawModal';

import {
    getWindowBreadcrumb
} from '../actions/MenuActions';

import {
    updateUri
} from '../actions/AppActions';

import {
    selectTableItems,
    setLatestNewDocument
} from '../actions/WindowActions';

class DocList extends Component {
    constructor(props){
        super(props);

        this.state = {
            modalTitle: '',
            notfound: false
        }
    }

    componentDidMount = () => {
        const {dispatch, windowType, latestNewDocument} = this.props;

        dispatch(getWindowBreadcrumb(windowType));

        if(latestNewDocument){
            dispatch(selectTableItems([latestNewDocument], windowType));
            dispatch(setLatestNewDocument(null));
        }
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

    setModalTitle = (title) => {
        this.setState({
            modalTitle: title
        })
    }

    setNotFound = (isNotFound) => {
        this.setState({
            notfound: isNotFound
        })
    }

    render() {
        const {
            windowType, breadcrumb, query, modal, selected, rawModal,
            indicator, processStatus, includedView, selectedWindowType
        } = this.props;

        const {
            modalTitle, notfound
        } = this.state;

        return (
            <Container
                entity="documentView"
                breadcrumb={breadcrumb}
                windowType={windowType}
                query={query}
                notfound={notfound}
                showIndicator={!modal.visible && !rawModal.visible}
            >
                {modal.visible &&
                    <Modal
                        windowType={modal.type}
                        dataId={modal.dataId ? modal.dataId : ''}
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
                        viewId={query.viewId}
                        rawModalVisible={rawModal.visible}
                        indicator={indicator}
                        isDocumentNotSaved={
                            (modal.saveStatus && !modal.saveStatus.saved) &&
                            (modal.validStatus &&
                                !modal.validStatus.initialValue)
                        }
                     />
                 }

                 {rawModal.visible &&
                     <RawModal
                         modalTitle={modalTitle}
                     >
                         <DocumentList
                             type="grid"
                             windowType={rawModal.type}
                             defaultViewId={rawModal.viewId}
                             selected={selected}
                             selectedWindowType={selectedWindowType}
                             setModalTitle={this.setModalTitle}
                             isModal={true}
                             processStatus={processStatus}
                             includedView={includedView}
                             inBackground={
                                 includedView.windowType && includedView.viewId
                             }
                         >
                             <DocumentList
                                 type="includedView"
                                 selected={selected}
                                 windowType={includedView.windowType}
                                 defaultViewId={includedView.viewId}
                                 isIncluded={true}
                             />
                         </DocumentList>
                     </RawModal>
                 }
                 <DocumentList
                     type="grid"
                     updateUri={this.updateUriCallback}
                     windowType={windowType}
                     defaultViewId={query.viewId}
                     defaultSort={query.sort}
                     defaultPage={parseInt(query.page)}
                     refType={query.refType}
                     refId={query.refId}
                     selectedWindowType={selectedWindowType}
                     selected={selected}
                     inBackground={rawModal.visible}
                     fetchQuickActionsOnInit={true}
                     processStatus={processStatus}
                     disablePaginationShortcuts=
                        {modal.visible || rawModal.visible}
                    setNotFound={this.setNotFound}
                    notfound={notfound}
                 />
            </Container>
        );
    }
}

DocList.propTypes = {
    dispatch: PropTypes.func.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    query: PropTypes.object.isRequired,
    includedView: PropTypes.object.isRequired,
    pathname: PropTypes.string.isRequired,
    modal: PropTypes.object.isRequired,
    rawModal: PropTypes.object.isRequired,
    selected: PropTypes.array,
    indicator: PropTypes.string.isRequired,
    processStatus: PropTypes.string.isRequired
}

function mapStateToProps(state) {
    const {
        windowHandler, menuHandler, listHandler, appHandler, routing
    } = state;

    const {
        modal,
        rawModal,
        selected,
        selectedWindowType,
        latestNewDocument,
        indicator
    } = windowHandler || {
        modal: false,
        rawModal: false,
        selected: [],
        selectedWindowType: null,
        latestNewDocument: null,
        indicator: ''
    }

    const {
        includedView
    } = listHandler || {
        includedView: {}
    }

    const {
        processStatus
    } = appHandler || {
        processStatus: ''
    }

    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: []
    }

    const {
        pathname
    } = routing.locationBeforeTransitions || {
        pathname: ''
    }

    return {
        modal, breadcrumb, pathname, selected, indicator, includedView,
        latestNewDocument, rawModal, processStatus, selectedWindowType
    }
}

DocList = connect(mapStateToProps)(DocList)

export default DocList;
