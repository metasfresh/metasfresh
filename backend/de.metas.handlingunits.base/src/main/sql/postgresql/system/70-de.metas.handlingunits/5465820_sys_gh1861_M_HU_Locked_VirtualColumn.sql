-- 2017-06-20T17:39:29.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(CASE WHEN EXISTS ( select 1 from T_Lock l where l.AD_Table_ID=540516 and l.Record_ID=M_HU_ID ) THEN ''Y'' ELSE ''N'' END)', IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-06-20 17:39:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550691
;


-- 2017-06-20T18:03:13.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541151,'S',TO_TIMESTAMP('2017-06-20 18:03:13','YYYY-MM-DD HH24:MI:SS'),100,'Determines if the system uses the virtual column M_HU.Locked. This boosts performance if running "not-near" the DB, but can introduce problems with stale M_HUs.
Values: A = "Always"; N = "Never"; C = "if run from swing-client"  (also lower-case is posssible).
The default if not set, or for any other value is "Never"','de.metas.handlingunits','Y','de.metas.handlingunits.HULockBL.UseVirtualColumn',TO_TIMESTAMP('2017-06-20 18:03:13','YYYY-MM-DD HH24:MI:SS'),100,'C')
;

-- 2017-06-20T18:06:06.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Determines if the system uses the virtual column M_HU.Locked. This boosts performance if running "not-near" the DB, but can introduce problems with stale M_HUs.
Values: A = "Always"; N = "Never"; C = "if run from swing-client"  (also lower-case is posssible).
The default if not set, or for any other value is "Never"; see https://github.com/metasfresh/metasfresh/issues/1861',Updated=TO_TIMESTAMP('2017-06-20 18:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541151
;

