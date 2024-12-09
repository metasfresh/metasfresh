-- 2022-08-20T15:30:45.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) 
VALUES (0,584135,1308,0,17,234,542190,'Posted',TO_TIMESTAMP('2022-08-20 18:30:45','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Buchungsstatus','D',0,1,'Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','Buchungsstatus',0,0,TO_TIMESTAMP('2022-08-20 18:30:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-20T15:30:45.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584135 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-20T15:30:45.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(1308) 
;

-- 2022-08-20T15:30:45.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) 
VALUES (0,584136,577755,0,30,540991,542190,'PostingError_Issue_ID',TO_TIMESTAMP('2022-08-20 18:30:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','D',0,10,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Verbuchungsfehler',0,0,TO_TIMESTAMP('2022-08-20 18:30:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-20T15:30:45.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584136 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-20T15:30:45.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577755) 
;




















-- 2022-08-20T15:43:15.930Z
INSERT INTO t_alter_column values('m_costrevaluation','DateAcct','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2022-08-20T15:43:15.955Z
INSERT INTO t_alter_column values('m_costrevaluation','DateAcct',null,'NOT NULL',null)
;

-- 2022-08-20T15:43:48.808Z
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN Posted CHAR(1) DEFAULT ''N'' NOT NULL')
;

-- 2022-08-20T15:43:52.078Z
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN PostingError_Issue_ID NUMERIC(10)')
;

-- 2022-08-20T15:44:01.670Z
INSERT INTO t_alter_column values('m_costrevaluation','Processing','CHAR(1)',null,null)
;

-- Name: C_DocType for M_CostRevaluation
-- 2022-08-20T15:44:43.998Z
UPDATE AD_Reference SET Name='C_DocType for M_CostRevaluation',Updated=TO_TIMESTAMP('2022-08-20 18:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541612
;

-- 2022-08-20T15:44:49.511Z
UPDATE AD_Reference_Trl SET Name='C_DocType for M_CostRevaluation',Updated=TO_TIMESTAMP('2022-08-20 18:44:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541612
;

-- 2022-08-20T15:44:52.970Z
UPDATE AD_Reference_Trl SET Name='C_DocType for M_CostRevaluation',Updated=TO_TIMESTAMP('2022-08-20 18:44:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541612
;

-- 2022-08-20T15:44:54.954Z
UPDATE AD_Reference_Trl SET Name='C_DocType for M_CostRevaluation',Updated=TO_TIMESTAMP('2022-08-20 18:44:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541612
;

