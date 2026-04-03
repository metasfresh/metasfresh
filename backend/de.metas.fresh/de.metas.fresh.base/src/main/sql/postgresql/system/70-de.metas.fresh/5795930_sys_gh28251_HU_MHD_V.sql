DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.HU_MHD_V
;

CREATE VIEW de_metas_endcustomer_fresh_reports.HU_MHD_V
            (HU_UnitType,
             M_HU_ID,
             PackingInstruction,
             Product,
             Vendor,
             Origin,
             ExternalProductCode,
             ProductNo,
             HUStatus,
             LU_Quantity,
             TU_CU_Quantity,
             StorageLocation,
             LotNumber,
             SupplierLotNumber,
             MHD,
             SourceReleaseStatus)
AS
WITH foo AS (SELECT CASE
                        WHEN hu_p1.m_hu_id IS NOT NULL AND hu_p2.m_hu_id IS NULL THEN 'TU'
                        WHEN hu_p1.m_hu_id IS NULL AND piv.hu_unittype = 'V'     THEN 'CU'
                                                                                 ELSE piv.hu_unittype
                    END                       AS hu_unittype,
                    hu.m_hu_id                AS m_hu_id,
                    CASE
                        WHEN hui.itemtype = 'HA' AND hui.qty <> 0 THEN CONCAT(hui.qty::text, ' ', hupii1_included.name, ' x ', ROUND(hus.qty / hui.qty, stockuom.stdprecision), ' ', stockuom.uomsymbol)
                        WHEN hui.itemtype = 'HA'                  THEN CONCAT(hui.qty::text, ' ', hupii1_included.name)
                        WHEN piv.hu_unittype = 'TU'               THEN CONCAT(piv.name, ' x ', ROUND(hus.qty, stockuom.stdprecision), ' ', stockuom.uomsymbol)
                                                                  ELSE piv.name
                    END                       AS PackingInstruction,
                    mp.name                   AS product,
                    bpvendor.name             AS Vendor,
                    origin.value              AS Origin,
                    mp.externalid             AS ExternalProductCode,
                    mp.value                  AS ProductNo,
                    hustatus.name             AS HUStatus,
                    CASE
                        WHEN piv.hu_unittype = 'LU' THEN hus.qty::text
                                                    ELSE ''
                    END                       AS LU_Quantity,
                    CASE
                        WHEN piv.hu_unittype = 'LU' THEN ''
                                                    ELSE hus.qty::text
                    END                       AS TU_CU_Quantity,
                    huloc.value               AS StorageLocation,
                    charge.value              AS LotNumber,
                    charge_vendor.value       AS SupplierLotNumber,
                    mhd.valuedate::date       AS MHD,
                    hulu_clearancestatus.name AS SourceReleaseStatus,
                    hu.m_hu_item_parent_id,
                    CASE
                        WHEN hu_p2.m_hu_id IS NOT NULL THEN hu_p2.m_hu_id * 100 + 2
                        WHEN hu_p1.m_hu_id IS NOT NULL THEN hu_p1.m_hu_id * 100 + 1
                                                       ELSE hu.m_hu_id * 100
                    END                       AS order_no
             FROM m_hu hu
                      LEFT JOIN m_hu_pi_version piv ON hu.m_hu_pi_version_id = piv.m_hu_pi_version_id
                      LEFT JOIN m_hu_storage hus ON hu.m_hu_id = hus.m_hu_id
                      LEFT JOIN m_locator huloc ON hu.m_locator_id = huloc.m_locator_id
                      LEFT JOIN m_product mp ON hus.m_product_id = mp.m_product_id
                      LEFT JOIN c_uom stockuom ON stockuom.c_uom_id = mp.c_uom_id
                      LEFT JOIN ad_ref_list hustatus
                                ON hustatus.ad_reference_id = 540478::numeric AND hustatus.value::bpchar = hu.hustatus
                      LEFT JOIN ad_ref_list hulu_clearancestatus ON hulu_clearancestatus.ad_reference_id = 541540::numeric AND
                                                                    hulu_clearancestatus.value::text = hu.clearancestatus::text
                      LEFT JOIN m_hu_attribute charge ON charge.m_hu_id = hu.m_hu_id AND charge.m_attribute_id = 1000017::numeric
                      LEFT JOIN m_hu_attribute mhd ON mhd.m_hu_id = hu.m_hu_id AND mhd.m_attribute_id = 540020::numeric
                      LEFT JOIN m_hu_attribute charge_vendor ON charge_vendor.value NOT IN ('0', '0.000') AND charge_vendor.m_hu_id = hu.m_hu_id AND charge_vendor.m_attribute_id = 1000029::numeric
                      LEFT JOIN m_hu_attribute vendor ON vendor.value NOT IN ('0', '0.000') AND vendor.m_hu_id = hu.m_hu_id AND vendor.m_attribute_id = 540017::numeric --Unterlieferant
                      LEFT JOIN m_hu_attribute origin ON origin.value NOT IN ('0', '0.000') AND origin.m_hu_id = hu.m_hu_id AND origin.m_attribute_id = 1000001::numeric --Herkunft
                      LEFT JOIN c_bpartner bpvendor ON bpvendor.c_bpartner_id = vendor.valuenumber
                      LEFT JOIN m_hu_item hui ON hui.m_hu_item_id = hu.m_hu_item_parent_id
                      LEFT JOIN m_hu hu_p1 ON hu_p1.m_hu_id = hui.m_hu_id
                      LEFT JOIN m_hu_pi_version piv_p1 ON hu_p1.m_hu_pi_version_id = piv_p1.m_hu_pi_version_id
                      LEFT JOIN m_hu_item hui_p1 ON hui_p1.m_hu_item_id = hu_p1.m_hu_item_parent_id
                      LEFT JOIN m_hu_pi_item hupii1 ON hui.m_hu_pi_item_id = hupii1.m_hu_pi_item_id
                      LEFT JOIN m_hu_pi hupii1_included ON hupii1.included_hu_pi_id = hupii1_included.m_hu_pi_id
                      LEFT JOIN m_hu hu_p2 ON hu_p2.m_hu_id = hui_p1.m_hu_id
             WHERE TRUE
               AND hu.hustatus = 'A'::bpchar
               AND NOT (hu_p2.m_hu_id IS NOT NULL AND piv.hu_unittype = 'V') --exclude cu if below lu-tu
               AND NOT (hu_p1.m_hu_id IS NOT NULL AND piv.hu_unittype = 'V' AND piv_p1.hu_unittype = 'TU') --exclude cu if below tu
    --AND piv.name::text <> 'No Packing Item'::text
)
SELECT foo.hu_unittype,
       foo.m_hu_id::text,
       foo.PackingInstruction,
       foo.product,
       foo.Vendor,
       foo.Origin,
       foo.ExternalProductCode,
       foo.ProductNo,
       foo.HUStatus,
       foo.LU_Quantity,
       foo.TU_CU_Quantity,
       foo.StorageLocation,
       foo.LotNumber,
       foo.SupplierLotNumber,
       foo.MHD,
       foo.SourceReleaseStatus
FROM foo
ORDER BY order_no
;
