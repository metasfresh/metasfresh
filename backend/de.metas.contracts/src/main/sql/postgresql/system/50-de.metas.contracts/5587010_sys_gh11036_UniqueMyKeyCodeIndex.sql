
-- 2021-04-21T14:33:48.391Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540588,0,291,TO_TIMESTAMP('2021-04-21 16:33:48','YYYY-MM-DD HH24:MI:SS'),100,'D','MyKeyCode should be unique.','Y','Y','C_BPartner_MyKeyCode_Unique','N',TO_TIMESTAMP('2021-04-21 16:33:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-21T14:33:48.393Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540588 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-04-21T14:34:46.614Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2895,541090,540588,0,TO_TIMESTAMP('2021-04-21 16:34:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-04-21 16:34:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-21T14:35:30.674Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2929,541091,540588,0,TO_TIMESTAMP('2021-04-21 16:35:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2021-04-21 16:35:30','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2021-04-22T06:31:05.365Z
-- URL zum Konzept
UPDATE AD_Index_Table SET ErrorMsg='MyKey-Code needs to be unique among all sales reps of the same org.',Updated=TO_TIMESTAMP('2021-04-22 08:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540588
;

-- 2021-04-22T06:32:58.089Z
-- URL zum Konzept
UPDATE AD_Index_Table_Trl SET ErrorMsg='Der MyKey-Code muss über alle Vertriebpartner einer Organsiation eindeutig sein.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-22 08:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540588 AND AD_Language='de_CH'
;

-- 2021-04-22T06:33:06.735Z
-- URL zum Konzept
UPDATE AD_Index_Table_Trl SET ErrorMsg='Der MyKey-Code muss über alle Vertriebpartner einer Organsiation eindeutig sein.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-22 08:33:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540588 AND AD_Language='nl_NL'
;

-- 2021-04-22T06:33:16.449Z
-- URL zum Konzept
UPDATE AD_Index_Table_Trl SET ErrorMsg='Der MyKey-Code muss über alle Vertriebpartner einer Organsiation eindeutig sein.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-22 08:33:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540588 AND AD_Language='de_DE'
;

-- 2021-04-22T06:33:34.497Z
-- URL zum Konzept
UPDATE AD_Index_Table_Trl SET ErrorMsg='MyKey-Code needs to be unique among all sales reps of the same org.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-22 08:33:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540588 AND AD_Language='en_US'
;

-- 2021-04-22T07:07:48.040Z
-- URL zum Konzept
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=541092
;

-- 2021-04-22T07:08:13.561Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569255,541093,540588,0,TO_TIMESTAMP('2021-04-22 09:08:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2021-04-22 09:08:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-22T07:15:32.555Z
-- URL zum Konzept
DROP INDEX IF EXISTS c_bpartner_mykeycode_unique
;

-- 2021-04-22T07:15:32.556Z
-- URL zum Konzept
CREATE UNIQUE INDEX C_BPartner_MyKeyCode_Unique ON C_BPartner (AD_Org_ID,IsSalesRep,SalesPartnerCode)
;

-- 2021-04-29T12:41:47.992Z
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y''',Updated=TO_TIMESTAMP('2021-04-29 14:41:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540588
;

-- 2021-04-29T12:41:53.458Z
-- URL zum Konzept
DROP INDEX IF EXISTS c_bpartner_mykeycode_unique
;

-- 2021-04-29T12:41:53.464Z
-- URL zum Konzept
CREATE UNIQUE INDEX C_BPartner_MyKeyCode_Unique ON C_BPartner (AD_Org_ID,IsSalesRep,SalesPartnerCode) WHERE IsActive='Y'
;
