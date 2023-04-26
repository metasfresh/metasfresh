
-- Column: S_Resource.InternalName
-- 2022-07-15T09:57:34.938Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583693,541268,0,10,487,'InternalName',TO_TIMESTAMP('2022-07-15 11:57:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Generally used to give records a name that can be safely referenced from code.','D',0,255,'Hint: make the field/column read-only or not-updatable to avoid accidental changes.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Interner Name',0,0,TO_TIMESTAMP('2022-07-15 11:57:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-15T09:57:34.948Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583693 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-15T09:57:34.955Z
/* DDL */  select update_Column_Translation_From_AD_Element(541268) 
;

-- 2022-07-15T09:57:35.879Z
/* DDL */ SELECT public.db_alter_table('S_Resource','ALTER TABLE public.S_Resource ADD COLUMN InternalName VARCHAR(255)')
;

-- Field: Ressource -> Ressource -> % Utilization
-- Column: S_Resource.PercentUtilization
-- 2022-07-15T10:10:10.055Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,53272,701921,0,414,TO_TIMESTAMP('2022-07-15 12:10:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','% Utilization',TO_TIMESTAMP('2022-07-15 12:10:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T10:10:10.060Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701921 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T10:10:10.064Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53230) 
;

-- 2022-07-15T10:10:10.143Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701921
;

-- 2022-07-15T10:10:10.146Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701921)
;

-- Field: Ressource -> Ressource -> Daily Capacity
-- Column: S_Resource.DailyCapacity
-- 2022-07-15T10:10:10.253Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,53273,701922,0,414,TO_TIMESTAMP('2022-07-15 12:10:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Daily Capacity',TO_TIMESTAMP('2022-07-15 12:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T10:10:10.256Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701922 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T10:10:10.258Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53231) 
;

-- 2022-07-15T10:10:10.264Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701922
;

-- 2022-07-15T10:10:10.266Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701922)
;

-- Field: Ressource -> Ressource -> Waiting Time
-- Column: S_Resource.WaitingTime
-- 2022-07-15T10:10:10.356Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,53275,701923,0,414,TO_TIMESTAMP('2022-07-15 12:10:10','YYYY-MM-DD HH24:MI:SS'),100,'Workflow Simulation Waiting time',10,'D','Amount of time needed to prepare the performance of the task on Duration Units','Y','N','N','N','N','N','N','N','Waiting Time',TO_TIMESTAMP('2022-07-15 12:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T10:10:10.359Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701923 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T10:10:10.361Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2331) 
;

-- 2022-07-15T10:10:10.366Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701923
;

-- 2022-07-15T10:10:10.367Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701923)
;

-- Field: Ressource -> Ressource -> Queuing Time
-- Column: S_Resource.QueuingTime
-- 2022-07-15T10:10:10.464Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,53277,701924,0,414,TO_TIMESTAMP('2022-07-15 12:10:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Queuing Time',TO_TIMESTAMP('2022-07-15 12:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T10:10:10.467Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701924 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T10:10:10.468Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53234) 
;

-- 2022-07-15T10:10:10.472Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701924
;

-- 2022-07-15T10:10:10.474Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701924)
;

-- Field: Ressource -> Ressource -> Interner Name
-- Column: S_Resource.InternalName
-- 2022-07-15T10:10:10.569Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583693,701925,0,414,TO_TIMESTAMP('2022-07-15 12:10:10','YYYY-MM-DD HH24:MI:SS'),100,'Generally used to give records a name that can be safely referenced from code.',255,'D','Hint: make the field/column read-only or not-updatable to avoid accidental changes.','Y','N','N','N','N','N','N','N','Interner Name',TO_TIMESTAMP('2022-07-15 12:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T10:10:10.571Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701925 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T10:10:10.574Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541268) 
;

-- 2022-07-15T10:10:10.584Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701925
;

-- 2022-07-15T10:10:10.586Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701925)
;

-- 2022-07-15T10:11:23.480Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542590,549532,TO_TIMESTAMP('2022-07-15 12:11:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','additional_info',15,TO_TIMESTAMP('2022-07-15 12:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T10:11:31.198Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2022-07-15 12:11:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543929
;

-- 2022-07-15T10:11:34.742Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2022-07-15 12:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549532
;

-- UI Element: Ressource -> Ressource.InternalName
-- Column: S_Resource.InternalName
-- 2022-07-15T10:11:58.562Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,701925,0,414,549532,610372,'F',TO_TIMESTAMP('2022-07-15 12:11:58','YYYY-MM-DD HH24:MI:SS'),100,'Generally used to give records a name that can be safely referenced from code.','Hint: make the field/column read-only or not-updatable to avoid accidental changes.','Y','N','N','Y','N','N','N',0,'InternalName',10,0,0,TO_TIMESTAMP('2022-07-15 12:11:58','YYYY-MM-DD HH24:MI:SS'),100)
;

