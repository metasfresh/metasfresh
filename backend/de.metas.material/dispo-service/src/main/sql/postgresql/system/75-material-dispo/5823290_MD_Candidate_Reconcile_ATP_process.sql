-- Registers AD_Process MD_Candidate_Reconcile_ATP: an operator-invocable run of
-- AtpReconciliationCommand over an optional warehouse/product/product-category filter,
-- with a dry-run mode and an optional liveness-cutoff date.
-- IDs allocated from idserver.metas.de on 2026-09-09.

-- Process registration for MD_Candidate_Reconcile_ATP -- an operator-invocable run of
-- AtpReconciliationCommand restricted by an optional warehouse/product/product-category
-- filter, with a dry-run mode and an optional liveness-cutoff date.
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,Help,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585674 /*From ID Server*/,'Y','de.metas.material.dispo.reconcile.process.MD_Candidate_Reconcile_ATP','N',TO_TIMESTAMP('2026-09-09 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Gleicht das gespeicherte Zusagbare (ATP) mit dem physischen Bestand und offenen Positionen ab, ohne den Bestand zu verändern.','Ein Testlauf wird sofort ausgeführt und zeigt nur an, was sich ändern würde. Ein echter Lauf wird stattdessen als Arbeitspaket zur Verarbeitung im Application-Server eingeplant - das Ergebnis steht deshalb nicht in diesem Prozessprotokoll, sondern am Arbeitspaket.','de.metas.material.dispo','Y','Y','N','N','Y','Y','N','Y','Y',0,'ATP abgleichen','json','Y','Y','Java',TO_TIMESTAMP('2026-09-09 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'MD_Candidate_Reconcile_ATP')
;

INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585674
AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Reconcile ATP', Description='Reconciles the stored Available-to-Promise (ATP) with physical stock and still-open positions, without moving stock.', Help='A dry run is carried out immediately and only reports what would change. A real run is instead queued as a work package for processing in the application server - so its result is reported on that work package, not in this process log.', Updated=TO_TIMESTAMP('2026-09-09 09:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Process_ID=585674
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 09:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585674
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 09:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585674
;

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585443 /*From ID Server*/,0,'IsDryRun',TO_TIMESTAMP('2026-09-09 09:00:05','YYYY-MM-DD HH24:MI:SS'),100,'Führt den Abgleich nur simulierend aus - es wird nichts geschrieben, nur die Ergebnisse werden angezeigt. Standardmäßig deaktiviert: ein Lauf ohne explizite Aktivierung schreibt echte Änderungen.','de.metas.material.dispo','Y','Testlauf','Testlauf',TO_TIMESTAMP('2026-09-09 09:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Description,Help,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585443
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Dry Run', PrintName='Dry Run', Description='Runs the reconciliation in preview mode only - nothing is written, only the values that would change are reported. Off by default: a run performs a real, data-writing reconciliation unless this option is explicitly enabled.', Updated=TO_TIMESTAMP('2026-09-09 09:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585443
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 09:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585443
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 09:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585443
;

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585444 /*From ID Server*/,0,'LivenessCutoffDate',TO_TIMESTAMP('2026-09-09 09:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Positionen vor diesem Datum gelten als abgeschlossen, unabhängig vom Status ihres Quellbelegs.','de.metas.material.dispo','Y','Aktualitäts-Stichtag','Aktualitäts-Stichtag',TO_TIMESTAMP('2026-09-09 09:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Description,Help,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585444
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Liveness Cutoff Date', PrintName='Liveness Cutoff Date', Description='Positions dated before this date are treated as closed, regardless of their source document''s status.', Updated=TO_TIMESTAMP('2026-09-09 09:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585444
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 09:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585444
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 09:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585444
;

INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,459,0,585674,543311 /*From ID Server*/,19,'M_Warehouse_ID',TO_TIMESTAMP('2026-09-09 09:00:15','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','de.metas.material.dispo',0,'Y','N','Y','N','N','N','Lager',10,TO_TIMESTAMP('2026-09-09 09:00:15','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543311
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

select update_TRL_Tables_On_AD_Element_TRL_Update(459,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(459,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(459,'en_US')
;

INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,454,0,585674,543312 /*From ID Server*/,19,'M_Product_ID',TO_TIMESTAMP('2026-09-09 09:00:17','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','de.metas.material.dispo',0,'Y','N','Y','N','N','N','Produkt',20,TO_TIMESTAMP('2026-09-09 09:00:17','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543312
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

select update_TRL_Tables_On_AD_Element_TRL_Update(454,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(454,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(454,'en_US')
;

INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,453,0,585674,543313 /*From ID Server*/,19,'M_Product_Category_ID',TO_TIMESTAMP('2026-09-09 09:00:19','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','de.metas.material.dispo',0,'Y','N','Y','N','N','N','Produkt Kategorie',30,TO_TIMESTAMP('2026-09-09 09:00:19','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543313
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

select update_TRL_Tables_On_AD_Element_TRL_Update(453,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(453,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(453,'en_US')
;

INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,DefaultValue,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,585443,0,585674,543314 /*From ID Server*/,20,'IsDryRun',TO_TIMESTAMP('2026-09-09 09:00:21','YYYY-MM-DD HH24:MI:SS'),100,'Führt den Abgleich nur simulierend aus - es wird nichts geschrieben, nur die Ergebnisse werden angezeigt. Standardmäßig deaktiviert: ein Lauf ohne explizite Aktivierung schreibt echte Änderungen.','de.metas.material.dispo',0,'N','Y','N','Y','N','Y','N','Testlauf',40,TO_TIMESTAMP('2026-09-09 09:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543314
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585443,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585443,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585443,'en_US')
;

INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,585444,0,585674,543315 /*From ID Server*/,15,'LivenessCutoffDate',TO_TIMESTAMP('2026-09-09 09:00:23','YYYY-MM-DD HH24:MI:SS'),100,'Positionen vor diesem Datum gelten als abgeschlossen, unabhängig vom Status ihres Quellbelegs.','de.metas.material.dispo',0,'Y','N','Y','N','N','N','Aktualitäts-Stichtag',50,TO_TIMESTAMP('2026-09-09 09:00:23','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543315
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585444,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585444,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585444,'en_US')
;

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585445 /*From ID Server*/,0,NULL,TO_TIMESTAMP('2026-09-09 09:00:25','YYYY-MM-DD HH24:MI:SS'),100,'Gleicht das gespeicherte Zusagbare (ATP) mit dem physischen Bestand und offenen Positionen ab, ohne den Bestand zu verändern.','de.metas.material.dispo','Y','ATP abgleichen','ATP abgleichen',TO_TIMESTAMP('2026-09-09 09:00:25','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Description,Help,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585445
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reconcile ATP', PrintName='Reconcile ATP', Description='Reconciles the stored Available-to-Promise (ATP) with physical stock and still-open positions, without moving stock.', Updated=TO_TIMESTAMP('2026-09-09 09:00:27','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585445
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 09:00:28','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585445
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 09:00:29','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585445
;

INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Action,Created,CreatedBy,EntityType,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,InternalName,Name,Updated,UpdatedBy)
VALUES (0,585445,542360 /*From ID Server*/,0,585674,'P',TO_TIMESTAMP('2026-09-09 09:00:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','N','N','N','MD_Candidate_Reconcile_ATP','ATP abgleichen',TO_TIMESTAMP('2026-09-09 09:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID,Name,Description,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Menu_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Menu t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542360
AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585445,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585445,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585445,'en_US')
;

INSERT INTO AD_TreeNodeMM (AD_Tree_ID,Node_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Parent_ID,SeqNo)
VALUES (10,542360,0,0,'Y',TO_TIMESTAMP('2026-09-09 09:00:32','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-09 09:00:32','YYYY-MM-DD HH24:MI:SS'),100,1000069,3)
;
