SELECT backup_table('AD_UI_Element')
;

SELECT backup_table('AD_Field')
;

SELECT backup_table('AD_Field_Trl')
;

-- Column: C_Invoice_Candidate.Purchaser_User_ID
DELETE
FROM AD_UI_Element
WHERE AD_Field_ID IN
      (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590817)
;

DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID IN
      (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590817)
;

DELETE
FROM AD_Field
WHERE AD_Field_ID IN
      (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590817)
;

