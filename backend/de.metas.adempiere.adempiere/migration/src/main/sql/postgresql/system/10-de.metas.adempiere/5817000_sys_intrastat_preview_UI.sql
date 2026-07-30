-- Intrastat preview window — UI layout (grid columns, section, element group).
-- Grid-only tab: AD_UI_Section + one AD_UI_Column + one AD_UI_ElementGroup holding
-- the 13 displayed business fields. Grid column order mirrors the Intrastat_Export
-- function output: CNCode, GoodsDescription, CountryDestinationConsignment,
-- CountryOfOrigin, IntrastaNatureOfTransaction, NetMass, SupplementaryUnits,
-- InvoiceValue, StatisticalValue, RecipientVATNo, then IsSOTrx / C_Year_ID /
-- C_Period_ID at the end as filter-visible columns.

-- =====================================================================
-- 1. AD_UI_Section
-- =====================================================================
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Tab_ID, Name, SeqNo)
VALUES (547864 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    549359, 'main', 10);

-- =====================================================================
-- 2. AD_UI_Column
-- =====================================================================
INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_UI_Section_ID, SeqNo)
VALUES (549612 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    547864, 10);

-- =====================================================================
-- 3. AD_UI_ElementGroup
-- =====================================================================
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_UI_Column_ID, Name, SeqNo, UIStyle)
VALUES (555533 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    549612, 'Intrastat', 10, 'label');

-- =====================================================================
-- 4. AD_UI_Elements — 13 grid columns (in Intrastat_Export output order)
-- =====================================================================

-- Grid col 1: CNCode (SeqNoGrid=10, form SeqNo=10)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652788 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781873, 549359, 555533, 'F',
    'CN-Code', 10, 10, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 2: GoodsDescription (SeqNoGrid=20)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652789 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781874, 549359, 555533, 'F',
    'Warenbezeichnung', 20, 20, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 3: CountryDestinationConsignment (SeqNoGrid=30)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652790 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781875, 549359, 555533, 'F',
    'Bestimmungs-/Versendungsland', 30, 30, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 4: CountryOfOrigin (SeqNoGrid=40)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652791 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781876, 549359, 555533, 'F',
    'Ursprungsland', 40, 40, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 5: IntrastaNatureOfTransaction (SeqNoGrid=50)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652792 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781877, 549359, 555533, 'F',
    'Art des Geschaefts', 50, 50, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 6: NetMass (SeqNoGrid=60)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652793 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781878, 549359, 555533, 'F',
    'Eigenmasse', 60, 60, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 7: SupplementaryUnits (SeqNoGrid=70)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652794 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781879, 549359, 555533, 'F',
    'Besondere Masseinheit', 70, 70, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 8: InvoiceValue (SeqNoGrid=80)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652795 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781880, 549359, 555533, 'F',
    'Rechnungsbetrag', 80, 80, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 9: StatisticalValue (SeqNoGrid=90)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652796 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781881, 549359, 555533, 'F',
    'Statistischer Wert', 90, 90, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 10: RecipientVATNo (SeqNoGrid=100)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652797 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781882, 549359, 555533, 'F',
    'USt-IdNr. Empfaenger', 100, 100, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 11: IsSOTrx (SeqNoGrid=110 — filter column)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652798 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781870, 549359, 555533, 'F',
    'Verkaufstransaktion', 110, 110, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 12: C_Year_ID (SeqNoGrid=120 — filter column)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652799 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781871, 549359, 555533, 'F',
    'Jahr', 120, 120, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 13: C_Period_ID (SeqNoGrid=130 — filter column)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652800 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    781872, 549359, 555533, 'F',
    'Periode', 130, 130, 0,
    'Y', 'Y', 'N', 'N');
