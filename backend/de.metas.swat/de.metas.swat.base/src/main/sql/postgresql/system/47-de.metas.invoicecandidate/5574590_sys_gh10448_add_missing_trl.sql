-- 2020-12-23T12:05:37.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Alle Aufträge in derselben Aggregationsgruppe wurden vollständig geliefert',IsTranslated='Y',Updated=TO_TIMESTAMP('2020-12-23 12:05:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542226
;

-- 2020-12-23T12:10:25.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Auftrag komplett',Description='Alle Aufträge in derselben Aggregationsgruppe wurden vollständig geliefert',Updated=TO_TIMESTAMP('2020-12-23 12:10:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542226
;
