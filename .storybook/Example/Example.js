import React, { Component } from 'react';
import PropTypes from 'prop-types';

export default class Example extends Component {
    static propTypes = {
        inverted: PropTypes.bool
    }

    render() {
        const { inverted } = this.props;

        let divProps;

        if (inverted) {
            divProps = { style: { background: 'black', color: 'white' } };
        }

        return (
            <div {...divProps}>
                <h1>This is an example story.</h1>
                <p>Navigate to <strong>.storybook/Example</strong> to see how to add a story to your component.</p>
                <p>For more info, have a look at the <a target="_blank" href="https://storybook.js.org/basics/introduction/">docs</a> or at some <a target="_blank" href="https://storybook.js.org/examples/">examples</a>.</p>
            </div>
        );
    }
}
