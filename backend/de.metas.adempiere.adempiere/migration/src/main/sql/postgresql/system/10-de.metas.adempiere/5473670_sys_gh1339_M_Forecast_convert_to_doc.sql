-- 2017-10-04T16:31:03.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557362,287,0,28,135,720,'DocAction',TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'CO','Der zukünftige Status des Belegs','D',2,'You find the current status in the Document Status field. The options are listed in a popup','Y','N','N','N','N','Y','N','N','N','N','Y','Belegverarbeitung',0,TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-10-04T16:31:03.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557362 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-04T16:31:03.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557363,289,0,17,131,720,'DocStatus',TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'DR','The current status of the document','D',2,'The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','N','N','Y','N','N','N','N','Y','Belegstatus',0,TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-10-04T16:31:03.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557363 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-04T16:31:03.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557364,1047,0,20,720,'Processed',TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','D',1,'Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','Y','N','N','N','N','N','Verarbeitet',0,TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-10-04T16:31:03.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557364 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-04T16:31:03.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Workflow_ID,Author,Cost,Created,CreatedBy,DocumentNo,Duration,DurationUnit,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsValid,Name,PublishStatus,Updated,UpdatedBy,Value,Version,WaitingTime,WorkflowType,WorkingTime) VALUES ('1',0,0,720,540100,'metasfresh ERP',0,TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'10000010',1,'D','D','Y','N','N','N','Process_M_Forecast','R',TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'Process_M_Forecast',0,0,'P',0)
;

-- 2017-10-04T16:31:03.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Workflow_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Workflow_ID=540100 AND NOT EXISTS (SELECT 1 FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2017-10-04T16:31:03.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('Z',0,0,540211,540100,0,TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)',0,0,'D','Y','Y','X','(Start)','X',TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'(Start)',0,0,0)
;

-- 2017-10-04T16:31:03.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540211 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2017-10-04T16:31:03.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Workflow SET AD_WF_Node_ID=540211, IsValid='Y',Updated=TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540100
;

-- 2017-10-04T16:31:03.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540212,540100,0,TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','--',0,0,'D','Y','Y','X','(DocAuto)','X',TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'(DocAuto)',0,0,0)
;

-- 2017-10-04T16:31:03.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540212 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2017-10-04T16:31:03.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540213,540100,0,TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','PR',0,0,'D','Y','Y','X','(DocPrepare)','X',TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'(DocPrepare)',0,0,0)
;

-- 2017-10-04T16:31:03.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540213 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2017-10-04T16:31:04.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540214,540100,0,TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','CO',0,0,'D','Y','Y','X','(DocComplete)','X',TO_TIMESTAMP('2017-10-04 16:31:03','YYYY-MM-DD HH24:MI:SS'),100,'(DocComplete)',0,0,0)
;

-- 2017-10-04T16:31:04.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540214 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2017-10-04T16:31:04.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,540213,540211,540148,TO_TIMESTAMP('2017-10-04 16:31:04','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y','N',10,TO_TIMESTAMP('2017-10-04 16:31:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-04T16:31:04.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_NodeNext SET Description='(Standard Approval)', IsStdUserWorkflow='Y',Updated=TO_TIMESTAMP('2017-10-04 16:31:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_NodeNext_ID=540148
;

-- 2017-10-04T16:31:04.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,540212,540211,540149,TO_TIMESTAMP('2017-10-04 16:31:04','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y','N',100,TO_TIMESTAMP('2017-10-04 16:31:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-04T16:31:04.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,540214,540213,540150,TO_TIMESTAMP('2017-10-04 16:31:04','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y','N',100,TO_TIMESTAMP('2017-10-04 16:31:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-04T16:31:04.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Workflow_ID,Created,CreatedBy,EntityType,IsActive,IsReport,IsUseBPartnerLanguage,Name,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,540866,540100,TO_TIMESTAMP('2017-10-04 16:31:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Y','Process_M_Forecast','Java',TO_TIMESTAMP('2017-10-04 16:31:04','YYYY-MM-DD HH24:MI:SS'),100,'Process_M_Forecast')
;

-- 2017-10-04T16:31:04.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540866 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-10-04T16:31:04.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Process_ID=540866,Updated=TO_TIMESTAMP('2017-10-04 16:31:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=11916
;

-- 2017-10-04T16:31:04.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Process_ID=540866,Updated=TO_TIMESTAMP('2017-10-04 16:31:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557362
;

-- 2017-10-04T17:09:37.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557362,560388,0,653,TO_TIMESTAMP('2017-10-04 17:09:37','YYYY-MM-DD HH24:MI:SS'),100,'Der zukünftige Status des Belegs',2,'D','You find the current status in the Document Status field. The options are listed in a popup','Y','Y','Y','N','N','N','N','N','Belegverarbeitung',TO_TIMESTAMP('2017-10-04 17:09:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-04T17:09:37.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560388 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-04T17:09:37.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557363,560389,0,653,TO_TIMESTAMP('2017-10-04 17:09:37','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document',2,'D','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','Y','Y','N','N','N','N','N','Belegstatus',TO_TIMESTAMP('2017-10-04 17:09:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-04T17:09:37.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560389 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-04T17:09:37.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557364,560390,0,653,TO_TIMESTAMP('2017-10-04 17:09:37','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'D','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','Y','Y','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2017-10-04 17:09:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-04T17:09:37.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560390 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-04T17:11:34.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2017-10-04 17:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560390
;

-- 2017-10-04T17:11:34.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2017-10-04 17:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10298
;

-- 2017-10-04T17:11:34.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2017-10-04 17:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560389
;

-- 2017-10-04T17:11:34.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2017-10-04 17:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560388
;

-- 2017-10-04T17:11:34.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2017-10-04 17:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10300
;

-- 2017-10-04T17:11:44.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-10-04 17:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560389
;

-- 2017-10-04T17:11:51.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-10-04 17:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560388
;

-- 2017-10-04T17:11:59.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-10-04 17:11:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10298
;

-- 2017-10-04T17:11:59.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-10-04 17:11:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10300
;

-- 2017-10-04T17:11:59.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-10-04 17:11:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560388
;

-- 2017-10-04T17:11:59.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-10-04 17:11:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560389
;

-- 2017-10-04T17:11:59.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-10-04 17:11:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560390
;

-- 2017-10-04T17:16:48.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Forecast','ALTER TABLE public.M_Forecast ADD COLUMN DocAction CHAR(2) DEFAULT ''CO'' NOT NULL')
;

-- 2017-10-04T17:16:59.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Forecast','ALTER TABLE public.M_Forecast ADD COLUMN DocStatus VARCHAR(2) DEFAULT ''DR'' NOT NULL')
;

-- 2017-10-04T17:17:15.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Forecast','ALTER TABLE public.M_Forecast ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

