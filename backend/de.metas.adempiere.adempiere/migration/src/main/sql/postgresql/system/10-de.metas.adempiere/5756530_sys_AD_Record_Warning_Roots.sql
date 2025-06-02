

-- 2025-06-02T15:52:01.819Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583682,0,'Root_AD_Table_ID',TO_TIMESTAMP('2025-06-02 15:52:01.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Root Table','Root Table',TO_TIMESTAMP('2025-06-02 15:52:01.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-02T15:52:01.826Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583682 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Root_AD_Table_ID
-- 2025-06-02T15:54:01.736Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Stammtabelle', PrintName='Stammtabelle',Updated=TO_TIMESTAMP('2025-06-02 15:54:01.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583682 AND AD_Language='de_CH'
;

-- 2025-06-02T15:54:01.737Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T15:54:02.166Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583682,'de_CH')
;

-- Element: Root_AD_Table_ID
-- 2025-06-02T15:54:08.105Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Stammtabelle', PrintName='Stammtabelle',Updated=TO_TIMESTAMP('2025-06-02 15:54:08.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583682 AND AD_Language='de_DE'
;

-- 2025-06-02T15:54:08.112Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T15:54:09.139Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583682,'de_DE')
;

-- 2025-06-02T15:54:09.140Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583682,'de_DE')
;

-- 2025-06-02T15:54:40.871Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583683,0,'Root_Record_ID',TO_TIMESTAMP('2025-06-02 15:54:40.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Root Record','Root Record',TO_TIMESTAMP('2025-06-02 15:54:40.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-02T15:54:40.872Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583683 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Root_Record_ID
-- 2025-06-02T15:54:58.314Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Stammdatensatz', PrintName='Stammdatensatz',Updated=TO_TIMESTAMP('2025-06-02 15:54:58.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583683 AND AD_Language='de_DE'
;

-- 2025-06-02T15:54:58.315Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T15:54:59.143Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583683,'de_DE')
;

-- 2025-06-02T15:54:59.145Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583683,'de_DE')
;

-- Element: Root_Record_ID
-- 2025-06-02T15:55:15.240Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Stammdatensatz', PrintName='Stammdatensatz',Updated=TO_TIMESTAMP('2025-06-02 15:55:15.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583683 AND AD_Language='de_CH'
;

-- 2025-06-02T15:55:15.247Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T15:55:15.795Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583683,'de_CH')
;






-- Column: AD_Record_Warning.Root_AD_Table_ID
-- 2025-06-02T16:03:35.507Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590150,583682,0,30,540750,542455,'XX','Root_AD_Table_ID',TO_TIMESTAMP('2025-06-02 16:03:35.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Stammtabelle',0,0,TO_TIMESTAMP('2025-06-02 16:03:35.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-02T16:03:35.514Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590150 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-02T16:03:35.527Z
/* DDL */  select update_Column_Translation_From_AD_Element(583682)
;

-- 2025-06-02T16:03:39.710Z
/* DDL */ SELECT public.db_alter_table('AD_Record_Warning','ALTER TABLE public.AD_Record_Warning ADD COLUMN Root_AD_Table_ID NUMERIC(10)')
;

-- 2025-06-02T16:03:39.717Z
ALTER TABLE AD_Record_Warning ADD CONSTRAINT RootADTable_ADRecordWarning FOREIGN KEY (Root_AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED
;

-- Column: AD_Record_Warning.Root_Record_ID
-- 2025-06-02T16:08:36.148Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590151,583683,0,28,542455,'XX','Root_Record_ID',TO_TIMESTAMP('2025-06-02 16:08:35.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Stammdatensatz',0,0,TO_TIMESTAMP('2025-06-02 16:08:35.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-02T16:08:36.198Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590151 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-02T16:08:36.243Z
/* DDL */  select update_Column_Translation_From_AD_Element(583683)
;

-- 2025-06-02T16:08:39.501Z
/* DDL */ SELECT public.db_alter_table('AD_Record_Warning','ALTER TABLE public.AD_Record_Warning ADD COLUMN Root_Record_ID NUMERIC(10)')
;

-- UI Column: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 10
-- UI Element Group: rootRecord
-- 2025-06-02T16:19:02.868Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547677,553089,TO_TIMESTAMP('2025-06-02 16:19:02.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','rootRecord',40,TO_TIMESTAMP('2025-06-02 16:19:02.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 10
-- UI Element Group: rootRecord
-- 2025-06-02T16:19:14.493Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2025-06-02 16:19:14.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553089
;

-- UI Column: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 10
-- UI Element Group: record
-- 2025-06-02T16:20:03.182Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2025-06-02 16:20:03.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552187
;

-- UI Column: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 10
-- UI Element Group: business rule
-- 2025-06-02T16:20:07.045Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2025-06-02 16:20:07.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552259
;

-- Field: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> Stammtabelle
-- Column: AD_Record_Warning.Root_AD_Table_ID
-- 2025-06-02T16:20:38.015Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590150,747487,0,547698,TO_TIMESTAMP('2025-06-02 16:20:37.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Stammtabelle',TO_TIMESTAMP('2025-06-02 16:20:37.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-02T16:20:38.017Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747487 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-02T16:20:38.019Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583682)
;

-- 2025-06-02T16:20:38.022Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747487
;

-- 2025-06-02T16:20:38.023Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747487)
;

-- Field: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> Stammdatensatz
-- Column: AD_Record_Warning.Root_Record_ID
-- 2025-06-02T16:20:38.119Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590151,747488,0,547698,TO_TIMESTAMP('2025-06-02 16:20:38.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Stammdatensatz',TO_TIMESTAMP('2025-06-02 16:20:38.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-02T16:20:38.120Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747488 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-02T16:20:38.121Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583683)
;

-- 2025-06-02T16:20:38.124Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747488
;

-- 2025-06-02T16:20:38.125Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747488)
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 10 -> rootRecord.Stammtabelle
-- Column: AD_Record_Warning.Root_AD_Table_ID
-- 2025-06-02T16:20:58.109Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747487,0,547698,553089,633896,'F',TO_TIMESTAMP('2025-06-02 16:20:57.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Stammtabelle',10,0,0,TO_TIMESTAMP('2025-06-02 16:20:57.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 10 -> rootRecord.Stammdatensatz
-- Column: AD_Record_Warning.Root_Record_ID
-- 2025-06-02T16:21:04.719Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747488,0,547698,553089,633897,'F',TO_TIMESTAMP('2025-06-02 16:21:04.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Stammdatensatz',20,0,0,TO_TIMESTAMP('2025-06-02 16:21:04.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

