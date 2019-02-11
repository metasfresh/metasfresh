import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import DocumentList from './app/DocumentList';
import ErrorScreen from './app/ErrorScreen';
import Modal from './app/Modal';
import RawModal from './app/RawModal';
import Header from './header/Header';

class Container extends Component {
  render() {
    const {
      docActionElem,
      docStatusData,
      docNoData,
      docId,
      processStatus,
      docSummaryData,
      dataId,
      windowType,
      breadcrumb,
      references,
      actions,
      showSidelist,
      siteName,
      connectionError,
      noMargin,
      entity,
      children,
      query,
      attachments,
      showIndicator,
      // TODO: We should be using indicator from the state instead of another variable
      isDocumentNotSaved,
      hideHeader,
      handleDeletedStatus,
      dropzoneFocused,
      notfound,
      rawModal,
      modal,
      pluginModal,
      indicator,
      modalTitle,
      setModalTitle,
      includedView,
      closeModalCallback,
      setModalDescription,
      modalDescription,
      editmode,
      handleEditModeToggle,
      activeTab,
      masterDocumentList,
      pluginComponents,
    } = this.props;
    const pluginModalVisible = pluginModal.visible;
    let PluginModalComponent = null;

    if (pluginModalVisible) {
      // check if pluginModal's component is saved in the redux state
      const modalPluginName = pluginComponents[pluginModal.id];

      if (modalPluginName) {
        // get the plugin holding the required component
        const parentPlugin = window.META_HOST_APP.getRegistry().getEntry(
          modalPluginName
        );

        PluginModalComponent = parentPlugin.components.filter(
          component => component.id === pluginModal.id
        )[0].component;
      }
    }

    return (
      <div>
        {!hideHeader && (
          // Forcing refresh component
          <Header
            docStatus={docActionElem}
            windowId={windowType}
            {...{
              entity,
              docStatusData,
              docNoData,
              docSummaryData,
              handleDeletedStatus,
              isDocumentNotSaved,
              showIndicator,
              query,
              siteName,
              showSidelist,
              attachments,
              actions,
              references,
              breadcrumb,
              dataId,
              dropzoneFocused,
              notfound,
              docId,
              editmode,
              handleEditModeToggle,
              activeTab,
            }}
          />
        )}

        {connectionError && <ErrorScreen />}

        <div
          className={
            'header-sticky-distance js-unselect ' +
            (noMargin ? 'dashboard' : 'container-fluid')
          }
        >
          {modal.visible && (
            <Modal
              {...modal}
              windowType={modal.type}
              dataId={modal.dataId ? modal.dataId : dataId}
              modalTitle={modal.title}
              modalViewId={modal.viewId}
              parentType={windowType}
              parentDataId={dataId}
              query={query}
              viewId={query && query.viewId}
              rawModalVisible={rawModal.visible}
              indicator={indicator}
              modalViewDocumentIds={modal.viewDocumentIds}
              closeCallback={closeModalCallback}
              modalSaveStatus={
                modal.saveStatus && modal.saveStatus.saved !== undefined
                  ? modal.saveStatus.saved
                  : true
              }
              isDocumentNotSaved={
                modal.saveStatus &&
                !modal.saveStatus.saved &&
                (modal.validStatus && !modal.validStatus.initialValue)
              }
            />
          )}

          {rawModal.visible && (
            <RawModal
              modalTitle={modalTitle}
              modalDescription={modalDescription}
              allowedCloseActions={rawModal.allowedCloseActions}
              windowType={rawModal.windowId}
              viewId={rawModal.viewId}
              masterDocumentList={masterDocumentList}
            >
              <div className="document-lists-wrapper">
                <DocumentList
                  type="grid"
                  windowType={rawModal.windowId}
                  defaultViewId={rawModal.viewId}
                  viewProfileId={rawModal.profileId}
                  setModalTitle={setModalTitle}
                  setModalDescription={setModalDescription}
                  fetchQuickActionsOnInit={
                    !(
                      includedView &&
                      includedView.windowType &&
                      includedView.viewId
                    )
                  }
                  modalDescription={this.modalDescription}
                  isModal
                  processStatus={processStatus}
                  includedView={includedView}
                  inBackground={
                    includedView &&
                    includedView.windowType &&
                    includedView.viewId
                  }
                  inModal={modal.visible}
                />

                {includedView &&
                  includedView.windowType &&
                  includedView.viewId && (
                    <DocumentList
                      type="includedView"
                      windowType={includedView.windowType}
                      defaultViewId={includedView.viewId}
                      viewProfileId={includedView.viewProfileId}
                      fetchQuickActionsOnInit
                      isModal
                      isIncluded
                      processStatus={processStatus}
                      inBackground={false}
                      inModal={modal.visible}
                    />
                  )}
              </div>
            </RawModal>
          )}

          {pluginModalVisible && (
            <PluginModalComponent
              docId={docId}
              windowType={windowType}
              dataId={dataId}
              entity={entity}
              query={query}
            />
          )}

          {children}
        </div>
      </div>
    );
  }
}

Container.propTypes = {
  connectionError: PropTypes.bool,
  pluginModal: PropTypes.object,
  pluginComponents: PropTypes.any,
};

const mapStateToProps = state => ({
  connectionError: state.windowHandler.connectionError || false,
  pluginComponents: state.pluginsHandler.components,
});

export default connect(mapStateToProps)(Container);
