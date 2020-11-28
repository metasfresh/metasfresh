import PropTypes from 'prop-types';
import React, { Component } from 'react';
import TetherComponent from 'react-tether';
import { connect } from 'react-redux';

import {
  autocompleteRequest,
  dropdownRequest,
} from '../../../actions/GenericActions';
import Label from './Label';
import SelectionDropdown from '../SelectionDropdown';

/**
 * @file Class based component.
 * @module Labels
 * @extends Component
 */
class Labels extends Component {
  state = {
    cursor: this.props.selected.length,
    focused: false,
    suggestion: null,
    suggestions: [],
  };

  lastTypeAhead = '';

  /**
   * @async
   * @method handleClick
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleClick = async () => {
    const {
      windowType, // windowId
      docId,
      name,
      entity,
      subentity,
      subentityId,
      tabId,
      rowId,
      viewId,
      readonly,
    } = this.props;

    if (readonly) return false;

    this.input.focus();
    this.setState({ focused: true });

    const response = await dropdownRequest({
      docType: windowType,
      docId,
      entity,
      subentity,
      subentityId,
      tabId,
      rowId,
      viewId,
      propertyName: name,
    });

    const suggestions = response.data.values;

    this.setState({
      suggestion: this.firstVisibleSuggestion(suggestions),
      suggestions,
    });
  };

  /**
   * @method handleFocus
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleFocus = () => {
    this.input.focus();
  };

  /**
   * @method handleBlur
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleBlur = () => {
    this.clearInput();

    this.setState({
      focused: false,
      cursor: this.props.selected.length,
    });
  };

  /**
   * @async
   * @method handleKeyUp
   * @summary ToDo: Describe the method
   * @param {object} event
   * @todo Write the documentation
   */
  handleKeyUp = async (event) => {
    const typeAhead = event.target.innerHTML;

    if (typeAhead !== this.lastTypeAhead) {
      const {
        windowType, // windowId
        docId,
        name,
        entity,
        subentity,
        subentityId,
        tabId,
        rowId,
        viewId,
      } = this.props;

      const response = await autocompleteRequest({
        docType: windowType, // windowId
        docId,
        entity,
        subentity,
        subentityId,
        tabId,
        rowId,
        viewId,
        propertyName: name,
        query: typeAhead,
      });

      const suggestions = response.data.values;

      this.setState({
        suggestion: this.firstVisibleSuggestion(suggestions),
        suggestions,
      });

      this.lastTypeAhead = typeAhead;
    }
  };

  /**
   * @method handleKeyDown
   * @summary ToDo: Describe the method
   * @param {object} event
   * @todo Write the documentation
   */
  handleKeyDown = (event) => {
    const typeAhead = event.target.innerHTML;
    const { onChange, selected } = this.props;
    const { cursor } = this.state;

    this.setState({ focused: true });

    switch (event.key) {
      case 'Backspace': {
        this.setState({ focused: false });
        if (selected.length < 1) {
          return;
        }

        if (!typeAhead) {
          if (cursor === 0) {
            return;
          }

          const selectedNew = [...selected];
          selectedNew.splice(cursor - 1, 1);

          onChange(selectedNew);

          this.setState({
            cursor: cursor - 1,
          });
        }

        return;
      }

      case 'ArrowLeft': {
        if (typeAhead) {
          return;
        }

        this.setState({ cursor: cursor - 1 });

        return;
      }

      case 'ArrowRight': {
        if (typeAhead) {
          return;
        }

        this.setState({ cursor: cursor + 1 });

        return;
      }
    }

    if (['Tab', 'ArrowTop', 'ArrowBottom'].includes(event.key)) {
      return;
    }

    // Exclude any single key stroke
    if (event.key.length === 1) {
      return;
    }

    // For any key not checked
    event.preventDefault();
  };

  /**
   * @method handleTemporarySelection
   * @summary ToDo: Describe the method
   * @param {*} suggestion
   * @todo Write the documentation
   */
  handleTemporarySelection = (suggestion) => {
    this.setState({ suggestion });
  };

  /**
   * @method handleSuggestionAdd
   * @summary ToDo: Describe the method
   * @param {*} suggestion
   * @todo Write the documentation
   */
  handleSuggestionAdd = (suggestion) => {
    const { onChange, selected } = this.props;
    const { cursor } = this.state;

    this.clearInput();

    if (!suggestion) {
      return;
    }

    onChange([...selected, suggestion]);

    this.setState({
      cursor: cursor + 1,
      suggestion: null,
    });
  };

  /**
   * @method handleLabelRemove
   * @summary ToDo: Describe the method
   * @param {*} label
   * @todo Write the documentation
   */
  handleLabelRemove = (label) => {
    this.props.onChange(this.props.selected.filter((item) => item !== label));
  };

  /**
   * @method handleLabelClick
   * @summary ToDo: Describe the method
   * @param {*} label
   * @todo Write the documentation
   */
  handleLabelClick = (label) => {
    this.setState({
      cursor: this.props.selected.indexOf(label) + 1,
    });
  };

  /**
   * @method handleCancel
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleCancel = () => {
    this.clearInput();

    this.setState({ focused: false });
  };

  /**
   * @method unusedSuggestions
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  unusedSuggestions = () => {
    const selected = new Set(this.props.selected.map((item) => item.key));

    return (suggestion) => !selected.has(suggestion.key);
  };

  /**
   * @method firstVisibleSuggestion
   * @summary ToDo: Describe the method
   * @param {*} suggestions
   * @todo Write the documentation
   */
  firstVisibleSuggestion = (suggestions) => {
    return suggestions.filter(this.unusedSuggestions())[0];
  };

  /**
   * @method clearInput
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  clearInput = () => {
    this.input.innerHTML = '';
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { focused, suggestion, cursor } = this.state;
    const { className, selected, tabIndex, readonly } = this.props;

    const suggestions = this.state.suggestions.filter(this.unusedSuggestions());

    const labels = selected
      .sort((a, b) => a.caption.localeCompare(b.caption))
      .map((item) => (
        <Label
          key={item.key}
          label={item}
          readonly={readonly}
          onClick={this.handleLabelClick}
          onRemove={this.handleLabelRemove}
        />
      ));

    labels.splice(
      cursor % (selected.length + 1),
      0,
      <span
        key="input"
        className="labels-input"
        ref={(ref) => {
          this.input = ref;
        }}
        contentEditable
        onKeyUp={this.handleKeyUp}
        onKeyDown={this.handleKeyDown}
        tabIndex={tabIndex}
      />
    );

    return (
      <TetherComponent
        attachment="top left"
        targetAttachment="bottom left"
        constraints={[
          {
            to: 'scrollParent',
          },
          {
            to: 'window',
            pin: ['bottom'],
          },
        ]}
      >
        <span
          ref={(ref) => {
            this.wrapper = ref;
          }}
          className={`${className} labels`}
          onClick={this.handleClick}
          onFocus={this.handleFocus}
          onBlur={this.handleBlur}
        >
          <span className="labels-wrap">{labels}</span>
        </span>
        {focused && (
          <SelectionDropdown
            options={suggestions}
            empty="There are no labels available"
            selected={suggestion}
            width={this.wrapper.offsetWidth}
            onChange={this.handleTemporarySelection}
            onSelect={this.handleSuggestionAdd}
            onCancel={this.handleCancel}
          />
        )}
      </TetherComponent>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {string} name
 * @prop {array} selected
 * @prop {string} [className]
 * @prop {func} onChange
 * @prop {string|number} [tabIndex]
 * @prop {*} windowType
 * @prop {*} docId
 * @prop {*} name
 * @prop {*} entity
 * @prop {*} subentity
 * @prop {*} subentityId
 * @prop {*} viewId
 * @todo Check title, buttons. Which proptype? Required or optional?
 */
Labels.propTypes = {
  name: PropTypes.string.isRequired,
  selected: PropTypes.array.isRequired,
  className: PropTypes.string,
  onChange: PropTypes.func.isRequired,
  tabIndex: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  windowType: PropTypes.any,
  docId: PropTypes.any,
  entity: PropTypes.any,
  subentity: PropTypes.any,
  subentityId: PropTypes.any,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  viewId: PropTypes.any,
  readonly: PropTypes.bool,
};

Labels.defaultProps = {
  entity: 'window',
  selected: [],
  onChange: () => {},
};

export default connect(({ windowHandler }) => ({
  docId: windowHandler.master.docId,
  windowId: windowHandler.master.layout.windowId,
}))(Labels);
