UPDATE ad_column c
SET technicalnote='It''s the C_Location_ID of the C_BPartner_Location_ID counterpart column'
WHERE c.columnname IN ('C_BPartner_Location_Value_ID', 'Bill_Location_Value_ID', 'DropShip_Location_Value_ID', 'HandOver_Location_Value_ID')
  AND c.technicalnote IS NULL
;


