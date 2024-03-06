import * as types from '../constants/ApplicationsActionTypes';

const initialState = {
  availableApplications: {},
};

export const getAvailableApplicationsArray = (state) => {
  const availableApplicationsById = state.applications?.availableApplications;
  return availableApplicationsById ? Object.values(availableApplicationsById) : [];
};

export const getApplicationInfoById = ({ state, applicationId }) => {
  return state.applications?.availableApplications?.[applicationId];
};

export const getApplicationCaptionById = ({ state, applicationId, fallbackCaption }) => {
  if (!applicationId) {
    return fallbackCaption;
  }

  return getApplicationInfoById({ state, applicationId })?.caption ?? fallbackCaption;
};

export default function applications(state = initialState, action) {
  const { payload } = action;
  switch (action.type) {
    case types.POPULATE_APPLICATIONS: {
      const availableApplications = payload.applications.reduce((acc, application) => {
        acc[application.id] = {
          id: application.id,
          caption: application.caption,
          iconClassNames: getIconClassNames(application.id),
        };
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

// TODO: this shall come from the backend
const getIconClassNames = (applicationId) => {
  switch (applicationId) {
    case 'picking':
      return 'fas fa-box-open';
    case 'distribution':
      return 'fas fa-people-carry';
    case 'mfg':
      return 'fas fa-industry';
    case 'huManager':
      return 'fas fa-boxes';
    default:
      return '';
  }
};
