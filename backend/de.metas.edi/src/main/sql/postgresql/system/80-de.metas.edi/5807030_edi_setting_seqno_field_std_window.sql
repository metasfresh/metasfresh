-- AD_Field + AD_UI_Element for SeqNo (C_BPartner_EDI_Setting.SeqNo) on tab 549287
-- (EDI-Einstellungen tab in standard BPartner window 123)
-- Resolves missing AD_Field for AD_Column_ID=592791 (SeqNo, AD_Element_ID=566).
-- Placed first in grid (SeqNoGrid=1) in the primary element group (555417).
--
-- IDs allocated from idserver.metas.de:
--   AD_Field_ID   780739 /*From ID Server*/
--   AD_UI_Element 652034 /*From ID Server*/

-- Field: SeqNo
-- AD_Field_ID=780739 (From ID Server), AD_Column_ID=592791, AD_Element_ID=566
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592791,780739 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-09 08:00:00','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2026-06-09 08:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780739
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(566)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=780739
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780739)
;

-- UI Element: primary group (555417) — SeqNo first in grid (SeqNoGrid=1, SeqNo=1)
-- AD_UI_Element_ID=652034 (From ID Server)
-- SeqNoGrid=1 places it before Geschäftspartner (SeqNoGrid=5) and Standort (SeqNoGrid=10)
-- SeqNo=1 places it first in the form view within the primary group
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780739,0,549287,555417,652034 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-09 08:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Reihenfolge',1,1,0,TO_TIMESTAMP('2026-06-09 08:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
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
