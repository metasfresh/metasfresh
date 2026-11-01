-- Run mode: SWING_CLIENT

-- Bestellkontrolle — add Produktion and Büro document types
-- Two new document base types created in Task 2: BKP (Produktion) and BKB (Büro)
-- C_DocType IDs allocated: 541177 (Bestellkontrolle Produktion), 541178 (Bestellkontrolle Büro)

-- DocNoSequence_ID / IsDocNoControlled left NULL/'N' deliberately: C_Order_MFGWarehouse_Report (the
-- table these doctypes govern) has no DocumentNo column at all (information_schema.columns for
-- c_order_mfgwarehouse_report lists no documentno), so a document-number sequence would be allocated
-- and never consumed. IDocTypeDAO.java:109 marks only glCategoryId @NonNull on DocTypeCreateRequest;
-- docNoSequenceId/newDocNoSequenceStartNo are plain unannotated ints, and DocTypeDAO.createDocType()
-- treats docNoSequenceId<=0 as a first-class supported path (sets IsDocNoControlled=false), not a
-- fallback to avoid. Per PLAN.md Task 6's own Architecture section, these two doctypes exist solely so
-- DocOutboundConfigService and AD_PrinterRouting can resolve per-kind — neither consults DocumentNo.

-- Produktion C_DocType (on base type BKP)
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive,Name,PrintName,DocBaseType,IsSOTrx,DocSubType,HasProforma,IsDocNoControlled,GL_Category_ID,HasCharges,DocumentNote,IsDefault,DocumentCopies,IsDefaultCounterDoc,IsShipConfirm,IsPickQAConfirm,IsInTransit,IsSplitWhenDifference,IsCreateCounter,IsIndexed,IsOverwriteSeqOnComplete,IsOverwriteDateOnComplete,IsExcludeFromCommision,CopyDescriptionAndDocumentNote,EntityType)
SELECT 1000000,0,541177 /*From ID Server*/,TO_TIMESTAMP('2026-09-21 13:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-09-21 13:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Bestellkontrolle Produktion','Bestellkontrolle Produktion','BKP','Y',NULL,'N','N',1000005,'N',NULL,'N',1,'N','N','N','N','N','Y','Y','N','N','N','CD','de.metas.fresh'
WHERE NOT EXISTS (SELECT 1 FROM C_DocType x WHERE x.C_DocType_ID=541177)
;

-- Seed C_DocType_Trl for all active system/base languages (base text is German and customer-visible per
-- REQUIREMENTS AC2 — no en_US override needed/available)
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive,Name,PrintName,IsTranslated)
SELECT l.AD_Language,t.C_DocType_ID,t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y',t.Name,t.PrintName,'N'
FROM AD_Language l, C_DocType t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541177
AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- Büro C_DocType (on base type BKB)
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive,Name,PrintName,DocBaseType,IsSOTrx,DocSubType,HasProforma,IsDocNoControlled,GL_Category_ID,HasCharges,DocumentNote,IsDefault,DocumentCopies,IsDefaultCounterDoc,IsShipConfirm,IsPickQAConfirm,IsInTransit,IsSplitWhenDifference,IsCreateCounter,IsIndexed,IsOverwriteSeqOnComplete,IsOverwriteDateOnComplete,IsExcludeFromCommision,CopyDescriptionAndDocumentNote,EntityType)
SELECT 1000000,0,541178 /*From ID Server*/,TO_TIMESTAMP('2026-09-21 13:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-09-21 13:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Bestellkontrolle Büro','Bestellkontrolle Büro','BKB','Y',NULL,'N','N',1000005,'N',NULL,'N',1,'N','N','N','N','N','Y','Y','N','N','N','CD','de.metas.fresh'
WHERE NOT EXISTS (SELECT 1 FROM C_DocType x WHERE x.C_DocType_ID=541178)
;

-- Seed C_DocType_Trl for all active system/base languages
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive,Name,PrintName,IsTranslated)
SELECT l.AD_Language,t.C_DocType_ID,t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y',t.Name,t.PrintName,'N'
FROM AD_Language l, C_DocType t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541178
AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;
