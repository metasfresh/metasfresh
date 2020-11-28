import React from 'react';
import { removeRows, mergeRows, formatDateWithZeros } from '../../utils/documentListHelper';

describe('DocumentList helpers', () => {
  it.todo('Row helpers - mergeRows');

  describe('Row helpers', () => {
    it('removes rows correctly', () => {
      const l = [{ id: '1' }, { id: '2' }];
      const remove = ['1'];
      const { rows, removedRows } = removeRows(l, remove);

      expect(removedRows.length).toBe(1);
      expect(rows.length).toBe(1);
    }); 
  })

  describe('Date manipulation function formatDateWithZeros', () => {
    it('should format corectly the date when only one char is inputed for month or day', async () => {
      const inputDateOne = '2.2.2020';
      const outputDateOne = await formatDateWithZeros(inputDateOne);
      const inputDateTwo = '2/3/2020';
      const outputDateTwo = await formatDateWithZeros(inputDateTwo);
      expect(outputDateOne).toBe('02.02.2020');
      expect(outputDateTwo).toBe('02/03/2020');
    });
  });
});

