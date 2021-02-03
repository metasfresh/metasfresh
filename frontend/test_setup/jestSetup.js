import '@babel/polyfill';
import 'jest-localstorage-mock';
import Enzyme, { shallow, render, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import { JSDOM } from 'jsdom';
import EventSource from 'eventsourcemock';

// React 16 Enzyme adapter
Enzyme.configure({ adapter: new Adapter() });
// Make Enzyme functions available in all test files without importing
const jsdom = new JSDOM('<!doctype html><html><body></body></html>', {
  url: 'http://localhost:3000',
});
const { window } = jsdom;

/* can be overriden per element if needed
  function createMockDiv (width, height) {
    const div = document.createElement("div");
    Object.assign(div.style, {
      width: width+"px",
      height: height+"px",
    });
    // we have to mock this for jsdom.
    div.getBoundingClientRect = () => ({
      width,
      height,
      top: 0,
      left: 0,
      right: width,
      bottom: height,
    });
    return div;
  }
*/
window.HTMLDivElement.prototype.getBoundingClientRect = function() {
  return {
    width: 0,
    height: 0,
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
  };
}

// uncomment for debugging
// process.on('unhandledRejection', (reason) => {
//   console.log('REJECTION', reason)
// })
// process.on('uncaughtException', function(err) {
//   console.log('EXCEPTION', err);
// });

global.EventSource = EventSource;
global.window = window;
global.document = window.document;

global.shallow = shallow;
global.render = render;
global.mount = mount;
global.config = {
  API_URL: 'http://api.test.url/rest/api',
  WS_URL: 'ws://localhost:8080/ws',
};
global.PLUGINS = [];
