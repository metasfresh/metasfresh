import WFProcessScreen from '../containers/wfProcessScreen/WFProcessScreen';
import { push } from 'connected-react-router';
import { pushHeaderEntry } from '../actions/HeaderActions';

export const gotoWFProcessScreen = ({ wfProcess }) => {
  return (dispatch) => {
    const location = workflowLocation({ workflowId: wfProcess.id });
    dispatch(push(location));
    dispatch(
      pushHeaderEntry({
        location,
        values: wfProcess.headerProperties.entries,
      })
    );
  };
};

const workflowLocation = ({ workflowId }) => `/workflow/${workflowId}`;

export const workflowRoutes = [{ path: workflowLocation({ workflowId: ':workflowId' }), Component: WFProcessScreen }];
