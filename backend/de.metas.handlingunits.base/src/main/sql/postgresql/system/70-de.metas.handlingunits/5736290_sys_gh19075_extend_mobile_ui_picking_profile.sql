-- 2024-10-09T07:11:08.964Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583314,0,'PickingLineGroupBy',TO_TIMESTAMP('2024-10-09 10:11:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Picking line group by','Picking line group by',TO_TIMESTAMP('2024-10-09 10:11:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T07:11:08.989Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583314 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PickingLineGroupBy
-- 2024-10-09T07:12:32.997Z
UPDATE AD_Element_Trl SET Name='Kommissionierung Liniengruppierung', PrintName='Kommissionierung Liniengruppierung',Updated=TO_TIMESTAMP('2024-10-09 10:12:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583314 AND AD_Language='de_CH'
;

-- 2024-10-09T07:12:33.034Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583314,'de_CH') 
;

-- Element: PickingLineGroupBy
-- 2024-10-09T07:12:36.479Z
UPDATE AD_Element_Trl SET Name='Kommissionierung Liniengruppierung', PrintName='Kommissionierung Liniengruppierung',Updated=TO_TIMESTAMP('2024-10-09 10:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583314 AND AD_Language='de_DE'
;

-- 2024-10-09T07:12:36.480Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583314,'de_DE') 
;

-- 2024-10-09T07:12:36.493Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583314,'de_DE') 
;

-- Element: PickingLineGroupBy
-- 2024-10-09T07:12:44.659Z
UPDATE AD_Element_Trl SET Name='Kommissionierung Liniengruppierung', PrintName='Kommissionierung Liniengruppierung',Updated=TO_TIMESTAMP('2024-10-09 10:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583314 AND AD_Language='fr_CH'
;

-- 2024-10-09T07:12:44.660Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583314,'fr_CH') 
;

-- 2024-10-09T07:13:09.633Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583315,0,'PickingLineSortBy',TO_TIMESTAMP('2024-10-09 10:13:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Picking line sort by','Picking line sort by',TO_TIMESTAMP('2024-10-09 10:13:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T07:13:09.636Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583315 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-10-09T07:13:54.334Z
UPDATE AD_Element SET Name='Sortierung der Kommissionierlinie',Updated=TO_TIMESTAMP('2024-10-09 10:13:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583315
;

-- 2024-10-09T07:13:54.338Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583315,'de_DE') 
;

-- Element: PickingLineSortBy
-- 2024-10-09T07:13:58.200Z
UPDATE AD_Element_Trl SET Name='Sortierung der Kommissionierlinie', PrintName='Sortierung der Kommissionierlinie',Updated=TO_TIMESTAMP('2024-10-09 10:13:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583315 AND AD_Language='de_CH'
;

-- 2024-10-09T07:13:58.203Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583315,'de_CH') 
;

-- Element: PickingLineSortBy
-- 2024-10-09T07:14:00.425Z
UPDATE AD_Element_Trl SET Name='Sortierung der Kommissionierlinie', PrintName='Sortierung der Kommissionierlinie',Updated=TO_TIMESTAMP('2024-10-09 10:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583315 AND AD_Language='de_DE'
;

-- 2024-10-09T07:14:00.427Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583315,'de_DE') 
;

-- 2024-10-09T07:14:00.428Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583315,'de_DE') 
;

-- Element: PickingLineSortBy
-- 2024-10-09T07:14:05.610Z
UPDATE AD_Element_Trl SET Name='Sortierung der Kommissionierlinie', PrintName='Sortierung der Kommissionierlinie',Updated=TO_TIMESTAMP('2024-10-09 10:14:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583315 AND AD_Language='fr_CH'
;

-- 2024-10-09T07:14:05.613Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583315,'fr_CH') 
;

-- Name: PickingLineGroupByValues
-- 2024-10-09T07:14:51.357Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541899,TO_TIMESTAMP('2024-10-09 10:14:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','PickingLineGroupByValues',TO_TIMESTAMP('2024-10-09 10:14:51','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-10-09T07:14:51.369Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541899 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PickingLineGroupByValues
-- Value: product_category
-- ValueName: Product_Category
-- 2024-10-09T07:15:35.280Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543754,541899,TO_TIMESTAMP('2024-10-09 10:15:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Product Category',TO_TIMESTAMP('2024-10-09 10:15:35','YYYY-MM-DD HH24:MI:SS'),100,'product_category','Product_Category')
;

-- 2024-10-09T07:15:35.286Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543754 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PickingLineGroupByValues -> product_category_Product_Category
-- 2024-10-09T07:15:52.476Z
UPDATE AD_Ref_List_Trl SET Name='Produktkategorie',Updated=TO_TIMESTAMP('2024-10-09 10:15:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543754
;

-- Reference Item: PickingLineGroupByValues -> product_category_Product_Category
-- 2024-10-09T07:15:55.260Z
UPDATE AD_Ref_List_Trl SET Name='Produktkategorie',Updated=TO_TIMESTAMP('2024-10-09 10:15:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543754
;

-- Reference Item: PickingLineGroupByValues -> product_category_Product_Category
-- 2024-10-09T07:15:57.562Z
UPDATE AD_Ref_List_Trl SET Name='Produktkategorie',Updated=TO_TIMESTAMP('2024-10-09 10:15:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543754
;

-- Column: MobileUI_UserProfile_Picking.PickingLineGroupBy
-- Column: MobileUI_UserProfile_Picking.PickingLineGroupBy
-- 2024-10-09T07:16:55.958Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589282,583314,0,17,541850,542373,'XX','PickingLineGroupBy',TO_TIMESTAMP('2024-10-09 10:16:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kommissionierung Liniengruppierung',0,0,TO_TIMESTAMP('2024-10-09 10:16:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T07:16:55.972Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589282 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T07:16:55.989Z
/* DDL */  select update_Column_Translation_From_AD_Element(583314) 
;

-- Column: MobileUI_UserProfile_Picking.PickingLineGroupBy
-- Column: MobileUI_UserProfile_Picking.PickingLineGroupBy
-- 2024-10-09T07:17:04.359Z
UPDATE AD_Column SET AD_Reference_Value_ID=541899,Updated=TO_TIMESTAMP('2024-10-09 10:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589282
;

-- 2024-10-09T07:17:06.940Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN PickingLineGroupBy VARCHAR(255)')
;

-- 2024-10-09T07:40:31.264Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking','PickingLineGroupBy','VARCHAR(255)',null,null)
;

-- Column: MobileUI_UserProfile_Picking.PickingLineSortBy
-- Column: MobileUI_UserProfile_Picking.PickingLineSortBy
-- 2024-10-09T07:41:51.790Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589283,583315,0,17,541899,542373,'XX','PickingLineSortBy',TO_TIMESTAMP('2024-10-09 10:41:51','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sortierung der Kommissionierlinie',0,0,TO_TIMESTAMP('2024-10-09 10:41:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T07:41:51.796Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589283 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T07:41:51.803Z
/* DDL */  select update_Column_Translation_From_AD_Element(583315) 
;

-- Name: PickingLineSortByValues
-- 2024-10-09T07:42:17.363Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541900,TO_TIMESTAMP('2024-10-09 10:42:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','PickingLineSortByValues',TO_TIMESTAMP('2024-10-09 10:42:17','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-10-09T07:42:17.365Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541900 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PickingLineSortByValues
-- Value: ORDER_LINE_SEQ_NO
-- ValueName: ORDER_LINE_SEQ_NO
-- 2024-10-09T07:43:04.815Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543755,541900,TO_TIMESTAMP('2024-10-09 10:43:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Line Seq',TO_TIMESTAMP('2024-10-09 10:43:04','YYYY-MM-DD HH24:MI:SS'),100,'ORDER_LINE_SEQ_NO','ORDER_LINE_SEQ_NO')
;

-- 2024-10-09T07:43:04.820Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543755 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PickingLineSortByValues
-- Value: QTY_TO_PICK_ASC
-- ValueName: QTY_TO_PICK_ASC
-- 2024-10-09T07:43:21.442Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543756,541900,TO_TIMESTAMP('2024-10-09 10:43:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Qty to pick',TO_TIMESTAMP('2024-10-09 10:43:21','YYYY-MM-DD HH24:MI:SS'),100,'QTY_TO_PICK_ASC','QTY_TO_PICK_ASC')
;

-- 2024-10-09T07:43:21.444Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543756 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PickingLineSortByValues
-- Value: QTY_TO_PICK_DESC
-- ValueName: QTY_TO_PICK_DESC
-- 2024-10-09T07:43:40.907Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543757,541900,TO_TIMESTAMP('2024-10-09 10:43:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Qty to pick - desc',TO_TIMESTAMP('2024-10-09 10:43:40','YYYY-MM-DD HH24:MI:SS'),100,'QTY_TO_PICK_DESC','QTY_TO_PICK_DESC')
;

-- 2024-10-09T07:43:40.910Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543757 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PickingLineSortByValues -> ORDER_LINE_SEQ_NO_ORDER_LINE_SEQ_NO
-- 2024-10-09T07:44:06.334Z
UPDATE AD_Ref_List_Trl SET Name='Zeile Seq',Updated=TO_TIMESTAMP('2024-10-09 10:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543755
;

-- Reference Item: PickingLineSortByValues -> ORDER_LINE_SEQ_NO_ORDER_LINE_SEQ_NO
-- 2024-10-09T07:44:09.062Z
UPDATE AD_Ref_List_Trl SET Name='Zeile Seq',Updated=TO_TIMESTAMP('2024-10-09 10:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543755
;

-- Reference Item: PickingLineSortByValues -> ORDER_LINE_SEQ_NO_ORDER_LINE_SEQ_NO
-- 2024-10-09T07:44:12.299Z
UPDATE AD_Ref_List_Trl SET Name='Zeile Seq',Updated=TO_TIMESTAMP('2024-10-09 10:44:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543755
;

-- Reference Item: PickingLineSortByValues -> QTY_TO_PICK_ASC_QTY_TO_PICK_ASC
-- 2024-10-09T07:46:24.181Z
UPDATE AD_Ref_List_Trl SET Name='Zu kommissionierende Menge',Updated=TO_TIMESTAMP('2024-10-09 10:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543756
;

-- Reference Item: PickingLineSortByValues -> QTY_TO_PICK_ASC_QTY_TO_PICK_ASC
-- 2024-10-09T07:46:26.916Z
UPDATE AD_Ref_List_Trl SET Name='Zu kommissionierende Menge',Updated=TO_TIMESTAMP('2024-10-09 10:46:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543756
;

-- Reference Item: PickingLineSortByValues -> QTY_TO_PICK_ASC_QTY_TO_PICK_ASC
-- 2024-10-09T07:46:29.739Z
UPDATE AD_Ref_List_Trl SET Name='Zu kommissionierende Menge',Updated=TO_TIMESTAMP('2024-10-09 10:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543756
;

-- Reference Item: PickingLineSortByValues -> QTY_TO_PICK_DESC_QTY_TO_PICK_DESC
-- 2024-10-09T07:47:03.759Z
UPDATE AD_Ref_List_Trl SET Name='Zu kommissionierende Menge - Absteigend',Updated=TO_TIMESTAMP('2024-10-09 10:47:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543757
;

-- Reference Item: PickingLineSortByValues -> QTY_TO_PICK_DESC_QTY_TO_PICK_DESC
-- 2024-10-09T07:47:06.892Z
UPDATE AD_Ref_List_Trl SET Name='Zu kommissionierende Menge - Absteigend',Updated=TO_TIMESTAMP('2024-10-09 10:47:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543757
;

-- Reference Item: PickingLineSortByValues -> QTY_TO_PICK_DESC_QTY_TO_PICK_DESC
-- 2024-10-09T07:47:10.367Z
UPDATE AD_Ref_List_Trl SET Name='Zu kommissionierende Menge - Absteigend',Updated=TO_TIMESTAMP('2024-10-09 10:47:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543757
;

-- Reference: PickingLineSortByValues
-- Value: QTY_TO_PICK_DESC
-- ValueName: QTY_TO_PICK_DESC
-- 2024-10-09T07:47:15.431Z
UPDATE AD_Ref_List SET Name='Zu kommissionierende Menge - Absteigend',Updated=TO_TIMESTAMP('2024-10-09 10:47:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543757
;

-- Reference: PickingLineSortByValues
-- Value: QTY_TO_PICK_ASC
-- ValueName: QTY_TO_PICK_ASC
-- 2024-10-09T07:47:20.571Z
UPDATE AD_Ref_List SET Name='Zu kommissionierende Menge',Updated=TO_TIMESTAMP('2024-10-09 10:47:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543756
;

-- Reference: PickingLineSortByValues
-- Value: ORDER_LINE_SEQ_NO
-- ValueName: ORDER_LINE_SEQ_NO
-- 2024-10-09T07:47:26.357Z
UPDATE AD_Ref_List SET Name='Zeile Seq',Updated=TO_TIMESTAMP('2024-10-09 10:47:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543755
;

-- Column: MobileUI_UserProfile_Picking.PickingLineSortBy
-- Column: MobileUI_UserProfile_Picking.PickingLineSortBy
-- 2024-10-09T07:47:47.966Z
UPDATE AD_Column SET AD_Reference_Value_ID=541900,Updated=TO_TIMESTAMP('2024-10-09 10:47:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589283
;

-- 2024-10-09T08:49:03.453Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN PickingLineSortBy VARCHAR(255)')
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Kommissionierung Liniengruppierung
-- Column: MobileUI_UserProfile_Picking.PickingLineGroupBy
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Kommissionierung Liniengruppierung
-- Column: MobileUI_UserProfile_Picking.PickingLineGroupBy
-- 2024-10-09T08:49:47.139Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589282,731880,0,547258,TO_TIMESTAMP('2024-10-09 11:49:46','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Kommissionierung Liniengruppierung',TO_TIMESTAMP('2024-10-09 11:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T08:49:47.148Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731880 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T08:49:47.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583314) 
;

-- 2024-10-09T08:49:47.209Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731880
;

-- 2024-10-09T08:49:47.217Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731880)
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Sortierung der Kommissionierlinie
-- Column: MobileUI_UserProfile_Picking.PickingLineSortBy
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Sortierung der Kommissionierlinie
-- Column: MobileUI_UserProfile_Picking.PickingLineSortBy
-- 2024-10-09T08:50:16.885Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589283,731881,0,547258,TO_TIMESTAMP('2024-10-09 11:50:16','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Sortierung der Kommissionierlinie',TO_TIMESTAMP('2024-10-09 11:50:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T08:50:16.890Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T08:50:16.911Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583315) 
;

-- 2024-10-09T08:50:16.921Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731881
;

-- 2024-10-09T08:50:16.928Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731881)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Kommissionierung Liniengruppierung
-- Column: MobileUI_UserProfile_Picking.PickingLineGroupBy
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10 -> settings.Kommissionierung Liniengruppierung
-- Column: MobileUI_UserProfile_Picking.PickingLineGroupBy
-- 2024-10-09T08:51:04.378Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,731880,0,547258,626162,551255,'F',TO_TIMESTAMP('2024-10-09 11:51:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kommissionierung Liniengruppierung',20,0,0,TO_TIMESTAMP('2024-10-09 11:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Sortierung der Kommissionierlinie
-- Column: MobileUI_UserProfile_Picking.PickingLineSortBy
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10 -> settings.Sortierung der Kommissionierlinie
-- Column: MobileUI_UserProfile_Picking.PickingLineSortBy
-- 2024-10-09T08:51:18.479Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,731881,0,547258,626163,551255,'F',TO_TIMESTAMP('2024-10-09 11:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sortierung der Kommissionierlinie',30,0,0,TO_TIMESTAMP('2024-10-09 11:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: PickingLineGroupByValues
-- Value: product_category
-- ValueName: Product_Category
-- 2024-10-09T14:53:20.992Z
UPDATE AD_Ref_List SET Name='Produktkategorie',Updated=TO_TIMESTAMP('2024-10-09 17:53:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543754
;

