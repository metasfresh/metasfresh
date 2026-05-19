DROP FUNCTION IF EXISTS computeProductBOMCost(numeric, date, numeric, numeric, numeric, numeric);

CREATE OR REPLACE FUNCTION computeProductBOMCost(
    p_PP_Product_BOM_ID  numeric,
    p_date               date,
    p_acctschema_id      numeric,
    p_m_costelement_id   numeric,
    p_ad_client_id       numeric,
    p_ad_org_id          numeric
)
RETURNS numeric
LANGUAGE sql STABLE
COST 100
AS
$func$
SELECT COALESCE(SUM(
    CASE WHEN bl.IsQtyPercentage = 'Y'
        THEN bl.QtyBatch / 100 * cost.unit_cost
        ELSE bl.QtyBOM * cost.unit_cost
    END
), 0)
FROM PP_Product_BOMLine bl
CROSS JOIN LATERAL (
    SELECT COALESCE(
        getCurrentCost(
            bl.M_Product_ID,
            bl.C_UOM_ID,
            p_date,
            p_acctschema_id,
            p_m_costelement_id,
            p_ad_client_id,
            p_ad_org_id
        ), 0) AS unit_cost
) cost
WHERE bl.PP_Product_BOM_ID = p_PP_Product_BOM_ID
  AND bl.IsActive = 'Y'
$func$;


DROP FUNCTION IF EXISTS PP_Product_BOM_Recursive(numeric,
                                                 varchar)
;

CREATE OR REPLACE FUNCTION PP_Product_BOM_Recursive(p_PP_Product_BOM_ID numeric,
                                                    p_ad_language       varchar)
    RETURNS table
            (
                Line            text,
                Parent_Line     text,
                ProductValue    varchar,
                ProductName     varchar,
                QtyBOM          numeric,
                Percentage      numeric,
                UOMSymbol       varchar,
                --
                depth           integer,
                M_Product_ID    numeric,
                IsQtyPercentage char(1),
                C_UOM_ID        numeric,
                path            integer[],
                cost            numeric
            )
AS
$BODY$
    --
WITH RECURSIVE bomNode AS ((SELECT ARRAY [1::integer]                      AS path,
                                   NULL::integer[]                         AS parent_path,
                                   1                                       AS depth,
                                   bomProduct.Value                        AS ProductValue,
                                   COALESCE(pt.Name, bomProduct.Name)      AS ProductName,
                                   bomProduct.M_Product_ID,
                                   bomProduct.IsBOM,
                                   bom.PP_Product_BOM_ID,
                                   'N'::char(1)                            AS IsQtyPercentage,
                                   ROUND(1::numeric, uom.StdPrecision)     AS QtyBOM,
                                   NULL::numeric                           AS Percentage,
                                   COALESCE(uom.UOMSymbol, uomt.UOMSymbol) AS UOMSymbol,
                                   uom.C_UOM_ID,
                                   bom.ad_org_id,
                                   bom.ad_client_id
                            FROM PP_Product_BOM bom
                                     INNER JOIN M_Product bomProduct ON bomProduct.M_Product_ID = bom.M_Product_ID
                                     LEFT OUTER JOIN M_Product_Trl pt ON bomProduct.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language
                                AND pt.isActive = 'Y'
                                     LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = COALESCE(bom.C_UOM_ID, bomProduct.C_UOM_ID)
                                     LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.IsActive = 'Y' AND uomt.AD_Language = p_ad_language
                            WHERE bom.PP_Product_BOM_ID = PP_Product_BOM_Recursive.p_PP_Product_BOM_ID)
                           --
                           UNION ALL
                           --
                           (SELECT parent.path || (ROW_NUMBER() OVER (PARTITION BY bomLine.PP_Product_BOM_ID ORDER BY bomLine.PP_Product_BOMLine_ID))::integer AS path,
                                   parent.path                                                                                                                 AS parent_path,
                                   parent.depth + 1                                                                                                            AS depth,
                                   bomLineProduct.Value                                                                                                        AS ProductValue,
                                   COALESCE(pt.Name, bomLineProduct.Name)                                                                                      AS ProductName,
                                   bomLineProduct.M_Product_ID,
                                   bomLineProduct.IsBOM,
                                   (CASE
                                        WHEN bomLineProduct.IsBOM = 'Y'
                                            THEN (SELECT bom.PP_Product_BOM_ID
                                                  FROM PP_Product_BOM bom
                                                  WHERE bom.M_Product_ID = bomLineProduct.M_Product_ID
                                                    AND bom.IsActive = 'Y'
                                                    AND (bom.validto >= NOW() OR bom.validto IS NULL)
                                                  ORDER BY bom.validfrom DESC, bom.PP_Product_BOM_ID DESC
                                                  LIMIT 1)
                                            ELSE NULL
                                    END)::numeric(10, 0)                                                                                                       AS PP_Product_BOM_ID,
                                   bomLine.IsQtyPercentage,
                                   (CASE WHEN bomLine.IsQtyPercentage = 'N' THEN ROUND(bomLine.QtyBOM, uom.StdPrecision) ELSE NULL END)                        AS QtyBOM,
                                   (CASE WHEN bomLine.IsQtyPercentage = 'Y' THEN ROUND(bomLine.QtyBatch, 2) ELSE NULL END)                                     AS Percentage,
                                   COALESCE(uom.UOMSymbol, uomt.UOMSymbol)                                                                                     AS UOMSymbol,
                                   uom.C_UOM_ID,
                                   parent.ad_org_id,
                                   parent.ad_client_id
                            FROM bomNode parent
                                     INNER JOIN PP_Product_BOMLine bomLine ON bomLine.PP_Product_BOM_ID = parent.PP_Product_BOM_ID
                                     INNER JOIN M_Product bomLineProduct ON bomLineProduct.M_Product_ID = bomLine.M_Product_ID
                                     LEFT OUTER JOIN M_Product_Trl pt ON bomLineProduct.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language
                                AND pt.isActive = 'Y'
                                     LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = bomLine.C_UOM_ID
                                     LEFT OUTER JOIN C_UOM_Trl uomt ON bomLine.C_UOM_ID = uomt.C_UOM_ID AND uomt.IsActive = 'Y' AND uomt.AD_Language = p_ad_language
                            WHERE bomLine.IsActive = 'Y'
                            ORDER BY bomLine.PP_Product_BOMLine_ID))
--
SELECT ARRAY_TO_STRING(n.path, '.') || '.'        AS Line,
       ARRAY_TO_STRING(n.parent_path, '.') || '.' AS Parent_Line,
       n.ProductValue,
       n.ProductName,
       n.QtyBOM,
       n.Percentage,
       n.UOMSymbol,
       --
       n.depth,
       n.M_Product_ID,
       n.IsQtyPercentage,
       n.C_UOM_ID,
       n.path,

       (CASE
            WHEN n.PP_Product_BOM_ID > 0
                THEN computeProductBOMCost(
                    p_PP_Product_BOM_ID => n.PP_Product_BOM_ID,
                    p_date => NOW()::date,
                    p_acctschema_id => (SELECT ci.C_AcctSchema1_ID FROM AD_ClientInfo ci WHERE n.AD_Client_ID = ci.AD_Client_ID),
                    p_m_costelement_id => (SELECT M_CostElement_ID FROM M_CostElement WHERE IsActive = 'Y' AND CostElementType = 'M' AND EXISTS (SELECT 1 FROM C_AcctSchema cas WHERE cas.CostingMethod = M_CostElement.CostingMethod AND cas.C_AcctSchema_ID = (SELECT ci.C_AcctSchema1_ID FROM AD_ClientInfo ci WHERE n.AD_Client_ID = ci.AD_Client_ID))),
                    p_ad_client_id => n.ad_client_id,
                    p_ad_org_id => n.AD_Org_ID
                     )
                ELSE getCurrentCost(
                        (n.M_Product_ID),
                        (SELECT p.C_UOM_ID FROM M_Product p WHERE p.M_Product_ID = n.M_Product_ID),
                        NOW()::date,
                        (SELECT ci.C_AcctSchema1_ID FROM AD_ClientInfo ci WHERE n.AD_Client_ID = ci.AD_Client_ID),
                        (SELECT M_CostElement_ID
                         FROM M_CostElement
                         WHERE IsActive = 'Y'
                           AND CostElementType = 'M'
                           AND EXISTS (SELECT 1
                                       FROM C_AcctSchema cas
                                       WHERE cas.CostingMethod = M_CostElement.CostingMethod
                                         AND cas.C_AcctSchema_ID = (SELECT ci.C_AcctSchema1_ID FROM AD_ClientInfo ci WHERE n.AD_Client_ID = ci.AD_Client_ID))),
                        (n.AD_Client_ID),
                        (n.AD_Org_ID)
                     )
        END):: numeric                            AS Cost
FROM bomNode n
ORDER BY path
    ;
$BODY$
    LANGUAGE sql STABLE
                 COST 100
;


DROP FUNCTION IF EXISTS PP_Product_BOM_Recursive_Report(numeric)
;

CREATE OR REPLACE FUNCTION PP_Product_BOM_Recursive_Report(p_PP_Product_BOM_ID numeric)
    RETURNS table
            (
                Line         text,
                ProductValue varchar,
                ProductName  varchar,
                QtyBOM       numeric,
                Percentage   numeric,
                UOMSymbol    varchar,
                Cost         numeric
            )
AS
$BODY$
SELECT t.Line,
       t.ProductValue,
       t.ProductName,
       t.QtyBOM,
       t.Percentage,
       t.UOMSymbol,
       t.Cost
FROM PP_Product_BOM_Recursive(PP_Product_BOM_Recursive_Report.p_PP_Product_BOM_ID, NULL) t
ORDER BY t.path
$BODY$
    LANGUAGE sql STABLE
                 COST 100
;
