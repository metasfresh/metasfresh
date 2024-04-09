import update from 'immutability-helper';
import { createSelector } from 'reselect';
import { createCachedSelector } from 're-reselect';
import { merge } from 'merge-anything';
import { get } from 'lodash';

import {
  ACTIVATE_TAB,
  ALLOW_OUTSIDE_CLICK,
  ALLOW_SHORTCUT,
  CHANGE_INDICATOR_STATE,
  CLEAR_MASTER_DATA,
  CLOSE_FILTER_BOX,
  CLOSE_MODAL,
  CLOSE_PLUGIN_MODAL,
  CLOSE_PROCESS_MODAL,
  CLOSE_RAW_MODAL,
  DISABLE_OUTSIDE_CLICK,
  DISABLE_SHORTCUT,
  INIT_DATA_SUCCESS,
  INIT_LAYOUT_SUCCESS,
  OPEN_FILTER_BOX,
  OPEN_MODAL,
  OPEN_PLUGIN_MODAL,
  OPEN_RAW_MODAL,
  PATCH_FAILURE,
  PATCH_REQUEST,
  PATCH_RESET,
  PATCH_SUCCESS,
  RESET_PRINTING_OPTIONS,
  SET_INLINE_TAB_ADD_NEW,
  SET_INLINE_TAB_ITEM_PROP,
  SET_INLINE_TAB_LAYOUT_AND_DATA,
  SET_INLINE_TAB_SHOW_MORE,
  SET_INLINE_TAB_WRAPPER_DATA,
  SET_PRINTING_OPTIONS,
  SET_RAW_MODAL_DESCRIPTION,
  SET_RAW_MODAL_TITLE,
  SET_SPINNER,
  SORT_TAB,
  TOGGLE_OVERLAY,
  TOGGLE_PRINTING_OPTION,
  TOP_ACTIONS_DELETE,
  TOP_ACTIONS_FAILURE,
  TOP_ACTIONS_LOADING,
  TOP_ACTIONS_SUCCESS,
  UNSELECT_TAB,
  UPDATE_DATA_FIELD_PROPERTY,
  UPDATE_DATA_INCLUDED_TABS_INFO,
  UPDATE_DATA_PROPERTY,
  UPDATE_DATA_SAVE_STATUS,
  UPDATE_DATA_VALID_STATUS,
  UPDATE_INLINE_TAB_DATA,
  UPDATE_INLINE_TAB_ITEM_FIELDS,
  UPDATE_INLINE_TAB_WRAPPER_FIELDS,
  UPDATE_MASTER_DATA,
  UPDATE_MODAL,
  UPDATE_RAW_MODAL,
  UPDATE_TAB_LAYOUT,
} from '../constants/ActionTypes';

import { updateTab } from '../utils';
import { shallowEqual, useSelector } from 'react-redux';

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
  topActions: {},
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

/**
 * In the initialState we have also the `inlineTab` which is the branch that holds the logic for the InlineTabWrapper & InlineTab components
 * - wrapperData keys pattern ${windowId}_{$tabId}_${docId}
 * - inlineTab keys ${windowId}_{$tabId}_${rowId}
 */
export const initialState = {
  showSpinner: false,
  printingOptions: {},
  // TODO: this should be moved to a separate `modalHandler`
  modal: initialModalState,
  inlineTab: {
    wrapperData: {},
    addNew: {},
    showMore: {},
  },

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
 * @param {boolean} isModal
 */
export const getData = (state, isModal = false) => {
  return getLayoutAndData(state, isModal).data;
};

export const getElementLayout = (state, isModal, layoutPath) => {
  const layout = getLayoutAndData(state, isModal).layout;
  const [sectionIdx, columnIdx, elGroupIdx, elLineIdx, elIdx] =
    layoutPath.split('_');

  return layout.sections[sectionIdx].columns[columnIdx].elementGroups[
    elGroupIdx
  ].elementsLine[elLineIdx].elements[elIdx];
};

export const getLayoutAndData = (state, isModal = false) => {
  const selector = isModal ? 'modal' : 'master';
  return state.windowHandler[selector] ?? {};
};

export const getInlineTabLayout = ({
  state,
  inlineTabId,
  layoutId: layoutPath,
}) => {
  const layout = state.windowHandler.inlineTab[inlineTabId].layout;
  const [sectionIdx, columnIdx, elGroupIdx, elLineIdx, elIdx] =
    layoutPath.split('_');
  // console.log('Section:', sectionIdx);
  // console.log('Column:', columnIdx);
  // console.log('elGroupIndex:', elGroupIdx)
  // console.log('ellineIdx:', elLineIdx);
  // console.log('elIds:', elIdx)
  // console.log('layoutContent:', layout)
  // console.log('layoutPath:', layoutPath);
  // console.log('LAYOUT_IN_SELECTOR:', layout.sections[sectionIdx].columns[columnIdx].elementGroups[elGroupIdx].elementsLine[elLineIdx].elements[elIdx])

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

  if (layout.fields) {
    widgetData = layout.fields.reduce((result, item) => {
      const values = get(data, [`${item.field}`], {});
      result.push(values);

      return result;
    }, []);
  }

  if (!widgetData || !widgetData.length) {
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

export const getInlineTabWidgetFields = ({ state, inlineTabId }) => {
  const data = state.windowHandler.inlineTab[`${inlineTabId}`].data;

  return data.fieldsByName;
};

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
export const getMasterDocStatus = createSelector(getData, (data) => {
  return [
    {
      status: data.DocStatus || null,
      action: data.DocAction || null,
      displayed: true,
    },
  ];
});

export const useTopActions = ({ tabId }) => {
  return useSelector(
    (state) => selectTopActionsArray(state, tabId),
    shallowEqual
  );
};

const selectTopActionsArray = (state, tabId) => {
  return state.windowHandler.master?.topActions?.[tabId]?.actions ?? [];
};

const mergeTopActions = (state, tabId, topActions) => {
  if (topActions == null) {
    return update(state, {
      master: { topActions: { $unset: [tabId] } },
    });
  } else if (state.master.topActions[tabId]) {
    return update(state, {
      master: { topActions: { [tabId]: { $merge: topActions } } },
    });
  } else {
    return update(state, {
      master: { topActions: { [tabId]: { $set: topActions } } },
    });
  }
};

export default function windowHandler(state = initialState, action) {
  switch (action.type) {
    case OPEN_MODAL:
      return {
        ...state,
        modal: {
          ...state.modal,
          visible: true,
          ...action.payload,
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
          title: action.title,
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

    case INIT_LAYOUT_SUCCESS: {
      return {
        ...state,
        [action.scope]: {
          ...state[action.scope],
          layout: {
            activeTab: state[action.scope].layout.activeTab, // preserve activeTab. In future consider extracting activeTab out of layout object
            ...action.layout,
          },
        },
      };
    }
    case INIT_DATA_SUCCESS: {
      let layout = state[action.scope].layout ?? {};

      // If the action data is for another windowId then reset the layout.
      // "INIT_LAYOUT_SUCCESS" action to come afterward and set the actual layout.
      if (
        action.windowId !== undefined &&
        layout.windowId !== action.windowId
      ) {
        layout = {};
      }

      if (action.notFoundMessage !== undefined) {
        layout = {
          ...layout,
          notFoundMessage: action.notFoundMessage,
        };
      }
      if (action.notFoundMessageDetail !== undefined) {
        layout = {
          ...layout,
          notFoundMessageDetail: action.notFoundMessageDetail,
        };
      }

      return {
        ...state,
        [action.scope]: {
          ...state[action.scope],
          data: action.data,
          docId: action.docId,
          layout,
          saveStatus: action.saveStatus,
          standardActions: action.standardActions,
          validStatus: action.validStatus,
          includedTabsInfo: action.includedTabsInfo,
          websocket: action.websocket,
          hasComments: action.hasComments,
        },
      };
    }
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
      } else if (
        [
          'saveStatus',
          'validStatus',
          'hasComments',
          'standardActions',
        ].includes(property)
      ) {
        newValue = value;
      } else {
        const currentVal = state[scope] ? state[scope][property] : {};

        newValue = merge(currentVal, value);
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
      return {
        ...state,
        master: {
          ...state.master,
          includedTabsInfo: {
            ...state.master.includedTabsInfo,
            ...action.includedTabsInfo,
          },
        },
      };
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
    case TOP_ACTIONS_LOADING: {
      return mergeTopActions(state, action.payload.tabId, {
        fetching: true,
      });
    }
    case TOP_ACTIONS_SUCCESS: {
      return mergeTopActions(state, action.payload.tabId, {
        fetching: false,
        error: false,
        actions: action.payload.actions,
      });
    }
    case TOP_ACTIONS_FAILURE: {
      return mergeTopActions(state, action.payload.tabId, {
        fetching: false,
        error: true,
      });
    }
    case TOP_ACTIONS_DELETE: {
      return mergeTopActions(state, action.payload.tabId, null);
    }

    case SET_SPINNER: {
      return {
        ...state,
        showSpinner: action.payload,
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
    // INLINE TAB ACTIONS
    case SET_INLINE_TAB_LAYOUT_AND_DATA: {
      return {
        ...state,
        inlineTab: {
          ...state.inlineTab,
          [`${action.payload.inlineTabId}`]: {
            ...state.inlineTab[`${action.payload.inlineTabId}`],
            ...action.payload.data,
          },
        },
      };
    }

    case UPDATE_INLINE_TAB_DATA: {
      return {
        ...state,
        inlineTab: {
          ...state.inlineTab,
          [`${action.payload.inlineTabId}`]: {
            ...state.inlineTab[`${action.payload.inlineTabId}`],
            data: {
              ...state.inlineTab[`${action.payload.inlineTabId}`].data,
              ...action.payload.data,
            },
          },
        },
      };
    }

    case SET_INLINE_TAB_WRAPPER_DATA: {
      return {
        ...state,
        inlineTab: {
          ...state.inlineTab,
          wrapperData: {
            ...state.inlineTab.wrapperData,
            [`${action.payload.inlineTabWrapperId}`]: action.payload.data,
          },
        },
      };
    }
    case UPDATE_INLINE_TAB_WRAPPER_FIELDS: {
      let indexWD;
      const { inlineTabWrapperId, rowId, response } = action.payload;
      if (!response) return { ...state };
      const { fieldsByName, saveStatus, validStatus } = response;
      state.inlineTab.wrapperData[inlineTabWrapperId].forEach((item, i) => {
        if (item.rowId === rowId) indexWD = i;
      });

      const wrapperDataClone = { ...state.inlineTab.wrapperData };
      if (wrapperDataClone[inlineTabWrapperId][indexWD]) {
        wrapperDataClone[inlineTabWrapperId][indexWD].saveStatus = saveStatus;
        wrapperDataClone[inlineTabWrapperId][indexWD].validStatus = validStatus;
        wrapperDataClone[inlineTabWrapperId][indexWD].fieldsByName = {
          ...wrapperDataClone[inlineTabWrapperId][indexWD].fieldsByName,
          ...fieldsByName,
        };
      }

      return {
        ...state,
        inlineTab: {
          ...state.inlineTab,
          wrapperData: wrapperDataClone,
        },
      };
    }

    case UPDATE_INLINE_TAB_ITEM_FIELDS: {
      const { inlineTabId, fieldsByName } = action.payload;

      const targetTabData = { ...state.inlineTab[`${inlineTabId}`].data };

      Object.keys(fieldsByName).forEach((fieldItem) => {
        targetTabData.fieldsByName[fieldItem] = {
          ...targetTabData.fieldsByName[fieldItem],
          ...fieldsByName[fieldItem],
        };
      });

      return {
        ...state,
        inlineTab: {
          ...state.inlineTab,
          [`${inlineTabId}`]: {
            ...state.inlineTab[`${inlineTabId}`],
            data: targetTabData,
          },
        },
      };
    }

    case SET_INLINE_TAB_ADD_NEW: {
      const { visible, windowId, tabId, rowId, docId } = action.payload;
      return {
        ...state,
        inlineTab: {
          ...state.inlineTab,
          addNew: {
            ...state.inlineTab.addNew,
            [`${windowId}_${tabId}_${docId}`]: {
              visible,
              windowId,
              tabId,
              rowId,
            },
          },
        },
      };
    }
    case SET_INLINE_TAB_SHOW_MORE: {
      const { inlineTabWrapperId, showMore } = action.payload;
      return {
        ...state,
        inlineTab: {
          ...state.inlineTab,
          showMore: {
            ...state.inlineTab.showMore,
            [`${inlineTabWrapperId}`]: showMore,
          },
        },
      };
    }
    case SET_INLINE_TAB_ITEM_PROP: {
      const { inlineTabId, targetProp, targetValue } = action.payload;

      return {
        ...state,
        inlineTab: {
          ...state.inlineTab,
          [`${inlineTabId}`]: {
            ...state.inlineTab[`${inlineTabId}`],
            [`${targetProp}`]: targetValue,
          },
        },
      };
    }
    default:
      return state;
  }
}
