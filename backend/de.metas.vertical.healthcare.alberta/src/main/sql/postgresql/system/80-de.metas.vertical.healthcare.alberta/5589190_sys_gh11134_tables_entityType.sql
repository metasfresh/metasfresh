-- 2021-05-19T19:39:53.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:39:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541317
;

-- 2021-05-19T19:41:06.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579160
;

-- 2021-05-19T19:41:57.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:41:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=982
;

-- 2021-05-19T19:43:09.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2021-05-19 22:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=982
;

-- 2021-05-19T19:44:09.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541318
;

-- 2021-05-19T19:45:24.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Alberta','ALTER TABLE C_BPartner_Alberta DROP COLUMN IF EXISTS IsAlbertaDoctor')
;

-- 2021-05-19T19:45:25.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=573869
;

-- 2021-05-19T19:45:25.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=573869
;

-- 2021-05-19T19:46:07.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=29,Updated=TO_TIMESTAMP('2021-05-19 22:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573867
;

-- 2021-05-19T19:46:49.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:46:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579163
;

-- 2021-05-19T19:48:03.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579185
;

-- 2021-05-19T19:48:26.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:48:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579183
;

-- 2021-05-19T19:49:22.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579187
;

-- 2021-05-19T19:49:52.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:49:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579181
;

-- 2021-05-19T19:50:16.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579182
;

-- 2021-05-19T19:50:40.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:50:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579168
;

-- 2021-05-19T19:51:01.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579169
;

-- 2021-05-19T19:51:33.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:51:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579184
;

-- 2021-05-19T19:51:50.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579174
;

-- 2021-05-19T19:52:19.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:52:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579173
;

-- 2021-05-19T19:52:40.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579172
;

-- 2021-05-19T19:52:58.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:52:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541320
;

-- 2021-05-19T19:53:23.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:53:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579165
;

-- 2021-05-19T19:53:42.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579171
;

-- 2021-05-19T19:53:55.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579170
;

-- 2021-05-19T19:54:12.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579167
;

-- 2021-05-19T19:54:27.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:54:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579166
;

-- 2021-05-19T19:54:42.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541319
;

-- 2021-05-19T19:55:08.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:55:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579186
;

-- 2021-05-19T19:56:37.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Caregiver',Updated=TO_TIMESTAMP('2021-05-19 22:56:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541645
;

-- 2021-05-19T19:56:40.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Caregiver',Updated=TO_TIMESTAMP('2021-05-19 22:56:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=541645
;

-- 2021-05-19T19:56:43.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Caregiver',Updated=TO_TIMESTAMP('2021-05-19 22:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=541645
;

-- 2021-05-19T19:56:46.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Caregiver',Updated=TO_TIMESTAMP('2021-05-19 22:56:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541645
;

-- 2021-05-19T19:57:00.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Patient',Updated=TO_TIMESTAMP('2021-05-19 22:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=541644
;

-- 2021-05-19T19:57:04.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Patient',Updated=TO_TIMESTAMP('2021-05-19 22:57:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541644
;

-- 2021-05-19T19:57:07.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Patient',Updated=TO_TIMESTAMP('2021-05-19 22:57:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=541644
;

-- 2021-05-19T19:57:10.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Patient',Updated=TO_TIMESTAMP('2021-05-19 22:57:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541644
;

-- 2021-05-19T19:57:43.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:57:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579177
;

-- 2021-05-19T19:58:01.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:58:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579176
;

-- 2021-05-19T19:58:15.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 22:58:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541321
;

