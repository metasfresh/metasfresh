-- 2018-07-18T13:21:53.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544179,0,'PurchasePriceActual',TO_TIMESTAMP('2018-07-18 13:21:53','YYYY-MM-DD HH24:MI:SS'),100,'Einkaufspreis pro Einheit, nach Abzug des Rabattes.','de.metas.purchasecandidate','','Y','EK-Preis','EK-Preis',TO_TIMESTAMP('2018-07-18 13:21:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-18T13:21:53.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544179 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-18T13:22:07.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=544179, ColumnName='PurchasePriceActual', Description='Einkaufspreis pro Einheit, nach Abzug des Rabattes.', Help='', Name='EK-Preis',Updated=TO_TIMESTAMP('2018-07-18 13:22:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560358
;

-- 2018-07-18T13:22:07.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='EK-Preis', Description='Einkaufspreis pro Einheit, nach Abzug des Rabattes.', Help='' WHERE AD_Column_ID=560358
;

-- 2018-07-18T13:22:15.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=37,Updated=TO_TIMESTAMP('2018-07-18 13:22:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560190
;

-- 2018-07-18T13:22:21.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=37,Updated=TO_TIMESTAMP('2018-07-18 13:22:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560194
;

SELECT db_alter_table ('C_PurchaseCandidate','ALTER TABLE C_PurchaseCandidate RENAME COLUMN PriceActual TO PurchasePriceActual');
