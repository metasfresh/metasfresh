import { find } from 'lodash';
import {
  POPULATE_APPLICATIONS,
  SET_ACTIVE_APPLICATION,
  CLEAR_ACTIVE_APPLICATION,
} from '../constants/ApplicationsActionTypes';

/**
 * @method populateApplications
 * @summary populate the applications branch in the redux store
 */
export function populateApplications({ applications }) {
  return {
    type: POPULATE_APPLICATIONS,
    payload: { applications },
  };
}

/**
 * @method setActiveApplication
 * @summary set the current active application
 */
export function setActiveApplication({ id, caption }) {
  return (dispatch, getState) => {
    if (!caption) {
      const state = getState();

      find(state.applications, (item) => {
        if (item.id === id) {
          caption = item.caption;

          return item;
        }
      });
    }

    dispatch({
      type: SET_ACTIVE_APPLICATION,
      payload: { id, caption },
    });
  };
}

/**
 * @method clearActiveApplication
 * @summary celar the current active application
 */
export function clearActiveApplication() {
  return {
    type: CLEAR_ACTIVE_APPLICATION,
  };
}
