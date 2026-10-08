
-----------------------------------------------------

--- additional trls, descriptions etc

-- 2019-01-21T13:56:48.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='MSV3_Product_Category',Updated=TO_TIMESTAMP('2019-01-21 13:56:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540425
;


-- 2019-01-21T14:05:43.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575970,0,TO_TIMESTAMP('2019-01-21 14:05:42','YYYY-MM-DD HH24:MI:SS'),100,'Achtung: wenn die gewählte Lagergruppe leer ist werden keine Produkte an den MSV3-Server übermittelt!','de.metas.vertical.pharma.msv3.server','Y','MSV3 Kommissionier-Lagergruppe','MSV3 Kommissionier-Lagergruppe',TO_TIMESTAMP('2019-01-21 14:05:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-21T14:05:43.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575970 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-01-21T14:05:58.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=626231
;

-- 2019-01-21T14:05:59.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=626232
;

-- 2019-01-21T14:05:59.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543497,626233,563151,0,540424,TO_TIMESTAMP('2019-01-21 14:05:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-01-21 14:05:59','YYYY-MM-DD HH24:MI:SS'),100)
--;

-- 2019-01-21T14:06:02.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=575970, Description='Achtung: wenn die gewählte Lagergruppe leer ist werden keine Produkte an den MSV3-Server übermittelt!', Name='MSV3 Kommissionier-Lagergruppe',Updated=TO_TIMESTAMP('2019-01-21 14:06:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563151
;


-- 2019-01-21T14:06:02.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575970) 
;
