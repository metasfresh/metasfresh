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

/**
 * POSTs {@code /api/v2/pos/invoices/cardSettlements}: pays the invoice's full open amount by card on the terminal's
 * default card reader. Returns once the checkout was sent (or its sending failed): the settlement, typically still
 * PENDING; UNKNOWN when the send's result is unknown; FAILED when SumUp definitely rejected it.
 */
export const startInvoiceCardSettlement = ({ posTerminalId, invoiceId }) => {
  return axios
    .post(`${apiBasePath}/pos/invoices/cardSettlements`, { posTerminalId, invoiceId })
    .then((response) => unboxAxiosResponse(response));
};

/**
 * GET {@code /api/v2/pos/invoices/cardSettlements/{id}}: the settlement's current state, as stored locally (no SumUp
 * call), so it is cheap enough to poll.
 */
export const getInvoiceCardSettlement = ({ id }) => {
  return axios
    .get(`${apiBasePath}/pos/invoices/cardSettlements/${id}`)
    .then((response) => unboxAxiosResponse(response));
};

/**
 * POSTs {@code /api/v2/pos/invoices/cardSettlements/{id}/check}: asks SumUp for the current result and reconciles the
 * settlement with it. {@code checkFailed} is set if SumUp could not be asked.
 */
export const checkInvoiceCardSettlement = ({ id }) => {
  return axios
    .post(`${apiBasePath}/pos/invoices/cardSettlements/${id}/check`)
    .then((response) => unboxAxiosResponse(response));
};

/**
 * POSTs {@code /api/v2/pos/invoices/cardSettlements/{id}/release}: first without confirmation; if the response says
 * {@code confirmationRequired}, again with {@code confirmNotCharged} and the status the confirmation prompt showed.
 * Returns {@code { settlement, confirmationRequired, refusedMessage }}.
 */
export const releaseInvoiceCardSettlement = ({ id, confirmNotCharged = false, expectedStatus = null }) => {
  return axios
    .post(`${apiBasePath}/pos/invoices/cardSettlements/${id}/release`, { confirmNotCharged, expectedStatus })
    .then((response) => unboxAxiosResponse(response));
};
