import qhistory from 'qhistory';
import queryString from 'query-string';
import { createBrowserHistory, createMemoryHistory } from 'history';

const isTest = process.env.NODE_ENV === 'test';
const history = isTest
  ? createMemoryHistory({ initialEntries: ['/'] })
  : createBrowserHistory();

export default qhistory(history, queryString.stringify, queryString.parse);
