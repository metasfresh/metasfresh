-- Run mode: SWING_CLIENT

-- 2025-04-17T19:05:55.028Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583584,0,'DR_AccountConceptualName',TO_TIMESTAMP('2025-04-17 19:05:54.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Buchungsschlüssel (Soll)','Buchungsschlüssel (Soll)',TO_TIMESTAMP('2025-04-17 19:05:54.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-17T19:05:55.031Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583584 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DR_AccountConceptualName
-- 2025-04-17T19:06:32.994Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-17 19:06:32.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583584 AND AD_Language='fr_CH'
;

-- 2025-04-17T19:06:32.996Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583584,'fr_CH')
;

-- Element: DR_AccountConceptualName
-- 2025-04-17T19:06:36.624Z
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2025-04-17 19:06:36.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583584 AND AD_Language='fr_CH'
;

-- 2025-04-17T19:06:36.626Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583584,'fr_CH')
;

-- Element: DR_AccountConceptualName
-- 2025-04-17T19:06:44.199Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Account Role (Debit)', PrintName='Account Role (Debit)',Updated=TO_TIMESTAMP('2025-04-17 19:06:44.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583584 AND AD_Language='en_US'
;

-- 2025-04-17T19:06:44.201Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-17T19:06:44.562Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583584,'en_US')
;

-- Element: DR_AccountConceptualName
-- 2025-04-17T19:06:47.776Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-17 19:06:47.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583584 AND AD_Language='de_DE'
;

-- 2025-04-17T19:06:47.778Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583584,'de_DE')
;

-- 2025-04-17T19:06:47.779Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583584,'de_DE')
;

-- 2025-04-17T19:07:21.101Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583585,0,'CR_AccountConceptualName',TO_TIMESTAMP('2025-04-17 19:07:20.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Buchungsschlüssel (Haben)','Buchungsschlüssel (Haben)',TO_TIMESTAMP('2025-04-17 19:07:20.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-17T19:07:21.103Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583585 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CR_AccountConceptualName
-- 2025-04-17T19:07:30.741Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-17 19:07:30.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583585 AND AD_Language='de_DE'
;

-- 2025-04-17T19:07:30.748Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583585,'de_DE')
;

-- 2025-04-17T19:07:30.750Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583585,'de_DE')
;

-- Element: CR_AccountConceptualName
-- 2025-04-17T19:07:54.902Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Account Role (Credit)', PrintName='Account Role (Credit)',Updated=TO_TIMESTAMP('2025-04-17 19:07:54.901000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583585 AND AD_Language='en_US'
;

-- 2025-04-17T19:07:54.904Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-17T19:07:55.314Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583585,'en_US')
;

-- Element: AccountConceptualName
-- 2025-04-17T19:08:38.876Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Account Role', PrintName='Account Role',Updated=TO_TIMESTAMP('2025-04-17 19:08:38.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582046 AND AD_Language='en_US'
;

-- 2025-04-17T19:08:38.877Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-17T19:08:39.240Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582046,'en_US')
;

-- Column: GL_JournalLine.DR_AccountConceptualName
-- 2025-04-17T19:18:35.193Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589913,583584,0,10,226,'XX','DR_AccountConceptualName',TO_TIMESTAMP('2025-04-17 19:18:35.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Buchungsschlüssel (Soll)',0,0,TO_TIMESTAMP('2025-04-17 19:18:35.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-17T19:18:35.195Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589913 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-17T19:18:35.197Z
/* DDL */  select update_Column_Translation_From_AD_Element(583584)
;

-- 2025-04-17T19:18:35.833Z
/* DDL */ SELECT public.db_alter_table('GL_JournalLine','ALTER TABLE public.GL_JournalLine ADD COLUMN DR_AccountConceptualName VARCHAR(255)')
;

-- Column: GL_JournalLine.CR_AccountConceptualName
-- 2025-04-17T19:18:49.053Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589914,583585,0,10,226,'XX','CR_AccountConceptualName',TO_TIMESTAMP('2025-04-17 19:18:48.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Buchungsschlüssel (Haben)',0,0,TO_TIMESTAMP('2025-04-17 19:18:48.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-17T19:18:49.056Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589914 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-17T19:18:49.190Z
/* DDL */  select update_Column_Translation_From_AD_Element(583585)
;

-- 2025-04-17T19:18:49.847Z
/* DDL */ SELECT public.db_alter_table('GL_JournalLine','ALTER TABLE public.GL_JournalLine ADD COLUMN CR_AccountConceptualName VARCHAR(255)')
;

-- Name: GL_JournalLine_AccountConceptualName
-- 2025-04-17T19:39:40.917Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541939,TO_TIMESTAMP('2025-04-17 19:39:40.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','GL_JournalLine_AccountConceptualName',TO_TIMESTAMP('2025-04-17 19:39:40.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-04-17T19:39:40.921Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541939 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: GL_JournalLine_AccountConceptualName
-- Value: P_Asset_Acct
-- ValueName: P_Asset_Acct
-- 2025-04-17T19:40:31.388Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541939,543868,TO_TIMESTAMP('2025-04-17 19:40:31.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Product Asset',TO_TIMESTAMP('2025-04-17 19:40:31.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'P_Asset_Acct','P_Asset_Acct')
;

-- 2025-04-17T19:40:31.394Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543868 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: GL_JournalLine_AccountConceptualName
-- Value: P_Asset_Acct
-- ValueName: P_Asset_Acct
-- 2025-04-17T19:44:44.141Z
UPDATE AD_Ref_List SET Name='Warenbestand',Updated=TO_TIMESTAMP('2025-04-17 19:44:44.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543868
;

-- 2025-04-17T19:44:44.142Z
UPDATE AD_Ref_List_Trl trl SET Name='Warenbestand' WHERE AD_Ref_List_ID=543868 AND AD_Language='de_DE'
;

-- Reference Item: GL_JournalLine_AccountConceptualName -> P_Asset_Acct_P_Asset_Acct
-- 2025-04-17T19:44:48.432Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Warenbestand',Updated=TO_TIMESTAMP('2025-04-17 19:44:48.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543868
;

-- 2025-04-17T19:44:48.433Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: GL_JournalLine_AccountConceptualName -> P_Asset_Acct_P_Asset_Acct
-- 2025-04-17T19:44:51.059Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-17 19:44:51.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543868
;

-- Reference Item: GL_JournalLine_AccountConceptualName -> P_Asset_Acct_P_Asset_Acct
-- 2025-04-17T19:44:54.944Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-17 19:44:54.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543868
;

-- Column: GL_JournalLine.CR_AccountConceptualName
-- 2025-04-17T19:46:10.780Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541939,Updated=TO_TIMESTAMP('2025-04-17 19:46:10.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589914
;

-- Column: GL_JournalLine.DR_AccountConceptualName
-- 2025-04-17T19:46:24.717Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541939,Updated=TO_TIMESTAMP('2025-04-17 19:46:24.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589913
;

-- Column: GL_JournalLine.DR_AccountConceptualName
-- 2025-04-17T19:47:35.948Z
UPDATE AD_Column SET MandatoryLogic='@DR_M_Product_ID/0@>0',Updated=TO_TIMESTAMP('2025-04-17 19:47:35.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589913
;

-- Column: GL_JournalLine.CR_AccountConceptualName
-- 2025-04-17T19:47:51.566Z
UPDATE AD_Column SET MandatoryLogic='@CR_M_Product_ID/0@>0',Updated=TO_TIMESTAMP('2025-04-17 19:47:51.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589914
;

-- Field: Hauptbuch Journal(540356,D) -> Position(540855,D) -> Buchungsschlüssel (Soll)
-- Column: GL_JournalLine.DR_AccountConceptualName
-- 2025-04-17T19:51:12.285Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589913,741976,0,540855,TO_TIMESTAMP('2025-04-17 19:51:12.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Buchungsschlüssel (Soll)',TO_TIMESTAMP('2025-04-17 19:51:12.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-17T19:51:12.287Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-17T19:51:12.290Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583584)
;

-- 2025-04-17T19:51:12.316Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741976
;

-- 2025-04-17T19:51:12.322Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741976)
;

-- Field: Hauptbuch Journal(540356,D) -> Position(540855,D) -> Buchungsschlüssel (Haben)
-- Column: GL_JournalLine.CR_AccountConceptualName
-- 2025-04-17T19:51:12.454Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589914,741977,0,540855,TO_TIMESTAMP('2025-04-17 19:51:12.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Buchungsschlüssel (Haben)',TO_TIMESTAMP('2025-04-17 19:51:12.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-17T19:51:12.456Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-17T19:51:12.458Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583585)
;

-- 2025-04-17T19:51:12.461Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741977
;

-- 2025-04-17T19:51:12.462Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741977)
;

-- UI Element: Hauptbuch Journal(540356,D) -> Position(540855,D) -> accounts and amounts -> 10 -> DR.Buchungsschlüssel (Soll)
-- Column: GL_JournalLine.DR_AccountConceptualName
-- 2025-04-17T19:52:20.666Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741976,0,540855,542600,631371,'F',TO_TIMESTAMP('2025-04-17 19:52:20.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Buchungsschlüssel (Soll)',70,0,0,TO_TIMESTAMP('2025-04-17 19:52:20.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch Journal(540356,D) -> Position(540855,D) -> accounts and amounts -> 10 -> DR.Buchungsschlüssel (Soll)
-- Column: GL_JournalLine.DR_AccountConceptualName
-- 2025-04-17T19:52:30.126Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2025-04-17 19:52:30.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631371
;

-- UI Element: Hauptbuch Journal(540356,D) -> Position(540855,D) -> accounts and amounts -> 20 -> CR.Buchungsschlüssel (Haben)
-- Column: GL_JournalLine.CR_AccountConceptualName
-- 2025-04-17T19:52:46.502Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741977,0,540855,542601,631372,'F',TO_TIMESTAMP('2025-04-17 19:52:46.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Buchungsschlüssel (Haben)',70,0,0,TO_TIMESTAMP('2025-04-17 19:52:46.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch Journal(540356,D) -> Position(540855,D) -> accounts and amounts -> 20 -> CR.Buchungsschlüssel (Haben)
-- Column: GL_JournalLine.CR_AccountConceptualName
-- 2025-04-17T19:52:53.795Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2025-04-17 19:52:53.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631372
;

-- Column: GL_JournalLine.CR_AccountConceptualName
-- 2025-04-17T20:11:46.109Z
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2025-04-17 20:11:46.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589914
;

-- Column: GL_JournalLine.DR_AccountConceptualName
-- 2025-04-17T20:11:56.147Z
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2025-04-17 20:11:56.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589913
;

