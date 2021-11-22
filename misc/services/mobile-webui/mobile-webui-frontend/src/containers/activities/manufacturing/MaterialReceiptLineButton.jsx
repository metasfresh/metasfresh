import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import LineButton from '../common/LineButton';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/ButtonQuantityProp';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

class MaterialReceiptLineButton extends PureComponent {
  handleClick = () => {
    const { dispatch, caption, onHandleClick, location } = this.props;

    onHandleClick();
    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('activities.mfg.ProductName'),
            value: caption,
            bold: true,
          },
        ],
      })
    );
  };

  render() {
    const { caption, uom, qtyCurrent, qtyTarget, completeStatus, appId } = this.props;

    return (
      <>
        <ButtonWithIndicator caption={caption} completeStatus={completeStatus}>
          <ButtonQuantityProp
            qtyCurrent={qtyCurrent}
            qtyTarget={qtyTarget}
            uom={uom}
            appId={appId}
            subtypeId="receipts"
          />
        </ButtonWithIndicator>
      </>
    );
  }
}

MaterialReceiptLineButton.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  isUserEditable: PropTypes.bool.isRequired,
  completeStatus: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,
  qtyCurrent: PropTypes.number.isRequired,
  qtyTarget: PropTypes.number.isRequired,
  appId: PropTypes.string.isRequired,
  onHandleClick: PropTypes.func.isRequired,
  location: PropTypes.object.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default LineButton(MaterialReceiptLineButton);
