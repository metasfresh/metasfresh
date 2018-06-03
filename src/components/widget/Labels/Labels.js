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
    cursor: this.props.selected.length,
    focused: false,
    suggestion: null,
    suggestions: [],
  };

  lastTypeAhead = '';

  handleClick = async () => {
    this.input.focus();

    this.setState({ focused: true });

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

    this.setState({
      suggestion: this.firstVisibleSuggestion(suggestions),
      suggestions,
    });
  };

  handleFocus = () => {
    this.input.focus();
  };

  handleBlur = () => {
    this.input.innerHTML = '';

    this.setState({
      focused: false,
      cursor: this.props.selected.length,
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
        suggestion: this.firstVisibleSuggestion(suggestions),
        suggestions,
      });

      this.lastTypeAhead = typeAhead;
    }
  };

  handleKeyDown = event => {
    const typeAhead = event.target.innerHTML;
    const { onChange, selected } = this.props;
    const { cursor } = this.state;

    this.setState({ focused: true });

    switch (event.key) {
      case 'Backspace': {
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

  handleTemporarySelection = suggestion => {
    this.setState({ suggestion });
  };

  handleSuggestionAdd = suggestion => {
    const { onChange, selected } = this.props;
    const { cursor } = this.state;

    this.input.innerHTML = '';

    if (!suggestion) {
      return;
    }

    onChange([...selected, suggestion]);

    this.setState({
      cursor: cursor + 1,
    });
  };

  handleLabelRemove = label => {
    this.props.onChange(this.props.selected.filter(item => item !== label));
  };

  handleLabelClick = label => {
    this.setState({
      cursor: this.props.selected.indexOf(label) + 1,
    });
  };

  handleCancel = () => {
    this.setState({ focused: false });
  };

  unusedSuggestions = () => {
    const selected = new Set(this.props.selected.map(item => item.key));

    return suggestion => !selected.has(suggestion.key);
  };

  firstVisibleSuggestion = suggestions => {
    return suggestions.filter(this.unusedSuggestions())[0];
  };

  render() {
    const { focused, suggestion, cursor } = this.state;
    const { className, selected, tabIndex } = this.props;

    const suggestions = this.state.suggestions.filter(this.unusedSuggestions());

    const labels = selected
      .sort((a, b) => a.caption.localeCompare(b.caption))
      .map(item => (
        <Label
          key={item.key}
          label={item}
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
        ref={ref => {
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
          ref={ref => {
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

export default connect(({ windowHandler }) => ({
  docId: windowHandler.master.docId,
  windowId: windowHandler.master.layout.windowId,
}))(Labels);
