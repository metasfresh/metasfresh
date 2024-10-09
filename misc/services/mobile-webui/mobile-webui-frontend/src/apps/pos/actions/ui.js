import { CLOSE_MODAL, SHOW_MODAL } from '../actionTypes';

export const MODAL_POSTerminalSelect = 'POSTerminalSelect';

export const showModalAction = ({ modal }) => {
  return {
    type: SHOW_MODAL,
    payload: { modal },
  };
};

export const closeModalAction = ({ ifModal } = {}) => {
  return {
    type: CLOSE_MODAL,
    payload: { ifModal },
  };
};
