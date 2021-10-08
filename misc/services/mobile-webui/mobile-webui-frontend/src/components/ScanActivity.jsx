import React from 'react';
// import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';

import ButtonWithIndicator from './ButtonWithIndicator';

const ScanActivity = (props) => {
  const history = useHistory();
  const { activityItem, activityState } = props;
  const { scannedCode } = activityState.dataStored;
  const caption = scannedCode ? `Code: ${activityItem.componentProps.barcodeCaption}` : activityItem.caption;
  const scanButtonStatus = scannedCode ? 'complete' : 'incomplete';
  const scanBtnCaption = caption || 'Scan';

  const handleClick = () => {
    const { wfProcessId } = props;
    const { activityId } = activityItem;

    history.push(`/workflow/${wfProcessId}/activityId/${activityId}/scanner`);
  };

  return (
    <div className="mt-0">
      <button className="button is-outlined complete-btn" onClick={handleClick}>
        <ButtonWithIndicator caption={scanBtnCaption} indicatorType={scanButtonStatus} />
      </button>
    </div>
  );
};

// ScanActivity.propTypes = {
//   caption: PropTypes.string,
//   scanButtonStatus: PropTypes.string,
// };

export default ScanActivity;
