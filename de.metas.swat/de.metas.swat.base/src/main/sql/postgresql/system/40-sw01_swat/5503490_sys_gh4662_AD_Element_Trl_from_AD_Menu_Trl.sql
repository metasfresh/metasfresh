
INSERT INTO AD_Element_TRL
(
	AD_Client_ID,
	AD_Org_ID, 
	Created,
	CreatedBy,
	Updated, 
	UpdatedBy, 
	IsActive,
	AD_Element_ID,
	AD_Language,
	Name,
	PrintName, 
	Description,
	IsTranslated, 
	WEBUI_NameBrowse,
	WEBUI_NameNew,
	WEBUI_NameNewBreadcrumb
)
(Select 

	menu.AD_Client_ID,
	menu.AD_Org_ID,
	now() as created,
	100 as createdBy,
	now() as updated,
	100 as updatedBy,
	'Y' as IsActive,
	menu.AD_Element_ID,
	mt.AD_Language,
	mt.name,
	mt.name as Printname,
	mt.description,
	mt.IsTranslated,
	mt.WEBUI_NameBrowse,
	mt.WEBUI_NameNew,
	mt.WEBUI_NameNewBreadcrumb

	FROM AD_Menu menu
	JOIn AD_Menu_TRL mt on menu.AD_Menu_ID = mt.AD_Menu_ID );