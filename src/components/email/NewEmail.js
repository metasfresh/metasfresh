import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { List } from 'immutable';

import { patchRequest } from '../../api';
import { addNotification } from '../../actions/AppActions';
import {
  createEmail,
  getEmail,
  getTemplates,
  sendEmail,
} from '../../actions/EmailActions';
import RawList from '../widget/List/RawList';
import Attachments from './Attachments';
import AutocompleteTo from './AutocompleteTo';

/**
 * @file Class based component.
 * @module NewEmail
 * @extends Component
 */
class NewEmail extends Component {
  constructor(props) {
    super(props);

    this.state = {
      init: false,
      cached: {},
      templates: List(),
      template: {},
      listFocused: true,
      listToggled: false,
    };
  }

  /**
   * @method UNSAFE_componentWillMount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  UNSAFE_componentWillMount = () => {
    const { windowId, docId, handleCloseEmail } = this.props;
    createEmail(windowId, docId)
      .then(res => {
        this.setState({
          init: true,
          ...res.data,
          cached: res.data,
        });
        this.getTemplates();
      })
      .catch(() => {
        handleCloseEmail();
      });
  };

  /**
   * @method getEmail
   * @summary ToDo: Describe the method
   * @param {*} emailId
   * @todo Write the documentation
   */
  getEmail = emailId => {
    getEmail(emailId).then(res => {
      this.setState({
        init: true,
        ...res.data,
        cached: res.data,
      });
    });
  };

  /**
   * @method getTemplates
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  getTemplates = () => {
    getTemplates().then(res => {
      this.setState({
        templates: List(res.data.values),
      });
    });
  };

  /**
   * @method change
   * @summary ToDo: Describe the method
   * @param {*} prop
   * @param {*} value
   * @todo Write the documentation
   */
  change = (prop, value) => {
    this.setState({
      [prop]: value,
    });
  };

  /**
   * @method patch
   * @summary ToDo: Describe the method
   * @param {*} prop
   * @param {*} value
   * @todo Write the documentation
   */
  patch = (prop, value) => {
    const { emailId } = this.state;

    if (this.state.cached[prop] === value) return;

    patchRequest({
      entity: 'mail',
      docType: emailId,
      property: prop,
      value,
    }).then(response => {
      this.setState({
        ...response.data,
        cached: response.data,
      });
    });
  };

  /**
   * @method send
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  send = () => {
    const { emailId } = this.state;
    const { handleCloseEmail, dispatch } = this.props;
    sendEmail(emailId).then(() => {
      handleCloseEmail();
      dispatch(
        addNotification('Email', 'Email has been sent.', 5000, 'success')
      );
    });
  };

  /**
   * @method handleTemplate
   * @summary ToDo: Describe the method
   * @param {*} option
   * @todo Write the documentation
   */
  handleTemplate = option => {
    const { emailId, template } = this.state;
    if (template === option) return;

    patchRequest({
      entity: 'mail',
      docType: emailId,
      property: 'templateId',
      value: option,
    }).then(response => {
      this.setState({
        ...response.data,
        template: option,
      });
    });
  };

  /**
   * @method handleFocus
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleFocus = () => {
    this.setState({
      listFocused: true,
    });
  };

  /**
   * @method handleBlur
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleBlur = () => {
    this.setState({
      listFocused: false,
    });
  };

  /**
   * @method closeTemplatesList
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  closeTemplatesList = () => {
    this.setState({
      listToggled: false,
    });
  };

  /**
   * @method openTemplatesList
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  openTemplatesList = () => {
    this.setState({
      listToggled: true,
    });
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { handleCloseEmail, windowId, docId } = this.props;
    const {
      init,
      attachments,
      emailId,
      subject,
      message,
      to,
      templates,
      template,
      listFocused,
      listToggled,
    } = this.state;

    if (!init) return false;

    return (
      <div className="screen-freeze">
        <div className="panel panel-modal panel-email panel-modal-primary">
          <div className="panel-email-header-wrapper">
            <div className="panel-email-header panel-email-header-top">
              <span className="email-headline">
                {counterpart.translate('window.email.new')}
              </span>
              {templates.size > 0 && (
                <div className="email-templates">
                  <RawList
                    rank="primary"
                    list={templates}
                    onSelect={option => this.handleTemplate(option)}
                    selected={template}
                    isFocused={listFocused}
                    isToggled={listToggled}
                    onOpenDropdown={this.openTemplatesList}
                    onCloseDropdown={this.closeTemplatesList}
                    onFocus={this.handleFocus}
                    onBlur={this.handleBlur}
                  />
                </div>
              )}
              <div
                className="input-icon input-icon-lg icon-email"
                onClick={handleCloseEmail}
              >
                <i className="meta-icon-close-1" />
              </div>
            </div>
            <div className="panel-email-header panel-email-bright">
              <div className="panel-email-data-wrapper">
                <span className="email-label">
                  {counterpart.translate('window.email.to')}:
                </span>
                <AutocompleteTo {...{ windowId, docId, emailId, to }} />
              </div>
            </div>
            <div className="panel-email-header panel-email-bright">
              <div className="panel-email-data-wrapper">
                <span className="email-label">
                  {counterpart.translate('window.email.topic')}:
                </span>
                <input
                  className="email-input email-input-msg"
                  type="text"
                  onChange={e => this.change('subject', e.target.value)}
                  value={subject ? subject : ''}
                  onBlur={() => this.patch('subject', subject)}
                />
              </div>
            </div>
          </div>
          <div className="panel-email-body">
            <textarea
              value={message ? message : ''}
              onChange={e => this.change('message', e.target.value)}
              onBlur={() => this.patch('message', message)}
            />
          </div>
          <div className="panel-email-footer">
            <Attachments
              getEmail={this.getEmail}
              {...{ attachments, emailId }}
            />
            <button
              onClick={this.send}
              className="btn btn-meta-success btn-sm btn-submit"
            >
              {counterpart.translate('window.email.send')}
            </button>
          </div>
        </div>
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {string} [windowId]
 * @prop {func} [dispatch]
 * @prop {string} [docId]
 * @prop {func} [handleCloseEmail]
 */
NewEmail.propTypes = {
  windowId: PropTypes.string,
  dispatch: PropTypes.func,
  docId: PropTypes.string,
  handleCloseEmail: PropTypes.func,
};

export default connect()(NewEmail);
