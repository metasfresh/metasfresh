-- AD_Column 593468 (M_ShipperTransportation.DeliveredState, added by 5822160) was left with
-- PersonalDataCategory NULL, unlike its sibling 593466 (QtyTotalOpenPlanned, same migration set)
-- which set 'NP'. DeliveredState is a derived document status -- no personal data -- so it gets
-- the same 'NP' classification.
UPDATE AD_Column SET PersonalDataCategory='NP',
  Updated=TO_TIMESTAMP('2026-09-03 11:04:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=593468
;
