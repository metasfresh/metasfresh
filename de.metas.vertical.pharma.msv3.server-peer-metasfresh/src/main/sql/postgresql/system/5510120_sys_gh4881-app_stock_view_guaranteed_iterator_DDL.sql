
-- 2019-01-21T22:02:53.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.T_MD_Stock_WarehouseAndProduct (AD_Client_ID NUMERIC(10), AD_Org_ID NUMERIC(10), AD_PInstance_ID NUMERIC(10) NOT NULL, M_Product_Category_ID NUMERIC(10), M_Product_ID NUMERIC(10), M_Warehouse_ID NUMERIC(10), ProductValue VARCHAR(40), QtyOnHand NUMERIC, T_MD_Stock_WarehouseAndProduct_ID NUMERIC(10) NOT NULL, CONSTRAINT T_MD_Stock_WarehouseAndProduct_Key PRIMARY KEY (T_MD_Stock_WarehouseAndProduct_ID))
;

-- 2019-01-21T22:30:26.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('T_MD_Stock_WarehouseAndProduct','ALTER TABLE public.T_MD_Stock_WarehouseAndProduct ADD COLUMN UUID VARCHAR(40) NOT NULL')
;

/* DDL */ SELECT public.db_alter_table('T_MD_Stock_WarehouseAndProduct','ALTER TABLE public.T_MD_Stock_WarehouseAndProduct DROP COLUMN AD_PInstance_ID')
;

-- 2019-01-21T22:40:55.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('T_MD_Stock_WarehouseAndProduct','ALTER TABLE public.T_MD_Stock_WarehouseAndProduct ADD COLUMN Line NUMERIC(10) NOT NULL')
;

-- 2019-01-21T22:41:12.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX T_MD_Stock_WarehouseAndProduct_UC ON T_MD_Stock_WarehouseAndProduct (Line,UUID)
;

-- 2019-01-22T00:16:38.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX T_MD_Stock_WarehouseAndProduct_UUID ON T_MD_Stock_WarehouseAndProduct (UUID)
;



drop view if exists MD_Stock_WarehouseAndProduct_v;
create or replace view MD_Stock_WarehouseAndProduct_v as
select
	
	pc.M_Product_Category_ID
	, p.M_Product_ID
	, p.Value as ProductValue
	, s.M_Warehouse_ID
	, COALESCE(SUM(s.QtyOnHand), 0) as QtyOnHand
	--
	, s.AD_Client_ID
	, s.AD_Org_ID
	--
	, row_number() over() AS Line /* used when we select this into T_MD_Stock_WarehouseAndProduct to obtain a guaranteed iterator */
from M_Product_Category pc
inner join M_Product p on (p.M_Product_Category_ID=pc.M_Product_Category_ID)
left outer join MD_Stock s on (s.M_Product_ID=p.M_Product_ID)
where pc.IsActive='Y' and p.IsActive='Y' and COALESCE(s.IsActive,'Y')='Y'
group by
	pc.M_Product_Category_ID
	, p.M_Product_ID
	, p.Value
	, s.M_Warehouse_ID
	, s.AD_Client_ID
	, s.AD_Org_ID
;
