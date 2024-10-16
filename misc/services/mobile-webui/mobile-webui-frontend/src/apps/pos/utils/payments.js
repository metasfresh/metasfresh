import { getPaymentMethodCaption } from '../constants/paymentMethods';

export const getPaymentSummaryLine = ({ paymentMethod, status, statusDetails }) => {
  return (
    getPaymentMethodCaption({ paymentMethod }) +
    (statusDetails ? ' ' + statusDetails : '') +
    (status ? ' (' + status + ')' : '')
  );
};
