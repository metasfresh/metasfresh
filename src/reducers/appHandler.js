import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
	notifications: [],
    isLogged: false
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

        default:
            return state
    }
}
