import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';

class MaterialReceiptActivity extends Component {
  render() {
    const {
      activityState: {
        componentProps: { lines },
      },
    } = this.props;

    console.log(lines);

    return (
      <div className="mfg-rawMaterialsIssue-activity-container mt-5">
        {lines.length > 0 ? lines.map((line, lineIndex) => this.renderLineButton(line, lineIndex)) : null}
      </div>
    );
  }

  renderLineButton = (line, lineIndex) => {
    const {
      activityState: {
        dataStored: { isUserEditable },
      },
    } = this.props;

    const lineId = '' + lineIndex;
    return (
      <div className="buttons">
        <button key={lineId} className="button is-outlined complete-btn" disabled={!isUserEditable}>
          <ButtonWithIndicator key={lineId} caption={'Receive: ' + line.productName} />
        </button>
      </div>
    );
  };
}

MaterialReceiptActivity.propTypes = {
  wfProcessId: PropTypes.string,
  activityId: PropTypes.string,
  componentProps: PropTypes.object,
  activityState: PropTypes.object,
};

export default MaterialReceiptActivity;
