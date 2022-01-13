drop view if exists AD_Element_Trl_Effective_v;
create or replace view AD_Element_Trl_Effective_v as
SELECT
    AD_Element_ID,
    AD_Language,
    IsTranslated, 
    -- IsUseCustomization,
    --
    (case when IsUseCustomization='Y' then Name_Customized else Name end) as Name,
    (case when IsUseCustomization='Y' then Description_Customized else Description end) as Description,
    (case when IsUseCustomization='Y' then Help_Customized else Help end) as Help,
    PrintName,
    --
    PO_Name,
    PO_Description,
    PO_Help,
    PO_PrintName,
    --
    CommitWarning,
    WEBUI_NameBrowse,
    WEBUI_NameNew,
    WEBUI_NameNewBreadcrumb, 
    --
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy
FROM AD_Element_Trl
;
