
CREATE TABLE backup.BKP_ESR_ImportLine_Amount_11052022
AS
SELECT * FROM esr_importline;


SELECT public.db_alter_table('ESR_ImportLine','ALTER TABLE esr_importline ALTER COLUMN amount DROP DEFAULT');
;

SELECT public.db_alter_table('ESR_ImportLine','ALTER TABLE esr_importline ALTER COLUMN amount Type numeric USING amount::numeric');
;

SELECT public.db_alter_table('ESR_ImportLine','ALTER TABLE esr_importline ALTER COLUMN amount SET DEFAULT 0')
;
