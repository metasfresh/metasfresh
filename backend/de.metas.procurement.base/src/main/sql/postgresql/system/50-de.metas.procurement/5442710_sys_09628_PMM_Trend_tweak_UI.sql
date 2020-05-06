
-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Window SET Name='Trend Verfügbarkeit',Updated=TO_TIMESTAMP('2016-03-16 11:18:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540289
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Window_Trl SET IsTranslated='N' WHERE AD_Window_ID=540289
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Trend Verfügbarkeit',Updated=TO_TIMESTAMP('2016-03-16 11:18:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540695
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540695
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Tab SET Name='Woche',Updated=TO_TIMESTAMP('2016-03-16 11:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540732
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540732
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Element SET Name='Wochendatum', PrintName='Wochendatum',Updated=TO_TIMESTAMP('2016-03-16 11:18:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543041
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543041
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='WeekDate', Name='Wochendatum', Description=NULL, Help=NULL WHERE AD_Element_ID=543041
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='WeekDate', Name='Wochendatum', Description=NULL, Help=NULL, AD_Element_ID=543041 WHERE UPPER(ColumnName)='WEEKDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='WeekDate', Name='Wochendatum', Description=NULL, Help=NULL WHERE AD_Element_ID=543041 AND IsCentrallyMaintained='Y'
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_Field SET Name='Wochendatum', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543041) AND IsCentrallyMaintained='Y'
;

-- 16.03.2016 11:18
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Wochendatum', Name='Wochendatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543041)
;

-- 16.03.2016 11:19
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=540272,Updated=TO_TIMESTAMP('2016-03-16 11:19:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554251
;

-- 16.03.2016 11:21
-- URL zum Konzept
UPDATE AD_Menu SET Name='Beschaffung',Updated=TO_TIMESTAMP('2016-03-16 11:21:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540688
;

-- 16.03.2016 11:21
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540688
;

---
-- 16.03.2016 11:22
-- URL zum Konzept
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) 
SELECT 1000000,540697,0,TO_TIMESTAMP('2016-03-16 11:22:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Admin','Y','N','N','Y','Admin',TO_TIMESTAMP('2016-03-16 11:22:17','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from AD_MEnu where AD_Menu_ID=1000000);
;

-- 16.03.2016 11:22
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540697 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 16.03.2016 11:22
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540697, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=1000000 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540697)
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53135, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53149 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53135, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53139 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53135, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53136 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53135, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540697 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540697 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540027 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540080 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540059 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540069 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540064 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540071 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540163 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53294 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540260 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=106, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=107, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540641 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=108, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540694 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540689 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540690 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540695 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540697 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540696 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540694 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540689 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540690 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540695 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540696 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540688, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540697 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540697, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540690 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540697, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540696 AND AD_Tree_ID=10
;

-- 16.03.2016 11:22
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540697, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540690 AND AD_Tree_ID=10
;

-- 16.03.2016 11:23
-- URL zum Konzept
UPDATE AD_Window SET Name='Verfügbarkeitstrenddatensatz',Updated=TO_TIMESTAMP('2016-03-16 11:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540290
;

-- 16.03.2016 11:23
-- URL zum Konzept
UPDATE AD_Window_Trl SET IsTranslated='N' WHERE AD_Window_ID=540290
;

-- 16.03.2016 11:23
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Verfügbarkeitstrenddatensatz',Updated=TO_TIMESTAMP('2016-03-16 11:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540696
;

-- 16.03.2016 11:23
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540696
;

