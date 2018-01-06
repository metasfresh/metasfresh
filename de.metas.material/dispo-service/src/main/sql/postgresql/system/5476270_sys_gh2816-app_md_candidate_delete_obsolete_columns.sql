

--delete MD_Candidate.Qty_Override
--
delete from AD_Field where AD_Column_ID=556508;
delete from AD_Column where AD_Column_ID=556508;
ALTER TABLE MD_Candidate DROP COLUMN IF EXISTS Qty_Override;

--delete MD_Candidate.Qty_Planner
--
delete from AD_Field where AD_Column_ID=556511;
delete from AD_Column where AD_Column_ID=556511;
ALTER TABLE MD_Candidate DROP COLUMN IF EXISTS Qty_Planner;

--delete MD_Candidate.AD_Table_ID
--
DELETE FROM ad_ui_element where AD_Field_ID=(select AD_Field_id from AD_Field where AD_Column_ID=556478);
delete from AD_Field where AD_Column_ID=556478;
delete from AD_Column where AD_Column_ID=556478;
ALTER TABLE MD_Candidate DROP COLUMN IF EXISTS AD_Table_ID;

--delete MD_Candidate.Record_ID
--
DELETE FROM ad_ui_element where AD_Field_ID=(select AD_Field_id from AD_Field where AD_Column_ID=556479);
delete from AD_Field where AD_Column_ID=556479;
delete from AD_Column where AD_Column_ID=556479;
ALTER TABLE MD_Candidate DROP COLUMN IF EXISTS Record_ID;

