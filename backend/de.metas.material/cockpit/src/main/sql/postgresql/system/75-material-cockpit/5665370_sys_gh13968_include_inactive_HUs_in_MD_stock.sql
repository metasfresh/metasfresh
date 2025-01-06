--DROP VIEW IF EXISTS MD_Stock_From_HUs_V
--;

CREATE OR REPLACE VIEW MD_Stock_From_HUs_V AS
SELECT COALESCE(wh.AD_Client_ID, hu_agg.AD_Client_ID) AS AD_Client_ID,
       COALESCE(wh.AD_Org_ID, hu_agg.AD_Org_ID) AS AD_Org_ID,
       COALESCE(s.M_Warehouse_ID, hu_agg.M_Warehouse_ID) AS M_Warehouse_ID,
       COALESCE(s.M_Product_ID, hu_agg.M_Product_ID) AS M_Product_ID,
       COALESCE(p.C_UOM_ID, hu_agg.C_UOM_ID) AS C_UOM_ID,
       COALESCE(s.AttributesKey, hu_agg.AttributesKey) AS AttributesKey,
       COALESCE(hu_agg.QtyOnHand, 0) AS QtyOnHand,

       -- QtyOnHandChange is the quantity - in stocking-UOM - to add to the *current* MD_Stock.QtyOnHand to get the *correct* qtyOnHand
       COALESCE(hu_agg.QtyOnHand, 0) - COALESCE(s.QtyOnHand, 0) AS QtyOnHandChange
FROM MD_Stock s
         LEFT JOIN M_Product p ON p.M_Product_ID = s.M_Product_ID /*needed for its C_UOM_ID in case there are no M_HU_Storages */
         LEFT JOIN M_Warehouse wh ON wh.M_Warehouse_ID = s.M_Warehouse_ID /* needed for its AD_Org_ID to make sure that we don't get stuck with a wrong AD_Org_ID */
         FULL OUTER JOIN -- the full outer join and the COALESCEs in the SELECT part are needed for the case that there is no MD_Stock yet
    (
        SELECT hu.AD_Client_ID,
               hu.AD_Org_ID,
               l.M_Warehouse_ID,
               hus.M_Product_ID,
               p.C_UOM_ID,
               GenerateHUAttributesKey(hu.m_hu_id) AS AttributesKey,
               SUM(COALESCE(
                       uomconvert(
                               hus.M_Product_ID,
                               hus.C_UOM_ID,
                               p.C_UOM_ID,
                               hus.Qty),
                       0))                         AS QtyOnHand
        FROM m_hu hu
                 JOIN M_HU_Storage hus ON hus.M_HU_ID = hu.M_HU_ID
                 JOIN M_Locator l ON l.M_Locator_ID = hu.M_Locator_ID
                 LEFT JOIN M_Product p ON p.M_Product_ID = hus.M_Product_ID /*needed for its C_UOM_ID*/
        WHERE -- hu.isactive = 'Y' AND -- hu may be inactive due to a bug in material return, and technically we don't need to check for IsActive, because we have the HuStatus to check for
            M_HU_Item_Parent_ID IS NULL

            /*please keep in sync with de.metas.handlingunits.IHUStatusBL.isPhysicalHU(I_M_HU)*/
          AND hu.HuStatus NOT IN ('P'/*Planning*/, 'D'/*Destroyed*/, 'E'/*Shipped*/)
        GROUP BY hu.AD_Client_ID,
                 hu.AD_Org_ID,
                 l.M_Warehouse_ID,
                 hus.M_Product_ID,
                 p.C_UOM_ID,
                 GenerateHUAttributesKey(hu.m_hu_id)
    ) hu_agg ON TRUE
    AND hu_agg.AD_Client_ID = s.AD_Client_ID
    AND hu_agg.AD_Org_ID = s.AD_Org_ID
    AND hu_agg.M_Warehouse_ID = s.M_Warehouse_ID
    AND hu_agg.M_Product_ID = s.M_Product_ID
    AND hu_agg.AttributesKey = s.AttributesKey
;

COMMENT ON VIEW MD_Stock_From_HUs_V IS
    'This view is used by the process MD_Stock_Reset_From_M_HUs to initialize or reset the MD_stock table.
    Note that due to the outer join, existing MD_Stock records that currently don''t have any HU-storage are also represented (with qty=0)
    Belongs to issue "Show onhand quantity in new WebUI MRP Product Info Window" https://github.com/metasfresh/metasfresh-webui-api/issues/762'
;
