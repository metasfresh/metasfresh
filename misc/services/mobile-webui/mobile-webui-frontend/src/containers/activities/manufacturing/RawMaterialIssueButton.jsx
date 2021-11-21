import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import * as CompleteStatus from '../../../constants/CompleteStatus';
import Indicator from '../../../components/Indicator';
import PickQuantityPrompt from '../PickQuantityPrompt';
import { toastError } from '../../../utils/toast';

class RawMaterialIssueButton extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isDialogOpen: false,
    };
  }

  onQtyPickedChanged = (qty) => {
    const { onClick } = this.props;

    const inputQty = parseInt(qty);
    if (isNaN(inputQty)) {
      return;
    }

    const isValidQty = this.validateQtyInput(inputQty);
    if (isValidQty) {
      this.setState({ isDialogOpen: false });
      onClick(inputQty);
    } else {
      toastError({ messageKey: 'activities.picking.invalidQtyPicked' });
    }
  };

  validateQtyInput = (numberInput) => {
    const { qtyToIssue } = this.props;

    return numberInput >= 0 && numberInput <= qtyToIssue;
  };

  showDialog = () => this.setState({ isDialogOpen: true });

  hideDialog = () => this.setState({ isDialogOpen: false });

  render() {
    const { productName, qtyIssued, qtyToIssue, uom, completeStatus } = this.props;
    const { isDialogOpen } = this.state;

    return (
      <div>
        {isDialogOpen && (
          <PickQuantityPrompt
            qtyTarget={qtyToIssue}
            qtyCaption={counterpart.translate('activities.mfg.target')}
            onQtyChange={this.onQtyPickedChanged}
          />
        )}
        <div className="mt-3">
          <button className="button is-outlined complete-btn pick-higher-btn" onClick={this.showDialog}>
            <div className="full-size-btn">
              <div className="left-btn-side" />
              <div className="caption-btn">
                <div className="rows">
                  <div className="row is-full pl-5">{productName}</div>
                  <div className="row is-full is-size-7">
                    <div className="picking-row-info">
                      <div className="picking-to-pick">{counterpart.translate('activities.mfg.target')}:</div>
                      <div className="picking-row-qty">
                        {qtyToIssue} {uom}
                      </div>
                      <div className="picking-row-picking">{counterpart.translate('activities.mfg.picked')}:</div>
                      <div className="picking-row-picked">
                        {qtyIssued} {uom}
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div className="right-btn-side pt-4">
                <Indicator completeStatus={completeStatus || CompleteStatus.NOT_STARTED} />
              </div>
            </div>
          </button>
        </div>
      </div>
    );
  }
}

RawMaterialIssueButton.propTypes = {
  //
  // Props
  onClick: PropTypes.func.isRequired,
  // line: PropTypes.object.isRequired,
};

export default RawMaterialIssueButton;
