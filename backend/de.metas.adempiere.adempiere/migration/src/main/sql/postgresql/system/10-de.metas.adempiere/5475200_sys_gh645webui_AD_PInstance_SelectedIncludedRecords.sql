-- drop table if exists AD_PInstance_SelectedIncludedRecords;

create table AD_PInstance_SelectedIncludedRecords
(
	AD_PInstance_ID numeric(10) not null,
	AD_Table_ID numeric(10) not null,
	Record_ID numeric(10) not null,
	SeqNo integer not null,
	Created timestamp with time zone not null DEFAULT now(),

	CONSTRAINT adpinstance_adpinstanceSelectedIncludedRecords FOREIGN KEY (ad_pinstance_id)
		REFERENCES public.ad_pinstance (ad_pinstance_id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED
)
;

create index AD_PInstance_SelectedIncludedRecords_Parent on AD_PInstance_SelectedIncludedRecords(AD_PInstance_ID);

