import React, { useEffect } from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { useMobileLocation } from '../../../hooks/useMobileLocation';
import { useWFActivity } from '../../../reducers/wfProcesses';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { trl } from '../../../utils/translations';
import { huConsolidationJobLocation } from '../routes';
import { isGRAIScanEnabled as isGRAIScanEnabledFunc, isHUConsolidationJobLoaded } from '../reducers';
import { useConsolidateByScan } from '../actions/useConsolidateByScan';

/**
 * Scans the GRAI of each crate of the job, no matter which picking slot it is in; every scan moves the crate onto the
 * current target. The screen stays open after a scan; Back returns to the job screen.
 * When the backend no longer enables the GRAI scan for the job, the screen returns to the job screen by itself.
 */
export const GRAIScanScreen = () => {
  const { wfProcessId, activityId } = useMobileLocation();
  const activity = useWFActivity({ wfProcessId, activityId });
  const isGRAIScanEnabled = isGRAIScanEnabledFunc({ activity });
  const consolidateByScan = useConsolidateByScan({ wfProcessId });
  const { history } = useScreenDefinition({
    screenId: 'GRAIScanScreen',
    captionKey: 'huConsolidation.HUConsolidationActivity.scanGRAI',
    back: huConsolidationJobLocation,
  });

  // scan no longer enabled -> back to the job screen (not before the job is loaded, e.g. after a reload of this screen)
  const isJobLoaded = isHUConsolidationJobLoaded({ activity });
  useEffect(() => {
    if (isJobLoaded && !isGRAIScanEnabled) {
      history.goBack();
    }
  }, [isJobLoaded, isGRAIScanEnabled]);

  const onGraiScanned = ({ scannedBarcode }) => consolidateByScan({ scannedCode: scannedBarcode });

  return (
    <div className="section pt-2">
      <BarcodeScannerComponent
        testId="grai-scanner"
        onResolvedResult={onGraiScanned}
        inputPlaceholderText={trl('huConsolidation.HUConsolidationActivity.scanGRAI')}
      />
    </div>
  );
};
