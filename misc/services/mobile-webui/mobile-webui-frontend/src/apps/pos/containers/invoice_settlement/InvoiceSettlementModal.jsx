import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import './InvoiceSettlementModal.scss';
import { usePOSTerminal } from '../../actions/posTerminal';
import { closeModalAction, MODAL_InvoiceSettlement } from '../../actions/ui';
import { searchOpenInvoices, settleInvoice } from '../../api/invoices';
import { NumericKeyboard } from '../NumericKeyboard';
import CashPaymentDetailsModal from '../payment_panel/CashPaymentDetailsModal';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { toastError } from '../../../../utils/toast';
import { trl } from '../../../../utils/translations';
import useEscapeKey from '../../../../hooks/useEscapeKey';

const _ = (key, args) => trl(`pos.invoiceSettlement.${key}`, args);

const STAGE_SEARCH = 'search';
const STAGE_RESULTS = 'results';
const STAGE_PAYMENT = 'payment';
const STAGE_SUCCESS = 'success';

/**
 * A cashier settling a customer's already-issued, still-open sales invoice in cash at the till: key the
 * invoice's document number on the numeric keypad, pick the matching invoice from the result list, then
 * tender cash the same way a sale is paid — reusing {@link CashPaymentDetailsModal}'s own change
 * calculation. On success the invoice is paid, allocated against a new cash payment, and the till's cash
 * journal carries a matching CASH_INOUT line (see {@code POSInvoiceSettlementService#settleInCash}).
 */
const InvoiceSettlementModal = () => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal();
  const posTerminalId = posTerminal.id;
  const currency = posTerminal.currencySymbol;
  const precision = posTerminal.currencyPrecision ?? 2;

  const [stage, setStage] = useState(STAGE_SEARCH);
  const [documentNo, setDocumentNo] = useState('');
  const [invoices, setInvoices] = useState([]);
  const [selectedInvoice, setSelectedInvoice] = useState(null);
  const [isSubmitting, setSubmitting] = useState(false);
  const [result, setResult] = useState(null);

  const closeModal = () => dispatch(closeModalAction({ ifModal: MODAL_InvoiceSettlement }));
  const onCancel = () => {
    if (isSubmitting || stage === STAGE_SUCCESS) return;
    if (stage === STAGE_PAYMENT) {
      setStage(STAGE_RESULTS); // mirror CashPaymentDetailsModal's own visible Cancel button: step back, don't close
      return;
    }
    closeModal();
  };
  useEscapeKey(onCancel);

  const onNumKeyPressed = (key) => {
    if (key >= '0' && key <= '9') {
      setDocumentNo((prev) => prev + key);
    } else if (key === 'Backspace') {
      setDocumentNo((prev) => prev.slice(0, -1));
    }
    // other keys (decimal point, +10/+20/+50, sign) don't apply to a document number and are ignored
  };

  const onSearch = () => {
    if (!documentNo || isSubmitting) return;

    setSubmitting(true);
    searchOpenInvoices({ posTerminalId, documentNo })
      .then((list) => {
        setInvoices(list);
        setStage(STAGE_RESULTS);
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSubmitting(false));
  };

  const onSelectInvoice = (invoice) => {
    setSelectedInvoice(invoice);
    setStage(STAGE_PAYMENT);
  };

  const onPaymentOK = ({ cashTenderedAmount }) => {
    if (isSubmitting) return;

    setSubmitting(true);
    settleInvoice({ posTerminalId, invoiceId: selectedInvoice.invoiceId, cashTenderedAmount })
      .then((response) => {
        setResult(response);
        setStage(STAGE_SUCCESS);
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSubmitting(false));
  };

  const onSuccessClose = () => {
    posTerminal.reload(); // the drawer/journal now carries the settlement's CASH_INOUT line
    closeModal();
  };

  //
  // Step 3: cash-tendered keypad — reuses the sale payment panel's own component and change calculation.
  if (stage === STAGE_PAYMENT && selectedInvoice) {
    return (
      <CashPaymentDetailsModal
        currency={currency}
        precision={precision}
        payAmount={selectedInvoice.openAmt}
        isAllowCancel={!isSubmitting}
        onOK={onPaymentOK}
        onCancel={() => setStage(STAGE_RESULTS)}
      />
    );
  } else if (stage === STAGE_SUCCESS && result) {
    //
    // Step 4: success message, closes the whole modal.
    return (
      <div className="modal is-active pos-invoice-settlement-modal" data-testid="pos-invoice-settlement-modal">
        <div className="modal-background"></div>
        <div className="modal-card">
          <section className="modal-card-body">
            <div className="pos-invoice-settlement-success" data-testid="pos-invoice-settlement-success">
              {_('success', { docNo: result.documentNo })}
            </div>
          </section>
          <footer className="modal-card-foot">
            <div className="buttons">
              <button
                className="button is-large"
                data-testid="pos-invoice-settlement-success-close-button"
                onClick={onSuccessClose}
              >
                {_('actions.close')}
              </button>
            </div>
          </footer>
        </div>
      </div>
    );
  } else if (stage === STAGE_RESULTS) {
    //
    // Step 2: the search result list.
    return (
      <div className="modal is-active pos-invoice-settlement-modal" data-testid="pos-invoice-settlement-modal">
        <div className="modal-background"></div>
        <div className="modal-card">
          <header className="modal-card-head">
            <p className="modal-card-title">{_('title')}</p>
            <button className="delete" aria-label="close" onClick={onCancel}></button>
          </header>
          <section className="modal-card-body">
            <div className="invoices-container">
              {invoices.length === 0 && <div className="no-results">{_('noResults')}</div>}
              {invoices.map((invoice) => (
                <button
                  key={invoice.invoiceId}
                  className="button is-large is-fullwidth invoice-row"
                  data-testid="pos-invoice-settlement-row"
                  onClick={() => onSelectInvoice(invoice)}
                >
                  <div className="invoice-row-documentNo" data-testid="pos-invoice-settlement-row-documentNo">
                    {invoice.documentNo}
                  </div>
                  <div className="invoice-row-bpartnerName" data-testid="pos-invoice-settlement-row-bpartnerName">
                    {invoice.bpartnerName}
                  </div>
                  <div className="invoice-row-dateInvoiced" data-testid="pos-invoice-settlement-row-dateInvoiced">
                    {invoice.dateInvoiced}
                  </div>
                  <div className="invoice-row-openAmt" data-testid="pos-invoice-settlement-row-openAmt">
                    {formatAmountToHumanReadableStr({ amount: invoice.openAmt, currency, precision })}
                  </div>
                </button>
              ))}
            </div>
          </section>
          <footer className="modal-card-foot">
            <div className="buttons">
              <button
                className="button is-large"
                data-testid="pos-invoice-settlement-back-button"
                onClick={() => setStage(STAGE_SEARCH)}
              >
                {_('actions.back')}
              </button>
              <button className="button is-large" data-testid="pos-invoice-settlement-cancel-button" onClick={onCancel}>
                {_('actions.cancel')}
              </button>
            </div>
          </footer>
        </div>
      </div>
    );
  } else {
    //
    // Step 1 (default): the document-number keypad.
    return (
      <div className="modal is-active pos-invoice-settlement-modal" data-testid="pos-invoice-settlement-modal">
        <div className="modal-background"></div>
        <div className="modal-card">
          <header className="modal-card-head">
            <p className="modal-card-title">{_('title')}</p>
            <button className="delete" aria-label="close" disabled={isSubmitting} onClick={onCancel}></button>
          </header>
          <section className="modal-card-body">
            <div className="caption">{_('documentNo')}</div>
            <div className="document-no-value" data-testid="pos-invoice-settlement-documentNo">
              {documentNo}
            </div>
            <div className="numpad-container">
              <NumericKeyboard onKey={onNumKeyPressed} />
            </div>
          </section>
          <footer className="modal-card-foot">
            <div className="buttons">
              <button
                className="button is-large"
                data-testid="pos-invoice-settlement-search-button"
                disabled={!documentNo || isSubmitting}
                onClick={onSearch}
              >
                {_('actions.search')}
              </button>
              <button className="button is-large" disabled={isSubmitting} onClick={onCancel}>
                {_('actions.cancel')}
              </button>
            </div>
          </footer>
        </div>
      </div>
    );
  }
};

export default InvoiceSettlementModal;
