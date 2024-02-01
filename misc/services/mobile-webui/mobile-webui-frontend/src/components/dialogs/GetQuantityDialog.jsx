import React, { useCallback, useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import { trl } from '../../utils/translations';

import QtyInputField from '../QtyInputField';
import QtyReasonsRadioGroup from '../QtyReasonsRadioGroup';
import * as ws from '../../utils/websocket';
import { qtyInfos } from '../../utils/qtyInfos';
import { formatQtyToHumanReadableStr } from '../../utils/qtys';
import { useBooleanSetting } from '../../reducers/settings';
import { toastError } from '../../utils/toast';
import BarcodeScannerComponent from '../BarcodeScannerComponent';
import { parseQRCodeString } from '../../utils/huQRCodes';

const GetQuantityDialog = ({
  readOnly = false,
  hideQtyInput = false,
  //
  userInfo,
  qtyTarget,
  totalQty,
  qtyAlreadyOnScale,
  qtyCaption,
  uom,
  qtyRejectedReasons,
  scaleDevice,
  scaleTolerance,
  //
  catchWeight: catchWeightParam,
  catchWeightUom,
  //
  validateQtyEntered,
  onQtyChange,
  onCloseDialog,
}) => {
  const allowManualInput = useBooleanSetting('qtyInput.AllowManualInputWhenScaleDeviceExists');
  const doNotValidateQty = useBooleanSetting('qtyInput.DoNotValidate');
  const allowTempQtyStorage = useBooleanSetting('qtyInput.allowTempQtyStorage');

  const [qtyInfo, setQtyInfo] = useState(qtyInfos.invalidOfNumber(qtyTarget));
  const [rejectedReason, setRejectedReason] = useState(null);
  const [useScaleDevice, setUseScaleDevice] = useState(!!scaleDevice);
  const [tempQtyStorage, setTempQtyStorage] = useState(qtyInfos.of({ qty: 0 }));

  const useCatchWeight = !scaleDevice && catchWeightUom;
  const [catchWeight, setCatchWeight] = useState(qtyInfos.invalidOfNumber(catchWeightParam));
  const [showCatchWeightQRCodeReader, setShowCatchWeightQRCodeReader] = useState(false);

  const onQtyEntered = (qtyInfo) => setQtyInfo(qtyInfo);
  const onReasonSelected = (reason) => setRejectedReason(reason);
  const onCatchWeightEntered = (qtyInfo) => setCatchWeight(qtyInfo);

  const isQtyRejectedRequired = Array.isArray(qtyRejectedReasons) && qtyRejectedReasons.length > 0;
  const qtyRejected =
    isQtyRejectedRequired && qtyInfos.isValid(qtyInfo)
      ? Math.max(qtyTarget - qtyInfos.toNumberOrString(qtyInfo), 0)
      : 0;

  const allValid =
    readOnly ||
    doNotValidateQty ||
    (qtyInfo?.isQtyValid &&
      (qtyRejected === 0 || rejectedReason != null) &&
      (!useCatchWeight || catchWeight?.isQtyValid));

  const actualValidateQtyEntered = useCallback(
    (qty, uom) => {
      if (!allowTempQtyStorage) {
        return validateQtyEntered(qty, uom);
      }

      return validateQtyEntered(qty + tempQtyStorage.qty, uom);
    },
    [tempQtyStorage]
  );

  const onDialogYes = () => {
    if (allValid) {
      const inputQtyEnteredAndValidated = qtyInfos.toNumberOrString(qtyInfo);

      const qtyToIssue = inputQtyEnteredAndValidated + tempQtyStorage.qty;

      let qtyEnteredAndValidated = qtyToIssue;
      if (qtyAlreadyOnScale) {
        qtyEnteredAndValidated = Math.max(qtyToIssue - qtyAlreadyOnScale, 0);
      }

      onQtyChange({
        qtyEnteredAndValidated: qtyEnteredAndValidated,
        qtyRejected,
        qtyRejectedReason: qtyRejected > 0 ? rejectedReason : null,
        catchWeight: useCatchWeight ? qtyInfos.toNumberOrString(catchWeight) : null,
        catchWeightUom: useCatchWeight ? catchWeightUom : null,
      });
    }
  };

  const addQtyToTempLocalStorage = () => {
    const inputQtyEnteredAndValidated = qtyInfos.toNumberOrString(qtyInfo);

    if (!inputQtyEnteredAndValidated) {
      return toastError({ messageKey: 'activities.mfg.issues.noQtyEnteredCannotAddToStorage' });
    }

    const updatedTempStorageQtyValue = tempQtyStorage.qty + inputQtyEnteredAndValidated;
    const notValidQtyErrorMessage = validateQtyEntered(updatedTempStorageQtyValue, uom);

    if (notValidQtyErrorMessage) {
      const errorMessage = `${trl('activities.mfg.issues.cannotAddToStorageDueTo')}${notValidQtyErrorMessage}`;
      return toastError({ plainMessage: errorMessage });
    }

    setTempQtyStorage(qtyInfos.of({ qty: updatedTempStorageQtyValue }));
  };

  const getTotalQty = () => {
    return totalQty - tempQtyStorage.qty;
  };

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
            qtyTarget: getTotalQty() || '0',
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
  }, [scaleDevice, useScaleDevice, tempQtyStorage]);

  return (
    <div>
      <div className="prompt-dialog get-qty-dialog">
        <article className="message is-dark">
          <div className="message-body">
            <table className="table">
              <tbody>
                {qtyCaption && (
                  <tr>
                    <th>{qtyCaption}</th>
                    <td>{formatQtyToHumanReadableStr({ qty: Math.max(qtyTarget, 0), uom })}</td>
                  </tr>
                )}
                {userInfo &&
                  userInfo.map((item) => (
                    <tr key={computeKeyFromUserInfoItem(item)}>
                      <th>{computeCaptionFromUserInfoItem(item)}</th>
                      <td>{item.value}</td>
                    </tr>
                  ))}
                {!hideQtyInput && !showCatchWeightQRCodeReader && (
                  <tr>
                    <th>Qty</th>
                    <td>
                      <QtyInputField
                        qty={qtyInfos.toNumberOrString(qtyInfo)}
                        uom={uom}
                        validateQtyEntered={actualValidateQtyEntered}
                        readonly={useScaleDevice || readOnly}
                        onQtyChange={onQtyEntered}
                        isRequestFocus={true}
                      />
                    </td>
                  </tr>
                )}
                {allowTempQtyStorage && (
                  <tr>
                    <th>
                      <button className="button is-danger" onClick={addQtyToTempLocalStorage}>
                        {trl('activities.mfg.issues.addToFunnel')}
                      </button>
                    </th>
                    <td>
                      <QtyInputField
                        qty={qtyInfos.toNumberOrString(tempQtyStorage)}
                        uom={uom}
                        readonly={true}
                        onQtyChange={() => {}}
                        isRequestFocus={true}
                      />
                    </td>
                  </tr>
                )}
                {scaleDevice && allowManualInput && !showCatchWeightQRCodeReader && (
                  <tr>
                    <td colSpan="2">
                      <div className="buttons has-addons">
                        <button
                          className={cx('button', { 'is-success': useScaleDevice, 'is-selected': useScaleDevice })}
                          onClick={() => setUseScaleDevice(true)}
                        >
                          {scaleDevice.caption}
                        </button>
                        <button
                          className={cx('button', { 'is-success': !useScaleDevice, 'is-selected': !useScaleDevice })}
                          onClick={() => setUseScaleDevice(false)}
                        >
                          Manual
                        </button>
                      </div>
                    </td>
                  </tr>
                )}
                {useCatchWeight && !showCatchWeightQRCodeReader && (
                  <tr>
                    <th>{trl('general.CatchWeight')}</th>
                    <td>
                      <QtyInputField
                        qty={qtyInfos.toNumberOrString(catchWeight)}
                        uom={catchWeightUom}
                        onQtyChange={onCatchWeightEntered}
                        readonly={readOnly}
                      />
                      <button className="button" onClick={() => setShowCatchWeightQRCodeReader(true)}>
                        {trl('general.QRCode')}
                      </button>
                    </td>
                  </tr>
                )}
                {showCatchWeightQRCodeReader && (
                  <tr>
                    <td colSpan="2">
                      <BarcodeScannerComponent
                        continuousRunning={true}
                        onResolvedResult={(result) => {
                          const qrCode = parseQRCodeString(result.scannedBarcode);
                          onQtyChange({
                            qtyEnteredAndValidated: 1,
                            catchWeight: qrCode.weightNet,
                            catchWeightUom: catchWeightUom,
                            gotoPickingLineScreen: false,
                          });
                        }}
                      />
                    </td>
                  </tr>
                )}

                {qtyRejected > 0 && !showCatchWeightQRCodeReader && (
                  <>
                    <tr>
                      <th>{trl('general.QtyRejected')}</th>
                      <td>{formatQtyToHumanReadableStr({ qty: qtyRejected, uom })}</td>
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

            <div className="buttons is-centered">
              {!showCatchWeightQRCodeReader && (
                <>
                  <button className="button is-danger" disabled={!allValid} onClick={onDialogYes}>
                    {trl('activities.picking.confirmDone')}
                  </button>
                  <button className="button is-success" onClick={onCloseDialog}>
                    {trl('general.cancelText')}
                  </button>
                </>
              )}
              {showCatchWeightQRCodeReader && (
                <button className="button is-danger" onClick={onCloseDialog}>
                  {trl('general.closeText')}
                </button>
              )}
            </div>
          </div>
        </article>
      </div>
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

GetQuantityDialog.propTypes = {
  // Properties
  hideQtyInput: PropTypes.bool,
  readOnly: PropTypes.bool,
  userInfo: PropTypes.array,
  qtyTarget: PropTypes.number.isRequired,
  totalQty: PropTypes.number,
  qtyAlreadyOnScale: PropTypes.number,
  qtyCaption: PropTypes.string,
  uom: PropTypes.string.isRequired,
  qtyRejectedReasons: PropTypes.arrayOf(PropTypes.object),
  scaleDevice: PropTypes.object,
  scaleTolerance: PropTypes.object,
  catchWeight: PropTypes.number,
  catchWeightUom: PropTypes.string,

  // Callbacks
  validateQtyEntered: PropTypes.func,
  onQtyChange: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func,
};

export default GetQuantityDialog;
