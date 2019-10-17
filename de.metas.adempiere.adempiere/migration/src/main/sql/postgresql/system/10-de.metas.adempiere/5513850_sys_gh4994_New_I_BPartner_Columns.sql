



-- 2019-02-26T16:28:50.610
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,IsCalculated,IsRangeFilter,IsShowFilterIncrementButtons,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType,ColumnName,Description,IsAutoApplyValidationRule) VALUES (14,2000,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-02-26 16:28:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-02-26 16:28:50','YYYY-MM-DD HH24:MI:SS'),100,'N','N',533,564256,'N','N','N','N','N','N',0,0,'Memo_Delivery','N','N','N',542819,'N','N','de.metas.swat','Memo_Delivery','Memo Lieferung','N')
;

-- 2019-02-26T16:28:50.615
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564256 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;






-- 2019-02-26T13:44:27.590
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,IsCalculated,IsRangeFilter,IsShowFilterIncrementButtons,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType,ColumnName,Description,IsAutoApplyValidationRule) VALUES (10,250,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-02-26 13:44:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-02-26 13:44:27','YYYY-MM-DD HH24:MI:SS'),100,'N','N',533,564250,'N','N','N','N','N','N',0,0,'Memo_Invoicing','N','N','N',542820,'N','N','de.metas.swat','Memo_Invoicing','Memo Abrechnung','N')
;

-- 2019-02-26T13:44:27.606
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564250 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;





-- 2019-02-26T16:23:12.996
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,IsCalculated,IsRangeFilter,IsShowFilterIncrementButtons,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType,ColumnName,IsAutoApplyValidationRule) VALUES (10,250,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-02-26 16:23:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-02-26 16:23:12','YYYY-MM-DD HH24:MI:SS'),100,'N','N',533,564252,'N','N','N','N','N','N',0,0,'Postfach','N','N','N',55445,'N','N','D','POBox','N')
;

-- 2019-02-26T16:23:12.999
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564252 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;





-- 2019-02-26T16:24:43.416
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,IsCalculated,IsRangeFilter,IsShowFilterIncrementButtons,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType,ColumnName,Description,IsAutoApplyValidationRule) VALUES (10,250,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-02-26 16:24:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-02-26 16:24:43','YYYY-MM-DD HH24:MI:SS'),100,'N','N',533,564253,'N','N','N','N','N','N',0,0,'Land','N','N','N',2585,'N','N','D','CountryName','Land','N')
;

-- 2019-02-26T16:24:43.420
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564253 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 2019-02-26T17:36:44.938
-- #298 changing anz. stellen
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 17:36:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564256
;

-- 2019-02-26T17:36:53.128
-- #298 changing anz. stellen
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 17:36:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564250
;




-- 2019-02-27T13:36:12.829
-- #298 changing anz. stellen
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-27 13:36:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552452
;




-- 2019-03-01T11:42:02.080
-- #298 changing anz. stellen
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-03-01 11:42:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552451
;


-- 2019-03-01T11:49:42.324
-- #298 changing anz. stellen
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-03-01 11:49:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558551
;






