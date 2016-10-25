import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
	notifications: [],
    isLogged: false,
    filters: {
        filterId: "",
        parameters: []
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

        // case types.UPDATE_FILTERS_PARAMETERS:
        //     return Object.assign({}, state, {
        //         filters: update(state.filters, {$push:
        //             [action.filter]
        //         })
        //     });

        case types.INIT_FILTERS_PARAMETERS:
            return Object.assign({}, state, {
                filters: Object.assign({}, state.filters, {
                    filterId: action.filterId,
                    parameters: action.parameters

                })
            })

        case types.DELETE_FILTERS_PARAMETERS:
            return Object.assign({}, state, {
                filters: Object.assign({}, state.filters, {
                    filterId: "",
                    parameters: []

                })
            })

        case types.UPDATE_FILTERS_PARAMETERS:
            return Object.assign({}, state, {
                filters: Object.assign({}, state.filters, {
                    filterId: action.filterId,
                    parameters: state.filters.parameters.map(item =>

                        (item.parameterName === action.property) ?
                        Object.assign({}, item, { value: action.value }) :
                        item
                    )

                })
            })




        // END OF NOTIFICATION ACTIONS

        default:
            return state
    }
}
