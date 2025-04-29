import * as types from '../constants/ApplicationsActionTypes';
import { shallowEqual, useSelector } from 'react-redux';
import { APPLICATION_ID_Manufacturing } from '../apps/manufacturing/constants';
import { useMobileLocation } from '../hooks/useMobileLocation';
import { APPLICATION_ID_Picking } from '../apps/picking';

const initialState = {
  availableApplications: {},
};

export const getAvailableApplicationsArray = (state) => {
  const availableApplicationsById = state.applications?.availableApplications;
  return availableApplicationsById ? Object.values(availableApplicationsById) : [];
};

const getApplicationInfoById = ({ state, applicationId }) => {
  return state.applications?.availableApplications?.[applicationId] ?? {};
};

export const useApplicationInfo = ({ applicationId }) => {
  const { applicationId: pathApplicationId } = useMobileLocation();
  const applicationIdEffective = applicationId ? applicationId : pathApplicationId;

  return useSelector((state) => getApplicationInfoById({ state, applicationId: applicationIdEffective }), shallowEqual);
};

export const useApplicationInfoParameters = ({ applicationId }) => {
  return useSelector(
    (state) => getApplicationInfoById({ state, applicationId })?.applicationParameters ?? {},
    shallowEqual
  );
};

export default function applications(state = initialState, action) {
  const { payload } = action;
  switch (action.type) {
    case types.POPULATE_APPLICATIONS: {
      const availableApplications = payload.applications.reduce((acc, application) => {
        acc[application.id] = {
          ...application,
          iconClassNames: getIconClassNames(application.id),
        };
        return acc;
      }, {});

      return { ...state, availableApplications };
    }
    default:
      return state;
  }
}

// TODO: this shall come from the backend
const getIconClassNames = (applicationId) => {
  switch (applicationId) {
    case APPLICATION_ID_Picking:
      return 'fas fa-box-open';
    case 'distribution':
      return 'fas fa-people-carry';
    case APPLICATION_ID_Manufacturing:
      return 'fas fa-industry';
    case 'huManager':
      return 'fas fa-boxes';
    case 'workplaceManager':
      return 'fas fa-location';
    case 'scanAnything':
      return 'fas fa-qrcode';
    default:
      return '';
  }
};
