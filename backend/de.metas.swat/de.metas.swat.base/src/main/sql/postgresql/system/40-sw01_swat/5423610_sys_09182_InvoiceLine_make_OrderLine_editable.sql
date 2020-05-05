
-- 11.08.2015 11:18
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2015-08-11 11:18:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2987
;

-- 11.08.2015 11:21
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2015-08-11 11:21:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3837
;

-- 11.08.2015 11:24
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=22,Updated=TO_TIMESTAMP('2015-08-11 11:24:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6566
;


-- 11.08.2015 13:12
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552628,558,0,30,333,'N','C_Order_ID',TO_TIMESTAMP('2015-08-11 13:12:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftrag','de.metas.esb.edi',10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Auftrag',0,TO_TIMESTAMP('2015-08-11 13:12:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 11.08.2015 13:12
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552628 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 11.08.2015 13:12
-- URL zum Konzept
ALTER TABLE C_InvoiceLine ADD C_Order_ID NUMERIC(10) DEFAULT NULL 
;

-- 11.08.2015 13:15
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542873,0,'IsOrderLineReadOnly',TO_TIMESTAMP('2015-08-11 13:15:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IsOrderLineReadOnly','IsOrderLineReadOnly',TO_TIMESTAMP('2015-08-11 13:15:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.08.2015 13:15
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542873 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 11.08.2015 13:15
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552629,542873,0,20,333,'N','IsOrderLineReadOnly',TO_TIMESTAMP('2015-08-11 13:15:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','IsOrderLineReadOnly',0,TO_TIMESTAMP('2015-08-11 13:15:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 11.08.2015 13:15
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552629 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 11.08.2015 13:15
-- URL zum Konzept
ALTER TABLE C_InvoiceLine ADD IsOrderLineReadOnly CHAR(1) DEFAULT 'Y' CHECK (IsOrderLineReadOnly IN ('Y','N')) NOT NULL
;

-- 11.08.2015 13:16
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@IsOrderLineReadOnly@=Y',Updated=TO_TIMESTAMP('2015-08-11 13:16:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3837
;

-- 11.08.2015 13:16
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.swat', ReadOnlyLogic='@IsOrderLineReadOnly@=Y',Updated=TO_TIMESTAMP('2015-08-11 13:16:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552628
;

-- 11.08.2015 13:16
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-08-11 13:16:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3837
;

-- 11.08.2015 13:18
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552628,556241,0,270,0,TO_TIMESTAMP('2015-08-11 13:18:32','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',0,'@IsOrderLineReadOnly@=N','de.metas.swat','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.',0,'Y','Y','Y','N','N','N','N','N','N','Auftrag',12,12,0,TO_TIMESTAMP('2015-08-11 13:18:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.08.2015 13:18
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556241 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 11.08.2015 13:18
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsOrderLineReadOnly@=N', EntityType='de.metas.swat', IsDisplayed='Y', SeqNo=14,Updated=TO_TIMESTAMP('2015-08-11 13:18:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2987
;

-- 11.08.2015 13:19
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=19, AD_Val_Rule_ID=203,Updated=TO_TIMESTAMP('2015-08-11 13:19:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3837
;

-- 11.08.2015 13:19
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-08-11 13:19:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2987
;

