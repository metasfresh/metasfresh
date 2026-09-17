-- Run mode: SWING_CLIENT
--
-- gh#31558: Erweiterung Umsatzreport
--
-- Adds selection parameters to two revenue reports and pre-sets their
-- sales-transaction switch:
--
--   540558  Umsatzreport nach Merkmalen            + Geschäftspartner
--                                                  + Geschäftspartnergruppe
--                                                  + Vertriebspartner
--   540740  Umsatzreport Geschäftspartner mit Menge + Geschäftspartnergruppe
--                                                  + Vertriebspartner
--
-- The Vertriebspartner filter reads C_Invoice.C_BPartner_SalesRep_ID — the sales
-- partner recorded on the revenue document — NOT C_BPartner.C_BPartner_SalesRep_ID
-- on the customer's master record. See ai-work/31558/REQUIREMENTS.md AC3.
--
-- AD_Element_IDs are REUSED, never minted, so names and translations stay
-- centrally maintained:
--     187  C_BPartner_ID            "Geschäftspartner"
--    1383  C_BP_Group_ID            "Geschäftspartnergruppe"
--  541357  C_BPartner_SalesRep_ID   "Vertriebspartner"
--
-- IDs allocated from idserver.metas.de on 2026-09-17:
--   AD_Process_Para 543326 (540558 Geschäftspartner)
--   AD_Process_Para 543327 (540558 Geschäftspartnergruppe)
--   AD_Process_Para 543328 (540558 Vertriebspartner)
--   AD_Process_Para 543329 (540740 Geschäftspartnergruppe)
--   AD_Process_Para 543330 (540740 Vertriebspartner)
--
-- SeqNo note: the existing parameters of 540558 are pre-git seed data, so their
-- SeqNos are not readable from source. The new parameters use 100/110/120 (and
-- 100/110 on 540740, whose highest existing SeqNo is 90) to append them at the
-- end of both dialogs, which is the order the customer's screenshots show.


-- ========================================================================
-- 1) AD_Process 540558 "Umsatzreport nach Merkmalen"
-- ========================================================================

-- Geschäftspartner
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,187,0,540558,543326 /*From ID Server*/,19,'C_BPartner_ID',now(),100,'Bezeichnet einen Geschäftspartner','de.metas.fresh',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',100,now(),100);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=543326
AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);

-- Geschäftspartnergruppe
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,1383,0,540558,543327 /*From ID Server*/,30,'C_BP_Group_ID',now(),100,'Geschäftspartner-Gruppe','de.metas.fresh',0,'Die Geschäftspartner-Gruppe ermöglicht die Zuordnung von Vorgabewerten für neue Geschäftspartner.','Y','N','Y','N','N','N','Geschäftspartnergruppe',110,now(),100);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=543327
AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);

-- Vertriebspartner
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,541357,0,540558,543328 /*From ID Server*/,30,138,'C_BPartner_SalesRep_ID',now(),100,'Vertriebspartner','de.metas.fresh',0,'Der dem Umsatz zugeordnete Vertriebspartner.','Y','N','Y','N','N','N','Vertriebspartner',120,now(),100);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=543328
AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);


-- ========================================================================
-- 2) AD_Process 540740 "Umsatzreport Geschäftspartner mit Menge"
--    (already has a Geschäftspartner parameter — left untouched)
-- ========================================================================

-- Geschäftspartnergruppe
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,1383,0,540740,543329 /*From ID Server*/,30,'C_BP_Group_ID',now(),100,'Geschäftspartner-Gruppe','de.metas.fresh',0,'Die Geschäftspartner-Gruppe ermöglicht die Zuordnung von Vorgabewerten für neue Geschäftspartner.','Y','N','Y','N','N','N','Geschäftspartnergruppe',100,now(),100);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=543329
AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);

-- Vertriebspartner
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,541357,0,540740,543330 /*From ID Server*/,30,138,'C_BPartner_SalesRep_ID',now(),100,'Vertriebspartner','de.metas.fresh',0,'Der dem Umsatz zugeordnete Vertriebspartner.','Y','N','Y','N','N','N','Vertriebspartner',110,now(),100);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=543330
AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);


-- ========================================================================
-- 3) Verkaufstransaktion pre-set to sales on both dialogs
--    Addressed by process + ColumnName because 540558's IsSOTrx parameter is
--    pre-git seed data and its AD_Process_Para_ID is not knowable from source.
--    It stays editable — only the starting value changes.
-- ========================================================================

UPDATE AD_Process_Para
SET DefaultValue = 'Y', Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID IN (540558, 540740)
  AND ColumnName = 'IsSOTrx';
