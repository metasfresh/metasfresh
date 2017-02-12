create table backup.M_ProductPrice_BKP as select * from M_ProductPrice;
create table backup.M_ProductPrice_Attribute_BKP as select * from M_ProductPrice_Attribute;
create table backup.M_ProductPrice_Attribute_Line_BKP as select * from M_ProductPrice_Attribute_Line;
create table backup.M_ProductScalePrice_BKP as select * from M_ProductScalePrice;

drop table if exists TMP_M_ProductPrice;
create temporary table TMP_M_ProductPrice as
select 
	pp.M_PriceList_Version_ID
	, nextval('m_productprice_seq') as M_ProductPrice_ID
	, pp.SeqNo
	, pa.SeqNo as MatchSeqNo
	, pp.M_Product_ID
	, pp.C_UOM_ID
	--
	, pa.PriceList
	, pa.PriceStd
	, pa.PriceLimit
	--
	, pp.C_TaxCategory_ID
	--
	, pp.UseScalePrice
	, pp.IsSeasonFixedPrice
	--
	, pp.IsAttributeDependant
	, nextval('m_attributesetinstance_seq') as m_attributesetinstance_id
	--
	, pp.IsDefault
	--
	, pa.IsHUPrice
	, pa.M_HU_PI_Item_Product_ID
	--
	, pa.AD_Client_ID, pa.AD_Org_ID, pa.IsActive, pa.Created, pa.CreatedBy, pa.Updated, pa.UpdatedBy
	--
	, pa.M_ProductPrice_ID as Old_ProductPrice_ID
from M_ProductPrice_Attribute pa
inner join M_ProductPrice pp on (pp.M_ProductPrice_ID=pa.M_ProductPrice_ID)
;
-- select * from TMP_M_ProductPrice;






drop table if exists TMP_ASI;
create temporary table TMP_ASI as
select
	pp.M_ProductPrice_ID
	, pp.m_attributesetinstance_id
	, pa.ad_client_id, pa.ad_org_id, pa.isactive, pa.created, pa.createdby, pa.updated, pa.updatedby
	, 0::numeric as m_attributeset_id
	-- , null as serno
	-- , null as lot
	-- , null as guaranteedate
	, '?'::varchar as description -- TODO
	-- , null as m_lot_id
	--
	, pp.Old_ProductPrice_ID
	, pa.M_ProductPrice_Attribute_ID
from TMP_M_ProductPrice pp
inner join M_ProductPrice_Attribute pa on (pa.M_ProductPrice_ID=pp.Old_ProductPrice_ID)
where pa.IsActive='Y'
;
-- select * from TMP_ASI;






drop table if exists TMP_AI;
create temporary table TMP_AI as
select
	asi.M_ProductPrice_ID
	, asi.m_attributesetinstance_id
	, nextval('m_attributeinstance_seq') as m_attributeinstance_id
	--
	, pal.m_attribute_id
	, pal.m_attributevalue_id
	, av.Value
	--
	, pal.ad_client_id, pal.ad_org_id, pal.isactive, pal.created, pal.createdby, pal.updated, pal.updatedby
from TMP_ASI asi
inner join M_ProductPrice_Attribute_Line pal on (pal.M_ProductPrice_Attribute_ID=asi.M_ProductPrice_Attribute_ID)
inner join M_AttributeValue av on (av.M_AttributeValue_ID=pal.M_AttributeValue_ID)
where pal.IsActive='Y'
;
-- select * from TMP_AI;







--
--
--
INSERT INTO m_attributesetinstance
(
	m_attributesetinstance_id
	, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby
	, m_attributeset_id, description
)
select
	m_attributesetinstance_id
	, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby
	, m_attributeset_id, description
from TMP_ASI
;
--
INSERT INTO m_attributeinstance
(
	m_attributesetinstance_id
	, m_attribute_id
	, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby
	, m_attributevalue_id
	,  value
	, m_attributeinstance_id
)
select
	m_attributesetinstance_id
	, m_attribute_id
	, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby
	, m_attributevalue_id
	,  value
	, m_attributeinstance_id
from TMP_AI
;
--
INSERT INTO m_productprice
(
	m_pricelist_version_id, m_product_id
	, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby
	, pricelist, pricestd, pricelimit
	, usescaleprice
	, m_productprice_id
	, c_taxcategory_id 
	, isattributedependant
	, isseasonfixedprice
	, c_uom_id
	, seqno
	, m_attributesetinstance_id
	, isdefault
	--
	, IsHUPrice
	, M_HU_PI_Item_Product_ID
)
select
	m_pricelist_version_id, m_product_id
	, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby
	, pricelist, pricestd, pricelimit
	, usescaleprice
	, m_productprice_id
	, c_taxcategory_id 
	, isattributedependant
	, isseasonfixedprice
	, c_uom_id
	, seqno
	, m_attributesetinstance_id
	, isdefault
	--
	, IsHUPrice
	, M_HU_PI_Item_Product_ID
from TMP_M_ProductPrice
;
--
delete from M_ProductPrice pp
where exists (select 1 from TMP_M_ProductPrice t where pp.M_ProductPrice_ID=t.Old_ProductPrice_ID)
;


