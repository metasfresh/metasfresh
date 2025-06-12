import { parseQRCodeString, toQRCodeDisplayable, toQRCodeObject, toQRCodeString } from '../../../utils/qrCode/hu';
import { setupCounterpart } from '../../../utils/translations';

describe('huQRCodes tests', () => {
  describe('toQRCodeDisplayable', () => {
    it('qrCode object', () => {
      expect(toQRCodeDisplayable({ code: 'the code', displayable: 'the displayable' })).toBe('the displayable');
    });
    it('qrCode object with missing displayable value', () => {
      expect(
        toQRCodeDisplayable({
          code: 'HU#1#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU","packingInstructionsId":1000006,"caption":"Euro Palette"},"product":{"id":1000001,"code":"2680","name":"Sternflow 11 Raps"},"attributes":[]}',
        })
      ).toEqual('05650');
    });
    it('qrCode string', () => {
      expect(
        toQRCodeDisplayable(
          'HU#1#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU","packingInstructionsId":1000006,"caption":"Euro Palette"},"product":{"id":1000001,"code":"2680","name":"Sternflow 11 Raps"},"attributes":[]}'
        )
      ).toEqual('05650');
    });
  });
  describe('toQRCodeString', () => {
    it('qrCode object', () => {
      expect(toQRCodeString({ code: 'the code', displayable: 'the displayable' })).toBe('the code');
    });
    it('qrCode string', () => {
      expect(toQRCodeString('string QR code')).toEqual('string QR code');
    });
  });
  describe('toQRCodeObject', () => {
    it('qrCode object', () => {
      expect(toQRCodeObject({ code: 'the code', displayable: 'the displayable' })).toEqual({
        code: 'the code',
        displayable: 'the displayable',
      });
    });
    it('qrCode string', () => {
      expect(
        toQRCodeObject(
          'HU#1#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU","packingInstructionsId":1000006,"caption":"Euro Palette"},"product":{"id":1000001,"code":"2680","name":"Sternflow 11 Raps"},"attributes":[]}'
        )
      ).toEqual({
        code: 'HU#1#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU","packingInstructionsId":1000006,"caption":"Euro Palette"},"product":{"id":1000001,"code":"2680","name":"Sternflow 11 Raps"},"attributes":[]}',
        displayable: '05650',
      });
    });
  });
  describe('parseQRCode', () => {
    it('wrong code', () => {
      setupCounterpart();
      expect(() => parseQRCodeString('983u2r982foijklscdskdshvd')).toThrow(/Invalid QR Code/);
      expect(() => parseQRCodeString('zzzzzz')).toThrow(/Invalid QR Code/);
      expect(() => parseQRCodeString('    ')).toThrow(/Invalid QR Code/);
      expect(() => parseQRCodeString('')).toThrow(/Invalid QR Code/);
      expect(() => parseQRCodeString(null)).toThrow(/Invalid QR Code/);
    });
    it('wrong code, bug do not fail', () => {
      setupCounterpart();
      expect(parseQRCodeString('983u2r982foijklscdskdshvd', true)).toEqual(false);
      expect(parseQRCodeString('zzzzzzz', true)).toEqual(false);
      expect(parseQRCodeString('     ', true)).toEqual(false);
      expect(parseQRCodeString('', true)).toEqual(false);
      expect(parseQRCodeString(null, true)).toEqual(false);
    });
    it('unknown type', () => {
      setupCounterpart();
      const code =
        'UNKNOWN_TYPE#1#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU","packingInstructionsId":1000006,"caption":"Euro Palette"},"product":{"id":1000001,"code":"2680","name":"Sternflow 11 Raps"},"attributes":[]}';
      expect(() => parseQRCodeString(code)).toThrow(/Invalid QR Code/);
    });
    it('unknown version', () => {
      setupCounterpart();
      const code =
        'HU#UNKNOWN_VERSION#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU","packingInstructionsId":1000006,"caption":"Euro Palette"},"product":{"id":1000001,"code":"2680","name":"Sternflow 11 Raps"},"attributes":[]}';
      expect(() => parseQRCodeString(code)).toThrow(/Invalid QR Code/);
    });
    it('unknown version, but do not fail', () => {
      setupCounterpart();
      const code =
        'HU#UNKNOWN_VERSION#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU","packingInstructionsId":1000006,"caption":"Euro Palette"},"product":{"id":1000001,"code":"2680","name":"Sternflow 11 Raps"},"attributes":[]}';
      expect(parseQRCodeString(code, true)).toEqual(false);
    });
    it('standard test', () => {
      const code =
        'HU#1#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU","packingInstructionsId":1000006,"caption":"Euro Palette"},"product":{"id":1000001,"code":"2680","name":"Sternflow 11 Raps"},"attributes":[]}';
      expect(parseQRCodeString(code)).toEqual({ code, barcodeType: 'HU', displayable: '05650', productId: '1000001' });
    });
    it('QR code with attributes', () => {
      const code =
        'HU#1#{"id":"c9f94fa0d4268d63d86497b407ba-28193","packingInfo":{"huUnitType":"V","packingInstructionsId":101,"caption":"No Packing Item"},"product":{"id":2005577,"code":"P002737","name":"Convenience Salat 250g"},"attributes":[{"code":"HU_BestBeforeDate","displayName":"Mindesthaltbarkeit"},{"code":"Lot-Nummer","displayName":"Lot-Nummer"},{"code":"WeightNet","displayName":"Gewicht Netto","value":"2427.425"}]}';
      expect(parseQRCodeString(code)).toEqual({
        code,
        barcodeType: 'HU',
        displayable: '28193',
        productId: '2005577',
        weightNet: 2427.425,
      });
    });
    describe('Leich+Mehl', () => {
      it('standard', () => {
        // IMPORTANT: keep in sync with de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCodeTest
        const code = 'LMQ#1#123.456#13.12.2024#lot3';
        expect(parseQRCodeString(code)).toEqual({
          code,
          barcodeType: 'LMQ',
          displayable: '123.456',
          weightNet: 123.456,
          weightNetUOM: 'kg',
          isTUToBePickedAsWhole: true,
          bestBeforeDate: '2024-12-13',
          lotNo: 'lot3',
        });
      });
      it('with productNo', () => {
        // IMPORTANT: keep in sync with de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCodeTest
        const code = 'LMQ#1#123.456#13.12.2024#lot3#productNo88';
        expect(parseQRCodeString(code)).toEqual({
          code,
          barcodeType: 'LMQ',
          displayable: '123.456',
          weightNet: 123.456,
          weightNetUOM: 'kg',
          isTUToBePickedAsWhole: true,
          bestBeforeDate: '2024-12-13',
          lotNo: 'lot3',
          productNo: 'productNo88',
        });
      });
    });
    describe('GS1-128', () => {
      it('standard test', () => {
        const code = '019731187634181131030075201527080910501';
        expect(parseQRCodeString(code)).toEqual({
          code,
          barcodeType: 'GS1',
          displayable: '7.52 kg',
          weightNet: 7.52,
          weightNetUOM: 'kg',
          isTUToBePickedAsWhole: true,
          GTIN: '97311876341811',
          bestBeforeDate: '2027-08-09',
          lotNo: '501',
        });
      });
    });
    describe('EAN13', () => {
      it('standard test', () => {
        const code = '2859414004825';
        expect(parseQRCodeString(code)).toEqual({
          code,
          barcodeType: 'EAN13',
          displayable: '0.482 kg',
          productNo: '59414',
          weightNet: 0.482,
          weightNetUOM: 'kg',
          isTUToBePickedAsWhole: true,
        });
      });
    });
    describe('Custom QR Code format', () => {
      describe('custom format with constant markers', () => {
        const format = {
          name: 'my custom code',
          parts: [
            { startPosition: 1, endPosition: 1, type: 'CONSTANT', constantValue: 'A' },
            { startPosition: 2, endPosition: 7, type: 'PRODUCT_CODE' },
            { startPosition: 8, endPosition: 8, type: 'CONSTANT', constantValue: 'G' },
            { startPosition: 9, endPosition: 14, type: 'WEIGHT_KG', decimalPointPosition: 3 },
            { startPosition: 15, endPosition: 15, type: 'CONSTANT', constantValue: 'C' },
            { startPosition: 16, endPosition: 23, type: 'LOT' },
            { startPosition: 24, endPosition: 24, type: 'CONSTANT', constantValue: 'E' },
            { startPosition: 25, endPosition: 30, type: 'IGNORE' },
          ],
        };

        it('happy case', () => {
          expect(
            parseQRCodeString({
              string: 'A593707G000384C05321124E000001',
              customQRCodeFormats: [format],
              returnFalseOnError: false,
            })
          ).toEqual({
            code: 'A593707G000384C05321124E000001',
            barcodeType: 'CUSTOM',
            displayable: '0.384 kg',
            productNo: '593707',
            weightNet: 0.384,
            weightNetUOM: 'kg',
            lotNo: '5321124',
          });
        });

        it('invalid maker', () => {
          expect(() =>
            parseQRCodeString({
              string: 'A593707G000384C05321124X000001', // remark it's X instead of E
              customQRCodeFormats: [format],
              returnFalseOnError: false,
            })
          ).toThrow();
        });
      });

      it('custom format with productNo, weight, lot and best before date', () => {
        const format = {
          name: 'my custom code',
          parts: [
            { startPosition: 1, endPosition: 4, type: 'PRODUCT_CODE' },
            { startPosition: 5, endPosition: 10, type: 'WEIGHT_KG', decimalPointPosition: 3 },
            { startPosition: 11, endPosition: 18, type: 'LOT' },
            { startPosition: 19, endPosition: 24, type: 'IGNORE' },
            { startPosition: 25, endPosition: 30, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
          ],
        };
        let result = parseQRCodeString({
          string: '100009999900000123250403260410',
          customQRCodeFormats: [format],
          returnFalseOnError: false,
        });
        console.log('result: ', result);
        expect(result).toEqual({
          code: '100009999900000123250403260410',
          barcodeType: 'CUSTOM',
          displayable: '99.999 kg',
          productNo: '1000',
          weightNet: 99.999,
          weightNetUOM: 'kg',
          lotNo: '123',
          bestBeforeDate: '2026-04-10',
        });
      });
    });
  });
});
