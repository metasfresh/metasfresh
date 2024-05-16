-- 2022-02-10T09:39:15.914Z
-- URL zum Konzept
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2022-02-10 10:39:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541987
;

-- 2022-02-10T09:39:39.691Z
-- URL zum Konzept
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2022-02-10 10:39:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=334
;

-- 2022-02-10T09:40:12.904Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,195,543125,TO_TIMESTAMP('2022-02-10 10:40:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','PayPal Extern',TO_TIMESTAMP('2022-02-10 10:40:12','YYYY-MM-DD HH24:MI:SS'),100,'PE','PayPal Extern')
;

-- 2022-02-10T09:40:12.972Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543125 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-02-10T09:40:38.629Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,195,543126,TO_TIMESTAMP('2022-02-10 10:40:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kreditkarte',TO_TIMESTAMP('2022-02-10 10:40:37','YYYY-MM-DD HH24:MI:SS'),100,'CE','Kreditkarte Extern')
;

-- 2022-02-10T09:40:38.695Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543126 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-02-10T09:40:58.628Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Name='Credit Card Extern',Updated=TO_TIMESTAMP('2022-02-10 10:40:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543126
;

-- 2022-02-10T09:41:28.790Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,195,543127,TO_TIMESTAMP('2022-02-10 10:41:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Sofortüberweisung',TO_TIMESTAMP('2022-02-10 10:41:28','YYYY-MM-DD HH24:MI:SS'),100,'BE','Sofortüberweisung')
;

-- 2022-02-10T09:41:28.852Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543127 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-02-10T09:41:46.668Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Name='Instant Bank Transfer',Updated=TO_TIMESTAMP('2022-02-10 10:41:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543127
;

-- 2022-02-10T09:49:47.941Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579213,1143,0,17,195,540270,52033,'PaymentRule',TO_TIMESTAMP('2022-02-10 10:49:46','YYYY-MM-DD HH24:MI:SS'),100,'N','P','Wie die Rechnung bezahlt wird','D',0,1,'Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsweise',0,0,TO_TIMESTAMP('2022-02-10 10:49:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-02-10T09:49:48.012Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579213 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-02-10T09:49:48.191Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1143) 
;

-- 2022-02-10T09:50:04.368Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN PaymentRule CHAR(1) DEFAULT ''P''')
;


-- 2022-02-10T10:11:06.723Z
-- URL zum Konzept
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,579213,1000000,1000000,540050,TO_TIMESTAMP('2022-02-10 11:11:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','COL',TO_TIMESTAMP('2022-02-10 11:11:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-10T10:11:06.896Z
-- URL zum Konzept
INSERT INTO C_Queue_Block (AD_Client_ID,AD_Org_ID,C_Queue_Block_ID,C_Queue_PackageProcessor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,562065,540028,TO_TIMESTAMP('2022-02-10 11:11:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-02-10 11:11:06','YYYY-MM-DD HH24:MI:SS'),100)
;
