import qhistory from 'qhistory';
import { stringify, parse } from 'qs';
import { createBrowserHistory } from 'history';

const History = qhistory(createBrowserHistory(), stringify, parse);

export default History;
