import React, { useCallback, useEffect, useRef } from 'react';
import { closePickingTarget, setTUPickingTargetFromGrai, useAvailablePickingTargets } from '../../../api/picking';
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
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { parseGraiFromRawInput } from '../../../utils/grai';
import { toastError } from '../../../utils/toast';

const GRAI_DEBOUNCE_MILLIS = 1500;

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

  const { isTargetsLoading, targets, graiScanEnabled, setPickingTarget } = useAvailablePickingTargets({
    wfProcessId,
    lineId,
    type,
  });

  const onSelectTargetClicked = async (target) => {
    setPickingTarget({ target })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .then(() => history.goBack()); // go back to Picking Job
  };

  const onGraiScanned = useGraiScanner({ wfProcessId, lineId, dispatch, history });

  return (
    <>
      {graiScanEnabled && <GraiScanPanel onGraiScanned={onGraiScanned} />}
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

/**
 * Hook that returns an `onGraiScanned(rawString)` callback implementing the
 * debounce + exactly-one-distinct logic required by REQUIREMENTS §3.
 *
 * Accumulates distinct parsed GRAIs for GRAI_DEBOUNCE_MILLIS after the last
 * scan.  When the timer fires:
 *   - exactly one distinct GRAI → POST to the pick-target endpoint and navigate back
 *   - two or more distinct GRAIs → toast error (i18n key: activities.picking.graiScan.multipleScanned), no list
 * Identical repeated scans are deduplicated (count as one distinct value).
 */
const useGraiScanner = ({ wfProcessId, lineId, dispatch, history }) => {
  const pendingGraisRef = useRef(new Set());
  const debounceTimerRef = useRef(null);

  const fireDebounced = useCallback(() => {
    const distinctGrais = [...pendingGraisRef.current];
    pendingGraisRef.current = new Set();
    debounceTimerRef.current = null;

    if (distinctGrais.length === 0) return;

    if (distinctGrais.length >= 2) {
      toastError({ plainMessage: trl('activities.picking.graiScan.multipleScanned') });
      return;
    }

    // Exactly one distinct GRAI
    const graiString = distinctGrais[0];
    setTUPickingTargetFromGrai({ wfProcessId, lineId, graiString })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .then(() => history.goBack())
      .catch((axiosError) => toastError({ axiosError }));
  }, [wfProcessId, lineId, dispatch, history]);

  const fireDebouncedRef = useRef(fireDebounced);
  useEffect(() => {
    fireDebouncedRef.current = fireDebounced;
  }, [fireDebounced]);

  const onGraiScanned = useCallback((rawString) => {
    const grai = parseGraiFromRawInput(rawString);
    if (!grai) return; // unparseable scans are ignored at this level; the scanner component handles beep/error

    pendingGraisRef.current.add(grai);

    if (debounceTimerRef.current) {
      clearTimeout(debounceTimerRef.current);
    }
    debounceTimerRef.current = setTimeout(() => fireDebouncedRef.current(), GRAI_DEBOUNCE_MILLIS);
  }, []);

  useEffect(() => {
    return () => {
      if (debounceTimerRef.current) {
        clearTimeout(debounceTimerRef.current);
      }
    };
  }, []);

  return onGraiScanned;
};

//
//
//--------------------------------------------------------------------------
//
//

/**
 * Renders the live GRAI scanner (camera + RFID-as-keyboard).
 * No tap required — the scanner runs continuously from mount.
 */
const GraiScanPanel = ({ onGraiScanned }) => {
  const onResolvedResult = useCallback(
    (resolvedResult) => {
      onGraiScanned(resolvedResult.scannedBarcode);
    },
    [onGraiScanned]
  );

  return (
    <BarcodeScannerComponent onResolvedResult={onResolvedResult} continuousRunning={true} testId="grai-scan-input" />
  );
};

GraiScanPanel.propTypes = {
  onGraiScanned: PropTypes.func.isRequired,
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
