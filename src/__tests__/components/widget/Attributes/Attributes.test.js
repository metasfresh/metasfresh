import React from 'react';
import { shallow } from 'enzyme';
import nock from 'nock';
import Attributes from '../../../../components/widget/Attributes/Attributes';
// import AttributesDropdown from '../../../../components/widget/Attributes/AttributesDropdown';
// import RawWidget from '../../../../components/widget/RawWidget';
import fixtures from '../../../../../test_setup/fixtures/attributes.json';

nock.enableNetConnect()

describe('Attributes component', () => {

  describe('renders', () => {
    it('without errors', () => {
      const props = fixtures.data1.widgetProps;
      const patchFn = jest.fn();

      shallow(
        <Attributes
          { ...props }
          patch={patchFn}
        />
      );
    });
  });

  beforeEach(() => {
      nock('test.api.url').post('/address').reply(200, fixtures.data1.instanceData)//.persist();
      nock('test.api.url').get(`/address/${fixtures.data1.instanceData.id}`).reply(200, fixtures.data1.layout)//.persist();
      // nock('test.api.url').get('/v1/people/jared.brown').reply(200, response).persist();
  });

  describe('component magic', () => {
    // let spy

    afterEach(() => {
      // spy.mockClear()
      jest.clearAllMocks();
    })

    it('toggles dropdown', () => {
      const props = fixtures.data1.widgetProps;
      const patchFn = jest.fn();

      const wrapper = shallow(
        <Attributes
          { ...props }
          patch={patchFn}
        />
      );
      const spy = jest.spyOn(wrapper.instance(), 'handleToggle')
      wrapper.instance().forceUpdate()
      wrapper.update()

    wrapper.find('button').simulate('click');
    // component.find('button.helplink'),

    // wrapper.instance().nextPage(new Event('click'));
    expect(spy).toHaveBeenCalled();


  // it('can render button correctly', () => {
  //   const toggleHelpDrawer = createSpy();
  //   const { button } = render({ toggleHelpDrawer, helpDrawerVisible: true });

  //   expect(button.length).toBeTruthy();

  //   button.simulate('click');
  handleKeyDown

  //   expect(toggleHelpDrawer).toHaveBeenCalled();
  // });

    });
  });
});

      // const spy = jest.spyOn(Attributes.prototype, 'handleToggle');
      // const wrapper = shallow(
      //   <Attributes
      //     { ...props }
      //     patch={patchFn}
      //   />
      // );
      // wrapper.update()

      // const spy = jest.spyOn(Attributes.prototype, 'handleToggle');
// const wrapper = mount(<Component {...props} />);
// wrapper.instance().methodName();
// expect(spy).toHaveBeenCalled();
    // shortcut.context = { shortcuts: { subscribe: jest.fn() } };

    // const subscribe = jest.spyOn(shortcut.context.shortcuts, "subscribe");

    // shortcut.componentWillMount();

    // expect(subscribe).toHaveBeenCalledWith(name, handler);
      // component.find('button.helplink'),

    // const wrapper = shallow(<Component itemsCount={5} currentPage={1} />);
      // expect(handler1).not.toHaveBeenCalled();
      // expect(handler3).toHaveBeenCalled();
    // expect(wrapper.hasClass('single-entry-modal')).toEqual(true);

// /

//   return get(`${config.API_URL}/${entity}/${docType}${
//     docId ? '/' + docId : ''}${
//       tabId ? `/${tabId}` : ''}${subentity ? `/${subentity}` : ''}/layout${
//         isAdvanced ? '?advanced=true' : ''}${list ? `?viewType=${list}` : ''}${
//           supportTree ? '&supportTree=true' : ''}
//   `);












// {
//   "autoFocus": false
//   "readonly"
// }

// test('render a document title', () => {
//     const wrapper = shallow(
//         <DocumentTitle title="Events" />
//     );
//     expect(wrapper.prop('title')).toEqual('Events');
// });


// test('CheckboxWithLabel changes the text after click', () => {
//   // Render a checkbox with label in the document
//   const checkbox = shallow(<CheckboxWithLabel labelOn="On" labelOff="Off" />);

//   expect(checkbox.text()).toEqual('Off');

//   checkbox.find('input').simulate('change');

//   expect(checkbox.text()).toEqual('On');
// });

// it('renders welcome message', () => {
//   const wrapper = shallow(<App />);
//   const welcome = <h2>Welcome to React</h2>;
//   // expect(wrapper.contains(welcome)).to.equal(true);
//   expect(wrapper.contains(welcome)).toEqual(true);
// });

// expect(wrapper).toContainReact(welcome)

// test('render Markdown in preview mode', () => {
//     const wrapper = shallow(
//         <MarkdownEditor value="*Hello* Jest!" />
//     );

//     expect(wrapper).toMatchSnapshot();

//     wrapper.find('[name="toggle-preview"]').simulate('click');

//     expect(wrapper).toMatchSnapshot();
// });