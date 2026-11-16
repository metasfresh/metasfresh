-- me03 25618 / F19100 — Stock per week.
-- IDs allocated from idserver.metas.de on 2026-06-03:
--   AD_MigrationScript sequence: 5806100 (filename prefix)
--   AD_SysConfig_ID            : 541811
--
-- Forward ISO-week horizon shown by the "Stock per week" window / MD_Stock_PerWeek_V view.
-- Read by MD_Stock_PerWeek_V; default 12 forward weeks (=> 13 rows incl. current week).

INSERT INTO AD_SysConfig
  (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
   Name, Value, Description, EntityType)
VALUES
  (541811 /*From ID Server*/, 0, 0, 'Y',
   TO_TIMESTAMP('2026-06-03 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-06-03 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
   'de.metas.material.stockperweek.HorizonWeeks', '12',
   'Number of forward ISO weeks shown by the Stock-per-week window (MD_Stock_PerWeek_V)',
   'de.metas.material.dispo');