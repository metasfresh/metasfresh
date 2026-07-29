UPDATE ad_element
SET columnname = 'PackingInstruction_OLD'
WHERE columnname = 'PackingInstruction'
;

UPDATE AD_Ref_List
SET value = 'Delivery_Via_Rule_OLD'
WHERE value = 'Delivery_Via_Rule'
;
