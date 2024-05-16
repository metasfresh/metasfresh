

-- 2021-08-31T17:58:20.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Ein prozentualer Anteil der Verkaufserlöse wird an eine Hierarchie von Vertriebspartnern ausgeschüttet.', Name='Hierarchie-Provision',Updated=TO_TIMESTAMP('2021-08-31 20:58:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542010
;

-- 2021-08-31T17:58:39.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Ein prozentualer Anteil der Verkaufserlöse wird an eine Hierarchie von Vertriebspartnern ausgeschüttet.', Name='Hierarchie-Provision',Updated=TO_TIMESTAMP('2021-08-31 20:58:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542010
;

-- 2021-08-31T17:58:58.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='A percentage of sales revenue is distributed to a hierarchy of sales-partners.', Name='Hierarchy Commission',Updated=TO_TIMESTAMP('2021-08-31 20:58:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542010
;

-- 2021-08-31T17:59:07.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Ein prozentualer Anteil der Verkaufserlöse wird an eine Hierarchie von Vertriebspartnern ausgeschüttet.', Name='Hierarchie-Provision',Updated=TO_TIMESTAMP('2021-08-31 20:59:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542010
;

-- 2021-08-31T17:59:16.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Ein prozentualer Anteil der Verkaufserlöse wird an eine Hierarchie von Vertriebspartnern ausgeschüttet.', Name='Hierarchie-Provision',Updated=TO_TIMESTAMP('2021-08-31 20:59:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542010
;

-- 2021-08-31T17:59:56.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542857,540271,TO_TIMESTAMP('2021-08-31 20:59:56','YYYY-MM-DD HH24:MI:SS'),100,'Die Differenz zwischen dem Endkunden-Preis und dem Vertriebspartner-Preis wird teilweise als Endkunden-Rabatt gewährt und teilweise als Provision ausgeschüttet.','de.metas.contracts','Y','Margen-Provision',TO_TIMESTAMP('2021-08-31 20:59:56','YYYY-MM-DD HH24:MI:SS'),100,'MarginCommission','MarginCommission')
;

-- 2021-08-31T17:59:56.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542857 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-08-31T18:00:19.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='The difference between the end-customer price and the sales-partner price is partly granted as an end-customer discount and partly distributed as commission.', Name='Margin Commission',Updated=TO_TIMESTAMP('2021-08-31 21:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542857
;

