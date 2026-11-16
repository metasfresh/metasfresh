-- Workplace window (541744), main tab (547260), right UI column (547140) — me03 #30326
-- Make the auto-assign matching fields understandable as ONE concept:
-- every field that restricts the Traffic-Management auto-assignment of shipment schedules
-- moves into the "restrictions" element group (555431, created by script 5806910), and each
-- gets an explanation of its matching semantics.
--
-- Labels fields: caption comes from AD_Name_ID -> AD_Element (translatable); the tooltip comes
-- from AD_UI_Element.Description (no _Trl table -> base language German only). The previously
-- referenced shared elements (M_Product_ID etc.) are singular and carry generic descriptions,
-- so each Labels field gets a DEDICATED element (plural caption) instead — shared elements must
-- never be mutated.
-- IDs allocated from idserver.metas.de: AD_Element 584975..584980.

-- 1) Dedicated caption elements (German base, en_US override) -------------------------------

-- 584975: Produkte / Products (UI element 638746)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584975 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-11 13:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produkte','Produkte',TO_TIMESTAMP('2026-06-11 13:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 584976: Produktkategorien / Product Categories (UI element 638739)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584976 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-11 13:00:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produktkategorien','Produktkategorien',TO_TIMESTAMP('2026-06-11 13:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 584977: Lieferweg-Produkte / Carrier Products (UI element 638740)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584977 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-11 13:00:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferweg-Produkte','Lieferweg-Produkte',TO_TIMESTAMP('2026-06-11 13:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 584978: Externe Systeme / External Systems (UI element 638738)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584978 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-11 13:00:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Externe Systeme','Externe Systeme',TO_TIMESTAMP('2026-06-11 13:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 584979: Geschäftspartnergruppen / Business Partner Groups (UI element 652032)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584979 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-11 13:00:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geschäftspartnergruppen','Geschäftspartnergruppen',TO_TIMESTAMP('2026-06-11 13:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 584980: Auftrags-Belegarten / Order Document Types (UI element 652033)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584980 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-11 13:00:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Auftrags-Belegarten','Auftrags-Belegarten',TO_TIMESTAMP('2026-06-11 13:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Seed _Trl rows for all six elements
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID IN (584975,584976,584977,584978,584979,584980) AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- en_US overrides
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Products', PrintName='Products',Updated=TO_TIMESTAMP('2026-06-11 13:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=584975 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Product Categories', PrintName='Product Categories',Updated=TO_TIMESTAMP('2026-06-11 13:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=584976 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Carrier Products', PrintName='Carrier Products',Updated=TO_TIMESTAMP('2026-06-11 13:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=584977 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='External Systems', PrintName='External Systems',Updated=TO_TIMESTAMP('2026-06-11 13:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=584978 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Business Partner Groups', PrintName='Business Partner Groups',Updated=TO_TIMESTAMP('2026-06-11 13:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=584979 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Order Document Types', PrintName='Order Document Types',Updated=TO_TIMESTAMP('2026-06-11 13:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=584980 AND AD_Language='en_US'
;

-- de_DE / de_CH carry the (German) base text already -> mark actively translated
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-11 13:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID IN (584975,584976,584977,584978,584979,584980) AND AD_Language IN ('de_DE','de_CH')
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584975,'en_US')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584976,'en_US')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584977,'en_US')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584978,'en_US')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584979,'en_US')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584980,'en_US')
;

-- 2) Labels fields: dedicated caption element + matching-semantics tooltip + move into the
--    "restrictions" group (555431). AD_UI_Element.Description has no _Trl -> German.

-- Produkte (638746)
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555431, SeqNo=30, AD_Name_ID=584975, Name='Produkte', Description='Einschränkung für die automatische Arbeitsplatz-Zuordnung: nur Lieferdispositionen mit einem dieser Produkte (oder mit einer der ausgewählten Produktkategorien) werden diesem Arbeitsplatz zugeordnet. Leer = keine Einschränkung.', Updated=TO_TIMESTAMP('2026-06-11 13:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=638746
;
-- Produktkategorien (638739)
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555431, SeqNo=40, AD_Name_ID=584976, Name='Produktkategorien', Description='Einschränkung für die automatische Arbeitsplatz-Zuordnung: nur Lieferdispositionen, deren Produkt zu einer dieser Kategorien gehört (oder eines der ausgewählten Produkte ist), werden diesem Arbeitsplatz zugeordnet. Leer = keine Einschränkung.', Updated=TO_TIMESTAMP('2026-06-11 13:00:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=638739
;
-- Lieferweg-Produkte (638740)
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555431, SeqNo=50, AD_Name_ID=584977, Name='Lieferweg-Produkte', Description='Einschränkung für die automatische Arbeitsplatz-Zuordnung: nur Lieferdispositionen mit einem dieser Lieferweg-Produkte werden diesem Arbeitsplatz zugeordnet. Leer = keine Einschränkung.', Updated=TO_TIMESTAMP('2026-06-11 13:00:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=638740
;
-- Externe Systeme (638738)
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555431, SeqNo=60, AD_Name_ID=584978, Name='Externe Systeme', Description='Einschränkung für die automatische Arbeitsplatz-Zuordnung: nur Lieferdispositionen, die aus einem dieser externen Systeme stammen, werden diesem Arbeitsplatz zugeordnet. Leer = keine Einschränkung.', Updated=TO_TIMESTAMP('2026-06-11 13:00:23','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=638738
;
-- Geschäftspartnergruppen (652032)
UPDATE AD_UI_Element SET SeqNo=70, AD_Name_ID=584979, Name='Geschäftspartnergruppen', Description='Einschränkung für die automatische Arbeitsplatz-Zuordnung: nur Lieferdispositionen, deren Lieferempfänger zu einer dieser Geschäftspartnergruppen (einschließlich der direkt übergeordneten Gruppe) gehört, werden diesem Arbeitsplatz zugeordnet. Leer = keine Einschränkung.', Updated=TO_TIMESTAMP('2026-06-11 13:00:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=652032
;
-- Auftrags-Belegarten (652033)
UPDATE AD_UI_Element SET SeqNo=80, AD_Name_ID=584980, Name='Auftrags-Belegarten', Description='Einschränkung für die automatische Arbeitsplatz-Zuordnung: nur Lieferdispositionen, deren Auftrag eine dieser Belegarten hat, werden diesem Arbeitsplatz zugeordnet. Zur Auswahl stehen nur Auftrags-Belegarten. Leer = keine Einschränkung.', Updated=TO_TIMESTAMP('2026-06-11 13:00:25','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=652033
;

-- 3) The two regular matching fields also move into the "restrictions" group ----------------

-- Kommissionierart (638741, AD_Field 756178 / OrderPickingType)
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555431, SeqNo=10, Updated=TO_TIMESTAMP('2026-06-11 13:00:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=638741
;
-- Priorität (638745, AD_Field 756182 / PriorityRule)
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555431, SeqNo=20, Updated=TO_TIMESTAMP('2026-06-11 13:00:31','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=638745
;

-- Their descriptions must explain the matching semantics. Field-level Description overrides do
-- NOT survive: after_migration_sync_translations() re-syncs every AD_Field without AD_Name_ID
-- from its column's element. So each field gets a DEDICATED element (same caption, restriction
-- description) wired via AD_Field.AD_Name_ID — the sync then propagates OUR text.
-- IDs allocated from idserver.metas.de: AD_Element 584981, 584982.

-- 584981: Kommissionierart / Order Picking Type (field 756178)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584981 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-11 13:10:00','YYYY-MM-DD HH24:MI:SS'),100,'Einschränkung für die automatische Arbeitsplatz-Zuordnung: ''Single'' = nur Aufträge mit genau 1 Stück Gesamtliefermenge, ''Multi'' = nur Aufträge mit mehr als 1 Stück. Leer = keine Einschränkung.','D','Y','Kommissionierart','Kommissionierart',TO_TIMESTAMP('2026-06-11 13:10:00','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 584982: Priorität / Priority (field 756182)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584982 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-11 13:10:01','YYYY-MM-DD HH24:MI:SS'),100,'Einschränkung für die automatische Arbeitsplatz-Zuordnung: nur Lieferdispositionen mit genau dieser Priorität werden diesem Arbeitsplatz zugeordnet. Leer = keine Einschränkung.','D','Y','Priorität','Priorität',TO_TIMESTAMP('2026-06-11 13:10:01','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID IN (584981,584982) AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Order Picking Type', PrintName='Order Picking Type', Description='Restriction for the automatic workplace assignment: ''Single'' = only orders with a total quantity to deliver of exactly 1, ''Multi'' = only orders with more than 1. Empty = no restriction.',Updated=TO_TIMESTAMP('2026-06-11 13:10:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=584981 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Priority', PrintName='Priority', Description='Restriction for the automatic workplace assignment: only shipment schedules with exactly this priority are assigned to this workplace. Empty = no restriction.',Updated=TO_TIMESTAMP('2026-06-11 13:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=584982 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-11 13:10:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID IN (584981,584982) AND AD_Language IN ('de_DE','de_CH')
;

-- Wire the fields to their dedicated elements and refresh links + translations.
UPDATE AD_Field SET AD_Name_ID=584981, Updated=TO_TIMESTAMP('2026-06-11 13:10:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=756178
;
UPDATE AD_Field SET AD_Name_ID=584982, Updated=TO_TIMESTAMP('2026-06-11 13:10:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=756182
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (756178,756182)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(756178)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(756182)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584981)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584982)
;

-- Clear stale AD_UI_Element.Description on the two F-type elements: for 'F' elements the WebUI
-- resolves the tooltip from the field (AD_Field_v/_vt), so these element-level texts are dead
-- metadata — but a misleading leftover (e.g. English 'Priority of a document'). The
-- AD_Field(_Trl) descriptions set via the dedicated elements above are the single truth.
UPDATE AD_UI_Element SET Description=NULL, Updated=TO_TIMESTAMP('2026-06-11 13:10:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID IN (638741,638745)
;

-- Align the DocType labels tab caption with its Labels field caption: 'Auftrags-Belegarten'
-- (tab 549294 / caption element 584964 were created by script 5806920 as 'Belegarten').
UPDATE AD_Element SET Name='Auftrags-Belegarten', PrintName='Auftrags-Belegarten', Updated=TO_TIMESTAMP('2026-06-11 13:10:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584964
;
UPDATE AD_Element_Trl SET Name='Auftrags-Belegarten', PrintName='Auftrags-Belegarten', Updated=TO_TIMESTAMP('2026-06-11 13:10:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584964 AND AD_Language IN ('de_DE','de_CH')
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Order Document Types', PrintName='Order Document Types', Updated=TO_TIMESTAMP('2026-06-11 13:10:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584964 AND AD_Language='en_US'
;
UPDATE AD_Tab SET Name='Auftrags-Belegarten', Updated=TO_TIMESTAMP('2026-06-11 13:10:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Tab_ID=549294
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584964,'en_US')
;
/* DDL */ select update_tab_translation_from_ad_element(584964)
;

SELECT add_missing_translations()
;
