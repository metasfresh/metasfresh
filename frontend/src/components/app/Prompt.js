import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import ModalContextShortcuts from '../keyshortcuts/ModalContextShortcuts';

/**
 * @file Class based component.
 * @module Prompt
 * @extends Component
 * @summary Used through the app for confirmation purposes. i.e deletion
 */
class Prompt extends Component {
  /**
   * @method renderCancelButton
   * @summary Renders the button that will allow canceling and closing the prompt
   */
  renderCancelButton = () => {
    const { buttons } = this.props;
    return (
      <span
        className="btn btn-meta-outline-secondary btn-distance-3 btn-sm"
        onClick={this.props.onCancelClick}
      >
        {buttons.cancel}
      </span>
    );
  };

  /**
   * @method renderSubmitButton
   * @summary Renders the button that will allow the submitting, this will continue/confirm the action for which we need the confirmation
   */
  renderSubmitButton = () => {
    const { buttons } = this.props;
    return (
      <button
        className="btn btn-meta-primary btn-sm btn-submit btn-borderless"
        onClick={this.submitClick}
        onKeyDown={this.onKeyDown}
        autoFocus
      >
        {buttons.submit}
      </button>
    );
  };

  /**
   * @method submitClick
   * @summary actions performed when the confirm/submit button is clicked
   */
  submitClick = () => {
    this.props.onSubmitClick(this.props.selected);
  };

  /**
   * @method onKeyDown
   * @summary Catches key presses and do not execute the default action
   */
  onKeyDown = (e) => e.preventDefault();

  /**
   * @method render
   * @summary Main render function
   */
  render() {
    const { onCancelClick, title, buttons } = this.props;
    const { cancel, submit } = buttons;
    return (
      <div className="screen-freeze screen-prompt-freeze">
        <div className="panel panel-modal-primary panel-prompt prompt-shadow">
          <div className="panel-groups-header panel-modal-header panel-prompt-header">
            <span className="panel-prompt-header-title panel-modal-header-title">
              {title}
            </span>
            <i className="meta-icon-close-1" onClick={onCancelClick} />
          </div>
          <div className="panel-modal-content panel-prompt-content">
            <p>{this.props.text}</p>
          </div>
          <div className="panel-groups-header panel-modal-header panel-prompt-header panel-prompt-footer">
            <div className="prompt-button-wrapper">
              {cancel ? this.renderCancelButton() : ''}
              {submit ? this.renderSubmitButton() : ''}
            </div>
          </div>
        </div>
        <ModalContextShortcuts
          done={this.submitClick}
          cancel={this.props.onCancelClick}
        />
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {func} dispatch
 * @prop {string} text
 * @prop {func} [onCancelClick]
 * @prop {func} [onSubmitClick]
 * @prop {*} [title]
 * @prop {*} [buttons]
 * @todo Check title, buttons. Which proptype? Required or optional?
 */
Prompt.propTypes = {
  dispatch: PropTypes.func.isRequired,
  text: PropTypes.string.isRequired,
  onCancelClick: PropTypes.func,
  onSubmitClick: PropTypes.func,
  title: PropTypes.any,
  buttons: PropTypes.any,
  selected: PropTypes.any,
};

export default connect()(Prompt);
