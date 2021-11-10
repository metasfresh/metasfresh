import React, { PureComponent, createRef } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

class PickQuantityPrompt extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      value: 0,
    };

    this.qtyInput = createRef();
  }

  componentDidMount() {
    this.qtyInput.current.focus();
    this.qtyInput.current.select();
  }

  changeQuantity = (e) => {
    this.setState({ value: e.target.value });
  };

  onDialogYes = () => {
    const { onQtyChange } = this.props;

    onQtyChange(this.state.value);
  };

  render() {
    const { qtyTarget } = this.props;

    return (
      <div>
        <div className="prompt-dialog-screen">
          <article className="message confirm-box is-dark">
            <div className="message-body">
              <strong>{`${counterpart.translate('general.QtyToPick')} : ${qtyTarget}`}</strong>
              <div>&nbsp;</div>
              <div className="control">
                <input
                  ref={this.qtyInput}
                  className="input"
                  type="number"
                  value={this.state.value}
                  onChange={this.changeQuantity}
                />
              </div>
              <div className="buttons is-centered mt-4">
                <button className="button is-medium btn-green confirm-button" onClick={this.onDialogYes}>
                  {counterpart.translate('activities.picking.confirmDone')}
                </button>
              </div>
            </div>
          </article>
        </div>
      </div>
    );
  }
}

PickQuantityPrompt.propTypes = {
  onQtyChange: PropTypes.func.isRequired,
  qtyTarget: PropTypes.number.isRequired,
};

export default PickQuantityPrompt;
