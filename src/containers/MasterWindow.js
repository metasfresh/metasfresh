import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { forEach, get } from 'lodash';

import { addNotification } from '../actions/AppActions';
import {
  addRowData,
  attachFileAction,
  clearMasterData,
  fireUpdateData,
  sortTab,
  updateTabRowsData,
} from '../actions/WindowActions';
import { connectWS, disconnectWS } from '../utils/websockets';
import { getTab, getData } from '../api';

import MasterWindow from '../components/app/MasterWindow';

/**
 * @file Class based component.
 * @module MasterWindow
 * @extends Component
 */
class MasterWindowContainer extends Component {
  static contextTypes = {
    router: PropTypes.object.isRequired,
  };

  componentDidUpdate(prevProps) {
    const { master, modal, params, addRowData } = this.props;

    if (prevProps.master.websocket !== master.websocket && master.websocket) {
      // websockets are responsible for pushing info about any updates to the data
      // displayed in tabs. This is the only place we're updating this apart of
      // initial load (so contrary to what we used to do, we're not handling responses
      // from any user actions now, like batch entry for instance)
      disconnectWS.call(this);
      // ^^ - for the case where we come from different area like Phonecall Schedule and then
      // we go via it to Sales Order master.websocket is changed and by doing that we assure that
      // communication is set on the right master.websocket . disconnectWS clears the WS
      connectWS.call(this, master.websocket, async (msg) => {
        this.onWebsocketEvent(msg);
      });
    }

    // When closing modal, we need to update the stale tab
    if (
      !modal.visible &&
      modal.visible !== prevProps.modal.visible &&
      master.includedTabsInfo &&
      master.layout
    ) {
      const tabId = master.layout.activeTab;

      getTab(tabId, params.windowType, master.docId).then((tab) => {
        addRowData({ [tabId]: tab }, 'master');
      });
    }
  }

  async onWebsocketEvent(event) {
    const { includedTabsInfo, stale } = event;

    if (stale) {
      // some tabs data got updated/row was added
      if (includedTabsInfo) {
        const rows = this.getTabRows(includedTabsInfo);

        // wait for all the rows requests to finish
        return await rows.then((res) => {
          this.mergeDataIntoIncludedTabs(res);
        });
      } else {
        this.fireFullUpdateData();
      }
    }

    if (includedTabsInfo) {
      const tabIds = Object.keys(includedTabsInfo);
      this.refreshActiveTab(tabIds);
    }
  }

  fireFullUpdateData() {
    const { params } = this.props;
    fireUpdateData(
      'window',
      params.windowType,
      params.docId,
      null,
      null,
      null,
      null
    );
  }

  getTabRows(includedTabsInfo) {
    const requests = [];

    forEach(includedTabsInfo, (tab, tabId) => {
      if (this.isActiveTab(tabId)) {
        const { staleRowIds } = tab;
        staleRowIds.forEach((rowId) => {
          requests.push(this.getTabRow(tabId, rowId));
        });
      }
    });

    return Promise.all(requests);
  }

  getTabRow(tabId, rowId) {
    const { params } = this.props;
    return getData(
      'window',
      params.windowType,
      params.docId,
      tabId,
      rowId
    ).catch(() => ({ rowId, tabId }));
  }

  isActiveTab(tabId) {
    const { master } = this.props;
    return tabId === master.layout.activeTab;
  }

  mergeDataIntoIncludedTabs(responses) {
    const changedTabs = {};

    responses.forEach((response) => {
      const { data } = response;
      let rowsById = null;
      let removedRows = null;
      let tabId;

      // removed row
      if (!data) {
        removedRows = removedRows || {};
        removedRows[response.rowId] = true;
        tabId = !tabId && response.tabId;
      } else {
        rowsById = rowsById || {};
        const rowZero = data[0];
        tabId = !tabId && rowZero.tabId;

        data.forEach((row) => {
          rowsById[row.rowId] = { ...row };
        });
      }

      changedTabs[tabId] = get(changedTabs, `${tabId}`, {});

      if (rowsById) {
        changedTabs[tabId].changed = {
          ...get(changedTabs, `${tabId}.changed`, {}),
          ...rowsById,
        };
      }
      if (removedRows) {
        changedTabs[tabId].removed = {
          ...get(changedTabs, `${tabId}.removed`, {}),
          ...removedRows,
        };
      }
    });

    // FIXME i think we can remove the code below?!
    forEach(changedTabs, (rowsChanged, tabId) => {
      updateTabRowsData('master', tabId, rowsChanged);
    });
  }

  refreshActiveTab(tabIds) {
    const { master, params } = this.props;

    tabIds.forEach((tabId) => {
      const tabLayout =
        master.layout.tabs &&
        master.layout.tabs.filter((tab) => tab.tabId === tabId)[0];
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
        sortingOrder = (ordering.ascending ? '+' : '-') + ordering.fieldName;
      }

      getTab(tabId, params.windowType, master.docId, sortingOrder).then(
        (tab) => {
          addRowData({ [tabId]: tab }, 'master');
        }
      );
    });
  }

  componentWillUnmount() {
    const { clearMasterData } = this.props;

    clearMasterData();
    disconnectWS.call(this);
  }

  render() {
    return <MasterWindow {...this.props} />;
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
 * @prop {func} addNotification
 * @prop {func} addRowData
 * @prop {func} attachFileAction
 * @prop {func} sortTab
 * @prop {func} push
 * @prop {func} clearMasterData
 * @prop {func} fireUpdateData
 * @prop {func} updateTabRowsdata
 */
MasterWindowContainer.propTypes = {
  modal: PropTypes.object.isRequired,
  master: PropTypes.object.isRequired,
  breadcrumb: PropTypes.array.isRequired,
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
  clearMasterData: PropTypes.func,
  addNotification: PropTypes.func,
  addRowData: PropTypes.func,
  attachFileAction: PropTypes.func,
  fireUpdateData: PropTypes.func,
  sortTab: PropTypes.func,
  updateTabRowsData: PropTypes.func,
  push: PropTypes.func,
};

/**
 * @method mapStateToProps
 * @summary ToDo: Describe the method.
 * @param {object} state
 */
const mapStateToProps = (state) => ({
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

export default connect(
  mapStateToProps,
  {
    addNotification,
    addRowData,
    attachFileAction,
    clearMasterData,
    fireUpdateData,
    sortTab,
    updateTabRowsData,
    push,
  }
)(MasterWindowContainer);
