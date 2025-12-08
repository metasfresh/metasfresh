DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_POS_Payments(p_record_id numeric, p_ad_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_POS_Payments(p_record_id numeric, p_ad_language Character Varying(6))

    RETURNS TABLE
            (
                C_POS_Payment_id numeric(10, 0),
                paidamt          numeric,
                cursymbol        varchar,
                paymentrule      varchar,
                ad_org_id        numeric
            )
AS
$$
SELECT posp.C_POS_Payment_id,
       posp.amount                   as paydamount,
       c.cursymbol,
       coalesce(rflt.name, rfl.name) as paymentrule,
       pos.ad_org_id

FROM C_POS_Order pos

         INNER JOIN C_POS_Payment posp on pos.c_pos_order_id = posp.c_pos_order_id
         INNER JOIN C_Currency c ON pos.C_Currency_ID = c.C_Currency_ID
         INNER JOIN ad_ref_list rfl on ad_reference_id = 541891 and rfl.value = posp.pospaymentmethod
         INNER JOIN ad_ref_list_trl rflt
                    on rfl.ad_ref_list_id = rflt.ad_ref_list_id and rflt.ad_language = p_ad_language

WHERE pos.C_POS_Order_ID = p_record_id
ORDER BY posp.C_POS_Payment_id

$$
    LANGUAGE sql STABLE
;