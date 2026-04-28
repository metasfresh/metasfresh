-- 2025-01-09T10:57:03.280Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583422,0,TO_TIMESTAMP('2025-01-09 11:57:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferung an','Lieferung an',TO_TIMESTAMP('2025-01-09 11:57:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-01-09T10:57:03.342Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583422 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-01-09T10:57:36.301Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delivery to', PrintName='Delivery to',Updated=TO_TIMESTAMP('2025-01-09 11:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583422 AND AD_Language='en_US'
;

-- 2025-01-09T10:57:36.425Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583422,'en_US') 
;

-- Field: Auftrag_OLD -> Auftrag -> Lieferung an
-- Column: C_Order.DropShip_BPartner_ID
-- Field: Auftrag_OLD(143,D) -> Auftrag(186,D) -> Lieferung an
-- Column: C_Order.DropShip_BPartner_ID
-- 2025-01-09T10:58:28.611Z
UPDATE AD_Field SET AD_Name_ID=583422, Description=NULL, Help=NULL, Name='Lieferung an',Updated=TO_TIMESTAMP('2025-01-09 11:58:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=55410
;

-- 2025-01-09T10:58:28.662Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583422) 
;

-- 2025-01-09T10:58:28.741Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=55410
;

-- 2025-01-09T10:58:28.793Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(55410)
;

-- Column: C_Order.DropShip_BPartner_ID
-- Column: C_Order.DropShip_BPartner_ID
-- 2025-01-09T10:59:56.394Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-09 11:59:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=55314
;

-- Column: C_Order.IsDropShip
-- Column: C_Order.IsDropShip
-- 2025-01-09T11:01:11.127Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-09 12:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=11580
;

-- UI Element: Auftrag_OLD -> Auftrag.Abw. Kunde
-- Column: C_Order.DropShip_BPartner_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Abw. Kunde
-- Column: C_Order.DropShip_BPartner_ID
-- 2025-01-09T15:42:45.684Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-01-09 16:42:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544810
;

-- UI Element: Auftrag_OLD -> Auftrag.Abw. Lieferanschrift
-- Column: C_Order.IsDropShip
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Abw. Lieferanschrift
-- Column: C_Order.IsDropShip
-- 2025-01-09T15:42:46.111Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-01-09 16:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544809
;

-- UI Element: Auftrag_OLD -> Auftrag.Abladeort
-- Column: C_Order.HandOver_Location_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Abladeort
-- Column: C_Order.HandOver_Location_ID
-- 2025-01-09T15:42:46.521Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-01-09 16:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548025
;

-- UI Element: Auftrag_OLD -> Auftrag.Zugesagter Liefertermin
-- Column: C_Order.DatePromised
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 20 -> Dates.Zugesagter Liefertermin
-- Column: C_Order.DatePromised
-- 2025-01-09T15:42:46.930Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-01-09 16:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000009
;

-- UI Element: Auftrag_OLD -> Auftrag.Preissystem
-- Column: C_Order.M_PricingSystem_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> pricing.Preissystem
-- Column: C_Order.M_PricingSystem_ID
-- 2025-01-09T15:42:47.348Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-01-09 16:42:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000210
;

-- UI Element: Auftrag_OLD -> Auftrag.Währung
-- Column: C_Order.C_Currency_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> pricing.Währung
-- Column: C_Order.C_Currency_ID
-- 2025-01-09T15:42:47.757Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-01-09 16:42:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541299
;

-- UI Element: Auftrag_OLD -> Auftrag.Belegstatus
-- Column: C_Order.DocStatus
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 20 -> Rest.Belegstatus
-- Column: C_Order.DocStatus
-- 2025-01-09T15:42:48.159Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-01-09 16:42:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000024
;

-- UI Element: Auftrag_OLD -> Auftrag.Verbucht
-- Column: C_Order.Posted
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 20 -> posted.Verbucht
-- Column: C_Order.Posted
-- 2025-01-09T15:42:48.562Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-01-09 16:42:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544788
;

-- UI Element: Auftrag_OLD -> Auftrag.Incoterm Standort
-- Column: C_Order.IncotermLocation
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> pricing.Incoterm Standort
-- Column: C_Order.IncotermLocation
-- 2025-01-09T15:42:48.966Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-01-09 16:42:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544771
;

-- UI Element: Auftrag_OLD -> Auftrag.Organisation
-- Column: C_Order.AD_Org_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 20 -> Org und Lager.Organisation
-- Column: C_Order.AD_Org_ID
-- 2025-01-09T15:42:49.375Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-01-09 16:42:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

-- UI Element: Auftrag_OLD -> Auftrag.Abw. Lieferanschrift
-- Column: C_Order.IsDropShip
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Abw. Lieferanschrift
-- Column: C_Order.IsDropShip
-- 2025-01-09T15:43:58.052Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-01-09 16:43:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544809
;

-- UI Element: Auftrag_OLD -> Auftrag.Abw. Kunde
-- Column: C_Order.DropShip_BPartner_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Abw. Kunde
-- Column: C_Order.DropShip_BPartner_ID
-- 2025-01-09T15:43:58.461Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-01-09 16:43:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544810
;

