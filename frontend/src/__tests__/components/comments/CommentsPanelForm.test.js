import React from "react";
import { render } from "enzyme";
import { CommentsPanelForm } from '../../../components/comments/CommentsPanelForm';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';

const mockStore = configureStore([]);
const createStore = function(state = {}) {
  return state;
}

describe("CommentsPanelForm test", () => {
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
        <CommentsPanelForm />
      </Provider>
    );

    const html = wrapper.html();
    expect(html).toContain('textarea');
    expect(html).toContain('missing translation: en.window.comments.add');
  });
});
