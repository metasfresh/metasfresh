﻿DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details_Footer (IN p_Order_ID numeric,
                                                                                               IN p_Language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details_Footer(IN p_Order_ID numeric,
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
                Incoterms         character varying,
                incotermlocation  character varying,
                descriptionbottom character varying
            )
AS
$$
SELECT COALESCE(reft.name, ref.name)                                          AS paymentrule,
       REPLACE(REPLACE(REPLACE(COALESCE(ptt.name, pt.name),
                               '$datum_netto',
                               TO_CHAR(o.dateordered + pt.netdays, 'DD.MM.YYYY')),
                       '$datum_skonto_1',
                       TO_CHAR(o.dateordered::date + pt.discountdays, 'DD.MM.YYYY')),
               '$datum_skonto_2',
               TO_CHAR(o.dateordered::date + pt.discountdays2, 'DD.MM.YYYY')) AS paymentterm,
       (CASE
            WHEN pt.DiscountDays > 0
                THEN (o.grandtotal + (o.grandtotal * pt.discount / 100))
        END)                                                                  AS discount1,
       (CASE
            WHEN pt.DiscountDays2 > 0
                THEN (o.grandtotal + (o.grandtotal * pt.discount2 / 100))
        END)                                                                  AS discount2,
       TO_CHAR((o.DateOrdered - DiscountDays), 'dd.MM.YYYY')                  AS discount_date1,
       TO_CHAR((o.DateOrdered - DiscountDays2), 'dd.MM.YYYY')                 AS discount_date2,
       c.cursymbol,
       COALESCE(inc_trl.name, inc.name)                                       AS Incoterms,
       o.incotermlocation,
       o.descriptionbottom


FROM C_Order o

         LEFT OUTER JOIN C_PaymentTerm pt ON o.C_PaymentTerm_ID = pt.C_PaymentTerm_ID AND pt.isActive = 'Y'
         LEFT OUTER JOIN C_PaymentTerm_Trl ptt ON o.C_PaymentTerm_ID = ptt.C_PaymentTerm_ID AND ptt.AD_Language = p_Language AND ptt.isActive = 'Y'
         LEFT OUTER JOIN AD_Ref_List ref ON o.PaymentRule = ref.Value AND ref.AD_Reference_ID = (SELECT AD_Reference_ID
                                                                                                 FROM AD_Reference
                                                                                                 WHERE name = '_Payment Rule'
                                                                                                   AND isActive = 'Y') AND ref.isActive = 'Y'
         LEFT OUTER JOIN AD_Ref_List_Trl reft ON reft.AD_Ref_List_ID = ref.AD_Ref_List_ID AND reft.AD_Language = p_Language AND reft.isActive = 'Y'
         INNER JOIN C_Currency c ON o.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
         LEFT OUTER JOIN C_Incoterms inc ON o.c_incoterms_id = inc.c_incoterms_id
         LEFT OUTER JOIN C_Incoterms_trl inc_trl ON inc.c_incoterms_id = inc_trl.c_incoterms_id AND inc_trl.ad_language = p_Language

WHERE o.C_Order_ID = p_Order_ID
  AND o.isActive = 'Y'

$$
    LANGUAGE sql STABLE
;
