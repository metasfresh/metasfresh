import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import TetherComponent from 'react-tether';

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
export default class Labels extends PureComponent {
  state = {
    cursor: this.props.selected.length,
    focused: false,
    suggestion: null,
    suggestions: [],
    labelsUpdated: false,
  };

  lastTypeAhead = '';

  /**
   * @async
   * @method handleClick
   * @summary Called when click is performed on the labels. Focus is set and the data is fetched from the `/dropdown' API
   *          The corresponding suggestion are updated in the local state.
   */
  handleClick = async () => {
    const {
      windowId, // windowId
      name,
      entity,
      subentity,
      subentityId,
      tabId,
      rowId,
      viewId,
      readonly,
      dataId,
    } = this.props;

    if (readonly) return false;

    this.input.focus();
    this.setState({ focused: true });

    const response = await dropdownRequest({
      docType: windowId,
      docId: dataId,
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
   * @summary Sets the focus on the label
   */
  handleFocus = () => {
    this.input.focus();
  };

  /**
   * @method handleBlur
   * @summary Clears the focus and sets the cursor to the selected array length
   */
  handleBlur = () => {
    this.clearInput();

    this.setState({
      focused: false,
    });
  };

  /**
   * @async
   * @method handleKeyUp
   * @summary Autocomplete logic using typeahead API provided results
   * @param {object} event
   */
  handleKeyUp = async (event) => {
    const typeAhead = event.target.innerHTML;

    if (typeAhead !== this.lastTypeAhead) {
      const {
        windowId,
        name,
        entity,
        subentity,
        subentityId,
        tabId,
        rowId,
        viewId,
        dataId,
      } = this.props;

      const response = await autocompleteRequest({
        docType: windowId,
        docId: dataId,
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
   * @summary Navigation logic for the labels (also performing deletion when backspace is pressed)
   * @param {object} event
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
            cursor: cursor === 1 ? selectedNew.length : cursor - 1,
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
   * @summary Updates the state with a temporary suggestion
   * @param {*} suggestion
   */
  handleTemporarySelection = (suggestion) => {
    this.setState({ suggestion });
  };

  /**
   * @method handleSuggestionAdd
   * @summary This is where the actual addition of the selected suggestion item happens
   * @param {*} suggestion
   */
  handleSuggestionAdd = (suggestion) => {
    const { onChange, selected } = this.props;

    this.clearInput();

    if (!suggestion) {
      return;
    }

    onChange([...selected, suggestion]);

    const newCursor = [...selected, suggestion].length;

    this.setState({
      cursor: newCursor,
      suggestion: null,
      labelsUpdated: true,
    });
  };

  /**
   * @method handleLabelRemove
   * @summary Logic to remove the label provided as parameter from the existing labels
   * @param {*} label
   */
  handleLabelRemove = (label) => {
    this.props.onChange(this.props.selected.filter((item) => item !== label));
    this.setState({ labelsUpdated: true });
  };

  /**
   * @method handleLabelClick
   * @summary Sets the cursor at the label position and clears the labelsUpdated flag
   * @param {*} label
   */
  handleLabelClick = (label) => {
    this.setState({
      cursor: this.props.selected.indexOf(label) + 1,
      labelsUpdated: false,
    });
  };

  /**
   * @method handleCancel
   * @summary Cleaars the input and disables the focus in the state
   */
  handleCancel = () => {
    this.clearInput();

    this.setState({ focused: false });
  };

  /**
   * @method unusedSuggestions
   * @summary Returns the suggestions that are not already selected
   */
  unusedSuggestions = () => {
    const selected = new Set(this.props.selected.map((item) => item.key));

    return (suggestion) => !selected.has(suggestion.key);
  };

  /**
   * @method firstVisibleSuggestion
   * @summary Returns the first visible suggestion from a list of suggestions
   * @param {*} suggestions
   */
  firstVisibleSuggestion = (suggestions) => {
    return suggestions.filter(this.unusedSuggestions())[0];
  };

  /**
   * @method clearInput
   * @summary Clears the input by setting the innerHTML to an empty string
   */
  clearInput = () => {
    this.input.innerHTML = '';
  };

  render() {
    const { focused, suggestion, cursor, labelsUpdated } = this.state;
    const { className, selected, tabIndex, readonly } = this.props;

    const suggestions = this.state.suggestions.filter(this.unusedSuggestions());

    const newCursor = labelsUpdated
      ? selected.length
      : cursor % (selected.length + 1);
    const labels = selected
      .sort((a, b) => {
        return a.caption.localeCompare(b.caption);
      })
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
      newCursor,
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
        renderTarget={(ref) => (
          <span ref={ref}>
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
          </span>
        )}
        renderElement={(ref) =>
          focused && (
            <SelectionDropdown
              ref={ref}
              options={suggestions}
              empty="There are no labels available"
              selected={suggestion}
              width={this.wrapper.offsetWidth}
              onChange={this.handleTemporarySelection}
              onSelect={this.handleSuggestionAdd}
              onCancel={this.handleCancel}
            />
          )
        }
      />
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
 * @prop {*} windowId
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
  windowId: PropTypes.any,
  docId: PropTypes.any,
  entity: PropTypes.any,
  subentity: PropTypes.any,
  subentityId: PropTypes.any,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  viewId: PropTypes.any,
  readonly: PropTypes.bool,
  dataId: PropTypes.string,
};

Labels.defaultProps = {
  entity: 'window',
};
