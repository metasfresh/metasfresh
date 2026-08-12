



-- 2017-04-24T18:36:14.701
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556506,542132,0,30,540500,322,'N','M_HU_PI_Item_Product_ID',TO_TIMESTAMP('2017-04-24 18:36:14','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Packvorschrift-Produkt Zuordnung',0,TO_TIMESTAMP('2017-04-24 18:36:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-04-24T18:36:14.704
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556506 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-04-24T18:36:16.496
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('m_inventoryline','ALTER TABLE public.M_InventoryLine ADD COLUMN M_HU_PI_Item_Product_ID NUMERIC(10)')
;

-- 2017-04-24T18:36:16.598
-- URL zum Konzept
ALTER TABLE M_InventoryLine ADD CONSTRAINT MHUPIItemProduct_MInventoryLin FOREIGN KEY (M_HU_PI_Item_Product_ID) REFERENCES M_HU_PI_Item_Product DEFERRABLE INITIALLY DEFERRED
;




-- 2017-04-24T18:42:03.858
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556506,558142,0,683,0,TO_TIMESTAMP('2017-04-24 18:42:03','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.handlingunits',0,'Y','Y','Y','Y','N','N','N','N','Y','Packvorschrift-Produkt Zuordnung',72,72,0,1,1,TO_TIMESTAMP('2017-04-24 18:42:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-24T18:42:03.861
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558142 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;




