-- Run mode: SWING_CLIENT

-- Value: HU_PACK_INSTR_MATERIAL_LINE_NOT_FOUND
-- 2025-10-07T13:19:44.421Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545592,0,TO_TIMESTAMP('2025-10-07 13:19:44.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','NO_MATERIAL_PACK_INSTRUCTION_LINE_FOUND','Y','Keine "Material" Packvorschrift-Position für Packvorschrift {0} (ID={1}) gefunden. Betroffene HU-ID:{2}','E',TO_TIMESTAMP('2025-10-07 13:19:44.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HU_PACK_INSTR_MATERIAL_LINE_NOT_FOUND')
;

-- 2025-10-07T13:19:44.423Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545592 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: HU_PACK_INSTR_MATERIAL_LINE_NOT_FOUND
-- 2025-10-07T13:19:50.292Z
UPDATE AD_Message_Trl SET MsgText='No “Material” packaging instruction line found for the given packaging instruction (ID={1}), affecting HU {2}',Updated=TO_TIMESTAMP('2025-10-07 13:19:50.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545592
;

-- 2025-10-07T13:19:50.294Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ERR_LU_HAS_NO_TU_SUB_PACK_INSTR
-- 2025-10-07T13:39:02.865Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545593,0,TO_TIMESTAMP('2025-10-07 13:39:02.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','LU-Packvorschrift {0} hat keine TU-Unterpackvorschrift {1}','E',TO_TIMESTAMP('2025-10-07 13:39:02.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_LU_HAS_NO_TU_SUB_PACK_INSTR')
;

-- 2025-10-07T13:39:02.866Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545593 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_LU_HAS_NO_TU_SUB_PACK_INSTR
-- 2025-10-07T13:40:02.974Z
UPDATE AD_Message_Trl SET MsgText='LU packaging instruction {0} has no TU sub-packaging instruction {1}',Updated=TO_TIMESTAMP('2025-10-07 13:40:02.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545593
;

-- 2025-10-07T13:40:02.974Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG
-- 2025-10-07T13:46:30.066Z
UPDATE AD_Message SET ErrorCode='HU_CANNOT_BE_PICKED',Updated=TO_TIMESTAMP('2025-10-07 13:46:30.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545384
;

-- Value: de.metas.handlingunits.picking.job.TU_CANNOT_BE_PICKED_ERROR_MSG
-- 2025-10-07T13:47:02.703Z
UPDATE AD_Message SET ErrorCode='TU_CANNOT_BE_PICKED',Updated=TO_TIMESTAMP('2025-10-07 13:47:02.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545448
;

-- Value: de.metas.handlingunits.picking.job.PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG
-- 2025-10-07T13:47:22.364Z
UPDATE AD_Message SET ErrorCode='PICKING_UNIT_NOT_SUPPORTED',Updated=TO_TIMESTAMP('2025-10-07 13:47:22.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545449
;

-- Value: de.metas.handlingunits.picking.job.QTY_REJECTED_ALTERNATIVES_ERROR_MSG
-- 2025-10-07T13:47:37.018Z
UPDATE AD_Message SET ErrorCode='QTY_REJECTED_ALTERNATIVES',Updated=TO_TIMESTAMP('2025-10-07 13:47:37.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545451
;

-- Value: de.metas.handlingunits.picking.job.NO_QR_CODE_ERROR_MSG
-- 2025-10-07T13:47:49.955Z
UPDATE AD_Message SET ErrorCode='NO_QR_CODE',Updated=TO_TIMESTAMP('2025-10-07 13:47:49.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545453
;

-- Value: de.metas.handlingunits.picking.job.CANNOT_PACK_ERROR_MSG
-- 2025-10-07T13:48:03.842Z
UPDATE AD_Message SET ErrorCode='CANNOT_PACK',Updated=TO_TIMESTAMP('2025-10-07 13:48:03.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545461
;

-- Value: de.metas.handlingunits.picking.job.INVALID_NUMBER_QR_CODES_ERROR_MSG
-- 2025-10-07T13:48:19.306Z
UPDATE AD_Message SET ErrorCode='INVALID_NUMBER_QR_CODES',Updated=TO_TIMESTAMP('2025-10-07 13:48:19.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545463
;

-- Value: de.metas.handlingunits.picking.job.UNKNOWN_TARGET_LU_ERROR_MSG
-- 2025-10-07T13:48:30.540Z
UPDATE AD_Message SET ErrorCode='UNKNOWN_TARGET_LU',Updated=TO_TIMESTAMP('2025-10-07 13:48:30.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545464
;

-- Value: de.metas.handlingunits.picking.job.NOT_ENOUGH_TUS_ERROR_MSG
-- 2025-10-07T13:48:43.389Z
UPDATE AD_Message SET ErrorCode='NOT_ENOUGH_TUS',Updated=TO_TIMESTAMP('2025-10-07 13:48:43.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545465
;

-- Value: de.metas.handlingunits.picking.job.CATCH_WEIGHT_LM_QR_CODE_ERROR_MSG
-- 2025-10-07T13:49:14.557Z
UPDATE AD_Message SET ErrorCode='MISSING_CATCH_WEIGHT',Updated=TO_TIMESTAMP('2025-10-07 13:49:14.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545466
;

-- Value: de.metas.handlingunits.picking.job.NO_QTY_ERROR_MSG
-- 2025-10-07T13:49:41.655Z
UPDATE AD_Message SET ErrorCode='NO_QUANTITY',Updated=TO_TIMESTAMP('2025-10-07 13:49:41.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545468
;

-- Value: de.metas.handlingunits.picking.job.HU_NOT_IN_VALID_PICKING_LOCATOR
-- 2025-10-07T14:04:18.912Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545594,0,TO_TIMESTAMP('2025-10-07 14:04:18.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','HU_NOT_IN_VALID_PICKING_LOCATOR','Y','Die ausgewählte Handling Unit befindet sich nicht an einem gültigen Lagerort für diese Kommissionierung','E',TO_TIMESTAMP('2025-10-07 14:04:18.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.picking.job.HU_NOT_IN_VALID_PICKING_LOCATOR')
;

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

-- 2025-10-07T14:04:18.920Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545594 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.HU_NOT_IN_VALID_PICKING_LOCATOR
-- 2025-10-07T14:04:27.928Z
UPDATE AD_Message_Trl SET MsgText='The selected Handling Unit is not located in a valid storage location for this picking process',Updated=TO_TIMESTAMP('2025-10-07 14:04:27.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545594
;

-- 2025-10-07T14:04:27.929Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

