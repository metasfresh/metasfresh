-- Registers AD_Process MD_Candidate_ATP_Divergence_Report: a read-only run reporting, per reconciliation
-- key, the divergence between the stored and the expected Available-to-Promise (ATP), and every open
-- M_ShipmentSchedule / M_ReceiptSchedule referenced by no candidate at all. Writes nothing.
-- IDs allocated from idserver.metas.de on 2026-09-09.

-- Process registration for MD_Candidate_ATP_Divergence_Report -- a read-only run over an optional
-- warehouse/product/product-category filter.
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585676 /*From ID Server*/,'Y','de.metas.material.dispo.reconcile.process.MD_Candidate_ATP_Divergence_Report','N',TO_TIMESTAMP('2026-09-09 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Meldet je Schlüssel die Abweichung zwischen gespeichertem und erwartetem Zusagbaren (ATP) sowie jeden offenen Beleg ohne Kandidat. Schreibt nichts.','de.metas.material.dispo','Y','Y','N','N','Y','Y','N','Y','Y',0,'ATP Abweichungsbericht','json','N','Y','Java',TO_TIMESTAMP('2026-09-09 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'MD_Candidate_ATP_Divergence_Report')
;

INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585676
AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Name='ATP Divergence Report', Description='Reports, per reconciliation key, the divergence between the stored and the expected Available-to-Promise (ATP), and every open source document referenced by no candidate at all. Writes nothing.', Updated=TO_TIMESTAMP('2026-09-09 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Process_ID=585676
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585676
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 10:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585676
;

INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,459,0,585676,543317 /*From ID Server*/,19,'M_Warehouse_ID',TO_TIMESTAMP('2026-09-09 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','de.metas.material.dispo',0,'Y','N','Y','N','N','N','Lager',10,TO_TIMESTAMP('2026-09-09 10:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543317
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

select update_TRL_Tables_On_AD_Element_TRL_Update(459,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(459,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(459,'en_US')
;

INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,454,0,585676,543318 /*From ID Server*/,19,'M_Product_ID',TO_TIMESTAMP('2026-09-09 10:00:07','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','de.metas.material.dispo',0,'Y','N','Y','N','N','N','Produkt',20,TO_TIMESTAMP('2026-09-09 10:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543318
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

select update_TRL_Tables_On_AD_Element_TRL_Update(454,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(454,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(454,'en_US')
;

INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,453,0,585676,543319 /*From ID Server*/,19,'M_Product_Category_ID',TO_TIMESTAMP('2026-09-09 10:00:09','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','de.metas.material.dispo',0,'Y','N','Y','N','N','N','Produkt Kategorie',30,TO_TIMESTAMP('2026-09-09 10:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543319
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

select update_TRL_Tables_On_AD_Element_TRL_Update(453,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(453,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(453,'en_US')
;

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585447 /*From ID Server*/,0,NULL,TO_TIMESTAMP('2026-09-09 10:00:11','YYYY-MM-DD HH24:MI:SS'),100,'Meldet je Schlüssel die Abweichung zwischen gespeichertem und erwartetem Zusagbaren (ATP) sowie jeden offenen Beleg ohne Kandidat. Schreibt nichts.','de.metas.material.dispo','Y','ATP Abweichungsbericht','ATP Abweichungsbericht',TO_TIMESTAMP('2026-09-09 10:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Description,Help,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585447
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='ATP Divergence Report', PrintName='ATP Divergence Report', Description='Reports, per reconciliation key, the divergence between the stored and the expected Available-to-Promise (ATP), and every open source document referenced by no candidate at all. Writes nothing.', Updated=TO_TIMESTAMP('2026-09-09 10:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585447
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 10:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585447
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 10:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585447
;

INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Action,Created,CreatedBy,EntityType,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,InternalName,Name,Updated,UpdatedBy)
VALUES (0,585447,542362 /*From ID Server*/,0,585676,'P',TO_TIMESTAMP('2026-09-09 10:00:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','N','N','N','MD_Candidate_ATP_Divergence_Report','ATP Abweichungsbericht',TO_TIMESTAMP('2026-09-09 10:00:16','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID,Name,Description,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Menu_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Menu t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542362
AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585447,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585447,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585447,'en_US')
;

INSERT INTO AD_TreeNodeMM (AD_Tree_ID,Node_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Parent_ID,SeqNo)
VALUES (10,542362,0,0,'Y',TO_TIMESTAMP('2026-09-09 10:00:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-09 10:00:18','YYYY-MM-DD HH24:MI:SS'),100,1000069,4)
;
