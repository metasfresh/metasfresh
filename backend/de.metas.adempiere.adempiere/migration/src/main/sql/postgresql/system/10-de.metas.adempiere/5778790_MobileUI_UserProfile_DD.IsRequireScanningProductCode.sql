-- Run mode: SWING_CLIENT

-- 2025-11-27T19:58:15.507Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584300,0,'IsRequireScanningProductCode',TO_TIMESTAMP('2025-11-27 19:58:15.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bei Aktivierung muss der Benutzer den Artikelcode (GTIN) unmittelbar nach dem Scannen der Pick-von-Ladeeinheit (HU) während des Kommissionierungsvorgangs scannen.','D','Ist diese Einstellung aktiviert, erzwingt das System einen zusätzlichen Verifizierungsschritt: Der Kommissionierer muss den Barcode des Artikels (GTIN) scannen, um zu bestätigen, dass der tatsächlich entnommene Artikel mit dem erwarteten Produkt der Auftragsposition übereinstimmt. Dies erhöht die Genauigkeit, verlängert jedoch den Arbeitsablauf.','Y','Artikelcode-Scan erforderlich','Artikelcode-Scan erforderlich',TO_TIMESTAMP('2025-11-27 19:58:15.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-27T19:58:15.545Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584300 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsRequireScanningProductCode
-- 2025-11-27T19:58:19.777Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-27 19:58:19.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584300 AND AD_Language='de_CH'
;

-- 2025-11-27T19:58:19.955Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584300,'de_CH')
;

-- Element: IsRequireScanningProductCode
-- 2025-11-27T19:58:21.104Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-27 19:58:21.104000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584300 AND AD_Language='de_DE'
;

-- 2025-11-27T19:58:21.107Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584300,'de_DE')
;

-- 2025-11-27T19:58:21.109Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584300,'de_DE')
;

-- Element: IsRequireScanningProductCode
-- 2025-11-27T19:59:25.511Z
UPDATE AD_Element_Trl SET Description='When enabled, the user must scan the Product Code (GTIN) immediately after scanning the Pick From Handling Unit (HU) code during the picking process.', Help='If this flag is enabled, the system enforces an extra verification step: the picker must scan the item''s barcode (GTIN) to confirm that the physical item picked from the source container matches the expected product for the order line. This increases accuracy but adds a step to the workflow.', IsTranslated='Y', Name='Require Product Code Scan', PrintName='Require Product Code Scan',Updated=TO_TIMESTAMP('2025-11-27 19:59:25.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584300 AND AD_Language='en_US'
;

-- 2025-11-27T19:59:25.511Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-27T19:59:25.853Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584300,'en_US')
;

-- Element: IsRequireScanningProductCode
-- 2025-11-27T20:02:16.649Z
UPDATE AD_Element_Trl SET Description='If enabled, the user must scan the Product Code (e.g., GTIN/EAN) after scanning the Handling Unit (HU) QR Code during mobile picking.', Help='Activates an additional mandatory step in the mobile picking process to verify the specific product code (GTIN/EAN) inside the HU, providing an extra layer of quality assurance against picking errors.', Name='Is Require Product Code Scan', PrintName='Is Require Product Code Scan',Updated=TO_TIMESTAMP('2025-11-27 20:02:16.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584300 AND AD_Language='en_US'
;

-- 2025-11-27T20:02:16.650Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-27T20:02:16.991Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584300,'en_US')
;

-- Element: IsRequireScanningProductCode
-- 2025-11-27T20:02:40.737Z
UPDATE AD_Element_Trl SET Description='Bei Aktivierung muss der Benutzer den Produktcode (z.B. GTIN/EAN) scannen, nachdem er den Handling Unit (HU) QR Code beim mobilen Kommissionieren gescannt hat.', Help='Aktiviert einen zusätzlichen obligatorischen Schritt im mobilen Kommissionierprozess, um den spezifischen Produktcode (GTIN/EAN) innerhalb der HU zu überprüfen. Dies bietet eine zusätzliche Ebene der Qualitätssicherung gegen Kommissionierfehler.', Name='Ist Produktcode-Scan Erforderlich', PrintName='Ist Produktcode-Scan Erforderlich',Updated=TO_TIMESTAMP('2025-11-27 20:02:40.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584300 AND AD_Language='de_CH'
;

-- 2025-11-27T20:02:40.739Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-27T20:02:41.077Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584300,'de_CH')
;

-- Element: IsRequireScanningProductCode
-- 2025-11-27T20:02:56.622Z
UPDATE AD_Element_Trl SET Description='Bei Aktivierung muss der Benutzer den Produktcode (z.B. GTIN/EAN) scannen, nachdem er den Handling Unit (HU) QR Code beim mobilen Kommissionieren gescannt hat.', Help='Aktiviert einen zusätzlichen obligatorischen Schritt im mobilen Kommissionierprozess, um den spezifischen Produktcode (GTIN/EAN) innerhalb der HU zu überprüfen. Dies bietet eine zusätzliche Ebene der Qualitätssicherung gegen Kommissionierfehler.', Name='Ist Produktcode-Scan Erforderlich', PrintName='Ist Produktcode-Scan Erforderlich',Updated=TO_TIMESTAMP('2025-11-27 20:02:56.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584300 AND AD_Language='de_DE'
;

-- 2025-11-27T20:02:56.623Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-27T20:02:58.067Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584300,'de_DE')
;

-- 2025-11-27T20:02:58.069Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584300,'de_DE')
;

-- Column: MobileUI_UserProfile_DD.IsRequireScanningProductCode
-- 2025-11-27T20:07:05.115Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591608,584300,0,20,542462,'XX','IsRequireScanningProductCode',TO_TIMESTAMP('2025-11-27 20:07:04.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Bei Aktivierung muss der Benutzer den Produktcode (z.B. GTIN/EAN) scannen, nachdem er den Handling Unit (HU) QR Code beim mobilen Kommissionieren gescannt hat.','D',0,1,'Aktiviert einen zusätzlichen obligatorischen Schritt im mobilen Kommissionierprozess, um den spezifischen Produktcode (GTIN/EAN) innerhalb der HU zu überprüfen. Dies bietet eine zusätzliche Ebene der Qualitätssicherung gegen Kommissionierfehler.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Ist Produktcode-Scan Erforderlich',0,0,TO_TIMESTAMP('2025-11-27 20:07:04.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-27T20:07:05.120Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591608 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-27T20:07:05.125Z
/* DDL */  select update_Column_Translation_From_AD_Element(584300)
;

-- 2025-11-27T20:07:06.358Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_DD','ALTER TABLE public.MobileUI_UserProfile_DD ADD COLUMN IsRequireScanningProductCode CHAR(1) DEFAULT ''N'' CHECK (IsRequireScanningProductCode IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> Ist Produktcode-Scan Erforderlich
-- Column: MobileUI_UserProfile_DD.IsRequireScanningProductCode
-- 2025-11-27T20:07:24.881Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591608,758458,0,547735,TO_TIMESTAMP('2025-11-27 20:07:24.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bei Aktivierung muss der Benutzer den Produktcode (z.B. GTIN/EAN) scannen, nachdem er den Handling Unit (HU) QR Code beim mobilen Kommissionieren gescannt hat.',1,'D','Aktiviert einen zusätzlichen obligatorischen Schritt im mobilen Kommissionierprozess, um den spezifischen Produktcode (GTIN/EAN) innerhalb der HU zu überprüfen. Dies bietet eine zusätzliche Ebene der Qualitätssicherung gegen Kommissionierfehler.','Y','N','N','N','N','N','N','N','Ist Produktcode-Scan Erforderlich',TO_TIMESTAMP('2025-11-27 20:07:24.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-27T20:07:24.894Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758458 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-27T20:07:24.900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584300)
;

-- 2025-11-27T20:07:24.936Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758458
;

-- 2025-11-27T20:07:24.939Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758458)
;

-- UI Element: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 20 -> job.Ist Produktcode-Scan Erforderlich
-- Column: MobileUI_UserProfile_DD.IsRequireScanningProductCode
-- 2025-11-27T20:08:16.809Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758458,0,547735,553846,639708,'F',TO_TIMESTAMP('2025-11-27 20:08:16.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bei Aktivierung muss der Benutzer den Produktcode (z.B. GTIN/EAN) scannen, nachdem er den Handling Unit (HU) QR Code beim mobilen Kommissionieren gescannt hat.','Aktiviert einen zusätzlichen obligatorischen Schritt im mobilen Kommissionierprozess, um den spezifischen Produktcode (GTIN/EAN) innerhalb der HU zu überprüfen. Dies bietet eine zusätzliche Ebene der Qualitätssicherung gegen Kommissionierfehler.','Y','N','Y','N','N','Ist Produktcode-Scan Erforderlich',20,0,0,TO_TIMESTAMP('2025-11-27 20:08:16.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 20 -> job.Job automatisch abschließen
-- Column: MobileUI_UserProfile_DD.IsCompleteJobAutomatically
-- 2025-11-27T20:08:23.353Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2025-11-27 20:08:23.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=639183
;

-- UI Element: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 20 -> job.Ist Produktcode-Scan Erforderlich
-- Column: MobileUI_UserProfile_DD.IsRequireScanningProductCode
-- 2025-11-27T20:08:29.825Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2025-11-27 20:08:29.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=639708
;

