import * as types from '../constants/ApplicationsActionTypes';

const initialState = {
  apps: {},
  activeApplication: null,
};

export default function applications(state = initialState, action) {
  const { payload } = action;
  switch (action.type) {
    case types.SET_ACTIVE_APPLICATION:
      return {
        ...state,
        activeApplication: { ...payload },
      };
    case types.CLEAR_ACTIVE_APPLICATION:
      return {
        ...state,
        activeApplication: null,
      };
    case types.POPULATE_APPLICATIONS: {
      const apps = payload.applications.reduce((acc, application) => {
        acc[application.id] = application.caption;

        return acc;
      }, {});

      return {
        ...state,
        apps,
      };
    }
    default:
      return state;
  }
}
