import { APPLICATION_ID } from './constants';
import { trl } from '../../utils/translations';

export const appTrl = (key, args = {}) => trl(`${APPLICATION_ID}.${key}`, args);
