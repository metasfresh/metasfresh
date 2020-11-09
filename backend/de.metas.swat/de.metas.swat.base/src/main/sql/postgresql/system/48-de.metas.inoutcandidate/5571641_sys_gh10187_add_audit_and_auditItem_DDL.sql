
-- rename the existing M_ShipmentSchedule_ExportAudit to M_ShipmentSchedule_ExportAudit_Item

/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_ExportAudit','ALTER TABLE M_ShipmentSchedule_ExportAudit RENAME COLUMN M_ShipmentSchedule_ExportAudit_ID TO M_ShipmentSchedule_ExportAudit_Item_ID;');
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_ExportAudit','ALTER TABLE M_ShipmentSchedule_ExportAudit RENAME TO M_ShipmentSchedule_ExportAudit_Item;');

alter table M_ShipmentSchedule_ExportAudit_Item drop constraint m_shipmentschedule_exportaudit_key;

-- 2020-11-06T09:50:32.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--/* DDL */ CREATE TABLE public.M_ShipmentSchedule_ExportAudit_Item (AD_Client_ID NUMERIC(10) NOT NULL, AD_Issue_ID NUMERIC(10), AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_ShipmentSchedule_ExportAudit_ID NUMERIC(10), M_ShipmentSchedule_ExportAudit_Item_ID NUMERIC(10) NOT NULL, M_ShipmentSchedule_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MShipmentScheduleExportAudit_MShipmentScheduleExportAuditItem FOREIGN KEY (M_ShipmentSchedule_ExportAudit_ID) REFERENCES public.M_ShipmentSchedule_ExportAudit DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_ShipmentSchedule_ExportAudit_Item_Key PRIMARY KEY (M_ShipmentSchedule_ExportAudit_Item_ID))
--;


-- 2020-11-06T08:59:20.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--ALTER SEQUENCE M_ShipmentSchedule_ExportAudit_SEQ RENAME TO M_ShipmentSchedule_ExportAudit_Item_SEQ
--;
--CREATE SEQUENCE M_ShipmentSchedule_ExportAudit_SEQ
--    MINVALUE 1000000
--    MAXVALUE 2147483647
--;



--create unique index m_shipmentschedule_exportaudit_item_m_shipmentschedule_id
--    on m_shipmentschedule_exportaudit_item (m_shipmentschedule_id, m_shipmentschedule_exportaudit_id)
--    where (isactive = 'Y'::bpchar);


-- 2020-11-06T16:31:08.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule_exportaudit_item','ExportStatus','VARCHAR(25)',null,'PENDING')
;

------------------

-- create a new m_shipmentschedule_exportaudit

create table m_shipmentschedule_exportaudit
(
    ad_client_id numeric(10) not null,
    ad_org_id numeric(10) not null,
    created timestamp with time zone not null,
    createdby numeric(10) not null,
    isactive char not null
        constraint m_shipmentschedule_exportaudit_isactive_check
            check (isactive = ANY (ARRAY['Y'::bpchar, 'N'::bpchar])),
    m_shipmentschedule_exportaudit_id numeric(10) not null
        constraint m_shipmentschedule_exportaudit_key
            primary key,
    transactionidapi varchar(255),
    updated timestamp with time zone not null,
    updatedby numeric(10) not null,
    exportstatus varchar(25) default 'PENDING'::character varying not null,
    ad_issue_id numeric(10),
    forwardeddata text,
    exportsequencenumber numeric(10)
);



-- 2020-11-06T09:16:49.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule_exportaudit','ExportSequenceNumber','NUMERIC(10)',null,null)
;

-- 2020-11-06T09:16:54.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule_exportaudit','ForwardedData','TEXT',null,null)
;

-- 2020-11-06T09:46:12.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule_exportaudit','TransactionIdAPI','VARCHAR(255)',null,null)
;

-- 2020-11-06T09:47:02.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule_exportaudit','TransactionIdAPI','VARCHAR(255)',null,null)
;

-- 2020-11-06T16:43:59.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule_exportaudit','AD_Issue_ID','NUMERIC(10)',null,null)
;

-- 2020-11-06T16:44:22.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS m_shipmentschedule_exportaudit_transactionidapi_uc
;

-- 2020-11-06T16:44:22.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_ShipmentSchedule_ExportAudit_TransactionIdAPI_UC ON M_ShipmentSchedule_ExportAudit (TransactionIdAPI) WHERE IsActive='Y'
;

ALTER TABLE public.M_ShipmentSchedule_ExportAudit_Item
    ADD COLUMN M_ShipmentSchedule_ExportAudit_ID NUMERIC(10)
;

ALTER TABLE M_ShipmentSchedule_ExportAudit_Item
    ADD CONSTRAINT MShipmentScheduleExportAudit_MShipmentScheduleExportAuditItem FOREIGN KEY (M_ShipmentSchedule_ExportAudit_ID) REFERENCES PUBLIC.M_ShipmentSchedule_ExportAudit DEFERRABLE INITIALLY DEFERRED
;

alter table m_shipmentschedule_exportaudit_item
	add constraint m_shipmentschedule_exportaudit_item_key
		primary key (m_shipmentschedule_exportaudit_item_id);

CREATE SEQUENCE M_ShipmentSchedule_ExportAudit_Item_SEQ
   MINVALUE 1000000
   MAXVALUE 2147483647
;

alter table m_shipmentschedule_exportaudit_item alter column m_shipmentschedule_exportaudit_item_id set default nextval('m_shipmentschedule_exportAudit_item_seq');

