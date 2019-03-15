-- 2019-03-11T12:19:36.298
-- #298 changing anz. stellen
UPDATE AD_Column SET AD_Element_ID=1876, ColumnName='BPValue', Description='Sponsor-Nr.', Help='Suchschlüssel für den Geschäftspartner', IsCalculated='N', Name='Nr.',Updated=TO_TIMESTAMP('2019-03-11 12:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7887
;

-- 2019-03-11T12:19:36.300
-- #298 changing anz. stellen
UPDATE AD_Field SET Name='Nr.', Description='Sponsor-Nr.', Help='Suchschlüssel für den Geschäftspartner' WHERE AD_Column_ID=7887
;



ALTER TABLE I_BPartner RENAME COLUMN Value to BPValue;