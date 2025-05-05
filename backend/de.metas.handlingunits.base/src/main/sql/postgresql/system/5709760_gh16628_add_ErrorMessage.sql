/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

-- Value: de.metas.handlingunits.rest_api.ExternalLotNumber_Missing_QRCode
-- 2023-11-07T17:54:07.660Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545358,0,TO_TIMESTAMP('2023-11-07 19:54:07','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','ExternalLotNumber not found in the provided QR code.','E',TO_TIMESTAMP('2023-11-07 19:54:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.rest_api.ExternalLotNumber_Missing_QRCode')
;

-- 2023-11-07T17:54:07.680Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545358 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.rest_api.ExternalLotNumber_Missing_QRCode
-- 2023-11-07T17:54:48.003Z
UPDATE AD_Message_Trl SET MsgText='ExterneLotNummer nicht im angegebenen QR-Code gefunden.',Updated=TO_TIMESTAMP('2023-11-07 19:54:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545358
;

-- Value: de.metas.handlingunits.rest_api.ExternalLotNumber_Missing_QRCode
-- 2023-11-07T17:54:51.694Z
UPDATE AD_Message_Trl SET MsgText='ExterneLotNummer nicht im angegebenen QR-Code gefunden.',Updated=TO_TIMESTAMP('2023-11-07 19:54:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545358
;

-- Value: de.metas.handlingunits.rest_api.ExternalLotNumber_Missing_QRCode
-- 2023-11-07T17:54:55.607Z
UPDATE AD_Message_Trl SET MsgText='ExterneLotNummer nicht im angegebenen QR-Code gefunden.',Updated=TO_TIMESTAMP('2023-11-07 19:54:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545358
;

