import React, { Component } from 'react';
import PropTypes from 'prop-types';

export default class ChangeLogModal extends Component {
  render() {
    const { data } = this.props;

    return (
      <div className="col-12">
        <div className="panel panel-spaced panel-distance panel-bordered panel-primary">
          <div className="elements-line">
            <div className="form-group row  form-field-Value">
              <div className="form-control-label col-sm-3">Created By:</div>
              <div className="col-sm-9 ">
                <div className="input-body-container">
                  <span />
                  <div className="input-block input-icon-container input-disabled input-secondary pulse-off">
                    <input
                      className="input-field js-input-field"
                      disabled
                      tabIndex="-1"
                      type="text"
                      value={data.createdByUsername}
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="elements-line">
            <div className="form-group row  form-field-Value">
              <div className="form-control-label col-sm-3">Created At:</div>
              <div className="col-sm-9 ">
                <div className="input-body-container">
                  <span />
                  <div className="input-block input-icon-container input-disabled input-secondary pulse-off">
                    <input
                      className="input-field js-input-field"
                      disabled
                      tabIndex="-1"
                      type="text"
                      value={data.createdTimestamp}
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="panel panel-spaced panel-distance panel-bordered panel-secondary">
          <div className="elements-line">
            <div className="form-group row  form-field-Value">
              <div className="form-control-label col-sm-3">Updated By:</div>
              <div className="col-sm-9 ">
                <div className="input-body-container">
                  <span />
                  <div className="input-block input-icon-container input-disabled input-secondary pulse-off">
                    <input
                      className="input-field js-input-field"
                      disabled
                      tabIndex="-1"
                      type="text"
                      value={data.lastChangedByUsername}
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="elements-line">
            <div className="form-group row  form-field-Value">
              <div className="form-control-label col-sm-3">Updated At:</div>
              <div className="col-sm-9 ">
                <div className="input-body-container">
                  <span />
                  <div className="input-block input-icon-container input-disabled input-secondary pulse-off">
                    <input
                      className="input-field js-input-field"
                      disabled
                      tabIndex="-1"
                      type="text"
                      value={data.lastChangedTimestamp}
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

ChangeLogModal.propTypes = {
  data: PropTypes.object.isRequired,
};
