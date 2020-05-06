
-- 2018-10-02T07:26:55.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET IsActive='Y',Updated=TO_TIMESTAMP('2018-10-02 07:26:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540
;

-- 2018-10-02T07:56:34.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Produkte mit diesem Typ sind nicht Lagerhaltig, und es werden keine Lieferdispo-Datensätze für sie erstellt. In der Rechnungsdispo wird sofort Liefermenge gleich beauftragte Menge gesetzt.',Updated=TO_TIMESTAMP('2018-10-02 07:56:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540
;

-- 2018-10-02T07:57:03.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-02 07:57:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='Produkte mit diesem Typ sind nicht Lagerhaltig, und es werden keine Lieferdispo-Datensätze für sie erstellt. In der Rechnungsdispo wird sofort Liefermenge gleich beauftragte Menge gesetzt.' WHERE AD_Ref_List_ID=540 AND AD_Language='de_CH'
;

-- 2018-10-02T07:58:31.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-02 07:58:31','YYYY-MM-DD HH24:MI:SS'),Description='Products with this type are never stocked. No delivery schedule records are created for them and in invoice schedule records, their delivered quantity is alsways set to equal the ordered quantity.' WHERE AD_Ref_List_ID=540 AND AD_Language='en_US'
;


-- activate the "Hour" UOM
UPDATE C_UOM SET IsActive='Y' WHERE C_UOM_ID=540024

