update S_Resource set Name='Arbeitsaufwand' where s_resource_id=540013 and value='Labor';
update M_Product set Name='Arbeitsaufwand' where s_resource_id=540013 and value='Labor';

update ad_workflow set Name='Standard Reparatur Arbeitsablauf', documentno='Standard Reparatur Arbeitsablauf' where value='Default Service/Repair routing';
update ad_workflow_trl set name='Standard Reparatur Arbeitsablauf', istranslated='Y' where ad_workflow_id=540110 and ad_language in ('de_DE', 'de_CH');

update ad_wf_node set name='Arbeitsaufwand' where s_resource_id=540013 and value='Labor';
update ad_wf_node_trl set name='Arbeitsaufwand', istranslated='Y' where ad_wf_node_id=540243 and ad_language in ('de_DE', 'de_CH');

