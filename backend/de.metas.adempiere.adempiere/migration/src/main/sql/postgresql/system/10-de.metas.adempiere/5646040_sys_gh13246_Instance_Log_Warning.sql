-- 2022-07-04T15:27:25.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581084,0,'Warnings',TO_TIMESTAMP('2022-07-04 18:27:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Warnings','Warnings',TO_TIMESTAMP('2022-07-04 18:27:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-04T15:27:25.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581084 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-04T15:27:54.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583584,581084,0,10,578,'Warnings',TO_TIMESTAMP('2022-07-04 18:27:54','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Warnings',0,0,TO_TIMESTAMP('2022-07-04 18:27:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-04T15:27:54.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583584 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-04T15:27:54.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581084) 
;

-- 2022-07-04T15:27:55.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_PInstance_Log','ALTER TABLE public.AD_PInstance_Log ADD COLUMN Warnings VARCHAR(2000)')
;

-- 2022-07-04T15:28:15.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=5000,Updated=TO_TIMESTAMP('2022-07-04 18:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583584
;

-- 2022-07-04T15:28:15.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_pinstance_log','Warnings','VARCHAR(5000)',null,null)
;

-- 2022-07-04T15:32:56.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=36,Updated=TO_TIMESTAMP('2022-07-04 18:32:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583584
;

-- 2022-07-04T15:33:15.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583584,701331,0,665,TO_TIMESTAMP('2022-07-04 18:33:15','YYYY-MM-DD HH24:MI:SS'),100,5000,'D','Y','N','N','N','N','N','N','N','Warnings',TO_TIMESTAMP('2022-07-04 18:33:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-04T15:33:15.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701331 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-04T15:33:15.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581084) 
;

-- 2022-07-04T15:33:15.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701331
;

-- 2022-07-04T15:33:15.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(701331)
;

-- 2022-07-04T15:39:33.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-07-04 18:39:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701331
;

-- 2022-07-04T15:40:12.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,701331,0,665,541065,610028,'F',TO_TIMESTAMP('2022-07-04 18:40:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Warnings',10,0,0,TO_TIMESTAMP('2022-07-04 18:40:12','YYYY-MM-DD HH24:MI:SS'),100,'XL')
;

-- 2022-07-04T15:42:17.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581085,0,'IsLogWarning',TO_TIMESTAMP('2022-07-04 18:42:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Log Warning','Log Warning',TO_TIMESTAMP('2022-07-04 18:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-04T15:42:17.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581085 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-04T15:42:25.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583585,581085,0,20,284,'IsLogWarning',TO_TIMESTAMP('2022-07-04 18:42:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Log Warning',0,0,TO_TIMESTAMP('2022-07-04 18:42:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-04T15:42:25.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583585 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-04T15:42:25.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581085) 
;

-- 2022-07-04T17:08:52.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583585
;

-- 2022-07-04T17:08:52.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=583585
;

-- 2022-07-04T17:09:08.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583586,581085,0,20,284,'IsLogWarning',TO_TIMESTAMP('2022-07-04 20:09:08','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Log Warning',0,0,TO_TIMESTAMP('2022-07-04 20:09:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-04T17:09:08.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583586 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-04T17:09:08.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581085) 
;

-- 2022-07-04T17:09:54.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-07-04 20:09:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583586
;

-- 2022-07-04T17:09:57.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-07-04 20:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583586
;

-- 2018-05-10T16:08:52.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN IsLogWarning CHAR(1) DEFAULT ''N'' CHECK (IsLogWarning IN (''Y'',''N'')) NOT NULL');
;

-- 2022-07-04T17:13:07.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583586,701332,0,245,TO_TIMESTAMP('2022-07-04 20:13:07','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Log Warning',TO_TIMESTAMP('2022-07-04 20:13:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-04T17:13:07.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701332 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-04T17:13:07.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581085) 
;

-- 2022-07-04T17:13:07.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701332
;

-- 2022-07-04T17:13:07.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(701332)
;

-- 2022-07-04T17:13:54.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,701332,0,245,541397,610029,'F',TO_TIMESTAMP('2022-07-04 20:13:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Log Warning',65,0,0,TO_TIMESTAMP('2022-07-04 20:13:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-04T17:14:33.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type@=SQL', SeqNo=400,Updated=TO_TIMESTAMP('2022-07-04 20:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701332
;

-- 2022-07-04T17:14:47.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-07-04 20:14:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701332
;

-- 2022-07-05T14:19:53.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=301,Updated=TO_TIMESTAMP('2022-07-05 17:19:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701332
;




-- 2022-07-05T15:09:12.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNo=70,Updated=TO_TIMESTAMP('2022-07-05 18:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701331
;

-- 2022-07-05T15:09:17.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-07-05 18:09:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610028
;



-- 2022-07-07T15:31:26.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541395, SeqNo=100,Updated=TO_TIMESTAMP('2022-07-07 18:31:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610029
;




-- 2022-07-08T08:06:00.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.',Updated=TO_TIMESTAMP('2022-07-08 11:06:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='en_US'
;

-- 2022-07-08T08:06:00.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'en_US') 
;

-- 2022-07-08T08:06:17.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-08 11:06:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='de_DE'
;

-- 2022-07-08T08:06:17.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'de_DE') 
;

-- 2022-07-08T08:06:17.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581085,'de_DE') 
;

-- 2022-07-08T08:06:17.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE AD_Element_ID=581085
;

-- 2022-07-08T08:06:17.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', AD_Element_ID=581085 WHERE UPPER(ColumnName)='ISLOGWARNING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-08T08:06:17.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE AD_Element_ID=581085 AND IsCentrallyMaintained='Y'
;

-- 2022-07-08T08:06:17.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581085) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581085)
;

-- 2022-07-08T08:06:17.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', CommitWarning = NULL WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:06:17.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:06:17.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Log Warning', Description = 'Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:06:24.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-08 11:06:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='de_CH'
;

-- 2022-07-08T08:06:24.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'de_CH') 
;

-- 2022-07-08T08:06:31.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-08 11:06:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='en_US'
;

-- 2022-07-08T08:06:31.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'en_US') 
;





-- 2022-07-08T08:40:23.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.
A single warning message should have a maximum of 5000 characters.',Updated=TO_TIMESTAMP('2022-07-08 11:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='de_CH'
;

-- 2022-07-08T08:40:23.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'de_CH') 
;

-- 2022-07-08T08:40:52.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2022-07-08 11:40:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='de_CH'
;

-- 2022-07-08T08:40:52.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'de_CH') 
;

-- 2022-07-08T08:41:39.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars) will be logged, while the ones returned with "Raise Notice" will be ignored.',Updated=TO_TIMESTAMP('2022-07-08 11:41:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='de_CH'
;

-- 2022-07-08T08:41:39.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'de_CH') 
;

-- 2022-07-08T08:42:15.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.',Updated=TO_TIMESTAMP('2022-07-08 11:42:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='de_DE'
;

-- 2022-07-08T08:42:15.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'de_DE') 
;

-- 2022-07-08T08:42:15.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581085,'de_DE') 
;

-- 2022-07-08T08:42:15.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE AD_Element_ID=581085
;

-- 2022-07-08T08:42:15.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', AD_Element_ID=581085 WHERE UPPER(ColumnName)='ISLOGWARNING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-08T08:42:15.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE AD_Element_ID=581085 AND IsCentrallyMaintained='Y'
;

-- 2022-07-08T08:42:15.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581085) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581085)
;

-- 2022-07-08T08:42:15.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', CommitWarning = NULL WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:42:15.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:42:15.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Log Warning', Description = 'Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars) will be logged, while the ones returned with "Raise Notice" will be ignored.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:42:20.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars) will be logged, while the ones returned with "Raise Notice" will be ignored.',Updated=TO_TIMESTAMP('2022-07-08 11:42:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='en_US'
;

-- 2022-07-08T08:42:20.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'en_US') 
;

-- 2022-07-08T08:42:51.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.
A single warning message should contain a maximum of 5000 characters.',Updated=TO_TIMESTAMP('2022-07-08 11:42:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='de_CH'
;

-- 2022-07-08T08:42:51.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'de_CH') 
;

-- 2022-07-08T08:42:55.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.',Updated=TO_TIMESTAMP('2022-07-08 11:42:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='de_DE'
;

-- 2022-07-08T08:42:55.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'de_DE') 
;

-- 2022-07-08T08:42:55.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581085,'de_DE') 
;

-- 2022-07-08T08:42:55.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE AD_Element_ID=581085
;

-- 2022-07-08T08:42:55.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', AD_Element_ID=581085 WHERE UPPER(ColumnName)='ISLOGWARNING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-08T08:42:55.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE AD_Element_ID=581085 AND IsCentrallyMaintained='Y'
;

-- 2022-07-08T08:42:55.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581085) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581085)
;

-- 2022-07-08T08:42:55.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.', CommitWarning = NULL WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:42:55.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.' WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:42:55.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Log Warning', Description = 'Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:42:58.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.',Updated=TO_TIMESTAMP('2022-07-08 11:42:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='en_US'
;

-- 2022-07-08T08:42:58.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'en_US') 
;

-- 2022-07-08T08:43:10.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.
A single warning message should contain a maximum of 5000 characters.',Updated=TO_TIMESTAMP('2022-07-08 11:43:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='de_DE'
;

-- 2022-07-08T08:43:10.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'de_DE') 
;

-- 2022-07-08T08:43:10.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581085,'de_DE') 
;

-- 2022-07-08T08:43:10.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.
A single warning message should contain a maximum of 5000 characters.' WHERE AD_Element_ID=581085
;

-- 2022-07-08T08:43:10.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.
A single warning message should contain a maximum of 5000 characters.', AD_Element_ID=581085 WHERE UPPER(ColumnName)='ISLOGWARNING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-08T08:43:10.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLogWarning', Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.
A single warning message should contain a maximum of 5000 characters.' WHERE AD_Element_ID=581085 AND IsCentrallyMaintained='Y'
;

-- 2022-07-08T08:43:10.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.
A single warning message should contain a maximum of 5000 characters.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581085) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581085)
;

-- 2022-07-08T08:43:10.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.
A single warning message should contain a maximum of 5000 characters.', CommitWarning = NULL WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:43:10.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Log Warning', Description='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.
A single warning message should contain a maximum of 5000 characters.' WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:43:10.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Log Warning', Description = 'Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log. Only the messages returned with "Raise Warning" (max. 5000 chars per warning) will be logged, while the ones returned with "Raise Notice" will be ignored.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581085
;

-- 2022-07-08T08:43:14.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Set to true if the warnings raised by the sql function should be logged in the AD_PInstance_Log.
Only the messages returned with "Raise Warning" will be logged, while the ones returned with "Raise Notice" will be ignored.
A single warning message should contain a maximum of 5000 characters.',Updated=TO_TIMESTAMP('2022-07-08 11:43:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581085 AND AD_Language='en_US'
;

-- 2022-07-08T08:43:14.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581085,'en_US') 
;