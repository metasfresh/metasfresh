import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { toUrl, unboxAxiosResponse } from '../../../utils';

/**
 * GET {@code /api/v2/pos/invoices}: open sales invoices (completed/closed, unpaid, not a credit memo)
 * matching {@code documentNo} on the terminal's own org. Returns an empty list for a blank {@code documentNo}.
 */
export const searchOpenInvoices = ({ posTerminalId, documentNo }) => {
  return axios
    .get(toUrl(`${apiBasePath}/pos/invoices`, { posTerminalId, documentNo }))
    .then((response) => unboxAxiosResponse(response))
    .then((result) => result.list ?? []);
};

/**
 * POSTs {@code /api/v2/pos/invoices/settle}: settles an open invoice in cash. Returns the settled amount,
 * the change to hand back to the customer, and the till's updated cash journal.
 */
export const settleInvoice = ({ posTerminalId, invoiceId, cashTenderedAmount }) => {
  return axios
    .post(`${apiBasePath}/pos/invoices/settle`, { posTerminalId, invoiceId, cashTenderedAmount })
    .then((response) => unboxAxiosResponse(response));
};
