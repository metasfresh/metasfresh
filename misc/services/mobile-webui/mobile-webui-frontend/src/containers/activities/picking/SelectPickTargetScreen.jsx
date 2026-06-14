import React, { useEffect } from 'react';
import { closePickingTarget, useAvailablePickingTargets } from '../../../api/picking';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDispatch } from 'react-redux';
import Spinner from '../../../components/Spinner';
import { useCurrentPickingTargetInfo } from '../../../reducers/wfProcesses/picking/useCurrentPickTarget';
import { updateHeaderEntry } from '../../../actions/HeaderActions';
import { trl } from '../../../utils/translations';
import PropTypes from 'prop-types';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { PickingTargetType } from '../../../constants/PickingTargetType';
import { pickingJobOrLineLocation } from '../../../routes/picking';

export const SelectPickTargetScreen = () => {
  const { history, url, wfProcessId, activityId, lineId, type } = useScreenDefinition({
    screenId: 'SelectPickTargetScreen',
    back: pickingJobOrLineLocation,
  });

  const { currentTarget, closePickingTarget } = useCurrentTarget({ wfProcessId, activityId, lineId, type });

  useHeaderUpdate({ url, currentTarget });

  const onCloseTargetClicked = async () => {
    closePickingTarget().then(() => history.goBack());
  };

  return (
    <div className="section pt-2">
      {currentTarget && (
        <ButtonWithIndicator captionKey="activities.picking.pickingTarget.CloseTarget" onClick={onCloseTargetClicked} />
      )}
      {!currentTarget && <NewTargets wfProcessId={wfProcessId} lineId={lineId} type={type} />}
    </div>
  );
};

//
//
//--------------------------------------------------------------------------
//
//

const NewTargets = ({ wfProcessId, lineId, type }) => {
  const dispatch = useDispatch();
  const history = useMobileNavigation();

  const { isTargetsLoading, targets, setPickingTarget } = useAvailablePickingTargets({ wfProcessId, lineId, type });

  const onSelectTargetClicked = async (target) => {
    setPickingTarget({ target })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .then(() => history.goBack()); // go back to Picking Job
  };

  return (
    <>
      {isTargetsLoading && <Spinner />}
      {targets?.map((target, index) => {
        return (
          <ButtonWithIndicator
            key={index}
            caption={target.caption}
            onClick={() => onSelectTargetClicked(target)}
            additionalCssClass={target.default ? 'green-border-button' : undefined}
          />
        );
      })}
    </>
  );
};
NewTargets.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  lineId: PropTypes.string,
  type: PropTypes.string.isRequired,
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
      updateHeaderEntry({
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

//
//
//--------------------------------------------------------------------------
//
//

const useCurrentTarget = ({ wfProcessId, activityId, lineId, type }) => {
  const dispatch = useDispatch();
  const { luPickingTarget, tuPickingTarget } = useCurrentPickingTargetInfo({ wfProcessId, activityId, lineId });

  return {
    currentTarget: type === PickingTargetType.TU ? tuPickingTarget : luPickingTarget,
    closePickingTarget: () => {
      return closePickingTarget({ wfProcessId, lineId, type }).then((wfProcess) =>
        dispatch(updateWFProcess({ wfProcess }))
      );
    },
  };
};
