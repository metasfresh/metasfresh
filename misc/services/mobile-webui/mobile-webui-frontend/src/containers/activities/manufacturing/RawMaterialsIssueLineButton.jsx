// import React, { PureComponent } from 'react';
// import PropTypes from 'prop-types';
// import ButtonWithIndicator from '../../../components/ButtonWithIndicator';

// class RawMaterialsIssueLineButton extends PureComponent {
//   render() {
//     const { productName, isUserEditable } = this.props;

//     return (
//       <div className="buttons">
//         <button className="button is-outlined complete-btn" disabled={!isUserEditable}>
//           <ButtonWithIndicator caption={'Issue: ' + productName} />
//         </button>
//       </div>
//     );
//   }
// }

// RawMaterialsIssueLineButton.propTypes = {
//   productName: PropTypes.string.isRequired,
//   isUserEditable: PropTypes.bool.isRequired,
// };

// export default RawMaterialsIssueLineButton;

import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import LineButton from '../common/LineButton';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/ButtonQuantityProp';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

class RawMaterialsIssueLineButton extends PureComponent {
  handleClick = () => {
    const { dispatch, caption, onHandleClick } = this.props;

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
    const { caption, uom, qtyIssued, qtyToIssue, completeStatus, appId } = this.props;

    return (
      <>
        <ButtonWithIndicator caption={caption} completeStatus={completeStatus}>
          <ButtonQuantityProp qtyCurrent={qtyIssued} qtyTarget={qtyToIssue} uom={uom} appId={appId} />
        </ButtonWithIndicator>
      </>
    );
  }
}

RawMaterialsIssueLineButton.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  isUserEditable: PropTypes.bool.isRequired,
  completeStatus: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,
  qtyIssued: PropTypes.number.isRequired,
  qtyToIssue: PropTypes.number.isRequired,
  appId: PropTypes.string.isRequired,
  onHandleClick: PropTypes.func.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default LineButton(RawMaterialsIssueLineButton);
