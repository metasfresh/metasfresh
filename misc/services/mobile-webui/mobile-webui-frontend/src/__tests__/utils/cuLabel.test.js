import { readCULabel, resolveCULabel, routeScannedCode, selectCULabelFormat } from '../../utils/cuLabel';

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

//
// A label of the real shape (raw scanner form: AI digits, GS = \u001d, no symbology prefix):
// GTIN 09506000134352, product value (7), best-before 261107, lot (9), pack ID 2
const GS = '\u001d';
const LABEL = `0109506000134352` + `91PTST001${GS}17261107` + `10LA0000001${GS}922`;
const constant = (startPosition, endPosition, constantValue) => ({
  type: 'CONSTANT',
  startPosition,
  endPosition,
  constantValue,
});
const markerParts = [constant(1, 2, '01'), constant(17, 18, '91'), constant(27, 28, '17'), constant(35, 36, '10')];
const dataParts = [
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
    expect(readCULabel({ string: 'HU#1#{"id":"x"}', format })).toEqual({ errorKey: 'picking.cuLabel.formatMismatch' });
    expect(readCULabel({ string: LABEL.replace('91PTST001', '99PTST001'), format })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
    });
  });

  it('reports formatMismatch for a label with a symbology prefix (positions shift)', () => {
    expect(readCULabel({ string: ']d2' + LABEL, format })).toEqual({ errorKey: 'picking.cuLabel.formatMismatch' });
  });

  it('reports formatMismatch for a label shorter than the format', () => {
    expect(readCULabel({ string: LABEL.substring(0, 30), format })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
    });
  });

  it('uses only the given format: neither GS1, EAN13 nor the global QR code are tried', () => {
    const globalQr = 'HU#1#{"id":"59c0e3b5845d9fca58ccd9f2a906-30926","packingInfo":{"huUnitType":"V"}}';
    expect(readCULabel({ string: globalQr, format })).toEqual({ errorKey: 'picking.cuLabel.formatMismatch' });
    expect(readCULabel({ string: '9506000134352', format })).toEqual({ errorKey: 'picking.cuLabel.formatMismatch' });
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

  it('refuses another product with the existing differentProduct key', () => {
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
    parts: [...markerParts, ...dataParts, constant(47, 48, '92'), { type: 'PACK_ID', startPosition: 49 }],
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

  it('a label ending before the "92" marker or the pack ID part has no pack ID when one is required (L0)', () => {
    const beforeMarker = LABEL.substring(0, 46);
    const markerOnly = LABEL.substring(0, 48);
    for (const string of [beforeMarker, markerOnly]) {
      expect(readCULabel({ string, format: packIdFormat, requirePackId: true })).toEqual({
        errorKey: 'picking.cuLabel.noPackId',
      });
      expect(readCULabel({ string, format: packIdFormat })).toEqual({ errorKey: 'picking.cuLabel.formatMismatch' });
    }
  });

  // parity vector with the backend PickingCULabelServiceTest.fallbackMarkerWithGapBeforeNextPart_labelEndingInsideTheGap_isAFormatMismatch
  it('no PackId part, a gap between the "92" marker and the next part: the zone ends after the marker itself', () => {
    const gapFormat = {
      ...packIdFormat,
      parts: [...markerParts, ...dataParts, constant(47, 48, '92'), constant(52, 53, '93')],
    };
    expect(readCULabel({ string: LABEL.substring(0, 49), format: gapFormat, requirePackId: true })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
    });
    expect(readCULabel({ string: LABEL.substring(0, 46), format: gapFormat, requirePackId: true })).toEqual({
      errorKey: 'picking.cuLabel.noPackId',
    });
  });

  it('a label ending before the lot is a format mismatch even when a pack ID is required', () => {
    expect(readCULabel({ string: LABEL.substring(0, 36), format: packIdFormat, requirePackId: true })).toEqual({
      errorKey: 'picking.cuLabel.formatMismatch',
    });
  });

  it('the pack-ID zone is the part after its own marker, not a fixed "92"', () => {
    const otherMarkerFormat = {
      ...packIdFormat,
      parts: [...markerParts, ...dataParts, constant(47, 48, '93'), { type: 'PACK_ID', startPosition: 49 }],
    };
    expect(readCULabel({ string: LABEL.substring(0, 46), format: otherMarkerFormat, requirePackId: true })).toEqual({
      errorKey: 'picking.cuLabel.noPackId',
    });
  });

  it('a format without a PackId part reads, but has no pack ID when one is required (effective format without PackId)', () => {
    expect(readCULabel({ string: LABEL, format, requirePackId: true })).toEqual({
      errorKey: 'picking.cuLabel.noPackId',
    });
    expect(resolveCULabel({ string: LABEL, format, expectedProductNo: 'PTST001', requirePackId: true })).toEqual({
      errorKey: 'picking.cuLabel.noPackId',
    });
  });

  it('resolveCULabel passes the pack ID with the label', () => {
    expect(
      resolveCULabel({ string: LABEL, format: packIdFormat, expectedProductNo: 'PTST001', requirePackId: true })
    ).toEqual({
      productNo: 'PTST001',
      lotNo: 'LA0000001',
      bestBeforeDate: '2026-11-07',
      packId: '2',
      cuLabel: LABEL,
    });
  });
});
