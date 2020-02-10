import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { fetchTopActions } from '../../actions/WindowActions';
import { dropdownRequest } from '../../actions/GenericActions';
import DocumentStatusContextShortcuts from '../keyshortcuts/DocumentStatusContextShortcuts';

/**
 * @file Class based component.
 * @module ActionButton
 * @extends Component
 */
class ActionButton extends Component {
  constructor(props) {
    super(props);

    this.state = {
      list: [],
      selected: 0,
    };
  }

  /**
   * @method componentDidMount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  componentDidMount() {
    this.fetchStatusList();
  }

  /**
   * @method handleKeyDown
   * @summary ToDo: Describe the method
   * @param {object} event
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
      return;
    }
    dropdownRequest({
      docId: dataId,
      docType: windowType,
      entity: 'window',
      propertyName: fields[1].field,
    }).then((res) => {
      this.setState({
        list: res.data.values,
      });
    });
  }

  /**
   * @method handleChangeStatus
   * @summary ToDo: Describe the method
   * @param {*} status
   * @todo Write the documentation
   */
  handleChangeStatus = (status) => {
    const {
      onChange,
      docId,
      windowType,
      activeTab,
      fetchTopActions,
    } = this.props;
    const changePromise = onChange(status);

    this.statusDropdown.blur();
    if (changePromise instanceof Promise) {
      changePromise.then(() => {
        fetchTopActions(windowType, docId, activeTab);

        return this.fetchStatusList();
      });
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

    if (data.action.value && data.action.value.key !== abrev) {
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
   * @summary ToDo: Describe the method
   * @param {*} list
   * @todo Write the documentation
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
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { data, modalVisible } = this.props;
    const { list } = this.state;
    const abrev = data.status.value && data.status.value.key;
    const status = this.getStatusContext(abrev);
    let value;

    if (abrev) {
      value = data.status.value.caption;
    }

    return (
      <div
        onKeyDown={this.handleKeyDown}
        className="meta-dropdown-toggle dropdown-status-toggler js-dropdown-toggler"
        tabIndex={modalVisible ? -1 : 0}
        ref={(c) => (this.statusDropdown = c)}
        onBlur={this.handleDropdownBlur}
        onFocus={this.handleDropdownFocus}
      >
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
          handleDocumentCompleteStatus={() => {
            this.handleChangeStatus(list.find((elem) => elem.key === 'CO'));
          }}
        />
      </div>
    );
  }
}

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
  fetchTopActions: PropTypes.func.isRequired,
  data: PropTypes.any,
  onChange: PropTypes.any,
  dropdownOpenCallback: PropTypes.any,
  windowType: PropTypes.any,
  fields: PropTypes.any,
  dataId: PropTypes.any,
  docId: PropTypes.any,
  activeTab: PropTypes.string,
};

const mapStateToProps = ({ windowHandler }) => ({
  modalVisible: windowHandler.modal.visible,
});

export default connect(
  mapStateToProps,
  { fetchTopActions }
)(ActionButton);
