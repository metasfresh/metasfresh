import PropTypes from 'prop-types';
import React from 'react';
import { connect } from 'react-redux';

import { viewState } from '../reducers/viewHandler';
import {
  setRawModalTitle,
  setRawModalDescription,
} from '../actions/WindowActions';

import DocumentList from './app/DocumentList';
import ErrorScreen from './app/ErrorScreen';
import Modal from './app/Modal';
import RawModal from './app/RawModal';
import Header from './header/Header';

/**
 * @file Class based component.
 * @module Container
 * @extends Component
 */
const Container = (props) => {
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
    includedView,
    closeModalCallback,
    editmode,
    handleEditModeToggle,
    activeTab,
    masterDocumentList,
    pluginComponents,
    setRawModalTitle,
    setRawModalDescription,
  } = props;
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
        (component) => component.id === pluginModal.id
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
            modalTitle={rawModal.title}
            modalDescription={rawModal.description}
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
                setModalTitle={setRawModalTitle}
                setModalDescription={setRawModalDescription}
                fetchQuickActionsOnInit={
                  !(
                    includedView &&
                    includedView.windowType &&
                    includedView.viewId
                  )
                }
                modalDescription={rawModal.description}
                isModal
                processStatus={processStatus}
                includedView={includedView}
                inBackground={
                  includedView && includedView.windowType && includedView.viewId
                }
                inModal={modal.visible}
              />

              {includedView &&
                includedView.windowType &&
                includedView.viewId && (
                  <DocumentList
                    type="includedView"
                    windowType={includedView.windowType}
                    viewProfileId={includedView.viewProfileId}
                    defaultViewId={includedView.viewId}
                    parentDefaultViewId={rawModal.viewId}
                    parentWindowType={rawModal.windowId}
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
};

/**
 * @typedef {object} Props Component props
 * @prop {*} actions
 * @prop {*} activeTab
 * @prop {*} attachments
 * @prop {*} breadcrumb
 * @prop {*} children
 * @prop {bool} connectionError
 * @prop {*} closeModalCallback
 * @prop {string} dataId
 * @prop {*} docActionElem
 * @prop {*} docStatusData
 * @prop {*} docNoData
 * @prop {string} docId
 * @prop {*} docSummaryData
 * @prop {*} dropzoneFocused
 * @prop {*} editmode
 * @prop {*} entity
 * @prop {*} handleDeletedStatus
 * @prop {*} handleEditModeToggle
 * @prop {*} hideHeader
 * @prop {*} includedView
 * @prop {string} indicator
 * @prop {bool} isDocumentNotSaved
 * @prop {*} masterDocumentList
 * @prop {*} modal
 * @prop {*} modalDescription
 * @prop {*} modalTitle
 * @prop {*} notfound
 * @prop {*} noMargin
 * @prop {*} pluginComponents
 * @prop {object} pluginModal
 * @prop {*} pluginComponents
 * @prop {*} processStatus
 * @prop {*} query
 * @prop {*} rawModal
 * @prop {*} references
 * @prop {*} showIndicator
 * @prop {*} showSidelist
 * @prop {*} setModalDescription
 * @prop {*} setModalTitle
 * @prop {*} siteName
 * @prop {*} windowType
 */
Container.propTypes = {
  actions: PropTypes.any,
  activeTab: PropTypes.any,
  attachments: PropTypes.any,
  breadcrumb: PropTypes.any,
  children: PropTypes.any,
  closeModalCallback: PropTypes.any,
  connectionError: PropTypes.bool,
  dataId: PropTypes.any,
  docActionElem: PropTypes.any,
  docId: PropTypes.any,
  docNoData: PropTypes.any,
  docSummaryData: PropTypes.any,
  docStatusData: PropTypes.any,
  dropzoneFocused: PropTypes.any,
  editmode: PropTypes.any,
  entity: PropTypes.any,
  hideHeader: PropTypes.any,
  handleDeletedStatus: PropTypes.any,
  handleEditModeToggle: PropTypes.any,
  indicator: PropTypes.any,
  includedView: PropTypes.any,
  isDocumentNotSaved: PropTypes.any,
  masterDocumentList: PropTypes.any,
  modal: PropTypes.any,
  modalDescription: PropTypes.any,
  modalTitle: PropTypes.any,
  noMargin: PropTypes.any,
  notfound: PropTypes.bool,
  pluginModal: PropTypes.object,
  pluginComponents: PropTypes.any,
  processStatus: PropTypes.any,
  query: PropTypes.any,
  rawModal: PropTypes.any,
  references: PropTypes.any,
  showIndicator: PropTypes.any,
  showSidelist: PropTypes.any,
  siteName: PropTypes.any,
  setRawModalDescription: PropTypes.any,
  setRawModalTitle: PropTypes.any,
  windowType: PropTypes.any,
};

/**
 * @method mapStateToProps
 * @summary ToDo: Describe the method.
 * @param {object} state
 */
const mapStateToProps = (state, { windowType }) => {
  let master = state.viewHandler.views[windowType];

  if (!master || !windowType) {
    master = viewState;
  }

  return {
    notfound: master.notfound,
    connectionError: state.windowHandler.connectionError || false,
    pluginComponents: state.pluginsHandler.components,
  };
};

export default connect(
  mapStateToProps,
  { setRawModalTitle, setRawModalDescription }
)(Container);
