import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
	notifications: [],
    isLogged: false,
    inbox: {
        notifications: [],
        unreadCount: 0
    }
}

export default function appHandler(state = initialState, action) {
    switch(action.type){
        case types.LOGIN_SUCCESS:
            return Object.assign({}, state, {
                isLogged: true
            })

        case types.LOGOUT_SUCCESS:
            return Object.assign({}, state, {
                isLogged: false
            })

        // NOTIFICATION ACTIONS
        case types.ADD_NOTIFICATION:
            return Object.assign({}, state, {
                notifications: update(state.notifications, {$push:
                    [{
                        title: action.title,
                        msg: action.msg,
                        time: action.time,
                        notifType: action.notifType
                    }]
                })
            });
            
        case types.DELETE_NOTIFICATION:
            return Object.assign({}, state, {
                notifications: update(state.notifications, {$splice: [[action.id,1]]})
            });
        // END OF NOTIFICATION ACTIONS
        case types.GET_NOTIFICATIONS_SUCCESS:
            return Object.assign({}, state, {
                inbox: Object.assign({}, state.inbox, {
                    notifications: action.notifications,
                    unreadCount: action.unreadCount
                })
            });
        case types.NEW_NOTIFICATION:
            return update(state, {
                inbox: {
                    notifications: {$unshift: [action.notification]},
                    unreadCount: {$set: action.unreadCount}
                }
            })
        case types.UPDATE_NOTIFICATION:
            return update(state, {
                inbox: {
                    notifications: {$merge: [action.notification]},
                    unreadCount: {$set: action.unreadCount}
                }
            })

        default:
            return state
    }
}
