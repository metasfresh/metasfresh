import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';
import classnames from 'classnames';

import { dropdownRequest } from '../../actions/GenericActions';

import DocumentStatusContextShortcuts from '../keyshortcuts/DocumentStatusContextShortcuts';
import Prompt from '../../components/app/Prompt';

/**
 * @file Document Status/Action Button (Complete, Reverse)
 * @module ActionButton
 * @extends Component
 */
class ActionButton extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      list: [],
      selected: 0,
      prompt: {
        isOpen: {},
        title: 'Confirm',
        text: 'Are you sure?',
        yes: 'Cancel',
      },
    };
  }

  /**
   * @method componentDidMount
   * @summary Lifecycle method
   *          Fetches the status list once the component is mounted
   */
  componentDidMount() {
    this.fetchStatusList();
  }

  /**
   * @method handleKeyDown
   * @summary ToDo: Describe the method
   * @param {object} e
   * @todo Write the documentation
   */
  handleKeyDown = (e) => {
    const { list, selected } = this.state;
    switch (e.key) {
      case 'ArrowDown':
        e.preventDefault();
        this.navigate(true);
        break;
      case 'ArrowUp':
        e.preventDefault();
        this.navigate();
        break;
      case 'Enter':
        e.preventDefault();
        if (selected != null) {
          this.handleChangeStatus(list[selected]);
        }
        break;
      case 'Escape':
        e.preventDefault();
        this.handleDropdownBlur();
        break;
    }
  };

  /**
   * @method navigate
   * @summary ToDo: Describe the method
   * @param {bool} up
   * @todo Write the documentation
   */
  navigate = (up) => {
    const { selected, list } = this.state;
    const next = up ? selected + 1 : selected - 1;

    this.setState({
      selected: next >= 0 && next <= list.length ? next : selected,
    });
  };

  /**
   * @method handleDropdownBlur
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleDropdownBlur = () => {
    if (this.statusDropdown) {
      this.statusDropdown.classList.remove('dropdown-status-open');
    }
  };

  /**
   * @method handleDropdownFocus
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleDropdownFocus = () => {
    const { dropdownOpenCallback } = this.props;

    this.fetchStatusList();
    dropdownOpenCallback();
    this.statusDropdown.classList.add('dropdown-status-open');
  };

  /**
   * @method fetchStatusList
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  fetchStatusList() {
    const { windowType, fields, dataId } = this.props;
    if (!dataId) {
      return Promise.resolve(null);
    }

    return dropdownRequest({
      docId: dataId,
      docType: windowType,
      entity: 'window',
      propertyName: fields[1].field,
    })
      .then((res) => {
        this.setState({
          list: res.data.values,
        });
      })
      .catch((e) => e);
  }

  /**
   * @method handleChangeStatus
   * @summary ToDo: Describe the method
   * @param {object} status
   * @todo Write the documentation
   */
  handleChangeStatus = (status) => {
    const prompt = { ...this.state.prompt };
    const promptOpenClone = { ...prompt.isOpen };
    if (Object.prototype.hasOwnProperty.call(status, 'validationInformation')) {
      promptOpenClone[status.key] = true;
      this.setState({
        prompt: {
          isOpen: promptOpenClone,
          title: status.validationInformation.title,
          text: status.validationInformation.question,
          yes: status.validationInformation.answerYes,
          no: status.validationInformation.answerNo,
        },
      });
    } else {
      this.setState({ prompt: { ...prompt, isOpen: false } });
      this.processStatus(status, false);
    }
  };

  /**
   * @method getStatusClassName
   * @summary ToDo: Describe the method
   * @param {string} abrev
   * @todo Write the documentation
   */
  getStatusClassName = (abrev) => {
    const { data } = this.props;

    if (get(data, ['action', 'value', 'key'], null) !== abrev) {
      return '';
    }

    if (abrev === 'DR') {
      return 'dropdown-status-item-def';
    } else if (abrev === 'CO') {
      return 'dropdown-status-item-def-1';
    } else {
      return '';
    }
  };

  /**
   * @method getStatusContext
   * @summary ToDo: Describe the method
   * @param {string} abrev
   * @todo Write the documentation
   */
  getStatusContext = (abrev) => {
    if (abrev === 'DR') {
      return 'primary';
    } else if (abrev === 'CO') {
      return 'success';
    } else {
      return 'default';
    }
  };

  /**
   * @method renderStatusList
   * @summary Renders the status list by looping through the list array passed as parameter
   * @param {array} list
   */
  renderStatusList = (list) => {
    const { selected } = this.state;

    return list.map((item, index) => {
      return (
        <li
          key={index}
          className={
            'dropdown-status-item ' +
            (selected === index ? 'dropdown-status-item-on-key ' : '') +
            this.getStatusClassName(item.key)
          }
          title={item.description ? item.description : null}
          onClick={() => this.handleChangeStatus(item)}
        >
          {item.caption}
        </li>
      );
    });
  };

  /**
   * @method processStatus
   * @summary Processes the actual status. Depending of the boolean value of option it will show or
   *          hide the prompt for the user
   * @param {object} status
   * @param {boolean} option
   */
  processStatus = (status, option) => {
    const { onChange } = this.props;
    const changePromise = onChange(status);

    this.statusDropdown.blur();
    if (changePromise instanceof Promise) {
      changePromise.then(() => {
        return this.fetchStatusList();
      });
    }
    if (option) {
      this.setState({ prompt: { ...this.state.prompt, isOpen: false } });
    }
  };

  handleCancel = () => {
    const { prompt } = this.state;

    this.setState({ prompt: { ...prompt, isOpen: false } }, () => {
      this.statusDropdown.blur();
    });
  };

  handleSubmit = () => {
    const { list, prompt } = this.state;

    let activePrompt = null;
    list.forEach((listItem) => {
      if (prompt.isOpen[listItem.key]) {
        activePrompt = listItem.key;
      }
    });

    this.processStatus(
      list.find(
        (elem) =>
          Object.prototype.hasOwnProperty.call(elem, 'validationInformation') &&
          elem.key === activePrompt
      ),
      true
    );
  };

  documentCompleteStatus = () => {
    if (this.isDisabled()) return false;
    const { list } = this.state;

    this.handleChangeStatus(list.find((elem) => elem.key === 'CO'));
  };

  setRef = (ref) => {
    this.statusDropdown = ref;
  };

  /**
   * @method isDisabled
   * @summary the action button can be disabled when it has the property readonly `true` but it can also be disabled in case
   *          of a process running situation or when a modal window is active
   */
  isDisabled = () => {
    const { modalVisible, readonly, processStatus } = this.props;
    return readonly || processStatus === 'pending' || modalVisible;
  };

  /**
   * @method render
   * @summary Main render function for the ActionButton
   */
  render() {
    const { data, modalVisible } = this.props;
    const { list, prompt } = this.state;
    const abrev = get(data, ['status', 'value', 'key'], null);
    const status = this.getStatusContext(abrev);
    let value;

    if (abrev) {
      value = data.status.value.caption;
    }
    let showPrompt = false;
    list.forEach((listItem) => {
      if (prompt.isOpen[listItem.key]) {
        showPrompt = true;
      }
    });

    return (
      <div
        onKeyDown={this.handleKeyDown}
        className={classnames(
          'meta-dropdown-toggle dropdown-status-toggler js-dropdown-toggler',
          {
            disabled: this.isDisabled(),
          }
        )}
        tabIndex={modalVisible ? -1 : 0}
        ref={this.setRef}
        onBlur={this.handleDropdownBlur}
        onFocus={this.handleDropdownFocus}
      >
        {showPrompt && (
          <Prompt
            title={prompt.title}
            text={prompt.text}
            buttons={{ submit: prompt.yes, cancel: prompt.no }}
            onCancelClick={this.handleCancel}
            onSubmitClick={this.handleSubmit}
          />
        )}

        {value ? (
          <div className={`tag tag-${status}`}>{value}</div>
        ) : (
          <div
            className={`tag tag-${status}`}
            dangerouslySetInnerHTML={{ __html: '&nbsp;' }}
          />
        )}
        <i className={`meta-icon-chevron-1 meta-icon-${status}`} />
        <ul className="dropdown-status-list">{this.renderStatusList(list)}</ul>
        <DocumentStatusContextShortcuts
          handleDocumentCompleteStatus={this.documentCompleteStatus}
        />
      </div>
    );
  }
}
/**
 * Default props
 */
ActionButton.defaultProps = { readonly: false };

/**
 * @typedef {object} Props Component props
 * @prop {bool} modalVisible
 * @prop {*} data
 * @prop {*} onChange
 * @prop {*} dropdownOpenCallback
 * @prop {*} windowType
 * @prop {*} fields
 * @prop {*} dataId
 * @prop {*} docId
 * @prop {string} activeTab
 * @todo Check title, buttons. Which proptype? Required or optional?
 */
ActionButton.propTypes = {
  modalVisible: PropTypes.bool.isRequired,
  data: PropTypes.any,
  onChange: PropTypes.func,
  dropdownOpenCallback: PropTypes.any,
  windowType: PropTypes.any,
  fields: PropTypes.any,
  dataId: PropTypes.any,
  docId: PropTypes.any,
  activeTab: PropTypes.string,
  processStatus: PropTypes.string,
  readonly: PropTypes.bool,
};

const mapStateToProps = ({ windowHandler, appHandler }) => ({
  modalVisible: windowHandler.modal.visible,
  processStatus: appHandler.processStatus,
});

export default connect(mapStateToProps, null, null, {
  forwardRef: true,
})(ActionButton);
