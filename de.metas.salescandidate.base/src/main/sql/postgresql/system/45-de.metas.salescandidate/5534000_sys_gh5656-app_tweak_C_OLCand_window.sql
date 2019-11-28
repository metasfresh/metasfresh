-- 2019-10-13T11:06:24.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_LU_ID)', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-10-13 13:06:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569179
;

-- 2019-10-13T11:06:38.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export.',Updated=TO_TIMESTAMP('2019-10-13 13:06:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569179
;

-- 2019-10-13T11:08:01.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_TU_ID)', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-10-13 13:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569180
;

ALTER TABLE EDI_DesadvLine DROP M_HU_PackagingCode_LU_Text;
ALTER TABLE EDI_DesadvLine DROP M_HU_PackagingCode_TU_Text;

-- 2019-10-13T11:18:14.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export. The exporter makes sure that we won''t export a stale value here.',Updated=TO_TIMESTAMP('2019-10-13 13:18:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569179
;

-- 2019-10-13T11:18:30.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for DESADV-export. The exporter makes sure that we won''t export a stale value here.',Updated=TO_TIMESTAMP('2019-10-13 13:18:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569180
;

UPDATE C_Queue_WorkPackage_Param
SET p_string='/* Finally, drop the two columns we migrated */
ALTER TABLE AD_AttachmentEntry DROP COLUMN IF EXISTS AD_Table_ID, DROP COLUMN IF EXISTS Record_ID;'
WHERE C_Queue_WorkPackage_ID=1000004 and ParameterName='AfterFinishSQL';

UPDATE C_Queue_WorkPackage_Param
SET p_string='UPDATE c_flatrate_term ft_outer 
	SET C_OrderLine_Term_ID = data.C_OrderLine_Term_ID, updated=now(), updatedby=99
	FROM 
	(
		select 
			ft_outer.c_flatrate_term_ID as ft_to_update_c_flatrate_term_ID, 
			ft_inner.c_flatrate_term_ID, ft_inner.C_OrderLine_Term_ID, ft_inner.path
		FROM 
			c_flatrate_term ft_outer,
			(
			select ft.c_flatrate_term_ID, ft.C_OrderLine_Term_ID, hierarchy.path
			from c_flatrate_term ft
				JOIN de_metas_contracts.fetchflatratetermhierarchy_byc_flatrate_term_id(ft.C_Flatrate_Term_ID) hierarchy ON true
			where C_OrderLine_Term_ID is not null
			) ft_inner 
		WHERE true
			AND ft_inner.path @> ARRAY[ft_outer.c_flatrate_term_ID]
			AND ft_outer.C_OrderLine_Term_ID IS NULL
		LIMIT 5000
	) data
	WHERE data.ft_to_update_c_flatrate_term_ID = ft_outer.c_flatrate_term_ID'
WHERE C_Queue_WorkPackage_ID=1000002 and ParameterName='WorkPackageSQL';-- 2019-10-13T12:12:42.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540964, SeqNo=40,Updated=TO_TIMESTAMP('2019-10-13 14:12:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554455
;

-- 2019-10-13T12:12:54.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540964, SeqNo=50,Updated=TO_TIMESTAMP('2019-10-13 14:12:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554456
;

-- 2019-10-13T12:13:16.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2019-10-13 14:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554455
;

-- 2019-10-13T12:13:19.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2019-10-13 14:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554456
;

-- 2019-10-13T12:13:52.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540962, SeqNo=570,Updated=TO_TIMESTAMP('2019-10-13 14:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547257
;

-- 2019-10-13T12:14:22.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y', Name='AD_InputDataSource_ID', SeqNo=10,Updated=TO_TIMESTAMP('2019-10-13 14:14:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547257
;

-- 2019-10-13T12:15:07.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547257
;

-- 2019-10-13T12:15:07.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548677
;

-- 2019-10-13T12:15:07.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548678
;

-- 2019-10-13T12:15:07.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548675
;

-- 2019-10-13T12:15:07.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548676
;

-- 2019-10-13T12:15:07.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547269
;

-- 2019-10-13T12:15:07.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547271
;

-- 2019-10-13T12:15:07.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547274
;

-- 2019-10-13T12:15:07.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547259
;

-- 2019-10-13T12:15:07.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547260
;

-- 2019-10-13T12:15:07.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547261
;

-- 2019-10-13T12:15:07.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-10-13 14:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548695
;

-- 2019-10-13T12:15:26.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='M_Product_ID',Updated=TO_TIMESTAMP('2019-10-13 14:15:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547203
;

-- 2019-10-13T12:15:46.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N', Name='EXP_ReplicationTrx_ID',Updated=TO_TIMESTAMP('2019-10-13 14:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547245
;

-- 2019-10-13T12:16:35.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540964, SeqNo=60,Updated=TO_TIMESTAMP('2019-10-13 14:16:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547245
;

-- 2019-10-13T12:19:28.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@EXP_ReplicationTrx_ID/-1@>0',Updated=TO_TIMESTAMP('2019-10-13 14:19:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554018
;

-- 2019-10-13T12:21:22.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@ExternalHeaderId/-1@>0',Updated=TO_TIMESTAMP('2019-10-13 14:21:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570748
;

-- 2019-10-13T12:21:47.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@ExternalLineId/-1@>0',Updated=TO_TIMESTAMP('2019-10-13 14:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569300
;

-- 2019-10-13T12:24:37.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540968, SeqNo=50,Updated=TO_TIMESTAMP('2019-10-13 14:24:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548675
;

-- 2019-10-13T12:25:03.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2019-10-13 14:25:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548675
;

-- 2019-10-13T12:25:10.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-10-13 14:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548675
;

