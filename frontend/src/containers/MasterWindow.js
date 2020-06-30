import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { forEach, get } from 'lodash';

import { getTableId } from '../reducers/tables';
import { addNotification } from '../actions/AppActions';
import {
  attachFileAction,
  clearMasterData,
  fireUpdateData,
  sortTab,
} from '../actions/WindowActions';
import {
  deleteTable,
  updateTabTableData,
  updateTabRowsData,
} from '../actions/TableActions';
import { connectWS, disconnectWS } from '../utils/websockets';
import { getTabRequest, getRowsData } from '../api';

import MasterWindow from '../components/app/MasterWindow';

/**
 * @file Class based component.
 * @module MasterWindow
 * @extends PureComponent
 */
class MasterWindowContainer extends PureComponent {
  componentDidUpdate(prevProps) {
    const { master } = this.props;

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
  }

  async onWebsocketEvent(event) {
    const { includedTabsInfo, stale } = event;

    const activeTab = includedTabsInfo
      ? Object.values(includedTabsInfo).find((tabInfo) =>
          // TODO: Why sometimes we use `tabid` and other times `tabId` ??!!
          this.isActiveTab(tabInfo.tabId)
        )
      : null;

    // Document header got staled
    if (stale) {
      const { params, fireUpdateData } = this.props;

      fireUpdateData({
        windowId: params.windowType,
        documentId: params.docId,
        doNotFetchIncludedTabs: true,
      });
    }

    // Active included tab got staled
    if (activeTab) {
      // Full tab got staled
      if (activeTab.stale) {
        this.refreshActiveTab();
      }
      // Some included rows got staled
      else {
        // if `staleRowIds` is empty, we'll just query for all rows and update what changed
        // This can happen when adding a new product via the `Add new` modal.
        const { staleRowIds } = activeTab;

        await this.getTabRows(activeTab.tabId, staleRowIds).then((res) => {
          this.mergeDataIntoIncludedTab(res);
        });
      }
    }
  }

  getTabRows(tabId, rows) {
    const {
      params: { windowType, docId },
    } = this.props;

    return getRowsData({
      entity: 'window',
      docType: windowType,
      docId,
      tabId: tabId,
      rows,
    }).catch(() => {
      // since we're querying multiple rows, but can only catch one
      // error, we'll focus on the case when row was deleted and the
      // endpoint returns 404
      return { rowId: rows[0], tabId };
    });
  }

  isActiveTab(tabId) {
    const { master } = this.props;

    return tabId === master.layout.activeTab;
  }

  mergeDataIntoIncludedTab(response) {
    const {
      updateTabRowsData,
      params: { windowType, docId },
    } = this.props;
    const { data } = response;
    const changedTabs = {};
    let rowsById = null;
    let removedRows = null;
    let tabId;

    // removed row
    if (!data) {
      removedRows = removedRows || {};
      removedRows[response.rowId] = true;
      tabId = response.tabId;
    } else {
      rowsById = rowsById || {};
      const rowZero = data[0];
      tabId = rowZero.tabId;

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

    forEach(changedTabs, (rowsChanged, tabId) => {
      const tableId = getTableId({ windowId: windowType, docId, tabId });
      updateTabRowsData(tableId, rowsChanged);
    });
  }

  refreshActiveTab() {
    const {
      master,
      params: { windowType, docId },
      updateTabTableData,
    } = this.props;

    const activeTabId = master.layout.activeTab;
    if (!activeTabId) {
      return;
    }
    const tableId = getTableId({
      windowId: windowType,
      docId,
      tabId: activeTabId,
    });

    const tabLayout =
      master.layout.tabs &&
      master.layout.tabs.filter((tab) => tab.tabId === activeTabId)[0];

    const orderBy = tabLayout.orderBy;
    let sortingOrder = null;
    if (orderBy && orderBy.length) {
      const ordering = orderBy[0];
      sortingOrder = (ordering.ascending ? '+' : '-') + ordering.fieldName;
    }

    getTabRequest(activeTabId, windowType, docId, sortingOrder).then((rows) =>
      updateTabTableData(tableId, rows)
    );
  }

  deleteTabsTables = () => {
    const {
      master: { includedTabsInfo },
      params: { windowType, docId },
      deleteTable,
    } = this.props;

    // if this is falsy, it was a details view without tabs
    if (includedTabsInfo) {
      const tabs = Object.keys(includedTabsInfo);

      if (tabs) {
        tabs.forEach((tabId) => {
          const tableId = getTableId({ windowId: windowType, docId, tabId });
          deleteTable(tableId);
        });
      }
    }
  };

  componentWillUnmount() {
    const { clearMasterData } = this.props;

    clearMasterData();
    this.deleteTabsTables();
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
  clearMasterData: PropTypes.func.isRequired,
  addNotification: PropTypes.func.isRequired,
  attachFileAction: PropTypes.func.isRequired,
  fireUpdateData: PropTypes.func.isRequired,
  sortTab: PropTypes.func.isRequired,
  updateTabRowsData: PropTypes.func.isRequired,
  push: PropTypes.func.isRequired,
  deleteTable: PropTypes.func.isRequired,
  updateTabTableData: PropTypes.func.isRequired,
};

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
    attachFileAction,
    clearMasterData,
    fireUpdateData,
    sortTab,
    updateTabRowsData,
    updateTabTableData,
    push,
    deleteTable,
  }
)(MasterWindowContainer);
