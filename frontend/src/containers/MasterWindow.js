import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { forEach, get } from 'lodash';

import { connectWS, disconnectWS } from '../utils/websockets';
import { getRowsData, getTabRequest } from '../api';
import { getTab } from '../utils';

import { getTableId } from '../reducers/tables';
import { addNotification, updateLastBackPage } from '../actions/AppActions';
import {
  attachFileAction,
  clearMasterData,
  fireUpdateData,
  openModal,
  patchWindow,
  sortTab,
  updateTabLayout,
} from '../actions/WindowActions';
import {
  deleteTable,
  updateTabRowsData,
  updateTabTableData,
} from '../actions/TableActions';

import MasterWindow from '../components/app/MasterWindow';
import { toOrderBysCommaSeparatedString } from '../utils/windowHelpers';
import { fetchTopActions } from '../actions/Actions';

import history from '../services/History';

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

    this.handleURLParams();
  }

  componentDidMount() {
    const fullPath = window.location.href;
    const { updateLastBackPage } = this.props;
    if (!fullPath.includes('viewId')) {
      updateLastBackPage('');
    }
  }

  componentWillUnmount() {
    const { clearMasterData } = this.props;

    clearMasterData();
    this.deleteTabsTables();
    disconnectWS.call(this);
  }

  async onWebsocketEvent(event) {
    const { includedTabsInfo, stale, activeTabStaled } = event;

    const activeTab = includedTabsInfo
      ? Object.values(includedTabsInfo).find((tabInfo) =>
          this.isActiveTab(tabInfo.tabId)
        )
      : null;
    //console.log('onWebsocketEvent', { event, activeTab });

    //
    // Document header got staled
    if (stale) {
      const { params, fireUpdateData } = this.props;

      fireUpdateData({
        windowId: params.windowId,
        documentId: params.docId,
      });
    }

    //
    // Active included tab got staled
    if (activeTabStaled || activeTab?.stale) {
      this.refreshActiveTab();
    }
    // Some included rows got staled
    else if (activeTab) {
      // if `staleRowIds` is empty, we'll just query for all rows and update what changed
      // This can happen when adding a new product via the `Add new` modal.
      await this.getTabRows(activeTab.tabId, activeTab.staleRowIds).then(
        (res) => {
          this.mergeDataIntoIncludedTab(res);
        }
      );
    }
  }

  /** Handle URL search params and get rid of them */
  handleURLParams = () => {
    const {
      master: { layout },
    } = this.props;

    // Do nothing until layout & data are loaded
    if (!isLayoutLoaded(layout)) {
      return;
    }

    const {
      location: { pathname, search },
      params: { windowId, docId },
      openModal,
      patchWindow,
    } = this.props;
    const urlParams = new URLSearchParams(search);

    let doRemoveURLParams = false;
    for (const fieldName of urlParams.keys()) {
      const field = getFieldFromLayout(layout, fieldName);
      if (!field) {
        console.warn(`Field ${fieldName} not found`);
        continue;
      }

      doRemoveURLParams = true;

      const value = urlParams.get(fieldName);
      if (value === 'NEW' && field.newRecordWindowId) {
        openModal({
          title: field.newRecordCaption,
          windowId: field.newRecordWindowId,
          modalType: 'window',
          dataId: 'NEW',
          triggerField: field.field,
        });
      } else {
        patchWindow({
          windowId,
          documentId: docId,
          fieldName,
          value,
        });
      }
    }

    if (doRemoveURLParams) {
      //console.log('Replacing URL with: ', pathname);
      history.replace(pathname);
    }
  };

  getTabRows(tabId, rows) {
    const {
      params: { windowId, docId },
    } = this.props;

    return getRowsData({
      entity: 'window',
      docType: windowId,
      docId,
      tabId,
      rows,
    }).then((response) => Promise.resolve({ response, tabId }));
  }

  isActiveTab(tabId) {
    const { master } = this.props;
    const activeTab = master.layout.activeTab;
    if (!activeTab) {
      console.log('No active activeTab found', { master });
      return false;
    }

    return tabId === activeTab;
  }

  mergeDataIntoIncludedTab({ response, tabId }) {
    const {
      updateTabRowsData,
      params: { windowId, docId },
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
      const tableId = getTableId({ windowId: windowId, docId, tabId });
      updateTabRowsData(tableId, rowsChanged);
    });
  }

  refreshActiveTab = () => {
    const {
      master,
      params: { windowId, docId },
      updateTabTableData,
      updateTabLayout,
      fetchTopActions,
    } = this.props;

    const activeTabId = master.layout.activeTab;
    if (!activeTabId) {
      return;
    }
    const tableId = getTableId({
      windowId: windowId,
      docId,
      tabId: activeTabId,
    });

    const tabLayout = getTab(master.layout, activeTabId);
    const orderBysArray = tabLayout ? tabLayout.orderBy : null;
    const orderBy = toOrderBysCommaSeparatedString(orderBysArray);

    updateTabLayout(windowId, activeTabId)
      .then(() => {
        getTabRequest(activeTabId, windowId, docId, orderBy).then(({ rows }) =>
          updateTabTableData(tableId, rows)
        );
      })
      .catch((error) => error);

    fetchTopActions({ windowId, tabId: activeTabId, docId });
  };

  deleteTabsTables = () => {
    const {
      master: { includedTabsInfo },
      params: { windowId, docId },
      deleteTable,
    } = this.props;

    // if this is falsy, it was a details view without tabs
    if (includedTabsInfo) {
      const tabs = Object.keys(includedTabsInfo);

      if (tabs) {
        tabs.forEach((tabId) => {
          const tableId = getTableId({ windowId: windowId, docId, tabId });
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
      params: { windowId, docId },
    } = this.props;

    const activeTabId = master.layout.activeTab;
    if (tabId !== activeTabId) {
      return;
    }

    const orderBy = (asc ? '+' : '-') + field;
    const dataId = master.docId;
    const tableId = getTableId({ windowId, docId, tabId });

    sortTab({ scope: 'master', windowId, docId, tabId, field, asc });
    getTabRequest(tabId, windowId, dataId, orderBy).then(({ rows }) => {
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
  location: PropTypes.object,
  clearMasterData: PropTypes.func.isRequired,
  addNotification: PropTypes.func.isRequired,
  attachFileAction: PropTypes.func.isRequired,
  fireUpdateData: PropTypes.func.isRequired,
  openModal: PropTypes.func.isRequired,
  patchWindow: PropTypes.func.isRequired,
  sortTab: PropTypes.func.isRequired,
  updateTabRowsData: PropTypes.func.isRequired,
  deleteTable: PropTypes.func.isRequired,
  updateTabTableData: PropTypes.func.isRequired,
  updateTabLayout: PropTypes.func.isRequired,
  updateLastBackPage: PropTypes.func.isRequired,
  fetchTopActions: PropTypes.func.isRequired,
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
  openModal,
  patchWindow,
  sortTab,
  updateTabRowsData,
  updateTabTableData,
  deleteTable,
  updateTabLayout,
  updateLastBackPage,
  fetchTopActions,
})(MasterWindowContainer);

//
//
//

const isLayoutLoaded = (layout) => {
  return !!layout?.windowId;
};

const getFieldFromLayout = (layout, fieldName) => {
  for (const section of layout.sections ?? []) {
    // console.log('section', section);

    for (const column of section.columns ?? []) {
      // console.log('column', column);

      for (const elementGroup of column.elementGroups ?? []) {
        // console.log('elementGroup', elementGroup);

        for (const elementLine of elementGroup.elementsLine ?? []) {
          // console.log('elementLine', elementLine);

          for (const element of elementLine.elements ?? []) {
            // console.log('element', element);

            for (const field of element?.fields ?? []) {
              // console.log('field', field);

              if (field.field === fieldName) {
                return field;
              }
            }
          }
        }
      }
    }
  }

  return null; // not found
};
