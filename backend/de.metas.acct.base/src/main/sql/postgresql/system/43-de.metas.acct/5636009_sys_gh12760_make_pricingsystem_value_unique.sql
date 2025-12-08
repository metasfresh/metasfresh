CREATE TABLE backup.M_Pricingsystem_20220420 AS
SELECT * FROM M_Pricingsystem
;

SELECT make_unique('M_Pricingsystem', 'value', TRUE)
;
