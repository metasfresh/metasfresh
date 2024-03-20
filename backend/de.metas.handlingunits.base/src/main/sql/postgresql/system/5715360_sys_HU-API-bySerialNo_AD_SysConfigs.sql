-- 2024-01-15T15:23:53.829Z
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2024-01-15 16:23:53.751','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541679
;

-- 2024-01-15T15:24:14.489Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Contains comma separated values from AD_Reference_ID = 540478. If set, the HUs returned by ''de.metas.handlingunits.rest_api.HandlingUnitsRestController.getBySerialNo'' will be filtered against those statuses. (for example, for active and planning HUs the sys config must be set to: A,P)',Updated=TO_TIMESTAMP('2024-01-15 16:24:14.413','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541679
;

-- 2024-01-15T15:29:41.392Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541692,'O',TO_TIMESTAMP('2024-01-15 16:29:41.103','YYYY-MM-DD HH24:MI:SS.US'),100,'Contains comma separated values from AD_Attribute.Value. If set, the each HU returned by ''de.metas.handlingunits.rest_api.HandlingUnitsRestController.getBySerialNo'' contains the respectinve attributes, even if they have no value wihtin the respective HU.
(Note that we are non allowed to have no sysconfig-value, so it''s set to "<NONE>" right now)','de.metas.handlingunits','Y','de.metas.handlingunits.rest_api.bySerialNo.includedHUAttributesEvenIfEmpty',TO_TIMESTAMP('2024-01-15 16:29:41.103','YYYY-MM-DD HH24:MI:SS.US'),100,'<NONE>')
;

-- 2024-01-15T15:31:11.169Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Contains comma separated values from AD_Attribute.Value. If set, the each HU returned by ''de.metas.handlingunits.rest_api.HandlingUnitsRestController.getBySerialNo'' contains the respectinve attributes, even if they have no value wihtin the respective HU.
When this sysconfig is evaluated, the Client- and Org-ID of the invoking user (Env) is taken into account.
(Note that we are non allowed to have no sysconfig-value, so it''s set to "<NONE>" right now).',Updated=TO_TIMESTAMP('2024-01-15 16:31:11.093','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541692
;

-- 2024-01-15T15:31:43.123Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Contains comma separated values from AD_Reference_ID = 540478. If set, the HUs returned by ''de.metas.handlingunits.rest_api.HandlingUnitsRestController.getBySerialNo'' will be filtered against those statuses. (for example, for active and planning HUs the sys config must be set to: A,P)
When this sysconfig is evaluated, the Client- and Org-ID of the invoking user (Env) is taken into account.',Updated=TO_TIMESTAMP('2024-01-15 16:31:43.058','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541679
;

-- 2024-01-15T15:32:12.081Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Contains comma separated values from AD_Attribute.Value. If set, the each HU returned by ''de.metas.handlingunits.rest_api.HandlingUnitsRestController.getBySerialNo'' contains the respective attributes, even if they have no value within the respective HU.
When this sysconfig is evaluated, the Client- and Org-ID of the invoking user (Env) is taken into account.
(Note that we are not allowed to have an empty sysconfig-value, so it''s set to "<NONE>" right now).',Updated=TO_TIMESTAMP('2024-01-15 16:32:12.018','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541692
;

