
INSERT INTO AD_InputDataSource (
    ad_client_id, -- numeric(10,0) NOT NULL,
    ad_inputdatasource_id, -- numeric(10,0) NOT NULL,
    ad_org_id, -- numeric(10,0) NOT NULL,
    created, -- timestamp with time zone NOT NULL,
    createdby, -- numeric(10,0) NOT NULL,
    description, -- character varying(255) COLLATE pg_catalog."default",
    isactive, -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'Y'::bpchar,
    m_product_id, -- numeric(10,0),
    name, -- character varying(60) COLLATE pg_catalog."default" NOT NULL,
    updated, -- timestamp with time zone NOT NULL,
    updatedby, -- numeric(10,0) NOT NULL,
    internalname, -- character varying(150) COLLATE pg_catalog."default",
    a_asset_id, -- numeric(10,0),
    isdestination, -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'N'::bpchar,
    entitytype, -- character varying(512) COLLATE pg_catalog."default" NOT NULL DEFAULT 'de.metas.swat'::character varying,
    isedienabled -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'N'::bpchar,
) SELECT
    1000000 AS ad_client_id, -- numeric(10,0) NOT NULL,
    nextval('ad_inputdatasource_seq'), -- numeric(10,0) NOT NULL,
    0 AS ad_org_id, -- numeric(10,0) NOT NULL,
    now() AS created, -- timestamp with time zone NOT NULL,
    0 AS createdby, -- numeric(10,0) NOT NULL,
    'See issue https://github.com/metasfresh/metasfresh/issues/4621' AS description, -- character varying(255) COLLATE pg_catalog."default",
    'Y' AS isactive, -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'Y'::bpchar,
    null AS m_product_id, -- numeric(10,0),
    'forum-datenaustausch.ch Invoice' AS name, -- character varying(60) COLLATE pg_catalog."default" NOT NULL,
    now() AS updated, -- timestamp with time zone NOT NULL,
    0 AS updatedby, -- numeric(10,0) NOT NULL,
    'SOURCE.de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.ImportInvoice440RestController' AS internalname, -- character varying(150) COLLATE pg_catalog."default",
    null AS a_asset_id, -- numeric(10,0),
    'N' AS isdestination, -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'N'::bpchar,
    'de.metas.vertical.healthcare.forum_datenaustausch_ch' AS entitytype, -- character varying(512) COLLATE pg_catalog."default" NOT NULL DEFAULT 'de.metas.swat'::character varying,
    'N' AS isedienabled -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'N'::bpchar,
;
-- 2018-10-04T08:07:18.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=1999,Updated=TO_TIMESTAMP('2018-10-04 08:07:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559491
;

-- 2018-10-04T08:07:21.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product','CustomerLabelName','VARCHAR(1999)',null,null)
;

