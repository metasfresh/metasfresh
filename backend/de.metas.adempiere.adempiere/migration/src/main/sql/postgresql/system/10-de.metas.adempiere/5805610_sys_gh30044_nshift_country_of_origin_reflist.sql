-- nShift: new mapping attribute source value CountryOfOrigin

-- AD_Ref_List: CountryOfOrigin
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,
                         Created,CreatedBy,Description,EntityType,IsActive,Name,
                         Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544240 /*From ID Server*/,542001,
        TO_TIMESTAMP('2026-06-01 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,'D','Y','Herkunftsland',
        TO_TIMESTAMP('2026-06-01 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'CountryOfOrigin','CountryOfOrigin');

-- AD_Ref_List_Trl skeleton for CountryOfOrigin
INSERT INTO AD_Ref_List_Trl (AD_Client_ID,AD_Org_ID,AD_Language,AD_Ref_List_ID,
                             Created,CreatedBy,Description,IsActive,IsTranslated,Name,
                             Updated,UpdatedBy)
SELECT 0,0,l.AD_Language,544240 /*From ID Server*/,
       TO_TIMESTAMP('2026-06-01 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       NULL,'Y','N','Herkunftsland',
       TO_TIMESTAMP('2026-06-01 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100
FROM AD_Language l WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID=544240 AND tt.AD_Language=l.AD_Language);

-- English translation for CountryOfOrigin
UPDATE AD_Ref_List_Trl SET Name='Country of Origin', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-01 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544240 AND AD_Language='en_US';

-- de_DE and de_CH share the base German text — mark as translated
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-01 10:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544240 AND AD_Language IN ('de_DE','de_CH');
