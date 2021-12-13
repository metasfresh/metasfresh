import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { toastError } from '../../../utils/toast';
import { disposeHU, getDisposalReasonsArray, getHUByBarcode } from '../api';

import CodeScanner from '../../../containers/activities/scan/CodeScanner';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import QtyReasonsRadioGroup from '../../../components/QtyReasonsRadioGroup';

class InventoryDisposalScreen extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      handlingUnitInfo: null,
      disposalReasons: {},
      selectedDisposalReasonKey: null,
    };
  }

  componentDidMount() {
    this.loadDisposalReasons();
  }

  loadDisposalReasons = () => {
    getDisposalReasonsArray()
      .then((disposalReasons) => {
        this.setState({
          ...this.state,
          disposalReasons,
          selectedDisposalReasonKey: null,
        });
      })
      .catch((axiosError) => {
        //toastError({ axiosError });
        console.trace('Failed loading disposal reasons', axiosError);
      });
  };

  onHUBarcodeScanned = ({ scannedBarcode }) => {
    getHUByBarcode(scannedBarcode)
      .then((handlingUnitInfo) => {
        this.setState({
          ...this.state,
          handlingUnitInfo,
        });
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  onDisposalReasonSelected = (disposalReasonKey) => {
    this.setState({
      ...this.state,
      selectedDisposalReasonKey: disposalReasonKey,
    });
  };

  onDisposeClick = () => {
    const { dispatch } = this.props;
    const { handlingUnitInfo, selectedDisposalReasonKey } = this.state;
    disposeHU({
      huId: handlingUnitInfo.id,
      reasonCode: selectedDisposalReasonKey,
    })
      .then(() => {
        dispatch(push('/'));
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  render() {
    const { handlingUnitInfo } = this.state;

    if (handlingUnitInfo && handlingUnitInfo.id) {
      return this.renderHandlingUnitInfo();
    } else {
      return <CodeScanner onBarcodeScanned={this.onHUBarcodeScanned} />;
    }
  }

  renderHandlingUnitInfo = () => {
    const { handlingUnitInfo, disposalReasons, selectedDisposalReasonKey } = this.state;

    return (
      <div className="pt-3 section">
        <div className="centered-text is-size-5">
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
              {counterpart.translate('inventoryDisposal.HU')}
            </div>
            <div className="column is-half has-text-left pb-0">{handlingUnitInfo.displayName}</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
              {counterpart.translate('inventoryDisposal.barcode')}
            </div>
            <div className="column is-half has-text-left pb-0">{handlingUnitInfo.barcode}</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
              {counterpart.translate('inventoryDisposal.locator')}
            </div>
            <div className="column is-half has-text-left pb-0">{handlingUnitInfo.locatorValue}</div>
          </div>
          {handlingUnitInfo.products.map((product) => (
            <div key={product.productValue}>
              <div className="columns is-mobile">
                <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
                  {counterpart.translate('inventoryDisposal.product')}
                </div>
                <div className="column is-half has-text-left pb-0">
                  {product.productName} ({product.productValue})
                </div>
              </div>
              <div className="columns is-mobile">
                <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
                  {counterpart.translate('inventoryDisposal.qty')}
                </div>
                <div className="column is-half has-text-left pb-0">
                  {product.qty} {product.uom}
                </div>
              </div>
            </div>
          ))}

          <br />
          <QtyReasonsRadioGroup reasons={disposalReasons} onReasonSelected={this.onDisposalReasonSelected} />

          <br />
          <div className="mt-0">
            <button
              className="button is-outlined complete-btn"
              onClick={this.onDisposeClick}
              disabled={!selectedDisposalReasonKey}
            >
              <ButtonWithIndicator caption={counterpart.translate('inventoryDisposal.disposeButton')} />
            </button>
          </div>
        </div>
      </div>
    );
  };
}

const mapStateToProps = () => {
  return {};
};

InventoryDisposalScreen.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

export default connect(mapStateToProps)(InventoryDisposalScreen);
