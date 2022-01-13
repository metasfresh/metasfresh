

CREATE TABLE backup.c_countryarea_assign_gh11232_07062021
AS
    SELECT * from c_countryarea_assign;





UPDATE c_countryarea_assign
SET AD_Client_ID = 0,
    AD_Org_ID = 0
WHERE c_countryarea_assign_id = 540038;  -- Kroatien (Hrvatska)