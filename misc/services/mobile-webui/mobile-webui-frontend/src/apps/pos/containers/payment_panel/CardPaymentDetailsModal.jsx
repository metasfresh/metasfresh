import React from 'react';
import PropTypes from 'prop-types';
import './PaymentDetailsModal.scss';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import Spinner from '../../../../components/Spinner';

const CardPaymentDetailsModal = ({ currency, precision, payAmount, cardReaderName }) => {
  const payAmountStr = formatAmountToHumanReadableStr({ amount: payAmount, currency, precision });

  return (
    <div className="modal is-active payment-details-modal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">Waiting card payment</p>
        </header>
        <section className="modal-card-body">
          <div className="detail-line">
            <div className="detail-caption">Amount to pay</div>
            <div className="detail-value">{payAmountStr}</div>
          </div>
          {cardReaderName && (
            <div className="detail-line">
              <div className="detail-caption">Card reader</div>
              <div className="detail-value">{cardReaderName}</div>
            </div>
          )}
          <div className="detail-line-spinner">
            <Spinner />
          </div>
        </section>
      </div>
    </div>
  );
};
CardPaymentDetailsModal.propTypes = {
  currency: PropTypes.string.isRequired,
  precision: PropTypes.number.isRequired,
  payAmount: PropTypes.number.isRequired,
  cardReaderName: PropTypes.string,
};

export default CardPaymentDetailsModal;
