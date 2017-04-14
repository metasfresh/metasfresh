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
            modalTitle: ''
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

    render() {
        const {
            windowType, breadcrumb, query, modal, selected, rawModal,
            indicator, processStatus, includedView
        } = this.props;

        const {
            modalTitle
        } = this.state;

        return (
            <Container
                entity="documentView"
                breadcrumb={breadcrumb}
                windowType={windowType}
                query={query}
                showIndicator={!modal.visible && !rawModal.visible}
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
                             windowType={parseInt(rawModal.type)}
                             defaultViewId={rawModal.viewId}
                             selected={selected}
                             setModalTitle={this.setModalTitle}
                             isModal={true}
                             processStatus={processStatus}
                             includedView={includedView}
                         >
                         </DocumentList>
                     </RawModal>
                 }
                 <DocumentList
                     type="grid"
                     updateUri={this.updateUriCallback}
                     windowType={parseInt(windowType)}
                     defaultViewId={query.viewId}
                     defaultSort={query.sort}
                     defaultPage={parseInt(query.page)}
                     refType={query.refType}
                     refId={query.refId}
                     selected={selected}
                     inBackground={rawModal.visible}
                     fetchQuickActionsOnInit={true}
                     processStatus={processStatus}
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
    const { windowHandler, menuHandler, listHandler, appHandler, routing } = state;

    const {
        modal,
        rawModal,
        selected,
        latestNewDocument,
        indicator
    } = windowHandler || {
        modal: false,
        rawModal: false,
        selected: [],
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
        latestNewDocument, rawModal, processStatus
    }
}

DocList = connect(mapStateToProps)(DocList)

export default DocList;
