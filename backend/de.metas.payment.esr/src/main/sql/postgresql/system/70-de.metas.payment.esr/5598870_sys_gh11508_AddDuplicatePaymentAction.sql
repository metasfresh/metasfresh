-- 2021-07-16T12:38:32.275Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540386,542738,TO_TIMESTAMP('2021-07-16 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'The payment already exists. No action is required.','de.metas.payment.esr','Y','Payment duplicate ',TO_TIMESTAMP('2021-07-16 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'P','Duplicate_Payment')
;

-- 2021-07-16T12:38:32.328Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542738 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-07-16T12:40:03.534Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Zahlung ist bereits vorhanden. Keine weiteren Aktionen erforderlich.', IsTranslated='Y', Name='Zahlungsdublette',Updated=TO_TIMESTAMP('2021-07-16 14:40:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542738
;

-- 2021-07-16T12:40:03.534Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Zahlung ist bereits vorhanden. Keine weiteren Aktionen erforderlich.', IsTranslated='Y', Name='Zahlungsdublette',Updated=TO_TIMESTAMP('2021-07-16 14:40:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542738
;


-- 2021-07-16T12:40:06.831Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-16 14:40:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542738
;

