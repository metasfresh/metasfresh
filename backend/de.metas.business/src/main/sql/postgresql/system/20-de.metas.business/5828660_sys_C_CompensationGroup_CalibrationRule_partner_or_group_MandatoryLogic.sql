-- C_CompensationGroup_CalibrationRule: a rule needs a customer (C_BPartner_ID) or a business-partner group (C_BP_Group_ID).
-- Conditional mandatory logic on both columns: each one is mandatory while the other one is empty.
-- So a new rule in window "Kalibrierungsregeln" shows both fields as mandatory and is not saved until one of them is set,
-- instead of saving at once and showing the server-side "customer or group required" error.
-- The model interceptor and the DB check constraint stay as the backstop for REST / import / plain SQL.

UPDATE AD_Column SET MandatoryLogic='@C_BP_Group_ID/0@=0', Updated=TO_TIMESTAMP('2026-10-08 19:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593723
;

UPDATE AD_Column SET MandatoryLogic='@C_BPartner_ID/0@=0', Updated=TO_TIMESTAMP('2026-10-08 19:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593724
;
