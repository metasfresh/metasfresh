import React from 'react';

import { storiesOf } from '@storybook/react';

const stories = require('./foundStories').default;

for (const [storyName, storyExport] of stories) {
    const { name, default: story } = storyExport;

    story(storiesOf(name || storyName, module));
}
