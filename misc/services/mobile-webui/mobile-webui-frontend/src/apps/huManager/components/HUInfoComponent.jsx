import React from 'react';
import PropTypes from 'prop-types';
import { trl } from '../../../utils/translations';
import { toQRCodeDisplayable } from '../../../utils/qrCode/hu';
import { computeTestId } from '../../../utils/testing_support';

export const HUInfoComponent = ({ handlingUnitInfo, currentLocatorQRCode }) => {
  const clearanceStatus = handlingUnitInfo.clearanceStatus ? handlingUnitInfo.clearanceStatus.caption : '';
  const clearanceStatusKey = handlingUnitInfo.clearanceStatus?.key;
  const { clearanceNote } = handlingUnitInfo;

  return (
    <table className="table view-header is-size-6" data-testid="huinfo-table">
      <tbody>
        <Row captionKey="huManager.HU" value={handlingUnitInfo.displayName} />
        {handlingUnitInfo.numberOfAggregatedHUs && handlingUnitInfo.numberOfAggregatedHUs > 1 ? (
            <tr>
                <th>{trl('huManager.numberOfAggregatedHUs')}</th>
                <td>{handlingUnitInfo.numberOfAggregatedHUs}</td>
            </tr>
        ) : undefined}
        <Row captionKey="huManager.qrCode" value={toQRCodeDisplayable(handlingUnitInfo.qrCode)} />
        <Row captionKey="huManager.locator" value={computeLocatorCaption({ handlingUnitInfo, currentLocatorQRCode })} />
        <Row captionKey="huManager.HUStatus" value={computeHUStatusCaption(handlingUnitInfo)} />
        <Row
          captionKey="huManager.clearanceStatus"
          value={clearanceStatus}
          internalValue={clearanceStatusKey}
          hidden={!clearanceStatus}
        />
        <Row captionKey="huManager.clearanceNote" value={clearanceNote} hidden={!clearanceNote} />
        {handlingUnitInfo.products.map((product) => (
          <ProductInfoRows key={product.productValue} product={product} />
        ))}
        {handlingUnitInfo.attributes2 &&
          handlingUnitInfo.attributes2.list &&
          handlingUnitInfo.attributes2.list.map((attribute) => (
            <AttributeRow
              key={attribute.code}
              testId={`attribute-${attribute.code}-value`}
              caption={attribute.caption}
              value={attribute.value}
              valueDisplay={attribute.valueDisplay}
            />
          ))}
      </tbody>
    </table>
  );
};

HUInfoComponent.propTypes = {
  handlingUnitInfo: PropTypes.object.isRequired,
  currentLocatorQRCode: PropTypes.object,
};

const computeHUStatusCaption = (handlingUnitInfo) => {
  let result = handlingUnitInfo.huStatusCaption;
  if (handlingUnitInfo.isDisposalPending) {
    result += ' / ' + trl('huManager.disposePendingStatus');
  }
  return result;
};

const computeLocatorCaption = ({ handlingUnitInfo, currentLocatorQRCode }) => {
  if (handlingUnitInfo?.locatorValue) {
    return handlingUnitInfo.locatorValue;
  } else if (currentLocatorQRCode?.displayable) {
    return '(' + currentLocatorQRCode?.displayable + ')';
  } else {
    return '-';
  }
};

const ProductInfoRows = ({ product }) => {
  return (
    <>
      <Row captionKey="huManager.product" value={`${product.productName} (${product.productValue})`} />
      <Row captionKey="huManager.qty" value={`${product.qty} ${product.uom}`} />
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

const AttributeRow = ({ caption, value, valueDisplay, testId }) => {
  // hide rows with empty values
  if (value == null) {
    return null;
  }

  return <Row caption={caption} value={`${valueDisplay ?? value}`} testId={testId} />;
};

AttributeRow.propTypes = {
  caption: PropTypes.string.isRequired,
  value: PropTypes.any,
  valueDisplay: PropTypes.any,
  testId: PropTypes.string,
};

const Row = ({ captionKey, caption: captionParam, value, internalValue, hidden, testId: testIdParam }) => {
  if (hidden) return null;

  const caption = captionParam ? captionParam : trl(captionKey);
  const testId = computeTestId({ testIdParam, captionKey, suffix: 'value' });

  return (
    <tr>
      <th>{caption}</th>
      <td data-testid={testId} data-internalvalue={internalValue}>
        {value}
      </td>
    </tr>
  );
};
Row.propTypes = {
  captionKey: PropTypes.string,
  caption: PropTypes.string,
  value: PropTypes.any,
  internalValue: PropTypes.any,
  hidden: PropTypes.bool,
  testId: PropTypes.string,
};
