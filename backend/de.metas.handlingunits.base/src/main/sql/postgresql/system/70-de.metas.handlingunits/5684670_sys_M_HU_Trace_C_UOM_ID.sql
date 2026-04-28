-- Column: M_HU_Trace.C_UOM_ID
-- Column: M_HU_Trace.C_UOM_ID
-- 2023-04-10T11:31:20.695Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586466,215,0,30,540832,'C_UOM_ID',TO_TIMESTAMP('2023-04-10 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Maßeinheit','de.metas.handlingunits',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2023-04-10 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-10T11:31:20.702Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586466 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-10T11:31:20.749Z
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- 2023-04-10T11:31:21.861Z
/* DDL */ SELECT public.db_alter_table('M_HU_Trace','ALTER TABLE public.M_HU_Trace ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2023-04-10T11:31:21.868Z
ALTER TABLE M_HU_Trace ADD CONSTRAINT CUOM_MHUTrace FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;




-- Field: HU Rückverfolgung -> Rückverfolgung -> Maßeinheit
-- Column: M_HU_Trace.C_UOM_ID
-- Field: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> Maßeinheit
-- Column: M_HU_Trace.C_UOM_ID
-- 2023-04-10T12:13:43.292Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586466,713839,0,540842,TO_TIMESTAMP('2023-04-10 15:13:43','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'de.metas.handlingunits','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2023-04-10 15:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-10T12:13:43.294Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=713839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-10T12:13:43.296Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-04-10T12:13:43.318Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713839
;

-- 2023-04-10T12:13:43.319Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713839)
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Maßeinheit
-- Column: M_HU_Trace.C_UOM_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> type.Maßeinheit
-- Column: M_HU_Trace.C_UOM_ID
-- 2023-04-10T12:14:24.835Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713839,0,540842,540892,616651,'F',TO_TIMESTAMP('2023-04-10 15:14:24','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',50,0,0,TO_TIMESTAMP('2023-04-10 15:14:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Maßeinheit
-- Column: M_HU_Trace.C_UOM_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> type.Maßeinheit
-- Column: M_HU_Trace.C_UOM_ID
-- 2023-04-10T12:14:33.349Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-04-10 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616651
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Handling Unit
-- Column: M_HU_Trace.M_HU_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> doc.Handling Unit
-- Column: M_HU_Trace.M_HU_ID
-- 2023-04-10T12:14:33.359Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-04-10 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546771
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Belegart
-- Column: M_HU_Trace.C_DocType_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> doc.Belegart
-- Column: M_HU_Trace.C_DocType_ID
-- 2023-04-10T12:14:33.368Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-04-10 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546779
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Lieferung/Wareneingang
-- Column: M_HU_Trace.M_InOut_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Lieferung/Wareneingang
-- Column: M_HU_Trace.M_InOut_ID
-- 2023-04-10T12:14:33.377Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-04-10 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546783
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Inventur
-- Column: M_HU_Trace.M_Inventory_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Inventur
-- Column: M_HU_Trace.M_Inventory_ID
-- 2023-04-10T12:14:33.386Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-04-10 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616650
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Warenbewegung
-- Column: M_HU_Trace.M_Movement_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Warenbewegung
-- Column: M_HU_Trace.M_Movement_ID
-- 2023-04-10T12:14:33.395Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-04-10 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546784
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Transaktionszeile
-- Column: M_HU_Trace.M_HU_Trx_Line_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> doc.Transaktionszeile
-- Column: M_HU_Trace.M_HU_Trx_Line_ID
-- 2023-04-10T12:14:33.404Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-04-10 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546781
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Lieferdisposition
-- Column: M_HU_Trace.M_ShipmentSchedule_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Lieferdisposition
-- Column: M_HU_Trace.M_ShipmentSchedule_ID
-- 2023-04-10T12:14:33.413Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-04-10 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546785
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Manufacturing Cost Collector
-- Column: M_HU_Trace.PP_Cost_Collector_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Manufacturing Cost Collector
-- Column: M_HU_Trace.PP_Cost_Collector_ID
-- 2023-04-10T12:14:33.421Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-04-10 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546786
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Produktionsauftrag
-- Column: M_HU_Trace.PP_Order_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Produktionsauftrag
-- Column: M_HU_Trace.PP_Order_ID
-- 2023-04-10T12:14:33.430Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-04-10 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546787
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Sektion
-- Column: M_HU_Trace.AD_Org_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> org.Sektion
-- Column: M_HU_Trace.AD_Org_ID
-- 2023-04-10T12:14:33.437Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-04-10 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546776
;





