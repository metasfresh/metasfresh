import WFProcessScreen from '../containers/wfProcessScreen/WFProcessScreen';
import { getWFProcessScreenLocation } from './workflow_locations';

export const workflowRoutes = [
  {
    path: getWFProcessScreenLocation({ applicationId: ':applicationId', wfProcessId: ':workflowId' }),
    Component: WFProcessScreen,
  },
];
