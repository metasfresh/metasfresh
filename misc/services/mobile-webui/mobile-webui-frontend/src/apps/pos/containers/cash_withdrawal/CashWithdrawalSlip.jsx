import React, { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import printJS from 'print-js';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { getLanguage, trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.cashWithdrawal.slip.${key}`);

// The slip is printed from print-js' own iframe, which has none of the app's stylesheets.
const PRINT_STYLE = `
  .slip { font-family: sans-serif; font-size: 12pt; width: 72mm; }
  .slip-title { font-weight: bold; font-size: 14pt; margin-bottom: 4mm; }
  .slip-line { display: flex; justify-content: space-between; gap: 4mm; }
  .slip-caption { color: #555; flex-shrink: 0; }
  .slip-value { min-width: 0; font-weight: bold; text-align: right; overflow-wrap: break-word; }
  .slip-blank-line { height: 12mm; }
  .slip-signature { border-top: 1px solid #000; margin-top: 14mm; padding-top: 1mm; font-size: 9pt; }
`;

const formatDateTime = (date) =>
  new Date(date).toLocaleString(getLanguage(), {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  });

/**
 * The receipt of a cash withdrawal, signed by whoever received the money. Printed once when shown; can be reprinted.
 */
const CashWithdrawalSlip = ({ withdrawal, currency, precision, onClose }) => {
  const slipRef = useRef(null);

  const print = () => {
    printJS({ printable: slipRef.current.outerHTML, type: 'raw-html', style: PRINT_STYLE });
  };

  useEffect(() => {
    print();
  }, []);

  const amountStr = formatAmountToHumanReadableStr({ amount: withdrawal.amount, currency, precision });

  return (
    <div className="modal is-active pos-cash-withdrawal-modal" data-testid="pos-cash-withdrawal-modal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <section className="modal-card-body">
          <div className="slip" ref={slipRef} data-testid="pos-cash-withdrawal-slip">
            <div className="slip-title">{_('title')}</div>
            <SlipLine caption={_('terminal')} value={withdrawal.terminal} testId="pos-cash-withdrawal-slip-terminal" />
            <SlipLine
              caption={_('date')}
              value={formatDateTime(withdrawal.date)}
              testId="pos-cash-withdrawal-slip-date"
            />
            <SlipLine caption={_('cashier')} value={withdrawal.cashier} testId="pos-cash-withdrawal-slip-cashier" />
            <SlipLine caption={_('category')} value={withdrawal.category} testId="pos-cash-withdrawal-slip-category" />
            <SlipLine caption={_('amount')} value={amountStr} testId="pos-cash-withdrawal-slip-amount" />
            <div className="slip-blank-line" />
            <div className="slip-received" data-testid="pos-cash-withdrawal-slip-received">
              {_('moneyReceived')}
            </div>
            <div className="slip-signature" data-testid="pos-cash-withdrawal-slip-signature">
              {_('signature')}
            </div>
          </div>
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            <button className="button is-large" data-testid="pos-cash-withdrawal-slip-print-button" onClick={print}>
              {_('actions.print')}
            </button>
            <button className="button is-large" data-testid="pos-cash-withdrawal-slip-close-button" onClick={onClose}>
              {_('actions.close')}
            </button>
          </div>
        </footer>
      </div>
    </div>
  );
};
CashWithdrawalSlip.propTypes = {
  withdrawal: PropTypes.shape({
    terminal: PropTypes.string.isRequired,
    date: PropTypes.string.isRequired,
    cashier: PropTypes.string.isRequired,
    category: PropTypes.string.isRequired,
    amount: PropTypes.number.isRequired,
  }).isRequired,
  currency: PropTypes.string,
  precision: PropTypes.number.isRequired,
  onClose: PropTypes.func.isRequired,
};

//
//
//

const SlipLine = ({ caption, value, testId }) => {
  return (
    <div className="slip-line">
      <span className="slip-caption">{caption}</span>
      <span className="slip-value" data-testid={testId}>
        {value}
      </span>
    </div>
  );
};
SlipLine.propTypes = {
  caption: PropTypes.string.isRequired,
  value: PropTypes.string.isRequired,
  testId: PropTypes.string.isRequired,
};

export default CashWithdrawalSlip;
