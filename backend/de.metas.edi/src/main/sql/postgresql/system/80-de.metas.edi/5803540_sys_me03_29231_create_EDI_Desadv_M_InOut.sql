-- DDL: backend/de.metas.edi/src/main/sql/postgresql/system/80-de.metas.edi/5803540_sys_me03_29231_create_EDI_Desadv_M_InOut.sql
-- Junction table linking EDI_Desadv to M_InOut (N:M relationship)
-- For me03#29231 — Option A: N:M junction instead of single-FK on M_InOut

/* DDL */ CREATE TABLE public.edi_desadv_m_inout (
    edi_desadv_m_inout_id numeric(10) NOT NULL PRIMARY KEY,
    edi_desadv_id numeric(10) NOT NULL REFERENCES public.edi_desadv(edi_desadv_id) DEFERRABLE INITIALLY DEFERRED,
    m_inout_id numeric(10) NOT NULL REFERENCES public.m_inout(m_inout_id) DEFERRABLE INITIALLY DEFERRED,
    ad_client_id numeric(10) NOT NULL,
    ad_org_id numeric(10) NOT NULL,
    created timestamp with time zone NOT NULL DEFAULT now(),
    createdby numeric(10) NOT NULL,
    updated timestamp with time zone NOT NULL DEFAULT now(),
    updatedby numeric(10) NOT NULL,
    isactive char(1) NOT NULL DEFAULT 'Y' CHECK (isactive IN ('Y','N')),
    CONSTRAINT edi_desadv_m_inout_un UNIQUE (edi_desadv_id, m_inout_id)
)
;

CREATE SEQUENCE edi_desadv_m_inout_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

CREATE INDEX edi_desadv_m_inout_minout_idx ON public.edi_desadv_m_inout(m_inout_id)
;

CREATE INDEX edi_desadv_m_inout_desadv_idx ON public.edi_desadv_m_inout(edi_desadv_id)
;
