-- 2022-10-26T09:16:45.131Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581623,0,'SFTP_CreditLimit_TargetDirectory',TO_TIMESTAMP('2022-10-26 12:16:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','SFTP Credit Limit TargetDirectory','SFTP Credit Limit TargetDirectory',TO_TIMESTAMP('2022-10-26 12:16:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-26T09:16:45.139Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581623 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_TargetDirectory
-- 2022-10-26T09:22:57.252Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584828,581623,0,10,542238,'SFTP_CreditLimit_TargetDirectory',TO_TIMESTAMP('2022-10-26 12:22:57','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SFTP Credit Limit TargetDirectory',0,0,TO_TIMESTAMP('2022-10-26 12:22:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-26T09:22:57.260Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584828 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-26T09:22:57.287Z
/* DDL */  select update_Column_Translation_From_AD_Element(581623) 
;

-- 2022-10-26T09:22:58.113Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN SFTP_CreditLimit_TargetDirectory VARCHAR(255)')
;

-- Reference: External_Request SAP
-- Value: startCreditLimitsSync
-- ValueName: Start Credit Limits Synchronization
-- 2022-10-26T09:55:11.259Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543329,541661,TO_TIMESTAMP('2022-10-26 12:55:11','YYYY-MM-DD HH24:MI:SS'),100,'Starts the credit limits sychronization with SAP external system','de.metas.externalsystem','Y','Start Credit Limits Synchronization',TO_TIMESTAMP('2022-10-26 12:55:11','YYYY-MM-DD HH24:MI:SS'),100,'startCreditLimitsSync','Start Credit Limits Synchronization')
;

-- 2022-10-26T09:55:11.264Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543329 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitsSync
-- ValueName: Stop Credit Limits Synchronization
-- 2022-10-26T09:56:06.936Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543330,541661,TO_TIMESTAMP('2022-10-26 12:56:06','YYYY-MM-DD HH24:MI:SS'),100,'Stops the credit limits synchronization with SAP external system','de.metas.externalsystem','Y','Stop Credit Limits Synchronization',TO_TIMESTAMP('2022-10-26 12:56:06','YYYY-MM-DD HH24:MI:SS'),100,'stopCreditLimitsSync','Stop Credit Limits Synchronization')
;

-- 2022-10-26T09:56:06.938Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543330 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;


-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP Credit Limit TargetDirectory
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_TargetDirectory
-- 2022-10-26T10:05:51.268Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584828,707881,0,546647,0,TO_TIMESTAMP('2022-10-26 13:05:51','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','SFTP Credit Limit TargetDirectory',0,110,0,1,1,TO_TIMESTAMP('2022-10-26 13:05:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-26T10:05:51.281Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-26T10:05:51.287Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581623) 
;

-- 2022-10-26T10:05:51.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707881
;

-- 2022-10-26T10:05:51.310Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707881)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP Credit Limit TargetDirectory
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_TargetDirectory
-- 2022-10-26T10:06:45.170Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707881,0,546647,613321,549954,'F',TO_TIMESTAMP('2022-10-26 13:06:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SFTP Credit Limit TargetDirectory',100,0,0,TO_TIMESTAMP('2022-10-26 13:06:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-07T10:45:09.687966200Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Service_ID,IsActive,Name,Type,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2022-10-07 13:45:09','YYYY-MM-DD HH24:MI:SS'),100,540004,'Y','SFTP Sync-CreditLimits','SAP',TO_TIMESTAMP('2022-10-07 13:45:09','YYYY-MM-DD HH24:MI:SS'),100,'SFTPSyncCreditLimits')
;

-- 2022-10-07T10:45:12.897805900Z
UPDATE ExternalSystem_Service SET Description='SFTP Sync-CreditLimits',Updated=TO_TIMESTAMP('2022-10-07 13:45:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540004
;

-- 2022-10-07T10:46:07.923288900Z
UPDATE ExternalSystem_Service SET EnableCommand='startCreditLimitsSync',Updated=TO_TIMESTAMP('2022-10-07 13:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540004
;

-- 2022-10-07T10:46:17.838934100Z
UPDATE ExternalSystem_Service SET DisableCommand='stopCreditLimitsSync',Updated=TO_TIMESTAMP('2022-10-07 13:46:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540004
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-04T09:10:22.829Z
UPDATE AD_Element_Trl SET Name='SFTP Kreditlimits Zielverzeichnis', PrintName='SFTP Kreditlimits Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-04 11:10:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='de_CH'
;

-- 2022-11-04T09:10:22.833Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'de_CH')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-04T09:10:48.827Z
UPDATE AD_Element_Trl SET Name='SFTP Kreditlimits Zielverzeichnis', PrintName='SFTP Kreditlimits Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-04 11:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='de_DE'
;

-- 2022-11-04T09:10:48.830Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581623,'de_DE')
;

-- 2022-11-04T09:10:48.869Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'de_DE')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-04T09:13:52.633Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das zum Abrufen der Kreditlimits vom SFTP-Server verwendet wird.',Updated=TO_TIMESTAMP('2022-11-04 11:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='de_DE'
;

-- 2022-11-04T09:13:52.636Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581623,'de_DE')
;

-- 2022-11-04T09:13:52.637Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'de_DE')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-04T09:13:54.463Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das zum Abrufen der Kreditlimits vom SFTP-Server verwendet wird.',Updated=TO_TIMESTAMP('2022-11-04 11:13:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='de_CH'
;

-- 2022-11-04T09:13:54.465Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'de_CH')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-04T09:16:13.272Z
UPDATE AD_Element_Trl SET Description='The directory used to retrieve credit limits from the sftp server. (If no value is specified here, the files are pulled from the root directory of the sftp server).',Updated=TO_TIMESTAMP('2022-11-04 11:16:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='en_US'
;

-- 2022-11-04T09:16:13.277Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'en_US')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-04T09:16:21.070Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das zum Abrufen der Kreditlimits vom sftp-Server verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).',Updated=TO_TIMESTAMP('2022-11-04 11:16:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='de_CH'
;

-- 2022-11-04T09:16:21.071Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'de_CH')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-04T09:16:24.075Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das zum Abrufen der Kreditlimits vom sftp-Server verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).',Updated=TO_TIMESTAMP('2022-11-04 11:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='de_DE'
;

-- 2022-11-04T09:16:24.076Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581623,'de_DE')
;

-- 2022-11-04T09:16:24.078Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'de_DE')
;

-- Reference Item: External_Request SAP -> startCreditLimitsSync_Start Credit Limits Synchronization
-- 2022-11-04T09:19:12.487Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Synchronisation der Kreditlimits mit dem externen SAP-System ', Name='Start der Kreditlimitsynchronisation',Updated=TO_TIMESTAMP('2022-11-04 11:19:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> startCreditLimitsSync_Start Credit Limits Synchronization
-- 2022-11-04T09:19:23.295Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Synchronisation der Kreditlimits mit dem externen SAP-System ',Updated=TO_TIMESTAMP('2022-11-04 11:19:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> startCreditLimitsSync_Start Credit Limits Synchronization
-- 2022-11-04T09:19:26.796Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation',Updated=TO_TIMESTAMP('2022-11-04 11:19:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> stopCreditLimitsSync_Stop Credit Limits Synchronization
-- 2022-11-04T09:20:48.188Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Synchronisation des Kreditlimits mit dem externen SAP-System', Name='Kreditlimit-Synchronisation stoppen',Updated=TO_TIMESTAMP('2022-11-04 11:20:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopCreditLimitsSync_Stop Credit Limits Synchronization
-- 2022-11-04T09:20:57.233Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Synchronisation des Kreditlimits mit dem externen SAP-System', Name='Kreditlimit-Synchronisation stoppen',Updated=TO_TIMESTAMP('2022-11-04 11:20:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543330
;

-- Reference: External_Request SAP
-- Value: startCreditLimitsSync
-- ValueName: Start Credit Limits Synchronization
-- 2022-11-10T07:22:00.663Z
UPDATE AD_Ref_List SET Name='Start der Kreditlimitsynchronisation',Updated=TO_TIMESTAMP('2022-11-10 09:22:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543329
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitsSync
-- ValueName: Stop Credit Limits Synchronization
-- 2022-11-10T07:22:28.023Z
UPDATE AD_Ref_List SET Name='Stop Kreditlimit-Synchronisation',Updated=TO_TIMESTAMP('2022-11-10 09:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopCreditLimitsSync_Stop Credit Limits Synchronization
-- 2022-11-10T07:22:34.170Z
UPDATE AD_Ref_List_Trl SET Name='Stop Kreditlimit-Synchronisation',Updated=TO_TIMESTAMP('2022-11-10 09:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543330
;

