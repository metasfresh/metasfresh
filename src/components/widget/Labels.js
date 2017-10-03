import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
    dropdownRequest,
    autocompleteRequest
} from '../../actions/GenericActions';

class Labels extends Component {
    state = {
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

    handleInput = async event => {
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

    render() {
        let suggestions;

        if (this.state.suggestions.length) {
            suggestions = this.state.suggestions;
        } else {
            suggestions = this.state.values;
        }

        return (
            <div
                className={this.props.className}
                onClick={this.handleClick}
            >
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
                    onInput={this.handleInput}
                />
                <div>
                    {suggestions.map(suggestion => {
                        const [key, value] = Object.entries(suggestion)[0];

                        return (
                            <div key={key}>
                                {value}
                            </div>
                        );
                    })}
                </div>
            </div>
        );
    }
}

export default connect(state => ({
    docId: state.windowHandler.master.docId,
    windowId: state.windowHandler.master.layout.windowId
}))(Labels);
