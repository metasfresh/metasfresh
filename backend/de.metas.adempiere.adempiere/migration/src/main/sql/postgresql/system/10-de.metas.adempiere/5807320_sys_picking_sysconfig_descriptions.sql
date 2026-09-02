-- me03 #30326: document the two picking sysconfigs used by the Workplace auto-assign rollout.
-- AD_SysConfig has no _Trl companion -> description text directly in German (base-language rule).

-- 541788: de.metas.handlingunits.picking.job_schedule.service.commands.PickingJobScheduleAutoAssignCommand.QueryLimit
UPDATE AD_SysConfig SET Description='Maximale Anzahl Lieferdispositionen, die ein Lauf der automatischen Arbeitsplatz-Zuordnung (Traffic Management) lädt und zuordnet. Standard: 1000.', Updated=TO_TIMESTAMP('2026-06-11 14:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_SysConfig_ID=541788
;
-- 541740: de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
UPDATE AD_SysConfig SET Description='Wenn gesetzt (Y), werden nach Fertigstellung einer Kommissionier-Lieferung automatisch Packstücke erzeugt und dem täglichen Speditionsauftrag hinzugefügt. Standard: N.', Updated=TO_TIMESTAMP('2026-06-11 14:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_SysConfig_ID=541740
;
