-- 2020-09-08T06:10:23.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Der Prozess selektiert zuerst die Transaktionen aller ausgewählten Positionen, sofern diese aktiviert und noch nicht verarbeitet sind.
Im zweiten Schritt werden die selektierten Transaktionen dann freigegeben, d.h. alle  - auch nicht selektierte - Positionen werden für die Auftragsgenerierung freigegeben.',Updated=TO_TIMESTAMP('2020-09-08 08:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584741
;

-- 2020-09-08T08:02:15.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Der Prozess selektiert zuerst die Transaktionen aller ausgewählten Positionen, sofern diese aktiviert und noch nicht verarbeitet sind.
Im zweiten Schritt werden die selektierten Transaktionen dann freigegeben, d.h. alle  - auch nicht selektierte - Positionen werden für die Auftragsgenerierung freigegeben.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-08 10:02:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584741
;

-- 2020-09-08T08:02:30.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-08 10:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540429
;

-- 2020-09-08T08:02:32.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-08 10:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540429
;

-- 2020-09-08T08:02:57.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='02 clear transaction for processing',Updated=TO_TIMESTAMP('2020-09-08 10:02:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=540429
;

-- 2020-09-08T08:03:22.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Clear selected transaktions for processing',Updated=TO_TIMESTAMP('2020-09-08 10:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584741
;

