-- 2024-03-15T11:51:33.457Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583026,0,'IsManuallyClosed',TO_TIMESTAMP('2024-03-15 13:51:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Manually closed','Manually closed',TO_TIMESTAMP('2024-03-15 13:51:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-15T11:51:33.470Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583026 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Picking_Job_Line.IsManuallyClosed
-- Column: M_Picking_Job_Line.IsManuallyClosed
-- 2024-03-15T11:51:50.725Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587993,583026,0,20,541907,'XX','IsManuallyClosed',TO_TIMESTAMP('2024-03-15 13:51:50','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Manually closed',0,0,TO_TIMESTAMP('2024-03-15 13:51:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-15T11:51:50.730Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587993 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-15T11:51:50.786Z
/* DDL */  select update_Column_Translation_From_AD_Element(583026) 
;

-- 2024-03-15T11:51:52.288Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD COLUMN IsManuallyClosed CHAR(1) DEFAULT ''N'' CHECK (IsManuallyClosed IN (''Y'',''N'')) NOT NULL')
;

-- Field: Picking Job -> Picking Job Line -> Manually closed
-- Column: M_Picking_Job_Line.IsManuallyClosed
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Manually closed
-- Column: M_Picking_Job_Line.IsManuallyClosed
-- 2024-03-15T11:52:18.248Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587993,726582,0,544862,TO_TIMESTAMP('2024-03-15 13:52:17','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Manually closed',TO_TIMESTAMP('2024-03-15 13:52:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-15T11:52:18.253Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726582 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-15T11:52:18.258Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583026) 
;

-- 2024-03-15T11:52:18.281Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726582
;

-- 2024-03-15T11:52:18.284Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726582)
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20
-- UI Element Group: referenced documents
-- 2024-03-15T11:52:56.760Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2024-03-15 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551278
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20
-- UI Element Group: flags
-- 2024-03-15T11:53:01.965Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547150,551694,TO_TIMESTAMP('2024-03-15 13:53:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',30,TO_TIMESTAMP('2024-03-15 13:53:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job Line.Verarbeitet
-- Column: M_Picking_Job_Line.Processed
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> flags.Verarbeitet
-- Column: M_Picking_Job_Line.Processed
-- 2024-03-15T11:53:13.039Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669049,0,544862,551694,623758,'F',TO_TIMESTAMP('2024-03-15 13:53:12','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2024-03-15 13:53:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job Line.Manually closed
-- Column: M_Picking_Job_Line.IsManuallyClosed
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> flags.Manually closed
-- Column: M_Picking_Job_Line.IsManuallyClosed
-- 2024-03-15T11:53:23.273Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726582,0,544862,551694,623759,'F',TO_TIMESTAMP('2024-03-15 13:53:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Manually closed',20,0,0,TO_TIMESTAMP('2024-03-15 13:53:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Picking Job -> Picking Job Line -> Verarbeitet
-- Column: M_Picking_Job_Line.Processed
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Verarbeitet
-- Column: M_Picking_Job_Line.Processed
-- 2024-03-15T11:53:32.168Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-03-15 13:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669049
;

-- Field: Picking Job -> Picking Job Line -> Manually closed
-- Column: M_Picking_Job_Line.IsManuallyClosed
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Manually closed
-- Column: M_Picking_Job_Line.IsManuallyClosed
-- 2024-03-15T11:53:34.683Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-03-15 13:53:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=726582
;

-- UI Element: Picking Job -> Picking Job Line.Manually closed
-- Column: M_Picking_Job_Line.IsManuallyClosed
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> flags.Manually closed
-- Column: M_Picking_Job_Line.IsManuallyClosed
-- 2024-03-15T11:53:47.790Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-03-15 13:53:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623759
;

-- UI Element: Picking Job -> Picking Job Line.Verarbeitet
-- Column: M_Picking_Job_Line.Processed
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> flags.Verarbeitet
-- Column: M_Picking_Job_Line.Processed
-- 2024-03-15T11:53:47.808Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-03-15 13:53:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623758
;

-- UI Element: Picking Job -> Picking Job Line.Verarbeitet
-- Column: M_Picking_Job_Line.Processed
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> flags.Verarbeitet
-- Column: M_Picking_Job_Line.Processed
-- 2024-03-15T11:53:55.051Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-03-15 13:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623758
;

