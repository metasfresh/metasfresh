-- 2017-09-04T12:37:20.242
-- URL zum Konzept
UPDATE AD_Process SET AllowProcessReRun='N',Updated=TO_TIMESTAMP('2017-09-04 12:37:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540820
;

-- 2017-09-04T12:37:32.672
-- URL zum Konzept
UPDATE AD_Table_Process SET WEBUI_QuickAction='Y',Updated=TO_TIMESTAMP('2017-09-04 12:37:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540820 AND AD_Table_ID=540516
;

-- 2017-09-04T12:40:39.837
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540369,'M_Warehouse.AD_Org_ID=@AD_Org_ID/-1@ AND M_Warehouse.M_Warehouse_ID <> @M_Warehouse_ID/-1@',TO_TIMESTAMP('2017-09-04 12:40:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','M_Warehouse Org Different','S',TO_TIMESTAMP('2017-09-04 12:40:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-04T12:43:27.436
-- URL zum Konzept
UPDATE AD_Val_Rule SET Name='M_Warehouse Same Org Different than Context Warehouse',Updated=TO_TIMESTAMP('2017-09-04 12:43:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540369
;

-- 2017-09-04T12:44:40.314
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,459,0,540820,541204,30,540369,'M_Warehouse_ID',TO_TIMESTAMP('2017-09-04 12:44:40','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','de.metas.handlingunits',0,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','Lager',10,TO_TIMESTAMP('2017-09-04 12:44:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-04T12:44:40.321
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541204 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-09-04T12:44:53.462
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-09-04 12:44:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541204
;

