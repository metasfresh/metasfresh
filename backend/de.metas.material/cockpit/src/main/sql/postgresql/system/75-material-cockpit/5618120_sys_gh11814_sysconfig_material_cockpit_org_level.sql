-- allow us to have a default config and overide it for individual users 
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description=TRIM(COALESCE(Description, '')) || 'Can be overridden on client or org-level', updatedby=99, updated='2021-12-10 11:07'
WHERE Name ILIKE 'de.metas.ui.web.material.cockpit.field%IsDisplayed'
  AND ConfigurationLevel != 'O'
;

