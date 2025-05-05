-- Column: ExternalSystem_Config_SAP.SFTP_Port
-- 2022-10-18T15:51:12.126846300Z
UPDATE AD_Column SET DefaultValue='22',Updated=TO_TIMESTAMP('2022-10-18 18:51:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584649
;

-- 2022-10-18T15:53:11.582136400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581583,0,'ProcessedDirectory',TO_TIMESTAMP('2022-10-18 18:53:11','YYYY-MM-DD HH24:MI:SS'),100,'Defines where files should be moved after being successfully processed.','D','Y','Processed Directory','Processed Directory',TO_TIMESTAMP('2022-10-18 18:53:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-18T15:53:11.588312400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581583 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ProcessedDirectory
-- 2022-10-18T15:54:09.841372400Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin Dateien nach erfolgreicher Verarbeitung verschoben werden sollen.', Name='Bearbeitetes Verzeichnis', PrintName='Bearbeitetes Verzeichnis',Updated=TO_TIMESTAMP('2022-10-18 18:54:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='de_CH'
;

-- 2022-10-18T15:54:09.865853100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581583,'de_CH') 
;

-- Element: ProcessedDirectory
-- 2022-10-18T15:54:26.290049Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin Dateien nach erfolgreicher Verarbeitung verschoben werden sollen.', Name='Bearbeitetes Verzeichnis', PrintName='Bearbeitetes Verzeichnis',Updated=TO_TIMESTAMP('2022-10-18 18:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='de_DE'
;

-- 2022-10-18T15:54:26.291996300Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581583,'de_DE') 
;

-- 2022-10-18T15:54:26.302655800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581583,'de_DE') 
;

-- Column: ExternalSystem_Config_SAP.ProcessedDirectory
-- 2022-10-18T15:55:47.322205500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584768,581583,0,10,542238,'ProcessedDirectory',TO_TIMESTAMP('2022-10-18 18:55:47','YYYY-MM-DD HH24:MI:SS'),100,'N','move','Legt fest, wohin Dateien nach erfolgreicher Verarbeitung verschoben werden sollen.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bearbeitetes Verzeichnis',0,0,TO_TIMESTAMP('2022-10-18 18:55:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-18T15:55:47.329279800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584768 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-18T15:55:47.336570700Z
/* DDL */  select update_Column_Translation_From_AD_Element(581583) 
;

-- Column: ExternalSystem_Config_SAP.ProcessedDirectory
-- 2022-10-18T15:56:06.840341100Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-10-18 18:56:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584768
;

-- 2022-10-18T15:58:53.651264700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581584,0,'ErroredDirectory',TO_TIMESTAMP('2022-10-18 18:58:53','YYYY-MM-DD HH24:MI:SS'),100,'Defines where files should be moved after attempting to process them with error.','D','Y','Errored Directory','Errored Directory',TO_TIMESTAMP('2022-10-18 18:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-18T15:58:53.654298800Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581584 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ErroredDirectory
-- 2022-10-18T15:59:17.553369700Z
UPDATE AD_Element_Trl SET Description='Gibt an, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen.',Updated=TO_TIMESTAMP('2022-10-18 18:59:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='de_CH'
;

-- 2022-10-18T15:59:17.555368400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'de_CH') 
;

-- Element: ErroredDirectory
-- 2022-10-18T16:00:16.892907900Z
UPDATE AD_Element_Trl SET Name='Fehlerhaftes Verzeichnis', PrintName='Fehlerhaftes Verzeichnis',Updated=TO_TIMESTAMP('2022-10-18 19:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='de_CH'
;

-- 2022-10-18T16:00:16.895039600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'de_CH') 
;

-- Element: ErroredDirectory
-- 2022-10-18T16:01:18.141697700Z
UPDATE AD_Element_Trl SET Description='Gibt an, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen.', Name='Fehlerhaftes Verzeichnis', PrintName='Fehlerhaftes Verzeichnis',Updated=TO_TIMESTAMP('2022-10-18 19:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='de_DE'
;

-- 2022-10-18T16:01:18.143733200Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581584,'de_DE') 
;

-- 2022-10-18T16:01:18.150549100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'de_DE') 
;

-- Column: ExternalSystem_Config_SAP.ErroredDirectory
-- 2022-10-18T16:02:00.213851400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584769,581584,0,10,542238,'ErroredDirectory',TO_TIMESTAMP('2022-10-18 19:02:00','YYYY-MM-DD HH24:MI:SS'),100,'N','error','Gibt an, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Fehlerhaftes Verzeichnis',0,0,TO_TIMESTAMP('2022-10-18 19:02:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-18T16:02:00.218376300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584769 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-18T16:02:00.224733400Z
/* DDL */  select update_Column_Translation_From_AD_Element(581584) 
;

-- 2022-10-18T16:02:52.019276Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581585,0,'PollingFrequency',TO_TIMESTAMP('2022-10-18 19:02:51','YYYY-MM-DD HH24:MI:SS'),100,'Defines how frequently should the process poll for new files.','D','Y','Polling Frequency','Polling Frequency',TO_TIMESTAMP('2022-10-18 19:02:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-18T16:02:52.022238500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581585 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PollingFrequency
-- 2022-10-18T16:03:42.614305Z
UPDATE AD_Element_Trl SET Description='Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.', Name='Abfragefrequenz', PrintName='Abfragefrequenz',Updated=TO_TIMESTAMP('2022-10-18 19:03:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581585 AND AD_Language='de_CH'
;

-- 2022-10-18T16:03:42.616612500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581585,'de_CH') 
;

-- Element: PollingFrequency
-- 2022-10-18T16:03:55.997911300Z
UPDATE AD_Element_Trl SET Description='Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.', Name='Abfragefrequenz', PrintName='Abfragefrequenz',Updated=TO_TIMESTAMP('2022-10-18 19:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581585 AND AD_Language='de_DE'
;

-- 2022-10-18T16:03:55.998882300Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581585,'de_DE') 
;

-- 2022-10-18T16:03:56.000870100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581585,'de_DE') 
;

-- Column: ExternalSystem_Config_SAP.PollingFrequency
-- 2022-10-18T16:05:20.844809500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584770,581585,0,22,542238,'PollingFrequency',TO_TIMESTAMP('2022-10-18 19:05:20','YYYY-MM-DD HH24:MI:SS'),100,'N','1000','Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.','de.metas.externalsystem',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Abfragefrequenz',0,0,TO_TIMESTAMP('2022-10-18 19:05:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-18T16:05:20.847811400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584770 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-18T16:05:20.849810700Z
/* DDL */  select update_Column_Translation_From_AD_Element(581585) 
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Bearbeitetes Verzeichnis
-- Column: ExternalSystem_Config_SAP.ProcessedDirectory
-- 2022-10-18T16:08:54.133906600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584768,707803,0,546647,0,TO_TIMESTAMP('2022-10-18 19:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin Dateien nach erfolgreicher Verarbeitung verschoben werden sollen.',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Bearbeitetes Verzeichnis',0,100,0,1,1,TO_TIMESTAMP('2022-10-18 19:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-18T16:08:54.144339800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-18T16:08:54.150342600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581583) 
;

-- 2022-10-18T16:08:54.163675900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707803
;

-- 2022-10-18T16:08:54.169684100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707803)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Fehlerhaftes Verzeichnis
-- Column: ExternalSystem_Config_SAP.ErroredDirectory
-- 2022-10-18T16:09:19.161766100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584769,707804,0,546647,0,TO_TIMESTAMP('2022-10-18 19:09:19','YYYY-MM-DD HH24:MI:SS'),100,'Gibt an, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen.',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Fehlerhaftes Verzeichnis',0,100,0,1,1,TO_TIMESTAMP('2022-10-18 19:09:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-18T16:09:19.163767300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-18T16:09:19.164766200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581584) 
;

-- 2022-10-18T16:09:19.166848800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707804
;

-- 2022-10-18T16:09:19.166848800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707804)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Abfragefrequenz
-- Column: ExternalSystem_Config_SAP.PollingFrequency
-- 2022-10-18T16:09:54.778202500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584770,707805,0,546647,0,TO_TIMESTAMP('2022-10-18 19:09:54','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Abfragefrequenz',0,100,0,1,1,TO_TIMESTAMP('2022-10-18 19:09:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-18T16:09:54.781371500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-18T16:09:54.783629200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581585) 
;

-- 2022-10-18T16:09:54.787625400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707805
;

-- 2022-10-18T16:09:54.793288Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707805)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.Bearbeitetes Verzeichnis
-- Column: ExternalSystem_Config_SAP.ProcessedDirectory
-- 2022-10-18T16:10:39.740224900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707803,0,546647,613278,549954,'F',TO_TIMESTAMP('2022-10-18 19:10:39','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin Dateien nach erfolgreicher Verarbeitung verschoben werden sollen.','Y','N','N','Y','N','N','N',0,'Bearbeitetes Verzeichnis',70,0,0,TO_TIMESTAMP('2022-10-18 19:10:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.Fehlerhaftes Verzeichnis
-- Column: ExternalSystem_Config_SAP.ErroredDirectory
-- 2022-10-18T16:11:12.802401Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707804,0,546647,613279,549954,'F',TO_TIMESTAMP('2022-10-18 19:11:12','YYYY-MM-DD HH24:MI:SS'),100,'Gibt an, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen.','Y','N','N','Y','N','N','N',0,'Fehlerhaftes Verzeichnis',80,0,0,TO_TIMESTAMP('2022-10-18 19:11:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.Abfragefrequenz
-- Column: ExternalSystem_Config_SAP.PollingFrequency
-- 2022-10-18T16:11:28.921938200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707805,0,546647,613280,549954,'F',TO_TIMESTAMP('2022-10-18 19:11:28','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.','Y','N','N','Y','N','N','N',0,'Abfragefrequenz',90,0,0,TO_TIMESTAMP('2022-10-18 19:11:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-18T16:36:08.219312500Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN ProcessedDirectory VARCHAR(255) DEFAULT ''move'' NOT NULL')
;

-- 2022-10-18T16:36:19.456260400Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN ErroredDirectory VARCHAR(255) DEFAULT ''error'' NOT NULL')
;

-- 2022-10-18T16:36:33.404377600Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN PollingFrequency NUMERIC DEFAULT 1000 NOT NULL')
;

-- Column: ExternalSystem_Config_SAP.PollingFrequency
-- 2022-10-19T10:54:34.565920100Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0',Updated=TO_TIMESTAMP('2022-10-19 13:54:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584770
;

-- 2022-10-19T10:54:39.839803Z
INSERT INTO t_alter_column values('externalsystem_config_sap','PollingFrequency','NUMERIC(10)',null,'0')
;

-- 2022-10-19T10:54:39.981116800Z
UPDATE ExternalSystem_Config_SAP SET PollingFrequency=0 WHERE PollingFrequency IS NULL
;

-- Column: ExternalSystem_Config_SAP.PollingFrequency
-- 2022-10-19T10:55:16.499602800Z
UPDATE AD_Column SET DefaultValue='1000',Updated=TO_TIMESTAMP('2022-10-19 13:55:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584770
;

-- 2022-10-19T10:55:20.185125500Z
INSERT INTO t_alter_column values('externalsystem_config_sap','PollingFrequency','NUMERIC(10)',null,'1000')
;

-- 2022-10-19T10:55:20.200896800Z
UPDATE ExternalSystem_Config_SAP SET PollingFrequency=1000 WHERE PollingFrequency IS NULL
;

-- 2022-10-19T13:43:45.815500900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581586,0,'PollingFrequencyInMs',TO_TIMESTAMP('2022-10-19 16:43:45','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.','U','Y','Abfragefrequenz in Millisekunden','Abfragefrequenz in Millisekunden',TO_TIMESTAMP('2022-10-19 16:43:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-19T13:43:45.823432600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581586 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-19T13:44:44.487034500Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-10-19 16:44:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581586
;

-- 2022-10-19T13:44:44.507520300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581586,'de_DE')
;

-- Element: PollingFrequencyInMs
-- 2022-10-19T13:45:32.025532300Z
UPDATE AD_Element_Trl SET Description='Defines how frequently should the process poll for new files.', Name='Frequency In Milliseconds', PrintName='Frequency In Milliseconds',Updated=TO_TIMESTAMP('2022-10-19 16:45:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581586 AND AD_Language='nl_NL'
;

-- 2022-10-19T13:45:32.026564200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581586,'nl_NL')
;

-- Element: PollingFrequencyInMs
-- 2022-10-19T13:45:35.959356500Z
UPDATE AD_Element_Trl SET Name='Frequency In Milliseconds', PrintName='Frequency In Milliseconds',Updated=TO_TIMESTAMP('2022-10-19 16:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581586 AND AD_Language='en_US'
;

-- 2022-10-19T13:45:35.960816300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581586,'en_US')
;

-- Element: PollingFrequencyInMs
-- 2022-10-19T13:45:43.424342600Z
UPDATE AD_Element_Trl SET Description='Defines how frequently should the process poll for new files.',Updated=TO_TIMESTAMP('2022-10-19 16:45:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581586 AND AD_Language='en_US'
;

-- 2022-10-19T13:45:43.426341600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581586,'en_US')
;

-- Column: ExternalSystem_Config_SAP.PollingFrequencyInMs
-- 2022-10-19T13:46:25.748827400Z
UPDATE AD_Column SET AD_Element_ID=581586, ColumnName='PollingFrequencyInMs', Description='Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.', Help=NULL, Name='Abfragefrequenz in Millisekunden',Updated=TO_TIMESTAMP('2022-10-19 16:46:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584770
;

-- 2022-10-19T13:46:25.750830Z
UPDATE AD_Field SET Name='Abfragefrequenz in Millisekunden', Description='Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.', Help=NULL WHERE AD_Column_ID=584770
;

-- 2022-10-19T13:46:25.754825900Z
/* DDL */  select update_Column_Translation_From_AD_Element(581586)
;

/* DDL */ select db_alter_table('ExternalSystem_Config_SAP', 'ALTER TABLE ExternalSystem_Config_SAP RENAME COLUMN PollingFrequency to PollingFrequencyInMs;');
;