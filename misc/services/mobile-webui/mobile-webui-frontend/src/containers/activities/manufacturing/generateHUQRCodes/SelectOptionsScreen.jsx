import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { confirmOptionLocation } from '../../../../routes/generateHUQRCodes';
import { getOptionsFromActivity } from './utils';
import Button from '../../../../components/buttons/Button';
import { updateHeaderEntry } from '../../../../actions/HeaderActions';
import { getActivityById } from '../../../../reducers/wfProcesses';
import { useScreenDefinition } from '../../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../../routes/workflow_locations';

const SelectOptionsScreen = () => {
  const { history, url, applicationId, wfProcessId, activityId } = useScreenDefinition({
    screenId: 'GenerateHUQRCodesScreen',
    back: getWFProcessScreenLocation,
  });

  const { activityCaption, userInstructions, options } = useSelector((state) => {
    const activity = getActivityById(state, wfProcessId, activityId);
    return {
      activityCaption: activity.caption,
      userInstructions: activity.userInstructions,
      options: getOptionsFromActivity(activity),
    };
  });

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(updateHeaderEntry({ location: url, caption: activityCaption, userInstructions }));
  }, []);

  const onOptionButtonClicked = (optionIndex) => {
    history.push(confirmOptionLocation({ applicationId, wfProcessId, activityId, optionIndex }));
  };

  return (
    <div className="section pt-2">
      {options.map((optionItem, optionIndex) => (
        <Button
          key={optionIndex}
          caption={optionItem.caption}
          onClick={() => onOptionButtonClicked(optionIndex)}
          testId={optionItem.testId}
        />
      ))}
    </div>
  );
};

export default SelectOptionsScreen;
