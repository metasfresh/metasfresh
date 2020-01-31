-- 2019-11-20T14:19:12.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET IsActive='Y', Name='Produkt Plandaten_OLD',Updated=TO_TIMESTAMP('2019-11-20 16:19:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=53007
;

-- 2019-11-20T14:19:12.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Erfassen der Produkt Plandaten', IsActive='Y', Name='Produkt Plandaten_OLD',Updated=TO_TIMESTAMP('2019-11-20 16:19:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53032
;

-- 2019-11-20T14:19:12.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Erfassen der Produkt Plandaten', IsActive='Y', Name='Produkt Plandaten_OLD',Updated=TO_TIMESTAMP('2019-11-20 16:19:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540820
;

-- 2019-11-20T14:19:12.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET IsActive='Y',Updated=TO_TIMESTAMP('2019-11-20 16:19:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50052
;

-- 2019-11-20T14:19:20.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=574285, Description='Erfassen der Produkt Plandaten', Help='in the Window Product Planning Data you enter the product information which will serve as a base to execute the algorithms of Material Requirement Planning, along with MPS, open orders and inventories', Name='Produkt Plandaten',Updated=TO_TIMESTAMP('2019-11-20 16:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540750
;

-- 2019-11-20T14:19:20.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Erfassen der Produkt Plandaten', IsActive='Y', Name='Produkt Plandaten',Updated=TO_TIMESTAMP('2019-11-20 16:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541392
;

-- 2019-11-20T14:19:20.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(574285) 
;

-- 2019-11-20T14:19:20.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540750
;

-- 2019-11-20T14:19:20.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(540750)
;

-- 2019-11-20T14:23:05.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-20 16:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=53007
;

-- 2019-11-20T14:23:05.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Erfassen der Produkt Plandaten', IsActive='N', Name='Produkt Plandaten_OLD',Updated=TO_TIMESTAMP('2019-11-20 16:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53032
;

-- 2019-11-20T14:23:05.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Erfassen der Produkt Plandaten', IsActive='N', Name='Produkt Plandaten_OLD',Updated=TO_TIMESTAMP('2019-11-20 16:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540820
;

-- 2019-11-20T14:23:05.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-20 16:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50052
;

