--
-- show plants
-- don't show any attributes
-- show estimated on-hand-qtys QtyOnHandEstimate
--

update ad_sysconfig set value='Y', updated='2021-02-18 01:11', updatedby=99 where name ='de.metas.ui.web.material.cockpit.DisplayPerPlantDetailRows';
update ad_sysconfig set value='Y', updated='2021-02-18 01:11', updatedby=99 where name ='de.metas.ui.web.material.cockpit.field.QtyOnHandEstimate.IsDisplayed';

update dim_dimension_spec set isincludeempty='N', isincludeothergroup='N', updated='2021-02-18 01:11', updatedby=99 where dim_dimension_spec_id=540010; -- Material_Cockpit_Default_Spec
update dim_dimension_spec_attribute set isactive='N', updated='2021-02-18 01:11', updatedby=99 where dim_dimension_spec_id=540010; -- Material_Cockpit_Default_Spec
