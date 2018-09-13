-- 2018-09-10T17:58:51.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544250,0,'C_BPartner_Memo',TO_TIMESTAMP('2018-09-10 17:58:51','YYYY-MM-DD HH24:MI:SS'),100,'Memo Text','D','Y','C_BPartner_Memo','Memo',TO_TIMESTAMP('2018-09-10 17:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-10T17:58:51.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544250 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


-- 2018-09-10T17:59:03.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=544250, ColumnName='C_BPartner_Memo', Description='Memo Text', Help=NULL, Name='C_BPartner_Memo',Updated=TO_TIMESTAMP('2018-09-10 17:59:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560118
;

-- 2018-09-10T17:59:03.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='C_BPartner_Memo', Description='Memo Text', Help=NULL WHERE AD_Column_ID=560118
;

-- 2018-09-10T18:00:01.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='AD_User_Memo1', Name='AD_User_Memo1',Updated=TO_TIMESTAMP('2018-09-10 18:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544078
;

-- 2018-09-10T18:00:01.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_User_Memo1', Name='AD_User_Memo1', Description='Memo Text', Help=NULL WHERE AD_Element_ID=544078
;

-- 2018-09-10T18:00:01.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_Memo1', Name='AD_User_Memo1', Description='Memo Text', Help=NULL, AD_Element_ID=544078 WHERE UPPER(ColumnName)='AD_USER_MEMO1' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-10T18:00:01.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_Memo1', Name='AD_User_Memo1', Description='Memo Text', Help=NULL WHERE AD_Element_ID=544078 AND IsCentrallyMaintained='Y'
;

-- 2018-09-10T18:00:01.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='AD_User_Memo1', Description='Memo Text', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544078) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544078)
;

-- 2018-09-10T18:00:02.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Memo1', Name='AD_User_Memo1' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544078)
;

-- 2018-09-10T18:00:29.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='AD_User_Memo2', Name='AD_User_Memo2',Updated=TO_TIMESTAMP('2018-09-10 18:00:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544079
;

-- 2018-09-10T18:00:29.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_User_Memo2', Name='AD_User_Memo2', Description='Memo Text', Help=NULL WHERE AD_Element_ID=544079
;

-- 2018-09-10T18:00:29.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_Memo2', Name='AD_User_Memo2', Description='Memo Text', Help=NULL, AD_Element_ID=544079 WHERE UPPER(ColumnName)='AD_USER_MEMO2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-10T18:00:29.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_Memo2', Name='AD_User_Memo2', Description='Memo Text', Help=NULL WHERE AD_Element_ID=544079 AND IsCentrallyMaintained='Y'
;

-- 2018-09-10T18:00:29.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='AD_User_Memo2', Description='Memo Text', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544079) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544079)
;

-- 2018-09-10T18:00:29.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Memo2', Name='AD_User_Memo2' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544079)
;

-- 2018-09-10T18:00:53.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='AD_User_Memo3', Name='AD_User_Memo3',Updated=TO_TIMESTAMP('2018-09-10 18:00:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544080
;

-- 2018-09-10T18:00:53.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_User_Memo3', Name='AD_User_Memo3', Description='Memo Text', Help=NULL WHERE AD_Element_ID=544080
;

-- 2018-09-10T18:00:53.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_Memo3', Name='AD_User_Memo3', Description='Memo Text', Help=NULL, AD_Element_ID=544080 WHERE UPPER(ColumnName)='AD_USER_MEMO3' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-10T18:00:53.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_Memo3', Name='AD_User_Memo3', Description='Memo Text', Help=NULL WHERE AD_Element_ID=544080 AND IsCentrallyMaintained='Y'
;

-- 2018-09-10T18:00:53.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='AD_User_Memo3', Description='Memo Text', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544080) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544080)
;

-- 2018-09-10T18:00:54.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Memo3', Name='AD_User_Memo3' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544080)
;

-- 2018-09-10T18:01:47.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544251,0,'AD_User_Memo4',TO_TIMESTAMP('2018-09-10 18:01:46','YYYY-MM-DD HH24:MI:SS'),100,'Memo Text','D','Y','AD_User_Memo4','Memo4',TO_TIMESTAMP('2018-09-10 18:01:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-10T18:01:47.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544251 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-10T18:01:55.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560991,544251,0,14,533,'AD_User_Memo4',TO_TIMESTAMP('2018-09-10 18:01:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Memo Text','D',5000,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','AD_User_Memo4',0,0,TO_TIMESTAMP('2018-09-10 18:01:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-09-10T18:01:55.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560991 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-10T18:01:58.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN AD_User_Memo4 TEXT')
;


/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner RENAME COLUMN Memo3 TO AD_User_Memo3')
;


/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner RENAME COLUMN Memo2 TO AD_User_Memo2')
;


/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner RENAME COLUMN Memo1 TO AD_User_Memo1')
;


/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner RENAME COLUMN Memo TO C_BPartner_Memo')
;


