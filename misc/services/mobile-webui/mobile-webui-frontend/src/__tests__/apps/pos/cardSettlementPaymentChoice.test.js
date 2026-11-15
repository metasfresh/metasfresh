import {
  CARD_STATUS_ABANDONED,
  CARD_STATUS_APPROVED,
  CARD_STATUS_BOOKING_ERROR,
  CARD_STATUS_CANCELLED,
  CARD_STATUS_CREDIT_REFUND_PENDING,
  CARD_STATUS_FAILED,
  CARD_STATUS_PENDING,
  CARD_STATUS_REFUNDED,
  CARD_STATUS_SUCCESSFUL,
  CARD_STATUS_UNKNOWN,
  isBlockingCardSettlement,
  isNewPaymentAllowed,
} from '../../../apps/pos/containers/invoice_settlement/CardSettlementStatusPanel';

const settlement = (status, isAbandoned = false) => ({ status, isAbandoned });

describe('isNewPaymentAllowed (the payment choice in the card stage of the invoice settlement dialog)', () => {
  it('is offered when the card payment did not go through or was released', () => {
    expect(isNewPaymentAllowed(settlement(CARD_STATUS_FAILED))).toBe(true);
    expect(isNewPaymentAllowed(settlement(CARD_STATUS_CANCELLED))).toBe(true);
    expect(isNewPaymentAllowed(settlement(CARD_STATUS_ABANDONED, true))).toBe(true);
  });

  it('is offered when a card payment released as abandoned is approved later, because it does not block the invoice', () => {
    const approvedAfterRelease = settlement(CARD_STATUS_APPROVED, true);
    expect(isBlockingCardSettlement(approvedAfterRelease)).toBe(false);
    expect(isNewPaymentAllowed(approvedAfterRelease)).toBe(true);

    const bookingErrorAfterRelease = settlement(CARD_STATUS_BOOKING_ERROR, true);
    expect(isBlockingCardSettlement(bookingErrorAfterRelease)).toBe(false);
    expect(isNewPaymentAllowed(bookingErrorAfterRelease)).toBe(true);
  });

  it('is not offered while the card payment blocks the invoice', () => {
    [CARD_STATUS_PENDING, CARD_STATUS_UNKNOWN, CARD_STATUS_APPROVED, CARD_STATUS_BOOKING_ERROR].forEach((status) => {
      expect(isBlockingCardSettlement(settlement(status))).toBe(true);
      expect(isNewPaymentAllowed(settlement(status))).toBe(false);
    });
  });

  it('is not offered when the card payment was booked', () => {
    [CARD_STATUS_SUCCESSFUL, CARD_STATUS_CREDIT_REFUND_PENDING, CARD_STATUS_REFUNDED].forEach((status) => {
      expect(isNewPaymentAllowed(settlement(status))).toBe(false);
      expect(isNewPaymentAllowed(settlement(status, true))).toBe(false);
    });
  });

  it('is not offered while there is no card payment yet', () => {
    expect(isNewPaymentAllowed(null)).toBe(false);
    expect(isNewPaymentAllowed(undefined)).toBe(false);
  });
});
