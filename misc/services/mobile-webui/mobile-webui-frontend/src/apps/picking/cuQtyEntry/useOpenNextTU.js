import { useCallback, useRef, useState } from 'react';
import { useDispatch } from 'react-redux';

import { closePickingTarget, setTUPickingTargetFromGrai } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { PickingTargetType } from '../../../constants/PickingTargetType';
import { selectPickingTargetScreenLocation } from '../../../routes/picking';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { UserMessageError } from '../../../utils/UserMessageError';
import { toastErrorFromObj, toastNotification } from '../../../utils/toast';
import { trl } from '../../../utils/translations';

/** Result of a booking that was not sent (the TU it waited for could not be opened); see ScanHUAndGetQtyComponent `isNotBooked` */
export const NOT_BOOKED = Object.freeze({ isNotBooked: true });

const hasTUPackingInstructions = (packingTuPIId) => packingTuPIId != null; // null and absent alike

/** Closes the open TU target (if any), then opens a new TU of the line's TU packing, or goes to the target selection when the line has no TU packing. */
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

/** Wires the two ways of opening a TU (button, GRAI scan) to the picking target APIs of the line */
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
  // the TU opening in flight (button or GRAI scan), so a booking can wait for it (see bookAfterTUOpened)
  const openingRef = useRef(null);

  const isTargetOpen = tuPickingTarget != null;
  const updateWF = (wfProcess) => dispatch(updateWFProcess({ wfProcess }));

  const closeOpenTUDeps = {
    isTargetOpen,
    closeTarget: () =>
      closePickingTarget({ wfProcessId, lineId, type: PickingTargetType.TU }).then((wfProcess) => updateWF(wfProcess)),
  };

  const runExclusive = useCallback(async (action, { isRethrow = false } = {}) => {
    if (isInFlightRef.current) {
      // a scan must not report success for a TU that was not opened.
      if (isRethrow) throw new UserMessageError('picking.nextTUBusy');
      return;
    }
    isInFlightRef.current = true;
    setProcessing(true);
    const opening = action();
    openingRef.current = opening;
    try {
      await opening;
    } catch (error) {
      if (isRethrow) throw error;
      toastErrorFromObj(error);
    } finally {
      openingRef.current = null;
      isInFlightRef.current = false;
      setProcessing(false);
    }
  }, []);

  /**
   * Books once the TU opening in flight (if any) is done, so the count goes into the TU the picker just opened.
   * A booking sent during the opening would land in the previous TU, and the opening's later answer would overwrite the booking's newer job state on screen.
   * When the opening fails, nothing is booked and the result is {@link NOT_BOOKED}: the opening already showed its error (one error toast),
   * and the quantity block keeps the typed count for the TU that stays open.
   */
  const bookAfterTUOpened = useCallback(
    (book) =>
      openingRef.current
        ? openingRef.current.then(
            () => book(),
            () => NOT_BOOKED
          )
        : book(),
    []
  );

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
        await setTUPickingTargetFromGrai({ wfProcessId, lineId, graiString }).then((wfProcess) => updateWF(wfProcess));
        toastNotification({ plainMessage: trl('picking.nextTUOpened', { caption: graiString }) });
      },
      { isRethrow: true }
    );

  return { isProcessing, onNextTU, onGraiScanned, bookAfterTUOpened };
};
