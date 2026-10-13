-- Run mode: SWING_CLIENT

-- Bestellkontrolle — add one outbound configuration per document type
-- Each configuration maps a document base type (Produktion or Büro) to a print format,
-- allowing DocOutboundConfigService to resolve the correct report for each kind.
-- Both configurations reference C_Order_MFGWarehouse_Report_With_Barcode (AD_PrintFormat_ID=540068),
-- matching the print format currently in use on target instances.
-- AD_Org_ID=1000000 matches the existing generic fallback row 540002, avoiding silent fallback
-- to generic configuration (DocOutboundConfigMap.java:35-42 resolves by (table, base type, org)).

-- Produktion configuration (base type BKP, for Warehouse records)
INSERT INTO C_Doc_Outbound_Config (AD_Client_ID,AD_Org_ID,AD_Table_ID,C_Doc_Outbound_Config_ID,DocBaseType,AD_PrintFormat_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive,IsDirectEnqueue,IsDirectProcessQueueItem,IsAutoSendDocument)
SELECT 1000000,1000000,540683,540022 /*From ID Server*/,'BKP',540068,TO_TIMESTAMP('2026-09-21 14:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-09-21 14:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','N'
WHERE NOT EXISTS (SELECT 1 FROM C_Doc_Outbound_Config x WHERE x.C_Doc_Outbound_Config_ID=540022)
;

-- Büro configuration (base type BKB, for Plant records)
INSERT INTO C_Doc_Outbound_Config (AD_Client_ID,AD_Org_ID,AD_Table_ID,C_Doc_Outbound_Config_ID,DocBaseType,AD_PrintFormat_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive,IsDirectEnqueue,IsDirectProcessQueueItem,IsAutoSendDocument)
SELECT 1000000,1000000,540683,540023 /*From ID Server*/,'BKB',540068,TO_TIMESTAMP('2026-09-21 14:00:00.000001','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-09-21 14:00:00.000001','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','N'
WHERE NOT EXISTS (SELECT 1 FROM C_Doc_Outbound_Config x WHERE x.C_Doc_Outbound_Config_ID=540023)
;
