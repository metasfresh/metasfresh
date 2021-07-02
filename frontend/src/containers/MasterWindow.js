import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { forEach, get } from 'lodash';

import { connectWS, disconnectWS } from '../utils/websockets';
import { getTabRequest, getRowsData } from '../api';
import { getTab } from '../utils';

import { getTableId } from '../reducers/tables';
import { addNotification } from '../actions/AppActions';
import {
  attachFileAction,
  clearMasterData,
  fireUpdateData,
  sortTab,
  updateTabLayout,
} from '../actions/WindowActions';
import {
  deleteTable,
  updateTabTableData,
  updateTabRowsData,
} from '../actions/TableActions';

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

  componentWillUnmount() {
    const { clearMasterData } = this.props;

    clearMasterData();
    this.deleteTabsTables();
    disconnectWS.call(this);
  }

  async onWebsocketEvent(event) {
    const { includedTabsInfo, stale } = event;

    const activeTab = includedTabsInfo
      ? Object.values(includedTabsInfo).find((tabInfo) =>
          this.isActiveTab(tabInfo.tabId)
        )
      : null;

    // Document header got staled
    if (stale) {
      const { params, fireUpdateData } = this.props;

      fireUpdateData({
        windowId: params.windowType,
        documentId: params.docId,
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
      tabId,
      rows,
    }).then((response) => Promise.resolve({ response, tabId }));
  }

  isActiveTab(tabId) {
    const { master } = this.props;

    return tabId === master.layout.activeTab;
  }

  mergeDataIntoIncludedTab({ response, tabId }) {
    const {
      updateTabRowsData,
      params: { windowType, docId },
    } = this.props;
    const {
      data: { result, missingIds },
    } = response;
    const changedTabs = {};
    let rowsById = null;
    let removedRows = null;

    if (missingIds && missingIds.length) {
      removedRows = removedRows || {};
      missingIds.forEach((rowId) => {
        removedRows[rowId] = true;
      });
    }
    if (result && result.length) {
      rowsById = rowsById || {};
      result.forEach((row) => {
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

  refreshActiveTab = () => {
    const {
      master,
      params: { windowType, docId },
      updateTabTableData,
      updateTabLayout,
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

    const tabLayout = getTab(master.layout, activeTabId);
    const orderBy = tabLayout ? tabLayout.orderBy : null;
    let sortingOrder = null;

    if (orderBy && orderBy.length) {
      const ordering = orderBy[0];
      sortingOrder = (ordering.ascending ? '+' : '-') + ordering.fieldName;
    }

    updateTabLayout(windowType, activeTabId)
      .then(() => {
        getTabRequest(activeTabId, windowType, docId, sortingOrder).then(
          (rows) => updateTabTableData(tableId, rows)
        );
      })
      .catch((error) => error);
  };

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

  /**
   * @method sort
   * @summary Sort table data
   */
  sort = (asc, field, startPage, page, tabId) => {
    const {
      updateTabTableData,
      sortTab,
      master,
      params: { windowType, docId },
    } = this.props;
    const orderBy = (asc ? '+' : '-') + field;
    const dataId = master.docId;
    const activeTabId = master.layout.activeTab;

    if (!activeTabId) {
      return;
    }
    const tableId = getTableId({
      windowId: windowType,
      docId,
      tabId: activeTabId,
    });

    sortTab('master', tabId, field, asc);
    getTabRequest(tabId, windowType, dataId, orderBy).then((rows) => {
      updateTabTableData(tableId, rows);
    });
  };

  render() {
    return (
      <MasterWindow
        {...this.props}
        onRefreshTab={this.refreshActiveTab}
        onSortTable={this.sort}
      />
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
  updateTabLayout: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => ({
  master: state.windowHandler.master,
  modal: state.windowHandler.modal,
  rawModal: state.windowHandler.rawModal,
  pluginModal: state.windowHandler.pluginModal,
  overlay: state.windowHandler.overlay,
  indicator: state.windowHandler.indicator,
  includedView: state.viewHandler.includedView,
  allowShortcut: state.windowHandler.allowShortcut,
  enableTutorial: state.appHandler.enableTutorial,
  processStatus: state.appHandler.processStatus,
  me: state.appHandler.me,
  breadcrumb: state.menuHandler.breadcrumb,
});

export default connect(mapStateToProps, {
  addNotification,
  attachFileAction,
  clearMasterData,
  fireUpdateData,
  sortTab,
  updateTabRowsData,
  updateTabTableData,
  push,
  deleteTable,
  updateTabLayout,
})(MasterWindowContainer);
