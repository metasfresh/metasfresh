-- 2021-12-07T16:13:41.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543770,547715,TO_TIMESTAMP('2021-12-07 18:13:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','lookup',30,TO_TIMESTAMP('2021-12-07 18:13:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-07T16:15:10.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578451,675190,0,543838,0,TO_TIMESTAMP('2021-12-07 18:15:09','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Produkt-Zuordnung',0,10,0,1,1,TO_TIMESTAMP('2021-12-07 18:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-07T16:15:10.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=675190 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-07T16:15:10.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580241) 
;

-- 2021-12-07T16:15:10.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675190
;

-- 2021-12-07T16:15:10.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(675190)
;

-- 2021-12-07T16:16:31.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543770,547716,TO_TIMESTAMP('2021-12-07 18:16:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','lookup',30,TO_TIMESTAMP('2021-12-07 18:16:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-07T16:17:16.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,675190,0,543838,598393,547716,'F',TO_TIMESTAMP('2021-12-07 18:17:16','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird','Y','N','N','Y','N','N','N',0,'Produkt-Zuordnung',10,0,0,TO_TIMESTAMP('2021-12-07 18:17:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T07:43:16.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@JSONPathConstantBPartnerID/''''@!''''',Updated=TO_TIMESTAMP('2021-12-08 09:43:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578850
;

-- 2021-12-08T07:46:11.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=547715
;

/*
 * #%L
 * de.metas.externalsystem
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

-- 2021-12-08T07:46:40.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545816, SeqNo=40,Updated=TO_TIMESTAMP('2021-12-08 09:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=598393
;

-- 2021-12-08T07:46:51.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=547716
;

-- 2021-12-08T07:48:04.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543771,547717,TO_TIMESTAMP('2021-12-08 09:48:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','lookup',40,TO_TIMESTAMP('2021-12-08 09:48:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T07:48:11.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=25,Updated=TO_TIMESTAMP('2021-12-08 09:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=547717
;

-- 2021-12-08T07:48:41.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=547717, SeqNo=10,Updated=TO_TIMESTAMP('2021-12-08 09:48:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=598393
;

