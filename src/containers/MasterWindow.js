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
    const {
      master,
      modal,
      params,
      updateTabRowsData,
      fireUpdateData,
      addRowData,
    } = this.props;

    if (prevProps.master.websocket !== master.websocket && master.websocket) {
      // websockets are responsible for pushing info about any updates to the data
      // displayed in tabs. This is the only place we're updating this apart of
      // initial load (so contrary to what we used to do, we're not handling responses
      // from any user actions now, like batch entry for instance)
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
                    ).catch(() => ({ rowId, tabId }))
                  );
                });
              }
            });

            // wait for all the rows requests to finish
            return await Promise.all(requests).then(res => {
              const changedTabs = {};

              res.forEach(response => {
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

                  data.forEach(row => {
                    rowsById[row.rowId] = { ...row };
                  });
                }

                changedTabs[tabId] = {};

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

              forEach(changedTabs, (rowsChanged, tabId) => {
                updateTabRowsData('master', tabId, rowsChanged);
              });
            });

            // Check my comment in https://github.com/metasfresh/me03/issues/3628 - Kuba
          } else {
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
                  addRowData({ [tabId]: tab }, 'master');
                }
              );
            }
          });
        }
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

      getTab(tabId, params.windowType, master.docId).then(tab => {
        addRowData({ [tabId]: tab }, 'master');
      });
    }
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
