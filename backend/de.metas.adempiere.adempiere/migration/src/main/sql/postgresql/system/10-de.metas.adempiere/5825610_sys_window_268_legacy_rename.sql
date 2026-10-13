-- Rename window 268 ("Rolle - Datenzugriff") to mark it legacy (AC13). It is not deleted and not
-- otherwise changed: AD_Table_Access moves to window 111, but AD_Column_Access stays behind on 268
-- unowned, so 268 has no replacement window (` - LEGACY` marker, not `_OLD`).
--
-- AD_Window 268 is element-driven (AD_Window.AD_Element_ID=574066), and that element is used by
-- window 268 alone (no AD_Tab / AD_Column / AD_Field / AD_Menu references it) -- so this rename
-- cannot leak into window 111 or its new tab 549493, which deliberately carries its own caption
-- element (585480, see 5825600_sys_AD_Table_Access_tab_window_111.sql). Verified against the running
-- stack before writing this script.
--
-- Written through AD_Element_Trl + the two propagation functions, never the AD_Window base row
-- directly. de_DE and de_CH are updated (base language row of Name is the base column); en_US is
-- translated; fr_CH is out of scope and left untouched.

UPDATE AD_Element_Trl SET Name='Rolle - Datenzugriff - LEGACY',
       Updated=TO_TIMESTAMP('2026-09-22 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=574066 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Rolle - Datenzugriff - LEGACY',
       Updated=TO_TIMESTAMP('2026-09-22 11:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=574066 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Role Data Access - LEGACY',
       Updated=TO_TIMESTAMP('2026-09-22 11:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=574066 AND AD_Language='en_US'
;

-- Sync the AD_Element base row (de_DE is the base language) and propagate to AD_Window / AD_Window_Trl.
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(574066);
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(574066);
