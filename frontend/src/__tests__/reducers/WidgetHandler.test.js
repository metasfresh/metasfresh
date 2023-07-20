import { merge } from 'merge-anything';

import * as ACTION_TYPES from '../../constants/ActionTypes';
import reducer, { initialState, } from '../../reducers/widgetHandler';

import fixtures
  from '../../../test_setup/fixtures/independent_widgets/reducers.json';

const createState = function(state = {}) {
  return merge(
    {
      ...initialState,
    },
    state
  );
};

describe('WidgetHandler reducer', () => {
  it('should return the initial state', () => {
    expect(reducer(undefined, {})).toEqual(initialState);
  });

  describe('SelectionAttributes', () => {
    it('Should handle FETCH_ATTRIBUTES_DATA', () => {
      const { selectionAttributesData } = fixtures;
      const fetchAction = {
        type: ACTION_TYPES.FETCH_ATTRIBUTES_DATA,
        payload: {
          fieldsByName: selectionAttributesData.fieldsByName,
          id: selectionAttributesData.id,
        },
      };
      const initialStateData = createState();
      const actions = [fetchAction];
      const state = actions.reduce(reducer, initialStateData);

      expect(state.attributes).toEqual(
        expect.objectContaining({
          fields: expect.objectContaining({ ...selectionAttributesData.fieldsByName }),
          elements: [],
          dataId: selectionAttributesData.id,
        })
      );
    });

    it('Should handle FETCH_ATTRIBUTES_LAYOUT', () => {
      const { selectionAttributesLayout } = fixtures;
      const fetchAction = {
        type: ACTION_TYPES.FETCH_ATTRIBUTES_LAYOUT,
        payload: {
          elements: selectionAttributesLayout.elements,
        },
      };
      const initialStateData = createState();
      const actions = [fetchAction];
      const state = actions.reduce(reducer, initialStateData);

      expect(state.attributes).toEqual(
        expect.objectContaining({
          elements: expect.objectContaining({ ...selectionAttributesLayout.elements }),
          fields: {},
          dataId: null,
        })
      );
    });

    it('Should handle SET_ATTRIBUTES_DATA', () => {
      const { selectionAttributesData, selectionAttributesPatch } = fixtures;
      const { fieldsByName, id } = selectionAttributesData;
      const fieldName = Object.keys(selectionAttributesPatch[0].fieldsByName).reduce(k => k);
      const fieldData = fieldsByName[fieldName];
      const initialStateData = createState({
        ...initialState,
        attributes: {
          fields: fieldsByName,
          dataId: id,
        },
      });

      const patchAction = {
        type: ACTION_TYPES.SET_ATTRIBUTES_DATA,
        payload: {
          field: fieldName,
          value: '4',
        },
      }
      const actions = [patchAction];
      const state = actions.reduce(reducer, initialStateData);

      expect(state.attributes.fields[fieldName]).toEqual(
        expect.objectContaining({
          ...fieldData,
          value: patchAction.payload.value,
        })
      );
    });

    it('Should handle PATCH_DATA', () => {
      const { selectionAttributesData, selectionAttributesPatch } = fixtures;
      const { fieldsByName, id } = selectionAttributesData;
      const initialStateData = createState({
        ...initialState,
        attributes: {
          fields: fieldsByName,
          dataId: id,
        },
      });
      const fieldName = Object.keys(selectionAttributesPatch[0].fieldsByName).reduce(k => k);
      const patchAction = {
        type: ACTION_TYPES.PATCH_ATTRIBUTES,
        payload: {
          data: selectionAttributesPatch[0].fieldsByName,
        }
      }
      const actions = [patchAction];
      const state = actions.reduce(reducer, initialStateData);

      expect(state.attributes.fields[fieldName].value).toEqual(
        patchAction.payload.data[fieldName].value
      );
    });

    it('Should handle DELETE_ATTRIBUTES', () => {
      const initialStateData = createState({
        ...initialState,
        attributes: {
          ...initialState.attributes,
          dataId: 1,
        },
      });
      const deleteAction = {
        type: ACTION_TYPES.DELETE_ATTRIBUTES,
      }
      const actions = [deleteAction];
      const state = actions.reduce(reducer, initialStateData);

      expect(state.attributes.dataId).toEqual(null);
    });
  });
});
