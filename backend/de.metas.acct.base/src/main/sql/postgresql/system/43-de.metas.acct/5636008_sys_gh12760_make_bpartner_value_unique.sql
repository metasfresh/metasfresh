CREATE TABLE backup.c_bpartner_20220420 AS
SELECT * FROM c_bpartner
;

SELECT make_unique('C_BPartner', 'value', TRUE)
;
