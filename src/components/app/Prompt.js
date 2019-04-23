import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import ModalContextShortcuts from '../keyshortcuts/ModalContextShortcuts';

class Prompt extends Component {
  constructor(props) {
    super(props);
  }

  renderCancelButton = () => {
    const { buttons } = this.props;
    return (
      <span
        className="btn btn-meta-outline-secondary btn-distance-3 btn-sm"
        onClick={e => this.props.onCancelClick(e)}
      >
        {buttons.cancel}
      </span>
    );
  };

  renderSubmitButton = () => {
    const { buttons } = this.props;
    return (
      <span
        className="btn btn-meta-primary btn-sm btn-submit"
        onClick={e => this.props.onSubmitClick(e)}
      >
        {buttons.submit}
      </span>
    );
  };

  render() {
    const { onCancelClick, title, buttons } = this.props;
    const { cancel, submit } = buttons;
    return (
      <div className="screen-freeze screen-prompt-freeze">
        <div className="panel panel-modal-primary panel-prompt prompt-shadow">
          <div className="panel-modal-header panel-prompt-header">
            <span className="panel-prompt-header-title panel-modal-header-title">
              {title}
            </span>
            <i className="meta-icon-close-1" onClick={e => onCancelClick(e)} />
          </div>
          <div className="panel-modal-content panel-prompt-content">
            <p>{this.props.text}</p>
          </div>
          <div className="panel-modal-header panel-prompt-header panel-prompt-footer">
            <div className="prompt-button-wrapper">
              {cancel ? this.renderCancelButton() : ''}
              {submit ? this.renderSubmitButton() : ''}
            </div>
          </div>
        </div>
        <ModalContextShortcuts
          done={this.props.onSubmitClick}
          cancel={this.props.onCancelClick}
        />
      </div>
    );
  }
}

Prompt.propTypes = {
  dispatch: PropTypes.func.isRequired,
  text: PropTypes.string.isRequired,
  onCancelClick: PropTypes.func,
  onSubmitClick: PropTypes.func,
};

export default connect()(Prompt);
