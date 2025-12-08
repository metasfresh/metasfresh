import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getActivityById } from '../../../../reducers/wfProcesses';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import { issueAdjustmentLineScreenLocation } from '../../../../routes/manufacturing_issue_adjustment';
import { updateHeaderEntry } from '../../../../actions/HeaderActions';
import { useScreenDefinition } from '../../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../../routes/workflow_locations';

const IssueAdjustmentScreen = () => {
  const { history, url, applicationId, wfProcessId, activityId } = useScreenDefinition({
    back: getWFProcessScreenLocation,
  });

  const { caption, userInstructions, lines } = useSelector((state) =>
    getPropsFromState({ state, wfProcessId, activityId })
  );

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(updateHeaderEntry({ location: url, caption, userInstructions }));
  }, []);

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
