import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { v4 as uuidv4 } from 'uuid';
import { useDispatch } from 'react-redux';
import Spinner from '../../../../components/Spinner';
import ProductSearchBar from '../order_panel/ProductSearchBar';
import ProductButton from '../order_panel/ProductButton';
import ReturnLine from './ReturnLine';
import ReturnQtyModal from './ReturnQtyModal';
import ReturnConfirmModal from './ReturnConfirmModal';
import { useProducts } from '../../actions/products';
import { usePOSTerminal } from '../../actions/posTerminal';
import { closePanelAction } from '../../actions/ui';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { trl } from '../../../../utils/translations';
import './POSReturnPanel.scss';

const _ = (key) => trl(`pos.return.${key}`);

// The price UOM: the catch-weight UOM when the product is priced by catch weight, else the product's own UOM
// — mirrors the server-side resolution in POSProduct#getPriceUom (POSReturnService#toReturnLine).
const extractPriceUom = (product) => product.catchWeightUomSymbol ?? product.uomSymbol;

/**
 * A cashier taking back returned products at the till — no original receipt needed. The mobile UI sends
 * product + qty (+ this cart's own externalId) only: the till's current price and its tax rate are always
 * resolved server-side. The return cart is local component state (never the sale-order cart/reducer).
 */
const POSReturnPanel = ({ disabled }) => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal();
  const pricePrecision = posTerminal?.pricePrecision ?? 2;
  const currencyPrecision = posTerminal?.currencyPrecision ?? 2;

  const [externalId] = useState(() => uuidv4());
  const [lines, setLines] = useState([]);
  const [editingLineIndex, setEditingLineIndex] = useState(null);
  const [isConfirming, setConfirming] = useState(false);

  const addLine = (product) => {
    const uom = extractPriceUom(product);
    const qty = product.catchWeight ?? 1;
    setLines((prevLines) => [
      ...prevLines,
      {
        uuid: uuidv4(),
        productId: product.id,
        productName: product.name,
        qty,
        uom,
        price: product.price,
        currencySymbol: product.currencySymbol,
      },
    ]);
  };

  const products = useProducts({ onBarcodeResult: (product) => addLine(product) });

  const isEnabled = !disabled && !isConfirming;

  const setLineQty = ({ index, qty }) => {
    setLines((prevLines) => prevLines.map((line, i) => (i === index ? { ...line, qty } : line)));
    setEditingLineIndex(null);
  };

  const totalAmt = lines.reduce((sum, line) => sum + line.qty * line.price, 0);

  const onCancel = () => {
    if (isConfirming) return;
    dispatch(closePanelAction());
  };

  const onConfirmed = () => {
    posTerminal.reload(); // the drawer/journal now carries the refund's CASH_INOUT line
    setLines([]);
    dispatch(closePanelAction());
  };

  return (
    <div className="pos-content pos-return-panel" data-testid="pos-return-panel">
      <div className="current-return">
        <div className="lines-container">
          {lines.map((line, index) => (
            <ReturnLine
              key={line.uuid}
              productName={line.productName}
              qty={line.qty}
              uom={line.uom}
              price={line.price}
              currencySymbol={line.currencySymbol}
              pricePrecision={pricePrecision}
              currencyPrecision={currencyPrecision}
              onClick={() => isEnabled && setEditingLineIndex(index)}
            />
          ))}
        </div>
        <div className="summary">
          <div className="summary-line totalAmt" data-testid="pos-return-total-amount">
            {_('totalAmt')}:{' '}
            {formatAmountToHumanReadableStr({
              amount: totalAmt,
              currency: posTerminal?.currencySymbol,
              precision: currencyPrecision,
            })}
          </div>
        </div>
        <div className="actions">
          <button
            className="button is-large"
            data-testid="pos-return-cancel-button"
            disabled={isConfirming}
            onClick={onCancel}
          >
            {_('actions.cancel')}
          </button>
          <button
            className="button is-large is-success"
            data-testid="pos-return-payout-button"
            disabled={!isEnabled || lines.length === 0}
            onClick={() => setConfirming(true)}
          >
            {_('actions.payout')}
          </button>
        </div>
      </div>
      <div className="products-container">
        <ProductSearchBar
          queryString={products.queryString}
          onQueryStringChanged={products.setQueryString}
          isEnabled={isEnabled}
        />
        <div className="products">
          {products.list.map((product) => (
            <ProductButton
              key={product.id}
              name={product.name}
              price={product.price}
              currencySymbol={product.currencySymbol}
              uomSymbol={extractPriceUom(product)}
              disabled={!isEnabled}
              onClick={() => addLine(product)}
            />
          ))}
          {products.isLoading && <Spinner />}
        </div>
      </div>

      {editingLineIndex != null && (
        <ReturnQtyModal
          uom={lines[editingLineIndex].uom}
          initialQty={lines[editingLineIndex].qty}
          onOk={({ qty }) => setLineQty({ index: editingLineIndex, qty })}
          onCancel={() => setEditingLineIndex(null)}
        />
      )}

      {isConfirming && (
        <ReturnConfirmModal
          posTerminalId={posTerminal.id}
          externalId={externalId}
          lines={lines}
          totalAmt={totalAmt}
          currencySymbol={posTerminal?.currencySymbol}
          currencyPrecision={currencyPrecision}
          onConfirmed={onConfirmed}
          onCancel={() => setConfirming(false)}
        />
      )}
    </div>
  );
};

POSReturnPanel.propTypes = {
  disabled: PropTypes.bool,
};

export default POSReturnPanel;
