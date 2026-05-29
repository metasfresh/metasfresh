-- Run mode: SWING_CLIENT

-- Column: M_InOut.Return_Origin_InOut_ID
-- 2026-02-04T12:52:35.395Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2026-02-04 12:52:35.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591915
;

-- Column: M_InOut.Return_Origin_InOut_ID
-- 2026-02-04T12:54:25.263Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2026-02-04 12:54:25.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591915
;

-- Name: M_InOut SOTrx target for Customer Return
-- 2026-02-04T13:01:39.077Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542048,TO_TIMESTAMP('2026-02-04 13:01:38.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_InOut SOTrx target for Customer Return',TO_TIMESTAMP('2026-02-04 13:01:38.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2026-02-04T13:01:39.145Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542048 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_InOut SOTrx target for Customer Return
-- Table: M_InOut
-- Key: M_InOut.M_InOut_ID
-- 2026-02-04T13:05:07.746Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,542048,319,TO_TIMESTAMP('2026-02-04 13:05:07.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2026-02-04 13:05:07.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut.M_InOut_ID = @Return_Origin_InOut_ID/-1@')
;

-- Name: Customer Return -> Shipment
-- Source Reference: Customer Return Source
-- Target Reference: M_InOut SOTrx target for Customer Return
-- 2026-02-04T13:05:22.670Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540726,542048,540488,TO_TIMESTAMP('2026-02-04 13:05:22.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y','N','Customer Return -> Shipment',TO_TIMESTAMP('2026-02-04 13:05:22.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: Customer Return target for Shipment
-- 2026-02-04T13:19:48.481Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542049,TO_TIMESTAMP('2026-02-04 13:19:48.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','Customer Return target for Shipment',TO_TIMESTAMP('2026-02-04 13:19:48.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2026-02-04T13:19:48.550Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542049 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Customer Return target for Shipment
-- Table: M_InOut
-- Key: M_InOut.M_InOut_ID
-- 2026-02-04T13:21:01.745Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,542049,319,TO_TIMESTAMP('2026-02-04 13:21:01.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2026-02-04 13:21:01.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut.Return_Origin_InOut_ID = @M_InOut_ID/-1@')
;

-- Name: Shipment -> Customer Return
-- Source Reference: M_InOut
-- Target Reference: Customer Return target for Shipment
-- 2026-02-04T13:21:13.512Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,337,542049,540489,TO_TIMESTAMP('2026-02-04 13:21:13.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y','N','Shipment -> Customer Return',TO_TIMESTAMP('2026-02-04 13:21:13.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

