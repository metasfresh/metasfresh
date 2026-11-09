import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import './InvoiceSettlementModal.scss';
import { usePOSTerminal } from '../../actions/posTerminal';
import { closeModalAction, MODAL_InvoiceSettlement } from '../../actions/ui';
import {
  checkInvoiceCardSettlement,
  getInvoiceCardSettlement,
  releaseInvoiceCardSettlement,
  searchOpenInvoices,
  settleInvoice,
  startInvoiceCardSettlement,
} from '../../api/invoices';
import { PAYMENT_METHOD_CARD } from '../../constants/paymentMethods';
import { NumericKeyboard } from '../NumericKeyboard';
import CashPaymentDetailsModal from '../payment_panel/CashPaymentDetailsModal';
import CardSettlementStatusPanel, {
  CARD_STATUS_SUCCESSFUL,
  isBlockingCardSettlement,
  isReleasableStatus,
  isRetryableCardSettlement,
} from './CardSettlementStatusPanel';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { toastError } from '../../../../utils/toast';
import { trl } from '../../../../utils/translations';
import useEscapeKey from '../../../../hooks/useEscapeKey';

const _ = (key, args) => trl(`pos.invoiceSettlement.${key}`, args);

const STAGE_SEARCH = 'search';
const STAGE_RESULTS = 'results';
const STAGE_METHOD = 'method';
const STAGE_CARD = 'card';
const STAGE_PAYMENT = 'payment';
const STAGE_SUCCESS = 'success';

const CARD_SETTLEMENT_POLL_MILLIS = 2000;

/**
 * A cashier settling a customer's already-issued, still-open sales invoice at the till: key the invoice's document
 * number on the numeric keypad and pick the matching invoice from the result list.
 * <ul>
 * <li>On a terminal that offers card, the cashier then chooses cash or card ({@link STAGE_METHOD}); on a cash-only
 * terminal the cash keypad follows directly, as before.</li>
 * <li>Cash: tender cash the same way a sale is paid — reusing {@link CashPaymentDetailsModal}'s own change
 * calculation (see {@code POSInvoiceSettlementService#settleInCash}).</li>
 * <li>Card: the invoice's full open amount is sent to the terminal's card reader; the till shows the card payment's
 * state ({@link STAGE_CARD}), polling it every 2 s while it is unfinished. An invoice whose card payment is still
 * unfinished opens directly in that state, after asking SumUp once for the current result.</li>
 * </ul>
 */
const InvoiceSettlementModal = () => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal();
  const posTerminalId = posTerminal.id;
  const currency = posTerminal.currencySymbol;
  const precision = posTerminal.currencyPrecision ?? 2;
  const isCardOffered = !!posTerminal.availablePaymentMethods?.includes(PAYMENT_METHOD_CARD);

  const [stage, setStage] = useState(STAGE_SEARCH);
  const [documentNo, setDocumentNo] = useState('');
  const [invoices, setInvoices] = useState([]);
  const [selectedInvoice, setSelectedInvoice] = useState(null);
  const [isSubmitting, setSubmitting] = useState(false);
  const [result, setResult] = useState(null);
  const [cardSettlement, setCardSettlement] = useState(null);
  // the status the cashier was asked to confirm "not charged" for; null while no confirmation is asked
  const [releaseConfirmationStatus, setReleaseConfirmationStatus] = useState(null);
  const [releaseMessage, setReleaseMessage] = useState(null);
  const [now, setNow] = useState(Date.now());
  const [cashBackStage, setCashBackStage] = useState(STAGE_RESULTS);

  const isCardSettlementBlocking = isBlockingCardSettlement(cardSettlement);
  // the confirmation stays only while the payment is still in the status it was asked for
  const isReleaseConfirmationShown =
    releaseConfirmationStatus != null &&
    isReleasableStatus(cardSettlement) &&
    cardSettlement.status === releaseConfirmationStatus;

  //
  // Poll the unfinished card payment (local state only, no SumUp call) and keep "now" current for the release button.
  // The next request is scheduled only after the previous one finished, so polls never overlap; a response arriving
  // after the poll stopped (e.g. the dialog was closed) is ignored.
  useEffect(() => {
    if (stage !== STAGE_CARD || !cardSettlement || !isCardSettlementBlocking) {
      return;
    }

    const settlementId = cardSettlement.id;
    let isStopped = false;
    let timeoutId = null;
    const pollOnce = () => {
      getInvoiceCardSettlement({ id: settlementId })
        .then((settlement) => {
          if (!isStopped) {
            setCardSettlement((prev) => (prev?.id === settlement.id ? settlement : prev));
          }
        })
        .catch((axiosError) => console.warn('Failed polling card settlement', { settlementId, axiosError }))
        .finally(() => {
          if (!isStopped) {
            setNow(Date.now());
            timeoutId = setTimeout(pollOnce, CARD_SETTLEMENT_POLL_MILLIS);
          }
        });
    };
    timeoutId = setTimeout(pollOnce, CARD_SETTLEMENT_POLL_MILLIS);

    return () => {
      isStopped = true;
      clearTimeout(timeoutId);
    };
  }, [stage, cardSettlement?.id, isCardSettlementBlocking]);

  //
  // The payment left the status the confirmation was asked for (e.g. it was approved meanwhile): drop the
  // confirmation, the panel shows the new state instead.
  useEffect(() => {
    if (releaseConfirmationStatus != null && !isReleaseConfirmationShown) {
      setReleaseConfirmationStatus(null);
    }
  }, [releaseConfirmationStatus, isReleaseConfirmationShown]);

  //
  // An approved and booked card payment: the invoice is paid.
  useEffect(() => {
    if (stage === STAGE_CARD && cardSettlement?.status === CARD_STATUS_SUCCESSFUL) {
      setResult({ documentNo: cardSettlement.documentNo });
      setStage(STAGE_SUCCESS);
    }
  }, [stage, cardSettlement?.status]);

  const closeModal = () => dispatch(closeModalAction({ ifModal: MODAL_InvoiceSettlement }));
  const onCancel = () => {
    if (isSubmitting || stage === STAGE_SUCCESS) return;
    if (stage === STAGE_PAYMENT) {
      setStage(cashBackStage); // mirror CashPaymentDetailsModal's own visible Cancel button: step back, don't close
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

  const resetCardSettlement = (settlement) => {
    setCardSettlement(settlement);
    setReleaseConfirmationStatus(null);
    setReleaseMessage(null);
    setNow(Date.now());
  };

  const onSelectInvoice = (invoice) => {
    setSelectedInvoice(invoice);

    if (invoice.blockingCardSettlement) {
      // the invoice's card payment is unfinished: show it, after asking SumUp once for its current result
      resetCardSettlement(invoice.blockingCardSettlement);
      setStage(STAGE_CARD);
      setSubmitting(true);
      checkInvoiceCardSettlement({ id: invoice.blockingCardSettlement.id })
        .then((settlement) => setCardSettlement(settlement))
        .catch((axiosError) => toastError({ axiosError }))
        .finally(() => setSubmitting(false));
    } else if (isCardOffered) {
      resetCardSettlement(null);
      setStage(STAGE_METHOD);
    } else {
      setCashBackStage(STAGE_RESULTS);
      setStage(STAGE_PAYMENT);
    }
  };

  const onPayByCash = () => {
    setCashBackStage(stage);
    setStage(STAGE_PAYMENT);
  };

  const onPayByCard = () => {
    if (isSubmitting) return;

    resetCardSettlement(null);
    setStage(STAGE_CARD);
    setSubmitting(true);
    startInvoiceCardSettlement({ posTerminalId, invoiceId: selectedInvoice.invoiceId })
      .then((settlement) => setCardSettlement(settlement))
      .catch((axiosError) => {
        // nothing was sent (e.g. the invoice is no longer open, no open cash journal): back to the choice
        toastError({ axiosError });
        setStage(STAGE_METHOD);
      })
      .finally(() => setSubmitting(false));
  };

  const applyReleaseResponse = (response) => {
    setCardSettlement(response.settlement);
    // remember the status the confirmation is asked for: the confirm request must refer to exactly that one
    setReleaseConfirmationStatus(response.confirmationRequired ? response.settlement.status : null);
    setReleaseMessage(response.refusedMessage ?? null);
  };

  const onRelease = () => {
    if (isSubmitting || !cardSettlement) return;

    setSubmitting(true);
    releaseInvoiceCardSettlement({ id: cardSettlement.id })
      .then(applyReleaseResponse)
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSubmitting(false));
  };

  const onConfirmRelease = () => {
    if (isSubmitting || !cardSettlement || !isReleaseConfirmationShown) return;

    setSubmitting(true);
    releaseInvoiceCardSettlement({
      id: cardSettlement.id,
      confirmNotCharged: true,
      expectedStatus: releaseConfirmationStatus, // released only if the payment is still in the status the cashier confirmed
    })
      .then(applyReleaseResponse)
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSubmitting(false));
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
    posTerminal.reload(); // the drawer/journal now carries the settlement's journal line
    closeModal();
  };

  //
  // Cash-tendered keypad — reuses the sale payment panel's own component and change calculation.
  if (stage === STAGE_PAYMENT && selectedInvoice) {
    return (
      <CashPaymentDetailsModal
        currency={currency}
        precision={precision}
        payAmount={selectedInvoice.openAmt}
        isAllowCancel={!isSubmitting}
        onOK={onPaymentOK}
        onCancel={() => setStage(cashBackStage)}
      />
    );
  } else if (stage === STAGE_SUCCESS && result) {
    //
    // Success message, closes the whole modal.
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
  } else if ((stage === STAGE_METHOD || stage === STAGE_CARD) && selectedInvoice) {
    //
    // The selected invoice with the payment choice and, once a card payment exists, its state.
    const isCardStage = stage === STAGE_CARD;
    const isStartingCardPayment = isCardStage && !cardSettlement && isSubmitting;
    const isPaymentChoiceAllowed = !isCardStage || isRetryableCardSettlement(cardSettlement);
    const isCashRefused = isCardStage && (isCardSettlementBlocking || isStartingCardPayment);
    const isReleaseShown = isCardStage && isReleasableStatus(cardSettlement);
    const isReleaseEnabled = isReleaseShown && now >= Date.parse(cardSettlement.releasableAt);

    return (
      <div className="modal is-active pos-invoice-settlement-modal" data-testid="pos-invoice-settlement-modal">
        <div className="modal-background"></div>
        <div className="modal-card">
          <header className="modal-card-head">
            <p className="modal-card-title">{_('title')}</p>
            <button
              className="delete"
              aria-label="close"
              data-testid="pos-invoice-settlement-header-close-button"
              disabled={isSubmitting}
              onClick={onCancel}
            ></button>
          </header>
          <section className="modal-card-body">
            <div className="payment-choice" data-testid="pos-invoice-settlement-payment-choice">
              <div className="selected-invoice">
                <div className="selected-invoice-line">
                  <span className="summary-caption">{_('documentNo')}</span>
                  <span className="summary-value" data-testid="pos-invoice-settlement-selected-documentNo">
                    {selectedInvoice.documentNo}
                  </span>
                </div>
                <div className="selected-invoice-line">
                  <span className="summary-caption">{_('customer')}</span>
                  <span
                    className="summary-value"
                    title={selectedInvoice.bpartnerName}
                    data-testid="pos-invoice-settlement-selected-bpartnerName"
                  >
                    {selectedInvoice.bpartnerName}
                  </span>
                </div>
                <div className="selected-invoice-line">
                  <span className="summary-caption">{_('openAmt')}</span>
                  <span className="summary-value" data-testid="pos-invoice-settlement-selected-openAmt">
                    {formatAmountToHumanReadableStr({ amount: selectedInvoice.openAmt, currency, precision })}
                  </span>
                </div>
              </div>

              {isCardStage && (
                <CardSettlementStatusPanel
                  settlement={cardSettlement}
                  isStarting={isStartingCardPayment}
                  isReleaseShown={isReleaseShown}
                  isReleaseEnabled={isReleaseEnabled}
                  onRelease={onRelease}
                  isConfirmationRequired={isReleaseConfirmationShown}
                  onConfirmRelease={onConfirmRelease}
                  onCancelConfirmation={() => setReleaseConfirmationStatus(null)}
                  releaseMessage={releaseMessage}
                  isDisabled={isSubmitting}
                />
              )}

              <div className="payment-methods">
                {isCardOffered && isPaymentChoiceAllowed && (
                  <button
                    className="button is-large is-fullwidth"
                    data-testid="pos-invoice-settlement-card-button"
                    disabled={isSubmitting}
                    onClick={onPayByCard}
                  >
                    <span className="icon">
                      <i className="fa-solid fa-credit-card" />
                    </span>
                    <span>{_('actions.payByCard')}</span>
                  </button>
                )}
                {(isPaymentChoiceAllowed || isCashRefused) && (
                  <button
                    className="button is-large is-fullwidth"
                    data-testid="pos-invoice-settlement-cash-button"
                    disabled={isSubmitting || isCashRefused}
                    onClick={onPayByCash}
                  >
                    <span className="icon">
                      <i className="fa-solid fa-money-bill-1" />
                    </span>
                    <span>{_('actions.payByCash')}</span>
                  </button>
                )}
                {isCashRefused && (
                  <div className="cash-refused-message" data-testid="pos-invoice-settlement-cash-refused-message">
                    {_('card.cardPaymentStillOpen')}
                  </div>
                )}
              </div>
            </div>
          </section>
          <footer className="modal-card-foot">
            <div className="buttons">
              <button
                className="button is-large"
                data-testid="pos-invoice-settlement-close-button"
                disabled={isSubmitting}
                onClick={onCancel}
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
    // The search result list.
    return (
      <div className="modal is-active pos-invoice-settlement-modal" data-testid="pos-invoice-settlement-modal">
        <div className="modal-background"></div>
        <div className="modal-card">
          <header className="modal-card-head">
            <p className="modal-card-title">{_('title')}</p>
            <button
              className="delete"
              aria-label="close"
              data-testid="pos-invoice-settlement-header-close-button"
              onClick={onCancel}
            ></button>
          </header>
          <section className="modal-card-body">
            <div className="invoices-container">
              {invoices.length === 0 && (
                <div className="no-results" data-testid="pos-invoice-settlement-no-results">
                  {_('noResults')}
                </div>
              )}
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
                  {invoice.blockingCardSettlement && (
                    <div className="invoice-row-cardSettlement" data-testid="pos-invoice-settlement-row-cardSettlement">
                      {_('card.cardPaymentOpen')}
                    </div>
                  )}
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
            <button
              className="delete"
              aria-label="close"
              data-testid="pos-invoice-settlement-header-close-button"
              disabled={isSubmitting}
              onClick={onCancel}
            ></button>
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
