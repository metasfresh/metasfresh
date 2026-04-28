-- Run mode: SWING_CLIENT

-- Name: QtyDemand_QtySupply_V
-- 2025-10-23T18:18:31.916Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542004,TO_TIMESTAMP('2025-10-23 18:18:31.640000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','QtyDemand_QtySupply_V',TO_TIMESTAMP('2025-10-23 18:18:31.640000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-10-23T18:18:31.922Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542004 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: QtyDemand_QtySupply_V
-- Table: QtyDemand_QtySupply_V
-- Key: QtyDemand_QtySupply_V.QtyDemand_QtySupply_V_ID
-- 2025-10-23T18:18:55.616Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,591429,0,542004,542218,TO_TIMESTAMP('2025-10-23 18:18:55.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-10-23 18:18:55.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_ShipmentSchedule target for QtyDemand_QtySupply_V
-- 2025-10-23T18:19:45.764Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542005,TO_TIMESTAMP('2025-10-23 18:19:45.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_ShipmentSchedule target for QtyDemand_QtySupply_V',TO_TIMESTAMP('2025-10-23 18:19:45.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-10-23T18:19:45.765Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542005 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_ShipmentSchedule target for QtyDemand_QtySupply_V
-- Table: M_ReceiptSchedule
-- Key: M_ShipmentSchedule.M_ReceiptSchedule_ID
-- 2025-10-23T18:24:50.639Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,549487,0,542005,540524,TO_TIMESTAMP('2025-10-23 18:24:50.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-10-23 18:24:50.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS (SELECT 1               from M_ShipmentSchedule ss               where ss.M_ShipmentSchedule_ID = M_ShipmentSchedule.M_ShipmentSchedule_ID     AND ((IsActive = ''Y'')         AND (M_Product_ID = @M_Product_ID/-1@)         AND (M_Warehouse_ID = @M_Warehouse_ID/-1@)         AND (AD_Org_ID = @AD_Org_ID/-1@)         AND (generateASIStorageAttributesKey(M_AttributeSetInstance_ID) = @AttributesKey/''''@)         AND ((QtyReserved <> 0 OR QtyReserved IS NULL))))')
;

-- Name: QtyDemand_QtySupply_V_to_Shipment_Schedule
-- Source Reference: QtyDemand_QtySupply_V
-- Target Reference: M_ShipmentSchedule target for QtyDemand_QtySupply_V
-- 2025-10-23T18:25:17.725Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,542004,542005,540464,TO_TIMESTAMP('2025-10-23 18:25:17.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','QtyDemand_QtySupply_V_to_Shipment_Schedule',TO_TIMESTAMP('2025-10-23 18:25:17.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: QtyDemand_QtySupply_V.M_Product_ID
-- 2025-10-23T18:28:54.574Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2025-10-23 18:28:54.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=584383
;

-- Column: QtyDemand_QtySupply_V.M_Warehouse_ID
-- 2025-10-23T18:29:05.860Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2025-10-23 18:29:05.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=584386
;

-- Column: QtyDemand_QtySupply_V.AttributesKey
-- 2025-10-23T18:29:18.909Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=2,Updated=TO_TIMESTAMP('2025-10-23 18:29:18.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=584385
;

-- Column: QtyDemand_QtySupply_V.QtyDemand_QtySupply_V_ID
-- 2025-10-23T18:55:02.021Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-10-23 18:55:02.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591429
;

-- Name: QtyDemand_QtySupply_V ->M_ReceiptSchedule
-- 2025-10-23T18:57:05.131Z
UPDATE AD_Reference SET Name='QtyDemand_QtySupply_V ->M_ReceiptSchedule ',Updated=TO_TIMESTAMP('2025-10-23 18:57:05.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542005
;

-- 2025-10-23T18:57:05.132Z
UPDATE AD_Reference_Trl trl SET Name='QtyDemand_QtySupply_V ->M_ReceiptSchedule ' WHERE AD_Reference_ID=542005 AND AD_Language='de_DE'
;

-- Name: M_ShipmentSchedule target for QtyDemand_QtySupply_V
-- 2025-10-23T18:57:25.496Z
UPDATE AD_Reference SET Name='M_ShipmentSchedule target for QtyDemand_QtySupply_V',Updated=TO_TIMESTAMP('2025-10-23 18:57:25.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542005
;

-- 2025-10-23T18:57:25.497Z
UPDATE AD_Reference_Trl trl SET Name='M_ShipmentSchedule target for QtyDemand_QtySupply_V' WHERE AD_Reference_ID=542005 AND AD_Language='de_DE'
;

-- Reference: M_ShipmentSchedule target for QtyDemand_QtySupply_V
-- Table: M_ShipmentSchedule
-- Key: M_ShipmentSchedule.M_ShipmentSchedule_ID
-- 2025-10-23T18:58:01.904Z
UPDATE AD_Ref_Table SET AD_Key=500232, AD_Table_ID=500221,Updated=TO_TIMESTAMP('2025-10-23 18:58:01.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542005
;

-- Reference: M_ShipmentSchedule target for QtyDemand_QtySupply_V
-- Table: M_ShipmentSchedule
-- Key: M_ShipmentSchedule.M_ShipmentSchedule_ID
-- 2025-10-23T18:59:09.470Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1               from M_ShipmentSchedule ss               where ss.M_ShipmentSchedule_ID = M_ShipmentSchedule.M_ShipmentSchedule_ID     AND ((IsActive = ''Y'')         AND (M_Product_ID = @M_Product_ID/-1@)         AND (M_Warehouse_ID = @M_Warehouse_ID/-1@)         AND (AD_Org_ID = @AD_Org_ID/-1@)         AND (generateASIStorageAttributesKey(M_AttributeSetInstance_ID) = ''@AttributesKey/@'')         AND ((QtyReserved <> 0 OR QtyReserved IS NULL))))',Updated=TO_TIMESTAMP('2025-10-23 18:59:09.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542005
;

-- Reference: M_ShipmentSchedule target for QtyDemand_QtySupply_V
-- Table: M_ShipmentSchedule
-- Key: M_ShipmentSchedule.M_ShipmentSchedule_ID
-- 2025-10-23T18:59:16.310Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1               from M_ShipmentSchedule ss               where ss.M_ShipmentSchedule_ID = M_ShipmentSchedule.M_ShipmentSchedule_ID     AND ((IsActive = ''Y'')         AND (M_Product_ID = @M_Product_ID/-1@)         AND (M_Warehouse_ID = @M_Warehouse_ID/-1@)         AND (AD_Org_ID = @AD_Org_ID/-1@)         AND (generateASIStorageAttributesKey(M_AttributeSetInstance_ID) = ''@AttributesKey@'')         AND ((QtyReserved <> 0 OR QtyReserved IS NULL))))',Updated=TO_TIMESTAMP('2025-10-23 18:59:16.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542005
;

-- Tab: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply
-- Table: QtyDemand_QtySupply_V
-- 2025-10-23T19:03:39.331Z
UPDATE AD_Tab SET IsGenericZoomTarget='N',Updated=TO_TIMESTAMP('2025-10-23 19:03:39.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548476
;

-- Name: M_ShipmentSchedule target for QtyDemand_QtySupply_V
-- 2025-10-23T19:07:43.630Z
UPDATE AD_Reference SET Name='M_ShipmentSchedule target for QtyDemand_QtySupply_V',Updated=TO_TIMESTAMP('2025-10-23 19:07:43.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542005
;

-- 2025-10-23T19:07:43.632Z
UPDATE AD_Reference_Trl trl SET Name='M_ShipmentSchedule target for QtyDemand_QtySupply_V' WHERE AD_Reference_ID=542005 AND AD_Language='de_DE'
;

-- 2025-10-24T08:31:44.749Z
UPDATE AD_Reference_Trl SET Name='M_ShipmentSchedule target for QtyDemand_QtySupply_V',Updated=TO_TIMESTAMP('2025-10-24 08:31:44.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=542005
;

-- 2025-10-24T08:31:44.758Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-24T08:31:46.053Z
UPDATE AD_Reference_Trl SET Name='M_ShipmentSchedule target for QtyDemand_QtySupply_V',Updated=TO_TIMESTAMP('2025-10-24 08:31:46.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=542005
;

-- 2025-10-24T08:31:46.055Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-24T08:31:48.202Z
UPDATE AD_Reference_Trl SET Name='M_ShipmentSchedule target for QtyDemand_QtySupply_V',Updated=TO_TIMESTAMP('2025-10-24 08:31:48.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=542005
;

-- 2025-10-24T08:31:48.203Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Name: M_ReceiptSchedule target for QtyDemand_QtySupply_V
-- 2025-10-24T08:38:55.272Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542008,TO_TIMESTAMP('2025-10-24 08:38:55.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_ReceiptSchedule target for QtyDemand_QtySupply_V',TO_TIMESTAMP('2025-10-24 08:38:55.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-10-24T08:38:55.278Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542008 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_ReceiptSchedule target for QtyDemand_QtySupply_V
-- Table: M_ReceiptSchedule
-- Key: M_ReceiptSchedule.M_ReceiptSchedule_ID
-- 2025-10-24T08:40:46.151Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,549487,0,542008,540524,TO_TIMESTAMP('2025-10-24 08:40:46.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-10-24 08:40:46.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS (SELECT 1               from M_ReceiptSchedule rs               where rs.M_ReceiptSchedule_ID = M_ReceiptSchedule.M_ReceiptSchedule_ID                 AND ((IsActive = ''Y'')                   AND (rs.M_Product_ID = @M_Product_ID/-1@)                   AND (rs.M_Warehouse_ID = @M_Warehouse_ID/-1@)                   AND (rs.AD_Org_ID = @AD_Org_ID/-1@)                   AND (generateASIStorageAttributesKey(rs.M_AttributeSetInstance_ID) = ''@AttributesKey@'')                   AND ((rs.QtyToMove <> 0 OR rs.QtyToMove IS NULL))))')
;

-- Name: QtyDemand_QtySupply_V_to_Receipt_Schedule
-- Source Reference: QtyDemand_QtySupply_V
-- Target Reference: M_ReceiptSchedule target for QtyDemand_QtySupply_V
-- 2025-10-24T08:44:15.339Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,542004,542008,540465,TO_TIMESTAMP('2025-10-24 08:44:15.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','QtyDemand_QtySupply_V_to_Receipt_Schedule',TO_TIMESTAMP('2025-10-24 08:44:15.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_Forecast target for QtyDemand_QtySupply_V
-- 2025-10-24T08:44:52.514Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542009,TO_TIMESTAMP('2025-10-24 08:44:52.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_Forecast target for QtyDemand_QtySupply_V',TO_TIMESTAMP('2025-10-24 08:44:52.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-10-24T08:44:52.516Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542009 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Forecast target for QtyDemand_QtySupply_V
-- Table: M_Forecast
-- Key: M_Forecast.M_Forecast_ID
-- 2025-10-24T08:47:11.245Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,11918,0,542009,720,TO_TIMESTAMP('2025-10-24 08:47:11.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-10-24 08:47:11.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS (SELECT 1               from M_Forecast f               INNER JOIN M_ForecastLine fl on f.m_forecast_id = fl.m_forecast_id               where f.M_Forecast_ID = M_Forecast.M_Forecast_ID                 AND ((f.IsActive = ''Y'')                   AND (fl.M_Product_ID = @M_Product_ID/-1@)                   AND (COALESCE(fl.M_Warehouse_ID, f.m_warehouse_id) = @M_Warehouse_ID/-1@)                   AND (f.AD_Org_ID = @AD_Org_ID/-1@)                   AND (generateASIStorageAttributesKey(fl.M_AttributeSetInstance_ID) = ''@AttributesKey@'')                   AND ((fl.Qty <> 0 OR fl.Qty IS NULL))))')
;

-- Name: QtyDemand_QtySupply_V_to_M_Forecast
-- Source Reference: QtyDemand_QtySupply_V
-- Target Reference: M_Forecast target for QtyDemand_QtySupply_V
-- 2025-10-24T08:47:54.055Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,542004,542009,540466,TO_TIMESTAMP('2025-10-24 08:47:53.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','QtyDemand_QtySupply_V_to_M_Forecast',TO_TIMESTAMP('2025-10-24 08:47:53.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: PP_Order_Candidate target for QtyDemand_QtySupply_V
-- 2025-10-24T08:55:04.046Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542010,TO_TIMESTAMP('2025-10-24 08:55:03.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','PP_Order_Candidate target for QtyDemand_QtySupply_V',TO_TIMESTAMP('2025-10-24 08:55:03.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-10-24T08:55:04.048Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542010 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PP_Order_Candidate target for QtyDemand_QtySupply_V
-- Table: PP_Order_Candidate
-- Key: PP_Order_Candidate.PP_Order_Candidate_ID
-- 2025-10-24T08:55:28.909Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,577875,0,542010,541913,TO_TIMESTAMP('2025-10-24 08:55:28.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-10-24 08:55:28.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS (SELECT 1               from PP_Order_Candidate pp               where pp.PP_Order_Candidate_ID = PP_Order_Candidate.PP_Order_Candidate_ID                 AND ((pp.IsActive = ''Y'')                   AND (pp.M_Product_ID = @M_Product_ID/-1@)                   AND (pp.m_warehouse_id) = @M_Warehouse_ID/-1@                   AND (pp.AD_Org_ID = @AD_Org_ID/-1@)                   AND (generateASIStorageAttributesKey(pp.M_AttributeSetInstance_ID) = ''@AttributesKey@'')                   AND ((pp.QtyToProcess <> 0 OR pp.QtyToProcess IS NULL))))')
;

-- Element: QtyToProduce
-- 2025-10-24T09:14:21.316Z
UPDATE AD_Element_Trl SET Name='Production - pending', PrintName='Production - pending',Updated=TO_TIMESTAMP('2025-10-24 09:14:21.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584140 AND AD_Language='en_US'
;

-- 2025-10-24T09:14:21.317Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-24T09:14:21.624Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584140,'en_US')
;

-- Element: QtyForecasted
-- 2025-10-24T09:14:45.800Z
UPDATE AD_Element_Trl SET Name='Forecast - pending', PrintName='Forecast - pending',Updated=TO_TIMESTAMP('2025-10-24 09:14:45.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584141 AND AD_Language='en_US'
;

-- 2025-10-24T09:14:45.802Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-24T09:14:46.077Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584141,'en_US')
;

-- Name: QtyDemand_QtySupply_V_to_PP_Order_Candidate
-- Source Reference: QtyDemand_QtySupply_V
-- Target Reference: PP_Order_Candidate target for QtyDemand_QtySupply_V
-- 2025-10-24T09:26:02.147Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,542004,542010,540467,TO_TIMESTAMP('2025-10-24 09:26:01.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y','N','QtyDemand_QtySupply_V_to_PP_Order_Candidate',TO_TIMESTAMP('2025-10-24 09:26:01.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: QtyDemand_QtySupply_V_to_PP_Order_Candidate
-- Source Reference: QtyDemand_QtySupply_V
-- Target Reference: PP_Order_Candidate target for QtyDemand_QtySupply_V
-- 2025-10-24T09:26:07.706Z
UPDATE AD_RelationType SET EntityType='D',Updated=TO_TIMESTAMP('2025-10-24 09:26:07.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_RelationType_ID=540467
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.AttributesKey (technical)
-- Column: QtyDemand_QtySupply_V.AttributesKey
-- 2025-10-24T09:34:38.348Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-10-24 09:34:38.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637935
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Reservierte Menge
-- Column: QtyDemand_QtySupply_V.QtyReserved
-- 2025-10-24T09:34:38.357Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-24 09:34:38.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637938
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestellt - offen
-- Column: QtyDemand_QtySupply_V.QtyToMove
-- 2025-10-24T09:34:38.365Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-24 09:34:38.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637939
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Produktion - offen
-- Column: QtyDemand_QtySupply_V.QtyToProduce
-- 2025-10-24T09:34:38.373Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-10-24 09:34:38.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637940
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Prognose - offen
-- Column: QtyDemand_QtySupply_V.QtyForecasted
-- 2025-10-24T09:34:38.381Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-10-24 09:34:38.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637941
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestand
-- Column: QtyDemand_QtySupply_V.QtyStock
-- 2025-10-24T09:34:38.389Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-10-24 09:34:38.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637942
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> org.Sektion
-- Column: QtyDemand_QtySupply_V.AD_Org_ID
-- 2025-10-24T09:34:38.397Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-10-24 09:34:38.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637937
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Produkt
-- Column: QtyDemand_QtySupply_V.M_Product_ID
-- 2025-10-24T09:42:21.988Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755070,0,548476,553650,637948,'F',TO_TIMESTAMP('2025-10-24 09:42:21.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',60,0,0,TO_TIMESTAMP('2025-10-24 09:42:21.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Produkt
-- Column: QtyDemand_QtySupply_V.M_Product_ID
-- 2025-10-24T09:42:34.216Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2025-10-24 09:42:34.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637948
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Produkt
-- Column: QtyDemand_QtySupply_V.M_Product_ID
-- 2025-10-24T09:46:13.838Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2025-10-24 09:46:13.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637948
;

-- Column: QtyDemand_QtySupply_V.ProductName
-- 2025-10-24T09:48:31.630Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2025-10-24 09:48:31.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591430
;

-- Column: QtyDemand_QtySupply_V.M_Product_Category_ID
-- 2025-10-24T10:16:24.747Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591436,453,0,19,542218,'XX','M_Product_Category_ID',TO_TIMESTAMP('2025-10-24 10:16:24.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Kategorie eines Produktes','de.metas.material.cockpit',0,10,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Produkt Kategorie',0,0,TO_TIMESTAMP('2025-10-24 10:16:24.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-24T10:16:24.751Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591436 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-24T10:16:24.755Z
/* DDL */  select update_Column_Translation_From_AD_Element(453)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Produkt Kategorie
-- Column: QtyDemand_QtySupply_V.M_Product_Category_ID
-- 2025-10-24T10:17:20.909Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591436,755084,0,548476,0,TO_TIMESTAMP('2025-10-24 10:17:20.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes',0,'U',0,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Produkt Kategorie',0,0,10,0,1,1,TO_TIMESTAMP('2025-10-24 10:17:20.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-24T10:17:20.912Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-24T10:17:20.914Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453)
;

-- 2025-10-24T10:17:20.942Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755084
;

-- 2025-10-24T10:17:20.945Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755084)
;

-- Column: QtyDemand_QtySupply_V.M_Product_Category_ID
-- 2025-10-24T10:17:35.222Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-24 10:17:35.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591436
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Produkt
-- Column: QtyDemand_QtySupply_V.M_Product_ID
-- 2025-10-24T10:18:16.391Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2025-10-24 10:18:16.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637948
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Maßeinheit
-- Column: QtyDemand_QtySupply_V.C_UOM_ID
-- 2025-10-24T10:18:21.169Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2025-10-24 10:18:21.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637934
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.AttributesKey (technical)
-- Column: QtyDemand_QtySupply_V.AttributesKey
-- 2025-10-24T10:18:24.649Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2025-10-24 10:18:24.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637935
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Lager
-- Column: QtyDemand_QtySupply_V.M_Warehouse_ID
-- 2025-10-24T10:18:29.947Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2025-10-24 10:18:29.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637936
;

/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Produkt Kategorie
-- Column: QtyDemand_QtySupply_V.M_Product_Category_ID
-- 2025-10-24T10:18:45.722Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755084,0,548476,553650,637949,'F',TO_TIMESTAMP('2025-10-24 10:18:45.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','Y','N','N','N',0,'Produkt Kategorie',40,0,0,TO_TIMESTAMP('2025-10-24 10:18:45.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: QtyDemand_QtySupply_V.M_Product_Category_ID
-- 2025-10-24T10:20:27.629Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540153, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-10-24 10:20:27.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591436
;

