-- Column: C_POS_Order.C_Order_ID
-- Column: C_POS_Order.C_Order_ID
-- 2024-09-27T15:32:47.669Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589175,558,0,30,542434,'XX','C_Order_ID',TO_TIMESTAMP('2024-09-27 18:32:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftrag','de.metas.pos',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftrag',0,0,TO_TIMESTAMP('2024-09-27 18:32:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-27T15:32:47.677Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589175 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-27T15:32:47.683Z
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- 2024-09-27T15:32:51.011Z
/* DDL */ SELECT public.db_alter_table('C_POS_Order','ALTER TABLE public.C_POS_Order ADD COLUMN C_Order_ID NUMERIC(10)')
;

-- 2024-09-27T15:32:51.027Z
ALTER TABLE C_POS_Order ADD CONSTRAINT COrder_CPOSOrder FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- Field: POS Order -> POS Order -> Auftrag
-- Column: C_POS_Order.C_Order_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Auftrag
-- Column: C_POS_Order.C_Order_ID
-- 2024-09-27T15:33:13.107Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589175,731792,0,547591,TO_TIMESTAMP('2024-09-27 18:33:12','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',10,'de.metas.pos','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2024-09-27 18:33:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-27T15:33:13.113Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731792 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-27T15:33:13.116Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2024-09-27T15:33:13.175Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731792
;

-- 2024-09-27T15:33:13.181Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731792)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20
-- UI Element Group: sales order
-- 2024-09-27T15:34:00.390Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547547,552021,TO_TIMESTAMP('2024-09-27 18:34:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','sales order',50,TO_TIMESTAMP('2024-09-27 18:34:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20
-- UI Element Group: org
-- 2024-09-27T15:34:04.634Z
UPDATE AD_UI_ElementGroup SET SeqNo=990,Updated=TO_TIMESTAMP('2024-09-27 18:34:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551957
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20
-- UI Element Group: sales order
-- 2024-09-27T15:34:06.623Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2024-09-27 18:34:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552021
;

-- UI Element: POS Order -> POS Order.Auftrag
-- Column: C_POS_Order.C_Order_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> sales order.Auftrag
-- Column: C_POS_Order.C_Order_ID
-- 2024-09-27T15:34:23.664Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731792,0,547591,552021,626106,'F',TO_TIMESTAMP('2024-09-27 18:34:22','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','Auftrag',10,0,0,TO_TIMESTAMP('2024-09-27 18:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

