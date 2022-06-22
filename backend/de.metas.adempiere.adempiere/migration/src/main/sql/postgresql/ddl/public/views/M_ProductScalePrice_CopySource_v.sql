DROP VIEW IF EXISTS M_ProductScalePrice_CopySource_v
;

CREATE OR REPLACE VIEW M_ProductScalePrice_CopySource_v AS
SELECT psp.Target_PriceList_Version_ID
     , psp.M_ProductPrice_ID
     --
     , psp.Qty
     --
     , (psp.prices).PriceList
     , (psp.prices).PriceStd
     , (psp.prices).PriceLimit
     , (psp.prices).M_DiscountSchemaLine_ID
     --
     , psp.AD_Client_ID
     , psp.AD_Org_ID
     , psp.IsActive
     --
     , psp.Source_ProductPrice_ID
     , psp.Source_ProductScalePrice_ID
FROM (SELECT target_plv.M_PriceList_Version_ID AS Target_PriceList_Version_ID
           -- , target_plv.Name as Target_PriceListVersionName
           , target_pp.M_ProductPrice_ID       AS M_ProductPrice_ID
           , source_pp.M_ProductPrice_ID       AS Source_ProductPrice_ID
           --
           , source_psp.Qty
           --
           , M_DiscountSchemaLine_TransformPrices(
            dsl := dsl
        , p_PriceList := source_psp.PriceList
        , p_PriceStd := source_psp.PriceStd
        , p_PriceLimit := source_psp.PriceLimit
        , p_Source_Currency_ID := source_pl.C_Currency_ID
        , p_Target_Currency_ID := target_pl.C_Currency_ID
        , p_Conv_Client_ID := target_plv.AD_Client_ID
        , p_Conv_Org_ID := target_plv.AD_Org_ID
        , p_ApplyDiscountSchema := source_pp.IsSeasonFixedPrice = 'N' AND source_pp.IsActive = 'Y' AND source_psp.IsActive = 'Y'
        , p_donotchangezeroprices := ds.donotchangezeroprices = 'Y'
        )                                      AS prices
           --
           , target_plv.AD_Client_ID
           , target_plv.AD_Org_ID
           , source_psp.IsActive
           , source_psp.M_ProductScalePrice_ID AS Source_ProductScalePrice_ID
      FROM M_PriceList_Version target_plv
               INNER JOIN M_PriceList target_pl ON (target_pl.M_PriceList_ID = target_plv.M_PriceList_ID)
          --
               INNER JOIN M_PriceList_Version source_plv ON (source_plv.M_Pricelist_Version_ID = target_plv.M_Pricelist_Version_Base_ID)
               INNER JOIN M_PriceList source_pl ON (source_pl.M_PriceList_ID = source_plv.M_PriceList_ID)
          --
               INNER JOIN M_ProductPrice target_pp ON (target_pp.M_PriceList_Version_ID = target_plv.M_PriceList_Version_ID)
               INNER JOIN M_ProductPrice source_pp ON (source_pp.M_ProductPrice_ID = target_pp.M_ProductPrice_Base_ID)
               INNER JOIN M_ProductScalePrice source_psp ON (source_psp.M_ProductPrice_ID = source_pp.M_ProductPrice_ID)
          --
               INNER JOIN M_DiscountSchemaLine dsl ON (dsl.M_DiscountSchemaLine_ID = target_pp.M_DiscountSchemaLine_ID)
               INNER JOIN M_DiscountSchema ds ON dsl.m_discountschema_id = ds.m_discountschema_id) psp
;


--
-- Test it
-- select * from M_ProductScalePrice_CopySource_v;
