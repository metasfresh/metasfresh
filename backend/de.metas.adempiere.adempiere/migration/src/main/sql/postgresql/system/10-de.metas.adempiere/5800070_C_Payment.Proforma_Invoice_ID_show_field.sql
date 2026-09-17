-- Run mode: SWING_CLIENT

-- Field: Zahlung(195,D) -> Zahlung(330,D) -> Proforma Rechnung
-- Column: C_Payment.Proforma_Invoice_ID
-- 2026-04-28T15:43:40.771Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592361,777462,0,330,TO_TIMESTAMP('2026-04-28 15:43:40.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Proforma Rechnung',TO_TIMESTAMP('2026-04-28 15:43:40.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-28T15:43:40.776Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777462 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-28T15:43:40.948Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584758)
;

-- 2026-04-28T15:43:40.973Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777462
;

-- 2026-04-28T15:43:40.979Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777462)
;

-- Field: Zahlung(195,D) -> Zahlung(330,D) -> Proforma Rechnung
-- Column: C_Payment.Proforma_Invoice_ID
-- 2026-04-28T15:44:42.114Z
UPDATE AD_Field SET DisplayLogic='@Proforma_Invoice_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2026-04-28 15:44:42.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=777462
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> order invoice.Proforma Rechnung
-- Column: C_Payment.Proforma_Invoice_ID
-- 2026-04-28T15:45:43.102Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777462,0,330,540955,650093,'F',TO_TIMESTAMP('2026-04-28 15:45:42.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Proforma Rechnung',50,0,0,TO_TIMESTAMP('2026-04-28 15:45:42.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> order invoice.Proforma Rechnung
-- Column: C_Payment.Proforma_Invoice_ID
-- 2026-04-28T15:45:57.936Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2026-04-28 15:45:57.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=650093
;

-- Run mode: SWING_CLIENT

-- Column: C_Payment.Proforma_Invoice_ID
-- 2026-04-28T15:53:06.726Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2026-04-28 15:53:06.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592361
;

