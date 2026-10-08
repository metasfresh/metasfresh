-- C_OrderLine.GroupCompensation_Product_Category_ID: the product category a compensation line without schema line is computed on.
-- Stored on the order line so that the line keeps its base on reload and on the invoice candidates, whatever happens to the record it was created from.
-- System column: not placed on any window / tab / field.

-- ============================================================================
-- 1) AD_Element
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585520 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'GroupCompensation_Product_Category_ID', 'D', 'Rabatt-Basis Produktkategorie', 'Rabatt-Basis Produktkategorie',
        'Produktkategorie, auf deren Positionen eine Rabattzeile ohne Schema-Zeile berechnet wird.')
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585520
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl
SET Name         = 'Compensation base product category',
    PrintName    = 'Compensation base product category',
    Description  = 'Product category whose lines a compensation line without schema line is computed on.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-06 14:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585520
  AND AD_Language = 'en_US'
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585520, 'de_DE')
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585520, 'de_CH')
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585520, 'en_US')
;

-- ============================================================================
-- 2) AD_Column C_OrderLine.GroupCompensation_Product_Category_ID (AD_Table_ID=260)
--    Search on M_Product_Category (AD_Reference_Value_ID=163), optional
-- ============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593713 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'D', 'GroupCompensation_Product_Category_ID', 260, 585520, 30, 163,
        10, 'Rabatt-Basis Produktkategorie', 'Produktkategorie, auf deren Positionen eine Rabattzeile ohne Schema-Zeile berechnet wird.',
        'N', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593713
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

/* DDL */ SELECT update_Column_Translation_From_AD_Element(585520)
;

-- ============================================================================
-- 3) Physical column, FK and index (only compensation lines without schema line carry a value)
-- ============================================================================
/* DDL */ SELECT public.db_alter_table('C_OrderLine', 'ALTER TABLE public.C_OrderLine ADD COLUMN GroupCompensation_Product_Category_ID NUMERIC(10)')
;

/* DDL */ SELECT public.db_alter_table('C_OrderLine', 'ALTER TABLE public.C_OrderLine ADD CONSTRAINT GroupCompensationProductCategory_COrderLine FOREIGN KEY (GroupCompensation_Product_Category_ID) REFERENCES public.M_Product_Category DEFERRABLE INITIALLY DEFERRED')
;

CREATE INDEX IF NOT EXISTS c_orderline_groupcompensation_product_category_id
    ON C_OrderLine (GroupCompensation_Product_Category_ID)
    WHERE GroupCompensation_Product_Category_ID IS NOT NULL
;
