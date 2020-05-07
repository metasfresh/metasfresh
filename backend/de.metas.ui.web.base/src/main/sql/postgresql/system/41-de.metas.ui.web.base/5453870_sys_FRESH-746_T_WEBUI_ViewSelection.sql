DROP TABLE IF EXISTS T_WEBUI_ViewSelection;

CREATE TABLE T_WEBUI_ViewSelection
(
  uuid character varying(40) NOT NULL,
  line numeric(10,0) NOT NULL,
  record_id numeric(10,0) NOT NULL,
  created timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT T_WEBUI_ViewSelection_pkey PRIMARY KEY (uuid, line, record_id)
)
WITH (
  OIDS=FALSE
);
-- ALTER TABLE T_WEBUI_ViewSelection OWNER TO metasfresh;

CREATE INDEX T_WEBUI_ViewSelection_Search
	ON T_WEBUI_ViewSelection
	USING btree
	(uuid COLLATE pg_catalog."default", record_id);

CREATE INDEX T_WEBUI_ViewSelection_UUID
  ON public.T_WEBUI_ViewSelection
  USING btree
  (uuid COLLATE pg_catalog."default");

