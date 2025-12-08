-- 2023-06-22T09:53:07.338Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582465,0,'EDIDesadvDocumentNo',TO_TIMESTAMP('2023-06-22 12:53:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','EDI Desadv-Nr.','EDI Desadv-Nr.',TO_TIMESTAMP('2023-06-22 12:53:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-22T09:53:07.347Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582465 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


-- Column: C_Invoice.EDIDesadvDocumentNo
-- Column SQL (old): null
-- Column: C_Invoice.EDIDesadvDocumentNo
-- 2023-06-22T11:10:52.948Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586846,582465,0,10,318,'EDIDesadvDocumentNo','1=1',TO_TIMESTAMP('2023-06-22 14:10:52','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'EDI Desadv-Nr.',0,0,TO_TIMESTAMP('2023-06-22 14:10:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-22T11:10:52.956Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586846 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-22T11:10:52.965Z
/* DDL */  select update_Column_Translation_From_AD_Element(582465)
;

-- Column: C_Invoice.EDIDesadvDocumentNo
-- Column SQL (old): 1=1
-- Column: C_Invoice.EDIDesadvDocumentNo
-- Column SQL (old): 1=1
-- 2023-06-22T13:17:01.395Z
UPDATE AD_Column SET ColumnSQL='(select string_agg(edi.DocumentNo, '','')         from c_invoiceline icl                  inner join c_invoice_line_alloc inalloc on icl.c_invoiceline_id = inalloc.c_invoiceline_id                  inner join C_InvoiceCandidate_InOutLine candinout                             on inalloc.c_invoice_candidate_id = candinout.c_invoice_candidate_id                  inner join M_InOutLine inoutline on candinout.m_inoutline_id = inoutline.m_inoutline_id                  inner join m_inout inout on inoutline.m_inout_id = inout.m_inout_id                  inner join EDI_Desadv edi on inout.EDI_Desadv_ID = edi.EDI_Desadv_ID         where icl.C_Invoice_ID = C_Invoice.C_Invoice_ID)',Updated=TO_TIMESTAMP('2023-06-22 16:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586846
;



-- Column: EDI_cctop_invoic_v.EDIDesadvDocumentNo
-- Column: EDI_cctop_invoic_v.EDIDesadvDocumentNo
-- 2023-06-22T10:21:44.777Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586845,582465,0,10,540462,'EDIDesadvDocumentNo',TO_TIMESTAMP('2023-06-22 13:21:44','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'EDI Desadv-Nr.',0,0,TO_TIMESTAMP('2023-06-22 13:21:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-22T10:21:44.781Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586845 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-22T10:21:44.787Z
/* DDL */  select update_Column_Translation_From_AD_Element(582465) 
;


-- 2023-06-22T10:22:39.669Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,586845,0,TO_TIMESTAMP('2023-06-22 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'D',540220,550593,'Y','N','N','EDIDesadvDocumentNo',510,'E',TO_TIMESTAMP('2023-06-22 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'EDIDesadvDocumentNo')
;

-- 2023-06-22T10:23:29.553Z
UPDATE EXP_FormatLine SET EntityType='de.metas.esb', Position=150,Updated=TO_TIMESTAMP('2023-06-22 13:23:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550593
;



