import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import useEscapeKey from '../../../../hooks/useEscapeKey';
import { trl } from '../../../../utils/translations';
import { useWhyDidYouUpdate } from '../../../../hooks/useWhyDidYouUpdate';

const _ = (key, args = {}) => trl(`pos.currentOrder.getWeightModal.${key}`, args);

const GetCatchWeightModal = ({ catchWeightUomSymbol, onOk, onCancel }) => {
  const [weightStr, setWeightStr] = useState(0);
  const weightRef = useRef();

  const weight = Number(weightStr);
  const isValid = weight > 0;

  useWhyDidYouUpdate('AAAA', { weightRef_current: weightRef.current });
  useEffect(() => {
    if (weightRef?.current) {
      weightRef.current.focus();
      weightRef.current.select();
    }
  }, []);

  useEscapeKey(onCancel);

  const handleOKClick = () => {
    if (!isValid) return;
    onOk({ catchWeight: weight });
  };

  return (
    <div className="modal is-active">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">{_('title', { uom: catchWeightUomSymbol })}</p>
          <button className="delete" aria-label="close" onClick={onCancel}></button>
        </header>
        <section className="modal-card-body">
          <div className="control">
            <input
              ref={weightRef}
              className="input is-large"
              type="text"
              placeholder={_('weightPlaceholder')}
              value={weightStr}
              onChange={(e) => setWeightStr(e.target.value)}
            />
          </div>
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            <button className="button is-success" disabled={!isValid} onClick={handleOKClick}>
              {_('actions.ok')}
            </button>
          </div>
        </footer>
      </div>
    </div>
  );
};

GetCatchWeightModal.propTypes = {
  catchWeightUomSymbol: PropTypes.string.isRequired,
  onOk: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export default GetCatchWeightModal;
