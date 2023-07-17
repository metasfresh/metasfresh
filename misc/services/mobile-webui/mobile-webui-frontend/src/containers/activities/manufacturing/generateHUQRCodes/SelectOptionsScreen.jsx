import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { confirmOptionLocation } from '../../../../routes/generateHUQRCodes';
import { getOptionsFromActivity } from './utils';
import Button from '../../../../components/buttons/Button';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { getActivityById } from '../../../../reducers/wfProcesses';

const SelectOptionsScreen = () => {
  const {
    url,
    params: { applicationId, wfProcessId, activityId },
  } = useRouteMatch();

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
    dispatch(pushHeaderEntry({ location: url, caption: activityCaption, userInstructions }));
  }, []);

  const history = useHistory();
  const onOptionButtonClicked = (optionIndex) => {
    history.push(confirmOptionLocation({ applicationId, wfProcessId, activityId, optionIndex }));
  };

  return (
    <div className="section pt-2">
      {options.map((optionItem, optionIndex) => (
        <Button key={optionIndex} caption={optionItem.caption} onClick={() => onOptionButtonClicked(optionIndex)} />
      ))}
    </div>
  );
};

export default SelectOptionsScreen;
