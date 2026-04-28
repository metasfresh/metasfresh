-- Run mode: SWING_CLIENT

-- Name: DHL_ShipmentOrder
-- 2025-04-22T10:23:03.030Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541940,TO_TIMESTAMP('2025-04-22 10:23:02.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','DHL_ShipmentOrder',TO_TIMESTAMP('2025-04-22 10:23:02.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-04-22T10:23:03.032Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541940 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: DHL_ShipmentOrder
-- Table: DHL_ShipmentOrder
-- Key: DHL_ShipmentOrder.DHL_ShipmentOrder_ID
-- 2025-04-22T10:24:09.428Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,569086,0,541940,541419,540743,TO_TIMESTAMP('2025-04-22 10:24:09.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-04-22 10:24:09.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: DHL_ShipmentOrder_Log target for DHL_ShipmentOrder
-- 2025-04-22T10:25:51.073Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541941,TO_TIMESTAMP('2025-04-22 10:25:50.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','DHL_ShipmentOrder_Log target for DHL_ShipmentOrder',TO_TIMESTAMP('2025-04-22 10:25:50.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-04-22T10:25:51.075Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541941 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: DHL_ShipmentOrder_Log target for DHL_ShipmentOrder
-- Table: Dhl_ShipmentOrder_Log
-- Key: Dhl_ShipmentOrder_Log.Dhl_ShipmentOrder_Log_ID
-- 2025-04-22T10:30:18.166Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,569235,0,541941,541426,TO_TIMESTAMP('2025-04-22 10:30:18.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-04-22 10:30:18.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS           (SELECT 1            from DHL_ShipmentOrder_Log log                     INNER JOIN DHL_ShipmentOrder so on log.DHL_ShipmentOrderRequest_ID = so.DHL_ShipmentOrderRequest_ID            where log.DHL_ShipmentOrder_Log_ID = Dhl_ShipmentOrder_Log.DHL_ShipmentOrder_Log_ID              AND so.DHL_ShipmentOrder_ID = @DHL_ShipmentOrder_ID/-1@ )')
;

-- Column: DHL_ShipmentOrder.awb
-- 2025-04-22T10:43:46.077Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2025-04-22 10:43:46.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569203
;

-- Column: Dhl_ShipmentOrder_Log.ConfigSummary
-- 2025-04-22T10:44:25.190Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2025-04-22 10:44:25.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569233
;

-- Column: Dhl_ShipmentOrder_Log.IsError
-- 2025-04-22T10:44:51.129Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2025-04-22 10:44:51.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569237
;

-- Name: DHL_ShipmentOrder -> DHL_ShipmentOrder_Log
-- Source Reference: DHL_ShipmentOrder
-- Target Reference: DHL_ShipmentOrder_Log target for DHL_ShipmentOrder
-- 2025-04-22T10:47:58.305Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541940,541941,540454,TO_TIMESTAMP('2025-04-22 10:47:58.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.shipper.gateway.dhl','Y','N','DHL_ShipmentOrder -> DHL_ShipmentOrder_Log',TO_TIMESTAMP('2025-04-22 10:47:58.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Reference: DHL_ShipmentOrder_Log target for DHL_ShipmentOrder
-- Table: Dhl_ShipmentOrder_Log
-- Key: Dhl_ShipmentOrder_Log.Dhl_ShipmentOrder_Log_ID
-- 2025-04-22T10:49:55.359Z
UPDATE AD_Ref_Table SET AD_Window_ID=541879,Updated=TO_TIMESTAMP('2025-04-22 10:49:55.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541941
;
