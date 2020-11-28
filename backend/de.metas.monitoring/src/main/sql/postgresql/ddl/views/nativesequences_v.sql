
CREATE OR REPLACE VIEW "de.metas.monitoring".nativesequences_v AS 
SELECT 
	sequences.sequence_name,
    sequences.maximum_value,
    nextval(((('"'::text || sequences.sequence_schema::text) || '".'::text) || sequences.sequence_name::text)::regclass) AS nextval,
    currval(((('"'::text || sequences.sequence_schema::text) || '".'::text) || sequences.sequence_name::text)::regclass) AS currval,
    round((sequences.maximum_value::numeric - currval(((('"'::text || sequences.sequence_schema::text) || '".'::text) || sequences.sequence_name::text)::regclass)::numeric) / sequences.maximum_value::numeric * 100::numeric, 2) AS sequence_range_free_percent
FROM information_schema.sequences
WHERE sequences.sequence_schema = 'public' /* only look at the public schema to avoid access right problems with the agent that is supposed to execute this view */
;
COMMENT ON VIEW "de.metas.monitoring".nativesequences_v IS 
'Selects our native sequences; can be monitored to make sure we don''t run out of numbers.
Used in monitoring like this:
select * from "de.metas.monitoring".nativesequences_v order by sequence_range_free_percent limit 1;
';
