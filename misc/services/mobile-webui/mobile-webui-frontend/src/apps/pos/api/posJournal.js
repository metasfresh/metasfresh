import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { toUrl, unboxAxiosResponse } from '../../../utils';

export const openJournal = ({ posTerminalId, cashBeginningBalance, openingNote }) => {
  return axios
    .post(`${apiBasePath}/pos/terminal/openJournal`, { posTerminalId, cashBeginningBalance, openingNote })
    .then((response) => unboxAxiosResponse(response));
};

export const closeJournal = ({ posTerminalId, cashClosingBalance, closingNote }) => {
  return axios
    .post(`${apiBasePath}/pos/terminal/closeJournal`, { posTerminalId, cashClosingBalance, closingNote })
    .then((response) => unboxAxiosResponse(response));
};

export const getJournalSummary = ({ posTerminalId }) => {
  return axios
    .get(toUrl(`${apiBasePath}/pos/terminal/journal`, { posTerminalId }))
    .then((response) => unboxAxiosResponse(response));
};
