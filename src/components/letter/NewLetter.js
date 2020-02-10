import counterpart from 'counterpart';
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { List } from 'immutable';

import { addNotification } from '../../actions/AppActions';
import { patchRequest } from '../../api';
import {
  completeLetter,
  createLetter,
  getTemplates,
} from '../../actions/LetterActions';
import RawList from '../widget/List/RawList';

/**
 * @file Class based component.
 * @module NewLetter
 * @extends Component
 */
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

  /**
   * @async
   * @method UNSAFE_componentWillMount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  async UNSAFE_componentWillMount() {
    const { windowId, docId, handleCloseLetter } = this.props;

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
      handleCloseLetter();
    }
  }

  /**
   * @method getTemplates
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  getTemplates = async () => {
    const res = await getTemplates();

    this.setState({
      templates: List(res.data.values),
    });
  };

  /**
   * @method handleTemplate
   * @summary ToDo: Describe the method
   * @param {*} option
   * @todo Write the documentation
   */
  handleTemplate = async (option) => {
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

  /**
   * @method handleChange
   * @summary ToDo: Describe the method
   * @param {object} target
   * @todo Write the documentation
   */
  handleChange = ({ target: { value: message } }) => {
    this.setState({
      message,
    });
  };

  /**
   * @async
   * @method handleBlur
   * @summary ToDo: Describe the method
   * @param {object} target
   * @todo Write the documentation
   */
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

  /**
   * @method handleListFocus
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleListFocus = () => {
    this.setState({
      listFocused: true,
    });
  };

  /**
   * @method handleListBlur
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleListBlur = () => {
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
   * @async
   * @method renderCancelButton
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  complete = async () => {
    const { letterId } = this.state;
    const { handleCloseLetter, dispatch } = this.props;

    await completeLetter(letterId);

    handleCloseLetter();

    await dispatch(
      addNotification('Letter', 'Letter has been sent.', 5000, 'success')
    );
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { handleCloseLetter } = this.props;
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
                rel="noopener noreferrer"
                className="input-icon input-icon-lg letter-icon-print"
              >
                <i className="meta-icon-print" />
              </a>
              {templates.size > 0 && (
                <div className="letter-templates">
                  <RawList
                    rank="primary"
                    list={templates}
                    onSelect={(option) => this.handleTemplate(option)}
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
                onClick={handleCloseLetter}
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

/**
 * @typedef {object} Props Component props
 * @prop {*} handleCloseLetter
 * @prop {func} dispatch
 * @prop {string} windowId
 * @prop {string} docId
 * @todo Check props. Which proptype? Required or optional?
 */
NewLetter.propTypes = {
  handleCloseLetter: PropTypes.any,
  dispatch: PropTypes.func,
  windowId: PropTypes.string,
  docId: PropTypes.string,
};

export default connect()(NewLetter);
