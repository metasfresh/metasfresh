-- Run mode: SWING_CLIENT

-- Process: M_Warehouse_PrintLocatorQRCodes(de.metas.warehouse.process.M_Warehouse_PrintLocatorQRCodes)
-- ParameterName: RenderedQRCode
-- 2026-01-21T17:15:42.036Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,580597,0,584999,543103,36,'RenderedQRCode',TO_TIMESTAMP('2026-01-21 17:15:41.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.','@RenderedQRCode/''''@!''''','D',0,'Y','N','Y','N','N','N','Rendered QR Code','1=1',10,TO_TIMESTAMP('2026-01-21 17:15:41.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:15:42.057Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543103 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Warehouse_PrintLocatorQRCodes(de.metas.warehouse.process.M_Warehouse_PrintLocatorQRCodes)
-- ParameterName: RenderedQRCode
-- 2026-01-21T17:17:35.087Z
UPDATE AD_Process_Para SET SeqNo=20,Updated=TO_TIMESTAMP('2026-01-21 17:17:35.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543103
;

-- Process: M_Warehouse_PrintLocatorQRCodes(de.metas.warehouse.process.M_Warehouse_PrintLocatorQRCodes)
-- ParameterName: M_Locator_ID
-- 2026-01-21T17:18:05.456Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,448,0,584999,543104,31,'M_Locator_ID',TO_TIMESTAMP('2026-01-21 17:18:05.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort im Lager','@M_Locator_ID/0@>0','D',0,'"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','Y','N','N','N','Lagerort','1=1',10,TO_TIMESTAMP('2026-01-21 17:18:05.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:18:05.461Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543104 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

