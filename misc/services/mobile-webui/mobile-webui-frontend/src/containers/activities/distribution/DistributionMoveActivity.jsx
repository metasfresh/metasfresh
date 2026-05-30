import React from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';

import * as CompleteStatus from '../../../constants/CompleteStatus';

import DistributionLineButton from './DistributionLineButton';
import { getLinesArrayFromActivity } from '../../../reducers/wfProcesses';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { trl } from '../../../utils/translations';
import { distributionDropAllToScreenLocation, distributionPickFromScreenLocation } from '../../../routes/distribution';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { postDistributionSwitchPickFromLocatorThunk } from '../../../apps/distribution/redux/postDistributionSwitchPickFromLocatorThunk';
import { toastError } from '../../../utils/toast';

const DistributionMoveActivity = ({ applicationId, wfProcessId, activityId, activityState }) => {
  const history = useMobileNavigation();
  const dispatch = useDispatch();
  const lines = getLinesArrayFromActivity(activityState);
  const {
    dataStored: { isUserEditable, hasLinesInTransit, canSwitchPickFromLocator },
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

  return (
    <div className="mt-5">
      <BarcodeScannerComponent isShowInputText={false} isShowVideo={false} onResolvedResult={onScannedCode} />
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
