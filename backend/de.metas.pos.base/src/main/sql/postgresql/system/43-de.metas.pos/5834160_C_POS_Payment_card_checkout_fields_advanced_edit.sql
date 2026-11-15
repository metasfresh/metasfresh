-- C_POS_Payment: the card checkout columns of a till card sale whose SumUp result may be unknown, shown read-only in the
-- advanced edit (Alt+E) of the "POS Zahlung" tab (AD_Tab_ID=547593) of the "POS Auftrag" window (AD_Window_ID=541818), so that the
-- back office can find a card payment's SumUp charge in the SumUp dashboard (current and released SumUp client transaction id)
-- and see when its current checkout started and since when looking it up at SumUp has been failing.
-- IDs allocated from the central ID server.

-- Field: POS Auftrag(541818,de.metas.pos) -> POS Zahlung(547593,de.metas.pos) -> SumUp-Client-Transaktions-ID
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593827,788179 /*From ID Server*/,0,547593,TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.pos','Y','N','N','N','N','N','Y','N','SumUp-Client-Transaktions-ID',TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=788179 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- UI Element: POS Auftrag(541818,de.metas.pos) -> POS Zahlung(547593,de.metas.pos) -> advanced -> SumUp-Client-Transaktions-ID
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,788179,0,547593,551963,656496 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','SumUp-Client-Transaktions-ID',20,0,0,TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: POS Auftrag(541818,de.metas.pos) -> POS Zahlung(547593,de.metas.pos) -> Kartenzahlung gestartet am
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593826,788178 /*From ID Server*/,0,547593,TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.pos','Y','N','N','N','N','N','Y','N','Kartenzahlung gestartet am',TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=788178 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- UI Element: POS Auftrag(541818,de.metas.pos) -> POS Zahlung(547593,de.metas.pos) -> advanced -> Kartenzahlung gestartet am
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,788178,0,547593,551963,656495 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','Kartenzahlung gestartet am',30,0,0,TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: POS Auftrag(541818,de.metas.pos) -> POS Zahlung(547593,de.metas.pos) -> Freigegebene SumUp-Client-Transaktions-ID
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593828,788180 /*From ID Server*/,0,547593,TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.pos','Y','N','N','N','N','N','Y','N','Freigegebene SumUp-Client-Transaktions-ID',TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=788180 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- UI Element: POS Auftrag(541818,de.metas.pos) -> POS Zahlung(547593,de.metas.pos) -> advanced -> Freigegebene SumUp-Client-Transaktions-ID
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,788180,0,547593,551963,656497 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','Freigegebene SumUp-Client-Transaktions-ID',40,0,0,TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: POS Auftrag(541818,de.metas.pos) -> POS Zahlung(547593,de.metas.pos) -> SumUp-Abfrage fehlgeschlagen seit
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593829,788181 /*From ID Server*/,0,547593,TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.pos','Y','N','N','N','N','N','Y','N','SumUp-Abfrage fehlgeschlagen seit',TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=788181 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- UI Element: POS Auftrag(541818,de.metas.pos) -> POS Zahlung(547593,de.metas.pos) -> advanced -> SumUp-Abfrage fehlgeschlagen seit
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,788181,0,547593,551963,656498 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','SumUp-Abfrage fehlgeschlagen seit',50,0,0,TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- the field captions in every language come from the columns' elements (de_DE / de_CH / en_US are translated there)
UPDATE AD_Field_Trl ft
SET Name=et.Name, Description=et.Description, Help=et.Help, IsTranslated=et.IsTranslated, Updated=TO_TIMESTAMP('2026-11-11 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM AD_Field f
         JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID
  AND et.AD_Language = ft.AD_Language
  AND f.AD_Field_ID IN (788178, 788179, 788180, 788181)
;
