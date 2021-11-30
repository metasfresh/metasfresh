import WFProcessScreen from '../containers/wfProcessScreen/WFProcessScreen';
import { push } from 'connected-react-router';

import { getLocation } from '../utils';

export const gotoWFProcessScreen = ({ wfProcess }) => {
  return (dispatch) => {
    const location = getLocation({ wfProcessId: wfProcess.id });

    dispatch(push(location));
  };
};

export const workflowRoutes = [{ path: getLocation({ wfProcessId: ':workflowId' }), Component: WFProcessScreen }];
