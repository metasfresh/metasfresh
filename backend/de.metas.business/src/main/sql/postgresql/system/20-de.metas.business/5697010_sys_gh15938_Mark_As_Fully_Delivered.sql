-- Name: Order Status
-- 2023-07-26T10:03:57.845Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541809,TO_TIMESTAMP('2023-07-26 13:03:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Order Status',TO_TIMESTAMP('2023-07-26 13:03:57','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-07-26T10:03:57.853Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541809 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Order Status
-- Value: DR
-- ValueName: Drafted
-- 2023-07-26T10:04:21.356Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543527,541809,TO_TIMESTAMP('2023-07-26 13:04:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Drafted',TO_TIMESTAMP('2023-07-26 13:04:21','YYYY-MM-DD HH24:MI:SS'),100,'DR','Drafted')
;

-- 2023-07-26T10:04:21.362Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543527 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Order Status
-- Value: VO
-- ValueName: Voided
-- 2023-07-26T10:05:02.088Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543528,541809,TO_TIMESTAMP('2023-07-26 13:05:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Voided',TO_TIMESTAMP('2023-07-26 13:05:01','YYYY-MM-DD HH24:MI:SS'),100,'VO','Voided')
;

-- 2023-07-26T10:05:02.088Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543528 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Order Status
-- Value: CO
-- ValueName: Completed
-- 2023-07-26T10:06:07.569Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543529,541809,TO_TIMESTAMP('2023-07-26 13:06:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Completed',TO_TIMESTAMP('2023-07-26 13:06:07','YYYY-MM-DD HH24:MI:SS'),100,'CO','Completed')
;

-- 2023-07-26T10:06:07.569Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543529 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Order Status
-- Value: FD
-- ValueName: Fully Delivered
-- 2023-07-26T10:06:23.210Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543530,541809,TO_TIMESTAMP('2023-07-26 13:06:23','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Fully Delivered',TO_TIMESTAMP('2023-07-26 13:06:23','YYYY-MM-DD HH24:MI:SS'),100,'FD','Fully Delivered')
;

-- 2023-07-26T10:06:23.218Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543530 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Order Status -> CO_Completed
-- 2023-07-26T10:07:09.909Z
UPDATE AD_Ref_List_Trl SET Name='Fertiggestellt',Updated=TO_TIMESTAMP('2023-07-26 13:07:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543529
;

-- Reference Item: Order Status -> CO_Completed
-- 2023-07-26T10:07:13.423Z
UPDATE AD_Ref_List_Trl SET Name='Fertiggestellt',Updated=TO_TIMESTAMP('2023-07-26 13:07:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543529
;

-- Reference Item: Order Status -> CO_Completed
-- 2023-07-26T10:07:20.238Z
UPDATE AD_Ref_List_Trl SET Name='Fertiggestellt',Updated=TO_TIMESTAMP('2023-07-26 13:07:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543529
;

-- Reference Item: Order Status -> DR_Drafted
-- 2023-07-26T10:08:07.721Z
UPDATE AD_Ref_List_Trl SET Name='Entwurf',Updated=TO_TIMESTAMP('2023-07-26 13:08:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543527
;

-- Reference Item: Order Status -> DR_Drafted
-- 2023-07-26T10:08:11.203Z
UPDATE AD_Ref_List_Trl SET Name='Entwurf',Updated=TO_TIMESTAMP('2023-07-26 13:08:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543527
;

-- Reference Item: Order Status -> DR_Drafted
-- 2023-07-26T10:08:20.846Z
UPDATE AD_Ref_List_Trl SET Name='Entwurf',Updated=TO_TIMESTAMP('2023-07-26 13:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543527
;

-- Reference Item: Order Status -> VO_Voided
-- 2023-07-26T10:23:53.373Z
UPDATE AD_Ref_List_Trl SET Name='Storniert',Updated=TO_TIMESTAMP('2023-07-26 13:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543528
;

-- Reference Item: Order Status -> VO_Voided
-- 2023-07-26T10:23:56.218Z
UPDATE AD_Ref_List_Trl SET Name='Storniert',Updated=TO_TIMESTAMP('2023-07-26 13:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543528
;

-- Reference Item: Order Status -> VO_Voided
-- 2023-07-26T10:23:59.843Z
UPDATE AD_Ref_List_Trl SET Name='Storniert',Updated=TO_TIMESTAMP('2023-07-26 13:23:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543528
;

-- Reference Item: Order Status -> FD_Fully Delivered
-- 2023-07-26T10:26:51.578Z
UPDATE AD_Ref_List_Trl SET Name='Vollständig ausgeliefert',Updated=TO_TIMESTAMP('2023-07-26 13:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543530
;

-- Reference Item: Order Status -> FD_Fully Delivered
-- 2023-07-26T10:26:54.908Z
UPDATE AD_Ref_List_Trl SET Name='Vollständig ausgeliefert',Updated=TO_TIMESTAMP('2023-07-26 13:26:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543530
;

-- Reference Item: Order Status -> FD_Fully Delivered
-- 2023-07-26T10:26:58.409Z
UPDATE AD_Ref_List_Trl SET Name='Vollständig ausgeliefert',Updated=TO_TIMESTAMP('2023-07-26 13:26:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543530
;

-- Column: C_Order.OrderStatus
-- 2023-07-26T10:27:22.658Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587172,581683,0,17,541809,259,'XX','OrderStatus',TO_TIMESTAMP('2023-07-26 13:27:22','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Order Status',0,0,TO_TIMESTAMP('2023-07-26 13:27:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-26T10:27:22.664Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587172 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-26T10:27:24.069Z
/* DDL */  select update_Column_Translation_From_AD_Element(581683) 
;

-- Column: C_Order.OrderStatus
-- 2023-07-26T16:10:46.342Z
UPDATE AD_Column SET ColumnSQL='( CASE  WHEN C_Order.DocStatus = ''VO'' THEN ''VO'' WHEN C_Order.DocStatus = ''CO'' AND C_Order.QtyOrdered > C_Order.QtyMoved THEN ''CO'' WHEN C_Order.QtyOrdered = C_Order.QtyMoved THEN ''FD'' ELSE ''DR''  END )', IsLazyLoading='Y', IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-07-26 19:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;

-- Column: C_Order.OrderStatus
-- Column SQL (old): ( CASE  WHEN C_Order.DocStatus = 'VO' THEN 'VO' WHEN C_Order.DocStatus = 'CO' AND C_Order.QtyOrdered > C_Order.QtyMoved THEN 'CO' WHEN C_Order.QtyOrdered = C_Order.QtyMoved THEN 'FD' ELSE 'DR'  END )
-- 2023-07-26T16:19:24.972Z
UPDATE AD_Column SET ColumnSQL='( CASE  WHEN C_Order.DocStatus = ''IP'' THEN ''IP'' WHEN C_Order.DocStatus = ''VO'' THEN ''VO'' WHEN C_Order.DocStatus = ''CO'' AND C_Order.QtyOrdered > C_Order.QtyMoved THEN ''CO'' WHEN C_Order.QtyOrdered = C_Order.QtyMoved THEN ''FD'' ELSE ''DR''  END )',Updated=TO_TIMESTAMP('2023-07-26 19:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;

-- Column: C_Order.InvoiceStatus
-- Column SQL (old): ( CASE WHEN C_Order.QtyOrdered <= C_Order.QtyInvoiced AND C_Order.QtyOrdered > 0 THEN 'CI' WHEN C_Order.QtyOrdered > C_Order.QtyInvoiced AND C_Order.QtyInvoiced > 0 THEN 'PI' ELSE 'O' END )
-- 2023-07-27T07:49:42.910Z
UPDATE AD_Column SET ColumnSQL='( CASE  WHEN C_Order.QtyMoved <= C_Order.QtyInvoiced AND C_Order.QtyMoved > 0 THEN ''CI''  WHEN C_Order.QtyMoved > C_Order.QtyInvoiced AND C_Order.QtyInvoiced > 0 THEN ''PI''  ELSE ''O''  END )',Updated=TO_TIMESTAMP('2023-07-27 10:49:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552695
;


-- Column: C_Order.OrderStatus
-- 2023-07-27T07:55:30.943Z
UPDATE AD_Column SET FilterDefaultValue='Y', FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-07-27 10:55:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;

-- Column: C_Order.OrderStatus
-- 2023-07-27T07:56:24.406Z
UPDATE AD_Column SET FilterDefaultValue='CD',Updated=TO_TIMESTAMP('2023-07-27 10:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;

-- Column: C_Order.InvoiceStatus
-- 2023-07-27T07:57:17.964Z
UPDATE AD_Column SET FilterDefaultValue='CI',Updated=TO_TIMESTAMP('2023-07-27 10:57:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552695
;

-- Reference: Order Status
-- Value: IP
-- ValueName: In Progress
-- 2023-07-27T07:50:38.436Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543531,541809,TO_TIMESTAMP('2023-07-27 10:50:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','In Progress',TO_TIMESTAMP('2023-07-27 10:50:37','YYYY-MM-DD HH24:MI:SS'),100,'IP','In Progress')
;

-- 2023-07-27T07:50:38.436Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543531 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Order Status -> IP_In Progress
-- 2023-07-27T07:51:38.767Z
UPDATE AD_Ref_List_Trl SET Name='In Verarbeitung',Updated=TO_TIMESTAMP('2023-07-27 10:51:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543531
;

-- Reference Item: Order Status -> IP_In Progress
-- 2023-07-27T07:51:42.787Z
UPDATE AD_Ref_List_Trl SET Name='In Verarbeitung',Updated=TO_TIMESTAMP('2023-07-27 10:51:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543531
;

-- Reference Item: Order Status -> IP_In Progress
-- 2023-07-27T07:51:47.236Z
UPDATE AD_Ref_List_Trl SET Name='In Verarbeitung',Updated=TO_TIMESTAMP('2023-07-27 10:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543531
;

-- Column: C_Order.OrderStatus
-- Column SQL (old): ( CASE  WHEN C_Order.DocStatus = 'IP' THEN 'IP' WHEN C_Order.DocStatus = 'VO' THEN 'VO' WHEN C_Order.DocStatus = 'CO' AND C_Order.QtyOrdered > C_Order.QtyMoved THEN 'CO' WHEN C_Order.QtyOrdered = C_Order.QtyMoved THEN 'FD' ELSE 'DR'  END )
-- 2023-07-27T13:15:05.109Z
UPDATE AD_Column SET ColumnSQL='( CASE  WHEN C_Order.DocStatus = ''IP'' THEN ''IP'' WHEN C_Order.DocStatus IN (''VO'',''RE'') THEN ''VO'' WHEN C_Order.DocStatus IN (''CO'',''CL'') AND C_Order.QtyOrdered > C_Order.QtyMoved THEN ''CO'' WHEN C_Order.QtyOrdered = C_Order.QtyMoved THEN ''FD'' ELSE ''DR''  END )',Updated=TO_TIMESTAMP('2023-07-27 16:15:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;

-- Column: C_Order.OrderStatus
-- Column SQL (old): ( CASE  WHEN C_Order.DocStatus = 'IP' THEN 'IP' WHEN C_Order.DocStatus IN ('VO','RE') THEN 'VO' WHEN C_Order.DocStatus IN ('CO','CL') AND C_Order.QtyOrdered > C_Order.QtyMoved THEN 'CO' WHEN C_Order.QtyOrdered = C_Order.QtyMoved THEN 'FD' ELSE 'DR'  END )
-- 2023-07-27T14:13:47.062Z
UPDATE AD_Column SET ColumnSQL='( CASE  WHEN C_Order.DocStatus = ''DR'' THEN ''DR'' WHEN C_Order.DocStatus = ''IP'' THEN ''IP'' WHEN C_Order.DocStatus IN (''VO'',''RE'') THEN ''VO'' WHEN C_Order.DocStatus IN (''CO'',''CL'') AND C_Order.QtyOrdered > C_Order.QtyMoved THEN ''CO'' WHEN C_Order.QtyOrdered = C_Order.QtyMoved THEN ''FD'' ELSE ''DR''  END )',Updated=TO_TIMESTAMP('2023-07-27 17:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;

-- Column: C_Order.OrderStatus
-- 2023-07-27T14:15:20.846Z
UPDATE AD_Column SET FilterDefaultValue='FD',Updated=TO_TIMESTAMP('2023-07-27 17:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;

-- Column: C_Order.OrderStatus
-- Column SQL (old): ( CASE  WHEN C_Order.DocStatus = 'DR' THEN 'DR' WHEN C_Order.DocStatus = 'IP' THEN 'IP' WHEN C_Order.DocStatus IN ('VO','RE') THEN 'VO' WHEN C_Order.DocStatus IN ('CO','CL') AND C_Order.QtyOrdered > C_Order.QtyMoved THEN 'CO' WHEN C_Order.QtyOrdered = C_Order.QtyMoved THEN 'FD' ELSE 'DR'  END )
-- 2023-07-27T14:52:26.213Z
UPDATE AD_Column SET ColumnSQL='( CASE  WHEN C_Order.DocStatus = ''DR'' THEN ''DR'' WHEN C_Order.DocStatus = ''IP'' THEN ''IP'' WHEN C_Order.DocStatus IN (''VO'',''RE'') THEN ''VO'' WHEN C_Order.DocStatus IN (''CO'',''CL'') AND (SELECT SUM(qtyordered - qtydelivered) from C_OrderLine where C_Order_ID = @C_Order_ID/-1@) > 0 THEN ''CO'' WHEN (SELECT SUM(qtyordered - qtydelivered) from C_OrderLine where C_Order_ID = @C_Order_ID/-1@) = 0 THEN ''FD'' ELSE ''DR''  END )',Updated=TO_TIMESTAMP('2023-07-27 17:52:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;

-- Column: C_Order.OrderStatus
-- Column SQL (old): ( CASE  WHEN C_Order.DocStatus = 'DR' THEN 'DR' WHEN C_Order.DocStatus = 'IP' THEN 'IP' WHEN C_Order.DocStatus IN ('VO','RE') THEN 'VO' WHEN C_Order.DocStatus IN ('CO','CL') AND (SELECT SUM(qtyordered - qtydelivered) from C_OrderLine where C_Order_ID = @C_Order_ID/-1@) > 0 THEN 'CO' WHEN (SELECT SUM(qtyordered - qtydelivered) from C_OrderLine where C_Order_ID = @C_Order_ID/-1@) = 0 THEN 'FD' ELSE 'DR'  END )
-- 2023-07-27T15:53:19.070Z
UPDATE AD_Column SET ColumnSQL='( CASE  WHEN C_Order.DocStatus = ''DR'' THEN ''DR'' WHEN C_Order.DocStatus = ''IP'' THEN ''IP'' WHEN C_Order.DocStatus IN (''VO'',''RE'') THEN ''VO'' WHEN C_Order.DocStatus IN (''CO'',''CL'') AND (SELECT SUM(qtyordered - qtydelivered) from C_OrderLine where C_Order_ID = @JoinTableNameOrAliasIncludingDot@c_order_id) > 0 THEN ''CO'' WHEN (SELECT SUM(qtyordered - qtydelivered) from C_OrderLine where C_Order_ID = @JoinTableNameOrAliasIncludingDot@c_order_id) = 0 THEN ''FD'' ELSE ''DR''  END )',Updated=TO_TIMESTAMP('2023-07-27 18:53:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587172
;