import { extractUniqueKey } from '../../../utils/documentReferencesHelper';

describe('extractUniqueKey', () => {
    it('int targetWindowId without category', () => {
    const key = extractUniqueKey({ targetWindowId: 123});
    expect(key).toEqual('123');
  });

    it('string targetWindowId without category', () => {
        const key = extractUniqueKey({ targetWindowId: '123W'});
        expect(key).toEqual('123W');
    });

    it('string targetWindowId with category', () => {
        const key = extractUniqueKey({ targetWindowId: '123W', targetCategory: 'CAT1'});
        expect(key).toEqual('123W_CAT1');
    });
});
