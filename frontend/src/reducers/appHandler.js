import update from 'immutability-helper';

import * as types from '../constants/ActionTypes';

export const initialState = {
  connectionErrorType: '',
  notifications: {},
  me: {},
  isLogged: false,
  enableTutorial: false,
  processStatus: 'saved',
  inbox: {
    notifications: [],
    unreadCount: 0,
    pending: false,
  },
  keymap: {},
  hotkeys: {},
  lastBackPage: '',
};

export default function appHandler(state = initialState, action) {
  switch (action.type) {
    case types.CONNECTION_ERROR:
      return {
        ...state,
        connectionErrorType: action.errorType,
      };
    case types.USER_SESSION_INIT:
      return {
        ...state,
        me: action.me,
      };

    case types.USER_SESSION_UPDATE:
      return {
        ...state,
        me: {
          ...state.me,
          ...action.me,
        },
      };

    case types.UPDATE_LAST_BACK_PAGE:
      return {
        ...state,
        lastBackPage: action.lastBackPage,
      };

    case types.SET_LANGUAGES:
      return {
        ...state,
        me: {
          ...state.me,
          language: action.data,
        },
      };

    case types.LOGIN_SUCCESS:
      return {
        ...state,
        isLogged: true,
      };

    case types.LOGOUT_SUCCESS:
      return {
        ...state,
        isLogged: false,
      };

    case types.ENABLE_TUTORIAL:
      return {
        ...state,
        enableTutorial: action.flag,
      };

    // NOTIFICATION ACTIONS
    case types.ADD_NOTIFICATION:
      return {
        ...state,
        notifications: {
          ...state.notifications,
          [action.title]: {
            title: action.title,
            msg: action.msg,
            shortMsg: action.shortMsg,
            time: action.time,
            notifType: action.notifType,
            count: state.notifications[action.title]
              ? state.notifications[action.title].count + 1
              : 1,
            onCancel: action.onCancel || null,
          },
        },
      };

    case types.SET_NOTIFICATION_PROGRESS: {
      const notifications = {
        ...state.notifications,
        [action.key]: {
          ...state.notifications[action.key],
          progress: action.progress,
        },
      };

      return {
        ...state,
        notifications: notifications,
      };
    }

    case types.DELETE_NOTIFICATION:
      return {
        ...state,
        notifications: Object.keys(state.notifications).reduce((res, key) => {
          if (key !== action.key) {
            res[key] = state.notifications[key];
          }
          return res;
        }, {}),
      };

    case types.CLEAR_NOTIFICATIONS:
      if (state.inbox.notifications.length === 0) {
        return state;
      }

      return {
        ...state,
        inbox: {
          notifications: [],
          unreadCount: 0,
          pending: false,
        },
        notifications: {},
      };

    case types.GET_NOTIFICATIONS_REQUEST: {
      return {
        ...state,
        inbox: {
          ...state.inbox,
          pending: true,
        },
      };
    }

    // END OF NOTIFICATION ACTIONS
    case types.GET_NOTIFICATIONS_SUCCESS: {
      return {
        ...state,
        inbox: {
          ...state.inbox,
          notifications: action.notifications,
          unreadCount: action.unreadCount,
          pending: false,
        },
      };
    }

    case types.NEW_NOTIFICATION: {
      return update(state, {
        inbox: {
          notifications: {
            $unshift: [action.notification],
          },
          unreadCount: {
            $set: action.unreadCount,
          },
        },
      });
    }

    case types.REMOVE_NOTIFICATION: {
      return update(state, {
        inbox: {
          notifications: {
            $set: state.inbox.notifications.filter(
              (item) => item.id !== action.notificationId
            ),
          },
          unreadCount: {
            $set: action.unreadCount,
          },
        },
      });
    }

    case types.READ_NOTIFICATION: {
      return update(state, {
        inbox: {
          notifications: {
            $set: state.inbox.notifications.map((item) =>
              item.id === action.notificationId ? { ...item, read: true } : item
            ),
          },
          unreadCount: {
            $set: action.unreadCount,
          },
        },
      });
    }

    case types.READ_ALL_NOTIFICATIONS: {
      return update(state, {
        inbox: {
          notifications: {
            $set: state.inbox.notifications.map((item) => ({
              ...item,
              read: true,
            })),
          },
          unreadCount: {
            $set: 0,
          },
        },
      });
    }

    case types.REMOVE_ALL_NOTIFICATIONS: {
      return update(state, {
        inbox: {
          notifications: {
            $set: [],
          },
          unreadCount: {
            $set: 0,
          },
        },
      });
    }

    case types.SET_PROCESS_STATE_PENDING:
      return {
        ...state,
        processStatus: 'pending',
      };

    case types.SET_PROCESS_STATE_SAVED:
      return {
        ...state,
        processStatus: 'saved',
      };

    case types.INIT_KEYMAP:
      return {
        ...state,
        keymap: action.payload,
      };
    case types.UPDATE_KEYMAP:
      return {
        ...state,
        keymap: {
          ...state.keymap,
          ...action.payload,
        },
      };
    case types.INIT_HOTKEYS:
      return {
        ...state,
        hotkeys: action.payload,
      };
    case types.UPDATE_HOTKEYS:
      return {
        ...state,
        hotkeys: {
          ...state.hotkeys,
          ...action.payload,
        },
      };

    default:
      return state;
  }
}
