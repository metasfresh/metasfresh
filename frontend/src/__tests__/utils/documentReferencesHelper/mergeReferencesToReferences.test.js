import { mergeReferencesToReferences } from '../../../utils/documentReferencesHelper';

describe('mergeReferencesToReferences', () => {
    it('add to empty array', () => {
        const existingReferences = [];
        const referencesToAdd = [ { id: 'id1', caption: 'item 1', priority: 555, } ];
        const result = mergeReferencesToReferences(existingReferences, referencesToAdd);

        expect(result).toEqual(referencesToAdd);
    });


    it('add a non existing item', () => {
        const existingReferences = [
            { id: '1', caption: 'A', priority: 555, },
            { id: '3', caption: 'C', priority: 555, },
        ];
        const referencesToAdd = [ { id: '2', caption: 'B', priority: 555, } ];
        const result = mergeReferencesToReferences(existingReferences, referencesToAdd);

        expect(result).toEqual([
            { id: '1', caption: 'A', priority: 555, },
            { id: '2', caption: 'B', priority: 555, },
            { id: '3', caption: 'C', priority: 555, },
        ]);
    });


    it('add existing item but with higher priority', () => {
        const existingReferences = [
            { id: '1', caption: 'A', priority: 555, },
            { id: '2', caption: 'B', priority: 555, },
        ];
        const referencesToAdd = [ { id: '1', caption: 'Z', priority: 0, } ];
        const result = mergeReferencesToReferences(existingReferences, referencesToAdd);

        expect(result).toEqual([
            { id: '2', caption: 'B', priority: 555, },
            { id: '1', caption: 'Z', priority: 0, },
        ]);
    });



    it('add existing item but with lower priority', () => {
        const existingReferences = [
            { id: '1', caption: 'A', priority: 555, },
            { id: '2', caption: 'B', priority: 555, },
        ];
        const referencesToAdd = [ { id: '1', caption: 'Z', priority: 999, } ];
        const result = mergeReferencesToReferences(existingReferences, referencesToAdd);

        expect(result).toEqual(existingReferences);
    });


});