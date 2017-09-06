-- 2017-09-06T12:26:19.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=557077
;

-- 2017-09-06T12:26:19.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=557077
;

ALTER TABLE M_Source_HU DROP COLUMN M_Locator_ID;

-- 2017-09-06T12:27:21.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540403,0,540835,TO_TIMESTAMP('2017-09-06 12:27:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Y','M_Source_HU_UC','N',TO_TIMESTAMP('2017-09-06 12:27:21','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2017-09-06T12:27:21.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540403 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-09-06T12:27:37.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,557076,540806,540403,0,TO_TIMESTAMP('2017-09-06 12:27:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',10,TO_TIMESTAMP('2017-09-06 12:27:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-06T12:27:44.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_Source_HU_UC ON M_Source_HU (M_HU_ID) WHERE IsActive='Y'
;

