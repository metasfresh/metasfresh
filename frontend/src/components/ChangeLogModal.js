import counterpart from 'counterpart';
import React, { Component } from 'react';
import PropTypes from 'prop-types';

/**
 * @file Class based component.
 * @module ChangeLogModal
 * @extends Component
 */
class ChangeLogModal extends Component {
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

  /**
   * @method renderRowsData
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  renderRowsData = () => {
    const { data } = this.props;

    if (data.rowsData) {
      return (
        <div className="panel panel-spaced changelog-rows">
          <h5>{counterpart.translate('view.about.row')}:</h5>
          <div className="panel panel-spaced panel-distance panel-bordered panel-primary">
            {this.renderField(
              `${counterpart.translate('view.about.createdBy')}:`,
              data.rowsData.createdByUsername
            )}
            {this.renderField(
              `${counterpart.translate('view.about.created')}:`,
              data.rowsData.createdTimestamp
            )}
          </div>
          <div className="panel panel-spaced panel-distance panel-bordered panel-secondary">
            {this.renderField(
              `${counterpart.translate('view.about.updatedBy')}:`,
              data.rowsData.lastChangedByUsername
            )}
            {this.renderField(
              `${counterpart.translate('view.about.updated')}:`,
              data.rowsData.lastChangedTimestamp
            )}
          </div>
        </div>
      );
    }

    return null;
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { data } = this.props;

    if (!data || !data.createdByUsername) {
      return null;
    }

    return (
      <div className="col-12">
        <div className="panel panel-spaced panel-distance panel-bordered panel-primary">
          {this.renderField(
            `${counterpart.translate('view.about.createdBy')}:`,
            data.createdByUsername
          )}
          {this.renderField(
            `${counterpart.translate('view.about.created')}:`,
            data.createdTimestamp
          )}
        </div>
        <div className="panel panel-spaced panel-distance panel-bordered panel-secondary">
          {this.renderField(
            `${counterpart.translate('view.about.updatedBy')}:`,
            data.lastChangedByUsername
          )}
          {this.renderField(
            `${counterpart.translate('view.about.updated')}:`,
            data.lastChangedTimestamp
          )}
        </div>
        {this.renderRowsData()}
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {object} data
 */
ChangeLogModal.propTypes = {
  data: PropTypes.object.isRequired,
};

export default ChangeLogModal;
