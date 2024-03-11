
DROP VIEW IF EXISTS "de.metas.monitoring".async_workpackage_performance_v;

CREATE OR REPLACE VIEW "de.metas.monitoring".async_workpackage_performance_v AS 
 SELECT pp.classname,
    wp.laststarttime::date AS day,
    date_part('hour'::text, wp.laststarttime) AS hourofday,
    count(wp.c_queue_workpackage_id) AS count,
    avg(wp.lastdurationmillis) AS avg,
    sum(wp.lastdurationmillis) AS sum,
    var_samp(wp.lastdurationmillis) AS var_samp
   FROM c_queue_workpackage wp
     JOIN c_queue_packageprocessor pp ON pp.c_queue_packageprocessor_id = wp.c_queue_packageprocessor_id
  WHERE true AND wp.laststarttime::date >= '2015-06-19'::date
  GROUP BY pp.classname, (wp.laststarttime::date), (date_part('hour'::text, wp.laststarttime))
  ORDER BY pp.classname, (wp.laststarttime::date), (date_part('hour'::text, wp.laststarttime));
