-- 2022-08-31T13:09:57.553Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581409,0,'Filter_Val_Rule_ID',TO_TIMESTAMP('2022-08-31 16:09:57','YYYY-MM-DD HH24:MI:SS'),100,'Validation Rule used for filtering','D','','Y','Filter Validation Rule','Filter Validation Rule',TO_TIMESTAMP('2022-08-31 16:09:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-31T13:09:57.565Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581409 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Filter_Val_Rule_ID
-- 2022-08-31T13:10:04.159Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-31 16:10:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581409 AND AD_Language='en_US'
;

-- 2022-08-31T13:10:04.199Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581409,'en_US') 
;

-- Reference: AD_Val_Rule_IDs
-- Table: AD_Val_Rule
-- Key: AD_Val_Rule.AD_Val_Rule_ID
-- 2022-08-31T13:10:55.866Z
UPDATE AD_Ref_Table SET AD_Display=NULL,Updated=TO_TIMESTAMP('2022-08-31 16:10:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=53462
;

-- Reference: AD_Val_Rule_IDs
-- Table: AD_Val_Rule
-- Key: AD_Val_Rule.AD_Val_Rule_ID
-- 2022-08-31T13:11:04.316Z
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2022-08-31 16:11:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=53462
;

-- Name: AD_Val_Rule
-- 2022-08-31T13:11:30.401Z
UPDATE AD_Reference SET Name='AD_Val_Rule',Updated=TO_TIMESTAMP('2022-08-31 16:11:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=53462
;

-- Column: AD_Column.Filter_Val_Rule_ID
-- 2022-08-31T13:11:55.440Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,
                       IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,584244,581409,0,18,53462,101,'Filter_Val_Rule_ID',TO_TIMESTAMP('2022-08-31 16:11:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Validation Rule used for filtering','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',
        'N','N','N','N','N','N','Y','N',0,'Filter Validation Rule',0,0,TO_TIMESTAMP('2022-08-31 16:11:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-31T13:11:55.445Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584244 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-31T13:11:55.453Z
/* DDL */  select update_Column_Translation_From_AD_Element(581409) 
;

-- 2022-08-31T19:10:32.659Z
UPDATE AD_Column SET IsActive='Y',Updated=TO_TIMESTAMP('2022-08-31 22:10:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584244
;


-- 2022-08-31T19:16:23.982Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,
                       IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,584245,581409,0,30,53462,107,'Filter_Val_Rule_ID',TO_TIMESTAMP('2022-08-31 22:16:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Validation Rule used for filtering','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',
        'N','N','N','N','N','N','Y','N',0,'Filter Validation Rule',0,0,TO_TIMESTAMP('2022-08-31 22:16:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-31T19:16:23.987Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584245 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 2022-08-31T19:18:14.335Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584244,706891,0,101,TO_TIMESTAMP('2022-08-31 22:18:14','YYYY-MM-DD HH24:MI:SS'),100,'Validation Rule used for filtering',10,'D','','Y','N','N','N','N','N','N','N','Filter Validation Rule',TO_TIMESTAMP('2022-08-31 22:18:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-31T19:18:14.338Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706891 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-31T19:19:33.307Z
UPDATE AD_Field SET DisplayLogic='@IsSelectionColumn@=Y', SeqNo=610,Updated=TO_TIMESTAMP('2022-08-31 22:19:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706891
;

-- 2022-08-31T19:19:39.681Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-08-31 22:19:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706891
;

-- 2022-08-31T19:20:52.434Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584245,706892,0,107,TO_TIMESTAMP('2022-08-31 22:20:52','YYYY-MM-DD HH24:MI:SS'),100,'Validation Rule used for filtering',10,'D','','Y','N','N','N','N','N','N','N','Filter Validation Rule',TO_TIMESTAMP('2022-08-31 22:20:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-31T19:20:52.436Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=706892 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-31T19:21:18.124Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=330,Updated=TO_TIMESTAMP('2022-08-31 22:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706892
;

-- 2022-08-31T19:23:30.106Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2022-08-31 22:23:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584244
;

