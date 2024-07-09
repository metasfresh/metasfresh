-- read only for now, because sales modular contracts aren't supported atm

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Verkaufstransaktion
-- Column: ModCntr_Settings.IsSOTrx
-- 2024-07-08T07:14:31.179Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-08 09:14:31.179','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=717243
;

