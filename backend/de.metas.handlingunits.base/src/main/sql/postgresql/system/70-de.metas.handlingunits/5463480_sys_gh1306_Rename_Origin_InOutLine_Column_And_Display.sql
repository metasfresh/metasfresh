-- 2017-05-26T18:08:52.480
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='Return_Origin_InOutLine_ID', Name='Return_Origin_InOutLine_ID', PrintName='Return_Origin_InOutLine_ID',Updated=TO_TIMESTAMP('2017-05-26 18:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543319
;

-- 2017-05-26T18:08:52.488
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543319
;

-- 2017-05-26T18:08:52.492
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Return_Origin_InOutLine_ID', Name='Return_Origin_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL WHERE AD_Element_ID=543319
;

-- 2017-05-26T18:08:52.552
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Return_Origin_InOutLine_ID', Name='Return_Origin_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL, AD_Element_ID=543319 WHERE UPPER(ColumnName)='RETURN_ORIGIN_INOUTLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-26T18:08:52.559
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Return_Origin_InOutLine_ID', Name='Return_Origin_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL WHERE AD_Element_ID=543319 AND IsCentrallyMaintained='Y'
;

-- 2017-05-26T18:08:52.560
-- URL zum Konzept
UPDATE AD_Field SET Name='Return_Origin_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543319) AND IsCentrallyMaintained='Y'
;

-- 2017-05-26T18:08:52.573
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Return_Origin_InOutLine_ID', Name='Return_Origin_InOutLine_ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543319)
;

-- 2017-05-26T18:10:28.686
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT * FROM M_InOut io WHERE M_InOutLine.M_InOut_ID=io.M_InOut_ID)',Updated=TO_TIMESTAMP('2017-05-26 18:10:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=234
;

-- 2017-05-26T18:11:44.622
-- URL zum Konzept
UPDATE AD_Field SET Description='Original inout line containing the products that are returned.',Updated=TO_TIMESTAMP('2017-05-26 18:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558145
;

-- 2017-05-26T18:11:44.625
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=558145
;

-- 2017-05-26T18:11:49.241
-- URL zum Konzept
UPDATE AD_Field SET Description='Original receipt line containing the products that are returned.',Updated=TO_TIMESTAMP('2017-05-26 18:11:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558145
;

-- 2017-05-26T18:11:49.243
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=558145
;

-- 2017-05-26T18:11:54.774
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-05-26 18:11:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558145
;

-- 2017-05-26T18:12:43.281
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556514,558524,0,53272,0,TO_TIMESTAMP('2017-05-26 18:12:42','YYYY-MM-DD HH24:MI:SS'),100,'Original receipt line containing the products that are returned.',0,'de.metas.handlingunits',0,'Y','Y','Y','Y','N','N','N','N','N','Return_Origin_InOutLine_ID',330,330,0,1,1,TO_TIMESTAMP('2017-05-26 18:12:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-26T18:12:43.287
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558524 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-05-26T18:12:46.104
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-05-26 18:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558524
;


 ALTER TABLE M_InOutLine
    RENAME vendorReturn_Origin_inoutline_ID  TO Return_Origin_inoutline_ID;