/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- 2022-08-19T09:26:07.856Z
UPDATE AD_Message SET MsgText='The sysconfig "{0}" is not set. So Camel-ExternalSystem will not be able to authenticate into metasfresh.',Updated=TO_TIMESTAMP('2022-08-19 12:26:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545140
;

-- 2022-08-19T09:26:17.733Z
UPDATE AD_Message_Trl SET MsgText='The sysconfig "{0}" is not set. So Camel-ExternalSystem will not be able to authenticate into metasfresh.',Updated=TO_TIMESTAMP('2022-08-19 12:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545140
;

-- 2022-08-19T09:26:32.698Z
UPDATE AD_Message_Trl SET MsgText='Die Sysconfig "{0}" ist nicht gesetzt. Daher kann sich Camel-ExternalSystem nicht bei metasfresh authentifizieren.',Updated=TO_TIMESTAMP('2022-08-19 12:26:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545140
;

-- 2022-08-19T09:26:48.804Z
UPDATE AD_Message_Trl SET MsgText='De sysconfig "{0}" is niet ingesteld. Dus Camel-ExternalSystem zal niet in staat zijn om zich te authenticeren in metasfresh.',Updated=TO_TIMESTAMP('2022-08-19 12:26:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545140
;

-- 2022-08-19T09:27:00.036Z
UPDATE AD_Message_Trl SET MsgText='Die Sysconfig "{0}" ist nicht gesetzt. Daher kann sich Camel-ExternalSystem nicht bei metasfresh authentifizieren.',Updated=TO_TIMESTAMP('2022-08-19 12:27:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545140
;

-- 2022-08-19T09:29:17.849Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545148,0,TO_TIMESTAMP('2022-08-19 12:29:17','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Could not find any record in Metasfresh for provided {0}. So Camel-ExternalSystem will not be able to authenticate into metasfresh.','I',TO_TIMESTAMP('2022-08-19 12:29:17','YYYY-MM-DD HH24:MI:SS'),100,'External_Systems_Authorization_SysConfig_Value_Does_Not_Exist')
;

-- 2022-08-19T09:29:17.852Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545148 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

