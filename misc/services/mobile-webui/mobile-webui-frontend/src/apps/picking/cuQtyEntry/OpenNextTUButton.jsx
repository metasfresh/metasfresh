import React, { useCallback, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';

import DialogButton from '../../../components/dialogs/DialogButton';
import { closePickingTarget, setTUPickingTargetFromGrai } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { PickingTargetType } from '../../../constants/PickingTargetType';
import { selectPickingTargetScreenLocation } from '../../../routes/picking';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { UserMessageError } from '../../../utils/UserMessageError';
import { toastErrorFromObj, toastNotification } from '../../../utils/toast';
import { trl } from '../../../utils/translations';

/**
 * Button "Next TU" (picking, lines with a CU quantity entry mode that books into TUs).
 *
 * What it provides: one tap closes the line's open TU (if any) and opens the next TU of the line's TU type (TU), or leads to the
 * TU list when the line's packing names no TU type. Booking a CU needs an open TU, so without this button the picker would have to
 * leave the quantity screen to start the next TU.
 *
 * Why it is here: the screens that show it (the scan step and the quantity block of the picking line) only render it; the logic lives
 * next to it in {@link useOpenNextTU}, which the screens call and which also opens a TU for a scanned GRAI.
 *
 * When to use it: on a picking line whose `cuQtyEntryPolicy.labelRequired` is true. It is a plain button, it never submits the quantity form.
 */
const OpenNextTUButton = ({ isProcessing = false, onNextTU }) => (
  <DialogButton
    captionKey="picking.nextTU"
    testId="next-tu-button"
    className="is-link"
    type="button"
    disabled={isProcessing}
    onClick={onNextTU}
  />
);

OpenNextTUButton.propTypes = {
  isProcessing: PropTypes.bool,
  onNextTU: PropTypes.func.isRequired,
};

export default OpenNextTUButton;

const hasTUPackingInstructions = (packingTuPIId) => packingTuPIId != null; // null and absent alike

/**
 * "Weitere Kiste": closes the open TU (if any), then opens a new one of the line's TU type; a line whose packing
 * names no TU type gets the TU list instead.
 */
// @VisibleForTesting
export const closeAndOpenNextTU = async ({
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
 * Wires the two ways of opening a TU (button, GRAI scan) to the picking target APIs of the line.
 *
 * @param setPickingTarget `setPickingTarget` of `useAvailablePickingTargets({type: TU})`
 * @param tuPickingTarget the TU open on the line, if any
 */
export const useOpenNextTU = ({
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
  // synchronous twin of isProcessing: a second tap before the next render must not open a second TU
  const isInFlightRef = useRef(false);

  const isTargetOpen = tuPickingTarget != null;
  const updateWF = (wfProcess) => dispatch(updateWFProcess({ wfProcess }));

  const closeOpenTUDeps = {
    isTargetOpen,
    closeTarget: () =>
      closePickingTarget({ wfProcessId, lineId, type: PickingTargetType.TU }).then((wfProcess) => updateWF(wfProcess)),
  };

  // a tap and a GRAI scan share the guard: two overlapping close + open runs would leave an extra empty TU.
  // `isRethrow`: the scanner's own flow reports the error, so it gets it back instead of a toast.
  const runExclusive = useCallback(async (action, { isRethrow = false } = {}) => {
    if (isInFlightRef.current) {
      // a scan must not report success for a TU that was not opened.
      if (isRethrow) throw new UserMessageError('picking.nextTUBusy');
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

  const onNextTU = () =>
    runExclusive(async () => {
      await closeAndOpenNextTU({
        ...closeOpenTUDeps,
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
        toastNotification({ plainMessage: trl('picking.nextTUOpened', { caption: packingItemName }) });
      }
    });

  const onGraiScanned = (graiString) =>
    runExclusive(
      async () => {
        // The open TU is NOT closed first: the backend replaces the line's TU target in one step,
        // so a GRAI it refuses leaves the open TU as it was.
        await setTUPickingTargetFromGrai({ wfProcessId, lineId, graiString }).then((wfProcess) => updateWF(wfProcess));
        toastNotification({ plainMessage: trl('picking.nextTUOpened', { caption: graiString }) });
      },
      { isRethrow: true }
    );

  return { isProcessing, onNextTU, onGraiScanned };
};
