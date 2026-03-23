import { BlankPage } from '../../components/BlankPage';
import React from 'react';
import renderer from 'react-test-renderer';
import counterpart from 'counterpart';

afterEach(() => {
  counterpart.registerTranslations('lang', {}); // cleanup custom registered translations
});

test('BlankPage renders correctly', () => {
  counterpart.registerTranslations('en', {
    window: {
      notFound: {
        title: 'not found title',
        description: 'not found description',
      }
    }
  });

  const component = renderer.create(<BlankPage />);
  let tree = component.toJSON();
  expect(tree).toMatchSnapshot();
});
