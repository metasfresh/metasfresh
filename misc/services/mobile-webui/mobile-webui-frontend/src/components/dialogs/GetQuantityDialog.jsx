import React, { useCallback, useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import { trl } from '../../utils/translations';

import QtyInputField from '../QtyInputField';
import QtyReasonsRadioGroup from '../QtyReasonsRadioGroup';
import DateInput from '../DateInput';
import EditableAttributesSection from '../attributes/EditableAttributesSection';
import * as ws from '../../utils/websocket';
import { qtyInfos } from '../../utils/qtyInfos';
import { formatQtyToHumanReadableStr } from '../../utils/qtys';
import { useBooleanSetting } from '../../reducers/settings';
import BarcodeScannerComponent from '../BarcodeScannerComponent';
import { parseQRCodeString } from '../../utils/qrCode/hu';
import { toastErrorFromObj } from '../../utils/toast';
import { doFinally } from '../../utils';
import YesNoDialog from './YesNoDialog';
import DialogButton from './DialogButton';
import Dialog from './Dialog';
import * as uiTrace from './../../utils/ui_trace';
import Spinner from '../Spinner';
import { QTY_REJECTED_REASON_TO_IGNORE_KEY } from '../../reducers/wfProcesses';
import { PickAttribute } from '../../reducers/wfProcesses/picking/PickAttribute';
import { useSerialNos } from '../../hooks/useSerialNos';

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
  //
  readAttributes = [],
  bestBeforeDate: bestBeforeDateParam = '',
  lotNo: lotNoParam = '',
  serialNos: serialNosParam = [],
  editableAttributes = [],
  isShowCloseTargetButton = false,
  //
  validateQtyEntered,
  getConfirmationPromptForQty,
  onQtyChange,
  onCloseDialog,
}) => {
  const isShowBestBeforeDate = readAttributes.includes(PickAttribute.BestBeforeDate);
  const isShowLotNo = readAttributes.includes(PickAttribute.LotNo);
  const isShowSerialNo = readAttributes.includes(PickAttribute.SerialNo);

  const [isProcessing, setProcessing] = useState(false);
  const [confirmationDialogProps, setConfirmationDialogProps] = useState({
    promptQuestion: '',
    onQtyChangePayload: undefined,
  });

  const allowManualInput = useBooleanSetting('qtyInput.AllowManualInputWhenScaleDeviceExists');
  const doNotValidateQty = useBooleanSetting('qtyInput.DoNotValidate');
  const useZeroAsInitialValue = useBooleanSetting('qtyInput.useZeroAsInitialValue');

  const [qtyInfo, setQtyInfo] = useState(
    qtyInfos.invalidOfNumber((useZeroAsInitialValue ? 0 : null) ?? qtyInitial ?? qtyTarget)
  );
  const [rejectedReason, setRejectedReason] = useState(computeDefaultQtyRejectedReason(qtyRejectedReasons));
  const [useScaleDevice, setUseScaleDevice] = useState(!!scaleDevice);

  const useCatchWeight = !scaleDevice && catchWeightUom;
  const [catchWeight, setCatchWeight] = useState(
    qtyInfos.invalidOfNumber(useZeroAsInitialValue ? 0 : catchWeightParam)
  );
  const [showCatchWeightQRCodeReader, setShowCatchWeightQRCodeReader] = useState(useCatchWeight);

  const onQtyEntered = (qtyInfo) => setQtyInfo(qtyInfo);
  const onReasonSelected = (reason) => setRejectedReason(reason);
  const onCatchWeightEntered = (qtyInfo) => setCatchWeight(qtyInfo);

  const [bestBeforeDate, setBestBeforeDate] = useState(bestBeforeDateParam);
  const [isBestBeforeDateValid, setIsBestBeforeDateValid] = useState(true);
  const onBestBeforeDateEntered = ({ date, isValid }) => {
    setBestBeforeDate(date);
    setIsBestBeforeDateValid(isValid);
  };

  const [lotNo, setLotNo] = useState(lotNoParam);
  const onLotNoEntered = (e) => {
    const lotNoNew = e.target.value ? e.target.value : '';
    //console.log('onLotNoEntered', { lotNoNew, e });
    setLotNo(lotNoNew);
  };

  // Generic, per-attribute-code editable-attribute values (e.g. mfg receive) — collected as a
  // `{ [code]: value }` map, already excluding empty/invalid entries (EditableAttributesSection's
  // own contract). Not shown/used unless a caller passes `editableAttributes`.
  const [attributeValues, setAttributeValues] = useState({});

  // Serial numbers: one per picked unit (required count = entered qty). Captured via a live
  // multi-scan screen (chips + "X of N"), mirroring the GRAI scan UX. Manual entry is the
  // BarcodeScannerComponent fallback; duplicate scans are silently deduped.
  const serialNoRequiredCount = computeSerialNoRequiredCount(qtyInfo);
  const {
    serialNos,
    assignedSerialNos,
    extraSerialNos,
    isComplete: isSerialNosComplete,
    addSerialNos,
    removeSerialNo,
  } = useSerialNos({ requiredCount: serialNoRequiredCount, initialSerialNos: serialNosParam });
  const [showSerialNoScanner, setShowSerialNoScanner] = useState(false);
  const onSerialNoScanned = (result) => {
    const scanned = result?.scannedBarcode ?? '';
    if (scanned) {
      addSerialNos([scanned]);
    }
    // stay in the scan view to keep accumulating (live multi-scan); operator taps Done to return
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
  const allValid =
    (readOnlyParam ||
      (isQtyValid && (!isShowBestBeforeDate || isBestBeforeDateValid) && (!isShowSerialNo || isSerialNosComplete))) &&
    !isProcessing;
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
      const inputQtyEnteredAndValidated = qtyInfos.toNumberOrString(qtyInfo);

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
        serialNos: isShowSerialNo ? serialNos : null,
        attributeValues: editableAttributes.length > 0 ? attributeValues : null,
        isCloseTarget: !!isCloseTarget,
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

  const readQtyFromQrCode = useCallback(
    async (result) => {
      const qrCode = parseQRCodeString({ string: result.scannedBarcode, customQRCodeFormats });
      if (!qrCode.weightNet || !qrCode.weightNetUOM) {
        throw { messageKey: 'activities.picking.qrcode.missingQty' };
      }
      if (qrCode.weightNetUOM !== catchWeightUom) {
        throw { messageKey: 'activities.picking.qrcode.differentUOM' };
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
    [catchWeightUom, fireOnQtyChange]
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
    return showCatchWeightQRCodeReader || showSerialNoScanner;
  };

  const getCustomView = () => {
    if (showCatchWeightQRCodeReader) {
      return getQRCodeCatchWeightView();
    } else if (showSerialNoScanner) {
      return getSerialNoScanView();
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
                  <td data-testid={computeKeyFromUserInfoItem(item)}>{item.value}</td>
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
          <DialogButton
            captionKey="activities.picking.switchToManualInput"
            onClick={() => setShowCatchWeightQRCodeReader(false)}
            testId="switchToManualInput-button"
          />
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

  const renderSerialNoCount = () => (
    <div className="serialNo-count" data-testid="serialNo-count">
      {trl('activities.picking.serialNoCount', {
        scanned: assignedSerialNos.length,
        total: serialNoRequiredCount,
      })}
      {extraSerialNos.length > 0 && (
        <span className="serialNo-count-extra" data-testid="serialNo-count-extra">
          {' '}
          {trl('activities.picking.serialNoCountExtra', { extra: extraSerialNos.length })}
        </span>
      )}
    </div>
  );

  const renderSerialNoChips = () => (
    <div className="serialNo-chip-list" data-testid="serialNo-chip-list">
      {assignedSerialNos.map((sn) => (
        <SerialNoChip key={sn} value={sn} onRemove={() => removeSerialNo(sn)} disabled={readOnly} />
      ))}
      {extraSerialNos.map((sn) => (
        <SerialNoChip key={sn} value={sn} extra onRemove={() => removeSerialNo(sn)} disabled={readOnly} />
      ))}
    </div>
  );

  const getSerialNoScanView = () => {
    return (
      <>
        {/* Live multi-scan: the scanner stays open; each scan adds a chip and "X of N" advances.
            Mirrors the GRAI scan UX. The operator taps Done to return to the qty dialog. */}
        <div className="serialNo-scan-view">
          <BarcodeScannerComponent
            onResolvedResult={onSerialNoScanned}
            inputPlaceholderText={trl('activities.picking.scanSerialNo')}
          />
        </div>
        {renderSerialNoCount()}
        {renderSerialNoChips()}
        <div className="buttons is-centered">
          <DialogButton
            captionKey="activities.picking.serialNoScanDone"
            className="is-success"
            onClick={() => setShowSerialNoScanner(false)}
            testId="serialNo-scan-done-button"
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
          <form onSubmit={() => onDialogYes({ isCloseTarget: false })}>
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
                        <td data-testid={computeKeyFromUserInfoItem(item)}>{item.value}</td>
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
                          readonly={useScaleDevice || readOnly}
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
                            className={cx('button', {
                              'is-success': useScaleDevice,
                              'is-selected': useScaleDevice,
                            })}
                            onClick={() => setUseScaleDevice(true)}
                          >
                            {scaleDevice.caption}
                          </button>
                          <button
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
                  {isShowSerialNo && (
                    <tr>
                      <th>{trl('general.SerialNo')}</th>
                      <td>
                        <div className="serialNo-scanned">
                          {/* "X of N" count + the scanned serials as removable chips, then a
                              full-width button into the live multi-scan view. Mirrors the GRAI
                              multi-scan feel; one serial per picked unit. */}
                          {renderSerialNoCount()}
                          {serialNos.length > 0 && renderSerialNoChips()}
                          <DialogButton
                            captionKey={
                              serialNos.length > 0
                                ? 'activities.picking.scanSerialNoAgain'
                                : 'activities.picking.scanSerialNo'
                            }
                            className="is-fullwidth"
                            onClick={() => setShowSerialNoScanner(true)}
                            testId={serialNos.length > 0 ? 'serialNo-scan-again-button' : 'serialNo-scan-button'}
                            disabled={readOnly}
                          />
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
            <EditableAttributesSection
              attributes={editableAttributes}
              disabled={readOnly}
              onFieldChange={setAttributeValues}
            />
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
                onClick={() => onDialogYes({ isCloseTarget: false })}
                testId="done-button"
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

// Required serial count = the entered pick quantity (one serial per unit). 0 when qty not yet a
// positive number (the qty gate keeps the dialog from confirming until a valid qty is entered).
const computeSerialNoRequiredCount = (qtyInfo) => {
  // toNumberOrString may yield a number or a numeric string depending on validation state — coerce both.
  const n = Number(qtyInfos.toNumberOrString(qtyInfo));
  return Number.isFinite(n) && n > 0 ? Math.round(n) : 0;
};

// A scanned-serial chip: middle-truncated value (leading + last 4, like "SN-0001…6789") + a remove
// button. `extra` styles serials scanned beyond the required count (they block confirm).
const SerialNoChip = ({ value, extra = false, disabled = false, onRemove }) => (
  <div className={cx('serialNo-chip', { 'serialNo-chip--extra': extra })} data-testid="serialNo-chip">
    {/* Middle-truncate on overflow: keep the leading chars + the always-visible last 4 (e.g.
        "SN-0001…6789"); split spans keep the full value as textContent. */}
    <span className="serialNo-chip-text" title={value}>
      <span className="serialNo-chip-text-head">{value.slice(0, -4)}</span>
      <span className="serialNo-chip-text-tail">{value.slice(-4)}</span>
    </span>
    {!disabled && (
      <button
        type="button"
        className="serialNo-chip-remove"
        data-testid="serialNo-chip-remove"
        onClick={onRemove}
        aria-label="remove"
      >
        ✕
      </button>
    )}
  </div>
);
SerialNoChip.propTypes = {
  value: PropTypes.string.isRequired,
  extra: PropTypes.bool,
  disabled: PropTypes.bool,
  onRemove: PropTypes.func.isRequired,
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
  serialNos: PropTypes.arrayOf(PropTypes.string),
  editableAttributes: PropTypes.array,
  isShowCloseTargetButton: PropTypes.bool,
  customQRCodeFormats: PropTypes.array,

  // Callbacks
  validateQtyEntered: PropTypes.func,
  getConfirmationPromptForQty: PropTypes.func,
  onQtyChange: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func,
};

export default GetQuantityDialog;
