-- AD_Column 593535 (M_InOutLine.M_Delivery_Planning_ID, added by 5823550) was left with
-- PersonalDataCategory NULL. It is a foreign key to a logistics document -- no personal data -- and
-- every other M_Delivery_Planning_ID column in the dictionary is classified 'NP' (7 of 8; this one
-- was the only exception), so it gets the same classification.
--
-- Done as a follow-up UPDATE rather than by editing 5823550's INSERT, because that script has already
-- been applied: the runner's applied-check is keyed on the script name with no checksum, so an edit
-- there would never run on any database that already has it.
UPDATE AD_Column SET PersonalDataCategory='NP',
  Updated=TO_TIMESTAMP('2026-09-10 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=593535
;
