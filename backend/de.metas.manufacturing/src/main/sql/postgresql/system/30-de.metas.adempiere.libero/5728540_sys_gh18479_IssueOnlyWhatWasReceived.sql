-- Reference: S_Resource MFG Type
-- Value: ES
-- ValueName: ExternalSystem
-- 2024-07-12T06:44:01.493Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543704,53223,TO_TIMESTAMP('2024-07-12 09:44:01','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Externes System',TO_TIMESTAMP('2024-07-12 09:44:01','YYYY-MM-DD HH24:MI:SS'),100,'ES','ExternalSystem')
;

-- 2024-07-12T06:44:01.498Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543704 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: S_Resource MFG Type -> ES_ExternalSystem
-- 2024-07-12T06:44:11.484Z
UPDATE AD_Ref_List_Trl SET Name='External System',Updated=TO_TIMESTAMP('2024-07-12 09:44:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543704
;

-- Column: S_Resource.ExternalSystem_Config_ID
-- Column: S_Resource.ExternalSystem_Config_ID
-- 2024-07-12T06:46:37.445Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588831,578728,0,19,487,'XX','ExternalSystem_Config_ID',TO_TIMESTAMP('2024-07-12 09:46:37','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'External System Config',0,0,TO_TIMESTAMP('2024-07-12 09:46:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-12T06:46:37.451Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588831 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-12T06:46:37.493Z
/* DDL */  select update_Column_Translation_From_AD_Element(578728) 
;

-- 2024-07-12T06:46:41.175Z
/* DDL */ SELECT public.db_alter_table('S_Resource','ALTER TABLE public.S_Resource ADD COLUMN ExternalSystem_Config_ID NUMERIC(10)')
;

-- 2024-07-12T06:46:41.446Z
ALTER TABLE S_Resource ADD CONSTRAINT ExternalSystemConfig_SResource FOREIGN KEY (ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED
;

-- Field: Ressource -> Ressource -> External System Config
-- Column: S_Resource.ExternalSystem_Config_ID
-- Field: Ressource(236,D) -> Ressource(414,D) -> External System Config
-- Column: S_Resource.ExternalSystem_Config_ID
-- 2024-07-12T06:47:32.662Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588831,729092,0,414,TO_TIMESTAMP('2024-07-12 09:47:32','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2024-07-12 09:47:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-12T06:47:32.666Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729092 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-12T06:47:32.672Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2024-07-12T06:47:32.688Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729092
;

-- 2024-07-12T06:47:32.694Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729092)
;

-- Field: Ressource -> Ressource -> External System Config
-- Column: S_Resource.ExternalSystem_Config_ID
-- Field: Ressource(236,D) -> Ressource(414,D) -> External System Config
-- Column: S_Resource.ExternalSystem_Config_ID
-- 2024-07-12T06:48:37.169Z
UPDATE AD_Field SET DisplayLogic='@ManufacturingResourceType@ = ''ES''',Updated=TO_TIMESTAMP('2024-07-12 09:48:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729092
;

-- Column: S_Resource.ExternalSystem_Config_ID
-- Column: S_Resource.ExternalSystem_Config_ID
-- 2024-07-12T06:48:49.028Z
UPDATE AD_Column SET MandatoryLogic='@ManufacturingResourceType@ = ''ES''',Updated=TO_TIMESTAMP('2024-07-12 09:48:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588831
;

-- UI Element: Ressource -> Ressource.External System Config
-- Column: S_Resource.ExternalSystem_Config_ID
-- UI Element: Ressource(236,D) -> Ressource(414,D) -> main -> 10 -> details.External System Config
-- Column: S_Resource.ExternalSystem_Config_ID
-- 2024-07-12T06:49:58.352Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729092,0,414,625006,543930,'F',TO_TIMESTAMP('2024-07-12 09:49:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External System Config',40,0,0,TO_TIMESTAMP('2024-07-12 09:49:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Value: de.metas.manufacturing.callExternalSystem.ResendToSameMachine
-- 2024-07-15T07:27:14.613Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545432,0,TO_TIMESTAMP('2024-07-15 10:27:13','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Erneut an das bereits gescannte Gerät senden?','I',TO_TIMESTAMP('2024-07-15 10:27:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.manufacturing.callExternalSystem.ResendToSameMachine')
;

-- 2024-07-15T07:27:14.627Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545432 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.manufacturing.callExternalSystem.ResendToSameMachine
-- 2024-07-15T07:27:24.871Z
UPDATE AD_Message_Trl SET MsgText='Resend to the already scanned machine?',Updated=TO_TIMESTAMP('2024-07-15 10:27:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545432
;

-- Reference: PP_Activity_Type
-- Value: IssueOnlyWhatWasReceived
-- ValueName: Issue Only What Was Received
-- 2024-07-15T10:17:00.236Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543705,541463,TO_TIMESTAMP('2024-07-15 13:16:59','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Issue Only What Was Received',TO_TIMESTAMP('2024-07-15 13:16:59','YYYY-MM-DD HH24:MI:SS'),100,'IssueOnlyWhatWasReceived','Issue Only What Was Received')
;

-- 2024-07-15T10:17:00.248Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543705 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

