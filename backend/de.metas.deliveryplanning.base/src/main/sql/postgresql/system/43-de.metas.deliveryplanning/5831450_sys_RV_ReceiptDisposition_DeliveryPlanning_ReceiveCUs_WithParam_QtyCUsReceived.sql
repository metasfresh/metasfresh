-- "CUs annehmen mit Menge" on the receipt-disposition delivery-planning window (AD_Process 585661):
-- its quantity parameter 543304 is no quantity per TU (there is no TU when receiving loose CUs).
-- Move it from element "Menge pro TU" (QtyCUsPerTU, 542492), which 5822600 copied from 541163, to "Menge CU"
-- (QtyCUsReceived, AD_Element 585563) - the element the receipt-schedule window's counterpart (AD_Process 540765)
-- uses. The Java process reads only QtyCUsReceived.
--
-- AD_Element 585563 is created by the migration 5831320_sys_PP_Order_Receipt_follows_packing_instruction.sql
-- (module de.metas.ui.web.base) as a plain INSERT, so this script must not create it as well: whichever of the two
-- ran second would fail on the duplicate primary key. It therefore requires the element to exist already.
--
-- Idempotent: every statement sets absolute values, so it is a no-op on a database where 543304 is already moved.
-- AD_Process_Para.ColumnName / Name are not synced from the element, hence set explicitly.

DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM AD_Element WHERE AD_Element_ID = 585563 AND ColumnName = 'QtyCUsReceived') THEN
            RAISE EXCEPTION 'AD_Element 585563 (QtyCUsReceived, "Menge CU") is missing: apply 5831320_sys_PP_Order_Receipt_follows_packing_instruction.sql first';
        END IF;
    END
$$
;

UPDATE AD_Process_Para
SET AD_Element_ID=585563,
    ColumnName='QtyCUsReceived',
    Name=(SELECT e.Name FROM AD_Element e WHERE e.AD_Element_ID = 585563),
    IsMandatory='Y',
    Updated=TO_TIMESTAMP('2026-10-23 17:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_Para_ID = 543304
  AND AD_Process_ID = 585661
  AND (AD_Element_ID IS DISTINCT FROM 585563
    OR ColumnName IS DISTINCT FROM 'QtyCUsReceived'
    OR Name IS DISTINCT FROM (SELECT e.Name FROM AD_Element e WHERE e.AD_Element_ID = 585563)
    OR IsMandatory IS DISTINCT FROM 'Y')
;

UPDATE AD_Process_Para_Trl t
SET Name=et.Name,
    IsTranslated=et.IsTranslated,
    Updated=TO_TIMESTAMP('2026-10-23 17:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
FROM AD_Element_Trl et
WHERE t.AD_Process_Para_ID = 543304
  AND et.AD_Element_ID = 585563
  AND et.AD_Language = t.AD_Language
  AND (t.Name IS DISTINCT FROM et.Name OR t.IsTranslated IS DISTINCT FROM et.IsTranslated)
;
