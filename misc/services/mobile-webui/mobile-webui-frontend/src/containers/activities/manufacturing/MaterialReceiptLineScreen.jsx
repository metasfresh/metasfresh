import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { push, go } from 'connected-react-router';
import counterpart from 'counterpart';

// import { updateManufacturingReceiptQty } from '../../../actions/ManufacturingActions';
import PickQuantityButton from './PickQuantityButton';

class MaterialReceiptLineScreen extends PureComponent {
  handleQuantityChange = (qtyPicked) => {
    //   const { dispatch, wfProcessId, activityId, lineId } = this.props;
    console.log('qtyPicked: ', qtyPicked, go);
    //   dispatch(updateManufacturingReceiptQty({ wfProcessId, activityId, lineId, qtyPicked }));
    //   dispatch(go(-1));
  };

  handleClick = () => {
    const { wfProcessId, activityId, lineId } = this.props;
    const { dispatch } = this.props;

    const location = `/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/receipt/target`;
    dispatch(push(location));
  };

  render() {
    const {
      lineProps: { uom, qtyReceived, qtyToReceive, productName, aggregateToLU },
    } = this.props;
    let caption = counterpart.translate('activities.mfg.receipts.receiveQty');
    let targetCaption = counterpart.translate('activities.mfg.receipts.receiveTarget'); // or packing material

    return (
      <div className="pt-2 section lines-screen-container">
        <div className="steps-container">
          <div className="buttons">
            <button className="button is-outlined complete-btn" disabled={false} onClick={this.handleClick}>
              <div className="full-size-btn">
                <div className="left-btn-side"></div>
                <div className="caption-btn">
                  <div className="rows">
                    <div className="row is-full pl-5">{targetCaption}</div>
                  </div>
                </div>
              </div>
            </button>
          </div>
          <PickQuantityButton
            qtyCurrent={qtyReceived}
            qtyTarget={qtyToReceive}
            isDisabled={aggregateToLU === null}
            onClick={this.handleQuantityChange}
            {...{ uom, productName, caption }}
          />
        </div>
      </div>
    );
  }
}

MaterialReceiptLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineProps: PropTypes.object.isRequired,
  lineId: PropTypes.string.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default MaterialReceiptLineScreen;
