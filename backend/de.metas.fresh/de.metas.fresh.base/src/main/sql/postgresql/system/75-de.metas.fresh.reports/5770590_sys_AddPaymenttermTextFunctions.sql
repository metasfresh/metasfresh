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




-------------



DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Invoice_Details_Footer (IN p_Invoice_ID numeric,
                                                                                        IN p_Language   Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Invoice_Details_Footer(IN p_Invoice_ID numeric,
                                                                                          IN p_Language   Character Varying(6))
    RETURNS TABLE
            (
                descriptionbottom text,
                p_term            text,
                paymentrule       character varying(60),
                textcenter        text,
                discount1         numeric,
                discount2         numeric,
                discount_date1    text,
                discount_date2    text,
                cursymbol         character varying(10),
                Incoterms         character varying,
                incotermlocation  character varying,
                deliveryrule      character varying,
                deliveryviarule   character varying,
                additionaltext    text
            )
AS
$$
SELECT i.descriptionbottom,

       (CASE
            WHEN i.PaymentTermText != '@paymenttermtext@' THEN i.PaymentTermText
                                                          ELSE
                                                              REPLACE(
                                                                      REPLACE(
                                                                              REPLACE(
                                                                                      COALESCE(ptt.name_invoice, ptt.name, pt.name_invoice, pt.name),
                                                                                      '$datum_netto',
                                                                                      TO_CHAR(i.dateinvoiced + pt.netdays, 'DD.MM.YYYY')
                                                                              ),
                                                                              '$datum_skonto_1',
                                                                              TO_CHAR(i.dateinvoiced::date + pt.discountdays, 'DD.MM.YYYY')
                                                                      ),
                                                                      '$datum_skonto_2',
                                                                      TO_CHAR(i.dateinvoiced::date + pt.discountdays2, 'DD.MM.YYYY'))

        END)                                                                                          AS p_term,
       COALESCE(reft.name, ref.name)                                                                  AS paymentrule,
       CASE
           WHEN (i.descriptionbottom IS NOT NULL AND i.descriptionbottom != '')
               THEN '<br><br><br>'
               ELSE ''
       END || COALESCE(dtt.documentnote, dt.documentnote)                                             AS textcenter,

       (CASE WHEN pt.DiscountDays > 0 THEN (i.grandtotal - (i.grandtotal * pt.discount / 100)) END)   AS discount1,
       (CASE WHEN pt.DiscountDays2 > 0 THEN (i.grandtotal - (i.grandtotal * pt.discount2 / 100)) END) AS discount2,
       TO_CHAR((i.DateInvoiced + DiscountDays), 'dd.MM.YYYY')                                         AS discount_date1,
       TO_CHAR((i.DateInvoiced + DiscountDays2), 'dd.MM.YYYY')                                        AS discount_date2,
       c.cursymbol,
       COALESCE(inc_trl.name, inc.name)                                                               AS Incoterms,
       i.incotermlocation,
       COALESCE(o_dr_trl.name, o_dr.name)                                                             AS deliveryrule,
       COALESCE(o_dvr_trl.name, o_dvr.name)                                                           AS deliveryviarule,
       report.getBPartner_CustomDocumentText(i.C_DocTypeTarget_ID, i.c_bpartner_id)                   AS AdditionalText
FROM C_Invoice i
         LEFT OUTER JOIN C_PaymentTerm pt ON i.C_PaymentTerm_ID = pt.C_PaymentTerm_ID
         LEFT OUTER JOIN C_PaymentTerm_Trl ptt ON i.C_PaymentTerm_ID = ptt.C_PaymentTerm_ID AND ptt.AD_Language = p_Language
         LEFT OUTER JOIN AD_Ref_List ref ON i.PaymentRule = ref.Value AND ref.AD_Reference_ID = 195
         LEFT OUTER JOIN AD_Ref_List_Trl reft ON reft.AD_Ref_List_ID = ref.AD_Ref_List_ID AND reft.AD_Language = p_Language
         LEFT OUTER JOIN c_doctype dt ON i.c_doctype_id = dt.c_doctype_id
         LEFT OUTER JOIN c_doctype_trl dtt ON dt.c_doctype_id = dtt.c_doctype_id AND dtt.AD_Language = p_Language
         INNER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID
         LEFT OUTER JOIN C_Incoterms inc ON i.c_incoterms_id = inc.c_incoterms_id
         LEFT OUTER JOIN C_Incoterms_trl inc_trl ON inc.c_incoterms_id = inc_trl.c_incoterms_id AND inc_trl.ad_language = p_Language
         LEFT OUTER JOIN C_Order o ON i.c_order_id = o.c_order_id
         LEFT OUTER JOIN AD_Ref_List o_dr ON o_dr.AD_Reference_ID = 151 AND o_dr.value = o.deliveryrule
         LEFT OUTER JOIN AD_Ref_List_Trl o_dr_trl ON o_dr.ad_ref_list_id = o_dr_trl.ad_ref_list_id AND o_dr_trl.ad_language = p_Language
         LEFT OUTER JOIN AD_Ref_List o_dvr ON o_dvr.AD_Reference_ID = 152 AND o_dvr.value = o.deliveryviarule
         LEFT OUTER JOIN AD_Ref_List_Trl o_dvr_trl ON o_dvr.ad_ref_list_id = o_dvr_trl.ad_ref_list_id AND o_dvr_trl.ad_language = p_Language

WHERE i.C_Invoice_ID = p_Invoice_ID

$$
    LANGUAGE sql STABLE
;
