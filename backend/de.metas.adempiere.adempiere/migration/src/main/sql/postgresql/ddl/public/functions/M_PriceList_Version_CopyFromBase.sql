DROP FUNCTION IF EXISTS M_PriceList_Version_CopyFromBase
(
    /* p_M_PriceList_Version_ID */ numeric
, /* p_AD_User_ID */               numeric
)
;

CREATE FUNCTION M_PriceList_Version_CopyFromBase(p_m_pricelist_version_id numeric,
                                                 p_ad_user_id             numeric DEFAULT 0) RETURNS void
    LANGUAGE plpgsql
AS
$$
BEGIN
    --
    -- Delete existing product prices
    DELETE FROM M_ProductPrice WHERE M_PriceList_Version_ID = p_M_PriceList_Version_ID;

    --
    -- Create the new product prices
    INSERT INTO M_ProductPrice
    (
        -- M_ProductPrice_ID -- not needed
        M_PriceList_Version_ID
    ,   SeqNo
    ,   MatchSeqNo
    ,   M_Product_ID
    ,   C_UOM_ID
        --
    ,   PriceList
    ,   PriceStd
    ,   PriceLimit
        --
    ,   C_TaxCategory_ID
        --
    ,   UseScalePrice
    ,   IsSeasonFixedPrice
        --
    ,   IsAttributeDependant
    ,   M_AttributeSetInstance_ID
        --
    ,   IsDefault
    ,   IsPriceEditable
    ,   IsDiscountEditable
    ,   IsHUPrice -- FIXME: this shall be part of de.metas.handlingunits.base module
    ,   M_HU_PI_Item_Product_ID -- FIXME: this shall be part of de.metas.handlingunits.base module
    ,   InvoicableQtyBasedOn
        --
    ,   AD_Client_ID
    ,   AD_Org_ID
    ,   IsActive
    ,   Created
    ,   CreatedBy
    ,   Updated
    ,   UpdatedBy
        --
    ,   M_ProductPrice_Base_ID
    ,   M_DiscountSchemaLine_ID)
        --
    SELECT s.Target_PriceList_Version_ID
         , s.SeqNo
         , s.MatchSeqNo
         , s.M_Product_ID
         , s.C_UOM_ID
         --
         , s.PriceList
         , s.PriceStd
         , s.PriceLimit
         --
         , s.C_TaxCategory_ID
         --
         , s.UseScalePrice
         , s.IsSeasonFixedPrice
         --
         , s.IsAttributeDependant
         , s.M_AttributeSetInstance_ID
         --
         , s.IsDefault
         , s.IsPriceEditable
         , s.IsDiscountEditable
         , s.IsHUPrice               -- FIXME: this shall be part of de.metas.handlingunits.base module
         , s.M_HU_PI_Item_Product_ID -- FIXME: this shall be part of de.metas.handlingunits.base module
         , s.InvoicableQtyBasedOn
         --
         , s.AD_Client_ID
         , s.AD_Org_ID
         , s.IsActive
         , NOW()        AS Created
         , p_AD_User_ID AS CreatedBy
         , NOW()        AS Updated
         , p_AD_User_ID AS UpdatedBy
         --
         , s.Source_M_ProductPrice_ID
         , s.M_DiscountSchemaLine_ID
    FROM M_ProductPrice_CopySource_v s
    WHERE s.Target_PriceList_Version_ID = p_M_PriceList_Version_ID
      AND s.M_DiscountSchemaLine_ID IS NOT NULL
      AND (s.isactive = 'Y'
        OR (s.isactive = 'N' AND s.isskipinactiveprices = 'N')
        OR (S.isactive = 'N' AND S.isskipinactiveprices IS NULL));


    --
    -- Create new product scale prices
    INSERT INTO M_ProductScalePrice
    (
        -- m_productscaleprice_id -- not needed
        M_ProductPrice_ID
        --
    ,   Qty
    ,   PriceLimit
    ,   PriceList
    ,   PriceStd
        --
    ,   AD_Client_ID
    ,   AD_Org_ID
    ,   IsActive
    ,   Created
    ,   CreatedBy
    ,   Updated
    ,   UpdatedBy)
    SELECT psp.M_ProductPrice_ID
         --
         , psp.Qty
         , psp.PriceLimit
         , psp.PriceList
         , psp.PriceStd
         --
         , psp.AD_Client_ID
         , psp.AD_Org_ID
         , psp.IsActive
         , NOW()        AS Created
         , p_AD_User_ID AS CreatedBy
         , NOW()        AS Updated
         , p_AD_User_ID AS UpdatedBy
    FROM M_ProductScalePrice_CopySource_v psp
    WHERE psp.Target_PriceList_Version_ID = p_M_PriceList_Version_ID
      AND psp.M_DiscountSchemaLine_ID IS NOT NULL
      AND (psp.isactive = 'Y'
        OR (psp.isactive = 'N' AND psp.isskipinactiveprices = 'N')
        OR (psp.isactive = 'N' AND psp.isskipinactiveprices IS NULL));

END;
$$
;

ALTER FUNCTION M_PriceList_Version_CopyFromBase(numeric, numeric) OWNER TO metasfresh
;