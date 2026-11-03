-- Run mode: SWING_CLIENT
--
-- FIX (hand-edited under the migration-immutability exception): the original recording hardcoded
-- AD_Ref_List_ID 1000000/1000001/1000002 -- the LOCAL Postgres sequence range (>= 1000000) that
-- every customer DB uses for its own AD_Ref_List rows. On any instance already holding rows there
-- (e.g. every customer with client-specific list values) the INSERTs abort with
-- "ad_ref_list_pkey duplicate key", and since the migration tool runs each script with
-- ON_ERROR_STOP that aborts the whole db-apply-migrations run -- no follow-up script can repair an
-- aborting INSERT, so this integrated script is corrected in place. Fix: use central-server
-- AD_Ref_List_IDs (544362/544363/544364, < 1000000, collision-free) and guard each row by its
-- natural key (AD_Reference_ID, Value) with NOT EXISTS so it is idempotent and a no-op where the
-- value already exists. Code reads these ReportElements by Value ('TotalAmount'/'Weight'/
-- 'DeliveryRule'), never by AD_Ref_List_ID, so the id change is behaviour-neutral. Instances that
-- already applied the original (fresh core/vanilla DBs) keep their existing rows and skip this.

-- Reference: ReportElements
-- Value: TotalAmount
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
SELECT 0,0,541946,544362 /*From ID Server*/,TO_TIMESTAMP('2026-08-06 11:48:58.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Total Amount',TO_TIMESTAMP('2026-08-06 11:48:58.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'TotalAmount','Total Amount'
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_List x WHERE x.AD_Reference_ID=541946 AND x.Value='TotalAmount')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541946 AND t.Value='TotalAmount' AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: ReportElements
-- Value: Weight
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
SELECT 0,0,541946,544363 /*From ID Server*/,TO_TIMESTAMP('2026-08-06 14:34:24.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Weight',TO_TIMESTAMP('2026-08-06 14:34:24.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Weight','Weight'
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_List x WHERE x.AD_Reference_ID=541946 AND x.Value='Weight')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541946 AND t.Value='Weight' AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: ReportElements
-- Value: DeliveryRule
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
SELECT 0,0,541946,544364 /*From ID Server*/,TO_TIMESTAMP('2026-08-06 14:37:34.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Delivery Rule',TO_TIMESTAMP('2026-08-06 14:37:34.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DeliveryRule','Delivery Rule'
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_List x WHERE x.AD_Reference_ID=541946 AND x.Value='DeliveryRule')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541946 AND t.Value='DeliveryRule' AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
