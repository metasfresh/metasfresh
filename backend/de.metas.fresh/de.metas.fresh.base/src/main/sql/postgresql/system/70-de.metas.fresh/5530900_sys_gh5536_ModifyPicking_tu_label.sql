DROP FUNCTION IF EXISTS report.picking_tu_label( IN p_M_HU_ID numeric);

DROP FUNCTION IF EXISTS report.picking_tu_label( IN p_M_HU_ID numeric, IN p_C_BPartner_Location_ID numeric );

CREATE FUNCTION report.picking_tu_label(IN p_M_HU_ID numeric, p_C_BPartner_Location_ID numeric)
  RETURNS TABLE
  (
    org_name    Character Varying,
    bp_gln      Character Varying,
    bp_gln_9_12 text,
    addr_name   Character Varying,
    address     text,
    barcode     text
  )
AS
$$
SELECT

  org.Name                                                                                     as org_name,
  COALESCE(NULLIF(bp_loc.gln, ''), '0000000000000')                                            as bp_gln,
  substring(COALESCE(NULLIF(bp_loc.gln, ''), '0000000000000') from 9 for 4)                    as bp_gln_9_12,
  bp_loc.name                                                                                  as addr_name,
  COALESCE(bp.CompanyName, bp.Name) || ', ' || loc.address1 || ', ' || loc.Postal || ' ' || loc.City,
  --AI 413 Ship to – Deliver to – Forward to EAN.UCC Global Location Number
  report.get_barcode(pc.M_HU_ID) || '413' || COALESCE(NULLIF(bp_loc.gln, ''), '0000000000000') as barcode


FROM m_picking_candidate pc


  JOIN AD_Org org ON pc.AD_Org_ID = org.AD_Org_ID AND org.isActive = 'Y'
  JOIN M_ShipmentSchedule ss ON pc.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID


  LEFT JOIN C_BPartner bp
    ON COALESCE(ss.C_BPartner_Override_ID, ss.C_BPartner_ID) = bp.C_BPartner_ID AND bp.isActive = 'Y'

  LEFT JOIN C_BPartner_Location bp_loc
    ON COALESCE(ss.C_BP_Location_Override_ID, ss.C_BPartner_Location_ID) = bp_loc.C_BPartner_Location_ID AND
       bp_loc.isActive = 'Y'
  LEFT JOIN C_Location loc ON bp_loc.C_Location_ID = loc.C_Location_ID AND loc.isActive = 'Y'

WHERE
  CASE
  WHEN p_M_HU_ID IS NOT NULL
    THEN pc.M_HU_ID = p_M_HU_ID
  WHEN p_C_BPartner_Location_ID IS NOT NULL
    THEN bp_loc.C_BPartner_Location_ID = p_C_BPartner_Location_ID
  ELSE FALSE -- shall never nappen
  END

$$
LANGUAGE sql
STABLE;