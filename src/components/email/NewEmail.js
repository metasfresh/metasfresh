import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { List } from 'immutable';

import { addNotification } from '../../actions/AppActions';
import {
  createEmail,
  getEmail,
  getTemplates,
  sendEmail,
} from '../../actions/EmailActions';
import { patchRequest } from '../../actions/GenericActions';
import RawList from '../widget/List/RawList';
import Attachments from './Attachments';
import AutocompleteTo from './AutocompleteTo';

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

  componentWillMount = () => {
    const { windowId, docId, onCloseEmail } = this.props;
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
        onCloseEmail();
      });
  };

  getEmail = emailId => {
    getEmail(emailId).then(res => {
      this.setState({
        init: true,
        ...res.data,
        cached: res.data,
      });
    });
  };

  getTemplates = () => {
    getTemplates().then(res => {
      this.setState({
        templates: List(res.data.values),
      });
    });
  };

  change = (prop, value) => {
    this.setState({
      [prop]: value,
    });
  };

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

  send = () => {
    const { emailId } = this.state;
    const { onCloseEmail, dispatch } = this.props;
    sendEmail(emailId).then(() => {
      onCloseEmail();
      dispatch(
        addNotification('Email', 'Email has been sent.', 5000, 'success')
      );
    });
  };

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

  handleFocus = () => {
    this.setState({
      listFocused: true,
    });
  };

  handleBlur = () => {
    this.setState({
      listFocused: false,
    });
  };

  closeTemplatesList = () => {
    this.setState({
      listToggled: false,
    });
  };

  openTemplatesList = () => {
    this.setState({
      listToggled: true,
    });
  };

  render() {
    const { onCloseEmail, windowId, docId } = this.props;
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
                onClick={onCloseEmail}
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

NewEmail.propTypes = {
  windowId: PropTypes.string,
  dispatch: PropTypes.func,
  docId: PropTypes.string,
  onCloseEmail: PropTypes.func,
};

export default connect()(NewEmail);
