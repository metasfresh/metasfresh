-- 10.11.2015 12:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552814,541447,0,19,540610,'N','C_Flatrate_Term_ID',TO_TIMESTAMP('2015-11-10 12:33:59','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.materialtracking',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Pauschale - Vertragsperiode',0,TO_TIMESTAMP('2015-11-10 12:33:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 10.11.2015 12:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552814 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 10.11.2015 12:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540279,Updated=TO_TIMESTAMP('2015-11-10 12:34:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552814
;

-- 10.11.2015 12:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540311,TO_TIMESTAMP('2015-11-10 12:36:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','C_Flatrate_Term for Material Tracking','S',TO_TIMESTAMP('2015-11-10 12:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.11.2015 12:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='C_Flatrate_Term for M_Material_Tracking',Updated=TO_TIMESTAMP('2015-11-10 12:36:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 10.11.2015 13:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	inner join M_QualityInsp_LagerKonf_Version lkv on lkv.C_FLatrate_Term_ID = t.C_FLatrate_Term_ID 
	where  lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)',Updated=TO_TIMESTAMP('2015-11-10 13:07:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 10.11.2015 13:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540311,Updated=TO_TIMESTAMP('2015-11-10 13:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552814
;

-- 10.11.2015 13:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Material_Tracking ADD C_Flatrate_Term_ID NUMERIC(10) DEFAULT NULL 
;

-- 10.11.2015 13:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@C_Flatrate_Term_ID@ > 0',Updated=TO_TIMESTAMP('2015-11-10 13:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551273
;

-- 10.11.2015 13:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@C_Flatrate_Term_ID@ > 0',Updated=TO_TIMESTAMP('2015-11-10 13:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551111
;

-- 10.11.2015 13:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@C_Flatrate_Term_ID@ > 0',Updated=TO_TIMESTAMP('2015-11-10 13:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551112
;


-- 10.11.2015 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552814,556387,0,540610,0,TO_TIMESTAMP('2015-11-10 14:44:32','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.materialtracking',0,'Y','Y','Y','Y','N','N','N','N','N','Pauschale - Vertragsperiode',150,150,0,TO_TIMESTAMP('2015-11-10 14:44:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.11.2015 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556387 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 10.11.2015 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=95, SeqNoGrid=95,Updated=TO_TIMESTAMP('2015-11-10 14:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 10.11.2015 14:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @C_Flatrate_Term_ID@ <= 0 then (select ad_color_id from ad_color where name = ''Rot'') else -1 end as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 14:47:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 10.11.2015 15:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2015-11-10 15:28:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552814
;

commit;
-- 10.11.2015 15:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_material_tracking','C_Flatrate_Term_ID','NUMERIC(10)',null,'NULL')
;

commit;

-- 10.11.2015 16:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	inner join M_QualityInsp_LagerKonf_Version lkv on lkv.C_FLatrate_Term_ID = t.C_FLatrate_Term_ID 
	where  (exists select 1 from C_Flatrate_Condition c where c.C_Flatrate_Term_ID = t.C_Flatrate_Term_ID and c.M_QualityInsp_LagerKonf_ID = (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)',Updated=TO_TIMESTAMP('2015-11-10 16:33:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 10.11.2015 16:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	inner join M_QualityInsp_LagerKonf_Version lkv on lkv.C_FLatrate_Term_ID = t.C_FLatrate_Term_ID 
	where  (exists (select 1 from C_Flatrate_Condition c where c.C_Flatrate_Term_ID = t.C_Flatrate_Term_ID and c.M_QualityInsp_LagerKonf_ID = (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)',Updated=TO_TIMESTAMP('2015-11-10 16:37:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;





-- 10.11.2015 16:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Condition c where c.C_Flatrate_Term_ID = t.C_Flatrate_Term_ID and c.M_QualityInsp_LagerKonf_ID = (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)',Updated=TO_TIMESTAMP('2015-11-10 16:41:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 10.11.2015 16:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Term_ID = t.C_Flatrate_Term_ID and c.M_QualityInsp_LagerKonf_ID = (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)',Updated=TO_TIMESTAMP('2015-11-10 16:42:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 10.11.2015 16:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and c.M_QualityInsp_LagerKonf_ID = (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)',Updated=TO_TIMESTAMP('2015-11-10 16:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 10.11.2015 16:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @C_Flatrate_Term_ID@ > 0  then -1 else (select ad_color_id from ad_color where name = ''Rot'') end as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 16:46:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 10.11.2015 16:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select (case when @C_Flatrate_Term_ID@ > 0  then -1 else (select ad_color_id from ad_color where name = ''Rot'') end ) as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 16:49:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 10.11.2015 16:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @C_Flatrate_Term_ID@ > 0  then -1 else (select ad_color_id from ad_color where name = ''Rot'') end  as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 16:51:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 10.11.2015 16:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @C_Flatrate_Term_ID@ <= 0  then  (select ad_color_id from ad_color where name = ''Rot'') end  as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 16:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 10.11.2015 16:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @C_Flatrate_Term_ID@ >0 then -1 else  (select ad_color_id from ad_color where name = ''Rot'') end  as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 16:59:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 10.11.2015 17:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @C_Flatrate_Term_ID@ >0 then -1 else  (select ad_color_id from ad_color where name = ''Rot'') end  as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:01:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @AD_Org_ID@ >0 then -1 else  (select ad_color_id from ad_color where name = ''Rot'') end  as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @AD_Org_ID@ <=0 then -1 else  (select ad_color_id from ad_color where name = ''Rot'') end  as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:16:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @C_Flatrate_Term_ID@ <=0 then -1 else  (select ad_color_id from ad_color where name = ''Rot'') end  as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:18:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2015-11-10 17:19:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552814
;

-- 10.11.2015 17:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @C_Flatrate_Term_ID@ >=0 then -1 else  (select ad_color_id from ad_color where name = ''Rot'') end  as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:28:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @C_Flatrate_Term_ID@ >0 then -1 else  (select ad_color_id from ad_color where name = ''Rot'') end  as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @C_Flarrrrrrrrrrrrtrate_Term_ID@ >0 then -1 else  (select ad_color_id from ad_color where name = ''Rot'') end  as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:32:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when @C_Flatrate_Term_ID@ >0 then -1 else  (select ad_color_id from ad_color where name = ''Rot'') end  as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:35:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Val_Rule_ID=540311,Updated=TO_TIMESTAMP('2015-11-10 17:36:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552814
;

-- 10.11.2015 17:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='case when
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and c.M_QualityInsp_LagerKonf_ID = (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)
then -1
else (select ad_color_id from ad_color where name = ''Rot'') 
end
as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='case when
@C_Flatrate_Term_ID/0@<>0
then -1
else (select ad_color_id from ad_color where name = ''Rot'') 
end
as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic=NULL,Updated=TO_TIMESTAMP('2015-11-10 17:42:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='case when

exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and c.M_QualityInsp_LagerKonf_ID = (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)
then -1
else (select ad_color_id from ad_color where name = ''Orange'')
end
as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:44:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='case when

exists
(
	select 1 
	from C_Flatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and c.M_QualityInsp_LagerKonf_ID = (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)
then -1
else (select ad_color_id from ad_color where name = ''Rot'')
end
as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:45:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when

exists
(
	select 1 
	from C_Flatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and c.M_QualityInsp_LagerKonf_ID = (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)
then -1
else (select ad_color_id from ad_color where name = ''Rot'')
end
as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:46:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when
@C_Flatrate_Term_ID@ > 0
then -1
else (select ad_color_id from ad_color where name = ''Rot'')
end
as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:47:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when
@C_Flatrate_Term_ID@ > 0 and @C_Flatrate_Term_ID@ is not null
then -1
else (select ad_color_id from ad_color where name = ''Rot'')
end
as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic=NULL,Updated=TO_TIMESTAMP('2015-11-10 17:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554701
;

-- 10.11.2015 17:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when
exists
(
	select 1 
	from C_Flatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and c.M_QualityInsp_LagerKonf_ID = (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)
then -1
else (select ad_color_id from ad_color where name = ''''Rot'''')
end
as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 10.11.2015 17:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when
exists
(
	select 1 
	from C_Flatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and c.M_QualityInsp_LagerKonf_ID = (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)
then -1
else (select ad_color_id from ad_color where name = ''Rot'')
end
as ad_color_id',Updated=TO_TIMESTAMP('2015-11-10 17:53:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;



-- modified after Tobi's suggestion



-- 11.11.2015 11:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when (select C_FlatrateTerm_ID from M_Material_Tracking where M_Material_Tracking_ID=@M_Material_Tracking_ID@) IS NULL then(select ad_color_id from ad_color where name = ''Red'')  end as ad_color_id',Updated=TO_TIMESTAMP('2015-11-11 11:52:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 11.11.2015 12:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when (select C_Flatrate_Term_ID from M_Material_Tracking where M_Material_Tracking_ID=@M_Material_Tracking_ID@) <=0 then(select ad_color_id from ad_color where name = ''Red'')  end as ad_color_id',Updated=TO_TIMESTAMP('2015-11-11 12:23:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 11.11.2015 12:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when (select C_Flatrate_Term_ID from M_Material_Tracking where M_Material_Tracking_ID=@M_Material_Tracking_ID@)  IS NULL  then(select ad_color_id from ad_color where name = ''Red'')  end as ad_color_id',Updated=TO_TIMESTAMP('2015-11-11 12:24:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 11.11.2015 12:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when (select C_Flatrate_Term_ID from M_Material_Tracking where M_Material_Tracking_ID=@M_Material_Tracking_ID@) <=0  then(select ad_color_id from ad_color where name = ''Red'')  end as ad_color_id',Updated=TO_TIMESTAMP('2015-11-11 12:24:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 11.11.2015 12:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic='select case when (select C_Flatrate_Term_ID from M_Material_Tracking where M_Material_Tracking_ID=@M_Material_Tracking_ID@)  IS NULL   then  (select ad_color_id from ad_color where name = ''Rot'')  end as ad_color_id',Updated=TO_TIMESTAMP('2015-11-11 12:26:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;




-- 12.11.2015 00:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and c.M_QualityInsp_LagerKonf_ID =coalesce( (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)), null))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)',Updated=TO_TIMESTAMP('2015-11-12 00:53:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 00:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and c.M_QualityInsp_LagerKonf_ID =coalesce( (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@), null))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)',Updated=TO_TIMESTAMP('2015-11-12 00:55:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 00:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and c.M_QualityInsp_LagerKonf_ID =coalesce( (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@), null)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)',Updated=TO_TIMESTAMP('2015-11-12 00:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 00:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and c.M_QualityInsp_LagerKonf_ID =coalesce( (select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@), 0)))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
)',Updated=TO_TIMESTAMP('2015-11-12 00:57:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

-- 12.11.2015 01:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS (
	SELECT 1
	FROM M_Material_Tracking mt
	INNER JOIN M_Attribute a on a.M_Attribute_ID=M_AttributeValue.M_Attribute_ID
	INNER JOIN C_Flatrate_Term term on mt.C_Flatrate_Term_ID = term.C_Flatrate_Term_ID
	WHERE a.Value=''M_Material_Tracking_ID''
	AND mt.M_Material_Tracking_ID::VARCHAR=M_AttributeValue.Value
	AND mt.C_BPartner_ID=@C_BPartner_ID@
	AND mt.M_Product_ID=@M_Product_ID@
	AND term.DocStatus = ''CO''
)',Updated=TO_TIMESTAMP('2015-11-12 01:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540259
;

-- 12.11.2015 01:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543700,0,TO_TIMESTAMP('2015-11-12 01:50:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','Auftrag {0} can nicht fertig gestellt werden, weil bei Position {1} noch kein Material-Vorgang (Karotten-ID) hinterlegt ist.','E',TO_TIMESTAMP('2015-11-12 01:50:18','YYYY-MM-DD HH24:MI:SS'),100,'M_Material_Tracking_Set_In_Orderline')
;

-- 12.11.2015 01:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543700 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 12.11.2015 11:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists
(
	select 1 
	from C_FLatrate_Term t
	where  (exists (select 1 from C_Flatrate_Conditions c where c.C_Flatrate_Conditions_ID = t.C_Flatrate_Conditions_ID and (
		c.M_QualityInsp_LagerKonf_ID =(select lkv.M_QualityInsp_LagerKonf_ID from M_QualityInsp_LagerKonf_Version lkv where lkv.M_QualityInsp_LagerKonf_Version_ID = @M_QualityInsp_LagerKonf_Version_ID/-1@)
		or
		c.M_QualityInsp_LagerKonf_ID <= 0)
		))
	and t.M_Product_ID = @M_Product_ID/-1@
	and t.Bill_BPartner_ID = @C_BPartner_ID/-1@
	
)',Updated=TO_TIMESTAMP('2015-11-12 11:56:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540311
;

