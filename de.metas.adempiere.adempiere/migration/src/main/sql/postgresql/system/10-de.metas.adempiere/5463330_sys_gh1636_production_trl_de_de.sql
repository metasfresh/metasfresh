-- 2017-05-24T08:30:20.740
-- URL zum Konzept
UPDATE AD_Process SET Name='Status: Planung',Updated=TO_TIMESTAMP('2017-05-24 08:30:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540781
;

-- 2017-05-24T08:30:20.751
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540781
;

-- 2017-05-24T08:30:25.004
-- URL zum Konzept
UPDATE AD_Process SET Name='Status: Prüfung',Updated=TO_TIMESTAMP('2017-05-24 08:30:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540782
;

-- 2017-05-24T08:30:25.009
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540782
;

-- 2017-05-24T08:30:37.938
-- URL zum Konzept
UPDATE AD_Process SET Name='Produzieren (barcode)',Updated=TO_TIMESTAMP('2017-05-24 08:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540788
;

-- 2017-05-24T08:30:37.943
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540788
;

-- 2017-05-24T08:30:42.111
-- URL zum Konzept
UPDATE AD_Process SET Name='Produzieren',Updated=TO_TIMESTAMP('2017-05-24 08:30:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540772
;

-- 2017-05-24T08:30:42.119
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540772
;

-- 2017-05-24T08:30:50.621
-- URL zum Konzept
UPDATE AD_Process SET Name='empfangen',Updated=TO_TIMESTAMP('2017-05-24 08:30:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540773
;

-- 2017-05-24T08:30:50.632
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540773
;

-- 2017-05-24T08:31:00.256
-- URL zum Konzept
UPDATE AD_Process SET Name='Rückgängig machen',Updated=TO_TIMESTAMP('2017-05-24 08:31:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540791
;

-- 2017-05-24T08:31:00.265
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540791
;
update ad_message
set msgtext='hinzufügen'
where value = 'PPOrderIncludedHUEditorActions.issueSelectedHUs';
update ad_message
set msgtext='hinzufügen (Planmenge)'
where value = 'PPOrderIncludedHUEditorActions.issueSelectedTUs';
update ad_message
set msgtext='hinzufügen'
where value = 'PPOrderLinesView.openViewsToIssue';
