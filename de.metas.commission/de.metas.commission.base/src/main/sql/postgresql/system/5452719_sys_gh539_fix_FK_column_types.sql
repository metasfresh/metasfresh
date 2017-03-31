

ALTER TABLE public.c_sponsor RENAME COLUMN c_advcomrank_system_id TO c_advcomrank_system_id_OLD;
ALTER TABLE public.c_sponsor ADD COLUMN c_advcomrank_system_id  NUMERIC(10);
UPDATE c_sponsor SET c_advcomrank_system_id=c_advcomrank_system_id_OLD::NUMERIC(10);
COMMIT;
ALTER TABLE c_sponsor DROP COLUMN c_advcomrank_system_id_OLD;
