-- 2017-07-06T15:50:36.557
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557008,542255,0,20,322,'N','IsInvoiceCandidate',TO_TIMESTAMP('2017-07-06 15:50:36','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.invoicecandidate',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','von Rechnungsdispo verarbeitet',0,TO_TIMESTAMP('2017-07-06 15:50:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-07-06T15:50:36.587
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557008 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-07T12:00:46.978
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('m_inventoryline','ALTER TABLE public.M_InventoryLine ADD COLUMN IsInvoiceCandidate CHAR(1) DEFAULT ''N'' CHECK (IsInvoiceCandidate IN (''Y'',''N'')) NOT NULL')
;

