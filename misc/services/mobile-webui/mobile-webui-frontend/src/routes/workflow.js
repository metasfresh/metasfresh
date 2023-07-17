import WFProcessScreen from '../containers/wfProcessScreen/WFProcessScreen';
import { push } from 'connected-react-router';
import { getWFProcessScreenLocation } from './workflow_locations';

export const gotoWFProcessScreen = ({ applicationId, wfProcessId }) => {
  return (dispatch) => {
    const location = getWFProcessScreenLocation({ applicationId, wfProcessId });

    dispatch(push(location));
  };
};

export const workflowRoutes = [
  {
    path: getWFProcessScreenLocation({ applicationId: ':applicationId', wfProcessId: ':workflowId' }),
    Component: WFProcessScreen,
  },
];
