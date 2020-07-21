import { mergeReferencesToReferences } from '../../../utils/documentReferencesHelper';

describe('mergeReferencesToReferences', () => {
  it('add to empty array', () => {
    const existingReferences = [];
    const referencesToAdd = [ { targetWindowId: 'id1', caption: 'item 1', priority: 555 } ];
    const result = mergeReferencesToReferences(existingReferences, referencesToAdd);

    expect(result).toEqual(referencesToAdd);
  });

  it('add a non existing item', () => {
    const existingReferences = [
      { targetWindowId: '1', caption: 'A', priority: 555 },
      { targetWindowId: '3', caption: 'C', priority: 555 },
    ];
    const referencesToAdd = [ { targetWindowId: '2', caption: 'B', priority: 555, } ];
    const result = mergeReferencesToReferences(existingReferences, referencesToAdd);

    expect(result).toEqual([
      { targetWindowId: '1', caption: 'A', priority: 555 },
      { targetWindowId: '2', caption: 'B', priority: 555 },
      { targetWindowId: '3', caption: 'C', priority: 555 },
    ]);
  });

  it('add existing item but with higher priority', () => {
    const existingReferences = [
      { targetWindowId: '1', caption: 'A', priority: 555 },
      { targetWindowId: '2', caption: 'B', priority: 555 },
    ];
    const referencesToAdd = [ { targetWindowId: '1', caption: 'Z', priority: 0, } ];
    const result = mergeReferencesToReferences(existingReferences, referencesToAdd);

    expect(result).toEqual([
      { targetWindowId: '2', caption: 'B', priority: 555 },
      { targetWindowId: '1', caption: 'Z', priority: 0 },
    ]);
  });

  it('add existing item but with lower priority', () => {
    const existingReferences = [
      { targetWindowId: '1', caption: 'A', priority: 555 },
      { targetWindowId: '2', caption: 'B', priority: 555 },
    ];
    const referencesToAdd = [ { targetWindowId: '1', caption: 'Z', priority: 999 } ];
    const result = mergeReferencesToReferences(existingReferences, referencesToAdd);

    expect(result).toEqual(existingReferences);
  });
});
