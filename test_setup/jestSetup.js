import 'babel-polyfill';
import 'jest-localstorage-mock';
import Enzyme, { shallow, render, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import { JSDOM } from 'jsdom';

// React 16 Enzyme adapter
Enzyme.configure({ adapter: new Adapter() });
// Make Enzyme functions available in all test files without importing
const jsdom = new JSDOM('<!doctype html><html><body></body></html>', {
  url: 'http://localhost:3000',
});
const { window } = jsdom;

global.window = window;
global.document = window.document;

global.shallow = shallow;
global.render = render;
global.mount = mount;
global.config = {
  API_URL: 'http://api.test.url',
  WS_URL: 'http://ws.test.url',
};

Object.defineProperty(document, 'activeElement', {
  value: function() { return {nodeName: 'FOO'} },
});
