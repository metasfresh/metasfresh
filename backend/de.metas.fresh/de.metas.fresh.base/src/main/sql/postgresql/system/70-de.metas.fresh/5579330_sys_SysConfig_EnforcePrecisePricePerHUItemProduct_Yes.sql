UPDATE AD_SysConfig
SET value='Y', updatedby=99, updated='2021-02-17 06:58',
    description='Setting this to N might make sense only if there are some prices with packing instructions and then an explicit price without PI, that serves as the default'
WHERE name = 'de.metas.ui.web.quickinput.field.PackingItemProductFieldHelper.EnforcePrecisePricePerHUItemProduct'
  AND Value != 'Y'
;
