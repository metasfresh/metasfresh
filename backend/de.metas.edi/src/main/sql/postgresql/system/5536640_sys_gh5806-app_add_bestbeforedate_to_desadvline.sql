-- 2019-11-22T22:02:35.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='HU-Mindesthaltbarkeitsdatum', PrintName='HU-Mindesthaltbarkeitsdatum',Updated=TO_TIMESTAMP('2019-11-22 23:02:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543895 AND AD_Language='de_CH'
;

-- 2019-11-22T22:02:35.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543895,'de_CH') 
;

-- 2019-11-22T22:02:46.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='HU best before date', PrintName='HU best before date',Updated=TO_TIMESTAMP('2019-11-22 23:02:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543895 AND AD_Language='en_US'
;

-- 2019-11-22T22:02:46.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543895,'en_US') 
;

-- 2019-11-22T22:02:51.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HU best before date',Updated=TO_TIMESTAMP('2019-11-22 23:02:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543895 AND AD_Language='de_DE'
;

-- 2019-11-22T22:02:51.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543895,'de_DE') 
;

-- 2019-11-22T22:02:51.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543895,'de_DE') 
;

-- 2019-11-22T22:02:51.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HU_BestBeforeDate', Name='HU best before date', Description=NULL, Help=NULL WHERE AD_Element_ID=543895
;

-- 2019-11-22T22:02:51.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_BestBeforeDate', Name='HU best before date', Description=NULL, Help=NULL, AD_Element_ID=543895 WHERE UPPER(ColumnName)='HU_BESTBEFOREDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-22T22:02:51.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_BestBeforeDate', Name='HU best before date', Description=NULL, Help=NULL WHERE AD_Element_ID=543895 AND IsCentrallyMaintained='Y'
;

-- 2019-11-22T22:02:51.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='HU best before date', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543895) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543895)
;

-- 2019-11-22T22:02:51.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Best Before Date', Name='HU best before date' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543895)
;

-- 2019-11-22T22:02:51.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='HU best before date', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543895
;

-- 2019-11-22T22:02:51.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='HU best before date', Description=NULL, Help=NULL WHERE AD_Element_ID = 543895
;

-- 2019-11-22T22:02:51.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'HU best before date', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543895
;

-- 2019-11-22T22:03:01.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='HU-Mindesthaltbarkeitsdatum', PrintName='HU-Mindesthaltbarkeitsdatum',Updated=TO_TIMESTAMP('2019-11-22 23:03:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543895 AND AD_Language='de_DE'
;

-- 2019-11-22T22:03:01.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543895,'de_DE') 
;

-- 2019-11-22T22:03:01.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543895,'de_DE') 
;

-- 2019-11-22T22:03:01.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HU_BestBeforeDate', Name='HU-Mindesthaltbarkeitsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=543895
;

-- 2019-11-22T22:03:01.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_BestBeforeDate', Name='HU-Mindesthaltbarkeitsdatum', Description=NULL, Help=NULL, AD_Element_ID=543895 WHERE UPPER(ColumnName)='HU_BESTBEFOREDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-22T22:03:01.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_BestBeforeDate', Name='HU-Mindesthaltbarkeitsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=543895 AND IsCentrallyMaintained='Y'
;

-- 2019-11-22T22:03:01.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='HU-Mindesthaltbarkeitsdatum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543895) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543895)
;

-- 2019-11-22T22:03:01.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='HU-Mindesthaltbarkeitsdatum', Name='HU-Mindesthaltbarkeitsdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543895)
;

-- 2019-11-22T22:03:01.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='HU-Mindesthaltbarkeitsdatum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543895
;

-- 2019-11-22T22:03:01.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='HU-Mindesthaltbarkeitsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID = 543895
;

-- 2019-11-22T22:03:01.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'HU-Mindesthaltbarkeitsdatum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543895
;

-- 2019-11-22T22:03:18.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577375,0,'BestBeforeDate',TO_TIMESTAMP('2019-11-22 23:03:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mindesthaltbarkeitsdatum','Mindesthaltbarkeitsdatum',TO_TIMESTAMP('2019-11-22 23:03:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-22T22:03:18.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577375 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-11-22T22:03:41.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Best before date', PrintName='Best before date',Updated=TO_TIMESTAMP('2019-11-22 23:03:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577375 AND AD_Language='en_US'
;

-- 2019-11-22T22:03:41.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577375,'en_US') 
;

-- 2019-11-22T22:03:47.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-22 23:03:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577375 AND AD_Language='de_DE'
;

-- 2019-11-22T22:03:47.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577375,'de_DE') 
;

-- 2019-11-22T22:03:47.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577375,'de_DE') 
;

-- 2019-11-22T22:03:49.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-22 23:03:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577375 AND AD_Language='de_CH'
;

-- 2019-11-22T22:03:49.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577375,'de_CH') 
;

-- 2019-11-22T22:04:04.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569642,577375,0,15,540645,'BestBeforeDate',TO_TIMESTAMP('2019-11-22 23:04:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Mindesthaltbarkeitsdatum',0,0,TO_TIMESTAMP('2019-11-22 23:04:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-11-22T22:04:04.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569642 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-22T22:04:04.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577375) 
;

-- 2019-11-22T22:04:05.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN BestBeforeDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2019-11-22T22:04:26.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569642,591932,0,540663,TO_TIMESTAMP('2019-11-22 23:04:26','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.esb.edi','Y','Y','N','N','N','N','N','Mindesthaltbarkeitsdatum',TO_TIMESTAMP('2019-11-22 23:04:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-22T22:04:26.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591932 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-22T22:04:26.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577375) 
;

-- 2019-11-22T22:04:26.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591932
;

-- 2019-11-22T22:04:26.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591932)
;

-- 2019-11-22T22:05:16.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,591932,0,540663,541228,564071,'F',TO_TIMESTAMP('2019-11-22 23:05:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'BestBeforeDate',87,0,0,TO_TIMESTAMP('2019-11-22 23:05:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-22T22:05:52.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-11-22 23:05:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564071
;

-- 2019-11-22T22:05:52.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-11-22 23:05:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563260
;

-- 2019-11-22T22:05:52.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-11-22 23:05:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549241
;

-- 2019-11-22T22:05:52.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2019-11-22 23:05:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549242
;

-- 2019-11-22T22:05:52.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2019-11-22 23:05:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549243
;

-- 2019-11-22T22:05:52.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2019-11-22 23:05:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549244
;

-- 2019-11-22T22:05:52.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2019-11-22 23:05:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549245
;

-- 2019-11-22T22:05:52.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2019-11-22 23:05:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563224
;

-- 2019-11-22T22:05:52.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2019-11-22 23:05:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563223
;

-- 2019-11-22T22:05:52.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2019-11-22 23:05:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549232
;

-- 2019-11-22T22:06:39.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569642,0,TO_TIMESTAMP('2019-11-22 23:06:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550257,'N','N','Mindesthaltbarkeitsdatum',340,'E',TO_TIMESTAMP('2019-11-22 23:06:39','YYYY-MM-DD HH24:MI:SS'),100,'BestBeforeDate')
;

-- 2019-11-22T22:06:51.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-11-22 23:06:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550257
;

