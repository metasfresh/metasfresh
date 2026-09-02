-- M_Delivery_Planning: ShipFrom_Location_ID (AD_Column 593414) and ShipTo_Location_ID
-- (AD_Column 593415) become Search (30) instead of Table (18).
--
-- A Table reference makes the filter widget a List, which the frontend preloads in full; a Search
-- reference is typeahead-only and paged. Over C_BPartner_Location that preload is the whole table,
-- which makes the window unusable wherever the partner count is large.
--
-- AD_Reference_Value_ID stays 159: neither column name follows the <Table>_ID convention, so the
-- reference value is what identifies C_BPartner_Location. IsSelectionColumn stays 'Y' -- the
-- columns remain filterable, which is the point.
--
-- Sequence number allocated from idserver.metas.de on 2026-09-02.

UPDATE AD_Column
SET    AD_Reference_ID = 30,
       Updated         = TO_TIMESTAMP('2026-09-02', 'YYYY-MM-DD'),
       UpdatedBy       = 100
WHERE  AD_Column_ID IN (593414, 593415)
AND    AD_Reference_ID <> 30
;
