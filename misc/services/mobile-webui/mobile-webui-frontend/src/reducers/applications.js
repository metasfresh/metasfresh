import * as types from '../constants/ApplicationsActionTypes';

const initialState = {
  availableApplications: {},
  activeApplication: null,
};

export const getApplicationCaptionById = ({ state, applicationId, fallbackCaption }) => {
  if (!applicationId) {
    return fallbackCaption;
  }

  return state.applications?.availableApplications?.[applicationId]?.caption ?? fallbackCaption;
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
      const availableApplications = payload.applications.reduce((acc, application) => {
        acc[application.id] = application;
        return acc;
      }, {});

      return {
        ...state,
        availableApplications,
      };
    }
    default:
      return state;
  }
}
