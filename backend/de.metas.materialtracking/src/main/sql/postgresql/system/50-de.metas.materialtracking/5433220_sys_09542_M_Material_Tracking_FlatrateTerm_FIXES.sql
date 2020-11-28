-- 12.11.2015 14:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when (select C_Flatrate_Term_ID from M_Material_Tracking where M_Material_Tracking_ID=@M_Material_Tracking_ID@)  IS NULL   then  (select ad_color_id from ad_color where name = ''Rot'') else -1 end as ad_color_id',Updated=TO_TIMESTAMP('2015-11-12 14:53:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 12.11.2015 17:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and (
		c.M_QualityInsp_LagerKonf_ID =(select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)
		)
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	
)',Updated=TO_TIMESTAMP('2015-11-12 17:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 17:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541423,0,540541,540821,18,540280,'C_Flatrate_Conditions_ID',TO_TIMESTAMP('2015-11-12 17:01:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.flatrate',0,'Y','N','Y','N','Y','N','Vertragsbedingungen',10,TO_TIMESTAMP('2015-11-12 17:01:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.11.2015 17:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540821 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 12.11.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540821
;

-- 12.11.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540821
;

-- 12.11.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541423,0,540626,540822,18,540280,'C_Flatrate_Conditions_ID',TO_TIMESTAMP('2015-11-12 17:02:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.flatrate',0,'Y','N','Y','N','Y','N','Vertragsbedingungen',10,TO_TIMESTAMP('2015-11-12 17:02:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.11.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540822 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 12.11.2015 17:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540312,' exists
 (
	select 1 
	from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf_Version lkv on fc.M_QualityInsp_LagerKonf_Version_ID = lvk.M_QualityInsp_LagerKonf_Version
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join C_Flatrate_Matching fm on fc.C_Flatrate_Conditions_ID = fm.C_Flatrate_Conditions_ID
	where 
		(fm.M_Product_ID = @M_Product_ID/-1@ or fm.M_Product_Category_ID = (select p.M_Product_Category_ID  from M_Product p where p.M_Product_ID = @M_Product_ID/-1@))
		and lk.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID/-1@
	
 )',TO_TIMESTAMP('2015-11-12 17:25:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','C_Flatrate_Conditions_For_Material_Tracking','S',TO_TIMESTAMP('2015-11-12 17:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.11.2015 17:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf_Version lkv on fc.M_QualityInsp_LagerKonf_Version_ID = lvk.M_QualityInsp_LagerKonf_Version
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		(fm.M_Product_ID = @M_Product_ID/-1@ or fm.M_Product_Category_ID = (select p.M_Product_Category_ID  from M_Product p where p.M_Product_ID = @M_Product_ID/-1@)) and 
		lk.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID/-1@
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	
)',Updated=TO_TIMESTAMP('2015-11-12 17:37:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 17:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540312,Updated=TO_TIMESTAMP('2015-11-12 17:37:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540822
;

-- 12.11.2015 18:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code=' exists
 (
	select 1 
	from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join C_Flatrate_Matching fm on fc.C_Flatrate_Conditions_ID = fm.C_Flatrate_Conditions_ID
	where 
		(fm.M_Product_ID = @M_Product_ID/-1@ or fm.M_Product_Category_ID = (select p.M_Product_Category_ID  from M_Product p where p.M_Product_ID = @M_Product_ID/-1@))
		and lk.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID/-1@
	
 )',Updated=TO_TIMESTAMP('2015-11-12 18:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540312
;

-- 12.11.2015 18:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		(fm.M_Product_ID = @M_Product_ID/-1@ or fm.M_Product_Category_ID = (select p.M_Product_Category_ID  from M_Product p where p.M_Product_ID = @M_Product_ID/-1@)) and 
		lk.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID/-1@
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	
)',Updated=TO_TIMESTAMP('2015-11-12 18:08:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 18:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join C_Flatrate_Matching fm on fm.C_Flatrate_Conditions_ID = fc.C_Flatrate_Conditions_ID
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		(fm.M_Product_ID = @M_Product_ID/-1@ or fm.M_Product_Category_ID = (select p.M_Product_Category_ID  from M_Product p where p.M_Product_ID = @M_Product_ID/-1@)) and 
		lk.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID/-1@
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	
)',Updated=TO_TIMESTAMP('2015-11-12 18:11:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 18:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join C_Flatrate_Matching fm on fm.C_Flatrate_Conditions_ID = fc.C_Flatrate_Conditions_ID
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
join M_QualityInsp_LagerKonf_Version lkv on lk._QualityInsp_LagerKonf_ID = lkv._QualityInsp_LagerKonf_ID

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		(fm.M_Product_ID = @M_Product_ID/-1@ or fm.M_Product_Category_ID = (select p.M_Product_Category_ID  from M_Product p where p.M_Product_ID = @M_Product_ID/-1@)) and 
		lkv.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID/-1@
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	
)',Updated=TO_TIMESTAMP('2015-11-12 18:12:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 18:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join C_Flatrate_Matching fm on fm.C_Flatrate_Conditions_ID = fc.C_Flatrate_Conditions_ID
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv._QualityInsp_LagerKonf_ID

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		(fm.M_Product_ID = @M_Product_ID/-1@ or fm.M_Product_Category_ID = (select p.M_Product_Category_ID  from M_Product p where p.M_Product_ID = @M_Product_ID/-1@)) and 
		lkv.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID/-1@
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	
)',Updated=TO_TIMESTAMP('2015-11-12 18:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 18:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join C_Flatrate_Matching fm on fm.C_Flatrate_Conditions_ID = fc.C_Flatrate_Conditions_ID
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		(fm.M_Product_ID = @M_Product_ID/-1@ or fm.M_Product_Category_ID = (select p.M_Product_Category_ID  from M_Product p where p.M_Product_ID = @M_Product_ID/-1@)) and 
		lkv.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID/-1@
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	
)',Updated=TO_TIMESTAMP('2015-11-12 18:16:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 18:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join C_Flatrate_Matching fm on fm.C_Flatrate_Conditions_ID = fc.C_Flatrate_Conditions_ID
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		(fm.M_Product_ID = @M_Product_ID/-1@ ) and 
		lkv.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID/-1@
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	
)',Updated=TO_TIMESTAMP('2015-11-12 18:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 18:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code=' exists
 (
	select 1 
	from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join C_Flatrate_Matching fm on fc.C_Flatrate_Conditions_ID = fm.C_Flatrate_Conditions_ID
	where 
		(fm.M_Product_ID = @M_Product_ID/-1@ )
		and lk.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID/-1@
	
 )',Updated=TO_TIMESTAMP('2015-11-12 18:22:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540312
;

-- 12.11.2015 18:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code=' exists
 (
	select 1 
	from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID
	join C_Flatrate_Matching fm on fc.C_Flatrate_Conditions_ID = fm.C_Flatrate_Conditions_ID
	where 
		(fm.M_Product_ID = @M_Product_ID/-1@ )
		and lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@
	
 )',Updated=TO_TIMESTAMP('2015-11-12 18:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540312
;

-- 12.11.2015 18:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code=' exists
 (
	select 1 
	from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID
	join C_Flatrate_Matching fm on fc.C_Flatrate_Conditions_ID = fm.C_Flatrate_Conditions_ID
	where 
		(fm.M_Product_ID = @M_Product_ID@ )
		and lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID@
	
 )',Updated=TO_TIMESTAMP('2015-11-12 18:29:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540312
;

-- 12.11.2015 18:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join C_Flatrate_Matching fm on fm.C_Flatrate_Conditions_ID = fc.C_Flatrate_Conditions_ID
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		(fm.M_Product_ID = @M_Product_ID@ ) and 
		lkv.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID@
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	
)',Updated=TO_TIMESTAMP('2015-11-12 18:29:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 18:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
	select fc.C_Flatrate_Conditions_ID 
	from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID
	join C_Flatrate_Matching fm on fc.C_Flatrate_Conditions_ID = fm.C_Flatrate_Conditions_ID
	where 
		(fm.M_Product_ID = @M_Product_ID@ )
		and lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID@
	
 ',Updated=TO_TIMESTAMP('2015-11-12 18:40:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540312
;

-- 12.11.2015 18:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists(	
select fc.C_Flatrate_Conditions_ID 
	from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID
	join C_Flatrate_Matching fm on fc.C_Flatrate_Conditions_ID = fm.C_Flatrate_Conditions_ID
	where 
		(fm.M_Product_ID = @M_Product_ID@ )
		and lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID@
	)
 ',Updated=TO_TIMESTAMP('2015-11-12 18:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540312
;

-- 12.11.2015 18:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists(	
select fc.C_Flatrate_Conditions_ID 
	from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID
	join C_Flatrate_Matching fm on fc.C_Flatrate_Conditions_ID = fm.C_Flatrate_Conditions_ID
	join M_Material_Tracking mt on mt.M_Material_Tracking_ID = @M_Material_Tracking_ID@
	where 
		(fm.M_Product_ID = mt.M_Product_ID )
		and lkv.M_QualityInsp_LagerKonf_Version_ID = mt.M_QualityInsp_LagerKonf_Version_ID
	)
 ',Updated=TO_TIMESTAMP('2015-11-12 18:49:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540312
;

-- 12.11.2015 18:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists(	
select fc.C_Flatrate_Conditions_ID 
	from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID
	join C_Flatrate_Matching fm on fc.C_Flatrate_Conditions_ID = fm.C_Flatrate_Conditions_ID
	join M_Material_Tracking mt on mt.M_Material_Tracking_ID = @M_Material_Tracking_ID@
	where 
		(fm.M_Product_ID = mt.M_Product_ID )
		and lkv.M_QualityInsp_LagerKonf_Version_ID = mt.M_QualityInsp_LagerKonf_Version_ID
		and  fc.C_Flatrate_Conditions_ID = C_Flatrate_Conditions.C_Flatrate_Conditions_ID 
	)
 ',Updated=TO_TIMESTAMP('2015-11-12 18:57:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540312
;

-- 12.11.2015 18:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join C_Flatrate_Matching fm on fm.C_Flatrate_Conditions_ID = fc.C_Flatrate_Conditions_ID
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		
		lkv.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID@
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@',Updated=TO_TIMESTAMP('2015-11-12 18:59:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 19:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists
(	
select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join C_Flatrate_Matching fm on fm.C_Flatrate_Conditions_ID = fc.C_Flatrate_Conditions_ID
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		
		lkv.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID@
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	and t.C_Flatrate_Term_ID = C_Flatrate_Term.C_Flatrate_Term_ID
)',Updated=TO_TIMESTAMP('2015-11-12 19:01:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 19:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists
(	
select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		
		lkv.M_QualityInsp_LagerKonf_ID = @M_QualityInsp_LagerKonf_ID@
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	and t.C_Flatrate_Term_ID = C_Flatrate_Term.C_Flatrate_Term_ID
)',Updated=TO_TIMESTAMP('2015-11-12 19:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 19:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists
(	
select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID
	join M_Material_Tracking mt on mt.M_Material_Tracking_ID = @M_Material_Tracking_ID@

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		
		lkv.M_QualityInsp_LagerKonf_ID = mt.M_QualityInsp_LagerKonf_ID
		))
	and t.M_Product_ID = mt.M_Product_ID
	and t.Bill_BPartner_ID = mt.C_BPartner_ID
	and t.C_Flatrate_Term_ID = C_Flatrate_Term.C_Flatrate_Term_ID
)',Updated=TO_TIMESTAMP('2015-11-12 19:47:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 19:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(	
select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID
	join M_Material_Tracking mt on mt.M_Material_Tracking_ID = @M_Material_Tracking_ID@

	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		
		lkv.M_QualityInsp_LagerKonf_Version_ID = mt.M_QualityInsp_LagerKonf_Version_ID
		))
	and t.M_Product_ID = mt.M_Product_ID
	and t.Bill_BPartner_ID = mt.C_BPartner_ID
	and t.C_Flatrate_Term_ID = C_Flatrate_Term.C_Flatrate_Term_ID
)
',Updated=TO_TIMESTAMP('2015-11-12 19:50:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 19:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(	
select 1 
	from C_FLatrate_Term t
	join M_Material_Tracking mt on mt.M_Material_Tracking_ID = @M_Material_Tracking_ID@
	where  (exists (select 1 from C_Flatrate_Conditions fc
	join M_QualityInsp_LagerKonf lk on fc.M_QualityInsp_LagerKonf_ID = lk.M_QualityInsp_LagerKonf_ID
	join M_QualityInsp_LagerKonf_Version lkv on lk.M_QualityInsp_LagerKonf_ID = lkv.M_QualityInsp_LagerKonf_ID


	where fc.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and
		
		lkv.M_QualityInsp_LagerKonf_Version_ID = mt.M_QualityInsp_LagerKonf_Version_ID
		))
	and t.M_Product_ID = mt.M_Product_ID
	and t.Bill_BPartner_ID = mt.C_BPartner_ID
	and t.C_Flatrate_Term_ID = C_Flatrate_Term.C_Flatrate_Term_ID
)
',Updated=TO_TIMESTAMP('2015-11-12 19:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

