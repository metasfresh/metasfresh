import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import { trl } from '../../utils/translations';

import QtyInputField from '../QtyInputField';
import QtyReasonsRadioGroup from '../QtyReasonsRadioGroup';
import * as ws from '../../utils/websocket';
import { qtyInfos } from '../../utils/qtyInfos';
import { formatQtyToHumanReadableStr } from '../../utils/qtys';
import { useBooleanSetting } from '../../reducers/settings';

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

  const [qtyInfo, setQtyInfo] = useState(qtyInfos.invalidOfNumber(qtyTarget));
  const [rejectedReason, setRejectedReason] = useState(null);
  const [useScaleDevice, setUseScaleDevice] = useState(!!scaleDevice);

  const useCatchWeight = !scaleDevice && catchWeightUom;
  const [catchWeight, setCatchWeight] = useState(qtyInfos.invalidOfNumber(catchWeightParam));

  const onQtyEntered = (qtyInfo) => setQtyInfo(qtyInfo);
  const onReasonSelected = (reason) => setRejectedReason(reason);
  const onCatchWeghtEntered = (qtyInfo) => setCatchWeight(qtyInfo);

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

  const onDialogYes = () => {
    if (allValid) {
      const inputQtyEnteredAndValidated = qtyInfos.toNumberOrString(qtyInfo);

      let qtyEnteredAndValidated = inputQtyEnteredAndValidated;
      if (!!qtyAlreadyOnScale && typeof inputQtyEnteredAndValidated === 'number') {
        qtyEnteredAndValidated = Math.max(inputQtyEnteredAndValidated - qtyAlreadyOnScale, 0);
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
                {!hideQtyInput && (
                  <tr>
                    <th>Qty</th>
                    <td>
                      <QtyInputField
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
                {scaleDevice && allowManualInput && (
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
                {useCatchWeight && (
                  <tr>
                    <th>{trl('general.CatchWeight')}</th>
                    <td>
                      <QtyInputField
                        qty={qtyInfos.toNumberOrString(catchWeight)}
                        uom={catchWeightUom}
                        onQtyChange={onCatchWeghtEntered}
                        readonly={readOnly}
                      />
                    </td>
                  </tr>
                )}
                {qtyRejected > 0 && (
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
              <button className="button is-success" disabled={!allValid} onClick={onDialogYes}>
                {trl('activities.picking.confirmDone')}
              </button>
              <button className="button is-danger" onClick={onCloseDialog}>
                {trl('general.cancelText')}
              </button>
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
