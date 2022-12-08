import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import { trl } from '../../utils/translations';

import QtyInputField from '../QtyInputField';
import QtyReasonsRadioGroup from '../QtyReasonsRadioGroup';
import * as ws from '../../utils/websocket';
import { qtyInfos } from '../../utils/qtyInfos';

const GetQuantityDialog = ({
  qtyInitial,
  qtyTarget,
  qtyCaption,
  uom,
  qtyRejectedReasons,
  scaleDevice,
  //
  validateQtyEntered,
  onQtyChange,
  onCloseDialog,
}) => {
  const [qtyInfo, setQtyInfo] = useState(qtyInfos.invalidOfNumber(qtyInitial));
  const [rejectedReason, setRejectedReason] = useState(null);
  const [useScaleDevice, setUseScaleDevice] = useState(!!scaleDevice);

  const onQtyEntered = (qtyInfo) => setQtyInfo(qtyInfo);
  const onReasonSelected = (reason) => setRejectedReason(reason);

  const requiredQtyRejectedReason = Array.isArray(qtyRejectedReasons) && qtyRejectedReasons.length > 0;
  const qtyRejected =
    requiredQtyRejectedReason && qtyInfos.isValid(qtyInfo)
      ? Math.max(qtyTarget - qtyInfos.toNumberOrString(qtyInfo), 0)
      : 0;

  const allValid = qtyInfo != null && qtyInfo.isQtyValid && (qtyRejected === 0 || rejectedReason != null);

  const onDialogYes = () => {
    if (allValid) {
      onQtyChange({
        qtyEnteredAndValidated: qtyInfos.toNumberOrString(qtyInfo),
        qtyRejectedReason: qtyRejected > 0 ? rejectedReason : null,
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
              setQtyInfo(qtyInfos.invalidOfNumber(value));
            }
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
                <tr>
                  <th>{qtyCaption}</th>
                  <td>
                    {qtyTarget > 0 ? qtyTarget : 0} {uom}
                  </td>
                </tr>
                <tr>
                  <th>Qty</th>
                  <td>
                    <QtyInputField
                      qty={qtyInfos.toNumberOrString(qtyInfo)}
                      uom={uom}
                      validateQtyEntered={validateQtyEntered}
                      readonly={useScaleDevice}
                      onQtyChange={onQtyEntered}
                      isRequestFocus={true}
                    />
                  </td>
                </tr>
                {scaleDevice && (
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
                {requiredQtyRejectedReason && (
                  <tr>
                    <th>{trl('general.QtyRejected')}</th>
                    <td>
                      {qtyRejected} {uom}
                    </td>
                  </tr>
                )}
                {requiredQtyRejectedReason && (
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
                )}
              </tbody>
            </table>

            <div className="buttons is-centered">
              <button className="button is-danger" disabled={!allValid} onClick={onDialogYes}>
                {trl('activities.picking.confirmDone')}
              </button>
              <button className="button is-success" onClick={onCloseDialog}>
                {trl('general.cancelText')}
              </button>
            </div>
          </div>
        </article>
      </div>
    </div>
  );
};

GetQuantityDialog.propTypes = {
  // Properties
  qtyInitial: PropTypes.number,
  qtyTarget: PropTypes.number.isRequired,
  qtyCaption: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,
  qtyRejectedReasons: PropTypes.arrayOf(PropTypes.object),
  scaleDevice: PropTypes.object,

  // Callbacks
  validateQtyEntered: PropTypes.func,
  onQtyChange: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func,
};

export default GetQuantityDialog;
