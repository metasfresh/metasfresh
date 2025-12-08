-- Name: C_POS_Payment_Status
-- 2024-10-04T13:38:49.620Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541897,TO_TIMESTAMP('2024-10-04 16:38:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','N','C_POS_Payment_Status',TO_TIMESTAMP('2024-10-04 16:38:49','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-10-04T13:38:49.623Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541897 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_POS_Payment_Status
-- Value: NEW
-- ValueName: New
-- 2024-10-04T13:39:25.663Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541897,543736,TO_TIMESTAMP('2024-10-04 16:39:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','New',TO_TIMESTAMP('2024-10-04 16:39:25','YYYY-MM-DD HH24:MI:SS'),100,'NEW','New')
;

-- 2024-10-04T13:39:25.666Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543736 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_POS_Payment_Status
-- Value: PENDING
-- ValueName: Pending
-- 2024-10-04T13:39:53.416Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541897,543737,TO_TIMESTAMP('2024-10-04 16:39:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Pending',TO_TIMESTAMP('2024-10-04 16:39:53','YYYY-MM-DD HH24:MI:SS'),100,'PENDING','Pending')
;

-- 2024-10-04T13:39:53.418Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543737 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_POS_Payment_Status
-- Value: ERROR
-- ValueName: Error
-- 2024-10-04T13:40:31.991Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541897,543738,TO_TIMESTAMP('2024-10-04 16:40:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Error',TO_TIMESTAMP('2024-10-04 16:40:31','YYYY-MM-DD HH24:MI:SS'),100,'ERROR','Error')
;

-- 2024-10-04T13:40:31.993Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543738 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_POS_Payment_Status
-- Value: SUCCESS
-- ValueName: Success
-- 2024-10-04T13:41:00.484Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541897,543739,TO_TIMESTAMP('2024-10-04 16:41:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Success',TO_TIMESTAMP('2024-10-04 16:41:00','YYYY-MM-DD HH24:MI:SS'),100,'SUCCESS','Success')
;

-- 2024-10-04T13:41:00.487Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543739 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-10-04T13:42:43.685Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583306,0,'POSPaymentProcessorStatus',TO_TIMESTAMP('2024-10-04 16:42:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Payment Processor Status','Payment Processor Status',TO_TIMESTAMP('2024-10-04 16:42:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T13:42:43.689Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583306 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: POSPaymentProcessorStatus
-- 2024-10-04T13:42:57.881Z
UPDATE AD_Reference SET Name='POSPaymentProcessorStatus',Updated=TO_TIMESTAMP('2024-10-04 16:42:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541897
;

-- Reference: POSPaymentProcessorStatus
-- Value: SUCCESS
-- ValueName: Success
-- 2024-10-04T13:43:11.891Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543739
;

-- 2024-10-04T13:43:11.900Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543739
;

-- Reference: POSPaymentProcessorStatus
-- Value: PENDING
-- ValueName: Pending
-- 2024-10-04T13:43:13.908Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543737
;

-- 2024-10-04T13:43:13.914Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543737
;

-- Reference: POSPaymentProcessorStatus
-- Value: NEW
-- ValueName: New
-- 2024-10-04T13:43:16.086Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543736
;

-- 2024-10-04T13:43:16.093Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543736
;

-- Reference: POSPaymentProcessorStatus
-- Value: ERROR
-- ValueName: Error
-- 2024-10-04T13:43:17.994Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543738
;

-- 2024-10-04T13:43:18Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543738
;

-- Reference: POSPaymentProcessorStatus
-- Value: SUCCESSFUL
-- ValueName: SUCCESSFUL
-- 2024-10-04T13:43:35.308Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541897,543740,TO_TIMESTAMP('2024-10-04 16:43:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','SUCCESSFUL',TO_TIMESTAMP('2024-10-04 16:43:35','YYYY-MM-DD HH24:MI:SS'),100,'SUCCESSFUL','SUCCESSFUL')
;

-- 2024-10-04T13:43:35.311Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543740 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: POSPaymentProcessorStatus
-- Value: CANCELLED
-- ValueName: CANCELLED
-- 2024-10-04T13:43:47.619Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541897,543741,TO_TIMESTAMP('2024-10-04 16:43:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','CANCELLED',TO_TIMESTAMP('2024-10-04 16:43:47','YYYY-MM-DD HH24:MI:SS'),100,'CANCELLED','CANCELLED')
;

-- 2024-10-04T13:43:47.622Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543741 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: POSPaymentProcessorStatus
-- Value: FAILED
-- ValueName: FAILED
-- 2024-10-04T13:44:04.377Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541897,543742,TO_TIMESTAMP('2024-10-04 16:44:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','FAILED',TO_TIMESTAMP('2024-10-04 16:44:04','YYYY-MM-DD HH24:MI:SS'),100,'FAILED','FAILED')
;

-- 2024-10-04T13:44:04.379Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543742 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: POSPaymentProcessorStatus
-- Value: PENDING
-- ValueName: PENDING
-- 2024-10-04T13:44:21.899Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541897,543743,TO_TIMESTAMP('2024-10-04 16:44:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','PENDING',TO_TIMESTAMP('2024-10-04 16:44:21','YYYY-MM-DD HH24:MI:SS'),100,'PENDING','PENDING')
;

-- 2024-10-04T13:44:21.900Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543743 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: POSPaymentProcessorStatus
-- Value: NEW
-- ValueName: NEW
-- 2024-10-04T13:44:37.015Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541897,543744,TO_TIMESTAMP('2024-10-04 16:44:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','NEW',TO_TIMESTAMP('2024-10-04 16:44:36','YYYY-MM-DD HH24:MI:SS'),100,'NEW','NEW')
;

-- 2024-10-04T13:44:37.017Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543744 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-10-04T13:45:23.328Z
UPDATE AD_Reference_Trl SET Name='POSPaymentProcessorStatus',Updated=TO_TIMESTAMP('2024-10-04 16:45:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541897
;

-- 2024-10-04T13:45:24.968Z
UPDATE AD_Reference_Trl SET Name='POSPaymentProcessorStatus',Updated=TO_TIMESTAMP('2024-10-04 16:45:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541897
;

-- 2024-10-04T13:45:26.837Z
UPDATE AD_Reference_Trl SET Name='POSPaymentProcessorStatus',Updated=TO_TIMESTAMP('2024-10-04 16:45:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541897
;

-- 2024-10-04T13:45:28.990Z
UPDATE AD_Reference_Trl SET Name='POSPaymentProcessorStatus',Updated=TO_TIMESTAMP('2024-10-04 16:45:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=541897
;

-- Column: C_POS_Payment.POSPaymentProcessorStatus
-- Column: C_POS_Payment.POSPaymentProcessorStatus
-- 2024-10-04T13:45:46.292Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589240,583306,0,17,541897,542437,'XX','POSPaymentProcessorStatus',TO_TIMESTAMP('2024-10-04 16:45:46','YYYY-MM-DD HH24:MI:SS'),100,'N','NEW','de.metas.pos',0,20,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Payment Processor Status',0,0,TO_TIMESTAMP('2024-10-04 16:45:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T13:45:46.296Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589240 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T13:45:46.303Z
/* DDL */  select update_Column_Translation_From_AD_Element(583306) 
;

-- 2024-10-04T13:48:45.419Z
UPDATE AD_Element SET ColumnName='POSPaymentProcessingStatus',Updated=TO_TIMESTAMP('2024-10-04 16:48:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583306
;

-- 2024-10-04T13:48:45.422Z
UPDATE AD_Column SET ColumnName='POSPaymentProcessingStatus' WHERE AD_Element_ID=583306
;

-- 2024-10-04T13:48:45.424Z
UPDATE AD_Process_Para SET ColumnName='POSPaymentProcessingStatus' WHERE AD_Element_ID=583306
;

-- 2024-10-04T13:48:45.439Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583306,'de_DE') 
;

-- Element: POSPaymentProcessingStatus
-- 2024-10-04T13:48:54.159Z
UPDATE AD_Element_Trl SET Name='Payment Processing Status', PrintName='Payment Processing Status',Updated=TO_TIMESTAMP('2024-10-04 16:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583306 AND AD_Language='de_CH'
;

-- 2024-10-04T13:48:54.165Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583306,'de_CH') 
;

-- Element: POSPaymentProcessingStatus
-- 2024-10-04T13:48:58.024Z
UPDATE AD_Element_Trl SET Name='Payment Processing Status', PrintName='Payment Processing Status',Updated=TO_TIMESTAMP('2024-10-04 16:48:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583306 AND AD_Language='en_US'
;

-- 2024-10-04T13:48:58.027Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583306,'en_US') 
;

-- Element: POSPaymentProcessingStatus
-- 2024-10-04T13:49:00.549Z
UPDATE AD_Element_Trl SET Name='Payment Processing Status', PrintName='Payment Processing Status',Updated=TO_TIMESTAMP('2024-10-04 16:49:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583306 AND AD_Language='de_DE'
;

-- 2024-10-04T13:49:00.553Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583306,'de_DE') 
;

-- 2024-10-04T13:49:00.555Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583306,'de_DE') 
;

-- Element: POSPaymentProcessingStatus
-- 2024-10-04T13:49:03.299Z
UPDATE AD_Element_Trl SET Name='Payment Processing Status', PrintName='Payment Processing Status',Updated=TO_TIMESTAMP('2024-10-04 16:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583306 AND AD_Language='fr_CH'
;

-- 2024-10-04T13:49:03.302Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583306,'fr_CH') 
;

-- Element: POSPaymentProcessingStatus
-- 2024-10-04T13:49:17.023Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-04 16:49:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583306 AND AD_Language='en_US'
;

-- 2024-10-04T13:49:17.028Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583306,'en_US') 
;

-- Name: POSPaymentProcessingStatus
-- 2024-10-04T13:49:40.719Z
UPDATE AD_Reference SET Name='POSPaymentProcessingStatus',Updated=TO_TIMESTAMP('2024-10-04 16:49:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541897
;

-- 2024-10-04T13:49:45.187Z
UPDATE AD_Reference_Trl SET Name='POSPaymentProcessingStatus',Updated=TO_TIMESTAMP('2024-10-04 16:49:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541897
;

-- 2024-10-04T13:49:46.674Z
UPDATE AD_Reference_Trl SET Name='POSPaymentProcessingStatus',Updated=TO_TIMESTAMP('2024-10-04 16:49:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541897
;

-- 2024-10-04T13:49:48.130Z
UPDATE AD_Reference_Trl SET Name='POSPaymentProcessingStatus',Updated=TO_TIMESTAMP('2024-10-04 16:49:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541897
;

-- 2024-10-04T13:49:50.531Z
UPDATE AD_Reference_Trl SET Name='POSPaymentProcessingStatus',Updated=TO_TIMESTAMP('2024-10-04 16:49:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=541897
;

-- 2024-10-04T13:49:53.944Z
/* DDL */ SELECT public.db_alter_table('C_POS_Payment','ALTER TABLE public.C_POS_Payment ADD COLUMN POSPaymentProcessingStatus VARCHAR(20) DEFAULT ''NEW'' NOT NULL')
;

-- Field: POS Order -> POS Payment -> Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- 2024-10-04T13:50:14.875Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589240,731843,0,547593,TO_TIMESTAMP('2024-10-04 16:50:14','YYYY-MM-DD HH24:MI:SS'),100,20,'de.metas.pos','Y','N','N','N','N','N','N','N','Payment Processing Status',TO_TIMESTAMP('2024-10-04 16:50:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T13:50:14.879Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T13:50:14.882Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583306) 
;

-- 2024-10-04T13:50:14.890Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731843
;

-- 2024-10-04T13:50:14.892Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731843)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20
-- UI Element Group: status
-- 2024-10-04T13:50:44.593Z
UPDATE AD_UI_ElementGroup SET Name='status',Updated=TO_TIMESTAMP('2024-10-04 16:50:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551964
;

-- UI Element: POS Order -> POS Payment.Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- 2024-10-04T13:51:03.889Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731843,0,547593,551964,626145,'F',TO_TIMESTAMP('2024-10-04 16:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Payment Processing Status',20,0,0,TO_TIMESTAMP('2024-10-04 16:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- 2024-10-04T13:51:18.389Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2024-10-04 16:51:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626145
;

-- UI Element: POS Order -> POS Payment.Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- 2024-10-04T13:51:34.233Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-10-04 16:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626145
;

-- UI Element: POS Order -> POS Payment.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- 2024-10-04T13:51:34.243Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-10-04 16:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625769
;

