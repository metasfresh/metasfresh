--
-- Script: /tmp/webui_migration_scripts_2023-03-10_2813194925603852044/5681540_migration_2023-03-12_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- 2023-03-12T12:08:14.132Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541600,'S',TO_TIMESTAMP('2023-03-12 13:08:14','YYYY-MM-DD HH24:MI:SS'),100,'Manage filtering with attributes in HU reservation screen','de.metas.ui.web','Y','de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.ConsiderAttributes',TO_TIMESTAMP('2023-03-12 13:08:14','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

