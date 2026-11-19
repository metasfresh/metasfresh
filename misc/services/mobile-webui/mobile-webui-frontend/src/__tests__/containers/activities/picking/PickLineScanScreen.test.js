import React from 'react';
import { render } from '@testing-library/react';
import { toast } from 'react-toastify';
import { setupCounterpart } from '../../../../utils/translations';
import { toastErrorFromObj } from '../../../../utils/toast';
import {
  usePostQtyPicked,
  convertScannedBarcodeToResolvedResult,
  convertScannedCULabelToResolvedResult,
  isInlineGraiCaptureEnabled,
  computeQtyUnitProps,
  computeScannedBarcodeParam,
  computeOpenBoxHeaderEntries,
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
  it('convertScannedBarcodeToResolvedResult', async () => {
    const result = await convertScannedBarcodeToResolvedResult({
      scannedBarcode:
        'HU#1#{"id":"59c0e3b5845d9fca58ccd9f2a906-30926","packingInfo":{"huUnitType":"V","packingInstructionsId":101,"caption":"No Packing Item"},"product":{"id":2009297,"code":"MyCode","name":"MyProduct"},"attributes":[{"code":"HU_BestBeforeDate","displayName":"Mindesthaltbarkeit","value":"2024-04-10"},{"code":"Lot-Nummer","displayName":"Lot-Nummer","value":"010124"},{"code":"WeightNet","displayName":"Gewicht Netto","value":"180.000"}]}',
      expectedProductId: '2009297',
    });

    expect(result).toEqual({
      bestBeforeDate: '2024-04-10',
      catchWeight: 180,
      lotNo: '010124',
      qrCode: expect.objectContaining({
        barcodeType: 'HU',
        huUnitType: 'V',
        productId: '2009297',
        weightNet: 180,
        lotNo: '010124',
        bestBeforeDate: '2024-04-10',
      }),
      scannedHU: { huUnitType: 'V' },
    });
  });
});

describe('PickingLineScanScreen: new mode label scan', () => {
  const GS = '\u001d';
  const label = `0109506000134352` + `91PTST001${GS}17261107` + `10LA0000001${GS}922`;
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
      constant(27, 28, '17'),
      constant(35, 36, '10'),
      { type: 'PRODUCT_CODE', startPosition: 19, endPosition: 25 },
      { type: 'BEST_BEFORE_DATE', startPosition: 29, endPosition: 34, dateFormat: 'yyMMdd' },
      { type: 'LOT', startPosition: 37, endPosition: 45 },
    ],
  };

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
});

describe('PickingLineScanScreen: pick-time GRAI capture', () => {
  it('stays on for Manual (today) and is switched off in both new modes', () => {
    const base = { graiScanEnabled: true, tuPickingTarget: null };
    expect(isInlineGraiCaptureEnabled({ ...base, cuQtyEntryMode: undefined })).toBe(true);
    expect(isInlineGraiCaptureEnabled({ ...base, cuQtyEntryMode: 'MANUAL' })).toBe(true);
    expect(isInlineGraiCaptureEnabled({ ...base, cuQtyEntryMode: 'SCAN_EACH_CU' })).toBe(false);
    expect(isInlineGraiCaptureEnabled({ ...base, cuQtyEntryMode: 'SCAN_ONCE_ENTER_QTY' })).toBe(false);
  });

  it('keeps today rule for Manual: off when GRAI scanning is not required or a TU target is set', () => {
    expect(isInlineGraiCaptureEnabled({ graiScanEnabled: false, tuPickingTarget: null })).toBe(false);
    expect(isInlineGraiCaptureEnabled({ graiScanEnabled: true, tuPickingTarget: { caption: 'x' } })).toBe(false);
  });
});

describe('PickingLineScanScreen: GRAI scan on a new-mode line', () => {
  const A_GRAI = '1234567.12345.SERIAL1';

  it('opens the box from the GRAI and does not read it as a label', async () => {
    const onGrai = jest.fn().mockResolvedValue(undefined);
    const result = await convertScannedCULabelToResolvedResult({
      scannedBarcode: A_GRAI,
      cuLabelFormat: undefined,
      expectedProductNo: 'P1',
      onGrai,
    });
    expect(onGrai).toHaveBeenCalledWith(A_GRAI);
    expect(result).toEqual({ isNextBoxOpened: true });
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
      ).resolves.toEqual({ isNextBoxOpened: true });
    }
  });

  it('a failing box opening is reported, not swallowed', async () => {
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

  it('a new-mode line that the backend reports in CU shows the CU caption and no packing item name', () => {
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
    expect(computeScannedBarcodeParam({ qrCode: 'HU#1#{}', cuQtyEntryMode: 'SCAN_ONCE_ENTER_QTY' })).toBeUndefined();
    expect(computeScannedBarcodeParam({ qrCode: 'HU#1#{}', cuQtyEntryMode: 'SCAN_EACH_CU' })).toBeUndefined();
  });

  it('Manual and an absent mode keep the param', () => {
    expect(computeScannedBarcodeParam({ qrCode: 'HU#1#{}', cuQtyEntryMode: 'MANUAL' })).toBe('HU#1#{}');
    expect(computeScannedBarcodeParam({ qrCode: 'HU#1#{}' })).toBe('HU#1#{}');
    expect(computeScannedBarcodeParam({ qrCode: null })).toBeNull();
  });
});

describe('PickingLineScanScreen: the open box is shown in the header', () => {
  it('a new-mode line with an open box shows its caption', () => {
    expect(computeOpenBoxHeaderEntries({ isNewModeLine: true, tuPickingTarget: { caption: 'Kiste 7' } })).toEqual([
      { caption: expect.any(String), value: 'Kiste 7' },
    ]);
  });

  it('no open box: no entry', () => {
    expect(computeOpenBoxHeaderEntries({ isNewModeLine: true, tuPickingTarget: null })).toEqual([]);
  });

  it('Manual line: no entry, header unchanged', () => {
    expect(computeOpenBoxHeaderEntries({ isNewModeLine: false, tuPickingTarget: { caption: 'x' } })).toEqual([]);
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

describe('PickingLineScanScreen: the HU the PICK event takes the packs from', () => {
  const sourceHUQRCode = 'HU#1#{"id":"source"}';
  const label = 'RAW-CU-LABEL';

  it('a CU quantity entry mode picks from the "Kommissionieren aus" HU, never from the scanned label', () => {
    expect(
      computePickFromHUQRCode({ cuQtyEntryMode: 'SCAN_ONCE_ENTER_QTY', sourceHUQRCode, scannedBarcode: label })
    ).toBe(sourceHUQRCode);
    expect(computePickFromHUQRCode({ cuQtyEntryMode: 'SCAN_EACH_CU', sourceHUQRCode, scannedBarcode: label })).toBe(
      sourceHUQRCode
    );
  });

  it('Manual and an absent mode keep today: the scanned code is the pick-from HU', () => {
    expect(computePickFromHUQRCode({ cuQtyEntryMode: 'MANUAL', sourceHUQRCode, scannedBarcode: 'HU#1#{}' })).toBe(
      'HU#1#{}'
    );
    expect(computePickFromHUQRCode({ sourceHUQRCode, scannedBarcode: 'HU#1#{}' })).toBe('HU#1#{}');
  });

  it('the source HU comes from the screen URL, else from the job\'s "Kommissionieren aus" HU', () => {
    const activity = { dataStored: { pickFromHU: { code: 'HU#1#{"id":"job"}' } } };
    expect(computeSourceHUQRCode({ qrCode: sourceHUQRCode, activity })).toBe(sourceHUQRCode);
    expect(computeSourceHUQRCode({ qrCode: null, activity })).toBe('HU#1#{"id":"job"}');
    expect(computeSourceHUQRCode({ qrCode: undefined, activity: { dataStored: {} } })).toBeUndefined();
    expect(computeSourceHUQRCode({ qrCode: null, activity: undefined })).toBeUndefined();
  });

  it('a new-mode pick without any source HU is refused with the pick-from message, nothing is posted', () => {
    ['SCAN_EACH_CU', 'SCAN_ONCE_ENTER_QTY'].forEach((cuQtyEntryMode) => {
      expect(() =>
        computePickFromHUQRCode({ cuQtyEntryMode, sourceHUQRCode: undefined, scannedBarcode: label })
      ).toThrow(expect.objectContaining({ messageKey: 'picking.pickFromRequired' }));
    });
  });

  it('Manual without a source HU is unchanged (no refusal)', () => {
    expect(computePickFromHUQRCode({ cuQtyEntryMode: 'MANUAL', scannedBarcode: 'HU#1#{}' })).toBe('HU#1#{}');
  });
});

describe('PickingLineScanScreen: no-source refusal reaches the toast', () => {
  it('a new-mode pick without a source HU rejects (so callers .catch it into the toast), shows the message, posts nothing', async () => {
    setupCounterpart();
    jest.spyOn(toast, 'error').mockImplementation(mockToastError);
    jest.spyOn(console, 'log').mockImplementation(() => {});
    jest.spyOn(console, 'trace').mockImplementation(() => {});

    let postQty;
    const Harness = () => {
      postQty = usePostQtyPicked({ wfProcessId: 'wf', activityId: 'a', cuQtyEntryMode: 'SCAN_ONCE_ENTER_QTY' });
      return null;
    };
    render(<Harness />);

    // same shape as the callers: onQtyChange(payload)?.catch?.(toastErrorFromObj)
    const error = await postQty({ qty: 3, scannedBarcode: 'x', barcodeType: undefined }).then(
      () => null,
      (e) => e
    );
    expect(error).toEqual({ messageKey: 'picking.pickFromRequired' });
    toastErrorFromObj(error);

    expect(mockDispatch).not.toHaveBeenCalled();
    expect(mockToastError).toHaveBeenCalledTimes(1);
    const toastNode = mockToastError.mock.calls[0][0];
    expect(toastNode.props.message).toBe("Please scan 'Pick From' first.");
  });
});
