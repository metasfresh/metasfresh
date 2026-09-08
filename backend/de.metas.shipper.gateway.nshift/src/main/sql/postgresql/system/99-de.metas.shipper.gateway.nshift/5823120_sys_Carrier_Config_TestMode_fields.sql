-- Run mode: SWING_CLIENT

-- nShift shipper config: test mode.
-- New Carrier_Config.IsTestMode (default 'Y') and Carrier_Config.TestMode_Attention (default 'TEST SHIPMENT'),
-- surfaced as fields on the nShift configuration tab (AD_Tab 548455, window 142) next to the existing
-- "Nur als Entwurf versenden" flag. ShipperConfigRepository maps every non-excluded Carrier_Config column to a
-- JsonShipperConfig additional property by column name, so both values are reachable in the nShift client as
-- additionalProperty("IsTestMode") / additionalProperty("TestMode_Attention"). While the flag is on, the
-- configured text IS the Attention sent to nShift for sender and receiver, on the shipment booking and on both
-- advisor calls, replacing the value the mapping configuration would resolve.
--
-- IDs allocated from idserver.metas.de on 2026-09-08:
--   AD_Element     585436 (IsTestMode)          / 585437 (TestMode_Attention)
--   AD_Column      593511 (IsTestMode)          / 593512 (TestMode_Attention)
--   AD_Field       784960 (IsTestMode)          / 784961 (TestMode_Attention)
--   AD_UI_Element  654726 (IsTestMode)          / 654727 (TestMode_Attention)
--
-- Timestamps are spread deliberately (element -> translations -> column -> field -> ui element). The translation
-- propagation functions skip rows where f_trl.updated = e_trl.updated, so reusing one literal across statements
-- would silently leave AD_Field_Trl untranslated.

-- ============================ IsTestMode ============================

-- Element: IsTestMode
-- 2026-09-08T12:00:00Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585436 /*From ID Server*/,0,'IsTestMode',TO_TIMESTAMP('2026-09-08 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Testmodus für die nShift-Schnittstelle. Ist er aktiv, wird der Text aus dem Feld "Testmodus z. Hd." als z.-Hd.-Angabe an nShift gesendet.','D','Ist der Testmodus aktiv, ersetzt der Text aus dem Feld "Testmodus z. Hd." die aus der Zuordnungskonfiguration ermittelte z.-Hd.-Angabe, und zwar bei Absender und Empfänger, bei jeder Sendungsbuchung und bei beiden Avis-Aufrufen. Der Text wird auf das Versandetikett gedruckt und kennzeichnet die Sendung als Testsendung. Das Feld "Testmodus z. Hd." darf dabei nicht leer sein: eine leere z.-Hd.-Angabe wird von nShift abgelehnt, damit keine Sendung unmarkiert gebucht werden kann. Beim Einspielen einer Produktivdatenbank wird der Testmodus automatisch wieder aktiviert.','Y','Testmodus','Testmodus',TO_TIMESTAMP('2026-09-08 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-09-08T12:00:00Z (inherits the element's timestamp — same event)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585436 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsTestMode (en_US translation)
-- 2026-09-08T12:00:12Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Test Mode', PrintName='Test Mode', Description='Test mode for the nShift interface. While it is on, the text in the "Test Mode Attention" field is sent to nShift as the Attention.', Help='While test mode is on, the text in the "Test Mode Attention" field replaces the Attention resolved from the mapping configuration, for both the sender and the receiver, on every shipment booking and on both advisor calls. It is printed on the shipping label and marks the shipment as a test. "Test Mode Attention" must not be empty: an empty Attention is rejected by nShift, so that a shipment can never be booked unmarked. After a production database is copied in, test mode is switched back on automatically.',Updated=TO_TIMESTAMP('2026-09-08 12:00:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585436 AND AD_Language='en_US'
;

-- 2026-09-08T12:00:12Z (copies the en_US trl row — only when en_US is the base language)
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Element_ID=585436 AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-09-08T12:00:12Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585436,'en_US')
;

-- Element: IsTestMode (de_CH translation — inherits de_DE, no ß to swap)
-- 2026-09-08T12:00:18Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-08 12:00:18','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585436 AND AD_Language='de_CH'
;

-- 2026-09-08T12:00:18Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585436,'de_CH')
;

-- Element: IsTestMode (de_DE translation)
-- 2026-09-08T12:00:24Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-08 12:00:24','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585436 AND AD_Language='de_DE'
;

-- 2026-09-08T12:00:24Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(585436,'de_DE')
;

-- 2026-09-08T12:00:24Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585436,'de_DE')
;

-- Column: Carrier_Config.IsTestMode
-- PersonalDataCategory 'NP': a technical configuration flag, no personal data.
-- 2026-09-08T12:01:00Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593511 /*From ID Server*/,585436,0,20,542540,'XX','IsTestMode',TO_TIMESTAMP('2026-09-08 12:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Testmodus','NP',0,0,TO_TIMESTAMP('2026-09-08 12:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-09-08T12:01:00Z (inherits the column's timestamp — same event)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593511 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-09-08T12:01:00Z
/* DDL */  select update_Column_Translation_From_AD_Element(585436)
;

-- DDL: Add physical column to Carrier_Config table.
-- ADD COLUMN with a non-volatile DEFAULT populates existing rows, so every carrier configuration already on the
-- instance comes out of this migration in test mode. A productive instance is switched to 'N' deliberately, by
-- hand; ops.after_transfer_db switches it back on after a database copy, because a copy of production arrives
-- with 'N'. IsMandatory='Y' on the AD_Column above is kept in sync with this NOT NULL.
-- 2026-09-08T12:01:30Z
/* DDL */ SELECT public.db_alter_table('Carrier_Config','ALTER TABLE public.Carrier_Config ADD COLUMN IsTestMode CHAR(1) DEFAULT ''Y'' NOT NULL')
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Testmodus
-- Column: Carrier_Config.IsTestMode
-- 2026-09-08T12:02:00Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,593511,784960 /*From ID Server*/,0,548455,0,TO_TIMESTAMP('2026-09-08 12:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Testmodus',0,0,20,0,1,1,TO_TIMESTAMP('2026-09-08 12:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-09-08T12:02:00Z (inherits the field's timestamp — same event)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784960 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-09-08T12:02:10Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585436)
;

-- 2026-09-08T12:02:20Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=784960
;

-- 2026-09-08T12:02:20Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(784960)
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 92 -> name.Testmodus
-- Column: Carrier_Config.IsTestMode
-- Placed as a normal (non-advanced) field right after "Nur als Entwurf versenden" (SeqNo 90): an operator has to
-- find this flag to switch it off on a productive instance, so it must not sit in the advanced-edit overlay.
-- 2026-09-08T12:02:30Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,784960,0,548455,553597,654726 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-08 12:02:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Testmodus',92,0,0,TO_TIMESTAMP('2026-09-08 12:02:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- ============================ TestMode_Attention ============================

-- Element: TestMode_Attention
-- 2026-09-08T12:10:00Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585437 /*From ID Server*/,0,'TestMode_Attention',TO_TIMESTAMP('2026-09-08 12:10:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Text, der bei aktivem Testmodus als z.-Hd.-Angabe an nShift gesendet wird.','D','Dieser Text wird bei aktivem Testmodus als z.-Hd.-Angabe an nShift gesendet, für Absender und Empfänger. Er sollte höchstens 30 Zeichen lang sein, die für die z.-Hd.-Angabe auf dem Versandetikett dokumentierte Länge. Bei aktivem Testmodus darf das Feld nicht leer sein, denn eine leere z.-Hd.-Angabe wird von nShift abgelehnt.','Y','Testmodus z. Hd.','Testmodus z. Hd.',TO_TIMESTAMP('2026-09-08 12:10:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-09-08T12:10:00Z (inherits the element's timestamp — same event)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585437 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: TestMode_Attention (en_US translation)
-- 2026-09-08T12:10:12Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Test Mode Attention', PrintName='Test Mode Attention', Description='Text sent to nShift as the Attention while test mode is on.', Help='While test mode is on, this text is sent to nShift as the Attention, for both the sender and the receiver. Keep it within 30 characters, the length documented for the Attention on the shipping label. It must not be empty while test mode is on, because an empty Attention is rejected by nShift.',Updated=TO_TIMESTAMP('2026-09-08 12:10:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585437 AND AD_Language='en_US'
;

-- 2026-09-08T12:10:12Z (copies the en_US trl row — only when en_US is the base language)
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Element_ID=585437 AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-09-08T12:10:12Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585437,'en_US')
;

-- Element: TestMode_Attention (de_CH translation — inherits de_DE, no ß to swap)
-- 2026-09-08T12:10:18Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-08 12:10:18','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585437 AND AD_Language='de_CH'
;

-- 2026-09-08T12:10:18Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585437,'de_CH')
;

-- Element: TestMode_Attention (de_DE translation)
-- 2026-09-08T12:10:24Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-08 12:10:24','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585437 AND AD_Language='de_DE'
;

-- 2026-09-08T12:10:24Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(585437,'de_DE')
;

-- 2026-09-08T12:10:24Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585437,'de_DE')
;

-- Column: Carrier_Config.TestMode_Attention
-- PersonalDataCategory 'NP': a technical configuration text, no personal data.
-- 2026-09-08T12:11:00Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593512 /*From ID Server*/,585437,0,10,542540,'XX','TestMode_Attention',TO_TIMESTAMP('2026-09-08 12:11:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','TEST SHIPMENT','D',0,60,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Testmodus z. Hd.','NP',0,0,TO_TIMESTAMP('2026-09-08 12:11:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-09-08T12:11:00Z (inherits the column's timestamp — same event)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593512 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-09-08T12:11:00Z
/* DDL */  select update_Column_Translation_From_AD_Element(585437)
;

-- DDL: Add physical column to Carrier_Config table.
-- Nullable on purpose (IsMandatory='N' above is kept in sync): ShipperConfigRepository drops null column values
-- from the additional-property map, and the nShift client reads the property with a nullable getter and falls
-- back to an empty Attention, which nShift rejects. That is the intended fail-safe: test mode on with no text
-- must fail the booking, never send the real Attention and let the shipment go out unmarked.
-- 2026-09-08T12:11:30Z
/* DDL */ SELECT public.db_alter_table('Carrier_Config','ALTER TABLE public.Carrier_Config ADD COLUMN TestMode_Attention VARCHAR(60) DEFAULT ''TEST SHIPMENT''')
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Testmodus z. Hd.
-- Column: Carrier_Config.TestMode_Attention
-- 2026-09-08T12:12:00Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,593512,784961 /*From ID Server*/,0,548455,0,TO_TIMESTAMP('2026-09-08 12:12:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Testmodus z. Hd.',0,0,20,0,1,1,TO_TIMESTAMP('2026-09-08 12:12:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-09-08T12:12:00Z (inherits the field's timestamp — same event)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784961 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-09-08T12:12:10Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585437)
;

-- 2026-09-08T12:12:20Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=784961
;

-- 2026-09-08T12:12:20Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(784961)
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 94 -> name.Testmodus z. Hd.
-- Column: Carrier_Config.TestMode_Attention
-- 2026-09-08T12:12:30Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,784961,0,548455,553597,654727 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-08 12:12:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Testmodus z. Hd.',94,0,0,TO_TIMESTAMP('2026-09-08 12:12:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
