import nock from 'nock';
import configureStore from 'redux-mock-store';
import merge from 'merge';

import {
  updateTabRowsData,
  initDataSuccess,
  initLayoutSuccess,
} from '../../actions/WindowActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import reducer, { initialState } from '../../reducers/tables';

const createState = function(state = {}) {
  return merge.recursive(
    true,
    {
      ...initialState,
    },
    state
  );
}

describe('Tables reducer', () => {
  it('should return the initial state', () => {
    expect(reducer(undefined, {})).toEqual(initialState);
  });
});
