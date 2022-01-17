import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { toastError } from '../../../utils/toast';
import CodeScanner from '../scan/CodeScanner';
import PickQuantityPrompt from '../PickQuantityPrompt';
import QtyReasonsView from '../QtyReasonsView';

// FIXME deprecated. To be replaced by ScanHUAndGetQtyComponent
class StepScanScreenComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      promptVisible: false,
      reasonsPanelVisible: false,
      newQuantity: 0,
      scannedBarcode: null,
      qtyRejected: 0,
    };
  }

  hidePrompt = () => {
    this.setState({ promptVisible: false });
  };

  onBarcodeScanned = ({ scannedBarcode }) => {
    const { qtyTarget, pushUpdatedQuantity } = this.props;

    if (this.isEligibleBarcode(scannedBarcode)) {
      this.props.setScannedBarcode(scannedBarcode);

      // in some cases we don't need store quantity (ie manufacturing receipts)
      if (qtyTarget) {
        this.setState({ promptVisible: true });
      } else {
        pushUpdatedQuantity({ qty: 0, reason: null });
      }
    } else {
      // show an error to user but keep scanning...
      toastError({ messageKey: 'activities.picking.notEligibleHUBarcode' });
    }
  };

  onQtyPickedChanged = (qty) => {
    const { qtyTarget, pushUpdatedQuantity } = this.props;

    const inputQty = parseFloat(qty);
    if (isNaN(inputQty)) {
      return;
    }

    const isValidQty = this.validateQtyInput(inputQty);
    if (isValidQty) {
      this.setState({ newQuantity: inputQty });

      if (inputQty !== qtyTarget) {
        const qtyRejected = qtyTarget - inputQty;
        this.setState({ reasonsPanelVisible: true, qtyRejected });
      } else {
        pushUpdatedQuantity({ qty: inputQty });
      }

      this.setState({ promptVisible: false });
    } else {
      toastError({ messageKey: 'activities.picking.invalidQtyPicked' });
    }
  };

  hideReasonsPanel = (reason) => {
    const { pushUpdatedQuantity } = this.props;
    this.setState({ reasonsPanelVisible: false });
    pushUpdatedQuantity({ qty: this.state.newQuantity, reason });
  };

  validateQtyInput = (numberInput) => {
    const { qtyTarget } = this.props;

    return numberInput >= 0 && numberInput <= qtyTarget;
  };

  isEligibleBarcode = (scannedBarcode) => {
    const { eligibleBarcode } = this.props;

    console.log(`checking ${eligibleBarcode} vs ${scannedBarcode}`);
    // in some cases we accept whatever code user scans and we're not constraining it
    return eligibleBarcode ? scannedBarcode === eligibleBarcode : true;
  };

  render() {
    const {
      stepProps: { uom },
      qtyTarget,
      qtyCaption,
    } = this.props;
    const { promptVisible, reasonsPanelVisible, qtyRejected } = this.state;

    return (
      <div className="mt-0">
        {reasonsPanelVisible ? (
          <QtyReasonsView onHide={this.hideReasonsPanel} uom={uom} qtyRejected={qtyRejected} />
        ) : (
          <>
            {promptVisible ? (
              <PickQuantityPrompt
                qtyTarget={qtyTarget}
                qtyCaption={qtyCaption}
                onQtyChange={this.onQtyPickedChanged}
                onCloseDialog={this.hidePrompt}
              />
            ) : (
              <CodeScanner onBarcodeScanned={this.onBarcodeScanned} />
            )}
          </>
        )}
      </div>
    );
  }
}

StepScanScreenComponent.propTypes = {
  // Props:
  eligibleBarcode: PropTypes.string,
  qtyTarget: PropTypes.number,
  qtyCaption: PropTypes.string.isRequired,
  stepProps: PropTypes.object.isRequired, // TODO: replace with uom!

  // Callbacks
  pushUpdatedQuantity: PropTypes.func,
  setScannedBarcode: PropTypes.func.isRequired,
};

export default StepScanScreenComponent;
