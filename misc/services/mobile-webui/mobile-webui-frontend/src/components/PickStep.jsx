import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

class PickStep extends PureComponent {
  handleClick = (id) => {
    console.log(id);
  };

  render() {
    const { id, locatorName, productName, qtyPicked, qtyToPick, uom } = this.props;
    return (
      <div key={id} className="ml-3 mr-3 is-light launcher" onClick={() => this.handleClick(id)}>
        <div className="box">
          <div className="columns is-mobile">
            <div className="column is-12">
              <div className="columns">
                <div className="column is-size-4-mobile no-p">Product: {productName}</div>
                <div className="column is-size-7 no-p">
                  To Pick: {qtyToPick} Quantity picked: {qtyPicked} UOM: {uom} Locator Name: {locatorName}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

PickStep.propTypes = {
  id: PropTypes.string.isRequired,
  locatorName: PropTypes.string.isRequired,
  productName: PropTypes.string.isRequired,
  qtyPicked: PropTypes.number,
  qtyToPick: PropTypes.number.isRequired,
  uom: PropTypes.string,
};

export default PickStep;
