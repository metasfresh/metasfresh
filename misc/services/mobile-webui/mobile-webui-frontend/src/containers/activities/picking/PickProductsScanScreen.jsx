import React, { useEffect } from 'react';
import { useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { trl } from '../../../utils/translations';
import { parseQRCodeString } from '../../../utils/qrCode/hu';
import { getActivityById, getQtyRejectedReasonsFromActivity } from '../../../reducers/wfProcesses';
import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { isShowBestBeforeDate, isShowLotNo } from './PickConfig';
import { convertQRCodeObjectToResolvedResult, usePostQtyPicked } from './PickLineScanScreen';
import {
  getLinesByProductId,
  getQtyToPickRemainingForLine,
  isAllowPickingAnyHUForLine,
  isLineNotCompleted,
} from '../../../utils/picking';

const PickProductsScanScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId },
  } = useRouteMatch();

  const { activity } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId }), shallowEqual);

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.picking.scanQRCode'),
        values: [],
      })
    );
  }, []);

  const resolveScannedBarcode = (scannedBarcode) => {
    const qrCode = parseQRCodeString(scannedBarcode);
    const line = getEligibleLineByProductId({ activity, productId: qrCode.productId });
    const qtyToPickRemaining = getQtyToPickRemainingForLine({ line });
    // console.log('resolveScannedBarcode', { qrCode, line, qtyToPickRemaining });

    return {
      qtyRejectedReasons: getQtyRejectedReasonsFromActivity(activity),
      lineId: line.pickingLineId,
      productId: line.productId,
      uom: line.uom,
      catchWeightUom: line.catchWeightUOM,
      qtyTarget: qtyToPickRemaining,
      qtyMax: qtyToPickRemaining,
      ...convertQRCodeObjectToResolvedResult(qrCode),
    };
  };

  const onResult = usePostQtyPicked({ wfProcessId, activityId });

  const onClose = () => {
    history.go(-1); // go to picking job screen
  };

  return (
    <ScanHUAndGetQtyComponent
      qtyCaption={trl('general.QtyToPick')}
      isShowBestBeforeDate={isShowBestBeforeDate}
      isShowLotNo={isShowLotNo}
      resolveScannedBarcode={resolveScannedBarcode}
      onResult={onResult}
      onClose={onClose}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  return { activity };
};

const getEligibleLineByProductId = ({ activity, productId }) => {
  const eligibleLines = getLinesByProductId(activity, productId).filter(
    (line) => isLineNotCompleted({ line }) && isAllowPickingAnyHUForLine({ line })
  );

  // console.log('getEligibleLineByProductId', { eligibleLines });

  if (!eligibleLines?.length) {
    throw 'No matching lines found'; // TODO trl
  } else {
    return eligibleLines[0];
  }
};

export default PickProductsScanScreen;
