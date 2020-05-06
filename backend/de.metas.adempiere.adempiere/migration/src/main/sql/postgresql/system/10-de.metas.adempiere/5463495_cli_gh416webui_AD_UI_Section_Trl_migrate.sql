update AD_UI_Section set Value=Name where value is null;

insert into AD_UI_Section_Trl
(
	AD_UI_Section_ID, AD_Language
	, Name, Description
	, IsTranslated
	, AD_Client_ID, AD_Org_ID
	, Created, CreatedBy, Updated, UpdatedBy, IsActive
)
select
	s.AD_UI_Section_ID, l.AD_Language
	, s.Name, s.Description
	, 'N' as IsTranslated
	, s.AD_Client_ID, s.AD_Org_ID
	, now(), 0, now(), 0, 'Y'
from AD_UI_Section s
, (select l.AD_Language from AD_Language l where l.IsSystemLanguage='Y') as l
where not exists (select 1 from AD_UI_Section_Trl z where z.AD_UI_Section_ID=s.AD_UI_Section_ID and z.AD_Language=l.AD_Language)
;

