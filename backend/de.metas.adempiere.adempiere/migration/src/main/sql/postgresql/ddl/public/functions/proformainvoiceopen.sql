-- Compute the open amount for a proforma invoice (APF/ARF doc base type).
--
-- Regular invoices are excluded from C_Invoice_v (the view filters on IsFinancial='Y',
-- which proforma invoices are not), so invoiceopen() returns NULL for them.
-- This function fills that gap for the pay-selection flow ONLY.
--
-- The pay-selection SQL uses:
--   COALESCE(invoiceopen(i.C_Invoice_ID, 0), proformaInvoiceOpen(i.C_Invoice_ID))
-- so this function is only called when invoiceopen() returns NULL (i.e. proforma invoices).
--
-- Returns: open amount in the invoice's own currency, or NULL when the invoice
--          is not a proforma (DocBaseType NOT IN ('APF','ARF')), so callers can
--          safely COALESCE the two functions.

-- DROP FUNCTION IF EXISTS proformaInvoiceOpen(numeric);

CREATE OR REPLACE FUNCTION proformaInvoiceOpen(p_c_invoice_id numeric)
    RETURNS numeric
    LANGUAGE plpgsql
    VOLATILE
AS
$BODY$
DECLARE
    v_GrandTotal   numeric;
    v_MultiplierAP numeric;
    v_Currency_ID  numeric;
    v_DocBaseType  char(3);
    v_AllocatedAmt numeric := 0;
    v_LineAmt      numeric;
    v_OpenAmt      numeric;
    allocationLine record;
BEGIN
    -- Fetch invoice header. Only handle proforma doc base types.
    SELECT i.grandtotal,
           -- Derive AP multiplier the same way C_Invoice_v does:
           -- second char of docbasetype is 'P' or 'E' → AP side → -1; otherwise +1.
           CASE
               WHEN charat(dt.docbasetype::character varying, 2)::text = ANY (ARRAY ['P'::text, 'E'::text])
                   THEN -1.0
               ELSE 1.0
               END,
           i.c_currency_id,
           dt.docbasetype
    INTO v_GrandTotal, v_MultiplierAP, v_Currency_ID, v_DocBaseType
    FROM c_invoice i
             JOIN c_doctype dt ON i.c_doctype_id = dt.c_doctype_id
    WHERE i.c_invoice_id = p_c_invoice_id;

    -- Not found or not a proforma invoice → return NULL so COALESCE falls through.
    IF v_DocBaseType IS NULL OR v_DocBaseType NOT IN ('APF', 'ARF') THEN
        RETURN NULL;
    END IF;

    -- Sum all active allocation lines for this invoice (no date filter — mirrors invoiceopen()).
    -- Currency is on the allocation header (a.C_Currency_ID), amounts on the line (al.Amount).
    FOR allocationLine IN (
        SELECT al.amount + al.discountamt + al.writeoffamt AS amt,
               a.c_currency_id                            AS currency_id,
               al.datetrx                                 AS date,
               a.ad_client_id,
               a.ad_org_id
        FROM c_allocationline al
                 JOIN c_allocationhdr a ON al.c_allocationhdr_id = a.c_allocationhdr_id
        WHERE al.c_invoice_id = p_c_invoice_id
          AND a.isactive = 'Y'
    )
        LOOP
            -- Convert to invoice currency if needed.
            IF allocationLine.currency_id = v_Currency_ID THEN
                v_LineAmt := allocationLine.amt;
            ELSE
                v_LineAmt := currencyconvert(
                        allocationLine.amt,
                        allocationLine.currency_id,
                        v_Currency_ID,
                        allocationLine.date,
                        NULL,
                        allocationLine.ad_client_id,
                        allocationLine.ad_org_id
                             );
            END IF;
            -- Multiply by v_MultiplierAP to get sign-consistent with grandtotal.
            -- AP invoices store allocation amounts as negative; * (-1) makes them positive.
            v_AllocatedAmt := v_AllocatedAmt + v_LineAmt * v_MultiplierAP;
        END LOOP;

    -- Open = GrandTotal - allocated (both in invoice-currency, sign-consistent).
    v_OpenAmt := v_GrandTotal - v_AllocatedAmt;

    -- Snap rounding noise to zero (same convention as invoiceOpenToDate).
    IF v_OpenAmt > -0.005 AND v_OpenAmt < 0.005 THEN
        v_OpenAmt := 0;
    END IF;

    RETURN v_OpenAmt;
END;
$BODY$;
