-- Run mode: SWING_CLIENT

-- Reference: ReportElements
-- Value: Delivery_To_Address
-- ValueName: Delivery To Address
-- 2026-01-15T13:59:00.462Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541946,544099,TO_TIMESTAMP('2026-01-15 13:59:00.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Delivery To Address',TO_TIMESTAMP('2026-01-15 13:59:00.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Delivery_To_Address','Delivery To Address')
;

-- 2026-01-15T13:59:00.525Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544099 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-01-15T14:00:26.052Z
INSERT INTO C_DocType_ReportElement (AD_Client_ID,AD_Org_ID,C_DocType_ID,C_DocType_ReportElement_ID,Created,CreatedBy,IsActive,IsHidden,ReportElement,Updated,UpdatedBy) VALUES (1000000,0,1000016,540036,TO_TIMESTAMP('2026-01-15 14:00:26.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Delivery_To_Address',TO_TIMESTAMP('2026-01-15 14:00:26.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
