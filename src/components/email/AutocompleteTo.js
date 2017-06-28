import React, { Component } from 'react';
import TagsInput from 'react-tagsinput';
import 'react-tagsinput/react-tagsinput.css';
import Autosuggest from 'react-autosuggest';

import {
    autocompleteRequest
} from '../../actions/GenericActions';

class AutocompleteTo extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            suggestions: [],
            tags: []
        }
    }
    
    handleChange = (tags) => {
        this.setState({tags});
    }
    
    suggestionsFetch = ({value}) => {
        const {emailId} = this.props;
        autocompleteRequest(
            emailId, 'to', value, null, null, null, 'mail', null,
            null, null, false
        ).then(res => {
            this.setState({
                suggestions: res.data.values
            })
        })
    }

    autocompleteRenderInput = ({addTag, ...props}) => {
        const {suggestions} = this.state;
        const {windowId, docId, emailId} = this.props;

        const handleOnChange = (e, {newValue, method}) => {
            if (method === 'enter') {
                e.preventDefault();
            } else {     
                props.onChange(e);
            }
        }

        return (
            <Autosuggest
                ref={props.ref}
                suggestions={suggestions}
                shouldRenderSuggestions={
                    (value) => value && value.trim().length > 0
                }
                getSuggestionValue={s => s[Object.keys(s)[0]]}
                renderSuggestion={
                    s => <span>{s[Object.keys(s)[0]]}</span>
                }
                inputProps={{...props, onChange: handleOnChange}}
                onSuggestionSelected={(e, {suggestion}) => {
                    addTag(suggestion[Object.keys(suggestion)[0]])
                }}
                onSuggestionsFetchRequested={this.suggestionsFetch}
                onSuggestionsClearRequested={() => this.setState(
                    {suggestions: []})
                }
            />
        )
    }

    render() {
        const {tags} = this.state;
        return (
            <TagsInput
                className="tagsinput"
                renderInput={this.autocompleteRenderInput}
                inputProps={{
                    placeholder: 'Add emails',
                    className: 'email-input'
                }}
                value={tags}
                onChange={this.handleChange}
            />
        );
    }
}

export default AutocompleteTo;
