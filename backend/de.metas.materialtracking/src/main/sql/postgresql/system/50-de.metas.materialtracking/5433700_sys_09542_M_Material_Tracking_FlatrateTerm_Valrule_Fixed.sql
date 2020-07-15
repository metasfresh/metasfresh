-- 19.11.2015 16:15
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
	and t.StartDate <= mt.ValidFrom
	and (t.EndDate is null or mt.ValidTo is null or t.EndDate >= mt.ValidTo) 
)
',Updated=TO_TIMESTAMP('2015-11-19 16:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;