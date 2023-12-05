-- select migrationscript_ignore('10-de.metas.adempiere/5711980_C_ElementValue_for_C_AcctSchema_ID_val_rule.sql');

-- Name: C_ElementValue_for_C_AcctSchema_ID
-- 2023-11-28T13:48:07.021366500Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540666,'exists (select 1 from c_acctschema_element cae
                     where cae.c_acctschema_id=@C_AcctSchema_ID/0@
                     and cae.elementtype=''AC''
                     and cae.c_element_id=c_elementvalue.c_element_id)',TO_TIMESTAMP('2023-11-28 15:48:06.854','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','C_ElementValue_for_C_AcctSchema_ID','S',TO_TIMESTAMP('2023-11-28 15:48:06.854','YYYY-MM-DD HH24:MI:SS.US'),100)
;

