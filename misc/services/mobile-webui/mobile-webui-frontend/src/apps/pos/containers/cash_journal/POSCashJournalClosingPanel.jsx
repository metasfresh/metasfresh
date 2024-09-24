import React, { useEffect, useState } from 'react';
import '../../styles/pos-input-panel.scss';
import './POSCashJournalClosingPanel.scss';
import cx from 'classnames';
import { usePOSTerminal } from '../../actions';
import { getJournalSummary } from '../../api/posJournal';
import { PAYMENT_METHOD_CASH } from '../../utils/paymentMethods';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import PropTypes from 'prop-types';

const POSCashJournalClosingPanel = () => {
  const posTerminal = usePOSTerminal();
  const [cashClosingBalance, setCashClosingBalance] = useState(0);
  const [closingNote, setClosingNote] = useState('');

  const onCloseClick = () => {
    posTerminal.closeJournal({ cashClosingBalance, closingNote });
  };
  const onCancelClick = () => {
    posTerminal.cancelClosing();
  };

  return (
    <div className="pos-content pos-input-panel-container pos-journal-closing-panel">
      <div className="pos-input-panel">
        <div className="pos-input-panel-header">Closing cash journal</div>
        <div className="pos-input-panel-content">
          <div className="line">
            <div className="field">
              <PaymentSummary
                cashAmountExpected={cashClosingBalance}
                onCashAmountExpectedChanged={setCashClosingBalance}
              />
            </div>
          </div>
          <div className="line">
            <div className="caption">Closing note</div>
            <div className="field">
              <textarea value={closingNote} onChange={(e) => setClosingNote(e.target.value)} />
            </div>
          </div>
        </div>
        <div className="pos-input-panel-bottom">
          <button onClick={onCloseClick}>Close</button>
          <button onClick={onCancelClick}>Cancel</button>
        </div>
      </div>
    </div>
  );
};

//
//
//
//
//

const PaymentSummary = ({ cashAmountExpected, onCashAmountExpectedChanged }) => {
  const journalSummary = useJournalSummary();

  return (
    <table className="payment-summary">
      <thead>
        <tr>
          <th>Payment Method</th>
          <th className="amt">Booked</th>
          <th className="amt">Counted</th>
          <th className="amt">Difference</th>
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
        <td className="description-col">{paymentMethod}</td>
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

const PaymentDetail = ({ description, amount, currency, precision }) => {
  const amountStr = formatAmountToHumanReadableStr({ amount, currency, precision });
  return (
    <tr className="line-level2">
      <td className="description-col">{description}</td>
      <td className="amt">{amountStr}</td>
    </tr>
  );
};
PaymentDetail.propTypes = {
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

const useJournalSummary = () => {
  const [journalSummary, setJournalSummary] = useState(null);

  useEffect(() => {
    if (!journalSummary) {
      setJournalSummary({ isLoading: true });
      getJournalSummary().then(setJournalSummary);
    }
  }, []);

  return journalSummary;
};

//
//
//
//
//

export default POSCashJournalClosingPanel;
