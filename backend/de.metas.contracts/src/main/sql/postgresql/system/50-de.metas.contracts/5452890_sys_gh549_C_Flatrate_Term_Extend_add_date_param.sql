
-- 02.11.2016 13:02
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,574,0,540216,541050,15,'StartDate',TO_TIMESTAMP('2016-11-02 13:02:24','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.flatrate',0,'','Y','N','Y','N','N','N','Anfangsdatum',20,TO_TIMESTAMP('2016-11-02 13:02:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 02.11.2016 13:02
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541050 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 02.11.2016 13:02
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2016-11-02 13:02:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541050
;



-- 02.11.2016 15:22
-- URL zum Konzept
UPDATE AD_Process_Para SET Description='Anfangsdatum der neuen Vertragsperiode. Falls nicht gesetzt, dann wird automatisch das Enddatum (plus ein Tag) der alten Vertragsperiode angenommen', Help='Der gewählte Wert darf werder vor dem anfangsdatum noch mehr als einen Tag nach dem enddatum der alten Vertragsperiode liegen.
Beim Fertigstellen der neuen Vertragsperiode wird das Enddatum der alten Vertragsperiode angepasst um eine Überlappung zu verhindern.',Updated=TO_TIMESTAMP('2016-11-02 15:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541050
;

-- 02.11.2016 15:22
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541050
;

-- 02.11.2016 15:26
-- URL zum Konzept
UPDATE AD_Process_Para SET Help='Der gewählte Wert darf weder vor dem Anfangsdatum noch mehr als einen Tag nach dem Enddatum der alten Vertragsperiode liegen.
Beim Fertigstellen der neuen Vertragsperiode wird das Enddatum der alten Vertragsperiode angepasst, um eine Überlappung zu verhindern.',Updated=TO_TIMESTAMP('2016-11-02 15:26:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541050
;

-- 02.11.2016 15:26
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541050
;

