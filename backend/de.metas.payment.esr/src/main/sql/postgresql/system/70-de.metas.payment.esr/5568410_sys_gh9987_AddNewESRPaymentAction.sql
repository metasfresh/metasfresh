-- 2020-09-22T10:24:34.943Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540386,542188,TO_TIMESTAMP('2020-09-22 12:24:34','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Skonto',TO_TIMESTAMP('2020-09-22 12:24:34','YYYY-MM-DD HH24:MI:SS'),100,'T','Discount')
;

-- 2020-09-22T10:24:34.961Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542188 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2020-09-22T10:25:22.176Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Skonto', Updated=TO_TIMESTAMP('2020-09-22 12:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542188
;

-- 2020-09-22T10:25:28.184Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Discount',Updated=TO_TIMESTAMP('2020-09-22 12:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542188
;

