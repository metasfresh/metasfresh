
-- 11.03.2016 16:23
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554225,459,0,30,540751,'N','M_Warehouse_ID',TO_TIMESTAMP('2016-03-11 16:23:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Lager oder Ort für Dienstleistung','de.metas.procurement',10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Lager',0,TO_TIMESTAMP('2016-03-11 16:23:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 11.03.2016 16:23
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554225 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 11.03.2016 16:25
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540326,'M_warehouse.IsPOWarehouse=''Y''',TO_TIMESTAMP('2016-03-11 16:25:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','M_Warehouse for Purchase Orders','S',TO_TIMESTAMP('2016-03-11 16:25:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.03.2016 16:26
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540326, IsAutocomplete='Y',Updated=TO_TIMESTAMP('2016-03-11 16:26:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554225
;

-- 11.03.2016 16:27
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='M_Warehouse.IsPOWarehouse=''Y''',Updated=TO_TIMESTAMP('2016-03-11 16:27:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540326
;


-- 11.03.2016 16:29
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-11 16:29:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554225
;

-- 11.03.2016 16:30
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554225,556769,0,540731,0,TO_TIMESTAMP('2016-03-11 16:30:20','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',0,'de.metas.procurement','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','Y','Y','Y','N','N','N','N','Y','Lager',80,80,0,1,1,TO_TIMESTAMP('2016-03-11 16:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.03.2016 16:30
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556769 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;


COMMIT;


-- 11.03.2016 16:29
-- URL zum Konzept
ALTER TABLE PMM_Product ADD M_Warehouse_ID NUMERIC(10)
;

-- 11.03.2016 16:46
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_product','M_Warehouse_ID','NUMERIC(10)',null,'NULL')
;

-- 11.03.2016 16:46
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_product','M_Warehouse_ID',null,'NULL',null)
;

