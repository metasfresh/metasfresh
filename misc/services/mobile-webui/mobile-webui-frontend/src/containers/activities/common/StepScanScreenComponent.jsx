import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { toastError } from '../../../utils/toast';
import CodeScanner from '../scan/CodeScanner';
import PickQuantityPrompt from '../PickQuantityPrompt';
import QtyReasonsView from '../QtyReasonsView';

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

  onBarcodeScanned = ({ scannedBarcode }, closeCameraCallback) => {
    const { qtyTarget } = this.props;

    if (this.isEligibleBarcode(scannedBarcode)) {
      this.closeCameraCallback = closeCameraCallback;

      // in some cases we don't need store quantity (ie manufacturing receipts)
      if (qtyTarget !== null) {
        this.setState({ promptVisible: true });
      }

      this.props.setScannedBarcode(scannedBarcode);
    } else {
      // show an error to user but keep scanning...
      toastError({ messageKey: 'activities.picking.notEligibleHUBarcode' });
    }
  };

  onQtyPickedChanged = (qty) => {
    const { qtyTarget } = this.props;

    const inputQty = parseInt(qty);
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
        this.props.pushUpdatedQuantity({ qty: inputQty });
      }

      this.setState({ promptVisible: false });
    } else {
      toastError({ messageKey: 'activities.picking.invalidQtyPicked' });
    }
  };

  hideReasonsPanel = (reason) => {
    this.setState({ reasonsPanelVisible: false });

    this.props.pushUpdatedQuantity({ qty: this.state.newQuantity, reason });
  };

  validateQtyInput = (numberInput) => {
    const { qtyTarget } = this.props;

    return numberInput >= 0 && numberInput <= qtyTarget;
  };

  isEligibleBarcode = (scannedBarcode) => {
    const { eligibleBarcode } = this.props;

    console.log(`checking ${eligibleBarcode} vs ${scannedBarcode}`);
    // in some cases we accept whatever code user scans and we're not constraining it
    return eligibleBarcode === null ? true : scannedBarcode === eligibleBarcode;
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
              <PickQuantityPrompt qtyTarget={qtyTarget} qtyCaption={qtyCaption} onQtyChange={this.onQtyPickedChanged} />
            ) : null}
            <CodeScanner onBarcodeScanned={this.onBarcodeScanned} />
          </>
        )}
      </div>
    );
  }
}

StepScanScreenComponent.propTypes = {
  componentProps: PropTypes.object,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string,
  eligibleBarcode: PropTypes.string,
  qtyTarget: PropTypes.number,
  qtyCaption: PropTypes.string.isRequired,
  stepProps: PropTypes.object.isRequired,
  pushUpdatedQuantity: PropTypes.func,
  setScannedBarcode: PropTypes.func.isRequired,
};

export default StepScanScreenComponent;
