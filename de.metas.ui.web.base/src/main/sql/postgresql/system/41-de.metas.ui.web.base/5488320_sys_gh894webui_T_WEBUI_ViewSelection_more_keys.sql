alter table T_WEBUI_ViewSelection drop column Record_ID;
alter table T_WEBUI_ViewSelection add column IntKey1 numeric(10);
alter table T_WEBUI_ViewSelection add column IntKey2 numeric(10);
alter table T_WEBUI_ViewSelection add column StringKey1 varchar(60);
CREATE INDEX T_WEBUI_ViewSelection_Search ON T_WEBUI_ViewSelection (IntKey1, UUID);

alter table T_WEBUI_ViewSelectionLine drop column Record_ID;
alter table T_WEBUI_ViewSelectionLine add column IntKey1 numeric(10);
alter table T_WEBUI_ViewSelectionLine add column IntKey2 numeric(10);
alter table T_WEBUI_ViewSelectionLine add column StringKey1 varchar(60);



