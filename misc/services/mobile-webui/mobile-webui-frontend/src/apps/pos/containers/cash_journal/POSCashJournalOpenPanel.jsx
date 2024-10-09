import React, { useState } from 'react';
import { usePOSTerminal } from '../../actions/posTerminal';

import './POSCashJournalOpenPanel.scss';

const POSCashJournalOpenPanel = () => {
  const posTerminal = usePOSTerminal();
  const [cashBeginningBalance, setCashBeginningBalance] = useState(posTerminal?.cashLastBalance ?? 0);
  const [openingNote, setOpeningNote] = useState('');

  const onOpenClick = () => {
    posTerminal.openJournal({ cashBeginningBalance, openingNote });
  };

  return (
    <div className="modal is-active pos-journal-open-panel">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">Opening cash</p>
        </header>
        <section className="modal-card-body">
          <div className="line">
            <div className="caption">Opening cash</div>
            <div className="field">
              <input
                type="number"
                value={cashBeginningBalance}
                onChange={(e) => {
                  setCashBeginningBalance(e.target.value);
                }}
              />
            </div>
          </div>
          <div className="line">
            <div className="caption">Opening note</div>
            <div className="field">
              <textarea value={openingNote} onChange={(e) => setOpeningNote(e.target.value)} />
            </div>
          </div>
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            <button className="button is-large" onClick={onOpenClick}>
              Open
            </button>
          </div>
        </footer>
      </div>
    </div>
  );
};

export default POSCashJournalOpenPanel;
