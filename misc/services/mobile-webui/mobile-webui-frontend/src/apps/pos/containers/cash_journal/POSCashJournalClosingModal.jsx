import React, { useEffect, useState } from 'react';
import cx from 'classnames';
import { getJournalSummary } from '../../api/posJournal';
import { getPaymentMethodCaption, PAYMENT_METHOD_CASH } from '../../constants/paymentMethods';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import PropTypes from 'prop-types';
import useEscapeKey from '../../../../hooks/useEscapeKey';
import { usePOSTerminal } from '../../actions/posTerminal';

import './POSCashJournalClosingModal.scss';
import { trl } from '../../../../utils/translations';
import { getPaymentDetailTypeCaption } from './paymentDetailType';

const _ = (key) => trl(`pos.closeCashJournal.${key}`);

const POSCashJournalClosingModal = () => {
  const posTerminal = usePOSTerminal();
  const [cashClosingBalance, setCashClosingBalance] = useState(0);
  const [closingNote, setClosingNote] = useState('');

  useEscapeKey(() => onCancelClick());

  const onCloseClick = () => {
    posTerminal.closeJournal({ cashClosingBalance, closingNote });
  };
  const onCancelClick = () => {
    posTerminal.cancelClosing();
  };

  return (
    <div className="modal is-active pos-journal-closing-panel">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">{_('title')}</p>
          <button className="delete" aria-label="close" onClick={onCancelClick}></button>
        </header>
        <section className="modal-card-body">
          <div className="line">
            <div className="field">
              <PaymentSummary
                posTerminalId={posTerminal.id}
                cashAmountExpected={cashClosingBalance}
                onCashAmountExpectedChanged={setCashClosingBalance}
              />
            </div>
          </div>
          <div className="line">
            <div className="caption">{_('closingNote')}</div>
            <div className="field">
              <textarea value={closingNote} onChange={(e) => setClosingNote(e.target.value)} />
            </div>
          </div>
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            <button className="button is-large" onClick={onCloseClick}>
              {_('actions.close')}
            </button>
            <button className="button is-large" onClick={onCancelClick}>
              {_('actions.cancel')}
            </button>
          </div>
        </footer>
      </div>
    </div>
  );
};

//
//
//
//
//

const PaymentSummary = ({ posTerminalId, cashAmountExpected, onCashAmountExpectedChanged }) => {
  const journalSummary = useJournalSummary({ posTerminalId });

  return (
    <table className="payment-summary">
      <thead>
        <tr>
          <th>{_('paymentMethod')}</th>
          <th className="amt">{_('bookedAmt')}</th>
          <th className="amt">{_('countedAmt')}</th>
          <th className="amt">{_('differenceAmt')}</th>
        </tr>
      </thead>
      <tbody>
        {journalSummary?.paymentMethods?.map((paymentMethod) => (
          <PaymentMethod
            key={paymentMethod.paymentMethod}
            paymentMethod={paymentMethod.paymentMethod}
            amountExpected={paymentMethod.amount}
            amountCounted={cashAmountExpected}
            currency={journalSummary.currencySymbol}
            precision={journalSummary.currencyPrecision}
            details={paymentMethod.details}
            onCountedAmountChanged={onCashAmountExpectedChanged}
          />
        ))}
      </tbody>
    </table>
  );
};

PaymentSummary.propTypes = {
  posTerminalId: PropTypes.number.isRequired,
  cashAmountExpected: PropTypes.number.isRequired,
  onCashAmountExpectedChanged: PropTypes.func.isRequired,
};

//
//
//
//
//

const PaymentMethod = ({
  paymentMethod,
  amountExpected,
  amountCounted,
  currency,
  precision,
  details,
  onCountedAmountChanged,
}) => {
  const amountExpectedStr = formatAmountToHumanReadableStr({ amount: amountExpected, currency, precision });

  const isRenderCountedField = paymentMethod === PAYMENT_METHOD_CASH && onCountedAmountChanged;
  let amountDifference = null;
  let amountDifferenceStr = '';
  if (isRenderCountedField) {
    amountDifference = amountCounted - amountExpected;
    amountDifferenceStr = formatAmountToHumanReadableStr({ amount: amountDifference, currency, precision });
  }

  return (
    <>
      <tr className="line-level1">
        <td className="description-col">{getPaymentMethodCaption({ paymentMethod })}</td>
        <td className="amt">{amountExpectedStr}</td>
        {isRenderCountedField && (
          <>
            <td className="amt">
              <input
                type="number"
                value={amountCounted ?? 0}
                onChange={(e) => {
                  onCountedAmountChanged(e.target.value);
                }}
              />
            </td>
            <td className={cx('amt', { 'is-red': amountDifference !== 0 })}>{amountDifferenceStr}</td>
          </>
        )}
      </tr>
      {details?.map?.((detail, idx) => (
        <PaymentDetail
          key={idx}
          type={detail.type}
          description={detail.description}
          amount={detail.amount}
          currency={currency}
          precision={precision}
        />
      ))}
    </>
  );
};
PaymentMethod.propTypes = {
  paymentMethod: PropTypes.string.isRequired,
  amountExpected: PropTypes.number.isRequired,
  amountCounted: PropTypes.number,
  currency: PropTypes.string.isRequired,
  precision: PropTypes.number.isRequired,
  details: PropTypes.array,
  onCountedAmountChanged: PropTypes.func.isRequired,
};

//
//
//
//
//

const PaymentDetail = ({ type, description, amount, currency, precision }) => {
  const amountStr = formatAmountToHumanReadableStr({ amount, currency, precision });
  const descriptionEff = getPaymentDetailTypeCaption(type) + (description ? ' ' + description : '');
  return (
    <tr className="line-level2">
      <td className="description-col">{descriptionEff}</td>
      <td className="amt">{amountStr}</td>
    </tr>
  );
};

PaymentDetail.propTypes = {
  type: PropTypes.string.isRequired,
  description: PropTypes.string,
  amount: PropTypes.number.isRequired,
  currency: PropTypes.string.isRequired,
  precision: PropTypes.number.isRequired,
};

//
//
//
//
//

const useJournalSummary = ({ posTerminalId }) => {
  const [journalSummary, setJournalSummary] = useState(null);

  useEffect(() => {
    if (!journalSummary) {
      setJournalSummary({ isLoading: true });
      getJournalSummary({ posTerminalId }).then(setJournalSummary);
    }
  }, [posTerminalId]);

  return journalSummary;
};

//
//
//
//
//

export default POSCashJournalClosingModal;
