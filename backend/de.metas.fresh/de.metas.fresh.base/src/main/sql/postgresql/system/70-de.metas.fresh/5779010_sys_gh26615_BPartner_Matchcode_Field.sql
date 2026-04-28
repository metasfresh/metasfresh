-- Run mode: SWING_CLIENT

-- Column: C_BPartner.Name3
-- 2025-12-01T08:55:22.968Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-12-01 08:55:22.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=557877
;

-- Column: C_BPartner.IsManufacturer
-- 2025-12-01T08:55:44.328Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2025-12-01 08:55:44.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560428
;

-- Column: C_BPartner.Value
-- 2025-12-01T08:55:53.205Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2025-12-01 08:55:53.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2901
;

-- Column: C_BPartner.Name
-- 2025-12-01T08:56:02.003Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2025-12-01 08:56:02.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2902
;

-- Column: C_BPartner.Name3
-- 2025-12-01T08:56:10.430Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2025-12-01 08:56:10.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=557877
;

-- Column: C_BPartner.NAICS
-- 2025-12-01T08:56:18.646Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2025-12-01 08:56:18.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2910
;

-- Column: C_BPartner.C_BP_Group_ID
-- 2025-12-01T08:56:27.381Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2025-12-01 08:56:27.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=4940
;

-- Column: C_BPartner.IsCustomer
-- 2025-12-01T08:56:36.082Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2025-12-01 08:56:36.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2916
;

-- Column: C_BPartner.IsSalesRep
-- 2025-12-01T08:56:44.675Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2025-12-01 08:56:44.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2929
;

-- Column: C_BPartner.IsVendor
-- 2025-12-01T08:56:52.682Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2025-12-01 08:56:52.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2915
;

-- Column: C_BPartner.AD_Org_ID
-- 2025-12-01T08:57:01.296Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2025-12-01 08:57:01.296000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2895
;

-- Column: C_BPartner.IsActive
-- 2025-12-01T08:57:09.931Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2025-12-01 08:57:09.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2896
;

-- 2025-12-01T08:58:20.015Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584301,0,TO_TIMESTAMP('2025-12-01 08:58:19.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Matchcode','Matchcode',TO_TIMESTAMP('2025-12-01 08:58:19.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-01T08:58:20.076Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584301 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> Matchcode
-- Column: C_BPartner.Name3
-- 2025-12-01T08:59:07.276Z
UPDATE AD_Field SET AD_Name_ID=584301, Description=NULL, Help=NULL, Name='Matchcode',Updated=TO_TIMESTAMP('2025-12-01 08:59:07.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=560559
;

-- 2025-12-01T08:59:07.337Z
UPDATE AD_Field_Trl trl SET Description=NULL,Name='Matchcode' WHERE AD_Field_ID=560559 AND AD_Language='de_DE'
;

-- 2025-12-01T08:59:07.425Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584301)
;

-- 2025-12-01T08:59:07.492Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=560559
;

-- 2025-12-01T08:59:07.551Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(560559)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> main -> 10 -> Greeting & Name.Name3
-- Column: C_BPartner.Name3
-- 2025-12-01T09:00:06.274Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=1000010, SeqNo=40,Updated=TO_TIMESTAMP('2025-12-01 09:00:06.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549287
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> main -> 10 -> Greeting & Name.Name3
-- Column: C_BPartner.Name3
-- 2025-12-01T09:01:10.929Z
UPDATE AD_UI_Element SET IsAdvancedField='N', SeqNo=25,Updated=TO_TIMESTAMP('2025-12-01 09:01:10.929000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549287
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> main -> 10 -> Greeting & Name.Name3
-- Column: C_BPartner.Name3
-- 2025-12-01T09:01:25.869Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-12-01 09:01:25.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549287
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> tax.Aktiv
-- Column: C_BPartner.IsActive
-- 2025-12-01T09:01:26.238Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-12-01 09:01:26.237000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000078
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> main -> 10 -> Greeting & Name.Firma
-- Column: C_BPartner.IsCompany
-- 2025-12-01T09:01:26.641Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-12-01 09:01:26.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000076
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> main -> 10 -> Sprache & Mitarbeiter.Geschäftspartnergruppe
-- Column: C_BPartner.C_BP_Group_ID
-- 2025-12-01T09:01:27.025Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-12-01 09:01:27.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000222
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> main -> 10 -> Sprache & Mitarbeiter.Sprache
-- Column: C_BPartner.AD_Language
-- 2025-12-01T09:01:27.406Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-12-01 09:01:27.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000086
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> flags.Sektion
-- Column: C_BPartner.AD_Org_ID
-- 2025-12-01T09:01:27.776Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-12-01 09:01:27.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000374
;

