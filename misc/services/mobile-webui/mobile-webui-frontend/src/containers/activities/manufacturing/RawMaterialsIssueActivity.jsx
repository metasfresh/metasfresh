import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import RawMaterialsIssueLineButton from './RawMaterialsIssueLineButton';

class RawMaterialsIssueActivity extends PureComponent {
  render() {
    const {
      activityState: {
        componentProps: { lines },
        dataStored: { isUserEditable },
      },
    } = this.props;

    return (
      <div className="mfg-rawMaterialsIssue-activity-container mt-5">
        {lines.map((line, lineIndex) => {
          return (
            <RawMaterialsIssueLineButton
              key={lineIndex}
              productName={line.productName}
              isUserEditable={isUserEditable}
            />
          );
        })}
      </div>
    );
  }
}

RawMaterialsIssueActivity.propTypes = {
  wfProcessId: PropTypes.string,
  activityId: PropTypes.string,
  componentProps: PropTypes.object,
  activityState: PropTypes.object,
};

export default RawMaterialsIssueActivity;
