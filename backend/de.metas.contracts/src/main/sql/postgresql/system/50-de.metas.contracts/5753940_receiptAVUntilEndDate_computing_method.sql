
-- Reference: Computing Methods
-- Value: AVReceiptUntilDate
-- ValueName: AVReceiptUntilDate
-- 2025-05-08T09:59:50.284Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543910,TO_TIMESTAMP('2025-05-08 11:59:50.094','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Frühablieferungsabzug',TO_TIMESTAMP('2025-05-08 11:59:50.094','YYYY-MM-DD HH24:MI:SS.US'),100,'AVReceiptUntilDate','AVReceiptUntilDate')
;

-- 2025-05-08T09:59:50.288Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543910 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Computing Methods -> AVReceiptUntilDate_AVReceiptUntilDate
-- 2025-05-08T10:00:23.818Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Receipt AV Until Date',Updated=TO_TIMESTAMP('2025-05-08 12:00:23.818','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543910
;

-- Reference Item: Computing Methods -> AVReceiptUntilDate_AVReceiptUntilDate
-- 2025-05-08T10:00:25.578Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-08 12:00:25.578','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543910
;

-- Reference Item: Computing Methods -> AVReceiptUntilDate_AVReceiptUntilDate
-- 2025-05-08T10:00:27.864Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-08 12:00:27.864','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543910
;

-- Run mode: WEBUI

-- 2025-05-08T12:02:56.939Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2025-05-08 14:02:56.936','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt Wareneingangslogs bis zu einem in den modularen Enstelungen festgelegtem Datum','Y','N',540037,'AVReceiptUntilDate','Frühablieferungsabzug',TO_TIMESTAMP('2025-05-08 14:02:56.936','YYYY-MM-DD HH24:MI:SS.US'),100,'AVReceiptUntilDate')
;

-- 2025-05-08T12:20:38.178Z
UPDATE ModCntr_Type SET Description='Erstellt Lieferlogs, die für Verkaufsmenge und Verkaufspreis in der Schlusszahlung genutzt werden. Als Preis wird in der Schlusszahlung der Durchschnittspreis genutzt',Updated=TO_TIMESTAMP('2025-05-08 14:20:38.178','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ModCntr_Type_ID=540032
;

