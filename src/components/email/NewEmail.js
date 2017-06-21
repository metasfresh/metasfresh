import React, { Component } from 'react';
import TagsInput from 'react-tagsinput';
import 'react-tagsinput/react-tagsinput.css';
import Autosuggest from 'react-autosuggest';

class NewEmail extends Component {
    constructor(props){
        super(props);

        this.state = {
            tags: [],
            emails: [
                {
                    name: 'Musterfrau',
                    mail: 'musterfrau@gmail.com'
                },
                {
                    name: 'metas-xx',
                    mail: 'metas-xx@gmail.com'
                },
                {
                    name: 'test',
                    mail: 'test@gmail.com'
                },
                {
                    name: 'Annie',
                    mail: 'annie@gmail.com'
                },
                {
                    name: 'Heinz Muller',
                    mail: 'muller@gmail.com'
                },
                {
                    name: 'Jorg Jorgsen',
                    mail: 'jorg@gmail.com'
                }
            ]
        }
    }

    handleChange = (tags) => {
        this.setState({tags})
    }

    autocompleteRenderInput = ({addTag, ...props}) => {
        const {emails} = this.state;

        const handleOnChange = (e, {newValue, method}) => {
            if (method === 'enter') {
                e.preventDefault()
            } else {
                props.onChange(e)
            }
        }

        const inputValue = (
            props.value && props.value.trim().toLowerCase()
            ) || ''
        const inputLength = inputValue.length

        let suggestions = emails.filter((item) => {
            return item.name.toLowerCase().slice(0, inputLength) === inputValue
        })

        return (
            <Autosuggest
                ref={props.ref}
                suggestions={suggestions}
                shouldRenderSuggestions={
                    (value) => value && value.trim().length > 0
                }
                getSuggestionValue={(suggestion) => suggestion.name}
                renderSuggestion={
                    (suggestion) => <span>{suggestion.name}</span>
                }
                inputProps={{...props, onChange: handleOnChange}}
                onSuggestionSelected={(e, {suggestion}) => {
                    addTag(suggestion.name)
                }}
                onSuggestionsClearRequested={() => {}}
                onSuggestionsFetchRequested={() => {}}
            />
        )
    }

    render() {
        const {handleCloseEmail} = this.props
        const {emails} = this.state
        return (
            <div className="screen-freeze">
                <div className="panel panel-modal panel-email panel-modal-primary">
                    <div className="panel-email-header-wrapper">
                        <div className="panel-email-header panel-email-header-top">
                            New message
                            <div
                                className="input-icon input-icon-lg"
                                onClick={handleCloseEmail}
                            >
                                <i className="meta-icon-close-1"/>
                            </div>

                        </div>
                        <div className="panel-email-header panel-email-bright">
                            <div className="panel-email-data-wrapper">
                                <span>To:</span>
                                <TagsInput
                                    className="tagsinput"
                                    renderInput={this.autocompleteRenderInput}
                                    inputProps={
                                        {placeholder: 'Add emails',
                                        className: 'email-input'}
                                    }
                                    value={this.state.tags}
                                    onChange={this.handleChange}
                                />

                            </div>
                        </div>
                        <div className="panel-email-header panel-email-bright">
                            <div className="panel-email-data-wrapper">
                                <span>Topic:</span> <input className="email-input email-input-msg" type="text"/>
                            </div>
                        </div>
                    </div>
                    <div className="panel-email-body">
                        <textarea></textarea>
                    </div>
                    <div className="email-attachments-wrapper">
                        <div className="attachment">
                            <div>
                                attachment.pdf <span>(230 kb)</span>
                            </div>
                            <div className="input-icon input-icon-lg">
                                <i className="meta-icon-close-1"/>
                            </div>
                        </div>
                        <div className="attachment">
                            <div>
                                new_metasfresh.pdf <span>(230 kb)</span>
                            </div>
                            <div className="input-icon input-icon-lg">
                                <i className="meta-icon-close-1"/>
                            </div>
                        </div>
                        <div className="attachment">
                            <div>
                                xxx.pdf <span>(230 kb)</span>
                            </div>
                            <div className="input-icon input-icon-lg">
                                <i className="meta-icon-close-1"/>
                            </div>
                        </div>
                    </div>
                    <div className="panel-email-footer">
                        <div><i className="meta-icon-attachments"/> add attachment</div>
                        <button className="btn btn-meta-success btn-sm btn-submit">Send</button>
                    </div>

                </div>
            </div>
        )
    }
}

export default NewEmail;
