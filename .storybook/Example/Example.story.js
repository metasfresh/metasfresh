import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Example from './';

// Defaults to <name>.story.js
export const name = 'Example component';

export default story => {
    const states = {
        'Regular state': () => (
            <Example />
        ),
        'Inverted state': () => (
            <Example inverted />
        ),
    };

    for (const [state, render] of Object.entries(states)) {
        story.add(state, render);
    }
};
