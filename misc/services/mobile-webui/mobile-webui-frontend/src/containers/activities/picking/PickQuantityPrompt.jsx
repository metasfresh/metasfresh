import React, { Component } from 'react';

class PickQuantityPrompt extends Component {
  constructor(props) {
    super(props);
    this.state = {
      value: 0,
    };
  }

  changeQuantity = (e) => {
    this.setState({ value: e.target.value });
  };

  onDialogYes = () => {
    const { onQtyChange } = this.props;

    onQtyChange(this.state.value);
  };

  render() {
    const { qtyToPick } = this.props;

    return (
      <div>
        <div className="prompt-dialog-screen">
          <article className="message confirm-box is-dark">
            <div className="message-body">
              <strong>{`Quantity to pick: ${qtyToPick}`}</strong>
              <div>&nbsp;</div>
              <div className="control">
                <input className="input" type="number" value={this.state.value} onChange={this.changeQuantity} />
              </div>
              <div className="buttons is-centered">
                <button className="button is-success confirm-button" onClick={this.onDialogYes}>
                  Done
                </button>
              </div>
            </div>
          </article>
        </div>
      </div>
    );
  }
}

export default PickQuantityPrompt;
