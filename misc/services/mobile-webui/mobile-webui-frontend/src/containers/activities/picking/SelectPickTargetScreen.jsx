import React from 'react';
import { setPickTarget, usePickTargets } from '../../../api/picking';
import { useHistory, useRouteMatch } from 'react-router-dom';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDispatch } from 'react-redux';
import Spinner from '../../../components/Spinner';

export const SelectPickTargetScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const {
    params: { workflowId: wfProcessId },
  } = useRouteMatch();

  const { isTargetsLoading, targets } = usePickTargets({ wfProcessId });

  const onSelectTargetClicked = async (target) => {
    setPickTarget({ wfProcessId, target })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .then(() => history.go(-1)); // go back to Picking Job
  };

  return (
    <div className="section pt-2">
      {isTargetsLoading && <Spinner />}
      {targets &&
        targets.map((target, index) => {
          return (
            <ButtonWithIndicator key={index} caption={target.caption} onClick={() => onSelectTargetClicked(target)} />
          );
        })}
    </div>
  );
};
