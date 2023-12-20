DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Request_Details(numeric,
                                                                                 varchar)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Request_Details(record_id   numeric,
                                                                         ad_language character varying)
    RETURNS TABLE
            (
                line          numeric,
                qty           numeric,
                product_value character varying,
                product_name  character varying,
                description   text,
                priceactual   numeric,
                linenetamt    numeric,
                cursymbol     character varying

            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT rl.line,
       rl.qty,
       p.value AS product_value,
       p.name  AS product_name,
       rl.description,
       rl.priceactual,
       rl.linenetamt,
       c.cursymbol

FROM M_RequisitionLine rl
         INNER JOIN M_Requisition r ON rl.M_Requisition_ID = r.M_Requisition_ID AND r.isActive = 'Y'
         LEFT OUTER JOIN M_Product p ON rl.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
         LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
    -- Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON rl.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
         LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'

         LEFT JOIN C_Currency c ON rl.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'

WHERE r.M_Requisition_ID = $1
  AND r.isActive = 'Y'
ORDER BY rl.line

$$
;

ALTER FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Request_Details(numeric, varchar) OWNER TO metasfresh
;