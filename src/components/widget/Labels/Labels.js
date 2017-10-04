import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
    dropdownRequest,
    autocompleteRequest
} from '../../../actions/GenericActions';
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

        if (!typeAhead && event.key === 'Backspace') {
            if (selected.length < 1) {
                return;
            }

            this.props.onChange(selected.slice(0, selected.length - 1));
        }
    }

    handleSuggestionMouseDown = suggestion => {
        this.input.innerHTML = '';

        this.props.onChange([...this.props.selected, suggestion]);
    }

    render() {
        let suggestions;

        if (this.state.suggestions.length) {
            suggestions = this.state.suggestions;
        } else {
            suggestions = this.state.values;
        }

        const selected = new Set(
            this.props.selected.map(item => Object.keys(item)[0])
        );

        suggestions = suggestions.filter(
            suggestion => !selected.has(Object.keys(suggestion)[0])
        );

        return (
            <div
                className={`${this.props.className} labels`}
                onClick={this.handleClick}
                onFocus={this.handleFocus}
                onBlur={this.handleBlur}
                tabIndex={this.props.tabIndex}
            >
                <span className="labels-wrap">
                    {this.props.selected.map(item => {
                        const [key, value] = Object.entries(item)[0];

                        return (
                            <span
                                key={key}
                                className="labels-label"
                            >
                                {value}
                            </span>
                        );
                    })}
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
                        {suggestions.map(suggestion => {
                            return (
                                <Suggestion
                                    className="labels-suggestion"
                                    key={Object.keys(suggestion)[0]}
                                    suggestion={suggestion}
                                    onMouseDown={this.handleSuggestionMouseDown}
                                />
                            );
                        })}
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
