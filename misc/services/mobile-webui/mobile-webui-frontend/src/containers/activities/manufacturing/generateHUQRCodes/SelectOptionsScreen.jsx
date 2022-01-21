import React from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { confirmOptionLocation } from '../../../../routes/generateHUQRCodes';
import { getOptions } from './utils';
import Button from '../../../../components/buttons/Button';

const SelectOptionsScreen = () => {
  const {
    params: { applicationId, wfProcessId, activityId },
  } = useRouteMatch();
  const options = useSelector((state) => getOptions({ state, wfProcessId, activityId }));

  const history = useHistory();
  const onOptionButtonClicked = (optionIndex) => {
    history.push(confirmOptionLocation({ applicationId, wfProcessId, activityId, optionIndex }));
  };

  return (
    <div className="pt-2 section">
      {options.map((optionItem, optionIndex) => (
        <Button key={optionIndex} caption={optionItem.caption} onClick={() => onOptionButtonClicked(optionIndex)} />
      ))}
    </div>
  );
};

export default SelectOptionsScreen;
