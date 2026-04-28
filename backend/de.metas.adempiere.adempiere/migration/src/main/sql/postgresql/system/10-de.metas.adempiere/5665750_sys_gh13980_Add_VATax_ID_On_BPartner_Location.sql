

DO
$do$
    BEGIN
        IF EXISTS (select 1 from ad_column where columnname = 'VATaxID' and ad_table_id = get_table_id('C_BPartner_Location')) THEN
            return;
        ELSE
            -- Column: C_BPartner_Location.VATaxID
            -- 2022-11-23T11:41:41.608Z
            INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585129,502388,0,10,293,'VATaxID',TO_TIMESTAMP('2022-11-23 13:41:41','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,60,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Umsatzsteuer ID',0,0,TO_TIMESTAMP('2022-11-23 13:41:41','YYYY-MM-DD HH24:MI:SS'),100,0)
            ;

            -- 2022-11-23T11:41:41.616Z
            INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585129 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
            ;

            -- 2022-11-23T11:41:41.667Z
            /* DDL */  perform update_Column_Translation_From_AD_Element(502388)
            ;

            -- 2022-11-23T11:41:45.512Z
            /* DDL */ perform public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN VATaxID VARCHAR(60)')
            ;

            -- Field: Geschäftspartner(123,D) -> Adresse(222,D) -> Umsatzsteuer ID
            -- Column: C_BPartner_Location.VATaxID
            -- 2022-11-23T11:44:12.026Z
            INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585129,708161,0,222,0,TO_TIMESTAMP('2022-11-23 13:44:11','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','N','Umsatzsteuer ID',0,200,0,1,1,TO_TIMESTAMP('2022-11-23 13:44:11','YYYY-MM-DD HH24:MI:SS'),100)
            ;

            -- 2022-11-23T11:44:12.041Z
            INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708161 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
            ;

            -- 2022-11-23T11:44:12.048Z
            /* DDL */  perform update_FieldTranslation_From_AD_Name_Element(502388)
            ;

            -- 2022-11-23T11:44:12.074Z
            DELETE FROM AD_Element_Link WHERE AD_Field_ID=708161
            ;

            -- 2022-11-23T11:44:12.087Z
            /* DDL */ perform AD_Element_Link_Create_Missing_Field(708161)
            ;

            -- Field: Geschäftspartner(123,D) -> Adresse(222,D) -> Umsatzsteuer ID
            -- Column: C_BPartner_Location.VATaxID
            -- 2022-11-23T11:45:30.986Z
            UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-11-23 13:45:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708161
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Umsatzsteuer ID
            -- Column: C_BPartner_Location.VATaxID
            -- 2022-11-23T11:48:43.060Z
            INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708161,0,222,613546,1000034,'F',TO_TIMESTAMP('2022-11-23 13:48:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Umsatzsteuer ID',190,0,0,TO_TIMESTAMP('2022-11-23 13:48:42','YYYY-MM-DD HH24:MI:SS'),100)
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Mandant
            -- Column: C_BPartner_Location.AD_Client_ID
            -- 2022-11-23T11:50:16.286Z
            UPDATE AD_UI_Element SET SeqNo=190,Updated=TO_TIMESTAMP('2022-11-23 13:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546540
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Umsatzsteuer ID
            -- Column: C_BPartner_Location.VATaxID
            -- 2022-11-23T11:50:29.866Z
            UPDATE AD_UI_Element SET SeqNo=180,Updated=TO_TIMESTAMP('2022-11-23 13:50:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613546
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Umsatzsteuer ID
            -- Column: C_BPartner_Location.VATaxID
            -- 2022-11-23T11:51:52.710Z
            UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2022-11-23 13:51:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613546
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Aktiv
            -- Column: C_BPartner_Location.IsActive
            -- 2022-11-23T11:52:03.485Z
            UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2022-11-23 13:52:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546541
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Lieferadresse
            -- Column: C_BPartner_Location.IsShipTo
            -- 2022-11-23T11:52:17.372Z
            UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2022-11-23 13:52:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546542
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Lieferstandard
            -- Column: C_BPartner_Location.IsShipToDefault
            -- 2022-11-23T11:52:38.460Z
            UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2022-11-23 13:52:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546543
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Rechnungsadresse
            -- Column: C_BPartner_Location.IsBillTo
            -- 2022-11-23T11:52:56.176Z
            UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2022-11-23 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546544
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Rechnungsstandard
            -- Column: C_BPartner_Location.IsBillToDefault
            -- 2022-11-23T11:53:09.271Z
            UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2022-11-23 13:53:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546545
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Abladeort
            -- Column: C_BPartner_Location.IsHandOverLocation
            -- 2022-11-23T11:53:21.010Z
            UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2022-11-23 13:53:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546546
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.EDI Anschrift
            -- Column: C_BPartner_Location.IsRemitTo
            -- 2022-11-23T11:53:33.504Z
            UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2022-11-23 13:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546547
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Replikation Standard
            -- Column: C_BPartner_Location.IsReplicationLookupDefault
            -- 2022-11-23T11:53:48.826Z
            UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2022-11-23 13:53:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546548
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Visitor Address
            -- Column: C_BPartner_Location.VisitorsAddress
            -- 2022-11-23T11:53:59.067Z
            UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2022-11-23 13:53:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559625
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Ephemeral
            -- Column: C_BPartner_Location.IsEphemeral
            -- 2022-11-23T11:54:19.395Z
            UPDATE AD_UI_Element SET SeqNo=142,Updated=TO_TIMESTAMP('2022-11-23 13:54:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605282
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Setup Place No.
            -- Column: C_BPartner_Location.Setup_Place_No
            -- 2022-11-23T11:54:54.115Z
            UPDATE AD_UI_Element SET SeqNo=145,Updated=TO_TIMESTAMP('2022-11-23 13:54:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576299
            ;

            -- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Mandant
            -- Column: C_BPartner_Location.AD_Client_ID
            -- 2022-11-23T11:55:39.642Z
            UPDATE AD_UI_Element SET SeqNo=180,Updated=TO_TIMESTAMP('2022-11-23 13:55:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546540
            ;
        END IF;
    END
$do$;