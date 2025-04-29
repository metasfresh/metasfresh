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

-- Field: Produkt -> Produkt -> QR Code Configuration
-- Column: M_Product.QRCode_Configuration_ID
-- Field: Produkt(140,D) -> Produkt(180,D) -> QR Code Configuration
-- Column: M_Product.QRCode_Configuration_ID
-- 2024-02-15T05:22:33.161Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587946,725177,0,180,TO_TIMESTAMP('2024-02-15 07:22:32','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','QR Code Configuration',TO_TIMESTAMP('2024-02-15 07:22:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-15T05:22:33.167Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725177 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-15T05:22:33.188Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582965) 
;

-- 2024-02-15T05:22:33.205Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725177
;

-- 2024-02-15T05:22:33.210Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725177)
;

-- UI Element: Produkt -> Produkt.QR Code Configuration
-- Column: M_Product.QRCode_Configuration_ID
-- UI Element: Produkt(140,D) -> Produkt(180,D) -> advanced edit -> 10 -> hu.QR Code Configuration
-- Column: M_Product.QRCode_Configuration_ID
-- 2024-02-15T05:23:17.978Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725177,0,180,622936,542064,'F',TO_TIMESTAMP('2024-02-15 07:23:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'QR Code Configuration',50,0,0,TO_TIMESTAMP('2024-02-15 07:23:16','YYYY-MM-DD HH24:MI:SS'),100)
;

