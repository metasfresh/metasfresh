/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> IsForbidNewRecordCreation
-- Column: AD_Field.IsForbidNewRecordCreation
-- 2023-06-07T10:33:56.553362100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586735,716276,0,107,TO_TIMESTAMP('2023-06-07 13:33:56.307','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','IsForbidNewRecordCreation',TO_TIMESTAMP('2023-06-07 13:33:56.307','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T10:33:56.565923500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716276 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T10:33:56.571379200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582381) 
;

-- 2023-06-07T10:33:56.593059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716276
;

-- 2023-06-07T10:33:56.594457700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716276)
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> IsForbidNewRecordCreation
-- Column: AD_Field.IsForbidNewRecordCreation
-- 2023-06-07T10:34:28.688072800Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-06-07 13:34:28.688','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716276
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> IsForbidNewRecordCreation
-- Column: AD_Field.IsForbidNewRecordCreation
-- 2023-06-07T10:36:05.673561400Z
UPDATE AD_Field SET SeqNo=105,Updated=TO_TIMESTAMP('2023-06-07 18:36:05.672','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716276
;

