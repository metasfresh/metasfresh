
delete from ad_tab where ad_table_id = get_table_id('AD_JAXRS_Endpoint');
delete from ad_table_process where ad_table_id = get_table_id('AD_JAXRS_Endpoint');
delete from ad_table where ad_table_id = get_table_id('AD_JAXRS_Endpoint');
drop table ad_jaxrs_endpoint;
drop SEQUENCE ad_jaxrs_endpoint_seq;

delete from ad_javaclass where ad_javaclass_type_id=540039;
delete from ad_javaclass_type where ad_javaclass_type_id=540039;

delete from ad_element where entitytype='de.metas.jax.rs';
delete from ad_menu where entitytype='de.metas.jax.rs';
delete from ad_process where entitytype='de.metas.jax.rs';
delete from ad_reference where entitytype='de.metas.jax.rs';
delete from ad_window where entitytype='de.metas.jax.rs';
delete from ad_entitytype where entitytype='de.metas.jax.rs';

---

delete from imp_processor_type where imp_processor_type_id=1000000; -- ActiveMQ
delete from ad_entitytype where entitytype='de.metas.jms';

delete from ad_Sysconfig where name in (
                                          'de.metas.jms.Password',
                                          'de.metas.jms.URL',
                                          'de.metas.jms.UseEmbeddedBroker',
                                          'de.metas.jms.User',
                                          'de.metas.procurement.webui.jms.queue.request',
                                          'de.metas.procurement.webui.jms.queue.response'
    );
    