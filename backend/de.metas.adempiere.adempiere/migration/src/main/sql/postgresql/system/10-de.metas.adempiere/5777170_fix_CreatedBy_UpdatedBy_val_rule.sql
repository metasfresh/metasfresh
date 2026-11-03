SELECT backup_table('AD_Column')
;

UPDATE AD_Column
SET ad_val_rule_id=NULL,
    updated='2025-11-15',
    updatedby=99
WHERE columnName IN ('CreatedBy', 'UpdatedBy')
  AND ad_val_rule_id IS NOT NULL
;


UPDATE AD_Column
SET ad_reference_value_id=110, -- AD_User
    updated='2025-11-15',
    updatedby=99
WHERE columnName IN ('CreatedBy', 'UpdatedBy')
  AND ad_reference_value_id != 110
;

