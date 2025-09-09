import React, { useMemo } from 'react';
import PropTypes from 'prop-types';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/buttons/ButtonQuantityProp';
import {
  pickingLineScanScreenLocation,
  pickingLineScreenLocation,
  pickingScanScreenLocation,
} from '../../../routes/picking';
import { getLinesArrayFromActivity, isAnonymousPickHUsOnTheFly } from '../../../reducers/wfProcesses';
import {
  getCurrentPickFromHUQRCode,
  isAllowPickingAnyHUOnHeaderLevel,
  isUserEditable as isUserEditableFunc,
} from '../../../utils/picking';
import { useCurrentPickingTargetInfo } from '../../../reducers/wfProcesses/picking/useCurrentPickTarget';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { NEXT_PickingJob } from './PickLineScanScreen';
import SelectCurrentLUTUButtons from './SelectCurrentLUTUButtons';
import { PICK_ON_THE_FLY_QRCODE } from './PickConfig';
import {
  computeCatchWeightsArrayForLine,
  formatCatchWeightToHumanReadableStr,
} from '../../../reducers/wfProcesses/picking/catch_weight';
import { isCurrentTargetEligibleForLine } from '../../../reducers/wfProcesses/picking/isCurrentTargetEligibleForLine';

export const COMPONENTTYPE_PickProducts = 'picking/pickProducts';

const PickProductsActivity = ({ applicationId, wfProcessId, activityId, activity }) => {
  const history = useMobileNavigation();

  const isUserEditable = isUserEditableFunc({ activity });
  const isAllowPickingAnyHU = isAllowPickingAnyHUOnHeaderLevel({ activity });

  const groupedLines = useMemo(() => {
    const lines = getLinesArrayFromActivity(activity);
    return groupLinesByDisplayKey(lines);
  }, [activity]);

  const onScanButtonClick = () => {
    history.push(pickingScanScreenLocation({ applicationId, wfProcessId, activityId }));
  };
  const onLineButtonClick = useLineButtonClickHandler({ applicationId, wfProcessId, activity, history });

  const { isLineReadOnly } = useIsLineReadonly({ wfProcessId, activityId });

  const isAtLeastOneReadOnlyLine = (groupedLines) => {
    if (!isUserEditable) return false;
    return groupedLines.some((lines) => lines.some((line) => isLineReadOnly({ line })));
  };

  return (
    <div className="mt-5">
      <SelectCurrentLUTUButtons
        applicationId={applicationId}
        wfProcessId={wfProcessId}
        activityId={activityId}
        isUserEditable={isUserEditable}
      />
      <br />

      {isAllowPickingAnyHU && (
        <ButtonWithIndicator
          id="scanQRCode-button"
          captionKey="activities.picking.scanQRCode"
          disabled={isAtLeastOneReadOnlyLine(groupedLines)}
          onClick={onScanButtonClick}
        />
      )}
      {groupedLines &&
        groupedLines.map((group, groupIndex) => {
          const getDisplayLines = (lines) => {
            return lines.map((line, lineIndex) => {
              const lineId = line.pickingLineId;
              const { uom, qtyToPick, qtyPicked } = line;
              const qtyPickedCatchWeight = computeCatchWeightsArrayForLine({ line });
              const qtyPickedCatchWeightStr = formatCatchWeightToHumanReadableStr(qtyPickedCatchWeight);

              return (
                <ButtonWithIndicator
                  id={`line-${groupIndex}-${lineIndex}-button`}
                  testId={`line-${groupIndex}-${lineIndex}-button`}
                  key={lineId}
                  caption={line.caption}
                  completeStatus={line.completeStatus || CompleteStatus.NOT_STARTED}
                  disabled={!isUserEditable || isLineReadOnly({ line })}
                  onClick={() => onLineButtonClick({ line })}
                >
                  <ButtonQuantityProp
                    uom={uom}
                    qtyTarget={qtyToPick}
                    qtyCurrent={qtyPicked}
                    qtyCurrentCatchWeight={qtyPickedCatchWeightStr}
                    applicationId={applicationId}
                  />
                </ButtonWithIndicator>
              );
            });
          };

          return (
            <React.Fragment key={groupIndex}>
              {getDisplayLines(group)}
              {groupIndex !== groupedLines.length - 1 && <br />}
            </React.Fragment>
          );
        })}
    </div>
  );
};

PickProductsActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  activity: PropTypes.object.isRequired,
};

export default PickProductsActivity;

//
//
//
//
//

const useIsLineReadonly = ({ wfProcessId, activityId }) => {
  const { allowedPickToStructures, luPickingTarget, tuPickingTarget } = useCurrentPickingTargetInfo({
    wfProcessId,
    activityId,
  });

  return {
    isLineReadOnly: ({ line }) => {
      return !isCurrentTargetEligibleForLine({ line, luPickingTarget, tuPickingTarget, allowedPickToStructures });
    },
  };
};

//
//
//
//
//

const groupLinesByDisplayKey = (lines) => {
  lines.sort((a, b) => (a?.sortingIndex ?? 0) - (b.sortingIndex ?? 0));
  let currentGroupKey = undefined;
  const groups = [];
  let currentGroup = [];

  for (const line of lines) {
    if (currentGroupKey === undefined) {
      currentGroupKey = line.displayGroupKey;
    }
    if (currentGroupKey !== line.displayGroupKey) {
      groups.push([...currentGroup]);
      currentGroupKey = line.displayGroupKey;
      currentGroup = [line];
    } else {
      currentGroup.push(line);
    }
  }
  groups.push([...currentGroup]);

  return groups;
};

//
//
//
//
//

const useLineButtonClickHandler = ({ applicationId, wfProcessId, activity, history }) => {
  const { activityId } = activity;
  const allowAnonymousPickHUsOnTheFly = isAnonymousPickHUsOnTheFly({ activity });

  return ({ line }) => {
    const { pickingLineId: lineId, qtyPicked } = line;

    const pickFromHUQRCode = getCurrentPickFromHUQRCode({ activity });
    if (qtyPicked <= 0 && pickFromHUQRCode) {
      history.push(
        pickingLineScanScreenLocation({
          applicationId,
          wfProcessId,
          activityId,
          lineId,
          qrCode: pickFromHUQRCode,
          next: NEXT_PickingJob,
        })
      );
    } else if (qtyPicked <= 0 && allowAnonymousPickHUsOnTheFly) {
      history.push(
        pickingLineScanScreenLocation({
          applicationId,
          wfProcessId,
          activityId,
          lineId,
          qrCode: PICK_ON_THE_FLY_QRCODE,
          next: NEXT_PickingJob,
        })
      );
    } else {
      history.push(pickingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
    }
  };
};
