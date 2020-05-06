-- 2018-09-13T13:44:52.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544256,0,'QuantityType',TO_TIMESTAMP('2018-09-13 13:44:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','QuantityType','QuantityType',TO_TIMESTAMP('2018-09-13 13:44:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-13T13:44:52.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544256 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-13T13:45:01.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=544256, ColumnName='QuantityType', Name='QuantityType',Updated=TO_TIMESTAMP('2018-09-13 13:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541336
;

-- 2018-09-13T13:45:45.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=544223
;

-- 2018-09-13T13:45:45.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=544223
;

-- 2018-09-13T13:49:56.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Kommissionierter Menge',Updated=TO_TIMESTAMP('2018-09-13 13:49:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541681
;

-- 2018-09-13T13:50:10.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-13 13:50:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Picked Qty' WHERE AD_Ref_List_ID=541681 AND AD_Language='en_US'
;

-- 2018-09-13T13:50:30.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Liefermenge',Updated=TO_TIMESTAMP('2018-09-13 13:50:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541682
;

-- 2018-09-13T13:50:39.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-13 13:50:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='To Be Delivered Qty' WHERE AD_Ref_List_ID=541682 AND AD_Language='en_US'
;

-- 2018-09-13T13:55:58.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Name='Mengenart',Updated=TO_TIMESTAMP('2018-09-13 13:55:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541336
;

-- 2018-09-13T13:56:08.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-13 13:56:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quantity Type' WHERE AD_Process_Para_ID=541336 AND AD_Language='en_US'
;

-- 2018-09-13T13:56:24.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='M_ShipmentSchedule_QtyTypeToShip',Updated=TO_TIMESTAMP('2018-09-13 13:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540907
;

-- 2018-09-13T13:56:40.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Beauftragte Menge',Updated=TO_TIMESTAMP('2018-09-13 13:56:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541682
;

-- 2018-09-13T13:57:03.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Kommissionierte Menge',Updated=TO_TIMESTAMP('2018-09-13 13:57:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541681
;

-- 2018-09-13T13:57:30.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-13 13:57:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Kommissionierte Menge' WHERE AD_Ref_List_ID=541681 AND AD_Language='de_CH'
;

-- 2018-09-13T13:57:39.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-13 13:57:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Beauftragte Menge' WHERE AD_Ref_List_ID=541682 AND AD_Language='de_CH'
;

-- 2018-09-13T13:57:53.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Beide',Updated=TO_TIMESTAMP('2018-09-13 13:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541683
;

-- 2018-09-13T13:57:57.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-13 13:57:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Beide' WHERE AD_Ref_List_ID=541683 AND AD_Language='de_CH'
;

-- 2018-09-13T13:58:06.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-13 13:58:06','YYYY-MM-DD HH24:MI:SS'),Name='Both' WHERE AD_Ref_List_ID=541683 AND AD_Language='en_US'
;

