-- 2024-07-03T10:08:34.761Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583163,0,'desadv_documentno',TO_TIMESTAMP('2024-07-03 12:08:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','desadv_documentno','desadv_documentno',TO_TIMESTAMP('2024-07-03 12:08:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-03T10:08:34.791Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583163 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: EDI_cctop_invoic_v.desadv_documentno
-- 2024-07-03T10:10:00.037Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588662,583163,0,10,540462,'desadv_documentno',TO_TIMESTAMP('2024-07-03 12:09:59','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,100,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'desadv_documentno',0,0,TO_TIMESTAMP('2024-07-03 12:09:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-03T10:10:00.065Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588662 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-03T10:10:00.149Z
/* DDL */  select update_Column_Translation_From_AD_Element(583163) 
;

-- 2024-07-03T10:11:03.112Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588662,0,TO_TIMESTAMP('2024-07-03 12:11:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540220,550699,'Y','N','N','desadv_documentno',510,'E',TO_TIMESTAMP('2024-07-03 12:11:02','YYYY-MM-DD HH24:MI:SS'),100,'desadv_documentno')
;

-- Column: EDI_Desadv.shipment_documentno
-- Column SQL (old): null
-- 2024-07-03T11:29:03.093Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588663,541980,0,10,540644,'shipment_documentno','select string_agg(io.DocumentNo, '','') from m_inout io where io.EDI_Desadv_ID=EDI_Desadv.EDI_Desadv_ID',TO_TIMESTAMP('2024-07-03 13:29:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,512,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'shipment_documentno',0,0,TO_TIMESTAMP('2024-07-03 13:29:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-03T11:29:03.122Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588663 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-03T11:29:03.178Z
/* DDL */  select update_Column_Translation_From_AD_Element(541980) 
;

-- 2024-07-03T11:30:56.513Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,588663,0,540159,540644,TO_TIMESTAMP('2024-07-03 13:30:56','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',551753,551753,319,TO_TIMESTAMP('2024-07-03 13:30:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: EDI_Desadv.DatePromised
-- Column SQL (old): null
-- 2024-07-03T11:33:24.403Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588664,269,0,15,540644,'DatePromised','select o.DatePromised from c_order o where o.EDI_Desadv_ID=EDI_Desadv.EDI_Desadv_ID',TO_TIMESTAMP('2024-07-03 13:33:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Zugesagter Termin für diesen Auftrag','de.metas.esb.edi',0,7,'Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Zugesagter Termin',0,0,TO_TIMESTAMP('2024-07-03 13:33:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-03T11:33:24.431Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588664 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-03T11:33:24.486Z
/* DDL */  select update_Column_Translation_From_AD_Element(269) 
;

-- 2024-07-03T11:34:26.884Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,588664,0,540160,540644,TO_TIMESTAMP('2024-07-03 13:34:26','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',552035,552035,259,TO_TIMESTAMP('2024-07-03 13:34:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-03T11:36:21.700Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588663,0,TO_TIMESTAMP('2024-07-03 13:36:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540405,550700,'Y','N','N','shipment_documentno',360,'E',TO_TIMESTAMP('2024-07-03 13:36:21','YYYY-MM-DD HH24:MI:SS'),100,'shipment_documentno')
;

-- 2024-07-03T11:36:55.763Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588664,0,TO_TIMESTAMP('2024-07-03 13:36:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540405,550701,'Y','N','N','DatePromised',370,'E',TO_TIMESTAMP('2024-07-03 13:36:55','YYYY-MM-DD HH24:MI:SS'),100,'DatePromised')
;

-- 2024-07-03T11:39:13.799Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=3791,Updated=TO_TIMESTAMP('2024-07-03 13:39:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540159
;

-- 2024-07-03T11:39:40.482Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=2182,Updated=TO_TIMESTAMP('2024-07-03 13:39:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540160
;

-- View: EDI_Cctop_INVOIC_v

DROP VIEW IF EXISTS EDI_Cctop_INVOIC_v;
CREATE OR REPLACE VIEW EDI_Cctop_INVOIC_v AS
SELECT
    i.C_Invoice_ID AS EDI_Cctop_INVOIC_v_ID
     , i.C_Invoice_ID
     , i.C_Order_ID
     , REGEXP_REPLACE(i.DocumentNo, '\s+$', '') AS Invoice_DocumentNo
     , i.DateInvoiced
     , (CASE
            WHEN REGEXP_REPLACE(i.POReference::TEXT, '\s+$', '') <> ''::TEXT AND i.POReference IS NOT NULL /* task 09182: if there is a POReference, then export it */
                THEN REGEXP_REPLACE(i.POReference, '\s+$', '')
            ELSE NULL::CHARACTER VARYING
    END) AS POReference
     , (CASE
            WHEN COALESCE(i.DateOrdered, o.DateOrdered) IS NOT NULL /* task 09182: if there is an orderDate, then export it */
                THEN COALESCE(i.DateOrdered, o.DateOrdered)
            ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
    END) AS DateOrdered
     , (CASE dt.DocBaseType
            WHEN 'ARI'::BPChar THEN (CASE
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='' THEN '380'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='AQ' THEN '383'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='AP' THEN '84'::TEXT
                                         ELSE 'ERROR EAN_DocType'::TEXT
                END)
            WHEN 'ARC'::BPChar THEN (CASE

                /* CQ => "GS - Lieferdifferenz"; CS => "GS - Retoure" */
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) IN ('CQ','CS') THEN '381'
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='CR' THEN '83'::TEXT
                                         ELSE 'ERROR EAN_DocType'::TEXT
                END)
            ELSE 'ERROR EAN_DocType'::TEXT
    END) AS EANCOM_DocType
     , i.GrandTotal
     , i.TotalLines
    /* IF docSubType is CS, the we don't reference the original shipment*/
     , CASE WHEN dt.DocSubType='CS' THEN NULL ELSE shipment.MovementDate END AS MovementDate
     , CASE WHEN dt.DocSubType='CS' THEN NULL ELSE shipment.DocumentNo END AS Shipment_DocumentNo
     , t.TotalVAT
     , t.TotalTaxBaseAmt
     , COALESCE(rbp.EdiInvoicRecipientGLN, rl.GLN) AS ReceiverGLN
     , rl.C_BPartner_Location_ID
     , (
    SELECT DISTINCT ON (REGEXP_REPLACE(sl.GLN, '\s+$', ''))
        REGEXP_REPLACE(sl.GLN, '\s+$', '') as GLN
    FROM C_BPartner_Location sl
    WHERE true
      AND sl.C_BPartner_ID = sp.C_BPartner_ID
      AND sl.IsRemitTo = 'Y'::BPChar
      AND sl.GLN IS NOT NULL
      AND sl.IsActive = 'Y'::BPChar
) AS SenderGLN
     , REGEXP_REPLACE(sp.VATaxId, '\s+$', '') as VATaxId
     , c.ISO_Code
     , REGEXP_REPLACE(i.CreditMemoReason, '\s+$', '') as CreditMemoReason
     , (
    SELECT
        REGEXP_REPLACE(Name, '\s+$', '') as Name
    FROM AD_Ref_List
    WHERE AD_Reference_ID=540014 -- C_CreditMemo_Reason
      AND Value=i.CreditMemoReason
) AS CreditMemoReasonText
     , (select CASE
                   WHEN array_length(array_agg(DISTINCT ol.invoicableqtybasedon), 1) = 1
                       THEN (array_agg(DISTINCT ol.invoicableqtybasedon))[1]
                   ELSE NULL
                   END
        from c_orderline ol
        where ol.c_order_id=o.c_order_id) as invoicableqtybasedon
     , cc.CountryCode
     , cc.CountryCode_3Digit
     , cc.CountryCode as AD_Language
     , i.AD_Client_ID , i.AD_Org_ID, i.Created, i.CreatedBy, i.Updated, i.UpdatedBy, i.IsActive
     , (select string_agg(edi.DocumentNo, ',') from c_invoiceline icl
                                                        inner join c_invoice_line_alloc inalloc on icl.c_invoiceline_id = inalloc.c_invoiceline_id
                                                        inner join C_InvoiceCandidate_InOutLine candinout
                                                                   on inalloc.c_invoice_candidate_id = candinout.c_invoice_candidate_id
                                                        inner join M_InOutLine inoutline on candinout.m_inoutline_id = inoutline.m_inoutline_id
                                                        inner join m_inout inout on inoutline.m_inout_id = inout.m_inout_id
                                                        inner join EDI_Desadv edi on inout.EDI_Desadv_ID = edi.EDI_Desadv_ID
        where icl.c_invoice_id = i.c_invoice_id) as EDIDesadvDocumentNo
FROM C_Invoice i
         LEFT JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypetarget_ID
         LEFT JOIN C_Order o ON o.C_Order_ID=i.C_Order_ID
         LEFT JOIN EDI_Desadv desadv ON desadv.EDI_Desadv_ID = o.EDI_Desadv_ID -- note that we prefer the EDI_Desadv over M_InOut. there might be multiple InOuts, all with the same POReference and the same EDI_Desadv_ID
         LEFT JOIN LATERAL (
    SELECT
        io.DocumentNo, io.MovementDate
    FROM M_InOut io
    WHERE io.C_Order_ID=o.C_Order_ID AND io.DocStatus IN ('CO', 'CL')
    ORDER BY io.Created
    LIMIT 1
    ) shipment ON true -- for the case of missing EDI_Desadv, we still get the first M_InOut; DESADV can be switched off for individual C_BPartners
         LEFT JOIN C_BPartner rbp ON rbp.C_BPartner_ID = i.C_BPartner_ID
         LEFT JOIN C_BPartner_Location rl ON rl.C_BPartner_Location_ID = i.C_BPartner_Location_ID
         LEFT JOIN C_Location l ON l.C_Location_ID = rl.C_Location_ID
         LEFT JOIN C_Currency c ON c.C_Currency_ID = i.C_Currency_ID
         LEFT JOIN C_Country cc ON cc.C_Country_ID = l.C_Country_ID
         LEFT JOIN (
    SELECT
        C_InvoiceTax.C_Invoice_ID
         , SUM(C_InvoiceTax.TaxAmt) AS TotalVAT
         , SUM(C_InvoiceTax.TaxBaseAmt) AS TotalTaxBaseAmt
    FROM C_InvoiceTax
    GROUP BY C_InvoiceTax.C_Invoice_ID
) t ON t.C_Invoice_ID = i.C_Invoice_ID
         LEFT JOIN C_BPartner sp ON sp.AD_OrgBP_ID = i.AD_Org_ID
;
