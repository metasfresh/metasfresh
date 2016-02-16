

-- 11.02.2016 20:35
-- URL zum Konzept
ALTER TABLE C_BPartner ADD IsCreateDefaultPOReference CHAR(1) DEFAULT 'N' CHECK (IsCreateDefaultPOReference IN ('Y','N')) NOT NULL
;

-- 11.02.2016 20:36
-- URL zum Konzept
ALTER TABLE C_BPartner ADD POReferencePattern VARCHAR(40) DEFAULT NULL 
;

