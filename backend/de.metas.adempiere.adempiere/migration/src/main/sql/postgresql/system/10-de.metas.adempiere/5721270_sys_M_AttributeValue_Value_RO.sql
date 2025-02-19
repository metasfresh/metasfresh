
UPDATE ad_column
SET isupdateable='N', isalwaysupdateable='N', 
    technicalnote='Not updatable because the value is rendered into M_AttributeSetInstance, M_Storage and MD_Candiate records', 
    updatedby=100, 
    updated=TO_TIMESTAMP('2024-04-08 10:28:00.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC'
WHERE ad_Table_id = get_table_id('M_AttributeValue')
  AND columnname = 'Value'
;
