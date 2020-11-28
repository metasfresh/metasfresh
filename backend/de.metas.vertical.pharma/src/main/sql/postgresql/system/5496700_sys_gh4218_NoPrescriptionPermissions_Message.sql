-- 2018-06-27T16:09:02.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544755,0,TO_TIMESTAMP('2018-06-27 16:09:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','Das Produkt <product identifier> ist verschreibungspflichig und darf nicht an den Kunden <bpartner name> verkauft werden. Grund: <c_order lieferberechtigung>','I',TO_TIMESTAMP('2018-06-27 16:09:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.PharmaService.NoPrescriptionPermission')
;

-- 2018-06-27T16:09:02.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544755 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-06-27T16:09:41.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Das Produkt <{0}> ist verschreibungspflichig und darf nicht an den Kunden <{1}> verkauft werden. Grund: <{2}>',Updated=TO_TIMESTAMP('2018-06-27 16:09:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544755
;

-- 2018-06-27T16:10:21.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Das Produkt {0} ist verschreibungspflichig und darf nicht an den Kunden {1} verkauft werden. Grund: {2}',Updated=TO_TIMESTAMP('2018-06-27 16:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544755
;

-- 2018-06-27T16:46:12.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-27 16:46:12','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='The product {0} is a prescription medicine and may not be sold to the customer {1}. Reason: {2}' WHERE AD_Message_ID=544755 AND AD_Language='en_US'
;

-- 2018-06-27T18:32:13.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.vertical.pharma.PharmaOrderLineQuickInputValidator.NoPrescriptionPermission',Updated=TO_TIMESTAMP('2018-06-27 18:32:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544755
;

