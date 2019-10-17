-- 2019-01-23T06:45:50.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541261,'S',TO_TIMESTAMP('2019-01-23 06:45:50','YYYY-MM-DD HH24:MI:SS'),100,'Specifies the max side of the webui''s document cache.
Note that on a machine with 1GB of memory (-Xmx1024M), we ran into an OOME and from the heap dump it turned out that this cache had contained 1474 documents and had reached a heap size of 507MB.
The default if not set is 800.
','de.metas.ui.web','Y','de.metas.ui.web.window.model.DocumentCollection.CacheSize',TO_TIMESTAMP('2019-01-23 06:45:50','YYYY-MM-DD HH24:MI:SS'),100,'800')
;

