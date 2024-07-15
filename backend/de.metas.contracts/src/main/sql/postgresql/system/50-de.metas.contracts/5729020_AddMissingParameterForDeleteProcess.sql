-- Run mode: SWING_CLIENT

-- Process: ModCntr_Specific_Delete_Price_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price_Selection)
-- ParameterName: M_Product_ID
-- 2024-07-15T06:58:47.843Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,585406,542864,30,'M_Product_ID',TO_TIMESTAMP('2024-07-15 09:58:47.688','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','U',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','Y','N','Produkt',10,TO_TIMESTAMP('2024-07-15 09:58:47.688','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-15T06:58:47.850Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542864 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-07-15T06:58:47.876Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(454)
;

-- Process: ModCntr_Specific_Delete_Price_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price_Selection)
-- ParameterName: M_Product_ID
-- 2024-07-15T06:59:17.288Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540272,Updated=TO_TIMESTAMP('2024-07-15 09:59:17.288','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542864
;

-- Process: ModCntr_Specific_Delete_Price_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price_Selection)
-- ParameterName: Price
-- 2024-07-15T06:59:29.222Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1416,0,585406,542865,12,'Price',TO_TIMESTAMP('2024-07-15 09:59:29.105','YYYY-MM-DD HH24:MI:SS.US'),100,'Preis','U',0,'Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','Y','N','Y','N','Preis',20,TO_TIMESTAMP('2024-07-15 09:59:29.105','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-15T06:59:29.223Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542865 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-07-15T06:59:29.225Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(1416)
;

-- Process: ModCntr_Specific_Delete_Price_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price_Selection)
-- ParameterName: MinValue
-- 2024-07-15T07:00:13.923Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,53400,0,585406,542866,22,'MinValue',TO_TIMESTAMP('2024-07-15 10:00:13.793','YYYY-MM-DD HH24:MI:SS.US'),100,'EE02',0,'Y','N','Y','N','Y','N','Mindestwert',30,TO_TIMESTAMP('2024-07-15 10:00:13.793','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-15T07:00:13.928Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542866 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-07-15T07:00:13.933Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(53400)
;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

-- Process: ModCntr_Specific_Delete_Price_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price_Selection)
-- ParameterName: C_Currency_ID
-- 2024-07-15T07:00:27.460Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,193,0,585406,542867,30,'C_Currency_ID',TO_TIMESTAMP('2024-07-15 10:00:27.321','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag','U',0,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','Y','N','Währung',40,TO_TIMESTAMP('2024-07-15 10:00:27.321','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-15T07:00:27.461Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542867 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-07-15T07:00:27.463Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(193)
;

-- Process: ModCntr_Specific_Delete_Price_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price_Selection)
-- ParameterName: C_UOM_ID
-- 2024-07-15T07:00:35.946Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,215,0,585406,542868,30,'C_UOM_ID',TO_TIMESTAMP('2024-07-15 10:00:35.825','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','U',0,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','Y','N','Maßeinheit',50,TO_TIMESTAMP('2024-07-15 10:00:35.825','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-15T07:00:35.947Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542868 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-07-15T07:00:35.949Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(215)
;

