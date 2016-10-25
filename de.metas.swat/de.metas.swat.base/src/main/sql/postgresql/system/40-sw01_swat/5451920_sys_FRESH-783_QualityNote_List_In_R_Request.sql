-- 18.10.2016 18:15
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540692,TO_TIMESTAMP('2016-10-18 18:15:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','QualityNoticeList',TO_TIMESTAMP('2016-10-18 18:15:47','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 18.10.2016 18:15
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540692 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 18.10.2016 18:18
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,8468,8466,0,540692,558,TO_TIMESTAMP('2016-10-18 18:18:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N',TO_TIMESTAMP('2016-10-18 18:18:16','YYYY-MM-DD HH24:MI:SS'),100,'M_AttributeValue.M_Attribute_ID = 540008')
;

-- 18.10.2016 18:18
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540692,Updated=TO_TIMESTAMP('2016-10-18 18:18:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550574
;



-- 18.10.2016 18:21
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540693,TO_TIMESTAMP('2016-10-18 18:21:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','QualityNoticeAttributes',TO_TIMESTAMP('2016-10-18 18:21:02','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 18.10.2016 18:21
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540693 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 18.10.2016 18:32
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2016-10-18 18:32:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550574
;

-- 18.10.2016 18:34
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540694,TO_TIMESTAMP('2016-10-18 18:34:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','QualityNoteList',TO_TIMESTAMP('2016-10-18 18:34:30','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 18.10.2016 18:34
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540694 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 18.10.2016 18:35
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=8469,Updated=TO_TIMESTAMP('2016-10-18 18:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 18.10.2016 18:36
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540692,Updated=TO_TIMESTAMP('2016-10-18 18:36:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 18.10.2016 18:46
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='M_AttributeValue.M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice'')',Updated=TO_TIMESTAMP('2016-10-18 18:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 18.10.2016 18:49
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Display=8459, WhereClause='',Updated=TO_TIMESTAMP('2016-10-18 18:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 18.10.2016 18:50
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540348,'M_AttributeValue.M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice'')',TO_TIMESTAMP('2016-10-18 18:49:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','R_Request_QualityNote','S',TO_TIMESTAMP('2016-10-18 18:49:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 18:50
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540348,Updated=TO_TIMESTAMP('2016-10-18 18:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 18.10.2016 18:51
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Display=8468, AD_Key=8459,Updated=TO_TIMESTAMP('2016-10-18 18:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;










-- 18.10.2016 18:15
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540692,TO_TIMESTAMP('2016-10-18 18:15:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','QualityNoticeList',TO_TIMESTAMP('2016-10-18 18:15:47','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 18.10.2016 18:15
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540692 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 18.10.2016 18:18
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,8468,8466,0,540692,558,TO_TIMESTAMP('2016-10-18 18:18:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N',TO_TIMESTAMP('2016-10-18 18:18:16','YYYY-MM-DD HH24:MI:SS'),100,'M_AttributeValue.M_Attribute_ID = 540008')
;

-- 18.10.2016 18:18
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540692,Updated=TO_TIMESTAMP('2016-10-18 18:18:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550574
;


-- 18.10.2016 18:21
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540693,TO_TIMESTAMP('2016-10-18 18:21:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','QualityNoticeAttributes',TO_TIMESTAMP('2016-10-18 18:21:02','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 18.10.2016 18:21
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540693 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 18.10.2016 18:32
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2016-10-18 18:32:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550574
;

-- 18.10.2016 18:34
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540694,TO_TIMESTAMP('2016-10-18 18:34:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','QualityNoteList',TO_TIMESTAMP('2016-10-18 18:34:30','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 18.10.2016 18:34
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540694 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 18.10.2016 18:35
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=8469,Updated=TO_TIMESTAMP('2016-10-18 18:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 18.10.2016 18:36
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540692,Updated=TO_TIMESTAMP('2016-10-18 18:36:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 18.10.2016 18:46
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='M_AttributeValue.M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice'')',Updated=TO_TIMESTAMP('2016-10-18 18:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 18.10.2016 18:49
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Display=8459, WhereClause='',Updated=TO_TIMESTAMP('2016-10-18 18:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 18.10.2016 18:50
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540348,'M_AttributeValue.M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice'')',TO_TIMESTAMP('2016-10-18 18:49:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','R_Request_QualityNote','S',TO_TIMESTAMP('2016-10-18 18:49:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 18:50
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540348,Updated=TO_TIMESTAMP('2016-10-18 18:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 18.10.2016 18:51
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Display=8468, AD_Key=8459,Updated=TO_TIMESTAMP('2016-10-18 18:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;






-- 21.10.2016 15:52
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='M_AttributeValue.M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice'')
and exists (select 1 from M_AttributeValue av where av.M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice'') and av.Name = R_Request.QualityNote )',Updated=TO_TIMESTAMP('2016-10-21 15:52:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540348
;

-- 21.10.2016 15:59
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='M_AttributeValue.M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice'')',Updated=TO_TIMESTAMP('2016-10-21 15:59:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540348
;

-- 21.10.2016 15:59
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2016-10-21 15:59:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;


-- 21.10.2016 16:33
-- URL zum Konzept
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2016-10-21 16:33:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 21.10.2016 16:37
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-21 16:37:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;






-- 21.10.2016 16:57
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='exists (select 1 from M_AttributeValue av where M_Attribute_ID= (select M_Attribute_ID from M_Attribute where value = ''QualityNotice'') and av.value = R_Request.QualityNote )',Updated=TO_TIMESTAMP('2016-10-21 16:57:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540348
;

-- 21.10.2016 16:59
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='exists (select 1 from M_AttributeValue av where M_Attribute_ID= (select M_Attribute_ID from M_Attribute where value = ''QualityNotice'') and av.value = M_AttributeValue.value )',Updated=TO_TIMESTAMP('2016-10-21 16:59:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540348
;

-- 21.10.2016 17:00
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='M_AttributeValue.M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''''QualityNotice'''')',Updated=TO_TIMESTAMP('2016-10-21 17:00:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540348
;

-- 21.10.2016 17:01
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='M_AttributeValue.M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice'')',Updated=TO_TIMESTAMP('2016-10-21 17:01:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540348
;

-- 21.10.2016 17:27
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=8468,Updated=TO_TIMESTAMP('2016-10-21 17:27:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 21.10.2016 17:29
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='M_AttributeValue.Name in ( select Name from M_AttributeValue where M_Attribute_ID =  = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice''))',Updated=TO_TIMESTAMP('2016-10-21 17:29:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540348
;

-- 21.10.2016 17:30
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='M_AttributeValue.Name in ( select Name from M_AttributeValue where M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice''))',Updated=TO_TIMESTAMP('2016-10-21 17:30:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540348
;

-- 21.10.2016 17:33
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540693,Updated=TO_TIMESTAMP('2016-10-21 17:33:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 21.10.2016 17:36
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='R_Request.QualityNote in ( select Name from M_AttributeValue where M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice''))',Updated=TO_TIMESTAMP('2016-10-21 17:36:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540348
;

-- 21.10.2016 17:37
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2016-10-21 17:37:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 21.10.2016 17:44
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540694,Updated=TO_TIMESTAMP('2016-10-21 17:44:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 21.10.2016 17:57
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=540692,Updated=TO_TIMESTAMP('2016-10-21 17:57:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 21.10.2016 17:57
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=8459,Updated=TO_TIMESTAMP('2016-10-21 17:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;





-- 21.10.2016 18:01
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='M_AttributeValue.Value in ( select Name from M_AttributeValue where M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice''))',Updated=TO_TIMESTAMP('2016-10-21 18:01:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540348
;

-- 21.10.2016 18:01
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='M_AttributeValue.Value in ( select value from M_AttributeValue where M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = ''QualityNotice''))',Updated=TO_TIMESTAMP('2016-10-21 18:01:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540348
;

-- 24.10.2016 11:24
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=8469,Updated=TO_TIMESTAMP('2016-10-24 11:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;



-- 24.10.2016 12:03
-- URL zum Konzept
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2016-10-24 12:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;







-- 24.10.2016 12:41
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=8459,Updated=TO_TIMESTAMP('2016-10-24 12:41:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 24.10.2016 15:58
-- URL zum Konzept
UPDATE AD_Ref_Table SET IsValueDisplayed='Y',Updated=TO_TIMESTAMP('2016-10-24 15:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 24.10.2016 16:10
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2016-10-24 16:10:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 24.10.2016 16:12
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2016-10-24 16:12:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 24.10.2016 16:32
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=8468,Updated=TO_TIMESTAMP('2016-10-24 16:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 24.10.2016 16:33
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=8459, IsValueDisplayed='N',Updated=TO_TIMESTAMP('2016-10-24 16:33:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 24.10.2016 16:33
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=8468,Updated=TO_TIMESTAMP('2016-10-24 16:33:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 24.10.2016 16:38
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=8459,Updated=TO_TIMESTAMP('2016-10-24 16:38:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 25.10.2016 18:54
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-10-25 18:54:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;


