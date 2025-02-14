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
                deliveryviarule   character varying
            )
AS
$$
SELECT i.descriptionbottom,
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
               TO_CHAR(i.dateinvoiced::date + pt.discountdays2, 'DD.MM.YYYY')
       )                                                                                              AS p_term,
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
       COALESCE(o_dvr_trl.name, o_dvr.name)                                                           AS deliveryviarule
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
LANGUAGE sql STABLE;
