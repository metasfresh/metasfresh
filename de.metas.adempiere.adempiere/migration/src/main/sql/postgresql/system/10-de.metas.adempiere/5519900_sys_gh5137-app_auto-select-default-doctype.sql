-- 2019-04-21T11:44:07.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='C_DocType.IsDefault DESC /*Default=Y records first*/',Updated=TO_TIMESTAMP('2019-04-21 11:44:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=170
;

-- 2019-04-21T11:45:03.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=170, IsAutoApplyValidationRule='Y',Updated=TO_TIMESTAMP('2019-04-21 11:45:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=12797
;

--
-- fix the inventory window for our new doctype and the modifies old doc type which now has a subtype
--
-- 2019-04-22T13:47:08.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='/*whereclause from AD_Tab_ID=255*/ M_Inventory.M_Inventory_ID in (select i.M_Inventory_ID from  M_Inventory i  join C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  where  dt.DocBaseType = ''MMI'' and dt.DocSubType IN (''IAH'',''ISH''))',Updated=TO_TIMESTAMP('2019-04-22 13:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=255
;

