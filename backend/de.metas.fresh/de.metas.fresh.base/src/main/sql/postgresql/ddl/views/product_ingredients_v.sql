--DROP VIEW IF EXISTS "de.metas.fresh".product_ingredients_v;

CREATE OR REPLACE VIEW "de.metas.fresh".product_ingredients_v AS
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
    TO_NUMBER(replace(pi.qty, '%',''), 'FM9G999.99')     as qtybatch,
    i.name as componentName,
    productIngred.ingredients as componentIngredients,
    p.M_product_ID
from m_product p
         left outer join ad_orginfo oi on p.ad_org_id = oi.ad_org_id
         left outer join C_BPartner_Location bpl on oi.orgbp_location_id = bpl.c_bpartner_location_id
         left outer join C_BPartner bp on bpl.C_BPartner_ID = bp.C_BPartner_ID
         left outer join c_location l on bpl.c_location_id = l.c_location_id
         left outer join c_country c on l.c_country_id = c.c_country_id
         left outer join M_Product_Allergen pa on pa.M_product_ID = p.M_product_ID
         left outer join M_Allergen a on a.m_allergen_id = pa.m_allergen_id
         left outer join M_Nutrition_Fact nf on 1 = 1
         left outer join M_Product_Nutrition pn
                         on pn.M_product_ID = p.M_product_ID and nf.M_Nutrition_Fact_ID = pn.M_Nutrition_Fact_ID
         left outer join m_product_ingredients pi on pi.m_product_id = p.m_product_id
         left outer JOIN m_ingredients i on pi.m_ingredients_id = i.m_ingredients_id
         left outer join m_product productIngred on i.m_product_id = productIngred.m_product_id;