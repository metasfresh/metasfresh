-- 2019-05-14T12:38:50.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,546860,540947,540123,0,'COALESCE(Township, '''')',TO_TIMESTAMP('2019-05-14 12:38:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y',40,TO_TIMESTAMP('2019-05-14 12:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-14T12:39:01.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2019-05-14 12:39:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540946
;

-- 2019-05-14T12:39:01.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_postal_unique
;

-- 2019-05-14T12:39:01.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Postal_Unique ON C_Postal (C_Country_ID,Postal,COALESCE(City,''),COALESCE(Township, '')) WHERE IsActive='Y'
;

