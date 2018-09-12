-- DROP VIEW "de.metas.fresh".product_specifications_v;

CREATE OR REPLACE VIEW "de.metas.fresh".product_specifications_v AS
select p.Name as productName, p.CustomerLabelName,
p.additional_produktinfos, p.Ingredients, p.Value as productValue,
p.UPC, p.weight, 'Schweiz' as country, pi.Name as piName, pi.qty as piQty,
p.guaranteedaysmin, p.warehouse_temperature, p.description as productDecription,
a.Name as allergen, nf.Name as nutritionName, pn.nutritionqty,
bomProduct.Name as componentName, bomLine.qtybatch, bomProduct.ingredients as componentIngredients
from m_product p
left outer join M_HU_PI_Item_Product pi on pi.M_product_ID = p.M_product_ID
left outer join M_Product_Allergen pa on pa.M_product_ID = p.M_product_ID
left outer join M_Allergen a on a.m_allergen_id =pa.m_allergen_id
left outer join M_Product_Nutrition pn on pn.M_product_ID = p.M_product_ID
left outer join  M_Nutrition_Fact nf on nf.M_Nutrition_Fact_ID = pn.M_Nutrition_Fact_ID
left outer join PP_Product_BOM bom on bom.M_Product_ID = p.m_product_id
left outer join PP_Product_BOMLine bomLine on bomLine.pp_product_bom_id = bom.pp_product_bom_id
left outer join m_product bomProduct on bomLine.m_product_id = bomProduct.m_product_id;
