
drop index if exists "c_referenceno_ESR";

CREATE INDEX "c_referenceno_ESR"
    ON c_referenceno ( ad_org_id, c_referenceno_type_id, referenceno)
;


SELECT public.db_alter_table('ESR_ImportLine','ALTER TABLE esr_importline ALTER COLUMN esrlinetext TYPE varchar(20000) USING esrlinetext::varchar(20000)')
;