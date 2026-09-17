-- Run mode: SWING_CLIENT

-- Name: C_DocType CUI
-- 2025-12-18T17:09:24.014Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542039,TO_TIMESTAMP('2025-12-18 17:09:23.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_DocType CUI',TO_TIMESTAMP('2025-12-18 17:09:23.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-12-18T17:09:24.078Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542039 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_DocType CUI
-- Table: C_DocType
-- Key: C_DocType.C_DocType_ID
-- 2025-12-18T17:10:11.028Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1509,1501,0,542039,217,TO_TIMESTAMP('2025-12-18 17:10:10.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-12-18 17:10:10.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_DocType.DocBaseType = ''CUI'' AND C_DocType.AD_Client_ID=@#AD_Client_ID@')
;

-- Column: C_Customs_Invoice.C_DocType_ID
-- 2025-12-18T17:10:29.564Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=542039, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-12-18 17:10:29.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=568015
;

