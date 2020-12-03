import update from 'immutability-helper';
import { Set as iSet } from 'immutable';
import { createSelector } from 'reselect';
import { createCachedSelector } from 're-reselect';
import merge from 'merge';
import { get } from 'lodash';

import {
  ACTIVATE_TAB,
  ALLOW_SHORTCUT,
  ALLOW_OUTSIDE_CLICK,
  CHANGE_INDICATOR_STATE,
  CLEAR_MASTER_DATA,
  CLOSE_MODAL,
  CLOSE_PLUGIN_MODAL,
  CLOSE_PROCESS_MODAL,
  CLOSE_RAW_MODAL,
  CLOSE_FILTER_BOX,
  DELETE_TOP_ACTIONS,
  DISABLE_SHORTCUT,
  DISABLE_OUTSIDE_CLICK,
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
  SET_RAW_MODAL_DESCRIPTION,
  SET_RAW_MODAL_TITLE,
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
  UPDATE_TAB_LAYOUT,
  SET_PRINTING_OPTIONS,
  RESET_PRINTING_OPTIONS,
  TOGGLE_PRINTING_OPTION,
} from '../constants/ActionTypes';

import { updateTab } from '../utils';

const initialMasterState = {
  layout: {
    activeTab: null,
  },
  data: [],
  saveStatus: {},
  validStatus: {},
  includedTabsInfo: {},
  hasComments: false,
  docId: undefined,
  websocket: null,
  topActions: {
    actions: [],
    fetching: false,
    error: false,
  },
};
const initialModalState = {
  visible: false,
  type: '',
  dataId: null,
  tabId: null,
  rowId: null,
  viewId: null,
  layout: {},
  data: {},
  modalTitle: '',
  modalType: '',
  isAdvanced: false,
  viewDocumentIds: [],
  childViewId: null,
  childViewSelectedIds: [],
  parentViewId: null,
  triggerField: null,
  saveStatus: {},
  validStatus: {},
  includedTabsInfo: {},
  staticModalType: '',
};

export const initialState = {
  connectionError: false,
  printingOptions: {},
  // TODO: this should be moved to a separate `modalHandler`
  modal: initialModalState,
  overlay: {
    visible: false,
    data: null,
  },

  // TODO: this should be moved to a separate `modalHandler`
  rawModal: {
    visible: false,
    windowType: null,
    viewId: null,
    title: '',
    description: '',
  },
  pluginModal: {
    visible: false,
    type: '',
    id: null,
  },

  // this only feeds data to details view now
  master: initialMasterState,

  indicator: 'saved',
  allowShortcut: true,
  allowOutsideClick: true,
  viewId: null,
  patches: {
    requests: {
      length: 0,
    },
    success: true,
  },
  filter: {},
};

/**
 * @method getData
 * @summary getter for master data
 *
 * @param {object} state - redux state
 */
export const getData = (state, isModal = false) => {
  const selector = isModal ? 'modal' : 'master';

  return state.windowHandler[selector].data;
};

const getElementLayout = (state, isModal, layoutPath) => {
  const selector = isModal ? 'modal' : 'master';
  const layout = state.windowHandler[selector].layout;
  const [
    sectionIdx,
    columnIdx,
    elGroupIdx,
    elLineIdx,
    elIdx,
  ] = layoutPath.split('_');

  return layout.sections[sectionIdx].columns[columnIdx].elementGroups[
    elGroupIdx
  ].elementsLine[elLineIdx].elements[elIdx];
};

const getProcessLayout = (state, isModal, elementIndex) =>
  state.windowHandler.modal.layout.elements[elementIndex];

/**
 * @method selectWidgetData
 * @summary map layout fields to widgets. If field doesn't exist in data
 * just return an empty object (might be it's not initialized yet, but it
 * exists in the layout)
 *
 * @param {object} data
 * @param {object} layout
 */
const selectWidgetData = (data, layout) => {
  let widgetData = null;

  widgetData = layout.fields.reduce((result, item) => {
    const values = get(data, [`${item.field}`], {});
    result.push(values);

    return result;
  }, []);

  if (!widgetData.length) {
    widgetData = [{}];
  }

  return widgetData;
};

/**
 * @method getElementWidgetData
 * @summary cached selector for picking widget data for a desired element
 *
 * @param {object} state - redux state
 * @param {boolean} isModal
 * @param {string} layoutPath - indexes of elements in the layout structure
 */
export const getElementWidgetData = createCachedSelector(
  getData,
  getElementLayout,
  (data, layout) => selectWidgetData(data, layout)
)((_state_, isModal, layoutPath) => layoutPath);

/**
 * @method getElementWidgetFields
 * @summary cached selector for picking fields of a layout section
 *
 * @param {object} state - redux state
 * @param {boolean} isModal
 * @param {string} layoutPath - indexes of elements in the layout structure
 */
export const getElementWidgetFields = createCachedSelector(
  getElementLayout,
  (layout) => layout.fields
)((_state, isModal, layoutPath) => layoutPath);

/**
 * @method getProcessWidgetData
 * @summary cached selector for picking widget data for a desired element in process
 *
 * @param {object} state - redux state
 * @param {boolean} isModal
 * @param {string} layoutPath - indexes of elements in the layout structure
 */
export const getProcessWidgetData = createCachedSelector(
  getData,
  getProcessLayout,
  (data, layout) => selectWidgetData(data, layout)
)((_state_, isModal, layoutPath) => layoutPath);

/**
 * @method getProcessWidgetFields
 * @summary cached selector for picking fields of process's elements
 *
 * @param {object} state - redux state
 * @param {boolean} isModal
 * @param {string} layoutPath - indexes of elements in the layout structure
 */
export const getProcessWidgetFields = createCachedSelector(
  getProcessLayout,
  (layout) => layout.fields
)((_state, isModal, layoutPath) => layoutPath);

/**
 * @method getMasterDocStatus
 * @summary selector for geting master document status
 *
 * @param {object} state - redux state
 */
export const getMasterDocStatus = createSelector(
  getData,
  (data) => {
    return [
      {
        status: data.DocStatus || null,
        action: data.DocAction || null,
        displayed: true,
      },
    ];
  }
);

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
          childViewId: action.childViewId,
          childViewSelectedIds: action.childViewSelectedIds,
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
    case CLOSE_MODAL:
      return {
        ...state,
        modal: {
          ...state.modal,
          ...initialState.modal,
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
    case SET_RAW_MODAL_TITLE:
      return {
        ...state,
        rawModal: {
          ...state.rawModal,
          title: action.payload.title,
        },
      };
    case SET_RAW_MODAL_DESCRIPTION:
      return {
        ...state,
        rawModal: {
          ...state.rawModal,
          description: action.payload.description,
        },
      };
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
    case OPEN_PLUGIN_MODAL:
      return {
        ...state,
        pluginModal: {
          visible: true,
          type: action.payload.type,
          id: action.payload.id,
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
          saveStatus: action.saveStatus,
          standardActions: iSet(action.standardActions),
          validStatus: action.validStatus,
          includedTabsInfo: action.includedTabsInfo,
          websocket: action.websocket,
          hasComments: action.hasComments,
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
        master: initialMasterState,
        modal: initialModalState,
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
    case UPDATE_TAB_LAYOUT: {
      const { tabId, layoutData } = action.payload;
      const updated = updateTab(state.master.layout.tabs, tabId, layoutData);

      return update(state, {
        master: {
          layout: {
            tabs: { $set: updated },
          },
        },
      });
    }
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
      const { scope, property, value } = action;
      let newValue = null;

      if (typeof value === 'string') {
        newValue = value;
      } else if (property === 'standardActions') {
        // TODO: Use normal array
        newValue = iSet(value);
      } else if (
        ['saveStatus', 'validStatus', 'hasComments'].includes(property)
      ) {
        newValue = value;
      } else {
        const currentVal = state[scope] ? state[scope][property] : {};

        newValue = merge.recursive(true, currentVal, value);
      }

      return update(state, {
        [scope]: {
          [property]: {
            $set: newValue,
          },
        },
      });
    }

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
    case SET_PRINTING_OPTIONS: {
      return {
        ...state,
        printingOptions: action.payload,
      };
    }
    case RESET_PRINTING_OPTIONS: {
      return {
        ...state,
        printingOptions: {},
      };
    }
    case TOGGLE_PRINTING_OPTION: {
      const newPrintingOptions = [...state.printingOptions.options];

      newPrintingOptions.map((item) => {
        if (item.internalName === action.payload) item.value = !item.value;
        return item;
      });
      return {
        ...state,
        printingOptions: {
          ...state.printingOptions,
          options: newPrintingOptions,
        },
      };
    }
    default:
      return state;
  }
}
