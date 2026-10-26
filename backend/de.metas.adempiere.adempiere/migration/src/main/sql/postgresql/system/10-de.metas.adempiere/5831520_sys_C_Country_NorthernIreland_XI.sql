-- 2026-10-26T12:00:00Z
-- Northern Ireland as own country (code XI), so that it is printed as country on addresses (delivery note, invoice).
-- Address format, currency and bank-code layout are copied from United Kingdom (GB); like IC / EA, the 3-letter code is the parent's.
-- Not assigned to the EU country area => tax destination type is "outside country area", same as GB.
INSERT INTO C_Country (AD_Client_ID, AD_Language, AD_Org_ID, AllowCitiesOutOfList, C_Country_ID, C_Currency_ID, CountryCode, CountryCode_3digit, Created, CreatedBy, Description, DisplaySequence, DisplaySequenceLocal, CaptureSequence, HasPostal_Add, HasRegion, IsActive, IsAddressLinesLocalReverse, IsAddressLinesReverse, Name, Updated, UpdatedBy,
                       BankCodeCharType, BranchCodeCharType, AccountNumberLength, BranchCodeLength, BankCodeLength, AccountNumberCharType, BankCodeSeqNo, BranchCodeSeqNo, AccountNumberSeqNo)
SELECT 0, gb.AD_Language, 0, gb.AllowCitiesOutOfList, 540018 /*From ID Server*/, gb.C_Currency_ID, 'XI', gb.CountryCode_3digit, TO_TIMESTAMP('2026-10-26 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Northern Ireland, United Kingdom', gb.DisplaySequence, gb.DisplaySequenceLocal, gb.CaptureSequence, gb.HasPostal_Add, 'N', 'Y', gb.IsAddressLinesLocalReverse, gb.IsAddressLinesReverse, 'Northern Ireland', TO_TIMESTAMP('2026-10-26 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       gb.BankCodeCharType, gb.BranchCodeCharType, gb.AccountNumberLength, gb.BranchCodeLength, gb.BankCodeLength, gb.AccountNumberCharType, gb.BankCodeSeqNo, gb.BranchCodeSeqNo, gb.AccountNumberSeqNo
FROM C_Country gb
WHERE gb.CountryCode = 'GB'
  AND NOT EXISTS (SELECT 1 FROM C_Country c WHERE c.CountryCode = 'XI') -- XI may already exist, e.g. entered via UI; c_countrycode is unique
ON CONFLICT (C_Country_ID) DO NOTHING
;

-- 2026-10-26T12:00:00Z
INSERT INTO C_Country_Trl (AD_Language, C_Country_ID, Description, Name, RegionName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.C_Country_ID, t.Description, t.Name, t.RegionName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     C_Country t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.C_Country_ID = 540018
  AND NOT EXISTS (SELECT 1 FROM C_Country_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.C_Country_ID = t.C_Country_ID)
;

-- 2026-10-26T12:00:00Z
UPDATE C_Country_Trl
SET Name='Nordirland', Description='Nordirland, Vereinigtes Königreich', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-26 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE C_Country_ID = 540018
  AND AD_Language IN ('de_DE', 'de_CH')
;

-- 2026-10-26T12:00:00Z
UPDATE C_Country_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-26 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE C_Country_ID = 540018
  AND AD_Language = 'en_US'
;

-- 2026-10-26T12:00:00Z
-- Country-of-origin attribute value, as the C_Country interceptor creates it when a country is entered in the UI (value = CountryCode, name = Name)
INSERT INTO M_AttributeValue (AD_Client_ID, AD_Org_ID, Created, CreatedBy, IsActive, M_Attribute_ID, M_AttributeValue_ID, Name, Updated, UpdatedBy, Value)
SELECT 0, 0, TO_TIMESTAMP('2026-10-26 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', sc.Value::numeric, 540276 /*From ID Server*/, 'Northern Ireland', TO_TIMESTAMP('2026-10-26 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'XI'
FROM AD_SysConfig sc
WHERE sc.Name = 'de.metas.swat.CountryAttribute'
  AND sc.AD_Client_ID = 0
  AND sc.AD_Org_ID = 0
  AND NOT EXISTS (SELECT 1 FROM M_AttributeValue av WHERE av.M_Attribute_ID = sc.Value::numeric AND av.Value = 'XI')
ON CONFLICT (M_AttributeValue_ID) DO NOTHING
;

-- 2026-10-26T12:00:00Z
-- Northern Ireland in the EU country area: for goods it follows EU VAT rules since 2021-01-01 (Windsor Framework),
-- so e.g. a delivery DE -> XI is an intra-community supply. Starts when GB's EU assignment ends (2020-12-31).
INSERT INTO C_CountryArea_Assign (AD_Client_ID, AD_Org_ID, C_Country_ID, C_CountryArea_Assign_ID, C_CountryArea_ID, Created, CreatedBy, IsActive, Updated, UpdatedBy, ValidFrom)
SELECT 0, 0, c.C_Country_ID, 540085 /*From ID Server*/, ca.C_CountryArea_ID, TO_TIMESTAMP('2026-10-26 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', TO_TIMESTAMP('2026-10-26 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2021-01-01', 'YYYY-MM-DD')
FROM C_Country c,
     C_CountryArea ca
WHERE c.CountryCode = 'XI'
  AND ca.Value = 'EU'
  AND ca.AD_Client_ID = 0
  AND NOT EXISTS (SELECT 1 FROM C_CountryArea_Assign a WHERE a.C_CountryArea_ID = ca.C_CountryArea_ID AND a.C_Country_ID = c.C_Country_ID)
ON CONFLICT (C_CountryArea_Assign_ID) DO NOTHING
;
