
CREATE table backup.BKP_M_HU_PI_Attribute_gh11159_25052021
AS
    SELECT * FROM M_HU_PI_Attribute;
	
	


-------------- Packing instructions version for CUs (a.k.a. virtual HUs)




-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='N',
isonlyifinproductattributeset = 'N',
 Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000020 -- Gewicht Brutto kg
;

-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='Y', 
isonlyifinproductattributeset = 'N',
Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000021 -- Gewicht Tara on the M_HU_PI_Version 101 (no package-item) - shall be read-only
;


-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=540069 -- Gewicht Netto
;



----Packing Item Template


-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='N', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000023 -- Gewicht Brutto kg
;
-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000025 -- Gewicht Tara
;
-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000024 -- Gewicht Netto
;





-- 2021-05-26T12:04:46.984Z
-- URL zum Konzept
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_UOM_ID,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2021-05-26 15:04:45','YYYY-MM-DD HH24:MI:SS'),100,540017,540027,'Y','Y','Y','N','N','N',540016,540070,100,'NONE',9110,TO_TIMESTAMP('2021-05-26 15:04:45','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2021-05-26T12:05:13.940Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute SET SeqNo=35,Updated=TO_TIMESTAMP('2021-05-26 15:05:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540070
;


/*
 I found those - not sure we need them
 -- 2021-05-25T11:49:26.490Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute SET IsReadOnly='N',Updated=TO_TIMESTAMP('2021-05-25 13:49:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540069
;

-- 2021-05-25T11:49:26.656Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-25 13:49:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540069
;

-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute SET IsReadOnly='N',Updated=TO_TIMESTAMP('2021-05-25 13:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540069
;
 */


/*
not yet sure these make a lot of sense
-- 2021-05-26T17:33:08.143Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-26 19:33:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 
WHERE M_HU_PI_Attribute_ID=1000021  -- Gewicht Tara
;

-- Gewicht Tara adjust for "no-packing-item HU-PI
-- 2021-05-26T17:46:31.504Z
-- URL zum Konzept
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (0,0,TO_TIMESTAMP('2021-05-26 19:46:31','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','N','N','N','N',540038,540074,101,'NONE',25,TO_TIMESTAMP('2021-05-26 19:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

update M_HU_PI_Attribute set ad_client_id=1000000, m_attribute_id=540016, isinstanceattribute='Y', useinasi='Y', c_uom_id=540017 where m_hu_pi_attribute_id=540074;
*/
-- this is crucial to avoid an error when creating the actual receipt M_InOut; without it, the cretions fails because the weight attributes were not loaded from the packing instruction
UPDATE m_hu_pi_attribute
SET useinasi='Y',
    Updated=TO_TIMESTAMP('2021-05-26 19:33:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE m_attribute_id IN (540005,--WeightGross
                         540006,--WeightTare
                         540004,--WeightNet
                         540016--WeightTareAdjust
    )
  AND useinasi = 'N';


-- 2021-05-26T19:19:22.145Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='!! Important to set to Y for attributes that can be set on material receipt !! (If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer)',Updated=TO_TIMESTAMP('2021-05-26 21:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542574 AND AD_Language='en_US'
;

-- 2021-05-26T19:19:22.305Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542574,'en_US')
;



-- 2021-05-26T19:20:04.593Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-26 21:20:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542574 AND AD_Language='en_US'
;

-- 2021-05-26T19:20:04.595Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542574,'en_US')
;

-- 2021-05-26T19:20:17.951Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='!! Important to set to Y for attributes that can be set on material receipt !! (If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer)',Updated=TO_TIMESTAMP('2021-05-26 21:20:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542574 AND AD_Language='en_GB'
;

-- 2021-05-26T19:20:17.952Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542574,'en_GB')
;

-- 2021-05-26T19:20:18.088Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-26 21:20:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542574 AND AD_Language='en_GB'
;

-- 2021-05-26T19:20:18.088Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542574,'en_GB')
;

-- 2021-05-26T19:20:44.517Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='!! Important to set to Y for attributes that can be set on material receipt !! (If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer)',Updated=TO_TIMESTAMP('2021-05-26 21:20:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542574 AND AD_Language='de_DE'
;

-- 2021-05-26T19:20:44.518Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542574,'de_DE')
;

-- 2021-05-26T19:20:44.527Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(542574,'de_DE')
;

-- 2021-05-26T19:20:44.530Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='UseInASI', Name='Use in ASI', Description='!! Important to set to Y for attributes that can be set on material receipt !! (If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer)', Help=NULL WHERE AD_Element_ID=542574
;

-- 2021-05-26T19:20:44.532Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='UseInASI', Name='Use in ASI', Description='!! Important to set to Y for attributes that can be set on material receipt !! (If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer)', Help=NULL, AD_Element_ID=542574 WHERE UPPER(ColumnName)='USEINASI' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-26T19:20:44.534Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='UseInASI', Name='Use in ASI', Description='!! Important to set to Y for attributes that can be set on material receipt !! (If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer)', Help=NULL WHERE AD_Element_ID=542574 AND IsCentrallyMaintained='Y'
;

-- 2021-05-26T19:20:44.535Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Use in ASI', Description='!! Important to set to Y for attributes that can be set on material receipt !! (If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer)', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542574) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542574)
;

-- 2021-05-26T19:20:44.563Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Use in ASI', Description='!! Important to set to Y for attributes that can be set on material receipt !! (If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer)', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542574
;

-- 2021-05-26T19:20:44.564Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Use in ASI', Description='!! Important to set to Y for attributes that can be set on material receipt !! (If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer)', Help=NULL WHERE AD_Element_ID = 542574
;

-- 2021-05-26T19:20:44.565Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Use in ASI', Description = '!! Important to set to Y for attributes that can be set on material receipt !! (If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer)', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542574
;

-- 2021-05-26T19:20:51.030Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='!! Important to set to Y for attributes that can be set on material receipt !! (If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer)',Updated=TO_TIMESTAMP('2021-05-26 21:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542574 AND AD_Language='de_CH'
;

-- 2021-05-26T19:20:51.030Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542574,'de_CH')
;
