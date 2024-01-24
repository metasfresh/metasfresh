-- 2023-06-14T08:22:49.620Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582437,0,'CloningEnabled',TO_TIMESTAMP('2023-06-14 11:22:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cloning Enabled','Cloning Enabled',TO_TIMESTAMP('2023-06-14 11:22:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-14T08:22:49.635Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582437 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: AD_Table_CloningEnabled
-- 2023-06-14T08:23:23.850Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541757,TO_TIMESTAMP('2023-06-14 11:23:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_Table_CloningEnabled',TO_TIMESTAMP('2023-06-14 11:23:23','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-06-14T08:23:23.856Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541757 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: AD_Table_CloningEnabled
-- Value: E
-- ValueName: Enabled
-- 2023-06-14T08:23:37.273Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541757,543500,TO_TIMESTAMP('2023-06-14 11:23:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Enabled',TO_TIMESTAMP('2023-06-14 11:23:37','YYYY-MM-DD HH24:MI:SS'),100,'E','Enabled')
;

-- 2023-06-14T08:23:37.279Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543500 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Table_CloningEnabled
-- Value: D
-- ValueName: Disabled
-- 2023-06-14T08:23:46.110Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541757,543501,TO_TIMESTAMP('2023-06-14 11:23:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Disabled',TO_TIMESTAMP('2023-06-14 11:23:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Disabled')
;

-- 2023-06-14T08:23:46.116Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543501 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Table_CloningEnabled
-- Value: A
-- ValueName: Auto
-- 2023-06-14T08:24:07.186Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541757,543502,TO_TIMESTAMP('2023-06-14 11:24:07','YYYY-MM-DD HH24:MI:SS'),100,'Determined by system','D','Y','Auto',TO_TIMESTAMP('2023-06-14 11:24:07','YYYY-MM-DD HH24:MI:SS'),100,'A','Auto')
;

-- 2023-06-14T08:24:07.189Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543502 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: AD_Table.CloningEnabled
-- 2023-06-14T08:24:21.468Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586815,582437,0,17,541757,100,'XX','CloningEnabled',TO_TIMESTAMP('2023-06-14 11:24:20','YYYY-MM-DD HH24:MI:SS'),100,'N','A','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cloning Enabled',0,0,TO_TIMESTAMP('2023-06-14 11:24:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-14T08:24:21.477Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586815 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-14T08:24:22.611Z
/* DDL */  select update_Column_Translation_From_AD_Element(582437) 
;

-- 2023-06-14T08:24:24.537Z
/* DDL */ SELECT public.db_alter_table('AD_Table','ALTER TABLE public.AD_Table ADD COLUMN CloningEnabled CHAR(1) DEFAULT ''A'' NOT NULL')
;

-- Reference: AD_Table_CloningStrategy
-- Value: I
-- ValueName: AlwaysInclude
-- 2023-06-14T08:25:24.065Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541756,543503,TO_TIMESTAMP('2023-06-14 11:25:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Always include',TO_TIMESTAMP('2023-06-14 11:25:23','YYYY-MM-DD HH24:MI:SS'),100,'I','AlwaysInclude')
;

-- 2023-06-14T08:25:24.068Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543503 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2023-06-14T08:27:38.170Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582438,0,'WhenChildCloningStrategy',TO_TIMESTAMP('2023-06-14 11:27:37','YYYY-MM-DD HH24:MI:SS'),100,'The cloning strategy to be used when this table is included (as a child) to a parent (e.g. C_OrderLine)','D','Y','When Child Cloning Strategy','When Child Cloning Strategy',TO_TIMESTAMP('2023-06-14 11:27:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-14T08:27:38.183Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582438 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Table.WhenChildCloningStrategy
-- 2023-06-14T08:29:04.183Z
UPDATE AD_Column SET AD_Element_ID=582438, ColumnName='WhenChildCloningStrategy', Description='The cloning strategy to be used when this table is included (as a child) to a parent (e.g. C_OrderLine)', Help=NULL, Name='When Child Cloning Strategy',Updated=TO_TIMESTAMP('2023-06-14 11:29:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586813
;

-- 2023-06-14T08:29:04.186Z
UPDATE AD_Column_Trl trl SET Name='When Child Cloning Strategy' WHERE AD_Column_ID=586813 AND AD_Language='en_US'
;

-- 2023-06-14T08:29:04.190Z
UPDATE AD_Field SET Name='When Child Cloning Strategy', Description='The cloning strategy to be used when this table is included (as a child) to a parent (e.g. C_OrderLine)', Help=NULL WHERE AD_Column_ID=586813
;

-- 2023-06-14T08:29:04.193Z
/* DDL */  select update_Column_Translation_From_AD_Element(582438) 
;

alter table AD_Table rename column CloningStrategy to WhenChildCloningStrategy;


-- Reference: AD_Table_DownlineCloningStrategy
-- Value: I
-- ValueName: OnlyIncluded
-- 2023-06-14T09:09:41.364Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541755,543504,TO_TIMESTAMP('2023-06-14 12:09:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Only if marked as included',TO_TIMESTAMP('2023-06-14 12:09:41','YYYY-MM-DD HH24:MI:SS'),100,'I','OnlyIncluded')
;

-- 2023-06-14T09:09:41.372Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543504 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

