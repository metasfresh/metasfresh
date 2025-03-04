// IMPORTANT: keep in sync with de.metas.pos.rest_api.json.JsonCashJournalSummary.JsonPaymentDetailType
import { trl } from '../../../../utils/translations';

export const PAYMENT_DETAIL_TYPE_OPENING_BALANCE = 'OPENING_BALANCE';
export const PAYMENT_DETAIL_TYPE_CLOSING_DIFFERENCE = 'CLOSING_DIFFERENCE';
export const PAYMENT_DETAIL_TYPE_CASH_PAYMENTS = 'CASH_PAYMENTS';
export const PAYMENT_DETAIL_TYPE_CASH_IN = 'CASH_IN';
export const PAYMENT_DETAIL_TYPE_CASH_OUT = 'CASH_OUT';

export const getPaymentDetailTypeCaption = (type) => {
  return trl(`pos.closeCashJournal.details.type.${type}`);
};
