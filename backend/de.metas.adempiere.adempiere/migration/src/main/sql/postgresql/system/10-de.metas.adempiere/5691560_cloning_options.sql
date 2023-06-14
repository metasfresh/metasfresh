-- 2023-06-13T19:48:16.635Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582434,0,'CloningStrategy',TO_TIMESTAMP('2023-06-13 22:48:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cloning Strategy','Cloning Strategy',TO_TIMESTAMP('2023-06-13 22:48:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-13T19:48:16.645Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582434 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: AD_Column_CloningStrategy
-- 2023-06-13T19:48:47.948Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541754,TO_TIMESTAMP('2023-06-13 22:48:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_Column_CloningStrategy',TO_TIMESTAMP('2023-06-13 22:48:47','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-06-13T19:48:47.951Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541754 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: AD_Column_CloningStrategy
-- Value: DC
-- ValueName: DirectCopy
-- 2023-06-13T19:49:47.905Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541754,543488,TO_TIMESTAMP('2023-06-13 22:49:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Direct Copy',TO_TIMESTAMP('2023-06-13 22:49:47','YYYY-MM-DD HH24:MI:SS'),100,'DC','DirectCopy')
;

-- 2023-06-13T19:49:47.907Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543488 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Column_CloningStrategy
-- Value: DV
-- ValueName: UseDefaultValue
-- 2023-06-13T19:50:30.444Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541754,543489,TO_TIMESTAMP('2023-06-13 22:50:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Use Default Value',TO_TIMESTAMP('2023-06-13 22:50:30','YYYY-MM-DD HH24:MI:SS'),100,'DV','UseDefaultValue')
;

-- 2023-06-13T19:50:30.446Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543489 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Column_CloningStrategy
-- Value: UQ
-- ValueName: MakeUnique
-- 2023-06-13T19:51:16.075Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541754,543490,TO_TIMESTAMP('2023-06-13 22:51:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Make Unique',TO_TIMESTAMP('2023-06-13 22:51:15','YYYY-MM-DD HH24:MI:SS'),100,'UQ','MakeUnique')
;

-- 2023-06-13T19:51:16.077Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543490 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Column_CloningStrategy
-- Value: SK
-- ValueName: Skip
-- 2023-06-13T19:51:38.481Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541754,543491,TO_TIMESTAMP('2023-06-13 22:51:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Skip',TO_TIMESTAMP('2023-06-13 22:51:38','YYYY-MM-DD HH24:MI:SS'),100,'SK','Skip')
;

-- 2023-06-13T19:51:38.482Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543491 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Column_CloningStrategy
-- Value: XX
-- ValueName: Auto
-- 2023-06-13T19:52:06.515Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541754,543492,TO_TIMESTAMP('2023-06-13 22:52:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Auto (legacy)',TO_TIMESTAMP('2023-06-13 22:52:06','YYYY-MM-DD HH24:MI:SS'),100,'XX','Auto')
;

-- 2023-06-13T19:52:06.517Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543492 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: AD_Column.CloningStrategy
-- 2023-06-13T19:52:58.728Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586811,582434,0,17,541754,101,'CloningStrategy',TO_TIMESTAMP('2023-06-13 22:52:58','YYYY-MM-DD HH24:MI:SS'),100,'N','XX','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cloning Strategy',0,0,TO_TIMESTAMP('2023-06-13 22:52:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-13T19:52:58.731Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586811 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T19:52:59.265Z
/* DDL */  select update_Column_Translation_From_AD_Element(582434) 
;

-- 2023-06-13T19:53:00.324Z
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN CloningStrategy VARCHAR(2) DEFAULT ''XX'' NOT NULL')
;

-- Reference: AD_Column_CloningStrategy
-- Value: DownlineCopyingStrategy
-- ValueName: Downline Copying Strategy
-- 2023-06-13T20:12:52.350Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541754,543493,TO_TIMESTAMP('2023-06-13 23:12:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Downline Copying Strategy',TO_TIMESTAMP('2023-06-13 23:12:52','YYYY-MM-DD HH24:MI:SS'),100,'DownlineCopyingStrategy','Downline Copying Strategy')
;

-- 2023-06-13T20:12:52.351Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543493 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Column_CloningStrategy
-- Value: DownlineCopyingStrategy
-- ValueName: Downline Copying Strategy
-- 2023-06-13T20:13:04.870Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543493
;

-- 2023-06-13T20:13:04.876Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543493
;

-- 2023-06-13T20:13:43.073Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582435,0,'DownlineCopyingStrategy',TO_TIMESTAMP('2023-06-13 23:13:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Downline Copying Strategy','Downline Copying Strategy',TO_TIMESTAMP('2023-06-13 23:13:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-13T20:13:43.075Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582435 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: AD_Table_DownlineCopyingStrategy
-- 2023-06-13T20:14:11.114Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541755,TO_TIMESTAMP('2023-06-13 23:14:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_Table_DownlineCopyingStrategy',TO_TIMESTAMP('2023-06-13 23:14:10','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-06-13T20:14:11.115Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541755 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: AD_Table_DownlineCopyingStrategy
-- Value: S
-- ValueName: Skip
-- 2023-06-13T20:14:39.053Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541755,543494,TO_TIMESTAMP('2023-06-13 23:14:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Skip',TO_TIMESTAMP('2023-06-13 23:14:38','YYYY-MM-DD HH24:MI:SS'),100,'S','Skip')
;

-- 2023-06-13T20:14:39.055Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543494 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Table_DownlineCopyingStrategy
-- Value: A
-- ValueName: Auto
-- 2023-06-13T20:15:27.222Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541755,543495,TO_TIMESTAMP('2023-06-13 23:15:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Auto-Detect and Copy Children',TO_TIMESTAMP('2023-06-13 23:15:27','YYYY-MM-DD HH24:MI:SS'),100,'A','Auto')
;

-- 2023-06-13T20:15:27.224Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543495 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: AD_Table.DownlineCopyingStrategy
-- 2023-06-13T20:16:08.964Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586812,582435,0,17,541755,100,'DownlineCopyingStrategy',TO_TIMESTAMP('2023-06-13 23:16:08','YYYY-MM-DD HH24:MI:SS'),100,'N','A','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Downline Copying Strategy',0,0,TO_TIMESTAMP('2023-06-13 23:16:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-13T20:16:08.966Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586812 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T20:16:09.705Z
/* DDL */  select update_Column_Translation_From_AD_Element(582435) 
;

-- 2023-06-13T20:16:13.902Z
/* DDL */ SELECT public.db_alter_table('AD_Table','ALTER TABLE public.AD_Table ADD COLUMN DownlineCopyingStrategy CHAR(1) DEFAULT ''A'' NOT NULL')
;

-- Reference: AD_Table_DownlineCopyingStrategy
-- Value: S
-- ValueName: Skip
-- 2023-06-13T20:17:04.068Z
UPDATE AD_Ref_List SET Name='Skip copying children',Updated=TO_TIMESTAMP('2023-06-13 23:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543494
;

-- 2023-06-13T20:17:04.069Z
UPDATE AD_Ref_List_Trl trl SET Name='Skip copying children' WHERE AD_Ref_List_ID=543494 AND AD_Language='en_US'
;

-- Reference: AD_Table_DownlineCopyingStrategy
-- Value: S
-- ValueName: Skip
-- 2023-06-13T20:17:12.946Z
UPDATE AD_Ref_List SET Name='Skip Copying Children',Updated=TO_TIMESTAMP('2023-06-13 23:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543494
;

-- 2023-06-13T20:17:12.947Z
UPDATE AD_Ref_List_Trl trl SET Name='Skip Copying Children' WHERE AD_Ref_List_ID=543494 AND AD_Language='en_US'
;

-- Reference Item: AD_Table_DownlineCopyingStrategy -> S_Skip
-- 2023-06-13T20:17:17.083Z
UPDATE AD_Ref_List_Trl SET Name='Skip Copying Children',Updated=TO_TIMESTAMP('2023-06-13 23:17:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543494
;

-- Reference Item: AD_Table_DownlineCopyingStrategy -> S_Skip
-- 2023-06-13T20:17:18.718Z
UPDATE AD_Ref_List_Trl SET Name='Skip Copying Children',Updated=TO_TIMESTAMP('2023-06-13 23:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543494
;

-- Reference Item: AD_Table_DownlineCopyingStrategy -> S_Skip
-- 2023-06-13T20:17:29.760Z
UPDATE AD_Ref_List_Trl SET Name='Skip Copying Children',Updated=TO_TIMESTAMP('2023-06-13 23:17:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543494
;

-- Reference Item: AD_Table_DownlineCopyingStrategy -> S_Skip
-- 2023-06-13T20:17:31.983Z
UPDATE AD_Ref_List_Trl SET Name='Skip Copying Children',Updated=TO_TIMESTAMP('2023-06-13 23:17:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543494
;

-- 2023-06-13T20:19:15.607Z
UPDATE AD_Element SET ColumnName='DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435
;

-- 2023-06-13T20:19:15.610Z
UPDATE AD_Column SET ColumnName='DownlineCloningStrategy' WHERE AD_Element_ID=582435
;

-- 2023-06-13T20:19:15.612Z
UPDATE AD_Process_Para SET ColumnName='DownlineCloningStrategy' WHERE AD_Element_ID=582435
;

-- 2023-06-13T20:19:15.627Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'en_US') 
;

-- Element: DownlineCloningStrategy
-- 2023-06-13T20:19:26.883Z
UPDATE AD_Element_Trl SET Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435 AND AD_Language='de_CH'
;

-- 2023-06-13T20:19:26.886Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'de_CH') 
;

-- Element: DownlineCloningStrategy
-- 2023-06-13T20:19:30.337Z
UPDATE AD_Element_Trl SET Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435 AND AD_Language='de_DE'
;

-- 2023-06-13T20:19:30.339Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'de_DE') 
;

-- Element: DownlineCloningStrategy
-- 2023-06-13T20:19:34.074Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435 AND AD_Language='en_US'
;

-- 2023-06-13T20:19:34.075Z
UPDATE AD_Element SET Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy' WHERE AD_Element_ID=582435
;

-- 2023-06-13T20:19:34.435Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582435,'en_US') 
;

-- 2023-06-13T20:19:34.437Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'en_US') 
;

-- Element: DownlineCloningStrategy
-- 2023-06-13T20:19:37.699Z
UPDATE AD_Element_Trl SET Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435 AND AD_Language='fr_CH'
;

-- 2023-06-13T20:19:37.701Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'fr_CH') 
;

-- Element: DownlineCloningStrategy
-- 2023-06-13T20:19:42.167Z
UPDATE AD_Element_Trl SET Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435 AND AD_Language='nl_NL'
;

-- 2023-06-13T20:19:42.169Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'nl_NL') 
;

-- Name: AD_Table_DownlineCloningStrategy
-- 2023-06-13T20:20:05.180Z
UPDATE AD_Reference SET Name='AD_Table_DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541755
;

-- 2023-06-13T20:20:05.181Z
UPDATE AD_Reference_Trl trl SET Name='AD_Table_DownlineCloningStrategy' WHERE AD_Reference_ID=541755 AND AD_Language='en_US'
;

-- 2023-06-13T20:20:08.663Z
UPDATE AD_Reference_Trl SET Name='AD_Table_DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541755
;

-- 2023-06-13T20:20:10.357Z
UPDATE AD_Reference_Trl SET Name='AD_Table_DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541755
;

-- 2023-06-13T20:20:12.533Z
UPDATE AD_Reference_Trl SET Name='AD_Table_DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=541755
;

-- 2023-06-13T20:20:17.369Z
UPDATE AD_Reference_Trl SET Name='AD_Table_DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:20:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541755
;

alter table AD_Table rename column DownlineCopyingStrategy to DownlineCloningStrategy;

-- Name: AD_Table_CloningStrategy
-- 2023-06-13T20:38:57.600Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541756,TO_TIMESTAMP('2023-06-13 23:38:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_Table_CloningStrategy',TO_TIMESTAMP('2023-06-13 23:38:57','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-06-13T20:38:57.602Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541756 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: AD_Table_CloningStrategy
-- Value: S
-- ValueName: Skip
-- 2023-06-13T20:39:27.913Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541756,543496,TO_TIMESTAMP('2023-06-13 23:39:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Skip when cloning as a child',TO_TIMESTAMP('2023-06-13 23:39:27','YYYY-MM-DD HH24:MI:SS'),100,'S','Skip')
;

-- 2023-06-13T20:39:27.914Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543496 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Table_CloningStrategy
-- Value: C
-- ValueName: Clone
-- 2023-06-13T20:40:22.228Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541756,543497,TO_TIMESTAMP('2023-06-13 23:40:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Include when cloning as a child',TO_TIMESTAMP('2023-06-13 23:40:22','YYYY-MM-DD HH24:MI:SS'),100,'C','Clone')
;

-- 2023-06-13T20:40:22.230Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543497 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Table_CloningStrategy
-- Value: C
-- ValueName: Clone
-- 2023-06-13T20:42:46.487Z
UPDATE AD_Ref_List SET Name='Allow including when cloning as a child',Updated=TO_TIMESTAMP('2023-06-13 23:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543497
;

-- 2023-06-13T20:42:46.488Z
UPDATE AD_Ref_List_Trl trl SET Name='Allow including when cloning as a child' WHERE AD_Ref_List_ID=543497 AND AD_Language='en_US'
;


update AD_Ref_List set value='A', valueName='AllowCloning' where AD_Ref_List_ID=543497;

-- Reference Item: AD_Table_CloningStrategy -> A_AllowCloning
-- 2023-06-13T20:44:10.782Z
UPDATE AD_Ref_List_Trl SET Name='Allow including when cloning as a child',Updated=TO_TIMESTAMP('2023-06-13 23:44:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543497
;

-- Reference Item: AD_Table_CloningStrategy -> A_AllowCloning
-- 2023-06-13T20:44:12.555Z
UPDATE AD_Ref_List_Trl SET Name='Allow including when cloning as a child',Updated=TO_TIMESTAMP('2023-06-13 23:44:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543497
;

-- Reference Item: AD_Table_CloningStrategy -> A_AllowCloning
-- 2023-06-13T20:44:15.580Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-13 23:44:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543497
;

-- Reference Item: AD_Table_CloningStrategy -> A_AllowCloning
-- 2023-06-13T20:44:18.419Z
UPDATE AD_Ref_List_Trl SET Name='Allow including when cloning as a child',Updated=TO_TIMESTAMP('2023-06-13 23:44:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543497
;

-- Reference Item: AD_Table_CloningStrategy -> A_AllowCloning
-- 2023-06-13T20:44:21.463Z
UPDATE AD_Ref_List_Trl SET Name='Allow including when cloning as a child',Updated=TO_TIMESTAMP('2023-06-13 23:44:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543497
;

-- Column: AD_Table.CloningStrategy
-- 2023-06-13T20:44:43.933Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586813,582434,0,17,541756,100,'CloningStrategy',TO_TIMESTAMP('2023-06-13 23:44:43','YYYY-MM-DD HH24:MI:SS'),100,'N','A','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cloning Strategy',0,0,TO_TIMESTAMP('2023-06-13 23:44:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-13T20:44:43.935Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586813 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T20:44:44.442Z
/* DDL */  select update_Column_Translation_From_AD_Element(582434) 
;

-- 2023-06-13T20:44:45.219Z
/* DDL */ SELECT public.db_alter_table('AD_Table','ALTER TABLE public.AD_Table ADD COLUMN CloningStrategy CHAR(1) DEFAULT ''A'' NOT NULL')
;

