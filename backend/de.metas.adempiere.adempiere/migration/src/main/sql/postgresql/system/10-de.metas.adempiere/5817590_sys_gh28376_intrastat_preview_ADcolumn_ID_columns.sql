-- Intrastat_Preview_V window (AD_Table 542632): three new AD_Columns to expose M_Product_ID
-- (Search ref 30, replaces GoodsDescription for zoom-into), C_UOM_ID (Search ref 30),
-- C_Currency_ID (Table Direct ref 19). Existing GoodsDescription AD_Column (593065) is
-- deactivated (IsActive='N') — it maps to a view column that no longer exists after migration
-- 5817570.
--
-- Uses standard shared AD_Elements: 454 (M_Product_ID), 215 (C_UOM_ID), 193 (C_Currency_ID) —
-- same elements the sibling debug window (AD_Table 542587) uses.

-- =====================================================================
-- 1. Deactivate the old GoodsDescription AD_Column
-- =====================================================================
UPDATE AD_Column
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-05 10:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Column_ID = 593065; -- GoodsDescription

-- =====================================================================
-- 2. New AD_Column: M_Product_ID (ref 30 = Search — zoom into product)
-- =====================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593074 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-08-05 10:00:01','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-05 10:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
    454, 542632, 30,
    'M_Product_ID', 'Produkt', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    125);

-- =====================================================================
-- 3. New AD_Column: C_UOM_ID (ref 30 = Search)
-- =====================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593075 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-08-05 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-05 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
    215, 542632, 30,
    'C_UOM_ID', 'Maßeinheit', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    185);

-- =====================================================================
-- 4. New AD_Column: C_Currency_ID (ref 19 = Table Direct)
-- =====================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593076 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-08-05 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-05 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
    193, 542632, 19,
    'C_Currency_ID', 'Währung', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    205);

-- =====================================================================
-- 5. Seed AD_Column_Trl rows for every active system language + propagate
--    translations from the linked AD_Elements to each new column.
-- =====================================================================
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Column_ID IN (593074, 593075, 593076)
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(454); -- M_Product_ID
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(215); -- C_UOM_ID
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(193); -- C_Currency_ID
