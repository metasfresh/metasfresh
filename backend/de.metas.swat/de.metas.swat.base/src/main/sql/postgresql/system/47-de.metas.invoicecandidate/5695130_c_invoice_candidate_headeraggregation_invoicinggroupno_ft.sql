DROP TRIGGER IF EXISTS C_Invoice_Candidate_HeaderAggregation_InvoicingGroupNo_trigger ON C_Invoice_Candidate_HeaderAggregation
;

DROP FUNCTION IF EXISTS c_invoice_candidate_headeraggregation_invoicinggroupno_ft()
;

CREATE FUNCTION c_invoice_candidate_headeraggregation_invoicinggroupno_ft() RETURNS trigger
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_nextInvoicingGroupNo INTEGER;
BEGIN
    SELECT COALESCE(MAX(ha.InvoicingGroupNo), 0) + 1
    INTO v_nextInvoicingGroupNo
    FROM C_Invoice_Candidate_HeaderAggregation ha
    WHERE ha.C_BPartner_ID = NEW.C_BPartner_ID
      AND ha.IsSOTrx = NEW.IsSOTrx;

    NEW.InvoicingGroupNo := v_nextInvoicingGroupNo;

    RETURN NEW;
END;
$$
;

COMMENT ON FUNCTION C_Invoice_Candidate_HeaderAggregation_InvoicingGroupNo_ft() IS 'Sets C_Invoice_Candidate_HeaderAggregation.InvoicingGroupNo'
;

CREATE TRIGGER C_Invoice_Candidate_HeaderAggregation_InvoicingGroupNo_trigger
    BEFORE INSERT
    ON C_Invoice_Candidate_HeaderAggregation
    FOR EACH ROW
    WHEN (NEW.InvoicingGroupNo IS NULL)
EXECUTE PROCEDURE C_Invoice_Candidate_HeaderAggregation_InvoicingGroupNo_ft()
;

