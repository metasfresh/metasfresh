-- Name: M_PickingSlot
-- 2024-07-11T15:08:58.277Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541876,TO_TIMESTAMP('2024-07-11 18:08:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_PickingSlot',TO_TIMESTAMP('2024-07-11 18:08:58','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-07-11T15:08:58.283Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541876 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_PickingSlot
-- Table: M_PickingSlot
-- Key: M_PickingSlot.M_PickingSlot_ID
-- 2024-07-11T15:10:17.184Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,549918,0,541876,540543,TO_TIMESTAMP('2024-07-11 18:10:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2024-07-11 18:10:17','YYYY-MM-DD HH24:MI:SS'),100,'M_Warehouse_ID=@M_Warehouse_ID/-1@')
;

-- Column: C_Workplace.M_PickingSlot_ID
-- Column: C_Workplace.M_PickingSlot_ID
-- 2024-07-11T15:11:47.626Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588830,542276,0,30,541876,542375,'XX','M_PickingSlot_ID',TO_TIMESTAMP('2024-07-11 18:11:47','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.picking',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Picking Slot',0,0,TO_TIMESTAMP('2024-07-11 18:11:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T15:11:47.631Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588830 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T15:11:47.709Z
/* DDL */  select update_Column_Translation_From_AD_Element(542276) 
;

-- 2024-07-11T15:11:50.584Z
/* DDL */ SELECT public.db_alter_table('C_Workplace','ALTER TABLE public.C_Workplace ADD COLUMN M_PickingSlot_ID NUMERIC(10)')
;

-- 2024-07-11T15:11:50.636Z
ALTER TABLE C_Workplace ADD CONSTRAINT MPickingSlot_CWorkplace FOREIGN KEY (M_PickingSlot_ID) REFERENCES public.M_PickingSlot DEFERRABLE INITIALLY DEFERRED
;

-- Field: Arbeitsplatz -> Arbeitsplatz -> Picking Slot
-- Column: C_Workplace.M_PickingSlot_ID
-- Field: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> Picking Slot
-- Column: C_Workplace.M_PickingSlot_ID
-- 2024-07-11T15:15:26.126Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588830,729091,0,547260,TO_TIMESTAMP('2024-07-11 18:15:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Picking Slot',TO_TIMESTAMP('2024-07-11 18:15:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T15:15:26.130Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729091 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T15:15:26.134Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542276) 
;

-- 2024-07-11T15:15:26.166Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729091
;

-- 2024-07-11T15:15:26.175Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729091)
;

-- UI Element: Arbeitsplatz -> Arbeitsplatz.Picking Slot
-- Column: C_Workplace.M_PickingSlot_ID
-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Picking Slot
-- Column: C_Workplace.M_PickingSlot_ID
-- 2024-07-11T15:15:42.958Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729091,0,547260,625005,551256,'F',TO_TIMESTAMP('2024-07-11 18:15:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Picking Slot',30,0,0,TO_TIMESTAMP('2024-07-11 18:15:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T16:13:15.633Z
INSERT INTO t_alter_column values('c_workplace','M_PickingSlot_ID','NUMERIC(10)',null,null)
;

-- Column: C_Workplace.M_PickingSlot_ID
-- Column: C_Workplace.M_PickingSlot_ID
-- 2024-07-11T16:14:54.273Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-07-11 19:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588830
;

-- 2024-07-11T16:14:55.393Z
INSERT INTO t_alter_column values('c_workplace','M_PickingSlot_ID','NUMERIC(10)',null,null)
;

-- Reference: M_PickingSlot
-- Table: M_PickingSlot
-- Key: M_PickingSlot.M_PickingSlot_ID
-- 2024-07-12T16:57:54.045Z
UPDATE AD_Ref_Table SET WhereClause='M_Warehouse_ID=@M_Warehouse_ID/-1@',Updated=TO_TIMESTAMP('2024-07-12 19:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541876
;

