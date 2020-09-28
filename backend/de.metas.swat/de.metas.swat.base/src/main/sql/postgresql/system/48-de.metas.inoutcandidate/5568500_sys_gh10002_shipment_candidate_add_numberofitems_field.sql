-- 2020-09-22T20:09:00.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-09-22 22:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=617675
;

-- 2020-09-22T20:09:56.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,617675,0,500221,540052,571533,'F',TO_TIMESTAMP('2020-09-22 22:09:56','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter','Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Lieferung gibt.','Y','Y','N','Y','N','N','N',0,'FilteredItemsWithSameHeaderAggregationKey',330,0,0,TO_TIMESTAMP('2020-09-22 22:09:56','YYYY-MM-DD HH24:MI:SS'),100)
;

