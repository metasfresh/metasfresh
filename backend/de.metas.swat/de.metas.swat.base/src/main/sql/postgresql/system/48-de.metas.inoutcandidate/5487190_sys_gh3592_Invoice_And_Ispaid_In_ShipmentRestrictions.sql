-- 2018-03-01T11:47:32.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559520,1008,0,19,540845,'N','C_Invoice_ID','(  select i.c_invoice_ID   from c_invoice i  join c_dunning_candidate dc on  i.c_invoice_id = dc.record_id and dc.ad_table_id = get_table_ID(''C_Invoice'')  join c_dunningdoc_line_source dls on dc.c_dunning_candidate_id = dls.c_dunning_candidate_id  join c_dunningdoc_line ddl on dls.c_dunningdoc_line_id = ddl.c_dunningdoc_line_id  join c_dunningdoc dd on ddl.c_dunningdoc_id =  dd.c_dunningdoc_id  join m_shipment_constraint sc on dd.c_dunningdoc_id = sc.sourcedoc_record_id and sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')  where sc.m_shipment_constraint_ID = m_shipment_constraint.m_shipment_constraint_ID  )',TO_TIMESTAMP('2018-03-01 11:47:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Invoice Identifier','de.metas.inoutcandidate',10,'The Invoice Document.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Rechnung',0,0,TO_TIMESTAMP('2018-03-01 11:47:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-03-01T11:47:32.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559520 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-01T11:48:12.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, ColumnSQL='(  select i.c_invoice_ID   from c_invoice i  join c_dunning_candidate dc on  i.c_invoice_id = dc.record_id and dc.ad_table_id = get_table_ID(''C_Invoice'')  join c_dunningdoc_line_source dls on dc.c_dunning_candidate_id = dls.c_dunning_candidate_id  join c_dunningdoc_line ddl on dls.c_dunningdoc_line_id = ddl.c_dunningdoc_line_id  join c_dunningdoc dd on ddl.c_dunningdoc_id =  dd.c_dunningdoc_id  join m_shipment_constraint sc on dd.c_dunningdoc_id = sc.sourcedoc_record_id and sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')  where i.isSoTrx = ''Y'' and sc.m_shipment_constraint_ID = m_shipment_constraint.m_shipment_constraint_ID  )',Updated=TO_TIMESTAMP('2018-03-01 11:48:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559520
;

-- 2018-03-01T11:51:47.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=540101,Updated=TO_TIMESTAMP('2018-03-01 11:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559520
;

-- 2018-03-01T11:52:06.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(  select i.c_invoice_ID   from c_invoice i  join c_dunning_candidate dc on  i.c_invoice_id = dc.record_id and dc.ad_table_id = get_table_ID(''C_Invoice'')  join c_dunningdoc_line_source dls on dc.c_dunning_candidate_id = dls.c_dunning_candidate_id  join c_dunningdoc_line ddl on dls.c_dunningdoc_line_id = ddl.c_dunningdoc_line_id  join c_dunningdoc dd on ddl.c_dunningdoc_id =  dd.c_dunningdoc_id  join m_shipment_constraint sc on dd.c_dunningdoc_id = sc.sourcedoc_record_id and sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')  where sc.m_shipment_constraint_ID = m_shipment_constraint.m_shipment_constraint_ID  )',Updated=TO_TIMESTAMP('2018-03-01 11:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559520
;

-- 2018-03-01T11:56:28.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559521,1402,0,20,540845,'N','IsPaid',' (  select i.IsPaid   from c_invoice i  join c_dunning_candidate dc on  i.c_invoice_id = dc.record_id and dc.ad_table_id = get_table_ID(''C_Invoice'')  join c_dunningdoc_line_source dls on dc.c_dunning_candidate_id = dls.c_dunning_candidate_id  join c_dunningdoc_line ddl on dls.c_dunningdoc_line_id = ddl.c_dunningdoc_line_id  join c_dunningdoc dd on ddl.c_dunningdoc_id =  dd.c_dunningdoc_id  join m_shipment_constraint sc on dd.c_dunningdoc_id = sc.sourcedoc_record_id and sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')  where sc.m_shipment_constraint_ID = m_shipment_constraint.m_shipment_constraint_ID    )',TO_TIMESTAMP('2018-03-01 11:56:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Beleg ist bezahlt','de.metas.inoutcandidate',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Gezahlt',0,0,TO_TIMESTAMP('2018-03-01 11:56:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-03-01T11:56:28.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559521 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-01T11:57:03.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2018-03-01 11:57:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559520
;

-- 2018-03-01T11:57:13.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2018-03-01 11:57:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559521
;

-- 2018-03-01T12:00:58.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559520,563069,0,540882,0,TO_TIMESTAMP('2018-03-01 12:00:58','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier',0,'de.metas.inout','The Invoice Document.',0,'Y','Y','Y','N','N','N','N','N','Rechnung',60,60,0,1,1,TO_TIMESTAMP('2018-03-01 12:00:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-01T12:00:58.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-03-01T12:01:05.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.inoutcandidate',Updated=TO_TIMESTAMP('2018-03-01 12:01:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563069
;

-- 2018-03-01T12:02:03.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559521,563070,0,540882,0,TO_TIMESTAMP('2018-03-01 12:02:03','YYYY-MM-DD HH24:MI:SS'),100,'Der Beleg ist bezahlt',0,'de.metas.inoutcandidate',0,'Y','Y','Y','N','N','N','N','N','Gezahlt',70,70,0,1,1,TO_TIMESTAMP('2018-03-01 12:02:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-01T12:02:03.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-03-01T12:03:00.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,563069,0,540882,551170,541210,'F',TO_TIMESTAMP('2018-03-01 12:02:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Rechnung',30,0,0,TO_TIMESTAMP('2018-03-01 12:02:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-01T12:03:41.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,563070,0,540882,551171,541208,'F',TO_TIMESTAMP('2018-03-01 12:03:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Gezahlt',30,0,0,TO_TIMESTAMP('2018-03-01 12:03:41','YYYY-MM-DD HH24:MI:SS'),100)
;

