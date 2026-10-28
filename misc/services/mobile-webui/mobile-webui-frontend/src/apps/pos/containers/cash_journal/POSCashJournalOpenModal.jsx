import React, { useState } from 'react';
import { usePOSTerminal } from '../../actions/posTerminal';

import './POSCashJournalOpenModal.scss';
import { trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.openCashJournal.${key}`);

const POSCashJournalOpenModal = () => {
  const posTerminal = usePOSTerminal();
  const [cashBeginningBalance, setCashBeginningBalance] = useState(posTerminal?.cashLastBalance ?? 0);
  const [openingNote, setOpeningNote] = useState('');

  const onOpenClick = () => {
    posTerminal.openJournal({ cashBeginningBalance, openingNote });
  };

  return (
    <div className="modal is-active pos-journal-open-panel" data-testid="pos-cash-journal-open-modal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">{_('title')}</p>
        </header>
        <section className="modal-card-body">
          <div className="line">
            <div className="caption">{_('openingBalance')}</div>
            <div className="field">
              <input
                type="number"
                data-testid="pos-cash-journal-opening-balance-input"
                value={cashBeginningBalance}
                onChange={(e) => {
                  setCashBeginningBalance(e.target.value);
                }}
              />
            </div>
          </div>
          <div className="line">
            <div className="caption">{_('openingNote')}</div>
            <div className="field">
              <textarea value={openingNote} onChange={(e) => setOpeningNote(e.target.value)} />
            </div>
          </div>
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            <button className="button is-large" data-testid="pos-cash-journal-open-button" onClick={onOpenClick}>
              {_('actions.open')}
            </button>
          </div>
        </footer>
      </div>
    </div>
  );
};

export default POSCashJournalOpenModal;
