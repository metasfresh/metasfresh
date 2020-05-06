const glob = require('globby');
const { writeFile: writeFileCb } = require('fs');
const { join } = require('path');
const { promisify } = require('util');
const { stripIndent } = require('common-tags');

const writeFile = promisify(writeFileCb);

(async () => {
    try {
        const storiesPath = `${__dirname}/foundStories.js`;
        const fileNameRegex = /[ \w-]+?(?=\.)/;

        const stories = await glob('src/**/*.story.js');
        const storyList = (stories
            .map(storyPath => {
                const storyName = fileNameRegex.exec(storyPath)[0];

                return `['${storyName}', require('${join(__dirname, '..', storyPath)}')]`;
            })
            .join(',\n')
        );

        const code = stripIndent`
            import { storiesOf } from '@storybook/react';

            export default [
                ${storyList}
            ];
        `;

        await writeFile(storiesPath, code);

        console.log(`${stories.length} ${stories.length === 1 ? 'story' : 'stories'} written to ${storiesPath}.`);
    } catch (error) {
        console.error('Failed finding stories.');

        throw error;
    }
})();
