-- Run mode: SWING_CLIENT

-- Reference: ReportElements
-- Value: Org_Email
-- ValueName: Organisation Email
-- 2026-01-15T13:44:53.013Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541946,544098,TO_TIMESTAMP('2026-01-15 13:44:52.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Organisation Email',TO_TIMESTAMP('2026-01-15 13:44:52.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Org_Email','Organisation Email')
;

-- 2026-01-15T13:44:53.078Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544098 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-01-15T13:46:37.233Z
INSERT INTO AD_Org_ReportElement (AD_Client_ID,AD_Org_ID,AD_Org_ReportElement_ID,Created,CreatedBy,IsActive,IsHidden,ReportElement,Updated,UpdatedBy) VALUES (1000000,1000000,540000,TO_TIMESTAMP('2026-01-15 13:46:37.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Org_Email',TO_TIMESTAMP('2026-01-15 13:46:37.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
