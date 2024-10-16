DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_POS(p_record_id numeric,
                                                                          p_ad_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_POS(p_record_id numeric,
                                                                             p_ad_language Character Varying(6))

    RETURNS TABLE
            (
                c_pos_orderline_id numeric(10, 0),
                documentno         character varying,
                datetrx            timestamp with time zone,
                cashier            character varying,
                Qty                numeric,
                uomsymbol          character varying,
                p_value            character varying,
                product            character varying,
                price              numeric,
                amount             numeric,
                taxamt             numeric,
                grandtotal         numeric,
                totaltax           numeric,
                taxrate            numeric,
                cursymbol          varchar,
                ad_org_id          numeric

            )
AS
$$
SELECT posl.c_pos_orderline_id,
       documentno,
       datetrx,
       cashier.name                                                                AS cashier,
       (case when posl.catchweight is not null then posl.catchweight else qty end) as qty,
       uom.uomsymbol,
       p.value                                                                     AS p_value,
       COALESCE(pt.Name, p.name)                                                   AS product,
       price,
       amount,
       posl.taxamt,
       grandtotal,
       pos.taxamt                                                                  AS totaltax,
       t.rate                                                                      AS taxrate,
       c.cursymbol,
       pos.ad_org_id

FROM C_POS_Order pos
         INNER JOIN c_pos_orderline posl ON pos.c_pos_order_id = posl.c_pos_order_id
         INNER JOIN C_UOM uom on coalesce(posl.catch_uom_id, posl.c_uom_id) = uom.c_uom_id
         INNER JOIN M_Product p ON posl.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_Product_Trl pt ON posl.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language
         LEFT OUTER JOIN C_Tax t ON posl.C_Tax_ID = t.C_Tax_ID
         INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
         INNER JOIN C_Currency c ON pos.C_Currency_ID = c.C_Currency_ID
         INNER JOIN AD_User cashier ON pos.cashier_id = cashier.ad_user_id
WHERE pos.C_POS_Order_ID = p_record_id
ORDER BY posl.c_pos_orderline_id

$$
    LANGUAGE sql STABLE
;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_POS_Payments(p_record_id numeric, p_ad_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_POS_Payments(p_record_id numeric, p_ad_language Character Varying(6))

    RETURNS TABLE
            (
                C_POS_Payment_id numeric(10, 0),
                paidamt          numeric,
                ChangeBackAmount   numeric,
                cursymbol        varchar,
                paymentrule      varchar,
                ad_org_id        numeric
            )
AS
$$
SELECT posp.C_POS_Payment_id,
       (CASE WHEN posp.POSPaymentMethod = 'CASH' THEN posp.amounttendered ELSE posp.amount END) AS paidamt,
       posp.ChangeBackAmount,
       c.cursymbol,
       COALESCE(rflt.name, rfl.name) || ' ' || coalesce(posp.POSPaymentProcessingSummary, '')   as paymentrule,
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