-- Run mode: SWING_CLIENT

-- Name: M_Package target for C_Order
-- 2025-08-06T17:16:27.796Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541971,TO_TIMESTAMP('2025-08-06 17:16:27.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_Package target for C_Order',TO_TIMESTAMP('2025-08-06 17:16:27.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-08-06T17:16:27.800Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541971 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Package target for C_Order
-- Table: M_Package
-- Key: M_Package.M_Package_ID
-- 2025-08-06T19:00:13.923Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,10888,0,541971,664,TO_TIMESTAMP('2025-08-06 19:00:13.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-08-06 19:00:13.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS (SELECT 1               from m_package p                        INNER JOIN m_shippingpackage sp on p.m_package_id = sp.m_package_id               inner join c_orderline ol on ol.c_orderline_id = sp.c_orderline_id               where p.m_package_id = m_package.m_package_id               and ol.c_order_id = @C_Order_ID/-1@)')
;

-- Name: C_Order -> M_Package
-- Source Reference: C_Order
-- Target Reference: M_Package target for C_Order
-- 2025-08-06T19:00:47.823Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,290,541971,540460,TO_TIMESTAMP('2025-08-06 19:00:47.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_Order -> M_Package',TO_TIMESTAMP('2025-08-06 19:00:47.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
