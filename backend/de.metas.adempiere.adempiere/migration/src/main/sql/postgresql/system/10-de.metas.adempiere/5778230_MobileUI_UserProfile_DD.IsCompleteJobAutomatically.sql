-- Run mode: SWING_CLIENT

-- Column: MobileUI_UserProfile_DD.IsCompleteJobAutomatically
-- 2025-11-25T09:39:41.302Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591600,584245,0,20,542462,'XX','IsCompleteJobAutomatically',TO_TIMESTAMP('2025-11-25 09:39:40.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Schließt den Job automatisch ab, sobald alle Positionen erfüllt sind.','D',0,1,'Wenn aktiviert, wird der Job automatisch abgeschlossen, sobald alle Positionen verarbeitet wurden. Der Benutzer muss den Abschließen-Button nicht mehr manuell drücken.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Job automatisch abschließen',0,0,TO_TIMESTAMP('2025-11-25 09:39:40.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-25T09:39:41.332Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591600 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-25T09:39:41.511Z
/* DDL */  select update_Column_Translation_From_AD_Element(584245)
;

-- 2025-11-25T09:39:42.779Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_DD','ALTER TABLE public.MobileUI_UserProfile_DD ADD COLUMN IsCompleteJobAutomatically CHAR(1) DEFAULT ''N'' CHECK (IsCompleteJobAutomatically IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> Job automatisch abschließen
-- Column: MobileUI_UserProfile_DD.IsCompleteJobAutomatically
-- 2025-11-25T09:40:03.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591600,757332,0,547735,TO_TIMESTAMP('2025-11-25 09:40:03.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Schließt den Job automatisch ab, sobald alle Positionen erfüllt sind.',1,'D','Wenn aktiviert, wird der Job automatisch abgeschlossen, sobald alle Positionen verarbeitet wurden. Der Benutzer muss den Abschließen-Button nicht mehr manuell drücken.','Y','N','N','N','N','N','N','N','Job automatisch abschließen',TO_TIMESTAMP('2025-11-25 09:40:03.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-25T09:40:03.641Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=757332 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-25T09:40:03.646Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584245)
;

-- 2025-11-25T09:40:03.668Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=757332
;

-- 2025-11-25T09:40:03.672Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(757332)
;

-- UI Column: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-11-25T09:40:44.625Z
UPDATE AD_UI_ElementGroup SET SeqNo=90,Updated=TO_TIMESTAMP('2025-11-25 09:40:44.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552271
;

-- UI Column: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 20
-- UI Element Group: job
-- 2025-11-25T09:40:51.103Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547725,553846,TO_TIMESTAMP('2025-11-25 09:40:50.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','job',20,TO_TIMESTAMP('2025-11-25 09:40:50.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 20 -> job.Job automatisch abschließen
-- Column: MobileUI_UserProfile_DD.IsCompleteJobAutomatically
-- 2025-11-25T09:41:01.877Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,757332,0,547735,553846,639183,'F',TO_TIMESTAMP('2025-11-25 09:41:01.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Schließt den Job automatisch ab, sobald alle Positionen erfüllt sind.','Wenn aktiviert, wird der Job automatisch abgeschlossen, sobald alle Positionen verarbeitet wurden. Der Benutzer muss den Abschließen-Button nicht mehr manuell drücken.','Y','N','Y','N','N','Job automatisch abschließen',10,0,0,TO_TIMESTAMP('2025-11-25 09:41:01.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

