import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
    connectionError: false,
    modal: {
        visible: false,
        type: '',
        dataId: null,
        tabId: null,
        rowId: null,
        viewId: null,
        layout: {},
        data: [],
        rowData: {},
        modalTitle: '',
        modalType: '',
        isAdvanced: false,
        viewDocumentIds: null,
        triggerField: null,
        saveStatus: {},
        validStatus: {},
        includedTabsInfo: {}
    },
    rawModal: {
        visible: false,
        windowType: null,
        viewId: null
    },
    master: {
        layout: {},
        data: [],
        rowData: {},
        saveStatus: {},
        validStatus: {},
        includedTabsInfo: {},
        docId: undefined
    },
    indicator: 'saved',
    latestNewDocument: null,
    viewId: null,
    selected: [],
    selectedWindowType: null
};

export default function windowHandler(state = initialState, action) {

    switch(action.type){

        case types.NO_CONNECTION:
            return Object.assign({}, state, {
                connectionError: action.status
            });

        case types.OPEN_MODAL:
            return Object.assign({}, state, {
                modal: Object.assign({}, state.modal, {
                    visible: true,
                    type: action.windowType,
                    dataId: action.dataId,
                    tabId: action.tabId,
                    rowId: action.rowId,
                    viewId: action.viewId,
                    title: action.title,
                    modalType: action.modalType,
                    isAdvanced: action.isAdvanced,
                    viewDocumentIds: action.viewDocumentIds,
                    triggerField: action.triggerField
                })
            });

        case types.UPDATE_MODAL:
            return Object.assign({}, state, {
                modal: Object.assign({}, state.modal, {
                    rowId: action.rowId,
                    dataId: action.dataId
                })
            });

        case types.CLOSE_MODAL:
            return Object.assign({}, state, {
                modal: Object.assign({}, state.modal, {
                    visible: false,
                    tabId: null,
                    rowId: null,
                    layout: {},
                    data: [],
                    title: '',
                    rowData: {}
                })
            });

        // SCOPED ACTIONS

        case types.INIT_LAYOUT_SUCCESS:
            return Object.assign({}, state, {
                [action.scope]: Object.assign({}, state[action.scope], {
                    layout: action.layout
                })
            });

        case types.INIT_DATA_SUCCESS:
            return Object.assign({}, state, {
                [action.scope]: Object.assign({}, state[action.scope], {
                    data: action.data,
                    docId: action.docId,
                    layout: {},
                    rowData: {},
                    saveStatus: action.saveStatus,
                    validStatus: action.validStatus,
                    includedTabsInfo: action.includedTabsInfo
                })
            });

        case types.CLEAR_MASTER_DATA:
            return Object.assign({}, state, {
                master: Object.assign({}, state.master, {
                    data: [],
                    rowData: {},
                    docId: undefined
                })
            });

        case types.ADD_ROW_DATA:
            return Object.assign({}, state, {
                [action.scope]: Object.assign({}, state[action.scope], {
                    rowData: Object.assign(
                        {}, state[action.scope].rowData, action.data
                    )
                })
            });

        case types.ADD_NEW_ROW:
            return update(state, {
                [action.scope]: {
                    rowData: {
                        [action.tabid]: {$push: [action.item]}
                    }
                }
            });

        case types.DELETE_ROW:
            return update(state, {
                [action.scope]: {
                    rowData: {
                        [action.tabid]:
                        {$set: state[action.scope].rowData[action.tabid]
                            .filter((item) => {
                                if( item.rowId === action.rowId) {
                                    return
                                } else {
                                    return item
                                }
                            })}
                    }
                }
            });

        case types.UPDATE_DATA_FIELD_PROPERTY:
            return update(state, {
                [action.scope]: {
                    data: {$set: state[action.scope].data.map(item =>
                        item.field === action.property ?
                        Object.assign({}, item, action.item) :
                        item
                    )}
                }
            });

        case types.UPDATE_DATA_PROPERTY:
            return update(state, {
                [action.scope]: {
                    [action.property]: {$set:
                        typeof action.value === 'string' ?
                            action.value :
                            Object.assign({},
                                state[action.scope] ?
                                    state[action.scope][action.property] : {},
                                action.value
                            )
                    }
                }
            });

            case types.UPDATE_ROW_FIELD_PROPERTY:
            return update(state, {
                [action.scope]: {
                    rowData: {
                        [action.tabid]: {
                            $set: state[action.scope].rowData[action.tabid]
                            .map(item =>
                                item.rowId === action.rowid?
                                    Object.assign({}, item, ()=>{
                                        item.fields.map(field => {
                                            field.field === action.property ?
                                            Object.assign(
                                                {},
                                                field,
                                                action.item
                                                )
                                            :field
                                        })
                                    })
                                :item
                            )
                        }
                    }
                }
            });

         case types.UPDATE_ROW_PROPERTY:
            return update(state, {
                [action.scope]: {
                    rowData: {
                        [action.tabid]: {
                            $set: state[action.scope].rowData[action.tabid]
                            .map(item =>
                                item.rowId === action.rowid ?
                                {$merge: {
                                    [action.property]: action.item
                                }}
                                : item
                            )
                        }
                    }
                }
            });

        case types.UPDATE_ROW_STATUS:
            return update(state, {
                [action.scope]: {
                    rowData: {
                        [action.tabid]: {
                             $set: state[action.scope].rowData[action.tabid]
                             .map(item =>
                                item.rowId === action.rowid ?
                                {$set : action.saveStatus}
                                : item
                            )
                        }
                    }
                }
            });

        case types.UPDATE_DATA_VALID_STATUS:
            return Object.assign({}, state, {
                [action.scope]: Object.assign({}, state[action.scope], {
                    validStatus: action.validStatus
                })
            });

        case types.UPDATE_DATA_SAVE_STATUS:
            return Object.assign({}, state, {
                [action.scope]: Object.assign({}, state[action.scope], {
                    saveStatus: action.saveStatus
                })
            });

        case types.UPDATE_DATA_INCLUDED_TABS_INFO:
            return Object.assign({}, state, {
                [action.scope]: Object.assign({}, state[action.scope], {
                    includedTabsInfo:
                        Object.keys(state[action.scope].includedTabsInfo)
                            .reduce((result, current) => {
                                result[current] = Object.assign({},
                                    state[action.scope]
                                        .includedTabsInfo[current],
                                    action.includedTabsInfo[current] ?
                                        action.includedTabsInfo[current] : {}
                                );
                                return result;
                            }, {})
                })
            });
        // END OF SCOPED ACTIONS

        // INDICATOR ACTIONS
        case types.CHANGE_INDICATOR_STATE:
            return Object.assign({}, state, {
                indicator: action.state
            });

        // END OF INDICATOR ACTIONS

        case types.SELECT_TABLE_ITEMS:
            return Object.assign({}, state, {
                selected: action.ids,
                selectedWindowType: action.windowType
            });

        // LATEST NEW DOCUMENT CACHE
        case types.SET_LATEST_NEW_DOCUMENT:
            return Object.assign({}, state, {
                latestNewDocument: action.id
            });

        // RAW Modal
        case types.CLOSE_RAW_MODAL:
            return Object.assign({}, state, {
                rawModal: Object.assign({}, state.rawModal, {
                    visible: false,
                    type: null,
                    viewId: null
                })
            });

        case types.OPEN_RAW_MODAL:
            return Object.assign({}, state, {
                rawModal: Object.assign({}, state.rawModal, {
                    visible: true,
                    type: action.windowType,
                    viewId: action.viewId
                })
            });

        default:
            return state;
    }
}
