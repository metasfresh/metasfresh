-- Reference: Type
-- Value: PC
-- ValueName: PrintClient
-- 2023-07-17T08:25:27.912Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541255,543523,TO_TIMESTAMP('2023-07-17 10:25:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Printing Client',TO_TIMESTAMP('2023-07-17 10:25:27','YYYY-MM-DD HH24:MI:SS'),100,'PC','PrintingClient')
;

-- 2023-07-17T08:25:27.915Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543523 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Type -> PC_PrintingClient
-- 2023-07-19T13:45:44.420Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-19 15:45:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543523
;

-- Reference Item: Type -> PC_PrintingClient
-- 2023-07-19T13:45:54.577Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Druck Client',Updated=TO_TIMESTAMP('2023-07-19 15:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543523
;

-- Reference Item: Type -> PC_PrintingClient
-- 2023-07-19T13:46:05.498Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Druck Client',Updated=TO_TIMESTAMP('2023-07-19 15:46:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543523
;

