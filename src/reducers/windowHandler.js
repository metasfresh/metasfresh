import update from 'immutability-helper';
import { Map, List, Set } from 'immutable';
import { get, forEach, difference } from 'lodash';
import { createSelector } from 'reselect';
import uuid from 'uuid/v4';

import {
  ACTIVATE_TAB,
  ADD_NEW_ROW,
  ADD_ROW_DATA,
  ALLOW_SHORTCUT,
  ALLOW_OUTSIDE_CLICK,
  CHANGE_INDICATOR_STATE,
  CLEAR_MASTER_DATA,
  CLOSE_MODAL,
  CLOSE_PLUGIN_MODAL,
  CLOSE_PROCESS_MODAL,
  CLOSE_RAW_MODAL,
  CLOSE_FILTER_BOX,
  DELETE_ROW,
  DELETE_QUICK_ACTIONS,
  DELETE_TOP_ACTIONS,
  DESELECT_TABLE_ITEMS,
  DISABLE_SHORTCUT,
  DISABLE_OUTSIDE_CLICK,
  FETCHED_QUICK_ACTIONS,
  FETCH_TOP_ACTIONS,
  FETCH_TOP_ACTIONS_FAILURE,
  FETCH_TOP_ACTIONS_SUCCESS,
  INIT_DATA_SUCCESS,
  INIT_LAYOUT_SUCCESS,
  NO_CONNECTION,
  OPEN_MODAL,
  OPEN_PLUGIN_MODAL,
  OPEN_RAW_MODAL,
  OPEN_FILTER_BOX,
  PATCH_FAILURE,
  PATCH_REQUEST,
  PATCH_RESET,
  PATCH_SUCCESS,
  REMOVE_TABLE_ITEMS_SELECTION,
  SELECT_TABLE_ITEMS,
  SET_LATEST_NEW_DOCUMENT,
  SORT_TAB,
  TOGGLE_OVERLAY,
  UNSELECT_TAB,
  UPDATE_DATA_FIELD_PROPERTY,
  UPDATE_DATA_INCLUDED_TABS_INFO,
  UPDATE_DATA_PROPERTY,
  UPDATE_DATA_SAVE_STATUS,
  UPDATE_DATA_VALID_STATUS,
  UPDATE_MASTER_DATA,
  UPDATE_MODAL,
  UPDATE_RAW_MODAL,
  UPDATE_ROW_FIELD_PROPERTY,
  UPDATE_ROW_PROPERTY,
  UPDATE_ROW_STATUS,
  UPDATE_TAB_ROWS_DATA,
  SHOW_SPINNER,
  HIDE_SPINNER,
} from '../constants/ActionTypes';

export const initialState = {
  connectionError: false,
  modal: {
    visible: false,
    type: '',
    dataId: null,
    tabId: null,
    rowId: null,
    viewId: null,
    layout: {},
    data: {},
    rowData: Map(),
    modalTitle: '',
    modalType: '',
    isAdvanced: false,
    viewDocumentIds: null,
    childViewId: null,
    childViewSelectedIds: null,
    parentViewId: null,
    parentViewSelectedIds: null,
    triggerField: null,
    saveStatus: {},
    validStatus: {},
    includedTabsInfo: {},
    staticModalType: '',
  },
  overlay: {
    visible: false,
    data: null,
  },
  rawModal: {
    visible: false,
    windowType: null,
    viewId: null,
  },
  pluginModal: {
    visible: false,
    type: '',
    id: null,
  },
  master: {
    layout: {
      activeTab: null,
    },
    data: [],

    // rowData is an immutable Map with tabId's as keys, and Lists as values.
    // List's elements are plain objects for now
    rowData: Map(),
    saveStatus: {},
    validStatus: {},
    includedTabsInfo: {},
    docId: undefined,
    websocket: null,
    topActions: {
      actions: [],
      fetching: false,
      error: false,
    },
  },
  quickActions: {},
  indicator: 'saved',
  allowShortcut: true,
  allowOutsideClick: true,
  latestNewDocument: null,
  viewId: null,
  selections: {},
  selectionsHash: null,
  patches: {
    requests: {
      length: 0,
    },
    success: true,
  },
  filter: {},
  spinner: null,
};

export const NO_SELECTION = [];

/* This is an improved function for getting selected rows, as it immediately reacts
 * to any changes to the selectionsHash variable in the state. This variable is set
 * with a random uuid hash whenever table row is selected/deleted.
 */
/* eslint-disable no-unused-vars */
export const getSelectionData = (
  state,
  { windowId, windowType, viewId },
  hash
) => {
  const winId = windowId || windowType;
  const windowTypeSelections = state.windowHandler.selections[winId];
  const id = viewId || winId;

  return (windowTypeSelections && windowTypeSelections[id]) || NO_SELECTION;
};

export const getSelectionInstant = createSelector(
  [getSelectionData],
  (items) => items
);

export const getSelection = ({ state, windowId, viewId }) => {
  const windowTypeSelections = state.windowHandler.selections[windowId];
  const id = viewId || windowId;

  return (windowTypeSelections && windowTypeSelections[id]) || NO_SELECTION;
};
export const getSelectionDirect = (selections, windowId, viewId) => {
  const windowTypeSelections = selections[windowId];

  return (windowTypeSelections && windowTypeSelections[viewId]) || NO_SELECTION;
};

export default function windowHandler(state = initialState, action) {
  switch (action.type) {
    case NO_CONNECTION:
      return {
        ...state,
        connectionError: action.status,
      };
    case OPEN_MODAL:
      return {
        ...state,
        modal: {
          ...state.modal,
          visible: true,
          type: action.windowType,
          staticModalType: action.staticModalType,
          dataId: action.dataId,
          tabId: action.tabId,
          rowId: action.rowId,
          viewId: action.viewId,
          title: action.title,
          modalType: action.modalType,
          isAdvanced: action.isAdvanced,
          viewDocumentIds: action.viewDocumentIds,
          triggerField: action.triggerField,
          parentViewId: action.parentViewId,
          parentViewSelectedIds: action.parentViewSelectedIds,
          childViewId: action.childViewId,
          childViewSelectedIds: action.childViewSelectedIds,
        },
      };
    case OPEN_PLUGIN_MODAL:
      return {
        ...state,
        pluginModal: {
          visible: true,
          type: action.payload.type,
          id: action.payload.id,
        },
      };
    case OPEN_RAW_MODAL:
      return {
        ...state,
        rawModal: {
          ...state.rawModal,
          visible: true,
          windowId: action.windowId,
          viewId: action.viewId,
          profileId: action.profileId,
        },
      };
    case UPDATE_MODAL:
      return {
        ...state,
        modal: {
          ...state.modal,
          rowId: action.rowId,
          dataId: action.dataId,
        },
      };
    case UPDATE_RAW_MODAL: {
      const { windowId, data } = action;

      if (state.rawModal.windowId === windowId) {
        return {
          ...state,
          rawModal: {
            ...state.rawModal,
            ...data,
          },
        };
      } else {
        return state;
      }
    }
    case CLOSE_RAW_MODAL:
      return {
        ...state,
        rawModal: {
          ...state.rawModal,
          visible: false,
          windowId: null,
          viewId: null,
          profileId: null,
        },
      };

    case CLOSE_PROCESS_MODAL:
      if (state.modal.modalType === 'process') {
        return {
          ...state,
          modal: {
            ...state.modal,
            ...initialState.modal,
          },
        };
      }
      return state;
    case CLOSE_MODAL:
      return {
        ...state,
        modal: {
          ...state.modal,
          ...initialState.modal,
        },
      };
    case CLOSE_PLUGIN_MODAL:
      return {
        ...state,
        pluginModal: {
          visible: false,
          type: '',
          id: null,
        },
      };
    case TOGGLE_OVERLAY:
      return {
        ...state,
        overlay: {
          visible: action.data === false ? false : !state.overlay.visible,
          data: action.data ? { ...action.data } : null,
        },
      };

    // SCOPED ACTIONS

    case INIT_LAYOUT_SUCCESS:
      return {
        ...state,
        [action.scope]: {
          ...state[action.scope],
          layout: action.layout,
        },
      };

    case INIT_DATA_SUCCESS:
      return {
        ...state,
        [action.scope]: {
          ...state[action.scope],
          data: action.data,
          docId: action.docId,
          layout: {},
          rowData: Map(),
          saveStatus: action.saveStatus,
          standardActions: Set(action.standardActions),
          validStatus: action.validStatus,
          includedTabsInfo: action.includedTabsInfo,
          websocket: action.websocket,
        },
      };
    case UPDATE_MASTER_DATA:
      return {
        ...state,
        master: {
          ...state.master,
          data: {
            ...state.master.data,
            ...action.payload,
          },
        },
      };
    case CLEAR_MASTER_DATA:
      return {
        ...state,
        master: {
          ...state.master,
          data: {},
          rowData: Map(),
          docId: undefined,
        },
      };
    case SORT_TAB:
      return Object.assign({}, state, {
        [action.scope]: Object.assign({}, state[action.scope], {
          layout: Object.assign({}, state[action.scope].layout, {
            tabs: state[action.scope].layout.tabs.map((tab) =>
              tab.tabId === action.tabId
                ? Object.assign({}, tab, {
                    orderBy: [
                      {
                        fieldName: action.field,
                        ascending: action.asc,
                      },
                    ],
                  })
                : tab
            ),
          }),
        }),
      });
    case ACTIVATE_TAB:
      return update(state, {
        [action.scope]: {
          layout: {
            activeTab: { $set: action.tabId },
          },
        },
      });
    case UNSELECT_TAB:
      return update(state, {
        [action.scope]: {
          layout: {
            activeTab: { $set: null },
          },
        },
      });
    /* eslint-disable no-case-declarations */
    case ADD_ROW_DATA:
      let addRowData = Map();

      for (const [key, item] of Object.entries(action.data)) {
        const arrayItem = item.length ? item : [];
        addRowData = addRowData.set(key, List(arrayItem));
      }

      return {
        ...state,
        [action.scope]: {
          ...state[action.scope],
          rowData: state[action.scope].rowData.merge(addRowData),
        },
      };
    case ADD_NEW_ROW:
      const newRowData = state[action.scope].rowData.update(
        `${action.tabid}`,
        (list) => list.push(action.item)
      );

      return {
        ...state,
        [`${action.scope}`]: {
          ...state[`${action.scope}`],
          rowData: newRowData,
        },
      };
    case DELETE_ROW:
      const deletedRowData = state[action.scope].rowData.update(
        `${action.tabid}`,
        (list) => list.filter((item) => item.rowId !== action.rowid)
      );

      return {
        ...state,
        [`${action.scope}`]: {
          ...state[`${action.scope}`],
          rowData: deletedRowData,
        },
      };

    // websocket event
    case UPDATE_TAB_ROWS_DATA: {
      const {
        data: { changed, removed },
        tabId,
        scope,
      } = action.payload;
      const rowData = state[scope].rowData.toJS();
      let rows = get(rowData, `${tabId}`, []);

      if (rows.length) {
        if (removed) {
          rows = rows.filter((row) => !removed[row.rowId]);
        }

        // find&replace updated rows (unfortunately it's a table so we'll have to traverse it)
        if (changed) {
          rows = rows.map((row) => {
            if (changed[row.rowId]) {
              row = { ...changed[row.rowId] };

              delete changed[row.rowId];

              return row;
            }
            return row;
          });
        }
      } else {
        rows = [];
      }
      // added rows
      forEach(changed, (value, key) => rows.push(value));

      let addRowData = Map();
      addRowData = addRowData.set(tabId, List(rows));

      return {
        ...state,
        [scope]: {
          ...state[scope],
          rowData: state[scope].rowData.merge(addRowData),
        },
      };
    }
    case UPDATE_DATA_FIELD_PROPERTY:
      return update(state, {
        [action.scope]: {
          data: {
            [action.property]: {
              $set: Object.assign(
                {},
                state[action.scope].data[action.property],
                action.item
              ),
            },
          },
        },
      });
    case UPDATE_DATA_PROPERTY: {
      let value;

      if (typeof action.value === 'string') {
        value = action.value;
      } else if (action.property === 'standardActions') {
        // TODO: Evaluate if standardActions of type Set
        // is worth this extra check
        value = Set(action.value);
      } else {
        value = Object.assign(
          {},
          state[action.scope] ? state[action.scope][action.property] : {},
          action.value
        );
      }

      return update(state, {
        [action.scope]: {
          [action.property]: {
            $set: value,
          },
        },
      });
    }
    case UPDATE_ROW_FIELD_PROPERTY: {
      const { scope, tabid, rowid, property } = action;
      const scState = state[scope];
      const scRowData = scState.rowData.get(`${tabid}`);

      if (scState && scState.rowData && scRowData) {
        const updateRowFieldProperty = state[action.scope].rowData.update(
          `${tabid}`,
          (list) =>
            list.map((item) =>
              item.rowId === rowid
                ? {
                    ...item,
                    fieldsByName: {
                      ...item.fieldsByName,
                      [property]: {
                        ...item.fieldsByName[property],
                        ...action.item,
                      },
                    },
                  }
                : item
            )
        );

        return {
          ...state,
          [`${action.scope}`]: {
            ...state[`${action.scope}`],
            rowData: updateRowFieldProperty,
          },
        };
      } else {
        return state;
      }
    }
    case UPDATE_ROW_PROPERTY:
      const updateRowPropertyData = state[action.scope].rowData.update(
        `${action.tabid}`,
        (list) =>
          list.map((item) =>
            item.rowId === action.rowid
              ? {
                  ...item,
                  [action.property]: action.item,
                }
              : item
          )
      );

      return {
        ...state,
        [`${action.scope}`]: {
          ...state[`${action.scope}`],
          rowData: updateRowPropertyData,
        },
      };
    case UPDATE_ROW_STATUS:
      const updateRowStatusData = state[action.scope].rowData.update(
        `${action.tabid}`,
        (list) =>
          list.map((item) =>
            item.rowId !== action.rowid
              ? {
                  ...item,
                  saveStatus: action.saveStatus,
                }
              : item
          )
      );

      return {
        ...state,
        [`${action.scope}`]: {
          ...state[`${action.scope}`],
          rowData: updateRowStatusData,
        },
      };
    /* eslint-enable no-case-declarations */
    case UPDATE_DATA_VALID_STATUS:
      return Object.assign({}, state, {
        [action.scope]: Object.assign({}, state[action.scope], {
          validStatus: action.validStatus,
        }),
      });
    case UPDATE_DATA_SAVE_STATUS:
      return Object.assign({}, state, {
        [action.scope]: Object.assign({}, state[action.scope], {
          saveStatus: action.saveStatus,
        }),
      });
    case UPDATE_DATA_INCLUDED_TABS_INFO:
      return Object.assign({}, state, {
        [action.scope]: Object.assign({}, state[action.scope], {
          includedTabsInfo: Object.keys(
            state[action.scope].includedTabsInfo
          ).reduce((result, current) => {
            result[current] = Object.assign(
              {},
              state[action.scope].includedTabsInfo[current],
              action.includedTabsInfo[current]
                ? action.includedTabsInfo[current]
                : {}
            );
            return result;
          }, {}),
        }),
      });
    // END OF SCOPED ACTIONS

    case SELECT_TABLE_ITEMS: {
      const { windowType, viewId, ids } = action.payload;

      if (!ids) {
        return state;
      }

      const checkedIds = ids.length && ids[0] === undefined ? null : ids;

      return {
        ...state,
        selectionsHash: uuid(),
        selections: {
          ...state.selections,
          [windowType]: {
            ...state.selections[windowType],
            [viewId]: checkedIds,
          },
        },
      };
    }

    case DESELECT_TABLE_ITEMS: {
      const { windowType, viewId, ids } = action.payload;

      const windowTypeSelections = state.selections[windowType]
        ? state.selections[windowType]
        : {};

      return {
        ...state,
        selectionsHash: uuid(),
        selections: {
          ...state.selections,
          [windowType]: {
            ...windowTypeSelections,
            [viewId]: difference(windowTypeSelections[viewId], ids),
          },
        },
      };
    }

    case REMOVE_TABLE_ITEMS_SELECTION: {
      const { windowType, viewId } = action.payload;
      const windowSelections = { ...state.selections[windowType] };

      delete state.selections[windowType];
      delete windowSelections[viewId];

      return {
        ...state,
        selectionsHash: uuid(),
        selections: {
          ...state.selections,
          [windowType]: { ...windowSelections },
        },
      };
    }

    // LATEST NEW DOCUMENT CACHE
    case SET_LATEST_NEW_DOCUMENT:
      return {
        ...state,
        latestNewDocument: action.id,
      };

    case OPEN_FILTER_BOX:
      return {
        ...state,
        filter: {
          ...state.filter,
          visible: true,
          boundingRect: action.boundingRect,
        },
      };
    case CLOSE_FILTER_BOX:
      return {
        ...state,
        filter: {
          ...state.filter,
          visible: false,
          boundingRect: null,
        },
      };
    case ALLOW_OUTSIDE_CLICK:
      return {
        ...state,
        allowOutsideClick: true,
      };
    case DISABLE_OUTSIDE_CLICK:
      return {
        ...state,
        allowOutsideClick: false,
      };
    case ALLOW_SHORTCUT:
      return {
        ...state,
        allowShortcut: true,
      };
    case DISABLE_SHORTCUT:
      return {
        ...state,
        allowShortcut: false,
      };
    case PATCH_REQUEST:
      return {
        ...state,
        patches: {
          ...state.patches,
          requests: {
            ...state.patches.requests,
            [action.symbol]: action.options,
            length: state.patches.requests.length + 1,
          },
        },
      };
    case PATCH_SUCCESS: {
      const requests = { ...state.patches.requests };

      if (!requests[action.symbol]) {
        return state;
      }

      delete requests[action.symbol];
      requests.length = requests.length - 1;

      return {
        ...state,
        patches: {
          ...state.patches,
          requests,
        },
      };
    }
    case PATCH_FAILURE: {
      const requests = { ...state.patches.requests };

      if (!requests[action.symbol]) {
        return state;
      }

      delete requests[action.symbol];
      requests.length = requests.length - 1;

      return {
        ...state,
        patches: {
          ...state.patches,
          requests,
          success: false,
        },
      };
    }
    case PATCH_RESET:
      return {
        ...state,
        patches: {
          ...state.patches,
          requests: {
            length: 0,
          },
          success: true,
        },
      };
    // INDICATOR ACTIONS
    case CHANGE_INDICATOR_STATE:
      return {
        ...state,
        indicator: action.state,
      };

    case SHOW_SPINNER: {
      const newState = {
        ...state,
      };

      if (!newState.spinner) {
        newState.spinner = action.spinnerId;
      }

      return newState;
    }
    case HIDE_SPINNER:
      return {
        ...state,
        spinner: null,
      };
    // QUICK ACTIONS
    case FETCHED_QUICK_ACTIONS:
      return {
        ...state,
        quickActions: {
          ...state.quickActions,
          [`${action.payload.windowId}${
            action.payload.id ? `-${action.payload.id}` : ''
          }`]: action.payload.data,
        },
      };
    case DELETE_QUICK_ACTIONS: {
      const key = `${action.payload.windowId}${
        action.payload.id ? `-${action.payload.id}` : ''
      }`;
      const newQuickActions = { ...state.quickActions };

      delete newQuickActions[key];

      return {
        ...state,
        quickActions: newQuickActions,
      };
    }
    // TOP ACTIONS
    case FETCH_TOP_ACTIONS:
      return {
        ...state,
        master: {
          ...state.master,
          topActions: {
            ...state.master.topActions,
            fetching: true,
          },
        },
      };
    case FETCH_TOP_ACTIONS_SUCCESS:
      return {
        ...state,
        master: {
          ...state.master,
          topActions: {
            ...state.master.topActions,
            actions: action.payload,
            fetching: false,
          },
        },
      };
    case FETCH_TOP_ACTIONS_FAILURE:
      return {
        ...state,
        master: {
          ...state.master,
          topActions: {
            ...state.master.topActions,
            fetching: false,
            error: true,
          },
        },
      };
    case DELETE_TOP_ACTIONS: {
      return {
        ...state,
        master: {
          ...state.master,
          topActions: {
            ...state.master.topActions,
            actions: [],
          },
        },
      };
    }
    default:
      return state;
  }
}
