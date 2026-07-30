UPDATE ad_element
SET columnname = 'PackingInstruction_OLD'
WHERE columnname = 'PackingInstruction'
;

DELETE
FROM AD_Ref_List
WHERE value = 'Delivery_Via_Rule'
;

DELETE
FROM AD_Ref_List
WHERE AD_Ref_List_ID=544107
;
