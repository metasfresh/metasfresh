
-- 2017-04-13T15:08:35.505
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556484,1026,0,19,322,'N','M_InOutLine_ID',TO_TIMESTAMP('2017-04-13 15:08:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Position auf Versand- oder Wareneingangsbeleg','D',10,'"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Versand-/Wareneingangsposition',0,TO_TIMESTAMP('2017-04-13 15:08:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-04-13T15:08:35.511
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556484 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-04-13T15:11:40.119
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('m_inventoryline','ALTER TABLE public.M_InventoryLine ADD COLUMN M_InOutLine_ID NUMERIC(10)')
;

-- 2017-04-13T15:11:40.354
-- URL zum Konzept
ALTER TABLE M_InventoryLine ADD CONSTRAINT MInOutLine_MInventoryLine FOREIGN KEY (M_InOutLine_ID) REFERENCES M_InOutLine DEFERRABLE INITIALLY DEFERRED
;

