import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import ScanHUAndGetQtyComponent from '../../../../components/ScanHUAndGetQtyComponent';
import { reducer as settings } from '../../../../reducers/settings';
import { toQtyEntryBehaviorProps } from '../../../../apps/picking/cuQtyEntry/cuQtyEntryPolicy';
import { POLICY } from '../../../../__testSupport__/picking/testPolicies';
import OpenNextTUButton from '../../../../apps/picking/cuQtyEntry/OpenNextTUButton';

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

// the real wiring (PickLineScanScreen): the "next TU" button owns the hidden scanner of the quantity block
const nextTUActions = ({ scanner }) => <OpenNextTUButton onNextTU={jest.fn()} scanner={scanner} />;

const store = () => createStore(combineReducers({ settings }));

const labels = {
  'RAW-A': { lotNo: 'LOTA', bestBeforeDate: '2026-11-07', cuLabel: 'RAW-A' },
  'RAW-B': { lotNo: 'LOTB', bestBeforeDate: '2026-12-01', cuLabel: 'RAW-B' },
};

const renderComponent = ({ onResult, resolveScannedBarcode, ...props }) =>
  render(
    <Provider store={store()}>
      <ScanHUAndGetQtyComponent
        qtyMax={12}
        qtyTarget={12}
        uom="PCE"
        {...toQtyEntryBehaviorProps(POLICY.SCAN_ONCE_ENTER_QTY)}
        nextTUActions={nextTUActions}
        resolveScannedBarcode={resolveScannedBarcode ?? (async (code) => labels[code])}
        onResult={onResult}
        {...props}
      />
    </Provider>
  );

const scan = async (testId, value) => {
  const input = screen.getByTestId(testId);
  input.value = value;
  await act(async () => {
    fireEvent.keyUp(input, { key: 'Enter' });
  });
  await act(async () => {});
};

const typeQty = async (value) => {
  await act(async () => {
    fireEvent.change(document.querySelector('#qty-input'), { target: { value } });
  });
};

const clickOk = async () => {
  await act(async () => {
    fireEvent.click(screen.getByTestId('done-button'));
  });
  await act(async () => {});
};

describe('ScanHUAndGetQtyComponent: scan once stays on the line', () => {
  it('OK with open quantity left reports "not done" and reopens the quantity block with the suggestion, no new scan', async () => {
    const onResult = jest.fn().mockResolvedValue();
    const view = renderComponent({ onResult });
    await act(async () => {});
    await scan('scanHUBarcode-input', 'RAW-A');
    await typeQty('5');
    await clickOk();

    expect(onResult).toHaveBeenCalledTimes(1);
    expect(onResult).toHaveBeenLastCalledWith(expect.objectContaining({ qty: '5', cuLabel: 'RAW-A', isDone: false }));
    // the screen keeps the quantity block, not the scan step
    expect(screen.queryByTestId('scanHUBarcode-input')).toBeNull();
    expect(screen.getByTestId('done-button')).toBeInTheDocument();

    // the open qty and the suggestion arrive as props after the booking (qtyMax 12 -> 7)
    view.rerender(
      <Provider store={store()}>
        <ScanHUAndGetQtyComponent
          qtyMax={7}
          qtyTarget={7}
          suggestedQty={5}
          uom="PCE"
          {...toQtyEntryBehaviorProps(POLICY.SCAN_ONCE_ENTER_QTY)}
          nextTUActions={nextTUActions}
          resolveScannedBarcode={async (code) => labels[code]}
          onResult={onResult}
        />
      </Provider>
    );
    await act(async () => {});
    expect(document.querySelector('#qty-input').value).toBe('5');

    // confirming books again on the last label, without a new scan
    await clickOk();
    expect(onResult).toHaveBeenCalledTimes(2);
    expect(onResult).toHaveBeenLastCalledWith(
      expect.objectContaining({ qty: '5', cuLabel: 'RAW-A', lotNo: 'LOTA', isDone: false })
    );
  });

  it('the booking that reaches the open quantity reports "done"', async () => {
    const onResult = jest.fn().mockResolvedValue();
    renderComponent({ onResult, qtyMax: 5, qtyTarget: 5 });
    await act(async () => {});
    await scan('scanHUBarcode-input', 'RAW-A');
    await typeQty('5');
    await clickOk();
    expect(onResult).toHaveBeenLastCalledWith(expect.objectContaining({ qty: '5', isDone: true }));
  });

  it('a label scanned in the quantity block replaces the remembered label', async () => {
    const onResult = jest.fn().mockResolvedValue();
    renderComponent({ onResult });
    await act(async () => {});
    await scan('scanHUBarcode-input', 'RAW-A');
    expect(screen.getByTestId('lotNo').value).toBe('LOTA');

    await scan('rescanCULabel-input', 'RAW-B');
    expect(screen.getByTestId('lotNo').value).toBe('LOTB');

    await typeQty('5');
    await clickOk();
    expect(onResult).toHaveBeenLastCalledWith(expect.objectContaining({ qty: '5', cuLabel: 'RAW-B', lotNo: 'LOTB' }));
  });

  it('a label of another product is refused in the quantity block: nothing is replaced, nothing booked', async () => {
    const onResult = jest.fn().mockResolvedValue();
    const resolveScannedBarcode = jest.fn(async (code) => {
      if (code === 'OTHER') {
        throw { messageKey: 'activities.picking.qrcode.differentProduct' };
      }
      return labels[code];
    });
    renderComponent({ onResult, resolveScannedBarcode });
    await act(async () => {});
    await scan('scanHUBarcode-input', 'RAW-A');
    await scan('rescanCULabel-input', 'OTHER');

    expect(screen.getByTestId('lotNo').value).toBe('LOTA');
    expect(onResult).not.toHaveBeenCalled();
  });

  it('a GRAI scanned in the quantity block only opens the TU: label and block stay', async () => {
    const onResult = jest.fn().mockResolvedValue();
    const resolveScannedBarcode = async (code) => (code === 'GRAI' ? { isNextTUOpened: true } : labels[code]);
    renderComponent({ onResult, resolveScannedBarcode });
    await act(async () => {});
    await scan('scanHUBarcode-input', 'RAW-A');
    await scan('rescanCULabel-input', 'GRAI');
    expect(screen.getByTestId('lotNo').value).toBe('LOTA');
    expect(onResult).not.toHaveBeenCalled();
  });

  it('re-entry: a remembered label opens the quantity block directly, with the suggestion', async () => {
    const onResult = jest.fn().mockResolvedValue();
    renderComponent({
      onResult,
      qtyMax: 7,
      qtyTarget: 7,
      suggestedQty: 5,
      rememberedCULabel: { cuLabel: 'RAW-A', lotNo: 'LOTA', bestBeforeDate: '2026-11-07' },
    });
    await act(async () => {});
    expect(screen.queryByTestId('scanHUBarcode-input')).toBeNull();
    expect(screen.getByTestId('lotNo').value).toBe('LOTA');
    expect(document.querySelector('#qty-input').value).toBe('5');

    await clickOk();
    expect(onResult).toHaveBeenCalledWith(
      expect.objectContaining({ qty: '5', cuLabel: 'RAW-A', lotNo: 'LOTA', isDone: false })
    );
  });

  it('without a remembered label the scan step comes first and the field starts empty', async () => {
    renderComponent({ onResult: jest.fn() });
    await act(async () => {});
    expect(screen.getByTestId('scanHUBarcode-input')).toBeInTheDocument();
    await scan('scanHUBarcode-input', 'RAW-A');
    expect(document.querySelector('#qty-input').value).toBe('');
  });

  it('Manual keeps leaving the screen after OK (done by default) and has no label rescan', async () => {
    const onResult = jest.fn().mockResolvedValue();
    renderComponent({
      onResult,
      ...toQtyEntryBehaviorProps(POLICY.MANUAL),
      resolveScannedBarcode: async () => ({ qtyTarget: 12, qtyMax: 12 }),
    });
    await act(async () => {});
    await scan('scanHUBarcode-input', 'whatever');
    expect(screen.queryByTestId('rescanCULabel-input')).toBeNull();
    await clickOk();
    expect(onResult).toHaveBeenLastCalledWith(expect.objectContaining({ isDone: true }));
  });
});
