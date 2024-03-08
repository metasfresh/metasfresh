import {
  addNotification,
  setProcessPending,
  setProcessSaved,
} from './AppActions';
import { parseToDisplay } from '../utils/documentListHelper';
import { findViewByViewId } from '../reducers/viewHandler';
import { getTab, openInNewTab } from '../utils';
import history from '../services/History';
import { setIncludedView, unsetIncludedView } from './ViewActions';
import { getTableId } from '../reducers/tables';
import { updateTableSelection, updateTabTableData } from './TableActions';
import {
  closeModal,
  fireUpdateData,
  initDataSuccess,
  initLayoutSuccess,
  openModal,
  openRawModal,
  toggleOverlay,
  updateTabLayout,
} from './WindowActions';
import { CLOSE_PROCESS_MODAL } from '../constants/ActionTypes';
import {
  getProcessData,
  getProcessFileUrl,
  getProcessLayout,
  startProcess,
} from '../api/process';
import { toOrderBysCommaSeparatedString } from '../utils/windowHelpers';
import { getTabRequest } from '../api';
import { getLayoutAndData } from '../reducers/windowHandler';

export const handleProcessResponse = ({
  response,
  processId,
  pinstanceId,
  parentId,
  contextWindowId,
  contextViewId,
}) => {
  return async (dispatch) => {
    const { error, summary, action, refreshCurrentWindowRequired } =
      response.data;

    if (error) {
      await dispatch(addNotification('Process error', summary, 5000, 'error'));
      await dispatch(setProcessSaved());

      // Close process modal in case when process has failed
      await dispatch(closeModal());
    } else {
      let keepProcessModal = false;

      if (action) {
        switch (action.type) {
          case 'displayQRCode': {
            dispatch(toggleOverlay({ type: 'qr', data: action.code }));
            break;
          }
          case 'openView': {
            await dispatch(closeModal());
            const { windowId, viewId, targetTab } = action;
            const urlPath = `/window/${windowId}/?viewId=${viewId}`;
            if (targetTab === 'NEW_TAB') {
              openInNewTab({ urlPath, dispatch, actionName: setProcessSaved });
              return;
            }
            if (targetTab === 'SAME_TAB') {
              window.open(urlPath, '_self');
              return;
            }

            if (targetTab === 'SAME_TAB_OVERLAY') {
              await dispatch(
                openRawModal({ windowId, viewId, profileId: action.profileId })
              );
            }
            break;
          }
          case 'openReport': {
            openProcessFile({
              processId,
              pinstanceId,
              filename: action.filename,
            });

            break;
          }
          case 'openDocument': {
            await dispatch(closeModal());
            const { windowId, documentId, targetTab } = action;
            const urlPath = `/window/${windowId}/${documentId}`;

            if (targetTab === 'NEW_TAB') {
              openInNewTab({ urlPath, dispatch, actionName: setProcessSaved });
              return false;
            }

            if (targetTab === 'SAME_TAB') {
              window.open(urlPath, '_self');
              return false;
            }

            if (action.modal || targetTab === 'SAME_TAB_OVERLAY') {
              // Do not close process modal,
              // since it will be re-used with document view
              keepProcessModal = true;

              await dispatch(
                openModal({
                  windowId: action.windowId,
                  modalType: 'window',
                  isAdvanced: action.advanced ? action.advanced : false,
                  dataId: action.documentId,
                })
              );
            } else {
              history.push(`/window/${action.windowId}/${action.documentId}`);
            }
            break;
          }
          case 'openIncludedView': {
            await dispatch(
              setIncludedView({
                windowId: action.windowId,
                viewId: action.viewId,
                viewProfileId: action.profileId,
                parentId,
              })
            );

            break;
          }
          case 'closeIncludedView': {
            await dispatch(
              unsetIncludedView({
                windowId: action.windowId,
                viewId: action.viewId,
              })
            );

            break;
          }
          case 'selectViewRows': {
            // eslint-disable-next-line no-console
            console.info(
              '@TODO: `selectViewRows` - check if selection worked ok'
            );
            const { windowId, viewId, rowIds } = action;
            const tableId = getTableId({ windowId, viewId });

            dispatch(
              updateTableSelection({
                id: tableId,
                selection: rowIds,
                windowId,
                viewId,
              })
            );

            break;
          }
        }
      }

      if (refreshCurrentWindowRequired) {
        dispatch(
          refreshContextWindowOrView({ contextWindowId, contextViewId })
        );
      }

      if (summary) {
        await dispatch(addNotification('Process', summary, 5000, 'primary'));
      }

      await dispatch(setProcessSaved());

      if (!keepProcessModal) {
        await dispatch(closeProcessModal());
      }
    }
  };
};

export const createProcess = ({
  ids,
  processType: processId,
  rowId,
  tabId,
  documentType,
  viewId,
  selectedTab,
  childViewId,
  childViewSelectedIds,
  parentViewId,
  parentViewSelectedIds,
}) => {
  return async (dispatch, getState) => {
    // creation of processes can be done only if there isn't any pending process
    // https://github.com/metasfresh/metasfresh/issues/10116
    const { processStatus } = getState().appHandler;
    if (processStatus === 'pending') {
      return false;
    }

    await dispatch(setProcessPending());

    let response;

    try {
      response = await getProcessData({
        ids,
        processId,
        rowId,
        tabId,
        documentType,
        viewId,
        selectedTab,
        childViewId,
        childViewSelectedIds,
        parentViewId,
        parentViewSelectedIds,
      });
    } catch (error) {
      // Close process modal in case when process start failed
      await dispatch(closeModal());
      await dispatch(setProcessSaved());

      throw error;
    }

    if (response.data) {
      const preparedData = parseToDisplay(response.data.fieldsByName);

      const pinstanceId = response.data.pinstanceId;

      if (response.data.startProcessDirectly) {
        let response;

        try {
          response = await startProcess(processId, pinstanceId);

          // processes opening included views need the id of the parent view
          const id = parentViewId ? parentViewId : viewId;
          const parentView = id && findViewByViewId(getState(), id);
          const parentId = parentView ? parentView.windowId : documentType;

          await dispatch(
            handleProcessResponse({
              response,
              processId,
              pinstanceId,
              parentId,
              contextWindowId: parentId,
              contextViewId: viewId,
            })
          );
        } catch (error) {
          await dispatch(closeModal());
          await dispatch(setProcessSaved());

          throw error;
        }
      } else {
        await dispatch(
          initDataSuccess({
            data: preparedData,
            scope: 'modal',
          })
        );

        let response;

        try {
          response = await getProcessLayout(processId);

          await dispatch(setProcessSaved());

          const preparedLayout = {
            ...response.data,
            pinstanceId,
          };

          await dispatch(initLayoutSuccess(preparedLayout, 'modal'));
        } catch (error) {
          await dispatch(setProcessSaved());

          throw error;
        }
      }
    }
  };
};

const refreshContextWindowOrView = ({ contextWindowId, contextViewId }) => {
  console.log('refreshContextWindowOrView', { contextWindowId, contextViewId });

  if (contextWindowId == null) {
    return;
  }

  return (dispatch, getState) => {
    if (contextViewId == null) {
      const state = getState();
      const { layout, docId } = getLayoutAndData(state, false);
      if (layout?.windowId === contextWindowId) {
        dispatch(
          fireUpdateData({
            windowId: layout.windowId,
            documentId: docId,
          })
        );

        dispatch(refreshActiveTab({ layout, docId }));
      } else {
        console.log('Refreshing (possible) modal is not implemented');
      }
    } else {
      // contextViewId != null
      console.log('Refreshing view is not implemented');
    }
  };
};

const refreshActiveTab = ({ layout, docId }) => {
  console.log('refreshActiveTab', { layout, docId });
  const windowId = layout.windowId;
  const tabId = layout.activeTab;
  if (!windowId || !tabId) {
    return;
  }

  const tabLayout = getTab(layout, tabId);
  const tabOrderBy = toOrderBysCommaSeparatedString(tabLayout?.orderBy);

  const tableId = getTableId({ windowId, docId, tabId });

  return (dispatch) => {
    dispatch(updateTabLayout(windowId, tabId))
      .then(() => getTabRequest(tabId, windowId, docId, tabOrderBy))
      .then((rows) => dispatch(updateTabTableData(tableId, rows)))
      .catch((error) => error);
  };
};

const openProcessFile = ({ processId, pinstanceId, filename }) => {
  const url = getProcessFileUrl({ processId, pinstanceId, filename });
  window.open(url, '_blank');
};

const closeProcessModal = () => ({
  type: CLOSE_PROCESS_MODAL,
});
