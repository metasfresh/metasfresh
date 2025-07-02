import { SET_SCANNED_BARCODE } from '../constants/ScanActionTypes';
import { getScannedBarcodeSuggestions, postScannedBarcode } from '../api/scanner';
import { updateWFProcess } from './WorkflowActions';
import { fireWFActivityCompleted } from '../apps';
import { useQuery } from '../hooks/useQuery';
import { toastError } from '../utils/toast';

export const setScannedBarcode = ({
  applicationId,
  wfProcessId,
  activityId,
  scannedBarcode,
  history,
  onWFActivityCompleted,
}) => {
  return async (dispatch) => {
    dispatch(updateScannedBarcodeState({ wfProcessId, activityId, scannedBarcode }));

    try {
      const wfProcess = await postScannedBarcode({ wfProcessId, activityId, scannedBarcode });

      dispatch(updateWFProcess({ wfProcess }));

      dispatch(
        fireWFActivityCompleted({
          applicationId,
          wfProcessId,
          activityId,
          history,
          defaultAction: onWFActivityCompleted,
        })
      );
    } catch (error) {
      dispatch(updateScannedBarcodeState({ wfProcessId, activityId, scannedBarcode: null }));

      toastError({
        axiosError: error,
        fallbackMessageKey: 'error.qrCode.invalid',
        context: { wfProcessId, activityId, scannedBarcode },
      });
    }
  };
};

/**
 * @summary update the scannedCode for a `common/scanBarcode` activity type with the scannedBarcode
 */
const updateScannedBarcodeState = ({ wfProcessId, activityId, scannedBarcode }) => {
  return {
    type: SET_SCANNED_BARCODE,
    payload: { wfProcessId, activityId, scannedBarcode },
  };
};

export const useScannedBarcodeSuggestions = ({ wfProcessId, activityId, enabled }) => {
  const { isPending: isScannedBarcodeSuggestionsLoading, data: scannedBarcodeSuggestions } = useQuery({
    queryKey: [wfProcessId, activityId],
    queryFn: () => getScannedBarcodeSuggestions({ wfProcessId, activityId }),
    enabled,
  });

  return { isScannedBarcodeSuggestionsLoading, scannedBarcodeSuggestions };
};
