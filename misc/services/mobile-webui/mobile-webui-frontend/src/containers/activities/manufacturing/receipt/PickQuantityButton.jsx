import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import PickQuantityPrompt from '../../PickQuantityPrompt';
import { toastError } from '../../../../utils/toast';

class PickQuantityButton extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isDialogOpen: false,
    };
  }

  onQtyPickedChanged = (qty) => {
    const { onClick } = this.props;

    const inputQty = parseFloat(qty);
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
    const { qtyTarget } = this.props;

    return numberInput >= 0 && numberInput <= qtyTarget;
  };

  showDialog = () => this.setState({ isDialogOpen: true });

  hideDialog = () => this.setState({ isDialogOpen: false });

  render() {
    const { /*completeStatus,*/ qtyTarget, caption, isDisabled } = this.props;
    const { isDialogOpen } = this.state;

    return (
      <div>
        {isDialogOpen && (
          <PickQuantityPrompt
            qtyTarget={qtyTarget}
            qtyCaption={counterpart.translate('activities.mfg.receipts.pickPromptTitle')}
            onQtyChange={this.onQtyPickedChanged}
            onCloseDialog={this.hideDialog}
          />
        )}
        <div className="mt-3">
          <button
            className="button is-outlined complete-btn pick-higher-btn"
            disabled={isDisabled}
            onClick={this.showDialog}
          >
            <div className="full-size-btn">
              <div className="left-btn-side" />
              <div className="caption-btn">
                <div className="rows">
                  <div className="row is-full pl-5">{caption}</div>
                </div>
              </div>
              {/*              <div className="right-btn-side pt-4">
                <Indicator completeStatus={completeStatus || CompleteStatus.NOT_STARTED} />
              </div>*/}
            </div>
          </button>
        </div>
      </div>
    );
  }
}

PickQuantityButton.propTypes = {
  //
  // Props
  onClick: PropTypes.func.isRequired,
  qtyTarget: PropTypes.number.isRequired,
  // completeStatus: PropTypes.number.isRequired,
  uom: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  isDisabled: PropTypes.bool,
};

export default PickQuantityButton;
