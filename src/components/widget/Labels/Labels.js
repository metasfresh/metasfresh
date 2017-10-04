import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
    dropdownRequest,
    autocompleteRequest
} from '../../../actions/GenericActions';
import Label from './Label';
import Suggestion from './Suggestion';

class Labels extends Component {
    state = {
        focused: false,
        values: [],
        suggestions: []
    };

    handleClick = async () => {
        this.input.focus();

        const { windowId, docId, name } = this.props;

        const response = await dropdownRequest({
            docId,
            entity: 'window',
            propertyName: name,
            viewId: windowId
        });

        const { values } = response.data;

        this.setState({ values });
    }

    handleFocus = () => {
        if (document.activeElement !== this.input) {
            return;
        }

        this.setState({
            focused: true
        });
    }

    handleBlur = () => {
        this.setState({
            focused: false
        });
    }

    handleKeyUp = async event => {
        const typeAhead = event.target.innerHTML;
        const { windowId, docId, name } = this.props;

        const response = await autocompleteRequest({
            docId,
            entity: 'window',
            propertyName: name,
            query: typeAhead,
            viewId: windowId
        });

        const { values } = response.data;

        this.setState({
            suggestions: values
        });
    }

    handleKeyDown = event => {
        const typeAhead = event.target.innerHTML;
        const { selected } = this.props;

        if (typeAhead) {
            if (event.key === 'Enter') {
                const suggestion = this.state.suggestions.filter(
                    this.unusedSuggestions()
                )[0];

                this.props.onChange([...this.props.selected, suggestion]);

                event.preventDefault();
                this.input.innerHTML = '';

                return;
            }
        } else {
            if (event.key === 'Backspace' && !typeAhead) {
                if (selected.length < 1) {
                    return;
                }

                this.props.onChange(selected.slice(0, selected.length - 1));
            }
        }

        if (['ArrowLeft', 'ArrowRight', 'Backspace'].includes(event.key)) {
            return;
        }

        const charAlphaNumeric = /^[\w|\p{L}]$/;

        if (charAlphaNumeric.test(event.key)) {
            return;
        }

        // For any key not checked
        event.preventDefault();
    }

    handleSuggestionMouseDown = suggestion => {
        this.input.innerHTML = '';

        this.props.onChange([...this.props.selected, suggestion]);
    }

    handleLabelRemove = label => {
        this.props.onChange(this.props.selected.filter(item => item !== label));
    }

    unusedSuggestions = () => {
        const selected = new Set(
            this.props.selected.map(item => Object.keys(item)[0])
        );

        return suggestion => !selected.has(Object.keys(suggestion)[0]);
    }

    render() {
        let suggestions;

        if (this.state.suggestions.length) {
            suggestions = this.state.suggestions;
        } else {
            suggestions = this.state.values;
        }

        suggestions = suggestions.filter(this.unusedSuggestions());

        return (
            <div
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
                            key={Object.keys(item)[0]}
                            label={item}
                            onRemove={this.handleLabelRemove}
                        />
                    ))}
                    <span
                        className="labels-input"
                        ref={ref => { this.input = ref; }}
                        contentEditable
                        onKeyUp={this.handleKeyUp}
                        onKeyDown={this.handleKeyDown}
                    />
                </span>
                {this.state.focused && (
                    <div className="labels-dropdown">
                        {suggestions.map(suggestion => (
                            <Suggestion
                                className="labels-suggestion"
                                key={Object.keys(suggestion)[0]}
                                suggestion={suggestion}
                                onMouseDown={this.handleSuggestionMouseDown}
                            />
                        ))}
                    </div>
                )}
            </div>
        );
    }
}

export default connect(state => ({
    docId: state.windowHandler.master.docId,
    windowId: state.windowHandler.master.layout.windowId
}))(Labels);
