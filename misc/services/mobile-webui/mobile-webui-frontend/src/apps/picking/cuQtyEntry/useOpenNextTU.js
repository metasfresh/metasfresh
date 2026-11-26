import { useCallback, useRef, useState } from 'react';
import { useDispatch } from 'react-redux';

import { closePickingTarget, PICKING_TARGET_TIMEOUT_MILLIS, setTUPickingTargetFromGrai } from '../../../api/picking';
import { getWFProcess } from '../../../api/launchers';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { PickingTargetType } from '../../../constants/PickingTargetType';
import { selectPickingTargetScreenLocation } from '../../../routes/picking';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { UserMessageError } from '../../../utils/UserMessageError';
import { toastErrorFromObj, toastNotification } from '../../../utils/toast';
import { trl } from '../../../utils/translations';

/**
 * Resolved value of a booking that was not sent because the TU it waited for could not be opened (see {@link useOpenNextTU} `bookAfterTUOpened`).
 * It is never an error: the opening already showed its own error, so the booking host (`onResult` of ScanHUAndGetQtyComponent)
 * must resolve to this marker to tell the quantity block "nothing was booked, keep the typed count".
 * The quantity block recognizes it by `isNotBooked`; any other resolved value means the booking was sent.
 */
export const NOT_BOOKED = Object.freeze({ isNotBooked: true });

/** An axios request that ran into its timeout (see PICKING_TARGET_TIMEOUT_MILLIS in api/picking.js) or the timeout of a wait for a booking */
const isTimeout = (error) => error?.code === 'ECONNABORTED' || error?.code === 'ETIMEDOUT';

/** The promise, or a rejection with a timeout error after `millis` (the same bound as the request timeout of the opening itself) */
const withTimeout = (promise, millis) => {
  let timer;
  const timeout = new Promise((resolve, reject) => {
    timer = setTimeout(
      () => reject(Object.assign(new UserMessageError('error.network.noResponse'), { code: 'ETIMEDOUT' })),
      millis
    );
  });
  return Promise.race([promise, timeout]).finally(() => clearTimeout(timer));
};

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
  // the booking in flight (settles when its answer is in; never rejects), so an opening waits for it (see runExclusive)
  const bookingRef = useRef(null);

  const isTargetOpen = tuPickingTarget != null;
  const updateWF = (wfProcess) => dispatch(updateWFProcess({ wfProcess }));

  const closeOpenTUDeps = {
    isTargetOpen,
    closeTarget: () =>
      closePickingTarget({ wfProcessId, lineId, type: PickingTargetType.TU }).then((wfProcess) => updateWF(wfProcess)),
  };

  // best effort: the timeout error is shown anyway; a failed reload only leaves the stale state, which the next load refreshes
  const refreshJob = useCallback(
    () =>
      getWFProcess({ wfProcessId })
        .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
        .catch(() => undefined),
    [dispatch, wfProcessId]
  );

  /**
   * Runs one TU opening at a time; a second call while one is in flight is refused (`isRethrow`: with a UserMessageError, so a scanner reports an error instead of a success; otherwise silently).
   * While the opening is in flight, its promise is kept in `openingRef` for {@link bookAfterTUOpened}, which needs the raw promise of `action` (it settles when the opening settles).
   * An opening requested while a booking is in flight (see {@link bookAfterTUOpened}) starts only after the booking's answer is in:
   * the count confirmed first stays in the TU that was open, and the booking's older job state cannot arrive after the opening's newer one and show the previous TU as open.
   * The wait for the booking has the same bound as the opening's own request (`PICKING_TARGET_TIMEOUT_MILLIS`): a booking without an answer
   * (the booking request itself has no timeout) ends the wait with a "Connection error", the opening is not started and the busy flags are released.
   * An opening that timed out may have been applied by the server (close + open are not idempotent), so the job is reloaded and shown: the screen then shows the TU that is really open.
   * The in-flight flags are released whatever happens: `action` may return a rejected promise or throw synchronously.
   * A failure of the opening is shown as an error toast and swallowed; with `isRethrow` it is rethrown to the caller instead (a scanner shows it) and no toast is shown here.
   * @param {() => Promise<void>|void} action the opening
   * @param {{isRethrow?: boolean}} options
   * @returns {Promise<void>} settles when the opening settled
   */
  const runExclusive = useCallback(
    async (action, { isRethrow = false } = {}) => {
      if (isInFlightRef.current) {
        // a scan must not report success for a TU that was not opened.
        if (isRethrow) throw new UserMessageError('picking.nextTUBusy');
        return;
      }
      isInFlightRef.current = true;
      setProcessing(true);
      try {
        const opening = bookingRef.current
          ? withTimeout(bookingRef.current, PICKING_TARGET_TIMEOUT_MILLIS).then(() => action())
          : action();
        openingRef.current = opening;
        await opening;
      } catch (error) {
        if (isTimeout(error)) {
          refreshJob();
        }
        if (isRethrow) throw error;
        toastErrorFromObj(error);
      } finally {
        openingRef.current = null;
        isInFlightRef.current = false;
        setProcessing(false);
      }
    },
    [refreshJob]
  );

  /**
   * Books once the TU opening in flight (if any) is done, so the count goes into the TU the picker just opened.
   * A booking sent during the opening would land in the previous TU, and the opening's later answer would overwrite the booking's newer job state on screen.
   * When the opening fails, nothing is booked and the result is {@link NOT_BOOKED}: the opening already showed its error (one error toast),
   * and the quantity block keeps the typed count for the TU that stays open.
   * An opening that hangs ends with the request timeout of the picking target APIs (`PICKING_TARGET_TIMEOUT_MILLIS` in api/picking.js): its error is shown, nothing is booked, the typed count stays.
   * The booking is remembered while its answer is pending, so a TU opening requested meanwhile waits for it (see runExclusive).
   * @param {() => any} book sends the booking; called at once when no opening is in flight
   * @returns {any} when an opening is in flight: a promise of what `book` returns, or of {@link NOT_BOOKED}; otherwise what `book()` returns itself
   * (a promise, or a synchronous throw of `book`: the caller handles both)
   */
  const bookAfterTUOpened = useCallback((book) => {
    // keeps the booking's promise for runExclusive; the caller gets the booking itself back
    const trackBooking = () => {
      const booking = book();
      if (booking?.then) {
        const settled = booking
          .then(
            () => undefined,
            () => undefined
          )
          .then(() => {
            if (bookingRef.current === settled) bookingRef.current = null;
          });
        bookingRef.current = settled;
      }
      return booking;
    };
    return openingRef.current
      ? openingRef.current.then(
          () => trackBooking(),
          () => NOT_BOOKED
        )
      : trackBooking();
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
        toastNotification({ plainMessage: trl('picking.nextTUOpened') });
      }
    });

  const onGraiScanned = (graiString) =>
    runExclusive(
      async () => {
        await setTUPickingTargetFromGrai({ wfProcessId, lineId, graiString }).then((wfProcess) => updateWF(wfProcess));
        toastNotification({ plainMessage: trl('picking.nextTUOpened') });
      },
      { isRethrow: true }
    );

  // runExclusive: @VisibleForTesting
  return { isProcessing, onNextTU, onGraiScanned, bookAfterTUOpened, runExclusive };
};
