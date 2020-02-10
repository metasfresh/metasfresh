import React, { Component } from 'react';
import Autosuggest from 'react-autosuggest';
import TagsInput from 'react-tagsinput';
import classnames from 'classnames';
import PropTypes from 'prop-types';

import 'react-tagsinput/react-tagsinput.css';

import { autocompleteRequest } from '../../actions/GenericActions';

class AutocompleteTo extends Component {
  static propTypes = {
    to: PropTypes.array,
    emailId: PropTypes.string,
  };

  constructor(props) {
    super(props);

    this.state = {
      value: '',
      suggestions: [],
      tags: this.getTags(),
    };
  }

  getTags = () => {
    const { to } = this.props;
    let tagsUpdated = [];

    to.map((item) => {
      tagsUpdated.push(item.caption);
    });
    return tagsUpdated;
  };

  handleTagChange = (tags) => {
    this.setState({ tags });
  };

  suggestionsFetch = ({ value }) => {
    const { emailId } = this.props;
    autocompleteRequest({
      attribute: false,
      docType: emailId,
      entity: 'mail',
      propertyName: 'to',
      query: value,
    }).then((response) => {
      this.setState({
        suggestions: response.data.values,
      });
    });
  };

  renderSuggestion = (option) => {
    const { tags } = this.state;

    return (
      <span
        className={classnames('autosuggest-option', {
          selected: tags.includes(option.caption),
        })}
      >
        {option.caption}
      </span>
    );
  };

  autocompleteRenderInput = ({ addTag, ...props }) => {
    const { suggestions } = this.state;
    const handleOnChange = (e, { newValue, method }) => {
      this.setState({ value: newValue });
      switch (method) {
        case 'type':
          props.onChange(e);
          break;
        default:
          e.preventDefault();
      }
    };

    return (
      <Autosuggest
        ref={props.ref}
        suggestions={suggestions}
        shouldRenderSuggestions={(value) => value && value.trim().length > 0}
        getSuggestionValue={(s) => s.caption}
        renderSuggestion={this.renderSuggestion}
        inputProps={{ ...props, onChange: handleOnChange }}
        onSuggestionSelected={(e, { suggestion }) => {
          addTag(suggestion.caption);
        }}
        onSuggestionsFetchRequested={this.suggestionsFetch}
        onSuggestionsClearRequested={() => this.setState({ suggestions: [] })}
      />
    );
  };

  render() {
    const { tags } = this.state;

    return (
      <TagsInput
        className="tagsinput"
        renderInput={this.autocompleteRenderInput}
        inputProps={{
          placeholder: '',
          className: 'email-input',
        }}
        value={tags}
        onChange={this.handleTagChange}
      />
    );
  }
}

export default AutocompleteTo;
