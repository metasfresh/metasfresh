-- Run mode: SWING_CLIENT

-- Name: C_Order target for PP_Order
-- 2026-04-16T17:47:48.981Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542085,TO_TIMESTAMP('2026-04-16 17:47:48.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_Order target for PP_Order',TO_TIMESTAMP('2026-04-16 17:47:48.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2026-04-16T17:47:49.362Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542085 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Order target for PP_Order
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2026-04-16T17:51:42.751Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,542085,259,TO_TIMESTAMP('2026-04-16 17:51:42.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2026-04-16 17:51:42.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'exists  ( select 1 from C_OrderLine ol join PP_Order pp on ol.C_OrderLine_ID=pp.C_OrderLine_ID where pp.PP_Order_ID = @PP_Order_ID@ )')
;

-- Name: PP_Order -> C_Order
-- Source Reference: PP_Order
-- Target Reference: C_Order target for PP_Order
-- 2026-04-16T17:53:28.367Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540503,542085,540495,TO_TIMESTAMP('2026-04-16 17:53:27.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','PP_Order_To_C_Order','Y','N','PP_Order -> C_Order',TO_TIMESTAMP('2026-04-16 17:53:27.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Reference: C_Order target for PP_Order
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2026-04-16T17:57:26.239Z
UPDATE AD_Ref_Table SET WhereClause='C_Order.C_Order_ID IN  ( select ol.C_Order_ID from C_OrderLine ol join PP_Order pp on ol.C_OrderLine_ID=pp.C_OrderLine_ID where pp.PP_Order_ID = @PP_Order_ID@ )',Updated=TO_TIMESTAMP('2026-04-16 17:57:26.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542085
;

