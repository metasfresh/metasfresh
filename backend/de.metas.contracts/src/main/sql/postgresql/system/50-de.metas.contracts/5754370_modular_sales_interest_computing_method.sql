-- Reference: Computing Methods
-- Value: SalesInterest
-- ValueName: SalesInterest
-- 2025-05-13T08:39:37.303Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543912,TO_TIMESTAMP('2025-05-13 10:39:36.982','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Zinsen Verkauf',TO_TIMESTAMP('2025-05-13 10:39:36.982','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesInterest','SalesInterest')
;

-- 2025-05-13T08:39:37.312Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543912 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Computing Methods -> SalesInterest_SalesInterest
-- 2025-05-13T08:40:01.538Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Interest Sales',Updated=TO_TIMESTAMP('2025-05-13 10:40:01.538','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543912
;

-- Reference Item: Computing Methods -> SalesInterest_SalesInterest
-- 2025-05-13T08:40:03.303Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 10:40:03.303','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543912
;

-- Reference Item: Computing Methods -> SalesInterest_SalesInterest
-- 2025-05-13T08:40:04.035Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 10:40:04.035','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543912
;

-- Run mode: WEBUI

-- 2025-05-13T08:45:48.878Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2025-05-13 10:45:48.874','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt Zinslogs für in der Basisbaustein Konfiguaration definierte Bausteine','Y','Y',540038,'SalesInterest','Zinsen Verkauf',TO_TIMESTAMP('2025-05-13 10:45:48.874','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesInterest')
;

-- 2025-05-13T08:47:14.725Z
UPDATE ModCntr_Type SET Description='Erstellt Wareneingangslogs bis zu einem in den modularen Einstellungen festgelegtem Datum',Updated=TO_TIMESTAMP('2025-05-13 10:47:14.725','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ModCntr_Type_ID=540037
;

