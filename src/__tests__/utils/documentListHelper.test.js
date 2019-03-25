import React from 'react';
import { List } from 'immutable';
import { removeRows, mergeRows } from '../../utils/documentListHelper';

describe('DocumentList helpers', () => {
  describe('Row helpers', () => {
    it('removes rows correctly', () => {
      const l = new List([{ id: '1' }, { id: '2' }]);
      const remove = ['1'];
      const { rows, removedRows } = removeRows(l, remove);

      expect(removedRows.length).toBe(1);
      expect(rows.size).toBe(1);
    });
  })
});