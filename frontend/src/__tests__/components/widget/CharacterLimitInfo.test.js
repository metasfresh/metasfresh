import React from 'react';
import { mount, shallow } from 'enzyme';

import CharacterLimitInfo from '../../../components/widget/CharacterLimitInfo';

describe('CharacterLimitInfo component', () => {
  describe('rendering tests:', () => {
    it('renders without errors', () => {
      const wrapper = shallow(<CharacterLimitInfo charsTyped={40} maxLength={40} />);
      const html = wrapper.html();

      expect(html).toContain('text-muted');
    });

    it('renders muted text when chars typed are near the max allowed limit', () => {
      const wrapper = shallow(<CharacterLimitInfo charsTyped={35} maxLength={40} />);
      const html = wrapper.html();

      expect(html).toContain('text-muted');
    });

    it('renders the red text when when chars typed are exceeding the max allowed limit', () => {
      const wrapper = shallow(<CharacterLimitInfo charsTyped={41} maxLength={40} />);
      const html = wrapper.html();

      expect(html).toContain('text-danger');
    });

    it('does not render any muted text or error text if input chars is not near the max limit', () => {
      const wrapper = shallow(<CharacterLimitInfo charsTyped={20} maxLength={40} />);
      const html = wrapper.html();
      expect(html).not.toContain('text-muted');
      expect(html).not.toContain('text-danger');
    });
  });
});


