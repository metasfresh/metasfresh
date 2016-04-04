-- 04.04.2016 16:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2016-04-04 16:23:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556657
;

-- 04.04.2016 16:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2016-04-04 16:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556654
;

-- 04.04.2016 16:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=3.000000000000,Updated=TO_TIMESTAMP('2016-04-04 16:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556653
;

-- 04.04.2016 16:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET OrderByClause='DatePromised DESC, (select p.Value||''_''||p.Name from M_Product p where p.M_Product_ID=PMM_PurchaseCandidate.M_Product_ID) ASC, (select bp.Value||''_''||bp.Name from C_BPartner bp where bp.C_BPartner_ID=PMM_PurchaseCandidate.C_BPartner_ID) ASC',Updated=TO_TIMESTAMP('2016-04-04 16:32:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540725
;

