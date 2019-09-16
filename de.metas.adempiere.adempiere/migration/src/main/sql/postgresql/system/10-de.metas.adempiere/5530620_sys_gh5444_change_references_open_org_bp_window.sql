-- 2019-09-13T18:40:47.243Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541039,TO_TIMESTAMP('2019-09-13 20:40:47','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner selection (no Summary)','U','Y','N','C_BPartner (Trx) Zoom to Org BP',TO_TIMESTAMP('2019-09-13 20:40:47','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-09-13T18:40:47.245Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541039 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-09-13T18:41:22.561Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2902,2893,0,541039,291,540676,TO_TIMESTAMP('2019-09-13 20:41:22','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',TO_TIMESTAMP('2019-09-13 20:41:22','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner.IsSummary=''N'' AND C_BPartner.IsActive=''Y''')
;

-- 2019-09-13T18:41:33.188Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=541039,Updated=TO_TIMESTAMP('2019-09-13 20:41:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568424
;

-- 2019-09-13T18:43:49.592Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541040,TO_TIMESTAMP('2019-09-13 20:43:49','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','C_BPartner Location Zoom to Org',TO_TIMESTAMP('2019-09-13 20:43:49','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-09-13T18:43:49.594Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541040 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-09-13T18:44:41.070Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2960,3434,0,541040,293,540676,TO_TIMESTAMP('2019-09-13 20:44:41','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',TO_TIMESTAMP('2019-09-13 20:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-13T18:45:02.584Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541040,Updated=TO_TIMESTAMP('2019-09-13 20:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546592
;

