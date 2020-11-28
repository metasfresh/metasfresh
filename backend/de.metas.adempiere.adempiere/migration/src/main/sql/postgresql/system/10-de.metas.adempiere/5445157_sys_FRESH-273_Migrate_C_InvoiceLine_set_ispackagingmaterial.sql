update c_invoiceline
set ispackagingmaterial = 'Y'
where
m_product_id in (select 
m_product_id from m_product where 
m_product_category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
);

