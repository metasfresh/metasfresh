/*
 * #%L
 * de.metas.handlingunits.base
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

-- Value: de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG
-- 2025-04-02T15:58:11.516Z
UPDATE AD_Message_Trl SET MsgText='Picking shall be DONE on all steps in order to complete the job.',Updated=TO_TIMESTAMP('2025-04-02 15:58:11.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545471
;

-- 2025-04-02T15:58:11.527Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG
-- 2025-04-02T16:01:16.689Z
UPDATE AD_Message SET MsgText='Die Kommissionierung muss in allen Schritten abgeschlossen werden,  um den Auftrag abzuschließen.',Updated=TO_TIMESTAMP('2025-04-02 16:01:16.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545471
;

-- 2025-04-02T16:01:16.717Z
UPDATE AD_Message_Trl trl SET MsgText='Die Kommissionierung muss in allen Schritten abgeschlossen werden,  um den Auftrag abzuschließen.' WHERE AD_Message_ID=545471 AND AD_Language='de_DE'
;

-- Value: de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG
-- 2025-04-02T16:01:27.534Z
UPDATE AD_Message_Trl SET MsgText='Die Kommissionierung muss in allen Schritten abgeschlossen werden,  um den Auftrag abzuschließen.',Updated=TO_TIMESTAMP('2025-04-02 16:01:27.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545471
;

-- 2025-04-02T16:01:27.535Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG
-- 2025-04-02T16:01:30.460Z
UPDATE AD_Message_Trl SET MsgText='Die Kommissionierung muss in allen Schritten abgeschlossen werden,  um den Auftrag abzuschließen.',Updated=TO_TIMESTAMP('2025-04-02 16:01:30.460000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545471
;