--DROP VIEW "de.metas.fresh".product_specifications_v;

CREATE OR REPLACE VIEW "de.metas.fresh".product_specifications_v AS
  select
    p.Name                                                  as productName,
    p.CustomerLabelName,
    p.additional_produktinfos,
    p.Ingredients,
    p.Value                                                 as productValue,
    p.UPC,
    p.weight,
    c.name                                                  as country,
    p.guaranteedaysmin,
    p.warehouse_temperature,
    p.description                                           as productDecription,
    a.Name                                                  as allergen,
    nf.Name                                                 as nutritionName,
    pn.SeqNo                                                as NutritionSeqNo,
    pn.nutritionqty,
    coalesce(NULLIF(TRIM(bomProduct.CustomerLabelName),''), bomProduct.Name) as componentName,
    round(bomLine.qtybatch, 2)                              as qtybatch,
    bomLine.componenttype,
    bom_pc.ispackagingmaterial,
    bomProduct.ingredients                                  as componentIngredients,
    bp.companyname,
    bpl.address,
    p.M_product_ID
  from m_product p
    left outer join ad_orginfo oi on p.ad_org_id = oi.ad_org_id
    left outer join C_BPartner_Location bpl on oi.orgbp_location_id = bpl.c_bpartner_location_id
    left outer join C_BPartner bp on bpl.C_BPartner_ID = bp.C_BPartner_ID
    left outer join c_location l on bpl.c_location_id = l.c_location_id
    left outer join c_country c on l.c_country_id = c.c_country_id
    left outer join M_Product_Allergen pa on pa.M_product_ID = p.M_product_ID
    left outer join M_Allergen a on a.m_allergen_id = pa.m_allergen_id
    left outer join M_Nutrition_Fact nf on 1 = 1 AND nf.isactive = 'Y'::bpchar
    left outer join M_Product_Nutrition pn
      on pn.M_product_ID = p.M_product_ID and nf.M_Nutrition_Fact_ID = pn.M_Nutrition_Fact_ID
    left outer join PP_Product_BOM bom on bom.M_Product_ID = p.m_product_id
    left outer join PP_Product_BOMLine bomLine on bomLine.pp_product_bom_id = bom.pp_product_bom_id and bomLine.isActive='Y'
    left outer join m_product bomProduct on bomLine.m_product_id = bomProduct.m_product_id
    left outer join m_product_category bom_pc on bomProduct.m_product_category_id = bom_pc.m_product_category_id;
