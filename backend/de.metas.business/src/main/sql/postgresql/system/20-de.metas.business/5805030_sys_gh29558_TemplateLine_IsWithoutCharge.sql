-- Run mode: SWING_CLIENT

-- Column: C_CompensationGroup_Schema_TemplateLine.IsWithoutCharge
-- gh#29558 F00127.1 Single price for bundle — add IsWithoutCharge to template line
-- Reusing AD_Element_ID=583822 (IsWithoutCharge / Ohne Berechnung)

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592638,583822,0,20,541679,'XX','IsWithoutCharge',TO_TIMESTAMP('2025-07-27 08:24:03.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Ohne Berechnung',0,0,TO_TIMESTAMP('2025-07-27 08:24:03.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592638 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */  select update_Column_Translation_From_AD_Element(583822)
;

/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_Schema_TemplateLine','ALTER TABLE public.C_CompensationGroup_Schema_TemplateLine ADD COLUMN IsWithoutCharge CHAR(1) DEFAULT ''N'' CHECK (IsWithoutCharge IN (''Y'',''N'')) NOT NULL')
;

-- Field: Schema-Zeilen(544005) -> Ohne Berechnung
-- Column: C_CompensationGroup_Schema_TemplateLine.IsWithoutCharge
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592638,780495,0,544005,0,TO_TIMESTAMP('2025-07-27 08:35:03.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Ohne Berechnung',0,70,70,0,1,1,TO_TIMESTAMP('2025-07-27 08:35:03.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780495 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583822)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=780495
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(780495)
;

-- AD_UI_ElementGroup: 0 rows on tab 544005 -- skip AD_UI_Element insert; IsDisplayed+IsDisplayedGrid flags on AD_Field are sufficient.

-- Tab 544005 description (base language DE)
UPDATE AD_Tab SET Description=$desc$Bestandteile, die automatisch zum Auftrag hinzugefügt werden, sobald ein Trigger-Produkt (Hauptartikel) erfasst wird. Wichtig: Das Schema wird nicht durch diese Zeilen ausgelöst, sondern über den Verweis im Produkt-Stammdatensatz auf das Kompensationsgruppen-Schema. Nur Hauptartikel sollten diesen Verweis tragen — niemals die hier aufgelisteten Bestandteile, sonst löst der Bestandteil den Bundle ebenfalls aus.$desc$,Updated=NOW(),UpdatedBy=100 WHERE AD_Tab_ID=544005
;

-- Tab translations: de_CH, de_DE, fr_CH -> DE text
UPDATE AD_Tab_Trl SET Description=$desc$Bestandteile, die automatisch zum Auftrag hinzugefügt werden, sobald ein Trigger-Produkt (Hauptartikel) erfasst wird. Wichtig: Das Schema wird nicht durch diese Zeilen ausgelöst, sondern über den Verweis im Produkt-Stammdatensatz auf das Kompensationsgruppen-Schema. Nur Hauptartikel sollten diesen Verweis tragen — niemals die hier aufgelisteten Bestandteile, sonst löst der Bestandteil den Bundle ebenfalls aus.$desc$,IsTranslated='Y',Updated=NOW(),UpdatedBy=100 WHERE AD_Tab_ID=544005 AND AD_Language IN ('de_CH','de_DE','fr_CH')
;

-- Tab translations: en_US -> EN text
UPDATE AD_Tab_Trl SET Description=$desc$Components that are automatically added to the order when a trigger product (main product) is entered. Important: the schema is not triggered by these rows — it is triggered via the reference on the product's master data record to the Compensation Group Schema. Only main products should carry that reference — never the components listed here, otherwise ordering the component alone will also fire the bundle.$desc$,IsTranslated='Y',Updated=NOW(),UpdatedBy=100 WHERE AD_Tab_ID=544005 AND AD_Language='en_US'
;
