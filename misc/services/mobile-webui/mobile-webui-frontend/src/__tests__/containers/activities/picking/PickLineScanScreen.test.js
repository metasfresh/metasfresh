import { POLICY } from '../../../../__testSupport__/picking/testPolicies';
import React from 'react';
import { render } from '@testing-library/react';
import { toast } from 'react-toastify';
import counterpart from 'counterpart';
import translations_de from '../../../../utils/translations_de';
import { setupCounterpart } from '../../../../utils/translations';
import { toastErrorFromObj } from '../../../../utils/toast';
import {
  usePostQtyPicked,
  convertScannedBarcodeToResolvedResult,
  convertScannedCULabelToResolvedResult,
  isInlineGraiCaptureEnabled,
  computeQtyUnitProps,
  computeScannedBarcodeParam,
  computeOpenTUHeaderEntries,
  computeRememberedCULabel,
  computePickFromHUQRCode,
  computeSourceHUQRCode,
} from '../../../../containers/activities/picking/PickLineScanScreen';

const mockDispatch = jest.fn();
const mockToastError = jest.fn();
jest.mock('../../../../utils/ui_trace', () => ({ trace: jest.fn() }));
jest.mock('react-redux', () => ({ ...jest.requireActual('react-redux'), useDispatch: () => mockDispatch }));
jest.mock('../../../../hooks/useMobileNavigation', () => ({ useMobileNavigation: () => ({}) }));

describe('PickingLineScanScreen', () => {
  const scannedBarcode =
    'HU#1#{"id":"59c0e3b5845d9fca58ccd9f2a906-30926","packingInfo":{"huUnitType":"V","packingInstructionsId":101,"caption":"No Packing Item"},"product":{"id":2009297,"code":"MyCode","name":"MyProduct"},"attributes":[{"code":"HU_BestBeforeDate","displayName":"Mindesthaltbarkeit","value":"2024-04-10"},{"code":"Lot-Nummer","displayName":"Lot-Nummer","value":"010124"},{"code":"WeightNet","displayName":"Gewicht Netto","value":"180.000"}]}';

  it('convertScannedBarcodeToResolvedResult', async () => {
    const result = await convertScannedBarcodeToResolvedResult({
      scannedBarcode,
      expectedProductId: '2009297',
    });

    expect(result).toEqual({
      bestBeforeDate: '2024-04-10',
      catchWeight: 180,
      lotNo: '010124',
      productionDate: undefined, // not present in this QR code
      scannedHU: { huUnitType: 'V' },
      qrCode: {
        barcodeType: 'HU',
        bestBeforeDate: '2024-04-10',
        code: scannedBarcode,
        displayable: '30926',
        huUnitType: 'V',
        isUnique: true,
        lotNo: '010124',
        productId: '2009297',
        weightNet: 180,
      },
    });
  });
});

describe('PickingLineScanScreen: label-required mode label scan', () => {
  const GS = '\u001d';
  // the string the scanner sends (DataWedge without control characters), and the same label from a scanner that sends GS
  const label = `0109506000134352` + `91PTST001` + `17261107` + `10LA0000001` + `922`;
  const labelGS = `0109506000134352` + `91PTST001${GS}17261107` + `10LA0000001${GS}922`;
  const constant = (startPosition, endPosition, constantValue) => ({
    type: 'CONSTANT',
    startPosition,
    endPosition,
    constantValue,
  });
  const cuLabelFormat = {
    id: 7,
    name: 'CU label',
    parts: [
      constant(1, 2, '01'),
      constant(17, 18, '91'),
      constant(26, 27, '17'),
      constant(34, 35, '10'),
      { type: 'PRODUCT_CODE', startPosition: 19, endPosition: 25 },
      { type: 'BEST_BEFORE_DATE', startPosition: 28, endPosition: 33, dateFormat: 'yyMMdd' },
      { type: 'LOT', startPosition: 36, endPosition: 44 },
    ],
  };
  const cuLabelFormatGS = {
    id: 8,
    name: 'CU label with GS',
    parts: [
      constant(1, 2, '01'),
      constant(17, 18, '91'),
      constant(27, 28, '17'),
      constant(35, 36, '10'),
      { type: 'PRODUCT_CODE', startPosition: 19, endPosition: 25 },
      { type: 'BEST_BEFORE_DATE', startPosition: 29, endPosition: 34, dateFormat: 'yyMMdd' },
      { type: 'LOT', startPosition: 37, endPosition: 45 },
    ],
  };

  it('a label with GS separators is read with a format of the GS positions', () => {
    expect(
      convertScannedCULabelToResolvedResult({
        scannedBarcode: labelGS,
        cuLabelFormat: cuLabelFormatGS,
        expectedProductNo: 'PTST001',
      })
    ).toEqual({
      lotNo: 'LA0000001',
      bestBeforeDate: '2026-11-07',
      cuLabel: labelGS,
      isTUToBePickedAsWhole: false,
    });
  });

  it('fills lot and best-before and keeps the raw label', () => {
    expect(
      convertScannedCULabelToResolvedResult({
        scannedBarcode: label,
        cuLabelFormat,
        expectedProductNo: 'PTST001',
      })
    ).toEqual({
      lotNo: 'LA0000001',
      bestBeforeDate: '2026-11-07',
      cuLabel: label,
      isTUToBePickedAsWhole: false,
    });
  });

  it.each([
    ['another product', label, cuLabelFormat, 'OTHER01', 'activities.picking.qrcode.differentProduct'],
    ['a code of another format', 'HU#1#{}', cuLabelFormat, 'PTST001', 'picking.cuLabel.formatMismatch'],
    ['no format on the line', label, undefined, 'PTST001', 'picking.cuLabel.noFormat'],
  ])('refuses %s', (_name, scannedBarcode, format, expectedProductNo, messageKey) => {
    expect(() =>
      convertScannedCULabelToResolvedResult({ scannedBarcode, cuLabelFormat: format, expectedProductNo })
    ).toThrow(expect.objectContaining({ messageKey }));
  });

  it('a refusal carries the scanned label as context, for the UI trace', () => {
    expect(() =>
      convertScannedCULabelToResolvedResult({ scannedBarcode: 'HU#1#{}', cuLabelFormat, expectedProductNo: 'PTST001' })
    ).toThrow(expect.objectContaining({ context: { scannedLabel: 'HU#1#{}' } }));
  });
});

describe('PickingLineScanScreen: pick-time GRAI capture', () => {
  it('stays on for Manual  and is switched off in both label-required modes', () => {
    const base = { graiScanEnabled: true, tuPickingTarget: null };
    expect(isInlineGraiCaptureEnabled({ ...base, cuQtyEntryPolicy: POLICY.MANUAL })).toBe(true);
    expect(isInlineGraiCaptureEnabled({ ...base, cuQtyEntryPolicy: POLICY.SCAN_EACH_CU })).toBe(false);
    expect(isInlineGraiCaptureEnabled({ ...base, cuQtyEntryPolicy: POLICY.SCAN_ONCE_ENTER_QTY })).toBe(false);
  });

  it('keeps the rule of Manual: off when GRAI scanning is not required or a TU target is set', () => {
    expect(isInlineGraiCaptureEnabled({ graiScanEnabled: false, tuPickingTarget: null })).toBe(false);
    expect(isInlineGraiCaptureEnabled({ graiScanEnabled: true, tuPickingTarget: { caption: 'x' } })).toBe(false);
  });
});

describe('PickingLineScanScreen: GRAI scan on a label-required-mode line', () => {
  const A_GRAI = '1234567.12345.SERIAL1';

  it('opens the TU from the GRAI and does not read it as a label', async () => {
    const onGrai = jest.fn().mockResolvedValue(undefined);
    const result = await convertScannedCULabelToResolvedResult({
      scannedBarcode: A_GRAI,
      cuLabelFormat: undefined,
      expectedProductNo: 'P1',
      onGrai,
    });
    expect(onGrai).toHaveBeenCalledWith(A_GRAI);
    expect(result).toEqual({ isNextTUOpened: true });
  });

  it('is accepted with and without a label format (never refused)', async () => {
    for (const cuLabelFormat of [undefined, { id: 1, name: 'x', parts: [] }]) {
      const onGrai = jest.fn().mockResolvedValue(undefined);
      await expect(
        convertScannedCULabelToResolvedResult({
          scannedBarcode: A_GRAI,
          cuLabelFormat,
          expectedProductNo: 'P1',
          onGrai,
        })
      ).resolves.toEqual({ isNextTUOpened: true });
    }
  });

  it('a failing TU opening is reported, not swallowed', async () => {
    const onGrai = jest.fn().mockRejectedValue(new Error('boom'));
    await expect(
      convertScannedCULabelToResolvedResult({ scannedBarcode: A_GRAI, expectedProductNo: 'P1', onGrai })
    ).rejects.toThrow('boom');
  });
});

describe('PickingLineScanScreen: quantity unit follows the line picking unit from the JSON', () => {
  it('a TU line shows the TU caption and the packing item name', () => {
    expect(computeQtyUnitProps({ pickingUnit: 'TU', packingItemName: 'TU x 4 Stk' })).toEqual({
      qtyCaptionKey: 'general.QtyTU',
      packingItemName: 'TU x 4 Stk',
    });
  });

  it('a label-required-mode line that the backend reports in CU shows the CU caption and no packing item name', () => {
    expect(
      computeQtyUnitProps({
        pickingUnit: 'CU',
        packingItemName: 'TU x 4 Stk',
      })
    ).toEqual({ qtyCaptionKey: 'general.Qty', packingItemName: null });
  });
});

describe('PickingLineScanScreen: re-entry hop', () => {
  it('a CU quantity entry mode never reads the source HU of the hop as a scan', () => {
    expect(
      computeScannedBarcodeParam({ qrCode: 'HU#1#{}', cuQtyEntryPolicy: POLICY.SCAN_ONCE_ENTER_QTY })
    ).toBeUndefined();
    expect(computeScannedBarcodeParam({ qrCode: 'HU#1#{}', cuQtyEntryPolicy: POLICY.SCAN_EACH_CU })).toBeUndefined();
  });

  it('Manual and an absent mode keep the param', () => {
    expect(computeScannedBarcodeParam({ qrCode: 'HU#1#{}', cuQtyEntryPolicy: POLICY.MANUAL })).toBe('HU#1#{}');
    expect(computeScannedBarcodeParam({ qrCode: 'HU#1#{}' })).toBe('HU#1#{}');
    expect(computeScannedBarcodeParam({ qrCode: null })).toBeNull();
  });
});

describe('PickingLineScanScreen: the open TU is shown in the header', () => {
  it('a label-required-mode line with an open TU shows its caption', () => {
    expect(computeOpenTUHeaderEntries({ isLabelRequiredLine: true, tuPickingTarget: { caption: 'Kiste 7' } })).toEqual([
      { caption: expect.any(String), value: 'Kiste 7', valueTestId: 'openTU-caption' },
    ]);
  });

  it('the caption of the entry is translated in German (no English text for German operators)', () => {
    counterpart.registerTranslations('de', translations_de);
    counterpart.setLocale('de');
    try {
      const [entry] = computeOpenTUHeaderEntries({
        isLabelRequiredLine: true,
        tuPickingTarget: { caption: 'Kiste 7' },
      });
      expect(entry.caption).not.toBe('Current');
      expect(entry.caption).toBe('Offene Kiste');
    } finally {
      counterpart.setLocale('en');
    }
  });

  it('no open TU: no entry', () => {
    expect(computeOpenTUHeaderEntries({ isLabelRequiredLine: true, tuPickingTarget: null })).toEqual([]);
  });

  it('Manual line: no entry, header unchanged', () => {
    expect(computeOpenTUHeaderEntries({ isLabelRequiredLine: false, tuPickingTarget: { caption: 'x' } })).toEqual([]);
  });
});

describe('PickingLineScanScreen: re-entry with the remembered label', () => {
  const cuEntry = { label: { cuLabel: 'RAW-A', lotNo: 'L1' }, lastTypedQty: 5 };

  it('reopens the quantity block only on the hop back from the line screen', () => {
    expect(computeRememberedCULabel({ isScanOnce: true, isResume: true, cuEntry })).toEqual(cuEntry.label);
    expect(computeRememberedCULabel({ isScanOnce: true, isResume: false, cuEntry })).toBeUndefined();
    expect(computeRememberedCULabel({ isScanOnce: false, isResume: true, cuEntry })).toBeUndefined();
  });
});

describe('PickingLineScanScreen: the HU the PICK event takes the CUs from', () => {
  const sourceHUQRCode = 'HU#1#{"id":"source"}';
  const label = 'RAW-CU-LABEL';

  it('a CU quantity entry mode picks from the "Kommissionieren aus" HU, never from the scanned label', () => {
    expect(
      computePickFromHUQRCode({ cuQtyEntryPolicy: POLICY.SCAN_ONCE_ENTER_QTY, sourceHUQRCode, scannedBarcode: label })
    ).toBe(sourceHUQRCode);
    expect(
      computePickFromHUQRCode({ cuQtyEntryPolicy: POLICY.SCAN_EACH_CU, sourceHUQRCode, scannedBarcode: label })
    ).toBe(sourceHUQRCode);
  });

  it('Manual and an absent mode: the scanned code is the pick-from HU', () => {
    expect(
      computePickFromHUQRCode({ cuQtyEntryPolicy: POLICY.MANUAL, sourceHUQRCode, scannedBarcode: 'HU#1#{}' })
    ).toBe('HU#1#{}');
    expect(computePickFromHUQRCode({ sourceHUQRCode, scannedBarcode: 'HU#1#{}' })).toBe('HU#1#{}');
  });

  it('the source HU comes from the screen URL, else from the job\'s "Kommissionieren aus" HU', () => {
    const activity = { dataStored: { pickFromHU: { code: 'HU#1#{"id":"job"}' } } };
    expect(computeSourceHUQRCode({ qrCode: sourceHUQRCode, activity })).toBe(sourceHUQRCode);
    expect(computeSourceHUQRCode({ qrCode: null, activity })).toBe('HU#1#{"id":"job"}');
    expect(computeSourceHUQRCode({ qrCode: undefined, activity: { dataStored: {} } })).toBeUndefined();
    expect(computeSourceHUQRCode({ qrCode: null, activity: undefined })).toBeUndefined();
  });

  it('a label-required-mode pick without any source HU is refused with the pick-from message, nothing is posted', () => {
    [POLICY.SCAN_EACH_CU, POLICY.SCAN_ONCE_ENTER_QTY].forEach((cuQtyEntryPolicy) => {
      expect(() =>
        computePickFromHUQRCode({ cuQtyEntryPolicy, sourceHUQRCode: undefined, scannedBarcode: label })
      ).toThrow(expect.objectContaining({ messageKey: 'picking.pickFromRequired' }));
    });
  });

  it('Manual without a source HU: no refusal', () => {
    expect(computePickFromHUQRCode({ cuQtyEntryPolicy: POLICY.MANUAL, scannedBarcode: 'HU#1#{}' })).toBe('HU#1#{}');
  });
});

describe('PickingLineScanScreen: no-source refusal reaches the toast', () => {
  it('a label-required-mode pick without a source HU rejects (so callers .catch it into the toast), shows the message, posts nothing', async () => {
    setupCounterpart();
    jest.spyOn(toast, 'error').mockImplementation(mockToastError);
    jest.spyOn(console, 'log').mockImplementation(() => {});
    jest.spyOn(console, 'trace').mockImplementation(() => {});

    let postQty;
    const Harness = () => {
      postQty = usePostQtyPicked({ wfProcessId: 'wf', activityId: 'a', cuQtyEntryPolicy: POLICY.SCAN_ONCE_ENTER_QTY });
      return null;
    };
    render(<Harness />);

    // same shape as the callers: onQtyChange(payload)?.catch?.(toastErrorFromObj)
    const error = await postQty({ qty: 3, scannedBarcode: 'x', barcodeType: undefined }).then(
      () => null,
      (e) => e
    );
    expect(error).toMatchObject({ name: 'UserMessageError', messageKey: 'picking.pickFromRequired' });
    toastErrorFromObj(error);

    expect(mockDispatch).not.toHaveBeenCalled();
    expect(mockToastError).toHaveBeenCalledTimes(1);
    const toastNode = mockToastError.mock.calls[0][0];
    expect(toastNode.props.message).toBe("Please scan 'Pick From' first.");
  });
});

describe('PickingLineScanScreen: Manual product mismatch stays synchronous', () => {
  it('a code of another product throws at the call (not a rejected promise), so the catch-weight scanner gives the error beep; nothing is posted', () => {
    jest.spyOn(console, 'log').mockImplementation(() => {});
    mockDispatch.mockClear();

    let postQty;
    const Harness = () => {
      postQty = usePostQtyPicked({
        wfProcessId: 'wf',
        activityId: 'a',
        expectedProductNo: 'P1',
        cuQtyEntryPolicy: POLICY.MANUAL,
      });
      return null;
    };
    render(<Harness />);

    let returned;
    expect(() => {
      returned = postQty({ qty: 1, scannedBarcode: 'x', productNo: 'OTHER', catchWeight: 5, isDone: false });
      returned?.catch?.(() => {});
    }).toThrow(expect.objectContaining({ messageKey: 'activities.picking.qrcode.differentProduct' }));
    expect(returned).toBeUndefined();
    expect(mockDispatch).not.toHaveBeenCalled();
  });
});
