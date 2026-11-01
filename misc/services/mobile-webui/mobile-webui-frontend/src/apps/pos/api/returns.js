import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';

/**
 * POSTs {@code /api/v2/pos/returns}: creates (or, on a retry with the same {@code externalId}, no-ops on) a
 * completed product return. Lines carry product + qty only — the price is always the till's own current
 * price, resolved server-side.
 */
export const createReturn = ({ posTerminalId, externalId, lines }) => {
  return axios
    .post(`${apiBasePath}/pos/returns`, {
      posTerminalId,
      externalId,
      lines: lines.map(({ productId, qty }) => ({ productId, qty })),
    })
    .then((response) => unboxAxiosResponse(response));
};
