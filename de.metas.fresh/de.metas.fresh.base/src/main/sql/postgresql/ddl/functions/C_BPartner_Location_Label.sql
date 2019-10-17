DROP FUNCTION IF EXISTS report.C_BPartner_Location_Label( IN p_C_BPartner_Location_ID numeric, IN copies numeric );

CREATE FUNCTION report.C_BPartner_Location_Label(p_C_BPartner_Location_ID numeric, IN copies numeric)
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
SELECT t.*
FROM generate_series(1, copies) i
  CROSS JOIN LATERAL
             (
             SELECT
               org.Name                                                                                as org_name,
                COALESCE(NULLIF(TRIM(bp_loc.gln), ''),
                        '0000000000000')                                                               as bp_gln,
               substring(COALESCE(NULLIF(TRIM(bp_loc.gln), ''), '0000000000000') from 9 for
                         4)                                                                            as bp_gln_9_12,
               bp_loc.name                                                                             as addr_name,
               COALESCE(bp.CompanyName, bp.Name) || ', ' || loc.address1 || ', ' || loc.Postal || ' ' || loc.City,
               --AI 413 Ship to – Deliver to – Forward to EAN.UCC Global Location Number
               report.get_barcode(bp_loc.C_BPartner_Location_ID) || '413' || COALESCE(NULLIF(bp_loc.gln, ''),
                                                                                      '0000000000000') as barcode


             FROM C_BPartner_Location bp_loc
               JOIN AD_Org org ON bp_loc.AD_Org_ID = org.AD_Org_ID AND org.isActive = 'Y'
               JOIN C_BPartner bp on bp.C_BPartner_id = bp_loc.C_Bpartner_id
               LEFT JOIN C_Location loc ON bp_loc.C_Location_ID = loc.C_Location_ID AND loc.isActive = 'Y'

             WHERE bp_loc.C_BPartner_Location_ID = p_C_BPartner_Location_ID
             ) t
$$
LANGUAGE sql
STABLE;