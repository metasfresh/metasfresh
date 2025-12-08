-- Run mode: SWING_CLIENT

-- Column: C_ElementValue.AccountConceptualName
-- 2025-04-17T20:23:52.824Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589915,582046,0,10,188,'XX','AccountConceptualName',TO_TIMESTAMP('2025-04-17 20:23:52.643000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Buchungsschlüssel',0,0,TO_TIMESTAMP('2025-04-17 20:23:52.643000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-17T20:23:52.833Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589915 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-17T20:23:52.838Z
/* DDL */  select update_Column_Translation_From_AD_Element(582046)
;

-- 2025-04-17T20:23:53.704Z
/* DDL */ SELECT public.db_alter_table('C_ElementValue','ALTER TABLE public.C_ElementValue ADD COLUMN AccountConceptualName VARCHAR(255)')
;

-- Field: Konten(540761,D) -> Kontenart(542127,D) -> Buchungsschlüssel
-- Column: C_ElementValue.AccountConceptualName
-- 2025-04-17T20:27:09.091Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589915,741978,0,542127,TO_TIMESTAMP('2025-04-17 20:27:08.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Buchungsschlüssel',TO_TIMESTAMP('2025-04-17 20:27:08.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-17T20:27:09.094Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741978 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-17T20:27:09.098Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582046)
;

-- 2025-04-17T20:27:09.101Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741978
;

-- 2025-04-17T20:27:09.102Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741978)
;

-- UI Element: Konten(540761,D) -> Kontenart(542127,D) -> main -> 10 -> desc.Buchungsschlüssel
-- Column: C_ElementValue.AccountConceptualName
-- 2025-04-17T20:28:25.489Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741978,0,542127,543187,631373,'F',TO_TIMESTAMP('2025-04-17 20:28:25.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Buchungsschlüssel',60,0,0,TO_TIMESTAMP('2025-04-17 20:28:25.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Konten(540761,D) -> Kontenart(542127,D) -> Buchungsschlüssel
-- Column: C_ElementValue.AccountConceptualName
-- 2025-04-17T20:29:17.681Z
UPDATE AD_Field SET DisplayLogic='@IsSummary/N@=N',Updated=TO_TIMESTAMP('2025-04-17 20:29:17.681000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=741978
;

