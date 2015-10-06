-- View: edi_cctop_111_v

-- DROP VIEW IF EXISTS edi_cctop_111_v;

CREATE OR REPLACE VIEW edi_cctop_111_v AS 
 SELECT o.c_order_id AS edi_cctop_111_v_id, o.c_order_id, 
        CASE
            WHEN o.poreference::text <> ''::text AND o.poreference IS NOT NULL AND o.dateordered IS NOT NULL THEN o.poreference
            ELSE NULL::character varying
        END AS poreference, 
        CASE
            WHEN o.poreference::text <> ''::text AND o.poreference IS NOT NULL AND o.dateordered IS NOT NULL THEN o.dateordered
            ELSE NULL::timestamp without time zone
        END AS dateordered, 
        s.m_inout_id, 
        s.documentno AS shipment_documentno, --// *not* send in edi-marshal-compudata-fresh.ftl, so won't do harm; TODO replace with EDI_DESADV
        s.movementdate, 
        o.ad_client_id, 
        o.ad_org_id, 
        o.created, 
        o.createdby, 
        o.updated, 
        o.updatedby, 
        o.isactive
   FROM c_order o
   JOIN m_inout s ON s.c_order_id = o.c_order_id;

ALTER TABLE edi_cctop_111_v
  OWNER TO adempiere;

