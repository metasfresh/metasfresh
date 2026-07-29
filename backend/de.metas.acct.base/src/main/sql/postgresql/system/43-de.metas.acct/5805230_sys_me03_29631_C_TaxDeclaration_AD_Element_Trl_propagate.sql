-- https://github.com/metasfresh/me03/issues/29631
-- Follow-up: propagate AD_Element translations to AD_UI_Element_Trl (and refresh AD_Column_Trl / AD_Field_Trl) for the 4 new Correction elements.
-- AD_MigrationScript_ID=5805230

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584908);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584909);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584910);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584911);
