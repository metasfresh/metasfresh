-- 2024-08-15T10:22:49.704Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541729,'S',TO_TIMESTAMP('2024-08-15 13:22:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','de.metas.ui.web.material.cockpit.SYS_CONFIG_SHOW_QTY_IN_TU_UOM',TO_TIMESTAMP('2024-08-15 13:22:49','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2024-08-15T17:01:05.193Z
UPDATE AD_SysConfig SET ConfigurationLevel='S',
                        Description='When enabled, the quantities showed in the Material Cockpit window will be computed and displayed using the default packing of each product. (e.g. the default packing of product ''P1'' is box ''B1'' with a 10 pieces capacity => a stock entry with 25 pieces of ''P1'' will be displayed as 3 ''B1'' boxes).',
                        Updated=TO_TIMESTAMP('2024-08-15 20:01:05','YYYY-MM-DD HH24:MI:SS'),
                        UpdatedBy=100
                    WHERE AD_SysConfig_ID=541729
;

