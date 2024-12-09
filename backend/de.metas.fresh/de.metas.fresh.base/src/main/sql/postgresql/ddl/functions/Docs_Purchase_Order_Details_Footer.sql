<<<<<<< HEAD
﻿DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details_Footer ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details_Footer ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE 
(
	paymentrule character varying(60),
	paymentterm character varying(60),
	discount1 numeric,
	discount2 numeric,
	discount_date1 text,
	discount_date2 text,
	cursymbol character varying(10)
)
AS
$$
SELECT
	COALESCE(reft.name, ref.name) AS paymentrule,
	COALESCE(ptt.name, pt.name) as paymentterm,
	(CASE WHEN pt.DiscountDays > 0 THEN (o.grandtotal + (o.grandtotal * pt.discount / 100)) ELSE null END) AS discount1,
	(CASE WHEN pt.DiscountDays2 > 0 THEN (o.grandtotal + (o.grandtotal * pt.discount2 / 100)) ELSE null END) AS discount2,
	to_char((o.DateOrdered - DiscountDays),'dd.MM.YYYY') AS discount_date1,
	to_char((o.DateOrdered - DiscountDays2),'dd.MM.YYYY') AS discount_date2,
	c.cursymbol
	
FROM C_Order o

LEFT OUTER JOIN C_PaymentTerm pt on o.C_PaymentTerm_ID = pt.C_PaymentTerm_ID AND pt.isActive = 'Y'
LEFT OUTER JOIN C_PaymentTerm_Trl ptt on o.C_PaymentTerm_ID = ptt.C_PaymentTerm_ID AND ptt.AD_Language = $2 AND ptt.isActive = 'Y'

LEFT OUTER JOIN AD_Ref_List ref ON o.PaymentRule = ref.Value AND ref.AD_Reference_ID = (SELECT AD_Reference_ID FROM AD_Reference WHERE name = '_Payment Rule' AND isActive = 'Y') AND ref.isActive = 'Y'
LEFT OUTER JOIN AD_Ref_List_Trl reft ON reft.AD_Ref_List_ID = ref.AD_Ref_List_ID AND reft.AD_Language = $2 AND reft.isActive = 'Y'
INNER JOIN C_Currency c ON o.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'

WHERE o.C_Order_ID = $1 AND o.isActive = 'Y'

$$
LANGUAGE sql STABLE;
=======
﻿DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details_Footer (IN record_id  numeric,
                                                                                               IN p_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details_Footer(IN record_id  numeric,
                                                                                                 IN p_language Character Varying(6))
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
                descriptionbottom character varying,
                deliveryrule      character varying,
                deliveryviarule   character varying
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
       o.descriptionbottom,
       COALESCE(o_dr_trl.name, o_dr.name)                                     AS deliveryrule,
       COALESCE(o_dvr_trl.name, o_dvr.name)                                   AS deliveryviarule

FROM C_Order o

         LEFT OUTER JOIN C_PaymentTerm pt ON o.C_PaymentTerm_ID = pt.C_PaymentTerm_ID
         LEFT OUTER JOIN C_PaymentTerm_Trl ptt ON o.C_PaymentTerm_ID = ptt.C_PaymentTerm_ID AND ptt.AD_Language = p_Language
         LEFT OUTER JOIN AD_Ref_List ref ON o.PaymentRule = ref.Value AND ref.AD_Reference_ID = (SELECT AD_Reference_ID
                                                                                                 FROM AD_Reference
                                                                                                 WHERE name = '_Payment Rule')
         LEFT OUTER JOIN AD_Ref_List_Trl reft ON reft.AD_Ref_List_ID = ref.AD_Ref_List_ID AND reft.AD_Language = p_Language
         INNER JOIN C_Currency c ON o.C_Currency_ID = c.C_Currency_ID
         LEFT OUTER JOIN C_Incoterms inc ON o.c_incoterms_id = inc.c_incoterms_id
         LEFT OUTER JOIN C_Incoterms_trl inc_trl ON inc.c_incoterms_id = inc_trl.c_incoterms_id AND inc_trl.ad_language = p_Language
         LEFT OUTER JOIN AD_Ref_List o_dr ON o_dr.AD_Reference_ID = 151 AND o_dr.value = o.deliveryrule
         LEFT OUTER JOIN AD_Ref_List_Trl o_dr_trl ON o_dr.ad_ref_list_id = o_dr_trl.ad_ref_list_id AND o_dr_trl.ad_language = p_Language
         LEFT OUTER JOIN AD_Ref_List o_dvr ON o_dvr.AD_Reference_ID = 152 AND o_dvr.value = o.deliveryviarule
         LEFT OUTER JOIN AD_Ref_List_Trl o_dvr_trl ON o_dvr.ad_ref_list_id = o_dvr_trl.ad_ref_list_id AND o_dvr_trl.ad_language = p_Language
WHERE o.C_Order_ID = record_id
  AND o.isActive = 'Y'

$$
    LANGUAGE sql STABLE
;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
