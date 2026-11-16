-- Reference from the till journal line to the invoice card settlement that produced it.

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsEncrypted,IsExcludeFromZoomTargets,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593813 /*From ID Server*/,585599,0,30,542439,'C_POS_InvoiceCardSettlement_ID',TO_TIMESTAMP('2026-11-09 11:00:01','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','N','POS Rechnungs-Kartenzahlung','NP',0,TO_TIMESTAMP('2026-11-09 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593813 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(585599)
;
/* DDL */ SELECT public.db_alter_table('C_POS_JournalLine','ALTER TABLE public.C_POS_JournalLine ADD COLUMN C_POS_InvoiceCardSettlement_ID NUMERIC(10)')
;
ALTER TABLE C_POS_JournalLine ADD CONSTRAINT CPOSInvoiceCardSettlement_CPOSJournalLine FOREIGN KEY (C_POS_InvoiceCardSettlement_ID) REFERENCES public.C_POS_InvoiceCardSettlement DEFERRABLE INITIALLY DEFERRED
;
