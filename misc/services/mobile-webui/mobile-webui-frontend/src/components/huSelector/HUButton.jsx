import React from 'react';
import PropTypes from 'prop-types';
import { isNullOrUndefined } from '../../utils';

const ATTRIBUTE_CODES_TO_DISPLAY = ['HU_BestBeforeDate', 'Lot-Nummer'];
const ATTRIBUTE_CODES_TO_MAX_LENGTH = {
  HU_BestBeforeDate: 10,
};

const HUButton = ({ handlingUnitInfo, onClick }) => {
  const getProductCaption = () => {
    const product = getSingleProduct();

    if (!product) {
      return '';
    }
    return `${product.productValue} - ${product.productName}`;
  };

  const getAttributes = () => {
    if (
      !handlingUnitInfo.attributes2 ||
      !handlingUnitInfo.attributes2.list ||
      !handlingUnitInfo.attributes2.list.length
    ) {
      return '';
    }

    return handlingUnitInfo.attributes2.list.filter((attribute) => ATTRIBUTE_CODES_TO_DISPLAY.includes(attribute.code));
  };
  const getAttributeDisplayValue = (attribute) => {
    if (isNullOrUndefined(attribute.value)) {
      return '-';
    }
    const stringValue = String(attribute.value);
    const maxLength = ATTRIBUTE_CODES_TO_MAX_LENGTH[attribute.code] || stringValue.length;
    return stringValue.substring(0, maxLength);
  };

  const getSingleProduct = () => {
    if (!handlingUnitInfo.products || !handlingUnitInfo.products.length || handlingUnitInfo.products.length !== 1) {
      return undefined;
    }

    return handlingUnitInfo.products[0];
  };

  const getQtyDisplayValue = () => {
    const productInfo = getSingleProduct();

    if (!productInfo) {
      return '';
    }
    const qtyCUs = productInfo.qty;

    if (isNullOrUndefined(qtyCUs)) {
      return '';
    }

    const packagingItemPart = handlingUnitInfo.packingInstructionName
      ? `${handlingUnitInfo.packingInstructionName} x `
      : '';

    if (!handlingUnitInfo.numberOfAggregatedHUs || handlingUnitInfo.numberOfAggregatedHUs === 1) {
      return `${packagingItemPart}${qtyCUs} ${productInfo.uom}`;
    }

    const nrOfCUsPerTUs = qtyCUs / handlingUnitInfo.numberOfAggregatedHUs;
    return `${handlingUnitInfo.numberOfAggregatedHUs} ${handlingUnitInfo.packingInstructionName} x ${nrOfCUsPerTUs} ${productInfo.uom}`;
  };

  return (
    <button className="hu-button is-outlined is-fullwidth complete-btn" onClick={onClick}>
      <div className="attribute-container">
        <div className="hu-row-value" style={{ flex: '30%' }}>
          <span>{handlingUnitInfo.id}</span>
        </div>
        <div className="hu-row-value" style={{ flex: '10%' }}>
          <span>{handlingUnitInfo.jsonHUType}</span>
        </div>
        <div className="hu-row-value" style={{ flex: '50%', justifyContent: 'flex-end' }}>
          <span>{getQtyDisplayValue()}</span>
        </div>
      </div>
      <div className={'hu-button-title'}>
        <span>{getProductCaption()}</span>
      </div>
      <div className="attribute-container">
        {getAttributes().map((attribute, index) => (
          <div key={index} className="hu-row-info">
            <div className="hu-row-label">
              <span>{attribute.caption}:</span>
            </div>
            <div className="hu-row-value">
              <span>{getAttributeDisplayValue(attribute)}</span>
            </div>
          </div>
        ))}
      </div>
    </button>
  );
};

HUButton.propTypes = {
  handlingUnitInfo: PropTypes.object.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default HUButton;
