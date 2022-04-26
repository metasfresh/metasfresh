import React from 'react';
import {
  mergeRows,
  formatDateWithZeros,
  retainExistingRowIds
} from '../../utils/documentListHelper';

describe('DocumentList helpers', () => {
  describe('mergeRows', () => {
    it('changed rows, deleted rows, new rows', () => {
      const result = mergeRows({
        toRows: [{ id: '1' }, { id: '2' }, { id: '3' }],
        changedIds: ['2', '3', '4'],
        changedRows: [{ id: '2', someMarker:'' }, { id: '4', someMarker:'' }]
      })

      expect(result).toEqual({
        hasChanges: true,
        rows: [{ id: '1' }, { id: '2', someMarker:'' } ],
        removedRowIds: ['3']
      });
    });

    it('no changed rows', () => {
      const result = mergeRows({
        toRows: [{ id: '1' }, { id: '2' }, { id: '3' }],
        changedIds: ['4'],
        changedRows: [{ id: '4', someMarker:'' }]
      })

      expect(result).toEqual({
        hasChanges: false,
        rows: [{ id: '1' }, { id: '2' }, { id: '3' }],
        removedRowIds: []
      });
    });

    it('changed included row', () => {
      const result = mergeRows({
        toRows: [{ id:'1', includedDocuments: [{id:'2'}] }],
        changedIds: ['2'],
        changedRows: [{ id: '2', someMarker:'' }]
      })

      expect(result).toEqual({
        hasChanges: true,
        rows: [ { id:'1', includedDocuments: [{id:'2', someMarker:''}] } ],
        removedRowIds: []
      });
    });

    it('removed included row', () => {
      const result = mergeRows({
        toRows: [{ id:'1', includedDocuments: [{id:'2'}] }],
        changedIds: ['2'],
        changedRows: []
      })

      expect(result).toEqual({
        hasChanges: true,
        rows: [ { id:'1', includedDocuments: [] } ],
        removedRowIds: []
      });
    });

  });



  describe('retainExistingRowIds', ()=> {
    it('null row ids array', ()=>{
      expect(retainExistingRowIds([{id:'1'}, {id:'2'}, {id:'3'}], null)).toEqual([]);
    })
    it('no row ids in page', ()=>{
      expect(retainExistingRowIds([{id:'1'}, {id:'2'}, {id:'3'}], ['4'])).toEqual([]);
    })
    it('partial row ids in page', ()=>{
      expect(retainExistingRowIds([{id:'1'}, {id:'2'}, {id:'3'}], ['2','4'])).toEqual(['2']);
    })
    it('row id in included row', ()=>{
      expect(retainExistingRowIds([{id:'1', includedDocuments:[{id:'2'}]}], ['2'])).toEqual(['2']);
    })
  });



  describe('formatDateWithZeros', () => {
    it('should format corectly the date when only one char is inputed for month or day', async () => {
      const inputDateOne = '2.2.2020';
      const outputDateOne = formatDateWithZeros(inputDateOne);
      const inputDateTwo = '2/3/2020';
      const outputDateTwo = formatDateWithZeros(inputDateTwo);
      expect(outputDateOne).toBe('02.02.2020');
      expect(outputDateTwo).toBe('02/03/2020');
    });
  });
});

