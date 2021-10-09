-- 2019-11-04T14:26:30.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Abgang, zu dem es keinen breits bestehenden Dispo-Datensatz gibt, der exakt mit Attributen und Zeitstempel passt.', Name='Ungeplanter Abgang', Value='UNEXPECTED_DECREASE',Updated=TO_TIMESTAMP('2019-11-04 15:26:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541324
;

-- 2019-11-04T14:26:36.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Abgang, zu dem es keinen breits bestehenden Dispo-Datensatz gibt, der exakt mit Attributen und Zeitstempel passt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-04 15:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541324
;

-- 2019-11-04T14:26:45.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Ungeplanter Abgang',Updated=TO_TIMESTAMP('2019-11-04 15:26:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541324
;

-- 2019-11-04T14:26:57.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Unexpected decrease',Updated=TO_TIMESTAMP('2019-11-04 15:26:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541324
;

UPDATE AD_Ref_List SET ValueName='UNEXPECTED_DECREASE' WHERE AD_Ref_List_ID=541324;

-- 2019-11-04T14:28:41.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Ungeplanter Zugang', Value='UNEXPECTED_INCREASE',Updated=TO_TIMESTAMP('2019-11-04 15:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541323
;

-- 2019-11-04T14:28:52.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Zugang, zu dem es keinen breits bestehenden Dispo-Datensatz gibt, der exakt mit Attributen und Zeitstempel passt.',Updated=TO_TIMESTAMP('2019-11-04 15:28:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541323
;

-- 2019-11-04T14:29:10.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Zugang, zu dem es keinen breits bestehenden Dispo-Datensatz gibt, der exakt mit Attributen und Zeitstempel passt.', IsTranslated='Y', Name='Ungeplanter Zugang',Updated=TO_TIMESTAMP('2019-11-04 15:29:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=541323
;

-- 2019-11-04T14:29:23.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Unexpected increase',Updated=TO_TIMESTAMP('2019-11-04 15:29:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541323
;

UPDATE AD_Ref_List SET ValueName='UNEXPECTED_INCREASE' WHERE AD_Ref_List_ID=541323;
