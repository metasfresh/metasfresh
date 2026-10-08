-- Migration: Clear stuck C_BPartner_ID on Active VHUs
--
-- Problem: On-the-fly picking sets C_BPartner_ID on VHUs, but shipment reversal
-- and picking cancellation did not clear it. This leaves VHUs invisible to
-- picking for other customers because the storage query filters by BPartner.
--
-- Safety: Only clears VHUs that are NOT:
--   - Reserved (M_HU.IsReserved='Y' or has active M_HU_Reservation)
--   - Actively picked (active M_ShipmentSchedule_QtyPicked with M_InOutLine)
--   - Assigned to a non-reversed shipment

-- Step 1: Backup affected M_HU rows before modification
SELECT backup_table('m_hu', '_stuck_bpartner');

-- Step 2: Clear C_BPartner_ID and C_BPartner_Location_ID on stuck VHUs
UPDATE m_hu
SET c_bpartner_id         = NULL,
    c_bpartner_location_id = NULL,
    updated                = '2026-02-12 21:00'::timestamp,
    updatedby              = 99
WHERE m_hu_id IN (
    SELECT hu.m_hu_id
    FROM m_hu hu
        JOIN m_hu_pi_version piv ON piv.m_hu_pi_version_id = hu.m_hu_pi_version_id
        JOIN m_hu_storage hus ON hus.m_hu_id = hu.m_hu_id
    WHERE piv.m_hu_pi_id = 101                          -- VHU only
      AND hu.hustatus = 'A'                             -- Active
      AND hu.isactive = 'Y'
      AND hu.c_bpartner_id IS NOT NULL
      AND hu.c_bpartner_id > 0
      AND hus.qty > 0
      -- Exclude reserved HUs
      AND hu.isreserved = 'N'
      AND NOT EXISTS (
          SELECT 1 FROM m_hu_reservation res
          WHERE res.vhu_id = hu.m_hu_id
            AND res.isactive = 'Y'
      )
      -- Exclude actively picked HUs (with active QtyPicked linked to a shipment line)
      AND NOT EXISTS (
          SELECT 1 FROM m_shipmentschedule_qtypicked sqp
          WHERE sqp.vhu_id = hu.m_hu_id
            AND sqp.isactive = 'Y'
            AND sqp.m_inoutline_id IS NOT NULL
      )
      -- Exclude HUs assigned to a non-reversed completed shipment
      AND NOT EXISTS (
          SELECT 1 FROM m_hu_assignment ha
              JOIN m_inoutline iol ON iol.m_inoutline_id = ha.record_id
              JOIN m_inout io ON io.m_inout_id = iol.m_inout_id
          WHERE ha.m_hu_id = hu.m_hu_id
            AND ha.ad_table_id = (SELECT ad_table_id FROM ad_table WHERE tablename = 'M_InOutLine')
            AND io.issotrx = 'Y'
            AND io.docstatus = 'CO'
            AND io.reversal_id IS NULL
      )
);
