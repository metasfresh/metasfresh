--DROP VIEW IF EXISTS rv_fresh_pricelist 

--DROP VIEW IF EXISTS RV_fresh_PriceList_Comparison;

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
