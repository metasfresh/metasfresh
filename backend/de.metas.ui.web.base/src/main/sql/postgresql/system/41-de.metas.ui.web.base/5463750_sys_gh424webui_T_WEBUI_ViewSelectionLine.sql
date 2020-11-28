DROP TABLE if exists public.T_WEBUI_ViewSelectionLine;

CREATE TABLE public.T_WEBUI_ViewSelectionLine
(
	UUID character varying(60) NOT NULL,
	Record_ID numeric(10,0) NOT NULL,
	Line_ID numeric,
	Created timestamp with time zone NOT NULL DEFAULT now(),
	--
	CONSTRAINT T_WEBUI_ViewSelectionLine_PKEY PRIMARY KEY (uuid, record_id, line_id)
)
WITH (
  OIDS=FALSE
);


CREATE INDEX T_WEBUI_ViewSelectionLine_UUID ON T_WEBUI_ViewSelectionLine(UUID);

