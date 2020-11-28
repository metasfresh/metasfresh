
CREATE OR REPLACE VIEW MKTG_ContactPerson_With_Campaign_V AS
SELECT 
	c_p.MKTG_Campaign_ContactPerson_ID AS MKTG_ContactPerson_With_Campaign_ID,
	c.MKTG_Campaign_ID, 
	p.*
FROM MKTG_Campaign c
	JOIN MKTG_Campaign_ContactPerson c_p ON c_p.MKTG_Campaign_ID = c.MKTG_Campaign_ID AND c_p.IsActive='Y'
		JOIN MKTG_ContactPerson p ON p.MKTG_ContactPerson_ID= c_p.MKTG_ContactPerson_ID
;
