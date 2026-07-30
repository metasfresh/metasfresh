-- Intrastat preview window — AD_Window, AD_Tab, AD_Fields on Intrastat_Preview_V.
-- Grid-only preview tab (IsSingleRow='N', IsReadOnly='Y'); 13 business columns displayed,
-- 7 audit columns + synthetic PK hidden. Default grid sort: CNCode ASC.

-- =====================================================================
-- 0. AD_Element for the new window's caption
-- =====================================================================
-- AD_Window.AD_Element_ID is globally UNIQUE (constraint ad_window_element_uq),
-- so we cannot reuse the existing 'Intrastat' element (584668, in use by the
-- debug window). A dedicated element for the preview window is required;
-- the tab-level caption (below) can still reuse 584668.
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, Name, PrintName)
VALUES (585150 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat_Preview', 'D', 'Intrastat Vorschau', 'Intrastat Vorschau');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
    Name, PrintName)
VALUES (585150 /*From ID Server*/, 'en_US', 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, 'Y',
    'Intrastat Preview', 'Intrastat Preview');

-- =====================================================================
-- 1. AD_Window (Intrastat preview)
-- =====================================================================
INSERT INTO AD_Window (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, EntityType, WindowType, IsDefault, IsSOTrx,
    IsBetaFunctionality, Processing, AD_Element_ID)
VALUES (542179 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat Vorschau', 'D', 'M', 'N', 'Y',
    'N', 'N', 585150);

-- =====================================================================
-- 2. AD_Tab (grid-only preview on Intrastat_Preview_V, AD_Table_ID=542632)
-- =====================================================================
-- AD_Element 584668 reused for the tab caption too.
INSERT INTO AD_Tab (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Table_ID, AD_Window_ID, EntityType, AD_Element_ID,
    TabLevel, SeqNo, IsSingleRow, IsReadOnly,
    IsInsertRecord, IsAdvancedTab,
    IsSearchCollapsed, HasTree, IsInfoTab,
    IsTranslationTab, IsSortTab,
    AllowQuickInput, IncludedTabNewRecordInputMode,
    IsAutodetectDefaultDateFilter, IsGridModeOnly,
    IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents)
VALUES (549359 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat', 542632, 542179, 'D', 584668,
    0, 10, 'N', 'Y',
    'N', 'N',
    'Y', 'N', 'N',
    'N', 'N',
    'N', 'A',
    'Y', 'Y',
    'N', 'N');

-- =====================================================================
-- 3. AD_Fields (21 fields — one per AD_Column on AD_Table 542632)
-- =====================================================================
-- IsReadOnly='Y' on all fields (preview only, view-backed).
-- IsDisplayed='N' on: PK (Intrastat_Preview_V_ID) + 7 audit columns.
-- IsDisplayed='Y' on the 13 business columns.
-- Form SeqNo: 10..130 across the 13 business columns; audit + PK use 0.

-- Field 1: Intrastat_Preview_V_ID (PK — hidden)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (781862 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat Preview', 593053, 549359, 'D',
    'N', 'Y', 'N');

-- Field 2: AD_Client_ID (hidden)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (781863 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Mandant', 593054, 549359, 'D',
    'N', 'Y', 'N');

-- Field 3: AD_Org_ID (hidden)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (781864 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Sektion', 593055, 549359, 'D',
    'N', 'Y', 'N');

-- Field 4: IsActive (hidden)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (781865 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Aktiv', 593056, 549359, 'D',
    'N', 'Y', 'N');

-- Field 5: Created (hidden)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (781866 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Erstellt', 593057, 549359, 'D',
    'N', 'Y', 'N');

-- Field 6: CreatedBy (hidden)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (781867 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Erstellt durch', 593058, 549359, 'D',
    'N', 'Y', 'N');

-- Field 7: Updated (hidden)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (781868 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Aktualisiert', 593059, 549359, 'D',
    'N', 'Y', 'N');

-- Field 8: UpdatedBy (hidden)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (781869 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Aktualisiert durch', 593060, 549359, 'D',
    'N', 'Y', 'N');

-- Field 9: IsSOTrx (displayed, form SeqNo=110)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781870 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Verkaufstransaktion', 593061, 549359, 'D',
    'Y', 110, 'Y', 'N');

-- Field 10: C_Year_ID (displayed, form SeqNo=120)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781871 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Jahr', 593062, 549359, 'D',
    'Y', 120, 'Y', 'N');

-- Field 11: C_Period_ID (displayed, form SeqNo=130)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781872 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Periode', 593063, 549359, 'D',
    'Y', 130, 'Y', 'N');

-- Field 12: CNCode (displayed, form SeqNo=10 — primary sort)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, SortNo, IsReadOnly, IsEncrypted)
VALUES (781873 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'CN-Code', 593064, 549359, 'D',
    'Y', 10, 10, 'Y', 'N');

-- Field 13: GoodsDescription (displayed, form SeqNo=20)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781874 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Warenbezeichnung', 593065, 549359, 'D',
    'Y', 20, 'Y', 'N');

-- Field 14: CountryDestinationConsignment (displayed, form SeqNo=30)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781875 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Bestimmungs-/Versendungsland', 593066, 549359, 'D',
    'Y', 30, 'Y', 'N');

-- Field 15: CountryOfOrigin (displayed, form SeqNo=40)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781876 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Ursprungsland', 593067, 549359, 'D',
    'Y', 40, 'Y', 'N');

-- Field 16: IntrastaNatureOfTransaction (displayed, form SeqNo=50)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781877 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Art des Geschaefts', 593068, 549359, 'D',
    'Y', 50, 'Y', 'N');

-- Field 17: NetMass (displayed, form SeqNo=60)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781878 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Eigenmasse', 593069, 549359, 'D',
    'Y', 60, 'Y', 'N');

-- Field 18: SupplementaryUnits (displayed, form SeqNo=70)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781879 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Besondere Masseinheit', 593070, 549359, 'D',
    'Y', 70, 'Y', 'N');

-- Field 19: InvoiceValue (displayed, form SeqNo=80)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781880 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Rechnungsbetrag', 593071, 549359, 'D',
    'Y', 80, 'Y', 'N');

-- Field 20: StatisticalValue (displayed, form SeqNo=90)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781881 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Statistischer Wert', 593072, 549359, 'D',
    'Y', 90, 'Y', 'N');

-- Field 21: RecipientVATNo (displayed, form SeqNo=100)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, SeqNo, IsReadOnly, IsEncrypted)
VALUES (781882 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'USt-IdNr. Empfaenger', 593073, 549359, 'D',
    'Y', 100, 'Y', 'N');

-- =====================================================================
-- 4. AD_Field_Trl — skeleton rows for all active system languages
-- =====================================================================
-- Seed one AD_Field_Trl per active system language + AD_Field on this tab
-- (skill metasfresh-application-dictionary § "Ordering matters" — mandatory
-- post-INSERT step before propagation).
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, f.AD_Field_ID, f.Name, 'N',
    f.AD_Client_ID, f.AD_Org_ID, f.Created, f.Createdby, f.Updated, f.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND f.AD_Tab_ID = 549359
  AND NOT EXISTS (
      SELECT 1 FROM AD_Field_Trl tt
      WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID);

-- =====================================================================
-- 5. Propagate translations from AD_Element → AD_Field / AD_Field_Trl
-- =====================================================================
-- For each of the 21 AD_Columns backing this tab, call the propagation
-- function (skill metasfresh-application-dictionary — takes an
-- AD_Element_ID; standard fields use the column's AD_Element_ID).
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585149); -- Intrastat_Preview_V_ID
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(102);    -- AD_Client_ID
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(113);    -- AD_Org_ID
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(348);    -- IsActive
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(245);    -- Created
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(246);    -- CreatedBy
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(607);    -- Updated
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(608);    -- UpdatedBy
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(1106);   -- IsSOTrx
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(223);    -- C_Year_ID
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(206);    -- C_Period_ID
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584088); -- CNCode
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584089); -- GoodsDescription
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584090); -- CountryDestinationConsignment
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584091); -- CountryOfOrigin
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584085); -- IntrastaNatureOfTransaction
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584092); -- NetMass
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584093); -- SupplementaryUnits
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584094); -- InvoiceValue
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584095); -- StatisticalValue
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584096); -- RecipientVATNo

-- =====================================================================
-- 6. Rebuild AD_Element_Link for the new fields
-- =====================================================================
DELETE FROM AD_Element_Link
WHERE AD_Field_ID IN (781862, 781863, 781864, 781865, 781866, 781867, 781868, 781869,
                      781870, 781871, 781872, 781873, 781874, 781875, 781876, 781877,
                      781878, 781879, 781880, 781881, 781882);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781862);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781863);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781864);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781865);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781866);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781867);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781868);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781869);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781870);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781871);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781872);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781873);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781874);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781875);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781876);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781877);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781878);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781879);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781880);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781881);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781882);

-- =====================================================================
-- 7. Propagate en_US translations from AD_Element_Trl to AD_Field_Trl
-- =====================================================================
-- The auto-generated AD_Field_Trl records copy base language text with
-- IsTranslated='N'. Fill the en_US rows from AD_Element_Trl where available
-- (same pattern used in the sibling debug-window migration 5794280).
UPDATE AD_Field_Trl
SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y'
FROM AD_Field f
JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID
JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID AND et.AD_Language = 'en_US'
WHERE AD_Field_Trl.AD_Field_ID = f.AD_Field_ID
  AND f.AD_Tab_ID = 549359
  AND AD_Field_Trl.AD_Language = 'en_US'
  AND AD_Field_Trl.IsTranslated = 'N'
  AND et.IsTranslated = 'Y';
