--
-- Backup
create table backup.AD_Message_BKP_BeforeImport_5469360 as select * from AD_Message;
create table backup.AD_Message_Trl_BKP_BeforeImport_5469360 as select * from AD_Message_Trl;

--
-- Expected input tables:
-- backup.WEBUI_AD_Message
-- backup.WEBUI_AD_Message_Trl


--
--
-- Update existing messages
update public.AD_Message m set
	Value=imp.Value,
	MsgText=imp.MsgText,
	MsgTip=imp.MsgTip
from backup.WEBUI_AD_Message imp
where m.AD_Message_ID=imp.AD_Message_ID
and (
	m.Value<>imp.Value
	or m.MsgText<>imp.MsgText
	or m.MsgTip<>imp.MsgTip
);
--
update public.AD_Message_Trl m set
	MsgText=imp.MsgText,
	MsgTip=imp.MsgTip,
	IsTranslated=imp.IsTranslated
from backup.WEBUI_AD_Message_Trl imp
where m.AD_Message_ID=imp.AD_Message_ID and m.AD_Language=imp.AD_Language
and (
	m.MsgText<>imp.MsgText
	or m.MsgTip<>imp.MsgTip
	or m.IsTranslated<>imp.IsTranslated
);




--
--
-- Insert missing messages
INSERT INTO public.ad_message(
	ad_message_id, ad_client_id, ad_org_id, isactive, created, createdby, 
	updated, updatedby, value, msgtext, msgtip, msgtype, entitytype
)
select
	ad_message_id, ad_client_id, ad_org_id, isactive, created, createdby, 
	updated, updatedby, value, msgtext, msgtip, msgtype, entitytype
from backup.WEBUI_AD_Message imp
where not exists (select 1 from AD_Message m where m.AD_Message_ID=imp.AD_Message_ID);

INSERT INTO public.ad_message_trl(
	ad_message_id, ad_language, ad_client_id, ad_org_id, isactive, 
	created, createdby, updated, updatedby, msgtext, msgtip, istranslated
)
select
	ad_message_id, ad_language, ad_client_id, ad_org_id, isactive, 
	created, createdby, updated, updatedby, msgtext, msgtip, istranslated
from backup.WEBUI_AD_Message_Trl imp
where not exists (select 1 from AD_Message_Trl trl where trl.AD_Message_ID=imp.AD_Message_ID and trl.AD_Language=imp.AD_Language);

/* check:
select m.AD_Message_ID, m.Value, m.MsgText, m.MsgTip, m.EntityType
, trl.AD_Language, trl.MsgText, trl.MsgTip, trl.IsTranslated
from AD_Message m
left outer join AD_Message_Trl trl on (trl.AD_Message_ID=m.AD_Message_ID)
where m.Value like 'webui.%'
order by m.Value, m.AD_Message_ID, trl.AD_Language
;
*/
