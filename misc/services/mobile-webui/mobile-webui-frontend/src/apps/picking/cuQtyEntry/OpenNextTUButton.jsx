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

/** Button "Next TU" (picking, lines with a CU quantity entry mode that books into TUs) */
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
        await setTUPickingTargetFromGrai({ wfProcessId, lineId, graiString }).then((wfProcess) => updateWF(wfProcess));
        toastNotification({ plainMessage: trl('picking.nextTUOpened', { caption: graiString }) });
      },
      { isRethrow: true }
    );

  return { isProcessing, onNextTU, onGraiScanned };
};
