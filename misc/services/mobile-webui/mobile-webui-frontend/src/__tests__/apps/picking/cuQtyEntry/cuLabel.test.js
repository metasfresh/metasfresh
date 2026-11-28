import {
  readCULabel,
  resolveCULabel,
  routeScannedCode,
  selectCULabelFormat,
} from '../../../../apps/picking/cuQtyEntry/cuLabel';

describe('cuLabel.selectCULabelFormat', () => {
  const f1 = { id: 11, name: 'A', parts: [] };
  const f2 = { id: 22, name: 'B', parts: [] };
  const noId = { name: 'C', parts: [] };

  it('selects the format with the given id', () => {
    expect(selectCULabelFormat([f1, f2], 22)).toBe(f2);
  });

  it('returns undefined when the id is null or undefined (no effective format)', () => {
    expect(selectCULabelFormat([f1, f2], null)).toBeUndefined();
    expect(selectCULabelFormat([f1, f2], undefined)).toBeUndefined();
  });

  it('returns undefined when no format has the id', () => {
    expect(selectCULabelFormat([f1, f2], 33)).toBeUndefined();
  });

  it('ignores formats without id and tolerates missing lists', () => {
    expect(selectCULabelFormat([noId, f1], 11)).toBe(f1);
    expect(selectCULabelFormat(undefined, 11)).toBeUndefined();
    expect(selectCULabelFormat(null, 11)).toBeUndefined();
  });
});

const GS = '\u001d';
// the string the scanner sends (DataWedge without control characters): no separators, the pack ID starts at 47
const LABEL = `0109506000134352` + `91PTST001` + `17261107` + `10LA0000001` + `922`;
// the same label from a scanner that sends the GS separator (char 29) after the product and after the lot
const LABEL_GS = `0109506000134352` + `91PTST001${GS}17261107` + `10LA0000001${GS}922`;
const constant = (startPosition, endPosition, constantValue) => ({
  type: 'CONSTANT',
  startPosition,
  endPosition,
  constantValue,
});
const markerParts = [constant(1, 2, '01'), constant(17, 18, '91'), constant(26, 27, '17'), constant(34, 35, '10')];
const markerPartsGS = [constant(1, 2, '01'), constant(17, 18, '91'), constant(27, 28, '17'), constant(35, 36, '10')];
const dataParts = [
  { type: 'PRODUCT_CODE', startPosition: 19, endPosition: 25 },
  { type: 'BEST_BEFORE_DATE', startPosition: 28, endPosition: 33, dateFormat: 'yyMMdd' },
  { type: 'LOT', startPosition: 36, endPosition: 44 },
];
const dataPartsGS = [
  { type: 'PRODUCT_CODE', startPosition: 19, endPosition: 25 },
  { type: 'BEST_BEFORE_DATE', startPosition: 29, endPosition: 34, dateFormat: 'yyMMdd' },
  { type: 'LOT', startPosition: 37, endPosition: 45 },
];
const format = { id: 7, name: 'CU label', parts: [...markerParts, ...dataParts] };

describe('cuLabel.routeScannedCode', () => {
  it('routes a canonical GRAI to GRAI', () => {
    expect(routeScannedCode('9506000.13435.SER1')).toBe('GRAI');
  });

  it('routes a GS1 AI 8003 string to GRAI', () => {
    expect(routeScannedCode('80030950600013435' + '2SER1')).toBe('GRAI');
  });

  it('routes a CU label, an empty string and null to CU_LABEL', () => {
    expect(routeScannedCode(LABEL)).toBe('CU_LABEL');
    expect(routeScannedCode('')).toBe('CU_LABEL');
    expect(routeScannedCode(null)).toBe('CU_LABEL');
  });
});

describe('cuLabel.readCULabel', () => {
  it('reads product, lot and best-before with the given format', () => {
    expect(readCULabel({ string: LABEL, format })).toEqual({
      productNo: 'PTST001',
      lotNo: 'LA0000001',
      bestBeforeDate: '2026-11-07',
      packId: undefined,
    });
  });

  it('reports noFormat when the line has no effective format', () => {
    expect(readCULabel({ string: LABEL, format: undefined })).toEqual({ errorKey: 'picking.cuLabel.noFormat' });
  });

  it('reports formatMismatch when a marker is missing (prefix of another code)', () => {
    expect(readCULabel({ string: 'HU#1#{"id":"x"}', format })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
    expect(readCULabel({ string: LABEL.replace('91PTST001', '99PTST001'), format })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
  });

  it('reports formatMismatch for a label with a symbology prefix (positions shift)', () => {
    expect(readCULabel({ string: ']d2' + LABEL, format })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
  });

  it('reports formatMismatch for a label shorter than the format', () => {
    expect(readCULabel({ string: LABEL.substring(0, 30), format })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
  });

  it('uses only the given format: neither GS1, EAN13 nor the global QR code are tried', () => {
    const globalQr = 'HU#1#{"id":"59c0e3b5845d9fca58ccd9f2a906-30926","packingInfo":{"huUnitType":"V"}}';
    expect(readCULabel({ string: globalQr, format })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
    expect(readCULabel({ string: '9506000134352', format })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
  });
});

describe('cuLabel.resolveCULabel', () => {
  it('returns the label data and the raw label when the product matches', () => {
    expect(resolveCULabel({ string: LABEL, format, expectedProductNo: 'PTST001' })).toEqual({
      productNo: 'PTST001',
      lotNo: 'LA0000001',
      bestBeforeDate: '2026-11-07',
      packId: undefined,
      cuLabel: LABEL,
    });
  });

  it('refuses another product with the differentProduct key', () => {
    expect(resolveCULabel({ string: LABEL, format, expectedProductNo: 'OTHER01' })).toEqual({
      errorKey: 'activities.picking.qrcode.differentProduct',
    });
  });

  it('passes the read errors through', () => {
    expect(resolveCULabel({ string: LABEL, format: undefined, expectedProductNo: 'PTST001' })).toEqual({
      errorKey: 'picking.cuLabel.noFormat',
    });
  });
});

describe('cuLabel.readCULabel with a PackId part', () => {
  const packIdFormat = {
    id: 8,
    name: 'CU label with pack ID',
    parts: [...markerParts, ...dataParts, constant(45, 46, '92'), { type: 'PACK_ID', startPosition: 47 }],
  };

  it('reads the pack ID (open end), also with several digits', () => {
    expect(readCULabel({ string: LABEL, format: packIdFormat })).toEqual({
      productNo: 'PTST001',
      lotNo: 'LA0000001',
      bestBeforeDate: '2026-11-07',
      packId: '2',
    });
    expect(readCULabel({ string: LABEL + '0', format: packIdFormat }).packId).toBe('20');
  });

  it('a label ending before the "92" marker or the pack ID part is a format mismatch, whether or not a pack ID is required (L0)', () => {
    const beforeMarker = LABEL.substring(0, 44);
    const markerOnly = LABEL.substring(0, 46);
    for (const string of [beforeMarker, markerOnly]) {
      expect(readCULabel({ string, format: packIdFormat, isPackIdRequired: true })).toEqual({
        errorKey: 'picking.cuLabel.formatMismatch',
        errorDetails: expect.any(String),
      });
      expect(readCULabel({ string, format: packIdFormat })).toEqual({
        errorKey: 'picking.cuLabel.formatMismatch',
        errorDetails: expect.any(String),
      });
    }
  });

  it('no PackId part: a cut-off label is a format mismatch', () => {
    const noZoneFormat = {
      ...packIdFormat,
      parts: [...markerParts, ...dataParts, constant(45, 46, '92'), constant(50, 51, '93')],
    };
    expect(readCULabel({ string: LABEL.substring(0, 47), format: noZoneFormat, isPackIdRequired: true })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
    expect(readCULabel({ string: LABEL.substring(0, 44), format: noZoneFormat, isPackIdRequired: true })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
  });

  it('a label ending before the lot is a format mismatch even when a pack ID is required', () => {
    expect(readCULabel({ string: LABEL.substring(0, 35), format: packIdFormat, isPackIdRequired: true })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
  });

  it('a label ending before the pack ID part is a format mismatch whatever the marker is', () => {
    const otherMarkerFormat = {
      ...packIdFormat,
      parts: [...markerParts, ...dataParts, constant(45, 46, '93'), { type: 'PACK_ID', startPosition: 47 }],
    };
    expect(readCULabel({ string: LABEL.substring(0, 44), format: otherMarkerFormat, isPackIdRequired: true })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
  });

  it('a format without a PackId part reads, but has no pack ID when one is required (effective format without PackId)', () => {
    expect(readCULabel({ string: LABEL, format, isPackIdRequired: true })).toEqual({
      errorKey: 'picking.cuLabel.noPackId',
    });
    expect(resolveCULabel({ string: LABEL, format, expectedProductNo: 'PTST001', isPackIdRequired: true })).toEqual({
      errorKey: 'picking.cuLabel.noPackId',
    });
  });

  it('resolveCULabel passes the pack ID with the label', () => {
    expect(
      resolveCULabel({ string: LABEL, format: packIdFormat, expectedProductNo: 'PTST001', isPackIdRequired: true })
    ).toEqual({
      productNo: 'PTST001',
      lotNo: 'LA0000001',
      bestBeforeDate: '2026-11-07',
      packId: '2',
      cuLabel: LABEL,
    });
  });
});

describe('cuLabel with a scanner that sends the GS separator (char 29)', () => {
  const formatGS = { id: 9, name: 'CU label with GS', parts: [...markerPartsGS, ...dataPartsGS] };
  const packIdFormatGS = {
    id: 10,
    name: 'CU label with GS and pack ID',
    parts: [...markerPartsGS, ...dataPartsGS, constant(47, 48, '92'), { type: 'PACK_ID', startPosition: 49 }],
  };

  it('reads product, lot and best-before with a format of the GS positions', () => {
    expect(readCULabel({ string: LABEL_GS, format: formatGS })).toEqual({
      productNo: 'PTST001',
      lotNo: 'LA0000001',
      bestBeforeDate: '2026-11-07',
      packId: undefined,
    });
  });

  it('reads the pack ID with a format of the GS positions', () => {
    expect(readCULabel({ string: LABEL_GS, format: packIdFormatGS }).packId).toBe('2');
  });

  it('the label without separators is a format mismatch for the format of the GS positions', () => {
    expect(readCULabel({ string: LABEL, format: formatGS })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
  });

  it('the label with GS separators is a format mismatch for the format of the real positions', () => {
    expect(readCULabel({ string: LABEL_GS, format })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
      errorDetails: expect.any(String),
    });
  });
});
