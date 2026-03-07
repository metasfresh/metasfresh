--
-- Script: /tmp/webui_migration_scripts_2025-10-27_7269838412584586299/5777200_migration_2025-11-16_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- 2025-11-16T18:15:28.200Z
UPDATE C_DocType SET IsDefault='Y',Updated=TO_TIMESTAMP('2025-11-16 18:15:28.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=1000004
;

-- 2025-11-16T18:15:31.971Z
UPDATE C_DocType SET IsDefault='N',Updated=TO_TIMESTAMP('2025-11-16 18:15:31.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=1000004
;

-- Reference: ReportElements
-- Value: QtyInvoicedInPriceUOM
-- ValueName: QtyInvoicedInPriceUOM
-- 2025-11-16T18:17:45.577Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541946,544064,TO_TIMESTAMP('2025-11-16 18:17:45.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','QtyInvoicedInPriceUOM',TO_TIMESTAMP('2025-11-16 18:17:45.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyInvoicedInPriceUOM','QtyInvoicedInPriceUOM')
;

-- 2025-11-16T18:17:45.578Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544064 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: ReportElements
-- Value: QtyInvoicedInPriceUOM
-- ValueName: QtyInvoicedInPriceUOM
-- 2025-11-16T18:18:18.948Z
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-16 18:18:18.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544064
;

-- Reference: ReportElements
-- Value: QtyInvoicedInPriceUOM
-- ValueName: QtyInvoicedInPriceUOM
-- 2025-11-16T18:19:00.316Z
UPDATE AD_Ref_List SET Name='Berechn. Menge in Preiseinheit',Updated=TO_TIMESTAMP('2025-11-16 18:19:00.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544064
;

-- 2025-11-16T18:19:00.317Z
UPDATE AD_Ref_List_Trl trl SET Name='Berechn. Menge in Preiseinheit' WHERE AD_Ref_List_ID=544064 AND AD_Language='de_DE'
;

-- Reference: ReportElements
-- Value: UOMSymbol
-- ValueName: UOMSymbol
-- 2025-11-16T18:19:55.623Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541946,544065,TO_TIMESTAMP('2025-11-16 18:19:55.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','MaÃŸeinheit',TO_TIMESTAMP('2025-11-16 18:19:55.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'UOMSymbol','UOMSymbol')
;

-- 2025-11-16T18:19:55.624Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544065 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: ReportElements
-- Value: UOMSymbol
-- ValueName: UOMSymbol
-- 2025-11-16T18:20:20.088Z
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-16 18:20:20.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544065
;

