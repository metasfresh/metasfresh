-- 2021-03-18T11:58:54.599Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541287,TO_TIMESTAMP('2021-03-18 12:58:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','ESR_ype',TO_TIMESTAMP('2021-03-18 12:58:49','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-03-18T11:58:54.603Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541287 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-03-18T11:59:14.695Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541287,542348,TO_TIMESTAMP('2021-03-18 12:59:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','QRR',TO_TIMESTAMP('2021-03-18 12:59:14','YYYY-MM-DD HH24:MI:SS'),100,'QRR','QRR')
;

-- 2021-03-18T11:59:14.698Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542348 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-18T11:59:53.223Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541287,542349,TO_TIMESTAMP('2021-03-18 12:59:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','ESR ',TO_TIMESTAMP('2021-03-18 12:59:48','YYYY-MM-DD HH24:MI:SS'),100,'ESR','ISR Reference')
;

-- 2021-03-18T11:59:53.225Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542349 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-18T12:00:01.090Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='ESR_Type',Updated=TO_TIMESTAMP('2021-03-18 13:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541287
;

-- 2021-03-18T12:00:17.371Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573238,600,0,17,541287,540410,'Type',TO_TIMESTAMP('2021-03-18 13:00:17','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.payment.esr',0,15,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Art','NP',0,0,TO_TIMESTAMP('2021-03-18 13:00:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-18T12:00:17.375Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573238 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-18T12:00:17.407Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(600) 
;

-- 2021-03-18T12:01:39.735Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='''ISR Reference''', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-03-18 13:01:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573238
;

-- 2021-03-18T12:02:12.656Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('ESR_ImportLine','ALTER TABLE public.ESR_ImportLine ADD COLUMN Type VARCHAR(15) DEFAULT ''ISR Reference'' NOT NULL')
;


-- 2021-03-18T15:21:06.272Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545027,0,TO_TIMESTAMP('2021-03-18 16:21:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','Unable to process file because it contains both QRR and ESR transactions. Please create a separate file for each transaction type.','I',TO_TIMESTAMP('2021-03-18 16:21:01','YYYY-MM-DD HH24:MI:SS'),100,'ESR_CAMT54_MultipleTransactionsTypes')
;

-- 2021-03-18T15:21:06.276Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545027 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-03-18T15:21:20.260Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Die Datei kann nicht verarbeitet werden, da sie sowohl QRR- als auch ESR-Transaktionen enthĂ¤lt. Bitte pro Transaktionstyp eine eigene Datei erstellen.',Updated=TO_TIMESTAMP('2021-03-18 16:21:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545027
;

-- 2021-03-18T15:21:24.071Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-18 16:21:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545027
;


UPDATE ad_ref_list SET value = 'ISR Reference', valuename='ESR' WHERE ad_ref_list_id = 542349;

