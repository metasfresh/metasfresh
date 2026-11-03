-- Run mode: SWING_CLIENT

-- Reference: ReportElements
-- Value: Invoice_Header_QR
-- ValueName: QR-Code im Rechnungskopf
-- 2026-02-26T11:19:17.061Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541946,544132,TO_TIMESTAMP('2026-02-26 11:19:16.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','QR-Code im Rechnungskopf',TO_TIMESTAMP('2026-02-26 11:19:16.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Invoice_Header_QR','QR-Code im Rechnungskopf')
;

-- 2026-02-26T11:19:17.135Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544132 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ReportElements -> Invoice_Header_QR_QR-Code im Rechnungskopf
-- 2026-02-26T11:19:30.960Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='QR code in the invoice header',Updated=TO_TIMESTAMP('2026-02-26 11:19:30.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544132
;

-- 2026-02-26T11:19:31.020Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-26T11:22:55.697Z
INSERT INTO C_DocType_ReportElement (AD_Client_ID,AD_Org_ID,C_DocType_ID,C_DocType_ReportElement_ID,Created,CreatedBy,IsActive,IsHidden,ReportElement,Updated,UpdatedBy) VALUES (1000000,0,1000002,540069,TO_TIMESTAMP('2026-02-26 11:22:55.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Invoice_Header_QR',TO_TIMESTAMP('2026-02-26 11:22:55.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-26T11:22:56.972Z
UPDATE C_DocType_ReportElement SET IsHidden='Y',Updated=TO_TIMESTAMP('2026-02-26 11:22:56.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ReportElement_ID=540069
;
