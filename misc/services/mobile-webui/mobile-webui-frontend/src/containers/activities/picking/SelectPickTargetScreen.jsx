import React, { useEffect } from 'react';
import { closePickTarget, setPickTarget, usePickTargets } from '../../../api/picking';
import { useHistory, useRouteMatch } from 'react-router-dom';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDispatch } from 'react-redux';
import Spinner from '../../../components/Spinner';
import { useCurrentPickTarget } from '../../../reducers/wfProcesses/picking/useCurrentPickTarget';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { trl } from '../../../utils/translations';
import PropTypes from 'prop-types';

export const SelectPickTargetScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const {
    url,
    params: { workflowId: wfProcessId, activityId },
  } = useRouteMatch();

  const currentTarget = useCurrentPickTarget({ wfProcessId, activityId });

  useHeaderUpdate({ url, currentTarget });

  const onCloseTargetClicked = async () => {
    closePickTarget({ wfProcessId })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .then(() => history.go(-1)); // go back to Picking Job
  };

  return (
    <div className="section pt-2">
      {currentTarget && (
        <ButtonWithIndicator
          caption={trl('activities.picking.pickingTarget.CloseTarget')}
          onClick={onCloseTargetClicked}
        />
      )}
      {!currentTarget && <NewTargets wfProcessId={wfProcessId} />}
    </div>
  );
};

//
//
//--------------------------------------------------------------------------
//
//

const NewTargets = ({ wfProcessId }) => {
  const dispatch = useDispatch();
  const history = useHistory();

  const { isTargetsLoading, targets } = usePickTargets({ wfProcessId });

  const onSelectTargetClicked = async (target) => {
    setPickTarget({ wfProcessId, target })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .then(() => history.go(-1)); // go back to Picking Job
  };

  return (
    <>
      {isTargetsLoading && <Spinner />}
      {targets &&
        targets.map((target, index) => {
          return (
            <ButtonWithIndicator key={index} caption={target.caption} onClick={() => onSelectTargetClicked(target)} />
          );
        })}
    </>
  );
};
NewTargets.propTypes = {
  wfProcessId: PropTypes.string,
};

//
//
//--------------------------------------------------------------------------
//
//

const useHeaderUpdate = ({ url, currentTarget }) => {
  const dispatch = useDispatch();

  const currentTargetCaption = currentTarget?.caption;

  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.picking.pickingTarget.Select'),
        values: [
          {
            caption: trl('activities.picking.pickingTarget.Current'),
            value: currentTargetCaption,
            hidden: !currentTargetCaption,
          },
        ],
      })
    );
  }, [url, currentTargetCaption]);
};
