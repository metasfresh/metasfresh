import { getTabRequest } from '../../api/window';

jest.mock('../../api/view', () => ({
  getData: jest.fn(),
}));

const { getData } = require('../../api/view');

// Silence the intentional `console.error` inside getTabRequest's .catch,
// so a rejection-path test doesn't pollute Jest's output.
let consoleErrorSpy;
beforeEach(() => {
  jest.clearAllMocks();
  consoleErrorSpy = jest.spyOn(console, 'error').mockImplementation(() => {});
});
afterEach(() => {
  consoleErrorSpy.mockRestore();
});

describe('getTabRequest', () => {
  it('returns { rows: [], orderBys: [] } when the underlying getData rejects', async () => {
    getData.mockRejectedValueOnce(new Error('boom'));

    const result = await getTabRequest(
      'AD_Tab-999',
      '541851',
      '1000001',
      undefined
    );

    expect(result).toEqual({ rows: [], orderBys: [] });
    expect(consoleErrorSpy).toHaveBeenCalledTimes(1);
  });

  it('returns { rows, orderBys } when getData resolves with a well-formed response', async () => {
    getData.mockResolvedValueOnce({
      data: {
        result: [
          { rowId: 'r1', fieldsByName: {} },
          { rowId: 'r2', fieldsByName: {} },
        ],
        orderBys: [{ fieldName: 'Amount', ascending: true }],
      },
    });

    const result = await getTabRequest(
      'AD_Tab-999',
      '541851',
      '1000001',
      undefined
    );

    expect(result.rows).toHaveLength(2);
    expect(result.rows[0].rowId).toBe('r1');
    expect(result.orderBys).toEqual([
      { fieldName: 'Amount', ascending: true },
    ]);
  });

  it('returns empty rows and empty orderBys when the response has no result / orderBys fields', async () => {
    getData.mockResolvedValueOnce({ data: {} });

    const result = await getTabRequest(
      'AD_Tab-999',
      '541851',
      '1000001',
      undefined
    );

    expect(result).toEqual({ rows: [], orderBys: [] });
  });
});
