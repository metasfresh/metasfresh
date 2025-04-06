DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_Footer (IN p_Order_ID numeric,
                                                                                            IN p_Language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_Footer(IN p_Order_ID numeric,
                                                                                              IN p_Language Character Varying(6))
    RETURNS TABLE
            (
                paymentrule       character varying(60),
                paymentterm       character varying(60),
                discount1         numeric,
                discount2         numeric,
                discount_date1    text,
                discount_date2    text,
                cursymbol         character varying(10),
                documentnote      text,
                descriptionbottom text,
                subject           character varying,
                textsnippet       character varying,
                Incoterms         character varying,
                incotermlocation  character varying,
                additionaltext    text
            )
AS
$$
SELECT COALESCE(reft.name, ref.name)                          AS paymentrule,
       COALESCE(ptt.name, pt.name)                            AS paymentterm,
       (CASE
            WHEN pt.DiscountDays > 0 THEN (o.grandtotal + (o.grandtotal * pt.discount / 100))
        END)                                                  AS discount1,
       (CASE
            WHEN pt.DiscountDays2 > 0 THEN (o.grandtotal + (o.grandtotal * pt.discount2 / 100))
        END)                                                  AS discount2,
       TO_CHAR((o.DateOrdered - DiscountDays), 'dd.MM.YYYY')  AS discount_date1,
       TO_CHAR((o.DateOrdered - DiscountDays2), 'dd.MM.YYYY') AS discount_date2,
       c.cursymbol,
       COALESCE(NULLIF(dtt.documentnote, ''),
                NULLIF(dt.documentnote, ''))                  AS documentnote,
       o.descriptionbottom,
       otb.subject,
       otb.textsnippet,
       COALESCE(inc_trl.name, inc.name)                       AS Incoterms,
       o.incotermlocation,
       prt.additionaltext
FROM C_Order o

         LEFT OUTER JOIN C_PaymentTerm pt ON o.C_PaymentTerm_ID = pt.C_PaymentTerm_ID
         LEFT OUTER JOIN C_PaymentTerm_Trl ptt ON o.C_PaymentTerm_ID = ptt.C_PaymentTerm_ID AND ptt.AD_Language = p_Language

         LEFT OUTER JOIN AD_Ref_List ref ON o.PaymentRule = ref.Value AND ref.AD_Reference_ID = (SELECT AD_Reference_ID
                                                                                                 FROM AD_Reference
                                                                                                 WHERE name = '_Payment Rule')
         LEFT OUTER JOIN AD_Ref_List_Trl reft ON reft.AD_Ref_List_ID = ref.AD_Ref_List_ID AND reft.AD_Language = p_Language
         INNER JOIN C_Currency c ON o.C_Currency_ID = c.C_Currency_ID

    -- take out document type notes
         INNER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON o.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_Language
         LEFT OUTER JOIN de_metas_endcustomer_fresh_reports.getOrderTextBoilerPlate(p_Order_ID) otb ON TRUE
         LEFT OUTER JOIN C_Incoterms inc ON o.c_incoterms_id = inc.c_incoterms_id
         LEFT OUTER JOIN C_Incoterms_trl inc_trl ON inc.c_incoterms_id = inc_trl.c_incoterms_id AND inc_trl.ad_language = p_Language

         LEFT OUTER JOIN C_BPartner_DocType pdt ON pdt.c_doctype_id = o.C_DocTypeTarget_ID AND pdt.c_bpartner_id = o.c_bpartner_id
         LEFT OUTER JOIN C_BPartner_Report_Text prt ON pdt.c_bpartner_report_text_id = prt.c_bpartner_report_text_id

WHERE o.C_Order_ID = p_Order_ID;

$$
    LANGUAGE sql STABLE
;
