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
-- Shape follows IsReadyForReceipt (5823480): element + element_trl, German on
-- de_DE/de_CH, English on en_US, and fr_CH mirroring en_US per this change set's stated convention.
-- IsTranslated is left alone -- adding a description does not translate a name.
--
-- The UPDATEs are UNCONDITIONAL. They previously carried "AND COALESCE(Description,'')=''" to avoid
-- overwriting a text somebody had since written; that guard was wrong. A migration's job is to bring every
-- instance to the SAME known state, and a data-dependent guard silently leaves some instances elsewhere with
-- no error and no signal (metasfresh-db: a silent guard hides the condition instead of surfacing it; and
-- migrations must produce deterministic, reproducible results across environments). These are system
-- elements at AD_Client_ID=0 that shipped with no description at all; customer-specific wording belongs in a
-- customer migration on its own element, not typed over a core one.

UPDATE AD_Element SET Description='Die für die Entladung geplante Menge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581795
;

UPDATE AD_Element_Trl SET Description='Die für die Entladung geplante Menge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581795 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl SET Description='The quantity planned for discharge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581795 AND AD_Language IN ('en_US','fr_CH')
;

UPDATE AD_Element SET Description='Die tatsächlich entladene Menge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581796
;

UPDATE AD_Element_Trl SET Description='Die tatsächlich entladene Menge.', Updated=TO_TIMESTAMP('2026-09-10 09:30:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581796 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl SET Description='The quantity actually discharged.', Updated=TO_TIMESTAMP('2026-09-10 09:30:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581796 AND AD_Language IN ('en_US','fr_CH')
;


-- The row-type rule is NOT written onto AD_Column here. It is per-usage text, and the mechanism for that is
-- a dedicated AD_Element reached via AD_Field.AD_Name_ID - see 5824150, which adds it. Writing
-- Name/Description/Help straight onto a child record leaves it with no element behind it, so
-- update_TRL_Tables_On_AD_Element_TRL_Update has nothing to push from and the text never propagates.

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581795)
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581796)
;
