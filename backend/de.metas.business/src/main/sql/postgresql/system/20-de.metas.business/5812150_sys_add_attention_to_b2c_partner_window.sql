-- Add the C_BPartner_Location.Attention ("z. Hd.") field to the "Geschäftspartner B2C"
-- window (540354), Adresse tab (540847), for consistency with the other partner windows
-- that already expose it. Attention is a core column (592663) and B2C is a core window,
-- so this field-add belongs in the metasfresh core repo.

-- Field: Geschäftspartner B2C(540354) -> Adresse(540847) -> z. Hd.
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592663,781323 /*From ID Server*/,0,540847,TO_TIMESTAMP('2026-07-03 12:00:10','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zu Händen, Türcode oder weitere Pflichtinformationen für das Versandetikett (max. 30 Zeichen)',30,'D','Y','N','N','N','N','N','N','N','z. Hd.',TO_TIMESTAMP('2026-07-03 12:00:10','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781323 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584922)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781323
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(781323)
;

-- UI Element: Geschäftspartner B2C(540354) -> Adresse(540847) -> default -> z. Hd.
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781323,0,540847,540897,652432 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-03 12:00:11','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zu Händen, Türcode oder weitere Pflichtinformationen für das Versandetikett (max. 30 Zeichen)','Y','Y','N','Y','N','N','N',0,'z. Hd.',25,0,0,TO_TIMESTAMP('2026-07-03 12:00:11','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
