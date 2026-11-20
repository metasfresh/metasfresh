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
 * The "next box" button of a CU quantity entry mode: one tap, shown in the quantity block's button row (after
 * Abbrechen) and under the scan field on the scan step. A plain button: it never submits the quantity form.
 */
const NextBoxActions = ({ isProcessing = false, onNextBox }) => (
  <DialogButton
    captionKey="picking.nextBox"
    testId="next-box-button"
    className="is-link"
    type="button"
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

  const closeOpenBoxDeps = {
    isTargetOpen,
    closeTarget: () =>
      closePickingTarget({ wfProcessId, lineId, type: PickingTargetType.TU }).then((wfProcess) => updateWF(wfProcess)),
  };

  // a tap and a GRAI scan share the guard: two overlapping close + open runs would leave an extra empty box.
  // `isRethrow`: the scanner's own flow reports the error, so it gets it back instead of a toast.
  const runExclusive = useCallback(async (action, { isRethrow = false } = {}) => {
    if (isInFlightRef.current) {
      // a scan must not report success for a box that was not opened
      if (isRethrow) throw new Error(trl('picking.nextBoxBusy'));
      return;
    }
    isInFlightRef.current = true;
    setProcessing(true);
    try {
      await action();
    } catch (error) {
      if (isRethrow) throw error;
      toastErrorFromObj(error);
    } finally {
      isInFlightRef.current = false;
      setProcessing(false);
    }
  }, []);

  const onNextBox = () =>
    runExclusive(async () => {
      await openNextBox({
        ...closeOpenBoxDeps,
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

  const onGraiScanned = (graiString) =>
    runExclusive(
      async () => {
        // The open box is NOT closed first: the backend replaces the line's box target in one step,
        // so a GRAI it refuses leaves the open box as it was.
        await setTUPickingTargetFromGrai({ wfProcessId, lineId, graiString }).then((wfProcess) => updateWF(wfProcess));
        toastNotification({ plainMessage: trl('picking.nextBoxOpened', { caption: graiString }) });
      },
      { isRethrow: true }
    );

  return { isProcessing, onNextBox, onGraiScanned };
};
