import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';

class MaterialReceiptLineButton extends PureComponent {
  render() {
    const { productName, isUserEditable } = this.props;

    return (
      <div className="buttons">
        <button className="button is-outlined complete-btn" disabled={!isUserEditable}>
          <ButtonWithIndicator caption={'Receive: ' + productName} />
        </button>
      </div>
    );
  }
}

MaterialReceiptLineButton.propTypes = {
  productName: PropTypes.string.isRequired,
  isUserEditable: PropTypes.bool.isRequired,
};

export default MaterialReceiptLineButton;
