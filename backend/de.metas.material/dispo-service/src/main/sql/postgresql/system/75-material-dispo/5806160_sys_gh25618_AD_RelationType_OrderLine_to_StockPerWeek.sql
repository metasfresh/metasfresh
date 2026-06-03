-- IDs allocated from idserver.metas.de on 2026-06-04:
--   AD_Reference  542100  (target: RelType C_OrderLine -> MD_Stock_PerWeek_V)
--   AD_RelationType 540499 (C_OrderLine_MD_Stock_PerWeek)
--
-- Source AD_Reference: 271 ("C_OrderLine SO", ValidationType='T', AD_Table_ID=260)
-- Target AD_Table_ID:  542612 (MD_Stock_PerWeek_V)
-- Target AD_Window_ID: 542159 (Bestand pro Woche)
-- Target AD_Key:       592708 (WeekStartDate column)

-- 2026-06-04
-- Target AD_Reference for MD_Stock_PerWeek_V
INSERT INTO AD_Reference
    (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES
    (0, 0, 542100 /*From ID Server*/, TO_TIMESTAMP('2026-06-04 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.material.dispo', 'Y', 'N', 'RelType C_OrderLine->MD_Stock_PerWeek_V (target)', TO_TIMESTAMP('2026-06-04 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;

-- 2026-06-04
INSERT INTO AD_Reference_Trl
    (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Reference_ID = 542100
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- 2026-06-04
-- AD_Ref_Table for the target reference:
--   Table:        MD_Stock_PerWeek_V (AD_Table_ID=542612)
--   Window:       Bestand pro Woche  (AD_Window_ID=542159)
--   Key column:   WeekStartDate      (AD_Column_ID=592708)
--   WhereClause:  filters by product + resolved storage warehouse + week anchor from DatePromised
--   OrderByClause: WeekStartDate
INSERT INTO AD_Ref_Table
    (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID,
     Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, OrderByClause,
     ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES
    (0, 592708, 0, 542100, 542612, 542159,
     TO_TIMESTAMP('2026-06-04 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.material.dispo', 'Y', 'N', 'WeekStartDate',
     'N', TO_TIMESTAMP('2026-06-04 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Product_ID = @M_Product_ID@
AND M_Warehouse_ID = MD_getStockWarehouse( (SELECT o.M_Warehouse_ID FROM C_Order o WHERE o.C_Order_ID = @C_Order_ID/0@) )
AND WeekStartDate >= date_trunc(''week'', COALESCE(@DatePromised@, now()))::date')
;

-- 2026-06-04
-- AD_RelationType: directed zoom from C_OrderLine (source=271) to MD_Stock_PerWeek_V (target=542100)
--   Source ref 271: "C_OrderLine SO" — ValidationType=T, AD_Table_ID=260 (C_OrderLine), no WhereClause
--   IsDirected=Y: zoom is one-way (from order line to stock view)
--   Role_Source: label shown on the C_OrderLine side (DE: "Auftrag", EN: "Order")
--   Role_Target: label shown on the stock-per-week side (DE: "Bestand pro Woche" / EN: "Stock per week")
INSERT INTO AD_RelationType
    (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID,
     Created, CreatedBy, EntityType, InternalName, IsActive, IsTableRecordIdTarget,
     Name, Role_Source, Role_Target, Updated, UpdatedBy, IsDirected)
VALUES
    (0, 0, 271, 542100, 540499 /*From ID Server*/,
     TO_TIMESTAMP('2026-06-04 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.material.dispo', 'C_OrderLine_MD_Stock_PerWeek', 'Y', 'N',
     'C_OrderLine -> MD_Stock_PerWeek_V', 'Auftrag', 'Bestand pro Woche', TO_TIMESTAMP('2026-06-04 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'Y')
;
