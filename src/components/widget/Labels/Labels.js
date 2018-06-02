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

class Labels extends Component {
  static propTypes = {
    name: PropTypes.string.isRequired,
    selected: PropTypes.array.isRequired,
    className: PropTypes.string,
    onChange: PropTypes.func.isRequired,
    tabIndex: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  };

  static defaultProps = {
    entity: 'window',
    selected: [],
    onChange: () => {},
  };

  state = {
    focused: false,
    suggestion: null,
    suggestions: [],
  };

  lastTypeAhead = '';

  handleClick = async () => {
    this.input.focus();

    const {
      windowType, // windowId
      docId,
      name,
      entity,
      subentity,
      subentityId,
      viewId,
    } = this.props;

    const response = await dropdownRequest({
      docType: windowType,
      docId,
      entity,
      subentity,
      subentityId,
      viewId,
      propertyName: name,
    });

    const suggestions = response.data.values;

    this.setState({ suggestions });
  };

  handleFocus = () => {
    if (document.activeElement !== this.input) {
      return;
    }

    this.setState({
      focused: true,
    });
  };

  handleBlur = () => {
    this.setState({
      focused: false,
    });
  };

  handleKeyUp = async event => {
    const typeAhead = event.target.innerHTML;

    if (typeAhead !== this.lastTypeAhead) {
      const {
        windowType, // windowId
        docId,
        name,
        entity,
        subentity,
        subentityId,
        viewId,
      } = this.props;

      const response = await autocompleteRequest({
        docType: windowType, // windowId
        docId,
        entity,
        subentity,
        subentityId,
        viewId,
        propertyName: name,
        query: typeAhead,
      });

      const suggestions = response.data.values;

      this.setState({
        suggestions,
      });

      this.lastTypeAhead = typeAhead;
    }
  };

  handleKeyDown = event => {
    const typeAhead = event.target.innerHTML;
    const { selected } = this.props;

    if (event.key === 'Backspace') {
      if (selected.length < 1) {
        return;
      } else if (!typeAhead) {
        this.props.onChange(selected.slice(0, selected.length - 1));
      }

      return;
    }

    if (
      ['ArrowTop', 'ArrowRight', 'ArrowBottom', 'ArrowLeft'].includes(event.key)
    ) {
      return;
    }

    // Exclude any single key stroke
    if (event.key.length === 1) {
      return;
    }

    // For any key not checked
    event.preventDefault();
  };

  handleTemporarySelection = suggestion => {
    this.setState({ suggestion });
  };

  handleSuggestionAdd = suggestion => {
    this.input.innerHTML = '';

    if (!suggestion) {
      return;
    }

    this.props.onChange([...this.props.selected, suggestion]);
  };

  handleLabelRemove = label => {
    this.props.onChange(this.props.selected.filter(item => item !== label));
  };

  handleCancel = () => {
    this.input.blur();
  };

  unusedSuggestions = () => {
    const selected = new Set(this.props.selected.map(item => item.key));

    return suggestion => !selected.has(suggestion.key);
  };

  render() {
    const { focused, suggestion } = this.state;

    const suggestions = this.state.suggestions.filter(this.unusedSuggestions());

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
          ref={ref => {
            this.wrapper = ref;
          }}
          className={`${this.props.className} labels`}
          onClick={this.handleClick}
          onFocus={this.handleFocus}
          onBlur={this.handleBlur}
          tabIndex={this.props.tabIndex}
        >
          <span className="labels-wrap">
            {this.props.selected.map(item => (
              <Label
                className="labels-label"
                key={item.key}
                label={item}
                onRemove={this.handleLabelRemove}
              />
            ))}
            <span
              className="labels-input"
              ref={ref => {
                this.input = ref;
              }}
              contentEditable
              onKeyUp={this.handleKeyUp}
              onKeyDown={this.handleKeyDown}
            />
          </span>
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

export default connect(({ windowHandler }) => ({
  docId: windowHandler.master.docId,
  windowId: windowHandler.master.layout.windowId,
}))(Labels);
