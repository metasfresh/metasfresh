-- 2018-04-19T18:01:52.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543964,0,'IsCalculatePrice',TO_TIMESTAMP('2018-04-19 18:01:51','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.contracts','','Y','Calculate Price','Calculate Price',TO_TIMESTAMP('2018-04-19 18:01:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-19T18:01:52.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543964 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-04-19T18:02:10.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559719,543964,0,20,540311,'N','IsCalculatePrice',TO_TIMESTAMP('2018-04-19 18:02:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','','de.metas.contracts',1,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Calculate Price',0,0,TO_TIMESTAMP('2018-04-19 18:02:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-04-19T18:02:10.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559719 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-04-19T18:02:15.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Conditions','ALTER TABLE public.C_Flatrate_Conditions ADD COLUMN IsCalculatePrice CHAR(1) DEFAULT ''Y'' CHECK (IsCalculatePrice IN (''Y'',''N'')) NOT NULL')
;



-- 2018-04-19T19:21:23.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559719,563597,0,540331,0,TO_TIMESTAMP('2018-04-19 19:21:22','YYYY-MM-DD HH24:MI:SS'),100,'',0,'de.metas.contracts','',0,'Y','Y','Y','N','N','N','N','N','Calculate Price',320,320,0,1,1,TO_TIMESTAMP('2018-04-19 19:21:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-19T19:21:23.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563597 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-04-19T19:21:43.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,563597,0,540331,551489,540763,'F',TO_TIMESTAMP('2018-04-19 19:21:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Calculate Price',50,0,0,TO_TIMESTAMP('2018-04-19 19:21:43','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2018-04-20T13:16:14.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='onFlatrateTermExtend', Name='Behaviour when extending contract', PrintName='Behaviour when extending contract',Updated=TO_TIMESTAMP('2018-04-20 13:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543964
;

-- 2018-04-20T13:16:14.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='onFlatrateTermExtend', Name='Behaviour when extending contract', Description='', Help='' WHERE AD_Element_ID=543964
;

-- 2018-04-20T13:16:14.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='onFlatrateTermExtend', Name='Behaviour when extending contract', Description='', Help='', AD_Element_ID=543964 WHERE UPPER(ColumnName)='ONFLATRATETERMEXTEND' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-20T13:16:14.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='onFlatrateTermExtend', Name='Behaviour when extending contract', Description='', Help='' WHERE AD_Element_ID=543964 AND IsCentrallyMaintained='Y'
;

-- 2018-04-20T13:16:14.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Behaviour when extending contract', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543964) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543964)
;

-- 2018-04-20T13:16:14.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Behaviour when extending contract', Name='Behaviour when extending contract' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543964)
;

-- 2018-04-20T13:16:24.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 13:16:24','YYYY-MM-DD HH24:MI:SS'),Name='Behaviour when extending contract',PrintName='Behaviour when extending contract' WHERE AD_Element_ID=543964 AND AD_Language='de_CH'
;

-- 2018-04-20T13:16:24.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543964,'de_CH') 
;

-- 2018-04-20T13:16:27.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 13:16:27','YYYY-MM-DD HH24:MI:SS'),Name='Behaviour when extending contract',PrintName='Behaviour when extending contract' WHERE AD_Element_ID=543964 AND AD_Language='nl_NL'
;

-- 2018-04-20T13:16:27.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543964,'nl_NL') 
;

-- 2018-04-20T13:16:30.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 13:16:30','YYYY-MM-DD HH24:MI:SS'),Name='Behaviour when extending contract',PrintName='Behaviour when extending contract' WHERE AD_Element_ID=543964 AND AD_Language='en_US'
;

-- 2018-04-20T13:16:30.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543964,'en_US') 
;

-- 2018-04-20T13:18:37.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540853,TO_TIMESTAMP('2018-04-20 13:18:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','Conditions_BehaviourWhenExtending',TO_TIMESTAMP('2018-04-20 13:18:37','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2018-04-20T13:18:37.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540853 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-04-20T13:19:30.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541619,540853,TO_TIMESTAMP('2018-04-20 13:19:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Copy prices from predecessor',TO_TIMESTAMP('2018-04-20 13:19:30','YYYY-MM-DD HH24:MI:SS'),100,'Copy','Copy')
;

-- 2018-04-20T13:19:30.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541619 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-04-20T13:19:54.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541620,540853,TO_TIMESTAMP('2018-04-20 13:19:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Calculate from pricing data',TO_TIMESTAMP('2018-04-20 13:19:54','YYYY-MM-DD HH24:MI:SS'),100,'Calculate','Calculate')
;

-- 2018-04-20T13:19:54.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541620 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-04-20T13:20:33.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='Ca',Updated=TO_TIMESTAMP('2018-04-20 13:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541620
;

-- 2018-04-20T13:20:38.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='Co',Updated=TO_TIMESTAMP('2018-04-20 13:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541619
;

-- 2018-04-20T13:20:56.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540000, DefaultValue='''Ca''',Updated=TO_TIMESTAMP('2018-04-20 13:20:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559719
;

-- 2018-04-20T13:21:13.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2,Updated=TO_TIMESTAMP('2018-04-20 13:21:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559719
;

-- 2018-04-20T13:21:15.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Conditions','ALTER TABLE public.C_Flatrate_Conditions ADD COLUMN onFlatrateTermExtend VARCHAR(2) DEFAULT ''Ca'' NOT NULL')
;

/* DDL */ SELECT public.db_alter_table('C_Flatrate_Conditions','ALTER TABLE public.C_Flatrate_Conditions DROP COLUMN IsCalculatePrice')
;


-- 2018-04-20T13:26:23.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=540853,Updated=TO_TIMESTAMP('2018-04-20 13:26:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559719
;


-- 2018-04-20T14:09:36.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='OnFlatrateTermExtend',Updated=TO_TIMESTAMP('2018-04-20 14:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543964
;

-- 2018-04-20T14:09:36.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='OnFlatrateTermExtend', Name='Behaviour when extending contract', Description='', Help='' WHERE AD_Element_ID=543964
;

-- 2018-04-20T14:09:36.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OnFlatrateTermExtend', Name='Behaviour when extending contract', Description='', Help='', AD_Element_ID=543964 WHERE UPPER(ColumnName)='ONFLATRATETERMEXTEND' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-20T14:09:36.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OnFlatrateTermExtend', Name='Behaviour when extending contract', Description='', Help='' WHERE AD_Element_ID=543964 AND IsCentrallyMaintained='Y'
;

-- 2018-04-20T14:09:46.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','OnFlatrateTermExtend','VARCHAR(2)',null,'Ca')
;

-- 2018-04-20T14:09:46.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Flatrate_Conditions SET OnFlatrateTermExtend='Ca' WHERE OnFlatrateTermExtend IS NULL
;


