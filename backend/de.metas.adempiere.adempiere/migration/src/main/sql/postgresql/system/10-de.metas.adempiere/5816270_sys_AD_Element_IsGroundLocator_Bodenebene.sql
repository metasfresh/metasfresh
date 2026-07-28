-- AD_Element IsGroundLocator (585005) — rename the German label to "Bodenebene" for ALL customers.
-- Created by 5808180_sys_M_Locator_IsGroundLocator.sql as "Erdgeschoss-Lagerort"; the term is now
-- globally "Bodenebene" in de_DE + de_CH. English (en_US "Ground Floor Locator") is unchanged.
-- The change propagates to every AD_Column / AD_Field / AD_Tab / AD_Window that resolves its label
-- from this element (core Warehouse window 139 field + any customer override field) via the two
-- TRL-propagation functions below.
--
-- IDs: AD_MigrationScript 5816270 (this script) — from idserver.metas.de 2026-07-27.
-- No new AD_Element/Column/Field: this only re-labels the existing global element 585005.

-- de_DE translation -> Bodenebene
UPDATE AD_Element_Trl
   SET Name = 'Bodenebene', PrintName = 'Bodenebene', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-07-27 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585005 AND AD_Language = 'de_DE';

-- Sync the base column from de_DE (base language) and cascade to Column/Field/Tab/Window Trl
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585005, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585005, 'de_DE');

-- de_CH translation -> Bodenebene (Swiss German: same term, no ß)
UPDATE AD_Element_Trl
   SET Name = 'Bodenebene', PrintName = 'Bodenebene', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-07-27 09:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585005 AND AD_Language = 'de_CH';

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585005, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585005, 'de_CH');
