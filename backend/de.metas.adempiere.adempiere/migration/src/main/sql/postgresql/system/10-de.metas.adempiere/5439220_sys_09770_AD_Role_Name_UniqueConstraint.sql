-- 08.02.2016 16:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540373,0,156,TO_TIMESTAMP('2016-02-08 16:40:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','AD_Role_Name_Unique','N',TO_TIMESTAMP('2016-02-08 16:40:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.02.2016 16:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540373 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 08.02.2016 16:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,532,540745,540373,0,TO_TIMESTAMP('2016-02-08 16:41:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2016-02-08 16:41:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.02.2016 16:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='@IsActive/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2016-02-08 16:45:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540373
;

-- 08.02.2016 16:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y''',Updated=TO_TIMESTAMP('2016-02-08 16:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540373
;

-- 08.02.2016 16:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_Role_Name_Unique ON AD_Role (Name) WHERE IsActive = 'Y'
;

-- 08.02.2016 17:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET ErrorMsg='Test',Updated=TO_TIMESTAMP('2016-02-08 17:07:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540373
;

-- 08.02.2016 17:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET IsTranslated='N' WHERE AD_Index_Table_ID=540373
;

-- 08.02.2016 17:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS ad_role_name_unique
;

-- 08.02.2016 17:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_Role_Name_Unique ON AD_Role (Name) WHERE IsActive = 'Y'
;

-- 08.02.2016 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET ErrorMsg='Der Rollenname muss eindeutig sein.',Updated=TO_TIMESTAMP('2016-02-08 17:15:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540373
;

-- 08.02.2016 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET IsTranslated='N' WHERE AD_Index_Table_ID=540373
;

-- 08.02.2016 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS ad_role_name_unique
;

-- 08.02.2016 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_Role_Name_Unique ON AD_Role (Name) WHERE IsActive = 'Y'
;

