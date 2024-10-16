import { getPaymentMethodCaption } from '../constants/paymentMethods';
import { trl } from '../../../utils/translations';

export const getPaymentSummaryLine = ({ paymentMethod, status, statusDetails }) => {
  const statusStr = status ? trl(`pos.payment.status.${status}`) : null;

  return (
    getPaymentMethodCaption({ paymentMethod }) +
    (statusDetails ? ' ' + statusDetails : '') +
    (statusStr ? ' (' + statusStr + ')' : '')
  );
};
