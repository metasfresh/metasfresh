-- IsReadyForReceipt's description carries a clause that is only true on the VIEW, on the SHARED element:
--
--   "... eine Zeile ohne Lieferplanung ist immer bereit."
--   "... a row with no delivery planning is always ready."
--
-- AD_Element 585451 is used by M_Delivery_Planning.IsReadyForReceipt AND
-- RV_ReceiptDisposition_DeliveryPlanning.IsReadyForReceipt, and on M_Delivery_Planning EVERY row IS a
-- planning - so "a row with no delivery planning" describes nothing there. The tooltip on that table currently
-- asserts a rule about rows that cannot exist in it.
--
-- This is the split 5823750 (the discharge-quantity descriptions) already states as the rule -- "the ELEMENT
-- carries the general meaning only, and the row-type rule goes on the RV_ReceiptDisposition_DeliveryPlanning
-- columns, where it is the truth" -- and which that script says it took FROM IsReadyForReceipt (5823480).
-- It did not: 5823480 put the row-type clause on the element. So the later migration articulated the principle
-- while the earlier one violated it, and both columns ended up with the identical full text.
--
-- Applying it now matters more than when it shipped: the receipt-readiness gate added alongside this makes the
-- flag meaningful on the delivery-planning window too, where the clause would be read.
--
-- General text -> element + M_Delivery_Planning column. Row-type clause -> the view column only.
-- German on de_DE/de_CH, English on en_US, fr_CH mirroring en_US per this change set's stated convention.

-- ---------------------------------------------------------------------------
-- 1. The element keeps the general meaning only
-- ---------------------------------------------------------------------------
UPDATE AD_Element
   SET Description = 'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist.',
       Updated = TO_TIMESTAMP('2026-09-10 23:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585451
;

UPDATE AD_Element_Trl
   SET Description = 'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist.',
       Updated = TO_TIMESTAMP('2026-09-10 23:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585451 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl
   SET Description = 'Indicates whether the material receipt can be done for this row. A delivery planning is ready once it is allocated to a completed delivery instruction.',
       Updated = TO_TIMESTAMP('2026-09-10 23:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585451 AND AD_Language IN ('en_US','fr_CH')
;

-- ---------------------------------------------------------------------------
-- 2. The row-type clause belongs to the VIEW column, where unplanned rows exist
-- ---------------------------------------------------------------------------
UPDATE AD_Column
   SET Description = 'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist; eine Zeile ohne Lieferplanung ist immer bereit.',
       Updated = TO_TIMESTAMP('2026-09-10 23:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Column_ID = (SELECT c.AD_Column_ID FROM AD_Column c JOIN AD_Table t ON t.AD_Table_ID = c.AD_Table_ID
                        WHERE t.TableName = 'RV_ReceiptDisposition_DeliveryPlanning' AND c.ColumnName = 'IsReadyForReceipt')
;

UPDATE AD_Column_Trl
   SET Description = 'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist; eine Zeile ohne Lieferplanung ist immer bereit.',
       Updated = TO_TIMESTAMP('2026-09-10 23:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Language IN ('de_DE','de_CH')
   AND AD_Column_ID = (SELECT c.AD_Column_ID FROM AD_Column c JOIN AD_Table t ON t.AD_Table_ID = c.AD_Table_ID
                        WHERE t.TableName = 'RV_ReceiptDisposition_DeliveryPlanning' AND c.ColumnName = 'IsReadyForReceipt')
;

UPDATE AD_Column_Trl
   SET Description = 'Indicates whether the material receipt can be done for this row. A delivery planning is ready once it is allocated to a completed delivery instruction; a row with no delivery planning is always ready.',
       Updated = TO_TIMESTAMP('2026-09-10 23:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Language IN ('en_US','fr_CH')
   AND AD_Column_ID = (SELECT c.AD_Column_ID FROM AD_Column c JOIN AD_Table t ON t.AD_Table_ID = c.AD_Table_ID
                        WHERE t.TableName = 'RV_ReceiptDisposition_DeliveryPlanning' AND c.ColumnName = 'IsReadyForReceipt')
;

-- ---------------------------------------------------------------------------
-- 3. The M_Delivery_Planning column follows the element: general text, no row-type clause
-- ---------------------------------------------------------------------------
UPDATE AD_Column
   SET Description = 'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist.',
       Updated = TO_TIMESTAMP('2026-09-10 23:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Column_ID = (SELECT c.AD_Column_ID FROM AD_Column c JOIN AD_Table t ON t.AD_Table_ID = c.AD_Table_ID
                        WHERE t.TableName = 'M_Delivery_Planning' AND c.ColumnName = 'IsReadyForReceipt')
;

UPDATE AD_Column_Trl
   SET Description = 'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist.',
       Updated = TO_TIMESTAMP('2026-09-10 23:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Language IN ('de_DE','de_CH')
   AND AD_Column_ID = (SELECT c.AD_Column_ID FROM AD_Column c JOIN AD_Table t ON t.AD_Table_ID = c.AD_Table_ID
                        WHERE t.TableName = 'M_Delivery_Planning' AND c.ColumnName = 'IsReadyForReceipt')
;

UPDATE AD_Column_Trl
   SET Description = 'Indicates whether the material receipt can be done for this row. A delivery planning is ready once it is allocated to a completed delivery instruction.',
       Updated = TO_TIMESTAMP('2026-09-10 23:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Language IN ('en_US','fr_CH')
   AND AD_Column_ID = (SELECT c.AD_Column_ID FROM AD_Column c JOIN AD_Table t ON t.AD_Table_ID = c.AD_Table_ID
                        WHERE t.TableName = 'M_Delivery_Planning' AND c.ColumnName = 'IsReadyForReceipt')
;
