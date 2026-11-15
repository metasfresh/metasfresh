-- At most one blocking (open) card settlement per invoice; violation shows a translated message via AD_Index_Table.

CREATE UNIQUE INDEX c_pos_invoicecardsettlement_blocking_uq ON C_POS_InvoiceCardSettlement (C_Invoice_ID) WHERE Status IN ('PE','UN','AP','BE') AND IsAbandoned='N'
;
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause,ErrorMsg) VALUES (0,540879 /*From ID Server*/,0,542659,TO_TIMESTAMP('2026-11-09 11:20:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Y','c_pos_invoicecardsettlement_blocking_uq','N',TO_TIMESTAMP('2026-11-09 11:20:02','YYYY-MM-DD HH24:MI:SS'),100,'Status IN (''PE'',''UN'',''AP'',''BE'') AND IsAbandoned=''N''','Für diese Rechnung ist noch eine Kartenzahlung offen.')
;
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540879 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;
UPDATE AD_Index_Table_Trl SET ErrorMsg='A card payment for this invoice is still open.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-09 11:20:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Index_Table_ID=540879 AND AD_Language='en_US'
;
UPDATE AD_Index_Table_Trl SET ErrorMsg='Für diese Rechnung ist noch eine Kartenzahlung offen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-09 11:20:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Index_Table_ID=540879 AND AD_Language='de_CH'
;
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,593799,541558 /*From ID Server*/,540879,0,TO_TIMESTAMP('2026-11-09 11:20:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y',10,TO_TIMESTAMP('2026-11-09 11:20:06','YYYY-MM-DD HH24:MI:SS'),100)
;
