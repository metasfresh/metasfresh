import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import OpenNextTUButton from '../../../../apps/picking/cuQtyEntry/OpenNextTUButton';
import { closeAndOpenNextTU, useOpenNextTU } from '../../../../apps/picking/cuQtyEntry/useOpenNextTU';
import * as pickingApi from '../../../../api/picking';
import { toastErrorFromObj, toastNotification } from '../../../../utils/toast';
import GetQuantityDialog from '../../../../components/dialogs/GetQuantityDialog';
import ScanHUAndGetQtyComponent from '../../../../components/ScanHUAndGetQtyComponent';
import { reducer as settings } from '../../../../reducers/settings';
import { toQtyEntryBehaviorProps } from '../../../../apps/picking/cuQtyEntry/cuQtyEntryPolicy';
import { POLICY } from '../../../../__testSupport__/picking/testPolicies';

jest.mock('../../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../../../utils/toast', () => ({
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
  toastNotification: jest.fn(),
}));

jest.mock('../../../../api/picking', () => ({
  closePickingTarget: jest.fn(),
  setTUPickingTargetFromGrai: jest.fn(),
}));
jest.mock('../../../../hooks/useMobileNavigation', () => ({ useMobileNavigation: () => ({ push: jest.fn() }) }));
jest.mock('react-redux', () => ({ ...jest.requireActual('react-redux'), useDispatch: () => jest.fn() }));

const TU_TYPE_ID = 1000042;

const createDeps = () => {
  const calls = [];
  return {
    calls,
    closeTarget: jest.fn(async () => calls.push('close')),
    setTarget: jest.fn(async ({ target }) => calls.push(['set', target])),
    gotoSelectTarget: jest.fn(() => calls.push('gotoS7')),
  };
};

describe('closeAndOpenNextTU: the "Weitere Kiste" button', () => {
  it('line names a TU type, a TU is open: closes it, then opens a new TU of that type', async () => {
    const deps = createDeps();
    await closeAndOpenNextTU({
      ...deps,
      packingTuPIId: TU_TYPE_ID,
      packingItemName: 'Kiste x 4',
      isTargetOpen: true,
    });
    expect(deps.calls).toEqual(['close', ['set', { tuPIId: TU_TYPE_ID, caption: 'Kiste x 4' }]]);
  });

  it('line names a TU type, no TU open (first TU): only opens the new TU', async () => {
    const deps = createDeps();
    await closeAndOpenNextTU({
      ...deps,
      packingTuPIId: TU_TYPE_ID,
      packingItemName: 'Kiste x 4',
      isTargetOpen: false,
    });
    expect(deps.calls).toEqual([['set', { tuPIId: TU_TYPE_ID, caption: 'Kiste x 4' }]]);
  });

  it('line names no TU type, a TU is open: closes it, then goes to the TU list', async () => {
    const deps = createDeps();
    await closeAndOpenNextTU({ ...deps, packingTuPIId: null, isTargetOpen: true });
    expect(deps.calls).toEqual(['close', 'gotoS7']);
    expect(deps.setTarget).not.toHaveBeenCalled();
  });

  it('line names no TU type, no TU open: goes to the TU list without closing anything', async () => {
    const deps = createDeps();
    await closeAndOpenNextTU({ ...deps, packingTuPIId: null, isTargetOpen: false });
    expect(deps.calls).toEqual(['gotoS7']);
    expect(deps.closeTarget).not.toHaveBeenCalled();
  });

  it('an absent packingTuPIId is treated like null', async () => {
    const deps = createDeps();
    await closeAndOpenNextTU({ ...deps, isTargetOpen: false });
    expect(deps.calls).toEqual(['gotoS7']);
  });

  it('a TU type id of 0 is still a TU type (only null / undefined mean none)', async () => {
    const deps = createDeps();
    await closeAndOpenNextTU({ ...deps, packingTuPIId: 0, packingItemName: 'x', isTargetOpen: false });
    expect(deps.setTarget).toHaveBeenCalledWith({ target: { tuPIId: 0, caption: 'x' } });
  });

  it('a failing close stops the flow: no new TU is opened', async () => {
    const deps = createDeps();
    deps.closeTarget.mockRejectedValue(new Error('close failed'));
    await expect(
      closeAndOpenNextTU({ ...deps, packingTuPIId: TU_TYPE_ID, packingItemName: 'x', isTargetOpen: true })
    ).rejects.toThrow('close failed');
    expect(deps.setTarget).not.toHaveBeenCalled();
  });
});

describe('OpenNextTUButton component', () => {
  it('shows one "next TU" button that calls onNextTU', () => {
    const onNextTU = jest.fn();
    render(<OpenNextTUButton onNextTU={onNextTU} />);
    fireEvent.click(screen.getByTestId('next-tu-button'));
    expect(onNextTU).toHaveBeenCalledTimes(1);
  });

  it('is disabled while a TU is being opened', () => {
    render(<OpenNextTUButton isProcessing onNextTU={jest.fn()} />);
    expect(screen.getByTestId('next-tu-button')).toBeDisabled();
  });
});

const store = () => createStore(combineReducers({ settings }));

describe('next TU button placement', () => {
  const nextTUActions = <button data-testid="next-tu-button">Next TU</button>;

  it('scan once quantity block: button sits in the action area, next to Abbrechen', () => {
    render(
      <Provider store={store()}>
        <GetQuantityDialog
          qtyTarget={10}
          uom="PCE"
          {...toQtyEntryBehaviorProps(POLICY.SCAN_ONCE_ENTER_QTY)}
          nextTUActions={nextTUActions}
          onQtyChange={jest.fn()}
          onCloseDialog={jest.fn()}
        />
      </Provider>
    );
    const buttons = screen.getByTestId('cancel-button').parentElement;
    expect(buttons).toContainElement(screen.getByTestId('next-tu-button'));
  });

  it('Manual quantity block without the prop: no next TU button', () => {
    render(
      <Provider store={store()}>
        <GetQuantityDialog qtyTarget={10} uom="PCE" onQtyChange={jest.fn()} onCloseDialog={jest.fn()} />
      </Provider>
    );
    expect(screen.queryByTestId('next-tu-button')).toBeNull();
  });

  it('scan step (before any booking): button is shown under the scan field', async () => {
    render(
      <Provider store={store()}>
        <ScanHUAndGetQtyComponent
          qtyMax={1}
          qtyTarget={1}
          uom="PCE"
          nextTUActions={nextTUActions}
          onResult={jest.fn()}
        />
      </Provider>
    );
    await act(async () => {});
    expect(screen.getByTestId('scanHUBarcode-input')).toBeInTheDocument();
    expect(screen.getByTestId('next-tu-button')).toBeInTheDocument();
  });

  it('a scan that only opened a TU does not open the quantity block', async () => {
    const onResult = jest.fn();
    const resolveScannedBarcode = jest.fn(async () => ({ isNextTUOpened: true }));
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

describe('next TU button inside the quantity block form', () => {
  it('is a plain button: a tap books nothing, even with a valid count typed', () => {
    const onQtyChange = jest.fn(() => Promise.resolve());
    render(
      <Provider store={store()}>
        <GetQuantityDialog
          qtyTarget={10}
          qtyInitial={5}
          uom="PCE"
          {...toQtyEntryBehaviorProps(POLICY.SCAN_ONCE_ENTER_QTY)}
          nextTUActions={<OpenNextTUButton onNextTU={jest.fn()} />}
          onQtyChange={onQtyChange}
          onCloseDialog={jest.fn()}
        />
      </Provider>
    );
    const button = screen.getByTestId('next-tu-button');
    expect(button).toHaveAttribute('type', 'button');
    fireEvent.click(button);
    expect(onQtyChange).not.toHaveBeenCalled();
  });
});

describe('useOpenNextTU', () => {
  const Harness = ({ exposed }) => {
    const nextTU = useOpenNextTU({
      applicationId: 'picking',
      wfProcessId: 'wf',
      activityId: 'a',
      lineId: 'l',
      packingTuPIId: TU_TYPE_ID,
      packingItemName: 'Kiste',
      tuPickingTarget: exposed.tuPickingTarget ?? null,
      setPickingTarget: exposed.setPickingTarget,
    });
    exposed.nextTU = nextTU;
    return null;
  };
  const setup = (setPickingTarget, tuPickingTarget = null) => {
    const exposed = { setPickingTarget, tuPickingTarget };
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

  it('a second tap while the first is in flight opens no second TU', async () => {
    const gate = deferred();
    const setPickingTarget = jest.fn(() => gate.promise);
    const exposed = setup(setPickingTarget);
    let first;
    await act(async () => {
      first = exposed.nextTU.onNextTU();
      exposed.nextTU.onNextTU();
    });
    expect(setPickingTarget).toHaveBeenCalledTimes(1);
    await act(async () => {
      gate.resolve({});
      await first;
    });
  });

  it('success: shows the opened TU in a notification', async () => {
    const exposed = setup(jest.fn().mockResolvedValue({}));
    await act(async () => {
      await exposed.nextTU.onNextTU();
    });
    expect(toastNotification).toHaveBeenCalledTimes(1);
    expect(toastErrorFromObj).not.toHaveBeenCalled();
  });

  it('failure: shows the error', async () => {
    const error = new Error('refused');
    const exposed = setup(jest.fn().mockRejectedValue(error));
    await act(async () => {
      await exposed.nextTU.onNextTU();
    });
    expect(toastErrorFromObj).toHaveBeenCalledWith(error);
    expect(toastNotification).not.toHaveBeenCalled();
  });

  it('a GRAI scan while a tap is in flight opens no second TU', async () => {
    const gate = deferred();
    const setPickingTarget = jest.fn(() => gate.promise);
    const exposed = setup(setPickingTarget);
    let first;
    await act(async () => {
      first = exposed.nextTU.onNextTU();
      // the scanner is told (error beep + message) instead of a success for a TU that was not opened
      await expect(exposed.nextTU.onGraiScanned('1234567.12345.S1')).rejects.toMatchObject({
        name: 'UserMessageError',
        messageKey: 'picking.nextTUBusy',
      });
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
      await expect(exposed.nextTU.onGraiScanned('1234567.12345.S1')).rejects.toThrow('bad grai');
    });
  });

  describe('a scanned GRAI', () => {
    const OPEN_TU = { tuPIId: TU_TYPE_ID, caption: 'Kiste' };

    it('TU open: opens the new TU from the GRAI without closing the open one first (the backend replaces it)', async () => {
      const exposed = setup(jest.fn(), OPEN_TU);
      await act(async () => {
        await exposed.nextTU.onGraiScanned('1234567.12345.S1');
      });
      expect(pickingApi.setTUPickingTargetFromGrai).toHaveBeenCalledWith({
        wfProcessId: 'wf',
        lineId: 'l',
        graiString: '1234567.12345.S1',
      });
      expect(pickingApi.closePickingTarget).not.toHaveBeenCalled();
    });

    it('TU open, backend refuses the GRAI: the open TU is not closed and the error reaches the caller', async () => {
      pickingApi.setTUPickingTargetFromGrai.mockRejectedValue(new Error('GRAI refused'));
      const exposed = setup(jest.fn(), OPEN_TU);
      await act(async () => {
        await expect(exposed.nextTU.onGraiScanned('bad')).rejects.toThrow('GRAI refused');
      });
      expect(pickingApi.closePickingTarget).not.toHaveBeenCalled();
      expect(toastNotification).not.toHaveBeenCalled();
    });

    it('no TU open: only opens the TU from the GRAI and notifies', async () => {
      const exposed = setup(jest.fn());
      await act(async () => {
        await exposed.nextTU.onGraiScanned('1234567.12345.S1');
      });
      expect(pickingApi.setTUPickingTargetFromGrai).toHaveBeenCalledTimes(1);
      expect(pickingApi.closePickingTarget).not.toHaveBeenCalled();
      expect(toastNotification).toHaveBeenCalledTimes(1);
    });
  });
});

describe('OpenNextTUButton owns the hidden scanner of the quantity block', () => {
  const renderWithScanner = (scanner) =>
    render(
      <Provider store={store()}>
        <OpenNextTUButton onNextTU={jest.fn()} scanner={scanner} />
      </Provider>
    );

  it('shows the barcode icon inside the button', () => {
    renderWithScanner(undefined);
    expect(screen.getByTestId('next-tu-button').querySelector('i.fa-barcode')).not.toBeNull();
  });

  it('without a scanner prop (first scan screen): no second scanner', () => {
    renderWithScanner(undefined);
    expect(screen.queryByTestId('rescanCULabel-input')).toBeNull();
  });

  it('with a scanner prop: one hidden scanner, no scanner visuals', async () => {
    renderWithScanner({ resolveScannedBarcode: jest.fn(), onResolvedResult: jest.fn() });
    await act(async () => {});
    expect(screen.getByTestId('rescanCULabel-input')).toBeInTheDocument();
    expect(document.querySelectorAll('input[data-testid="rescanCULabel-input"]')).toHaveLength(1);
    expect(document.querySelector('.scan-prompt')).toBeNull();
    expect(document.querySelector('.scan-prompt-icon')).toBeNull();
    expect(document.querySelector('.barcode-scanner-footer')).toBeNull();
    expect(screen.queryByTestId('barcode-scanner-enter-manually')).toBeNull();
  });

  it('a scan on the hidden scanner is resolved and forwarded to the caller', async () => {
    const resolveScannedBarcode = jest.fn(async ({ scannedBarcode }) => ({ scannedBarcode, ok: true }));
    const onResolvedResult = jest.fn();
    renderWithScanner({ resolveScannedBarcode, onResolvedResult });
    await act(async () => {});
    const input = screen.getByTestId('rescanCULabel-input');
    input.value = 'LABEL-1';
    await act(async () => {
      fireEvent.keyUp(input, { key: 'Enter' });
    });
    await act(async () => {});
    expect(onResolvedResult).toHaveBeenCalledWith({ scannedBarcode: 'LABEL-1', ok: true });
  });
});

describe('hidden scanner inside the quantity block', () => {
  const renderBlock = (nextTUActions) =>
    render(
      <Provider store={store()}>
        <ScanHUAndGetQtyComponent
          qtyMax={12}
          qtyTarget={12}
          uom="PCE"
          {...toQtyEntryBehaviorProps(POLICY.SCAN_ONCE_ENTER_QTY)}
          rememberedCULabel={{ cuLabel: 'RAW-A', lotNo: 'LOTA', bestBeforeDate: '2026-11-07' }}
          resolveScannedBarcode={async (code) => ({ cuLabel: code, lotNo: `LOT-${code}` })}
          nextTUActions={nextTUActions}
          onResult={jest.fn()}
        />
      </Provider>
    );
  const asButton = ({ scanner }) => <OpenNextTUButton onNextTU={jest.fn()} scanner={scanner} />;

  it('pop-up: no scanner visuals at all, the scanner input is outside the OK / Abbrechen submit flow', async () => {
    renderBlock(asButton);
    await act(async () => {});
    expect(screen.getByTestId('rescanCULabel-input')).toBeInTheDocument();
    expect(document.querySelector('.scan-prompt')).toBeNull();
    expect(document.querySelector('.barcode-scanner-footer')).toBeNull();
    expect(screen.queryByTestId('barcode-scanner-enter-manually')).toBeNull();
    expect(screen.queryByText('Manuell eingeben')).toBeNull();
    // an Enter of the scanner must never submit (= book) the quantity form
    const keyDownNotPrevented = fireEvent.keyDown(screen.getByTestId('rescanCULabel-input'), { key: 'Enter' });
    expect(keyDownNotPrevented).toBe(false);
  });

  it('pop-up: a label scanned on the hidden scanner switches the lot', async () => {
    renderBlock(asButton);
    await act(async () => {});
    const input = screen.getByTestId('rescanCULabel-input');
    input.value = 'RAW-B';
    await act(async () => {
      fireEvent.keyUp(input, { key: 'Enter' });
    });
    await act(async () => {});
    expect(screen.getByTestId('lotNo').value).toBe('LOT-RAW-B');
  });

  it('pop-up with a plain node (no scanner support): no scanner is added', async () => {
    renderBlock(<button data-testid="next-tu-button">x</button>);
    await act(async () => {});
    expect(screen.queryByTestId('rescanCULabel-input')).toBeNull();
  });

  it('first scan screen: exactly one scanner (its own visible one), the button brings none', async () => {
    render(
      <Provider store={store()}>
        <ScanHUAndGetQtyComponent qtyMax={1} qtyTarget={1} uom="PCE" nextTUActions={asButton} onResult={jest.fn()} />
      </Provider>
    );
    await act(async () => {});
    expect(screen.getByTestId('next-tu-button')).toBeInTheDocument();
    expect(screen.getByTestId('scanHUBarcode-input')).toBeInTheDocument();
    expect(screen.queryByTestId('rescanCULabel-input')).toBeNull();
    expect(document.querySelectorAll('input.input-text-offscreen')).toHaveLength(1);
  });
});
