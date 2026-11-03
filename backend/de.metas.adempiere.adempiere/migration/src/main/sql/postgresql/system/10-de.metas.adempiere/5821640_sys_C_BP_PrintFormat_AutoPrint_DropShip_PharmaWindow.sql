-- Mirror C_BP_PrintFormat.IsDropShip + C_BP_PrintFormat.IsAutoPrint (added by an earlier script in
-- this same folder) onto the second live window that also has its own "Druck Format" tab over
-- C_BP_PrintFormat: the vertical/pharma flavor of the business-partner window. Both columns already
-- exist; this script only adds the AD_Field + AD_UI_Element wiring for this second tab.

-- Field: Geschäftspartner Pharma(540409,U) -> Druck Format(541019,D) -> Abweichende Lieferadresse
-- Column: C_BP_PrintFormat.IsDropShip
-- 2026-09-01T11:00:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593458,783061 /*From ID Server*/,0,541019,TO_TIMESTAMP('2026-09-01 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Abweichende Lieferadresse',TO_TIMESTAMP('2026-09-01 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-01T11:00:01.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=783061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-09-01T11:00:02.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(2466)
;

-- 2026-09-01T11:00:03.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783061
;

-- 2026-09-01T11:00:04.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(783061)
;

-- Field: Geschäftspartner Pharma(540409,U) -> Druck Format(541019,D) -> Sofort drucken
-- Column: C_BP_PrintFormat.IsAutoPrint
-- 2026-09-01T11:00:05.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593459,783062 /*From ID Server*/,0,541019,TO_TIMESTAMP('2026-09-01 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Sofort drucken',TO_TIMESTAMP('2026-09-01 11:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-01T11:00:06.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=783062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-09-01T11:00:07.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(581849)
;

-- 2026-09-01T11:00:08.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783062
;

-- 2026-09-01T11:00:09.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(783062)
;

-- UI Element: Geschäftspartner Pharma(540409,U) -> Druck Format(541019,D) -> main -> 10 -> default.Abweichende Lieferadresse
-- Column: C_BP_PrintFormat.IsDropShip
-- 2026-09-01T11:00:10.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783061,0,541019,541438,653701 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-01 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Abweichende Lieferadresse',41,41,0,TO_TIMESTAMP('2026-09-01 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner Pharma(540409,U) -> Druck Format(541019,D) -> main -> 10 -> default.Sofort drucken
-- Column: C_BP_PrintFormat.IsAutoPrint
-- 2026-09-01T11:00:11.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783062,0,541019,541438,653702 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-01 11:00:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Sofort drucken',42,42,0,TO_TIMESTAMP('2026-09-01 11:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;
