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
const waitForTimers = () => new Promise((resolve) => setTimeout(resolve, 10));

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
  let container;

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

    // attached to the document, so focus / blur behave like in the browser
    container = document.createElement('div');
    document.body.appendChild(container);

    wrapper = mount(
      <Provider store={store}>
        <ConnectedTableQuickInput
          {...quickInputData.basicProps}
          addNotification={addNotification}
          closeBatchEntry={jest.fn()}
        />
      </Provider>,
      { attachTo: container }
    );

    await flushPromises();
    wrapper.update();
  });

  afterEach(() => {
    wrapper.unmount();
    container.remove();
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

  it('sends the typed qty from the blur on submit when Enter did not patch it', async () => {
    pickProduct();
    await flushPromises();
    wrapper.update();

    // the user typed into the qty field, which still has the focus
    const qtyNode = qtyInput().getDOMNode();
    await waitForTimers(); // the first field's initial auto-focus happens in a timer
    qtyNode.focus();
    await waitForTimers(); // RawWidget marks itself focused in a timer
    expect(document.activeElement).toBe(qtyNode);
    qtyNode.value = '10';
    qtyInput().simulate('change', { target: { value: '10' } });

    // submit without a keyDown: only the blur in onSubmit can send the qty
    wrapper.find('form.quick-input-container').simulate('submit');

    productPatch.resolve({
      data: [{ fieldsByName: { M_Product_ID: { value: PRODUCT } } }],
    });
    await flushPromises();
    await flushPromises();

    expect(addNotification).not.toHaveBeenCalled();
    expect(
      api.patchQuickInput.mock.calls.map(([{ fieldName, fieldValue }]) => [
        fieldName,
        fieldValue,
      ])
    ).toEqual([
      ['M_Product_ID', PRODUCT],
      ['Qty', '10'],
    ]);
    expect(api.completeQuickInput).toHaveBeenCalledTimes(1);
    // complete is sent only after the Qty PATCH the blur queued has been answered
    expect(api.completeQuickInput.mock.invocationCallOrder[0]).toBeGreaterThan(
      Math.max(...api.patchQuickInput.mock.invocationCallOrder)
    );
  });

  it('tells the user when a field PATCH failed, on every Enter, and does not complete', async () => {
    productPatch.resolve({
      data: [{ fieldsByName: { M_Product_ID: { value: PRODUCT } } }],
    });
    pickProduct();
    await flushPromises();
    wrapper.update();

    api.patchQuickInput.mockImplementation(() =>
      Promise.reject(new Error('server rejected the value'))
    );

    typeQtyAndPressEnter('10');
    await flushPromises();
    await flushPromises();

    // pressing Enter again without editing must not be a silent no-op
    wrapper.find('form.quick-input-container').simulate('submit');
    await flushPromises();
    await flushPromises();

    expect(addNotification).toHaveBeenCalledTimes(2);
    expect(addNotification.mock.calls[0][0]).toBe('Error');
    expect(addNotification.mock.calls[1][0]).toBe('Error');
    expect(api.completeQuickInput).not.toHaveBeenCalled();
  });

  it('re-sends a failed field PATCH on the next Enter and adds the line once it succeeds', async () => {
    productPatch.resolve({
      data: [{ fieldsByName: { M_Product_ID: { value: PRODUCT } } }],
    });
    pickProduct();
    await flushPromises();
    wrapper.update();

    // the first Qty PATCH fails (e.g. a temporary server error), the retry succeeds
    api.patchQuickInput.mockImplementationOnce(() =>
      Promise.reject(new Error('temporary server error'))
    );

    typeQtyAndPressEnter('10');
    await flushPromises();
    await flushPromises();
    expect(api.completeQuickInput).not.toHaveBeenCalled();

    // Enter again without editing the field
    wrapper.find('form.quick-input-container').simulate('submit');
    await flushPromises();
    await flushPromises();

    expect(
      api.patchQuickInput.mock.calls.map(([{ fieldName, fieldValue }]) => [
        fieldName,
        fieldValue,
      ])
    ).toEqual([
      ['M_Product_ID', PRODUCT],
      ['Qty', '10'],
      ['Qty', '10'],
    ]);
    expect(addNotification).toHaveBeenCalledTimes(1); // only for the first, failed attempt
    expect(api.completeQuickInput).toHaveBeenCalledTimes(1);
  });

  it('after a failed PATCH, an edited value is not overwritten by re-sending the old one', async () => {
    productPatch.resolve({
      data: [{ fieldsByName: { M_Product_ID: { value: PRODUCT } } }],
    });
    pickProduct();
    await flushPromises();
    wrapper.update();

    api.patchQuickInput.mockImplementationOnce(() =>
      Promise.reject(new Error('server rejected the value'))
    );
    typeQtyAndPressEnter('10');
    await flushPromises();
    await flushPromises();

    // the user corrects the value and presses Enter again
    typeQtyAndPressEnter('20');
    await flushPromises();
    await flushPromises();

    const qtyPatches = api.patchQuickInput.mock.calls
      .map(([{ fieldName, fieldValue }]) => [fieldName, fieldValue])
      .filter(([fieldName]) => fieldName === 'Qty');
    expect(qtyPatches[qtyPatches.length - 1]).toEqual(['Qty', '20']);
    expect(qtyPatches).toEqual([
      ['Qty', '10'],
      ['Qty', '20'],
    ]);
    expect(api.completeQuickInput).toHaveBeenCalledTimes(1);
  });

  it('a failure of an older PATCH does not resurrect its value when a newer one of the same field was queued', async () => {
    productPatch.resolve({
      data: [{ fieldsByName: { M_Product_ID: { value: PRODUCT } } }],
    });
    pickProduct();
    await flushPromises();
    wrapper.update();

    const firstQtyPatch = deferred();
    const secondQtyPatch = deferred();
    api.patchQuickInput
      .mockImplementationOnce(() =>
        firstQtyPatch.promise.then(() => {
          throw new Error('server rejected the value');
        })
      )
      .mockImplementationOnce(() => secondQtyPatch.promise);

    // Arrow up on a number field sends its PATCH right away (Qty=10), without submitting
    qtyInput().simulate('change', { target: { value: '10' } });
    qtyInput().simulate('keyDown', { key: 'ArrowUp', target: { value: '10' } });
    await flushPromises();

    // while it is in flight, the user changes the value with arrow up again (Qty=20 queued)
    qtyInput().simulate('change', { target: { value: '20' } });
    qtyInput().simulate('keyDown', { key: 'ArrowUp', target: { value: '20' } });

    firstQtyPatch.resolve(); // Qty=10 fails now, while Qty=20 is still in flight
    await flushPromises();

    // the user presses Enter before the Qty=20 PATCH was answered
    typeQtyAndPressEnter('20');
    await flushPromises();

    secondQtyPatch.resolve({
      data: [{ fieldsByName: { Qty: { value: '20' } } }],
    });
    await flushPromises();
    await flushPromises();

    const qtyPatches = api.patchQuickInput.mock.calls
      .map(([{ fieldName, fieldValue }]) => [fieldName, fieldValue])
      .filter(([fieldName]) => fieldName === 'Qty');
    expect(qtyPatches).toEqual([
      ['Qty', '10'],
      ['Qty', '20'],
    ]);
    expect(addNotification).not.toHaveBeenCalled();
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
