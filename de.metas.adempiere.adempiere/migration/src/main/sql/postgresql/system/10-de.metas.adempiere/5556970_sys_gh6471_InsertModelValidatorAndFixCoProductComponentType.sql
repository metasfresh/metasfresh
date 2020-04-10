-- 2020-04-10T12:13:58.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ModelValidator (AD_Client_ID,AD_ModelValidator_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,ModelValidationClass,Name,SeqNo,Updated,UpdatedBy) VALUES (0,540124,0,TO_TIMESTAMP('2020-04-10 15:13:58','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','org.eevolution.callout.PP_Product_BomModuleActivator','Module Activator for PP_Product_Bom',0,TO_TIMESTAMP('2020-04-10 15:13:58','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2020-04-10T13:23:19.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET IsActive='Y', Name='CoProduct', Value='CP',Updated=TO_TIMESTAMP('2020-04-10 16:23:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=53481
;

