DROP VIEW IF EXISTS M_ProductScalePrice_CopySource_v
;

CREATE VIEW M_ProductScalePrice_CopySource_v(target_pricelist_version_id, m_productprice_id, qty, pricelist, pricestd, pricelimit, m_discountschemaline_id, ad_client_id, ad_org_id, isactive, source_productprice_id, source_productscaleprice_id, IsSkipInactivePrices) AS
SELECT psp.target_pricelist_version_id,
       psp.m_productprice_id,
       psp.qty,
       (psp.prices).pricelist               AS pricelist,
       (psp.prices).pricestd                AS pricestd,
       (psp.prices).pricelimit              AS pricelimit,
       (psp.prices).m_discountschemaline_id AS m_discountschemaline_id,
       psp.ad_client_id,
       psp.ad_org_id,
       psp.isactive,
       psp.source_productprice_id,
       psp.source_productscaleprice_id,
       psp.IsSkipInactivePrices
FROM (SELECT target_plv.m_pricelist_version_id                                                                                                                                                                                                                       AS target_pricelist_version_id,
             target_pp.m_productprice_id,
             source_pp.m_productprice_id                                                                                                                                                                                                                             AS source_productprice_id,
             source_psp.qty,
             m_discountschemaline_transformprices(dsl => dsl.*, p_pricelist => source_psp.pricelist, p_pricestd => source_psp.pricestd, p_pricelimit => source_psp.pricelimit, p_source_currency_id => source_pl.c_currency_id, p_target_currency_id => target_pl.c_currency_id, p_conv_client_id => target_plv.ad_client_id, p_conv_org_id => target_plv.ad_org_id,
                                                  p_applydiscountschema => source_pp.isseasonfixedprice = 'N'::bpchar AND source_pp.isactive = 'Y'::bpchar AND source_psp.isactive = 'Y'::bpchar, p_donotchangezeroprices => ds.donotchangezeroprices = 'Y'::bpchar) AS prices,
             target_plv.ad_client_id,
             target_plv.ad_org_id,
             source_psp.isactive,
             source_psp.m_productscaleprice_id                                                                                                                                                                                                                       AS source_productscaleprice_id,
             ds.IsSkipInactivePrices
      FROM m_pricelist_version target_plv
               JOIN m_pricelist target_pl ON target_pl.m_pricelist_id = target_plv.m_pricelist_id
               JOIN m_pricelist_version source_plv ON source_plv.m_pricelist_version_id = target_plv.m_pricelist_version_base_id
               JOIN m_pricelist source_pl ON source_pl.m_pricelist_id = source_plv.m_pricelist_id
               JOIN m_productprice target_pp ON target_pp.m_pricelist_version_id = target_plv.m_pricelist_version_id
               JOIN m_productprice source_pp ON source_pp.m_productprice_id = target_pp.m_productprice_base_id
               JOIN m_productscaleprice source_psp ON source_psp.m_productprice_id = source_pp.m_productprice_id
               JOIN m_discountschemaline dsl ON dsl.m_discountschemaline_id = target_pp.m_discountschemaline_id
               JOIN m_discountschema ds ON dsl.m_discountschema_id = ds.m_discountschema_id) psp
;

ALTER TABLE M_ProductScalePrice_CopySource_v
    OWNER TO metasfresh
;