-- ScriptedImportConversion "call" process command list (AD_Reference 541998).
-- A parent ExternalSystem_Config can have several import children with different endpoints (one REST,
-- one SFTP), so the user must not pick a transport-specific command. The process param becomes a
-- Start/Stop intent; the concrete command (enableRestAPI vs enableSftpPolling) is derived per child
-- from the child's endpoint transport (InvokeScriptedImportConversionAction).
--
-- Remove the now-unused transport-specific list entries (enableRestAPI 544006, disableRestAPI 544007)
-- and add Start / Stop. The command STRINGS enableRestAPI/disableRestAPI remain live (enum +
-- ExternalSystem_Service records) -- only the user-facing dropdown rows change.
--
-- IDs allocated from idserver.metas.de on 2026-07-19:
--   AD_Ref_List 544319 (start), 544320 (stop)

-- drop the obsolete REST-specific list entries (+ their translations)
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID IN (544006, 544007);
DELETE FROM AD_Ref_List     WHERE AD_Ref_List_ID IN (544006, 544007);

-- Start
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544319 /*From ID Server*/,541998,now(),100,'de.metas.externalsystem','Y','Starten',now(),100,'start','Start');

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544319
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

UPDATE AD_Ref_List_Trl SET Name='Starten',   IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Ref_List_ID=544319 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Ref_List_Trl SET Name='Start',     IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Ref_List_ID=544319 AND AD_Language='en_US';
UPDATE AD_Ref_List_Trl SET Name='Démarrer',  IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Ref_List_ID=544319 AND AD_Language='fr_CH';
UPDATE AD_Ref_List_Trl SET Name='Avvia',     IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Ref_List_ID=544319 AND AD_Language='it_CH';

-- Stop
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544320 /*From ID Server*/,541998,now(),100,'de.metas.externalsystem','Y','Stoppen',now(),100,'stop','Stop');

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544320
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

UPDATE AD_Ref_List_Trl SET Name='Stoppen',  IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Ref_List_ID=544320 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Ref_List_Trl SET Name='Stop',     IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Ref_List_ID=544320 AND AD_Language='en_US';
UPDATE AD_Ref_List_Trl SET Name='Arrêter',  IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Ref_List_ID=544320 AND AD_Language='fr_CH';
UPDATE AD_Ref_List_Trl SET Name='Arresta',  IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Ref_List_ID=544320 AND AD_Language='it_CH';
