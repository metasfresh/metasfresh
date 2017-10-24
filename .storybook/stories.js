import React from 'react';

import { storiesOf } from '@storybook/react';

const stories = require('./foundStories').default;

for (const [storyName, story] of stories) {
    story(storiesOf(storyName, module));
}
