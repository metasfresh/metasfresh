-- AD_Org.IsDropShipTaxFromDestCountry is an organisation setting (a Y/N switch): not personal data.

-- Column: AD_Org.IsDropShipTaxFromDestCountry
UPDATE AD_Column SET PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2026-11-27 20:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=593864
;
