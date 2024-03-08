-- Column: PP_Order_Candidate.AD_Workflow_ID
-- Column SQL (old): null
-- Column: PP_Order_Candidate.AD_Workflow_ID
-- 2024-03-07T08:11:34.375Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587978,144,0,30,541913,'XX','AD_Workflow_ID','(SELECT pp.AD_Workflow_ID from PP_Product_Planning pp where pp.PP_Product_Planning_ID=@JoinTableNameOrAliasIncludingDot@PP_Product_Planning_ID)',TO_TIMESTAMP('2024-03-07 10:11:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Workflow oder Kombination von Aufgaben','EE01',0,10,'E','"Workflow" bezeichnet einen eindeutigen Workflow im System.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N',0,'Workflow',0,0,TO_TIMESTAMP('2024-03-07 10:11:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T08:11:34.377Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587978 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T08:11:34.381Z
/* DDL */  select update_Column_Translation_From_AD_Element(144) 
;

-- Field: Produktionsdisposition -> Produktionsdisposition -> Workflow
-- Column: PP_Order_Candidate.AD_Workflow_ID
-- Field: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> Workflow
-- Column: PP_Order_Candidate.AD_Workflow_ID
-- 2024-03-07T08:12:01.601Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587978,726569,0,544794,TO_TIMESTAMP('2024-03-07 10:12:01','YYYY-MM-DD HH24:MI:SS'),100,'Workflow oder Kombination von Aufgaben',10,'EE01','"Workflow" bezeichnet einen eindeutigen Workflow im System.','Y','N','N','N','N','N','N','N','Workflow',TO_TIMESTAMP('2024-03-07 10:12:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T08:12:01.605Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726569 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-07T08:12:01.607Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(144) 
;

-- 2024-03-07T08:12:01.617Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726569
;

-- 2024-03-07T08:12:01.619Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726569)
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Workflow
-- Column: PP_Order_Candidate.AD_Workflow_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Workflow
-- Column: PP_Order_Candidate.AD_Workflow_ID
-- 2024-03-07T08:12:33.195Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726569,0,544794,547106,623747,'F',TO_TIMESTAMP('2024-03-07 10:12:33','YYYY-MM-DD HH24:MI:SS'),100,'Workflow oder Kombination von Aufgaben','"Workflow" bezeichnet einen eindeutigen Workflow im System.','Y','N','Y','N','N','Workflow',70,0,0,TO_TIMESTAMP('2024-03-07 10:12:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Workflow
-- Column: PP_Order_Candidate.AD_Workflow_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Workflow
-- Column: PP_Order_Candidate.AD_Workflow_ID
-- 2024-03-07T08:12:44.457Z
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2024-03-07 10:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623747
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Workflow
-- Column: PP_Order_Candidate.AD_Workflow_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Workflow
-- Column: PP_Order_Candidate.AD_Workflow_ID
-- 2024-03-07T08:13:03.460Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623747
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Produktionsressource
-- Column: PP_Order_Candidate.S_Resource_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Produktionsressource
-- Column: PP_Order_Candidate.S_Resource_ID
-- 2024-03-07T08:13:03.468Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594539
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- 2024-03-07T08:13:03.477Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623744
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Lager
-- Column: PP_Order_Candidate.M_Warehouse_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Lager
-- Column: PP_Order_Candidate.M_Warehouse_ID
-- 2024-03-07T08:13:03.486Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594542
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Menge
-- Column: PP_Order_Candidate.QtyEntered
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Menge
-- Column: PP_Order_Candidate.QtyEntered
-- 2024-03-07T08:13:03.494Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594540
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Quantity To Process
-- Column: PP_Order_Candidate.QtyToProcess
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Quantity To Process
-- Column: PP_Order_Candidate.QtyToProcess
-- 2024-03-07T08:13:03.501Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595256
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Number of Resources to Process
-- Column: PP_Order_Candidate.NumberOfResources_ToProcess
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Number of Resources to Process
-- Column: PP_Order_Candidate.NumberOfResources_ToProcess
-- 2024-03-07T08:13:03.509Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613397
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Quantity Processed
-- Column: PP_Order_Candidate.QtyProcessed
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Quantity Processed
-- Column: PP_Order_Candidate.QtyProcessed
-- 2024-03-07T08:13:03.516Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595255
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Maßeinheit
-- Column: PP_Order_Candidate.C_UOM_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Maßeinheit
-- Column: PP_Order_Candidate.C_UOM_ID
-- 2024-03-07T08:13:03.524Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594541
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2024-03-07T08:13:03.532Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609611
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Zugesagter Termin
-- Column: PP_Order_Candidate.DatePromised
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> dates.Zugesagter Termin
-- Column: PP_Order_Candidate.DatePromised
-- 2024-03-07T08:13:03.537Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594544
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.geplanter Beginn
-- Column: PP_Order_Candidate.DateStartSchedule
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> dates.geplanter Beginn
-- Column: PP_Order_Candidate.DateStartSchedule
-- 2024-03-07T08:13:03.543Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594641
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Verarbeitet
-- Column: PP_Order_Candidate.Processed
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> bp.Verarbeitet
-- Column: PP_Order_Candidate.Processed
-- 2024-03-07T08:13:03.549Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594543
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Geschlossen
-- Column: PP_Order_Candidate.IsClosed
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> bp.Geschlossen
-- Column: PP_Order_Candidate.IsClosed
-- 2024-03-07T08:13:03.554Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595252
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Sektion
-- Column: PP_Order_Candidate.AD_Org_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> org.Sektion
-- Column: PP_Order_Candidate.AD_Org_ID
-- 2024-03-07T08:13:03.559Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594545
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Mandant
-- Column: PP_Order_Candidate.AD_Client_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> org.Mandant
-- Column: PP_Order_Candidate.AD_Client_ID
-- 2024-03-07T08:13:03.565Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2024-03-07 10:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594546
;

-- Column: PP_Order_Candidate.AD_Workflow_ID
-- Column: PP_Order_Candidate.AD_Workflow_ID
-- 2024-03-07T08:19:11.334Z
UPDATE AD_Column SET AD_Val_Rule_ID=52003,Updated=TO_TIMESTAMP('2024-03-07 10:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587978
;

