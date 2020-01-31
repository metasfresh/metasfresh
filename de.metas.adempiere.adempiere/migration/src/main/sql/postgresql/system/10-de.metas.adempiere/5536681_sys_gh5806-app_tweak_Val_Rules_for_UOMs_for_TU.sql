

--the new one AD_Val_Rule (AD_Val_Rule_ID=540472)
--the old one AD_Val_Rule (AD_Val_Rule_ID=210)
BEGIN;
UPDATE AD_Val_Rule SET AD_Val_Rule_ID=5643 WHERE AD_Val_Rule_ID=210; --  free AD_Val_Rule_ID 210
UPDATE AD_Val_Rule SET AD_Val_Rule_ID=210 WHERE AD_Val_Rule_ID=540472; -- switch the new val rule that excludes For-TU-UOMs to be used in place of the old one
UPDATE AD_Val_Rule SET AD_Val_Rule_ID=540472 WHERE AD_Val_Rule_ID=5643; -- switch the old val rule that includes For-TU-UOMs to use the new ID.
COMMIT;

