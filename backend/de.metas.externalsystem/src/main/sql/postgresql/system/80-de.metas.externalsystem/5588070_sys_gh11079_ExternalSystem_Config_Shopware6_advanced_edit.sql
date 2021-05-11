-- 2021-05-11T08:08:42.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543838,543067,TO_TIMESTAMP('2021-05-11 11:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-05-11 11:08:42','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2021-05-11T08:08:42.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543067 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-05-11T08:09:00.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543858,543067,TO_TIMESTAMP('2021-05-11 11:09:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-05-11 11:09:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T08:09:19.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543858,545813,TO_TIMESTAMP('2021-05-11 11:09:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,TO_TIMESTAMP('2021-05-11 11:09:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T08:09:36.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,643991,0,543838,584731,545813,'F',TO_TIMESTAMP('2021-05-11 11:09:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Zugangs-ID',10,0,0,TO_TIMESTAMP('2021-05-11 11:09:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T08:11:37.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543859,543067,TO_TIMESTAMP('2021-05-11 11:11:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-05-11 11:11:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T08:12:09.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584731
;

-- 2021-05-11T08:12:14.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=545813
;

-- 2021-05-11T08:12:31.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543859,545814,TO_TIMESTAMP('2021-05-11 11:12:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,TO_TIMESTAMP('2021-05-11 11:12:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T08:12:49.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,643991,0,543838,584732,545814,'F',TO_TIMESTAMP('2021-05-11 11:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Zugangs-ID',10,0,0,TO_TIMESTAMP('2021-05-11 11:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T08:16:14.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584732
;

-- 2021-05-11T08:16:18.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=545814
;

-- 2021-05-11T08:16:24.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=543859
;

-- 2021-05-11T08:16:43.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,543858,545815,TO_TIMESTAMP('2021-05-11 11:16:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2021-05-11 11:16:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T08:17:09.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,643991,0,543838,584733,545815,'F',TO_TIMESTAMP('2021-05-11 11:17:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Zugangs-ID',10,0,0,TO_TIMESTAMP('2021-05-11 11:17:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T09:14:15.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,643992,0,543838,584734,545815,'F',TO_TIMESTAMP('2021-05-11 12:14:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Sicherheitsschlüssel',20,0,0,TO_TIMESTAMP('2021-05-11 12:14:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T09:14:35.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,643995,0,543838,584735,545815,'F',TO_TIMESTAMP('2021-05-11 12:14:35','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!','Y','Y','N','Y','N','N','N',0,'Kunden JSON-Path',30,0,0,TO_TIMESTAMP('2021-05-11 12:14:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T09:14:46.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,643996,0,543838,584736,545815,'F',TO_TIMESTAMP('2021-05-11 12:14:46','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Addresse die die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!','Y','Y','N','Y','N','N','N',0,'Adress JSON-Path',40,0,0,TO_TIMESTAMP('2021-05-11 12:14:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T09:15:12.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645427,0,543838,584737,545815,'F',TO_TIMESTAMP('2021-05-11 12:15:11','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.','Y','N','N','Y','N','N','N',0,'Vertriebpartner JSON-Path',50,0,0,TO_TIMESTAMP('2021-05-11 12:15:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-11T09:17:18.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2021-05-11 12:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584737
;

-- 2021-05-11T09:18:19.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584737
;

-- 2021-05-11T09:19:10.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645427,0,543838,584738,545815,'F',TO_TIMESTAMP('2021-05-11 12:19:10','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.','Y','Y','N','Y','N','N','N',0,'Vertriebpartner JSON-Path',50,0,0,TO_TIMESTAMP('2021-05-11 12:19:10','YYYY-MM-DD HH24:MI:SS'),100)
;

