-- 2017-07-26T14:40:30.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordnet',Updated=TO_TIMESTAMP('2017-07-26 14:40:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4266
;

-- 2017-07-26T14:41:09.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Zeigt an ob eine Zahlung bereits zugeordnet wurde', Help='', Name='Zugeordnet', PrintName='Zugeordnet',Updated=TO_TIMESTAMP('2017-07-26 14:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1508
;

-- 2017-07-26T14:41:09.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAllocated', Name='Zugeordnet', Description='Zeigt an ob eine Zahlung bereits zugeordnet wurde', Help='' WHERE AD_Element_ID=1508
;

-- 2017-07-26T14:41:09.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAllocated', Name='Zugeordnet', Description='Zeigt an ob eine Zahlung bereits zugeordnet wurde', Help='', AD_Element_ID=1508 WHERE UPPER(ColumnName)='ISALLOCATED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-26T14:41:09.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAllocated', Name='Zugeordnet', Description='Zeigt an ob eine Zahlung bereits zugeordnet wurde', Help='' WHERE AD_Element_ID=1508 AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T14:41:09.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordnet', Description='Zeigt an ob eine Zahlung bereits zugeordnet wurde', Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1508) AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T14:41:09.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zugeordnet', Name='Zugeordnet' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1508)
;

-- 2017-07-26T14:42:25.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abgeglichen',Updated=TO_TIMESTAMP('2017-07-26 14:42:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4040
;

-- 2017-07-26T14:43:07.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde', Name='Abgeglichen', PrintName='Abgeglichen',Updated=TO_TIMESTAMP('2017-07-26 14:43:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1105
;

-- 2017-07-26T14:43:07.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsReconciled', Name='Abgeglichen', Description='Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde', Help=NULL WHERE AD_Element_ID=1105
;

-- 2017-07-26T14:43:07.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReconciled', Name='Abgeglichen', Description='Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde', Help=NULL, AD_Element_ID=1105 WHERE UPPER(ColumnName)='ISRECONCILED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-26T14:43:07.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReconciled', Name='Abgeglichen', Description='Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde', Help=NULL WHERE AD_Element_ID=1105 AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T14:43:07.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abgeglichen', Description='Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1105) AND IsCentrallyMaintained='Y'
;

-- 2017-07-26T14:43:07.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abgeglichen', Name='Abgeglichen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1105)
;

-- 2017-07-26T14:44:58.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-26 14:44:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541197
;

-- 2017-07-26T14:44:58.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-07-26 14:44:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541198
;

-- 2017-07-26T14:44:58.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-07-26 14:44:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541189
;

-- 2017-07-26T15:14:59.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-07-26 15:14:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=755
;

-- 2017-07-26T15:16:24.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='Y', Name='Manuelle Zuordnung', SeqNo=40,Updated=TO_TIMESTAMP('2017-07-26 15:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=755
;

