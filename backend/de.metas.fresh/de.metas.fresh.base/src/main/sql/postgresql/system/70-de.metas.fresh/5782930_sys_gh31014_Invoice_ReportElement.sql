UPDATE ad_element
SET columnname = 'PackingInstruction_OLD'
WHERE columnname = 'PackingInstruction'
;

DELETE
FROM AD_Ref_List
WHERE value = 'Delivery_Via_Rule'
   OR value = 'Net_Sum'
;
