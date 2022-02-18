import React from 'react';
import PropTypes from 'prop-types';
import { trl } from '../../../utils/translations';
import { toQRCodeDisplayable } from '../../../utils/huQRCodes';

export const HUInfoComponent = ({ handlingUnitInfo }) => {
  return (
    <table className="table view-header is-size-6">
      <tbody>
        <tr>
          <th>{trl('huManager.HU')}</th>
          <td>{handlingUnitInfo.displayName}</td>
        </tr>
        <tr>
          <th>{trl('huManager.qrCode')}</th>
          <td>{toQRCodeDisplayable(handlingUnitInfo.qrCode)}</td>
        </tr>
        <tr>
          <th>{trl('huManager.locator')}</th>
          <td>{handlingUnitInfo.locatorValue}</td>
        </tr>
        {handlingUnitInfo.products.map((product) => (
          <ProductInfoRows key={product.productValue} product={product} />
        ))}
      </tbody>
    </table>
  );
};

HUInfoComponent.propTypes = {
  handlingUnitInfo: PropTypes.object.isRequired,
};

const ProductInfoRows = ({ product }) => {
  return (
    <>
      <tr>
        <th>{trl('huManager.product')}</th>
        <td>
          {product.productName} ({product.productValue})
        </td>
      </tr>
      <tr>
        <th>{trl('huManager.qty')}</th>
        <td>
          {product.qty} {product.uom}
        </td>
      </tr>
    </>
  );
};

ProductInfoRows.propTypes = {
  product: PropTypes.shape({
    productValue: PropTypes.string.isRequired,
    productName: PropTypes.string.isRequired,
    qty: PropTypes.string.isRequired, // it's string instead of number because it comes preformatted from the backend
    uom: PropTypes.string.isRequired,
  }).isRequired,
};
