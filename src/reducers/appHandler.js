import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
	notification: {
		id: '',
		title: '',
		msg: '',
		time: 0,
		notifType: '',
		notifications: []
	}
}

export default function appHandler(state = initialState, action) {
    switch(action.type){

    	// NOTIFICATION ACTIONS
        case types.ADD_NOTIFICATION:
	        let arr = state.notification.notifications;
	        let index = state.notification.notifications.length;
	        arr.push({id: index, title: action.title, msg: action.msg, time: action.time, notifType: action.notifType});
            return Object.assign({}, state, {
                notification: Object.assign({}, state.notification, {
                	notifications: arr
                })
	        });

	    case types.DELETE_NOTIFICATION:
	        let item = state.notification.notifications;
	        let array = state.notification.notifications;
	        let idToDel = array.indexOf(action.item);
	        array.splice(idToDel, 1);
	        return Object.assign({}, state, {
                notification: Object.assign({}, state.notification, {
                	notifications: array
                })
	        });

	        
        // END OF NOTIFICATION ACTIONS

        default:
            return state
    }
}
