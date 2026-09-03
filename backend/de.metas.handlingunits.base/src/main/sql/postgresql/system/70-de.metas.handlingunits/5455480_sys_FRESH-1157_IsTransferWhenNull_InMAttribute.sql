-- 20.01.2017 17:39
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543272,0,'IsTransferWhenNull',TO_TIMESTAMP('2017-01-20 17:39:42','YYYY-MM-DD HH24:MI:SS'),100,'U','Transfer the attribute from Issue to Receipt even if there are other boxes without the attribute.','Y','IsTransferWhenNull','IsTransferWhenNull',TO_TIMESTAMP('2017-01-20 17:39:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 20.01.2017 17:39
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543272 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 20.01.2017 17:39
-- URL zum Konzept
UPDATE AD_Element SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2017-01-20 17:39:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543272
;

-- 20.01.2017 17:40
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556047,543272,0,20,562,'N','IsTransferWhenNull',TO_TIMESTAMP('2017-01-20 17:40:14','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',1,'Transfer the attribute from Issue to Receipt even if there are other boxes without the attribute.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','IsTransferWhenNull',0,TO_TIMESTAMP('2017-01-20 17:40:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 20.01.2017 17:40
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556047 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 20.01.2017 17:40
-- URL zum Konzept
ALTER TABLE public.M_Attribute ADD IsTransferWhenNull CHAR(1) DEFAULT 'N' CHECK (IsTransferWhenNull IN ('Y','N')) NOT NULL
;

-- 20.01.2017 18:07
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556047,557503,0,462,0,TO_TIMESTAMP('2017-01-20 18:07:31','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.handlingunits','Transfer the attribute from Issue to Receipt even if there are other boxes without the attribute.',0,'Y','Y','Y','Y','N','N','N','N','N','IsTransferWhenNull',137,137,0,1,1,TO_TIMESTAMP('2017-01-20 18:07:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 20.01.2017 18:07
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557503 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

