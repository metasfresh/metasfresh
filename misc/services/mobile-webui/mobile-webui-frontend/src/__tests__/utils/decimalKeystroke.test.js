import { isAllowedDecimalInput } from '../../utils/decimalKeystroke';

describe('isAllowedDecimalInput', () => {
  it.each(['0', '5', '9', '.', ',', '-', ' ', ' ', ' ', '12,5', '1 234.5', '-1.5'])('allows %j', (s) => {
    expect(isAllowedDecimalInput(s)).toBe(true);
  });
  it.each(['a', 'E', '@', '+', "'", '’', '1a', '١', '%'])('blocks %j', (s) => {
    expect(isAllowedDecimalInput(s)).toBe(false);
  });
  it('passes deletion/navigation (no data)', () => {
    expect(isAllowedDecimalInput(null)).toBe(true);
    expect(isAllowedDecimalInput(undefined)).toBe(true);
    expect(isAllowedDecimalInput('')).toBe(true);
  });
});
