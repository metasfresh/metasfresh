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

-- Name: M_ReceiptSchedule target for QtyDemand_QtySupply_V
-- 2025-10-23T18:19:45.764Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542005,TO_TIMESTAMP('2025-10-23 18:19:45.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_ReceiptSchedule target for QtyDemand_QtySupply_V',TO_TIMESTAMP('2025-10-23 18:19:45.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-10-23T18:19:45.765Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542005 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_ReceiptSchedule target for QtyDemand_QtySupply_V
-- Table: M_ReceiptSchedule
-- Key: M_ReceiptSchedule.M_ReceiptSchedule_ID
-- 2025-10-23T18:24:50.639Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,549487,0,542005,540524,TO_TIMESTAMP('2025-10-23 18:24:50.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-10-23 18:24:50.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS (SELECT 1               from M_ShipmentSchedule ss               where ss.M_ShipmentSchedule_ID = M_ShipmentSchedule.M_ShipmentSchedule_ID     AND ((IsActive = ''Y'')         AND (M_Product_ID = @M_Product_ID/-1@)         AND (M_Warehouse_ID = @M_Warehouse_ID/-1@)         AND (AD_Org_ID = @AD_Org_ID/-1@)         AND (generateASIStorageAttributesKey(M_AttributeSetInstance_ID) = @AttributesKey/''''@)         AND ((QtyReserved <> 0 OR QtyReserved IS NULL))))')
;

-- Name: QtyDemand_QtySupply_V_to_Shipment_Schedule
-- Source Reference: QtyDemand_QtySupply_V
-- Target Reference: M_ReceiptSchedule target for QtyDemand_QtySupply_V
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

