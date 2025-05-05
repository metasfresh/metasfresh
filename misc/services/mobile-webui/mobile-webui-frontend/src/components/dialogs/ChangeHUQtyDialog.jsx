import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../utils/translations';

import QtyInputField from '../QtyInputField';
import { qtyInfos } from '../../utils/qtyInfos';
import DateInput from '../DateInput';

const ChangeHUQtyDialog = ({
  currentQty,
  uom,
  isShowBestBeforeDate = false,
  bestBeforeDate: bestBeforeDateParam,
  isShowLotNo = false,
  lotNo: lotNoParam,
  //
  onSubmit,
  onCloseDialog,
}) => {
  const [qtyInfo, setQtyInfo] = useState(qtyInfos.invalidOfNumber(currentQty));
  const [bestBeforeDate, setBestBeforeDate] = useState(bestBeforeDateParam);
  const [isBestBeforeDateValid, setIsBestBeforeDateValid] = useState(true);
  const [lotNo, setLotNo] = useState(lotNoParam);
  const [qtyChangeDescription, setQtyChangeDescription] = useState('');

  const allValid = qtyInfo !== null && qtyInfo.isQtyValid && isBestBeforeDateValid;

  const validateQtyEntered = (qtyEntered) => {
    // Qty shall be positive
    if (qtyEntered <= 0) {
      return trl('activities.picking.notPositiveQtyNotAllowed');
    }

    // OK
    return null;
  };
  const onQtyEntered = (qtyInfo) => {
    setQtyInfo(qtyInfo);
  };
  const onBestBeforeDateEntered = ({ date, isValid }) => {
    setBestBeforeDate(date);
    setIsBestBeforeDateValid(isValid);
  };
  const onLotNoEntered = (e) => {
    const lotNoNew = e.target.value ? e.target.value : '';
    //console.log('onLotNoEntered', { lotNoNew, e });
    setLotNo(lotNoNew);
  };
  const onDescriptionChange = (e) => {
    const description = e.target.value ? e.target.value : '';
    setQtyChangeDescription(description);
  };
  const onDialogYes = () => {
    if (allValid) {
      onSubmit({
        qty: {
          qty: qtyInfos.toNumberOrString(qtyInfo),
          uomCode: uom,
        },
        setBestBeforeDate: isShowBestBeforeDate,
        bestBeforeDate,
        setLotNo: isShowLotNo,
        lotNo,
        description: qtyChangeDescription,
      });
    }
  };

  return (
    <div>
      <div className="prompt-dialog get-qty-dialog">
        <article className="message is-dark">
          <div className="message-body">
            <div className="table-container">
              <table className="table">
                <tbody>
                  <tr>
                    <th>{trl('huManager.action.changeQty.qtyLabel')}</th>
                    <td>
                      <QtyInputField
                        qty={qtyInfos.toNumberOrString(qtyInfo)}
                        uom={uom}
                        validateQtyEntered={validateQtyEntered}
                        onQtyChange={onQtyEntered}
                        isRequestFocus={true}
                      />
                    </td>
                  </tr>
                  {isShowBestBeforeDate && (
                    <tr>
                      <th>{trl('general.BestBeforeDate')}</th>
                      <td>
                        <div className="field">
                          <div className="control">
                            <DateInput type="date" value={bestBeforeDate} onChange={onBestBeforeDateEntered} />
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
                            <input className="input" type="text" value={lotNo} onChange={onLotNoEntered} />
                          </div>
                        </div>
                      </td>
                    </tr>
                  )}
                  <tr>
                    <th>{trl('huManager.action.changeQty.descriptionLabel')}</th>
                    <td>
                      <textarea className="input" value={qtyChangeDescription} onChange={onDescriptionChange} />
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
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

ChangeHUQtyDialog.propTypes = {
  // Properties
  currentQty: PropTypes.number.isRequired,
  uom: PropTypes.string.isRequired,
  isShowBestBeforeDate: PropTypes.bool,
  bestBeforeDate: PropTypes.string,
  isShowLotNo: PropTypes.bool,
  lotNo: PropTypes.string,

  // Callbacks
  onCloseDialog: PropTypes.func,
  onSubmit: PropTypes.func.isRequired,
};

export default ChangeHUQtyDialog;
