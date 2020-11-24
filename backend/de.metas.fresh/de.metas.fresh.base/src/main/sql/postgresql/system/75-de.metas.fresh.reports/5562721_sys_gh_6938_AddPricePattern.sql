DROP VIEW IF EXISTS RV_fresh_PriceList;

DROP FUNCTION IF EXISTS report.fresh_PriceList_Details_Report_With_PP_PI(numeric, numeric, numeric, character varying, text);

DROP FUNCTION IF EXISTS report.fresh_PriceList_Details_Report(numeric, numeric, numeric, character varying, text);



DROP FUNCTION IF EXISTS report.Current_Vs_Previous_Pricelist_Comparison_Report(p_C_BPartner_ID numeric, p_C_BP_Group_ID numeric, p_IsSoTrx text, p_AD_Language text, p_show_product_price_pi_flag text)
;



DROP FUNCTION IF EXISTS report.Current_Vs_Previous_Pricelist_Comparison_Report_With_PP_PI(p_C_BPartner_ID numeric, p_C_BP_Group_ID numeric, p_IsSoTrx text, p_AD_Language text, p_show_product_price_pi_flag text)
;


DROP VIEW IF EXISTS RV_fresh_PriceList_Comparison_With_PP_PI;
DROP VIEW IF EXISTS RV_fresh_PriceList_Comparison;



CREATE OR REPLACE VIEW RV_fresh_PriceList_Comparison_With_PP_PI AS


SELECT pp.AD_Org_ID,
       pp.AD_Client_ID,
       pp.Created,
       pp.CreatedBy,
       pp.Updated,
       pp.UpdatedBy,
       pp.IsActive,

       -- Displayed pricelist data
       pc.Name                                                                                    AS ProductCategory,
       pc.value                                                                                   AS ProductCategoryValue,
       p.M_Product_ID,
       p.Value,
       p.Name                                                                                     AS ProductName,
       pp.IsSeasonFixedPrice,
       hupip.Name                                                                                 AS ItemProductName,
       pm.Name                                                                                    AS PackingMaterialName,
	   COALESCE(ppa.PriceStd, pp.PriceStd)                                              AS PriceStd,
       CASE
           WHEN pl.priceprecision = 0
               THEN '#,##0'
           ELSE Substring('#,##0.000' FROM 0 FOR 7 + pl.priceprecision :: integer) END  AS PricePattern1,
       pp2.PriceStd                                                                     AS AltPriceStd,
       CASE
           WHEN pl.priceprecision = 0
               THEN '#,##0'
           ELSE Substring('#,##0.000' FROM 0 FOR 7 + pl2.priceprecision :: integer) END AS PricePattern2,
       CASE WHEN pp2.PriceStd IS NULL THEN 0 ELSE 1 END                                           AS hasaltprice,
       uom.UOMSymbol,
       COALESCE(ppa.Attributes, '')                                                               as attributes,
       pp.seqNo,

       -- Filter Columns
       bp.C_BPartner_ID,
       plv.M_Pricelist_Version_ID,
       plv2.M_Pricelist_Version_ID                                                                AS Alt_PriceList_Version_ID,

       -- Additional internal infos to be used
       pp.M_ProductPrice_ID,
       ppa.m_attributesetinstance_ID,
--        pp.m_hu_pi_item_product_id                                                 as PP_M_HU_PI_Item_Product_ID,
       pp.M_HU_PI_Item_Product_ID                                                              as M_HU_PI_Item_Product_ID,
       uom.X12DE355                                                                               as UOM_X12DE355,
       hupip.Qty                                                                                  as QtyCUsPerTU,
       it.m_hu_pi_version_id                                                                      AS m_hu_pi_version_id,
       c.iso_code                                                                                 as currency,
       c2.iso_code                                                                                as currency2

FROM M_ProductPrice pp

         INNER JOIN M_Product p ON pp.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'

    /** Get all BPartner and Product combinations.
     * IMPORTANT: Never use the query without BPartner Filter active
     */
         LEFT JOIN C_BPartner bp ON TRUE
         INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
         INNER JOIN C_UOM uom ON pp.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'


    /*
      * We know if there are packing instructions limited to the BPartner/product-combination. If so,
      * we will use only those. If not, we will use only the non limited ones
      */
         LEFT OUTER JOIN LATERAL
    (
    SELECT vip.M_HU_PI_Item_Product_ID, vip.hasPartner
    FROM Report.Valid_PI_Item_Product_V vip
        /* WHERE isInfiniteCapacity = 'N' task 09045/09788: we can also export PiiPs with infinite capacity */
    WHERE p.M_Product_ID = vip.M_Product_ID

    ) bp_ip ON TRUE

         LEFT OUTER JOIN LATERAL
    (
    SELECT M_ProductPrice_ID, M_Attributesetinstance_ID, PriceStd, IsActive, M_HU_PI_Item_Product_ID, Attributes, Signature
    FROM report.fresh_AttributePrice ppa
    WHERE ppa.isActive = 'Y'
      AND ppa.M_ProductPrice_ID = pp.M_ProductPrice_ID
      AND (ppa.m_hu_pi_item_product_id = bp_ip.m_hu_pi_item_product_id OR ppa.m_hu_pi_item_product_id IS NULL)
      AND ppa.m_pricelist_version_id = pp.m_pricelist_version_id
    ) ppa on true

         LEFT OUTER JOIN m_hu_pi_item_product hupip ON pp.m_hu_pi_item_product_ID = hupip.m_hu_pi_item_product_id and hupip.isActive = 'Y'
         LEFT OUTER JOIN m_hu_pi_item it ON hupip.M_HU_PI_Item_ID = it.M_HU_PI_Item_ID AND it.isActive = 'Y'
         LEFT OUTER JOIN m_hu_pi_item pmit ON it.m_hu_pi_version_id = pmit.m_hu_pi_version_id AND pmit.itemtype::TEXT = 'PM'::TEXT AND pmit.isActive = 'Y'
         LEFT OUTER JOIN m_hu_packingmaterial pm ON pmit.m_hu_packingmaterial_id = pm.m_hu_packingmaterial_id AND pm.isActive = 'Y'

         INNER JOIN M_PriceList_Version plv ON pp.M_PriceList_Version_ID = plv.M_PriceList_Version_ID AND plv.IsActive = 'Y'
    /*
     Get Comparison Prices
    */

    /* Get all PriceList_Versions of the PriceList (we need all available PriceList_Version_IDs for outside filtering)
     limited to the same PriceList because the Parameter validation rule is enforcing this */
         LEFT JOIN M_PriceList_Version plv2 ON plv.M_PriceList_ID = plv2.M_PriceList_ID AND plv2.IsActive = 'Y'
         LEFT OUTER JOIN LATERAL (
    SELECT COALESCE(ppa2.PriceStd, pp2.PriceStd) AS PriceStd, ppa2.signature
    FROM M_ProductPrice pp2
             /* Joining attribute prices */
             INNER JOIN report.fresh_AttributePrice ppa2 ON pp2.M_ProductPrice_ID = ppa2.M_ProductPrice_ID AND ppa2.m_pricelist_version_id = pp2.m_pricelist_version_id

    WHERE p.M_Product_ID = pp2.M_Product_ID
      AND pp2.M_Pricelist_Version_ID = plv2.M_Pricelist_Version_ID
      AND pp2.IsActive = 'Y'
      AND (pp2.m_hu_pi_item_product_ID = pp.m_hu_pi_item_product_ID OR (pp2.m_hu_pi_item_product_ID is null and pp.m_hu_pi_item_product_ID is null))
      AND pp2.isAttributeDependant = pp.isAttributeDependant
      --avoid comparing different prices in same pricelist
      AND (CASE WHEN pp2.M_PriceList_Version_ID = pp.M_PriceList_Version_ID THEN pp2.M_ProductPrice_ID = pp.M_ProductPrice_ID ELSE TRUE END)
        /* we have to make sure that only prices with the same attributes and packing instructions are compared. Note:
        * - If there is an Existing Attribute Price but no signature related columns are filled the signature will be ''
        * - If there are no Attribute Prices the signature will be null
        * This is important, because otherwise an empty attribute price will be compared to the regular price AND the alternate attribute price */
      AND (ppa.signature = ppa2.signature)
      AND ppa2.IsActive = 'Y'
      AND (ppa2.m_hu_pi_item_product_id = bp_ip.m_hu_pi_item_product_id OR ppa2.m_hu_pi_item_product_id IS NULL)
    ) pp2 ON true

         INNER JOIN M_Pricelist pl ON plv.M_Pricelist_ID = pl.M_PriceList_ID AND pl.isActive = 'Y'
         LEFT JOIN M_Pricelist pl2 ON plv2.M_PriceList_ID = pl2.M_Pricelist_ID AND pl2.isActive = 'Y'
         INNER JOIN C_Currency c ON pl.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
         LEFT JOIN C_Currency c2 ON pl2.C_Currency_ID = c2.C_CUrrency_ID AND c2.isActive = 'Y'

WHERE pp.isActive = 'Y'

  AND (pp.M_Attributesetinstance_ID = ppa.M_Attributesetinstance_ID OR pp.M_Attributesetinstance_ID is null)
  AND (pp.M_HU_PI_Item_Product_ID = bp_ip.M_HU_PI_Item_Product_ID OR pp.M_HU_PI_Item_Product_ID is null)

  AND (case when plv2.M_PriceList_Version_ID = plv.M_PriceList_Version_ID THEN ppa.signature = pp2.signature ELSE true end)

GROUP BY pp.M_ProductPrice_ID, pp.AD_Client_ID, pp.Created, pp.CreatedBy, pp.Updated, pp.UpdatedBy, pp.IsActive, pc.Name, pc.value, p.M_Product_ID, p.Value, p.Name, pp.IsSeasonFixedPrice, hupip.Name, pm.Name, COALESCE(ppa.PriceStd, pp.PriceStd) , pl.priceprecision, pp2.PriceStd, pl2.priceprecision, CASE WHEN pp2.PriceStd IS NULL THEN 0 ELSE 1 END, uom.UOMSymbol, COALESCE(ppa.Attributes, ''), pp.seqNo, bp.C_BPartner_ID, plv.M_Pricelist_Version_ID, plv2.M_Pricelist_Version_ID, pp.AD_Org_ID, ppa.m_attributesetinstance_ID, pp.M_HU_PI_Item_Product_ID, uom.X12DE355, hupip.Qty, it.m_hu_pi_version_id, c.iso_code, c2.iso_code
;





CREATE OR REPLACE VIEW RV_fresh_PriceList_Comparison AS


SELECT pp.AD_Org_ID,
       pp.AD_Client_ID,
       pp.Created,
       pp.CreatedBy,
       pp.Updated,
       pp.UpdatedBy,
       pp.IsActive,

       -- Displayed pricelist data
       pc.Name                                                                          AS ProductCategory,
       pc.value                                                                         AS ProductCategoryValue,
       p.M_Product_ID,
       p.Value,
       p.Name                                                                           AS ProductName,
       pp.IsSeasonFixedPrice,
       hupip.Name                                                                       AS ItemProductName,
       pm.Name                                                                          AS PackingMaterialName,
       COALESCE(ppa.PriceStd, pp.PriceStd)                                              AS PriceStd,
       CASE
           WHEN pl.priceprecision = 0
               THEN '#,##0'
           ELSE Substring('#,##0.000' FROM 0 FOR 7 + pl.priceprecision :: integer) END  AS PricePattern1,
       pp2.PriceStd                                                                     AS AltPriceStd,
       CASE
           WHEN pl.priceprecision = 0
               THEN '#,##0'
           ELSE Substring('#,##0.000' FROM 0 FOR 7 + pl2.priceprecision :: integer) END AS PricePattern2,
       CASE WHEN pp2.PriceStd IS NULL THEN 0 ELSE 1 END                                 AS hasaltprice,
       uom.UOMSymbol,
       COALESCE(ppa.Attributes, '')                                                     as attributes,
       pp.seqNo,

       -- Filter Columns
       bp.C_BPartner_ID,
       plv.M_Pricelist_Version_ID,
       plv2.M_Pricelist_Version_ID                                                      AS Alt_PriceList_Version_ID,

       -- Additional internal infos to be used
       pp.M_ProductPrice_ID,
       ppa.m_attributesetinstance_ID,
       bp_ip.M_HU_PI_Item_Product_ID                                                    as M_HU_PI_Item_Product_ID,
       uom.X12DE355                                                                     as UOM_X12DE355,
       hupip.Qty                                                                        as QtyCUsPerTU,
       it.m_hu_pi_version_id                                                            AS m_hu_pi_version_id,
       c.iso_code                                                                       as currency,
       c2.iso_code                                                                      as currency2

FROM M_ProductPrice pp

         INNER JOIN M_Product p ON pp.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'

    /** Get all BPartner and Product combinations.
     * IMPORTANT: Never use the query without BPartner Filter active
     */
         INNER JOIN C_BPartner bp ON TRUE
         INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
         INNER JOIN C_UOM uom ON pp.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'


    /*
      * We know if there are packing instructions limited to the BPartner/product-combination. If so,
      * we will use only those. If not, we will use only the non limited ones
      */
         LEFT OUTER JOIN LATERAL
    (
    SELECT vip.M_HU_PI_Item_Product_ID, vip.hasPartner, vip.m_product_id
    FROM Report.Valid_PI_Item_Product_V vip
        /* WHERE isInfiniteCapacity = 'N' task 09045/09788: we can also export PiiPs with infinite capacity */
    WHERE p.M_Product_ID = vip.M_Product_ID

      AND CASE
              WHEN
                  EXISTS(select 0
                         from Report.Valid_PI_Item_Product_V v
                         where p.M_Product_ID = v.M_Product_ID
                           AND v.hasPartner is true
                           and bp.C_BPartner_ID = v.C_BPartner_ID)
                  THEN vip.C_BPartner_ID = bp.C_BPartner_ID
              else vip.C_BPartner_ID IS NULL END
    ) bp_ip ON TRUE

         LEFT OUTER JOIN LATERAL
    (
    SELECT M_ProductPrice_ID,
           M_Attributesetinstance_ID,
           PriceStd,
           IsActive,
           M_HU_PI_Item_Product_ID,
           Attributes,
           Signature
    FROM report.fresh_AttributePrice ppa
    WHERE ppa.isActive = 'Y'
      AND ppa.M_ProductPrice_ID = pp.M_ProductPrice_ID
      AND (ppa.m_hu_pi_item_product_id = bp_ip.m_hu_pi_item_product_id OR ppa.m_hu_pi_item_product_id IS NULL)
      AND ppa.m_pricelist_version_id = pp.m_pricelist_version_id
    ) ppa on true

         LEFT OUTER JOIN m_hu_pi_item_product hupip ON
    case
        when pp.m_hu_pi_item_product_id is null then
                bp_ip.m_hu_pi_item_product_ID = hupip.m_hu_pi_item_product_id and hupip.isActive = 'Y'
        else
            bp_ip.m_product_id = hupip.m_product_id and hupip.isActive = 'Y'
        end
         LEFT OUTER JOIN m_hu_pi_item it ON hupip.M_HU_PI_Item_ID = it.M_HU_PI_Item_ID AND it.isActive = 'Y'
         LEFT OUTER JOIN m_hu_pi_item pmit
                         ON it.m_hu_pi_version_id = pmit.m_hu_pi_version_id AND pmit.itemtype::TEXT = 'PM'::TEXT AND
                            pmit.isActive = 'Y'
         LEFT OUTER JOIN m_hu_packingmaterial pm
                         ON pmit.m_hu_packingmaterial_id = pm.m_hu_packingmaterial_id AND pm.isActive = 'Y'


         INNER JOIN M_PriceList_Version plv
                    ON pp.M_PriceList_Version_ID = plv.M_PriceList_Version_ID AND plv.IsActive = 'Y'
    /*
     Get Comparison Prices
    */

    /* Get all PriceList_Versions of the PriceList (we need all available PriceList_Version_IDs for outside filtering)
     limited to the same PriceList because the Parameter validation rule is enforcing this */
         LEFT JOIN M_PriceList_Version plv2 ON plv.M_PriceList_ID = plv2.M_PriceList_ID AND plv2.IsActive = 'Y'
         LEFT OUTER JOIN LATERAL (
    SELECT COALESCE(ppa2.PriceStd, pp2.PriceStd) AS PriceStd, ppa2.signature
    FROM M_ProductPrice pp2
             /* Joining attribute prices */
             INNER JOIN report.fresh_AttributePrice ppa2 ON pp2.M_ProductPrice_ID = ppa2.M_ProductPrice_ID AND
                                                            ppa2.m_pricelist_version_id = pp2.m_pricelist_version_id

    WHERE p.M_Product_ID = pp2.M_Product_ID
      AND pp2.M_Pricelist_Version_ID = plv2.M_Pricelist_Version_ID
      AND pp2.IsActive = 'Y'
      AND (pp2.m_hu_pi_item_product_ID = pp.m_hu_pi_item_product_ID OR
           (pp2.m_hu_pi_item_product_ID is null and pp.m_hu_pi_item_product_ID is null))
      AND pp2.isAttributeDependant = pp.isAttributeDependant
      --avoid comparing different prices in same pricelist
      AND (CASE
               WHEN pp2.M_PriceList_Version_ID = pp.M_PriceList_Version_ID
                   THEN pp2.M_ProductPrice_ID = pp.M_ProductPrice_ID
               ELSE TRUE END)
        /* we have to make sure that only prices with the same attributes and packing instructions are compared. Note:
        * - If there is an Existing Attribute Price but no signature related columns are filled the signature will be ''
        * - If there are no Attribute Prices the signature will be null
        * This is important, because otherwise an empty attribute price will be compared to the regular price AND the alternate attribute price */
      AND (ppa.signature = ppa2.signature)
      AND ppa2.IsActive = 'Y'
      AND (ppa2.m_hu_pi_item_product_id = bp_ip.m_hu_pi_item_product_id OR ppa2.m_hu_pi_item_product_id IS NULL)
    ) pp2 ON true

         INNER JOIN M_Pricelist pl ON plv.M_Pricelist_ID = pl.M_PriceList_ID AND pl.isActive = 'Y'
         LEFT JOIN M_Pricelist pl2 ON plv2.M_PriceList_ID = pl2.M_Pricelist_ID AND pl2.isActive = 'Y'
         INNER JOIN C_Currency c ON pl.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
         LEFT JOIN C_Currency c2 ON pl2.C_Currency_ID = c2.C_CUrrency_ID AND c2.isActive = 'Y'

WHERE pp.isActive = 'Y'

  AND (pp.M_Attributesetinstance_ID = ppa.M_Attributesetinstance_ID OR pp.M_Attributesetinstance_ID is null)
  AND (pp.M_HU_PI_Item_Product_ID = bp_ip.M_HU_PI_Item_Product_ID OR pp.M_HU_PI_Item_Product_ID is null)

  AND (case
           when plv2.M_PriceList_Version_ID = plv.M_PriceList_Version_ID THEN ppa.signature = pp2.signature
           ELSE true end)
;

COMMENT ON VIEW RV_fresh_PriceList_Comparison
    IS '06042 Preisliste Drucken Preisänderung (100373416918)
Refactored in Tasks 07833 and 07915
A view for a report that displays the same data as RV_fresh_PriceList but imroved to be able to filter by two Price list versions, to be able to compare them';






CREATE OR REPLACE VIEW RV_fresh_PriceList AS
SELECT
	-- Mandatory Columns
	plc.AD_Org_ID, plc.AD_Client_ID,
	plc.Created, plc.CreatedBy,
	plc.Updated, plc.UpdatedBy,
	plc.IsActive,

	-- Displayed pricelist data
	plc.ProductCategory,
	plc.ProductCategoryValue,
	plc.M_Product_ID,
	plc.Value,
	plc.ProductName,
	plc.IsSeasonFixedPrice,
	plc.ItemProductName,
	plc.PackingMaterialName,
	plc.PriceStd,
	plc.AltPriceStd,
	plc.HasAltPrice,
	plc.UOMSymbol,
	plc.Attributes,

	-- Filter Columns
	plc.C_BPartner_ID,
	plc.M_Pricelist_Version_ID,
	plc.M_ProductPrice_ID
FROM
	RV_fresh_PriceList_Comparison plc 
WHERE
	plc.M_Pricelist_Version_ID = plc.Alt_PriceList_Version_ID
;

COMMENT ON VIEW RV_fresh_PriceList IS '05956 Printformat for Pricelist (109054740508) 
Refactored in Task 07833 and 07915
A view for a report that displays all product prices of a BPartner, including the attribute prices and CU:TU relation';




CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_report_With_PP_PI(IN p_c_bpartner_id numeric,
                                                                            IN p_m_pricelist_version_id numeric,
                                                                            IN p_alt_pricelist_version_id numeric,
                                                                            IN p_ad_language character varying,
                                                                            IN p_show_product_price_pi_flag text)
    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                productcategory            text,
                m_product_id               integer,
                value                      text,
                customerproductnumber      text,
                productname                text,
                isseasonfixedprice         character,
                itemproductname            character varying,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
				PricePattern1			   text,
                altpricestd                numeric,
				PricePattern2			   text,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   character(3),
                currency2                  character(3),
                show_product_price_pi_flag text
            )
AS
$BODY$
    --
SELECT --
       bp.value                                                                                             AS BP_Value,
       bp.name                                                                                              AS BP_Name,
       plc.ProductCategory,
       plc.M_Product_ID::integer,
       plc.Value,
       bpp.ProductNo                                                                                        AS CustomerProductNumber,
       COALESCE(pt.name, plc.ProductName)                                                                   AS ProductName,
       plc.IsSeasonFixedPrice,
       CASE WHEN plc.m_hu_pi_version_id = 101 THEN NULL ELSE plc.ItemProductName END                        AS ItemProductName,
       plc.QtyCUsPerTU,
       plc.PackingMaterialName,
       plc.PriceStd,
	   PricePattern1,
       plc.AltPriceStd,
	   PricePattern2,
       plc.HasAltPrice,
       plc.UOMSymbol,
       plc.UOM_X12DE355::text,
       CASE WHEN p_ad_language = 'fr_CH' THEN Replace(plc.Attributes, 'AdR', 'DLR') ELSE plc.Attributes END AS Attributes,
       plc.M_ProductPrice_ID::integer,
       plc.M_AttributeSetInstance_ID::integer,
       plc.M_HU_PI_Item_Product_ID::integer,
       plc.currency                                                                                         AS currency,
       plc.currency2                                                                                        AS currency2,
       p_show_product_price_pi_flag                                                                         as show_product_price_pi_flag

FROM RV_fresh_PriceList_Comparison_With_PP_PI plc
         LEFT OUTER JOIN M_Product_Trl pt ON plc.M_Product_ID = pt.M_Product_ID AND AD_Language = p_ad_language AND pt.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner bp ON plc.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND plc.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'
WHERE TRUE
  AND plc.C_BPartner_ID = p_c_bpartner_id
  AND plc.M_Pricelist_Version_ID = p_m_pricelist_version_id
  AND plc.Alt_Pricelist_Version_ID = coalesce(p_alt_pricelist_version_id, plc.m_pricelist_version_id)
  AND CASE
          WHEN p_alt_pricelist_version_id IS NOT NULL
              THEN PriceStd != 0
          ELSE PriceStd != 0 OR AltPriceStd != 0
    END

ORDER BY plc.ProductCategoryValue,
         plc.ProductName,
         plc.seqNo,
         plc.attributes,
         plc.ItemProductName;
--
$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000
;



CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_report(IN p_c_bpartner_id numeric,
                                                                 IN p_m_pricelist_version_id numeric,
                                                                 IN p_alt_pricelist_version_id numeric,
                                                                 IN p_ad_language character varying,
                                                                 IN p_show_product_price_pi_flag text)


    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                productcategory            text,
                m_product_id               integer,
                value                      text,
                customerproductnumber      text,
                productname                text,
                isseasonfixedprice         character,
                itemproductname            character varying,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
				PricePattern1			   text,
                altpricestd                numeric,
				PricePattern2			   text,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   character(3),
                currency2                  character(3),
                show_product_price_pi_flag text
            )
AS
$BODY$
    --
SELECT --
       bp.value                                                                                             AS BP_Value,
       bp.name                                                                                              AS BP_Name,
       plc.ProductCategory,
       plc.M_Product_ID::integer,
       plc.Value,
       bpp.ProductNo                                                                                        AS CustomerProductNumber,
       COALESCE(pt.name, plc.ProductName)                                                                   AS ProductName,
       plc.IsSeasonFixedPrice,
       CASE WHEN plc.m_hu_pi_version_id = 101 THEN NULL ELSE plc.ItemProductName END                        AS ItemProductName,
       plc.QtyCUsPerTU,
       plc.PackingMaterialName,
       plc.PriceStd,
	   PricePattern1,
       plc.AltPriceStd,
	   PricePattern2,
       plc.HasAltPrice,
       plc.UOMSymbol,
       plc.UOM_X12DE355::text,
       CASE WHEN p_ad_language = 'fr_CH' THEN Replace(plc.Attributes, 'AdR', 'DLR') ELSE plc.Attributes END AS Attributes,
       plc.M_ProductPrice_ID::integer,
       plc.M_AttributeSetInstance_ID::integer,
       plc.M_HU_PI_Item_Product_ID::integer,
       plc.currency                                                                                         AS currency,
       plc.currency2                                                                                        AS currency2,
       p_show_product_price_pi_flag                                                                         as show_product_price_pi_flag

FROM RV_fresh_PriceList_Comparison plc
         LEFT OUTER JOIN M_Product_Trl pt ON plc.M_Product_ID = pt.M_Product_ID AND AD_Language = p_ad_language AND pt.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner bp ON plc.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND plc.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'
WHERE TRUE
  AND plc.C_BPartner_ID = p_c_bpartner_id
  AND plc.M_Pricelist_Version_ID = p_m_pricelist_version_id
  AND plc.Alt_Pricelist_Version_ID = coalesce(p_alt_pricelist_version_id, plc.m_pricelist_version_id)
  AND CASE
          WHEN p_alt_pricelist_version_id IS NOT NULL
              THEN PriceStd != 0
          ELSE PriceStd != 0 OR AltPriceStd != 0
    END

ORDER BY plc.ProductCategoryValue,
         plc.ProductName,
         plc.seqNo,
         plc.attributes,
         plc.ItemProductName;
--
$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000
;




CREATE OR REPLACE FUNCTION report.Current_Vs_Previous_Pricelist_Comparison_Report(p_C_BPartner_ID numeric = NULL,
                                                                                  p_C_BP_Group_ID numeric = NULL,
                                                                                  p_IsSoTrx text = 'Y',
                                                                                  p_AD_Language TEXT = 'en_US',
                                                                                  p_show_product_price_pi_flag text = 'Y')
    RETURNS TABLE
            (
                bp_value                     text,
                bp_name                      text,
                ProductCategory              text,
                M_Product_ID                 integer,
                value                        text,
                CustomerProductNumber        text,
                ProductName                  text,
                IsSeasonFixedPrice           text,
                ItemProductName              text,
                qtycuspertu                  numeric,
                packingmaterialname          text,
                pricestd                     numeric,
				PricePattern1			     text,
                altpricestd                  numeric,
				PricePattern2			     text,
                hasaltprice                  integer,
                uomsymbol                    text,
                uom_x12de355                 text,
                Attributes                   text,
                m_productprice_id            integer,
                m_attributesetinstance_id    integer,
                m_hu_pi_item_product_id      integer,
                currency                     text,
                currency2                    text,
                validFromPLV1                timestamp,
                validFromPLV2                timestamp,
                namePLV1                     text,
                namePLV2                     text,
                c_bpartner_location_id       numeric,
                AD_Org_ID                    numeric,
                show_product_price_pi_flag text
            )
AS
$$
WITH PriceListVersionsByValidFrom AS
         (
             SELECT t.*
             FROM (SELECT --
                          plv.c_bpartner_id,
                          plv.m_pricelist_version_id,
                          plv.validfrom,
                          plv.name,
                          row_number() OVER (PARTITION BY plv.c_bpartner_id ORDER BY plv.validfrom DESC, plv.m_pricelist_version_id DESC) rank
                   FROM Report.Fresh_PriceList_Version_Val_Rule plv
                   WHERE TRUE
                     AND plv.validfrom <= now()
                     AND plv.issotrx = p_IsSoTrx
                     AND (p_C_BPartner_ID IS NULL OR plv.c_bpartner_id = p_C_BPartner_ID)
                     AND (p_C_BP_Group_ID IS NULL OR plv.c_bpartner_id IN (SELECT DISTINCT b.c_bpartner_id FROM c_bpartner b WHERE b.c_bp_group_id = p_C_BP_Group_ID))
                   ORDER BY TRUE,
                            plv.validfrom DESC,
                            plv.m_pricelist_version_id DESC) t
             WHERE t.rank <= 2
         ),
     currentAndPreviousPLV AS
         (
             -- implementation detail: all these sub-selects would be better implemented with a pivot. Unfortunately i cant understand how pivots work.
             SELECT DISTINCT --
                             plvv.c_bpartner_id,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV1_ID,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV2_ID,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV1,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV2,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV1,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV2
             FROM PriceListVersionsByValidFrom plvv
             ORDER BY plvv.c_bpartner_id
         ),
     result AS
         (
             SELECT t.*,
                    plv.validFromPLV1,
                    plv.validFromPLV2,
                    plv.namePLV1,
                    plv.namePLV2,
                    (SELECT bpl.c_bpartner_location_id FROM c_bpartner_location bpl WHERE bpl.c_bpartner_id = plv.c_bpartner_id ORDER BY bpl.isbilltodefault DESC LIMIT 1) c_bpartner_location_id,
                    (SELECT plv2.ad_org_id FROM m_pricelist_version plv2 WHERE plv2.m_pricelist_version_id = plv.PLV1_ID)                                                  AD_Org_ID
             FROM currentAndPreviousPLV plv
                      INNER JOIN LATERAL report.fresh_PriceList_Details_Report(
                     plv.c_bpartner_id,
                     plv.PLV1_ID,
                     plv.PLV2_ID,
                     p_AD_Language,
                     p_show_product_price_pi_flag
                 ) AS t ON TRUE
         )
SELECT --
       r.bp_value,
       r.bp_name,
       r.productcategory,
       r.m_product_id,
       r.value,
       r.customerproductnumber,
       r.productname,
       r.isseasonfixedprice::text,
       r.itemproductname,
       r.qtycuspertu,
       r.packingmaterialname,
       r.pricestd,
	   PricePattern1,
       r.altpricestd,
	   PricePattern2,
       r.hasaltprice,
       r.uomsymbol,
       r.uom_x12de355,
       r.attributes,
       r.m_productprice_id,
       r.m_attributesetinstance_id,
       r.m_hu_pi_item_product_id,
       r.currency::text,
       r.currency2::text,
       r.validFromPLV1,
       r.validFromPLV2,
       r.namePLV1,
       r.namePLV2,
       r.c_bpartner_location_id,
       r.AD_Org_ID,
       p_show_product_price_pi_flag as show_product_price_pi_flag
FROM result r
ORDER BY TRUE,
         r.bp_value,
         r.productCategory,
         r.value
$$
    LANGUAGE sql STABLE
;


/*
How to run

- All Bpartners

SELECT *
FROM report.Current_Vs_Previous_Pricelist_Comparison_Report()
;

- Specific Bpartner

SELECT *
FROM report.Current_Vs_Previous_Pricelist_Comparison_Report(2156515)
;
 */



CREATE OR REPLACE FUNCTION report.Current_Vs_Previous_Pricelist_Comparison_Report_With_PP_PI(p_C_BPartner_ID numeric = NULL,
                                                                                    p_C_BP_Group_ID numeric = NULL,
                                                                                    p_IsSoTrx text = 'Y',
                                                                                    p_AD_Language TEXT = 'en_US',
                                                                                    p_show_product_price_pi_flag text = 'Y')


    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                ProductCategory            text,
                M_Product_ID               integer,
                value                      text,
                CustomerProductNumber      text,
                ProductName                text,
                IsSeasonFixedPrice         text,
                ItemProductName            text,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
				PricePattern1	     	   text,
                altpricestd                numeric,
				PricePattern2			   text,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                Attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   text,
                currency2                  text,
                validFromPLV1              timestamp,
                validFromPLV2              timestamp,
                namePLV1                   text,
                namePLV2                   text,
                c_bpartner_location_id     numeric,
                AD_Org_ID                  numeric,
                show_product_price_pi_flag text
            )
AS
$$
WITH PriceListVersionsByValidFrom AS
         (
             SELECT t.*
             FROM (SELECT --
                          plv.c_bpartner_id,
                          plv.m_pricelist_version_id,
                          plv.validfrom,
                          plv.name,
                          row_number() OVER (PARTITION BY plv.c_bpartner_id ORDER BY plv.validfrom DESC, plv.m_pricelist_version_id DESC) rank
                   FROM Report.Fresh_PriceList_Version_Val_Rule plv
                   WHERE TRUE
                     AND plv.validfrom <= now()
                     AND plv.issotrx = p_IsSoTrx
                     AND (p_C_BPartner_ID IS NULL OR plv.c_bpartner_id = p_C_BPartner_ID)
                     AND (p_C_BP_Group_ID IS NULL OR plv.c_bpartner_id IN (SELECT DISTINCT b.c_bpartner_id FROM c_bpartner b WHERE b.c_bp_group_id = p_C_BP_Group_ID))
                   ORDER BY TRUE,
                            plv.validfrom DESC,
                            plv.m_pricelist_version_id DESC) t
             WHERE t.rank <= 2
         ),
     currentAndPreviousPLV AS
         (
             -- implementation detail: all these sub-selects would be better implemented with a pivot. Unfortunately i cant understand how pivots work.
             SELECT DISTINCT --
                             plvv.c_bpartner_id,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV1_ID,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV2_ID,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV1,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV2,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV1,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV2
             FROM PriceListVersionsByValidFrom plvv
             ORDER BY plvv.c_bpartner_id
         ),
     result AS
         (
             SELECT t.*,
                    plv.validFromPLV1,
                    plv.validFromPLV2,
                    plv.namePLV1,
                    plv.namePLV2,
                    (SELECT bpl.c_bpartner_location_id FROM c_bpartner_location bpl WHERE bpl.c_bpartner_id = plv.c_bpartner_id ORDER BY bpl.isbilltodefault DESC LIMIT 1) c_bpartner_location_id,
                    (SELECT plv2.ad_org_id FROM m_pricelist_version plv2 WHERE plv2.m_pricelist_version_id = plv.PLV1_ID)                                                  AD_Org_ID
             FROM currentAndPreviousPLV plv
                      INNER JOIN LATERAL report.fresh_PriceList_Details_Report_With_PP_PI(
                     plv.c_bpartner_id,
                     plv.PLV1_ID,
                     plv.PLV2_ID,
                     p_AD_Language,
                     p_show_product_price_pi_flag
                 ) AS t ON TRUE
         )
SELECT --
       r.bp_value,
       r.bp_name,
       r.productcategory,
       r.m_product_id,
       r.value,
       r.customerproductnumber,
       r.productname,
       r.isseasonfixedprice::text,
       r.itemproductname,
       r.qtycuspertu,
       r.packingmaterialname,
       r.pricestd,
	   PricePattern1,
       r.altpricestd,
	   PricePattern2,
       r.hasaltprice,
       r.uomsymbol,
       r.uom_x12de355,
       r.attributes,
       r.m_productprice_id,
       r.m_attributesetinstance_id,
       r.m_hu_pi_item_product_id,
       r.currency::text,
       r.currency2::text,
       r.validFromPLV1,
       r.validFromPLV2,
       r.namePLV1,
       r.namePLV2,
       r.c_bpartner_location_id,
       r.AD_Org_ID,
       p_show_product_price_pi_flag as show_product_price_pi_flag
FROM result r
ORDER BY TRUE,
         r.bp_value,
         r.productCategory,
         r.value
$$
    LANGUAGE sql STABLE
;


/*
How to run

- All Bpartners

SELECT *
FROM report.Current_Vs_Previous_Pricelist_Comparison_Report_With_PP_PI()
;

- Specific Bpartner

SELECT *
FROM report.Current_Vs_Previous_Pricelist_Comparison_Report_With_PP_PI(2156515)
;
 */
