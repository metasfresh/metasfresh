
--
-- material-tracking fields: improve readbility (some important info was cut off because there are too many fields in one row)
--
-- 01.12.2015 17:55
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Name='Vertragsperiode',Updated=TO_TIMESTAMP('2015-12-01 17:55:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

-- 01.12.2015 17:55
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=556387
;

-- 01.12.2015 18:13
-- URL zum Konzept
UPDATE AD_Field SET DisplayLength=100, IsSameLine='N',Updated=TO_TIMESTAMP('2015-12-01 18:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554790
;

-- 01.12.2015 18:16
-- URL zum Konzept
UPDATE AD_Field SET DisplayLength=100,Updated=TO_TIMESTAMP('2015-12-01 18:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554696
;

-- 01.12.2015 18:16
-- URL zum Konzept
UPDATE AD_Field SET DisplayLength=100, IsSameLine='N',Updated=TO_TIMESTAMP('2015-12-01 18:16:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554697
;

-- 01.12.2015 18:17
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2015-12-01 18:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554751
;

-- 01.12.2015 18:17
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=35, SeqNoGrid=35,Updated=TO_TIMESTAMP('2015-12-01 18:17:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556347
;


--
-- C_Flatrate_Term: show product also for quality-inspection-terms
--
-- 01.12.2015 18:08
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@=''Subscr''|@Type_Conditions@=''QualityBsd''',Updated=TO_TIMESTAMP('2015-12-01 18:08:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550474
;


--
-- make M_MaterialTracking window-type transactional
--
-- 01.12.2015 18:31
-- URL zum Konzept
UPDATE AD_Window SET WindowType='T',Updated=TO_TIMESTAMP('2015-12-01 18:31:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540226
;

--
--M_Material_Tracking: make ValidFrom and ValidTo read-only when a term is set 
--
-- 01.12.2015 18:33
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@C_Flatrate_Term_ID@ > 0',Updated=TO_TIMESTAMP('2015-12-01 18:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551119
;

-- 01.12.2015 18:33
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@C_Flatrate_Term_ID@ > 0',Updated=TO_TIMESTAMP('2015-12-01 18:33:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551120
;

--
-- validation-rule for M_Material_Tracking.M_QualityInsp_LagerKonf_Version
--
-- 01.12.2015 18:37
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540313,'(@ValidFrom@ IS NULL OR M_QualityInsp_LagerKonf_Version.ValidFrom>=@ValidFrom@)
AND
(@ValidTo@ IS NULL OR M_QualityInsp_LagerKonf_Version.ValidTo>=@ValidTo@)',TO_TIMESTAMP('2015-12-01 18:37:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking.ch.lagerkonf','Y','M_QualityInsp_LagerKonf_Version for material tracking','S',TO_TIMESTAMP('2015-12-01 18:37:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.12.2015 18:38
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540313,Updated=TO_TIMESTAMP('2015-12-01 18:38:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551273
;

-- 01.12.2015 18:43
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(''@ValidFrom@'' IS NULL OR M_QualityInsp_LagerKonf_Version.ValidFrom>=''@ValidFrom@'')
AND
(''@ValidTo@'' IS NULL OR M_QualityInsp_LagerKonf_Version.ValidTo>=''@ValidTo@'')',Updated=TO_TIMESTAMP('2015-12-01 18:43:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540313
;

--
-- material-tracking-term field: don'T make a fuss about processed trackings
--
-- 01.12.2015 18:44
-- URL zum Konzept
UPDATE AD_Field SET ColorLogic='select case when (select C_Flatrate_Term_ID from M_Material_Tracking where M_Material_Tracking_ID=@M_Material_Tracking_ID@) IS NULL and ''@Processed@''=''N'' then  (select ad_color_id from ad_color where name = ''Rot'') else -1 end as ad_color_id',Updated=TO_TIMESTAMP('2015-12-01 18:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556387
;

--
-- rename process C_FlatrateTerm_Create_For_MaterialTracking
--
-- 01.12.2015 18:47
-- URL zum Konzept
UPDATE AD_Process SET Name='Laufenden Vertrag erstellen', Value='C_FlatrateTerm_Create_For_MaterialTracking',Updated=TO_TIMESTAMP('2015-12-01 18:47:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540626
;

-- 01.12.2015 18:47
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540626
;

--
-- customize M_MAterialTracking.Processed
--
-- 02.12.2015 06:10
-- URL zum Konzept
UPDATE AD_Field SET Description='Sagt aus, ob weitere Bestellungen, Wareneingänge usw zu diesem Vorgang erfasst werden sollen.', Help=NULL, IsCentrallyMaintained='N', Name='Material-Vorgang beendet',Updated=TO_TIMESTAMP('2015-12-02 06:10:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554700
;

-- 02.12.2015 06:10
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=554700
;

--
-- erorr-message for M_MaterialTracking.Lot
--
-- 02.12.2015 06:24
-- URL zum Konzept
UPDATE AD_Index_Table SET ErrorMsg='Die Karotten-ID mus eindeutig sein',Updated=TO_TIMESTAMP('2015-12-02 06:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540318
;

-- 02.12.2015 06:24
-- URL zum Konzept
UPDATE AD_Index_Table_Trl SET IsTranslated='N' WHERE AD_Index_Table_ID=540318
;

