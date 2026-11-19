import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import NextBoxActions, { openBoxFromGrai, openNextBox, useNextBox } from '../../components/NextBoxActions';
import * as pickingApi from '../../api/picking';
import { toastErrorFromObj, toastNotification } from '../../utils/toast';
import GetQuantityDialog from '../../components/dialogs/GetQuantityDialog';
import ScanHUAndGetQtyComponent from '../../components/ScanHUAndGetQtyComponent';
import { reducer as settings } from '../../reducers/settings';
import { CUQtyEntryMode } from '../../constants/CUQtyEntryMode';

jest.mock('../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../utils/toast', () => ({
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
  toastNotification: jest.fn(),
}));

jest.mock('../../api/picking', () => ({
  closePickingTarget: jest.fn(),
  setTUPickingTargetFromGrai: jest.fn(),
}));
jest.mock('../../hooks/useMobileNavigation', () => ({ useMobileNavigation: () => ({ push: jest.fn() }) }));
jest.mock('react-redux', () => ({ ...jest.requireActual('react-redux'), useDispatch: () => jest.fn() }));

const BOX_TYPE_ID = 1000042;

const newDeps = () => {
  const calls = [];
  return {
    calls,
    closeTarget: jest.fn(async () => calls.push('close')),
    setTarget: jest.fn(async ({ target }) => calls.push(['set', target])),
    setTargetFromGrai: jest.fn(async ({ graiString }) => calls.push(['setFromGrai', graiString])),
    gotoSelectTarget: jest.fn(() => calls.push('gotoS7')),
  };
};

describe('openNextBox: the "Weitere Kiste" button', () => {
  it('line names a TU type, a box is open: closes it, then opens a new box of that type', async () => {
    const deps = newDeps();
    await openNextBox({ ...deps, packingTuPIId: BOX_TYPE_ID, packingItemName: 'Kiste x 4', isTargetOpen: true });
    expect(deps.calls).toEqual(['close', ['set', { tuPIId: BOX_TYPE_ID, caption: 'Kiste x 4' }]]);
  });

  it('line names a TU type, no box open (first box): only opens the new box', async () => {
    const deps = newDeps();
    await openNextBox({ ...deps, packingTuPIId: BOX_TYPE_ID, packingItemName: 'Kiste x 4', isTargetOpen: false });
    expect(deps.calls).toEqual([['set', { tuPIId: BOX_TYPE_ID, caption: 'Kiste x 4' }]]);
  });

  it('line names no TU type, a box is open: closes it, then goes to the box list', async () => {
    const deps = newDeps();
    await openNextBox({ ...deps, packingTuPIId: null, isTargetOpen: true });
    expect(deps.calls).toEqual(['close', 'gotoS7']);
    expect(deps.setTarget).not.toHaveBeenCalled();
  });

  it('line names no TU type, no box open: goes to the box list without closing anything', async () => {
    const deps = newDeps();
    await openNextBox({ ...deps, packingTuPIId: null, isTargetOpen: false });
    expect(deps.calls).toEqual(['gotoS7']);
    expect(deps.closeTarget).not.toHaveBeenCalled();
  });

  it('an absent packingTuPIId is treated like null', async () => {
    const deps = newDeps();
    await openNextBox({ ...deps, isTargetOpen: false });
    expect(deps.calls).toEqual(['gotoS7']);
  });

  it('a TU type id of 0 is still a TU type (only null / undefined mean none)', async () => {
    const deps = newDeps();
    await openNextBox({ ...deps, packingTuPIId: 0, packingItemName: 'x', isTargetOpen: false });
    expect(deps.setTarget).toHaveBeenCalledWith({ target: { tuPIId: 0, caption: 'x' } });
  });

  it('a failing close stops the flow: no new box is opened', async () => {
    const deps = newDeps();
    deps.closeTarget.mockRejectedValue(new Error('close failed'));
    await expect(
      openNextBox({ ...deps, packingTuPIId: BOX_TYPE_ID, packingItemName: 'x', isTargetOpen: true })
    ).rejects.toThrow('close failed');
    expect(deps.setTarget).not.toHaveBeenCalled();
  });
});

describe('openBoxFromGrai: a scanned GRAI', () => {
  it('box open: closes it, then opens the new box from the GRAI', async () => {
    const deps = newDeps();
    await openBoxFromGrai({ ...deps, graiString: '1234567.12345.S1', isTargetOpen: true });
    expect(deps.calls).toEqual(['close', ['setFromGrai', '1234567.12345.S1']]);
  });

  it('no box open: only opens the box from the GRAI', async () => {
    const deps = newDeps();
    await openBoxFromGrai({ ...deps, graiString: '1234567.12345.S1', isTargetOpen: false });
    expect(deps.calls).toEqual([['setFromGrai', '1234567.12345.S1']]);
  });
});

describe('NextBoxActions component', () => {
  it('shows one "next box" button that calls onNextBox', () => {
    const onNextBox = jest.fn();
    render(<NextBoxActions onNextBox={onNextBox} />);
    fireEvent.click(screen.getByTestId('next-box-button'));
    expect(onNextBox).toHaveBeenCalledTimes(1);
  });

  it('is disabled while a box is being opened', () => {
    render(<NextBoxActions isProcessing onNextBox={jest.fn()} />);
    expect(screen.getByTestId('next-box-button')).toBeDisabled();
  });
});

const store = () => createStore(combineReducers({ settings }));

describe('next box button placement', () => {
  const nextBoxActions = <button data-testid="next-box-button">Next box</button>;

  it('scan once quantity block: button sits in the action area, next to Abbrechen', () => {
    render(
      <Provider store={store()}>
        <GetQuantityDialog
          qtyTarget={10}
          uom="PCE"
          cuQtyEntryMode={CUQtyEntryMode.SCAN_ONCE_ENTER_QTY}
          nextBoxActions={nextBoxActions}
          onQtyChange={jest.fn()}
          onCloseDialog={jest.fn()}
        />
      </Provider>
    );
    const buttons = screen.getByTestId('cancel-button').parentElement;
    expect(buttons).toContainElement(screen.getByTestId('next-box-button'));
  });

  it('Manual quantity block without the prop: no next box button', () => {
    render(
      <Provider store={store()}>
        <GetQuantityDialog qtyTarget={10} uom="PCE" onQtyChange={jest.fn()} onCloseDialog={jest.fn()} />
      </Provider>
    );
    expect(screen.queryByTestId('next-box-button')).toBeNull();
  });

  it('scan step (before any booking): button is shown under the scan field', async () => {
    render(
      <Provider store={store()}>
        <ScanHUAndGetQtyComponent
          qtyMax={1}
          qtyTarget={1}
          uom="PCE"
          nextBoxActions={nextBoxActions}
          onResult={jest.fn()}
        />
      </Provider>
    );
    await act(async () => {});
    expect(screen.getByTestId('scanHUBarcode-input')).toBeInTheDocument();
    expect(screen.getByTestId('next-box-button')).toBeInTheDocument();
  });

  it('a scan that only opened a box does not open the quantity block', async () => {
    const onResult = jest.fn();
    const resolveScannedBarcode = jest.fn(async () => ({ isNextBoxOpened: true }));
    render(
      <Provider store={store()}>
        <ScanHUAndGetQtyComponent
          qtyMax={1}
          qtyTarget={1}
          uom="PCE"
          resolveScannedBarcode={resolveScannedBarcode}
          onResult={onResult}
        />
      </Provider>
    );
    await act(async () => {});
    const input = screen.getByTestId('scanHUBarcode-input');
    input.value = 'whatever';
    await act(async () => {
      fireEvent.keyUp(input, { key: 'Enter' });
    });
    await act(async () => {});
    expect(resolveScannedBarcode).toHaveBeenCalledTimes(1);
    expect(screen.getByTestId('scanHUBarcode-input')).toBeInTheDocument();
    expect(screen.queryByTestId('done-button')).toBeNull();
    expect(onResult).not.toHaveBeenCalled();
  });
});

describe('next box button inside the quantity block form', () => {
  it('is a plain button: a tap books nothing, even with a valid count typed', () => {
    const onQtyChange = jest.fn(() => Promise.resolve());
    render(
      <Provider store={store()}>
        <GetQuantityDialog
          qtyTarget={10}
          qtyInitial={5}
          uom="PCE"
          cuQtyEntryMode={CUQtyEntryMode.SCAN_ONCE_ENTER_QTY}
          nextBoxActions={<NextBoxActions onNextBox={jest.fn()} />}
          onQtyChange={onQtyChange}
          onCloseDialog={jest.fn()}
        />
      </Provider>
    );
    const button = screen.getByTestId('next-box-button');
    expect(button).toHaveAttribute('type', 'button');
    fireEvent.click(button);
    expect(onQtyChange).not.toHaveBeenCalled();
  });
});

describe('useNextBox', () => {
  const Harness = ({ exposed }) => {
    const nextBox = useNextBox({
      applicationId: 'picking',
      wfProcessId: 'wf',
      activityId: 'a',
      lineId: 'l',
      packingTuPIId: BOX_TYPE_ID,
      packingItemName: 'Kiste',
      tuPickingTarget: null,
      setPickingTarget: exposed.setPickingTarget,
    });
    exposed.nextBox = nextBox;
    return null;
  };
  const setup = (setPickingTarget) => {
    const exposed = { setPickingTarget };
    render(<Harness exposed={exposed} />);
    return exposed;
  };
  const deferred = () => {
    let resolve;
    const promise = new Promise((r) => (resolve = r));
    return { promise, resolve };
  };

  beforeEach(() => {
    jest.clearAllMocks();
    pickingApi.setTUPickingTargetFromGrai.mockResolvedValue({});
  });

  it('a second tap while the first is in flight opens no second box', async () => {
    const gate = deferred();
    const setPickingTarget = jest.fn(() => gate.promise);
    const exposed = setup(setPickingTarget);
    let first;
    await act(async () => {
      first = exposed.nextBox.onNextBox();
      exposed.nextBox.onNextBox();
    });
    expect(setPickingTarget).toHaveBeenCalledTimes(1);
    await act(async () => {
      gate.resolve({});
      await first;
    });
  });

  it('success: shows the opened box in a notification', async () => {
    const exposed = setup(jest.fn().mockResolvedValue({}));
    await act(async () => {
      await exposed.nextBox.onNextBox();
    });
    expect(toastNotification).toHaveBeenCalledTimes(1);
    expect(toastErrorFromObj).not.toHaveBeenCalled();
  });

  it('failure: shows the error', async () => {
    const error = new Error('refused');
    const exposed = setup(jest.fn().mockRejectedValue(error));
    await act(async () => {
      await exposed.nextBox.onNextBox();
    });
    expect(toastErrorFromObj).toHaveBeenCalledWith(error);
    expect(toastNotification).not.toHaveBeenCalled();
  });

  it('a GRAI scan while a tap is in flight opens no second box', async () => {
    const gate = deferred();
    const setPickingTarget = jest.fn(() => gate.promise);
    const exposed = setup(setPickingTarget);
    let first;
    await act(async () => {
      first = exposed.nextBox.onNextBox();
      await exposed.nextBox.onGraiScanned('1234567.12345.S1');
    });
    expect(pickingApi.setTUPickingTargetFromGrai).not.toHaveBeenCalled();
    await act(async () => {
      gate.resolve({});
      await first;
    });
  });

  it('a GRAI scan reports its failure to the scanner (rethrows)', async () => {
    pickingApi.setTUPickingTargetFromGrai.mockRejectedValue(new Error('bad grai'));
    const exposed = setup(jest.fn());
    await act(async () => {
      await expect(exposed.nextBox.onGraiScanned('1234567.12345.S1')).rejects.toThrow('bad grai');
    });
  });
});
