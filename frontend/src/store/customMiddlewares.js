import { deleteView } from '../actions/ViewActions';

/**
 * Navigation middleware that resets the view (or actually the viewHandler state)
 * whenever there's a `push` action to the browser history.
 */
const navigationMiddleware = ({ dispatch }) => (next) => (action) => {
  const nextAction = next(action);

  if (
    action.type === '@@router/LOCATION_CHANGE' &&
    action.payload.action === 'PUSH'
  ) {
    dispatch(deleteView());
  }

  return nextAction;
};

export default navigationMiddleware;
