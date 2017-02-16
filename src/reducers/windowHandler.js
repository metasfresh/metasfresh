import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
    connectionError: false,
    modal: {
        visible: false,
        type: '',
        tabId: null,
        rowId: null,
        viewId: null,
        layout: {},
        data: [],
        rowData: {},
        modalTitle: '',
        modalType: '',
        isAdvanced: false
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
    },
    indicator: 'saved',
    latestNewDocument: null,
    viewId: null,
    selected: []
}

export default function windowHandler(state = initialState, action) {
    switch(action.type){

        case types.NO_CONNECTION:
            return Object.assign({}, state, {
                connectionError: action.status
            })

        case types.OPEN_MODAL:
            return Object.assign({}, state, {
                modal: Object.assign({}, state.modal, {
                    visible: true,
                    type: action.windowType,
                    tabId: action.tabId,
                    rowId: action.rowId,
                    viewId: action.viewId,
                    title: action.title,
                    modalType: action.modalType,
                    isAdvanced: action.isAdvanced
                })
        })

        case types.UPDATE_MODAL:
            return Object.assign({}, state, {
                modal: Object.assign({}, state.modal, {
                    rowId: action.rowId
                })
        })

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
        })

        // SCOPED ACTIONS

        case types.INIT_LAYOUT_SUCCESS:
            return Object.assign({}, state, {
                [action.scope]: Object.assign({}, state[action.scope], {
                    layout: action.layout
                })
        })

        case types.INIT_DATA_SUCCESS:
            return Object.assign({}, state, {
                [action.scope]: Object.assign({}, state[action.scope], {
                    data: action.data,
                    docId: action.docId,
                    layout: {},
                    rowData: {}
                })
        })

        case types.ADD_ROW_DATA:
            return Object.assign({}, state, {
                [action.scope]: Object.assign({}, state[action.scope], {
                    rowData: Object.assign({}, state[action.scope].rowData, action.data)
                })
        })

        case types.ADD_NEW_ROW:
            return update(state, {
                [action.scope]: {
                    rowData: {
                        [action.tabid]: {
                            [action.rowid]: {$set: action.item}
                        }
                    }
                }
            })

        case types.DELETE_ROW:
            return update(state, {
                [action.scope]: {
                    rowData: {
                        [action.tabid]: {$set:
                            Object.keys(state[action.scope].rowData[action.tabid])
                                .filter(key => key !== action.rowid)
                                .reduce((result, current) => {
                                    result[current] = state[action.scope].rowData[action.tabid][current];
                                    return result;
                                }, {})
                            }
                    }
                }
            })

        case types.UPDATE_ROW_SUCCESS:
            return update(state, {
                [action.scope]: {
                    rowData: {
                        [action.tabid]: {
                            [action.rowid]: {
                                fields: {$set: state[action.scope].rowData[action.tabid][action.rowid].fields.map(item =>
                                    item.field === action.item.field ?
                                        Object.assign({},item,action.item) :
                                        item
                                )}
                            }
                        }
                    }
                }
            })

        case types.UPDATE_DATA_SUCCESS:
            return Object.assign({}, state, {
                [action.scope]: Object.assign({}, state[action.scope], {
                    data: state[action.scope].data.map(item =>
                        item.field === action.item.field ?
                            Object.assign({}, item, action.item) :
                            item
                    )
                })
        })

        case types.UPDATE_DATA_PROPERTY:
            return Object.assign({}, state, {
                [action.scope]: Object.assign({}, state[action.scope], {
                    data: state[action.scope].data.map(item =>
                        item.field === action.property ?
                        Object.assign({}, item, { value: action.value }) :
                        item
                    )
                })
        })

        case types.UPDATE_ROW_PROPERTY:
            return update(state, {
                [action.scope]: {
                    rowData: {
                        [action.tabid]: {
                            [action.rowid]: {
                                fields: {$set: state[action.scope].rowData[action.tabid][action.rowid].fields.map( item =>
                                    item.field === action.property ?
                                        Object.assign({}, item, {value: action.value}):
                                        item
                                )}
                            }
                        }
                    }
                }
            })

        // END OF SCOPED ACTIONS

        // INDICATOR ACTIONS
        case types.CHANGE_INDICATOR_STATE:
            return Object.assign({}, state, {
                indicator: action.state
            })

        // END OF INDICATOR ACTIONS

        case types.SELECT_TABLE_ITEMS:
            return Object.assign({}, state, {
                selected: action.ids
            })

        // LATEST NEW DOCUMENT CACHE
        case types.SET_LATEST_NEW_DOCUMENT:
            return Object.assign({}, state, {
                latestNewDocument: action.id
            })

        // RAW Modal
        case types.CLOSE_RAW_MODAL:
            return Object.assign({}, state, {
                rawModal: Object.assign({}, state.rawModal, {
                    visible: false,
                    type: null,
                    viewId: null
                })
            })

        case types.OPEN_RAW_MODAL:
            return Object.assign({}, state, {
                rawModal: Object.assign({}, state.rawModal, {
                    visible: true,
                    type: action.windowType,
                    viewId: action.viewId
                })
            })

        default:
            return state
    }
}
