/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2026 metas GmbH
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

-- One-time, idempotent, non-destructive cleanup of the CheckProcessedAsynBatch backlog.
-- C_Async_Batch_Type.IsCheckProcessed (5812240/5812250) now gates enqueueing so that only
-- consumer types get a CheckProcessedAsynBatch work-package. This backlog cleanup closes the
-- already-accumulated stuck work-packages (and their orphan C_Async_Batch rows) for the
-- non-consumer types, using the exact same predicate as the enqueue gate:
--   IsCheckProcessed='N' AND (AD_Boilerplate_ID IS NULL OR AD_Boilerplate_ID<=0)
-- Only flips Processed='Y' on rows currently Processed='N' -- no DELETE, no other column touched.
-- Re-running is a no-op once every matching row is closed (WHERE Processed='N' guarantees this).

SELECT backup_table('c_queue_workpackage', '_20260704_cleanup_stuck_checkprocessed')
;

SELECT backup_table('c_async_batch', '_20260704_cleanup_stuck_checkprocessed')
;

-- 1) Close the stuck CheckProcessedAsynBatch work-packages of non-consumer async batch types.
UPDATE c_queue_workpackage wp
SET processed='Y', Updated=TO_TIMESTAMP('2026-07-04 11:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
FROM c_queue_packageprocessor pp, c_queue_element qe, c_async_batch b, c_async_batch_type t
WHERE pp.c_queue_packageprocessor_id=wp.c_queue_packageprocessor_id
  AND pp.classname LIKE '%CheckProcessedAsynBatch%'
  AND wp.processed='N'
  AND wp.iserror='N'
  AND qe.c_queue_workpackage_id=wp.c_queue_workpackage_id
  AND qe.ad_table_id=(SELECT ad_table_id FROM ad_table WHERE tablename='C_Async_Batch')
  AND b.c_async_batch_id=qe.record_id
  AND t.c_async_batch_type_id=b.c_async_batch_type_id
  AND t.ischeckprocessed='N'
  AND (t.ad_boilerplate_id IS NULL OR t.ad_boilerplate_id<=0)
;

-- 2) Close the orphan C_Async_Batch rows of non-consumer async batch types.
UPDATE c_async_batch b
SET processed='Y', Updated=TO_TIMESTAMP('2026-07-04 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
FROM c_async_batch_type t
WHERE t.c_async_batch_type_id=b.c_async_batch_type_id
  AND b.processed='N'
  AND t.ischeckprocessed='N'
  AND (t.ad_boilerplate_id IS NULL OR t.ad_boilerplate_id<=0)
;
