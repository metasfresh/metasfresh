import React from 'react';
import { render } from 'enzyme';
import Loader from '../../../components/app/Loader';

describe('Loader test', () => {
  it('renders without errors', () => {
    const wrapper = render(<Loader />);

    const html = wrapper.html();
    expect(html).not.toBe(null);
  });

  it('renders normal loader', () => {
    const wrapper = render(<Loader />);

    const html = wrapper.html();
    expect(html.includes('meta-icon-settings')).toBe(true);
  });

  it('renders bootstrap loader', () => {
    const wrapper = render(<Loader loaderType="bootstrap" />);

    const html = wrapper.html();
    expect(html).not.toBe(null);
    expect(html.includes('spinner-border')).toBe(true);
  });
});
