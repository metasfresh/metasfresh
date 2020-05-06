

-- 2018-06-20T18:14:36.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=560702,Updated=TO_TIMESTAMP('2018-06-20 18:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551657
;

-- 2018-06-20T18:14:57.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,564945,0,442,552290,541556,'F',TO_TIMESTAMP('2018-06-20 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Hersteller des Produktes','Der Hersteller des Produktes (genutzt, wenn abweichend von Geschäftspartner / Lieferant).','Y','N','N','Y','N','N','Hersteller',181,0,0,TO_TIMESTAMP('2018-06-20 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-20T18:15:02.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Product Manufacturer',Updated=TO_TIMESTAMP('2018-06-20 18:15:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551657
;

-- 2018-06-20T18:15:09.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=5988
;

-- 2018-06-20T18:15:09.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=5988
;

-- 2018-06-20T18:15:21.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=7849
;

-- 2018-06-20T18:15:21.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=7849
;


/* DDL */ SELECT public.db_alter_table('I_Product','ALTER TABLE public.I_Product DROP COLUMN Manufacturer')
;

