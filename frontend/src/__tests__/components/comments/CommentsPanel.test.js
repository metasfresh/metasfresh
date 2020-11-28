import React from "react";
import { render } from "enzyme";
import { CommentsPanel } from '../../../components/comments/CommentsPanel';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';

const mockStore = configureStore([]);
const createStore = function(state = {}) {
  return state;
}

describe("CommentsPanel test", () => {
  it("renders without errors", () => {
    const initialState = createStore({ 
      commentsPanel: {
        data: [],
        textInput: '',
        isOpen: false,
      }
    });
    const store = mockStore(initialState)
    const wrapper = render(
      <Provider store={store}>
        <CommentsPanel />
      </Provider>
    );

    const html = wrapper.html();
    expect(html).toContain('col-12');
    expect(html).toContain('textarea');
    expect(html).toContain('missing translation: en.window.comments.add');
  });
});
