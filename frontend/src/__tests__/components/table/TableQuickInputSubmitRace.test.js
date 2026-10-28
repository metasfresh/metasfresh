import React from 'react';
import { mount } from 'enzyme';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore } from 'redux';
import thunk from 'redux-thunk';

import { createRootReducer } from '../../../reducers';
import * as api from '../../../api/windowQuickInput';
import ConnectedTableQuickInput from '../../../components/table/TableQuickInput';

import quickInputData from '../../../../test_setup/fixtures/table/table_quickinput.json';

jest.mock('../../../api/windowQuickInput');

const flushPromises = () => new Promise((resolve) => setImmediate(resolve));

/** A promise the test resolves by hand, to keep a PATCH "in flight". */
const deferred = () => {
  let resolve;
  const promise = new Promise((res) => (resolve = res));
  return { promise, resolve };
};

const PRODUCT = { key: '2005577', caption: 'Test product' };

/**
 * Real-life case: the user picks a product in the quick input, types the
 * quantity and presses Enter while the product PATCH is still in flight
 * (a slow server response is enough). The line must still be added, with
 * both the product and the quantity.
 */
describe('TableQuickInput - submit while a field PATCH is pending', () => {
  const { windowId, docId, tabId } = quickInputData.basicProps;
  const quickInputId = quickInputData.data1.id;

  let store;
  let wrapper;
  let addNotification;
  let productPatch;

  beforeEach(async () => {
    jest.resetAllMocks();

    api.initQuickInput.mockResolvedValue({ data: quickInputData.data1 });
    api.getQuickInputLayout.mockResolvedValue({
      data: quickInputData.layout,
    });
    api.completeQuickInput.mockResolvedValue({ data: {} });

    // The product PATCH stays pending until the test resolves it;
    // every other PATCH answers immediately.
    productPatch = deferred();
    api.patchQuickInput.mockImplementation(({ fieldName, fieldValue }) => {
      if (fieldName === 'M_Product_ID') {
        return productPatch.promise;
      }
      return Promise.resolve({
        data: [{ fieldsByName: { [fieldName]: { value: fieldValue } } }],
      });
    });

    store = createStore(createRootReducer(), {}, applyMiddleware(thunk));
    addNotification = jest.fn();

    wrapper = mount(
      <Provider store={store}>
        <ConnectedTableQuickInput
          {...quickInputData.basicProps}
          addNotification={addNotification}
          closeBatchEntry={jest.fn()}
        />
      </Provider>
    );

    await flushPromises();
    wrapper.update();
  });

  afterEach(() => {
    wrapper.unmount();
  });

  const tableQuickInput = () =>
    wrapper.find('TableQuickInput').first().instance();

  /** Same entry point a lookup uses when the user selects a value (RawLookup -> onPatch). */
  const pickProduct = () =>
    tableQuickInput().rawWidgetsByFieldName['M_Product_ID'].handlePatch(
      'M_Product_ID',
      PRODUCT
    );

  const qtyInput = () => wrapper.find('.quick-input-container input').last();

  const typeQtyAndPressEnter = (qty) => {
    qtyInput().simulate('change', { target: { value: qty } });
    qtyInput().simulate('keyDown', { key: 'Enter', target: { value: qty } });
    wrapper.find('form.quick-input-container').simulate('submit');
  };

  it('adds the line with product and qty when Enter is pressed before the product PATCH returns', async () => {
    pickProduct();
    // the user needs some time to type: the product PATCH is now sent and in flight
    await flushPromises();
    wrapper.update();
    expect(store.getState().tableQuickInputHandler.inProgress).toBe(true);

    typeQtyAndPressEnter('10');

    // the product PATCH response arrives only now
    productPatch.resolve({
      data: [
        {
          fieldsByName: {
            M_Product_ID: { value: PRODUCT, validStatus: { valid: true } },
          },
        },
      ],
    });
    await flushPromises();
    await flushPromises();

    expect(addNotification).not.toHaveBeenCalled();

    const patchedFields = api.patchQuickInput.mock.calls.map(
      ([{ fieldName, fieldValue }]) => [fieldName, fieldValue]
    );
    expect(patchedFields).toEqual([
      ['M_Product_ID', PRODUCT],
      ['Qty', '10'],
    ]);

    expect(api.completeQuickInput).toHaveBeenCalledTimes(1);
    expect(api.completeQuickInput).toHaveBeenCalledWith(
      windowId,
      docId,
      tabId,
      quickInputId
    );

    // complete is sent only after every field PATCH has been answered
    const lastPatchOrder = Math.max(
      ...api.patchQuickInput.mock.invocationCallOrder
    );
    expect(api.completeQuickInput.mock.invocationCallOrder[0]).toBeGreaterThan(
      lastPatchOrder
    );
  });

  it('adds the line when the product PATCH has already returned (control)', async () => {
    pickProduct();
    productPatch.resolve({
      data: [{ fieldsByName: { M_Product_ID: { value: PRODUCT } } }],
    });
    await flushPromises();
    wrapper.update();

    typeQtyAndPressEnter('10');
    await flushPromises();
    await flushPromises();

    expect(addNotification).not.toHaveBeenCalled();
    expect(
      api.patchQuickInput.mock.calls.map(([{ fieldName }]) => fieldName)
    ).toEqual(['M_Product_ID', 'Qty']);
    expect(api.completeQuickInput).toHaveBeenCalledTimes(1);
  });

  it('still refuses to submit when a mandatory field is really empty', async () => {
    typeQtyAndPressEnter('10');
    await flushPromises();
    await flushPromises();

    expect(addNotification).toHaveBeenCalledWith(
      'Error',
      'Mandatory fields are not filled!',
      5000,
      'error'
    );
    expect(api.completeQuickInput).not.toHaveBeenCalled();
  });
});
