import React from 'react';
import { act, render } from '@testing-library/react';

import { useGrais } from '../../../apps/huManager/hooks/useGrais';
import * as api from '../../../apps/huManager/api';
import { toastError } from '../../../utils/toast';

jest.mock('../../../apps/huManager/api', () => ({
  getGRAIs: jest.fn(),
  setGRAIs: jest.fn(),
}));
jest.mock('../../../utils/toast', () => ({
  toastError: jest.fn(),
}));

const HU_ID = 4711;

/** A promise the test resolves by hand, to hold a backend answer while the user keeps scanning. */
const deferred = () => {
  let resolve;
  const promise = new Promise((r) => {
    resolve = r;
  });
  return { promise, resolve };
};

const renderUseGrais = async ({ initialGrais = [], tuCount = 0 } = {}) => {
  api.getGRAIs.mockResolvedValue({ graiCodes: initialGrais, tuCount });
  const result = { current: null };
  function TestComponent() {
    result.current = useGrais({ huId: HU_ID });
    return null;
  }
  await act(async () => {
    render(<TestComponent />);
  });
  return result;
};

/** Taps Send and keeps the PUT answer pending; returns the function that lets the backend answer. */
const sendAndHoldResponse = (result) => {
  const put = deferred();
  api.setGRAIs.mockReturnValueOnce(put.promise);
  act(() => result.current.sendToBackend());
  expect(result.current.sending).toBe(true);
  return async (backendResponse) => {
    await act(async () => {
      put.resolve(backendResponse);
      await put.promise;
    });
  };
};

describe('useGrais - send response while the user keeps scanning', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('nothing changed while sending: list = backend answer, not dirty', async () => {
    const result = await renderUseGrais();
    act(() => result.current.addGrais(['A', 'B']));
    expect(result.current.dirty).toBe(true);

    const answer = sendAndHoldResponse(result);
    expect(api.setGRAIs).toHaveBeenCalledWith(HU_ID, ['A', 'B']);
    await answer({ graiCodes: ['A', 'B'], tuCount: 0 });

    expect(result.current.graiCodes).toEqual(['A', 'B']);
    expect(result.current.dirty).toBe(false);
    expect(result.current.sending).toBe(false);
  });

  it('a GRAI scanned while the send is in flight survives the answer and stays unsaved (dirty)', async () => {
    const result = await renderUseGrais();
    act(() => result.current.addGrais(['A', 'B']));

    const answer = sendAndHoldResponse(result);
    act(() => result.current.addGrais(['C']));
    await answer({ graiCodes: ['A', 'B'], tuCount: 0 });

    expect(result.current.graiCodes).toEqual(['A', 'B', 'C']);
    expect(result.current.dirty).toBe(true);
  });

  it('a GRAI removed while the send is in flight stays removed and unsaved (dirty)', async () => {
    const result = await renderUseGrais();
    act(() => result.current.addGrais(['A', 'B']));

    const answer = sendAndHoldResponse(result);
    act(() => result.current.removeGrai('A'));
    await answer({ graiCodes: ['A', 'B'], tuCount: 0 });

    expect(result.current.graiCodes).toEqual(['B']);
    expect(result.current.dirty).toBe(true);
  });

  it('Clear All while the send is in flight keeps the list empty and unsaved (dirty)', async () => {
    const result = await renderUseGrais();
    act(() => result.current.addGrais(['A', 'B']));

    const answer = sendAndHoldResponse(result);
    act(() => result.current.clearAllGrais());
    await answer({ graiCodes: ['A', 'B'], tuCount: 0 });

    expect(result.current.graiCodes).toEqual([]);
    expect(result.current.dirty).toBe(true);
  });

  it('with a TU count: a GRAI scanned into a free slot while sending is kept as assigned, dirty', async () => {
    const result = await renderUseGrais({ tuCount: 3 });
    act(() => result.current.addGrais(['A', 'B']));

    const answer = sendAndHoldResponse(result);
    act(() => result.current.addGrais(['C']));
    await answer({ graiCodes: ['A', 'B'], tuCount: 3 });

    expect(result.current.assignedGrais).toEqual(['A', 'B', 'C']);
    expect(result.current.extraGrais).toEqual([]);
    expect(result.current.dirty).toBe(true);
  });

  it('with a TU count: a GRAI scanned beyond the TU count while sending stays an extra, dirty', async () => {
    const result = await renderUseGrais({ tuCount: 2 });
    act(() => result.current.addGrais(['A', 'B']));

    const answer = sendAndHoldResponse(result);
    act(() => result.current.addGrais(['C']));
    await answer({ graiCodes: ['A', 'B'], tuCount: 2 });

    expect(result.current.assignedGrais).toEqual(['A', 'B']);
    expect(result.current.extraGrais).toEqual(['C']);
    expect(result.current.dirty).toBe(true);
  });

  it('the backend lowers the TU count: the GRAIs beyond it show as extras, not dirty (unchanged behaviour)', async () => {
    // The answer reports tuCount 1, so the 2nd sent code is no longer assigned and shows as an extra.
    const result = await renderUseGrais({ tuCount: 2 });
    act(() => result.current.addGrais(['A', 'B']));

    const answer = sendAndHoldResponse(result);
    await answer({ graiCodes: ['A'], tuCount: 1 });

    expect(result.current.assignedGrais).toEqual(['A']);
    expect(result.current.extraGrais).toEqual(['B']);
    expect(result.current.dirty).toBe(false);
  });

  it('Undo while the send is in flight: the answer shows the saved list, not dirty', async () => {
    const result = await renderUseGrais();
    act(() => result.current.addGrais(['A', 'B']));

    const answer = sendAndHoldResponse(result);
    act(() => result.current.addGrais(['C']));
    api.getGRAIs.mockResolvedValueOnce({ graiCodes: ['A', 'B'], tuCount: 0 });
    await act(async () => {
      result.current.loadFromBackend();
    });
    await answer({ graiCodes: ['A', 'B'], tuCount: 0 });

    expect(result.current.graiCodes).toEqual(['A', 'B']);
    expect(result.current.dirty).toBe(false);
  });

  it('Undo while the send is in flight, then a scan: the scan is kept and unsaved (dirty)', async () => {
    const result = await renderUseGrais();
    act(() => result.current.addGrais(['A', 'B']));

    const answer = sendAndHoldResponse(result);
    api.getGRAIs.mockResolvedValueOnce({ graiCodes: ['A', 'B'], tuCount: 0 });
    await act(async () => {
      result.current.loadFromBackend();
    });
    act(() => result.current.addGrais(['C']));
    await answer({ graiCodes: ['A', 'B'], tuCount: 0 });

    expect(result.current.graiCodes).toEqual(['A', 'B', 'C']);
    expect(result.current.dirty).toBe(true);
  });

  it('a failed Undo while the send is in flight does not discard the edits made meanwhile', async () => {
    const result = await renderUseGrais();
    act(() => result.current.addGrais(['A', 'B']));

    const answer = sendAndHoldResponse(result);
    act(() => result.current.addGrais(['C']));
    api.getGRAIs.mockRejectedValueOnce(new Error('boom'));
    await act(async () => {
      result.current.loadFromBackend();
    });
    await answer({ graiCodes: ['A', 'B'], tuCount: 0 });

    expect(result.current.graiCodes).toEqual(['A', 'B', 'C']);
    expect(result.current.dirty).toBe(true);
  });

  it('a GRAI already in the list delivered again while sending (scanner re-read) changes nothing: not dirty', async () => {
    const result = await renderUseGrais();
    act(() => result.current.addGrais(['A', 'B']));

    const answer = sendAndHoldResponse(result);
    act(() => result.current.addGrais(['B']));
    await answer({ graiCodes: ['A', 'B'], tuCount: 0 });

    expect(result.current.graiCodes).toEqual(['A', 'B']);
    expect(result.current.dirty).toBe(false);
  });

  it('a GRAI already in the list scanned again after a send changes nothing: stays not dirty', async () => {
    const result = await renderUseGrais({ initialGrais: ['A'] });
    act(() => result.current.addGrais(['A']));
    act(() => result.current.removeGrai('X'));

    expect(result.current.graiCodes).toEqual(['A']);
    expect(result.current.dirty).toBe(false);
  });

  it('a failed send keeps the local list and stays dirty', async () => {
    const result = await renderUseGrais();
    act(() => result.current.addGrais(['A']));
    api.setGRAIs.mockRejectedValueOnce(new Error('boom'));
    await act(async () => {
      result.current.sendToBackend();
    });

    expect(toastError).toHaveBeenCalledTimes(1);
    expect(result.current.graiCodes).toEqual(['A']);
    expect(result.current.dirty).toBe(true);
    expect(result.current.sending).toBe(false);
  });
});
