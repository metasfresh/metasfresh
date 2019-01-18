import React, { Component } from 'react';
import PropTypes from 'prop-types';

export default class ChangeLogModal extends Component {
  renderField(caption, value) {
    return (
      <div className="elements-line">
        <div className="form-group row  form-field-Value">
          <div className="form-control-label col-sm-3">{caption}</div>
          <div className="col-sm-9 ">
            <div className="input-body-container">
              <span />
              <div className="input-block input-icon-container input-disabled input-secondary pulse-off">
                <input
                  className="input-field js-input-field"
                  disabled
                  tabIndex="-1"
                  type="text"
                  value={value}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  render() {
    const { data } = this.props;

    return (
      <div className="col-12">
        <div className="panel panel-spaced panel-distance panel-bordered panel-primary">
          {this.renderField('Created By:', data.createdByUsername)}
          {this.renderField('Created On:', data.createdTimestamp)}
        </div>
        <div className="panel panel-spaced panel-distance panel-bordered panel-secondary">
          {this.renderField('Updated By:', data.lastChangedByUsername)}
          {this.renderField('Updated At:', data.lastChangedTimestamp)}
        </div>
      </div>
    );
  }
}

ChangeLogModal.propTypes = {
  data: PropTypes.object.isRequired,
};
