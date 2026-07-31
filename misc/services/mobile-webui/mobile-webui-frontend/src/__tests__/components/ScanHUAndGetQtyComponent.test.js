import fs from 'fs';
import path from 'path';
import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import ScanHUAndGetQtyComponent from '../../components/ScanHUAndGetQtyComponent';
import { reducer as settings } from '../../reducers/settings';

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
}));

const HU_SCANNER_INPUT = 'scanHUBarcode-input';
const YES_BUTTON = '#yes-button';
const YES_NO_DIALOG = '.yes-no-dialog';
const SPINNER = '.loading';

const OVER_PICK_PROMPT = 'Do you really want to pack more than ordered?';

const newDeferred = () => {
  let resolve;
  let reject;
  const promise = new Promise((res, rej) => {
    resolve = res;
    reject = rej;
  });
  return { promise, resolve, reject };
};

/**
 * @param isTUToBePickedAsWhole whole-TU scan (books the scanned unit, no qty dialog) vs per-piece CU scan.
 * @param qtyRemaining what is still ordered on the line - the component's qtyMax/qtyTarget.
 * @param huQty the scanned TU's own content, which the whole-TU path compares against qtyRemaining.
 */
const renderComponent = ({ isTUToBePickedAsWhole, qtyRemaining, huQty, isPromptEnabled, onResult }) => {
  const getConfirmationPromptForQty = (qtyInput) => (Number(qtyInput) > qtyRemaining ? OVER_PICK_PROMPT : undefined);

  const resolveScannedBarcode = async (scannedBarcode) => ({
    qrCode: { code: scannedBarcode, isUnique: !isTUToBePickedAsWhole, isTUToBePickedAsWhole },
    ...(isTUToBePickedAsWhole ? { isTUToBePickedAsWhole: true, qtyInitial: huQty } : {}),
  });

  return render(
    <Provider store={createStore(combineReducers({ settings }))}>
      <ScanHUAndGetQtyComponent
        qtyMax={qtyRemaining}
        qtyTarget={qtyRemaining}
        uom="PCE"
        resolveScannedBarcode={resolveScannedBarcode}
        onResult={onResult}
        getConfirmationPromptForQty={isPromptEnabled ? getConfirmationPromptForQty : undefined}
      />
    </Provider>
  );
};

/** Feeds a barcode to the HU scanner the screen currently exposes, the way a hardware scan does. */
const scanHU = async (barcode) => {
  const input = screen.getByTestId(HU_SCANNER_INPUT);
  input.value = barcode;
  await act(async () => {
    fireEvent.keyUp(input, { key: 'Enter' });
  });
};

/**
 * Feeds a barcode to the HU scanner IF the screen still exposes one. That condition is the subject
 * under test: while a pick is in flight the screen must not offer a scan target at all, so the
 * operator's next scan cannot start a second pick.
 */
const scanHUIfScannerIsArmed = async (barcode) => {
  if (screen.queryByTestId(HU_SCANNER_INPUT)) {
    await scanHU(barcode);
  }
};

const clickYesIfPromptIsRaised = async () => {
  const yesButton = document.querySelector(YES_BUTTON);
  if (yesButton) {
    await act(async () => {
      fireEvent.click(yesButton);
    });
  }
};

//
//
// -----------------------------------------------------------------------------
//
//

// The over-delivery confirmation's "Ja" fires the pick and drops the dialog, returning the operator
// to the HU-scan step. Until the pick's POST comes back the screen must not accept another scan:
// on a handheld over warehouse WiFi the operator's next scan would otherwise book the same line
// twice. GetQuantityDialog already guards its own confirmation this way (isProcessing + Spinner +
// disabled confirm); the whole-TU path has to do the same.
describe('ScanHUAndGetQtyComponent: the confirmed whole-TU pick is in flight', () => {
  const QTY_REMAINING = 2;
  const HU_QTY = 32;

  const startConfirmedWholeTUPick = async () => {
    const pick = newDeferred();
    const onResult = jest.fn(() => pick.promise);

    renderComponent({
      isTUToBePickedAsWhole: true,
      qtyRemaining: QTY_REMAINING,
      huQty: HU_QTY,
      isPromptEnabled: true,
      onResult,
    });

    await act(async () => {});
    await scanHU('WHOLE-TU-LABEL-1');

    expect(document.querySelector(YES_NO_DIALOG)).not.toBeNull();
    await act(async () => {
      fireEvent.click(document.querySelector(YES_BUTTON));
    });
    expect(onResult).toHaveBeenCalledTimes(1);

    return { pick, onResult };
  };

  it('shows the pick in progress and does not re-arm the HU scanner', async () => {
    const { pick } = await startConfirmedWholeTUPick();

    expect(document.querySelector(SPINNER)).not.toBeNull();
    expect(screen.queryByTestId(HU_SCANNER_INPUT)).toBeNull();

    await act(async () => {
      pick.resolve({});
    });

    expect(document.querySelector(SPINNER)).toBeNull();
    expect(screen.getByTestId(HU_SCANNER_INPUT)).toBeInTheDocument();
  });

  it('books nothing a second time when the operator scans again', async () => {
    const { onResult } = await startConfirmedWholeTUPick();

    await scanHUIfScannerIsArmed('WHOLE-TU-LABEL-2');
    await clickYesIfPromptIsRaised();

    expect(onResult).toHaveBeenCalledTimes(1);
  });

  it('clears the in-progress state when the pick fails, so the operator can scan again', async () => {
    const { pick, onResult } = await startConfirmedWholeTUPick();

    // The in-progress state has to be entered before the error branch can be shown to clear it -
    // without this the assertions below hold trivially on a component that never enters it at all.
    expect(document.querySelector(SPINNER)).not.toBeNull();

    await act(async () => {
      pick.reject({ message: 'pick rejected by the server' });
    });

    expect(document.querySelector(SPINNER)).toBeNull();
    expect(screen.getByTestId(HU_SCANNER_INPUT)).toBeInTheDocument();

    await scanHU('WHOLE-TU-LABEL-3');
    await clickYesIfPromptIsRaised();
    expect(onResult).toHaveBeenCalledTimes(2);
  });
});

//
//
// -----------------------------------------------------------------------------
//
//

const COMPONENT_SOURCE = fs.readFileSync(path.join(__dirname, '../../components/ScanHUAndGetQtyComponent.jsx'), 'utf8');

/** The body, braces included, of a module-level `const <name> = (…) => { … }`. */
const arrowFunctionBody = (name) => {
  const declarationIndex = COMPONENT_SOURCE.indexOf(`const ${name} = `);
  if (declarationIndex < 0) {
    throw new Error(`ScanHUAndGetQtyComponent.jsx declares no "${name}"`);
  }

  const bodyStart = COMPONENT_SOURCE.indexOf('{', COMPONENT_SOURCE.indexOf('=>', declarationIndex));
  let depth = 0;
  for (let i = bodyStart; i < COMPONENT_SOURCE.length; i++) {
    if (COMPONENT_SOURCE[i] === '{') {
      depth++;
    } else if (COMPONENT_SOURCE[i] === '}' && --depth === 0) {
      return COMPONENT_SOURCE.substring(bodyStart, i + 1);
    }
  }
  throw new Error(`Unbalanced braces in "${name}"`);
};

const countOccurrences = (haystack, needle) => haystack.split(needle).length - 1;

// The CU and whole-TU paths must stay behaviourally identical, enforced structurally by the single
// validateQtyAgainstMax both of them call. Hence the structural assertions: re-duplicating the ceiling
// into two behaviour-identical copies keeps an outcome-only test green, while these fail.
describe('ScanHUAndGetQtyComponent: the CU and whole-TU ceilings resolve through one helper', () => {
  it('reaches the ceiling from the CU path via validateQtyAgainstMax', () => {
    expect(arrowFunctionBody('validateQtyEntered')).toContain('validateQtyAgainstMax(');
  });

  it('reaches the ceiling from the whole-TU path via the same validateQtyAgainstMax', () => {
    expect(arrowFunctionBody('requestQtyOrReportResult')).toContain('validateQtyAgainstMax(');
  });

  it('has exactly one ceiling to reach - nothing outside the helper builds a qtyAboveMax error', () => {
    expect(countOccurrences(COMPONENT_SOURCE, 'const validateQtyAgainstMax = ')).toBe(1);

    const linesOutsideTheHelper = COMPONENT_SOURCE.replace(arrowFunctionBody('validateQtyAgainstMax'), '')
      .replace(/^const DEFAULT_MSG_qtyAboveMax = .*$/m, '')
      .split('\n');
    expect(linesOutsideTheHelper.filter((line) => line.includes('DEFAULT_MSG_qtyAboveMax'))).toEqual([]);
  });
});
