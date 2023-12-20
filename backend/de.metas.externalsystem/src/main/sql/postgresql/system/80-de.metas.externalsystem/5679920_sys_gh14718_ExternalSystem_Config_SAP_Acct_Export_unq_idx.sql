/*
 * #%L
 * de.metas.externalsystem
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

-- 2023-03-01T14:56:01.520Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540724,0,542316,TO_TIMESTAMP('2023-03-01 16:56:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Y','idx_unq_doctype_sap_confg_id','N',TO_TIMESTAMP('2023-03-01 16:56:01','YYYY-MM-DD HH24:MI:SS'),100,'isActive=''Y''')
;

-- 2023-03-01T14:56:01.548Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540724 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-03-01T14:56:29.514Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586229,541308,540724,0,TO_TIMESTAMP('2023-03-01 16:56:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2023-03-01 16:56:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T14:56:50.933Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586227,541309,540724,0,TO_TIMESTAMP('2023-03-01 16:56:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',20,TO_TIMESTAMP('2023-03-01 16:56:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T14:56:56.703Z
CREATE UNIQUE INDEX idx_unq_doctype_sap_confg_id ON ExternalSystem_Config_SAP_Acct_Export (C_DocType_ID,ExternalSystem_Config_SAP_ID) WHERE isActive='Y'
;

