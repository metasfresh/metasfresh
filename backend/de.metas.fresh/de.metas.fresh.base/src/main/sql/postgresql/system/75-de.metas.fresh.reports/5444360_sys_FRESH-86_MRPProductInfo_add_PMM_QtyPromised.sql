
-- 08.04.2016 13:46
-- URL zum Konzept
INSERT INTO AD_InfoColumn (AD_Client_ID,AD_Element_ID,AD_InfoColumn_ID,AD_InfoWindow_ID,AD_Org_ID,AD_Reference_ID,AndOr,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsParameter,IsParameterNextLine,IsQueryCriteria,IsRange,IsTree,Name,ParameterSeqNo,SelectClause,SeqNo,Updated,UpdatedBy) VALUES (0,542659,540134,540024,0,29,'N',TO_TIMESTAMP('2016-04-08 13:46:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','Y','N','N','N','N','N','Lieferangebot',160,'X_MRP_ProductInfo_V.QtyPromised_OnDate',115,TO_TIMESTAMP('2016-04-08 13:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.04.2016 13:46
-- URL zum Konzept
INSERT INTO AD_InfoColumn_Trl (AD_Language,AD_InfoColumn_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_InfoColumn_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_InfoColumn t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_InfoColumn_ID=540134 AND NOT EXISTS (SELECT * FROM AD_InfoColumn_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_InfoColumn_ID=t.AD_InfoColumn_ID)
;

-- 08.04.2016 13:50
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543070,0,'QtyPromised_PMM',TO_TIMESTAMP('2016-04-08 13:50:35','YYYY-MM-DD HH24:MI:SS'),100,'Vom Lieferanten per Webapplikation zugesagte Menge','de.metas.procurement','Y','Zusage Lieferant','Zusage Lieferant',TO_TIMESTAMP('2016-04-08 13:50:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.04.2016 13:50
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543070 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 08.04.2016 13:50
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554354,543070,0,29,540642,'N','QtyPromised_PMM',TO_TIMESTAMP('2016-04-08 13:50:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Vom Lieferanten per Webapplikation zugesagte Menge','de.metas.procurement',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Zusage Lieferant',0,TO_TIMESTAMP('2016-04-08 13:50:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 08.04.2016 13:50
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554354 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 08.04.2016 13:51
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='QtyPromised_PMM_OnDate',Updated=TO_TIMESTAMP('2016-04-08 13:51:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543070
;

-- 08.04.2016 13:51
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='QtyPromised_PMM_OnDate', Name='Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID=543070
;

-- 08.04.2016 13:51
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='QtyPromised_PMM_OnDate', Name='Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL, AD_Element_ID=543070 WHERE UPPER(ColumnName)='QTYPROMISED_PMM_ONDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 08.04.2016 13:51
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='QtyPromised_PMM_OnDate', Name='Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID=543070 AND IsCentrallyMaintained='Y'
;

-- 08.04.2016 13:52
-- URL zum Konzept
UPDATE AD_InfoColumn SET AD_Element_ID=543070,Updated=TO_TIMESTAMP('2016-04-08 13:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_InfoColumn_ID=540134
;

-- 08.04.2016 13:52
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='PMM_QtyPromised_OnDate',Updated=TO_TIMESTAMP('2016-04-08 13:52:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543070
;

-- 08.04.2016 13:52
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PMM_QtyPromised_OnDate', Name='Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID=543070
;

-- 08.04.2016 13:52
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PMM_QtyPromised_OnDate', Name='Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL, AD_Element_ID=543070 WHERE UPPER(ColumnName)='PMM_QTYPROMISED_ONDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 08.04.2016 13:52
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PMM_QtyPromised_OnDate', Name='Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID=543070 AND IsCentrallyMaintained='Y'
;

-- 08.04.2016 13:52
-- URL zum Konzept
UPDATE AD_InfoColumn SET Name='Zusage Lieferant',Updated=TO_TIMESTAMP('2016-04-08 13:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_InfoColumn_ID=540134
;

-- 08.04.2016 13:52
-- URL zum Konzept
UPDATE AD_InfoColumn_Trl SET IsTranslated='N' WHERE AD_InfoColumn_ID=540134
;

-- 08.04.2016 15:05
-- URL zum Konzept
DELETE FROM AD_Scheduler WHERE AD_Scheduler_ID=550022
;

DELETE FROM AD_Menu WHERE AD_Process_ID=540539;

-- 08.04.2016 15:05
-- URL zum Konzept
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540539
;

-- 08.04.2016 15:05
-- URL zum Konzept
DELETE FROM AD_Process WHERE AD_Process_ID=540539
;

