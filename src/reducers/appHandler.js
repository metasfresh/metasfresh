import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
	notification: {
		notifications: []
	}
}

export default function appHandler(state = initialState, action) {
    switch(action.type){

    	// NOTIFICATION ACTIONS
        case types.ADD_NOTIFICATION:
	        let initialArray = state.notification.notifications;
	        let index = state.notification.notifications.length;
	        var newArray = update(initialArray, {$push: [{id: index, title: action.title, msg: action.msg, time: action.time, notifType: action.notifType}]});

            return Object.assign({}, state, {
                notification: Object.assign({}, state.notification, {
                	notifications: newArray
                })
	        });

	    case types.DELETE_NOTIFICATION:
	        let item = state.notification.notifications;
	        let array = state.notification.notifications;
	        let idToDel = array.indexOf(action.item);
	        var newArraySpliced = update(array, {$splice: [[idToDel,1]]});

	        return Object.assign({}, state, {
                notification: Object.assign({}, state.notification, {
                	notifications: newArraySpliced
                })
	        });

	        
        // END OF NOTIFICATION ACTIONS

        default:
            return state
    }
}
