DROP VIEW IF EXISTS M_ProductPrice_CopySource_v
;

CREATE OR REPLACE VIEW M_ProductPrice_CopySource_v AS
SELECT pp.Target_PriceList_Version_ID
     --
     , pp.M_Product_ID
     , pp.C_UOM_ID
     , pp.SeqNo
     , pp.MatchSeqNo
     --
     , (pp.prices).PriceList
     , pp.Source_PriceList
     , (pp.prices).PriceStd
     , pp.Source_PriceStd
     , (pp.prices).PriceLimit
     , pp.Source_PriceLimit
     , (pp.prices).M_DiscountSchemaLine_ID
     --
     , pp.Source_M_ProductPrice_ID
     --
     , pp.C_TaxCategory_ID
     , pp.UseScalePrice
     , pp.IsSeasonFixedPrice
     , pp.IsAttributeDependant
     , pp.M_AttributeSetInstance_ID
     , pp.IsDefault
     , pp.IsPriceEditable
     , pp.IsDiscountEditable
     , pp.IsHUPrice               -- FIXME: this shall be part of de.metas.handlingunits.base module
     , pp.M_HU_PI_Item_Product_ID -- FIXME: this shall be part of de.metas.handlingunits.base module
     , pp.InvoicableQtyBasedOn
     --
     , pp.AD_Client_ID
     , pp.AD_Org_ID
     , pp.IsActive
FROM (SELECT target_plv.M_PriceList_Version_ID                                                                       AS Target_PriceList_Version_ID
           --
           , M_DiscountSchemaLine_TransformPrices(
            dsl := dsl
        , p_PriceList := source_pp.PriceList
        , p_PriceStd := source_pp.PriceStd
        , p_PriceLimit := source_pp.PriceLimit
        , p_Source_Currency_ID := source_pl.C_Currency_ID
        , p_Target_Currency_ID := target_pl.C_Currency_ID
        , p_Conv_Client_ID := target_plv.AD_Client_ID
        , p_Conv_Org_ID := target_plv.AD_Org_ID
        , p_ApplyDiscountSchema := source_pp.IsSeasonFixedPrice = 'N' AND source_pp.IsActive = 'Y'
        , p_doNotChangeZeroPrices := ds.donotchangezeroprices = 'Y'
        )                                                                                                            AS prices
           --
           , source_pp.M_Product_ID
           , source_pp.C_UOM_ID
           , source_pp.MatchSeqNo                                                                                    AS MatchSeqNo
           , source_pp.SeqNo                                                                                         AS SeqNo
           --
           , target_plv.AD_Client_ID                                                                                 AS AD_Client_ID
           , target_plv.AD_Org_ID                                                                                    AS AD_Org_ID
           , source_pp.IsActive                                                                                      AS IsActive
           --
           , source_pp.PriceList                                                                                     AS Source_PriceList
           , source_pp.PriceStd                                                                                      AS Source_PriceStd
           , source_pp.PriceLimit                                                                                    AS Source_PriceLimit
           --
           , source_pp.M_ProductPrice_ID                                                                             AS Source_M_ProductPrice_ID
           --
           , COALESCE(dsl.C_TaxCategory_Target_ID, source_pp.C_TaxCategory_ID)                                       AS C_TaxCategory_ID
           , source_pp.UseScalePrice
           , source_pp.IsSeasonFixedPrice
           , source_pp.IsAttributeDependant
           , (CASE WHEN source_pp.IsAttributeDependant = 'Y' THEN source_pp.M_AttributeSetInstance_ID ELSE NULL END) AS M_AttributeSetInstance_ID
           , source_pp.IsDefault
           , source_pp.IsPriceEditable
           , source_pp.IsDiscountEditable
           , source_pp.IsHUPrice               -- FIXME: this shall be part of de.metas.handlingunits.base module
           , source_pp.M_HU_PI_Item_Product_ID -- FIXME: this shall be part of de.metas.handlingunits.base module
           , source_pp.InvoicableQtyBasedOn
           --
      FROM M_PriceList_Version target_plv
               INNER JOIN M_PriceList target_pl ON (target_pl.M_PriceList_ID = target_plv.M_PriceList_ID)
               INNER JOIN C_Currency target_currency ON (target_currency.C_Currency_ID = target_pl.C_Currency_ID)
          --
               INNER JOIN M_PriceList_Version source_plv ON (source_plv.M_Pricelist_Version_ID = target_plv.M_Pricelist_Version_Base_ID)
               INNER JOIN M_PriceList source_pl ON (source_pl.M_PriceList_ID = source_plv.M_PriceList_ID)
          --
               INNER JOIN M_ProductPrice source_pp ON (source_pp.M_PriceList_Version_ID = source_plv.M_PriceList_Version_ID)
          --
               LEFT OUTER JOIN M_DiscountSchemaLine dsl ON (dsl.M_DiscountSchemaLine_ID = getMatchingDiscountSchemaLine_ID(
              p_M_DiscountSchema_ID := target_plv.M_DiscountSchema_ID
          , p_M_Product_ID := source_pp.M_Product_ID
          , p_C_TaxCategory_ID := source_pp.C_TaxCategory_ID
          ))

               LEFT OUTER JOIN M_DiscountSchema ds ON dsl.m_discountschema_id = ds.m_discountschema_id) pp
;


--
-- Test it
-- select * from M_ProductPrice_CopySource_v order by Target_PriceList_Version_ID
