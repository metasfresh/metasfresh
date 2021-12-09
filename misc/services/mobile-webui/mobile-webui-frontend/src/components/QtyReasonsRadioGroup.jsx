import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';

class QtyReasonsRadioGroup extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      selectedReasonKey: '',
    };
  }

  setSelectedReason = (event) => {
    const { onReasonSelected } = this.props;
    const selectedReasonKey = event.target.name;
    this.setState({
      ...this.state,
      selectedReasonKey,
    });

    onReasonSelected(selectedReasonKey);
  };

  render() {
    const { selectedReasonKey } = this.state;
    const { reasons } = this.props;

    return (
      <div className="control">
        {reasons.map((reason, idx) => (
          <div key={idx} className="columns is-mobile">
            <div className="column is-full">
              <label className="radio">
                <input
                  className="mr-2"
                  type="radio"
                  name={reason.key}
                  value={reason.key}
                  onChange={this.setSelectedReason}
                  checked={selectedReasonKey === reason.key}
                />
                {reason.caption}
              </label>
            </div>
          </div>
        ))}
      </div>
    );
  }
}

QtyReasonsRadioGroup.propTypes = {
  reasons: PropTypes.array.isRequired,
  //
  onReasonSelected: PropTypes.func.isRequired,
};

export default withRouter(connect()(QtyReasonsRadioGroup));
