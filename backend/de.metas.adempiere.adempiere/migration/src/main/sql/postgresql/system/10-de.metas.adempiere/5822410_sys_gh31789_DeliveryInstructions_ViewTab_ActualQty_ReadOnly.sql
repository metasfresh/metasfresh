-- AD_Tab 546737 ("Lieferanweisungen fuer die Lieferplanung", AD_Window 541632 "Lieferplanung") is
-- backed by the view M_Delivery_Planning_Delivery_Instructions_V (AD_Table 542280) -- a read-only,
-- non-writable view. Its ActualLoadQty/ActualDischargeQuantity fields (710219/710220) were left
-- IsReadOnly='N', while 5822250 already flipped the identical mirrored fields on tab 546736
-- (Versandpaket, AD_Window 541657) to read-only in the same migration set. Without this, a
-- dispatcher can click into what looks like an editable actual-quantity cell backed by data that
-- cannot actually be persisted.
UPDATE AD_Field SET IsReadOnly='Y',
  Updated=TO_TIMESTAMP('2026-09-03 11:05:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=710219
;
UPDATE AD_Field SET IsReadOnly='Y',
  Updated=TO_TIMESTAMP('2026-09-03 11:05:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=710220
;
