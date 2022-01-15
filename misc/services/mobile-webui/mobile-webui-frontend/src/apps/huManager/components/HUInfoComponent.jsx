import React from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

export function HUInfoComponent(props) {
  const { handlingUnitInfo } = props;

  return (
    <div className="pt-3 section">
      <div className="centered-text is-size-5">
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('huManager.HU')}
          </div>
          <div className="column is-half has-text-left pb-0">{handlingUnitInfo.displayName}</div>
        </div>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('huManager.barcode')}
          </div>
          <div className="column is-half has-text-left pb-0">{handlingUnitInfo.barcode}</div>
        </div>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('huManager.locator')}
          </div>
          <div className="column is-half has-text-left pb-0">{handlingUnitInfo.locatorValue}</div>
        </div>
        {handlingUnitInfo.products.map((product) => (
          <div key={product.productValue}>
            <div className="columns is-mobile">
              <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
                {counterpart.translate('huManager.product')}
              </div>
              <div className="column is-half has-text-left pb-0">
                {product.productName} ({product.productValue})
              </div>
            </div>
            <div className="columns is-mobile">
              <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
                {counterpart.translate('huManager.qty')}
              </div>
              <div className="column is-half has-text-left pb-0">
                {product.qty} {product.uom}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

HUInfoComponent.propTypes = {
  handlingUnitInfo: PropTypes.object.isRequired,
};
