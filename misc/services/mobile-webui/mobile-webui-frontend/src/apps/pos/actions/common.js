import { getApplicationState } from '../../index';
import { APPLICATION_ID } from '../constants';

export const getPOSApplicationState = (globalState) => getApplicationState(globalState, APPLICATION_ID);
