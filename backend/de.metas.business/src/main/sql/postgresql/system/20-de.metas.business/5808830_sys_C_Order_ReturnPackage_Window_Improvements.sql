-- Run mode: SWING_CLIENT

-- Column: C_Order_ReturnPackage.C_BPartner_ID
-- 2026-06-18T15:04:45.302Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592856,187,0,30,542618,'XX','C_BPartner_ID','(SELECT C_BPartner_ID from C_Order where C_Order_ID = C_Order_ReturnPackage.C_Order_ID)',TO_TIMESTAMP('2026-06-18 15:04:44.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Bezeichnet einen Geschäftspartner','D',0,10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'Geschäftspartner',0,0,TO_TIMESTAMP('2026-06-18 15:04:44.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-18T15:04:45.332Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592856 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-18T15:04:45.423Z
/* DDL */  select update_Column_Translation_From_AD_Element(187)
;

-- Column: C_Order_ReturnPackage.C_BPartner_ID
-- 2026-06-18T15:05:33.666Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-06-18 15:05:33.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592856
;

-- Column: C_Order_ReturnPackage.C_Order_ID
-- 2026-06-18T15:05:50.700Z
UPDATE AD_Column SET AD_Reference_ID=30, IsExcludeFromZoomTargets='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2026-06-18 15:05:50.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592824
;

-- Field: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> Rücknahme Gebinde
-- Column: C_Order_ReturnPackage.C_Order_ReturnPackage_ID
-- 2026-06-18T15:07:23.137Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592815,781223,0,549320,TO_TIMESTAMP('2026-06-18 15:07:22.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Rücknahme Gebinde',TO_TIMESTAMP('2026-06-18 15:07:22.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-18T15:07:23.167Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781223 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-18T15:07:23.197Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585006)
;

-- 2026-06-18T15:07:23.232Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781223
;

-- 2026-06-18T15:07:23.259Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781223)
;

-- Field: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> Geschäftspartner
-- Column: C_Order_ReturnPackage.C_BPartner_ID
-- 2026-06-18T15:07:23.592Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592856,781224,0,549320,TO_TIMESTAMP('2026-06-18 15:07:23.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2026-06-18 15:07:23.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-18T15:07:23.621Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781224 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-18T15:07:23.650Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2026-06-18T15:07:23.681Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781224
;

-- 2026-06-18T15:07:23.707Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781224)
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 10 -> default.Geschäftspartner
-- Column: C_Order_ReturnPackage.C_BPartner_ID
-- 2026-06-18T15:08:04.471Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781224,0,549320,555463,652335,'F',TO_TIMESTAMP('2026-06-18 15:08:04.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',10,0,0,TO_TIMESTAMP('2026-06-18 15:08:04.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 10 -> default.Geschäftspartner
-- Column: C_Order_ReturnPackage.C_BPartner_ID
-- 2026-06-18T15:08:20.339Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-06-18 15:08:20.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652335
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 20 -> default.Sektion
-- Column: C_Order_ReturnPackage.AD_Org_ID
-- 2026-06-18T15:08:20.501Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-06-18 15:08:20.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652311
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Order_ReturnPackage.IsActive
-- 2026-06-18T15:08:38.288Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-06-18 15:08:38.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652310
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 10 -> default.Palette
-- Column: C_Order_ReturnPackage.PalletType
-- 2026-06-18T15:08:38.457Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-06-18 15:08:38.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652307
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 10 -> default.Geliefert
-- Column: C_Order_ReturnPackage.QtyDeliveredLU
-- 2026-06-18T15:08:38.619Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-06-18 15:08:38.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652308
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 10 -> default.Zurück
-- Column: C_Order_ReturnPackage.QtyReturnedLU
-- 2026-06-18T15:08:38.782Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-06-18 15:08:38.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652309
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 20 -> default.Sektion
-- Column: C_Order_ReturnPackage.AD_Org_ID
-- 2026-06-18T15:08:38.947Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-06-18 15:08:38.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652311
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Order_ReturnPackage.IsActive
-- 2026-06-18T15:08:43.720Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-06-18 15:08:43.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652310
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 10 -> default.Palette
-- Column: C_Order_ReturnPackage.PalletType
-- 2026-06-18T15:08:43.885Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-06-18 15:08:43.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652307
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 10 -> default.Geliefert
-- Column: C_Order_ReturnPackage.QtyDeliveredLU
-- 2026-06-18T15:08:44.048Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-06-18 15:08:44.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652308
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 10 -> default.Zurück
-- Column: C_Order_ReturnPackage.QtyReturnedLU
-- 2026-06-18T15:08:44.213Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-06-18 15:08:44.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652309
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 20 -> default.Sektion
-- Column: C_Order_ReturnPackage.AD_Org_ID
-- 2026-06-18T15:08:44.379Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-06-18 15:08:44.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652311
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 20 -> default.Mandant
-- Column: C_Order_ReturnPackage.AD_Client_ID
-- 2026-06-18T15:40:35.292Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2026-06-18 15:40:35.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652312
;

