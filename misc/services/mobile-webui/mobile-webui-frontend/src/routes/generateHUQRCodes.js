import SelectOptionsScreen from '../containers/activities/manufacturing/generateHUQRCodes/SelectOptionsScreen';
import { getWFProcessScreenLocation } from './workflow_locations';
import ConfirmOptionScreen from '../containers/activities/manufacturing/generateHUQRCodes/ConfirmOptionScreen';

export const selectOptionsLocation = ({ applicationId, wfProcessId, activityId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/generateHUQRCodes/A/${activityId}`;

export const confirmOptionLocation = ({ applicationId, wfProcessId, activityId, optionIndex }) =>
  selectOptionsLocation({ applicationId, wfProcessId, activityId }) + `/confirm/${optionIndex}`;

export const generateHUQRCodesRoutes = [
  {
    path: selectOptionsLocation({
      applicationId: ':applicationId',
      wfProcessId: ':wfProcessId',
      activityId: ':activityId',
    }),
    Component: SelectOptionsScreen,
  },
  {
    path: confirmOptionLocation({
      applicationId: ':applicationId',
      wfProcessId: ':wfProcessId',
      activityId: ':activityId',
      optionIndex: ':optionIndex',
    }),
    Component: ConfirmOptionScreen,
  },
];
