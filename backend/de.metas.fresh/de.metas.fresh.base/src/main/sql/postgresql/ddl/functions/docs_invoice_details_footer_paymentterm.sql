CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_invoice_details_footer_paymentterm(p_C_Invoice_ID numeric)
    RETURNS TABLE
            (
                paytermtext text
            )
AS
$BODY$
DECLARE
    v_c_paymentterm_id NUMERIC := -1;
    v_Amt              NUMERIC := 0;
    v_date             date;
    v_c_curency_id     NUMERIC := -1;
    v_currencyISOCode  text ;

BEGIN
    -- Retrieve essential invoice data: payment term ID, grand total, invoice date, and currency ID.
    SELECT c_paymentterm_id, grandtotal, DateInvoiced, C_Currency_ID
    INTO v_c_paymentterm_id, v_Amt, v_date, v_c_curency_id
    FROM C_Invoice
    WHERE C_Invoice_ID = p_C_Invoice_ID;

    -- Retrieve the currency's ISO code (e.g., 'EUR') based on the invoice's currency ID.
    SELECT iso_code
    INTO v_currencyISOCode
    FROM c_currency
    WHERE c_currency_id = v_c_curency_id;

    -- The main logic: construct the output text.
    -- RETURN QUERY is used to execute a SELECT statement and return its result set.
    RETURN QUERY
        SELECT COALESCE(line1 || '<br>', '') || COALESCE(line2 || '<br>', '') || COALESCE(line3 || '<br>', '') || COALESCE(line4, '')
        FROM (
                 -- Select distinct payment term details to avoid duplicates.
                 SELECT DISTINCT discountdays,
                                 pt_new.name         AS line1,

                                 -- CASE statement to generate the discount payment line if a discount exists.
                                 CASE
                                     WHEN pt_new.discountdays > 0 THEN
                                         '@paymentterm.PayUntil@ ' || TO_CHAR(
                                                 (v_date + (pt_new.discountdays || ' days')::interval)::date,
                                                 'DD.MM.YYYY')
                                             || ' @paymentterm.payby@ ' || ROUND(v_Amt * (100 - pt_new.discount) / 100, 2) || ' ' || v_currencyISOCode
                                 END                 AS line2,

                                 -- CASE statement to generate the net payment line if both discount and net days exist.
                                 CASE
                                     WHEN pt_new.discountdays > 0 AND pt_new.netdays > 0 THEN
                                         '@paymentterm.PayUntil@ ' || TO_CHAR(
                                                 (v_date + (pt_new.netdays || ' days')::interval)::date,
                                                 'DD.MM.YYYY')
                                             || ' @paymentterm.payby@ ' || v_Amt || ' ' || v_currencyISOCode
                                 END                 AS line3,

                                 -- The name of the invoice.
                                 pt_new.name_invoice AS line4

                 FROM c_paymentterm pt_new
                 -- Link the payment term to the one retrieved from the invoice.
                 WHERE pt_new.c_paymentterm_id = v_c_paymentterm_id) data;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
;
