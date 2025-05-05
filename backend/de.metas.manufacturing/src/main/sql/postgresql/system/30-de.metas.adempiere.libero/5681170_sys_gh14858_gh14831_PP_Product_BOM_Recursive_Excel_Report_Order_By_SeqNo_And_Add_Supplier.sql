/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

DROP FUNCTION IF EXISTS PP_Product_BOM_Recursive(numeric,
                                                 character varying)
;

DROP FUNCTION IF EXISTS PP_Product_BOM_Recursive_Report(numeric)
;

CREATE OR REPLACE FUNCTION pp_product_bom_recursive(p_pp_product_bom_id numeric,
                                                    p_ad_language       character varying)
    RETURNS TABLE
            (
                line            text,
                parent_line     text,
                productvalue    character varying,
                productname     character varying,
                qtybom          numeric,
                percentage      numeric,
                uomsymbol       character varying,
                depth           integer,
                m_product_id    numeric,
                isqtypercentage character,
                c_uom_id        numeric,
                path            integer[],
                supplier        text
            )
    STABLE
    LANGUAGE sql
AS
$$
    --
WITH RECURSIVE bomNode AS ((SELECT ARRAY [1::integer]                          AS path,
                                   NULL::integer[]                             AS parent_path,
                                   1                                           AS depth,
                                   bomProduct.Value                            AS ProductValue,
                                   COALESCE(pt.Name, bomProduct.Name)          AS ProductName,
                                   bomProduct.M_Product_ID,
                                   bomProduct.IsBOM,
                                   bom.PP_Product_BOM_ID,
                                   'N'::char(1)                                AS IsQtyPercentage,
                                   ROUND(1::numeric, uom.StdPrecision)         AS QtyBOM,
                                   NULL::numeric                               AS Percentage,
                                   COALESCE(uom.UOMSymbol, uomt.UOMSymbol)     AS UOMSymbol,
                                   uom.C_UOM_ID,
                                   (SELECT CONCAT_WS(' ', bPartner.name, bPartner.name2, bPartner.name3)
                                    FROM C_BPartner_Product bPartnerProduct
                                             INNER JOIN C_BPartner bPartner ON bPartnerProduct.c_bpartner_id = bPartner.c_bpartner_id
                                    WHERE bomProduct.m_product_id = bPartnerProduct.m_product_id
                                      AND bPartnerProduct.iscurrentvendor = 'Y'
                                      AND bPartnerProduct.isActive = 'Y'
                                      AND bPartnerProduct.usedforvendor = 'Y') AS Supplier
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
                           (SELECT parent.path || (ROW_NUMBER() OVER (PARTITION BY bomLine.PP_Product_BOM_ID ORDER BY bomLine.line, bomLine.PP_Product_BOMLine_ID))::integer AS path,
                                   parent.path                                                                                                                               AS parent_path,
                                   parent.depth + 1                                                                                                                          AS depth,
                                   bomLineProduct.Value                                                                                                                      AS ProductValue,
                                   COALESCE(pt.Name, bomLineProduct.Name)                                                                                                    AS ProductName,
                                   bomLineProduct.M_Product_ID,
                                   bomLineProduct.IsBOM,
                                   (CASE
                                        WHEN bomLineProduct.IsBOM = 'Y'
                                            THEN (SELECT bom.PP_Product_BOM_ID
                                                  FROM PP_Product_BOM bom
                                                  WHERE bom.M_Product_ID = bomLineProduct.M_Product_ID
                                                    AND bom.IsActive = 'Y'
                                                    AND bom.Value = bomLineProduct.Value
                                                  ORDER BY bom.PP_Product_BOM_ID
                                                  LIMIT 1)
                                            ELSE NULL
                                    END)::numeric(10, 0)                                                                                                                     AS PP_Product_BOM_ID,
                                   bomLine.IsQtyPercentage,
                                   (CASE WHEN bomLine.IsQtyPercentage = 'N' THEN ROUND(bomLine.QtyBOM, uom.StdPrecision) ELSE NULL END)                                      AS QtyBOM,
                                   (CASE WHEN bomLine.IsQtyPercentage = 'Y' THEN ROUND(bomLine.QtyBatch, 2) ELSE NULL END)                                                   AS Percentage,
                                   COALESCE(uom.UOMSymbol, uomt.UOMSymbol)                                                                                                   AS UOMSymbol,
                                   uom.C_UOM_ID,
                                   (SELECT CONCAT_WS(' ', bPartner.name, bPartner.name2, bPartner.name3)
                                    FROM C_BPartner_Product bPartnerProduct
                                             INNER JOIN C_BPartner bPartner ON bPartnerProduct.c_bpartner_id = bPartner.c_bpartner_id
                                    WHERE bomLine.m_product_id = bPartnerProduct.m_product_id
                                      AND bPartnerProduct.iscurrentvendor = 'Y'
                                      AND bPartnerProduct.isActive = 'Y'
                                      AND bPartnerProduct.usedforvendor = 'Y')                                                                                               AS Supplier
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
       n.Supplier
FROM bomNode n
ORDER BY path
    ;
$$
;

ALTER FUNCTION pp_product_bom_recursive(numeric, varchar) OWNER TO metasfresh
;

CREATE OR REPLACE FUNCTION pp_product_bom_recursive_report(p_pp_product_bom_id numeric)
    RETURNS TABLE
            (
                line         text,
                productvalue character varying,
                productname  character varying,
                qtybom       numeric,
                percentage   numeric,
                uomsymbol    character varying,
                supplier     text
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT t.Line,
       t.ProductValue,
       t.ProductName,
       t.QtyBOM,
       t.Percentage,
       t.UOMSymbol,
       t.Supplier
FROM PP_Product_BOM_Recursive(PP_Product_BOM_Recursive_Report.p_PP_Product_BOM_ID, NULL) t
ORDER BY t.path
$$
;

ALTER FUNCTION pp_product_bom_recursive_report(numeric) OWNER TO metasfresh
;