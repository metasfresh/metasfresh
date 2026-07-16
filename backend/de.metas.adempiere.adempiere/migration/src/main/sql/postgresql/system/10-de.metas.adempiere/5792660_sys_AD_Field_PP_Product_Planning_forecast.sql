-- Migration: AD_Field entries for forecast columns on PP_Product_Planning tab
-- Window: Produkt Plandaten (AD_Window_ID=540750)
-- Tab: Produktplanung (AD_Tab_ID=542102)

-- AD_Field: Forecast_CalculationMethod (AD_Column_ID=592199)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,592199,774856,0,542102,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,1,'D','Y','Y','Y','N','N','N','N','N','Berechnungsmethode Prognose',30,30,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774856
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

UPDATE AD_Field_Trl SET Name='Forecast Calculation Method', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Field_ID=774856 AND AD_Language='en_US';

-- AD_Field: Forecast_PrecisionUnit (AD_Column_ID=592200)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,592200,774857,0,542102,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,1,'D','Y','Y','Y','N','N','N','N','Y','Prognose Zeiteinheit',40,40,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774857
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

UPDATE AD_Field_Trl SET Name='Forecast Precision Unit', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Field_ID=774857 AND AD_Language='en_US';

-- AD_Field: Forecast_Frequency (AD_Column_ID=592201)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,592201,774858,0,542102,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,10,'D','Y','Y','Y','N','N','N','N','N','Prognose Bestellhäufigkeit',50,50,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774858
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

UPDATE AD_Field_Trl SET Name='Forecast Frequency', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Field_ID=774858 AND AD_Language='en_US';

-- AD_Field: Forecast_BufferTime (AD_Column_ID=592202)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,592202,774859,0,542102,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,10,'D','Y','Y','Y','N','N','N','N','Y','Prognose Pufferzeit',60,60,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774859
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

UPDATE AD_Field_Trl SET Name='Forecast Buffer Time', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Field_ID=774859 AND AD_Language='en_US';

-- AD_Field: IsExcludeFromForecast (AD_Column_ID=592203)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,592203,774860,0,542102,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100,1,'D','Y','Y','Y','N','N','N','N','N','Von Prognose ausschließen',70,70,TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'),100);

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774860
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

UPDATE AD_Field_Trl SET Name='Exclude from Forecast', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Field_ID=774860 AND AD_Language='en_US';
