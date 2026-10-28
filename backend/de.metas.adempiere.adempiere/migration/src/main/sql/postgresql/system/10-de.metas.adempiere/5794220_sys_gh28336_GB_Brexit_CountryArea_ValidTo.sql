-- gh#28336: Set validTo for GB in EU country area (Brexit transition ended 2020-12-31)
UPDATE C_CountryArea_Assign
SET ValidTo       = '2020-12-31',
    Updated       = '2026-03-12 21:00',
    UpdatedBy     = 0
WHERE C_CountryArea_ID = (SELECT C_CountryArea_ID FROM C_CountryArea WHERE value = 'EU' AND isactive = 'Y')
  AND C_Country_ID = (SELECT C_Country_ID FROM C_Country WHERE CountryCode = 'GB');
