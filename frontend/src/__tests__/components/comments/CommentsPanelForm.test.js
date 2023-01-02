/*
 * #%L
 * metasfresh
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import React from "react";
import { render } from "enzyme";
import {
  CommentsPanelForm
} from '../../../components/comments/CommentsPanelForm';
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
