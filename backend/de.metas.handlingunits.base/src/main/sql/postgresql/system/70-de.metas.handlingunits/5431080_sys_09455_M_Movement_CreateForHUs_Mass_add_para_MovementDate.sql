
-- 22.10.2015 17:34
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N', Name='Filter Quell-Lager',Updated=TO_TIMESTAMP('2015-10-22 17:34:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540785
;

-- 22.10.2015 17:34
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=540785
;

-- 23.10.2015 07:13
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2015-10-23 07:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540785
;



-- 22.10.2015 17:35
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1037,0,540613,540808,15,'MovementDate',TO_TIMESTAMP('2015-10-22 17:35:17','YYYY-MM-DD HH24:MI:SS'),100,'@#Date@','Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde','de.metas.handlingunits',0,'"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewqegung.','Y','N','N','N','Y','N','Bewegungs-Datum',15,TO_TIMESTAMP('2015-10-22 17:35:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.10.2015 17:35
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540808 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;


-- 23.10.2015 07:54
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2015-10-23 07:54:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540786
;

-- 23.10.2015 07:55
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,275,0,540613,540809,10,'Description',TO_TIMESTAMP('2015-10-23 07:55:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',255,'Y','N','N','N','N','N','Beschreibung für Warenbewegungen',30,TO_TIMESTAMP('2015-10-23 07:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.10.2015 07:55
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540809 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

