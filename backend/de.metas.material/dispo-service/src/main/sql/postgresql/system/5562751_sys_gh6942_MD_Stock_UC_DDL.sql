

-- 2020-07-02T06:47:58.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_Stock_UC ON MD_Stock (M_Product_ID,AttributesKey,M_Warehouse_ID,AD_Org_ID,AD_Client_ID) WHERE IsActive='Y'
;
