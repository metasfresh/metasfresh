import { Hints, Steps } from 'intro.js-react';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { forEach } from 'lodash';

import { addNotification } from '../actions/AppActions';
import { getData } from '../actions/GenericActions';
import {
  addRowData,
  attachFileAction,
  clearMasterData,
  connectWS,
  discardNewDocument,
  discardNewRow,
  disconnectWS,
  fireUpdateData,
  getTab,
  sortTab,
  updateTabRowsData,
} from '../actions/WindowActions';
import BlankPage from '../components/BlankPage';
import Container from '../components/Container';
import Window from '../components/Window';
import Overlay from '../components/app/Overlay';
import { introHints, introSteps } from '../components/intro/intro';

/**
 * @file Class based component.
 * @module MasterWindow
 * @extends Component
 */
class MasterWindow extends Component {
  state = {
    newRow: false,
    modalTitle: null,
    isDeleted: false,
    dropzoneFocused: false,
    introEnabled: null,
    hintsEnabled: null,
    introSteps: null,
    introHints: null,
  };

  static contextTypes = {
    router: PropTypes.object.isRequired,
  };

  /**
   * @method componentDidMount
   * @summary ToDo: Describe the method.
   */
  componentDidMount() {
    const { master } = this.props;
    const isDocumentNotSaved = !master.saveStatus.saved;

    if (isDocumentNotSaved) {
      this.initEventListeners();
    }
  }

  /**
   * @method componentDidUpdate
   * @summary ToDo: Describe the method.
   */
  componentDidUpdate(prevProps) {
    const { master, modal, params, dispatch, me } = this.props;
    const isDocumentNotSaved = !master.saveStatus.saved;
    const isDocumentSaved = master.saveStatus.saved;

    if (
      me &&
      master &&
      master.docId &&
      master.layout &&
      master.layout.windowId &&
      this.state.introEnabled === null
    ) {
      let docIntroSteps, docIntroHints;

      const windowIntroSteps = introSteps[master.layout.windowId];
      if (windowIntroSteps) {
        docIntroSteps = [];

        if (windowIntroSteps['all']) {
          docIntroSteps = docIntroSteps.concat(windowIntroSteps['all']);
        }

        if (master.docId && windowIntroSteps[master.docId]) {
          docIntroSteps = docIntroSteps.concat(windowIntroSteps[master.docId]);
        }
      }

      if (Array.isArray(introHints['default'])) {
        docIntroHints = introHints['default'];
      }

      const windowIntroHints = introHints[master.layout.windowId];
      if (windowIntroHints) {
        docIntroHints = [];

        if (windowIntroHints['all']) {
          docIntroHints = docIntroHints.concat(windowIntroHints['all']);
        }

        if (master.docId && windowIntroHints[master.docId]) {
          docIntroHints = docIntroHints.concat(windowIntroHints[master.docId]);
        }
      }

      this.setState({
        introEnabled: docIntroSteps && docIntroSteps.length > 0,
        hintsEnabled: docIntroHints && docIntroHints.length > 0,
        introSteps: docIntroSteps,
        introHints: docIntroHints,
      });
    }

    if (prevProps.master.websocket !== master.websocket && master.websocket) {
      connectWS.call(this, master.websocket, async msg => {
        const { includedTabsInfo, stale } = msg;
        const { master } = this.props;

        if (stale) {
          // some tabs data got updated/row was added
          if (includedTabsInfo) {
            const requests = [];

            forEach(includedTabsInfo, (tab, tabId) => {
              const { staleRowIds } = tab;

              // check if tab is active
              if (tabId === master.layout.activeTab) {
                staleRowIds.forEach(rowId => {
                  requests.push(
                    getData(
                      'window',
                      params.windowType,
                      params.docId,
                      tabId,
                      rowId
                    ).catch(() => {
                      return { rowId, tabId };
                    })
                  );
                });
              }
            });

            // wait for all the rows requests to finish
            return await Promise.all(requests).then(res => {
              const changedTabs = {};

              res.forEach(response => {
                const { data } = response;
                const rowsById = {};
                const removedRows = {};
                let tabId;

                // removed row
                if (!data) {
                  removedRows[response.rowId] = true;
                  tabId = response.tabId;
                } else {
                  const rowZero = data[0];
                  tabId = rowZero.tabId;

                  data.forEach(row => {
                    rowsById[row.rowId] = { ...row };
                  });
                }

                changedTabs[`${tabId}`] = {
                  changed: { ...rowsById },
                  removed: { ...removedRows },
                };
              });

              forEach(changedTabs, (rowsChanged, tabId) => {
                dispatch(updateTabRowsData('master', tabId, rowsChanged));
              });
            });

            // Check my comment in https://github.com/metasfresh/me03/issues/3628 - Kuba
          } else {
            dispatch(
              fireUpdateData(
                'window',
                params.windowType,
                params.docId,
                null,
                null,
                null,
                null
              )
            );
          }
        }

        if (includedTabsInfo) {
          Object.keys(includedTabsInfo).forEach(tabId => {
            const tabLayout =
              master.layout.tabs &&
              master.layout.tabs.filter(tab => tab.tabId === tabId)[0];
            if (
              tabLayout &&
              tabLayout.queryOnActivate &&
              master.layout.activeTab !== tabId
            ) {
              return;
            }

            const orderBy = tabLayout.orderBy;
            let sortingOrder = null;

            if (orderBy && orderBy.length) {
              const ordering = orderBy[0];
              sortingOrder =
                (ordering.ascending ? '+' : '-') + ordering.fieldName;
            }

            if (includedTabsInfo[tabId]) {
              getTab(tabId, params.windowType, master.docId, sortingOrder).then(
                tab => {
                  dispatch(addRowData({ [tabId]: tab }, 'master'));
                }
              );
            }
          });
        }
      });
    }

    if (prevProps.master.saveStatus.saved && isDocumentNotSaved) {
      this.initEventListeners();
    }
    if (!prevProps.master.saveStatus.saved && isDocumentSaved) {
      this.removeEventListeners();
    }

    // When closing modal, we need to update the stale tab
    if (
      !modal.visible &&
      modal.visible !== prevProps.modal.visible &&
      master.includedTabsInfo &&
      master.layout
    ) {
      const tabId = master.layout.activeTab;

      getTab(tabId, params.windowType, master.docId).then(tab => {
        dispatch(addRowData({ [tabId]: tab }, 'master'));
      });
    }
  }

  /**
   * @method componentWillUnmount
   * @summary ToDo: Describe the method.
   */
  componentWillUnmount() {
    const {
      master,
      dispatch,
      location: { pathname },
      params: { windowType, docId: documentId },
    } = this.props;
    const { isDeleted } = this.state;
    const isDocumentNotSaved =
      !master.saveStatus.saved && master.saveStatus.saved !== undefined;

    this.removeEventListeners();

    if (isDocumentNotSaved && !isDeleted) {
      const result = window.confirm('Do you really want to leave?');

      if (result) {
        discardNewDocument({ windowType, documentId });
      } else {
        dispatch(push(pathname));
      }
    }

    dispatch(clearMasterData());
    disconnectWS.call(this);
  }

  /**
   * @method confirm
   * @summary ToDo: Describe the method.
   */
  confirm = e => {
    e.returnValue = '';
  };

  /**
   * @method initEventListeners
   * @summary ToDo: Describe the method.
   */
  initEventListeners = () => {
    if (!navigator.userAgent.includes('Cypress')) {
      // try workaround https://github.com/cypress-io/cypress/issues/1235#issuecomment-411839157 for our "hanging" problem
      window.addEventListener('beforeunload', this.confirm);
    }
  };

  /**
   * @method removeEventListeners
   * @summary ToDo: Describe the method.
   */
  removeEventListeners = () => {
    window.removeEventListener('beforeunload', this.confirm);
  };

  /**
   * @method closeModalCallback
   * @summary ToDo: Describe the method.
   */
  closeModalCallback = ({
    isNew,
    windowType,
    documentId,
    tabId,
    rowId,
  } = {}) => {
    if (isNew) {
      return discardNewRow({ windowType, documentId, tabId, rowId });
    }
  };

  /**
   * @method handleDropFile
   * @summary ToDo: Describe the method.
   */
  handleDropFile = files => {
    const file = files instanceof Array ? files[0] : files;

    if (!(file instanceof File)) {
      return Promise.reject();
    }

    const {
      dispatch,
      master: {
        data,
        layout: { type },
      },
    } = this.props;
    const dataId = data ? data.ID.value : -1;

    let fd = new FormData();
    fd.append('file', file);

    return dispatch(attachFileAction(type, dataId, fd));
  };

  /**
   * @method handleDragStart
   * @summary ToDo: Describe the method.
   */
  handleDragStart = () => {
    this.setState(
      {
        dropzoneFocused: true,
      },
      () => {
        this.setState({
          dropzoneFocused: false,
        });
      }
    );
  };

  /**
   * @method handleRejectDropped
   * @summary ToDo: Describe the method.
   */
  handleRejectDropped = droppedFiles => {
    const { dispatch } = this.props;

    const dropped =
      droppedFiles instanceof Array ? droppedFiles[0] : droppedFiles;

    dispatch(
      addNotification(
        'Attachment',
        `Dropped item ['${dropped.type}'] could not be attached`,
        5000,
        'error'
      )
    );
  };

  /**
   * @method setModalTitle
   * @summary ToDo: Describe the method.
   */
  setModalTitle = title => {
    this.setState({ modalTitle: title });
  };

  /**
   * @method handleDeletedStatus
   * @summary ToDo: Describe the method.
   */
  handleDeletedStatus = param => {
    this.setState({ isDeleted: param });
  };

  /**
   * @method sort
   * @summary ToDo: Describe the method.
   */
  sort = (asc, field, startPage, page, tabId) => {
    const {
      dispatch,
      master,
      params: { windowType },
    } = this.props;
    const orderBy = (asc ? '+' : '-') + field;
    const dataId = master.docId;

    dispatch(sortTab('master', tabId, field, asc));
    getTab(tabId, windowType, dataId, orderBy).then(res => {
      dispatch(addRowData({ [tabId]: res }, 'master'));
    });
  };

  /**
   * @method handleIntroExit
   * @summary ToDo: Describe the method.
   */
  handleIntroExit = () => {
    this.setState({ introEnabled: false });
  };

  /**
   * @method render
   * @summary ToDo: Describe the method.
   */
  render() {
    const {
      master,
      modal,
      breadcrumb,
      params,
      rawModal,
      pluginModal,
      overlay,
      allowShortcut,
      includedView,
      processStatus,
      enableTutorial,
    } = this.props;
    const {
      dropzoneFocused,
      newRow,
      modalTitle,
      introEnabled,
      hintsEnabled,
      introSteps,
      introHints,
    } = this.state;
    const { docActionElement, documentSummaryElement } = master.layout;
    const dataId = master.docId;
    const docNoData = master.data.DocumentNo;
    let activeTab;

    if (master.layout) {
      activeTab = master.layout.activeTab;
    }

    const docStatusData = {
      status: master.data.DocStatus || -1,
      action: master.data.DocAction || -1,
      displayed: true,
    };
    const docSummaryData =
      documentSummaryElement &&
      master.data[documentSummaryElement.fields[0].field];

    // valid status for unsaved items with errors does not
    // have initialValue set, but does have the error message
    const initialValidStatus =
      master.validStatus.initialValue !== undefined
        ? master.validStatus.initialValue
        : master.validStatus.valid;
    const isDocumentNotSaved =
      dataId !== 'notfound' &&
      master.saveStatus.saved !== undefined &&
      !master.saveStatus.saved &&
      !initialValidStatus;

    return (
      <Container
        entity="window"
        dropzoneFocused={dropzoneFocused}
        docStatusData={docStatusData}
        docSummaryData={docSummaryData}
        modal={modal}
        dataId={dataId}
        breadcrumb={breadcrumb}
        docNoData={docNoData}
        isDocumentNotSaved={isDocumentNotSaved}
        rawModal={rawModal}
        pluginModal={pluginModal}
        modalTitle={modalTitle}
        includedView={includedView}
        processStatus={processStatus}
        activeTab={activeTab}
        closeModalCallback={this.closeModalCallback}
        setModalTitle={this.setModalTitle}
        docActionElem={docActionElement}
        windowType={params.windowType}
        docId={params.docId}
        showSidelist
        showIndicator={!modal.visible}
        handleDeletedStatus={this.handleDeletedStatus}
      >
        <Overlay data={overlay.data} showOverlay={overlay.visible} />

        {dataId === 'notfound' ? (
          <BlankPage what="Document" />
        ) : (
          <Window
            key="window"
            data={master.data}
            layout={master.layout}
            rowData={master.rowData}
            tabsInfo={master.includedTabsInfo}
            sort={this.sort}
            dataId={dataId}
            isModal={false}
            newRow={newRow}
            allowShortcut={allowShortcut}
            handleDragStart={this.handleDragStart}
            handleDropFile={this.handleDropFile}
            handleRejectDropped={this.handleRejectDropped}
          />
        )}

        {enableTutorial && introSteps && introSteps.length > 0 && (
          <Steps
            enabled={introEnabled}
            steps={introSteps}
            initialStep={0}
            onExit={this.handleIntroExit}
          />
        )}

        {enableTutorial && introHints && introHints.length > 0 && (
          <Hints enabled={hintsEnabled} hints={introHints} />
        )}
      </Container>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {object} modal
 * @prop {object} master
 * @prop {array} breadcrumb
 * @prop {func} dispatch
 * @prop {object} rawModal
 * @prop {string} indicator
 * @prop {object} me
 * @prop {object} [pluginModal]
 * @prop {object} [overlay]
 * @prop {bool} [allowShortcut]
 * @prop {*} [params]
 * @prop {*} [includedView]
 * @prop {*} [processStatus]
 * @prop {*} [enableTutorial]
 * @prop {*} [location]
 */
MasterWindow.propTypes = {
  modal: PropTypes.object.isRequired,
  master: PropTypes.object.isRequired,
  breadcrumb: PropTypes.array.isRequired,
  dispatch: PropTypes.func.isRequired,
  rawModal: PropTypes.object.isRequired,
  indicator: PropTypes.string.isRequired,
  me: PropTypes.object.isRequired,
  pluginModal: PropTypes.object,
  overlay: PropTypes.object,
  allowShortcut: PropTypes.bool,
  params: PropTypes.any,
  includedView: PropTypes.any,
  processStatus: PropTypes.any,
  enableTutorial: PropTypes.any,
  location: PropTypes.any,
};

/**
 * @method mapStateToProps
 * @summary ToDo: Describe the method.
 * @param {object} state
 */
const mapStateToProps = state => ({
  master: state.windowHandler.master,
  modal: state.windowHandler.modal,
  rawModal: state.windowHandler.rawModal,
  pluginModal: state.windowHandler.pluginModal,
  overlay: state.windowHandler.overlay,
  indicator: state.windowHandler.indicator,
  includedView: state.listHandler.includedView,
  allowShortcut: state.windowHandler.allowShortcut,
  enableTutorial: state.appHandler.enableTutorial,
  processStatus: state.appHandler.processStatus,
  me: state.appHandler.me,
  breadcrumb: state.menuHandler.breadcrumb,
});

export default connect(mapStateToProps)(MasterWindow);
