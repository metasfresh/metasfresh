DROP VIEW IF EXISTS M_ProductPrice_CopySource_v
;

CREATE VIEW M_ProductPrice_CopySource_v
            (target_pricelist_version_id, m_product_id, c_uom_id, seqno, matchseqno, pricelist, source_pricelist, pricestd, source_pricestd, pricelimit, source_pricelimit, m_discountschemaline_id, source_m_productprice_id, c_taxcategory_id, usescaleprice, isseasonfixedprice, isattributedependant, m_attributesetinstance_id, isdefault, ispriceeditable, isdiscounteditable, ishuprice,
             m_hu_pi_item_product_id, invoicableqtybasedon, ad_client_id, ad_org_id, isactive, IsSkipInactivePrices)
AS
SELECT pp.target_pricelist_version_id,
       pp.m_product_id,
       pp.c_uom_id,
       pp.seqno,
       pp.matchseqno,
       (pp.prices).pricelist               AS pricelist,
       pp.source_pricelist,
       (pp.prices).pricestd                AS pricestd,
       pp.source_pricestd,
       (pp.prices).pricelimit              AS pricelimit,
       pp.source_pricelimit,
       (pp.prices).m_discountschemaline_id AS m_discountschemaline_id,
       pp.source_m_productprice_id,
       pp.c_taxcategory_id,
       pp.usescaleprice,
       pp.isseasonfixedprice,
       pp.isattributedependant,
       pp.m_attributesetinstance_id,
       pp.isdefault,
       pp.ispriceeditable,
       pp.isdiscounteditable,
       pp.ishuprice,
       pp.m_hu_pi_item_product_id,
       pp.invoicableqtybasedon,
       pp.ad_client_id,
       pp.ad_org_id,
       pp.isactive,
       pp.IsSkipInactivePrices
FROM (SELECT target_plv.m_pricelist_version_id                                                                                                                                                                                 AS target_pricelist_version_id,
             m_discountschemaline_transformprices(dsl => dsl.*, p_pricelist => source_pp.pricelist, p_pricestd => source_pp.pricestd, p_pricelimit => source_pp.pricelimit, p_source_currency_id => source_pl.c_currency_id, p_target_currency_id => target_pl.c_currency_id, p_conv_client_id => target_plv.ad_client_id, p_conv_org_id => target_plv.ad_org_id,
                                                  p_applydiscountschema => source_pp.isseasonfixedprice = 'N'::bpchar AND source_pp.isactive = 'Y'::bpchar, p_donotchangezeroprices => ds.donotchangezeroprices = 'Y'::bpchar) AS prices,
             source_pp.m_product_id,
             source_pp.c_uom_id,
             source_pp.matchseqno,
             source_pp.seqno,
             target_plv.ad_client_id,
             target_plv.ad_org_id,
             source_pp.isactive,
             source_pp.pricelist                                                                                                                                                                                               AS source_pricelist,
             source_pp.pricestd                                                                                                                                                                                                AS source_pricestd,
             source_pp.pricelimit                                                                                                                                                                                              AS source_pricelimit,
             source_pp.m_productprice_id                                                                                                                                                                                       AS source_m_productprice_id,
             COALESCE(dsl.c_taxcategory_target_id, source_pp.c_taxcategory_id)                                                                                                                                                 AS c_taxcategory_id,
             source_pp.usescaleprice,
             source_pp.isseasonfixedprice,
             source_pp.isattributedependant,
             CASE
                 WHEN source_pp.isattributedependant = 'Y'::bpchar THEN source_pp.m_attributesetinstance_id
                                                                   ELSE NULL::numeric
             END                                                                                                                                                                                                               AS m_attributesetinstance_id,
             source_pp.isdefault,
             source_pp.ispriceeditable,
             source_pp.isdiscounteditable,
             source_pp.ishuprice,
             source_pp.m_hu_pi_item_product_id,
             source_pp.invoicableqtybasedon,
             ds.IsSkipInactivePrices
      FROM m_pricelist_version target_plv
               JOIN m_pricelist target_pl ON target_pl.m_pricelist_id = target_plv.m_pricelist_id
               JOIN c_currency target_currency ON target_currency.c_currency_id = target_pl.c_currency_id
               JOIN m_pricelist_version source_plv ON source_plv.m_pricelist_version_id = target_plv.m_pricelist_version_base_id
               JOIN m_pricelist source_pl ON source_pl.m_pricelist_id = source_plv.m_pricelist_id
               JOIN m_productprice source_pp ON source_pp.m_pricelist_version_id = source_plv.m_pricelist_version_id
               LEFT JOIN m_discountschemaline dsl ON dsl.m_discountschemaline_id = getmatchingdiscountschemaline_id(p_m_discountschema_id => target_plv.m_discountschema_id, p_m_product_id => source_pp.m_product_id, p_c_taxcategory_id => source_pp.c_taxcategory_id)
               LEFT JOIN m_discountschema ds ON dsl.m_discountschema_id = ds.m_discountschema_id) pp
;

ALTER TABLE M_ProductPrice_CopySource_v
    OWNER TO metasfresh
;