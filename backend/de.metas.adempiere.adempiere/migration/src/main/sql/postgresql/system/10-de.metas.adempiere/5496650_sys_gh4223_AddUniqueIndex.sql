-- 2018-06-26T19:41:17.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540446,0,540992,TO_TIMESTAMP('2018-06-26 19:41:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Unique_Manufacture_Bpartner','N',TO_TIMESTAMP('2018-06-26 19:41:17','YYYY-MM-DD HH24:MI:SS'),100,'IsActive = ''Y''')
;

-- 2018-06-26T19:41:17.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540446 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-06-26T19:41:27.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560426,540878,540446,0,TO_TIMESTAMP('2018-06-26 19:41:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2018-06-26 19:41:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-26T19:41:37.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560427,540879,540446,0,TO_TIMESTAMP('2018-06-26 19:41:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2018-06-26 19:41:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-26T19:41:58.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX Unique_Manufacture_Bpartner ON M_BannedManufacturer (C_BPartner_ID,Manufacturer_ID) WHERE IsActive = 'Y'
;

