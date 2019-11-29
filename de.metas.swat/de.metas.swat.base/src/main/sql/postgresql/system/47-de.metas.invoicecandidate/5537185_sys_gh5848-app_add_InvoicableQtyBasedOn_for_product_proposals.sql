
drop view if exists C_BPartner_Product_Stats_Invoice_Online_V;
create or replace view C_BPartner_Product_Stats_Invoice_Online_V as
select
	i.C_BPartner_ID,
	i.M_Product_ID,
	i.IsSOTrx,
	i.DateInvoiced,
	i.C_Currency_ID,
	i.PriceActual,
    i.InvoicableQtyBasedOn,
	--
	i.C_Invoice_ID,
	i.C_InvoiceLine_ID,
	--
	i.AD_Client_ID, i.AD_Org_ID, i.Created, i.CreatedBy, i.Updated, i.UpdatedBy, i.IsActive
from (
	select 
		i.C_BPartner_ID,
		i.IsSOTrx,
		i.DateInvoiced,
		i.C_Currency_ID,
		i.C_Invoice_ID,
		--
		il.M_Product_ID,
		il.C_InvoiceLine_ID,
		il.PriceActual,
		il.InvoicableQtyBasedOn,
		--
		row_number()
			over (
				partition by i.C_BPartner_ID, i.IsSOTrx, il.M_Product_ID
				order by i.DateInvoiced desc, i.C_Invoice_ID desc, il.PriceActual desc
			) as rownum,
		--
    	i.AD_Client_ID, 0 as AD_Org_ID, i.Created, i.CreatedBy, i.Updated, i.UpdatedBy, i.IsActive
	from C_Invoice i
	inner join C_InvoiceLine il on il.C_Invoice_ID=i.C_Invoice_ID and il.M_Product_ID is not null
	where i.DocStatus in ('CO', 'CL')
) i
where rownum=1
;

-- 2019-11-28T12:58:47.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569684,576948,0,17,541023,333,'InvoicableQtyBasedOn',TO_TIMESTAMP('2019-11-28 13:58:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Abr. Menge basiert auf',0,0,TO_TIMESTAMP('2019-11-28 13:58:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-11-28T12:58:47.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569684 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-28T12:58:47.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576948) 
;

-- 2019-11-28T12:58:48.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN InvoicableQtyBasedOn VARCHAR(10)')
;

-- 2019-11-28T12:59:26.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=11,Updated=TO_TIMESTAMP('2019-11-28 13:59:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569684
;

-- 2019-11-28T12:59:27.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoiceline','InvoicableQtyBasedOn','VARCHAR(11)',null,null)
;

-- 2019-11-28T13:00:40.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569685,576948,0,17,541023,541177,'InvoicableQtyBasedOn',TO_TIMESTAMP('2019-11-28 14:00:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','D',11,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Abr. Menge basiert auf',0,0,TO_TIMESTAMP('2019-11-28 14:00:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-11-28T13:00:40.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569685 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-28T13:00:40.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576948) 
;




