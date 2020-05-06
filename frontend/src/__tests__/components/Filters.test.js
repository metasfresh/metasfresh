import React from "react";
import * as Immutable from "immutable";
import { mount, shallow, render } from "enzyme";
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';

import { initialState as appHandlerState } from '../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../reducers/windowHandler';

import Filters from "../../components/filters/Filters";
import filtersFixtures from "../../../test_setup/fixtures/filters.json";

const mockStore = configureStore([]);

const createStore = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      appHandler: { ...appHandlerState },
      windowHandler: { ...windowHandlerState }
    },
    state
  );

  return res;
}

const createInitialProps = function(basicFixtures = filtersFixtures.data1, additionalProps) {
    const filterData = additionalProps && additionalProps.filterData
      ? additionalProps.filterData
      : basicFixtures.filterData;
    const filtersActive = additionalProps && additionalProps.filterData
      ? additionalProps.filtersActive
      : basicFixtures.filtersActive;
    const initialValuesNulled = additionalProps && additionalProps.filterData
      ? additionalProps.initialValuesNulled
      : basicFixtures.initialValuesNulled;

  return {
    ...basicFixtures,
    ...additionalProps,
    resetInitialValues: jest.fn(),
    updateDocList: jest.fn(),
    filterData: Immutable.Map(filterData),
    filtersActive: Immutable.Map(filtersActive),
    initialValuesNulled: Immutable.Map(initialValuesNulled),
  };
};

describe("Filters tests", () => {
  it("renders without errors", () => {
    const dummyProps = createInitialProps();
    const initialState = createStore({ 
      windowHandler: {
        allowShortcut: true,
        modal: {
          visible: false,
        },
      }
    });
    const store = mockStore(initialState)
    const wrapper = render(
        <Provider store={store}>
          <Filters {...dummyProps} />
        </Provider>
    );
  });
});
