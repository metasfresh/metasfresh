-- 2022-03-07T11:01:53.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abrufauftragdetail', PrintName='Abrufauftragdetail',Updated=TO_TIMESTAMP('2022-03-07 13:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580603 AND AD_Language='de_CH'
;

-- 2022-03-07T11:01:53.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580603,'de_CH') 
;

-- 2022-03-07T11:02:00.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abrufauftragdetail', PrintName='Abrufauftragdetail',Updated=TO_TIMESTAMP('2022-03-07 13:02:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580603 AND AD_Language='de_DE'
;

-- 2022-03-07T11:02:00.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580603,'de_DE') 
;

-- 2022-03-07T11:02:00.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580603,'de_DE') 
;

-- 2022-03-07T11:02:00.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_CallOrderDetail_ID', Name='Abrufauftragdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=580603
;

-- 2022-03-07T11:02:00.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CallOrderDetail_ID', Name='Abrufauftragdetail', Description=NULL, Help=NULL, AD_Element_ID=580603 WHERE UPPER(ColumnName)='C_CALLORDERDETAIL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-07T11:02:00.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CallOrderDetail_ID', Name='Abrufauftragdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=580603 AND IsCentrallyMaintained='Y'
;

-- 2022-03-07T11:02:00.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abrufauftragdetail', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580603) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580603)
;

-- 2022-03-07T11:02:00.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abrufauftragdetail', Name='Abrufauftragdetail' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580603)
;

-- 2022-03-07T11:02:00.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abrufauftragdetail', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580603
;

-- 2022-03-07T11:02:00.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abrufauftragdetail', Description=NULL, Help=NULL WHERE AD_Element_ID = 580603
;

-- 2022-03-07T11:02:00.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abrufauftragdetail', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580603
;

-- 2022-03-07T11:02:12.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abrufauftragdetail', PrintName='Abrufauftragdetail',Updated=TO_TIMESTAMP('2022-03-07 13:02:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580603 AND AD_Language='nl_NL'
;

-- 2022-03-07T11:02:12.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580603,'nl_NL') 
;

-- 2022-03-07T11:02:27.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Call-off order detail', PrintName='Call-off order detail',Updated=TO_TIMESTAMP('2022-03-07 13:02:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580603 AND AD_Language='en_US'
;

-- 2022-03-07T11:02:27.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580603,'en_US') 
;

-- 2022-03-07T11:07:00.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The bill partner {0} does not match the contract partner {1} in line {2}.',Updated=TO_TIMESTAMP('2022-03-07 13:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545108
;

-- 2022-03-07T11:07:06.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Rechnungspartner {0} stimmt nicht mit dem Vertragspartner {1} in Zeile {2} überein.',Updated=TO_TIMESTAMP('2022-03-07 13:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545108
;

-- 2022-03-07T11:07:10.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Rechnungspartner {0} stimmt nicht mit dem Vertragspartner {1} in Zeile {2} überein.',Updated=TO_TIMESTAMP('2022-03-07 13:07:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545108
;

-- 2022-03-07T11:07:16.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Rechnungspartner {0} stimmt nicht mit dem Vertragspartner {1} in Zeile {2} überein.',Updated=TO_TIMESTAMP('2022-03-07 13:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545108
;

-- 2022-03-07T11:07:29.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The bill partner {0} does not match the contract partner {1} in line {2}.',Updated=TO_TIMESTAMP('2022-03-07 13:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545108
;

-- 2022-03-07T11:12:08.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The product {0} in line {1} does not match the product {2} of the contract.',Updated=TO_TIMESTAMP('2022-03-07 13:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545109
;

-- 2022-03-07T11:12:17.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The product {0} in line {1} does not match the product {2} of the contract.',Updated=TO_TIMESTAMP('2022-03-07 13:12:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545109
;

-- 2022-03-07T11:12:34.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Product {0} in Zeile {1} stimmt nicht mit dem Produkt {2} des Vertrags überein.',Updated=TO_TIMESTAMP('2022-03-07 13:12:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545109
;

-- 2022-03-07T11:12:39.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Product {0} in Zeile {1} stimmt nicht mit dem Produkt {2} des Vertrags überein.',Updated=TO_TIMESTAMP('2022-03-07 13:12:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545109
;

-- 2022-03-07T11:12:46.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Product {0} in Zeile {1} stimmt nicht mit dem Produkt {2} des Vertrags überein.',Updated=TO_TIMESTAMP('2022-03-07 13:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545109
;

-- 2022-03-07T11:17:35.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The contract in line {0} needs to have the contract type "call-off order".',Updated=TO_TIMESTAMP('2022-03-07 13:17:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545107
;

-- 2022-03-07T11:17:46.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The contract in line {0} needs to have the contract type "call-off order".',Updated=TO_TIMESTAMP('2022-03-07 13:17:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545107
;

-- 2022-03-07T11:18:21.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Vertrag in Zeile {0} muss die Vertragsart "Abrufauftrag" haben.',Updated=TO_TIMESTAMP('2022-03-07 13:18:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545107
;

-- 2022-03-07T11:18:25.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Vertrag in Zeile {0} muss die Vertragsart "Abrufauftrag" haben.',Updated=TO_TIMESTAMP('2022-03-07 13:18:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545107
;

-- 2022-03-07T11:18:30.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Vertrag in Zeile {0} muss die Vertragsart "Abrufauftrag" haben.',Updated=TO_TIMESTAMP('2022-03-07 13:18:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545107
;

-- 2022-03-07T11:32:23.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Vertrag in Zeile {0} muss die Vertragsart "Abrufauftrag" haben.',Updated=TO_TIMESTAMP('2022-03-07 13:32:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545107
;

-- 2022-03-07T11:32:58.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Rechnungspartner {0} stimmt nicht mit dem Vertragspartner {1} in Zeile {2} überein.',Updated=TO_TIMESTAMP('2022-03-07 13:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545108
;

-- 2022-03-07T11:33:19.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Das Product {0} in Zeile {1} stimmt nicht mit dem Produkt {2} des Vertrags überein.',Updated=TO_TIMESTAMP('2022-03-07 13:33:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545109
;

