-- Column: M_Inventory.M_Picking_Job_ID
-- Column: M_Inventory.M_Picking_Job_ID
-- 2024-02-08T06:55:47.932Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587899,580042,0,30,321,'XX','M_Picking_Job_ID',TO_TIMESTAMP('2024-02-08 08:55:47','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.picking',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Picking Job',0,0,TO_TIMESTAMP('2024-02-08 08:55:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-08T06:55:47.942Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587899 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-08T06:55:48.029Z
/* DDL */  select update_Column_Translation_From_AD_Element(580042) 
;

-- 2024-02-08T06:55:50.278Z
/* DDL */ SELECT public.db_alter_table('M_Inventory','ALTER TABLE public.M_Inventory ADD COLUMN M_Picking_Job_ID NUMERIC(10)')
;

-- 2024-02-08T06:55:50.789Z
ALTER TABLE M_Inventory ADD CONSTRAINT MPickingJob_MInventory FOREIGN KEY (M_Picking_Job_ID) REFERENCES public.M_Picking_Job DEFERRABLE INITIALLY DEFERRED
;

-- Field: Inventur -> Bestandszählung -> Picking Job
-- Column: M_Inventory.M_Picking_Job_ID
-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Picking Job
-- Column: M_Inventory.M_Picking_Job_ID
-- 2024-02-08T06:56:35.425Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587899,725151,0,255,TO_TIMESTAMP('2024-02-08 08:56:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Picking Job',TO_TIMESTAMP('2024-02-08 08:56:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-08T06:56:35.431Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725151 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-08T06:56:35.438Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580042) 
;

-- 2024-02-08T06:56:35.462Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725151
;

-- 2024-02-08T06:56:35.466Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725151)
;

-- UI Column: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10
-- UI Element Group: referenced
-- 2024-02-08T07:00:05.887Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540909,551530,TO_TIMESTAMP('2024-02-08 09:00:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','referenced',15,TO_TIMESTAMP('2024-02-08 09:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur -> Bestandszählung.Picking Job
-- Column: M_Inventory.M_Picking_Job_ID
-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> referenced.Picking Job
-- Column: M_Inventory.M_Picking_Job_ID
-- 2024-02-08T07:00:19.648Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,725151,0,255,551530,622915,'F',TO_TIMESTAMP('2024-02-08 09:00:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Picking Job',10,0,0,TO_TIMESTAMP('2024-02-08 09:00:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Inventur -> Bestandszählung -> Picking Job
-- Column: M_Inventory.M_Picking_Job_ID
-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Picking Job
-- Column: M_Inventory.M_Picking_Job_ID
-- 2024-02-08T07:00:53.181Z
UPDATE AD_Field SET DisplayLogic='@M_Picking_Job_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-02-08 09:00:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=725151
;

-- Column: M_Inventory.M_Picking_Job_ID
-- Column: M_Inventory.M_Picking_Job_ID
-- 2024-02-08T07:06:34.953Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2024-02-08 09:06:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587899
;

