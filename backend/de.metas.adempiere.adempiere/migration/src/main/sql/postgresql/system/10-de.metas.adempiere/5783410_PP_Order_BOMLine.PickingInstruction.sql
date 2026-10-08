-- Run mode: SWING_CLIENT

-- Column: PP_Order_BOMLine.PickingInstruction
-- 2026-01-12T14:52:09.148Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591856,584390,0,10,53025,'XX','PickingInstruction',TO_TIMESTAMP('2026-01-12 14:52:08.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Hinweise für Lagermitarbeiter und Fertigungspersonal zur Identifizierung, Auswahl und Handhabung dieser Materialkomponente (z.B. visuelle Qualitätskriterien, spezifische Merkmale, die vor der Kommissionierung zu prüfen sind).','EE01',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kommissionierhinweis',0,0,TO_TIMESTAMP('2026-01-12 14:52:08.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-12T14:52:09.162Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591856 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-12T14:52:09.167Z
/* DDL */  select update_Column_Translation_From_AD_Element(584390)
;

-- 2026-01-12T14:52:10.009Z
/* DDL */ SELECT public.db_alter_table('PP_Order_BOMLine','ALTER TABLE public.PP_Order_BOMLine ADD COLUMN PickingInstruction VARCHAR(2000)')
;

-- Field: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01) -> Kommissionierhinweis
-- Column: PP_Order_BOMLine.PickingInstruction
-- 2026-01-12T14:57:55.362Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591856,761010,0,53039,TO_TIMESTAMP('2026-01-12 14:57:55.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Hinweise für Lagermitarbeiter und Fertigungspersonal zur Identifizierung, Auswahl und Handhabung dieser Materialkomponente (z.B. visuelle Qualitätskriterien, spezifische Merkmale, die vor der Kommissionierung zu prüfen sind).',2000,'EE01','Y','N','N','N','N','N','N','N','Kommissionierhinweis',TO_TIMESTAMP('2026-01-12 14:57:55.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-12T14:57:55.365Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=761010 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-12T14:57:55.371Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584390)
;

-- 2026-01-12T14:57:55.375Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=761010
;

-- 2026-01-12T14:57:55.377Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(761010)
;

-- UI Element: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01) -> main -> 10 -> default.Kommissionierhinweis
-- Column: PP_Order_BOMLine.PickingInstruction
-- 2026-01-12T14:58:30.012Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,761010,0,53039,540244,641333,'F',TO_TIMESTAMP('2026-01-12 14:58:29.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Hinweise für Lagermitarbeiter und Fertigungspersonal zur Identifizierung, Auswahl und Handhabung dieser Materialkomponente (z.B. visuelle Qualitätskriterien, spezifische Merkmale, die vor der Kommissionierung zu prüfen sind).','Y','N','Y','N','N','Kommissionierhinweis',420,0,0,TO_TIMESTAMP('2026-01-12 14:58:29.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

