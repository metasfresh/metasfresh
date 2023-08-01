-- Value: de.metas.deliveryplanning.DeliveryPlanningService.OrderFullyDelivered
-- 2023-07-31T16:25:22.385Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545314,0,TO_TIMESTAMP('2023-07-31 19:25:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Order is fully delivered.','I',TO_TIMESTAMP('2023-07-31 19:25:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.deliveryplanning.DeliveryPlanningService.OrderFullyDelivered')
;

-- 2023-07-31T16:25:22.385Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545314 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.OrderFullyDelivered
-- 2023-07-31T16:26:01.968Z
UPDATE AD_Message_Trl SET MsgText='Die Bestellung wird vollständig geliefert.',Updated=TO_TIMESTAMP('2023-07-31 19:26:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545314
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.OrderFullyDelivered
-- 2023-07-31T16:26:04.708Z
UPDATE AD_Message_Trl SET MsgText='Die Bestellung wird vollständig geliefert.',Updated=TO_TIMESTAMP('2023-07-31 19:26:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545314
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.OrderFullyDelivered
-- 2023-07-31T16:26:08.041Z
UPDATE AD_Message_Trl SET MsgText='Die Bestellung wird vollständig geliefert.',Updated=TO_TIMESTAMP('2023-07-31 19:26:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545314
;

-- Name: C_Order Not Fully Delivered
-- 2023-07-31T16:45:09.475Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540650,TO_TIMESTAMP('2023-07-31 19:45:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Order Not Fully Delivered','S',TO_TIMESTAMP('2023-07-31 19:45:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: C_Order Not Fully Delivered
-- 2023-07-31T16:48:53.764Z
UPDATE AD_Val_Rule SET Code='C_Order.OrderStatus != ''FD''',Updated=TO_TIMESTAMP('2023-07-31 19:48:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540650
;

-- Name: C_Order Not Completely Invoiced
-- 2023-07-31T16:50:21.349Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540651,'C_Order.InvoiceStatus != ''CI''',TO_TIMESTAMP('2023-07-31 19:50:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Order Not Completely Invoiced','S',TO_TIMESTAMP('2023-07-31 19:50:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Order.OrderStatus
-- 2023-07-31T16:50:54.937Z
UPDATE AD_Column SET FilterDefaultValue='',Updated=TO_TIMESTAMP('2023-07-31 19:50:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;

-- Column: C_Order.OrderStatus
-- 2023-07-31T16:51:25.886Z
UPDATE AD_Column SET Filter_Val_Rule_ID=540650,Updated=TO_TIMESTAMP('2023-07-31 19:51:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;

-- Column: C_Order.InvoiceStatus
-- 2023-07-31T16:51:46.138Z
UPDATE AD_Column SET FilterDefaultValue='',Updated=TO_TIMESTAMP('2023-07-31 19:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552695
;

-- Column: C_Order.InvoiceStatus
-- 2023-07-31T16:52:10.744Z
UPDATE AD_Column SET Filter_Val_Rule_ID=540651,Updated=TO_TIMESTAMP('2023-07-31 19:52:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552695
;

-- Name: C_Order Not Fully Delivered
-- 2023-07-31T16:55:45.380Z
UPDATE AD_Val_Rule SET Code='(select * from c_order where orderstatus != ''FD'')',Updated=TO_TIMESTAMP('2023-07-31 19:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540650
;

-- Name: C_Order Not Fully Delivered
-- 2023-07-31T16:59:34.831Z
UPDATE AD_Val_Rule SET Code='(select * from c_order where c_order.orderstatus != ''FD'')',Updated=TO_TIMESTAMP('2023-07-31 19:59:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540650
;

-- Name: C_Order Not Fully Delivered
-- 2023-07-31T17:09:35.425Z
UPDATE AD_Val_Rule SET Code='C_Order.OrderStatus IN (''DR'',''IP'',''VO'',''CO'')',Updated=TO_TIMESTAMP('2023-07-31 20:09:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540650
;

-- Name: C_Order Not Fully Delivered
-- 2023-07-31T17:22:17.909Z
UPDATE AD_Val_Rule SET Code='(select * from C_Order where C_Order.OrderStatus IN (''DR'',''IP'',''VO'',''CO''))',Updated=TO_TIMESTAMP('2023-07-31 20:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540650
;

-- Name: C_Order Not Fully Delivered
-- 2023-07-31T17:30:38.755Z
UPDATE AD_Val_Rule SET Code='(select * from C_Order where 
( CASE     
WHEN C_Order.DocStatus = ''DR'' THEN ''DR''     
WHEN C_Order.DocStatus = ''IP'' THEN ''IP''     
WHEN C_Order.DocStatus IN (''VO'',''RE'') THEN ''VO''     
WHEN C_Order.DocStatus IN (''CO'',''CL'') AND (SELECT SUM(qtyordered - qtydelivered) from C_OrderLine where C_Order_ID = C_Order.c_order_id) > 0 THEN ''CO''     
WHEN (SELECT SUM(qtydelivered - qtyordered) from C_OrderLine where C_Order_ID = C_Order.c_order_id) >= 0 THEN ''FD''   ELSE ''DR''  END ) IN (''DR'',''IP'',''VO'',''CO''))',Updated=TO_TIMESTAMP('2023-07-31 20:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540650
;

-- Name: C_Order Not Fully Delivered
-- 2023-07-31T17:31:59.326Z
UPDATE AD_Val_Rule SET Code='( CASE     
WHEN C_Order.DocStatus = ''DR'' THEN ''DR''     
WHEN C_Order.DocStatus = ''IP'' THEN ''IP''     
WHEN C_Order.DocStatus IN (''VO'',''RE'') THEN ''VO''     
WHEN C_Order.DocStatus IN (''CO'',''CL'') AND (SELECT SUM(qtyordered - qtydelivered) from C_OrderLine where C_Order_ID = C_Order.c_order_id) > 0 THEN ''CO''     
WHEN (SELECT SUM(qtydelivered - qtyordered) from C_OrderLine where C_Order_ID = C_Order.c_order_id) >= 0 THEN ''FD''   ELSE ''DR''  END ) IN (''DR'',''IP'',''VO'',''CO''))',Updated=TO_TIMESTAMP('2023-07-31 20:31:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540650
;

-- Column: C_Order.OrderStatus
-- 2023-08-01T07:09:35.479Z
UPDATE AD_Column SET Filter_Val_Rule_ID=NULL, FilterDefaultValue='DR,IP,VO,CO', FilterOperator='B',Updated=TO_TIMESTAMP('2023-08-01 10:09:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.OrderFullyDelivered
-- 2023-07-31T16:25:22.385Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545314,0,TO_TIMESTAMP('2023-07-31 19:25:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Order is fully delivered.','I',TO_TIMESTAMP('2023-07-31 19:25:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.deliveryplanning.DeliveryPlanningService.OrderFullyDelivered')
;

-- 2023-07-31T16:25:22.385Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545314 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.OrderFullyDelivered
-- 2023-07-31T16:26:01.968Z
UPDATE AD_Message_Trl SET MsgText='Die Bestellung wird vollständig geliefert.',Updated=TO_TIMESTAMP('2023-07-31 19:26:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545314
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.OrderFullyDelivered
-- 2023-07-31T16:26:04.708Z
UPDATE AD_Message_Trl SET MsgText='Die Bestellung wird vollständig geliefert.',Updated=TO_TIMESTAMP('2023-07-31 19:26:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545314
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.OrderFullyDelivered
-- 2023-07-31T16:26:08.041Z
UPDATE AD_Message_Trl SET MsgText='Die Bestellung wird vollständig geliefert.',Updated=TO_TIMESTAMP('2023-07-31 19:26:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545314
;

-- 2023-08-01T09:01:24.796Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582619,0,'NotFullyDelivered',TO_TIMESTAMP('2023-08-01 12:01:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Not Fully Delivered','Not Fully Delivered',TO_TIMESTAMP('2023-08-01 12:01:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-08-01T09:01:25.280Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582619 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: NotFullyDelivered
-- 2023-08-01T09:01:51.894Z
UPDATE AD_Element_Trl SET Name='Nicht vollständig ausgeliefert', PrintName='Nicht vollständig ausgeliefert',Updated=TO_TIMESTAMP('2023-08-01 12:01:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582619 AND AD_Language='de_CH'
;

-- 2023-08-01T09:01:51.925Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582619,'de_CH')
;

-- Element: NotFullyDelivered
-- 2023-08-01T09:01:55.357Z
UPDATE AD_Element_Trl SET Name='Nicht vollständig ausgeliefert',Updated=TO_TIMESTAMP('2023-08-01 12:01:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582619 AND AD_Language='de_DE'
;

-- 2023-08-01T09:01:55.359Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582619,'de_DE')
;

-- Element: NotFullyDelivered
-- 2023-08-01T09:02:03.690Z
UPDATE AD_Element_Trl SET PrintName='Nicht vollständig ausgeliefert',Updated=TO_TIMESTAMP('2023-08-01 12:02:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582619 AND AD_Language='de_DE'
;

-- 2023-08-01T09:02:03.706Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582619,'de_DE')
;

-- Column: C_Order.NotFullyDelivered
-- 2023-08-01T09:03:05.108Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587241,582619,0,20,259,'XX','NotFullyDelivered','( CASE           WHEN (SELECT SUM(qtydelivered - qtyordered) from C_OrderLine where C_Order_ID = C_Order.c_order_id) >= 0 THEN ''N''    ELSE ''Y''   END )',TO_TIMESTAMP('2023-08-01 12:03:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Not Fully Delivered',0,0,TO_TIMESTAMP('2023-08-01 12:03:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-08-01T09:03:05.108Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587241 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-01T09:03:05.785Z
/* DDL */  select update_Column_Translation_From_AD_Element(582619)
;

-- 2023-08-01T09:04:28.095Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582620,0,'NotCompletelyInvoiced',TO_TIMESTAMP('2023-08-01 12:04:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Not Completely Invoiced','Not Completely Invoiced',TO_TIMESTAMP('2023-08-01 12:04:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-08-01T09:04:28.095Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582620 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: NotCompletelyInvoiced
-- 2023-08-01T09:04:50.796Z
UPDATE AD_Element_Trl SET Name='Nicht vollständig fakturiert', PrintName='Nicht vollständig fakturiert',Updated=TO_TIMESTAMP('2023-08-01 12:04:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582620 AND AD_Language='de_CH'
;

-- 2023-08-01T09:04:50.796Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582620,'de_CH')
;

-- Element: NotCompletelyInvoiced
-- 2023-08-01T09:04:55.830Z
UPDATE AD_Element_Trl SET Name='Nicht vollständig fakturiert', PrintName='Nicht vollständig fakturiert',Updated=TO_TIMESTAMP('2023-08-01 12:04:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582620 AND AD_Language='de_DE'
;

-- 2023-08-01T09:04:55.830Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582620,'de_DE')
;

-- Column: C_Order.NotCompletelyInvoiced
-- 2023-08-01T09:08:46.770Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587242,582620,0,20,259,'XX','NotCompletelyInvoiced','( CASE   WHEN C_Order.QtyMoved <= C_Order.QtyInvoiced AND C_Order.QtyMoved > 0 THEN ''N'' ELSE ''Y''   END )',TO_TIMESTAMP('2023-08-01 12:08:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Not Completely Invoiced',0,0,TO_TIMESTAMP('2023-08-01 12:08:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-08-01T09:08:46.770Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587242 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-01T09:08:47.336Z
/* DDL */  select update_Column_Translation_From_AD_Element(582620)
;

-- Column: C_Order.NotCompletelyInvoiced
-- 2023-08-01T09:09:10.067Z
UPDATE AD_Column SET FilterDefaultValue='Y', FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-01 12:09:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587242
;

-- Column: C_Order.NotFullyDelivered
-- 2023-08-01T09:09:31.250Z
UPDATE AD_Column SET FilterDefaultValue='Y', FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-01 12:09:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587241
;

-- Column: C_Order.InvoiceStatus
-- 2023-08-01T09:09:47.014Z
UPDATE AD_Column SET Filter_Val_Rule_ID=NULL, IsSelectionColumn='N',Updated=TO_TIMESTAMP('2023-08-01 12:09:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552695
;

-- Column: C_Order.OrderStatus
-- 2023-08-01T09:10:10.199Z
UPDATE AD_Column SET FilterDefaultValue='', IsSelectionColumn='N',Updated=TO_TIMESTAMP('2023-08-01 12:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;
