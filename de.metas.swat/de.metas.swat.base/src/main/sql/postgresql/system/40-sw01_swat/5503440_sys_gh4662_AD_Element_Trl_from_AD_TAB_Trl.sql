


INSERT INTO AD_Element_TRL
(AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Element_ID, AD_Language, Name, PrintName, Description, Help, CommitWarning , IsTranslated)
(Select 

tab.AD_Client_ID,
tab.AD_Org_ID,
now() as created,
100 as createdBy,
now() as updated,
100 as updatedBy,
'Y' as IsActive,
tab.AD_Element_ID,
	tabt.AD_Language,
	tabt.name,
	tabt.name as Printname,
	tabt.description,
	tabt.help,
	tabt.commitWarning,
	tabt.IsTranslated

	FROM AD_Tab tab
	JOIn AD_Tab_TRL tabt on tab.AD_Tab_ID = tabt.AD_Tab_ID );



