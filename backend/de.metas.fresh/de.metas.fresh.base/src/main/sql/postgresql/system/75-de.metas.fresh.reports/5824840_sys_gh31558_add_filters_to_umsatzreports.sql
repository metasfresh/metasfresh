-- Run mode: SWING_CLIENT
--
-- gh#31558: Erweiterung Umsatzreport
--
-- Adds selection parameters to two revenue reports and pre-sets their
-- sales-transaction switch:
--
--   540558  Umsatzreport nach Merkmalen             + Geschäftspartner
--                                                   + Geschäftspartnergruppe
--                                                   + Vertriebspartner
--   540740  Umsatzreport Geschäftspartner mit Menge + Geschäftspartnergruppe
--                                                   + Vertriebspartner
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
-- SeqNo — queried, not assumed. Three of 540558's parameters are pre-git seed
-- rows that no migration script carries, so the full picture was read from a
-- live flaming_merlin_release database on 2026-09-17:
--   540558: AD_Org_ID=5, C_Year_ID=10, C_Period_ID=20, IsSOTrx=30,
--           M_AttributeSetInstance_ID=40, ReportFormat=50.  Maximum 50.
--   540740: AD_Org_ID=5, Base_Period_Start=10, Base_Period_End=20,
--           Comp_Period_Start=30, Comp_Period_End=40, IsSOTrx=50,
--           C_BPartner_ID=60, C_Activity_ID=70, M_Product_ID=80,
--           M_Product_Category_ID=90, M_AttributeSetInstance_ID=100,
--           ReportFormat=110.  Maximum 110.
-- New parameters therefore start at 100 on 540558 and at 120 on 540740, which
-- appends them at the end of both dialogs — the order the customer's
-- screenshots show — with no tie against an existing parameter. (A tied SeqNo
-- would leave the rendering order undefined.)
--
-- The same query confirmed both IsSOTrx parameters currently have an EMPTY
-- DefaultValue, which is what section 3 below changes, and is why the guard
-- there expects exactly two updated rows.


-- ========================================================================
-- 1) AD_Process 540558 "Umsatzreport nach Merkmalen"
-- ========================================================================

-- Geschäftspartner
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy)
VALUES (0,187,0,540558,543326 /*From ID Server*/,19,NULL,'C_BPartner_ID',now(),100,'Bezeichnet einen Geschäftspartner','de.metas.fresh',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',100,'N',now(),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543326 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Geschäftspartnergruppe
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy)
VALUES (0,1383,0,540558,543327 /*From ID Server*/,19,NULL,'C_BP_Group_ID',now(),100,'Geschäftspartnergruppe','de.metas.fresh',0,'Die Geschäftspartner-Gruppe ermöglicht die Zuordnung von Vorgabewerten für neue Geschäftspartner.','Y','N','Y','N','N','N','Geschäftspartnergruppe',110,'N',now(),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543327 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Vertriebspartner
-- AD_Reference_Value_ID 138 = "C_BPartner (Trx)" — the same search reference every
-- other C_BPartner_SalesRep_ID column definition uses. Needed because the column
-- name does not resolve to a table by the plain <ColumnName minus _ID> convention.
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy)
VALUES (0,541357,0,540558,543328 /*From ID Server*/,30,138,'C_BPartner_SalesRep_ID',now(),100,'Vertriebspartner','de.metas.fresh',0,'Der dem Umsatz zugeordnete Vertriebspartner.','Y','N','Y','N','N','N','Vertriebspartner',120,'N',now(),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543328 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;


-- ========================================================================
-- 2) AD_Process 540740 "Umsatzreport Geschäftspartner mit Menge"
--    (already has a Geschäftspartner parameter — left untouched)
-- ========================================================================

-- Geschäftspartnergruppe
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy)
VALUES (0,1383,0,540740,543329 /*From ID Server*/,19,NULL,'C_BP_Group_ID',now(),100,'Geschäftspartnergruppe','de.metas.fresh',0,'Die Geschäftspartner-Gruppe ermöglicht die Zuordnung von Vorgabewerten für neue Geschäftspartner.','Y','N','Y','N','N','N','Geschäftspartnergruppe',120,'N',now(),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543329 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Vertriebspartner
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy)
VALUES (0,541357,0,540740,543330 /*From ID Server*/,30,138,'C_BPartner_SalesRep_ID',now(),100,'Vertriebspartner','de.metas.fresh',0,'Der dem Umsatz zugeordnete Vertriebspartner.','Y','N','Y','N','N','N','Vertriebspartner',130,'N',now(),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543330 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;


-- ========================================================================
-- 3) Verkaufstransaktion pre-set to sales on both dialogs
--    Addressed by process + ColumnName because 540558's IsSOTrx parameter is
--    a pre-git seed row whose AD_Process_Para_ID is not knowable from source.
--    It stays editable — only the starting value changes.
--
--    The row-count check exists for one concrete scenario: 540558's parameter
--    is not readable from source, so if its ColumnName is not 'IsSOTrx' the
--    UPDATE would touch fewer rows than intended and silently ship the
--    pre-ticked behaviour broken, with nothing to catch it. Failing loudly
--    here turns that into a migration error instead.
-- ========================================================================

DO $$
DECLARE
	updated_count integer;
BEGIN
	UPDATE AD_Process_Para
	SET DefaultValue = 'Y', Updated = now(), UpdatedBy = 100
	WHERE AD_Process_ID IN (540558, 540740)
	  AND ColumnName = 'IsSOTrx';

	GET DIAGNOSTICS updated_count = ROW_COUNT;

	IF updated_count <> 2 THEN
		RAISE EXCEPTION 'gh31558: expected to default exactly 2 IsSOTrx process parameters (540558, 540740), but updated %', updated_count;
	END IF;
END
$$;
