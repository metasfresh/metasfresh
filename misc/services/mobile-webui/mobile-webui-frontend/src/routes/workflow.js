import WFProcessScreen from '../containers/wfProcessScreen/WFProcessScreen';
import { push } from 'connected-react-router';

export const getWFProcessScreenLocation = ({ wfProcessId }) => `/workflow/${wfProcessId}`;

export const gotoWFProcessScreen = ({ wfProcess }) => {
  return (dispatch) => {
    const location = getWFProcessScreenLocation({ wfProcessId: wfProcess.id });

    dispatch(push(location));
  };
};

export const workflowRoutes = [
  {
    path: getWFProcessScreenLocation({ wfProcessId: ':workflowId' }),
    Component: WFProcessScreen,
  },
];
