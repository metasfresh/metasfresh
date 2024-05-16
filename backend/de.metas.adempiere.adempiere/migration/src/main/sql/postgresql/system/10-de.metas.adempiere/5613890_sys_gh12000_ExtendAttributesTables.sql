-- 2021-11-18T08:22:33.141519Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541335,542946,TO_TIMESTAMP('2021-11-18 10:22:32','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Zeitung',TO_TIMESTAMP('2021-11-18 10:22:32','YYYY-MM-DD HH24:MI:SS'),100,'Zeitung','Zeitung')
;

-- 2021-11-18T08:22:33.341805800Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542946 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-11-18T08:22:40.473910600Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541335,542947,TO_TIMESTAMP('2021-11-18 10:22:40','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','TV',TO_TIMESTAMP('2021-11-18 10:22:40','YYYY-MM-DD HH24:MI:SS'),100,'TV','TV')
;

-- 2021-11-18T08:22:40.572193900Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542947 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-11-18T08:23:06.035095400Z
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2021-11-18 10:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542946
;

-- 2021-11-18T08:23:10.145603300Z
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2021-11-18 10:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542947
;

-- 2021-11-18T08:23:24.658493800Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541335,542948,TO_TIMESTAMP('2021-11-18 10:23:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Empfehlung',TO_TIMESTAMP('2021-11-18 10:23:24','YYYY-MM-DD HH24:MI:SS'),100,'Empfehlung','Empfehlung')
;

-- 2021-11-18T08:23:24.721053200Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542948 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-11-18T08:23:40.491385500Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541335,542949,TO_TIMESTAMP('2021-11-18 10:23:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Inserate',TO_TIMESTAMP('2021-11-18 10:23:40','YYYY-MM-DD HH24:MI:SS'),100,'Inserate','Inserate')
;

-- 2021-11-18T08:23:40.560008100Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542949 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-11-18T08:31:09.914807900Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_bpartner_attribute4','Attributes4','VARCHAR(40)',null,null)
;

-- 2021-11-18T08:39:34.532315100Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Politik', Value='Politik',Updated=TO_TIMESTAMP('2021-11-18 10:39:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542667
;

-- 2021-11-18T08:40:10.196849700Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='Politisches Engagement',Updated=TO_TIMESTAMP('2021-11-18 10:40:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542667
;

-- 2021-11-18T08:40:25.671374200Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Politisches Engagement',Updated=TO_TIMESTAMP('2021-11-18 10:40:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542667
;

-- 2021-11-18T08:41:00.873863400Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541336,542950,TO_TIMESTAMP('2021-11-18 10:41:00','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Dienstleistungen',TO_TIMESTAMP('2021-11-18 10:41:00','YYYY-MM-DD HH24:MI:SS'),100,'Dienstleistungen','Dienstleistungen')
;

-- 2021-11-18T08:41:00.936650700Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542950 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-11-18T08:41:06.552971400Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541336,542951,TO_TIMESTAMP('2021-11-18 10:41:06','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Andere',TO_TIMESTAMP('2021-11-18 10:41:06','YYYY-MM-DD HH24:MI:SS'),100,'Andere','Andere')
;

-- 2021-11-18T08:41:06.620134900Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542951 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-11-18T08:41:12.847511700Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541336,542952,TO_TIMESTAMP('2021-11-18 10:41:12','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Netzwerk',TO_TIMESTAMP('2021-11-18 10:41:12','YYYY-MM-DD HH24:MI:SS'),100,'Netzwerk','Netzwerk')
;

-- 2021-11-18T08:41:12.910766Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542952 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-11-18T08:41:21.684285800Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541336,542953,TO_TIMESTAMP('2021-11-18 10:41:21','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Seminare & Veranstaltungen',TO_TIMESTAMP('2021-11-18 10:41:21','YYYY-MM-DD HH24:MI:SS'),100,'Seminare & Veranstaltungen','Seminare & Veranstaltungen')
;

-- 2021-11-18T08:41:21.755914900Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542953 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;




-- 2021-11-18T09:37:24.992313900Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541332,542954,TO_TIMESTAMP('2021-11-18 11:37:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rechtsberater',TO_TIMESTAMP('2021-11-18 11:37:24','YYYY-MM-DD HH24:MI:SS'),100,'R','Rechtsberater')
;

-- 2021-11-18T09:37:25.171759300Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542954 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-11-18T09:37:31.292375200Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541332,542955,TO_TIMESTAMP('2021-11-18 11:37:30','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Schätzer',TO_TIMESTAMP('2021-11-18 11:37:30','YYYY-MM-DD HH24:MI:SS'),100,'S','Schätzer')
;

-- 2021-11-18T09:37:31.385256600Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542955 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-11-18T09:37:37.762214400Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541332,542956,TO_TIMESTAMP('2021-11-18 11:37:37','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Vorstand',TO_TIMESTAMP('2021-11-18 11:37:37','YYYY-MM-DD HH24:MI:SS'),100,'V','Vorstand')
;

-- 2021-11-18T09:37:37.826295800Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542956 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-11-18T09:37:46.061385700Z
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2021-11-18 11:37:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542956
;

-- 2021-11-18T09:37:50.020269Z
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2021-11-18 11:37:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542955
;

-- 2021-11-18T09:38:08.418807300Z
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='U',Updated=TO_TIMESTAMP('2021-11-18 11:38:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542954
;

-- 2021-11-18T09:38:15.654815600Z
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='U',Updated=TO_TIMESTAMP('2021-11-18 11:38:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542955
;

-- 2021-11-18T09:38:22.743178200Z
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='U',Updated=TO_TIMESTAMP('2021-11-18 11:38:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542956
;

