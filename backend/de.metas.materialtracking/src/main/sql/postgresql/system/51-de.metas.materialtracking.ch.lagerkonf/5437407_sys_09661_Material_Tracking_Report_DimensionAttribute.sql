-- 16.12.2015 17:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DIM_Dimension_Spec (AD_Client_ID,AD_Org_ID,Created,CreatedBy,DIM_Dimension_Spec_ID,DIM_Dimension_Type_ID,IsActive,IsIncludeEmpty,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2015-12-16 17:29:19','YYYY-MM-DD HH24:MI:SS'),100,540006,540000,'Y','Y','M_Material_Tracking_Report_Dimension',TO_TIMESTAMP('2015-12-16 17:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2015 17:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DIM_Dimension_Spec_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,DIM_Dimension_Spec_Attribute_ID,DIM_Dimension_Spec_ID,IsActive,IsIncludeAllAttributeValues,IsValueAggregate,M_Attribute_ID,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2015-12-16 17:29:46','YYYY-MM-DD HH24:MI:SS'),100,540014,540006,'Y','Y','N',1000002,TO_TIMESTAMP('2015-12-16 17:29:46','YYYY-MM-DD HH24:MI:SS'),100)
;


UPDATE DIM_Dimension_Spec
SET InternalName = 'M_Material_Tracking_Report_Dimension'
WHERE DIM_Dimension_Spec_ID = 540006
;

-- 16.12.2015 17:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540929,'S',TO_TIMESTAMP('2015-12-16 17:37:02','YYYY-MM-DD HH24:MI:SS'),100,'The InternalName of the M_Material_Tracking_Report_Dimension','de.metas.materialtracking.ch.lagerkonf','Y','de.metas.materialtracking.impl.MaterialTrackingBL.M_Material_Tracking_Report_Dimension',TO_TIMESTAMP('2015-12-16 17:37:02','YYYY-MM-DD HH24:MI:SS'),100,'M_Material_Tracking_Report_Dimension')
;

