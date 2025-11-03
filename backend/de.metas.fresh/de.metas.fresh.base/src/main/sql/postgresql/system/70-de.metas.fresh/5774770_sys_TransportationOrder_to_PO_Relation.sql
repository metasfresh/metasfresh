-- Run mode: SWING_CLIENT

-- Name: M_ShipperTransportation
-- 2025-10-27T15:49:39.403Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542013,TO_TIMESTAMP('2025-10-27 15:49:39.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_ShipperTransportation',TO_TIMESTAMP('2025-10-27 15:49:39.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-10-27T15:49:39.405Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542013 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_ShipperTransportation
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2025-10-27T15:49:56.692Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,540426,0,542013,540030,TO_TIMESTAMP('2025-10-27 15:49:56.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-10-27 15:49:56.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: C_Order target for M_ShipperTransportation
-- 2025-10-27T15:54:06.076Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542014,TO_TIMESTAMP('2025-10-27 15:54:05.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_Order target for M_ShipperTransportation',TO_TIMESTAMP('2025-10-27 15:54:05.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-10-27T15:54:06.078Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542014 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Order target for M_ShipperTransportation
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2025-10-27T15:55:32.299Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,542014,259,TO_TIMESTAMP('2025-10-27 15:55:32.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-10-27 15:55:32.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS (SELECT 1 from M_ShippingPackage sp where sp.C_Order_ID = C_Order.C_Order_ID                                                                      AND sp.M_ShipperTransportation_ID = @M_ShipperTransportation_ID/-1@)')
;

-- Name: M_ShipperTransportation -> C_Order
-- Source Reference: M_ShipperTransportation
-- Target Reference: C_Order target for M_ShipperTransportation
-- 2025-10-27T15:57:09.133Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,542013,542014,540468,TO_TIMESTAMP('2025-10-27 15:57:08.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_ShipperTransportation -> C_Order',TO_TIMESTAMP('2025-10-27 15:57:08.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Reference: C_Order target for M_ShipperTransportation
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2025-10-27T16:59:49.125Z
UPDATE AD_Ref_Table SET AD_Window_ID=181,Updated=TO_TIMESTAMP('2025-10-27 16:59:49.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542014
;

-- Reference: C_Order target for M_ShipperTransportation
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2025-10-27T17:01:46.827Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1               from M_ShippingPackage sp                        INNER JOIN C_Order o on sp.C_Order_ID = o.C_Order_ID AND o.issotrx = ''N''               where C_Order.C_Order_ID = o.C_Order_ID                 AND sp.M_ShipperTransportation_ID = @M_ShipperTransportation_ID / -1@)',Updated=TO_TIMESTAMP('2025-10-27 17:01:46.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542014
;

-- Name: C_Order (PO) target for M_ShipperTransportation
-- 2025-10-27T17:01:59.457Z
UPDATE AD_Reference SET Name='C_Order (PO) target for M_ShipperTransportation',Updated=TO_TIMESTAMP('2025-10-27 17:01:59.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542014
;

-- 2025-10-27T17:01:59.458Z
UPDATE AD_Reference_Trl trl SET Name='C_Order (PO) target for M_ShipperTransportation' WHERE AD_Reference_ID=542014 AND AD_Language='de_DE'
;

-- 2025-10-27T17:02:10.168Z
UPDATE AD_Reference_Trl SET Name='C_Order (PO) target for M_ShipperTransportation',Updated=TO_TIMESTAMP('2025-10-27 17:02:10.166000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=542014
;

-- 2025-10-27T17:02:10.170Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-27T17:02:11.012Z
UPDATE AD_Reference_Trl SET Name='C_Order (PO) target for M_ShipperTransportation',Updated=TO_TIMESTAMP('2025-10-27 17:02:11.010000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=542014
;

-- 2025-10-27T17:02:11.013Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-27T17:02:12.269Z
UPDATE AD_Reference_Trl SET Name='C_Order (PO) target for M_ShipperTransportation',Updated=TO_TIMESTAMP('2025-10-27 17:02:12.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=542014
;

-- 2025-10-27T17:02:12.271Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Name: M_ShipperTransportation -> C_Order (PO)
-- Source Reference: M_ShipperTransportation
-- Target Reference: C_Order (PO) target for M_ShipperTransportation
-- 2025-10-27T17:09:04.718Z
UPDATE AD_RelationType SET Name='M_ShipperTransportation -> C_Order (PO)',Updated=TO_TIMESTAMP('2025-10-27 17:09:04.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_RelationType_ID=540468
;

