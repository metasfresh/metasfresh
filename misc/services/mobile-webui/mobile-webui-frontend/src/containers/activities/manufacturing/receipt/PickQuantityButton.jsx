import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import Button from '../../../../components/buttons/Button';
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
      <>
        {isDialogOpen && (
          <PickQuantityPrompt
            qtyTarget={qtyTarget}
            qtyCaption={counterpart.translate('activities.mfg.receipts.pickPromptTitle')}
            onQtyChange={this.onQtyPickedChanged}
            onCloseDialog={this.hideDialog}
          />
        )}
        <Button caption={caption} onClick={this.showDialog} disabled={isDisabled} />
      </>
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
