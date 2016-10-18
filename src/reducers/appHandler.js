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
            return Object.assign({}, state, {
                notification: Object.assign({}, state.notification, {
                	notifications: update(state.notification.notifications, {$push:
                        [{
                            title: action.title,
                            msg: action.msg,
                            time: action.time,
                            notifType: action.notifType
                        }]
                    })
                })
	        });

	    case types.DELETE_NOTIFICATION:
	        return Object.assign({}, state, {
                notification: Object.assign({}, state.notification, {
                	notifications: update(state.notification.notifications, {$splice: [[action.id,1]]})
                })
	        });


        // END OF NOTIFICATION ACTIONS

        default:
            return state
    }
}
