-- gh#28675: Forecast polish round 2
--
-- 1. DatePromised: change from DateTime (16) to Date (15) on M_Forecast
--    Root cause: Date+Time caused period lookup failure (00:59 vs 00:00 boundary)
-- 2. Fix AD_Name_ID on forecast line fields on standalone tab 548194 (Prognose-Position window 541900)
--    Migration 5793890 only set AD_Name_ID on tab 654 fields (main window), not on tab 548194
-- 3. Hide useless fields from forecast lines in BOTH tabs (654 in main window, 548194 in standalone)
-- 4. Make Lager (M_Warehouse_ID) a search field on Forecast header
-- 5. Clean up grid view columns for both forecast line tabs

-- ==========================================================================
-- 1. DatePromised: DateTime (16) → Date (15) on M_Forecast
-- ==========================================================================
UPDATE AD_Column SET
  AD_Reference_ID=15, -- Date instead of Date+Time
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Column_ID=557887 -- M_Forecast.DatePromised
  AND AD_Reference_ID=16; -- only if still DateTime

-- ==========================================================================
-- 2. Fix AD_Name_ID on standalone tab 548194 (Prognose-Position window 541900)
--    Elements 584657 (Menge), 584658 (Berechnete Menge), 584644 (Stichtag)
-- ==========================================================================
UPDATE AD_Field SET
  AD_Name_ID=584657,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=747559; -- Qty on tab 548194

UPDATE AD_Field SET
  AD_Name_ID=584658,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=747564; -- QtyCalculated on tab 548194

UPDATE AD_Field SET
  AD_Name_ID=584644,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=747566; -- DatePromised on tab 548194

SELECT update_FieldTranslation_From_AD_Name_Element(584657);
SELECT update_FieldTranslation_From_AD_Name_Element(584658);
SELECT update_FieldTranslation_From_AD_Name_Element(584644);

-- ==========================================================================
-- 3a. Hide useless UI elements on tab 654 (main Prognose window 328)
--     Keep: Produkt, Menge, Berechnete Menge, Maßeinheit, Lager, Stichtag
--     Hide: Merkmale, Menge TU, Packvorschrift, Geschäftspartner, Kundenbetreuer,
--           Aktiv, Sektion, Mandant, Kostenstelle, Werbemassnahme, Projekt
-- ==========================================================================

-- Hide M_AttributeSetInstance_ID (Merkmale)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=543133;

-- Hide QtyEnteredTU (Menge TU)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=549295;

-- Hide M_HU_PI_Item_Product_ID (Packvorschrift)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=549296;

-- Hide C_BPartner_ID (Geschäftspartner)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=543139;

-- Hide SalesRep_ID (Vertriebsbeauftragter)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=543130;

-- Hide IsActive (Aktiv)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=543138;

-- Hide AD_Org_ID (Sektion)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=549007;

-- Hide AD_Client_ID (Mandant)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=549008;

-- Hide C_Activity_ID (Kostenstelle)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=637024;

-- Hide C_Campaign_ID (Werbemassnahme)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=637025;

-- Hide C_Project_ID (Projekt)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=637026;

-- Reorder remaining elements on tab 654:
-- Produkt (10), Menge (20), Berechnete Menge (30), Maßeinheit (40), Lager (50), Stichtag (60)
UPDATE AD_UI_Element SET SeqNo=10,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=543132; -- Produkt

UPDATE AD_UI_Element SET SeqNo=20,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=543134; -- Menge

UPDATE AD_UI_Element SET SeqNo=30,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=543135; -- Berechnete Menge (Calculated Quantity)

UPDATE AD_UI_Element SET SeqNo=40,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=549297; -- Maßeinheit

UPDATE AD_UI_Element SET SeqNo=50,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=543131; -- Lager

UPDATE AD_UI_Element SET SeqNo=60,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=543137; -- Stichtag (DatePromised)

-- ==========================================================================
-- 3b. Hide useless UI elements on tab 548194 (standalone Prognose-Position window 541900)
--     Same cleanup as tab 654
-- ==========================================================================

-- Hide M_Forecast_ID (parent link, redundant)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633946;

-- Hide M_AttributeSetInstance_ID (Merkmale)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633948;

-- Hide QtyEnteredTU (Menge TU)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633949;

-- Hide M_HU_PI_Item_Product_ID (Packvorschrift)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633950;

-- Hide C_BPartner_ID (Geschäftspartner)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633955;

-- Hide SalesRep_ID (Kundenbetreuer)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633957;

-- Hide IsActive (Aktiv)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633958;

-- Hide AD_Org_ID (Sektion)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633959;

-- Hide AD_Client_ID (Mandant)
UPDATE AD_UI_Element SET IsActive='N',
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633960;

-- Reorder remaining elements on tab 548194
UPDATE AD_UI_Element SET SeqNo=10,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633947; -- Produkt

UPDATE AD_UI_Element SET SeqNo=20,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633951; -- Menge

UPDATE AD_UI_Element SET SeqNo=30,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633953; -- Berechnete Menge

UPDATE AD_UI_Element SET SeqNo=40,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633952; -- Maßeinheit

UPDATE AD_UI_Element SET SeqNo=50,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633954; -- Lager

UPDATE AD_UI_Element SET SeqNo=60,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=633956; -- Stichtag

-- ==========================================================================
-- 4. Make Lager (M_Warehouse_ID) a search field on Forecast header
--    Change AD_Reference_ID from 19 (Table Direct) to 30 (Search)
-- ==========================================================================
UPDATE AD_Column SET
  AD_Reference_ID=30, -- Search instead of Table Direct
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Column_ID=557885 -- M_Forecast.M_Warehouse_ID
  AND AD_Reference_ID=19; -- only if still Table Direct

-- ==========================================================================
-- 5a. Clean up grid view on tab 654 (main Prognose window)
--     Hide useless grid columns, reorder to: Produkt, Menge, Berechnete Menge, Maßeinheit, Lager, Stichtag
-- ==========================================================================

-- Hide useless grid columns on tab 654
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Tab_ID=654 AND AD_Field_ID IN (
  56277,   -- SalesRep_ID
  555660,  -- M_AttributeSetInstance_ID
  10308,   -- IsActive
  554032,  -- C_BPartner_ID
  554034,  -- C_BPartner_Location_ID
  753602,  -- C_Activity_ID
  753603,  -- C_Campaign_ID
  753604,  -- C_Project_ID
  560572,  -- M_HU_PI_Item_Product_ID
  560571,  -- QtyEnteredTU
  560573   -- C_UOM_ID (will re-add with correct seqno below)
);

-- Set grid order: Produkt (10), Menge (20), Berechnete Menge (30), Maßeinheit (40), Lager (50), Stichtag (60)
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=10301; -- M_Product_ID

UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=10306; -- Qty

UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=10310; -- QtyCalculated

UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=560573; -- C_UOM_ID

UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=53638; -- M_Warehouse_ID

UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=53639; -- DatePromised

-- Hide period from grid (not very useful in grid view)
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=10303; -- C_Period_ID

-- ==========================================================================
-- 5b. Set up grid view columns for standalone tab 548194
-- ==========================================================================
UPDATE AD_Field SET
  IsDisplayedGrid='N', SeqNoGrid=0,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Tab_ID=548194;

UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=747562; -- M_Product_ID

UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=747559; -- Qty

UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=747564; -- QtyCalculated

UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=747573; -- C_UOM_ID

UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=747565; -- M_Warehouse_ID

UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,
  Updated=TO_TIMESTAMP('2026-03-13 18:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=747566; -- DatePromised
