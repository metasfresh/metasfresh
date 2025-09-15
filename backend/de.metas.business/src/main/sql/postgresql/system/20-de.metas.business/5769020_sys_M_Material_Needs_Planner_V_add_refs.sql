-- Run mode: SWING_CLIENT

-- Name: M_Material_Needs_Planner_V
-- 2025-09-15T15:27:20.137Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541982,TO_TIMESTAMP('2025-09-15 15:27:17.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_Material_Needs_Planner_V',TO_TIMESTAMP('2025-09-15 15:27:17.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-09-15T15:27:20.139Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541982 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Material_Needs_Planner_V
-- Table: M_Material_Needs_Planner_V
-- Key: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-09-15T15:32:13.098Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,589739,0,541982,542466,TO_TIMESTAMP('2025-09-15 15:32:13.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-09-15 15:32:13.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: PP_Order target for M_Material_Needs_Planner_V
-- 2025-09-15T15:33:55.744Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541983,TO_TIMESTAMP('2025-09-15 15:33:55.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','PP_Order target for M_Material_Needs_Planner_V',TO_TIMESTAMP('2025-09-15 15:33:55.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-09-15T15:33:55.746Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541983 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PP_Order target for M_Material_Needs_Planner_V
-- Table: PP_Order
-- Key: PP_Order.PP_Order_ID
-- 2025-09-15T15:35:38.438Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,53659,0,541983,53027,TO_TIMESTAMP('2025-09-15 15:35:38.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-09-15 15:35:38.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS (SELECT 1               from pp_order o               where o.pp_order_id = o.pp_order_id                 AND o.m_product_id = @M_Product_ID/-1@                 AND o.m_warehouse_id = @M_Warehouse_ID/-1@)')
;

-- Name: M_Material_Needs_Planner_V -> PP_Order
-- Source Reference: M_Material_Needs_Planner_V
-- Target Reference: PP_Order target for M_Material_Needs_Planner_V
-- 2025-09-15T15:37:27.993Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541982,541983,540461,TO_TIMESTAMP('2025-09-15 15:37:27.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_Material_Needs_Planner_V -> PP_Order',TO_TIMESTAMP('2025-09-15 15:37:27.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_Material_Needs_Planner_V.M_Material_Needs_Planner_V_ID
-- 2025-09-15T16:19:41.949Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590905,583510,0,13,542466,'XX','M_Material_Needs_Planner_V_ID',TO_TIMESTAMP('2025-09-15 16:19:39.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'Materialbedarf',0,0,TO_TIMESTAMP('2025-09-15 16:19:39.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-15T16:19:41.953Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590905 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-15T16:19:41.989Z
/* DDL */  select update_Column_Translation_From_AD_Element(583510)
;

-- Reference: M_Material_Needs_Planner_V
-- Table: M_Material_Needs_Planner_V
-- Key: M_Material_Needs_Planner_V.M_Material_Needs_Planner_V_ID
-- 2025-09-15T16:23:56.835Z
UPDATE AD_Ref_Table SET AD_Key=590905,Updated=TO_TIMESTAMP('2025-09-15 16:23:56.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541982
;

-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-09-15T16:26:01.873Z
UPDATE AD_Column SET IsParent='N',Updated=TO_TIMESTAMP('2025-09-15 16:26:01.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589737
;

-- Column: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-09-15T16:26:03.917Z
UPDATE AD_Column SET IsParent='N',Updated=TO_TIMESTAMP('2025-09-15 16:26:03.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589739
;

-- Column: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-09-15T16:27:31.314Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2025-09-15 16:27:31.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589739
;

-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-09-15T16:27:44.973Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2025-09-15 16:27:44.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589737
;

-- Column: M_Material_Needs_Planner_V.M_Material_Needs_Planner_V_ID
-- 2025-09-15T17:01:54.699Z
UPDATE AD_Column SET IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-09-15 17:01:54.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590905
;

-- Name: C_Order(Purchase) target for M_Material_Needs_Planner_V
-- 2025-09-15T17:10:48.257Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541984,TO_TIMESTAMP('2025-09-15 17:10:46.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_Order(Purchase) target for M_Material_Needs_Planner_V',TO_TIMESTAMP('2025-09-15 17:10:46.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-09-15T17:10:48.260Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541984 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Order(Purchase) target for M_Material_Needs_Planner_V
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2025-09-15T17:11:49.625Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541984,259,TO_TIMESTAMP('2025-09-15 17:11:49.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-09-15 17:11:49.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS (SELECT 1               from c_order o               where o.c_order_id = c_order.c_order_id                 AND o.m_product_id = @M_Product_ID/-1@                 AND o.m_warehouse_id = @M_Warehouse_ID/-1@               AND o.issotrx=''N'')')
;

-- Name: C_Order(PO) target for M_Material_Needs_Planner_V
-- 2025-09-15T17:12:42.712Z
UPDATE AD_Reference SET Name='C_Order(PO) target for M_Material_Needs_Planner_V',Updated=TO_TIMESTAMP('2025-09-15 17:12:42.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541984
;

-- 2025-09-15T17:12:42.713Z
UPDATE AD_Reference_Trl trl SET Name='C_Order(PO) target for M_Material_Needs_Planner_V' WHERE AD_Reference_ID=541984 AND AD_Language='de_DE'
;

-- Name: M_Material_Needs_Planner_V ->
-- Source Reference: M_Material_Needs_Planner_V
-- Target Reference: C_Order(PO) target for M_Material_Needs_Planner_V
-- 2025-09-15T17:12:49.257Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541982,541984,540462,TO_TIMESTAMP('2025-09-15 17:12:47.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_Material_Needs_Planner_V -> ',TO_TIMESTAMP('2025-09-15 17:12:47.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_Material_Needs_Planner_V -> C_Order (PO)
-- Source Reference: M_Material_Needs_Planner_V
-- Target Reference: C_Order(PO) target for M_Material_Needs_Planner_V
-- 2025-09-15T17:13:02.784Z
UPDATE AD_RelationType SET Name='M_Material_Needs_Planner_V -> C_Order (PO)',Updated=TO_TIMESTAMP('2025-09-15 17:13:02.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_RelationType_ID=540462
;

-- Reference: C_Order(PO) target for M_Material_Needs_Planner_V
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2025-09-15T17:14:34.491Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1               from c_order o                        INNER JOIN c_orderline ol on o.c_order_id = ol.c_order_id               where ol.c_order_id = c_order.c_order_id                 AND ol.m_product_id = @M_Product_ID / -1@                 AND ol.m_warehouse_id = @M_Warehouse_ID / -1@                 AND o.issotrx = ''N'')',Updated=TO_TIMESTAMP('2025-09-15 17:14:34.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541984
;

-- Reference: C_Order(PO) target for M_Material_Needs_Planner_V
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2025-09-15T17:16:04.771Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1               from c_order o                        INNER JOIN c_orderline ol on o.c_order_id = ol.c_order_id               where ol.c_order_id = c_order.c_order_id                 AND ol.m_product_id = @M_Product_ID/-1@                 AND ol.m_warehouse_id = @M_Warehouse_ID/-1@                 and ol.m_hu_pi_item_product_id = @M_HU_PI_Item_Product_ID/-1@                 AND o.issotrx = ''N'')',Updated=TO_TIMESTAMP('2025-09-15 17:16:04.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541984
;

-- Reference: C_Order(PO) target for M_Material_Needs_Planner_V
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2025-09-15T17:18:32.888Z
UPDATE AD_Ref_Table SET AD_Window_ID=540375,Updated=TO_TIMESTAMP('2025-09-15 17:18:32.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541984
;

-- Name: C_OLCand(PO) target for M_Material_Needs_Planner_V
-- 2025-09-15T17:18:42.627Z
UPDATE AD_Reference SET Name='C_OLCand(PO) target for M_Material_Needs_Planner_V',Updated=TO_TIMESTAMP('2025-09-15 17:18:42.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541984
;

-- 2025-09-15T17:18:42.628Z
UPDATE AD_Reference_Trl trl SET Name='C_OLCand(PO) target for M_Material_Needs_Planner_V' WHERE AD_Reference_ID=541984 AND AD_Language='de_DE'
;

-- Name: C_PurchaseCandidate target for M_Material_Needs_Planner_V
-- 2025-09-15T17:24:46.681Z
UPDATE AD_Reference SET Name='C_PurchaseCandidate target for M_Material_Needs_Planner_V',Updated=TO_TIMESTAMP('2025-09-15 17:24:46.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541984
;

-- 2025-09-15T17:24:46.682Z
UPDATE AD_Reference_Trl trl SET Name='C_PurchaseCandidate target for M_Material_Needs_Planner_V' WHERE AD_Reference_ID=541984 AND AD_Language='de_DE'
;

-- Reference: C_PurchaseCandidate target for M_Material_Needs_Planner_V
-- Table: C_PurchaseCandidate
-- Key: C_PurchaseCandidate.C_PurchaseCandidate_ID
-- 2025-09-15T17:25:10.379Z
UPDATE AD_Ref_Table SET AD_Key=557857, AD_Table_ID=540861, AD_Window_ID=NULL, WhereClause='EXISTS (SELECT 1               from C_PurchaseCandidate o               where o.c_purchasecandidate_id = C_PurchaseCandidate.c_purchasecandidate_id                 AND o.m_product_id = @M_Product_ID/-1@                 AND o.m_warehousepo_id = @M_Warehouse_ID/-1@)',Updated=TO_TIMESTAMP('2025-09-15 17:25:10.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541984
;

-- Name: M_Material_Needs_Planner_V -> C_PurchaseCandidate
-- Source Reference: M_Material_Needs_Planner_V
-- Target Reference: C_PurchaseCandidate target for M_Material_Needs_Planner_V
-- 2025-09-15T17:26:01.546Z
UPDATE AD_RelationType SET Name='M_Material_Needs_Planner_V -> C_PurchaseCandidate',Updated=TO_TIMESTAMP('2025-09-15 17:26:01.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_RelationType_ID=540462
;

-- 2025-09-15T17:26:24.786Z
UPDATE AD_Reference_Trl SET Name='C_PurchaseCandidate target for M_Material_Needs_Planner_V',Updated=TO_TIMESTAMP('2025-09-15 17:26:24.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541984
;

-- 2025-09-15T17:26:24.788Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-15T17:26:25.867Z
UPDATE AD_Reference_Trl SET Name='C_PurchaseCandidate target for M_Material_Needs_Planner_V',Updated=TO_TIMESTAMP('2025-09-15 17:26:25.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=541984
;

-- 2025-09-15T17:26:25.869Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-15T17:26:30.185Z
UPDATE AD_Reference_Trl SET Name='C_PurchaseCandidate target for M_Material_Needs_Planner_V',Updated=TO_TIMESTAMP('2025-09-15 17:26:30.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541984
;

-- 2025-09-15T17:26:30.186Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Name: M_Material_Needs_Planner_V -> PP_Order_Candidate
-- Source Reference: M_Material_Needs_Planner_V
-- Target Reference: PP_Order target for M_Material_Needs_Planner_V
-- 2025-09-15T17:28:21.758Z
UPDATE AD_RelationType SET Name='M_Material_Needs_Planner_V -> PP_Order_Candidate',Updated=TO_TIMESTAMP('2025-09-15 17:28:21.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_RelationType_ID=540461
;

-- Name: PP_Order_Candidate target for M_Material_Needs_Planner_V
-- 2025-09-15T17:28:31.158Z
UPDATE AD_Reference SET Name='PP_Order_Candidate target for M_Material_Needs_Planner_V',Updated=TO_TIMESTAMP('2025-09-15 17:28:31.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541983
;

-- 2025-09-15T17:28:31.159Z
UPDATE AD_Reference_Trl trl SET Name='PP_Order_Candidate target for M_Material_Needs_Planner_V' WHERE AD_Reference_ID=541983 AND AD_Language='de_DE'
;

-- 2025-09-15T17:28:41.450Z
UPDATE AD_Reference_Trl SET Name='PP_Order_Candidate target for M_Material_Needs_Planner_V',Updated=TO_TIMESTAMP('2025-09-15 17:28:41.447000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541983
;

-- 2025-09-15T17:28:41.451Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-15T17:28:42.700Z
UPDATE AD_Reference_Trl SET Name='PP_Order_Candidate target for M_Material_Needs_Planner_V',Updated=TO_TIMESTAMP('2025-09-15 17:28:42.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=541983
;

-- 2025-09-15T17:28:42.701Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-15T17:28:44.280Z
UPDATE AD_Reference_Trl SET Name='PP_Order_Candidate target for M_Material_Needs_Planner_V',Updated=TO_TIMESTAMP('2025-09-15 17:28:44.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541983
;

-- 2025-09-15T17:28:44.281Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: PP_Order_Candidate target for M_Material_Needs_Planner_V
-- Table: PP_Order
-- Key: PP_Order.PP_Order_ID
-- 2025-09-15T17:28:54.940Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1               from pp_order_candidate o               where o.pp_order_candidate_id = o.pp_order_candidate_id                 AND o.m_product_id = @M_Product_ID/-1@                 AND o.m_warehouse_id = @M_Warehouse_ID/-1@                 AND o.m_hu_pi_item_product_id = @M_HU_PI_Item_Product_ID/-1@)',Updated=TO_TIMESTAMP('2025-09-15 17:28:54.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541983
;

-- Reference: PP_Order_Candidate target for M_Material_Needs_Planner_V
-- Table: PP_Order_Candidate
-- Key: PP_Order_Candidate.PP_Order_Candidate_ID
-- 2025-09-15T17:30:03.687Z
UPDATE AD_Ref_Table SET AD_Key=577875, AD_Table_ID=541913,Updated=TO_TIMESTAMP('2025-09-15 17:30:03.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541983
;

