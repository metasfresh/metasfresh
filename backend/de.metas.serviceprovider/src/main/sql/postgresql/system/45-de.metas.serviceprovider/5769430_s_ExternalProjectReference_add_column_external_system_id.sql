
-- Column: S_ExternalProjectReference.ExternalSystem_ID
-- 2025-09-18T07:39:33.874Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590938,583968,0,30,541988,541466,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-09-18 07:39:33.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.serviceprovider',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2025-09-18 07:39:33.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-18T07:39:33.883Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590938 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-18T07:39:33.887Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-09-18T07:55:25.116Z
/* DDL */ SELECT public.db_alter_table('S_ExternalProjectReference','ALTER TABLE public.S_ExternalProjectReference ADD COLUMN ExternalSystem_ID NUMERIC(10)')
;

-- 2025-09-18T07:55:25.122Z
ALTER TABLE S_ExternalProjectReference ADD CONSTRAINT ExternalSystem_SExternalProjectReference FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Field: Externe projekt Referenzen(540870,de.metas.serviceprovider) -> External project reference ID(542339,de.metas.serviceprovider) -> Externes System
-- Column: S_ExternalProjectReference.ExternalSystem_ID
-- 2025-09-18T07:41:32.687Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590938,753781,0,542339,TO_TIMESTAMP('2025-09-18 07:41:32.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-09-18 07:41:32.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T07:41:32.690Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753781 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T07:41:32.691Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-09-18T07:41:32.695Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753781
;

-- 2025-09-18T07:41:32.696Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753781)
;

-- UI Element: Externe projekt Referenzen(540870,de.metas.serviceprovider) -> External project reference ID(542339,de.metas.serviceprovider) -> main -> 10 -> default.Externes System
-- Column: S_ExternalProjectReference.ExternalSystem_ID
-- 2025-09-18T07:42:17.216Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753781,0,542339,543560,637131,'F',TO_TIMESTAMP('2025-09-18 07:42:17.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',61,0,0,TO_TIMESTAMP('2025-09-18 07:42:17.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: ExternalSystem_ID
-- 2025-09-18T07:43:35.274Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Externes System',Updated=TO_TIMESTAMP('2025-09-18 07:43:35.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583968 AND AD_Language='de_CH'
;

-- 2025-09-18T07:43:35.275Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-18T07:43:35.658Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583968,'de_CH')
;

-- Element: ExternalSystem_ID
-- 2025-09-18T07:43:38.573Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-18 07:43:38.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583968 AND AD_Language='en_US'
;

-- 2025-09-18T07:43:38.577Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583968,'en_US')
;

