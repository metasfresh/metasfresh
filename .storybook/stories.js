import React from 'react';

import { storiesOf } from '@storybook/react';

const example = require('./Example/Example.story.js');
const stories = require('./foundStories').default;

for (const [storyName, storyExport] of [['Example', example], ...stories]) {
    const { name, default: story } = storyExport;

    story(storiesOf(name || storyName, module));
}
