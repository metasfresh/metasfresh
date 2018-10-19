
INSERT INTO AD_Element_TRL
(AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Element_ID, AD_Language, Name, PrintName, Description, Help, IsTranslated)
(Select 

w.AD_Client_ID,
w.AD_Org_ID,
now() as created,
100 as createdBy,
now() as updated,
100 as updatedBy,
'Y' as IsActive,
w.AD_Element_ID,
	wt.AD_Language,
	wt.name,
	wt.name as Printname,
	wt.description,
	wt.help,
	wt.IsTranslated

	FROM AD_Window w
	JOIn AD_Window_TRL wt on w.AD_Window_ID = wt.AD_Window_ID );