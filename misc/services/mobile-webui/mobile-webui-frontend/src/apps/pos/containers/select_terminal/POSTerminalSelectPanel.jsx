import React, { useEffect, useState } from 'react';
import cx from 'classnames';
import { getPOSTerminalsArray } from '../../api/posTerminal';
import './POSTerminalSelectPanel.scss';
import { usePOSTerminal } from '../../actions/posTerminal';
import { closeModalAction, MODAL_POSTerminalSelect } from '../../actions/ui';
import { useDispatch } from 'react-redux';

const POSTerminalSelectPanel = () => {
  const dispatch = useDispatch();
  const currentTerminal = usePOSTerminal();
  const [posTerminals, setPOSTerminals] = useState([]);

  useEffect(() => {
    getPOSTerminalsArray().then(setPOSTerminals);
  }, []);

  return (
    <div className="modal is-active pos-select-terminal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">Select terminal</p>
        </header>
        <section className="modal-card-body">
          {posTerminals.map((posTerminal) => (
            <button
              className="button is-large"
              key={posTerminal.id}
              onClick={() => {
                currentTerminal.setPOSTerminalId(posTerminal.id);
                dispatch(closeModalAction({ ifModal: MODAL_POSTerminalSelect }));
              }}
            >
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

export default POSTerminalSelectPanel;
