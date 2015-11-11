--
-- renaming and rearanging fields to better distinguish between *filters* and *assignments*;
-- also translating and fixing some entitytypes etc
--

-- 05.11.2015 10:56
-- URL zum Konzept
INSERT INTO AD_FieldGroup (AD_Client_ID,AD_FieldGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsCollapsedByDefault,Name,Updated,UpdatedBy) VALUES (0,540080,0,TO_TIMESTAMP('2015-11-05 10:56:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Filterkriterien',TO_TIMESTAMP('2015-11-05 10:56:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.11.2015 10:56
-- URL zum Konzept
INSERT INTO AD_FieldGroup_Trl (AD_Language,AD_FieldGroup_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_FieldGroup_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_FieldGroup t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_FieldGroup_ID=540080 AND NOT EXISTS (SELECT * FROM AD_FieldGroup_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_FieldGroup_ID=t.AD_FieldGroup_ID)
;

-- 05.11.2015 10:57
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540080,Updated=TO_TIMESTAMP('2015-11-05 10:57:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551297
;

-- 05.11.2015 10:58
-- URL zum Konzept
UPDATE AD_FieldGroup SET FieldGroupType='L',Updated=TO_TIMESTAMP('2015-11-05 10:58:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_FieldGroup_ID=540080
;

-- 05.11.2015 10:58
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540080,Updated=TO_TIMESTAMP('2015-11-05 10:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551301
;

-- 05.11.2015 10:58
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540080,Updated=TO_TIMESTAMP('2015-11-05 10:58:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551292
;

-- 05.11.2015 10:58
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540080,Updated=TO_TIMESTAMP('2015-11-05 10:58:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551299
;

-- 05.11.2015 10:58
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540080,Updated=TO_TIMESTAMP('2015-11-05 10:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551300
;

-- 05.11.2015 11:00
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540080,Updated=TO_TIMESTAMP('2015-11-05 11:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551304
;

-- 05.11.2015 11:01
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540080,Updated=TO_TIMESTAMP('2015-11-05 11:01:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555108
;

-- 05.11.2015 11:02
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540080,Updated=TO_TIMESTAMP('2015-11-05 11:02:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555109
;

-- 05.11.2015 11:02
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2015-11-05 11:02:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555185
;

-- 05.11.2015 11:04
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540080, EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2015-11-05 11:04:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555184
;

-- 05.11.2015 11:04
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540080,Updated=TO_TIMESTAMP('2015-11-05 11:04:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555185
;

-- 05.11.2015 11:07
-- URL zum Konzept
INSERT INTO AD_FieldGroup (AD_Client_ID,AD_FieldGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,FieldGroupType,IsActive,IsCollapsedByDefault,Name,Updated,UpdatedBy) VALUES (0,540081,0,TO_TIMESTAMP('2015-11-05 11:07:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','L','Y','N','Zuordnung zu logischem Drucker',TO_TIMESTAMP('2015-11-05 11:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.11.2015 11:07
-- URL zum Konzept
INSERT INTO AD_FieldGroup_Trl (AD_Language,AD_FieldGroup_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_FieldGroup_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_FieldGroup t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_FieldGroup_ID=540081 AND NOT EXISTS (SELECT * FROM AD_FieldGroup_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_FieldGroup_ID=t.AD_FieldGroup_ID)
;

-- 05.11.2015 11:07
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540081,Updated=TO_TIMESTAMP('2015-11-05 11:07:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551302
;

-- 05.11.2015 11:07
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540081,Updated=TO_TIMESTAMP('2015-11-05 11:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551298
;

-- 05.11.2015 11:07
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=540080, EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2015-11-05 11:07:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556239
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556239
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551289
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551293
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551297
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551301
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551292
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551299
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551300
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551304
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555108
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555109
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555184
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555185
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551302
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551298
;

-- 05.11.2015 11:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2015-11-05 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551294
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556239
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551289
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551297
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551301
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551299
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551300
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551304
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555184
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555108
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555109
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551294
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555185
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551302
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551298
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2015-11-05 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551293
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2015-11-05 11:11:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555184
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2015-11-05 11:11:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555108
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2015-11-05 11:11:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555109
;

-- 05.11.2015 11:11
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-11-05 11:11:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551289
;

-- 05.11.2015 11:12
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2015-11-05 11:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551300
;

-- 05.11.2015 11:12
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-11-05 11:12:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551304
;

-- 05.11.2015 11:12
-- URL zum Konzept
UPDATE AD_Element SET Name='Seiten-Kriterium', PrintName='Seiten-Kriterium',Updated=TO_TIMESTAMP('2015-11-05 11:12:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542632
;

-- 05.11.2015 11:12
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542632
;

-- 05.11.2015 11:12
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='RoutingType', Name='Seiten-Kriterium', Description=NULL, Help=NULL WHERE AD_Element_ID=542632
;

-- 05.11.2015 11:12
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='RoutingType', Name='Seiten-Kriterium', Description=NULL, Help=NULL, AD_Element_ID=542632 WHERE UPPER(ColumnName)='ROUTINGTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 05.11.2015 11:12
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='RoutingType', Name='Seiten-Kriterium', Description=NULL, Help=NULL WHERE AD_Element_ID=542632 AND IsCentrallyMaintained='Y'
;

-- 05.11.2015 11:12
-- URL zum Konzept
UPDATE AD_Field SET Name='Seiten-Kriterium', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542632) AND IsCentrallyMaintained='Y'
;

-- 05.11.2015 11:12
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Seiten-Kriterium', Name='Seiten-Kriterium' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542632)
;

-- 05.11.2015 11:13
-- URL zum Konzept
UPDATE AD_Element SET Name='Letzte Seiten', PrintName='Letzte Seiten',Updated=TO_TIMESTAMP('2015-11-05 11:13:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542633
;

-- 05.11.2015 11:13
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542633
;

-- 05.11.2015 11:13
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='LastPages', Name='Letzte Seiten', Description=NULL, Help=NULL WHERE AD_Element_ID=542633
;

-- 05.11.2015 11:13
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LastPages', Name='Letzte Seiten', Description=NULL, Help=NULL, AD_Element_ID=542633 WHERE UPPER(ColumnName)='LASTPAGES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 05.11.2015 11:13
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LastPages', Name='Letzte Seiten', Description=NULL, Help=NULL WHERE AD_Element_ID=542633 AND IsCentrallyMaintained='Y'
;

-- 05.11.2015 11:13
-- URL zum Konzept
UPDATE AD_Field SET Name='Letzte Seiten', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542633) AND IsCentrallyMaintained='Y'
;

-- 05.11.2015 11:13
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Letzte Seiten', Name='Letzte Seiten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542633)
;

-- 05.11.2015 11:15
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=160,Updated=TO_TIMESTAMP('2015-11-05 11:15:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551294
;

-- 05.11.2015 11:15
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2015-11-05 11:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551294
;

-- 05.11.2015 11:16
-- URL zum Konzept
UPDATE AD_FieldGroup SET Name='Drucker-Zuordnung',Updated=TO_TIMESTAMP('2015-11-05 11:16:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_FieldGroup_ID=540081
;

-- 05.11.2015 11:16
-- URL zum Konzept
UPDATE AD_FieldGroup_Trl SET IsTranslated='N' WHERE AD_FieldGroup_ID=540081
;

-- 05.11.2015 11:17
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='letzte Seiten',Updated=TO_TIMESTAMP('2015-11-05 11:17:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540873
;

-- 05.11.2015 11:17
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=540873
;

-- 05.11.2015 11:17
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Seiten von-bis',Updated=TO_TIMESTAMP('2015-11-05 11:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540872
;

-- 05.11.2015 11:17
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=540872
;

-- 05.11.2015 11:18
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@RoutingType@ = L',Updated=TO_TIMESTAMP('2015-11-05 11:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555185
;

-- 05.11.2015 11:19
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@RoutingType@ = P',Updated=TO_TIMESTAMP('2015-11-05 11:19:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555109
;

-- 05.11.2015 11:19
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@RoutingType@ = P',Updated=TO_TIMESTAMP('2015-11-05 11:19:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555108
;

-- 05.11.2015 11:19
-- URL zum Konzept
UPDATE AD_Tab SET OrderByClause='SeqNo',Updated=TO_TIMESTAMP('2015-11-05 11:19:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540469
;

-- 05.11.2015 11:20
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Letzte Seiten',Updated=TO_TIMESTAMP('2015-11-05 11:20:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540873
;

-- 05.11.2015 11:20
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=540873
;

-- 05.11.2015 11:22
-- URL zum Konzept
UPDATE AD_Field SET Help='The Direct Print checkbox indicates that this report will print without a print dialog box being displayed.
Note: currently not showing because it''s not really used', IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-11-05 11:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551294
;

-- 05.11.2015 11:22
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=551294
;



-- 05.11.2015 11:31
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Letzte Seiten (optional)',Updated=TO_TIMESTAMP('2015-11-05 11:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540873
;

-- 05.11.2015 11:31
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=540873
;

-- 05.11.2015 11:31
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Seiten von-bis (optional)',Updated=TO_TIMESTAMP('2015-11-05 11:31:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540872
;

-- 05.11.2015 11:31
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=540872
;

