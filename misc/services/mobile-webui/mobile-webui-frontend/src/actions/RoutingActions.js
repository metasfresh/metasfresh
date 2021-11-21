import { push } from 'connected-react-router';

export function redirectToAppLaunchers(appId) {
  return (dispatch) => {
    dispatch(push(`/launchers/${appId}`));
  };
}
