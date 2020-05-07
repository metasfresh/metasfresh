alter table T_WEBUI_ViewSelection drop column Record_ID;
alter table T_WEBUI_ViewSelection add column IntKey1 numeric(10);
alter table T_WEBUI_ViewSelection add column IntKey2 numeric(10);
alter table T_WEBUI_ViewSelection add column StringKey1 varchar(60);
CREATE INDEX T_WEBUI_ViewSelection_Search ON T_WEBUI_ViewSelection (IntKey1, UUID);

alter table T_WEBUI_ViewSelectionLine drop column Record_ID;
alter table T_WEBUI_ViewSelectionLine add column IntKey1 numeric(10);
alter table T_WEBUI_ViewSelectionLine add column IntKey2 numeric(10);
alter table T_WEBUI_ViewSelectionLine add column StringKey1 varchar(60);



/* Backup:

DROP TABLE t_webui_viewselection;
CREATE TABLE t_webui_viewselection
(
  uuid character varying(60) NOT NULL,
  line numeric(10,0) NOT NULL,
  record_id numeric(10,0) NOT NULL,
  created timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT t_webui_viewselection_pkey PRIMARY KEY (uuid, line, record_id)
);
CREATE INDEX t_webui_viewselection_search ON t_webui_viewselection (uuid, record_id);
CREATE INDEX t_webui_viewselection_uuid ON t_webui_viewselection (uuid);


DROP TABLE public.t_webui_viewselectionline;
CREATE TABLE public.t_webui_viewselectionline
(
  uuid character varying(60) NOT NULL,
  record_id numeric(10,0) NOT NULL,
  line_id numeric NOT NULL,
  created timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT t_webui_viewselectionline_pkey PRIMARY KEY (uuid, record_id, line_id)
);
CREATE INDEX t_webui_viewselectionline_uuid ON public.t_webui_viewselectionline (uuid);

*/