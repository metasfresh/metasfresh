-- Corrects the AD_Element_Trl.IsTranslated flag on the de_DE / de_CH rows of the elements added by
-- 5823290_MD_Candidate_Reconcile_ATP_process.sql (AD_Element_ID 585443 IsDryRun, 585444
-- LivenessCutoffDate, 585445 the process element) and 5823400_MD_Candidate_ATP_Divergence_Report_process.sql
-- (AD_Element_ID 585447 the process element). Those two already-integrated scripts UPDATEd the
-- de_DE / de_CH AD_Element_Trl rows to IsTranslated='Y', which deviates from the base-language
-- convention followed elsewhere in this same PR (see 5823240_MD_ATP_Reconciliation_Backup_table.sql,
-- which never touches the de_DE/de_CH _Trl rows it seeds): base-language (German) translation rows
-- stay IsTranslated='N' -- only the actual en_US override is marked 'Y'. Text is unaffected here
-- (identical to base either way); this corrects the flag only, and re-cascades the correction to the
-- AD_Process_Para_Trl / AD_Menu_Trl rows that had already copied the wrong flag via
-- update_TRL_Tables_On_AD_Element_TRL_Update in the original scripts.
-- Per the Migration Script Immutability rule, the two original scripts are already committed and
-- integrated on this branch, so they are left untouched and this follow-up script carries the fix.

UPDATE AD_Element_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-09-10 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Element_ID IN (585443,585444,585445,585447)
;

UPDATE AD_Element_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-09-10 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Element_ID IN (585443,585444,585445,585447)
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585443,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585443,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585444,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585444,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585445,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585445,'de_CH')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585447,'de_DE')
;

select update_TRL_Tables_On_AD_Element_TRL_Update(585447,'de_CH')
;
