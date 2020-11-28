
-- 26.11.2015 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Description='Entscheidet, ob die betreffende Preislistenversion vom System in der Karotten-/Lagerabrechnung verwendet wird.', Name='Freigegeben für Karotten-/Lagerabrechnung',Updated=TO_TIMESTAMP('2015-11-26 09:58:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556416
;

-- 26.11.2015 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=556416
;
