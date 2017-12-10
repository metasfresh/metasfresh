-- 2017-12-04T00:50:12.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,ColumnName,AD_Element_ID,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Product / Partner','ProductOrBPartner',543501,0,'Product / Partner','de.metas.picking',100,TO_TIMESTAMP('2017-12-04 00:50:12','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-04 00:50:12','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-04T00:50:12.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543501 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-12-04T00:50:18.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=544585
;

-- 2017-12-04T00:50:18.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=544585
;

-- 2017-12-04T00:50:53.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,ColumnName,AD_Element_ID,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Order / Location','OrderOrBPLocation',543502,0,'Order / Location','de.metas.picking',100,TO_TIMESTAMP('2017-12-04 00:50:53','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-04 00:50:53','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-04T00:50:53.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543502 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-12-04T00:51:01.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=544586
;

-- 2017-12-04T00:51:01.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=544586
;

