-- Run mode: SWING_CLIENT

-- Process: C_Workplace_PrintQRCode(de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode)
-- ParameterName: RenderedQRCode
-- 2026-01-21T13:03:14.963Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,580597,0,585332,543101,36,'RenderedQRCode',TO_TIMESTAMP('2026-01-21 13:03:14.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.','U',0,'Y','N','Y','N','N','N','Rendered QR Code','1=1',10,TO_TIMESTAMP('2026-01-21 13:03:14.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T13:03:15.002Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543101 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Workplace_PrintQRCode(de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode)
-- ParameterName: RenderedQRCode
-- 2026-01-21T13:03:23.465Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-21 13:03:23.465000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543101
;

-- Process: C_Workplace_PrintQRCode(de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode)
-- ParameterName: RenderedQRCode
-- 2026-01-21T13:04:47.664Z
UPDATE AD_Process_Para SET DisplayLogic='@RenderedQRCode/''''@!''''',Updated=TO_TIMESTAMP('2026-01-21 13:04:47.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543101
;

-- Process: C_Workplace_PrintQRCode_ViewSelection(de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode_ViewSelection)
-- ParameterName: RenderedQRCode
-- 2026-01-21T17:05:15.897Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,580597,0,585333,543102,36,'RenderedQRCode',TO_TIMESTAMP('2026-01-21 17:05:15.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.','@RenderedQRCode/''''@!''''','D',0,'Y','N','Y','N','N','N','Rendered QR Code','1=1',10,TO_TIMESTAMP('2026-01-21 17:05:15.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:05:15.916Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543102 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

