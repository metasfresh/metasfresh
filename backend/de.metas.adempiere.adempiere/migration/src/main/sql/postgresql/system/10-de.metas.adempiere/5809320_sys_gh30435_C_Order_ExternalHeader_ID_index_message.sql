-- gh30435: register the existing unique index C_Order_ExternalHeader_ID as an AD_Index_Table
-- so a violation surfaces a clear, translated error message instead of the generic fallback.
-- Order candidates that share a non-blank ExternalId (+ ExternalSystem_ID + AD_Org_ID) but are
-- forced into separate orders by some other differing field still collide on this unique index
-- (columns ExternalSystem_ID, ExternalId, AD_Org_ID; WHERE IsActive='Y' AND ExternalId IS NOT NULL).
-- Gating is automatic via the index's WHERE ExternalId IS NOT NULL. DB-only, no Java change.
-- The raw index was created in 5774280_sys_gh25584_ExternalSystem_In_Documents.sql with no AD_Index_Table record.

-- AD_Index_Table: C_Order_ExternalHeader_ID on C_Order (AD_Table_ID=259); German ErrorMsg in the base column.
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause,ErrorMsg) VALUES (0,540864 /*From ID Server*/,0,259,TO_TIMESTAMP('2026-06-22 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Y','C_Order_ExternalHeader_ID','N',TO_TIMESTAMP('2026-06-22 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y'' AND ExternalId IS NOT NULL','Auftragskandidaten mit derselben externen Auftragsreferenz können nicht zu einem Auftrag zusammengefasst werden, da sich ihre Kopfdaten unterscheiden.')
;

-- Seed AD_Index_Table_Trl rows for the active non-base system languages (copies base German text).
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540864 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- en_US translation override.
UPDATE AD_Index_Table_Trl SET ErrorMsg='Order candidates that share the same external order reference cannot be combined into one order because their header data differs.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-22 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Index_Table_ID=540864 AND AD_Language='en_US'
;

-- fr_CH translation override.
UPDATE AD_Index_Table_Trl SET ErrorMsg='Les candidats à la commande qui partagent la même référence de commande externe ne peuvent pas être regroupés en une seule commande, car les données d''en-tête diffèrent.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-22 10:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Index_Table_ID=540864 AND AD_Language='fr_CH'
;

-- de_CH mirrors the German base text; mark as actively translated.
UPDATE AD_Index_Table_Trl SET ErrorMsg='Auftragskandidaten mit derselben externen Auftragsreferenz können nicht zu einem Auftrag zusammengefasst werden, da sich ihre Kopfdaten unterscheiden.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-22 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Index_Table_ID=540864 AND AD_Language='de_CH'
;

-- AD_Index_Column children mirroring the real index tuple (ExternalSystem_ID, ExternalId, AD_Org_ID).
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591418,541526 /*From ID Server*/,540864,0,TO_TIMESTAMP('2026-06-22 10:00:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y',10,TO_TIMESTAMP('2026-06-22 10:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569692,541527 /*From ID Server*/,540864,0,TO_TIMESTAMP('2026-06-22 10:00:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y',20,TO_TIMESTAMP('2026-06-22 10:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2163,541528 /*From ID Server*/,540864,0,TO_TIMESTAMP('2026-06-22 10:00:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y',30,TO_TIMESTAMP('2026-06-22 10:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;
