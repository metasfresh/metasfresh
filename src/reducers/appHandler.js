import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
	notification: 'false'
}

export default function appHandler(state = initialState, action) {
    switch(action.type){

    	// NOTIFICATION ACTIONS
        case types.CHANGE_NOTIFICATION_STATE:
            return Object.assign({}, state, {
                notification: action.state
            }
        );
        // END OF NOTIFICATION ACTIONS

        default:
            return state
    }
}
