import { clearActiveApplication } from '../actions/ApplicationsActions';

/**
 * Navigation middleware that resets the view (or actually the viewHandler state)
 * whenever there's a `push` action to the browser history.
 */
const navigationMiddleware =
  ({ dispatch }) =>
  (next) =>
  (action) => {
    const nextAction = next(action);

    if (
      action.type === '@@router/LOCATION_CHANGE' &&
      action.payload.action === 'POP' &&
      action.payload.location.pathname === '/'
    ) {
      dispatch(clearActiveApplication());
    }

    return nextAction;
  };

export default navigationMiddleware;
