import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';

class RawMaterialsIssueLineButton extends PureComponent {
  render() {
    const { productName, isUserEditable } = this.props;

    return (
      <div className="buttons">
        <button className="button is-outlined complete-btn" disabled={!isUserEditable}>
          <ButtonWithIndicator caption={'Issue: ' + productName} />
        </button>
      </div>
    );
  }
}

RawMaterialsIssueLineButton.propTypes = {
  productName: PropTypes.string.isRequired,
  isUserEditable: PropTypes.bool.isRequired,
};

export default RawMaterialsIssueLineButton;
