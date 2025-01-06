
-- 2024-05-08T15:37:17.543Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value)
 VALUES (1000000,1000000,TO_TIMESTAMP('2024-05-08 18:37:17.347','YYYY-MM-DD HH24:MI:SS.US'),100,'N',540008,'InformativeLogs','Informative Logs',
 TO_TIMESTAMP('2024-05-08 18:37:17.347','YYYY-MM-DD HH24:MI:SS.US'),100,'1000009')
;

-- 2024-05-08T15:37:31.827Z
UPDATE ModCntr_Type SET Value='InformativeLogs',Updated=TO_TIMESTAMP('2024-05-08 18:37:31.826','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ModCntr_Type_ID=540008
;

-- 2024-05-08T15:45:41.397Z
UPDATE ModCntr_Type SET Description='Dieser Vertragsbausteintyp erzeugt informative Logs über abgeschlossene Bestellzeilen und die Erstellung der Vertragsbaustein-Verträge. Für sie wird kein Rechnungskandidat erstellt.',Updated=TO_TIMESTAMP('2024-05-08 18:45:41.396','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ModCntr_Type_ID=540008
;

-- 2024-05-08T15:45:58.270Z
UPDATE ModCntr_Type SET Description='Dieser Vertragsbausteintyp erzeugt informative Logs über abgeschlossene Bestellzeilen und die erstellten Vertragsbaustein-Verträge. Für sie wird kein Rechnungskandidat erstellt.',Updated=TO_TIMESTAMP('2024-05-08 18:45:58.269','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ModCntr_Type_ID=540008
;


