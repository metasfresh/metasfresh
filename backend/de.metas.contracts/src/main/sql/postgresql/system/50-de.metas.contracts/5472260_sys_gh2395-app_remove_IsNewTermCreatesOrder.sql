
--
-- remove C_Flatrate_Conditions.IsNewTermCreatesOrder aka "AB bei neuer Vertragslaufzeit", because it's not working and not needed
--
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN (select AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=541877));

DELETE FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=541877);

DELETE FROM AD_Column WHERE AD_Element_ID=541877;

DELETE FROM AD_Element WHERE AD_Element_ID=541877;

ALTER TABLE public.C_Flatrate_Conditions DROP COLUMN IsNewTermCreatesOrder;
