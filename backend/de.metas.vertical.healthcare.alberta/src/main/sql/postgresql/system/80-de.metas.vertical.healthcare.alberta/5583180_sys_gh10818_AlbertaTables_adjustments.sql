-- 2021-03-20T11:52:34.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-03-20 13:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573117
;

-- 2021-03-20T11:52:38.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_albertaarticle','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2021-03-20T11:52:38.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_albertaarticle','M_Product_ID',null,'NOT NULL',null)
;

-- 2021-03-20T11:52:55.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-03-20 13:52:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573138
;

-- 2021-03-20T11:52:56.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_albertabillabletherapy','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2021-03-20T11:52:56.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_albertabillabletherapy','M_Product_ID',null,'NOT NULL',null)
;

-- 2021-03-20T11:53:18.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-03-20 13:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573116
;

-- 2021-03-20T11:53:26.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_albertapackagingunit','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2021-03-20T11:53:26.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_albertapackagingunit','M_Product_ID',null,'NOT NULL',null)
;

-- 2021-03-20T11:54:00.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-03-20 13:54:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573128
;

-- 2021-03-20T11:54:02.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_albertatherapy','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2021-03-20T11:54:02.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_albertatherapy','M_Product_ID',null,'NOT NULL',null)
;

-- 2021-03-20T11:54:46.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542350,541279,TO_TIMESTAMP('2021-03-20 13:54:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','G',TO_TIMESTAMP('2021-03-20 13:54:46','YYYY-MM-DD HH24:MI:SS'),100,'G','G')
;

-- 2021-03-20T11:54:46.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542350 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-20T11:55:00.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-03-20 13:55:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542350
;

