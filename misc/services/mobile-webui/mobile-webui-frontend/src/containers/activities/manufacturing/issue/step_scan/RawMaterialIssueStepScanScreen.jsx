import React from 'react';
import { useRouteMatch } from 'react-router-dom';
import RawMaterialIssueStepScanComponent from './RawMaterialIssueStepScanComponent';

const RawMaterialIssueStepScanScreen = () => {
  const {
    params: { workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  return (
    <RawMaterialIssueStepScanComponent
      wfProcessId={wfProcessId}
      activityId={activityId}
      lineId={lineId}
      stepId={stepId}
    />
  );

  // const urlSearchParams = useURLSearchParams();
  // const isBarcodeAlreadyScanned = urlSearchParams.get('isBarcodeAlreadyScanned') === 'true';
  // console.log('!!!!', { urlSearchParams: urlSearchParams.toString(), isBarcodeAlreadyScanned });
  //
  // const {
  //   huQRCode,
  //   uom,
  //   qtyToIssueTarget,
  //   qtyToIssueMax,
  //   lineQtyToIssue,
  //   lineQtyToIssueTolerancePerc,
  //   lineQtyToIssueRemaining,
  //   isWeightable,
  //   isIssueWholeHU,
  //   qtyRejectedReasons,
  //   scaleDevice,
  // } = useSelector((state) => computeStepScanPropsFromState({ state, wfProcessId, activityId, lineId, stepId }));
  //
  // const userInfo = computeStepScanUserInfoQtys({
  //   uom,
  //   lineQtyToIssue,
  //   lineQtyToIssueTolerancePerc,
  //   lineQtyToIssueRemaining,
  // });
  //
  // const dispatch = useDispatch();
  // const history = useHistory();
  // const onResult = ({ qty = 0, qtyRejected = 0, reason = null }) => {
  //   dispatch(
  //     updateManufacturingIssue({
  //       wfProcessId,
  //       activityId,
  //       lineId,
  //       stepId,
  //       huWeightGrossBeforeIssue: isWeightable && isIssueWholeHU ? qty : null,
  //       qtyIssued: qty,
  //       qtyRejected: isIssueWholeHU ? qtyRejected : 0,
  //       qtyRejectedReasonCode: isIssueWholeHU ? reason : null,
  //     })
  //   )
  //     .catch((axiosError) => toastError({ axiosError }))
  //     .finally(() => history.go(-1));
  // };
  //
  // return (
  //   <ScanHUAndGetQtyComponent
  //     isBarcodeAlreadyScanned={isBarcodeAlreadyScanned}
  //     eligibleBarcode={toQRCodeString(huQRCode)}
  //     //
  //     userInfo={userInfo}
  //     qtyTarget={qtyToIssueTarget}
  //     qtyMax={qtyToIssueMax}
  //     uom={uom}
  //     qtyRejectedReasons={qtyRejectedReasons}
  //     scaleDevice={scaleDevice}
  //     //
  //     // Callbacks:
  //     onResult={onResult}
  //   />
  // );
};

export default RawMaterialIssueStepScanScreen;
