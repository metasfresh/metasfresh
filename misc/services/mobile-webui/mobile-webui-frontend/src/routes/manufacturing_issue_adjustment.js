import { getWFProcessScreenLocation } from './workflow_locations';
import IssueAdjustmentScreen from '../containers/activities/manufacturing/issue_adjustment/IssueAdjustmentScreen';
import IssueAdjustmentLineScreen from '../containers/activities/manufacturing/issue_adjustment/IssueAdjustmentLineScreen';
import IssueAdjustmentScanScreen from '../containers/activities/manufacturing/issue_adjustment/IssueAdjustmentScanScreen';

export const issueAdjustmentScreenLocation = ({ applicationId, wfProcessId, activityId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/issueAdjustment/A/${activityId}`;

export const issueAdjustmentLineScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  issueAdjustmentScreenLocation({ applicationId, wfProcessId, activityId }) + `/L/${lineId}`;

export const issueAdjustmentScanScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  issueAdjustmentLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }) + `/scan`;

export const manufacturingIssueAdjustmentRoutes = [
  {
    path: issueAdjustmentScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
    }),
    Component: IssueAdjustmentScreen,
  },
  {
    path: issueAdjustmentLineScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: IssueAdjustmentLineScreen,
  },
  {
    path: issueAdjustmentScanScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: IssueAdjustmentScanScreen,
  },
];
