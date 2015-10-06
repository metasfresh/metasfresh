-- 30.09.2015 08:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542894,0,'HasOpenOutgoingPayments',TO_TIMESTAMP('2015-09-30 08:53:03','YYYY-MM-DD HH24:MI:SS'),100,NULL,'de.metas.swat','Y','Has Open Outgoing payments','Has Open Outgoing payments',TO_TIMESTAMP('2015-09-30 08:53:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.09.2015 08:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542894 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 30.09.2015 08:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552758,542894,0,20,427,'N','HasOpenOutgoingPayments','(case when exists (select 1 from C_Payment p where p.C_BPartner_ID=C_PaySelectionLine.C_BPartner_ID and p.IsReceipt=''N'' and p.DocStatus IN (''CO'', ''CL'') and p.IsAllocated=''N'') then ''Y'' else ''N'' end)',TO_TIMESTAMP('2015-09-30 08:57:51','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Has Open Outgoing payments',0,TO_TIMESTAMP('2015-09-30 08:57:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 30.09.2015 08:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552758 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 30.09.2015 09:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @OpenAmt/0@<>0 and (''@HasOpenCreditMemos/X@''=''Y'' or ''@HasOpenOutgoingPayments/X@''=''Y'') then (select ad_color_id from ad_color where name = ''Rot'' limit 1) end as ad_color_id',Updated=TO_TIMESTAMP('2015-09-30 09:10:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10641
;

-- 30.09.2015 11:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(case when exists (select 1 from C_Invoice i inner join C_DocType dt on (dt.C_DocType_ID=i.C_DocType_ID) where i.C_BPartner_ID=C_PaySelectionLine.C_BPartner_ID and i.IsSOTrx=C_PaySelectionLine.IsSOTrx and i.IsPaid=''N'' and i.DocStatus in (''CO'', ''CL'') and (dt.DocBaseType in (''APC'', ''ARC'') or i.GrandTotal < 0) ) then ''Y'' else ''N'' end)::char(1)',Updated=TO_TIMESTAMP('2015-09-30 11:20:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552565
;

-- 30.09.2015 11:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(case when exists (select 1 from C_Payment p where p.C_BPartner_ID=C_PaySelectionLine.C_BPartner_ID and p.IsReceipt=''N'' and p.DocStatus IN (''CO'', ''CL'') and p.IsAllocated=''N'') then ''Y'' else ''N'' end)::char(1)',Updated=TO_TIMESTAMP('2015-09-30 11:22:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552758
;
