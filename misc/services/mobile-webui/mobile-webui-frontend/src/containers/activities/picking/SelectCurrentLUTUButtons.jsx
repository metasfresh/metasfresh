import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { trl } from '../../../utils/translations';
import React, { useState } from 'react';
import { useCurrentPickingTargetInfo } from '../../../reducers/wfProcesses/picking/useCurrentPickTarget';
import PropTypes from 'prop-types';
import { reopenClosedLUScreenLocation, selectPickingTargetScreenLocation } from '../../../routes/picking';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import {
  PICKTO_STRUCTURE_CU,
  PICKTO_STRUCTURE_LU_CU,
  PICKTO_STRUCTURE_LU_TU,
  PICKTO_STRUCTURE_TU,
} from '../../../reducers/wfProcesses/picking/PickToStructure';
import { useHasClosedHUs } from './useClosedHUs';
import { useDispatch } from 'react-redux';
import { advisePickingTarget } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { toastError } from '../../../utils/toast';

const SelectCurrentLUTUButtons = ({ applicationId, wfProcessId, activityId, lineId, isUserEditable = true }) => {
  const history = useMobileNavigation();
  const dispatch = useDispatch();
  const [isAdvising, setIsAdvising] = useState(false);

  const {
    luPickingTarget,
    tuPickingTarget,
    lineCarrierAdviseAvailable,
    lineCarrierAdviseReadOnly,
    lineCarrierProductCaption,
    jobCarrierAdviseAvailable,
    jobCarrierAdviseReadOnly,
    jobCarrierProductCaption,
    allowedPickToStructures,
    isAllowReopeningLU,
  } = useCurrentPickingTargetInfo({ wfProcessId, activityId, lineId });

  const { hasClosedLUs } = useHasClosedHUs({ wfProcessId, lineId });

  const isShowReopenLUButton = isAllowReopeningLU && hasClosedLUs;

  const { isShowLUButton, isShowTUButton } = computeIsShowButtons({
    luPickingTarget,
    tuPickingTarget,
    allowedPickToStructures,
  });

  const onReopenClosedLUClicked = () => {
    history.push(reopenClosedLUScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  const onSelectLUPickingTargetClick = () => {
    history.push(selectPickingTargetScreenLocation({ applicationId, wfProcessId, activityId, lineId, type: 'lu' }));
  };

  const onSelectTUPickingTargetClick = () => {
    history.push(selectPickingTargetScreenLocation({ applicationId, wfProcessId, activityId, lineId, type: 'tu' }));
  };

  const onAdviseCarrierClick = () => {
    setIsAdvising(true);
    advisePickingTarget({ wfProcessId, lineId })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setIsAdvising(false));
  };

  // Carrier-advise source, in precedence: the LU/TU pick target → the line (line-level pick) →
  // the job (header-level CU-direct: no target and no line in scope).
  const carrierAdviseTarget = luPickingTarget ?? tuPickingTarget;
  const isCarrierAdviseAvailable = carrierAdviseTarget
    ? carrierAdviseTarget.carrierAdviseAvailable === true
    : lineCarrierAdviseAvailable === true || jobCarrierAdviseAvailable === true;
  const isCarrierAdviseReadOnly = carrierAdviseTarget
    ? carrierAdviseTarget.carrierAdviseReadOnly === true
    : lineCarrierAdviseAvailable
    ? lineCarrierAdviseReadOnly === true
    : jobCarrierAdviseReadOnly === true;
  const carrierProductCaption = carrierAdviseTarget
    ? carrierAdviseTarget.carrierProductCaption
    : lineCarrierProductCaption ?? jobCarrierProductCaption;

  return (
    <>
      {isShowReopenLUButton && (
        <ButtonWithIndicator
          testId="reopenLU-button"
          captionKey="activities.picking.reopenLU"
          disabled={!isUserEditable}
          onClick={onReopenClosedLUClicked}
        />
      )}
      {isShowLUButton && (
        <ButtonWithIndicator
          testId="targetLU-button"
          caption={
            luPickingTarget?.caption
              ? trl('activities.picking.pickingTarget.Current') + ': ' + luPickingTarget?.caption
              : trl('activities.picking.pickingTarget.New')
          }
          disabled={!isUserEditable}
          onClick={onSelectLUPickingTargetClick}
        />
      )}
      {isShowTUButton && (
        <ButtonWithIndicator
          testId="targetTU-button"
          caption={
            tuPickingTarget?.caption
              ? trl('activities.picking.tuPickingTarget.Current') + ': ' + tuPickingTarget?.caption
              : trl('activities.picking.tuPickingTarget.New')
          }
          disabled={!isUserEditable}
          onClick={onSelectTUPickingTargetClick}
        />
      )}
      {isCarrierAdviseAvailable && (
        <ButtonWithIndicator
          testId="advise-carrier-button"
          captionKey="activities.picking.adviseCarrier"
          disabled={!isUserEditable || isAdvising || isCarrierAdviseReadOnly}
          onClick={onAdviseCarrierClick}
        >
          {carrierProductCaption && (
            <div className="row">
              <span data-testid="carrier-product-caption">
                {trl('activities.picking.carrierProduct') + ': ' + carrierProductCaption}
              </span>
            </div>
          )}
        </ButtonWithIndicator>
      )}
    </>
  );
};
SelectCurrentLUTUButtons.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string,
  isUserEditable: PropTypes.bool,
};
export default SelectCurrentLUTUButtons;

//
//
//
//
//

const computeIsShowButtons = ({ luPickingTarget, tuPickingTarget, allowedPickToStructures }) => {
  let isShowLUButton = false;
  let isHideLUButton = false;
  let isShowTUButton = false;
  let isHideTUButton = false;

  allowedPickToStructures.forEach((pickToStructure) => {
    if (pickToStructure === PICKTO_STRUCTURE_LU_TU) {
      isShowLUButton = true;
      isShowTUButton = true;
    } else if (pickToStructure === PICKTO_STRUCTURE_TU) {
      isShowTUButton = true;
      if (tuPickingTarget && !luPickingTarget) {
        isHideLUButton = true;
      }
    } else if (pickToStructure === PICKTO_STRUCTURE_LU_CU) {
      isShowLUButton = true;
    } else if (pickToStructure === PICKTO_STRUCTURE_CU) {
      // don't show LU nor TU buttons
    }
  });

  return {
    isShowLUButton: isShowLUButton && !isHideLUButton,
    isShowTUButton: isShowTUButton && !isHideTUButton,
  };
};
