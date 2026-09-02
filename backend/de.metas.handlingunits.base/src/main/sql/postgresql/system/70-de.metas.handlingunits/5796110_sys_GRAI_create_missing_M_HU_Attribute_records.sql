-- gh#28474 GRAI: Create missing M_HU_Attribute records for existing HUs
--
-- Problem: HUs created before the GRAI attribute was added to the PI template
-- don't have an M_HU_Attribute record for GRAI (M_Attribute_ID=540192).
-- The Java code (HUAttributesBL.updateHUAttribute) silently skips the update
-- if the record doesn't exist (hasAttribute check returns false).
--
-- Prerequisite: 5795460_add_GRAI_attr_to_PI_template.sql already added
-- the GRAI attribute to the Template PI (M_HU_PI_Version_ID=100).
--
-- Fix: Insert M_HU_Attribute records for existing TUs and VHUs (not LUs) that are missing the GRAI attribute.
-- LUs are excluded because GRAIs are stored on TUs (one per TU) and VHUs (comma-separated for aggregate blocks).
INSERT INTO M_HU_Attribute (AD_Client_ID, AD_Org_ID,
                            Created, CreatedBy,
                            IsActive,
                            M_Attribute_ID,
                            M_HU_Attribute_ID,
                            M_HU_ID,
                            M_HU_PI_Attribute_ID,
                            Updated, UpdatedBy,
                            Value)
SELECT hu.AD_Client_ID,
       hu.AD_Org_ID,
       '2026-03-27',
       99,
       'Y',
       540192,                                        -- GRAI attribute
       nextval('m_hu_attribute_seq'),                 -- new PK
       hu.M_HU_ID,
       (SELECT pia.M_HU_PI_Attribute_ID
        FROM M_HU_PI_Attribute pia
        WHERE pia.M_HU_PI_Version_ID = 100
          AND pia.M_Attribute_ID = 540192
        LIMIT 1),                                     -- Template PI attribute
       '2026-03-27',
       99,
       NULL                                           -- no GRAI assigned yet
FROM M_HU hu
         JOIN M_HU_PI_Version piv ON piv.M_HU_PI_Version_ID = hu.M_HU_PI_Version_ID
WHERE hu.IsActive = 'Y'
  AND piv.HU_UnitType IN ('TU', 'V')                 -- TUs and VHUs only, exclude LUs
  AND NOT EXISTS (SELECT 1
                  FROM M_HU_Attribute hua
                  WHERE hua.M_HU_ID = hu.M_HU_ID
                    AND hua.M_Attribute_ID = 540192)
;
