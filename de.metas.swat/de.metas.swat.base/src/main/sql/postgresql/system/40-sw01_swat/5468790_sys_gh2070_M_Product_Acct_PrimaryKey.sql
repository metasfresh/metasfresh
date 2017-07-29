-- 2017-07-26T16:49:47.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543392,0,'M_Product_Acct_ID',TO_TIMESTAMP('2017-07-26 16:49:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Product Acct','Product Acct',TO_TIMESTAMP('2017-07-26 16:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:49:47.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543392 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-07-26T16:49:47.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,557042,543392,0,13,273,'N','M_Product_Acct_ID',TO_TIMESTAMP('2017-07-26 16:49:47','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Product Acct',TO_TIMESTAMP('2017-07-26 16:49:47','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2017-07-26T16:49:47.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557042 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-26T16:49:47.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE M_PRODUCT_ACCT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2017-07-26T16:49:47.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Product_Acct ADD COLUMN M_Product_Acct_ID numeric(10,0) NOT NULL DEFAULT nextval('m_product_acct_seq')
;

-- 2017-07-26T16:49:47.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Product_Acct DROP CONSTRAINT IF EXISTS m_product_acct_pkey
;

-- 2017-07-26T16:49:47.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Product_Acct DROP CONSTRAINT IF EXISTS m_product_acct_key
;

-- 2017-07-26T16:49:47.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Product_Acct ADD CONSTRAINT m_product_acct_pkey PRIMARY KEY (M_Product_Acct_ID)
;

-- 2017-07-26T16:49:47.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557042,559433,0,210,TO_TIMESTAMP('2017-07-26 16:49:47','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','Product Acct',TO_TIMESTAMP('2017-07-26 16:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:49:47.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559433 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-07-26T16:49:47.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557042,559434,0,419,TO_TIMESTAMP('2017-07-26 16:49:47','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','Product Acct',TO_TIMESTAMP('2017-07-26 16:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:49:47.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559434 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-07-26T16:49:47.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557042,559435,0,422,TO_TIMESTAMP('2017-07-26 16:49:47','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','Product Acct',TO_TIMESTAMP('2017-07-26 16:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:49:47.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559435 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;









-- 2017-07-26T16:50:48.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540401,0,273,TO_TIMESTAMP('2017-07-26 16:50:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','M_Product_Acct_Unique_ProductAcctSchema','N',TO_TIMESTAMP('2017-07-26 16:50:48','YYYY-MM-DD HH24:MI:SS'),100,'IsActive = ''Y''')
;

-- 2017-07-26T16:50:48.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540401 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-07-26T16:50:59.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2565,540803,540401,0,TO_TIMESTAMP('2017-07-26 16:50:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2017-07-26 16:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:51:08.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2556,540804,540401,0,TO_TIMESTAMP('2017-07-26 16:51:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2017-07-26 16:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T16:51:11.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_Product_Acct_Unique_ProductAcctSchema ON M_Product_Acct (M_Product_ID,C_AcctSchema_ID) WHERE IsActive = 'Y'
;


-- 2017-07-26T17:47:12.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2017-07-26 17:47:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=210
;
