import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import Header from './header/Header';
import ErrorScreen from './app/ErrorScreen';
import Modal from './app/Modal';
import RawModal from './app/RawModal';
import DocumentList from './app/DocumentList';


class Container extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {
            docActionElem, docStatusData, docNoData, docId, processStatus,
            docSummaryData, dataId, windowType, breadcrumb, references, actions,
            showSidelist, siteName, connectionError, noMargin, entity, children,
            query, attachments, showIndicator, isDocumentNotSaved, hideHeader,
            handleDeletedStatus, dropzoneFocused, notfound, rawModal, modal,
            selected, selectedWindowType, indicator, modalTitle, setModalTitle,
            includedView, closeModalCallback, setModalDescription,
            modalDescription, editmode, handleEditModeToggle
        } = this.props;

        return (
            <div>
                {
                    // Forcing refresh component
                    !hideHeader && <Header
                        {...{entity, docStatusData, docNoData, docSummaryData,
                            handleDeletedStatus, isDocumentNotSaved,
                            showIndicator, query, siteName, showSidelist,
                            attachments, actions, references, windowType,
                            breadcrumb, dataId, dropzoneFocused, notfound,
                            docId, editmode, handleEditModeToggle
                        }}
                        docStatus = {docActionElem}
                    />
                }
                {connectionError && <ErrorScreen />}
                <div
                    className={
                        'header-sticky-distance js-unselect ' +
                        (noMargin ? 'dashboard' : 'container-fluid')
                    }
                >
                {modal.visible &&
                    <Modal
                        windowType={modal.type}
                        dataId={modal.dataId ? modal.dataId : dataId}
                        data={modal.data}
                        layout={modal.layout}
                        rowData={modal.rowData}
                        tabId={modal.tabId}
                        rowId={modal.rowId}
                        modalTitle={modal.title}
                        modalType={modal.modalType}
                        modalViewId={modal.viewId}
                        isAdvanced={modal.isAdvanced}
                        relativeType={windowType}
                        relativeDataId={dataId}
                        triggerField={modal.triggerField}
                        query={query}
                        selected={selected}
                        viewId={query && query.viewId}
                        rawModalVisible={rawModal.visible}
                        indicator={indicator}
                        modalViewDocumentIds={modal.viewDocumentIds}
                        closeCallback={closeModalCallback}
                        modalSaveStatus={
                            modal.saveStatus &&
                            modal.saveStatus.saved !== undefined ?
                                modal.saveStatus.saved : true
                        }
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
                             modalDescription={this.modalDescription}
                             modalDescription={modalDescription}
                             windowType={rawModal.type}
                             viewId={rawModal.viewId}
                         >
                            <div className="document-lists-wrapper">
                                 <DocumentList
                                     type="grid"
                                     windowType={rawModal.type}
                                     defaultViewId={rawModal.viewId}
                                     selected={selected}
                                     selectedWindowType={selectedWindowType}
                                     setModalTitle={setModalTitle}
                                     setModalDescription={setModalDescription}
                                     modalDescription={this.modalDescription}
                                     isModal={true}
                                     processStatus={processStatus}
                                     includedView={includedView}
                                     inBackground={
                                         includedView.windowType &&
                                            includedView.viewId
                                     }
                                 >
                                 </DocumentList>
                                 {includedView.windowType &&
                                     includedView.viewId &&
                                     <DocumentList
                                         type="includedView"
                                         selected={selected}
                                         selectedWindowType={selectedWindowType}
                                         windowType={includedView.windowType}
                                         defaultViewId={includedView.viewId}
                                         isIncluded={true}
                                     />
                                 }
                            </div>
                         </RawModal>
                     }
                    {children}
                </div>
            </div>
        );
    }
}

Container.propTypes = {
    connectionError: PropTypes.bool
};

const mapStateToProps = state => ({
    connectionError: state.windowHandler.connectionError || false
});

export default connect(mapStateToProps)(Container);
