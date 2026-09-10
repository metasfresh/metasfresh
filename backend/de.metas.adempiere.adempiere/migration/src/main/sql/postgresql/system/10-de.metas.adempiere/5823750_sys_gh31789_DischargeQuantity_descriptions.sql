-- PlannedDischargeQuantity (AD_Element 581795) and ActualDischargeQuantity (581796) shipped with no
-- Description in any language, so neither column carries a tooltip. On the receipt-disposition window that
-- matters more than usual: an UNPLANNED row has no planning to ask, so its planned figure is what was
-- ORDERED (see RV_ReceiptDisposition_DeliveryPlanning.sql, rs.qtyordered AS planneddischargequantity),
-- which makes it identical to that row's Qty Ordered column. Two adjacent columns showing the same number
-- with no explanation reads as a bug rather than a definition.
--
-- The text is deliberately split across two levels, because the row-type rule is NOT generally true.
-- Eleven columns across seven tables share these two elements (M_Delivery_Planning, M_ShippingPackage,
-- I_DeliveryPlanning, the two delivery-instruction views, the transport-order planning-history view and
-- this view), and on M_Delivery_Planning the planned figure
-- is ALWAYS the planning's own decision. So the ELEMENT carries the general meaning only, and the row-type
-- rule goes on the two RV_ReceiptDisposition_DeliveryPlanning columns, where it is the truth.
--
-- Shape follows IsReadyForReceipt (5823480): element + element_trl + column + column_trl, German on
-- de_DE/de_CH, English on en_US, and fr_CH mirroring en_US per this change set's stated convention.
-- IsTranslated is left alone -- adding a description does not translate a name. Each UPDATE is guarded on
-- the description still being empty, so it cannot overwrite a text somebody has since written.

UPDATE AD_Element SET Description='Die für die Entladung geplante Menge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581795 AND COALESCE(Description,'')=''
;

UPDATE AD_Element_Trl SET Description='Die für die Entladung geplante Menge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581795 AND AD_Language IN ('de_DE','de_CH') AND COALESCE(Description,'')=''
;

UPDATE AD_Element_Trl SET Description='The quantity planned for discharge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581795 AND AD_Language IN ('en_US','fr_CH') AND COALESCE(Description,'')=''
;

UPDATE AD_Element SET Description='Die tatsächlich entladene Menge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581796 AND COALESCE(Description,'')=''
;

UPDATE AD_Element_Trl SET Description='Die tatsächlich entladene Menge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581796 AND AD_Language IN ('de_DE','de_CH') AND COALESCE(Description,'')=''
;

UPDATE AD_Element_Trl SET Description='The quantity actually discharged.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581796 AND AD_Language IN ('en_US','fr_CH') AND COALESCE(Description,'')=''
;


-- The row-type rule, only where it holds: the two view columns.

UPDATE AD_Column SET Description='Die für die Entladung geplante Menge. Auf einer Zeile mit Lieferplanung ist es die Entscheidung der Lieferplanung selbst; eine Zeile ohne Lieferplanung hat keine Planung, die man fragen könnte, daher gilt dort die bestellte Menge - auf diesen Zeilen stimmt der Wert deshalb mit der Spalte "Bestellt/ Beauftragt" überein.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=593501 AND COALESCE(Description,'')=''
;

UPDATE AD_Column_Trl SET Description='Die für die Entladung geplante Menge. Auf einer Zeile mit Lieferplanung ist es die Entscheidung der Lieferplanung selbst; eine Zeile ohne Lieferplanung hat keine Planung, die man fragen könnte, daher gilt dort die bestellte Menge - auf diesen Zeilen stimmt der Wert deshalb mit der Spalte "Bestellt/ Beauftragt" überein.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=593501 AND AD_Language IN ('de_DE','de_CH') AND COALESCE(Description,'')=''
;

UPDATE AD_Column_Trl SET Description='The quantity planned for discharge. On a row with a delivery planning it is the planning''s own decision; a row with no delivery planning has no planning to ask, so what was ordered applies there - which is why on those rows this equals the Qty Ordered column.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=593501 AND AD_Language IN ('en_US','fr_CH') AND COALESCE(Description,'')=''
;

UPDATE AD_Column SET Description='Die tatsächlich entladene Menge. Auf einer Zeile mit Lieferplanung ist es die von der Lieferplanung erfasste tatsächliche Menge; auf einer Zeile ohne Lieferplanung die bereits eingegangene Menge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=593530 AND COALESCE(Description,'')=''
;

UPDATE AD_Column_Trl SET Description='Die tatsächlich entladene Menge. Auf einer Zeile mit Lieferplanung ist es die von der Lieferplanung erfasste tatsächliche Menge; auf einer Zeile ohne Lieferplanung die bereits eingegangene Menge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=593530 AND AD_Language IN ('de_DE','de_CH') AND COALESCE(Description,'')=''
;

UPDATE AD_Column_Trl SET Description='The quantity actually discharged. On a row with a delivery planning it is the actual recorded by the planning; on a row with no delivery planning it is the quantity already moved on the receipt schedule.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=593530 AND AD_Language IN ('en_US','fr_CH') AND COALESCE(Description,'')=''
;
