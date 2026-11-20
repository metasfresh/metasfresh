-- CU quantity entry: description for the picking profile field "Scannable Code Format" (AD_Field 788188, MobileUI_UserProfile_Picking.C_ScannableCode_Format_ID),
-- so the profile user can tell it is the format of the CU label. Same approach as AD_Element 585623 for the customer field: Name / PrintName are copied, per
-- language, from AD_Element 583610, only the description is field-specific.
-- Also: the en_US description translation of AD_Element 585623 is marked translated.

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Description,Updated,UpdatedBy) SELECT 0,585624 /*From ID Server*/,0,NULL,TO_TIMESTAMP('2026-11-19 09:00:00.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',e.Name,e.PrintName,'Format des Etiketts, das in den CU-Mengeneingabe-Modi gelesen wird.',TO_TIMESTAMP('2026-11-19 09:00:00.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100 FROM AD_Element e WHERE e.AD_Element_ID=583610
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Name,PrintName,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.Name,t.PrintName,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585624 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl t SET Name=src.Name, PrintName=src.PrintName, IsTranslated=src.IsTranslated, Updated=TO_TIMESTAMP('2026-11-19 09:00:01.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 FROM AD_Element_Trl src WHERE src.AD_Element_ID=583610 AND src.AD_Language=t.AD_Language AND t.AD_Element_ID=585624
;
UPDATE AD_Element_Trl SET Description='Format des Etiketts, das in den CU-Mengeneingabe-Modi gelesen wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-19 09:00:02.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=585624
;
UPDATE AD_Element_Trl SET Description='Format of the CU label read in the CU quantity entry modes.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-19 09:00:03.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585624
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585624)
;
UPDATE AD_Field SET AD_Name_ID=585624, Updated=TO_TIMESTAMP('2026-11-19 09:00:04.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=788188
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585624)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=788188
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(788188)
;

-- AD_Element 585623 (customer field): en_US description is a real translation
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-19 09:00:05.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585623
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585623)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585623)
;
