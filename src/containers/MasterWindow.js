import { Hints, Steps } from 'intro.js-react';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';

import { addNotification } from '../actions/AppActions';
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
} from '../actions/WindowActions';
import BlankPage from '../components/BlankPage';
import Container from '../components/Container';
import Window from '../components/Window';
import { introHints, introSteps } from '../components/intro/intro';

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

  static propTypes = {
    modal: PropTypes.object.isRequired,
    master: PropTypes.object.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    dispatch: PropTypes.func.isRequired,
    rawModal: PropTypes.object.isRequired,
    indicator: PropTypes.string.isRequired,
    me: PropTypes.object.isRequired,
    pluginModal: PropTypes.object,
  };

  componentDidMount() {
    const { master } = this.props;
    const isDocumentNotSaved = !master.saveStatus.saved;

    if (isDocumentNotSaved) {
      this.initEventListeners();
    }
  }

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
      connectWS.call(this, master.websocket, msg => {
        const { includedTabsInfo, stale } = msg;
        const { master } = this.props;

        if (stale) {
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

    // When closing modal, we need to update the stale tabs
    if (
      !modal.visible &&
      modal.visible !== prevProps.modal.visible &&
      master.includedTabsInfo
    ) {
      Object.keys(master.includedTabsInfo).map(tabId => {
        getTab(tabId, params.windowType, master.docId).then(tab => {
          dispatch(addRowData({ [tabId]: tab }, 'master'));
        });
      });
    }
  }

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

  confirm = e => {
    e.returnValue = '';
  };

  initEventListeners = () => {
    window.addEventListener('beforeunload', this.confirm);
  };

  removeEventListeners = () => {
    window.removeEventListener('beforeunload', this.confirm);
  };

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

  handleDropFile = files => {
    const file = files instanceof Array ? files[0] : files;

    if (!(file instanceof File)) {
      return Promise.reject();
    }

    const { dispatch, master: { data, layout: { type } } } = this.props;
    const dataId = data ? data.ID.value : -1;

    let fd = new FormData();
    fd.append('file', file);

    return dispatch(attachFileAction(type, dataId, fd));
  };

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

  setModalTitle = title => {
    this.setState({ modalTitle: title });
  };

  handleDeletedStatus = param => {
    this.setState({ isDeleted: param });
  };

  sort = (asc, field, startPage, page, tabId) => {
    const { dispatch, master, params: { windowType } } = this.props;
    const orderBy = (asc ? '+' : '-') + field;
    const dataId = master.docId;

    dispatch(sortTab('master', tabId, field, asc));
    getTab(tabId, windowType, dataId, orderBy).then(res => {
      dispatch(addRowData({ [tabId]: res }, 'master'));
    });
  };

  handleIntroExit = () => {
    this.setState({ introEnabled: false });
  };

  render() {
    const {
      master,
      modal,
      breadcrumb,
      params,
      rawModal,
      pluginModal,
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

    // TODO: We should be using indicator from the state instead of another variable
    const isDocumentNotSaved =
      dataId !== 'notfound' &&
      master.saveStatus.saved !== undefined &&
      !master.saveStatus.saved &&
      master.validStatus.initialValue !== undefined &&
      !master.validStatus.initialValue;

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
            handleDragStart={this.handleDragStart}
            handleDropFile={this.handleDropFile}
            handleRejectDropped={this.handleRejectDropped}
          />
        )}

        {enableTutorial &&
          introSteps &&
          introSteps.length > 0 && (
            <Steps
              enabled={introEnabled}
              steps={introSteps}
              initialStep={0}
              onExit={this.handleIntroExit}
            />
          )}

        {enableTutorial &&
          introHints &&
          introHints.length > 0 && (
            <Hints enabled={hintsEnabled} hints={introHints} />
          )}
      </Container>
    );
  }
}

const mapStateToProps = state => ({
  master: state.windowHandler.master,
  modal: state.windowHandler.modal,
  rawModal: state.windowHandler.rawModal,
  pluginModal: state.windowHandler.pluginModal,
  indicator: state.windowHandler.indicator,
  includedView: state.listHandler.includedView,
  enableTutorial: state.appHandler.enableTutorial,
  processStatus: state.appHandler.processStatus,
  me: state.appHandler.me,
  breadcrumb: state.menuHandler.breadcrumb,
});

export default connect(mapStateToProps)(MasterWindow);
