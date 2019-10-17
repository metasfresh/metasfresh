-- 2018-11-27T07:57:51.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2018-11-27 07:57:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540980
;

UPDATE AD_Ref_List SET ValueName='Tiered' WHERE (AD_Ref_List_ID=541669);

-- 2018-11-27T08:01:10.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='C_Invoice_Candidate',Updated=TO_TIMESTAMP('2018-11-27 08:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540092
;

-- 2018-11-27T08:02:18.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-27 08:02:18','YYYY-MM-DD HH24:MI:SS'),Name='Rückzuvergütende RKn',PrintName='Rückzuvergütende RKn' WHERE AD_Element_ID=572548 AND AD_Language='nl_NL'
;

-- 2018-11-27T08:02:18.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572548,'nl_NL') 
;

-- 2018-11-27T08:04:53.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-27 08:04:53','YYYY-MM-DD HH24:MI:SS'),Description='Ggf. andere Rechnungskandidaten, die einem Rückvergütungsvertrag unterliegen. Die Rückvergütung erfolgt durch den aktuellen Rechnungskandiaten.' WHERE AD_Element_ID=572548 AND AD_Language='de_DE'
;

-- 2018-11-27T08:04:53.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572548,'de_DE') 
;

-- 2018-11-27T08:04:53.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(572548,'de_DE') 
;

-- 2018-11-27T08:04:53.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Rückzuvergütende RKn', Description='Ggf. andere Rechnungskandidaten, die einem Rückvergütungsvertrag unterliegen. Die Rückvergütung erfolgt durch den aktuellen Rechnungskandiaten.', Help=NULL WHERE AD_Element_ID=572548
;

-- 2018-11-27T08:04:53.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rückzuvergütende RKn', Description='Ggf. andere Rechnungskandidaten, die einem Rückvergütungsvertrag unterliegen. Die Rückvergütung erfolgt durch den aktuellen Rechnungskandiaten.', Help=NULL WHERE AD_Element_ID=572548 AND IsCentrallyMaintained='Y'
;

-- 2018-11-27T08:04:53.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rückzuvergütende RKn', Description='Ggf. andere Rechnungskandidaten, die einem Rückvergütungsvertrag unterliegen. Die Rückvergütung erfolgt durch den aktuellen Rechnungskandiaten.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=572548) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 572548)
;

-- 2018-11-27T08:04:53.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rückzuvergütende RKn', Description='Ggf. andere Rechnungskandidaten, die einem Rückvergütungsvertrag unterliegen. Die Rückvergütung erfolgt durch den aktuellen Rechnungskandiaten.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 572548
;

-- 2018-11-27T08:04:53.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rückzuvergütende RKn', Description='Ggf. andere Rechnungskandidaten, die einem Rückvergütungsvertrag unterliegen. Die Rückvergütung erfolgt durch den aktuellen Rechnungskandiaten.', Help=NULL WHERE AD_Element_ID = 572548
;

-- 2018-11-27T08:04:53.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Rückzuvergütende RKn', Description='Ggf. andere Rechnungskandidaten, die einem Rückvergütungsvertrag unterliegen. Die Rückvergütung erfolgt durch den aktuellen Rechnungskandiaten.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 572548
;

-- 2018-11-27T08:04:58.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-27 08:04:58','YYYY-MM-DD HH24:MI:SS'),Description='Ggf. andere Rechnungskandidaten, die einem Rückvergütungsvertrag unterliegen. Die Rückvergütung erfolgt durch den aktuellen Rechnungskandiaten.' WHERE AD_Element_ID=572548 AND AD_Language='nl_NL'
;

-- 2018-11-27T08:04:58.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572548,'nl_NL') 
;

-- 2018-11-27T08:05:02.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-27 08:05:02','YYYY-MM-DD HH24:MI:SS'),Description='Ggf. andere Rechnungskandidaten, die einem Rückvergütungsvertrag unterliegen. Die Rückvergütung erfolgt durch den aktuellen Rechnungskandiaten.' WHERE AD_Element_ID=572548 AND AD_Language='de_CH'
;

-- 2018-11-27T08:05:02.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572548,'de_CH') 
;

-- 2018-11-27T08:06:49.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_Flatrate_Term_ID',Updated=TO_TIMESTAMP('2018-11-27 08:06:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552064
;

-- 2018-11-27T08:07:05.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='AD_Org_ID',Updated=TO_TIMESTAMP('2018-11-27 08:07:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552060
;

-- 2018-11-27T08:07:10.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='AD_Client_ID',Updated=TO_TIMESTAMP('2018-11-27 08:07:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552059
;

-- 2018-11-27T08:07:17.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='IsActive',Updated=TO_TIMESTAMP('2018-11-27 08:07:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552061
;

-- 2018-11-27T08:07:25.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_Invoice_Candidate_Term_ID',Updated=TO_TIMESTAMP('2018-11-27 08:07:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552062
;

-- 2018-11-27T08:07:37.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_Invoice_Candidate_Assigned_ID',Updated=TO_TIMESTAMP('2018-11-27 08:07:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552063
;

-- 2018-11-27T08:08:30.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-27 08:08:30','YYYY-MM-DD HH24:MI:SS'),Name='Rückzuvergütender RK',PrintName='Rückzuvergütender RK' WHERE AD_Element_ID=544075 AND AD_Language='de_CH'
;

-- 2018-11-27T08:08:30.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544075,'de_CH') 
;

-- 2018-11-27T08:08:44.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-27 08:08:44','YYYY-MM-DD HH24:MI:SS'),Name='Refundable invoice candidate',PrintName='Refundable invoice candidate' WHERE AD_Element_ID=544075 AND AD_Language='en_US'
;

-- 2018-11-27T08:08:44.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544075,'en_US') 
;

-- 2018-11-27T08:08:53.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-27 08:08:53','YYYY-MM-DD HH24:MI:SS'),Name='Rückzuvergütender RK',PrintName='Rückzuvergütender RK' WHERE AD_Element_ID=544075 AND AD_Language='nl_NL'
;

-- 2018-11-27T08:08:53.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544075,'nl_NL') 
;

-- 2018-11-27T08:08:58.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-27 08:08:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Rückzuvergütender RK',PrintName='Rückzuvergütender RK' WHERE AD_Element_ID=544075 AND AD_Language='de_DE'
;

-- 2018-11-27T08:08:58.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544075,'de_DE') 
;

-- 2018-11-27T08:08:58.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(544075,'de_DE') 
;

-- 2018-11-27T08:08:58.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_Candidate_Assigned_ID', Name='Rückzuvergütender RK', Description=NULL, Help=NULL WHERE AD_Element_ID=544075
;

-- 2018-11-27T08:08:58.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_Assigned_ID', Name='Rückzuvergütender RK', Description=NULL, Help=NULL, AD_Element_ID=544075 WHERE UPPER(ColumnName)='C_INVOICE_CANDIDATE_ASSIGNED_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-11-27T08:08:58.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_Assigned_ID', Name='Rückzuvergütender RK', Description=NULL, Help=NULL WHERE AD_Element_ID=544075 AND IsCentrallyMaintained='Y'
;

-- 2018-11-27T08:08:58.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rückzuvergütender RK', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544075) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544075)
;

-- 2018-11-27T08:08:58.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rückzuvergütender RK', Name='Rückzuvergütender RK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544075)
;

-- 2018-11-27T08:08:58.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rückzuvergütender RK', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 544075
;

-- 2018-11-27T08:08:58.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rückzuvergütender RK', Description=NULL, Help=NULL WHERE AD_Element_ID = 544075
;

-- 2018-11-27T08:08:58.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Rückzuvergütender RK', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 544075
;

-- 2018-11-27T08:10:39.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,568304,0,541110,541613,554410,'F',TO_TIMESTAMP('2018-11-27 08:10:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_Flatrate_RefundConfig_ID',60,0,0,TO_TIMESTAMP('2018-11-27 08:10:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-27T08:11:05.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,568305,0,541110,541613,554411,'F',TO_TIMESTAMP('2018-11-27 08:11:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'BaseMoneyAmount',70,0,0,TO_TIMESTAMP('2018-11-27 08:11:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-27T08:11:26.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,568302,0,541110,541613,554412,'F',TO_TIMESTAMP('2018-11-27 08:11:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'AssignedMoneyAmount',80,0,0,TO_TIMESTAMP('2018-11-27 08:11:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-27T08:11:53.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,568303,0,541110,541613,554413,'F',TO_TIMESTAMP('2018-11-27 08:11:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'AssignedQuantity',90,0,0,TO_TIMESTAMP('2018-11-27 08:11:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-27T08:12:24.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,568306,0,541110,541613,554414,'F',TO_TIMESTAMP('2018-11-27 08:12:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsAssignedQuantityIncludedInSum',100,0,0,TO_TIMESTAMP('2018-11-27 08:12:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-27T08:13:56.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-11-27 08:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552062
;

-- 2018-11-27T08:13:56.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-11-27 08:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554410
;

-- 2018-11-27T08:13:56.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-11-27 08:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554411
;

-- 2018-11-27T08:13:56.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-11-27 08:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554412
;

-- 2018-11-27T08:13:56.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-11-27 08:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554413
;

-- 2018-11-27T08:13:56.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-11-27 08:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554414
;

-- 2018-11-27T08:13:56.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-11-27 08:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552061
;

-- 2018-11-27T08:13:56.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2018-11-27 08:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552060
;

-- 2018-11-27T09:16:04.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='T',Updated=TO_TIMESTAMP('2018-11-27 09:16:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541669
;

UPDATE C_Flatrate_RefundConfig SET Updated=now(), UpdatedBy=99, RefundMode='T' WHERE RefundMode='S';

ALTER TABLE public.c_flatrate_refundconfig
    ALTER COLUMN refundpercent DROP NOT NULL;
