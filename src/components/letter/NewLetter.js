import counterpart from 'counterpart';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { List } from 'immutable';

import { addNotification } from '../../actions/AppActions';
import { patchRequest } from '../../actions/GenericActions';
import {
  completeLetter,
  createLetter,
  getTemplates,
} from '../../actions/LetterActions';
import RawList from '../widget/List/RawList';

class NewLetter extends Component {
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

  async componentWillMount() {
    const { windowId, docId, onCloseLetter } = this.props;

    try {
      const res = await createLetter(windowId, docId);

      this.setState({
        ...res.data,
        init: true,
        cached: res.data,
      });

      try {
        await this.getTemplates();
      } catch (error) {
        // eslint-disable-next-line no-console
        console.error(error);
      }
    } catch (error) {
      onCloseLetter();
    }
  }

  getTemplates = async () => {
    const res = await getTemplates();

    this.setState({
      templates: List(res.data.values),
    });
  };

  handleTemplate = async option => {
    const { letterId, template } = this.state;

    if (template === option) {
      return;
    }

    const response = await patchRequest({
      entity: 'letter',
      docType: letterId,
      property: 'templateId',
      value: option,
    });

    this.setState({
      ...response.data,
      template: option,
    });
  };

  handleChange = ({ target: { value: message } }) => {
    this.setState({
      message,
    });
  };

  handleBlur = async ({ target: { value: message } }) => {
    const { letterId } = this.state;

    if (this.state.cached.message === message) {
      return;
    }

    const response = await patchRequest({
      entity: 'letter',
      docType: letterId,
      property: 'message',
      value: message,
    });

    this.setState({
      ...response.data,
      cached: response.data,
      listFocused: false,
    });
  };

  handleListFocus = () => {
    this.setState({
      listFocused: true,
    });
  };

  handleListBlur = () => {
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

  complete = async () => {
    const { letterId } = this.state;
    const { onCloseLetter, dispatch } = this.props;

    await completeLetter(letterId);

    onCloseLetter();

    await dispatch(
      addNotification('Letter', 'Letter has been sent.', 5000, 'success')
    );
  };

  render() {
    const { onCloseLetter } = this.props;
    const {
      init,
      message,
      templates,
      template,
      letterId,
      listFocused,
      listToggled,
    } = this.state;

    if (!init) {
      return false;
    }

    return (
      <div className="screen-freeze">
        <div className="panel panel-modal panel-letter panel-modal-primary">
          <div className="panel-letter-header-wrapper">
            <div className="panel-letter-header panel-letter-header-top">
              <span className="letter-headline">
                {counterpart.translate('window.letter.new')}
              </span>
              <a
                href={`${config.API_URL}/letter/${letterId}/printPreview`}
                target="_blank"
                className="input-icon input-icon-lg letter-icon-print"
              >
                <i className="meta-icon-print" />
              </a>
              {templates.size > 0 && (
                <div className="letter-templates">
                  <RawList
                    rank="primary"
                    list={templates}
                    onSelect={option => this.handleTemplate(option)}
                    selected={template}
                    isFocused={listFocused}
                    isToggled={listToggled}
                    onOpenDropdown={this.openTemplatesList}
                    onCloseDropdown={this.closeTemplatesList}
                    onFocus={this.handleListFocus}
                    onBlur={this.handleListBlur}
                  />
                </div>
              )}
              <div
                className="input-icon input-icon-lg letter-icon-close"
                onClick={onCloseLetter}
              >
                <i className="meta-icon-close-1" />
              </div>
            </div>
          </div>
          <div className="panel-letter-body">
            <textarea
              value={message ? message : ''}
              onChange={this.handleChange}
              onBlur={this.handleBlur}
            />
          </div>
          <div className="panel-letter-footer">
            <button
              onClick={this.complete}
              className="btn btn-meta-success btn-sm btn-submit"
            >
              {counterpart.translate('window.letter.create')}
            </button>
          </div>
        </div>
      </div>
    );
  }
}

export default connect()(NewLetter);
