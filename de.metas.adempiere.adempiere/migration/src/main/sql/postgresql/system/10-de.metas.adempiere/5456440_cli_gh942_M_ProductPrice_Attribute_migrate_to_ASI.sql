--
-- Backup
/*
drop table if exists backup.M_ProductPrice_BKP;
drop table if exists backup.M_ProductPrice_Attribute_BKP;
drop table if exists backup.M_ProductPrice_Attribute_Line_BKP;
drop table if exists backup.M_ProductScalePrice_BKP;
drop table if exists backup.C_OLCand_BKP;
*/
create table backup.M_ProductPrice_BKP as select * from M_ProductPrice;
create table backup.M_ProductPrice_Attribute_BKP as select * from M_ProductPrice_Attribute;
create table backup.M_ProductPrice_Attribute_Line_BKP as select * from M_ProductPrice_Attribute_Line;
create table backup.M_ProductScalePrice_BKP as select * from M_ProductScalePrice;
create table backup.C_OLCand_BKP as select * from C_OLCand;

--
-- Restore (if needed)
/*
delete from M_ProductPrice_Attribute_Line;
--
create index TMP_C_OLCand_M_ProductPrice_Attribute on C_OLCand(M_ProductPrice_Attribute_ID);
delete from M_ProductPrice_Attribute;
drop index TMP_C_OLCand_M_ProductPrice_Attribute;
--
delete from M_ProductScalePrice;
--
create index TMP_C_OLCand_M_ProductPrice on C_OLCand(M_ProductPrice_ID);
update C_OLCand set M_ProductPrice_ID=null where M_ProductPrice_ID is not null;
delete from M_ProductPrice;
drop index TMP_C_OLCand_M_ProductPrice;
--
--
INSERT INTO M_ProductPrice(m_pricelist_version_id, m_product_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, pricelist, pricestd, pricelimit, usescaleprice, m_productprice_id, c_taxcategory_id, isattributedependant, isseasonfixedprice, c_uom_id, seqno, c_uom_id_pre_08147)
	select m_pricelist_version_id, m_product_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, pricelist, pricestd, pricelimit, usescaleprice, m_productprice_id, c_taxcategory_id, isattributedependant, isseasonfixedprice, c_uom_id, seqno, c_uom_id_pre_08147
	from backup.M_ProductPrice_BKP;
INSERT INTO M_ProductPrice_Attribute(ad_client_id, ad_org_id, created, createdby, isactive, m_productprice_attribute_id, m_productprice_id, pricelimit, pricelist, pricestd, updated, updatedby, seqno, attribute_line_included_tab, m_hu_pi_item_product_id, isdefault, ishuprice)
	select ad_client_id, ad_org_id, created, createdby, isactive, m_productprice_attribute_id, m_productprice_id, pricelimit, pricelist, pricestd, updated, updatedby, seqno, attribute_line_included_tab, m_hu_pi_item_product_id, isdefault, ishuprice
	from backup.M_ProductPrice_Attribute_BKP;
INSERT INTO M_ProductPrice_Attribute_Line(ad_client_id, ad_org_id, created, createdby, isactive, m_attribute_id, m_attributevalue_id, m_productprice_attribute_id, m_productprice_attribute_line_id, updated, updatedby)
	select ad_client_id, ad_org_id, created, createdby, isactive, m_attribute_id, m_attributevalue_id, m_productprice_attribute_id, m_productprice_attribute_line_id, updated, updatedby
	from backup.M_ProductPrice_Attribute_Line_BKP;
INSERT INTO M_ProductScalePrice(ad_client_id, ad_org_id, created, createdby, isactive, m_productprice_id, m_productscaleprice_id, pricelimit, pricelist, pricestd, qty, updated, updatedby)
	select ad_client_id, ad_org_id, created, createdby, isactive, m_productprice_id, m_productscaleprice_id, pricelimit, pricelist, pricestd, qty, updated, updatedby
	from backup.M_ProductScalePrice_BKP;
	
-- Restore C_OLCand
create index if not exists C_OLCand_BKP_Key on backup.C_OLCand_BKP(C_OLCand_ID);
update C_OLCand olc set 
	M_ProductPrice_ID=bkp.M_ProductPrice_ID
	, M_ProductPrice_Attribute_ID=bkp.M_ProductPrice_Attribute_ID
from backup.C_OLCand_BKP bkp
where bkp.C_OLCand_ID=olc.C_OLCand_ID
;
*/


--
-- 2.7secs
drop table if exists backup.TMP_M_ProductPrice_New;
create table backup.TMP_M_ProductPrice_New as
select 
	pp.M_PriceList_Version_ID
	, nextval('m_productprice_seq') as M_ProductPrice_ID
	, pp.SeqNo
	, pa.SeqNo as MatchSeqNo
	, pp.M_Product_ID
	, pp.C_UOM_ID
	--
	-- the may be null in M_ProductPrice_Attribute, but not in M_ProductPrice
	, COALESCE(pa.PriceList, pp.PriceList) AS PriceList 
	, COALESCE(pa.PriceStd, pp.PriceStd) AS PriceStd
	, COALESCE(pa.PriceLimit, pp.PriceLimit) AS PriceLimit
	--
	, pp.C_TaxCategory_ID
	--
	, pp.UseScalePrice
	, pp.IsSeasonFixedPrice
	--
	, pp.IsAttributeDependant
	, nextval('m_attributesetinstance_seq') as m_attributesetinstance_id -- we will create ASIs those those IDs further down the road
	--
	, pp.IsDefault
	--
	, pa.IsHUPrice
	, pa.M_HU_PI_Item_Product_ID
	--
	, pa.AD_Client_ID, pa.AD_Org_ID, pa.IsActive, pa.Created, pa.CreatedBy, pa.Updated, pa.UpdatedBy
	--
	, pa.M_ProductPrice_ID as Old_ProductPrice_ID
	, pa.M_ProductPrice_Attribute_ID as Old_ProductPrice_Attribute_ID
from M_ProductPrice_Attribute pa
inner join M_ProductPrice pp on (pp.M_ProductPrice_ID=pa.M_ProductPrice_ID)
where pa.IsActive='Y' AND pp.IsActive='Y'
;
-- select * from backup.TMP_M_ProductPrice_New;



-- 3.3secs
drop table if exists backup.TMP_ASI_New;
create table backup.TMP_ASI_New as
select
	pp.M_ProductPrice_ID
	, pp.m_attributesetinstance_id
	, pa.ad_client_id, pa.ad_org_id, pa.isactive, pa.created, pa.createdby, pa.updated, pa.updatedby
	, 0::numeric as m_attributeset_id
	, (
		select STRING_AGG (av.Name, '_')
		from M_ProductPrice_Attribute_Line pal
		inner join M_AttributeValue av on (av.M_AttributeValue_ID=pal.M_AttributeValue_ID)
		where pal.M_ProductPrice_Attribute_ID=pa.M_ProductPrice_Attribute_ID
	) as Description
	--
	-- , null as serno
	-- , null as lot
	-- , null as m_lot_id
	-- , null as guaranteedate
	--
	, pp.Old_ProductPrice_ID
	, pa.M_ProductPrice_Attribute_ID
from backup.TMP_M_ProductPrice_New pp
inner join M_ProductPrice_Attribute pa on (pa.M_ProductPrice_Attribute_ID=pp.Old_ProductPrice_Attribute_ID)
where pa.IsActive='Y'
;
-- select * from backup.TMP_ASI_New;





-- 0.762secs
drop table if exists backup.TMP_AI_New;
create table backup.TMP_AI_New as
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
from backup.TMP_ASI_New asi
inner join M_ProductPrice_Attribute_Line pal on (pal.M_ProductPrice_Attribute_ID=asi.M_ProductPrice_Attribute_ID)
inner join M_AttributeValue av on (av.M_AttributeValue_ID=pal.M_AttributeValue_ID)
where pal.IsActive='Y'
;
-- select * from backup.TMP_AI_New;







--
--
-- 9.2secs for 672388rows
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
from backup.TMP_ASI_New
;
-- 6.5secs for 190087rows
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
from backup.TMP_AI_New
;
-- 45.1secs for 672388rows
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
	, MatchSeqNo
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
	, MatchSeqNo
	, m_attributesetinstance_id
	, isdefault
	--
	, IsHUPrice
	, M_HU_PI_Item_Product_ID
from backup.TMP_M_ProductPrice_New
;

--
-- Transform the old M_ProductPrice to main product price records
-- 11.9secs for 555888rows
update M_ProductPrice pp set
	IsAttributeDependant='N' -- not anymore, we created different records for that
	, MatchSeqNo = 0
where exists (select 1 from backup.TMP_M_ProductPrice_New t where pp.M_ProductPrice_ID=t.Old_ProductPrice_ID)
;


--
-- Migrate C_OLCand
CREATE INDEX IF NOT EXISTS C_OLCand_M_ProductPrice_ID_M_ProductPrice_Attribute_ID
  ON C_OLCand
  USING btree
  (M_ProductPrice_ID, M_ProductPrice_Attribute_ID);
CREATE INDEX IF NOT EXISTS TMP_ASI_ProductPrice_ID_M_ProductPrice_Attribute_ID
  ON backup.TMP_ASI_New
  USING btree
  (Old_ProductPrice_ID, M_ProductPrice_Attribute_ID);

-- 332ms
update C_OLCand c set
	M_ProductPrice_ID=asi.M_ProductPrice_ID
	, M_ProductPrice_Attribute_ID=null
from backup.TMP_ASI_New asi
where 
	c.M_ProductPrice_ID=asi.Old_ProductPrice_ID
	and c.M_ProductPrice_Attribute_ID=asi.M_ProductPrice_Attribute_ID
;





-- Delete the migration M_ProductPrice_Attribute records
-- NOTE: KEEP THIS LAST
CREATE INDEX IF NOT EXISTS TMP_M_ProductPrice_Old_ProductPrice_Attribute_ID
  ON backup.TMP_M_ProductPrice_New
  USING btree
  (Old_ProductPrice_Attribute_ID);
--
delete from M_ProductPrice_Attribute_Line pal
where exists (select 1 from backup.TMP_M_ProductPrice_New t where t.Old_ProductPrice_Attribute_ID=pal.M_ProductPrice_Attribute_ID)
;
--
create index TMP_C_OLCand_M_ProductPrice_Attribute on C_OLCand(M_ProductPrice_Attribute_ID);
-- 15secs for 672388rows
delete from M_ProductPrice_Attribute pa
where exists (select 1 from backup.TMP_M_ProductPrice_New t where t.Old_ProductPrice_Attribute_ID=pa.M_ProductPrice_Attribute_ID)
;
--
-- Now really remove M_ProductPrice_Attribute_Line and M_ProductPrice_Attribute because no longer need them
-- Expect ZERO records to be deleted because they are already empty
delete from M_ProductPrice_Attribute_Line pal;
delete from M_ProductPrice_Attribute pa;
--
drop index TMP_C_OLCand_M_ProductPrice_Attribute;
