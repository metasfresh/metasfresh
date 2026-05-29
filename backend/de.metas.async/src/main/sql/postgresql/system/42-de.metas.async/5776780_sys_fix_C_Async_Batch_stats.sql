/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


CREATE OR REPLACE VIEW fix.c_async_batch_computed_values AS
SELECT b.c_async_batch_id,
       MAX(wp.created)                          AS lastenqueued,
       MIN(wp.created)                          AS firstenqueued,
       COUNT(wp.c_queue_workpackage_id)         AS countenqueued,
       MAX(wp_processed.c_queue_workpackage_id) AS lastprocessed_workpackage_id,
       MAX(wp_processed.updated)                AS lastprocessed
FROM c_async_batch b
         JOIN c_queue_workpackage wp ON wp.c_async_batch_id = b.c_async_batch_id
         JOIN c_queue_workpackage wp_processed ON wp_processed.c_async_batch_id = b.c_async_batch_id AND wp_processed.processed = 'Y'
GROUP BY b.c_async_batch_id
;

COMMENT ON VIEW fix.c_async_batch_computed_values IS 'This view helps fixing the async batch statistics. 
Those statistic are essentioa for the CheckProcessedAsynBatchWorkpackageProcessor to do its job'
;

SELECT backup_table('c_async_batch', '', 'c_async_batch_20251113_fix_stats_columns')
;

UPDATE c_async_batch b
SET countenqueued                = f.countenqueued,
    firstenqueued                = f.firstenqueued,
    lastenqueued                 = f.lastenqueued,
    lastprocessed                = f.lastprocessed,
    lastprocessed_workpackage_id = f.lastprocessed_workpackage_id,
    updated='2025-11-13 17:33:00',
    updatedby=99
FROM fix.c_async_batch_computed_values f
WHERE f.c_async_batch_id = b.c_async_batch_id
  AND b.firstenqueued IS NULL
;
