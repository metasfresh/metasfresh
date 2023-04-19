ALTER TABLE s_resourceassignment
    DROP CONSTRAINT IF EXISTS valid_date_range
;

ALTER TABLE s_resourceassignment
    ADD CONSTRAINT valid_date_range CHECK (assigndateto IS NULL OR assigndatefrom <= assigndateto)
;

