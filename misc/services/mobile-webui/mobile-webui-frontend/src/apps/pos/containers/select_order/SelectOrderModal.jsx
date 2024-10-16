import React from 'react';
import { useDispatch } from 'react-redux';
import cx from 'classnames';
import { usePOSTerminal } from '../../actions/posTerminal';
import { closeModalAction, MODAL_SelectOrders } from '../../actions/ui';
import useEscapeKey from '../../../../hooks/useEscapeKey';
import { setCurrentOrder, useCurrentOrder, useOpenOrdersArray } from '../../actions/orders';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.selectOrder.${key}`);

const SelectOrderModal = () => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal();
  const currentOrder = useCurrentOrder({ posTerminalId: posTerminal.id });
  const currentOrderUUID = currentOrder.uuid;
  const orders = useOpenOrdersArray();

  const closeModal = () => {
    dispatch(closeModalAction({ ifModal: MODAL_SelectOrders }));
  };
  const onOrderSelected = (order_uuid) => {
    dispatch(setCurrentOrder({ order_uuid }));
    closeModal();
  };

  const onCancel = () => {
    closeModal();
  };

  useEscapeKey(onCancel);

  if (!orders.length) return null;

  return (
    <div className="modal is-active pos-select-terminal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">{_('title')}</p>
          <button className="delete" aria-label="close" onClick={onCancel}></button>
        </header>
        <section className="modal-card-body">
          {orders.map((order) => (
            <button className="button is-large" key={order.uuid} onClick={() => onOrderSelected(order.uuid)}>
              <span className="icon is-small">
                <i className={cx('fas', { 'fa-check': order.uuid === currentOrderUUID })}></i>
              </span>
              <span>{computeOrderSummary({ order, currencyPrecision: posTerminal.currencyPrecision })}</span>
            </button>
          ))}
        </section>
      </div>
    </div>
  );
};

export default SelectOrderModal;

//
//
//
//
//
//

const computeOrderSummary = ({ order, currencyPrecision }) => {
  const amountStr = formatAmountToHumanReadableStr({
    amount: order.totalAmt,
    currency: order.currencySymbol,
    precision: currencyPrecision,
  });

  const statusStr = order.status;
  const documentNo = order.documentNo ?? 'new';

  return amountStr + ' | ' + statusStr + ' | ' + documentNo;
};
