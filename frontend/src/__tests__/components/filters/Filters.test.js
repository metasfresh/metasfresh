import React from 'react';
import { mount, shallow } from 'enzyme';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';
import { act } from 'react-dom/test-utils';

import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';

import Filters from '../../../components/filters/Filters';
import filtersFixtures from '../../../../test_setup/fixtures/filters.json';
import hotkeys from '../../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../../test_setup/fixtures/keymap.json';
import filterData from '../../../../test_setup/fixtures/filters/filterData.json';
import filtersActive from '../../../../test_setup/fixtures/filters/filtersActive.json';
import filtersStoreOne from '../../../../test_setup/fixtures/filters/filtersStoreOne.json';
import filtersStoreTwo from '../../../../test_setup/fixtures/filters/filtersStoreTwo.json';
import filtersStoreThree from '../../../../test_setup/fixtures/filters/filtersStoreThree.json';
const mockStore = configureStore([]);

const createStore = function (state = {}) {
  const res = merge(
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

const createInitialProps = function (
  basicFixtures = filtersFixtures.data1,
  additionalProps = {}
) {
  return {
    ...basicFixtures,
    updateDocList: jest.fn(),
    ...additionalProps,
    filterData,
    filtersActive,
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
      filters: filtersStoreOne,
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
      filters: filtersStoreOne,
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
      filters: filtersStoreOne,
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
    expect(wrapper.find('.filter-menu').length).toBe(1);
    expect(wrapper.find('.filters-overlay').length).toBe(1);

    wrapper.find('.filter-active').simulate('click');
    expect(wrapper.find('.filter-widget .filter-default').length).toBe(1);
  });

  //@TODO: I expect this to be replaced by a combination of small unit and e2e tests, but
  // for now it doesn't make sense to write targeted unit tests for Filter descendant components
  // as the widgets need an architecture overhaul, and filters should be moved to redux state
  describe('Temporary bloated filter tests', () => {
    // // https://github.com/metasfresh/me03/issues/3649
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
        filters: filtersStoreOne,
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

      wrapper.find('.meta-icon-close-alt').simulate('click');
      wrapper.update();

      expect(wrapper.find('FiltersItem').state().activeFilter).toBeTruthy();
      wrapper
        .find('.filter-widget .filter-btn-wrapper .applyBtn')
        .simulate('click');

      wrapper.update();
      expect(wrapper.find('.filters-overlay').length).toBe(0);
    });

    it('supports `false` values for checkbox widgets', () => {
      const updateDocListListener = jest.fn();
      const dummyProps = createInitialProps(undefined, {
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
        filters: filtersStoreTwo,
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
        wrapper.find('.form-field-Processed input[type="checkbox"]').length
      ).toBe(1);

      act(() => {
        wrapper
          .find('.form-field-Processed input[type="checkbox"]')
          .simulate('change');
        wrapper.update();
      });

      expect(
        wrapper.find('.form-field-Processed input[type="checkbox"]').checked
      ).toBe(undefined);

      expect(wrapper.find('FiltersItem').state().activeFilter).toBeTruthy();
      wrapper
        .find('.filter-widget .filter-btn-wrapper .applyBtn')
        .simulate('click');

      wrapper.update();

      const filterResult = [
        {
          filterId: 'default',
          parameters: [
            {
              parameterName: 'C_BPartner_ID',
              value: {
                key: '2156429',
                caption: '1000003_TestVendor',
                description: '1000003_TestVendor',
              },
              valueTo: null,
              defaultValue: null,
              defaultValueTo: null,
            },
            {
              parameterName: 'Processed',
              value: false,
              valueTo: null,
              defaultValue: null,
              defaultValueTo: null,
            },
          ],
        },
      ];
      expect(updateDocListListener).toBeCalledWith(filterResult);
    });

    it('supports filters without parameters', () => {
      const updateDocListListener = jest.fn();
      const dummyProps = createInitialProps(undefined, {
        updateDocList: updateDocListListener,
      });
      const initialState = createStore({
        windowHandler: {
          allowShortcut: true,
          modal: {
            visible: false,
          },
        },
        filters: filtersStoreThree,
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

      const filterResult = [
        {
          filterId: 'default',
          parameters: [
            {
              parameterName: 'C_BPartner_ID',
              value: {
                key: '2156429',
                caption: '1000003_TestVendor',
                description: '1000003_TestVendor',
              },
              valueTo: null,
            },
          ],
        },
        {
          filterId: 'userquery-540024',
          caption: 'Abrechnung_offen_normal',
          frequent: false,
          inlineRenderMode: 'button',
          parametersLayoutType: 'panel',
          debugProperties: {},
          isActive: false,
        },
      ];

      expect(updateDocListListener).toBeCalledWith(filterResult);
    });
  });
});
