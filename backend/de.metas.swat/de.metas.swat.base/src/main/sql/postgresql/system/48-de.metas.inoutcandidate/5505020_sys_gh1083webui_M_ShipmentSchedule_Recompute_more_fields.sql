ALTER TABLE m_shipmentschedule_recompute
	ADD COLUMN created time with time zone NOT NULL DEFAULT now();
ALTER TABLE m_shipmentschedule_recompute
	ADD COLUMN description character varying(2000);
