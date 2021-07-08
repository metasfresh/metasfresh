import React, { Component } from 'react';
import BarcodeScanner from './BarcodeScanner';
import PropTypes from 'prop-types';

import currentDevice from 'current-device';

/* @file HOC function
 * addBarcodeScanner wraps the underlying component adding Barcode scanner. The wrapped component
 * gets a few properties, that must be properly used:
 *
 * @prop {object} scannerElement - the actual scanner component, using device's camera API
 * @prop {bool} scanning - flag indicating if the scanner component shdould be visible
 * @prop {func} onScanBarcode - function initiating scanning (scanning component can now be displayed, `scanning` set to true)
 * @prop {func} onSelectBarcode - function called when code is found, or used to reset scanner when passed `null` value
 *
 * So the correct way to use it, is to have a component wrapped with this HOC (like BarcodeScannerHOC(MyComponent)) and a button
 * with a click handler, calling `onScanBarcode` - `onClick={() => onScanBarcode(true)}`. Once the `scanning` flag is true, we
 * render the `scannerElement, ie like this:
 *
 * ```
 *  if (scanning) {
 *    return (
 *      <div className="overlay-field js-not-unselect">{scannerElement}</div>
 *    );
 *  }
 * ```
 */
function addBarcodeScanner(WrappedComponent) {
  return class BarcodeScannerWidget extends Component {
    static propTypes = {
      layout: PropTypes.object,
      closeOverlay: PropTypes.any,
      postDetectionExec: PropTypes.func,
    };

    constructor(props) {
      super(props);

      const barcodeScannerType =
        props.layout && props.layout.barcodeScannerType
          ? props.layout.barcodeScannerType
          : null;

      const scanning = barcodeScannerType && currentDevice.type === 'mobile';

      this.state = {
        barcodeScannerType: barcodeScannerType,
        scanning: scanning,
        codeSelected: null,
      };
    }

    /**
     * @method resetWidgetValues
     * @summary scanCode set the scanner in scanning mode
     */
    scanBarcode = (val) => {
      this.setState({
        scanning: typeof val !== 'undefined' ? val : !this.state.scanning,
        codeSelected: null,
      });
    };

    /**
     * @method onBarcodeDetected
     * @summary called once scanner finds a valid code, or used to reset the scanner if `null` is passed
     *
     * @prop {*} result - either resulting code, or null for reset
     */
    onBarcodeDetected = (result) => {
      const { postDetectionExec } = this.props;

      // runs the function from the props after the QR is detected
      postDetectionExec && postDetectionExec(result);

      this.setState({
        codeSelected: result || null,
        scanning: false,
      });
    };

    /**
     * @method renderScanner
     * @summary create the actual scanner component
     */
    renderScanner = () => {
      const { closeOverlay } = this.props;
      const { barcodeScannerType } = this.state;

      return (
        <div className="row barcode-scanner-widget">
          <div className="col-sm-12">
            <BarcodeScanner
              barcodeScannerType={barcodeScannerType}
              onDetected={this.onBarcodeDetected}
              onClose={(val) => {
                const value = typeof val !== 'undefined' ? val : false;
                this.scanBarcode(value);

                if (currentDevice.type === 'mobile') {
                  closeOverlay();
                }
              }}
            />
          </div>
        </div>
      );
    };

    render() {
      const scannerElement = this.renderScanner();

      return (
        <WrappedComponent
          {...this.props}
          {...this.state}
          scannerElement={scannerElement}
          onScanBarcode={this.scanBarcode}
          onSelectBarcode={this.onBarcodeDetected}
        />
      );
    }
  };
}

export default addBarcodeScanner;
