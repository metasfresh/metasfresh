import { merge } from 'merge-anything';

import * as ACTION_TYPES from '../../constants/actions/TableQuickInputActionTypes';
import reducer, { initialState, } from '../../reducers/tableQuickInputHandler';
import { parseToDisplay } from '../../utils/documentListHelper';

const createInitialState = (state = {}) => {
  return merge({ ...initialState }, state);
};

describe('tableQuickInputHandler reducer', () => {
  it('should return the initial state', () => {
    expect(reducer(undefined, {})).toEqual(initialState);
  });

  it('Should handle FETCH_QUICKINPUT_DATA', () => {
    const data = { placeholder: 'Dummy data' };
    const actions = [{
      type: ACTION_TYPES.SET_QUICKINPUT_DATA,
      payload: { quickInputId: '123', data },
    }];
    const state = actions.reduce(reducer, createInitialState());

    expect(state).toEqual({
      data,
      layout: null,
      quickInputId: '123',
      inProgress: false,
    });
  });

  it('Should handle FETCH_QUICKINPUT_LAYOUT', () => {
    const layout = { placeholder: 'Dummy layout' };
    const actions = [{
      type: ACTION_TYPES.SET_QUICKINPUT_LAYOUT,
      payload: { layout },
    }];
    const state = actions.reduce(reducer, createInitialState());

    expect(state).toEqual({
      data: null,
      layout,
      quickInputId: null,
      inProgress: false,
    });
  });

  it('Should handle SET_QUICKINPUT_DATA', () => {
    const actions = [{
      type: ACTION_TYPES.UPDATE_QUICKINPUT_DATA,
      payload: {
        fieldData: {
          Qty: { value: '2' },
        }
      },
    }];

    const state = actions.reduce(
      reducer,
      createInitialState({
        quickInputId: 'does not matter',
        data: parseToDisplay({
          Qty: {
            field: 'Qty',
            value: null,
          }
        })
      })
    );

    expect(state.data).toEqual({
      Qty: {
        field: 'Qty',
        value: '2',
      }
    });
  });

  it('Should handle DELETE_QUICKINPUT', () => {
    const actions = [{
      type: ACTION_TYPES.DELETE_QUICKINPUT,
    }];
    const state = actions.reduce(reducer, createInitialState({ quickInputId: 1 }));

    expect(state).toEqual(initialState);
  });

  it('Should handle PATCH_QUICKINPUT_PENDING', () => {
    const actions = [{
      type: ACTION_TYPES.PATCH_QUICKINPUT_PENDING,
    }];
    const state = actions.reduce(reducer, createInitialState());

    expect(state.inProgress).toEqual(true);
  });

  it('Should handle PATCH_QUICKINPUT_DONE', () => {
    const actions = [{
      type: ACTION_TYPES.PATCH_QUICKINPUT_DONE,
    }];
    const state = actions.reduce(reducer, createInitialState({ pending: true }));

    expect(state.inProgress).toEqual(false);
  });
});
