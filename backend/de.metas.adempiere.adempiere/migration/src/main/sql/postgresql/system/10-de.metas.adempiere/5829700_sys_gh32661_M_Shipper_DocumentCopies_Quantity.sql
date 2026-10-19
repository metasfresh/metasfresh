-- Run mode: SWING_CLIENT

-- M_Shipper.DocumentCopies: declare the column as Quantity (29) instead of Integer (11).
-- Reason: the WebUI stores an emptied Integer field as 0, which for this column means "print nothing";
-- an emptied Quantity field is stored as NULL ("not set" = fall back to the business partner / doc type copies),
-- while a typed 0 stays 0. Same pattern as the nullable *_Override quantity columns.
-- Still nullable, no default, ValueMin 0.

-- Column: M_Shipper.DocumentCopies
-- 2026-10-19T14:00:00.000Z
UPDATE AD_Column SET AD_Reference_ID=29, Updated=TO_TIMESTAMP('2026-10-19 14:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593749
;

-- Physical column: unconstrained NUMERIC like the other Quantity columns, still nullable without default
-- 2026-10-19T14:00:01.000Z
INSERT INTO t_alter_column values('m_shipper','DocumentCopies','NUMERIC',null,null)
;
