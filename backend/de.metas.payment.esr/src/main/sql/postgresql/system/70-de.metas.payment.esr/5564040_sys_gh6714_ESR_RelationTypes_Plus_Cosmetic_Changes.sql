-- 2020-07-14T13:39:06.542Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_BankStatement Source',Updated=TO_TIMESTAMP('2020-07-14 16:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541125
;

-- 2020-07-14T13:39:41.235Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541125,540247,TO_TIMESTAMP('2020-07-14 16:39:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','C_BankStatement -> ESR_Import',TO_TIMESTAMP('2020-07-14 16:39:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-14T13:43:02.109Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541157,TO_TIMESTAMP('2020-07-14 16:43:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','ESR_Import target for C_BankStatement',TO_TIMESTAMP('2020-07-14 16:43:01','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-07-14T13:43:02.218Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541157 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-07-14T13:43:13.046Z
-- URL zum Konzept
UPDATE AD_Reference SET EntityType='de.metas.payment.esr',Updated=TO_TIMESTAMP('2020-07-14 16:43:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541157
;

-- 2020-07-14T13:47:36.156Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,547550,0,541157,540409,540159,TO_TIMESTAMP('2020-07-14 16:47:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N',TO_TIMESTAMP('2020-07-14 16:47:35','YYYY-MM-DD HH24:MI:SS'),100,' exists (     select 1     from ESR_Import  esr     join ESR_ImportLine  esrl on  esr.ESR_Import_ID =  esrl.ESR_Import_ID     join C_BankStatementLine bsl on  esrl.C_BankStatementLine_ID = bsl.C_BankStatementLine_ID     join C_BankStatement bs on bsl.C_BankStatement_ID = bs.C_BankStatement_ID     where     ESR_Import.ESR_Import_ID =  esr.ESR_Import_ID     and         ( esr.ESR_Import_ID = @ESR_Import_ID/-1@ or bs.C_BankStatement_ID = @C_BankStatement_ID/-1@)     )')
;

-- 2020-07-14T13:48:06.767Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541157, EntityType='de.metas.payment.esr',Updated=TO_TIMESTAMP('2020-07-14 16:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540247
;






-- 2020-07-14T15:02:15.590Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541158,TO_TIMESTAMP('2020-07-14 18:02:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','ESR_Import Source',TO_TIMESTAMP('2020-07-14 18:02:14','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-07-14T15:02:16.351Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541158 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-07-14T15:02:59.157Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,547550,0,541158,540409,540159,TO_TIMESTAMP('2020-07-14 18:02:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N',TO_TIMESTAMP('2020-07-14 18:02:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-14T15:07:25.903Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Payment Target For ESR_ImportLine',Updated=TO_TIMESTAMP('2020-07-14 18:07:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540539
;

-- 2020-07-14T15:07:30.456Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Payment Target For ESR_Import',Updated=TO_TIMESTAMP('2020-07-14 18:07:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540539
;

-- 2020-07-14T15:11:01.402Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='EXISTS (Select 1 from C_Payment p join ESR_ImportLine esrl on esrl.C_Payment_ID = p.C_Payment_ID JOIN ESR_Import esr on esrl.ESR_Import_ID = esr.ESR_Import_ID where p.C_Payment_ID = C_Payment.C_Payment_ID and esr.ESR_Import_ID = @ESR_Import_ID/-1@',Updated=TO_TIMESTAMP('2020-07-14 18:11:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540539
;

-- 2020-07-14T15:11:06.818Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=195,Updated=TO_TIMESTAMP('2020-07-14 18:11:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540539
;

-- 2020-07-14T15:11:37.627Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541158,540539,540248,TO_TIMESTAMP('2020-07-14 18:11:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','N','ESR_Import -> C_Payment',TO_TIMESTAMP('2020-07-14 18:11:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-14T15:12:15.524Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2020-07-14 18:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540248
;

-- 2020-07-14T15:13:18.175Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541159,TO_TIMESTAMP('2020-07-14 18:13:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y','N','C_Payment Source',TO_TIMESTAMP('2020-07-14 18:13:17','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-07-14T15:13:18.384Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541159 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-07-14T15:14:03.075Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,5043,0,541159,335,195,TO_TIMESTAMP('2020-07-14 18:14:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y','N',TO_TIMESTAMP('2020-07-14 18:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-14T15:14:58.422Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541160,TO_TIMESTAMP('2020-07-14 18:14:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','ESR_Import Target For C_Payment',TO_TIMESTAMP('2020-07-14 18:14:58','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-07-14T15:14:58.491Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541160 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-07-14T15:15:31.468Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,547550,0,541160,540409,540159,TO_TIMESTAMP('2020-07-14 18:15:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N',TO_TIMESTAMP('2020-07-14 18:15:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-14T15:17:10.146Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from ESR_Import esr JOIN ESR_ImportLine esrl on esr.ESR_Import_ID = esrl.ESR_Import_ID JOIN C_Payment p on esrl.C_Payment_ID = p.C_Payment_ID where p.C_Payment_ID = @C_Payment_ID/-1@ AND ESR_Import.ESR_Import_ID = esr.ESR_Import_ID)',Updated=TO_TIMESTAMP('2020-07-14 18:17:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541160
;

-- 2020-07-14T15:17:27.803Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541159,541160,540249,TO_TIMESTAMP('2020-07-14 18:17:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','N','C_Payment -> ESR_Import',TO_TIMESTAMP('2020-07-14 18:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-14T15:18:59.483Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='EXISTS (Select 1 from C_Payment p join ESR_ImportLine esrl on esrl.C_Payment_ID = p.C_Payment_ID JOIN ESR_Import esr on esrl.ESR_Import_ID = esr.ESR_Import_ID where p.C_Payment_ID = C_Payment.C_Payment_ID and esr.ESR_Import_ID = @ESR_Import_ID/-1@)',Updated=TO_TIMESTAMP('2020-07-14 18:18:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540539
;

-- 2020-07-14T15:19:13.711Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='ESR_Import Target for C_BankStatement',Updated=TO_TIMESTAMP('2020-07-14 18:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541157
;

-- 2020-07-14T15:19:43.912Z
-- URL zum Konzept
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540110
;

-- 2020-07-14T15:19:53.298Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2020-07-14 18:19:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540249
;






-- 2020-07-20T14:09:06.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='ESR_Import',Updated=TO_TIMESTAMP('2020-07-20 17:09:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541158
;

-- 2020-07-20T14:09:24.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541158,Updated=TO_TIMESTAMP('2020-07-20 17:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541763
;

-- 2020-07-20T14:18:44.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552039,615412,0,540442,TO_TIMESTAMP('2020-07-20 17:18:43','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.payment.esr','Y','N','N','N','N','N','N','N','Process Now',TO_TIMESTAMP('2020-07-20 17:18:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-20T14:18:44.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=615412 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-07-20T14:18:44.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2020-07-20T14:18:44.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=615412
;

-- 2020-07-20T14:18:44.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(615412)
;

-- 2020-07-20T14:18:44.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557168,615413,0,540442,TO_TIMESTAMP('2020-07-20 17:18:44','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.esr','Y','N','N','N','N','N','N','N','Anhang',TO_TIMESTAMP('2020-07-20 17:18:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-20T14:18:44.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=615413 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-07-20T14:18:44.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543390) 
;

-- 2020-07-20T14:18:44.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=615413
;

-- 2020-07-20T14:18:44.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(615413)
;

-- 2020-07-20T14:18:44.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570817,615414,0,540442,TO_TIMESTAMP('2020-07-20 17:18:44','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde',1,'de.metas.payment.esr','Y','N','N','N','N','N','N','N','Abgeglichen',TO_TIMESTAMP('2020-07-20 17:18:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-20T14:18:44.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=615414 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-07-20T14:18:44.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1105) 
;

-- 2020-07-20T14:18:44.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=615414
;

-- 2020-07-20T14:18:44.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(615414)
;

-- 2020-07-20T14:19:34.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,615414,0,540442,570384,540790,'F',TO_TIMESTAMP('2020-07-20 17:19:34','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde','Y','N','N','Y','N','N','N',0,'Abgeglichen',40,0,0,TO_TIMESTAMP('2020-07-20 17:19:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-20T14:20:59.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-07-20 17:20:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=615414
;

-- 2020-07-21T14:45:18.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2020-07-21 17:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540247
;

-- 2020-07-21T14:45:37.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2020-07-21 17:45:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540247
;

-- 2020-07-21T14:49:24.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540592,Updated=TO_TIMESTAMP('2020-07-21 17:49:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540247
;

-- 2020-07-21T14:49:36.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541125,Updated=TO_TIMESTAMP('2020-07-21 17:49:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540247
;

-- 2020-07-21T14:50:09.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists (     select 1     from ESR_Import  esr     join ESR_ImportLine  esrl on  esr.ESR_Import_ID =  esrl.ESR_Import_ID     join C_BankStatementLine bsl on  esrl.C_BankStatementLine_ID = bsl.C_BankStatementLine_ID     join C_BankStatement bs on bsl.C_BankStatement_ID = bs.C_BankStatement_ID     where     ESR_Import.ESR_Import_ID =  esr.ESR_Import_ID     and    bs.C_BankStatement_ID = @C_BankStatement_ID/-1@     )',Updated=TO_TIMESTAMP('2020-07-21 17:50:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541157
;

-- 2020-07-21T14:51:42.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541165,TO_TIMESTAMP('2020-07-21 17:51:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','C_BankStatement Target for ESR_mport',TO_TIMESTAMP('2020-07-21 17:51:42','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-07-21T14:51:42.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541165 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-07-21T14:52:51.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_BankStatement Target for ESR_Import',Updated=TO_TIMESTAMP('2020-07-21 17:52:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541165
;

-- 2020-07-21T14:54:19.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,4909,0,541165,392,194,TO_TIMESTAMP('2020-07-21 17:54:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','N',TO_TIMESTAMP('2020-07-21 17:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-21T14:55:56.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists (     select 1     from ESR_Import  esr     join ESR_ImportLine  esrl on  esr.ESR_Import_ID =  esrl.ESR_Import_ID     join C_BankStatementLine bsl on  esrl.C_BankStatementLine_ID = bsl.C_BankStatementLine_ID     join C_BankStatement bs on bsl.C_BankStatement_ID = bs.C_BankStatement_ID     where     bs.C_BankStatement_ID =  C_BankStatement.C_BankStatement_ID    and    esr.ESR_Import_ID = @ESR_Import_ID/-1@    )',Updated=TO_TIMESTAMP('2020-07-21 17:55:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541165
;

-- 2020-07-21T14:56:13.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541158,541165,540250,TO_TIMESTAMP('2020-07-21 17:56:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','ESR_Import -> Bank Statement',TO_TIMESTAMP('2020-07-21 17:56:13','YYYY-MM-DD HH24:MI:SS'),100)
;


