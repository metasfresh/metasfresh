DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_Footer (IN C_Order_ID numeric, IN AD_Language Character Varying(6));
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_Footer(IN C_Order_ID numeric, IN AD_Language Character Varying(6))
    RETURNS TABLE
            (
                paymentrule    character varying(60),
                paymentterm    character varying(60),
                discount1      numeric,
                discount2      numeric,
                discount_date1 text,
                discount_date2 text,
                cursymbol      character varying(10),
                documentnote   text
            )
AS
$$
SELECT COALESCE(reft.name, ref.name)                                                                            AS paymentrule,
       COALESCE(ptt.name, pt.name)                                                                              as paymentterm,
       (CASE
            WHEN pt.DiscountDays > 0 THEN (o.grandtotal + (o.grandtotal * pt.discount / 100))
            ELSE null END)                                                                                      AS discount1,
       (CASE
            WHEN pt.DiscountDays2 > 0 THEN (o.grandtotal + (o.grandtotal * pt.discount2 / 100))
            ELSE null END)                                                                                      AS discount2,
       to_char((o.DateOrdered - DiscountDays), 'dd.MM.YYYY')                                                    AS discount_date1,
       to_char((o.DateOrdered - DiscountDays2), 'dd.MM.YYYY')                                                   AS discount_date2,
       c.cursymbol,
       COALESCE(nullif(dtt.documentnote, ''),
                nullif(dt.documentnote, ''))                                                                    as documentnote
FROM C_Order o

         LEFT OUTER JOIN C_PaymentTerm pt on o.C_PaymentTerm_ID = pt.C_PaymentTerm_ID AND pt.isActive = 'Y'
         LEFT OUTER JOIN C_PaymentTerm_Trl ptt
                         on o.C_PaymentTerm_ID = ptt.C_PaymentTerm_ID AND ptt.AD_Language = $2 AND ptt.isActive = 'Y'

         LEFT OUTER JOIN AD_Ref_List ref ON o.PaymentRule = ref.Value AND ref.AD_Reference_ID = (SELECT AD_Reference_ID
                                                                                                 FROM AD_Reference
                                                                                                 WHERE name = '_Payment Rule'
                                                                                                   AND isActive = 'Y') AND
                                            ref.isActive = 'Y'
         LEFT OUTER JOIN AD_Ref_List_Trl reft
                         ON reft.AD_Ref_List_ID = ref.AD_Ref_List_ID AND reft.AD_Language = $2 AND reft.isActive = 'Y'
         INNER JOIN C_Currency c ON o.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'

-- take out document type notes
         INNER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
         LEFT OUTER JOIN C_DocType_Trl dtt
                         ON o.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'

WHERE o.C_Order_ID = $1
  AND o.isActive = 'Y'

$$
    LANGUAGE sql STABLE;
