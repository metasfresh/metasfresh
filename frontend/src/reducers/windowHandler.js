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
  DELETE_QUICK_ACTIONS,
  DELETE_TOP_ACTIONS,
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
  SET_INLINE_TAB_LAYOUT_AND_DATA,
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
  inlineTab: {},
  // {
  //   layout: {"windowId":"123","type":"123","tabId":"AD_Tab-222","tabid":"AD_Tab-222","internalName":"C_BPartner_Location","caption":"Adresse","sections":[{"columns":[{"elementGroups":[{"type":"primary","internalName":"X_AD_UI_ElementGroup[AD_UI_ElementGroup_ID=1000034, Name=default, trxName=<<ThreadInherited>>]","elementsLine":[{"elements":[{"caption":"Abw. Firmenname","description":"","widgetType":"Text","maxLength":255,"viewEditorRenderMode":"never","fields":[{"field":"BPartnerName","caption":"Abw. Firmenname","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"Name","description":"","widgetType":"Text","maxLength":60,"size":"L","viewEditorRenderMode":"never","fields":[{"field":"Name","caption":"Name","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"Anschrift","description":"Adresse oder Anschrift","widgetType":"Address","viewEditorRenderMode":"never","fields":[{"field":"C_Location_ID","caption":"Anschrift","emptyText":"leer","clearValueText":"leer","source":"lookup","lookupSearchStringMinLength":-1,"lookupSearchStartDelayMillis":500}]}]},{"elements":[{"caption":"Adresse","description":"Anschrift","widgetType":"LongText","maxLength":360,"viewEditorRenderMode":"never","fields":[{"field":"Address","caption":"Adresse","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"GLN","description":"","widgetType":"Text","maxLength":20,"viewEditorRenderMode":"never","fields":[{"field":"GLN","caption":"GLN","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"Aktiv","description":"Der Eintrag ist im System aktiv","widgetType":"Switch","viewEditorRenderMode":"never","fields":[{"field":"IsActive","caption":"Aktiv","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"Lieferadresse","description":"Liefer-Adresse für den Geschäftspartner","widgetType":"YesNo","viewEditorRenderMode":"never","fields":[{"field":"IsShipTo","caption":"Lieferadresse","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"Lieferstandard","description":"","widgetType":"YesNo","viewEditorRenderMode":"never","fields":[{"field":"IsShipToDefault","caption":"Lieferstandard","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"Rechnungsadresse","description":"Rechnungs-Adresse für diesen Geschäftspartner","widgetType":"YesNo","viewEditorRenderMode":"never","fields":[{"field":"IsBillTo","caption":"Rechnungsadresse","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"Rechnungsstandard","description":"","widgetType":"YesNo","viewEditorRenderMode":"never","fields":[{"field":"IsBillToDefault","caption":"Rechnungsstandard","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"Abladeort","description":"","widgetType":"YesNo","viewEditorRenderMode":"never","fields":[{"field":"IsHandOverLocation","caption":"Abladeort","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"EDI-Supplier-Addresse","description":"Erstattungs-Adresse für den Lieferanten","widgetType":"YesNo","viewEditorRenderMode":"never","fields":[{"field":"IsRemitTo","caption":"EDI-Supplier-Addresse","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"Replikations Standardwert","description":"","widgetType":"YesNo","viewEditorRenderMode":"never","fields":[{"field":"IsReplicationLookupDefault","caption":"Replikations Standardwert","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"Besuchsadresse","description":"","widgetType":"YesNo","viewEditorRenderMode":"never","fields":[{"field":"VisitorsAddress","caption":"Besuchsadresse","emptyText":"leer","clearValueText":"leer"}]}]},{"elements":[{"caption":"Sektion","description":"Organisatorische Einheit des Mandanten","widgetType":"Lookup","size":"M","viewEditorRenderMode":"never","fields":[{"field":"AD_Org_ID","caption":"Sektion","emptyText":"leer","clearValueText":"leer","source":"lookup","lookupSearchStringMinLength":-1,"lookupSearchStartDelayMillis":500,"supportZoomInto":true}]}]},{"elements":[{"caption":"Mandant","description":"Mandant für diese Installation.","widgetType":"List","size":"M","viewEditorRenderMode":"never","fields":[{"field":"AD_Client_ID","caption":"Mandant","emptyText":"leer","clearValueText":"leer","source":"list","lookupSearchStringMinLength":-1,"lookupSearchStartDelayMillis":500,"supportZoomInto":true}]}]}]}]}],"closableMode":"ALWAYS_OPEN"}]},
  //   data: [{"windowId":"123","id":"2155894","tabId":"AD_Tab-222","tabid":"AD_Tab-222","rowId":"2202690","fieldsByName":{"ID":{"field":"ID","value":2202690,"widgetType":"Integer"},"AD_Client_ID":{"field":"AD_Client_ID","value":{"key":"1000000","caption":"metasfresh"},"readonly":true,"mandatory":true,"displayed":true,"lookupValuesStale":true,"widgetType":"List","validStatus":{"valid":true,"initialValue":true,"fieldName":"AD_Client_ID"},"viewEditorRenderMode":"never"},"AD_Org_ID":{"field":"AD_Org_ID","value":{"key":"1000000","caption":"metasfresh AG"},"readonly":true,"mandatory":true,"displayed":true,"lookupValuesStale":true,"widgetType":"Lookup","validStatus":{"valid":true,"initialValue":true,"fieldName":"AD_Org_ID"},"viewEditorRenderMode":"never"},"Name":{"field":"Name","value":"Am Nossbacher Weg 2","readonly":false,"mandatory":true,"displayed":true,"widgetType":"Text","validStatus":{"valid":true,"initialValue":true,"fieldName":"Name"},"viewEditorRenderMode":"always"},"GLN":{"field":"GLN","value":null,"readonly":false,"mandatory":false,"displayed":true,"widgetType":"Text","validStatus":{"valid":true,"initialValue":true,"fieldName":"GLN"},"viewEditorRenderMode":"always"},"IsActive":{"field":"IsActive","value":true,"readonly":false,"mandatory":true,"displayed":true,"widgetType":"Switch","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsActive"},"viewEditorRenderMode":"always"},"C_Location_ID":{"field":"C_Location_ID","value":{"key":"2189861","caption":"Bonn_Deutschland"},"readonly":false,"mandatory":true,"displayed":true,"lookupValuesStale":true,"widgetType":"Address","validStatus":{"valid":true,"initialValue":true,"fieldName":"C_Location_ID"},"viewEditorRenderMode":"always"},"Address":{"field":"Address","value":"Am Nossbacher Weg 2\nDEU-53179 Bonn","readonly":true,"mandatory":false,"displayed":true,"widgetType":"LongText","validStatus":{"valid":true,"initialValue":true,"fieldName":"Address"},"viewEditorRenderMode":"never"},"IsShipTo":{"field":"IsShipTo","value":true,"readonly":true,"mandatory":true,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsShipTo"},"viewEditorRenderMode":"never"},"IsShipToDefault":{"field":"IsShipToDefault","value":true,"readonly":false,"mandatory":false,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsShipToDefault"},"viewEditorRenderMode":"always"},"IsBillTo":{"field":"IsBillTo","value":true,"readonly":true,"mandatory":true,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsBillTo"},"viewEditorRenderMode":"never"},"IsBillToDefault":{"field":"IsBillToDefault","value":true,"readonly":false,"mandatory":false,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsBillToDefault"},"viewEditorRenderMode":"always"},"IsHandOverLocation":{"field":"IsHandOverLocation","value":true,"readonly":false,"mandatory":true,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsHandOverLocation"},"viewEditorRenderMode":"always"},"IsRemitTo":{"field":"IsRemitTo","value":true,"readonly":false,"mandatory":true,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsRemitTo"},"viewEditorRenderMode":"always"},"IsReplicationLookupDefault":{"field":"IsReplicationLookupDefault","value":false,"readonly":false,"mandatory":false,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsReplicationLookupDefault"},"viewEditorRenderMode":"always"},"VisitorsAddress":{"field":"VisitorsAddress","value":false,"readonly":false,"mandatory":true,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"VisitorsAddress"},"viewEditorRenderMode":"always"},"BPartnerName":{"field":"BPartnerName","value":null,"readonly":false,"mandatory":false,"displayed":true,"widgetType":"Text","validStatus":{"valid":true,"initialValue":true,"fieldName":"BPartnerName"},"viewEditorRenderMode":"always"}},"validStatus":{"valid":true},"saveStatus":{"deleted":false,"saved":true,"hasChanges":false,"error":false,"reason":"just loaded"},"standardActions":["new","advancedEdit","email","letter","delete","comments"],"websocketEndpoint":"/document/123/2155894"},{"windowId":"123","id":"2155894","tabId":"AD_Tab-222","tabid":"AD_Tab-222","rowId":"2205176","fieldsByName":{"ID":{"field":"ID","value":2205176,"widgetType":"Integer"},"AD_Client_ID":{"field":"AD_Client_ID","value":{"key":"1000000","caption":"metasfresh"},"readonly":true,"mandatory":true,"displayed":true,"lookupValuesStale":true,"widgetType":"List","validStatus":{"valid":true,"initialValue":true,"fieldName":"AD_Client_ID"},"viewEditorRenderMode":"never"},"AD_Org_ID":{"field":"AD_Org_ID","value":{"key":"1000000","caption":"metasfresh AG"},"readonly":true,"mandatory":true,"displayed":true,"lookupValuesStale":true,"widgetType":"Lookup","validStatus":{"valid":true,"initialValue":true,"fieldName":"AD_Org_ID"},"viewEditorRenderMode":"never"},"Name":{"field":"Name","value":".","readonly":false,"mandatory":true,"displayed":true,"widgetType":"Text","validStatus":{"valid":true,"initialValue":true,"fieldName":"Name"},"viewEditorRenderMode":"always"},"GLN":{"field":"GLN","value":null,"readonly":false,"mandatory":false,"displayed":true,"widgetType":"Text","validStatus":{"valid":true,"initialValue":true,"fieldName":"GLN"},"viewEditorRenderMode":"always"},"IsActive":{"field":"IsActive","value":true,"readonly":false,"mandatory":true,"displayed":true,"widgetType":"Switch","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsActive"},"viewEditorRenderMode":"always"},"C_Location_ID":{"field":"C_Location_ID","value":null,"readonly":false,"mandatory":true,"displayed":true,"lookupValuesStale":true,"widgetType":"Address","validStatus":{"valid":false,"initialValue":true,"reason":"Mandatory field C_Location_ID not filled","fieldName":"C_Location_ID"},"viewEditorRenderMode":"always"},"Address":{"field":"Address","value":null,"readonly":true,"mandatory":false,"displayed":true,"widgetType":"LongText","validStatus":{"valid":false,"initialValue":true,"reason":"not validated yet"},"viewEditorRenderMode":"never"},"IsShipTo":{"field":"IsShipTo","value":true,"readonly":false,"mandatory":true,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsShipTo"},"viewEditorRenderMode":"always"},"IsShipToDefault":{"field":"IsShipToDefault","value":false,"readonly":false,"mandatory":false,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsShipToDefault"},"viewEditorRenderMode":"always"},"IsBillTo":{"field":"IsBillTo","value":true,"readonly":false,"mandatory":true,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsBillTo"},"viewEditorRenderMode":"always"},"IsBillToDefault":{"field":"IsBillToDefault","value":false,"readonly":false,"mandatory":false,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsBillToDefault"},"viewEditorRenderMode":"always"},"IsHandOverLocation":{"field":"IsHandOverLocation","value":true,"readonly":false,"mandatory":true,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsHandOverLocation"},"viewEditorRenderMode":"always"},"IsRemitTo":{"field":"IsRemitTo","value":false,"readonly":false,"mandatory":true,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsRemitTo"},"viewEditorRenderMode":"always"},"IsReplicationLookupDefault":{"field":"IsReplicationLookupDefault","value":false,"readonly":false,"mandatory":false,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"IsReplicationLookupDefault"},"viewEditorRenderMode":"always"},"VisitorsAddress":{"field":"VisitorsAddress","value":false,"readonly":false,"mandatory":true,"displayed":true,"widgetType":"YesNo","validStatus":{"valid":true,"initialValue":true,"fieldName":"VisitorsAddress"},"viewEditorRenderMode":"always"},"BPartnerName":{"field":"BPartnerName","value":null,"readonly":false,"mandatory":false,"displayed":true,"widgetType":"Text","validStatus":{"valid":false,"initialValue":true,"reason":"not validated yet"},"viewEditorRenderMode":"always"}},"validStatus":{"valid":false,"initialValue":true,"reason":"Mandatory field C_Location_ID not filled","fieldName":"C_Location_ID"},"saveStatus":{"deleted":false,"saved":false,"hasChanges":true,"error":false,"reason":"Mandatory field C_Location_ID not filled"},"standardActions":["new","advancedEdit","email","letter","delete","comments"],"websocketEndpoint":"/document/123/2155894"}],
  // },
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
  quickActions: {},
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

export const NO_SELECTION = [];

const getQuickactionsData = (state, { windowType, viewId }) => {
  const key = `${windowType}${viewId ? `-${viewId}` : ''}`;

  return state.windowHandler.quickActions[key] || NO_SELECTION;
};

export const getQuickactions = createSelector(
  [getQuickactionsData],
  (actions) => actions
);

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

export const getElementLayout = (state, isModal, layoutPath) => {
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

export const getInlineTabLayout = ({
  state,
  inlineTabId,
  layoutId: layoutPath,
}) => {
  const layout = state.windowHandler.inlineTab[inlineTabId].layout;
  const [
    sectionIdx,
    columnIdx,
    elGroupIdx,
    elLineIdx,
    elIdx,
  ] = layoutPath.split('_');
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
 * @method getInlineTabWidgetFields
 *
 * @param {object} state - redux state
 * @param {boolean} isModal
 * @param {string} layoutPath - indexes of elements in the layout structure
 */
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
    case SET_INLINE_TAB_LAYOUT_AND_DATA: {
      return {
        ...state,
        inlineTab: {
          ...state.inlineTab,
          [`${action.payload.inlineTabId}`]: action.payload.data,
        },
      };
    }
    default:
      return state;
  }
}
