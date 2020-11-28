-- delete from WEBUI_DashboardItem_Trl;

insert into WEBUI_DashboardItem_Trl
(
	AD_Language
	, WEBUI_DashboardItem_ID
	, Name
	, AD_Client_ID, AD_Org_ID
	, IsActive, Created, CreatedBy, Updated, UpdatedBy
)
select
	l.AD_Language
	, f.WEBUI_DashboardItem_ID
	, f.Name
	, f.AD_Client_ID, f.AD_Org_ID
	, 'Y' as IsActive, now() as Created, 0 as CreatedBy, now() as Updated, 0 as UpdatedBy
from AD_Language l, WEBUI_DashboardItem f
where l.IsSystemLanguage='Y'
and not exists ( select 1 from WEBUI_DashboardItem_Trl trl where trl.AD_Language=l.AD_Language and trl.WEBUI_DashboardItem_ID=f.WEBUI_DashboardItem_ID)
;

