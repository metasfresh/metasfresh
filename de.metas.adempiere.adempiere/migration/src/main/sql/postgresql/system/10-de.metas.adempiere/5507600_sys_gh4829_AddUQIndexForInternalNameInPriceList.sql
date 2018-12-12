-- 2018-12-11T11:14:53.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540482,0,255,TO_TIMESTAMP('2018-12-11 11:14:53','YYYY-MM-DD HH24:MI:SS'),100,'U','Already exists a price list with this internal name','Y','Y','M_PriceList_UC_InernalName','N',TO_TIMESTAMP('2018-12-11 11:14:53','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2018-12-11T11:14:53.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540482 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-12-11T11:15:32.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='D',Updated=TO_TIMESTAMP('2018-12-11 11:15:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540482
;

-- 2018-12-11T11:15:46.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,558398,540926,540482,0,TO_TIMESTAMP('2018-12-11 11:15:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2018-12-11 11:15:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-11T11:15:58.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,505147,540927,540482,0,TO_TIMESTAMP('2018-12-11 11:15:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2018-12-11 11:15:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-11T11:16:05.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_PriceList_UC_InernalName ON M_PriceList (InternalName,M_PricingSystem_ID) WHERE IsActive='Y'
;

----------------
