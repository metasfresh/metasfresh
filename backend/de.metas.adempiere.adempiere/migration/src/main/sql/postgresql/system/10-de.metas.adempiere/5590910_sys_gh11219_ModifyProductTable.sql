-- 2021-06-02T13:53:32.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN shopdescription TEXT')
;

-- 2021-06-02T13:53:53.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN shopinventoryqty NUMERIC')
;

-- 2021-06-02T13:54:04.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN Trademark VARCHAR(255)')
;

-- 2021-06-02T13:54:37.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN M_Shop_Category_ID NUMERIC(10)')
;

-- 2021-06-02T13:56:23.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN ManufacturerProductDescription TEXT')
;

-- 2021-06-02T14:01:20.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN SafetyInfo TEXT')
;

-- 2021-06-03T06:06:06.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.m_product_shop_category (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_ID NUMERIC(10) NOT NULL, m_product_shop_category_ID NUMERIC(10) NOT NULL, M_Shop_Category_ID NUMERIC(10) NOT NULL, Name VARCHAR(40) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MProduct_mproductshopcategory FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED, CONSTRAINT m_product_shop_category_Key PRIMARY KEY (m_product_shop_category_ID), CONSTRAINT MShopCategory_mproductshopcategory FOREIGN KEY (M_Shop_Category_ID) REFERENCES public.M_Shop_Category DEFERRABLE INITIALLY DEFERRED)
;


-- 2021-06-03T06:37:06.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE M_Product DROP COLUMN IF EXISTS M_Shop_Category_ID')
;

-- 2021-06-03T06:56:09.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_ProductShop_Category (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_ID NUMERIC(10), M_ProductShop_Category_ID NUMERIC(10) NOT NULL, M_Shop_Category_ID NUMERIC(10), Name VARCHAR(40) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MProduct_MProductShopCategory FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_ProductShop_Category_Key PRIMARY KEY (M_ProductShop_Category_ID), CONSTRAINT MShopCategory_MProductShopCategory FOREIGN KEY (M_Shop_Category_ID) REFERENCES public.M_Shop_Category DEFERRABLE INITIALLY DEFERRED)
;

-- 2021-06-03T07:49:11.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ProductShop_Category','ALTER TABLE M_ProductShop_Category DROP COLUMN IF EXISTS Name')
;