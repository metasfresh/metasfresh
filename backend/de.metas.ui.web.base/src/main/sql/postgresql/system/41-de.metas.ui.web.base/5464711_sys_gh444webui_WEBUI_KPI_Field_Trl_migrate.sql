-- delete from WEBUI_KPI_Field_Trl;

insert into WEBUI_KPI_Field_Trl
(
	AD_Language
	, WEBUI_KPI_Field_ID
	, Name
	, OffsetName
	, AD_Client_ID, AD_Org_ID
	, IsActive, Created, CreatedBy, Updated, UpdatedBy
)
select
	l.AD_Language
	, f.WEBUI_KPI_Field_ID
	, f.Name
	, f.OffsetName
	, f.AD_Client_ID, f.AD_Org_ID
	, 'Y' as IsActive, now() as Created, 0 as CreatedBy, now() as Updated, 0 as UpdatedBy
from AD_Language l, WEBUI_KPI_Field f
where l.IsSystemLanguage='Y'
and not exists ( select 1 from WEBUI_KPI_Field_Trl trl where trl.AD_Language=l.AD_Language and trl.WEBUI_KPI_Field_ID=f.WEBUI_KPI_Field_ID)
;
