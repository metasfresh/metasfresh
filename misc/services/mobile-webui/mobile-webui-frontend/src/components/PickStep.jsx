import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

class PickStep extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      activePickingStep: false,
    };
  }

  handleClick = () => {
    this.setState({ activePickingStep: true });
    window.scrollTo(0, 0);
  };
  goBackToPickingSteps = () => this.setState({ activePickingStep: false });

  render() {
    const { activePickingStep } = this.state;
    const { id, locatorName, productName, qtyPicked, qtyToPick, uom } = this.props;
    return (
      <div>
        {activePickingStep && (
          <div className="scanner-container">
            <div className="subtitle centered-text is-size-4">
              Pick Item <button onClick={this.goBackToPickingSteps}>Go back</button>
            </div>
            <video className="viewport scanner-window" id="video" />
          </div>
        )}
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
