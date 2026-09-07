-- Enforce "at most one ACTIVE access record per Mobile Application and Role" on
-- Mobile_Application_Access. A duplicate makes the role's mobile-application permissions
-- ambiguous and degrades the whole instance.
-- The index is declared in the application dictionary (AD_Index_Table / AD_Index_Column) so
-- that a violation surfaces as the translatable ErrorMsg below (HTTP 422) instead of a raw
-- database error: DBUniqueConstraintException maps the violated index name back to its
-- AD_Index_Table row via MIndexTable.getByNameIgnoringCase(). Therefore the physical index
-- name MUST stay equal to AD_Index_Table.Name.
-- Existing duplicates are deactivated by
-- 5818050_sys_Mobile_Application_Access_deactivate_duplicates.sql (lower prefix, runs first).

INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540867 /*From ID Server*/,0,542446,TO_TIMESTAMP('2026-08-10 09:10:00','YYYY-MM-DD HH24:MI:SS'),100,'Ensures there is at most one active access record per Mobile Application and Role.','D','Für diese Mobile Application und Rolle gibt es bereits einen aktiven Eintrag. Bitte bearbeiten Sie den bestehenden Eintrag.','Y','Y','Mobile_Application_Access_UC','N',TO_TIMESTAMP('2026-08-10 09:10:00','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540867 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

UPDATE AD_Index_Table_Trl SET ErrorMsg='An active entry for this Mobile Application and Role already exists. Please edit the existing entry.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-08-10 09:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Index_Table_ID=540867
;

UPDATE AD_Index_Table_Trl SET ErrorMsg='Für diese Mobile Application und Rolle gibt es bereits einen aktiven Eintrag. Bitte bearbeiten Sie den bestehenden Eintrag.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-08-10 09:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Index_Table_ID=540867
;

UPDATE AD_Index_Table_Trl SET ErrorMsg='Für diese Mobile Application und Rolle gibt es bereits einen aktiven Eintrag. Bitte bearbeiten Sie den bestehenden Eintrag.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-08-10 09:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Index_Table_ID=540867
;

INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,589278,541535 /*From ID Server*/,540867,0,TO_TIMESTAMP('2026-08-10 09:11:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2026-08-10 09:11:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,589279,541536 /*From ID Server*/,540867,0,TO_TIMESTAMP('2026-08-10 09:11:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2026-08-10 09:11:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- IF NOT EXISTS: the index was already created manually on the affected production instance
-- under exactly this name and definition; there it stays untouched.
CREATE UNIQUE INDEX IF NOT EXISTS Mobile_Application_Access_UC ON Mobile_Application_Access (Mobile_Application_ID, AD_Role_ID) WHERE IsActive = 'Y'
;

-- When this migration script fails on CREATE UNIQUE INDEX, the instance still holds duplicate
-- active rows. Inspect them with:
/*
SELECT Mobile_Application_ID, AD_Role_ID, count(*), array_agg(Mobile_Application_Access_ID)
FROM Mobile_Application_Access
WHERE IsActive = 'Y'
GROUP BY Mobile_Application_ID, AD_Role_ID
HAVING count(*) > 1;
*/
