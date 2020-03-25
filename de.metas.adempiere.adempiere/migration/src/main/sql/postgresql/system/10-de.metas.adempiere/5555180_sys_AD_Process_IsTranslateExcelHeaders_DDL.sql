-- note that our postgres 9.5. does not yet have "ADD COLUMN IF NOT EXISTS"
DO $$
BEGIN
ALTER TABLE public.AD_Process ADD COLUMN IsTranslateExcelHeaders CHAR(1) DEFAULT 'N' CHECK (IsTranslateExcelHeaders IN ('Y','N')) NOT NULL
;
EXCEPTION WHEN SQLSTATE '42701' THEN
    RAISE NOTICE 'At least one column already existed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
END
$$;
