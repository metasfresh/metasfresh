-- Column: M_HU_Trace.M_Inventory_ID
-- Column: M_HU_Trace.M_Inventory_ID
-- 2023-04-07T15:13:37.571Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586465,1027,0,30,540832,'M_Inventory_ID',TO_TIMESTAMP('2023-04-07 18:13:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Parameter für eine physische Inventur','de.metas.handlingunits',0,10,'Bezeichnet die eindeutigen Parameter für eine physische Inventur','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Inventur',0,0,TO_TIMESTAMP('2023-04-07 18:13:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-07T15:13:37.573Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586465 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-07T15:13:37.599Z
/* DDL */  select update_Column_Translation_From_AD_Element(1027) 
;

-- 2023-04-07T15:13:38.574Z
/* DDL */ SELECT public.db_alter_table('M_HU_Trace','ALTER TABLE public.M_HU_Trace ADD COLUMN M_Inventory_ID NUMERIC(10)')
;

-- 2023-04-07T15:13:38.584Z
ALTER TABLE M_HU_Trace ADD CONSTRAINT MInventory_MHUTrace FOREIGN KEY (M_Inventory_ID) REFERENCES public.M_Inventory DEFERRABLE INITIALLY DEFERRED
;

-- Field: HU Rückverfolgung -> Rückverfolgung -> Inventur
-- Column: M_HU_Trace.M_Inventory_ID
-- Field: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> Inventur
-- Column: M_HU_Trace.M_Inventory_ID
-- 2023-04-07T15:14:05.055Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586465,713838,0,540842,TO_TIMESTAMP('2023-04-07 18:14:04','YYYY-MM-DD HH24:MI:SS'),100,'Parameter für eine physische Inventur',10,'de.metas.handlingunits','Bezeichnet die eindeutigen Parameter für eine physische Inventur','Y','N','N','N','N','N','N','N','Inventur',TO_TIMESTAMP('2023-04-07 18:14:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-07T15:14:05.056Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=713838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-07T15:14:05.058Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1027) 
;

-- 2023-04-07T15:14:05.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713838
;

-- 2023-04-07T15:14:05.070Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713838)
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Inventur
-- Column: M_HU_Trace.M_Inventory_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Inventur
-- Column: M_HU_Trace.M_Inventory_ID
-- 2023-04-07T15:14:44.196Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713838,0,540842,540891,616650,'F',TO_TIMESTAMP('2023-04-07 18:14:44','YYYY-MM-DD HH24:MI:SS'),100,'Parameter für eine physische Inventur','Bezeichnet die eindeutigen Parameter für eine physische Inventur','Y','N','N','Y','N','N','N',0,'Inventur',60,0,0,TO_TIMESTAMP('2023-04-07 18:14:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Menge
-- Column: M_HU_Trace.Qty
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> type.Menge
-- Column: M_HU_Trace.Qty
-- 2023-04-07T15:14:58.780Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-04-07 18:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546769
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Handling Unit
-- Column: M_HU_Trace.M_HU_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> doc.Handling Unit
-- Column: M_HU_Trace.M_HU_ID
-- 2023-04-07T15:14:58.789Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-04-07 18:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546771
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Belegart
-- Column: M_HU_Trace.C_DocType_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> doc.Belegart
-- Column: M_HU_Trace.C_DocType_ID
-- 2023-04-07T15:14:58.798Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-04-07 18:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546779
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Lieferung/Wareneingang
-- Column: M_HU_Trace.M_InOut_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Lieferung/Wareneingang
-- Column: M_HU_Trace.M_InOut_ID
-- 2023-04-07T15:14:58.807Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-04-07 18:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546783
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Inventur
-- Column: M_HU_Trace.M_Inventory_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Inventur
-- Column: M_HU_Trace.M_Inventory_ID
-- 2023-04-07T15:14:58.816Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-04-07 18:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616650
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Warenbewegung
-- Column: M_HU_Trace.M_Movement_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Warenbewegung
-- Column: M_HU_Trace.M_Movement_ID
-- 2023-04-07T15:14:58.824Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-04-07 18:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546784
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Transaktionszeile
-- Column: M_HU_Trace.M_HU_Trx_Line_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> doc.Transaktionszeile
-- Column: M_HU_Trace.M_HU_Trx_Line_ID
-- 2023-04-07T15:14:58.832Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-04-07 18:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546781
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Lieferdisposition
-- Column: M_HU_Trace.M_ShipmentSchedule_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Lieferdisposition
-- Column: M_HU_Trace.M_ShipmentSchedule_ID
-- 2023-04-07T15:14:58.840Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-04-07 18:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546785
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Manufacturing Cost Collector
-- Column: M_HU_Trace.PP_Cost_Collector_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Manufacturing Cost Collector
-- Column: M_HU_Trace.PP_Cost_Collector_ID
-- 2023-04-07T15:14:58.849Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-04-07 18:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546786
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Produktionsauftrag
-- Column: M_HU_Trace.PP_Order_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Produktionsauftrag
-- Column: M_HU_Trace.PP_Order_ID
-- 2023-04-07T15:14:58.857Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-04-07 18:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546787
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Sektion
-- Column: M_HU_Trace.AD_Org_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> org.Sektion
-- Column: M_HU_Trace.AD_Org_ID
-- 2023-04-07T15:14:58.863Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-04-07 18:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546776
;

