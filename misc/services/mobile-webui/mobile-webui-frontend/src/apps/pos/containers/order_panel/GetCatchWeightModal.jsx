import React, { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';

const GetCatchWeightModal = ({ catchWeightUomSymbol, onOk, onCancel }) => {
  const weightRef = useRef();

  useEffect(() => {
    weightRef?.current?.focus();
  }, [weightRef.current]);

  return (
    <div className="modal is-active">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">Weight ({catchWeightUomSymbol})</p>
          <button className="delete" aria-label="close" onClick={onCancel}></button>
        </header>
        <section className="modal-card-body">
          <div className="control">
            <input ref={weightRef} className="input is-large" type="text" placeholder="Catch weight" />
          </div>
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            <button
              className="button is-success"
              onClick={() => {
                onOk({ catchWeight: Number(weightRef.current.value) });
              }}
            >
              OK
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
