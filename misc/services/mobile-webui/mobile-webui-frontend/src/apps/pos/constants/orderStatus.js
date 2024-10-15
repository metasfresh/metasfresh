export const ORDER_STATUS_DRAFTED = 'DR';
export const ORDER_STATUS_WAITING_PAYMENT = 'WP';
export const ORDER_STATUS_COMPLETED = 'CO';
export const ORDER_STATUS_VOIDED = 'VO';

// IMPORTANT: keep in sync with de.metas.pos.POSOrderStatus.OPEN_STATUSES
const OPEN_ORDER_STATUSES = [ORDER_STATUS_DRAFTED, ORDER_STATUS_WAITING_PAYMENT, ORDER_STATUS_COMPLETED];

export const isOpenOrderStatus = (status) => {
  return OPEN_ORDER_STATUSES.includes(status);
};
