-- Column: S_Resource.S_Resource_Group_ID
-- 2022-05-24T06:43:54.488Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583176,580932,0,30,487,'S_Resource_Group_ID',TO_TIMESTAMP('2022-05-24 09:43:54','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Resource Group',0,0,TO_TIMESTAMP('2022-05-24 09:43:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-24T06:43:54.491Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583176 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T06:43:54.495Z
/* DDL */  select update_Column_Translation_From_AD_Element(580932) 
;

-- 2022-05-24T06:43:58.143Z
/* DDL */ SELECT public.db_alter_table('S_Resource','ALTER TABLE public.S_Resource ADD COLUMN S_Resource_Group_ID NUMERIC(10)')
;

-- 2022-05-24T06:43:58.403Z
ALTER TABLE S_Resource ADD CONSTRAINT SResourceGroup_SResource FOREIGN KEY (S_Resource_Group_ID) REFERENCES public.S_Resource_Group DEFERRABLE INITIALLY DEFERRED
;

-- Field: Ressource -> Ressource -> Resource Group
-- Column: S_Resource.S_Resource_Group_ID
-- 2022-05-24T06:44:22.737Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583176,696443,0,414,TO_TIMESTAMP('2022-05-24 09:44:22','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Resource Group',TO_TIMESTAMP('2022-05-24 09:44:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T06:44:22.740Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696443 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T06:44:22.743Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580932) 
;

-- 2022-05-24T06:44:22.746Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696443
;

-- 2022-05-24T06:44:22.750Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696443)
;

-- UI Element: Ressource -> Ressource.Resource Group
-- Column: S_Resource.S_Resource_Group_ID
-- 2022-05-24T06:45:01.011Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696443,0,414,607595,543924,'F',TO_TIMESTAMP('2022-05-24 09:45:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Resource Group',70,0,0,TO_TIMESTAMP('2022-05-24 09:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Ressource -> Ressource.Resource Group
-- Column: S_Resource.S_Resource_Group_ID
-- 2022-05-24T06:45:12.146Z
UPDATE AD_UI_Element SET SeqNo=55,Updated=TO_TIMESTAMP('2022-05-24 09:45:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=607595
;

-- Field: Produktions Ressource -> Produktions Ressource -> Resource Group
-- Column: S_Resource.S_Resource_Group_ID
-- 2022-05-24T07:34:51.413Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583176,696444,0,53015,TO_TIMESTAMP('2022-05-24 10:34:51','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Resource Group',TO_TIMESTAMP('2022-05-24 10:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T07:34:51.415Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696444 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T07:34:51.420Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580932) 
;

-- 2022-05-24T07:34:51.428Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696444
;

-- 2022-05-24T07:34:51.433Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696444)
;

-- UI Element: Produktions Ressource -> Produktions Ressource.Resource Group
-- Column: S_Resource.S_Resource_Group_ID
-- 2022-05-24T07:35:18.612Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696444,0,53015,607596,540357,'F',TO_TIMESTAMP('2022-05-24 10:35:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Resource Group',50,0,0,TO_TIMESTAMP('2022-05-24 10:35:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktions Ressource -> Produktions Ressource.Resource Group
-- Column: S_Resource.S_Resource_Group_ID
-- 2022-05-24T07:35:34.949Z
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2022-05-24 10:35:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=607596
;

update ad_ref_list set valuename='ProductionLine' where ad_reference_id=53223 and value='PL';
update ad_ref_list set valuename='Plant' where ad_reference_id=53223 and value='PT';
update ad_ref_list set valuename='WorkCenter' where ad_reference_id=53223 and value='WC';
update ad_ref_list set valuename='WorkStation' where ad_reference_id=53223 and value='WS';



