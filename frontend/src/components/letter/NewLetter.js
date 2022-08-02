import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import counterpart from 'counterpart';

import { addNotification } from '../../actions/AppActions';
import {
  completeLetter,
  createLetter,
  getTemplates,
  applyTemplate,
  patchMessage,
} from '../../actions/LetterActions';

import RawList from '../widget/List/RawList';

class NewLetter extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      init: false,
      cached: {},
      templates: [],
      template: {},
      listFocused: true,
      listToggled: false,
    };
  }

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
        await this.loadTemplates();
      } catch (error) {
        // eslint-disable-next-line no-console
        console.error(error);
      }
    } catch (error) {
      handleCloseLetter();
    }
  }

  loadTemplates = async () => {
    const axiosResponse = await getTemplates();
    const { values, defaultValue } = axiosResponse.data;

    this.setState({ templates: values });

    //
    // Apply default template if any
    if (values && values.length > 0 && defaultValue) {
      const defaultTemplate = values.find(
        (template) => template.key === defaultValue
      );
      if (defaultTemplate != null) {
        await this.handleTemplate(defaultTemplate);
      }
    }
  };

  handleTemplate = async (option) => {
    const { letterId, template } = this.state;

    if (template === option) {
      return;
    }

    const letter = await applyTemplate(letterId, option);

    this.setState({
      ...letter,
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

    const letter = await patchMessage(letterId, message);

    this.setState({
      ...letter,
      cached: letter,
      listFocused: false,
    });
  };

  handleListFocus = () => this.setState({ listFocused: true });

  handleListBlur = () => this.setState({ listFocused: false });

  closeTemplatesList = () => this.setState({ listToggled: false });

  openTemplatesList = () => this.setState({ listToggled: true });

  complete = async () => {
    const { letterId } = this.state;
    const { handleCloseLetter, dispatch } = this.props;

    await completeLetter(letterId);

    handleCloseLetter();

    await dispatch(
      addNotification('Letter', 'Letter has been sent.', 5000, 'success')
    );
  };

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
              {templates.length > 0 && (
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

NewLetter.propTypes = {
  handleCloseLetter: PropTypes.func.isRequired,
  dispatch: PropTypes.func.isRequired,
  windowId: PropTypes.string,
  docId: PropTypes.string,
};

export default connect()(NewLetter);
