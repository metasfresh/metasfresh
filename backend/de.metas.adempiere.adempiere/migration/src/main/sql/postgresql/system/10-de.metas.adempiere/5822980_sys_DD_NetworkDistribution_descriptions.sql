-- Run mode: SWING_CLIENT

-- Distribution Network window (AD_Window 53018) metadata: describe the empties-network
-- flag (DD_NetworkDistribution.IsHUDestroyed) and the traced DD_NetworkDistributionLine
-- fields, in en_US / de_DE / de_CH. Metadata text only -- no DDL, no column rename.
--
-- AFFECTED RECORDS
-- =====================================================================
-- 1) AD_Element 542544 (IsHUDestroyed, sole usage) -- Label/Description/Help added
-- 2) AD_Element 542484 (DD_AllowPush, shared with DD_OrderLine) -- Label only
-- 3) AD_Element 542627 (IsKeepTargetPlant, shared with DD_OrderLine) -- Label only
-- 4) AD_Element 573441 (tab 53072 'Network Line' name, sole usage) -- Description added
-- 5) NEW AD_Element 585430 -- field-only override for DD_NetworkDistributionLine.M_WarehouseSource_ID
--    (element 2814 is shared with M_Warehouse and T_Replenish -- meaning would be wrong there)
-- 6) NEW AD_Element 585431 -- field-only override for DD_NetworkDistributionLine.M_Warehouse_ID
--    (element 459 'Lager' is the generic Warehouse element, used on 94 columns)
-- 7) NEW AD_Element 585432 -- field-only override for DD_NetworkDistributionLine.PriorityNo
--    (element 1145 is shared with M_Locator -- meaning would be wrong there)
-- 8) NEW AD_Element 585433 -- field-only override for DD_NetworkDistributionLine.TransfertTime
--    (element 53271 is shared with M_Product_PlanningSchema and PP_Product_Planning)
-- 9) NEW AD_Element 585434 -- field-only override for DD_NetworkDistributionLine.Percent
--    (element 951 'Percent' is a generic element shared across 8 unrelated columns)
--
-- NOT AFFECTED: M_ChangeNotice_ID, CopyFrom, Revision (header fields, out of scope);
-- DD_AllowPush / IsKeepTargetPlant descriptions (untraced downstream effect, per plan).
-- IDs allocated from idserver.metas.de on 2026-09-08:
--   AD_Element 585430 (M_WarehouseSource_ID field-only label/description)
--   AD_Element 585431 (M_Warehouse_ID field-only label/description)
--   AD_Element 585432 (PriorityNo field-only label/description)
--   AD_Element 585433 (TransfertTime field-only label/description)
--   AD_Element 585434 (Percent field-only label/description)
-- =====================================================================

-- ===== DD_NetworkDistribution.IsHUDestroyed (AD_Element 542544, AD_Field 554755) =====

UPDATE AD_Element_Trl SET Name='Empties network', PrintName='Empties network', Description='Designates this distribution network as the empties network: when a handling unit is destroyed or emptied, its packing material is moved to the target warehouse defined by this network''s line for that handling unit''s warehouse.', Help='Only one distribution network may be flagged as the empties network. The standard setup ships one covering the standard warehouse, returning packing material to the Leergebindelager. Every additional warehouse whose packing material should be returned needs its own line here; a handling unit destroyed in a warehouse with no line causes an error instead of a movement.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=542544
;

-- German text MUST also be written to the de_DE _Trl row (base language) -- the sync cascade
-- reads AD_Element_Trl, not AD_Element, as the source of truth for the base row.
UPDATE AD_Element SET Name='Leergebindenetzwerk', PrintName='Leergebindenetzwerk', Description='Kennzeichnet dieses Distributionsnetzwerk als Leergebindenetzwerk: Wird eine Handling Unit zerstört oder geleert, wird deren Verpackungsmaterial in das Ziellager verschoben, das die Netzwerklinie für das Lager dieser Handling Unit festlegt.', Help='Nur ein Distributionsnetzwerk darf als Leergebindenetzwerk markiert werden. Die Standardeinrichtung enthält bereits eine Linie für das Standardlager, die Verpackungsmaterial in das Leergebindelager zurückführt. Für jedes weitere Lager, dessen Verpackungsmaterial zurückgeführt werden soll, ist eine eigene Linie erforderlich; wird eine Handling Unit in einem Lager ohne Linie zerstört, führt dies zu einem Fehler statt zu einer Bewegung.', Updated=TO_TIMESTAMP('2026-09-08 09:00:00.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=542544
;

UPDATE AD_Element_Trl SET Name='Leergebindenetzwerk', PrintName='Leergebindenetzwerk', Description='Kennzeichnet dieses Distributionsnetzwerk als Leergebindenetzwerk: Wird eine Handling Unit zerstört oder geleert, wird deren Verpackungsmaterial in das Ziellager verschoben, das die Netzwerklinie für das Lager dieser Handling Unit festlegt.', Help='Nur ein Distributionsnetzwerk darf als Leergebindenetzwerk markiert werden. Die Standardeinrichtung enthält bereits eine Linie für das Standardlager, die Verpackungsmaterial in das Leergebindelager zurückführt. Für jedes weitere Lager, dessen Verpackungsmaterial zurückgeführt werden soll, ist eine eigene Linie erforderlich; wird eine Handling Unit in einem Lager ohne Linie zerstört, führt dies zu einem Fehler statt zu einer Bewegung.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:00.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=542544
;

UPDATE AD_Element_Trl SET Name='Leergebindenetzwerk', PrintName='Leergebindenetzwerk', Description='Kennzeichnet dieses Distributionsnetzwerk als Leergebindenetzwerk: Wird eine Handling Unit zerstört oder geleert, wird deren Verpackungsmaterial in das Ziellager verschoben, das die Netzwerklinie für das Lager dieser Handling Unit festlegt.', Help='Nur ein Distributionsnetzwerk darf als Leergebindenetzwerk markiert werden. Die Standardeinrichtung enthält bereits eine Linie für das Standardlager, die Verpackungsmaterial in das Leergebindelager zurückführt. Für jedes weitere Lager, dessen Verpackungsmaterial zurückgeführt werden soll, ist eine eigene Linie erforderlich; wird eine Handling Unit in einem Lager ohne Linie zerstört, führt dies zu einem Fehler statt zu einer Bewegung.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:00.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=542544
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542544)
;

-- AD_Field.Help (base language) is NOT propagated by the element sync -- set it directly.
UPDATE AD_Field SET Help='Nur ein Distributionsnetzwerk darf als Leergebindenetzwerk markiert werden. Die Standardeinrichtung enthält bereits eine Linie für das Standardlager, die Verpackungsmaterial in das Leergebindelager zurückführt. Für jedes weitere Lager, dessen Verpackungsmaterial zurückgeführt werden soll, ist eine eigene Linie erforderlich; wird eine Handling Unit in einem Lager ohne Linie zerstört, führt dies zu einem Fehler statt zu einer Bewegung.', Updated=TO_TIMESTAMP('2026-09-08 09:00:00.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=554755
;

-- ===== DD_NetworkDistributionLine.DD_AllowPush (AD_Element 542484, shared with DD_OrderLine) -- label only =====

UPDATE AD_Element_Trl SET Name='Allow push', PrintName='Allow push', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:00.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=542484
;

UPDATE AD_Element SET Name='Push erlauben', PrintName='Push erlauben', Updated=TO_TIMESTAMP('2026-09-08 09:00:00.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=542484
;

UPDATE AD_Element_Trl SET Name='Push erlauben', PrintName='Push erlauben', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:00.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=542484
;

UPDATE AD_Element_Trl SET Name='Push erlauben', PrintName='Push erlauben', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:00.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=542484
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542484)
;

-- ===== DD_NetworkDistributionLine.IsKeepTargetPlant (AD_Element 542627, shared with DD_OrderLine) -- label only =====

UPDATE AD_Element_Trl SET Name='Keep target plant', PrintName='Keep target plant', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:01.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=542627
;

UPDATE AD_Element SET Name='Zielwerk beibehalten', PrintName='Zielwerk beibehalten', Updated=TO_TIMESTAMP('2026-09-08 09:00:01.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=542627
;

UPDATE AD_Element_Trl SET Name='Zielwerk beibehalten', PrintName='Zielwerk beibehalten', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:01.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=542627
;

UPDATE AD_Element_Trl SET Name='Zielwerk beibehalten', PrintName='Zielwerk beibehalten', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:01.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=542627
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542627)
;

-- ===== Tab 53072 'Network Line' description (AD_Element 573441, sole usage) -- AC20 =====

UPDATE AD_Element_Trl SET Description='Each line maps a source warehouse to the warehouse its packing material is returned to.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:01.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=573441
;

UPDATE AD_Element SET Description='Jede Linie ordnet einem Quelllager das Lager zu, in das dessen Verpackungsmaterial zurückgeführt wird.', Updated=TO_TIMESTAMP('2026-09-08 09:00:01.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=573441
;

UPDATE AD_Element_Trl SET Description='Jede Linie ordnet einem Quelllager das Lager zu, in das dessen Verpackungsmaterial zurückgeführt wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:01.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=573441
;

UPDATE AD_Element_Trl SET Description='Jede Linie ordnet einem Quelllager das Lager zu, in das dessen Verpackungsmaterial zurückgeführt wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:01.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=573441
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573441)
;

-- ===== DD_NetworkDistributionLine.M_WarehouseSource_ID (NEW AD_Element 585430, AD_Field 54380) =====

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585430,0,NULL,TO_TIMESTAMP('2026-09-08 09:00:02.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Quelllager','Quelllager',TO_TIMESTAMP('2026-09-08 09:00:02.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585430 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Source warehouse', PrintName='Source warehouse', Description='The warehouse the movement starts from. On the empties network this is the warehouse the handling unit was destroyed in — the line is found by matching this field.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:02.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585430
;

-- German text MUST also be written to the de_DE _Trl row (base language) -- the sync cascade
-- reads AD_Element_Trl, not AD_Element, as the source of truth for the base row.
UPDATE AD_Element SET Description='Das Lager, von dem die Bewegung ausgeht. Beim Leergebindenetzwerk ist dies das Lager, in dem die Handling Unit zerstört wurde — die Linie wird über dieses Feld ermittelt.', Updated=TO_TIMESTAMP('2026-09-08 09:00:02.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585430
;

UPDATE AD_Element_Trl SET Description='Das Lager, von dem die Bewegung ausgeht. Beim Leergebindenetzwerk ist dies das Lager, in dem die Handling Unit zerstört wurde — die Linie wird über dieses Feld ermittelt.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:02.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585430
;

UPDATE AD_Element_Trl SET Description='Das Lager, von dem die Bewegung ausgeht. Beim Leergebindenetzwerk ist dies das Lager, in dem die Handling Unit zerstört wurde — die Linie wird über dieses Feld ermittelt.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:02.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585430
;

-- wire the field to its own name element (per-field override -- the shared column element
-- keeps its original, generic meaning for every other table that uses it)
UPDATE AD_Field SET AD_Name_ID=585430, Description='Das Lager, von dem die Bewegung ausgeht. Beim Leergebindenetzwerk ist dies das Lager, in dem die Handling Unit zerstört wurde — die Linie wird über dieses Feld ermittelt.', Updated=TO_TIMESTAMP('2026-09-08 09:00:02.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=54380
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585430)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=54380
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(54380)
;

-- ===== DD_NetworkDistributionLine.M_Warehouse_ID (NEW AD_Element 585431, AD_Field 54381) =====

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585431,0,NULL,TO_TIMESTAMP('2026-09-08 09:00:03.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Ziellager','Ziellager',TO_TIMESTAMP('2026-09-08 09:00:03.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585431 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Target warehouse', PrintName='Target warehouse', Description='The warehouse the movement goes to. On the empties network this is where the packing material is returned — typically the Leergebindelager.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:03.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585431
;

-- German text MUST also be written to the de_DE _Trl row (base language) -- the sync cascade
-- reads AD_Element_Trl, not AD_Element, as the source of truth for the base row.
UPDATE AD_Element SET Description='Das Lager, in das die Bewegung geht. Beim Leergebindenetzwerk wird hierhin das Verpackungsmaterial zurückgeführt — üblicherweise das Leergebindelager.', Updated=TO_TIMESTAMP('2026-09-08 09:00:03.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585431
;

UPDATE AD_Element_Trl SET Description='Das Lager, in das die Bewegung geht. Beim Leergebindenetzwerk wird hierhin das Verpackungsmaterial zurückgeführt — üblicherweise das Leergebindelager.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:03.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585431
;

UPDATE AD_Element_Trl SET Description='Das Lager, in das die Bewegung geht. Beim Leergebindenetzwerk wird hierhin das Verpackungsmaterial zurückgeführt — üblicherweise das Leergebindelager.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:03.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585431
;

-- wire the field to its own name element (per-field override -- the shared column element
-- keeps its original, generic meaning for every other table that uses it)
UPDATE AD_Field SET AD_Name_ID=585431, Description='Das Lager, in das die Bewegung geht. Beim Leergebindenetzwerk wird hierhin das Verpackungsmaterial zurückgeführt — üblicherweise das Leergebindelager.', Updated=TO_TIMESTAMP('2026-09-08 09:00:03.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=54381
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585431)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=54381
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(54381)
;

-- ===== DD_NetworkDistributionLine.PriorityNo (NEW AD_Element 585432, AD_Field 54388) =====

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585432,0,NULL,TO_TIMESTAMP('2026-09-08 09:00:04.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Priorität','Priorität',TO_TIMESTAMP('2026-09-08 09:00:04.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585432 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Priority', PrintName='Priority', Description='Lines are evaluated in ascending priority order. On the empties network the lowest number decides where packing material is returned.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:04.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585432
;

-- German text MUST also be written to the de_DE _Trl row (base language) -- the sync cascade
-- reads AD_Element_Trl, not AD_Element, as the source of truth for the base row.
UPDATE AD_Element SET Description='Linien werden in aufsteigender Prioritätsreihenfolge ausgewertet. Beim Leergebindenetzwerk entscheidet die niedrigste Nummer, wohin das Verpackungsmaterial zurückgeführt wird.', Updated=TO_TIMESTAMP('2026-09-08 09:00:04.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585432
;

UPDATE AD_Element_Trl SET Description='Linien werden in aufsteigender Prioritätsreihenfolge ausgewertet. Beim Leergebindenetzwerk entscheidet die niedrigste Nummer, wohin das Verpackungsmaterial zurückgeführt wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:04.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585432
;

UPDATE AD_Element_Trl SET Description='Linien werden in aufsteigender Prioritätsreihenfolge ausgewertet. Beim Leergebindenetzwerk entscheidet die niedrigste Nummer, wohin das Verpackungsmaterial zurückgeführt wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:04.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585432
;

-- wire the field to its own name element (per-field override -- the shared column element
-- keeps its original, generic meaning for every other table that uses it)
UPDATE AD_Field SET AD_Name_ID=585432, Description='Linien werden in aufsteigender Prioritätsreihenfolge ausgewertet. Beim Leergebindenetzwerk entscheidet die niedrigste Nummer, wohin das Verpackungsmaterial zurückgeführt wird.', Updated=TO_TIMESTAMP('2026-09-08 09:00:04.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=54388
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585432)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=54388
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(54388)
;

-- ===== DD_NetworkDistributionLine.TransfertTime (NEW AD_Element 585433, AD_Field 54386) =====

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585433,0,NULL,TO_TIMESTAMP('2026-09-08 09:00:05.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Transportdauer (Tage)','Transportdauer (Tage)',TO_TIMESTAMP('2026-09-08 09:00:05.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585433 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Transfer time (days)', PrintName='Transfer time (days)', Description='The transfer duration for this line, in days.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:05.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585433
;

-- German text MUST also be written to the de_DE _Trl row (base language) -- the sync cascade
-- reads AD_Element_Trl, not AD_Element, as the source of truth for the base row.
UPDATE AD_Element SET Description='Die Transportdauer dieser Linie, in Tagen.', Updated=TO_TIMESTAMP('2026-09-08 09:00:05.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585433
;

UPDATE AD_Element_Trl SET Description='Die Transportdauer dieser Linie, in Tagen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:05.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585433
;

UPDATE AD_Element_Trl SET Description='Die Transportdauer dieser Linie, in Tagen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:05.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585433
;

-- wire the field to its own name element (per-field override -- the shared column element
-- keeps its original, generic meaning for every other table that uses it)
UPDATE AD_Field SET AD_Name_ID=585433, Description='Die Transportdauer dieser Linie, in Tagen.', Updated=TO_TIMESTAMP('2026-09-08 09:00:05.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=54386
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585433)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=54386
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(54386)
;

-- ===== DD_NetworkDistributionLine.Percent (NEW AD_Element 585434, AD_Field 54387) =====

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585434,0,NULL,TO_TIMESTAMP('2026-09-08 09:00:06.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Anteil','Anteil',TO_TIMESTAMP('2026-09-08 09:00:06.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585434 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Transfer share', PrintName='Transfer share', Description='The line''s transfer share. The empties-network lookup ignores this value — it always uses the highest-priority line only.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:06.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585434
;

-- German text MUST also be written to the de_DE _Trl row (base language) -- the sync cascade
-- reads AD_Element_Trl, not AD_Element, as the source of truth for the base row.
UPDATE AD_Element SET Description='Der Anteil dieser Linie am Transfer. Die Suche im Leergebindenetzwerk berücksichtigt diesen Wert nicht — sie verwendet ausschließlich die Linie mit der höchsten Priorität.', Updated=TO_TIMESTAMP('2026-09-08 09:00:06.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585434
;

UPDATE AD_Element_Trl SET Description='Der Anteil dieser Linie am Transfer. Die Suche im Leergebindenetzwerk berücksichtigt diesen Wert nicht — sie verwendet ausschließlich die Linie mit der höchsten Priorität.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:06.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585434
;

UPDATE AD_Element_Trl SET Description='Der Anteil dieser Linie am Transfer. Die Suche im Leergebindenetzwerk berücksichtigt diesen Wert nicht — sie verwendet ausschließlich die Linie mit der höchsten Priorität.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 09:00:06.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585434
;

-- wire the field to its own name element (per-field override -- the shared column element
-- keeps its original, generic meaning for every other table that uses it)
UPDATE AD_Field SET AD_Name_ID=585434, Description='Der Anteil dieser Linie am Transfer. Die Suche im Leergebindenetzwerk berücksichtigt diesen Wert nicht — sie verwendet ausschließlich die Linie mit der höchsten Priorität.', Updated=TO_TIMESTAMP('2026-09-08 09:00:06.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=54387
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585434)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=54387
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(54387)
;
