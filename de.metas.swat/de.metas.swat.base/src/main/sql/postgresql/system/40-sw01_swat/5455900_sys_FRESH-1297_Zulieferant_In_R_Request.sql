-- 01.02.2017 17:51
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543273,0,'C_BP_Vendor',TO_TIMESTAMP('2017-02-01 17:51:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zulieferant','Zulieferant',TO_TIMESTAMP('2017-02-01 17:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.02.2017 17:51
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543273 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 01.02.2017 17:51
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556050,543273,0,30,417,'N','C_BP_Vendor',TO_TIMESTAMP('2017-02-01 17:51:51','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Zulieferant',0,TO_TIMESTAMP('2017-02-01 17:51:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 01.02.2017 17:51
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556050 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 01.02.2017 17:53
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540353,'C_BPartner.IsVendor = ''Y''',TO_TIMESTAMP('2017-02-01 17:53:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_BP_Vendor','S',TO_TIMESTAMP('2017-02-01 17:53:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.02.2017 17:53
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540353,Updated=TO_TIMESTAMP('2017-02-01 17:53:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556050
;

-- 01.02.2017 17:54
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=138,Updated=TO_TIMESTAMP('2017-02-01 17:54:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556050
;

-- 01.02.2017 17:56
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556050,557542,0,402,0,TO_TIMESTAMP('2017-02-01 17:56:43','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Zulieferant',55,55,0,1,1,TO_TIMESTAMP('2017-02-01 17:56:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.02.2017 17:56
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557542 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 01.02.2017 17:58
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556050,557543,0,344,0,TO_TIMESTAMP('2017-02-01 17:58:24','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Zulieferant',55,55,0,1,1,TO_TIMESTAMP('2017-02-01 17:58:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.02.2017 17:58
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557543 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 01.02.2017 18:00
-- URL zum Konzept
UPDATE AD_Column SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2017-02-01 18:00:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556050
;

-- 01.02.2017 18:18
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='C_BPartner.IsVendor = ''Y'' ',Updated=TO_TIMESTAMP('2017-02-01 18:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540353
;

-- 01.02.2017 18:25
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='C_BP_Vendor_ID',Updated=TO_TIMESTAMP('2017-02-01 18:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543273
;

-- 01.02.2017 18:25
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_BP_Vendor_ID', Name='Zulieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=543273
;

-- 01.02.2017 18:25
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BP_Vendor_ID', Name='Zulieferant', Description=NULL, Help=NULL, AD_Element_ID=543273 WHERE UPPER(ColumnName)='C_BP_VENDOR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 01.02.2017 18:25
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BP_Vendor_ID', Name='Zulieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=543273 AND IsCentrallyMaintained='Y'
;

-- 01.02.2017 18:26
-- URL zum Konzept
ALTER TABLE public.R_Request ADD C_BP_Vendor_ID NUMERIC(10) DEFAULT NULL 
;

-- 01.02.2017 18:26
-- URL zum Konzept
ALTER TABLE R_Request ADD CONSTRAINT CBPVendor_RRequest FOREIGN KEY (C_BP_Vendor_ID) REFERENCES C_BPartner DEFERRABLE INITIALLY DEFERRED
;

