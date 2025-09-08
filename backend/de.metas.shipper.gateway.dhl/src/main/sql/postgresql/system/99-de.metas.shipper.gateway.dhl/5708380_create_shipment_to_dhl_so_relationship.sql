-- Name: DHL_ShipmentOrder_Target_for_M_InOut
-- 2023-10-23T14:47:31.078397700Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541840,TO_TIMESTAMP('2023-10-23 17:47:30.829','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.inout','Y','N','DHL_ShipmentOrder_Target_for_M_InOut',TO_TIMESTAMP('2023-10-23 17:47:30.829','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-10-23T14:47:31.083622900Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541840 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: DHL_ShipmentOrder_Target_for_M_InOut
-- Table: DHL_ShipmentOrder
-- Key: DHL_ShipmentOrder.DHL_ShipmentOrder_ID
-- 2023-10-23T14:52:40.328529900Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,569086,0,541840,541419,TO_TIMESTAMP('2023-10-23 17:52:40.296','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.inout','Y','N','N',TO_TIMESTAMP('2023-10-23 17:52:40.296','YYYY-MM-DD HH24:MI:SS.US'),100,'EXISTS(SELECT 1              from dhl_shipmentOrder dhl_so                       INNER JOIN m_package p on dhl_so.packageid = p.m_package_id                       INNER JOIN m_inout io on p.m_inout_id = io.m_inout_id              where dhl_shipmentorder.dhl_shipmentorder_id = dhl_so.dhl_shipmentorder_id                AND io.m_inout_id = @M_InOut_ID/-1@ )')
;

-- 2023-10-23T14:54:23.291007700Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,337,541840,540434,TO_TIMESTAMP('2023-10-23 17:54:23.159','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','M_InOut -> DHL_ShipmentOrder',TO_TIMESTAMP('2023-10-23 17:54:23.159','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Reference: DHL_ShipmentOrder_Target_for_M_InOut
-- Table: DHL_ShipmentOrder
-- Key: DHL_ShipmentOrder.DHL_ShipmentOrder_ID
-- 2023-10-23T14:55:36.219490700Z
UPDATE AD_Ref_Table SET AD_Window_ID=540743,Updated=TO_TIMESTAMP('2023-10-23 17:55:36.219','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541840
;

