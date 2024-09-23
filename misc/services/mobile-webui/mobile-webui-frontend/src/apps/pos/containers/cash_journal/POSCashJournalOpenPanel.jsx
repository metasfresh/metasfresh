import React, { useState } from 'react';
import '../../styles/pos-input-panel.scss';
import { usePOSTerminal } from '../../actions';

const POSCashJournalOpenPanel = () => {
  const posTerminal = usePOSTerminal();
  const [cashBeginningBalance, setCashBeginningBalance] = useState(posTerminal?.cashLastBalance ?? 0);
  const [openingNote, setOpeningNote] = useState('');

  const onOpenClick = () => {
    posTerminal.openJournal({ cashBeginningBalance, openingNote });
  };

  return (
    <div className="pos-content pos-input-panel-container pos-journal-open-panel">
      <div className="pos-input-panel">
        <div className="pos-input-panel-header">Open cash journal</div>
        <div className="pos-input-panel-content">
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
        </div>
        <div className="pos-input-panel-bottom">
          <button onClick={onOpenClick}>Open</button>
        </div>
      </div>
    </div>
  );
};

export default POSCashJournalOpenPanel;
