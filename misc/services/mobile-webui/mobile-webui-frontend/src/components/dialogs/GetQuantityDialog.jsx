import React, { useCallback, useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import { trl } from '../../utils/translations';

import QtyInputField from '../QtyInputField';
import QtyReasonsRadioGroup from '../QtyReasonsRadioGroup';
import DateInput from '../DateInput';
import * as ws from '../../utils/websocket';
import { qtyInfos } from '../../utils/qtyInfos';
import { formatQtyToHumanReadableStr } from '../../utils/qtys';
import { useBooleanSetting } from '../../reducers/settings';
import BarcodeScannerComponent from '../BarcodeScannerComponent';
import { parseQRCodeString, QRCODE_ERROR_CODE_INVALID_BEST_BEFORE_DATE } from '../../utils/qrCode/hu';
import { toastErrorFromObj } from '../../utils/toast';
import { doFinally } from '../../utils';
import YesNoDialog from './YesNoDialog';
import DialogButton from './DialogButton';
import Dialog from './Dialog';
import * as uiTrace from './../../utils/ui_trace';
import Spinner from '../Spinner';
import { QTY_REJECTED_REASON_TO_IGNORE_KEY } from '../../reducers/wfProcesses';
import { PickAttribute } from '../../reducers/wfProcesses/picking/PickAttribute';

const parseQRCodeForReview = ({ string, customQRCodeFormats }) => {
  try {
    return parseQRCodeString({ string, customQRCodeFormats });
  } catch (error) {
    console.log('Unparseable QR code', { string, error });
    const isBestBeforeDateError = error?.context?.errorCode === QRCODE_ERROR_CODE_INVALID_BEST_BEFORE_DATE;
    throw {
      messageKey: isBestBeforeDateError
        ? 'activities.picking.qrcode.invalidBestBeforeDate'
        : 'activities.picking.qrcode.invalidQRCode',
    };
  }
};

const isRealCalendarDate = (isoDate) => {
  const [year, month, day] = `${isoDate}`.split('-').map(Number);
  const date = new Date(Date.UTC(year, month - 1, day));
  return date.getUTCFullYear() === year && date.getUTCMonth() === month - 1 && date.getUTCDate() === day;
};

// A code that is used to receive must carry a lot and a valid best-before date: otherwise the pre-filled
// defaults would stay in the fields and be received as if they had been scanned.
const assertLotNoAndBestBeforeDateScanned = (qrCode) => {
  if (!qrCode.bestBeforeDate) {
    throw { messageKey: 'activities.picking.qrcode.missingBestBeforeDate' };
  }
  if (!isRealCalendarDate(qrCode.bestBeforeDate)) {
    throw { messageKey: 'activities.picking.qrcode.invalidBestBeforeDate' };
  }
  if (!qrCode.lotNo) {
    throw { messageKey: 'activities.picking.qrcode.missingLotNo' };
  }
};

// Catch weight with a mandatory scan is received one at a time: one receipt = one unit carrying the one scanned weight.
const QTY_SCAN_FOR_REVIEW = 1;

const GetQuantityDialog = ({
  readOnly: readOnlyParam = false,
  hideQtyInput = false,
  //
  userInfo,
  qtyTarget,
  qtyTargetCaption,
  totalQty,
  qtyAlreadyOnScale,
  qtyCaption,
  qtyInitial,
  packingItemName,
  uom,
  qtyRejectedReasons,
  scaleDevice,
  scaleTolerance,
  //
  catchWeight: catchWeightParam,
  catchWeightUom,
  customQRCodeFormats,
  isCatchWeightScanMandatory = false,
  //
  readAttributes = [],
  bestBeforeDate: bestBeforeDateParam = '',
  lotNo: lotNoParam = '',
  isShowCloseTargetButton = false,
  //
  validateQtyEntered,
  getConfirmationPromptForQty,
  onQtyChange,
  onCloseDialog,
}) => {
  const isShowBestBeforeDate = readAttributes.includes(PickAttribute.BestBeforeDate);
  const isShowLotNo = readAttributes.includes(PickAttribute.LotNo);

  const [isProcessing, setProcessing] = useState(false);
  const [confirmationDialogProps, setConfirmationDialogProps] = useState({
    promptQuestion: '',
    onQtyChangePayload: undefined,
  });

  const allowManualInput = useBooleanSetting('qtyInput.AllowManualInputWhenScaleDeviceExists');
  const doNotValidateQty = useBooleanSetting('qtyInput.DoNotValidate');
  const useZeroAsInitialValue = useBooleanSetting('qtyInput.useZeroAsInitialValue');

  const useCatchWeight = !scaleDevice && catchWeightUom;
  // Mandatory scan: the operator cannot leave the scanner without a scan, and the scan only fills the fields for review.
  const isScanForReview = isCatchWeightScanMandatory && !!useCatchWeight;

  const [qtyInfo, setQtyInfo] = useState(
    qtyInfos.invalidOfNumber(
      isScanForReview ? QTY_SCAN_FOR_REVIEW : (useZeroAsInitialValue ? 0 : null) ?? qtyInitial ?? qtyTarget
    )
  );
  const [rejectedReason, setRejectedReason] = useState(computeDefaultQtyRejectedReason(qtyRejectedReasons));
  const [useScaleDevice, setUseScaleDevice] = useState(!!scaleDevice);

  const [catchWeight, setCatchWeight] = useState(
    qtyInfos.invalidOfNumber(useZeroAsInitialValue ? 0 : catchWeightParam)
  );
  const [showCatchWeightQRCodeReader, setShowCatchWeightQRCodeReader] = useState(useCatchWeight);
  const [scannedQRCodeInfo, setScannedQRCodeInfo] = useState(null);

  const onQtyEntered = (qtyInfo) => setQtyInfo(qtyInfo);
  const onReasonSelected = (reason) => setRejectedReason(reason);
  const onCatchWeightEntered = (qtyInfo) => setCatchWeight(qtyInfo);

  const [bestBeforeDate, setBestBeforeDate] = useState(bestBeforeDateParam ?? '');
  const [isBestBeforeDateValid, setIsBestBeforeDateValid] = useState(true);
  const onBestBeforeDateEntered = ({ date, isValid }) => {
    setBestBeforeDate(date);
    setIsBestBeforeDateValid(isValid);
  };

  const [lotNo, setLotNo] = useState(lotNoParam ?? '');
  const onLotNoEntered = (e) => {
    const lotNoNew = e.target.value ? e.target.value : '';
    //console.log('onLotNoEntered', { lotNoNew, e });
    setLotNo(lotNoNew);
  };

  const isQtyRejectedRequired = Array.isArray(qtyRejectedReasons) && qtyRejectedReasons.length > 0;
  const qtyRejected =
    isQtyRejectedRequired && qtyInfos.isValid(qtyInfo)
      ? Math.max(qtyTarget - qtyInfos.toNumberOrString(qtyInfo), 0)
      : 0;

  const isQtyValid =
    doNotValidateQty ||
    (qtyInfo?.isQtyValid &&
      (qtyRejected === 0 || rejectedReason != null) &&
      (!useCatchWeight || catchWeight?.isQtyValid));
  const allValid = (readOnlyParam || (isQtyValid && (!isShowBestBeforeDate || isBestBeforeDateValid))) && !isProcessing;
  const readOnly = readOnlyParam || isProcessing;

  const getConfirmationPrompt = useCallback(
    async (qtyInput) => {
      return getConfirmationPromptForQty && (await getConfirmationPromptForQty(qtyInput));
    },
    [getConfirmationPromptForQty]
  );

  const fireOnQtyChange = useCallback(
    (payload) => {
      setProcessing(true);
      try {
        const promise = onQtyChange(payload)?.catch?.((error) => toastErrorFromObj(error));
        doFinally(promise, () => setProcessing(false));
      } catch (error) {
        setProcessing(false);
        throw error;
      }
    },
    [onQtyChange]
  );

  const onDialogYes = async ({ isCloseTarget }) => {
    if (isProcessing) return;

    if (allValid) {
      const inputQtyEnteredAndValidated = isScanForReview ? QTY_SCAN_FOR_REVIEW : qtyInfos.toNumberOrString(qtyInfo);

      let qtyEnteredAndValidated = inputQtyEnteredAndValidated;
      if (!!qtyAlreadyOnScale && typeof inputQtyEnteredAndValidated === 'number') {
        qtyEnteredAndValidated = Math.max(inputQtyEnteredAndValidated - qtyAlreadyOnScale, 0);
      }

      const onQtyChangePayload = {
        qtyEnteredAndValidated: qtyEnteredAndValidated,
        qtyRejected,
        qtyRejectedReason: qtyRejected > 0 ? rejectedReason : null,
        catchWeight: useCatchWeight ? qtyInfos.toNumberOrString(catchWeight) : null,
        catchWeightUom: useCatchWeight ? catchWeightUom : null,
        bestBeforeDate: isShowBestBeforeDate ? bestBeforeDate : null,
        lotNo: isShowLotNo ? lotNo : null,
        isCloseTarget: !!isCloseTarget,
        ...scannedQRCodeInfo,
      };
      uiTrace.putContext(onQtyChangePayload);

      const confirmationPrompt = await getConfirmationPrompt(qtyEnteredAndValidated);
      if (confirmationPrompt) {
        setConfirmationDialogProps({
          promptQuestion: confirmationPrompt,
          onQtyChangePayload,
        });
        return;
      }

      fireOnQtyChange(onQtyChangePayload);
    }
  };

  // The Done button submits the form (see onSubmit), so the confirm is traced here, as the Done button click.
  const onConfirmDoneTraced = uiTrace.traceFunction(() => onDialogYes({ isCloseTarget: false }), {
    eventName: 'dialogButtonClick',
    captionKey: 'activities.picking.confirmDone',
    caption: trl('activities.picking.confirmDone'),
    testId: 'done-button',
  });

  // Overwrites the pre-filled values with the scanned ones and shows them for review; the operator confirms.
  const fillFieldsFromScannedQRCode = ({ qrCode, scannedBarcode }) => {
    setQtyInfo(qtyInfos.invalidOfNumber(QTY_SCAN_FOR_REVIEW));
    setCatchWeight(qtyInfos.invalidOfNumber(qrCode.weightNet));
    if (qrCode.lotNo != null) {
      setLotNo(qrCode.lotNo);
    }
    if (qrCode.bestBeforeDate != null) {
      setBestBeforeDate(qrCode.bestBeforeDate);
      setIsBestBeforeDateValid(true);
    }
    setScannedQRCodeInfo({
      productionDate: qrCode.productionDate,
      barcodeType: qrCode.barcodeType,
      barcode: scannedBarcode, // i.e. the catch weight QR code
    });
    uiTrace.putContext({ scannedBarcode, isFilledForReview: true });
    setShowCatchWeightQRCodeReader(false);
  };

  const readQtyFromQrCode = useCallback(
    async (result) => {
      const qrCode = isScanForReview
        ? parseQRCodeForReview({ string: result.scannedBarcode, customQRCodeFormats })
        : parseQRCodeString({ string: result.scannedBarcode, customQRCodeFormats });
      if (!qrCode.weightNet || !qrCode.weightNetUOM) {
        throw { messageKey: 'activities.picking.qrcode.missingQty' };
      }
      if (qrCode.weightNetUOM !== catchWeightUom) {
        throw { messageKey: 'activities.picking.qrcode.differentUOM' };
      }

      if (isScanForReview) {
        assertLotNoAndBestBeforeDateScanned(qrCode);
        fillFieldsFromScannedQRCode({ qrCode, scannedBarcode: result.scannedBarcode });
        return;
      }

      const onQtyChangePayload = {
        qtyEnteredAndValidated: 1,
        catchWeight: qrCode.weightNet,
        catchWeightUom: catchWeightUom,
        bestBeforeDate: qrCode.bestBeforeDate,
        productionDate: qrCode.productionDate,
        lotNo: qrCode.lotNo,
        productNo: qrCode.productNo,
        barcodeType: qrCode.barcodeType,
        barcode: result.scannedBarcode, // i.e. the catch weight QR code
        isDone: false,
      };
      uiTrace.putContext(onQtyChangePayload);

      const confirmationPrompt = await getConfirmationPrompt(1);

      if (confirmationPrompt) {
        setConfirmationDialogProps({
          promptQuestion: confirmationPrompt,
          onQtyChangePayload,
        });
        return;
      }

      // console.log('readQtyFromQrCode', { qrCode, result, catchWeightUom });
      fireOnQtyChange(onQtyChangePayload);
    },
    [catchWeightUom, fireOnQtyChange, isScanForReview]
  );

  const wsClientRef = useRef(null);
  useEffect(() => {
    if (scaleDevice && useScaleDevice) {
      if (!wsClientRef.current) {
        wsClientRef.current = ws.connectAndSubscribe({
          topic: scaleDevice.websocketEndpoint,
          debug: false,
          onWebsocketMessage: (message) => {
            if (useScaleDevice) {
              const { value } = JSON.parse(message.body);

              const newQtyCandidate = qtyInfos.invalidOfNumber(value);

              setQtyInfo((prev) => {
                if (!prev || newQtyCandidate.qty !== prev.qty) {
                  return newQtyCandidate;
                }

                return prev;
              });
            }
          },
          headers: {
            qtyTarget: totalQty || '0',
            positiveTolerance: scaleTolerance?.positiveTolerance || '0',
            negativeTolerance: scaleTolerance?.negativeTolerance || '0',
            uom: uom,
          },
        });
      }
    }

    return () => {
      if (wsClientRef.current) {
        ws.disconnectClient(wsClientRef.current);
        wsClientRef.current = null;
      }
    };
  }, [scaleDevice, useScaleDevice]);

  const isCustomView = () => {
    return showCatchWeightQRCodeReader;
  };

  const getCustomView = () => {
    if (showCatchWeightQRCodeReader) {
      return getQRCodeCatchWeightView();
    } else {
      return <></>;
    }
  };

  const getQRCodeCatchWeightView = () => {
    return (
      <>
        <table className="table">
          <tbody>
            {qtyTargetCaption && (
              <tr>
                <th>{qtyTargetCaption}</th>
                <td data-testid="qty-target" data-internalvalue={qtyTarget} data-internalvalue-uom={uom}>
                  {formatQtyToHumanReadableStr({ qty: Math.max(qtyTarget, 0), uom })}
                </td>
              </tr>
            )}
            {userInfo &&
              userInfo.map((item) => (
                <tr key={computeKeyFromUserInfoItem(item)}>
                  <th>{computeCaptionFromUserInfoItem(item)}</th>
                  <td>{item.value}</td>
                </tr>
              ))}
            <tr>
              <td colSpan="2">
                <BarcodeScannerComponent
                  customQRCodeFormats={customQRCodeFormats}
                  onResolvedResult={readQtyFromQrCode}
                />
              </td>
            </tr>
          </tbody>
        </table>
        <div className="buttons is-centered">
          {!isScanForReview && (
            <DialogButton
              captionKey="activities.picking.switchToManualInput"
              onClick={() => setShowCatchWeightQRCodeReader(false)}
              testId="switchToManualInput-button"
            />
          )}
          <DialogButton
            captionKey="general.closeText"
            className="is-danger"
            onClick={onCloseDialog}
            testId="done-button"
          />
        </div>
      </>
    );
  };

  if (confirmationDialogProps?.promptQuestion && confirmationDialogProps.onQtyChangePayload) {
    return (
      <YesNoDialog
        promptQuestion={confirmationDialogProps.promptQuestion}
        onYes={() => {
          fireOnQtyChange(confirmationDialogProps.onQtyChangePayload);
          setConfirmationDialogProps(undefined);
        }}
        onNo={() => setConfirmationDialogProps(undefined)}
      />
    );
  }

  return (
    <div>
      {isProcessing && <Spinner />}
      <Dialog className="get-qty-dialog">
        {isCustomView() && getCustomView()}
        {!isCustomView() && (
          <form
            onSubmit={(event) => {
              event.preventDefault(); // never navigate; Done / Enter confirm exactly once, here
              return onConfirmDoneTraced();
            }}
          >
            <div className="table-container">
              <table className="table">
                <tbody>
                  {qtyTargetCaption && (
                    <tr>
                      <th>{qtyTargetCaption}</th>
                      <td data-testid="qty-target" data-internalvalue={qtyTarget} data-internalvalue-uom={uom}>
                        {formatQtyToHumanReadableStr({ qty: Math.max(qtyTarget, 0), uom })}
                      </td>
                    </tr>
                  )}
                  {userInfo &&
                    userInfo.map((item) => (
                      <tr key={computeKeyFromUserInfoItem(item)}>
                        <th>{computeCaptionFromUserInfoItem(item)}</th>
                        <td>{item.value}</td>
                      </tr>
                    ))}
                  {!hideQtyInput && (
                    <tr>
                      <th>{qtyCaption ?? trl('general.Qty')}</th>
                      <td>
                        <QtyInputField
                          id="qty-input"
                          qty={qtyInfos.toNumberOrString(qtyInfo)}
                          uom={uom}
                          validateQtyEntered={validateQtyEntered}
                          readonly={useScaleDevice || isScanForReview || readOnly}
                          onQtyChange={onQtyEntered}
                          isRequestFocus={true}
                        />
                      </td>
                    </tr>
                  )}
                  {packingItemName && (
                    <tr>
                      <th>{trl('general.PackingItemName')}</th>
                      <td id="packing-name">{packingItemName}</td>
                    </tr>
                  )}
                  {scaleDevice && allowManualInput && (
                    <tr>
                      <td colSpan="2">
                        <div className="buttons has-addons">
                          <button
                            type="button"
                            className={cx('button', {
                              'is-success': useScaleDevice,
                              'is-selected': useScaleDevice,
                            })}
                            onClick={() => setUseScaleDevice(true)}
                          >
                            {scaleDevice.caption}
                          </button>
                          <button
                            type="button"
                            className={cx('button', {
                              'is-success': !useScaleDevice,
                              'is-selected': !useScaleDevice,
                            })}
                            onClick={() => setUseScaleDevice(false)}
                          >
                            Manual
                          </button>
                        </div>
                      </td>
                    </tr>
                  )}
                  {isShowBestBeforeDate && (
                    <tr>
                      <th>{trl('general.BestBeforeDate')}</th>
                      <td>
                        <div className="field">
                          <div className="control">
                            <DateInput
                              testId="bestBeforeDate"
                              type="date"
                              value={bestBeforeDate}
                              disabled={readOnly}
                              onChange={onBestBeforeDateEntered}
                            />
                          </div>
                        </div>
                      </td>
                    </tr>
                  )}
                  {isShowLotNo && (
                    <tr>
                      <th>{trl('general.LotNo')}</th>
                      <td>
                        <div className="field">
                          <div className="control">
                            <input
                              data-testid="lotNo"
                              className="input"
                              type="text"
                              value={lotNo}
                              disabled={readOnly}
                              onChange={onLotNoEntered}
                            />
                          </div>
                        </div>
                      </td>
                    </tr>
                  )}
                  {useCatchWeight && (
                    <tr>
                      <th>{trl('general.CatchWeight')}</th>
                      <td>
                        <>
                          <QtyInputField
                            id="catch-weight"
                            qty={qtyInfos.toNumberOrString(catchWeight)}
                            uom={catchWeightUom}
                            onQtyChange={onCatchWeightEntered}
                            readonly={readOnly}
                          />
                          <DialogButton
                            captionKey="activities.picking.switchToQrCodeInput"
                            className="button"
                            onClick={() => setShowCatchWeightQRCodeReader(true)}
                          />
                        </>
                      </td>
                    </tr>
                  )}
                  {qtyRejected > 0 && (
                    <>
                      <tr>
                        <th>{trl('general.QtyRejected')}</th>
                        <td id="qty-rejected">{formatQtyToHumanReadableStr({ qty: qtyRejected, uom })}</td>
                      </tr>
                      <tr>
                        <td colSpan={2}>
                          <QtyReasonsRadioGroup
                            reasons={qtyRejectedReasons}
                            selectedReason={rejectedReason}
                            disabled={qtyRejected === 0}
                            onReasonSelected={onReasonSelected}
                          />
                        </td>
                      </tr>
                    </>
                  )}
                </tbody>
              </table>
            </div>
            <div className="buttons is-centered">
              {isShowCloseTargetButton && (
                <>
                  <DialogButton
                    captionKey="activities.picking.confirmDoneAndCloseTarget"
                    className="is-success"
                    disabled={!allValid}
                    onClick={() => onDialogYes({ isCloseTarget: true })}
                  />
                  <br />
                </>
              )}
              <DialogButton
                captionKey="activities.picking.confirmDone"
                className="is-success"
                disabled={!allValid}
                testId="done-button"
                type="submit"
              />
              <DialogButton
                captionKey="general.cancelText"
                className="is-danger"
                disabled={isProcessing}
                onClick={onCloseDialog}
                testId="cancel-button"
              />
            </div>
          </form>
        )}
      </Dialog>
    </div>
  );
};

const computeKeyFromUserInfoItem = ({ caption = null, captionKey = null, value }) => {
  return `userInfo_${caption || captionKey || value || '?'}`;
};

const computeCaptionFromUserInfoItem = ({ caption = null, captionKey = null }) => {
  if (caption) {
    return caption;
  } else if (captionKey) {
    return trl(captionKey);
  } else {
    // shall not happen
    return '';
  }
};

const computeDefaultQtyRejectedReason = (qtyRejectedReasons) => {
  if (!Array.isArray(qtyRejectedReasons) || qtyRejectedReasons.length <= 0) {
    return null;
  }

  const defaultReason = qtyRejectedReasons.find((reason) => reason.key === QTY_REJECTED_REASON_TO_IGNORE_KEY);
  return defaultReason?.key ?? null;
};

GetQuantityDialog.propTypes = {
  // Properties
  hideQtyInput: PropTypes.bool,
  readOnly: PropTypes.bool,
  userInfo: PropTypes.array,
  qtyTarget: PropTypes.number.isRequired,
  qtyTargetCaption: PropTypes.string,
  totalQty: PropTypes.number,
  qtyAlreadyOnScale: PropTypes.number,
  qtyCaption: PropTypes.string,
  qtyInitial: PropTypes.number,
  packingItemName: PropTypes.string,
  uom: PropTypes.string.isRequired,
  qtyRejectedReasons: PropTypes.arrayOf(PropTypes.object),
  scaleDevice: PropTypes.object,
  scaleTolerance: PropTypes.object,
  catchWeight: PropTypes.number,
  catchWeightUom: PropTypes.string,
  readAttributes: PropTypes.array,
  bestBeforeDate: PropTypes.string,
  lotNo: PropTypes.string,
  isShowCloseTargetButton: PropTypes.bool,
  customQRCodeFormats: PropTypes.array,
  // Catch-weight only: an L&M QR code must be scanned; the scan fills the fields for review instead of confirming
  isCatchWeightScanMandatory: PropTypes.bool,

  // Callbacks
  validateQtyEntered: PropTypes.func,
  getConfirmationPromptForQty: PropTypes.func,
  onQtyChange: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func,
};

export default GetQuantityDialog;
