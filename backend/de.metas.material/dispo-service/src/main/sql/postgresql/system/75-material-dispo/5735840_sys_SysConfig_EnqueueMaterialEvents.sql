-- 2024-10-04T14:06:53.246Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541742,'O',TO_TIMESTAMP('2024-10-04 16:06:52','YYYY-MM-DD HH24:MI:SS'),100,'Determines if material-events (de.metas.material.event.MaterialEvent) are enqueued for processing, or ignored.
They can be ignored if neither Material-Cockpit nor Material-Dispo are used.','D','Y','de.metas.material.event.EnqueueEvents',TO_TIMESTAMP('2024-10-04 16:06:52','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2024-10-04T14:07:40.138Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Determines if material-events (de.metas.material.event.MaterialEvent) are enqueued for processing, or ignored.
They can be ignored if neither Material-Cockpit nor Material-Dispo are used.
This property can be overridden on Client- and Org-Level.',Updated=TO_TIMESTAMP('2024-10-04 16:07:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541742
;

