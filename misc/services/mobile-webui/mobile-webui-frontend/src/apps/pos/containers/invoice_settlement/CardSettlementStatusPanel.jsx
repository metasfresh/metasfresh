import React from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import { trl } from '../../../../utils/translations';
import CardReleaseControls from '../card_release/CardReleaseControls';

const _ = (key, args) => trl(`pos.invoiceSettlement.card.${key}`, args);

export const CARD_STATUS_PENDING = 'PE';
export const CARD_STATUS_UNKNOWN = 'UN';
export const CARD_STATUS_APPROVED = 'AP';
export const CARD_STATUS_BOOKING_ERROR = 'BE';
export const CARD_STATUS_SUCCESSFUL = 'SU';
export const CARD_STATUS_CREDIT_REFUND_PENDING = 'CR';
export const CARD_STATUS_REFUNDED = 'RF';
export const CARD_STATUS_FAILED = 'FA';
export const CARD_STATUS_CANCELLED = 'CA';
export const CARD_STATUS_ABANDONED = 'AB';

/**
 * @returns whether the settlement blocks any further payment of its invoice (same rule as the backend's blocking index)
 */
export const isBlockingCardSettlement = (settlement) =>
  !!settlement &&
  !settlement.isAbandoned &&
  [CARD_STATUS_PENDING, CARD_STATUS_UNKNOWN, CARD_STATUS_APPROVED, CARD_STATUS_BOOKING_ERROR].includes(
    settlement.status
  );

/**
 * @returns whether the cashier may pay the invoice again, by card or in cash: the card payment did not go through or
 * was released, or it was released as abandoned and SumUp approved it later (being booked, or its booking failed) —
 * an abandoned settlement never blocks its invoice, see {@link isBlockingCardSettlement}
 */
export const isNewPaymentAllowed = (settlement) =>
  !!settlement &&
  ([CARD_STATUS_FAILED, CARD_STATUS_CANCELLED, CARD_STATUS_ABANDONED].includes(settlement.status) ||
    (!!settlement.isAbandoned && [CARD_STATUS_APPROVED, CARD_STATUS_BOOKING_ERROR].includes(settlement.status)));

/**
 * @returns whether the cashier may check / release the settlement (only a waiting or unknown one)
 */
export const isReleasableStatus = (settlement) =>
  !!settlement && [CARD_STATUS_PENDING, CARD_STATUS_UNKNOWN].includes(settlement.status) && !settlement.isAbandoned;

const getStatusMessage = ({ settlement, isStarting }) => {
  if (isStarting || !settlement) {
    return _('waiting');
  }

  switch (settlement.status) {
    case CARD_STATUS_PENDING:
      return _('waiting');
    case CARD_STATUS_UNKNOWN:
      return trl('pos.cardRelease.unknown');
    case CARD_STATUS_APPROVED:
      return _('bookingPending');
    case CARD_STATUS_BOOKING_ERROR:
      return _('bookingFailed');
    case CARD_STATUS_SUCCESSFUL:
      return _('paid', { docNo: settlement.documentNo });
    case CARD_STATUS_CREDIT_REFUND_PENDING:
      // the invoice was paid meanwhile: with an allocated part the card paid (the rest of) the invoice
      return Number(settlement.allocatedAmount) > 0 ? _('paidRemainderAsCustomerCredit') : _('bookedAsCustomerCredit');
    case CARD_STATUS_REFUNDED:
      return _('refunded');
    case CARD_STATUS_FAILED:
    case CARD_STATUS_CANCELLED:
      return _('failed');
    case CARD_STATUS_ABANDONED:
      return _('released');
    default:
      return _('otherStatus', { status: settlement.status });
  }
};

/**
 * The state of the invoice's card payment as the till shows it, plus the cashier's check / release of a waiting or
 * unknown one (enabled from the settlement's {@code releasableAt}), with the "not charged" confirmation.
 */
const CardSettlementStatusPanel = ({
  settlement,
  isStarting,
  isReleaseShown,
  isReleaseEnabled,
  onRelease,
  isConfirmationRequired,
  onConfirmRelease,
  onCancelConfirmation,
  releaseMessage,
  isDisabled,
}) => {
  const status = isStarting || !settlement ? CARD_STATUS_PENDING : settlement.status;
  const isWarning = [
    CARD_STATUS_UNKNOWN,
    CARD_STATUS_BOOKING_ERROR,
    CARD_STATUS_FAILED,
    CARD_STATUS_CANCELLED,
  ].includes(status);

  return (
    <div className="pos-card-settlement-status" data-testid="pos-card-settlement-status" data-status={status}>
      <div
        className={cx('status-message', { 'is-warning': isWarning })}
        data-testid="pos-card-settlement-status-message"
      >
        {status === CARD_STATUS_PENDING && <i className="fa-solid fa-spinner fa-spin status-icon" />}
        {getStatusMessage({ settlement, isStarting })}
      </div>
      <CardReleaseControls
        testIdPrefix="pos-card-settlement"
        isCheckFailed={!!settlement?.checkFailed}
        releaseMessage={releaseMessage}
        isReleaseShown={isReleaseShown}
        isReleaseEnabled={isReleaseEnabled}
        onRelease={onRelease}
        isConfirmationShown={isConfirmationRequired}
        onConfirmRelease={onConfirmRelease}
        onCancelConfirmation={onCancelConfirmation}
        isDisabled={isDisabled}
      />
    </div>
  );
};

CardSettlementStatusPanel.propTypes = {
  settlement: PropTypes.object,
  isStarting: PropTypes.bool,
  isReleaseShown: PropTypes.bool,
  isReleaseEnabled: PropTypes.bool,
  onRelease: PropTypes.func,
  isConfirmationRequired: PropTypes.bool,
  onConfirmRelease: PropTypes.func,
  onCancelConfirmation: PropTypes.func,
  releaseMessage: PropTypes.string,
  isDisabled: PropTypes.bool,
};

export default CardSettlementStatusPanel;
