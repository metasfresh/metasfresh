-- Run mode: SWING_CLIENT

-- Reference: ReportElements
-- Value: Delivery_Via_Rule
-- ValueName: Delivery_Via_Rule
-- 2026-01-16T10:43:54.599Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541946,544102,TO_TIMESTAMP('2026-01-16 10:43:54.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Delivery Via Rule',TO_TIMESTAMP('2026-01-16 10:43:54.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Delivery_Via_Rule','Delivery_Via_Rule')
;

-- 2026-01-16T10:43:54.663Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544102 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-01-16T10:46:37.470Z
INSERT INTO C_DocType_ReportElement (AD_Client_ID,AD_Org_ID,C_DocType_ID,C_DocType_ReportElement_ID,Created,CreatedBy,IsActive,IsHidden,ReportElement,Updated,UpdatedBy) VALUES (1000000,0,1000016,540042,TO_TIMESTAMP('2026-01-16 10:46:37.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Delivery_Via_Rule',TO_TIMESTAMP('2026-01-16 10:46:37.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
