import React, { useCallback, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';

import DialogButton from './dialogs/DialogButton';
import { closePickingTarget, setTUPickingTargetFromGrai } from '../api/picking';
import { updateWFProcess } from '../actions/WorkflowActions';
import { PickingTargetType } from '../constants/PickingTargetType';
import { selectPickingTargetScreenLocation } from '../routes/picking';
import { useMobileNavigation } from '../hooks/useMobileNavigation';
import { toastErrorFromObj, toastNotification } from '../utils/toast';
import { trl } from '../utils/translations';

/**
 * The "next box" button of a CU quantity entry mode: one tap, shown beside the quantity block's Abbrechen / the scan
 * loop's Schließen, and under the scan field before the first booking.
 */
const NextBoxActions = ({ isProcessing = false, onNextBox }) => (
  <DialogButton
    captionKey="picking.nextBox"
    testId="next-box-button"
    className="is-link"
    disabled={isProcessing}
    onClick={onNextBox}
  />
);

NextBoxActions.propTypes = {
  isProcessing: PropTypes.bool,
  onNextBox: PropTypes.func.isRequired,
};

export default NextBoxActions;

//
//
// -------------------------------------------------------------------------------------
//
//

const hasTUPackingInstructions = (packingTuPIId) => packingTuPIId != null; // null and absent alike

/**
 * "Weitere Kiste": closes the open box (if any), then opens a new one of the line's TU type; a line whose packing
 * names no TU type gets today's box list instead.
 */
// @VisibleForTesting
export const openNextBox = async ({
  packingTuPIId,
  packingItemName,
  isTargetOpen,
  closeTarget,
  setTarget,
  gotoSelectTarget,
}) => {
  if (isTargetOpen) {
    await closeTarget();
  }

  if (hasTUPackingInstructions(packingTuPIId)) {
    await setTarget({ target: { tuPIId: packingTuPIId, caption: packingItemName } });
  } else {
    gotoSelectTarget();
  }
};

/** A scanned GRAI: closes the open box (if any), then opens the new box with that GRAI. */
// @VisibleForTesting
export const openBoxFromGrai = async ({ graiString, isTargetOpen, closeTarget, setTargetFromGrai }) => {
  if (isTargetOpen) {
    await closeTarget();
  }
  await setTargetFromGrai({ graiString });
};

/**
 * Wires the two ways of opening a box (button, GRAI scan) to the picking target APIs of the line.
 *
 * @param setPickingTarget `setPickingTarget` of `useAvailablePickingTargets({type: TU})`
 * @param tuPickingTarget the box currently open on the line, if any
 */
export const useNextBox = ({
  applicationId,
  wfProcessId,
  activityId,
  lineId,
  packingTuPIId,
  packingItemName,
  tuPickingTarget,
  setPickingTarget,
}) => {
  const dispatch = useDispatch();
  const history = useMobileNavigation();
  const [isProcessing, setProcessing] = useState(false);
  // synchronous twin of isProcessing: a second tap before the next render must not open a second box
  const isInFlightRef = useRef(false);

  const isTargetOpen = tuPickingTarget != null;
  const updateWF = (wfProcess) => dispatch(updateWFProcess({ wfProcess }));

  const common = {
    isTargetOpen,
    closeTarget: () =>
      closePickingTarget({ wfProcessId, lineId, type: PickingTargetType.TU }).then((wfProcess) => updateWF(wfProcess)),
  };

  const runExclusive = useCallback(async (action) => {
    if (isInFlightRef.current) {
      return;
    }
    isInFlightRef.current = true;
    setProcessing(true);
    try {
      await action();
    } catch (error) {
      toastErrorFromObj(error);
    } finally {
      isInFlightRef.current = false;
      setProcessing(false);
    }
  }, []);

  const onNextBox = () =>
    runExclusive(async () => {
      await openNextBox({
        ...common,
        packingTuPIId,
        packingItemName,
        setTarget: ({ target }) => setPickingTarget({ target }).then((wfProcess) => updateWF(wfProcess)),
        gotoSelectTarget: () =>
          history.push(
            selectPickingTargetScreenLocation({
              applicationId,
              wfProcessId,
              activityId,
              lineId,
              type: PickingTargetType.TU,
            })
          ),
      });
      if (hasTUPackingInstructions(packingTuPIId)) {
        toastNotification({ plainMessage: trl('picking.nextBoxOpened', { caption: packingItemName }) });
      }
    });

  // not exclusive-guarded here: the scanner's own flow awaits it and reports its errors
  const onGraiScanned = async (graiString) => {
    await openBoxFromGrai({
      ...common,
      graiString,
      setTargetFromGrai: ({ graiString }) =>
        setTUPickingTargetFromGrai({ wfProcessId, lineId, graiString }).then((wfProcess) => updateWF(wfProcess)),
    });
    toastNotification({ plainMessage: trl('picking.nextBoxOpened', { caption: graiString }) });
  };

  return { isProcessing, onNextBox, onGraiScanned };
};
