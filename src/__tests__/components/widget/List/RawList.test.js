import React from 'react';
import { List } from 'immutable';
import { mount, shallow } from 'enzyme';

import RawList from '../../../../components/widget/List/RawList';
import fixtures from '../../../../../test_setup/fixtures/raw_list.json';

const createDummyProps = function(props, data) {
  return {
    onFocus: jest.fn(),
    onBlur: jest.fn(),
    onSelect: jest.fn(),
    onOpenDropdown: jest.fn(),
    onCloseDropdown: jest.fn(),
    ...props,
    list: List(data),
  };
};

describe('RawList component', () => {
  describe('rendering tests:', () => {
    it('renders without errors', () => {
      const props = createDummyProps(
        {
          ...fixtures.data1.widgetProps,
          isFocused: true,
        },
        fixtures.data1.listData
      );

      const wrapper = mount(<RawList {...props} />);

      expect(wrapper.html()).toContain('focused');
      expect(wrapper.find('input').length).toBe(1);
      expect(wrapper.find('input').html()).toContain(
        'Testpreisliste Lieferanten'
      );
    });
  });

  describe('functional tests', () => {
    describe('with elements attached to dummy element', function() {
      let wrapper;

      beforeEach(function(){
        document.body.appendChild(document.createElement('div'))
      });

      afterEach(function(){
        if (wrapper.detach) {
          wrapper.detach();
        }
      });

      it('list focuses and toggles on click', () => {
        const props = createDummyProps(
          {
            ...fixtures.data1.widgetProps,
            isFocused: false,
          },
          fixtures.data1.listData
        );

        wrapper = mount(
          <div><RawList {...props} /></div>,
          { attachTo: document.body.firstChild }
        );

        // console.log('BU: ', wrapper.find('.input-dropdown-container'));

        // const bar = document.querySelector('.input-dropdown-container focused')

        // expect(bar.find('.input-dropdown-container focused').length).toBeNull()

        expect(wrapper.find('.input-dropdown-container focused').length).toBe(0);

        // console.log('WRAPPER: ', wrapper.html(), wrapper.find('.input-dropdown-container'));

        wrapper.find('.input-dropdown-container').simulate('click');

                console.log('WRAPPER: ', wrapper.html(), wrapper.find('.input-dropdown-container').length);


                // expect(bar.find('.input-dropdown-container focused').length).not.toBeNull();
                // element.findWhere(node => node.hasClass('.feature__cover'))

        expect(wrapper.find('.input-dropdown-container focused').length).toBe(1);



        // expect(wrapper.html()).to.contain('Content in component')
        // expect(document.body.innerHTML).to.not.contain('Content in overlay')

        // expect(wrapper.html()).toContain('focused');
        // expect(wrapper.find('input').length).toBe(1);
        // expect(wrapper.find('input').html()).toContain(
        //   'Testpreisliste Lieferanten'
        // );
      });

      // it('list blurs and stays out of focus after tabbing out', () => {
      //   const props = createDummyProps(
      //     {
      //       ...fixtures.data1.widgetProps,
      //       isFocused: true,
      //       isToggled: false,
      //     },
      //     fixtures.data1.listData
      //   );

      //   // wrapper = mount(<RawList {...props} />);

      //   wrapper = mount(
      //     <RawList {...props} />,
      //     { attachTo: document.body.firstChild }
      //   )

      //   // console.log('HTML: ', wrapper.html())

      //   expect(wrapper.html()).toContain('input-dropdown');
      //   expect(wrapper.html()).toContain('focused');

      //   wrapper.simulate(
      //     'keyDown',
      //     {
      //       key: 'Tab',
      //   //     target: { value: fixtures.longText.data1.value },
      //       preventDefault: jest.fn(),
      //     },
      //   );

      //   expect(wrapper.html()).not.toContain('focused');

      //   // expect(wrapper.html()).to.contain('Content in component')
      //   // expect(document.body.innerHTML).to.not.contain('Content in overlay')

      //   // expect(wrapper.html()).toContain('focused');
      //   // expect(wrapper.find('input').length).toBe(1);
      //   // expect(wrapper.find('input').html()).toContain(
      //   //   'Testpreisliste Lieferanten'
      //   // );
      // });
    });
  });
});
