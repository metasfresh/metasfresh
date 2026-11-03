
-- 2017-07-27T12:30:59.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540363,'M_HU.HUStatus=''A'' AND COALESCE(M_HU.M_HU_Item_Parent,0)<=0
',TO_TIMESTAMP('2017-07-27 12:30:58','YYYY-MM-DD HH24:MI:SS'),100,'validation rule to filter for top level HUs with status "active"','de.metas.handlingunits','Y','M_HU_Toplevel active','S',TO_TIMESTAMP('2017-07-27 12:30:58','YYYY-MM-DD HH24:MI:SS'),100)
;
