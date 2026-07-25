import React from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';

import * as CompleteStatus from '../../../constants/CompleteStatus';

import DistributionLineButton from './DistributionLineButton';
import { getLinesArrayFromActivity } from '../../../reducers/wfProcesses';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ConfirmButton from '../../../components/buttons/ConfirmButton';
import { trl } from '../../../utils/translations';
import {
  distributionDropAllToScreenLocation,
  distributionJobsListScreenLocation,
  distributionPickFromScreenLocation,
} from '../../../routes/distribution';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { postDistributionSwitchPickFromLocatorThunk } from '../../../apps/distribution/redux/postDistributionSwitchPickFromLocatorThunk';
import { completeDistributionJobGivingUpRemainder } from '../../../api/distribution';
import { toastError } from '../../../utils/toast';

const DistributionMoveActivity = ({ applicationId, wfProcessId, activityId, activityState }) => {
  const history = useMobileNavigation();
  const dispatch = useDispatch();
  const lines = getLinesArrayFromActivity(activityState);
  const {
    dataStored: { isUserEditable, hasLinesInTransit, canSwitchPickFromLocator, qtyOutstanding },
  } = activityState;

  const onScannedCode = ({ scannedBarcode: huQRCode }) => {
    history.goTo(distributionPickFromScreenLocation({ applicationId, wfProcessId, activityId, huQRCode }));
  };

  const onDropAllToLocator = () => {
    history.push(
      distributionDropAllToScreenLocation({
        applicationId,
        wfProcessId,
        activityId,
      })
    );
  };

  const onSwitchPickFromLocator = () => {
    dispatch(postDistributionSwitchPickFromLocatorThunk({ wfProcessId })).catch((axiosError) => {
      toastError({ axiosError });
    });
  };

  const onCompleteGivingUpRemainder = () => {
    completeDistributionJobGivingUpRemainder({ wfProcessId })
      .then(() => history.push(distributionJobsListScreenLocation()))
      .catch((axiosError) => {
        toastError({ axiosError });
      });
  };

  return (
    <div className="mt-5">
      <BarcodeScannerComponent invisible onResolvedResult={onScannedCode} />
      {lines.map((line, lineIdx) => {
        const lineId = line.lineId;
        return (
          <DistributionLineButton
            key={lineId}
            testId={`line-${lineIdx + 1}-button`}
            applicationId={applicationId}
            wfProcessId={wfProcessId}
            activityId={activityId}
            lineId={lineId}
            caption={line.caption}
            isUserEditable={isUserEditable}
            completeStatus={line.completeStatus ?? CompleteStatus.NOT_STARTED}
            uom={line.uom}
            qtyToMove={line.qtyToMove}
            qtyPicked={line.qtyPicked}
          />
        );
      })}
      {canSwitchPickFromLocator && (
        <ButtonWithIndicator
          testId="switchPickFromLocator-button"
          data-pickfromlocator={lines[0]?.pickFromLocator?.id}
          caption={trl('activities.distribution.switchPickFromLocator')}
          disabled={!isUserEditable}
          onClick={onSwitchPickFromLocator}
        />
      )}
      <ButtonWithIndicator
        testId="scanDropToLocator-button"
        caption={trl('activities.distribution.scanDropToLocator')}
        disabled={!isUserEditable || !hasLinesInTransit}
        onClick={onDropAllToLocator}
      />
      {/* Only while something is actually outstanding — Complete then refuses, and this is the way out.
          The prompt names the very quantity that is abandoned, in the same wording as that refusal. */}
      {qtyOutstanding && (
        <ConfirmButton
          id="complete-giving-up-remainder-button"
          caption={trl('activities.distribution.completeGivingUpRemainder.caption')}
          promptQuestion={trl('activities.distribution.completeGivingUpRemainder.question', { qtyOutstanding })}
          isDangerousAction={true}
          isUserEditable={isUserEditable}
          onUserConfirmed={onCompleteGivingUpRemainder}
        />
      )}
    </div>
  );
};

DistributionMoveActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string,
  activityId: PropTypes.string,
  caption: PropTypes.string,
  componentProps: PropTypes.object,
  activityState: PropTypes.object,
};

export default DistributionMoveActivity;
