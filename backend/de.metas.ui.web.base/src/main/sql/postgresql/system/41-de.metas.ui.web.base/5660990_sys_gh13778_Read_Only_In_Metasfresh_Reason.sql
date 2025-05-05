/*
 * #%L
 * de.metas.ui.web.base
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

/*
 * #%L
 * de.metas.ui.web.base
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

-- Value: externalReferenceReadOnlyInMetasfreshReason
-- 2022-10-18T13:33:31.199653Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545169,0,TO_TIMESTAMP('2022-10-18 16:33:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Dokument {0} kann nicht gelöscht werden, da es extern referenziert und als schreibgeschützt gekennzeichnet ist.','I',TO_TIMESTAMP('2022-10-18 16:33:30','YYYY-MM-DD HH24:MI:SS'),100,'externalReferenceReadOnlyInMetasfreshReason')
;

-- 2022-10-18T13:33:31.199653Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545169 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: externalReferenceReadOnlyInMetasfreshReason
-- 2022-10-18T13:34:05.757030700Z
UPDATE AD_Message_Trl SET MsgText='Das Dokument ist extern referenziert und als schreibgeschützt gekennzeichnet.',Updated=TO_TIMESTAMP('2022-10-18 16:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545169
;

-- Value: externalReferenceReadOnlyInMetasfreshReason
-- 2022-10-18T13:34:10.228671300Z
UPDATE AD_Message SET MsgText='Das Dokument ist extern referenziert und als schreibgeschützt gekennzeichnet.',Updated=TO_TIMESTAMP('2022-10-18 16:34:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545169
;

-- Value: externalReferenceReadOnlyInMetasfreshReason
-- 2022-10-18T13:34:26.253469Z
UPDATE AD_Message_Trl SET MsgText='Das Dokument ist extern referenziert und als schreibgeschützt gekennzeichnet.',Updated=TO_TIMESTAMP('2022-10-18 16:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545169
;

-- Value: externalReferenceReadOnlyInMetasfreshReason
-- 2022-10-18T13:34:48.593649900Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Document is externally referenced and marked as read-only.',Updated=TO_TIMESTAMP('2022-10-18 16:34:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545169
;

-- Value: externalReferenceReadOnlyInMetasfreshReason
-- 2022-10-18T13:35:00.535995Z
UPDATE AD_Message_Trl SET MsgText='Document is externally referenced and marked as read-only.',Updated=TO_TIMESTAMP('2022-10-18 16:35:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545169
;

-- Value: externalReferenceReadOnlyInMetasfreshReason
-- 2022-10-18T14:05:59.683757100Z
UPDATE AD_Message_Trl SET MsgText='The document is externally referenced and marked as read-only.',Updated=TO_TIMESTAMP('2022-10-18 17:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545169
;

-- Value: externalReferenceReadOnlyInMetasfreshReason
-- 2022-10-18T14:06:07.979606900Z
UPDATE AD_Message_Trl SET MsgText='The document is externally referenced and marked as read-only.',Updated=TO_TIMESTAMP('2022-10-18 17:06:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545169
;

-- Value: ExternalReferenceReadOnlyInMetasfreshReason
-- 2022-10-20T13:00:37.566430800Z
UPDATE AD_Message SET Value='ExternalReferenceReadOnlyInMetasfreshReason',Updated=TO_TIMESTAMP('2022-10-20 16:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545169
;

-- Value: ExternalReferenceReadOnlyInMetasfreshReason
-- 2022-10-20T13:01:20.411465500Z
UPDATE AD_Message_Trl SET MsgText='Das Datensatz ist extern referenziert und als schreibgeschützt gekennzeichnet.',Updated=TO_TIMESTAMP('2022-10-20 16:01:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545169
;

-- Value: ExternalReferenceReadOnlyInMetasfreshReason
-- 2022-10-20T13:01:38.420646500Z
UPDATE AD_Message_Trl SET MsgText='Das Datensatz ist extern referenziert und als schreibgeschützt gekennzeichnet.',Updated=TO_TIMESTAMP('2022-10-20 16:01:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545169
;

-- Value: ExternalReferenceReadOnlyInMetasfreshReason
-- 2022-10-20T13:01:52.710437700Z
UPDATE AD_Message_Trl SET MsgText='The record is externally referenced and marked as read-only.',Updated=TO_TIMESTAMP('2022-10-20 16:01:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545169
;

-- Value: ExternalReferenceReadOnlyInMetasfreshReason
-- 2022-10-20T13:01:58.588133900Z
UPDATE AD_Message_Trl SET MsgText='The record is externally referenced and marked as read-only.',Updated=TO_TIMESTAMP('2022-10-20 16:01:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545169
;