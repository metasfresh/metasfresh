
-- 2021-06-25T12:11:20.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists (select 1 from M_Product p where p.AD_Org_ID = @AD_Org_Target_ID@ and C_CompensationGroup_Schema_Category.C_CompensationGroup_Schema_Category_ID = p.C_CompensationGroup_Schema_Category_ID)',Updated=TO_TIMESTAMP('2021-06-25 15:11:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540535
;

