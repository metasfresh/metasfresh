-- MD_ATP_Reconciliation_Backup legitimately holds MULTIPLE rows per MD_Candidate_ID over time - one per
-- reconciliation run that ever touched that candidate (its own migration header: "one row per STOCK
-- candidate a reconciliation run touched"). AtpReconciliationDetailRepo (added by an earlier commit on this
-- branch, reusing this same table as the ATP_RECONCILIATION business-case detail) looked a candidate up by
-- MD_Candidate_ID alone and called firstOnly(), which throws once a second reconciliation run backs up the
-- same candidate a second time - breaking the normal, expected case of reconciling the same key repeatedly.
--
-- This column marks the ONE row (out of however many accumulate for a given candidate) that is the
-- correction candidate's OWN detail - written only by AtpReconciliationDetailRepo.saveOrUpdate at the
-- candidate's creation, never by the audit-trail writes in AtpReconciliationBackupRepositoryImpl. The
-- partial unique index below enforces "at most one such row per candidate" at the database level, the same
-- way this codebase enforces "at most one active row per X" elsewhere.

-- New column: AD_Element + AD_Column
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585454 /*From ID Server*/,0,'IsCandidateOwnDetail',TO_TIMESTAMP('2026-09-10 22:40:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Eigenes Korrekturdetail','Eigenes Korrekturdetail',TO_TIMESTAMP('2026-09-10 22:40:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Description,Help,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585454
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='This candidate''s own ATP reconciliation detail', PrintName='This candidate''s own ATP reconciliation detail', Updated=TO_TIMESTAMP('2026-09-10 22:40:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585454
;

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593551 /*From ID Server*/,585454,0,20,542645,'IsCandidateOwnDetail',TO_TIMESTAMP('2026-09-10 22:41:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.material.dispo',1,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Eigenes Korrekturdetail','NP',90,TO_TIMESTAMP('2026-09-10 22:41:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593551
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(585454);

-- Physical column: brand new, so ADD COLUMN (t_alter_column only works on columns that already exist).
-- Non-volatile DEFAULT 'N' populates every existing row without a table rewrite; backup first since this
-- table already carries real audit data on any instance the earlier ATP-reconciliation commits reached.
SELECT backup_table('md_atp_reconciliation_backup', '_29675_IsCandidateOwnDetail');
ALTER TABLE MD_ATP_Reconciliation_Backup ADD COLUMN IF NOT EXISTS IsCandidateOwnDetail CHAR(1) DEFAULT 'N';
UPDATE MD_ATP_Reconciliation_Backup SET IsCandidateOwnDetail='N' WHERE IsCandidateOwnDetail IS NULL;
ALTER TABLE MD_ATP_Reconciliation_Backup ALTER COLUMN IsCandidateOwnDetail SET NOT NULL;

-- Enforce "at most one own-detail row per candidate" at the database level.
CREATE UNIQUE INDEX md_atp_reconciliation_backup_owndetail_uidx ON MD_ATP_Reconciliation_Backup (MD_Candidate_ID) WHERE IsCandidateOwnDetail='Y';
