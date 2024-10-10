export const ORDER_STATUS_DRAFTED = 'DR';
export const ORDER_STATUS_WAITING_PAYMENT = 'WP';
export const ORDER_STATUS_COMPLETED = 'CO';
export const ORDER_STATUS_VOIDED = 'VO';

export const isOpenOrderStatus = (status) => {
  return status === ORDER_STATUS_DRAFTED || status === ORDER_STATUS_WAITING_PAYMENT;
};
