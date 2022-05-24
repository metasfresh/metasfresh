/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

-- 2021-10-28T07:16:55.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540013,542915,TO_TIMESTAMP('2021-10-28 10:16:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Menge',TO_TIMESTAMP('2021-10-28 10:16:55','YYYY-MM-DD HH24:MI:SS'),100,'QT','Quantity')
;

-- 2021-10-28T07:16:55.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542915 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-10-28T07:17:05.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Quantity',Updated=TO_TIMESTAMP('2021-10-28 10:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542915
;

-- 2021-10-28T07:17:36.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Mengenänderung',Updated=TO_TIMESTAMP('2021-10-28 10:17:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542915
;

-- 2021-10-28T07:17:38.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Mengenänderung ',Updated=TO_TIMESTAMP('2021-10-28 10:17:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542915
;

-- 2021-10-28T07:17:42.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Mengenänderung',Updated=TO_TIMESTAMP('2021-10-28 10:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542915
;

-- 2021-10-28T07:17:47.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Mengenänderung',Updated=TO_TIMESTAMP('2021-10-28 10:17:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542915
;

-- 2021-10-28T07:18:25.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540013,542916,TO_TIMESTAMP('2021-10-28 10:18:24','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Preisänderung',TO_TIMESTAMP('2021-10-28 10:18:24','YYYY-MM-DD HH24:MI:SS'),100,'P','Price')
;

-- 2021-10-28T07:18:25.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542916 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-10-28T07:18:36.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2021-10-28 10:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542916
;

-- 2021-10-28T07:18:47.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Price change',Updated=TO_TIMESTAMP('2021-10-28 10:18:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542916
;

