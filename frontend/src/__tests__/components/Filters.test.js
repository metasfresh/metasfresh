import React from 'react';
import * as Immutable from 'immutable';
import { mount, shallow, render } from 'enzyme';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';

import { ShortcutProvider } from '../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../reducers/windowHandler';
import { filtersToMap } from '../../utils/documentListHelper';

import Filters from '../../components/filters/Filters';
import filtersFixtures from '../../../test_setup/fixtures/filters.json';
import hotkeys from '../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../test_setup/fixtures/keymap.json';

const mockStore = configureStore([]);

const createStore = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      appHandler: {
        ...appHandlerState,
        me: { timeZone: 'America/Los_Angeles' },
      },
      windowHandler: { ...windowHandlerState },
    },
    state
  );

  return res;
};

const createInitialProps = function(
  basicFixtures = filtersFixtures.data1,
  additionalProps = {}
) {
  const filterData = additionalProps.filters
    ? additionalProps.filters
    : basicFixtures.filters;
  const filtersActive = additionalProps.filtersActive
    ? additionalProps.filtersActive
    : basicFixtures.filtersActive;
  const initialValuesNulled = additionalProps.initialValuesNulled
    ? additionalProps.initialValuesNulled
    : basicFixtures.initialValuesNulled;

  return {
    ...basicFixtures,
    resetInitialValues: jest.fn(),
    updateDocList: jest.fn(),
    ...additionalProps,
    filterData: filtersToMap(filterData),
    filtersActive: filtersToMap(filtersActive),
    initialValuesNulled: Immutable.Map(initialValuesNulled),
  };
};

describe('Filters tests', () => {
  it('renders without errors', () => {
    const dummyProps = createInitialProps();
    const initialState = createStore({
      windowHandler: {
        allowShortcut: true,
        modal: {
          visible: false,
        },
      },
    });
    const store = mockStore(initialState);
    const wrapper = shallow(
      <Provider store={store}>
        <Filters {...dummyProps} />
      </Provider>
    );
    const html = wrapper.html();

    expect(html).toContain('filter-wrapper');
    expect(html).toContain('filters-frequent');
    expect(html).toContain('btn-filter');
    expect(html).toContain(': Date');
  });

  it('renders active filters caption', () => {
    const dummyProps = createInitialProps(undefined, {
      filtersActive: filtersFixtures.filtersActive1,
    });
    const initialState = createStore({
      windowHandler: {
        allowShortcut: true,
        modal: {
          visible: false,
        },
      },
    });

    const store = mockStore(initialState);
    const wrapper = shallow(
      <Provider store={store}>
        <Filters {...dummyProps} />
      </Provider>
    );

    wrapper.find(
      '.filter-wrapper button[title="Akontozahlung, Completed, Error"]'
    );
  });

  it('opens dropdown and filter details', () => {
    const dummyProps = createInitialProps();
    const initialState = createStore({
      windowHandler: {
        allowShortcut: true,
        modal: {
          visible: false,
        },
      },
    });
    const store = mockStore(initialState);
    const wrapper = mount(
      <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
        <Provider store={store}>
          <div className="document-lists-wrapper">
            <Filters {...dummyProps} />
          </div>
        </Provider>
      </ShortcutProvider>
    );

    wrapper.find('.filters-not-frequent .btn-filter').simulate('click');
    expect(wrapper.find('.filters-overlay').length).toBe(1);

    wrapper.find('.filter-option-default').simulate('click');
    expect(wrapper.find('.filter-widget .filter-default').length).toBe(1);
  });

  //@TODO: I expect this to be replaced by a combination of small unit and e2e tests, but
  // for now it doesn't make sense to write targeted unit tests for Filter descendant components
  // as the widgets need an architecture overhaul, and filters should be moved to redux state
  describe('Temporary bloated filter tests', () => {
    // https://github.com/metasfresh/me03/issues/3649
    it('clears list filters and applies without error', () => {
      const dummyProps = createInitialProps(undefined, {
        filtersActive: filtersFixtures.filtersActive2,
      });
      const initialState = createStore({
        windowHandler: {
          allowShortcut: true,
          modal: {
            visible: false,
          },
        },
      });
      const store = mockStore(initialState);
      const wrapper = mount(
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <Provider store={store}>
            <div className="document-lists-wrapper">
              <Filters {...dummyProps} />
            </div>
          </Provider>
        </ShortcutProvider>
      );

      wrapper.find('.filters-not-frequent .btn-filter').simulate('click');
      expect(wrapper.find('.filters-overlay').length).toBe(1);
      expect(wrapper.find('.filter-option-default').length).toBe(0);

      expect(wrapper.find('FiltersItem').state().activeFilter).toBeTruthy();

      wrapper
        .find('.form-field-C_DocType_ID .meta-icon-close-alt')
        .simulate('click');
      wrapper.update();
      wrapper
        .find('.form-field-DocStatus .meta-icon-close-alt')
        .simulate('click');
      wrapper.update();

      expect(wrapper.find('FiltersItem').state().activeFilter).toBeFalsy();
      wrapper
        .find('.filter-widget .filter-btn-wrapper .applyBtn')
        .simulate('click');

      wrapper.update();
      expect(wrapper.find('.filters-overlay').length).toBe(0);
    });

    it('supports `false` values for checkbox widgets', () => {
      const updateDocListListener = jest.fn();
      const dummyProps = createInitialProps(filtersFixtures.data2, {
        filtersActive: filtersFixtures.filtersActive3,
        updateDocList: updateDocListListener,
      });
      const initialState = createStore({
        windowHandler: {
          allowShortcut: true,
          modal: {
            visible: false,
          },
        },
      });
      const store = mockStore(initialState);
      const wrapper = mount(
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <Provider store={store}>
            <div className="document-lists-wrapper">
              <Filters {...dummyProps} />
            </div>
          </Provider>
        </ShortcutProvider>
      );

      wrapper.find('.filters-not-frequent .btn-filter').simulate('click');
      expect(wrapper.find('.filters-overlay').length).toBe(1);
      expect(wrapper.find('.filter-option-default').length).toBe(0);
      expect(wrapper.find('FiltersItem').state().activeFilter).toBeTruthy();
      expect(
        wrapper.find('.form-field-Processed .input-checkbox-tick.checked')
          .length
      ).toBe(1);

      wrapper
        .find('.form-field-Processed input[type="checkbox"]')
        .simulate('change', { target: { checked: false } });
      wrapper.update();

      expect(
        wrapper.find(
          '.form-field-Processed .input-checkbox-tick.input-state-false'
        ).length
      ).toBe(1);
      expect(wrapper.find('FiltersItem').state().activeFilter).toBeTruthy();
      wrapper
        .find('.filter-widget .filter-btn-wrapper .applyBtn')
        .simulate('click');
      wrapper.update();

      const filterResult = Immutable.Map({
        default: {
          defaultVal: false,
          filterId: 'default',
          parameters: [
            {
              parameterName: 'Processed',
              value: false,
              valueTo: '',
              defaultValue: null,
              defaultValueTo: null,
            },
          ],
        },
      });

      expect(updateDocListListener).toBeCalledWith(filterResult);
    });

    it('supports filters without parameters', () => {
      const updateDocListListener = jest.fn();
      const dummyProps = createInitialProps(filtersFixtures.data3, {
        updateDocList: updateDocListListener,
      });
      const initialState = createStore({
        windowHandler: {
          allowShortcut: true,
          modal: {
            visible: false,
          },
        },
      });
      const store = mockStore(initialState);
      const wrapper = mount(
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <Provider store={store}>
            <div className="document-lists-wrapper">
              <Filters {...dummyProps} />
            </div>
          </Provider>
        </ShortcutProvider>
      );
      wrapper.find('.filters-not-frequent .btn-filter').simulate('click');
      expect(wrapper.find('.filters-overlay').length).toBe(1);

      wrapper
        .find('.filters-overlay .filter-option-userquery-540024')
        .simulate('click');
      expect(wrapper.find('FiltersItem').state().activeFilter).toBeFalsy();

      wrapper
        .find('.filter-widget .filter-btn-wrapper .applyBtn')
        .simulate('click');
      wrapper.update();

      const filterResult = Immutable.Map({
        'userquery-540024': {
          filterId: 'userquery-540024',
          caption: 'Abrechnung_offen_normal',
          frequent: false,
          inlineRenderMode: 'button',
          parametersLayoutType: 'panel',
          debugProperties: {},
        },
      });

      expect(updateDocListListener).toBeCalledWith(filterResult);
    });
  });
});
