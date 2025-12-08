import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import { getPOSTerminalsArray } from '../../api/posTerminal';
import { usePOSTerminal } from '../../actions/posTerminal';
import { closeModalAction, MODAL_POSTerminalSelect } from '../../actions/ui';
import { useDispatch } from 'react-redux';

import './POSTerminalSelectModal.scss';
import useEscapeKey from '../../../../hooks/useEscapeKey';
import { trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.selectTerminal.${key}`);

const POSTerminalSelectModal = ({ allowCancel }) => {
  const dispatch = useDispatch();
  const currentTerminal = usePOSTerminal();
  const [posTerminals, setPOSTerminals] = useState(null);

  useEffect(() => {
    getPOSTerminalsArray().then((posTerminals) => {
      if (posTerminals?.length === 1) {
        onTerminalSelected(posTerminals[0].id);
      } else {
        setPOSTerminals(posTerminals);
      }
    });
  }, []);

  const closeModal = () => {
    dispatch(closeModalAction({ ifModal: MODAL_POSTerminalSelect }));
  };
  const onTerminalSelected = (posTerminalId) => {
    currentTerminal.setPOSTerminalId(posTerminalId);
    closeModal();
  };

  const onCancel = () => {
    if (!allowCancel) return;
    closeModal();
  };

  useEscapeKey(onCancel);

  if (posTerminals == null) return null;

  return (
    <div className="modal is-active pos-select-terminal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">{_('title')}</p>
          {allowCancel && <button className="delete" aria-label="close" onClick={onCancel}></button>}
        </header>
        <section className="modal-card-body">
          {posTerminals.map((posTerminal) => (
            <button className="button is-large" key={posTerminal.id} onClick={() => onTerminalSelected(posTerminal.id)}>
              <span className="icon is-small">
                <i className={cx('fas', { 'fa-check': posTerminal.id === currentTerminal.id })}></i>
              </span>
              <span>{posTerminal.caption}</span>
            </button>
          ))}
        </section>
      </div>
    </div>
  );
};
POSTerminalSelectModal.propTypes = {
  allowCancel: PropTypes.bool.isRequired,
};

export default POSTerminalSelectModal;
