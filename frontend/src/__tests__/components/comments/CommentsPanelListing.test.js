import React from "react";
import { render } from "enzyme";
import { CommentsPanelListing } from '../../../components/comments/CommentsPanelListing';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';

const mockStore = configureStore([]);
const createStore = function(state = {}) {
  return state;
}

describe("CommentsPanelListing test", () => {
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
        <CommentsPanelListing />
      </Provider>
    );

    const html = wrapper.html();
    expect(html).not.toBe(null);
  });
});
