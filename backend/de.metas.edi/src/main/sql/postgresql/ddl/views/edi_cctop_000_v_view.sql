-- View: edi_cctop_000_v

-- DROP VIEW IF EXISTS edi_cctop_000_v;

CREATE OR REPLACE VIEW edi_cctop_000_v AS 
SELECT 
  l.c_bpartner_location_id AS edi_cctop_000_v_id, 
  l.c_bpartner_location_id, 
  bp.EdiInvoicRecipientGLN,
  l.ad_client_id, 
  l.ad_org_id, 
  l.created, 
  l.createdby, 
  l.updated, 
  l.updatedby, 
  l.isactive
FROM c_bpartner_location l 
  INNER JOIN C_BPartner bp ON bp.C_BPartner_ID=l.C_BPartner_ID;
