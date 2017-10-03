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
            tags: this.getTags()
        }
    }

    getTags=()=>{
        const {to} = this.props;
        let tagsUpdated=[];

        to.map(item=>{
            tagsUpdated.push(item[Object.keys(item)[0]]);
        });
        return tagsUpdated;
    }

    handleChange = (tags) => {
        this.setState({tags});
    }

    suggestionsFetch = ({value}) => {
        const {emailId} = this.props;
        autocompleteRequest({
            attribute: false,
            docType: emailId,
            entity: 'mail',
            propertyName: 'to',
            query: value
        }).then(response => {
            this.setState({
                suggestions: response.data.values
            })
        });
    }

    autocompleteRenderInput = ({addTag, ...props}) => {
        const {suggestions} = this.state;

        const handleOnChange = (e, {method}) => {
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
                    placeholder: '',
                    className: 'email-input'
                }}
                value={tags}
                onChange={this.handleChange}
            />
        );
    }
}

export default AutocompleteTo;
