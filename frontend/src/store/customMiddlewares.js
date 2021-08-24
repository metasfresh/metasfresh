import { setBreadcrumb } from '../actions/MenuActions';
import { deleteView } from '../actions/ViewActions';

/**
 * Navigation middleware that resets the view (or actually the viewHandler state)
 * whenever there's a `push` action to the browser history.
 */
// TODO: For this to work properly we'd need to somehow get
// the windowId/viewId/docId/*tabId
const navigationMiddleware =
  ({ dispatch }) =>
  (next) =>
  (action) => {
    const nextAction = next(action);

    if (
      action.type === '@@router/LOCATION_CHANGE' &&
      action.payload.action === 'POP' &&
      action.payload.pathname === '/'
    ) {
      // make sure we clear the breadcrumbs once we are on the dashboard
      dispatch(setBreadcrumb([]));
    }

    if (
      action.type === '@@router/LOCATION_CHANGE' &&
      action.payload.action === 'PUSH'
    ) {
      dispatch(deleteView());
    }

    return nextAction;
  };

export default navigationMiddleware;
