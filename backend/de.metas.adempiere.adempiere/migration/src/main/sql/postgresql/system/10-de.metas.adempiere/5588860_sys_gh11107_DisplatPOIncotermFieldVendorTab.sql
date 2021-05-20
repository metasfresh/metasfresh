-- 2021-05-18T09:53:03.156Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579178,0,'PO_Incoterm',TO_TIMESTAMP('2021-05-18 11:53:03','YYYY-MM-DD HH24:MI:SS'),100,'Internationale Handelsklauseln (engl. International Commercial Terms)','D','Incoterms (International Commercial Terms, deutsch: Internationale Handelsklauseln) sind eine Reihe internationaler Regeln zur Interpretation spezifizierter Handelsbedingungen im Außenhandelsgeschäft.

Die Incoterms wurden von der Internationalen Handelskammer (International Chamber of Commerce, ICC) erstmals 1936 aufgestellt, um eine gemeinsame Basis für den internationalen Handel zu schaffen. Sie regeln vor allem die Art und Weise der Lieferung von Gütern. Die Bestimmungen legen fest, welche Transportkosten der Verkäufer, welche der Käufer zu tragen hat und wer im Falle eines Verlustes der Ware das finanzielle Risiko trägt. Die Incoterms geben jedoch keine Auskunft darüber, wann und wo das Eigentum an der Ware von dem Verkäufer auf den Käufer übergeht. Der Stand der Incoterms wird durch Angabe der Jahreszahl gekennzeichnet. Aktuell gelten die Incoterms 2000 (6. Revision).','Y','PO_Incoterm','PO_Incoterm',TO_TIMESTAMP('2021-05-18 11:53:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-18T09:53:03.159Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579178 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-18T10:06:38.108Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573903,579178,0,17,501599,291,'PO_Incoterm',TO_TIMESTAMP('2021-05-18 12:06:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Internationale Handelsklauseln (engl. International Commercial Terms)','D',0,3,'Incoterms (International Commercial Terms, deutsch: Internationale Handelsklauseln) sind eine Reihe internationaler Regeln zur Interpretation spezifizierter Handelsbedingungen im Außenhandelsgeschäft.

Die Incoterms wurden von der Internationalen Handelskammer (International Chamber of Commerce, ICC) erstmals 1936 aufgestellt, um eine gemeinsame Basis für den internationalen Handel zu schaffen. Sie regeln vor allem die Art und Weise der Lieferung von Gütern. Die Bestimmungen legen fest, welche Transportkosten der Verkäufer, welche der Käufer zu tragen hat und wer im Falle eines Verlustes der Ware das finanzielle Risiko trägt. Die Incoterms geben jedoch keine Auskunft darüber, wann und wo das Eigentum an der Ware von dem Verkäufer auf den Käufer übergeht. Der Stand der Incoterms wird durch Angabe der Jahreszahl gekennzeichnet. Aktuell gelten die Incoterms 2000 (6. Revision).','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'PO_Incoterm',0,0,TO_TIMESTAMP('2021-05-18 12:06:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-18T10:06:38.109Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573903 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-18T10:06:38.128Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579178)
;

-- 2021-05-18T10:10:06.197Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573903,645697,0,224,0,TO_TIMESTAMP('2021-05-18 12:10:06','YYYY-MM-DD HH24:MI:SS'),100,'Internationale Handelsklauseln (engl. International Commercial Terms)',0,'D','Incoterms (International Commercial Terms, deutsch: Internationale Handelsklauseln) sind eine Reihe internationaler Regeln zur Interpretation spezifizierter Handelsbedingungen im Außenhandelsgeschäft.

Die Incoterms wurden von der Internationalen Handelskammer (International Chamber of Commerce, ICC) erstmals 1936 aufgestellt, um eine gemeinsame Basis für den internationalen Handel zu schaffen. Sie regeln vor allem die Art und Weise der Lieferung von Gütern. Die Bestimmungen legen fest, welche Transportkosten der Verkäufer, welche der Käufer zu tragen hat und wer im Falle eines Verlustes der Ware das finanzielle Risiko trägt. Die Incoterms geben jedoch keine Auskunft darüber, wann und wo das Eigentum an der Ware von dem Verkäufer auf den Käufer übergeht. Der Stand der Incoterms wird durch Angabe der Jahreszahl gekennzeichnet. Aktuell gelten die Incoterms 2000 (6. Revision).',0,'Y','Y','Y','N','N','N','N','N','PO_Incoterm',200,180,0,1,1,TO_TIMESTAMP('2021-05-18 12:10:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-18T10:10:06.198Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645697 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-18T10:10:06.201Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579178)
;

-- 2021-05-18T10:10:06.208Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645697
;

-- 2021-05-18T10:10:06.210Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(645697)
;

-- 2021-05-18T10:12:48.091Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,645697,0,224,1000033,584844,'F',TO_TIMESTAMP('2021-05-18 12:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Internationale Handelsklauseln (engl. International Commercial Terms)','Incoterms (International Commercial Terms, deutsch: Internationale Handelsklauseln) sind eine Reihe internationaler Regeln zur Interpretation spezifizierter Handelsbedingungen im Außenhandelsgeschäft.

Die Incoterms wurden von der Internationalen Handelskammer (International Chamber of Commerce, ICC) erstmals 1936 aufgestellt, um eine gemeinsame Basis für den internationalen Handel zu schaffen. Sie regeln vor allem die Art und Weise der Lieferung von Gütern. Die Bestimmungen legen fest, welche Transportkosten der Verkäufer, welche der Käufer zu tragen hat und wer im Falle eines Verlustes der Ware das finanzielle Risiko trägt. Die Incoterms geben jedoch keine Auskunft darüber, wann und wo das Eigentum an der Ware von dem Verkäufer auf den Käufer übergeht. Der Stand der Incoterms wird durch Angabe der Jahreszahl gekennzeichnet. Aktuell gelten die Incoterms 2000 (6. Revision).','Y','N','N','Y','N','N','N',0,'PO_Incoterm',145,0,0,TO_TIMESTAMP('2021-05-18 12:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-18T10:12:59.225Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-05-18 12:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584844
;

-- 2021-05-18T10:12:59.227Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-05-18 12:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000271
;

-- 2021-05-18T10:12:59.229Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-05-18 12:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000281
;

-- 2021-05-18T10:12:59.231Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-05-18 12:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000279
;

-- 2021-05-18T10:12:59.233Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-05-18 12:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000280
;

-- 2021-05-18T10:12:59.236Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-05-18 12:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000282
;

-- 2021-05-18T10:12:59.238Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-05-18 12:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546537
;

-- 2021-05-20T06:16:57.922Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=501607, Description='Internationale Handelsklauseln (engl. International Commercial Terms)', Help='Incoterms (International Commercial Terms, deutsch: Internationale Handelsklauseln) sind eine Reihe internationaler Regeln zur Interpretation spezifizierter Handelsbedingungen im AuÃŸenhandelsgeschÃ¤ft.

Die Incoterms wurden von der Internationalen Handelskammer (International Chamber of Commerce, ICC) erstmals 1936 aufgestellt, um eine gemeinsame Basis fÃ¼r den internationalen Handel zu schaffen. Sie regeln vor allem die Art und Weise der Lieferung von GÃ¼tern. Die Bestimmungen legen fest, welche Transportkosten der VerkÃ¤ufer, welche der KÃ¤ufer zu tragen hat und wer im Falle eines Verlustes der Ware das finanzielle Risiko trÃ¤gt. Die Incoterms geben jedoch keine Auskunft darÃ¼ber, wann und wo das Eigentum an der Ware von dem VerkÃ¤ufer auf den KÃ¤ufer Ã¼bergeht. Der Stand der Incoterms wird durch Angabe der Jahreszahl gekennzeichnet. Aktuell gelten die Incoterms 2000 (6. Revision).', Name='Incoterm',Updated=TO_TIMESTAMP('2021-05-20 08:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645697
;

-- 2021-05-20T06:16:57.948Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501607)
;

-- 2021-05-20T06:16:58.025Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645697
;

-- 2021-05-20T06:16:58.044Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(645697)
;
