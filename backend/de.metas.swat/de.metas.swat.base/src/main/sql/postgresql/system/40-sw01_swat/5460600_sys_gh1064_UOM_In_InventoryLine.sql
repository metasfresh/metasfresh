-- 2017-04-24T18:29:13.667
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556504,215,0,30,114,322,'N','C_UOM_ID',TO_TIMESTAMP('2017-04-24 18:29:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Maßeinheit','D',10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Maßeinheit',0,TO_TIMESTAMP('2017-04-24 18:29:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-04-24T18:29:13.678
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556504 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-04-24T18:29:24.491
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('m_inventoryline','ALTER TABLE public.M_InventoryLine ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2017-04-24T18:29:24.734
-- URL zum Konzept
ALTER TABLE M_InventoryLine ADD CONSTRAINT CUOM_MInventoryLine FOREIGN KEY (C_UOM_ID) REFERENCES C_UOM DEFERRABLE INITIALLY DEFERRED
;






-- 2017-04-24T18:41:33.034
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556504,558141,0,683,0,TO_TIMESTAMP('2017-04-24 18:41:32','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',0,'de.metas.swat','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','Y','Y','Y','N','N','N','N','N','Maßeinheit',71,71,0,1,1,TO_TIMESTAMP('2017-04-24 18:41:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-24T18:41:33.038
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558141 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;