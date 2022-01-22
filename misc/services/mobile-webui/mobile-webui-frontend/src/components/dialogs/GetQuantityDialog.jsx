import React, { PureComponent, createRef } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

class GetQuantityDialog extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      value: props.qtyInitial ? props.qtyInitial : 0,
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
    const { qtyTarget, qtyCaption, uom, onCloseDialog } = this.props;
    const qtyTargetNorm = qtyTarget > 0 ? qtyTarget : 0;

    return (
      <div>
        <div className="prompt-dialog-screen">
          <article className="message is-dark">
            <div className="message-body">
              <strong>
                {qtyCaption}: {qtyTargetNorm} {uom}
              </strong>
              <div className="control">
                <input
                  ref={this.qtyInput}
                  className="input"
                  type="number"
                  value={this.state.value}
                  onChange={this.changeQuantity}
                />
              </div>
              <div className="buttons is-centered">
                <button className="button is-danger" onClick={this.onDialogYes}>
                  {counterpart.translate('activities.picking.confirmDone')}
                </button>
                <button className="button is-success" onClick={onCloseDialog}>
                  {counterpart.translate('general.cancelText')}
                </button>
              </div>
            </div>
          </article>
        </div>
      </div>
    );
  }
}

GetQuantityDialog.propTypes = {
  // Properties
  qtyInitial: PropTypes.number,
  qtyTarget: PropTypes.number.isRequired,
  qtyCaption: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,

  // Callbacks
  onQtyChange: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func,
};

export default GetQuantityDialog;
