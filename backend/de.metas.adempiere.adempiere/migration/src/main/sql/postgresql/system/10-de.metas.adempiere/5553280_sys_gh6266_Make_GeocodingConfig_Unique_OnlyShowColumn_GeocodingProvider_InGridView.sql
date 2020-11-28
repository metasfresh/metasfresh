-- 2020-02-26T09:43:10.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=10, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-02-26 11:43:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562880
;

-- 2020-02-26T09:53:01.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (CreatedBy,Processing,Created,AD_Client_ID,IsActive,IsUnique,AD_Table_ID,Updated,UpdatedBy,AD_Index_Table_ID,Name,AD_Org_ID,EntityType) VALUES (100,'N',TO_TIMESTAMP('2020-02-26 11:53:01','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',541410,TO_TIMESTAMP('2020-02-26 11:53:01','YYYY-MM-DD HH24:MI:SS'),100,540521,'GeocodingConfig_Unique',0,'D')
;

-- 2020-02-26T09:53:01.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540521 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-02-26T09:55:58.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2020-02-26 11:55:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540521
;

-- 2020-02-26T09:57:06.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (Created,CreatedBy,Updated,AD_Client_ID,AD_Index_Table_ID,IsActive,AD_Column_ID,SeqNo,UpdatedBy,AD_Index_Column_ID,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2020-02-26 11:57:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-02-26 11:57:06','YYYY-MM-DD HH24:MI:SS'),0,540521,'Y',568968,10,100,540990,0,'D')
;

-- 2020-02-26T09:57:54.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX GeocodingConfig_Unique ON GeocodingConfig (IsActive) WHERE IsActive='Y'
;

