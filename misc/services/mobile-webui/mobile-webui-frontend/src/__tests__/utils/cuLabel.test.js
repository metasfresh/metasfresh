import { selectCULabelFormat } from '../../utils/cuLabel';

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
