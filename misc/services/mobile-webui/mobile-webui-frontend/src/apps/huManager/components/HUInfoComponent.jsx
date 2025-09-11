import React from 'react';
import PropTypes from 'prop-types';
import { trl } from '../../../utils/translations';
import { toQRCodeDisplayable } from '../../../utils/qrCode/hu';
import { computeTestId } from '../../../utils/testing_support';

export const HUInfoComponent = ({ handlingUnitInfo, currentLocatorQRCode }) => {
  const layoutSections = handlingUnitInfo.layoutSections;
  const clearanceStatus = handlingUnitInfo.clearanceStatus ? handlingUnitInfo.clearanceStatus.caption : '';
  const clearanceStatusKey = handlingUnitInfo.clearanceStatus?.key;
  const { clearanceNote } = handlingUnitInfo;

  let isProductsAlreadyDisplayed = false;

  return (
    <table className="table view-header is-size-6" data-testid="huinfo-table">
      <tbody>
        {layoutSections.map((section) => {
          switch (section) {
            case 'DisplayName': {
              return (
                <React.Fragment key="DisplayName">
                  <Row captionKey="huManager.HU" value={handlingUnitInfo.displayName} />
                  <Row
                    captionKey="huManager.numberOfAggregatedHUs"
                    value={handlingUnitInfo.numberOfAggregatedHUs}
                    hidden={!(handlingUnitInfo.numberOfAggregatedHUs && handlingUnitInfo.numberOfAggregatedHUs > 1)}
                  />
                </React.Fragment>
              );
            }
            case 'QRCode': {
              return (
                <Row key="QRCode" captionKey="huManager.qrCode" value={toQRCodeDisplayable(handlingUnitInfo.qrCode)} />
              );
            }
            case 'EANCode': {
              return (
                <Row
                  key="EANCode"
                  captionKey="huManager.eanCode"
                  value={handlingUnitInfo.eanCode}
                  hidden={!handlingUnitInfo.eanCode}
                />
              );
            }
            case 'Locator': {
              return (
                <Row
                  key="Locator"
                  captionKey="huManager.locator"
                  value={computeLocatorCaption({ handlingUnitInfo, currentLocatorQRCode })}
                />
              );
            }
            case 'HUStatus': {
              return (
                <Row key="HUStatus" captionKey="huManager.HUStatus" value={computeHUStatusCaption(handlingUnitInfo)} />
              );
            }
            case 'ClearanceStatus': {
              return (
                <React.Fragment key="ClearanceStatus">
                  <Row
                    captionKey="huManager.clearanceStatus"
                    value={clearanceStatus}
                    internalValue={clearanceStatusKey}
                    hidden={!clearanceStatus}
                  />
                  <Row captionKey="huManager.clearanceNote" value={clearanceNote} hidden={!clearanceNote} />
                </React.Fragment>
              );
            }
            case 'Product':
            case 'Qty': {
              if (isProductsAlreadyDisplayed) return null;
              isProductsAlreadyDisplayed = true;
              return (
                <ProductsListRows key="Products" products={handlingUnitInfo.products} layoutSections={layoutSections} />
              );
            }
            case 'Attributes': {
              if (handlingUnitInfo?.attributes2?.list) {
                return (
                  <React.Fragment key="Attributes">
                    {handlingUnitInfo.attributes2.list.map((attribute) => (
                      <AttributeRow
                        key={attribute.code}
                        testId={`attribute-${attribute.code}-value`}
                        caption={attribute.caption}
                        value={attribute.value}
                        valueDisplay={attribute.valueDisplay}
                      />
                    ))}
                  </React.Fragment>
                );
              } else {
                return null;
              }
            }
          }
        })}
      </tbody>
    </table>
  );
};

HUInfoComponent.propTypes = {
  handlingUnitInfo: PropTypes.object.isRequired,
  currentLocatorQRCode: PropTypes.object,
};

//
//
//
//
//

const computeHUStatusCaption = (handlingUnitInfo) => {
  let result = handlingUnitInfo.huStatusCaption;
  if (handlingUnitInfo.isDisposalPending) {
    result += ' / ' + trl('huManager.disposePendingStatus');
  }
  return result;
};

const computeLocatorCaption = ({ handlingUnitInfo, currentLocatorQRCode }) => {
  if (currentLocatorQRCode?.displayable) {
    return '(' + currentLocatorQRCode?.displayable + ')';
  } else if (handlingUnitInfo?.locatorValue) {
    return handlingUnitInfo.locatorValue;
  } else {
    return '-';
  }
};

//
//
//
//
//

const ProductsListRows = ({ products, layoutSections }) => {
  return (
    <>
      {products?.map((product) => (
        <ProductRows key={product.productValue} product={product} layoutSections={layoutSections} />
      ))}
    </>
  );
};
ProductsListRows.propTypes = {
  products: PropTypes.array,
  layoutSections: PropTypes.array.isRequired,
};

//
//
//
//
//

const ProductRows = ({ product, layoutSections }) => {
  return (
    <>
      {layoutSections?.map((section) => {
        switch (section) {
          case 'Product': {
            return (
              <Row
                key="Product"
                captionKey="huManager.product"
                value={`${product.productName} (${product.productValue})`}
              />
            );
          }
          case 'Qty': {
            return <Row key="Qty" captionKey="huManager.qty" value={`${product.qty} ${product.uom}`} />;
          }
        }
      })}
    </>
  );
};

ProductRows.propTypes = {
  product: PropTypes.shape({
    productValue: PropTypes.string.isRequired,
    productName: PropTypes.string.isRequired,
    qty: PropTypes.string.isRequired, // it's string instead of number because it comes preformatted from the backend
    uom: PropTypes.string.isRequired,
  }).isRequired,
  layoutSections: PropTypes.array.isRequired,
};

//
//
//
//
//

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

//
//
//
//
//

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
