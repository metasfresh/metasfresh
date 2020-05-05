import BlankPage from '../../components/BlankPage';
import React from 'react';
import renderer from 'react-test-renderer';

test('BlankPage renders correctly', () => {
  const component = renderer.create(<BlankPage />);
  let tree = component.toJSON();
  expect(tree).toMatchSnapshot();
});
