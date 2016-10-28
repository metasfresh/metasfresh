-- Oct 28, 2016 12:23 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(
	select count(1)
	from RV_M_Material_Tracking_HU_Details hu
	where hu.M_Material_Tracking_ID=M_Material_Tracking.M_Material_Tracking_ID
	and hu.IsActive=''Y''
	and hu.IsQualityInspection=''Y'' or hu.QualityInspectionCycle is not null
)
',Updated=TO_TIMESTAMP('2016-10-28 12:23:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552797
;

