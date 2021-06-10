--Create Reference List
-- 2021-06-10T08:06:33.305Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541330,TO_TIMESTAMP('2021-06-10 11:06:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','GroceryCategory',TO_TIMESTAMP('2021-06-10 11:06:32','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-06-10T08:06:33.486Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541330 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-06-10T08:06:44.714Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Grocery Category',Updated=TO_TIMESTAMP('2021-06-10 11:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Reference_ID=541330
;

-- 2021-06-10T08:06:55.690Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Grocery Category',Updated=TO_TIMESTAMP('2021-06-10 11:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541330
;

-- 2021-06-10T08:07:12.165Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Lebensmittel Category',Updated=TO_TIMESTAMP('2021-06-10 11:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541330
;

-- 2021-06-10T08:08:32.935Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541330,542641,TO_TIMESTAMP('2021-06-10 11:08:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fleisch (inkl. Fisch)',TO_TIMESTAMP('2021-06-10 11:08:32','YYYY-MM-DD HH24:MI:SS'),100,'0','Meat (incl. Fish)')
;

-- 2021-06-10T08:08:32.978Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542641 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-10T08:08:56.623Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541330,542642,TO_TIMESTAMP('2021-06-10 11:08:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Obst & Gemüse',TO_TIMESTAMP('2021-06-10 11:08:56','YYYY-MM-DD HH24:MI:SS'),100,'1','Vegetables & Fruites')
;

-- 2021-06-10T08:08:56.668Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542642 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-10T08:09:23.578Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541330,542643,TO_TIMESTAMP('2021-06-10 11:09:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Soßen & Dips',TO_TIMESTAMP('2021-06-10 11:09:23','YYYY-MM-DD HH24:MI:SS'),100,'2','Sauces & Dips')
;

-- 2021-06-10T08:09:23.617Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542643 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-10T08:09:44.131Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541330,542644,TO_TIMESTAMP('2021-06-10 11:09:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Suppen',TO_TIMESTAMP('2021-06-10 11:09:43','YYYY-MM-DD HH24:MI:SS'),100,'3','Soups')
;

-- 2021-06-10T08:09:44.173Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542644 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-10T08:10:10.810Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541330,542645,TO_TIMESTAMP('2021-06-10 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Brot & Kuchen',TO_TIMESTAMP('2021-06-10 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,'4','Bread & Cake')
;

-- 2021-06-10T08:10:10.865Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542645 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-10T08:10:34.716Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541330,542646,TO_TIMESTAMP('2021-06-10 11:10:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Molkereiprodukte (inkl. Käse)',TO_TIMESTAMP('2021-06-10 11:10:34','YYYY-MM-DD HH24:MI:SS'),100,'5','Dairy (incl. Cheese)')
;

-- 2021-06-10T08:10:34.771Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542646 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-10T08:11:32.477Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541330,542647,TO_TIMESTAMP('2021-06-10 11:11:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nüsse & Samen',TO_TIMESTAMP('2021-06-10 11:11:32','YYYY-MM-DD HH24:MI:SS'),100,'6','Nuts & Seeds')
;

-- 2021-06-10T08:11:32.519Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542647 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-10T08:11:50.553Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541330,542648,TO_TIMESTAMP('2021-06-10 11:11:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Getreideprodukte',TO_TIMESTAMP('2021-06-10 11:11:50','YYYY-MM-DD HH24:MI:SS'),100,'7','Cereals')
;

-- 2021-06-10T08:11:50.601Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542648 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-10T08:12:13.219Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541330,542649,TO_TIMESTAMP('2021-06-10 11:12:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Desserts',TO_TIMESTAMP('2021-06-10 11:12:12','YYYY-MM-DD HH24:MI:SS'),100,'8','Desserts')
;

-- 2021-06-10T08:12:13.261Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542649 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-10T08:12:40.125Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541330,542650,TO_TIMESTAMP('2021-06-10 11:12:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Convenience (Mandus, Fritten usw.)',TO_TIMESTAMP('2021-06-10 11:12:39','YYYY-MM-DD HH24:MI:SS'),100,'9','Convenience (Mandus, Fries, etc.)')
;

-- 2021-06-10T08:12:40.164Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542650 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-10T08:13:30.807Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Convenience (Mandus, Fries, etc.)',Updated=TO_TIMESTAMP('2021-06-10 11:13:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542650
;

-- 2021-06-10T08:13:42.317Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Convenience (Mandus, Fries, etc.)',Updated=TO_TIMESTAMP('2021-06-10 11:13:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542650
;

-- 2021-06-10T08:13:54.576Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:13:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542650
;

-- 2021-06-10T08:14:16.877Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:14:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542649
;

-- 2021-06-10T08:14:22.359Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:14:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542649
;

-- 2021-06-10T08:14:29.340Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:14:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542649
;

-- 2021-06-10T08:14:54.775Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Cereals',Updated=TO_TIMESTAMP('2021-06-10 11:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542648
;

-- 2021-06-10T08:15:02.174Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Cereals',Updated=TO_TIMESTAMP('2021-06-10 11:15:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542648
;

-- 2021-06-10T08:15:09.141Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:15:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542648
;

-- 2021-06-10T08:15:28.664Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:15:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542647
;

-- 2021-06-10T08:15:36.872Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Name='Nuts & Seeds',Updated=TO_TIMESTAMP('2021-06-10 11:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542647
;

-- 2021-06-10T08:15:42.522Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Nuts & Seeds',Updated=TO_TIMESTAMP('2021-06-10 11:15:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542647
;

-- 2021-06-10T08:15:45.911Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542647
;

-- 2021-06-10T08:16:06.366Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Dairy (incl. Cheese) (inkl. Käse)',Updated=TO_TIMESTAMP('2021-06-10 11:16:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542646
;

-- 2021-06-10T08:16:10.592Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Dairy (incl. Cheese) (inkl. Käse)',Updated=TO_TIMESTAMP('2021-06-10 11:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542646
;

-- 2021-06-10T08:16:12.915Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542646
;

-- 2021-06-10T08:16:26.729Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542645
;

-- 2021-06-10T08:16:31.234Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Bread & Cake',Updated=TO_TIMESTAMP('2021-06-10 11:16:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542645
;

-- 2021-06-10T08:16:36.309Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Bread & Cake',Updated=TO_TIMESTAMP('2021-06-10 11:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542645
;

-- 2021-06-10T08:16:51.867Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Soups',Updated=TO_TIMESTAMP('2021-06-10 11:16:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542644
;

-- 2021-06-10T08:16:56.715Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Soups',Updated=TO_TIMESTAMP('2021-06-10 11:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542644
;

-- 2021-06-10T08:16:58.783Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:16:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542644
;

-- 2021-06-10T08:17:13.962Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:17:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542643
;

-- 2021-06-10T08:17:19.020Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Sauces & Dips',Updated=TO_TIMESTAMP('2021-06-10 11:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542643
;

-- 2021-06-10T08:17:26.084Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Sauces & Dips',Updated=TO_TIMESTAMP('2021-06-10 11:17:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542643
;

-- 2021-06-10T08:17:46.774Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Vegetables & Fruites',Updated=TO_TIMESTAMP('2021-06-10 11:17:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542642
;

-- 2021-06-10T08:17:51.533Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Vegetables & Fruites',Updated=TO_TIMESTAMP('2021-06-10 11:17:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542642
;

-- 2021-06-10T08:17:53.751Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:17:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542642
;

-- 2021-06-10T08:18:19.681Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Meat (incl. Fish)',Updated=TO_TIMESTAMP('2021-06-10 11:18:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542641
;

-- 2021-06-10T08:18:24.546Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Meat (incl. Fish)',Updated=TO_TIMESTAMP('2021-06-10 11:18:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542641
;

-- 2021-06-10T08:18:27.436Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542641
;



--Create Column Element
-- 2021-06-10T08:20:00.394Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579322,0,'GroceryCategory',TO_TIMESTAMP('2021-06-10 11:19:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Grocery Category','Grocery Category',TO_TIMESTAMP('2021-06-10 11:19:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-10T08:20:00.440Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579322 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-10T08:20:19.211Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:20:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579322 AND AD_Language='en_GB'
;

-- 2021-06-10T08:20:19.317Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579322,'en_GB')
;

-- 2021-06-10T08:20:30.248Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 11:20:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579322 AND AD_Language='en_US'
;

-- 2021-06-10T08:20:30.288Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579322,'en_US')
;

-- 2021-06-10T08:20:36.969Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lebensmittel Category', PrintName='Lebensmittel Category',Updated=TO_TIMESTAMP('2021-06-10 11:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579322 AND AD_Language='de_DE'
;

-- 2021-06-10T08:20:37.013Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579322,'de_DE')
;

-- 2021-06-10T08:20:37.192Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579322,'de_DE')
;

-- 2021-06-10T08:20:37.242Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='GroceryCategory', Name='Lebensmittel Category', Description=NULL, Help=NULL WHERE AD_Element_ID=579322
;

-- 2021-06-10T08:20:37.291Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GroceryCategory', Name='Lebensmittel Category', Description=NULL, Help=NULL, AD_Element_ID=579322 WHERE UPPER(ColumnName)='GROCERYCATEGORY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-10T08:20:37.337Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GroceryCategory', Name='Lebensmittel Category', Description=NULL, Help=NULL WHERE AD_Element_ID=579322 AND IsCentrallyMaintained='Y'
;

-- 2021-06-10T08:20:37.378Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Lebensmittel Category', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579322) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579322)
;

-- 2021-06-10T08:20:37.489Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Lebensmittel Category', Name='Lebensmittel Category' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579322)
;

-- 2021-06-10T08:20:37.528Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Lebensmittel Category', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579322
;

-- 2021-06-10T08:20:37.572Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Lebensmittel Category', Description=NULL, Help=NULL WHERE AD_Element_ID = 579322
;

-- 2021-06-10T08:20:37.613Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Lebensmittel Category', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579322
;

--Create Column
-- 2021-06-10T08:22:59.273Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574311,579322,0,17,541330,208,'GroceryCategory',TO_TIMESTAMP('2021-06-10 11:22:58','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lebensmittel Category',0,0,TO_TIMESTAMP('2021-06-10 11:22:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-10T08:22:59.327Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574311 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-10T08:22:59.426Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579322)
;

