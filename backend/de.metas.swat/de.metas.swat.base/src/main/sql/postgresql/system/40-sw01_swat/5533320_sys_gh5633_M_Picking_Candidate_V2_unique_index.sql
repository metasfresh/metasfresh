-- 2019-10-07T14:32:44.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540502,0,541418,TO_TIMESTAMP('2019-10-07 17:32:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Only one record is allowed','Y','Y','M_Picking_Config_V2_UQ','N',TO_TIMESTAMP('2019-10-07 17:32:44','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2019-10-07T14:32:44.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540502 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-10-07T14:32:55.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569071,540963,540502,0,TO_TIMESTAMP('2019-10-07 17:32:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y',10,TO_TIMESTAMP('2019-10-07 17:32:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-07T15:03:47.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_Picking_Config_V2_UQ ON M_Picking_Config_V2 (IsActive) WHERE IsActive='Y'
;

