import { getPaymentMethodCaption } from '../constants/paymentMethods';

export const getPaymentSummaryLine = ({ paymentMethod, statusDetails }) => {
  return getPaymentMethodCaption({ paymentMethod }) + (statusDetails ? ' ' + statusDetails : '');
};
