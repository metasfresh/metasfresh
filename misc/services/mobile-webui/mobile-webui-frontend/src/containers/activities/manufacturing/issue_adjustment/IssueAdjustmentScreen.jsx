import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { getActivityById } from '../../../../reducers/wfProcesses';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import { issueAdjustmentLineScreenLocation } from '../../../../routes/manufacturing_issue_adjustment';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';

const IssueAdjustmentScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId },
  } = useRouteMatch();

  const { caption, userInstructions, lines } = useSelector((state) =>
    getPropsFromState({ state, wfProcessId, activityId })
  );

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(pushHeaderEntry({ location: url, caption, userInstructions }));
  }, []);

  const history = useHistory();
  const onButtonClick = (lineId) => {
    history.push(issueAdjustmentLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  return (
    <div className="section pt-2">
      {lines && lines.length > 0
        ? lines.map((lineItem, lineIndex) => {
            const lineId = '' + lineIndex;

            return (
              <ButtonWithIndicator key={lineId} caption={lineItem.productName} onClick={() => onButtonClick(lineId)} />
            );
          })
        : null}
    </div>
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const rawMaterialsIssueActivityId = activity.dataStored.rawMaterialsIssueActivityId;

  const rawMaterialsIssueActivity = getActivityById(state, wfProcessId, rawMaterialsIssueActivityId);
  //console.log('getPropsFromState activity', { rawMaterialsIssueActivity });
  //const line = getLineByIdFromActivity(activity, lineId);
  return {
    caption: activity.caption,
    userInstructions: activity.userInstructions,
    lines: rawMaterialsIssueActivity.dataStored.lines,
  };
};

export default IssueAdjustmentScreen;
