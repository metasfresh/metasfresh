/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


CREATE OR REPLACE VIEW EDI_M_HU_PI_Item_Product_Lookup_UPC_v AS
SELECT DISTINCT ON (lookup.UPC, bpLookup.GLN, bpLookup.storegln) lookup.M_HU_PI_Item_Product_ID,
                                                                 --    lookup.M_Product_ID, not outputting M_Product_ID because we didn't do it in the past; just added M_Product_ID brelow so we can exclude inactive and discontinued products
                                                                 lookup.C_BPartner_ID,
                                                                 lookup.UPC,
                                                                 lookup.IsActive,
                                                                 bpLookup.GLN      AS GLN,
                                                                 bpLookup.storegln AS StoreGLN
FROM (SELECT piip.M_HU_PI_Item_Product_ID,
             piip.M_Product_ID,
             piip.C_BPartner_ID,
             piip.GTIN AS UPC /* backwards compatibility*/,
             piip.IsActive
      FROM M_HU_PI_Item_Product piip
      WHERE piip.IsActive = 'Y'
      UNION
      SELECT piip.M_HU_PI_Item_Product_ID,
             piip.M_Product_ID,
             piip.C_BPartner_ID,
             piip.EAN_TU,
             piip.IsActive
      FROM M_HU_PI_Item_Product piip
      WHERE piip.IsActive = 'Y'
      UNION
      SELECT piip.M_HU_PI_Item_Product_ID,
             piip.M_Product_ID,
             piip.C_BPartner_ID,
             piip.UPC,
             piip.IsActive
      FROM M_HU_PI_Item_Product piip
      WHERE piip.IsActive = 'Y'
      UNION
      SELECT 101 AS M_HU_PI_Item_Product_ID, -- fallback to "No Packing Item"
             bpp.M_Product_ID,
             bpp.C_BPartner_ID,
             bpp.EAN_CU,
             bpp.IsActive
      FROM C_BPartner_Product bpp
      WHERE bpp.IsActive = 'Y'
      UNION
      SELECT 101 AS M_HU_PI_Item_Product_ID, -- fallback to "No Packing Item"
             bpp.M_Product_ID,
             bpp.C_BPartner_ID,
             bpp.UPC,
             bpp.IsActive
      FROM C_BPartner_Product bpp
      WHERE bpp.IsActive = 'Y') lookup
         JOIN m_product p ON p.m_product_id = lookup.m_product_id
         LEFT JOIN EDI_C_BPartner_Lookup_BPL_GLN_v bpLookup ON bpLookup.c_bpartner_id = lookup.c_bpartner_id
    OR lookup.c_bpartner_id IS NULL
WHERE lookup.UPC IS NOT NULL
  AND TRIM(BOTH ' ' FROM lookup.UPC) != ''
  AND lookup.isactive = 'Y'
  AND p.isactive = 'Y'
  AND p.discontinued = 'N'
ORDER BY lookup.UPC, bpLookup.GLN, bpLookup.storegln,
         lookup.c_bpartner_id NULLS LAST /*prefer records that have the bpartner set explicitly*/,
         lookup.M_HU_PI_Item_Product_ID DESC
;

COMMENT ON VIEW EDI_M_HU_PI_Item_Product_Lookup_UPC_v IS
    'Lookup of M_HU_PI_Item_Product_ID via a M_HU_PI_Item_Product''s GTIN, TU-EAN (column EAN_TU), UPC (UPC) or as fallback to M_HU_PI_Item_Product_ID=101 with C_BPartner_Product''s EAN_CU or UPC. Ignores inactive or discontinued products as well as inactive packing-instructions (TU-EANs/TU-UPCs/GTINs) and inactive BPartner-Product-Records (CU-EAN/CU-UPC).'
;
