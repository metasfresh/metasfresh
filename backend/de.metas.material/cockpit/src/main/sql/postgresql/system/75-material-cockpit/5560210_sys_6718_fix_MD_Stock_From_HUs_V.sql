
DROP VIEW IF EXISTS MD_Stock_From_HUs_V;
CREATE VIEW MD_Stock_From_HUs_V AS
SELECT
    /*the COALESCEs are for the case of missing hu_agg (i.e. nothing on stock)*/
    COALESCE(hu_agg.AD_Client_ID, s.AD_Client_ID) AS AD_Client_ID,
    COALESCE(hu_agg.AD_Org_ID, s.AD_Org_ID) AS AD_Org_ID,
    COALESCE(hu_agg.M_Warehouse_ID, s.M_Warehouse_ID) AS M_Warehouse_ID,
    COALESCE(hu_agg.M_Product_ID, s.M_Product_ID) AS M_Product_ID,
    COALESCE(hu_agg.C_UOM_ID, p.C_UOM_ID) AS C_UOM_ID,
    COALESCE(hu_agg.AttributesKey, s.AttributesKey) AS AttributesKey,
    COALESCE(hu_agg.QtyOnHand, 0) AS QtyOnHand,

    -- QtyOnHandChange is the quantity - in the view's UOM - to add to the *current* MD_Stock.QtyOnHand to get the *correct* qtyOnHand
    COALESCE(hu_agg.QtyOnHand, 0) -
    uomconvert(
            COALESCE(hu_agg.M_Product_ID, s.M_Product_ID),
            p.C_UOM_ID,
            COALESCE(hu_agg.C_UOM_ID, p.C_UOM_ID),
            COALESCE(s.QtyOnHand, 0)) AS QtyOnHandChange
FROM
    MD_Stock s
        LEFT JOIN M_Product p ON p.M_Product_ID = s.M_Product_ID /*needed for its C_UOM_ID*/
        FULL OUTER JOIN
    (
        SELECT
            hu.AD_Client_ID,
            hu.AD_Org_ID,
            l.M_Warehouse_ID,
            hus.M_Product_ID,
            hus.C_UOM_ID,
            GenerateHUAttributesKey(hu.m_hu_id) as AttributesKey,
            SUM(hus.Qty) as QtyOnHand
        FROM m_hu hu
                 JOIN M_HU_Storage hus ON hus.M_HU_ID = hu.M_HU_ID
                 JOIN M_Locator l ON l.M_Locator_ID=hu.M_Locator_ID
        WHERE hu.isactive='Y'
          and M_HU_Item_Parent_ID IS NULL

            /*please keep in sync with de.metas.handlingunits.IHUStatusBL.isPhysicalHU(I_M_HU)*/
          and hu.HuStatus NOT IN ('P'/*Planning*/,'D'/*Destroyed*/,'E'/*Shipped*/)
        GROUP BY
            hu.AD_Client_ID,
            hu.AD_Org_ID,
            l.M_Warehouse_ID,
            hus.M_Product_ID,
            hus.C_UOM_ID,
            GenerateHUAttributesKey(hu.m_hu_id)
    ) hu_agg ON true
        AND hu_agg.AD_Client_ID = s.AD_Client_ID
        AND hu_agg.AD_Org_ID=s.AD_Org_ID
        AND hu_agg.M_Warehouse_ID = s.M_Warehouse_ID
        AND hu_agg.M_Product_ID = s.M_Product_ID
        AND hu_agg.AttributesKey = s.AttributesKey
;
COMMENT ON VIEW MD_Stock_From_HUs_V IS
    'This view is used by the process MD_Stock_Reset_From_M_HUs to initialize or reset the MD_stock table.
    Note that due to the outer join, existing MD_Stock records that currently don''t have any HU-storage are also represented (with qty=0)
    Belongs to issue "Show onhand quantity in new WebUI MRP Product Info Window" https://github.com/metasfresh/metasfresh-webui-api/issues/762';
