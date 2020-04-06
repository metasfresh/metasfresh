
-- 2017-11-28T21:25:49.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('X_MRP_ProductInfo_Detail_MV','ALTER TABLE public.X_MRP_ProductInfo_Detail_MV ADD COLUMN Fresh_QtyMRP NUMERIC DEFAULT NULL')
;
COMMENT ON COLUMN public.X_MRP_ProductInfo_Detail_MV.Fresh_QtyMRP
  IS 'This phyical column is *not* used within the swing MRP product info window. 
 It is used for a short time while the webui "Materialcockpit" is based on the X_MRP_ProductInfo_Detail_MV table, but (as of now) only to avoid SQL problems on loading X_MRP_ProductInfo_Detail_MV records';


