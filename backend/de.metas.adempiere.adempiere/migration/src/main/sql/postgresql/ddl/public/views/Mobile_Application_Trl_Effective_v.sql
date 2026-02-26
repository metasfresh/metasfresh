drop view if exists Mobile_Application_Trl_Effective_v;
create or replace view Mobile_Application_Trl_Effective_v as
SELECT
    Mobile_Application_ID,
    AD_Language,
    IsTranslated, 
    -- IsUseCustomization,
    --
    (case when IsUseCustomization='Y' then COALESCE(Name_Customized, Name) else Name end) as Name,
    (case when IsUseCustomization='Y' then COALESCE(Description_Customized, Description) else Description end) as Description,

	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy
FROM Mobile_Application_Trl
;
