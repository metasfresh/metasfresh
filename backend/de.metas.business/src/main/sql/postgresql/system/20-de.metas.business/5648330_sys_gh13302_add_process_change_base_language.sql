-- 2022-07-27T16:19:05.071Z
-- URL zum Konzept
UPDATE AD_Menu SET EntityType='D',Updated=TO_TIMESTAMP('2022-07-27 18:19:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541973
;

-- 2022-07-27T16:19:17.860Z
-- URL zum Konzept
UPDATE AD_Process SET AccessLevel='4',Updated=TO_TIMESTAMP('2022-07-27 18:19:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585075
;

-- 2022-07-27T16:22:03.394Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541613,TO_TIMESTAMP('2022-07-27 18:22:03','YYYY-MM-DD HH24:MI:SS'),100,'Ad_Language(Active System Languages)','D','Y','N','Ad_Language(Active System Languages)',TO_TIMESTAMP('2022-07-27 18:22:03','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-07-27T16:22:03.396Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541613 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-07-27T16:25:15.460Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,9622,0,541613,111,TO_TIMESTAMP('2022-07-27 18:25:15','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N',TO_TIMESTAMP('2022-07-27 18:25:15','YYYY-MM-DD HH24:MI:SS'),100,'isactive = ''Y'' AND isbaselanguage = ''N'' AND issystemlanguage = ''Y'' ')
;

-- 2022-07-27T16:26:02.056Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=541613,Updated=TO_TIMESTAMP('2022-07-27 18:26:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542275
;
