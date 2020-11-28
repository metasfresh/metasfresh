--
--
-- rearange, translate and hide unused

-- 04.12.2015 15:22
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552867,469,0,10,298,'N','Name',TO_TIMESTAMP('2015-12-04 15:22:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Alphanumeric identifier of the entity','D',60,'The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','N','Y','N','Name',10,TO_TIMESTAMP('2015-12-04 15:22:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.12.2015 15:22
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552867 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.12.2015 15:22
-- URL zum Konzept
UPDATE AD_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2015-12-04 15:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=5232
;

-- 04.12.2015 15:23
-- URL zum Konzept
UPDATE AD_Column SET SeqNo=30,Updated=TO_TIMESTAMP('2015-12-04 15:23:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3105
;

-- 04.12.2015 15:23
-- URL zum Konzept
UPDATE AD_Column SET SeqNo=40,Updated=TO_TIMESTAMP('2015-12-04 15:23:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3103
;

-- 04.12.2015 15:23
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2015-12-04 15:23:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552867
;

-- 04.12.2015 15:28
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2015-12-04 15:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2198
;

-- 04.12.2015 15:29
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-12-04 15:29:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2199
;

-- 04.12.2015 15:29
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=35, SeqNoGrid=25,Updated=TO_TIMESTAMP('2015-12-04 15:29:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2203
;

-- 04.12.2015 15:30
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552867,556433,0,226,0,TO_TIMESTAMP('2015-12-04 15:30:38','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',0,'de.metas.banking','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',0,'Y','Y','Y','Y','N','N','N','N','N','Name',37,27,0,TO_TIMESTAMP('2015-12-04 15:30:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.12.2015 15:30
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556433 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 04.12.2015 15:36
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=2204
;

-- 04.12.2015 15:36
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=2204
;

-- 04.12.2015 15:42
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Name='Inhaber-Name',Updated=TO_TIMESTAMP('2015-12-04 15:42:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4100
;

-- 04.12.2015 15:42
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=4100
;

-- 04.12.2015 15:42
-- URL zum Konzept
INSERT INTO AD_FieldGroup (AD_Client_ID,AD_FieldGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,FieldGroupType,IsActive,IsCollapsedByDefault,Name,Updated,UpdatedBy) VALUES (0,540082,0,TO_TIMESTAMP('2015-12-04 15:42:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','C','Y','Y','Kontoinhaber',TO_TIMESTAMP('2015-12-04 15:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.12.2015 15:42
-- URL zum Konzept
INSERT INTO AD_FieldGroup_Trl (AD_Language,AD_FieldGroup_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_FieldGroup_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_FieldGroup t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_FieldGroup_ID=540082 AND NOT EXISTS (SELECT * FROM AD_FieldGroup_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_FieldGroup_ID=t.AD_FieldGroup_ID)
;

-- 04.12.2015 15:43
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540082,Updated=TO_TIMESTAMP('2015-12-04 15:43:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4100
;

-- 04.12.2015 15:43
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540082,Updated=TO_TIMESTAMP('2015-12-04 15:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4102
;

-- 04.12.2015 15:43
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540082,Updated=TO_TIMESTAMP('2015-12-04 15:43:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4096
;

-- 04.12.2015 15:43
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540082,Updated=TO_TIMESTAMP('2015-12-04 15:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4103
;

-- 04.12.2015 15:43
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540082,Updated=TO_TIMESTAMP('2015-12-04 15:43:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4101
;

-- 04.12.2015 15:43
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540082,Updated=TO_TIMESTAMP('2015-12-04 15:43:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6298
;

-- 04.12.2015 15:45
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540082, IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-12-04 15:45:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4098
;

-- 04.12.2015 15:45
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2015-12-04 15:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4099
;

-- 04.12.2015 15:45
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-12-04 15:45:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4099
;

-- 04.12.2015 15:45
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2015-12-04 15:45:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4098
;

-- 04.12.2015 15:46
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=38, SeqNoGrid=28,Updated=TO_TIMESTAMP('2015-12-04 15:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551430
;

-- 04.12.2015 15:49
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-12-04 15:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2201
;

-- 04.12.2015 15:50
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.payment.esr', SeqNo=182, SeqNoGrid=152,Updated=TO_TIMESTAMP('2015-12-04 15:50:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551427
;

-- 04.12.2015 15:50
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.payment.esr', SeqNo=184, SeqNoGrid=154,Updated=TO_TIMESTAMP('2015-12-04 15:50:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551428
;

-- 04.12.2015 15:51
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=188, SeqNoGrid=158,Updated=TO_TIMESTAMP('2015-12-04 15:51:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551428
;

-- 04.12.2015 15:51
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=186, SeqNoGrid=156,Updated=TO_TIMESTAMP('2015-12-04 15:51:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551427
;

-- 04.12.2015 15:51
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=154, SeqNoGrid=184,Updated=TO_TIMESTAMP('2015-12-04 15:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551911
;

-- 04.12.2015 15:52
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=184, SeqNoGrid=154,Updated=TO_TIMESTAMP('2015-12-04 15:52:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551911
;

-- 04.12.2015 15:52
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=182, SeqNoGrid=152,Updated=TO_TIMESTAMP('2015-12-04 15:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551429
;

-- 04.12.2015 15:53
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-12-04 15:53:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4104
;

-- 04.12.2015 15:53
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=88, SeqNoGrid=78,Updated=TO_TIMESTAMP('2015-12-04 15:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551666
;

-- 04.12.2015 15:53
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=85, SeqNoGrid=75,Updated=TO_TIMESTAMP('2015-12-04 15:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551666
;

-- 04.12.2015 15:58
-- URL zum Konzept
UPDATE AD_Field SET Name='Ansprechpartner',Updated=TO_TIMESTAMP('2015-12-04 15:58:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4105
;

-- 04.12.2015 15:58
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=4105
;

-- 04.12.2015 15:58
-- URL zum Konzept
UPDATE AD_Element SET Name='ESR Konto', PrintName='ESR Konto',Updated=TO_TIMESTAMP('2015-12-04 15:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541939
;

-- 04.12.2015 15:58
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541939
;

-- 04.12.2015 15:58
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsEsrAccount', Name='ESR Konto', Description=NULL, Help=NULL WHERE AD_Element_ID=541939
;

-- 04.12.2015 15:58
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsEsrAccount', Name='ESR Konto', Description=NULL, Help=NULL, AD_Element_ID=541939 WHERE UPPER(ColumnName)='ISESRACCOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 04.12.2015 15:58
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsEsrAccount', Name='ESR Konto', Description=NULL, Help=NULL WHERE AD_Element_ID=541939 AND IsCentrallyMaintained='Y'
;

-- 04.12.2015 15:58
-- URL zum Konzept
UPDATE AD_Field SET Name='ESR Konto', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541939) AND IsCentrallyMaintained='Y'
;

-- 04.12.2015 15:58
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='ESR Konto', Name='ESR Konto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541939)
;

-- 04.12.2015 15:59
-- URL zum Konzept
UPDATE AD_Element SET Name='Standard ESR Konto', PrintName='Standard ESR Konto',Updated=TO_TIMESTAMP('2015-12-04 15:59:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542111
;

-- 04.12.2015 15:59
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542111
;

-- 04.12.2015 15:59
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsDefaultESR', Name='Standard ESR Konto', Description=NULL, Help=NULL WHERE AD_Element_ID=542111
;

-- 04.12.2015 15:59
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDefaultESR', Name='Standard ESR Konto', Description=NULL, Help=NULL, AD_Element_ID=542111 WHERE UPPER(ColumnName)='ISDEFAULTESR' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 04.12.2015 15:59
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDefaultESR', Name='Standard ESR Konto', Description=NULL, Help=NULL WHERE AD_Element_ID=542111 AND IsCentrallyMaintained='Y'
;

-- 04.12.2015 15:59
-- URL zum Konzept
UPDATE AD_Field SET Name='Standard ESR Konto', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542111) AND IsCentrallyMaintained='Y'
;

-- 04.12.2015 15:59
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Standard ESR Konto', Name='Standard ESR Konto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542111)
;

-- 04.12.2015 16:04
-- URL zum Konzept
UPDATE AD_Element SET Name='ESR Teilnehmernummer', PrintName='ESR Teilnehmernummer',Updated=TO_TIMESTAMP('2015-12-04 16:04:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541925
;

-- 04.12.2015 16:04
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541925
;

-- 04.12.2015 16:04
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='ESR_RenderedAccountNo', Name='ESR Teilnehmernummer', Description=NULL, Help=NULL WHERE AD_Element_ID=541925
;

-- 04.12.2015 16:04
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ESR_RenderedAccountNo', Name='ESR Teilnehmernummer', Description=NULL, Help=NULL, AD_Element_ID=541925 WHERE UPPER(ColumnName)='ESR_RENDEREDACCOUNTNO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 04.12.2015 16:04
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ESR_RenderedAccountNo', Name='ESR Teilnehmernummer', Description=NULL, Help=NULL WHERE AD_Element_ID=541925 AND IsCentrallyMaintained='Y'
;

-- 04.12.2015 16:04
-- URL zum Konzept
UPDATE AD_Field SET Name='ESR Teilnehmernummer', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541925) AND IsCentrallyMaintained='Y'
;

-- 04.12.2015 16:04
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='ESR Teilnehmernummer', Name='ESR Teilnehmernummer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541925)
;

-- 04.12.2015 16:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-12-04 16:06:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551428
;

-- 04.12.2015 16:06
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2015-12-04 16:06:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=5232
;

COMMIT;

-- 04.12.2015 16:09
-- URL zum Konzept
ALTER TABLE C_BP_BankAccount ADD Name VARCHAR(60) DEFAULT NULL 
;

--
-- currently A_Name is used to denominate the C_BP_BankAccount record , but in future, it shall be used for the account holder's name
-- do we move the values to Name and clear A_Name
UPDATE C_BP_BankAccount 
SET 
	Name=A_Name,
	Updated=now(),
	UpdatedBy=99
WHERE A_Name IS NOT NULL;

UPDATE C_BP_BankAccount 
SET A_Name=NULL
WHERE A_Name IS NOT NULL;
