
-- 2019-11-26T15:16:02.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540513,0,541433,TO_TIMESTAMP('2019-11-26 16:16:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.shipper.gateway.dpd','Y','Y','DPD_Shipper_Config_UC','N',TO_TIMESTAMP('2019-11-26 16:16:02','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2019-11-26T15:16:02.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540513 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-11-26T15:16:24.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569353,540983,540513,0,TO_TIMESTAMP('2019-11-26 16:16:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.shipper.gateway.dpd','Y',10,TO_TIMESTAMP('2019-11-26 16:16:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-26T15:16:25.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX DPD_Shipper_Config_UC ON DPD_Shipper_Config (M_Shipper_ID) WHERE IsActive='Y'
;

