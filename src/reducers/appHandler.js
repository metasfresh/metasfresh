import update from 'immutability-helper';

import * as types from '../constants/ActionTypes';

const initialState = {
  notifications: {},
  me: {},
  isLogged: false,
  enableTutorial: false,
  processStatus: 'saved',
  inbox: {
    notifications: [],
    unreadCount: 0,
  },
};

export default function appHandler(state = initialState, action) {
  switch (action.type) {
    case types.USER_SESSION_INIT:
      return Object.assign({}, state, {
        me: action.me,
      });
    case types.USER_SESSION_UPDATE:
      return Object.assign({}, state, {
        me: Object.assign({}, state.me, action.me),
      });
    case types.LOGIN_SUCCESS:
      return Object.assign({}, state, {
        isLogged: true,
      });

    case types.LOGOUT_SUCCESS:
      return Object.assign({}, state, {
        isLogged: false,
      });

    case types.ENABLE_TUTORIAL:
      return Object.assign({}, state, {
        enableTutorial: action.flag,
      });

    // NOTIFICATION ACTIONS
    case types.ADD_NOTIFICATION:
      return Object.assign({}, state, {
        notifications: Object.assign({}, state.notifications, {
          [action.title]: {
            title: action.title,
            msg: action.msg,
            shortMsg: action.shortMsg,
            time: action.time,
            notifType: action.notifType,
            count: state.notifications[action.title]
              ? state.notifications[action.title].count + 1
              : 0,
          },
        }),
      });

    case types.SET_NOTIFICATION_PROGRESS: {
      const notifications = Object.assign({}, state.notifications, {
        [action.key]: Object.assign({}, state.notifications[action.key], {
          progress: action.progress,
        }),
      });

      return Object.assign({}, state, {
        notifications: notifications,
      });
    }

    case types.DELETE_NOTIFICATION:
      return Object.assign({}, state, {
        notifications: Object.keys(state.notifications).reduce((res, key) => {
          if (key !== action.key) {
            res[key] = state.notifications[key];
          }
          return res;
        }, {}),
      });

    case types.CLEAR_NOTIFICATIONS:
      return Object.assign({}, state, {
        notifications: {},
      });

    // END OF NOTIFICATION ACTIONS
    case types.GET_NOTIFICATIONS_SUCCESS:
      return Object.assign({}, state, {
        inbox: Object.assign({}, state.inbox, {
          notifications: action.notifications,
          unreadCount: action.unreadCount,
        }),
      });
    case types.NEW_NOTIFICATION:
      return update(state, {
        inbox: {
          notifications: { $unshift: [action.notification] },
          unreadCount: { $set: action.unreadCount },
        },
      });
    case types.REMOVE_NOTIFICATION:
      return update(state, {
        inbox: {
          notifications: {
            $set: state.inbox.notifications.filter(
              item => item.id !== action.notification
            ),
          },
          unreadCount: { $set: action.unreadCount },
        },
      });
    case types.UPDATE_NOTIFICATION:
      return update(state, {
        inbox: {
          notifications: {
            $set: state.inbox.notifications.map(
              item =>
                item.id === action.notification.id
                  ? Object.assign({}, item, action.notification)
                  : item
            ),
          },
          unreadCount: { $set: action.unreadCount },
        },
      });
    case types.SET_PROCESS_STATE_PENDING:
      return Object.assign({}, state, {
        processStatus: 'pending',
      });
    case types.SET_PROCESS_STATE_SAVED:
      return Object.assign({}, state, {
        processStatus: 'saved',
      });

    default:
      return state;
  }
}
