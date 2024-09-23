import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';

export const openJournal = ({ cashBeginningBalance, openingNote }) => {
  return axios
    .post(`${apiBasePath}/pos/terminal/journal/open`, { cashBeginningBalance, openingNote })
    .then((response) => unboxAxiosResponse(response));
};

export const closeJournal = ({ cashClosingBalance, closingNote }) => {
  return axios
    .post(`${apiBasePath}/pos/terminal/journal/close`, { cashClosingBalance, closingNote })
    .then((response) => unboxAxiosResponse(response));
};

export const getJournalSummary = () => {
  return axios.get(`${apiBasePath}/pos/terminal/journal`).then((response) => unboxAxiosResponse(response));
};
