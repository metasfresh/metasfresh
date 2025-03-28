// import { Hints, Steps } from 'intro.js-react';
import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';

import { discardNewRequest } from '../../api';
import { getTableId } from '../../reducers/tables';

import { BlankPage } from '../BlankPage';
import Container from '../Container';
import SectionGroup from '../SectionGroup';
import Overlay from '../app/Overlay';
import { introHints, introSteps } from '../intro/intro';

/**
 * @file Class based component.
 * @module MasterWindow
 * @extends PureComponent
 */
export default class MasterWindow extends PureComponent {
  state = {
    newRow: false,
    modalTitle: null,
    dropzoneFocused: false,
    introEnabled: null,
    hintsEnabled: null,
    introSteps: null,
    introHints: null,
  };

  componentDidUpdate(/*prevProps*/) {
    const { master, me } = this.props;

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
  }

  closeModalCallback = ({
    isNew,
    windowType,
    documentId,
    tabId,
    rowId,
    saveStatus,
  } = {}) => {
    if (isNew) {
      const { updateTabRowsData } = this.props;
      const tableId = getTableId({
        windowId: windowType,
        docId: documentId,
        tabId,
      });

      return discardNewRequest({
        windowId: windowType,
        documentId,
        tabId,
        rowId,
      }).then(() => {
        // if modal was not saved, discard the new row
        if (!saveStatus) {
          updateTabRowsData(tableId, {
            removed: { [`${rowId}`]: true },
          });
        }
      });
    }
  };

  /**
   * @method handleDropFile
   * @summary ToDo: Describe the method.
   */
  handleDropFile = (files) => {
    const file = files instanceof Array ? files[0] : files;

    if (!(file instanceof File)) {
      return Promise.reject();
    }

    const {
      attachFileAction,
      master: {
        data,
        layout: { type },
      },
    } = this.props;
    const dataId = data ? data.ID.value : -1;

    let fd = new FormData();
    fd.append('file', file);

    return attachFileAction(type, dataId, fd);
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
  handleRejectDropped = (droppedFiles) => {
    const { addNotification } = this.props;

    const dropped =
      droppedFiles instanceof Array ? droppedFiles[0] : droppedFiles;

    addNotification(
      'Attachment',
      `Dropped item ['${dropped.type}'] could not be attached`,
      5000,
      'error'
    );
  };

  setModalTitle = (title) => {
    this.setState({ modalTitle: title });
  };

  // handleIntroExit = () => {
  //   this.setState({ introEnabled: false });
  // };

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
      // enableTutorial,
      onRefreshTab,
      onSortTable,
    } = this.props;
    const {
      dropzoneFocused,
      newRow,
      modalTitle,
      // introEnabled,
      // hintsEnabled,
      // introSteps,
      // introHints,
    } = this.state;
    const dataId = master.docId;
    const docNoData = master.data.DocumentNo;

    return (
      <Container
        entity="window"
        dropzoneFocused={dropzoneFocused}
        modal={modal}
        dataId={dataId}
        breadcrumb={breadcrumb}
        docNoData={docNoData}
        rawModal={rawModal}
        pluginModal={pluginModal}
        modalTitle={modalTitle}
        includedView={includedView}
        processStatus={processStatus}
        closeModalCallback={this.closeModalCallback}
        setModalTitle={this.setModalTitle}
        windowId={params.windowId}
        docId={params.docId}
        showSidelist
        modalHidden={!modal.visible}
        hasComments={master.hasComments}
      >
        <Overlay data={overlay.data} showOverlay={overlay.visible} />

        {dataId === 'notfound' ? (
          <BlankPage
            what="Document"
            title={master.layout.notFoundMessage}
            description={master.layout.notFoundMessageDetail}
          />
        ) : (
          <SectionGroup
            key="window"
            data={master.data}
            layout={master.layout}
            tabsInfo={master.includedTabsInfo}
            onSortTable={onSortTable}
            dataId={dataId}
            isModal={false}
            newRow={newRow}
            allowShortcut={allowShortcut}
            handleDragStart={this.handleDragStart}
            handleDropFile={this.handleDropFile}
            handleRejectDropped={this.handleRejectDropped}
            onRefreshTab={onRefreshTab}
          />
        )}

        {/* 
          Temporarly disabled the tutorial components. Activating this back implies adding the intro.js and intro.js-react deps 
          the package.json file. Note: Another usage is also in the Dashboard component.
        */}

        {/* {enableTutorial && introSteps && introSteps.length > 0 && (
          <Steps
            enabled={introEnabled}
            steps={introSteps}
            initialStep={0}
            onExit={this.handleIntroExit}
          />
        )}

        {enableTutorial && introHints && introHints.length > 0 && (
          <Hints enabled={hintsEnabled} hints={introHints} />
        )} */}
      </Container>
    );
  }
}

/**
 * definition almost identical to /containers/MasterWindow
 */
MasterWindow.propTypes = {
  modal: PropTypes.object.isRequired,
  master: PropTypes.object.isRequired,
  breadcrumb: PropTypes.array.isRequired,
  rawModal: PropTypes.object.isRequired,
  me: PropTypes.object.isRequired,
  pluginModal: PropTypes.object,
  overlay: PropTypes.object,
  allowShortcut: PropTypes.bool,
  params: PropTypes.any,
  includedView: PropTypes.any,
  processStatus: PropTypes.any,
  enableTutorial: PropTypes.any,
  onRefreshTab: PropTypes.func,
  location: PropTypes.any,
  addNotification: PropTypes.func,
  addRowData: PropTypes.func,
  attachFileAction: PropTypes.func,
  sortTab: PropTypes.func,
  onSortTable: PropTypes.func,
  updateTabRowsData: PropTypes.func.isRequired,
};
