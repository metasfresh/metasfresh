
--
-- use the new https://github.com/metasfresh/metasfresh/issues/1752 to make the field names clearer
--

-- 2018-01-10T11:12:08.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543754,0,TO_TIMESTAMP('2018-01-10 11:12:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Produktionsplandaten hinterlegt','Produktionsplandaten hinterlegt',TO_TIMESTAMP('2018-01-10 11:12:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T11:12:08.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543754 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-10T11:13:05.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=543754, Description=NULL, Help=NULL, Name='Produktionsplandaten hinterlegt',Updated=TO_TIMESTAMP('2018-01-10 11:13:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556296
;

-- 2018-01-10T11:13:21.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='EE01',Updated=TO_TIMESTAMP('2018-01-10 11:13:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543754
;

-- 2018-01-10T11:16:43.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543755,0,TO_TIMESTAMP('2018-01-10 11:16:43','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Stückliste hinterlegt','Stückliste hinterlegt',TO_TIMESTAMP('2018-01-10 11:16:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T11:16:43.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543755 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-10T11:17:01.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=543755, Description=NULL, EntityType='EE01', Help=NULL, Name='Stückliste hinterlegt',Updated=TO_TIMESTAMP('2018-01-10 11:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=3743
;


--
-- make columns IsManufactured and S_Resource updatable in PP_ProductPlanning
--
-- 2018-01-10T11:21:22.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2018-01-10 11:21:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550558
;

-- 2018-01-10T11:22:30.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, IsUpdateable='Y',Updated=TO_TIMESTAMP('2018-01-10 11:22:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53400
;

--
-- make the columns for plant, workflow and BOM mandatory if IsManufactured=Y
--
-- 2018-01-10T12:02:10.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsManufactured@=''Y''',Updated=TO_TIMESTAMP('2018-01-10 12:02:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53397
;

-- 2018-01-10T12:02:42.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsManufactured@=''Y''',Updated=TO_TIMESTAMP('2018-01-10 12:02:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53377
;

-- 2018-01-10T12:03:16.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsManufactured@=''Y''',Updated=TO_TIMESTAMP('2018-01-10 12:03:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53400
;


