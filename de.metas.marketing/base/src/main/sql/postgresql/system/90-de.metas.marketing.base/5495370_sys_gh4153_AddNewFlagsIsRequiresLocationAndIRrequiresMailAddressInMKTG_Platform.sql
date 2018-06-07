-- 2018-06-07T13:04:25.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544111,0,'IsRequiresMailAddres',TO_TIMESTAMP('2018-06-07 13:04:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','Requires Mail Address','Requires Mail Address',TO_TIMESTAMP('2018-06-07 13:04:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-07T13:04:25.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544111 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-06-07T13:04:51.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544112,0,'IsRequiresLocation',TO_TIMESTAMP('2018-06-07 13:04:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','Requires Location','Requires Location',TO_TIMESTAMP('2018-06-07 13:04:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-07T13:04:51.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544112 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-06-07T13:05:10.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560264,544112,0,20,540979,'N','IsRequiresLocation',TO_TIMESTAMP('2018-06-07 13:05:10','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.marketing.base',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Requires Location',0,0,TO_TIMESTAMP('2018-06-07 13:05:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-06-07T13:05:10.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560264 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-06-07T13:05:12.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MKTG_Platform','ALTER TABLE public.MKTG_Platform ADD COLUMN IsRequiresLocation CHAR(1) DEFAULT ''N'' CHECK (IsRequiresLocation IN (''Y'',''N'')) NOT NULL')
;

-- 2018-06-07T13:05:27.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560265,544111,0,20,540979,'N','IsRequiresMailAddres',TO_TIMESTAMP('2018-06-07 13:05:26','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.marketing.base',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Requires Mail Address',0,0,TO_TIMESTAMP('2018-06-07 13:05:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-06-07T13:05:27.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560265 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-06-07T13:05:28.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MKTG_Platform','ALTER TABLE public.MKTG_Platform ADD COLUMN IsRequiresMailAddres CHAR(1) DEFAULT ''N'' CHECK (IsRequiresMailAddres IN (''Y'',''N'')) NOT NULL')
;

-- 2018-06-07T13:07:57.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560264,564467,0,541104,0,TO_TIMESTAMP('2018-06-07 13:07:57','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.marketing.base',0,'Y','Y','Y','N','N','N','N','N','Requires Location',50,50,0,1,1,TO_TIMESTAMP('2018-06-07 13:07:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-07T13:07:57.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564467 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-07T13:08:11.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560265,564468,0,541104,0,TO_TIMESTAMP('2018-06-07 13:08:10','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.marketing.base',0,'Y','Y','Y','N','N','N','N','N','Requires Mail Address',50,50,0,1,1,TO_TIMESTAMP('2018-06-07 13:08:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-07T13:08:11.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564468 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-07T13:09:36.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,564468,0,541104,552159,541587,'F',TO_TIMESTAMP('2018-06-07 13:09:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Requires Mail Address',20,0,0,TO_TIMESTAMP('2018-06-07 13:09:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-07T13:09:57.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,564467,0,541104,552160,541587,'F',TO_TIMESTAMP('2018-06-07 13:09:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Requires Location',20,0,0,TO_TIMESTAMP('2018-06-07 13:09:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-07T13:16:01.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsRequiredMailAddres',Updated=TO_TIMESTAMP('2018-06-07 13:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544111
;

-- 2018-06-07T13:16:02.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsRequiredMailAddres', Name='Requires Mail Address', Description=NULL, Help=NULL WHERE AD_Element_ID=544111
;

-- 2018-06-07T13:16:02.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRequiredMailAddres', Name='Requires Mail Address', Description=NULL, Help=NULL, AD_Element_ID=544111 WHERE UPPER(ColumnName)='ISREQUIREDMAILADDRES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-06-07T13:16:02.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRequiredMailAddres', Name='Requires Mail Address', Description=NULL, Help=NULL WHERE AD_Element_ID=544111 AND IsCentrallyMaintained='Y'
;

-- 2018-06-07T13:16:15.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsRequiredLocation',Updated=TO_TIMESTAMP('2018-06-07 13:16:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544112
;

-- 2018-06-07T13:16:15.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsRequiredLocation', Name='Requires Location', Description=NULL, Help=NULL WHERE AD_Element_ID=544112
;

-- 2018-06-07T13:16:15.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRequiredLocation', Name='Requires Location', Description=NULL, Help=NULL, AD_Element_ID=544112 WHERE UPPER(ColumnName)='ISREQUIREDLOCATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-06-07T13:16:15.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRequiredLocation', Name='Requires Location', Description=NULL, Help=NULL WHERE AD_Element_ID=544112 AND IsCentrallyMaintained='Y'
;

-- 2018-06-07T13:16:30.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MKTG_Platform','ALTER TABLE public.MKTG_Platform ADD COLUMN IsRequiredLocation CHAR(1) DEFAULT ''N'' CHECK (IsRequiredLocation IN (''Y'',''N'')) NOT NULL')
;

-- 2018-06-07T13:16:34.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MKTG_Platform','ALTER TABLE public.MKTG_Platform ADD COLUMN IsRequiredMailAddres CHAR(1) DEFAULT ''N'' CHECK (IsRequiredMailAddres IN (''Y'',''N'')) NOT NULL')
;



/* DDL */ SELECT public.db_alter_table('MKTG_Platform','ALTER TABLE public.MKTG_Platform DROP COLUMN IsRequiresMailAddres')
;



/* DDL */ SELECT public.db_alter_table('MKTG_Platform','ALTER TABLE public.MKTG_Platform DROP COLUMN IsRequiresLocation')
;


