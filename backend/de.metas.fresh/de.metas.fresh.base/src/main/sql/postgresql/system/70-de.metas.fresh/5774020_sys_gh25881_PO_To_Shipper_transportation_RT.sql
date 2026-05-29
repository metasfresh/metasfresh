-- Run mode: SWING_CLIENT

-- Name: M_ShipperTransportation_Target_For_C_Order (PO)
-- 2025-10-21T19:47:45.968Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542002,TO_TIMESTAMP('2025-10-21 19:47:45.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_ShipperTransportation_Target_For_C_Order (PO)',TO_TIMESTAMP('2025-10-21 19:47:45.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-10-21T19:47:46.033Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542002 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_ShipperTransportation_Target_For_C_Order (PO)
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2025-10-21T19:50:27.332Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,540426,0,542002,540030,TO_TIMESTAMP('2025-10-21 19:50:26.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-10-21 19:50:26.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS (SELECT 1 from M_ShipperTransportation st JOIN M_ShippingPackage sp on st.M_ShipperTransportation_ID = sp.M_ShipperTransportation_ID where M_ShipperTransportation.M_ShipperTransportation_ID = st.M_ShipperTransportation_ID AND sp.C_Order_ID = @C_Order_ID/-1@)')
;

-- Name: C_Order (PO) -> M_ShipperTransportation
-- Source Reference: C_Order_POTrx_Source
-- Target Reference: M_ShipperTransportation_Target_For_C_Order (PO)
-- 2025-10-21T19:51:03.103Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540676,542002,540463,TO_TIMESTAMP('2025-10-21 19:51:02.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_Order (PO) -> M_ShipperTransportation',TO_TIMESTAMP('2025-10-21 19:51:02.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

