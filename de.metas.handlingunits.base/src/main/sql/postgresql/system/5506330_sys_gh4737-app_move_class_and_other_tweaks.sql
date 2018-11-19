-- 2018-11-13T06:02:52.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET InternalName='RV_M_Material_Tracking_HU_Details',Updated=TO_TIMESTAMP('2018-11-13 06:02:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540702
;

-- 2018-11-13T06:04:39.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET TechnicalNote='HUs are joined to material trackings via ''M_Material_Tracking_ID'' HU-Attribute.',Updated=TO_TIMESTAMP('2018-11-13 06:04:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540682
;

-- 2018-11-13T11:43:47.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_JavaClass SET Classname='de.metas.handlingunits.materialtracking.spi.impl.attribute.MaterialTrackingAttributeValuesProviderFactory', IsInterface='N',Updated=TO_TIMESTAMP('2018-11-13 11:43:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JavaClass_ID=540050
;

