DROP VIEW report.fresh_Pricelist_Version_Val_Rule;

CREATE OR REPLACE VIEW report.fresh_Pricelist_Version_Val_Rule AS
SELECT plv.m_pricelist_version_id,
       ps.c_bpartner_id,
       plv.validfrom,
       plv.name,
       CASE
           WHEN (pl.issopricelist = 'Y' AND ps.PsIsSOTrx = 'Y') THEN 'Y'
           WHEN (pl.issopricelist = 'N' AND ps.PsIsSOTrx = 'N') THEN 'N'
                                                                ELSE '?'
       END AS IsSOTrx
FROM (SELECT bp.C_BPartner_ID, COALESCE(bp.M_PricingSystem_ID, bpg.M_PricingSystem_ID) AS M_PricingSystem_ID, 'Y':: character AS PsIsSOTrx
      FROM C_BPartner bp
               LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
      UNION
      SELECT bp.C_BPartner_ID, COALESCE(bp.PO_PricingSystem_ID, bpg.PO_PricingSystem_ID) AS M_PricingSystem_ID, 'N'::character AS PsIsSOTrx
      FROM C_BPartner bp
               LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID) ps
         LEFT OUTER JOIN M_PriceList pl ON ps.M_PricingSystem_ID = pl.M_PricingSystem_ID
         LEFT OUTER JOIN M_PriceList_Version plv ON pl.M_PriceList_ID = plv.M_PriceList_ID
WHERE plv.IsActive = 'Y'::bpchar
;


COMMENT ON VIEW report.fresh_Pricelist_Version_Val_Rule IS 'Returns all currently valid C_PriceList_Version_IDs for all C_BPartner_ID along with the valid-from-date and price list version name'
;