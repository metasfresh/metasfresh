-- delete from R_RequestType_Trl;

insert into R_RequestType_Trl
(
	R_RequestType_ID
	, AD_Language
	, AD_Client_ID, AD_Org_ID
	, IsActive
	, Created, CreatedBy, Updated, UpdatedBy
	--
	, IsTranslated
	, Name
)
select
	rt.R_RequestType_ID
	, l.AD_Language
	, rt.AD_Client_ID, rt.AD_Org_ID
	, 'Y' as IsActive
	, now(), 0, now(), 0
	--
	, 'N' as IsTranslated
	, rt.Name
from
	(select l.AD_Language from AD_Language l where l.IsSystemLanguage='Y') l
	, R_RequestType rt
where not exists (select 1 from R_RequestType_Trl z where z.R_RequestType_ID=rt.R_RequestType_ID and z.AD_Language=l.AD_Language)
;

