import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import Autosuggest from 'react-autosuggest';
import TagsInput from 'react-tagsinput';
import 'react-tagsinput/react-tagsinput.css';

const AutocompleteField = ({
  value,
  suggestValuesForQueryString,
  onChange,
}) => {
  const [suggestions, setSuggestions] = React.useState([]);
  const [tags, setTags] = React.useState([]);

  useEffect(() => {
    setTags(value || []);
  }, [value]);

  const onTagsChanged = (newTags) => {
    const newTagsResolved = newTags.map(resolveNewTagIfNeeded);
    setTags(newTagsResolved);
    onChange && onChange(newTagsResolved);
  };

  const resolveNewTagIfNeeded = (newTag) => {
    // Case: newTag it's a string, i.e. not already resolved
    if (typeof newTag === 'string' || newTag instanceof String) {
      // If suggestions list has only one element => that's actually our match
      if (suggestions?.length === 1) {
        return suggestions[0];
      }
      // Fallback: consider a new email address introduced by user
      else {
        return { key: newTag, caption: newTag };
      }
    }
    // Case: assume it's an already resolved { key, caption } object
    else {
      return newTag;
    }
  };

  const fetchSuggestionsForQueryString = ({ value }) => {
    suggestValuesForQueryString(value).then(setSuggestions);
  };

  const renderSuggestion = (option) => {
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

  const autocompleteRenderInput = ({ addTag, ...props }) => {
    const handleOnChange = (e, { method }) => {
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
        getSuggestionValue={(s) => s}
        renderSuggestion={renderSuggestion}
        inputProps={{ ...props, onChange: handleOnChange }}
        onSuggestionSelected={(e, { suggestion }) => addTag(suggestion)}
        onSuggestionsFetchRequested={fetchSuggestionsForQueryString}
        onSuggestionsClearRequested={() => setSuggestions([])}
      />
    );
  };

  const renderTag = ({
    key,
    tag,
    disabled,
    onRemove,
    className,
    classNameRemove,
  }) => {
    return (
      <span key={key} className={className}>
        {tag?.caption || ''}
        {!disabled && (
          <a className={classNameRemove} onClick={() => onRemove(key)} />
        )}
      </span>
    );
  };

  return (
    <TagsInput
      className="tagsinput"
      renderInput={autocompleteRenderInput}
      renderTag={renderTag}
      inputProps={{ placeholder: '', className: 'email-input' }}
      value={tags}
      onChange={onTagsChanged}
    />
  );
};

AutocompleteField.propTypes = {
  value: PropTypes.array,
  onChange: PropTypes.func,
  suggestValuesForQueryString: PropTypes.func.isRequired,
};

export default AutocompleteField;
